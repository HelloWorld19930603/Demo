package com.yg.shop.server.pojo.dto;

import lombok.Data;

/**
 * 文章点赞返回信息
 *
 * @author ljh
 * @date 2022/12/12 19:32
 */
@Data
public class ArticleFollowDto {
    /**
     * mongodb自生成的主键, ObjectId
     */
    private String id;

    /**
     * 文章UUID
     */
    private String articleGenUuid;

    /**
     * 点赞人的唯一标识
     */
    private String userGenUuid;

    /**
     * 点赞人账号昵称
     */
    private String nickname;

    /**
     * 点赞人头像
     */
    private String headPhoto;

    /**
     * 关注类型, int32, 1-点赞 2-点踩
     */
    private Integer followType;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 插入时间
     */
    private Long insertTime;
}
