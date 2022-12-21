package com.yg.shop.server.pojo.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.HashSet;
import java.util.Set;

/**
 * 我的群组
 *
 * @author ljh
 * @date 2022/11/22 11:47
 */
@Data
public class InviteInfoDto {
    /**
     * 群生成的唯一标示
     */
    private String groupGenUuid;
    /**
     * 用户唯一id
     */
    private String userGenUuid;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像url
     */
    private String headPhotoUrl;
    /**
     * 头像文件路径
     */
    private String headPhoto;
    /**
     * 邀请人id
     */
    private String inviteUserId;
    /**
     * 生成的邀请人id
     */
    private String inviteUserGenUuid;
    /**
     * 邀请人昵称
     */
    private String inviteNickname;
    /**
     * 邀请人时间
     */
    private Long inviteTime;
    /**
     * 接收时间
     */
    private Long acceptTime;
    /**
     * 其他一些介绍的信息
     */
    private String otherInfo;
    /**
     * 发送提醒次数
     */
    private Integer sendInviteCount;
    /**
     * 最后一次发送提醒时间
     */
    private Long sendInviteTime;
    /**
     * 页面提醒时间
     */
    private Long webRemindTime;
    /**
     * 1-未接收 2-接收, 默认为1
     */
    private Integer status;
    /**
     * 标签列表
     */
    private Set<String> tagList=new HashSet<>(16);
    /**
     * 插入时间
     */
    private Long insertTime;
    /**
     * 修改时间
     */
    private Long updateTime;
}
