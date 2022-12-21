package com.yg.shop.server.pojo.vo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotNull;

/**
 * 搜索我的成员
 *
 * @author ljh
 * @date 2022/11/23 10:41
 */
@Data
public class GroupMemberVo {
    /**
     * 群唯一id 生成的
     */
    @NotNull
    private String groupGenUuid;
    /**
     * 网页用户id
     */
    @NotNull
    private String webUserId;
    /**
     * 类型，1-管理员 2-版主 3-专家 4-普通成员
     */
    @NotNull
    private Integer memberType;
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
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 搜索文本
     */
    private String text;
    /**
     * uuid,搜全部的成员
     */
    private String uuid;
}
