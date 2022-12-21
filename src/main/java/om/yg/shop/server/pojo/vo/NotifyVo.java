package om.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 代办参数
 *
 * @author ljh
 * @date 2022/12/9 15:26
 */
@Data
public class NotifyVo {
    /**
     * 群唯一id
     */
    @NotNull
    private String groupGenUuid;

    /**
     * 当前页
     */
    @NotNull
    private Integer pageNum;

    /**
     * 每页显示的数量
     */
    @NotNull
    private Integer pageSize;
}
