package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xiayule.getll.factory.CookieFactory;
import com.xiayule.getll.service.*;
import com.xiayule.getll.utils.Constants;
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
    // 兑换日志
    private static Logger exchangeLogger = LogManager.getLogger("com.xiayule.exchange");

    private SubscriberService subscriberService;
    private PlayService playService;
    private RegisterCodeService registerCodeService;
    private CookieService cookieService;
//    private CreditLogService creditLogService;

    private String mobile;
    private String password;
    private String r;// 是个随机参数，为了防止缓存机制，struts中不加这个会warning，不加也行
    private String type;
    private String startNum;
    private Map json;
    private JSONObject jsonObj;
    private String exchangeID;

    // TODO:这个参数好像是从 /ajax/queryCreditSum.action 和 /ajax/queryCreditDetail.action 这里传过来的，是 $.ajax 自动添加的，目前不明白
    private String _;

    private Boolean isLogin;

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
        jsonObj = new JSONObject();

        String m = mobile;
        String pass = password;


        Cookie cookie = CookieFactory.newCookie("mobile", m);

        // 存在 cookie， 是订阅者
        if (cookieService.isExist(m) && subscriberService.isSubscribe(m)) {
            jsonObj.put("status", "ok");

            // 设置 cookie
            ServletActionContext.getResponse().addCookie(cookie);

            return Action.SUCCESS;
        } else  if (cookieService.isExist(m) && !subscriberService.isSubscribe(m)) {// 存在 cookie，不是订阅者
            // 订购本站业务
            String registerCode = registerCodeService.generateRegisterCode();
            subscriberService.subscribe(m, registerCode);
            jsonObj.put("status", "ok");

            // 设置 cookie
            ServletActionContext.getResponse().addCookie(cookie);

            return Action.SUCCESS;
        }

        // 如果不存在 cookie，且密码不为空 则需要登录
        else if (!cookieService.isExist(m) && (pass != null && !pass.equals(""))) {
            String strJson = playService.loginDo(m, pass);

            jsonObj = JsonUtils.stringToJson(strJson);

            // 如果返回的不是 ok，则没有登录成功
            if (!jsonObj.getString("status").equals("ok")) {
                return Action.SUCCESS;
            }

            String registerCode = registerCodeService.generateRegisterCode();
            subscriberService.subscribe(m, registerCode);

            // 设置 cookie
            ServletActionContext.getResponse().addCookie(cookie);

            return Action.SUCCESS;
        }

        // 如果不存在 cookie，并且没有 password
        else if (!cookieService.isExist(m) && (pass == null || pass.equals(""))) {
            jsonObj.put("status", "error");
            jsonObj.put("errorId", 0);
            jsonObj.put("message", "请输入动态密码");
            return Action.SUCCESS;
        }

      /*  if (subscriberService.isSubscribe(m)) {


            JSONObject result = new JSONObject();
            result.put("mobile", m);

            // 如果不存在 cookie，证明第一次登录
            if (!cookieService.isExist(m)) {
                result.put("firstLogin", true);

                // 如果没有使用过本站服务, 就先登录
                if (playService.loginDo(m, pass) == null) {
                    json.put("status", "fail");
                    // 非山东手机号
                    json.put("errorId", "1");
                    json.put("errorDesc", "亲，只有山东移动用户才能参与哦！");

                    return Action.SUCCESS;
                }
            }

            json.put("result", result);


        } else {
            json.put("status", "error");
            json.put("errorId", 0);
            json.put("errorDesc", "亲,您未注册过本站服务");
        }*/

        return Action.SUCCESS;
    }

    /**
     * 取得登录密码
     */
    public String getPasswordDo() {
        String m = mobile;
        String strRes = playService.getPassword(m);

        jsonObj = JsonUtils.stringToJson(strRes);

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

        //mobile = null;

        Cookie[] cookies = ServletActionContext.getRequest().getCookies();

        String m = getMobileFromCookie();

        if (m == null) {
            json.put("status", "ok");
            Map<String, String> result = new HashMap<String, String>();
            json.put("result", result);
        } else {
            json.put("status", "ok");

            Map<String, String> result = new HashMap<String, String>();
            result.put("loginMobile", m);

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
        String m = getMobileFromCookie();

        playService.autoPlay(m);

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

        if (days <= Constants.TTL_XUQI_DAY) strDays = days + "天(点我续期)";
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

    /**
     * 获取兑换流量的动态密码
     */
    public String getOtherPassword() {
        // 获取动态密码

        String paramMobile = mobile;
        String realMobile = getMobileFromCookie();
        Boolean paramIsLogin = isLogin;

        String t = type;

        String rs = playService.getOtherPassword(realMobile, paramMobile, t, paramIsLogin);

        System.out.println(rs);

        exchangeLogger.info(realMobile + ": 获取动态密码准备兑换");

        jsonObj = JsonUtils.stringToJson(rs);

        return Action.SUCCESS;
    }

    /**
     * 自动兑换5M流量
     * @return
     */
    public String exchangePrize() {
        // 获取动态密码
        String m = getMobileFromCookie();
        String paramPassword = password;

        String paramExchangeID = exchangeID;

        String paramType = type;

        String strJson = playService.exchangePrize(m, paramExchangeID, paramType, paramPassword);

        exchangeLogger.info(m +": 兑换id(" + paramExchangeID + ")" + " 兑换type(" + paramType + ")" + " 返回信息:(" + strJson + ")");


        jsonObj = JsonUtils.stringToJson(strJson);

        return Action.SUCCESS;
    }

    /**
     * 	获取积分兑换列表请求路径
     */
    public String queryPrize() {
        String m = getMobileFromCookie();

        String rs = playService.queryPrize(m);

        jsonObj = JsonUtils.stringToJson(rs);

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getExchangeID() {
        return exchangeID;
    }

    public void setExchangeID(String exchangeID) {
        this.exchangeID = exchangeID;
    }

    public String get_() {
        return _;
    }

    public void set_(String _) {
        this._ = _;
    }
}
