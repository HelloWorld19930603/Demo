package om.yg.shop.server.controller;

import com.yg.shop.server.pojo.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 控制器基础模块
 *
 * @author bxx
 */
@Slf4j
public abstract class BaseController {

    public LoginUserInfo getUser(HttpServletRequest request) {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("userId")) {
                loginUserInfo.setWebUserId(cookie.getValue());
            } else if (cookie.getName().equals("userName")) {
                loginUserInfo.setWebUsername(cookie.getValue());
            }
        }
        return loginUserInfo;
    }

}
