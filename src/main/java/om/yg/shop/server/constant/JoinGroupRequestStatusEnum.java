package om.yg.shop.server.constant;

import lombok.Getter;

/**
 * 操作日志返回状态
 * @author ljh
 */
@Getter
public enum JoinGroupRequestStatusEnum {

    DEFAULT_STATUS(0,"无状态"),
    RECEIVE(1,"接受"),
    REFUSE(2,"拒绝"),
    RECEIVE_PROCESSING(3,"接受处理"),
    REFUSE_PROCESSING(4,"拒绝处理");


    private final int type;
    private final String name;
    JoinGroupRequestStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
