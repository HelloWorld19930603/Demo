package com.yg.shop.server.repository;

import com.yg.shop.server.entity.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 群组表account
 *
 * @author ljh
 */
@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    /**
     * 查找我的群组
     *
     * @param webUserId 网页用户id
     * @return 数据
     */
    List<Group> findByWebUserIdList(String webUserId);

    /**
     * 根据用户id和昵称查询数据
     *
     * @param webUserId 网页登录用户
     * @param nickName  昵称
     * @return 数据
     */
    List<Group> findByWebUserIdListAndNicknameLike(@Param("webUserId") String webUserId, @Param("nickName") String nickName);

    /**
     * 根据url查找我的群组
     *
     * @param url 参数
     * @return 群组
     */
    @Query(value = "{url: ?0}")
    List<Group> findGroupFromUrl(String url);

    /**
     * 根据机器人的uuid来搜群组
     *
     * @param uuid uuid
     * @return 群
     */
    List<Group> findByBotUserGenUuid(String uuid);
}
