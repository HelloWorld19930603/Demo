package com.yg.shop.server.service;


import com.yg.shop.server.pojo.ResultPage;
import com.yg.shop.server.pojo.dto.InviteInfoCountDto;
import com.yg.shop.server.pojo.dto.InviteInfoDto;
import com.yg.shop.server.pojo.vo.InviteInfoListVo;

/**
 * 邀请接口
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface InviteInfoService {

    /**
     * 获取邀请成员列表
     *
     * @param vo 参数
     * @return 列表
     */
    ResultPage<InviteInfoDto> findInviteList(InviteInfoListVo vo);

    /**
     * 获取群的分类统计
     *
     * @param uuid 群的唯一id
     * @return 统计结果
     */
    InviteInfoCountDto inviteCount(String uuid);
}
