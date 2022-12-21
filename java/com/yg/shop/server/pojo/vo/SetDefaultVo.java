package com.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 设为默认vo
 *
 * @author ljh
 * @date 2022/12/19 10:21
 */
@Data
public class SetDefaultVo {
    /**
     * 老的id
     */
    private String oldId;

    /**
     * 新的id
     */
    @NotNull
    private String newId;

    /**
     * 群id
     */
    @NotNull
    private String groupGenUuid;
}
