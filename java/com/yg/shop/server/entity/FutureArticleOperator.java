package com.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

/**
 * 定时贴操作
 *
 * @author ljh
 * @date 2022/12/12 14:13
 */
@Data
@Document(collection = "future_article_operator")
public class FutureArticleOperator {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;

    /**
     * 定时贴id
     */
    @Field(name = "future_article_id", targetType = FieldType.STRING)
    private String futureArticleId;

    /**
     * 群唯一标识
     */
    @Field(name = "group_gen_uuid", targetType = FieldType.STRING)
    private String groupGenUuid;

    /**
     * 帖子内容
     */
    @Field(name ="content",targetType = FieldType.STRING)
    private String content;

    /**
     * 帖子图片路径，hbase路径
     */
    @Field(name ="media_file_list")
    private List<String> mediaFileList;

    /**
     * 发送时间戳(秒)
     */
    @Field(name = "send_time", targetType = FieldType.INT64)
    private Long sendTime;

    /**
     * 是否删除，0-不删 1-删除 默认为0
     */
    @Field(name = "is_delete", targetType = FieldType.INT32)
    private Integer isDelete;

    /**
     * 是否立即发布 0-不是 1-是 默认为0
     */
    @Field(name = "is_send_now", targetType = FieldType.INT32)
    private Integer isSendNow;

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
