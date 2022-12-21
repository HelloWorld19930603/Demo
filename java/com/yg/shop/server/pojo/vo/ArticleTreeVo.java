package com.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 文章树搜索参数
 *
 * @author ljh
 * @date 2022/12/11 18:37
 */
@Data
public class ArticleTreeVo {
    /**
     * 群id
     */
    @NotNull
    private String groupGenUuid;

    /**
     * 搜索文本
     */
    private String text;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 当前页
     */
    @NotNull
    private Integer pageNum = 1;

    /**
     * 每页显示的数量
     */
    @NotNull
    private Integer pageSize = 4;

    /**
     * 深度,默认是1,查1层的评论,点击查看更多传100,代表查100层
     */
    private Integer height = 1;

    /**
     * 宽度,默认是2,查每一层的几篇文章,点击查看更多传10000,代表传查每层的10000条数据,不要想着数据不够,100*100000会把浏览器搞崩的
     */
    private Integer weight=2;


}
