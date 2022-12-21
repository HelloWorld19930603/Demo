package om.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Map;

/**
 * 入群申请集合
 *
 * @author ljh
 * @date 2022/11/22 10:53
 */
@Data
@Document(collection = "join_group_request")
public class JoinGroupRequest {
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
     * 生成的用户id(账号)
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
     * 加入fb时间
     */
    @Field(name = "join_time", targetType = FieldType.INT64)
    private Long joinTime;
    /**
     * 申请时间
     */
    @Field(name = "request_time", targetType = FieldType.INT64)
    private Long requestTime;
    /**
     * 接受或拒绝时间
     */
    @Field(name = "operator_time", targetType = FieldType.INT64)
    private Long operatorTime;
    /**
     * 请求问题及答案
     */
    @Field(name = "question_list", targetType = FieldType.STRING)
    private List<Map<String, String>> questionList;
    /**
     * 好友数
     */
    @Field(name = "friend_count", targetType = FieldType.INT32)
    private Integer friendCount;
    /**
     * 群内好友数
     */
    @Field(name = "group_friend_count", targetType = FieldType.INT32)
    private Integer groupFriendCount;
    /**
     * 加群数
     */
    @Field(name = "group_count", targetType = FieldType.INT32)
    private Integer groupCount;
    /**
     * 群昵称之类
     */
    @Field(name = "open_groups_list")
    private List<GroupInfo> openGroupsList;
    /**
     * 邀请人id，谁邀请改用户入群, 如果是自己加入，邀请人是自己
     */
    @Field(name = "invite_user_id", targetType = FieldType.STRING)
    private String inviteUserId;
    /**
     * 邀请人id，谁邀请改用户入群, 如果是自己加入，邀请人是自己
     */
    @Field(name = "invite_user_gen_uuid", targetType = FieldType.STRING)
    private String inviteUserGenUuid;
    /**
     * 邀请人昵称
     */
    @Field(name = "invite_nickname", targetType = FieldType.STRING)
    private String inviteNickname;
    /**
     * 所在位置
     */
    @Field(name = "location", targetType = FieldType.STRING)
    private String location;
    /**
     * 教育
     */
    @Field(name = "education", targetType = FieldType.STRING)
    private String education;
    /**
     * 工作
     */
    @Field(name = "work", targetType = FieldType.STRING)
    private String work;
    /**
     * 0-待操作 1-接受 2-拒绝 3-接受处理 4-拒绝处理
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
