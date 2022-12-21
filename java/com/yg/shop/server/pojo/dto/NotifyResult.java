package com.yg.shop.server.pojo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知返回结果
 *
 * @author ljh
 * @date 2022/12/19 16:05
 */
@Data
public class NotifyResult {
    /**
     * 数据列表
     */
    private List<NotifyDto> list=new ArrayList<>();
    /**
     * 列表数量
     */
    private Long count=0L;
    /**
     * 已读数量
     */
    private Integer readNum;
    /**
     * 未读数量
     */
    private Integer  unreadNum;
}
