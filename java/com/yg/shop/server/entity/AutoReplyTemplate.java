package com.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 自动评论 自动回复
 *
 * @author ljh
 * @date 2022/12/9 11:21
 */
@Data
@Document(collection = "auto_reply_template")
public class AutoReplyTemplate {
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
     * 关键词列表
     */
    @Field(name = "keyword_list")
    private List<String> keywordList;

    /**
     * 评论内容
     */
    @Field(name ="content",targetType = FieldType.STRING)
    private String content;

    /**
     * 图片路径，hbase路径
     */
    @Field(name ="media_file",targetType = FieldType.STRING)
    private String mediaFile;

    /**
     * 是否设置为默认评论, 0-不是  1-是，默认为0
     */
    @Field(name ="is_default",targetType = FieldType.INT32)
    private Integer isDefault;

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
