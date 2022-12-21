package om.yg.shop.server.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 操作参数
 *
 * @author ljh
 * @date 2022/11/23 15:54
 */
@Data
public class OperatorVo {
    /**
     * 网页用户id
     */
    @NotNull
    private String webUserId;
    /**
     * 网页用户名称,做日志用
     */
    @NotNull
    private String webUserName;
    /**
     * 生成的唯一标示
     */
    @NotNull
    private String groupGenUuid;
    /**
     * 群id
     */
    private String groupId;
    /**
     * 操作传数字
     *     BATCH_AGREE(1,"批量同意"),
     *     BATCH_REFUSE(2,"批量拒绝"),
     *     BATCH_REMIND(3,"批量提醒"),
     *     BATCH_REMOVE(4,"批量移除"),
     *     ALL_REMIND(5,"全部提醒"),
     *     ALL_AGREE(16,"全部同意");
     */
    @NotNull
    private Integer operator;
    /**
     * userIds
     */
    private List<String> userIdList;
    /**
     * 日志状态
     *     BATCH_AGREE(1,"批量同意"),
     *     BATCH_REFUSE(2,"批量拒绝"),
     *     BATCH_REMIND(3,"批量提醒"),
     *     BATCH_REMOVE(4,"批量移除"),
     *     ALL_REMIND(5,"全部提醒"),
     *     ADD_GROUP(6,"添加群"),
     *     UPDATE_GROUP_TAG(7,"修改群标签"),
     *     IMPORT_GROUP_MEMBER(8,"导出群成员"),
     *     BATCH_IMPORT_GROUP_MEMBER(9,"批量导出群成员"),
     *     ALL_IMPORT_GROUP_MEMBER(10,"全部导出群成员"),
     *     UPDATE_GROUP_MEMBER_TAG(11,"修改群成员标签"),
     *     AGREE(12,"同意"),
     *     REFUSE(13,"拒绝"),
     *     REMIND(14,"提醒"),
     *     REMOVE(15,"移除"),
     *     ALL_AGREE(16,"全部同意");
     */
    @NotNull
    private Integer logStatus;
}
