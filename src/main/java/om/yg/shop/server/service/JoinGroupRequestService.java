package om.yg.shop.server.service;


import com.yg.shop.server.pojo.dto.JoinGroupRequestResultDto;
import com.yg.shop.server.pojo.vo.JoinGroupRequestListVo;

/**
 * 入群申请接口
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface JoinGroupRequestService {

    /**
     * 获取入群申请列表
     *
     * @param vo 参数
     * @return 列表
     */
    JoinGroupRequestResultDto findJoinGroupRequestList(JoinGroupRequestListVo vo);
}
