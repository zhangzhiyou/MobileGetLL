package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.service.draw.api.PlayService;
import com.xiayule.getll.utils.factory.CookieFactory;
import com.xiayule.getll.service.*;
import com.xiayule.getll.utils.JsonUtils;
import com.xiayule.getll.utils.UserUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.*;

/**
 * Created by tan on 14-7-27.
 */
@Component
@Scope("prototype")
public class AjaxAction {
    private static Logger logger = Logger.getLogger(PlayService.class.getName());

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private PlayService playService;

//    @Autowired
//    private RegisterCodeService registerCodeService;

    @Autowired
    private CookieService cookieService;

    // 返回的结果类型
    private Map json;
    private JSONObject jsonObj;

    // 参数, 每天加一个参数，都要在 cleanParams 函数中添加清理
    private String mobile;
    private String password;
    private String r;// 是个随机参数，为了防止缓存机制，struts中不加这个会warning，不加也行
    private String type;
    private String startNum;
    private String exchangeID;
    private String smsContext;
    private String transferGifts;

    // TODO:这个参数好像是从 /ajax/queryCreditSum.action 和 /ajax/queryCreditDetail.action 这里传过来的，是 $.ajax 自动添加的，目前不明白
    private String _;
    private Boolean isLogin;
    private String queryType;
    private String status;
    private String id;
    private String value;
    private String nickname;

    private void cleanParams() {
        mobile = null;
        password = null;
        r = null;
        type = null;
        startNum = null;
        exchangeID = null;

        _ = null;

        isLogin = null;
        smsContext = null;
        transferGifts = null;
        queryType = null;
        status = null;
        id = null;
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
        String m = mobile;
        String pass = password;

        cleanParams();

        jsonObj = new JSONObject();

        Cookie cookie = CookieFactory.newCookie("mobile", m);

        // 存在 cookie
//        if (cookieService.isExist(m)) {
            //todo: 密码为空,证明不用到官方登录, 如果保存的 cookie不能登录，则需要删除 cookie 重新登录
            if ((pass == null || pass.equals("")) && !playService.isLogined(m)) {// 如果登录状态失败
//                System.out.println("删除 cookie 成功");
//                logger.info(m + " 删除 cookie 成功");
//                cookieService.deleteCookie(m);

                jsonObj.put("status", "error");
                jsonObj.put("errorId", 0);
                jsonObj.put("message", "请输入动态密码");
                return Action.SUCCESS;
            } else if ((pass != null && !pass.equals(""))) {// 需要登录
                String strJson = playService.loginDo(m, pass);

//                System.out.println("loginDo: mobile: " + m + " password: " + pass);

                jsonObj = JsonUtils.stringToJson(strJson);

                // 如果返回的不是 ok，则没有登录成功
                if (!jsonObj.getString("status").equals("ok")) {
                    return Action.SUCCESS;
                }

//                String registerCode = registerCodeService.generateRegisterCode();
//                subscriberService.subscribe(m, registerCode);

                subscriberService.subscribe(m);

                // 设置 cookie
                ServletActionContext.getResponse().addCookie(cookie);

                return Action.SUCCESS;
            }

            if (subscriberService.isSubscribe(m)) {// 是订阅者
                jsonObj.put("status", "ok");

                // 设置 cookie
                ServletActionContext.getResponse().addCookie(cookie);

                return Action.SUCCESS;
            } else {// 不是订阅者
                // 订购本站业务
//                String registerCode = registerCodeService.generateRegisterCode();
//                subscriberService.subscribe(m, registerCode);
                subscriberService.subscribe(m);

                jsonObj.put("status", "ok");

                // 设置 cookie
                ServletActionContext.getResponse().addCookie(cookie);

                return Action.SUCCESS;
            }


//        }
        // 如果不存在 cookie，且密码不为空 则需要登录
        /*else if (!cookieService.isExist(m) && (pass != null && !pass.equals(""))) {
            String strJson = playService.loginDo(m, pass);

            System.out.println("loginDo: mobile: " + m + " password: " + pass);

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
        }*/

        // 如果不存在 cookie，并且没有 password
       /* else if (!cookieService.isExist(m) && (pass == null || pass.equals(""))) {
            jsonObj.put("status", "error");
            jsonObj.put("errorId", 0);
            jsonObj.put("message", "请输入动态密码");
            return Action.SUCCESS;
        }*/

//        return Action.SUCCESS;
    }

    /**
     * 取得登录密码
     */
    public String getPasswordDo() {
        String m = mobile;
        String strRes = playService.getPassword(m);

        jsonObj = JsonUtils.stringToJson(strRes);

        cleanParams();

        return Action.SUCCESS;
    }

    /**
     * 退出登录，即清除 cookie
     */
    public String logout() {

//        System.out.println(mobile);

        UserUtils.clearCookie();
        json = new HashMap();
        json.put("status", "ok");

        cleanParams();

        return Action.SUCCESS;
    }

    /**
     * 获取当前登录的手机号，官方的服务
     */
    /*public String loadLoginMobile() {

        if (playService.isLogined())
    }*/

    /**
     * 获得当前登录的手机号, 自己的服务
     * @returnm 登录的手机号
     */
    public String loadLoginedMobile() {
        json = new HashMap();

        //mobile = null;

        Cookie[] cookies = ServletActionContext.getRequest().getCookies();

        String m = UserUtils.getMobileFromCookie();



        if (m == null) {
            // 返回空
            jsonObj = new JSONObject();

            json.put("status", "ok");
            Map<String, String> result = new HashMap<String, String>();
            json.put("result", result);

            //todo: 测试
            json.put("api", "my");

            // 为了兼容
            jsonObj.putAll(json);

        } else {
            String rs = playService.loadLoginMobile(m);

            jsonObj = JsonUtils.stringToJson(rs);

            // 测试 todo
            if (jsonObj == null) {
                jsonObj = new JSONObject();
//                System.out.println("jsonobj == null");
            }
        }

        return Action.SUCCESS;
    }

    /**
     * 由本应用获取的总流量排行
     */
   /* public String getRankByTotalAtHere() {

        return Action.SUCCESS;
    }
*/
    /**
     * 从官网上获取数据后，再转发回去
     */
    public String queryScore() {
        String m = UserUtils.getMobileFromCookie();

//        playService.setMobile(m);

        String strJson = playService.queryScoreWithSource(m);
        jsonObj = JsonUtils.stringToJson(strJson);

        return Action.SUCCESS;
    }

    /*public String getRegisterCodeDo() {
        json = new HashMap();

        // 生成注册码
        String registerCode = registerCodeService.generateRegisterCode();

        json.put("status", "ok");

        Map<String, String> result = new HashMap<String, String>();
        result.put("registerCode", registerCode);

        json.put("result", result);

        return Action.SUCCESS;
    }*/

    /**
     * 立刻摇奖
     */
 /*   public String shakeNow() {
        final String m = UserUtils.getMobileFromCookie();

//        playService.autoPlay(m);

        // 加入执行队列
        drawRequest.addRequest(m);

*//*

        new Thread(new Runnable() {
            @Override
            public void run() {
                playService.autoPlay(m);
            }
        }).start();
*//*
        json = new HashMap();
        json.put("status", "ok");

        return Action.SUCCESS;
    }
*/
    /**
     * 获取注册码有效期剩余天数
     */
    public String getTTL() {
        String m = UserUtils.getMobileFromCookie();

        String strDays = subscriberService.getTTLDays(m);

        json = new HashMap();
        json.put("status", "ok");

        Map result = new HashMap();
        result.put("ttl", strDays);

        json.put("result", result);

        return Action.SUCCESS;
    }

    /**
     * 获得剩余摇奖次数
     *//*
    public String getRemainTimes() {
        String m = UserUtils.getMobileFromCookie();

//        playService.setMobile(m);

        int remainsTimes = playService.getRemainTimes(m);

        json = new HashMap();

        json.put("status", "ok");

        Map result = new HashMap();
        result.put("remainsTimes", remainsTimes);

        json.put("result", result);

        return Action.SUCCESS;
    }*/

    /**
     * 加载收支总和信息, 返回原json信息
     */
    public String queryCreditSum() {
        String m = UserUtils.getMobileFromCookie();

        String rs = playService.queryCreditSum(m);
        jsonObj = JsonUtils.stringToJson(rs);

        return Action.SUCCESS;
    }

    /**
     * 加载收支明细
     * @return
     */
    public String queryCreditDetail() {
        String m = UserUtils.getMobileFromCookie();
        String t = type;
        String startN = startNum;

//        playService.setMobile(m);
        String rs = playService.queryCreditDetail(m, t, startN);

        jsonObj = JsonUtils.stringToJson(rs);

        cleanParams();

        return Action.SUCCESS;
    }

    /**
     * 免费续期 30 天
     * @return
     */
    public String freshRegisterCode() {
        String m = UserUtils.getMobileFromCookie();

        // 生成注册码
//        String registerCode = registerCodeService.generateRegisterCode();

        // 注册注册码
//        subscriberService.subscribe(m, registerCode);

        subscriberService.subscribe(m);

        // 获得更新后得到有效时间
        String strDays = subscriberService.getTTLDays(m);

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
        String realMobile = UserUtils.getMobileFromCookie();
        Boolean paramIsLogin = isLogin;

        String t = type;

        String rs = playService.getOtherPassword(realMobile, paramMobile, t, paramIsLogin);

//        exchangeLogger.info(realMobile + ": 获取动态密码, 返回信息:(" + rs + ")");

        jsonObj = JsonUtils.stringToJson(rs);

        cleanParams();

        return Action.SUCCESS;
    }

    /**
     * 自动兑换5M流量
     * @return
     */
    public String exchangePrize() {
        // 获取动态密码
        String m = UserUtils.getMobileFromCookie();
        String paramPassword = password;

        String paramExchangeID = exchangeID;

        String paramType = type;

        String strJson = playService.exchangePrize(m, paramExchangeID, paramType, paramPassword);

//        exchangeLogger.info(m + ": 兑换id(" + paramExchangeID + ")" + " 兑换type(" + paramType + ")" + " 返回信息:(" + strJson + ")");


        jsonObj = JsonUtils.stringToJson(strJson);

        cleanParams();

        return Action.SUCCESS;
    }

    /**
     * 转赠流量币
     */
    public String transferGifts() {
        String realMobile = UserUtils.getMobileFromCookie();
        String paramMobile = mobile;
        String paramSmsContext = smsContext;
        String paramTransferGifts = transferGifts;

        String strJson = playService.transferGifts(realMobile, paramMobile, password, smsContext, paramTransferGifts);

        jsonObj = JsonUtils.stringToJson(strJson);

//        exchangeLogger.info(realMobile + ": 转赠给 " + paramMobile + " smsContext:" + paramSmsContext + " 转赠金额: " + paramTransferGifts + " 返回信息:(" + strJson + ")");

        cleanParams();

        return Action.SUCCESS;
    }

    /**
     * 	获取积分兑换列表请求路径
     */
    public String queryPrize() {
        String m = UserUtils.getMobileFromCookie();

        String rs = playService.queryPrize(m);

        jsonObj = JsonUtils.stringToJson(rs);

        return Action.SUCCESS;
    }

    // 未领流量查询
    public String getTransferGiftsList() {
        String m = UserUtils.getMobileFromCookie();
        String paramQueryType = queryType;
        String paramType = type;
        String paramStatus = status;

        String strJson = playService.getTransferGiftsList(m, paramQueryType, paramType, paramStatus);

        jsonObj = JsonUtils.stringToJson(strJson);

        cleanParams();

        return Action.SUCCESS;
    }

    /**
     * 领取朋友赠送的流量币
     * @return
     */
    public String transferGiftsReceive() {

        String m = UserUtils.getMobileFromCookie();

        String paramId = id;

        String strJson = playService.transferGiftsReceive(m, paramId);

//        exchangeLogger.info(m + ": 领取赠送的流量币 id:(" + id + ") " + " 返回信息:(" + strJson + ")");

        jsonObj = JsonUtils.stringToJson(strJson);

        cleanParams();

        return Action.SUCCESS;
    }

    //todo: 找不到对应的 action
    public String getPackage() {
        String m = UserUtils.getMobileFromCookie();

        String strJson = playService.getPackage(m);

        jsonObj = JsonUtils.stringToJson(strJson);

        cleanParams();

        return Action.SUCCESS;
    }

    public String smsNoticeSetQuery() {
        String m = UserUtils.getMobileFromCookie();

        String strJson = playService.smsNoticeSetQuery(m);

        jsonObj = JsonUtils.stringToJson(strJson);

        cleanParams();

        return Action.SUCCESS;
    }

    public String smsNoticeSet() {
        String m = UserUtils.getMobileFromCookie();

        String paramType = getType();

        String paramValue = getValue();

        String strJson = playService.smsNoticeSet(m, paramType, paramValue);

        jsonObj = JsonUtils.stringToJson(strJson);

        cleanParams();

        return Action.SUCCESS;
    }

    /**
     * 退订本站所有服务
     * @return
     */
    public String deleteService() {
        String m = UserUtils.getMobileFromCookie();

        //todo: 删除服务
        subscriberService.unSubscribe(m);
        cookieService.deleteCookie(m);

        json = new HashMap();
        json.put("status", "ok");

        cleanParams();

        logger.info(m + " 退订服务");

        return Action.SUCCESS;
    }

    public String statusForFriend() {
        String m = UserUtils.getMobileFromCookie();

        json = new HashMap();

        json.put("status", "ok");

        Map<String, Object> result = new HashMap<String, Object>();

        if (subscriberService.isSubForFriend(m)) {
            result.put("forFriendSuber", true);
        } else result.put("forFriendSuber", false);

        json.put("result", result);

        return Action.SUCCESS;
    }

    public String changeStatusForFriend() {
        String m = UserUtils.getMobileFromCookie();

        json = new HashMap();

        json.put("status", "ok");

        String s = getStatus();

//              System.out.println(s);

        cleanParams();

        if (s.trim().equals("1")) {
            subscriberService.subForFriend(m);
            json.put("message", "成功开启朋友摇奖功能");
        } else {
            subscriberService.unsubForFriend(m);
            json.put("message", "成功关闭朋友摇奖功能");
        }

        logger.info(m + " changeStatusForFriend: status(" + s + ")");

        json.put("status", "ok");

        return Action.SUCCESS;
    }

    public String ifExistNickName() {
        String cookieMobile = UserUtils.getMobileFromCookie();

        String strJson = playService.ifExistNickName(cookieMobile, nickname);

        jsonObj = JsonUtils.stringToJson(strJson);

        return Action.SUCCESS;
    }

    public String changeNickName() {
        String cookieMobile = UserUtils.getMobileFromCookie();

        String strJson = playService.changeNickName(cookieMobile, nickname);

        jsonObj = JsonUtils.stringToJson(strJson);

        return Action.SUCCESS;
    }


    public String refreshNickName() {
        String cookieMobile = UserUtils.getMobileFromCookie();

        String strJson = playService.refreshNickName(cookieMobile);

        jsonObj = JsonUtils.stringToJson(strJson);

        return Action.SUCCESS;
    }


    public String statusAutoReceiveGifts() {
        String m = UserUtils.getMobileFromCookie();

        json = new HashMap();

        json.put("status", "ok");

        Map<String, Object> result = new HashMap<String, Object>();

        if (subscriberService.isSubAutoReceiveGifts(m)) {
            result.put("autoReceive", true);
        } else result.put("autoReceive", false);

        json.put("result", result);

        return Action.SUCCESS;
    }

    public String changeStatusAutoReceive() {
        String m = UserUtils.getMobileFromCookie();

        json = new HashMap();

        json.put("status", "ok");

        String s = getStatus();

        cleanParams();

        if (s.trim().equals("1")) {
            subscriberService.subAutoReceiveGifts(m);
            json.put("message", "成功开启自动领取流量币功能");
        } else {
            subscriberService.unsubAutoReceiveGifts(m);
            json.put("message", "成功关闭自动领取流量币功能");

        }

        logger.info(m + " changeStatusAutoReceive: status(" + s + ")");

        json.put("status", "ok");

        return Action.SUCCESS;
    }

    // set and get methods


//    public DrawRequest getDrawRequest() {
//        return drawRequest;
//    }

//    public void setDrawRequest(DrawRequest drawRequest) {
//        this.drawRequest = drawRequest;
//    }

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

//    public void setRegisterCodeService(RegisterCodeService registerCodeService) {
//        this.registerCodeService = registerCodeService;
//    }

    /*public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }*/

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

    public String getTransferGifts() {
        return transferGifts;
    }

    public void setTransferGifts(String transferGifts) {
        this.transferGifts = transferGifts;
    }

    public String getSmsContext() {
        return smsContext;
    }

    public void setSmsContext(String smsContext) {
        this.smsContext = smsContext;
    }

    public String getStartNum() {
        return startNum;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
