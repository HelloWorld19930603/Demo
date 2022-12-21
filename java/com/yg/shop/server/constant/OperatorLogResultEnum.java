package com.yg.shop.server.constant;

import lombok.Getter;

/**
 * 操作日志返回状态
 * @author ljh
 */
@Getter
public enum OperatorLogResultEnum {

    NOT_START(1,"未开始"),
    RUN(2,"进行中"),
    SUCCESS(3,"操作成功"),
    FAIL(4,"操作失败");

    private final int type;
    private final String name;
    OperatorLogResultEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
