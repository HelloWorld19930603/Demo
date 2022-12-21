package om.yg.shop.server.pojo.vo;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 邀请管理的搜索条件
 *
 * @author ljh
 * @date 2022/11/22 11:47
 */
@Data
public class InviteInfoListVo {
    /**
     * 群生成的唯一标示
     */
    @NotNull
    private String groupGenUuid;
    /**
     * 网页用户id
     */
    @NotNull
    private String webUserId;
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页显示的数量
     */
    private Integer pageSize;
    /**
     * 邀请开始时间
     */
    private Long inviteStartTime;
    /**
     * 邀请结束时间
     */
    private Long inviteEndTime;
    /**
     * 提醒开始时间
     */
    private Long remindStartTime;
    /**
     * 提醒结束时间
     */
    private Long remindEndTime;
    /**
     * 搜索文本
     */
    private String text;
    /**
     * 搜索状态 1-未接收 2-接收, 默认为1
     */
    private Integer status;
}
