package com.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.pojo.dto.NotifyResult;
import com.yg.shop.server.pojo.vo.NotifyVo;
import com.yg.shop.server.service.NotifyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 代办
 *
 * @author ljh
 * @date 2022/11/29 19:46
 */
@RequestMapping("/notify")
@RestController
@AllArgsConstructor
public class NotifyController {

    private final NotifyService notifyService;

    /**
     * 搜索代办
     *
     * @param vo 参数
     * @return 代办信息
     */
    @PostMapping("/searchNotify")
    public Result<NotifyResult> searchNotify(@RequestBody @Valid NotifyVo vo) {
        NotifyResult page = notifyService.searchNotify(vo);
        return new Result<>(ResultCode.SUCCESS, page);
    }

}
