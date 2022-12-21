package om.yg.shop.server.repository;

import com.yg.shop.server.entity.ArticleFollow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 文章点赞 repository
 *
 * @author ljh
 */
@Repository
public interface ArticleFollowRepository extends MongoRepository<ArticleFollow, String> {

    /**
     * 搜索文章点赞信息
     *
     * @param uuid 文章的uuid
     * @return 点赞信息
     */
    List<ArticleFollow> findByArticleGenUuid(String uuid);
}
