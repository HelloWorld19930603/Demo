package om.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 发送时间的参数
 *
 * @author ljh
 * @date 2022/12/12 14:34
 */
@Data
public class SendTimeVo {
    /**
     * id
     */
    @NotNull
    private String id;

    /**
     * 群id
     */
    @NotNull
    private String groupGenUuid;

    /**
     * 发帖时间
     */
    @NotNull
    private Long sendTime;

    /**
     * 类型:1. 修改定时贴的发布时间,2. 立即发布,时间为当前时间的时间戳,秒
     */
    @NotNull
    private Integer type;
}
