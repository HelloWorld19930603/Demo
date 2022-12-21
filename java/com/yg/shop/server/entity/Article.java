package com.yg.shop.server.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Map;

/**
 * 文章
 *
 * @author ljh
 * @date 2022/12/9 12:14
 */
@Data
@Document(collection = "article")
public class Article {
    /**
     * mongodb自生成的主键, ObjectId
     */
    @Id
    private String id;

    /**
     * 是app类型，int32, 2-facebook
     */
    @Field(name = "app_type", targetType = FieldType.INT32)
    private Integer appType;

    /**
     * 文章id
     */
    @Field(name = "article_id", targetType = FieldType.STRING)
    private String articleId;

    /**
     * 文章url
     */
    @Field(name = "url", targetType = FieldType.STRING)
    private String url;

    /**
     * 文章标题
     */
    @Field(name = "title", targetType = FieldType.STRING)
    private String title;

    /**
     * 文章类型, int32, 1-原创 2-转发 3-引用 4-评论
     */
    @Field(name = "article_type", targetType = FieldType.INT32)
    private Integer articleType;

    /**
     * 内容类型, int32, 1-文本 2-多媒体 3-文本和多媒体
     */
    @Field(name = "content_type", targetType = FieldType.INT32)
    private Integer contentType;

    /**
     * 内容文本
     */
    @Field(name = "content", targetType = FieldType.STRING)
    private String content;

    /**
     * 媒体列表
     */
    @Field(name = "media_list")
    private List<Map<String, String>> mediaList;

    /**
     * 发文时间戳(秒)
     */
    @Field(name = "create_time", targetType = FieldType.INT64)
    private Long createTime;

    /**
     * 评论数
     */
    @Field(name = "reply_count", targetType = FieldType.INT32)
    private Integer replyCount;

    /**
     * 引用数
     */
    @Field(name = "quote_count", targetType = FieldType.INT32)
    private Integer quoteCount;

    /**
     * 点赞数
     */
    @Field(name = "like_count", targetType = FieldType.INT32)
    private Integer likeCount;

    /**
     * 点踩数
     */
    @Field(name = "tread_count", targetType = FieldType.INT32)
    private Integer treadCount;

    /**
     * 关联文章生成唯一标识
     */
    @Field(name = "relate_article_gen_uuid", targetType = FieldType.STRING)
    private String relateArticleGenUuid;

    /**
     * 关联文章的用户生成唯一标识
     */
    @Field(name = "relate_article_user_gen_uuid", targetType = FieldType.STRING)
    private String relateArticleUserGenUuid;

    /**
     * 关联文章的URL
     */
    @Field(name = "relate_article_url", targetType = FieldType.STRING)
    private String relateArticleUrl;

    /**
     * future_article主键id, 只有在页面上发帖时，记录，方便回溯
     */
    @Field(name = "future_article_id", targetType = FieldType.STRING)
    private String futureArticleId;

    /**
     * 评论父级的昵称
     */
    @Field(name = "comment_parent_nickname", targetType = FieldType.STRING)
    private String commentParentNickname;

    /**
     * 文章作者UUID
     */
    @Field(name = "user_gen_uuid", targetType = FieldType.STRING)
    private String userGenUuid;

    /**
     * 文章UUID
     */
    @Field(name = "article_gen_uuid", targetType = FieldType.STRING)
    private String articleGenUuid;

    /**
     * 群uuid
     */
    @Field(name = "group_gen_uuid", targetType = FieldType.STRING)
    private String groupGenUuid;

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
