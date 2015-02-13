package com.xiayule.getll.utils.factory;

import org.apache.http.impl.cookie.BasicClientCookie;

import javax.servlet.http.Cookie;
import java.util.Calendar;

/**
 * Created by tan on 14-7-30.
 */
public class CookieFactory {
    /**
     * 产生自己站使用的cookie
     * @param key
     * @param value
     * @return
     */
    public static Cookie newCookie(String key, String value, String contextPath) {
        // 设置 cookie
        Cookie cookie = new Cookie(key, value);

//        cookie.setPath("/");

        //sae 需要设置
        cookie.setPath(contextPath + "/");

        cookie.setMaxAge(60*60*24*365*10);// cookie 默认十年

        return cookie;
    }

    /**
     * 产生官方使用的cookie
     * @param key
     * @param value
     * @return
     */
    public static BasicClientCookie newShakeCookie(String key, String value) {
        BasicClientCookie cookie = new BasicClientCookie(key, value);
        // 设置 cookie
        cookie.setDomain("shake.sd.chinamobile.com");

        cookie.setPath("/");

        //设置过期时间为 100 年后
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 100);
        cookie.setExpiryDate(c.getTime());

       return cookie;
    }
}
