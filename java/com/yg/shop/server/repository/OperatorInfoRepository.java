package com.yg.shop.server.repository;

import com.yg.shop.server.entity.OperatorInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * 群组表account
 *
 * @author ljh
 */
@Repository
public interface OperatorInfoRepository extends MongoRepository<OperatorInfo, String> {

}
