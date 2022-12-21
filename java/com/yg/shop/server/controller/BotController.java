package com.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.vo.BotHeartVo;
import com.yg.shop.server.pojo.vo.IdVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;
import com.yg.shop.server.service.BotService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 机器人
 *
 * @author ljh
 * @date 2022/11/29 19:46
 */
@RequestMapping("/bot")
@RestController
public class BotController extends BaseController {

    private final BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }

    /**
     * 心跳
     *
     * @param vo 参数
     */
    @PostMapping("/heartInfo")
    public void heartInfo(@RequestBody @Valid BotHeartVo vo) {
        botService.botHeart(vo);
    }

    /**
     * 自动点赞
     *
     * @param vo 参数
     * @return 当前按钮的状态
     */
    @PostMapping("/autoFabulous")
    public Result<Integer> autoFabulous(@RequestBody @Valid IdVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Integer i= botService.autoFabulous(vo, user);
        return new Result<>(ResultCode.SUCCESS, i);
    }

    /**
     * 自动评论
     *
     * @param vo 参数
     * @return 当前按钮的状态
     */
    @PostMapping("/autoComment")
    public Result<Integer> autoComment(@RequestBody @Valid IdVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Integer b = botService.autoComment(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);
    }
}
