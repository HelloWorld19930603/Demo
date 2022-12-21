package com.yg.shop.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yg.shop.server.constant.ArticleReadStatusEnum;
import com.yg.shop.server.constant.OperatorLogEnum;
import com.yg.shop.server.constant.OperatorLogResultEnum;
import com.yg.shop.server.constant.SystemConstant;
import com.yg.shop.server.entity.*;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.ArticleTree;
import com.yg.shop.server.pojo.vo.ArticleTreeVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;
import com.yg.shop.server.pojo.vo.SendArticleVo;
import com.yg.shop.server.repository.*;
import com.yg.shop.server.service.ArticleService;
import com.yg.shop.server.service.FileService;
import com.yg.shop.server.utils.MD5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时贴,未来文章接口的实现类
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final FileService fileService;
    private final FutureArticleRepository futureArticleRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final OperatorLogRepository operatorLogRepository;
    private final NotifyRepository notifyRepository;

    @Override
    public ResultPage<ArticleTree> articleTree(ArticleTreeVo vo) {
        ResultPage<ArticleTree> page = new ResultPage<>();
        List<Article> articleList;
        if (StringUtils.isEmpty(vo.getText())) {
            articleList = articleRepository.findByGroupGenUuid(vo.getGroupGenUuid());
        } else {
            articleList = articleRepository.findByGroupGenUuidAndContentIsLike(vo.getGroupGenUuid(), vo.getText());
        }
        if (!StringUtils.isEmpty(vo.getStartTime())) {
            articleList = articleList.stream()
                    .filter(i -> i.getCreateTime() >= vo.getStartTime() && i.getCreateTime() <= vo.getEndTime())
                    .collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(articleList)) {
            articleList = articleList.stream()
                    .filter(i -> i.getCreateTime() <= System.currentTimeMillis() / 1000)
                    .collect(Collectors.toList());
            page.setCount((long) articleList.size());
            List<ArticleTree> articleTreeList = articleList.stream()
                    .sorted(Comparator.comparing(Article::getCreateTime).reversed())
                    .skip((vo.getPageNum() - 1) * vo.getPageSize()).limit(vo.getPageSize())
                    .map(i -> {
                        ArticleTree tree = new ArticleTree();
                        BeanUtil.copyProperties(i, tree);
                        GroupMember firstByUserGenUuid = groupMemberRepository.findFirstByUserGenUuid(i.getUserGenUuid());
                        if (null != firstByUserGenUuid && !StringUtils.isEmpty(firstByUserGenUuid.getHeadPhoto())) {
                            firstByUserGenUuid.setHeadPhoto(StrUtil.SLASH + firstByUserGenUuid.getHeadPhoto());
                        }
                        tree.setGroupMember(firstByUserGenUuid);
                        if (!CollectionUtils.isEmpty(tree.getMediaList())) {
                            tree.setMediaList(tree.getMediaList().stream().peek(iter -> {
                                String file = iter.get("media_file");
                                if (null != file) {
                                    iter.put("media_file", StrUtil.SLASH + file);
                                }
                            }).collect(Collectors.toList()));
                        }
                        return tree;
                    }).collect(Collectors.toList());
            for (ArticleTree articleTree : articleTreeList) {
                Integer height = 0;
                findRelateArticle(articleTree, height, vo.getWeight(), vo.getHeight());
            }
            page.setList(articleTreeList);
        }
        return page;
    }

    @Override
    public Boolean sendArticle(SendArticleVo vo, LoginUserInfo user) {
        try {
            FutureArticle futureArticle = new FutureArticle();
            futureArticle.setGroupGenUuid(vo.getGroupGenUuid());
            futureArticle.setContent(vo.getContent());
            futureArticle.setInsertTime(System.currentTimeMillis() / 1000);
            futureArticle.setUpdateTime(System.currentTimeMillis() / 1000);
            futureArticle.setSendTime(vo.getSendTime());
            List<Map<String, String>> mapList = new ArrayList<>(10);
            if (!CollectionUtils.isEmpty(vo.getFileList())) {
                List<String> collect = vo.getFileList().stream().map(i -> {
                    String fileName = SystemConstant.shop + StrUtil.SLASH + vo.getGroupGenUuid() + StrUtil.SLASH + i.getOriginalFilename();
                    String upload = fileService.upload(SystemConstant.uploadUrl, fileName, i);
                    Map<String, String> map = new HashMap<>(16);
                    map.put("media_type", "1");
                    map.put("media_file", upload);
                    mapList.add(map);
                    return upload;
                }).collect(Collectors.toList());
                futureArticle.setMediaFileList(collect);
            }
            FutureArticle save = futureArticleRepository.save(futureArticle);

            Article article = new Article();
            article.setContent(vo.getContent());
            article.setMediaList(mapList);
            article.setCreateTime(vo.getSendTime());
            article.setFutureArticleId(save.getId());
            article.setInsertTime(System.currentTimeMillis() / 1000);
            article.setUpdateTime(System.currentTimeMillis() / 1000);
            article.setGroupGenUuid(vo.getGroupGenUuid());
            article.setUserGenUuid(vo.getUserGenUuid());
            article.setArticleGenUuid(MD5Util.MD5((vo.toString() + user.toString() + System.currentTimeMillis())));
            articleRepository.save(article);

            OperatorLog log = new OperatorLog();
            log.setWebUserId(user.getWebUserId());
            log.setWebUsername(user.getWebUsername());
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            HashMap<String, String> logMap = new HashMap<>(16);
            logMap.put("group_gen_uuid", vo.getGroupGenUuid());
            log.setData(logMap);
            log.setOperator(OperatorLogEnum.SEND_ARTICLE.getType());
            operatorLogRepository.save(log);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArticleTree info(String articleGenUuid, LoginUserInfo user) {
        List<Article> articles = articleRepository.findByArticleGenUuid(articleGenUuid);
        if (CollectionUtils.isEmpty(articles)) {
            return null;
        }
        Article article = articles.get(0);
        ArticleTree tree = new ArticleTree();
        BeanUtil.copyProperties(article, tree);
        GroupMember firstByUserGenUuid = groupMemberRepository.findFirstByUserGenUuid(tree.getUserGenUuid());
        if (null != firstByUserGenUuid && !StringUtils.isEmpty(firstByUserGenUuid.getHeadPhoto())) {
            firstByUserGenUuid.setHeadPhoto(StrUtil.SLASH + firstByUserGenUuid.getHeadPhoto());
        }
        tree.setGroupMember(firstByUserGenUuid);
        findRelateArticle(tree, 0, 10000, 10000);
        readArticle(articleGenUuid, user);
        return tree;
    }

    /**
     * 阅读文章操作
     *
     * @param articleGenUuid 文章uuid
     * @param user           用户信息
     */
    private void readArticle(String articleGenUuid, LoginUserInfo user) {
        List<Notify> notifyList = notifyRepository.findByArticleGenUuid(articleGenUuid);
        if (!CollectionUtils.isEmpty(notifyList)) {
            List<Notify> collect = notifyList.stream()
                    .filter(i -> i.getIsRead() == ArticleReadStatusEnum.DEFAULT.getType())
                    .peek(i -> i.setIsRead(ArticleReadStatusEnum.READ.getType()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                notifyRepository.saveAll(collect);
                OperatorLog log = new OperatorLog();
                log.setWebUserId(user.getWebUserId());
                log.setWebUsername(user.getWebUsername());
                log.setResult(OperatorLogResultEnum.SUCCESS.getType());
                log.setOperator(OperatorLogEnum.READ_ARTICLE.getType());
                operatorLogRepository.save(log);
            }
        }

    }

    /**
     * 查询子文章
     *
     * @param article   文章数
     * @param nowHeight 当前的深度
     * @param weight    想要的宽度
     * @param height    想要深度
     */
    public void findRelateArticle(ArticleTree article, Integer nowHeight, Integer weight, Integer height) {
        if (nowHeight >= height) {
            return;
        }
        nowHeight++;
        if (!StringUtils.isEmpty(article.getRelateArticleGenUuid())) {
            List<Article> articleList = articleRepository.findByRelateArticleGenUuid(article.getArticleGenUuid());
            if (!CollectionUtils.isEmpty(articleList)) {
                List<ArticleTree> relateList = articleList.stream().map(i -> {
                    ArticleTree tree = new ArticleTree();
                    BeanUtil.copyProperties(i, tree);
                    GroupMember firstByUserGenUuid = groupMemberRepository.findFirstByUserGenUuid(i.getUserGenUuid());
                    if (null != firstByUserGenUuid && !StringUtils.isEmpty(firstByUserGenUuid.getHeadPhoto())) {
                        firstByUserGenUuid.setHeadPhoto(StrUtil.SLASH + firstByUserGenUuid.getHeadPhoto());
                    }
                    if (!CollectionUtils.isEmpty(tree.getMediaList())) {
                        tree.setMediaList(tree.getMediaList().stream().peek(iter -> {
                            String file = iter.get("media_file");
                            if (null != file) {
                                iter.put("media_file", StrUtil.SLASH + file);
                            }
                        }).collect(Collectors.toList()));
                    }
                    tree.setGroupMember(firstByUserGenUuid);
                    return tree;
                }).limit(weight).collect(Collectors.toList());
                article.setSonList(relateList);
                if (!CollectionUtils.isEmpty(relateList)) {
                    Integer finalNowHeight = nowHeight;
                    long count = relateList.stream().map(i -> {
                        findRelateArticle(i, finalNowHeight, weight, height);
                        return null;
                    }).count();
//                    log.info("文章-{}-儿子的个数：{}", article.getArticleGenUuid(), count);
                }
            }
        }
    }
}
