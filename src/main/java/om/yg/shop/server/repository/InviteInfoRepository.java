package om.yg.shop.server.repository;

import com.yg.shop.server.entity.InviteInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


/**
 * 群组表account
 *
 * @author ljh
 */
@Repository
public interface InviteInfoRepository extends MongoRepository<InviteInfo, String> {

    /**
     * 根据群uuid获取邀请列表
     *
     * @param groupGenUuid 群uuid
     * @return 列表
     */
    @Query("{group_gen_uuid: ?0}")
    List<InviteInfo> findInviteInfoList(String groupGenUuid);

    /**
     * 获取群的人数统计
     *
     * @param uuid   群的uuid
     * @param status 状态
     * @return 数据
     */
    List<InviteInfo> findByGroupGenUuidAndStatus(String uuid, Integer status);

    /**
     * 根据账号的uuid来搜记录
     *
     * @param ids ids
     * @return 列表
     */
    List<InviteInfo> findAllByUserGenUuidIn(Collection<String> ids);
}
