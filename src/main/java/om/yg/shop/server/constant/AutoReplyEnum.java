package om.yg.shop.server.constant;


import lombok.Getter;

/**
 * 自动回复
 *
 * @author ljh
 */
@Getter
public enum AutoReplyEnum {

    CLOSE(0,"关闭"),
    OPEN(1,"开启");


    private final int type;
    private final String name;

    AutoReplyEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
