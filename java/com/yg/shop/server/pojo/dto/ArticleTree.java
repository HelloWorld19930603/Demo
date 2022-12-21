package com.yg.shop.server.pojo.dto;

import com.yg.shop.server.entity.GroupMember;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文章树
 *
 * @author ljh
 * @date 2022/12/11 18:19
 */
@Data
public class ArticleTree {
    /**
     * mongodb自生成的主键, ObjectId
     */
    private String id;

    /**
     * 是app类型，int32, 2-facebook
     */
    private Integer appType;

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 文章url
     */
    private String url;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章类型, int32, 1-原创 2-转发 3-引用 4-评论
     */
    private Integer articleType;

    /**
     * 内容类型, int32, 1-文本 2-多媒体 3-文本和多媒体
     */
    private Integer contentType;

    /**
     * 内容文本
     */
    private String content;

    /**
     * 媒体列表
     */
    private List<Map<String, String>> mediaList;

    /**
     * 发文时间戳(秒)
     */
    private Long createTime;

    /**
     * 评论数
     */
    private Integer replyCount;

    /**
     * 引用数
     */
    private Integer quoteCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 点踩数
     */
    private Integer treadCount;

    /**
     * 关联文章生成唯一标识
     */
    private String relateArticleGenUuid;

    /**
     * 关联文章的用户生成唯一标识
     */
    private String relateArticleUserGenUuid;

    /**
     * 关联文章的URL
     */
    private String relateArticleUrl;

    /**
     * 文章作者UUID
     */
    private String userGenUuid;

    /**
     * 文章UUID
     */
    private String articleGenUuid;

    /**
     * 群uuid
     */
    private String groupGenUuid;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 插入时间
     */
    private Long insertTime;

    /**
     * 评论父级的昵称
     */
    private String commentParentNickname;

    /**
     * 账号信息
     */
    private GroupMember groupMember;

    /**
     * 子文章
     */
    private List<ArticleTree> sonList = new ArrayList<>();
}
