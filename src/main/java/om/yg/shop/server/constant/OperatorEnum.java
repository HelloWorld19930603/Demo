package om.yg.shop.server.constant;

import lombok.Getter;

/**
 * 操作常量
 * @author ljh
 */
@Getter
public enum OperatorEnum {

    BATCH_AGREE(1,"批量同意"),
    BATCH_REFUSE(2,"批量拒绝"),
    BATCH_REMIND(3,"批量提醒"),
    BATCH_REMOVE(4,"批量移除"),
    ALL_REMIND(5,"全部提醒"),
    ALL_AGREE(16,"全部同意");
    private final int type;
    private final String name;
    OperatorEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
