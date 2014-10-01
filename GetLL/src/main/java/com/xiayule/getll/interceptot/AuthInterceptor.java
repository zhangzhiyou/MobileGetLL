package com.xiayule.getll.interceptot;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xiayule.getll.service.SubscriberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;

/**
 * Created by tan on 14-8-1.
 */
public class AuthInterceptor extends AbstractInterceptor {
    private static Logger logger = LogManager.getLogger(AuthInterceptor.class.getName());

    private SubscriberService subscriberService;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        // 如果存在 cookie 证明已经登录过了， 则取出手机号，
        String mobile = getMobileFromCookie();

        // 只要 mobile 为空，能访问的只有 login.html
        if (mobile == null && !actionInvocation.getProxy().getMethod().equals("login.html")) {
            return Action.LOGIN;
        }

        // 如果不存在，则证明进行的是登录操作, 跳过即可
        if (mobile == null || subscriberService.isSubscribe(mobile)) {
            logger.info(mobile + " 请求 " + actionInvocation.getProxy().getActionName());

            return actionInvocation.invoke();
        }

        // 否则，证明非法用户，清空cookie，重定向到登录页面
        clearCookies();

        // TOO: 设置 tip

        return Action.LOGIN;
    }

    /**
     * 从请求中获取Cookie中存储的手机号
     * @return
     */
    private String getMobileFromCookie() {
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mobile"))
                    return cookie.getValue();
            }
        }

        return null;
    }

    private void clearCookies() {
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

    // set and get methods

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }
}
