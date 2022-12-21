package com.yg.shop.server.repository;

import com.yg.shop.server.entity.FutureArticle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 定时贴,未来文章 repository
 *
 * @author ljh
 */
@Repository
public interface FutureArticleRepository extends MongoRepository<FutureArticle, String> {

    /**
     * 搜索发帖
     *
     * @param groupUuid 群唯一id
     * @return 数据列表
     */
    List<FutureArticle> findByGroupGenUuid(String groupUuid);
}
