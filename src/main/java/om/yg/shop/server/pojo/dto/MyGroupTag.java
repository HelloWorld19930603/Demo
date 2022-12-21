package om.yg.shop.server.pojo.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 我的群标签
 *
 * @author ljh
 * @date 2022/11/23 10:28
 */
@Data
public class MyGroupTag {
    /**
     * 网页用户id
     */
    private String webUserId;
    /**
     * 生成的唯一标示
     */
    private String groupGenUuid;
    /**
     * 标签列表
     */
    private List<String> tagList;
    /**
     * 插入时间
     */
    private Integer insertTime;
    /**
     * 修改时间
     */
    private Integer updateTime;
}
