package om.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * 文章点赞信息
 *
 * @author ljh
 * @date 2022/12/12 19:24
 */
@Data
@Document(collection = "article_follow")
public class ArticleFollow {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;

    /**
     * 文章UUID
     */
    @Field(name ="article_gen_uuid",targetType = FieldType.STRING)
    private String articleGenUuid;

    /**
     * 点赞人的唯一标识
     */
    @Field(name ="user_gen_uuid",targetType = FieldType.STRING)
    private String userGenUuid;
    
    /**
     * 点赞人账号昵称
     */
    @Field(name ="nickname",targetType = FieldType.STRING)
    private String nickname;

    /**
     * 点赞人头像
     */
    @Field(name ="head_photo",targetType = FieldType.STRING)
    private String headPhoto;

    /**
     * 关注类型, int32, 1-点赞 2-点踩
     */
    @Field(name ="follow_type",targetType = FieldType.INT32)
    private Integer followType;

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
