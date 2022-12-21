package om.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 操作信息
 *
 * @author ljh
 * @date 2022/11/22 10:53
 */
@Data
@Document(collection = "operator_info")
public class OperatorInfo {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;
    /**
     * 网页用户id
     */
    @Field(name = "web_user_id", targetType = FieldType.STRING)
    private String webUserId;
    /**
     * 生成的唯一标示
     */
    @Field(name = "group_gen_uuid", targetType = FieldType.STRING)
    private String groupGenUuid;
    /**
     * 操作 1- 入群同意  2- 入群拒绝 3- 批量邀请 4-全部邀请 note：全部邀请时，user_id_list为空
     */
    @Field(name = "operator", targetType = FieldType.INT32)
    private Integer operator;
    /**
     * 群id
     */
    @Field(name = "group_id", targetType = FieldType.STRING)
    private String groupId;
    /**
     * userIds
     */
    @Field(name = "user_id_list", targetType = FieldType.STRING)
    private List<String> userIdList;
    /**
     * 1- 未开始 2-进行中 3-操作成功  4、操作失败
     */
    @Field(name = "status", targetType = FieldType.INT32)
    private Integer status;
    /**
     * 更新时间
     */
    @Field(name = "update_time", targetType = FieldType.INT64)
    private Long updateTime;
    /**
     * 插入时间
     */
    @Field(name = "insert_time", targetType = FieldType.INT64)
    private Long insertTime;
}
