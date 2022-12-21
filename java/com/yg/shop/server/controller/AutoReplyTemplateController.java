package com.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.entity.AutoReplyTemplate;
import com.yg.shop.server.pojo.vo.IdVo;
import com.yg.shop.server.pojo.vo.LoginUserInfo;
import com.yg.shop.server.pojo.vo.ReplyTemplateVo;
import com.yg.shop.server.pojo.vo.SetDefaultVo;
import com.yg.shop.server.service.AutoReplyTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


/**
 * 自动评论 自动回复
 *
 * @author ljh
 * @date 2022/11/29 19:46
 */
@RequestMapping("/AutoReplyTemplate")
@RestController
@RequiredArgsConstructor
public class AutoReplyTemplateController extends BaseController {
    private final AutoReplyTemplateService autoReplyTemplateService;

    /**
     * 添加自动评论
     *
     * @param vo 参数
     * @return boolean
     */
    @PostMapping("/addReplyTemplate")
    public Result<Boolean> addReplyTemplate(ReplyTemplateVo vo, HttpServletRequest request) {
        LoginUserInfo loginUserInfo = getUser(request);
        Boolean b = autoReplyTemplateService.addReplyTemplate(vo, loginUserInfo);
        if (b) {
            return new Result<>(ResultCode.SUCCESS, b);
        } else {
            return new Result<>(ResultCode.FAIL, "数量达到上限", b);
        }
    }

    /**
     * 搜索群的自动评论
     *
     * @param groupGenUuid 群唯一id
     * @return 列表
     */
    @GetMapping("/searchTemplate")
    public Result<List<AutoReplyTemplate>> searchTemplate(@RequestParam String groupGenUuid) {
        List<AutoReplyTemplate> list = autoReplyTemplateService.searchTemplate(groupGenUuid);
        return new Result<>(ResultCode.SUCCESS, list);
    }

    /**
     * 编辑自动评论
     *
     * @param vo 参数
     * @return boolean
     */
    @PostMapping("/updateReplyTemplate")
    public Result<Boolean> updateReplyTemplate(ReplyTemplateVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Boolean b = autoReplyTemplateService.updateReplyTemplate(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);
    }

    /**
     * 删除自动评论
     *
     * @param vo 参数
     * @return boolean
     */
    @DeleteMapping("/deleteReplyTemplate")
    public Result<Boolean> deleteReplyTemplate(@RequestBody @Valid IdVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Boolean b = autoReplyTemplateService.del(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);
    }

    /**
     * 设为默认的自动评论
     *
     * @param vo      参数
     * @param request 请求
     * @return boolean
     */
    @PostMapping("setDefault")
    public Result<Boolean> setDefault(@RequestBody @Valid SetDefaultVo vo, HttpServletRequest request) {
        LoginUserInfo user = getUser(request);
        Boolean b = autoReplyTemplateService.setDefault(vo, user);
        return new Result<>(ResultCode.SUCCESS, b);
    }

}
