package com.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 用户-群关联表
 *
 * @author ljh
 * @date 2022/11/22 10:53
 */
@Data
@Document(collection = "group_member")
public class GroupMember {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;
    /**
     * 群唯一id
     */
    @Field(name = "group_gen_uuid", targetType = FieldType.STRING)
    private String groupGenUuid;
    /**
     * 用户唯一id
     */
    @Field(name = "user_gen_uuid", targetType = FieldType.STRING)
    private String userGenUuid;
    /**
     * 类型，1-管理员 2-版主 3-专家 4-普通成员
     */
    @Field(name = "member_type", targetType = FieldType.INT32)
    private Integer memberType;
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
     * 头像文件路径， string
     */
    @Field(name = "head_photo", targetType = FieldType.STRING)
    private String headPhoto;
    /**
     * 入群时间
     */
    @Field(name = "join_time", targetType = FieldType.INT64)
    private Long joinTime;
    /**
     * 由 Workout Partner & Live 邀请
     */
    @Field(name = "join_group_type", targetType = FieldType.STRING)
    private String joinGroupType;
    /**
     * 其他一些介绍的信息
     */
    @Field(name = "other_info", targetType = FieldType.STRING)
    private String otherInfo;
    /**
     * 插入时间
     */
    @Field(name = "insert_time", targetType = FieldType.INT64)
    private Long insertTime;
    /**
     * 修改时间
     */
    @Field(name = "update_time", targetType = FieldType.INT64)
    private Long updateTime;
}
