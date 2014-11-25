package com.xiayule.getll.utils;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;

/**
 * Created by tan on 14-11-25.
 */
public class UserUtils {
    /**
     * 从请求中获取Cookie中存储的手机号
     * @return
     */
    public static String getMobileFromCookie() {
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mobile"))
                    return cookie.getValue();
            }
        }

        return null;
    }

    public static void clearCookie() {
        // 退出登录，即清除 cookie
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setPath("/");
                cookie.setMaxAge(0);
                // 设置 cookie
                ServletActionContext.getResponse().addCookie(cookie);
            }
        }
    }
}
