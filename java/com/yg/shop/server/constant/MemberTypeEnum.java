package com.yg.shop.server.constant;

import lombok.Getter;

/**
 * @author ljh
 */

@Getter
public enum MemberTypeEnum {
    ADMIN(1,"管理员"),
    MODERATOR(2,"版主"),
    EXPERT(3,"专家"),
    MEMBER(4,"普通成员");

    private final int type;
    private final String name;
    MemberTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
