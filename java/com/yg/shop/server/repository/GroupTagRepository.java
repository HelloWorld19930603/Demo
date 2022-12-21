package com.yg.shop.server.repository;

import com.yg.shop.server.entity.GroupTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 群组标签表
 *
 * @author ljh
 */
@Repository
public interface GroupTagRepository extends MongoRepository<GroupTag, String> {

    /**
     * 查询群的标签
     *
     * @param uuid 生成的群uuid
     * @return 群标签
     */
    @Query("{group_gen_uuid: ?0}")
    List<GroupTag> findGroupTag(String uuid);

    /**
     * 根据用户id和群id搜索群标签
     *
     * @param webUserId 用户id
     * @param uuid      群id
     * @return 数据
     */
    GroupTag findFirstByWebUserIdAndGroupGenUuid(@Param("webUserId") String webUserId, @Param("uuid") String uuid);

}
