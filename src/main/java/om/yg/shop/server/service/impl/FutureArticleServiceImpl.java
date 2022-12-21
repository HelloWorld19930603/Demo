package om.yg.shop.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yg.shop.server.constant.*;
import com.yg.shop.server.entity.Article;
import com.yg.shop.server.entity.FutureArticle;
import com.yg.shop.server.entity.FutureArticleOperator;
import com.yg.shop.server.entity.OperatorLog;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.FutureArticleDto;
import com.yg.shop.server.pojo.vo.*;
import com.yg.shop.server.repository.ArticleRepository;
import com.yg.shop.server.repository.FutureArticleOperatorRepository;
import com.yg.shop.server.repository.FutureArticleRepository;
import com.yg.shop.server.repository.OperatorLogRepository;
import com.yg.shop.server.service.FileService;
import com.yg.shop.server.service.FutureArticleService;
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
public class FutureArticleServiceImpl implements FutureArticleService {

    private final FutureArticleRepository futureArticleRepository;
    private final OperatorLogRepository operatorLogRepository;
    private final FileService fileService;
    private final FutureArticleOperatorRepository futureArticleOperatorRepository;
    private final ArticleRepository articleRepository;


    @Override
    public Boolean createFutureArticle(AddFutureArticleVo vo, LoginUserInfo user) {
        try {
            FutureArticle futureArticle = new FutureArticle();
            BeanUtil.copyProperties(vo, futureArticle);
            futureArticle.setInsertTime(System.currentTimeMillis() / 1000);
            futureArticle.setUpdateTime(System.currentTimeMillis() / 1000);
            List<Map<String, String>> mapList = new ArrayList<>(10);
            if (!CollectionUtils.isEmpty(vo.getMediaFileList())) {
                List<String> list = vo.getMediaFileList().stream().map(i -> {
                    String fileName = SystemConstant.shop + StrUtil.SLASH
                            + vo.getGroupGenUuid() + StrUtil.SLASH + i.getOriginalFilename();
                    String upload = fileService.upload(SystemConstant.uploadUrl, fileName, i);
                    Map<String, String> map = new HashMap<>(16);
                    map.put("media_type", "1");
                    map.put("media_file", upload);
                    mapList.add(map);
                    return upload;
                }).collect(Collectors.toList());
                futureArticle.setMediaFileList(list);
            }
            FutureArticle save = futureArticleRepository.save(futureArticle);
            OperatorLog log = new OperatorLog();
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            log.setWebUsername(user.getWebUsername());
            log.setWebUserId(user.getWebUserId());
            log.setOperator(OperatorLogEnum.CREATE_FUTURE_ARTICLE.getType());
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            log.setData(map);
            operatorLogRepository.save(log);

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

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResultPage<FutureArticleDto> searchFutureArticle(String groupGenUuid, Integer pageNum, Integer pageSize) {
        ResultPage<FutureArticleDto> result = new ResultPage<>();
        List<FutureArticle> futureArticleList = futureArticleRepository.findByGroupGenUuid(groupGenUuid);
        if (!CollectionUtils.isEmpty(futureArticleList)) {
            futureArticleList = futureArticleList.stream()
                    .filter(i -> i.getSendTime() > System.currentTimeMillis() / 1000)
                    .collect(Collectors.toList());
            result.setCount((long) futureArticleList.size());
            List<FutureArticleDto> collect = futureArticleList.stream().map(i -> {
                FutureArticleDto dto = new FutureArticleDto();
                BeanUtil.copyProperties(i, dto);
                if (!CollectionUtils.isEmpty(dto.getMediaFileList())) {
                    List<String> mediaList = dto.getMediaFileList().stream()
                            .map(iter -> StrUtil.SLASH + iter).collect(Collectors.toList());
                    dto.setMediaFileList(mediaList);
                }
                return dto;
            }).sorted(Comparator.comparing(FutureArticleDto::getSendTime))
                    .skip((pageNum - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
            result.setList(collect);
        }
        return result;
    }

    @Override
    public Boolean updateFutureArticle(UpdateFutureArticleVo vo, LoginUserInfo user) {
        try {
            FutureArticle futureArticle = futureArticleRepository.findById(vo.getId()).get();
            futureArticle.setContent(vo.getContent());
            futureArticle.setUpdateTime(System.currentTimeMillis() / 1000);
            List<String> imgList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(vo.getMediaFileList())) {
                List<String> list = vo.getMediaFileList().stream().map(i -> {
                    String fileName = SystemConstant.shop + StrUtil.SLASH
                            + vo.getGroupGenUuid() + StrUtil.SLASH + i.getOriginalFilename();
                    return fileService.upload(SystemConstant.uploadUrl, fileName, i);
                }).collect(Collectors.toList());
                imgList.addAll(list);
            }
            if (!CollectionUtils.isEmpty(vo.getSaveImgList())) {
                List<String> collect = vo.getSaveImgList().stream()
                        .map(i -> i.replaceFirst(StrUtil.SLASH, StrUtil.EMPTY))
                        .collect(Collectors.toList());
                imgList.addAll(collect);
            }
            futureArticle.setMediaFileList(imgList);
            futureArticleRepository.save(futureArticle);

            FutureArticleOperator futureArticleOperator = new FutureArticleOperator();
            BeanUtil.copyProperties(futureArticle, futureArticleOperator);
            futureArticleOperator.setId(null);
            futureArticleOperator.setGroupGenUuid(vo.getGroupGenUuid());
            futureArticleOperator.setFutureArticleId(futureArticle.getId());
            futureArticleOperator.setIsDelete(DelStatusEnum.SAVE.getType());
            futureArticleOperator.setIsSendNow(SendStatusEnum.DEFAULT.getType());
            futureArticleOperatorRepository.save(futureArticleOperator);

            Article article = articleRepository.findFirstByFutureArticleId(vo.getId());
            article.setContent(vo.getContent());
            if (!StringUtils.isEmpty(imgList)) {
                List<Map<String, String>> mapList = imgList.stream().map(i -> {
                    Map<String, String> map = new HashMap<>(16);
                    map.put("media_type", "1");
                    map.put("media_file", i);
                    return map;
                }).collect(Collectors.toList());
                article.setMediaList(mapList);
            }
            articleRepository.save(article);

            OperatorLog log = new OperatorLog();
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            log.setWebUsername(user.getWebUsername());
            log.setWebUserId(user.getWebUserId());
            log.setOperator(OperatorLogEnum.UPDATE_FUTURE_ARTICLE.getType());
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            log.setData(map);
            operatorLogRepository.save(log);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateSendTime(SendTimeVo vo, LoginUserInfo user) {
        try {
            FutureArticle futureArticle = futureArticleRepository.findById(vo.getId()).get();
            futureArticle.setSendTime(vo.getSendTime());
            futureArticleRepository.save(futureArticle);

            int operator;
            int isSendNow;
            if (vo.getType() == 1) {
                operator = OperatorLogEnum.UPDATE_SEND_TIME.getType();
                isSendNow = SendStatusEnum.DEFAULT.getType();
            } else {
                operator = OperatorLogEnum.NOT_SEND_TIME.getType();
                isSendNow = SendStatusEnum.SEND.getType();
            }

            FutureArticleOperator futureArticleOperator = new FutureArticleOperator();
            BeanUtil.copyProperties(futureArticle, futureArticleOperator);
            futureArticleOperator.setId(null);
            futureArticleOperator.setGroupGenUuid(vo.getGroupGenUuid());
            futureArticleOperator.setFutureArticleId(futureArticle.getId());
            futureArticleOperator.setIsDelete(DelStatusEnum.SAVE.getType());
            futureArticleOperator.setIsSendNow(isSendNow);
            futureArticleOperatorRepository.save(futureArticleOperator);

            OperatorLog log = new OperatorLog();
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            log.setWebUsername(user.getWebUsername());
            log.setWebUserId(user.getWebUserId());
            log.setOperator(operator);
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            log.setData(map);
            operatorLogRepository.save(log);

            Article article = articleRepository.findFirstByFutureArticleId(vo.getId());
            article.setCreateTime(vo.getSendTime());
            articleRepository.save(article);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean del(IdVo vo, LoginUserInfo user) {
        try {
            FutureArticle futureArticle = futureArticleRepository.findById(vo.getId()).get();
            FutureArticleOperator futureArticleOperator = new FutureArticleOperator();
            BeanUtil.copyProperties(futureArticle, futureArticleOperator);
            futureArticleOperator.setId(null);
            futureArticleOperator.setGroupGenUuid(vo.getGroupGenUuid());
            futureArticleOperator.setFutureArticleId(futureArticle.getId());
            futureArticleOperator.setIsDelete(DelStatusEnum.DELETE.getType());
            futureArticleOperator.setIsSendNow(SendStatusEnum.DEFAULT.getType());
            futureArticleOperatorRepository.save(futureArticleOperator);

            OperatorLog log = new OperatorLog();
            log.setInsertTime(System.currentTimeMillis() / 1000);
            log.setUpdateTime(System.currentTimeMillis() / 1000);
            log.setWebUsername(user.getWebUsername());
            log.setWebUserId(user.getWebUserId());
            log.setOperator(OperatorLogEnum.DELETE_FUTURE_ARTICLE.getType());
            log.setResult(OperatorLogResultEnum.SUCCESS.getType());
            Map<String, String> map = new HashMap<>(16);
            map.put("group_gen_uuid", vo.getGroupGenUuid());
            map.put("info", JSON.toJSONString(futureArticle));
            log.setData(map);
            operatorLogRepository.save(log);
            futureArticleRepository.deleteById(vo.getId());

            Article article = articleRepository.findFirstByFutureArticleId(vo.getId());
            articleRepository.delete(article);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
