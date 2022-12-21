package com.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 群组标签表
 *
 * @author ljh
 * @date 2022/11/22 10:53
 */
@Data
@Document(collection = "group_tag")
public class GroupTag {
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
     * 标签列表
     */
    @Field(name = "tag_list", targetType = FieldType.STRING)
    private List<String> tagList;
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
