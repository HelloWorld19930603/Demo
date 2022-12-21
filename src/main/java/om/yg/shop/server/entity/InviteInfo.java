package om.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


/**
 * 群组标签表
 *
 * @author ljh
 * @date 2022/11/22 10:53
 */
@Data
@Document(collection = "invite_info")
public class InviteInfo {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;
    /**
     * 生成的唯一标示
     */
    @Field(name = "group_gen_uuid", targetType = FieldType.STRING)
    private String groupGenUuid;
    /**
     * 网页用户id
     */
    @Field(name = "user_gen_uuid", targetType = FieldType.STRING)
    private String userGenUuid;
    /**
     * 用户id
     */
    @Field(name = "user_id", targetType = FieldType.STRING)
    private String userId;
    /**
     * 昵称
     */
    @Field(name = "nickname", targetType = FieldType.STRING)
    private String nickname;
    /**
     * 头像url
     */
    @Field(name = "head_photo_url", targetType = FieldType.STRING)
    private String headPhotoUrl;
    /**
     * 头像文件路径
     */
    @Field(name = "head_photo", targetType = FieldType.STRING)
    private String headPhoto;
    /**
     * 邀请人id
     */
    @Field(name = "invite_user_id", targetType = FieldType.STRING)
    private String inviteUserId;
    /**
     * 生成的邀请人id
     */
    @Field(name = "invite_user_gen_uuid", targetType = FieldType.STRING)
    private String inviteUserGenUuid;
    /**
     * 邀请人昵称
     */
    @Field(name = "invite_nickname", targetType = FieldType.STRING)
    private String inviteNickname;
    /**
     * 邀请人时间
     */
    @Field(name = "invite_time", targetType = FieldType.INT64)
    private Long inviteTime;
    /**
     * 接收时间
     */
    @Field(name = "accept_time", targetType = FieldType.INT64)
    private Long acceptTime;
    /**
     * 其他一些介绍的信息
     */
    @Field(name = "other_info", targetType = FieldType.STRING)
    private String otherInfo;
    /**
     * 发送提醒次数
     */
    @Field(name = "send_invite_count", targetType = FieldType.INT32)
    private Integer sendInviteCount;
    /**
     * 最后一次发送提醒时间
     */
    @Field(name = "send_invite_time", targetType = FieldType.INT64)
    private Long sendInviteTime;
    /**
     * 页面提醒时间
     */
    @Field(name = "web_remind_time", targetType = FieldType.INT64)
    private Long webRemindTime;
    /**
     * 1-未接收 2-接收, 默认为1
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
