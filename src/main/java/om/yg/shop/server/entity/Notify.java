package om.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * 通知
 *
 * @author ljh
 * @date 2022/12/9 11:21
 */
@Data
@Document(collection = "notify")
public class Notify {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;

    /**
     * 群唯一标识
     */
    @Field(name = "group_gen_uuid", targetType = FieldType.STRING)
    private String groupGenUuid;

    /**
     * 对应操作的mongodb主键，做去重用
     */
    @Field(name = "operator_id", targetType = FieldType.STRING)
    private String operatorId;

    /**
     * 账号生成的唯一标示
     */
    @Field(name = "user_gen_uuid", targetType = FieldType.STRING)
    private String userGenUuid;

    /**
     * 账号昵称
     */
    @Field(name = "nickname", targetType = FieldType.STRING)
    private String nickname;

    /**
     * 文章作者昵称   后
     */
    @Field(name = "article_nickname", targetType = FieldType.STRING)
    private String articleNickname;

    /**
     * 头像 hbase文件路径
     */
    @Field(name = "head_photo", targetType = FieldType.STRING)
    private String headPhoto;

    /**
     * 1-发布帖子 2-点赞 3-评论
     */
    @Field(name = "operator_type", targetType = FieldType.INT32)
    private Integer operatorType;

    /**
     * 文章生成的唯一标示
     */
    @Field(name = "article_gen_uuid", targetType = FieldType.STRING)
    private String articleGenUuid;

    /**
     * 帖子内容
     */
    @Field(name = "content", targetType = FieldType.STRING)
    private String content;

    /**
     * 操作时间
     */
    @Field(name = "operator_time", targetType = FieldType.INT64)
    private Long operatorTime;

    /**
     * 是否已读 int, 0-未读 1-已读，默认为0
     */
    @Field(name = "is_read", targetType = FieldType.INT32)
    private Integer isRead;

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
