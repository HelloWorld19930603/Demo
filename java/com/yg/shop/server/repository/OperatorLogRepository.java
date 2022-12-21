package com.yg.shop.server.repository;

import com.yg.shop.server.entity.OperatorLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * 操作日志
 *
 * @author ljh
 */
@Repository
public interface OperatorLogRepository extends MongoRepository<OperatorLog, String> {

}
