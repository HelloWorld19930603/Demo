package com.yg.shop.server.constant;

import lombok.Getter;

/**
 * 模板的自动评论
 *
 * @author ljh
 */
@Getter
public enum TemplateDefaultEnum {

    YES(1, "是自动评论"),
    NO(0, "不是自动评论");


    private final int type;
    private final String name;

    TemplateDefaultEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
