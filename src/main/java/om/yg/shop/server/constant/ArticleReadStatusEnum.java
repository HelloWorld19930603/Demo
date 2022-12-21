package om.yg.shop.server.constant;


import lombok.Getter;

/**
 * 文章阅读状态
 *
 * @author ljh
 */
@Getter
public enum ArticleReadStatusEnum {

    DEFAULT(0,"默认未读"),
    READ(1,"已读");


    private final int type;
    private final String name;

    ArticleReadStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
