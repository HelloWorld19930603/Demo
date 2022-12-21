package com.yg.shop.server.constant;


import lombok.Getter;

/**
 * 机器人状态
 *
 * @author ljh
 * @date 2022/11/30 10:30
 */
@Getter
public enum BotStatusEnum {
    NO_BOT(0,"没有机器人"),
    NORMAL(1,"采集正常"),
    LIMIT(2,"操作被限制"),
    ABNORMAL(3,"机器人异常");
    private final int type;
    private final String name;
    BotStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
