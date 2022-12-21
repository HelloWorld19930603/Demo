package com.yg.shop.server.repository;

import com.yg.shop.server.entity.FutureArticleOperator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * 定时贴操作,未来文章 repository
 *
 * @author ljh
 */
@Repository
public interface FutureArticleOperatorRepository extends MongoRepository<FutureArticleOperator, String> {

}
