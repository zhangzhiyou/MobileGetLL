package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.xiayule.getll.domain.Result;
import com.xiayule.getll.domain.User;
import com.xiayule.getll.service.SubscriberService;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by tan on 14-7-27.
 */
public class AjaxAction {
    private SubscriberService subscriberService;

    private String mobile;

    private Map json;

    public String getRankByTotal(){
        json = new HashMap();

        json.put("status", "ok");

        json.put("mobile", mobile);

        return Action.SUCCESS;
    }

    /**
     * ajax登录的方法
     */
    public String login() {
        json = new HashMap();

        if (subscriberService.isSubscribe(mobile)) {
            json.put("status", "ok");

            Map result = new HashMap();
            result.put("mobile", mobile);

            json.put("result", result);

            //ActionContext.getContext().getSession().put("mobile", mobile);
            Cookie cookie = new Cookie("mobile", mobile);
            cookie.setPath("/");
            cookie.setMaxAge(60*100);

            ServletActionContext.getResponse().addCookie(cookie);
        } else {
            json.put("status", "error");
        }

        return Action.SUCCESS;
    }

    /**
     * 获得当前登录的手机号
     * @returnm 登录的手机号
     */
    public String loadLoginMobile() {
        json = new HashMap();

        mobile = null;

        Cookie[] cookies = ServletActionContext.getRequest().getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mobile")) {
                    mobile = cookie.getValue();
                    break;
                }
            }
        }

        if (mobile == null) {
            json.put("status", "error");
        } else {
            json.put("status", "ok");

            Map<String, String> result = new HashMap<String, String>();
            result.put("loginMobile", mobile);

            json.put("result", result);
        }

        return Action.SUCCESS;
    }

    /**
     * 由本应用获取的总流量排行
     * @return
     */
    public String getRankByTotalAtHere() {

        return Action.SUCCESS;
    }

    // set and get methods

    public Map getJson() {
        return json;
    }

    public void setJson(Map json) {
        this.json = json;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public SubscriberService getSubscriberService() {
        return subscriberService;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }
}
