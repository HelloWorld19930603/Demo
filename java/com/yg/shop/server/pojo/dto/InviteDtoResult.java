package com.yg.shop.server.pojo.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 邀请管理的返回值
 *
 * @author ljh
 * @date 2022/11/29 16:46
 */
public class InviteDtoResult {
    /**
     * 数据列表
     */
    private List<InviteInfoDto> list=new ArrayList<>();
    /**
     * 邀请数量
     */
    private Long inviteCount=0L;
    /**
     * 邀请数量
     */
    private Long refuseCount=0L;
}
