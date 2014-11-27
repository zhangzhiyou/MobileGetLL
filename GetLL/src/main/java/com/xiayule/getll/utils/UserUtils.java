package com.xiayule.getll.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;

/**
 * Created by tan on 14-11-25.
 */
public class UserUtils {

    /**
     * 通过查看userAgent中是否包含下面的关键字,来判断是否为pc用户
     */
    private static final String[] pcKeyWords = new String[] {
            "mac os x", "macintosh", "winnt", "win95",
            "win98", "windows", "x11", "mac_powerpc", "linux"
    };

    /**
     * 通过查看userAgent中是否包含下面的关键字,来判断是否为mobile用户
     */
    private static final String[] mobileKeyWords = new String[] {
            "mobile", "android", "phone"
    };

    /**
     * 通过传来的 userAgent 判断是否为手机用户
     * @param userAgent
     * @return
     */
    public static Boolean isMobile(String userAgent) {

        userAgent = userAgent.toLowerCase();

        // 判断是否是mobile
        Boolean isMobile = true;

        // 只要有可能是手机,就优选认为是手机
        for (String keyword : pcKeyWords) {
            if (userAgent.contains(keyword)) {
                isMobile = false;
                break;
            }
        }

        for (String keyword : mobileKeyWords) {
            if (userAgent.contains(keyword)) {
                isMobile = true;
                break;
            }
        }

        return isMobile;
    }

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
