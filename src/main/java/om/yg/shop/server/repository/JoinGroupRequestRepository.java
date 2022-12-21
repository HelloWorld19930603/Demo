package om.yg.shop.server.repository;

import com.yg.shop.server.entity.JoinGroupRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


/**
 * 入群申请
 *
 * @author ljh
 */
@Repository
public interface JoinGroupRequestRepository extends MongoRepository<JoinGroupRequest, String> {

    /**
     * 查询入群请求的成员
     *
     * @param uuid 群uuid
     * @return 成员列表
     */
    @Query("{group_gen_uuid: ?0}")
    List<JoinGroupRequest> findJoinGroup(String uuid);

    /**
     * 查询入群请求的成员
     *
     * @param uuid   群uuid
     * @param status 状态
     * @return 成员列表
     */
    List<JoinGroupRequest> findByGroupGenUuidAndStatus(String uuid, Integer status);


    /**
     * 根据账号的uuid来查信息
     *
     * @param ids ids
     * @return
     */
    List<JoinGroupRequest> findAllByUserGenUuidIn(Collection<String> ids);


}
