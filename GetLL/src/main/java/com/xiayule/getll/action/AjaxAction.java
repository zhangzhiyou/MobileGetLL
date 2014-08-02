package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.factory.CookieFactory;
import com.xiayule.getll.service.*;
import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import java.util.*;

/**
 * Created by tan on 14-7-27.
 */
public class AjaxAction {
    private static Logger logger = LogManager.getLogger(PlayService.class.getName());

    private SubscriberService subscriberService;
    private PlayService playService;
    private RegisterCodeService registerCodeService;
    private CookieService cookieService;
//    private CreditLogService creditLogService;

    private String mobile;
    private String r;// 是个随机参数，为了防止缓存机制，struts中不加这个会warning，不加也行
    private String type;
    private String startNum;
    private Map json;
    private JSONObject jsonObj;

    private String registerCode;


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


    public String getRankByTotal(){
        json = new HashMap();

        json.put("status", "ok");

        json.put("mobile", mobile);

        mobile = null;// 用完清空

        return Action.SUCCESS;
    }

    /**
     * ajax登录的方法, 如果传过来 registerCode，会优先注册
     * 如果不存在 手机号 的cookie，证明为第一次登录
     * 该服务写的有些复杂，因为有以下几种可能：
     * 1. 当非山东移动使用服务时，会消耗掉注册码，但是登录不成功
     * 2. 当非山东移动存在时（因为bug），会是服务停止
     *
     */
    public String login() {
        json = new HashMap();

        // 如果传递了注册码, 就先注册，时效会覆盖掉之前的
        if (registerCode != null && !registerCode.equals("") && registerCodeService.isValid(registerCode)) {

            // 判断下是不是移动手机号, 保证不是山东移动，不会注册
            if (!cookieService.isExist(mobile)) {
                // 返回空证明，不是山东移动号码
                if (playService.loginDo(mobile) == null) {
                    json.put("status", "fail");
                    // 非山东手机号
                    json.put("errorId", "1");
                    json.put("errorDesc", "亲，只有山东移动用户才能参与哦！");

                    return Action.SUCCESS;
                }
            }

            subscriberService.subscribe(mobile, registerCode);
        }

        if (subscriberService.isSubscribe(mobile)) {
            json.put("status", "ok");

            Map result = new HashMap();
            result.put("mobile", mobile);

            // 如果不存在 cookie，证明第一次登录
            if (!cookieService.isExist(mobile)) {
                result.put("firstLogin", true);

                // 立刻为该手机执行一次任务
//                logger.info(mobile + " 第一次使用本服务，立即执行一次任务");

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TODO; bug mobile 为空
//                        ystem.out.println("第一次登录手机号:" + mobile);

                // 如果没有使用过本站服务, 就先登录
                if (playService.loginDo(mobile) == null) {
                    json.put("status", "fail");
                    // 非山东手机号
                    json.put("errorId", "1");
                    json.put("errorDesc", "亲，只有山东移动用户才能参与哦！");

                    return Action.SUCCESS;
                }

//                        playService.autoPlay(mobile);
//                    }
//                }).start();
            }

            json.put("result", result);


            // 设置 cookie
            /*Cookie cookie = new Cookie("mobile", mobile);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*365);// cookie 默认一年*/

            Cookie cookie = CookieFactory.newCookie("mobile", mobile);

           /* CookieStore cookieStore = cookieService.getCookieStore(mobile);
            Cookie[] cookies = transFormCookieStore(cookieStore);
*/
            ServletActionContext.getResponse().addCookie(cookie);
        } else {
            json.put("status", "error");
            json.put("errorId", 0);
            json.put("errorDesc", "亲,您未注册过本站服务");
        }

        mobile = null;
        registerCode = null;

        return Action.SUCCESS;
    }

    //todo ：转发 url
    /* private Cookie[] transFormCookieStore(CookieStore cookieStore) {
        ServletActionContext.getrequ
    }*/

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

//        playService.setMobile(m);

        String strJson = playService.queryScoreWithSource(m);
        jsonObj = JsonUtils.stringToJson(strJson);

        return Action.SUCCESS;
    }

    public String getRegisterCodeDo() {
        json = new HashMap();

        // 生成注册码
        String registerCode = registerCodeService.generateRegisterCode();

        json.put("status", "ok");

        Map<String, String> result = new HashMap<String, String>();
        result.put("registerCode", registerCode);

        json.put("result", result);

        return Action.SUCCESS;
    }

    /**
     * 立刻摇奖
     * @return
     */
    public String shakeNow() {
        mobile = getMobileFromCookie();

        playService.autoPlay(mobile);

        json = new HashMap();
        json.put("status", "ok");

        return Action.SUCCESS;
    }

    /**
     * 获取注册码有效期剩余天数
     */
    public String getTTL() {
        String m = getMobileFromCookie();

        String strDays = getStrTTL(m);

        json = new HashMap();
        json.put("status", "ok");

        Map result = new HashMap();
        result.put("ttl", strDays);

        json.put("result", result);

        return Action.SUCCESS;
    }

    /**
     * 提取成一个函数，方便 getTTL 和 freshRegisterCode 调用
     * @return
     */
    private String getStrTTL(String m) {
        // 如果没有订阅，或者到期，会被 struts 拦截，因此不用考虑到期的情况
        Long remainSeconds = subscriberService.getTTL(m);

        // 将秒数转换为天
        Long days = remainSeconds / 60 / 60 / 24 + 1;

        String strDays = null;

        if (days <= 6) strDays = days + "天(免费续期)";
        else strDays = days + "天";

        return strDays;
    }

    /**
     * 获得剩余摇奖次数
     * @return
     */
    public String getRemainTimes() {
        String m = getMobileFromCookie();

//        playService.setMobile(m);

        int remainsTimes = playService.getRemainTimes(m);

        json = new HashMap();

        json.put("status", "ok");

        Map result = new HashMap();
        result.put("remainsTimes", remainsTimes);

        json.put("result", result);

        return Action.SUCCESS;
    }

    /**
     * 加载收支总和信息, 返回原json信息
     * @return
     */
    public String queryCreditSum() {
        String m = getMobileFromCookie();
//        playService.setMobile(m);
        String rs = playService.queryCreditSum(m);
        jsonObj = JsonUtils.stringToJson(rs);

        return Action.SUCCESS;
    }

    /**
     * 加载收支明细
     * @return
     */
    public String queryCreditDetail() {
        String m = getMobileFromCookie();

//        playService.setMobile(m);
        String rs = playService.queryCreditDetail(m, type, startNum);

        System.out.println("type:" + type + ";" + "startNum" + startNum);

        jsonObj = JsonUtils.stringToJson(rs);

        return Action.SUCCESS;
    }

    /**
     * 免费续期 7 天
     * @return
     */
    public String freshRegisterCode() {
        String m = getMobileFromCookie();

        // 生成注册码
        String registerCode = registerCodeService.generateRegisterCode();
        // 注册注册码
        subscriberService.subscribe(m, registerCode);

        // 获得更新后得到有效时间
        String strDays = getStrTTL(m);

        json = new HashMap();
        json.put("status", "ok");

        Map result = new HashMap();
        result.put("ttl", strDays);
        json.put("result", result);

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

    public void setPlayService(PlayService playService) {
        this.playService = playService;
    }

    public JSONObject getJsonObj() {
        return jsonObj;
    }

    public void setJsonObj(JSONObject jsonObj) {
        this.jsonObj = jsonObj;
    }

    public void setRegisterCodeService(RegisterCodeService registerCodeService) {
        this.registerCodeService = registerCodeService;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public void setCookieService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

//    public void setCreditLogService(CreditLogService creditLogService) {
//        this.creditLogService = creditLogService;
//    }

    public void setR(String r) {
        this.r = r;
    }

    public String getR() {
        return r;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }
}
