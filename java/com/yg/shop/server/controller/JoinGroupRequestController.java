package com.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.dto.JoinGroupRequestResultDto;
import com.yg.shop.server.pojo.vo.JoinGroupRequestListVo;
import com.yg.shop.server.service.JoinGroupRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 入群申请
 *
 * @author ljh
 * @date 2022/11/22 11:06
 */
@RestController
@RequestMapping("/JoinGroupRequest")
public class JoinGroupRequestController {

    private final JoinGroupRequestService joinGroupRequestService;

    public JoinGroupRequestController(JoinGroupRequestService joinGroupRequestService) {
        this.joinGroupRequestService = joinGroupRequestService;
    }

    /**
     * 获取入群申请的成员列表
     *
     * @param vo 参数
     * @return 数据列表
     */
    @PostMapping("/findJoinGroupRequestList")
    public Result<JoinGroupRequestResultDto> findJoinGroupRequestList(@RequestBody @Valid JoinGroupRequestListVo vo) {
        JoinGroupRequestResultDto page = joinGroupRequestService.findJoinGroupRequestList(vo);
        return new Result<>(ResultCode.SUCCESS, page);
    }

}
