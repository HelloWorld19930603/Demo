package com.yg.shop.server.constant;

import lombok.Getter;

/**
 * 操作常量
 * @author ljh
 */
@Getter
public enum OperatorStatusEnum {

    DEFAULT_STATUS(1,"未开始"),
    PROCESSING(2,"进行中"),
    SUCCESS(3,"操作成功"),
    FAIL(4,"操作失败");
    private final int type;
    private final String name;
    OperatorStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
