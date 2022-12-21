package om.yg.shop.server.pojo.vo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加群成员标签
 * @author ljh
 * @date 2022/11/23 10:23
 */
@Data
public class AddGroupMemberTag {
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
     * 生成的唯一标示
     */
    @NotNull
    private String userGenUuid;
    /**
     * 标签列表
     */
    @NotNull
    private List<String> tagList;
    /**
     * 群id列表
     */
    @NotNull
    private String groupGenUuidList;
}
