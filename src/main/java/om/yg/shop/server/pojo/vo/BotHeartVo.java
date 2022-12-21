package om.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 机器人健康状态的参数
 *
 * @author ljh
 * @date 2022/11/30 9:21
 */
@Data
public class BotHeartVo {
    /**
     * 机器人id
     */
    private String botUserId;
    /**
     * 生成的机器人id
     */
    @NotNull
    private String botUserGenUuid;
    /**
     * 机器人昵称
     */
    private String botNickName;
    /**
     * 1-采集正常  2-操作被限制 3-机器人异常
     */
    private Integer botStatus;
}
