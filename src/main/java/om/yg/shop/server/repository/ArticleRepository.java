package om.yg.shop.server.repository;

import com.yg.shop.server.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 文章 repository
 *
 * @author ljh
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    /**
     * 根据群id查文章
     *
     * @param uuid 群uuid
     * @return 文章列表
     */
    List<Article> findByGroupGenUuid(String uuid);

    /**
     * 根据文章uuid查找文章
     *
     * @param uuid 文章的uuid
     * @return 文章列表
     */
    List<Article> findByArticleGenUuid(String uuid);

    /**
     * 根据文章的关联id查找文章
     *
     * @param relateId 关联id
     * @return 文章
     */
    List<Article> findByRelateArticleGenUuid(String relateId);

    /**
     * 根据群id和内容查询文章
     *
     * @param uuid 群uuid
     * @param text 内容文本
     * @return 文章列表
     */
    List<Article> findByGroupGenUuidAndContentIsLike(String uuid, String text);

    /**
     * 根据定时贴的文章id来查文章
     *
     * @param id 定时贴文章id
     * @return 文章
     */
    Article findFirstByFutureArticleId(String id);

}
