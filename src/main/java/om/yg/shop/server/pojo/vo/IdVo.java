package om.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 仅仅是id
 *
 * @author ljh
 * @date 2022/12/9 16:02
 */
@Data
public class IdVo {
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
}
