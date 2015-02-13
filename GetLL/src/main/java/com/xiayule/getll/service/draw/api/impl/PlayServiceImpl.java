package com.xiayule.getll.service.draw.api.impl;

import com.xiayule.getll.service.*;
import com.xiayule.getll.service.draw.api.PlayService;
import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tan on 14-7-20.
 * 摇奖等进行操作的类
 */
@Component
@Scope("prototype")
public class PlayServiceImpl implements PlayService {
    // 输出到系统日志
    private static Logger logger = Logger.getLogger(PlayService.class);

    // 兑换日志
    private static Logger exchangeLogger = Logger.getLogger("com.xiayule.exchange");

    @Autowired
    private HttpService httpService;

    @Autowired
    private CookieService cookieService;

    /**
     * 获取动态密码
     * @return
     */
    public String getPassword(String mobile) {
        String urlGetPassword = "http://shake.sd.chinamobile.com/shake?method=getPassword&r="+Math.random();

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("mobile", mobile));

        String result = post(mobile, urlGetPassword, params);

        exchangeLogger.info(mobile + ": getPassword, 返回信息:(" + result + ")");
        System.out.println(mobile + ": getPassword, 返回信息:(" + result + ")");

        return result;
    }

    /**
     * 获取剩余摇奖次数
     */
    public int getRemainTimes(String mobile) {
        String urlGetRemainTimes = "http://shake.sd.chinamobile.com/shake?method=getRemainTimes&r=" + Math.random();

        String result = post(mobile, urlGetRemainTimes, null);


        try {
            String drawCount = getFromResult(result, "drawCount");
            return Integer.parseInt(drawCount);
        } catch (Exception e) {
            logger.info(mobile + " getRemainTimes 出错, 返回信息: " + "(" + result + ")");
            return 0; //  如果解析错误，就认为0次
        }
    }

    /**
     * 通过手机号 和 动态密码 登录
     * @param password 动态密码
     * @return 原文
     */
    public /*synchronized*/ String loginDo(String mobile, String password) {
        String urlLoginDo = "http://shake.sd.chinamobile.com/shake?method=loginDo&r=" + Math.random();

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("mobile", mobile));
        params.add(new BasicNameValuePair("password", password));

        // todo: 这里如果有多线程可能会除错, 因此加上线程锁

//        String s = httpService.post(urlLoginDo, params);
        String s = post(mobile, urlLoginDo, params);

        exchangeLogger.info(mobile + ": loginDo 返回信息:(" + s + ")");


//        System.out.println("loginDo:(" + s + ")");

//        updateCookieToLocal(mobile);

        return s;
    }



    /**
     * 从 json 文本中获取 key
     * @param json json 文本
     * @param key 要获取的 key
     * @return
     */
    private String getFromResult(String json, String key) {
        String value = JsonUtils.stringToJson(json).getJSONObject("result").getString(key);

        /*Map<String, String> mp = JsonUtils.jsonToHash(json);
        Map<String, String> resultJson = JsonUtils.jsonToHash(mp.get("result"));
        String value = resultJson.get(key);*/
        return value;
    }

    /**
     * 摇奖，返回 json 数据
     * @return json 数据
     */
    public String drawWithSource(String cookieMobile) {
        String urlDrawPath = "http://shake.sd.chinamobile.com/shake?method=draw&r=" + Math.random();

        String json = post(cookieMobile, urlDrawPath, null);

        logger.info(cookieMobile + " draw 返回(" + json + ")");

        return json;
    }

    /**
     * 进行摇奖
     * @return 获得的金币数量
     */
    public String draw(String cookieMobile) {
        String json = drawWithSource(cookieMobile);

        String winName = getFromResult(json, "winName");

        //TODO: DRAW WIN ID
        //TODO:如果想要订购业务，可以在这里增加代码
        String winId = getFromResult(json, "winID");

        if (winName == null || winName.equals("")) {
            winName = "没有获得任何奖品";
        }

        return winName;
    }


    public String addDrawScoreWithSource(String cookieMobile) {
        String urlAddDrawScore = "http://shake.sd.chinamobile.com/score?method=addDrawScore&r=" + Math.random();

        String json = get(cookieMobile, urlAddDrawScore);

        logger.info(cookieMobile + " addDrawScore 返回(" + json + ")");

        return json;
    }

    public double addDrawScore(String cookieMobile) {
        String json = addDrawScoreWithSource(cookieMobile);

        double firstShakeGiveCredit = JsonUtils.stringToJson(json).getJSONObject("result")
                .getJSONArray("list")
                .getJSONObject(0)
                .getDouble("firstShakeGiveCredit");

        return firstShakeGiveCredit;
    }

    /**
     * 返回类似如下的文本
     * {"message":"ok","result":{"list":[{"isHandlesCreditNumber":"3","count_1":"3","todayCredit":"0.3","times":"0","lastCreditTime":"2014-07-22 08:33:43","lastTime":"2014-07-22 13:38:30","usedCount":"0","otherTimes":"5","handlesCreditNumber":"5","credit":"4.44","mobile":"18369905136"}]},"status":"ok","class":"class com.aspire.portal.web.vo.JsonResult","code":""}
     *
     * count_1： 连续登录天数
     * todayCredit: 今天获取赠送积分数
     * times: 剩余次数
     *
     * 解析时，返回 list
     * @return
     */
    public JSONObject queryScore(String mobile) {
        String rs = queryScoreWithSource(mobile);
        return JsonUtils.stringToJson(rs)
                .getJSONObject("result")
                .getJSONArray("list")
                .getJSONObject(0);
    }

    /**
     * 同 queryScore， 但是返回的是官方返回的 string
     * @return
     */
    public String queryScoreWithSource(String mobile) {
        String urlQueryScore = "http://shake.sd.chinamobile.com/score?method=queryScore";
        String rs = get(mobile, urlQueryScore);
        return rs;
    }

    /**
     * 获取当前登录的手机号
     * @return 返回登录手机号，如果未登录，返回 ""
     */
    public boolean isLogined(String mobile) {
        String rs = loadLoginMobile(mobile);

        try {
            String loginMobile = getFromResult(rs, "loginMobile");

            //todo: 看了下日志,如果加载失败返回null,不应该删除cookie
            //todo: 这个应该放到 loadLoginMobile 里面?
            //todo: 返回的信息为 {"message":"ok","result":{"loginMobile":null,"seconds":0,"nickName":null,"drawMobile":null,"loginMobileProvinceId":null,"drawMobileProvinceId":null},"status":"ok","class":"class com.aspire.portal.web.vo.JsonResult","code":""}
            //todo: 但是 loginMobile 的值为 null
            if (loginMobile == null || loginMobile.equals("") || loginMobile.equals("null")) {
                return false;
            }

            // 只有当加载出来手机号了,且加载的手机号不为null, 才删除cookie
            if ( !loginMobile.equals(mobile)) {
                logger.info(mobile + " loadLoginMobile返回mobile(" + loginMobile + ") 删除 cookie 成功");
                cookieService.deleteCookie(mobile);
                return false;
            } else return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String loadLoginMobile(String mobile) {
        String urlLoadMobile = "http://shake.sd.chinamobile.com/shake?method=loadLoginMobile&r=" + new Date().getTime();

        String rs = get(mobile, urlLoadMobile);

        //todo: 检查返回信息
        logger.info(mobile + " loadLoginMobile 返回信息: " + "(" + rs + ")");

//        System.out.println(mobile + " loadLoginMobile 返回信息: " + "(" + rs + ")");

        return rs;
    }

    /**
     * 加载收支总和信息, 返回原json信息
     *
     */
    public String queryCreditSum(String mobile) {
        String urlQueryCreditSumPath = "http://shake.sd.chinamobile.com/flowScore?method=querCreditSum";

        String rs = post(mobile, urlQueryCreditSumPath, null);
        return rs;
    }

    /**
     * 加载收支明细
     * @return
     */
    public String queryCreditDetail(String mobile, String type, String startNum) {
        String urlQueryCreditDetail = "http://shake.sd.chinamobile.com/flowScore?method=queryCreditDetail&type=" +  type + "&startNum=" + startNum;

        String rs = post(mobile, urlQueryCreditDetail, null);

        return rs;
    }

    public String transferGiftsReceive(String cookieMobile, String paramId) {
        String urlTransferGiftsReceive =  "http://shake.sd.chinamobile.com/flowScore?method=transferGiftsReceive";

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("id", paramId));

        String rs = post(cookieMobile, urlTransferGiftsReceive, params);

        logger.info(cookieMobile + ": 领取赠送的流量币 id:(" + paramId + ") " + " 返回信息:(" + rs + ")");

        return rs;
    }

    /**
     * 调用httpservice的post，会设置和保存 cookie
     * @param url url
     * @param params 参数
     * @return 结果
     */
    private String post(String mobile, String url, List<BasicNameValuePair> params) {


        String rs = httpService.post(mobile, url, params);


        return rs;
    }

    public String get(String mobile, String url) {

        String rs = httpService.get(mobile, url);

        return rs;
    }

    /**
     * 获取其他密码，比如兑换流量币的密码, 返回原json
     */
    public String getOtherPassword(String realMobile, String paramMobile, String type, Boolean isLogin) {
        String urlGetOtherPassword = null;
        List<BasicNameValuePair> params = null;

        // 如果是转赠，则 type == null
        if (type == null) {
            urlGetOtherPassword = "http://shake.sd.chinamobile.com/shake?method=getOtherPassword&isLogin=true&r=" + Math.random();

            params = new ArrayList<BasicNameValuePair>();
            params.add(new BasicNameValuePair("mobile", paramMobile));

        }
        else {// 获取兑换动态密码
            urlGetOtherPassword = "http://shake.sd.chinamobile.com/shake?method=getOtherPassword&isLogin="+isLogin+"&mobile="+paramMobile+"&r=" + new Date().getTime();

            params = new ArrayList<BasicNameValuePair>();
            params.add(new BasicNameValuePair("type", type));
        }

        String rs = post(realMobile, urlGetOtherPassword, params);

        exchangeLogger.info(realMobile + ": getOtherPassword, 返回信息:(" + rs + ")");

        return rs;
    }

    public String transferGifts(String realMobile, String paramMobile, String password, String smsContext, String transferGifts) {
        String urlTransferGifts = "http://shake.sd.chinamobile.com/flowScore?method=transferGifts&r" + Math.random();

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("mobile", paramMobile));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("smsContext", smsContext));
        params.add(new BasicNameValuePair("transferGifts", transferGifts));

        String rs = post(realMobile, urlTransferGifts, params);

        exchangeLogger.info(realMobile + ": 转赠给 " + paramMobile + " smsContext:" + smsContext + " 转赠金额: " + transferGifts + " 返回信息:(" + rs + ")");

        return rs;
    }

    /**
     * 获得未领流量币信息
     */
    public String getTransferGiftsList(String cookieMobile, String queryType, String type, String status) {
        String urlGetTransferGiftsList = "http://shake.sd.chinamobile.com/flowScore?method=getTransferGiftsList";

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("queryType", queryType));
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("status", status));

        String rs = post(cookieMobile, urlGetTransferGiftsList, params);

        logger.info(cookieMobile + " getTransferGiftsList: 参数(queryType:" + queryType
                + ", type:" + type + ",status:" + status + ") 返回(" + rs + ")");

        return rs;
    }

    /**
     * 获取流量
     * @param mobile
     * @param exchangeID 要兑换的 id， 1 为 5m 流量
     * @return
     */
    public String exchangePrize(String mobile, String exchangeID, String type, String password) {
        String urlExchangePrize = "http://shake.sd.chinamobile.com/score?method=exchangePrize";

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("exchangeID", exchangeID));
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("password", password));

        String rs = post(mobile, urlExchangePrize, params);

        exchangeLogger.info(mobile + ": 使用动态(" + password + ") 兑换id(" + exchangeID + ") 兑换type(" + type + ") 返回信息:(" + rs + ")");

        return rs;
    }

    /**
     *  获取积分兑换列表请求路径
     * @return 原文
     */
    public String queryPrize(String mobile) {
        String urlQueryPrize = "http://shake.sd.chinamobile.com/score?method=queryPrize";

        String rs = get(mobile, urlQueryPrize);

        return rs;
    }

    /**
     * 获取流量套餐详细信息
     * @return
     */
    public String getPackage(String cookieMobile) {
        String urlGetPackage = "http://shake.sd.chinamobile.com/package?method=getPackage";

        String rs = post(cookieMobile, urlGetPackage, null);

        return rs;
    }

    /**
     * 设置朋友摇奖帐号
     * 重复使用会覆盖之前设置的朋友帐号
     * 摇奖完毕后，要再次使用该方法设置自己成的手机号
     * @param cookieMobile 要使用该服务的手机号
     * @param friendMobile 朋友的手机号
     * @return 流量汇返回值
     */
    public String setDrawMobile(String cookieMobile, String friendMobile) {
        String urlSetDrawMobile = "http://shake.sd.chinamobile.com/shake?method=setDrawMobile&r=" + Math.random();

        // 设置朋友帐号参数
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("drawMobile", friendMobile));

        String rs = post(cookieMobile, urlSetDrawMobile, params);
        return rs;
    }

    /**
     * 流量汇各类提醒状态
     * @return
     */
    public String smsNoticeSetQuery(String cookieMobile) {
        String urlSmsNoticeSetQuery = "http://shake.sd.chinamobile.com/systemSet?method=smsNoticeSetQuery";

        String rs = post(cookieMobile, urlSmsNoticeSetQuery, null);

//        System.out.println("smsNoticeSetQuery: " + rs);

        logger.info(cookieMobile + " smsNoticeSetQuery: rs(" + rs + ")");

        return rs;
    }

    /**
     * 设置流量汇提醒
     * @return
     */
    public String smsNoticeSet(String cookieMobile, String type, String value) {
        String urlSmsNoticeSet = "http://shake.sd.chinamobile.com/systemSet?method=smsNoticeSet";
        // 设置提醒参数
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("value", value));

        String rs = post(cookieMobile, urlSmsNoticeSet, params);

        logger.info(cookieMobile + " smsNoticeSet: cookieMobile(" + cookieMobile
                + ") type(" + type + ") value(" + value + ")" + " rs(" + rs + ")");

        return rs;
    }

    /**
     * 校验昵称是否存在
     */
    public String ifExistNickName(String cookieMobile, String nickName) {
        String urlIfExistNickName = "http://shake.sd.chinamobile.com/flowScore?method=ifExistNickName";

        // 设置参数
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("nickname", nickName));

        String rs = post(cookieMobile, urlIfExistNickName, params);

        logger.info(cookieMobile + " ifExistNickName: nickName(" + nickName + ") 返回(" + rs + ")");

        return rs;
    }

    /**
     * 修改昵称
     */
    public String changeNickName(String cookieMobile, String nickName) {
        String urlChangeNickName = "http://shake.sd.chinamobile.com/flowScore?method=changeNickName";

        // 设置参数
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("nickname", nickName));

        String rs = post(cookieMobile, urlChangeNickName, params);

        logger.info(cookieMobile + " changeNickName: nickName(" + nickName + ") 返回(" + rs + ")");

        return rs;
    }

    public String refreshNickName(String cookieMobile) {
        String urlRefreshNickName = "http://shake.sd.chinamobile.com/shake?method=refreshNickName&r=" + Math.random();

        String rs = post(cookieMobile, urlRefreshNickName, null);

        logger.info(cookieMobile + " refreshNickName 返回(" + rs + ")");

        return rs;
    }

    // set and get methods

//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }

    public void setCookieService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    public void setHttpService(HttpService httpService) {
        this.httpService = httpService;
    }


//    public void setCreditLogService(CreditLogService creditLogService) {
//        this.creditLogService = creditLogService;
//    }

}
