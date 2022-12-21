package om.yg.shop.server.pojo.vo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 修改群成员标签搜索参数
 *
 * @author ljh
 * @date 2022/11/22 17:01
 */
@Data
public class UpdateGroupMemberTagVo {
    /**
     * 网页用户id
     */
    @NotNull
    private String webUserId;
    /**
     * 网页用户名称,做日志用
     */
    @NotNull
    private String webUserName;
    /**
     * 搜索的成员唯一标示
     */
    @NotNull
    private String userGenUuid;
    /**
     * 群生成的唯一标示
     */
    @NotNull
    private String groupGenUuid;
    /**
     * 标签列表
     */
    private Set<String> tagList;
}
