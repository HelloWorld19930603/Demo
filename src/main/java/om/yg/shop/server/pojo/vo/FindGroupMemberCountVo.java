package om.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 搜索群成员总数的参数
 *
 * @author ljh
 * @date 2022/11/23 10:41
 */
@Data
public class FindGroupMemberCountVo {
    /**
     * 群唯一id 生成的
     */
    @NotNull
    private String groupGenUuid;

}
