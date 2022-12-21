package com.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Map;

/**
 * 操作信息
 *
 * @author ljh
 * @date 2022/11/22 10:53
 */
@Data
@Document(collection = "operator_log")
public class OperatorLog {
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
     * 操作
     */
    @Field(name = "operator", targetType = FieldType.INT32)
    private Integer operator;
    /**
     * 网页用户名称
     */
    @Field(name = "web_username", targetType = FieldType.STRING)
    private String webUsername;
    /**
     * 1- 未开始 2-进行中 3-操作成功  4、操作失败
     */
    @Field(name = "result", targetType = FieldType.INT32)
    private Integer result;
    /**
     * 数据
     */
    @Field(name = "data", targetType = FieldType.STRING)
    private Map<String, String> data;
    /**
     * 操作集合的主键
     */
    @Field(name = "operator_info_id", targetType = FieldType.STRING)
    private String operatorInfoId;
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
