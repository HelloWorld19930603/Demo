package com.yg.shop.server.constant;


import lombok.Getter;

/**
 * 自动点赞
 *
 * @author ljh
 */
@Getter
public enum AutoLikeEnum {

    CLOSE(0,"关闭"),
    OPEN(1,"开启");


    private final int type;
    private final String name;

    AutoLikeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
