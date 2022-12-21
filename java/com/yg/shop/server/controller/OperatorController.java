package com.yg.shop.server.controller;

import com.yg.base.code.ResultCode;
import com.yg.base.response.Result;
import com.yg.shop.server.service.OperationService;
import com.yg.shop.server.pojo.vo.OperatorVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 操作接口
 *
 * @author ljh
 * @date 2022/11/22 11:06
 */
@RestController
@RequestMapping("/operator")
public class OperatorController {

    private final OperationService operationService;

    public OperatorController(OperationService operationService) {
        this.operationService = operationService;
    }

    /**
     * 添加操作
     *
     * @param vo 参数
     * @return boolean
     */
    @PostMapping("/saveOperator")
    public Result<Boolean> addMyGroup(@RequestBody @Valid OperatorVo vo) {
        Boolean b = operationService.saveOperator(vo);
        return new Result<>(ResultCode.SUCCESS, b);
    }

}
