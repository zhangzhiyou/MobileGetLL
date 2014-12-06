package com.xiayule.getll.utils.factory;

import javax.servlet.http.Cookie;

/**
 * Created by tan on 14-7-30.
 */
public class CookieFactory {
    public static Cookie newCookie(String key, String value) {
        // 设置 cookie
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*365*10);// cookie 默认十年

        return cookie;
    }
}
