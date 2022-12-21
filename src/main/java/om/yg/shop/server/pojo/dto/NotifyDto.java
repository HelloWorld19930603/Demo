package om.yg.shop.server.pojo.dto;

import lombok.Data;

/**
 * 代办返回结果
 *
 * @author ljh
 * @date 2022/12/9 15:28
 */
@Data
public class NotifyDto {
    /**
     * mongodb自生成的主键, ObjectId
     */
    private String id;

    /**
     * 群唯一标识
     */
    private String groupGenUuid;

    /**
     * 对应操作的mongodb主键，做去重用
     */
    private String operatorId;

    /**
     * 账号生成的唯一标示
     */
    private String userGenUuid;

    /**
     * 账号昵称
     */
    private String nickname;

    /**
     * 文章作者昵称   后
     */
    private String articleNickname;

    /**
     * 头像 hbase文件路径
     */
    private String headPhoto;

    /**
     * 1-发布帖子 2-点赞 3-评论
     */
    private Integer operatorType;

    /**
     * 文章生成的唯一标示
     */
    private String articleGenUuid;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 操作时间
     */
    private Long operatorTime;

    /**
     * 是否已读 int, 0-未读 1-已读，默认为0
     */
    private Integer isRead;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 插入时间
     */
    private Long insertTime;
}
