package om.yg.shop.server.pojo.vo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 我的群组标签
 *
 * @author ljh
 * @date 2022/11/22 17:01
 */
@Data
public class MyGroupTagVo {
    /**
     * 网页用户id
     */
    @NotNull
    private String webUserId;
    /**
     * 群生成的唯一标示
     */
    @NotNull
    private String groupGenUuid;
}
