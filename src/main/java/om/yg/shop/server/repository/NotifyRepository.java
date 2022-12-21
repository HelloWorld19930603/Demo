package om.yg.shop.server.repository;

import com.yg.shop.server.entity.Notify;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 通知 repository
 *
 * @author ljh
 */
@Repository
public interface NotifyRepository extends MongoRepository<Notify, String> {

    /**
     * 根据群uuid搜索代办
     *
     * @param uuid 群uuid
     * @return 数据
     */
    List<Notify> findByGroupGenUuid(String uuid);

    /**
     * 根据文章的uuid搜索代办
     *
     * @param uuid 文章的uuid
     * @return 数据
     */
    List<Notify> findByArticleGenUuid(String uuid);
}
