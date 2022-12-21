package om.yg.shop.server.pojo.dto;

import lombok.Data;

/**
 * 入群邀请的统计
 *
 * @author ljh
 * @date 2022/11/22 11:47
 */
@Data
public class InviteInfoCountDto {
    /**
     * 已邀请
     */
    private Integer inviteCount;
    /**
     * 未接收
     */
    private Integer notInviteCount;
}
