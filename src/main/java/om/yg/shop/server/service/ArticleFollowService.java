package om.yg.shop.server.service;


import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.ArticleFollowDto;

/**
 * 文章点赞
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface ArticleFollowService {

    /**
     * 获取文章的点赞信息
     *
     * @param articleGenUuid 文章生成的唯一标识
     * @param pageNum        当前页
     * @param pageSize       每页显示的数量
     * @return 分页信息
     */
    ResultPage<ArticleFollowDto> getArticleFollow(String articleGenUuid, Integer pageNum, Integer pageSize);
}
