package com.yg.shop.server.service;


import com.yg.shop.server.pojo.vo.BotHeartVo;
import com.yg.shop.server.pojo.vo.IdVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;

/**
 * 机器人接口
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface BotService {

    /**
     * 机器人健康状态的接口
     *
     * @param vo 参数
     */
    void botHeart(BotHeartVo vo);


    /**
     * 自动点赞
     *
     * @param vo   前端参数
     * @param user 用户信息
     * @return 按钮状态
     */
    Integer autoFabulous(IdVo vo, LoginUserInfo user);

    /**
     * 自动评论
     *
     * @param vo   前端参数
     * @param user 用户信息
     * @return 按钮状态
     */
    Integer autoComment(IdVo vo, LoginUserInfo user);
}
