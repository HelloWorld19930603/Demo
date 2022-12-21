package om.yg.shop.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yg.shop.server.entity.ArticleFollow;
import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.ArticleFollowDto;
import com.yg.shop.server.repository.ArticleFollowRepository;
import com.yg.shop.server.service.ArticleFollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章点赞
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleFollowServiceImpl implements ArticleFollowService {

    private final ArticleFollowRepository articleFollowRepository;

    @Override
    public ResultPage<ArticleFollowDto> getArticleFollow(String articleGenUuid, Integer pageNum, Integer pageSize) {
        ResultPage<ArticleFollowDto> page = new ResultPage<>();
        List<ArticleFollow> list = articleFollowRepository.findByArticleGenUuid(articleGenUuid);
        if (!CollectionUtils.isEmpty(list)) {
            page.setCount((long) list.size());
            List<ArticleFollowDto> collect = list.stream().map(i -> {
                ArticleFollowDto dto = new ArticleFollowDto();
                BeanUtil.copyProperties(i, dto);
                if (!StringUtils.isEmpty(i.getHeadPhoto())) {
                    dto.setHeadPhoto(StrUtil.SLASH + dto.getHeadPhoto());
                }
                return dto;
            }).skip((pageNum - 1) * pageSize)
                    .limit(pageSize)
                    .sorted(Comparator.comparing(ArticleFollowDto::getInsertTime))
                    .collect(Collectors.toList());
            page.setList(collect);
        }
        return page;
    }
}
