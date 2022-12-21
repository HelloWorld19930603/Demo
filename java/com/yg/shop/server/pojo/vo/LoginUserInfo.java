package com.yg.shop.server.pojo.vo;

import lombok.Data;

/**
 * 登陆用户基本信息
 *
 * @author ljh
 * @date 2022/12/9 16:45
 */
@Data
public class LoginUserInfo {
    /**
     * 用户id
     */
    private String webUserId;

    /**
     * 用户名
     */
    private String webUsername;
}
