package com.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 入群申请列表参数
 *
 * @author ljh
 * @date 2022/11/23 11:49
 */
@Data
public class JoinGroupRequestListVo {
    /**
     * 群生成的唯一标示
     */
    @NotNull
    private String groupGenUuid;
    /**
     * 网页用户id,要查标签
     */
    @NotNull
    private String webUserId;
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页显示的数量
     */
    private Integer pageSize;
    /**
     * 开始时间
     */
    private Integer startTime;
    /**
     * 结束时间
     */
    private Integer endTime;
    /**
     * 搜索文本
     */
    private String text;
    /**
     * "0-无状态 1-接受 2-拒绝 3-处理中"
     */
    private Integer status;
}
