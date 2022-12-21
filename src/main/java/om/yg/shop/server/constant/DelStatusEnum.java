package om.yg.shop.server.constant;

import lombok.Getter;

/**
 * 删除状态
 *
 * @author ljh
 */
@Getter
public enum DelStatusEnum {

    SAVE(1,"默认,保存"),
    DELETE(2,"删除");


    private final int type;
    private final String name;

    DelStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
