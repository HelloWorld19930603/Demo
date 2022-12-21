package om.yg.shop.server.constant;

import lombok.Getter;

/**
 * 邀请状态
 * @author ljh
 */
@Getter
public enum InviteStatusEnum {

    NOT_RECEIVE(1,"为接收"),
    RECEIVE(2,"接收"),
    REMOVE(3,"移除");
    private final int type;
    private final String name;
    InviteStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
