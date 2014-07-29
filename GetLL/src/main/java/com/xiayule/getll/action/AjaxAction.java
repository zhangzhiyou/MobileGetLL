package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import java.util.*;

/**
 * Created by tan on 14-7-27.
 */
public class AjaxAction {
    private SubscriberService subscriberService;
    private PlayService playService;

    private String mobile;

    private Map json;
    private JSONObject jsonObj;

    public String getRankByTotal(){
        json = new HashMap();

        json.put("status", "ok");

        json.put("mobile", mobile);

        mobile = null;// 用完清空

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

        mobile = null;

        return Action.SUCCESS;
    }

    /**
     * 退出登录，即清除 cookie
     * @return
     */
    public String logout() {

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

        json = new HashMap();
        json.put("status", "ok");

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

        mobile = null;

        return Action.SUCCESS;
    }

    /**
     * 由本应用获取的总流量排行
     * @return
     */
    public String getRankByTotalAtHere() {

        return Action.SUCCESS;
    }

    /**
     * 从官网上获取数据后，再转发回去
     * @return
     */
    public String queryScore() {
        String m = getMobileFromCookie();

        playService.setMobile(m);

        String strJson = playService.queryScoreWithSource();
        jsonObj = JsonUtils.stringToJson(strJson);

        return Action.SUCCESS;
    }

    /**
     * 从请求中获取Cookie中存储的手机号
     * @return
     */
    private String getMobileFromCookie() {
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("mobile"))
                return cookie.getValue();
        }

        return null;
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

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public JSONObject getJsonObj() {
        return jsonObj;
    }

    public void setJsonObj(JSONObject jsonObj) {
        this.jsonObj = jsonObj;
    }
}
