package om.yg.shop.server.service;


import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.ArticleTree;
import com.yg.shop.server.pojo.vo.ArticleTreeVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;
import com.yg.shop.server.pojo.vo.SendArticleVo;

/**
 * 文章
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface ArticleService {

    /**
     * 文章树
     *
     * @param vo 群参数
     * @return 文章树
     */
    ResultPage<ArticleTree> articleTree(ArticleTreeVo vo);

    /**
     * 发布文章
     *
     * @param vo   前端参数
     * @param user 用户信息
     * @return boolean
     */
    Boolean sendArticle(SendArticleVo vo, LoginUserInfo user);

    /**
     * 获取文章信息
     *
     * @param articleGenUuid 文章的唯一uuid
     * @param user 用户信息
     * @return 文章信息
     */
    ArticleTree info(String articleGenUuid,LoginUserInfo user);
}
