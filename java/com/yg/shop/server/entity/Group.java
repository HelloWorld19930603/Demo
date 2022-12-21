package com.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 群组表实体类
 *
 * @author ljh
 * @date 2022/11/22 10:53
 */
@Data
@Document(collection = "group")
public class Group {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;
    /**
     * 应用类型
     * app类型，int32, 2-facebook
     */
    @Field(name = "app_type", targetType = FieldType.INT32)
    private Integer appType;
    /**
     * 群id, string
     */
    @Field(name = "group_id", targetType = FieldType.STRING)
    private String groupId;
    /**
     * 群url，string
     */
    @Field(name = "url", targetType = FieldType.STRING)
    private String url;
    /**
     * 群简介, string
     */
    @Field(name = "desc", targetType = FieldType.STRING)
    private String desc;
    /**
     * 群简介, string, 中文翻译
     */
    @Field(name = "desc_cn", targetType = FieldType.STRING)
    private String descCn;
    /**
     * 群昵称， string
     */
    @Field(name = "nickname", targetType = FieldType.STRING)
    private String nickname;
    /**
     * 成员数
     */
    @Field(name = "member_count", targetType = FieldType.INT32)
    private Integer memberCount;
    /**
     * 管理员数
     */
    @Field(name = "admin_count", targetType = FieldType.INT32)
    private Integer adminCount;
    /**
     * 创建时间戳(秒)
     */
    @Field(name = "create_time", targetType = FieldType.INT32)
    private Integer createTime;
    /**
     * 群头像url
     */
    @Field(name = "head_photo_url", targetType = FieldType.STRING)
    private String headPhotoUrl;
    /**
     * 群头像文件路径
     */
    @Field(name = "head_photo", targetType = FieldType.STRING)
    private String headPhoto;
    /**
     * 所在位置
     */
    @Field(name = "location", targetType = FieldType.STRING)
    private String location;
    /**
     * 是否私有
     */
    @Field(name = "is_private", targetType = FieldType.BOOLEAN)
    private Boolean isPrivate;
    /**
     * 账号赞他人总数
     */
    @Field(name = "tag_list", targetType = FieldType.STRING)
    private List<String> tagList;
    /**
     * 群规则
     */
    @Field(name = "rule_info", targetType = FieldType.STRING)
    private String ruleInfo;
    /**
     * 群规则，string, 中文翻译
     */
    @Field(name = "rule_info_cn", targetType = FieldType.STRING)
    private String ruleInfoCn;
    /**
     * 生成的唯一标示
     */
    @Field(name = "group_gen_uuid", targetType = FieldType.STRING)
    private String groupGenUuid;
    /**
     * 采集时间戳(秒)
     */
    @Field(name = "collect_time", targetType = FieldType.STRING)
    private Long collectTime;
    /**
     * 更新时间戳(秒)
     */
    @Field(name = "update_time", targetType = FieldType.INT64)
    private Long updateTime;
    /**
     * 网页用户id, 群可以属于多个用户
     */
    @Field(name = "web_user_id_list", targetType = FieldType.STRING)
    private List<String> webUserIdList;
    /**
     * 机器人id
     */
    @Field(name = "bot_user_id", targetType = FieldType.STRING)
    private String botUserId;
    /**
     * 机器人唯一id
     */
    @Field(name = "bot_user_gen_uuid", targetType = FieldType.STRING)
    private String botUserGenUuid;
    /**
     * 机器人昵称
     */
    @Field(name = "bot_nickname", targetType = FieldType.STRING)
    private String botNickname;
    /**
     * 机器人头像文件路径
     */
    @Field(name = "bot_head_photo", targetType = FieldType.STRING)
    private String botHeadPhoto;
    /**
     * 活跃度
     */
    @Field(name = "activity_degree", targetType = FieldType.STRING)
    private String activityDegree;
    /**
     * int, 0-无机器人 1-采集正常  2-操作被限制 3-机器人异常  默认为0
     */
    @Field(name = "bot_status", targetType = FieldType.INT32)
    private Integer botStatus;
    /**
     * 机器人自动点赞 int， 0-不点赞 1-点赞  默认为0
     */
    @Field(name = "auto_like", targetType = FieldType.INT32)
    private Integer autoLike;
    /**
     * 机器人自动回复 int， 0-不回复 1-回复  默认为0
     */
    @Field(name = "auto_reply", targetType = FieldType.INT32)
    private Integer autoReply;
    /**
     * 插入时间
     */
    @Field(name = "insert_time", targetType = FieldType.INT64)
    private Long insertTime;

}
