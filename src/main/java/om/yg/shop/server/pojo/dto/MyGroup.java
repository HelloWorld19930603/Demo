package om.yg.shop.server.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 我的群组
 *
 * @author ljh
 * @date 2022/11/22 11:47
 */
@Data
public class MyGroup {
    /**
     * mongodb自生成的主键, ObjectId
     */
    private String id;
    /**
     * 群昵称
     */
    private String nickname;

    /**
     * 机器人唯一id
     */
    private String botUserGenUuid;
    /**
     * 机器人昵称
     */
    private String botNickname;
    /**
     * 机器人头像文件路径
     */
    private String botHeadPhoto;
    /**
     * int, 0-无机器人 1-采集正常  2-操作被限制 3-机器人异常  默认为0
     */
    private Integer botStatus;
    /**
     * 成员数
     */
    private Integer memberCount;
    /**
     * 活跃度
     */
    private String activityDegree;
    /**
     * 群url，string
     */
    private String url;
    /**
     * 群头像url
     */
    private String headPhotoUrl;
    /**
     * 群头像文件路径
     */
    private String headPhoto;
    /**
     * 标签列表
     */
    private List<String> tagList;
    /**
     * 群生成的唯一id
     */
    private String groupGenUuid;
    /**
     * 群id
     */
    private String groupId;
    /**
     * 机器人自动点赞 int， 0-不点赞 1-点赞  默认为0
     */
    private Integer autoLike;
    /**
     * 机器人自动回复 int， 0-不回复 1-回复  默认为0
     */
    private Integer autoReply;
    /**
     * 插入时间
     */
    private Long insertTime;
    /**
     * 采集时间戳(秒)
     */
    private Long collectTime;
    /**
     * 更新时间戳(秒)
     */
    private Long updateTime;
    /**
     * 成员数量
     */
    private Long memberNumber = 0L;
    /**
     * 邀请数量
     */
    private Long inviteNumber = 0L;
    /**
     * 未接受邀请数量
     */
    private Long noInviteNumber = 0L;
    /**
     * 入群审核邀请数量
     */
    private Long joinGroupNumber = 0L;
    /**
     * 入群审核拒绝数量
     */
    private Long joinGroupRefuseNumber = 0L;
    /**
     * 入群审核同意数量
     */
    private Long joinGroupAgreeNumber = 0L;
    /**
     * 入群审核已处理数量
     */
    private Long joinGroupHandleNumber = 0L;

    /**
     * 入群审核待处理数量
     */
    private Long joinGroupNoHandleNumber = 0L;

}
