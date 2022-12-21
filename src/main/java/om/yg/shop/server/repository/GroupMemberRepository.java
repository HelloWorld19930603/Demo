package om.yg.shop.server.repository;

import com.yg.shop.server.entity.GroupMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 群成员集合
 *
 * @author ljh
 */
@Repository
public interface GroupMemberRepository extends MongoRepository<GroupMember, String> {

    /**
     * 根据类型搜索群成员
     *
     * @param groupGenUuid 群唯一id
     * @param memberType   成员类型
     * @return 成员列表
     */
    List<GroupMember> findByGroupGenUuidAndMemberType
    (@Param("groupGenUuid") String groupGenUuid, @Param("memberType") Integer memberType);

    /**
     * 根据昵称搜索群成立
     *
     * @param groupGenUuid 群的唯一标识
     * @param name         账号昵称
     * @return 成员列表
     */
    List<GroupMember> findByGroupGenUuidAndNicknameLike
    (@Param("groupGenUuid") String groupGenUuid, @Param("name") String name);

    /**
     * 根据群的唯一id搜成员
     *
     * @param uuid uuid
     * @return 列表
     */
    @Query("{group_gen_uuid: ?0}")
    List<GroupMember> findGroupMemberList(String uuid);

    /**
     * 根据账号uuid搜信息
     *
     * @param uuid 账号uuid
     * @return 群成员
     */
    GroupMember findFirstByUserGenUuid(String uuid);
}
