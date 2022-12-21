package com.yg.shop.server.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回结果
 *
 * @author ljh
 * @date 2022/11/23 10:36
 */
@Data
public class ResultPage<T> {
    /**
     * 数据列表
     */
    private List<T> list=new ArrayList<>();
    /**
     * 列表数量
     */
    private Long count=0L;
}
