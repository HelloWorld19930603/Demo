package com.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * (Account)表实体类
 * @author ljh
 */
@Data
@Document(collection = "account")
public class Account {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;
    /**
     * app类型，int32, 1-twitter 2-facebook 3-youtube
     */
    @Field(name = "app_type", targetType = FieldType.INT32)
    private Integer appType;
    /**
     * fb用户id
     */
    @Field(name = "user_id", targetType = FieldType.STRING)
    private String userId;
    /**
     * 用户名
     */
    @Field(name = "username", targetType = FieldType.STRING)
    private String username;
    /**
     * 昵称
     */
    @Field(name = "nickname", targetType = FieldType.STRING)
    private String nickname;
    /**
     * 用户网络地址，string
     */
    @Field(name = "url", targetType = FieldType.STRING)
    private String url;
    /**
     * 用户简介
     */
    @Field(name = "desc", targetType = FieldType.STRING)
    private String desc;
    /**
     * 用户简介, string, 中文翻译
     */
    @Field(name = "desc_cn", targetType = FieldType.STRING)
    private String descCn;
    /**
     * 邮箱
     */
    @Field(name = "email", targetType = FieldType.STRING)
    private String email;
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
     * 发文总量
     */
    @Field(name = "article_count", targetType = FieldType.INT32)
    private Integer articleCount;
    /**
     * 粉丝总量
     */
    @Field(name = "fan_count", targetType = FieldType.INT32)
    private Integer fanCount;
    /**
     * 粉他人总量
     */
    @Field(name = "fan_other_count", targetType = FieldType.INT32)
    private Integer fanOtherCount;
    /**
     * 账号被赞总数
     */
    @Field(name = "liked_count", targetType = FieldType.INT32)
    private Integer likedCount;
    /**
     * 账号赞他人总数
     */
    @Field(name = "like_count", targetType = FieldType.INT32)
    private Integer likeCount;
    /**
     * 所在位置
     */
    @Field(name = "location", targetType = FieldType.STRING)
    private String location;
    /**
     * 加入fb时间戳
     */
    @Field(name = "join_time", targetType = FieldType.INT32)
    private Integer joinTime;
    /**
     * 是否验证
     */
    @Field(name = "is_verify", targetType = FieldType.BOOLEAN)
    private Boolean isVerify;
    /**
     * 是否保护
     */
    @Field(name = "is_protect", targetType = FieldType.BOOLEAN)
    private Boolean isProtect;
    /**
     * 生成的唯一标示
     */
    @Field(name = "user_gen_uuid", targetType = FieldType.STRING)
    private String userGenUuid;
    /**
     * 采集时间戳
     */
    @Field(name = "collect_time", targetType = FieldType.INT32)
    private Integer collectTime;
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