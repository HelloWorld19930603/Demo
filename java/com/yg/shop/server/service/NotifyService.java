package com.yg.shop.server.service;


import com.yg.shop.server.pojo.dto.NotifyResult;
import com.yg.shop.server.pojo.vo.NotifyVo;

/**
 * 通知
 *
 * @author ljh
 * @date 2022/11/22 11:36
 */
public interface NotifyService {

    /**
     * 搜索代办
     *
     * @param vo 参数
     * @return 数据
     */
    NotifyResult searchNotify(NotifyVo vo);
}
