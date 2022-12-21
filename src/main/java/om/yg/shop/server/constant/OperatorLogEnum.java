package om.yg.shop.server.constant;

import lombok.Getter;

/**
 * 操作日志常量
 *
 * @author ljh
 */
@Getter
public enum OperatorLogEnum {

    BATCH_AGREE(1, "批量同意"),
    BATCH_REFUSE(2, "批量拒绝"),
    BATCH_REMIND(3, "批量提醒"),
    BATCH_REMOVE(4, "批量移除"),
    ALL_REMIND(5, "全部提醒"),
    ADD_GROUP(6, "添加群"),
    UPDATE_GROUP_TAG(7, "修改群标签"),
    IMPORT_GROUP_MEMBER(8, "导出群成员"),
    BATCH_IMPORT_GROUP_MEMBER(9, "批量导出群成员"),
    ALL_IMPORT_GROUP_MEMBER(10, "全部导出群成员"),
    UPDATE_GROUP_MEMBER_TAG(11, "修改群成员标签"),
    AGREE(12, "同意"),
    REFUSE(13, "拒绝"),
    REMIND(14, "提醒"),
    REMOVE(15, "移除"),
    ALL_AGREE(16, "全部同意"),
    ADD_AUTO_COMMENT(17, "添加自动评论"),
    UPDATE_AUTO_COMMENT(18, "编辑自动评论"),
    DEL_AUTO_COMMENT(19, "删除自动评论"),
    OPEN_AUTO_LIKE(20, "开启自动点赞"),
    CLOSE_AUTO_LIKE(21, "关闭自动点赞"),
    OPEN_AUTO_REPLY(22, "开启自动回复"),
    CLOSE_AUTO_REPLY(23, "关闭自动回复"),
    CREATE_FUTURE_ARTICLE(24, "创建定时贴"),
    UPDATE_FUTURE_ARTICLE(25, "修改定时贴"),
    UPDATE_SEND_TIME(26, "修改定时贴发布时间"),
    NOT_SEND_TIME(27, "立即发布"),
    DELETE_FUTURE_ARTICLE(28, "删除定时贴"),
    SEND_ARTICLE(29, "发布文章"),
    READ_ARTICLE(30, "首次从代办中阅读文章"),
    SET_TEMPLATE_EFAULT(31, "设置为自动评论");


    private final int type;
    private final String name;

    OperatorLogEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

}
