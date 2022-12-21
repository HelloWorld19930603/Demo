package com.yg.shop.server.repository;

import com.yg.shop.server.entity.GroupMemberTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 群成员标签
 *
 * @author ljh
 */
@Repository
public interface GroupMemberTagRepository extends MongoRepository<GroupMemberTag, String> {

    /**
     * 查找成员标签
     *
     * @param webUserId 网页用户id
     * @param accountId 账号id
     * @return 账号标签
     */
    GroupMemberTag findFirstByWebUserIdAndUserGenUuid
    (@Param("webUserId") String webUserId, @Param("accountId") String accountId);

}
