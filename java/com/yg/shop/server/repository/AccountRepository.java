package com.yg.shop.server.repository;

import com.yg.shop.server.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 成员表
 *
 * @author ljh
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    /**
     * 搜索账号
     *
     * @param uuid
     * @return
     */
    Account findFirstByUserGenUuid(String uuid);
}
