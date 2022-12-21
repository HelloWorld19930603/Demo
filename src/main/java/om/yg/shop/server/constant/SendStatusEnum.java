package om.yg.shop.server.constant;

import lombok.Getter;

/**
 * 发送状态状态
 *
 * @author ljh
 */
@Getter
public enum SendStatusEnum {

    DEFAULT(0,"默认,不发送"),
    SEND(1,"发送");


    private final int type;
    private final String name;

    SendStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
