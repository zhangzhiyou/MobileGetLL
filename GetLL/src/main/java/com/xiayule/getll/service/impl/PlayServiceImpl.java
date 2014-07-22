package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.CookieService;
import com.xiayule.getll.service.HttpService;
import com.xiayule.getll.service.PlayService;
import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tan on 14-7-20.
 */
public class PlayServiceImpl implements PlayService {
    private HttpService httpService;
    private CookieService cookieService;

    private String mobile;

    public PlayServiceImpl(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取动态密码
     * @return
     */
    public String getPassword() {
        String urlGetPassword = "http://shake.sd.chinamobile.com/shake?method=getPassword&r=";

        urlGetPassword += Math.random();

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("mobile", mobile));

        String result = httpService.post(urlGetPassword, params);

        return result;
    }

    /**
     * 获取剩余摇奖次数
     */
    public int getRemainTimes() {
        String urlGetRemainTimes = "http://shake.sd.chinamobile.com/shake?method=getRemainTimes&r=" + Math.random();

        String result = post(urlGetRemainTimes, null);

        // TODO: 如果解析错误，就认为0次
        String drawCount = getFromResult(result, "drawCount");

        return Integer.parseInt(drawCount);
    }

    /**
     * 通过手机号 和 动态密码 登录
     * @param password 动态密码
     * @return
     */
    public String loginDo(String password) {
        String urlLoginDo = "http://shake.sd.chinamobile.com/shake?method=loginDo&r=" + Math.random();

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("mobile", mobile));
        params.add(new BasicNameValuePair("password", password));

        String s = httpService.post(urlLoginDo, params);

        updateCookieToLocal();

        return s;
    }

    /**
     * 通过手机号登录
     * @return
     */
    public String loginDo() {
        String resultJson = getPassword();
        String password = getFromResult(resultJson, "password");

        System.out.println("获得动态密码:" + password);

        String result = loginDo(password);
        return result;
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
     * 进行摇奖
     * @return 获得的金币数量
     */
    public String draw() {
        String urlDrawPath = "http://shake.sd.chinamobile.com/shake?method=draw&r=" + Math.random();
        String json = post(urlDrawPath, null);

        String winName = getFromResult(json, "winName");
        String winId = getFromResult(json, "winID");

        if (winName == null || winName.equals("")) {
            winName = "没有获得任何奖品";
        }

        return winName;
    }


    public String addDrawScore() {
        String urlAddDrawScore = "http://shake.sd.chinamobile.com/score?method=addDrawScore";

        String json = get(urlAddDrawScore);
        return json;
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
    public JSONObject queryScore() {
        String urlQueryScore = "http://shake.sd.chinamobile.com/score?method=queryScore";
        String rs = get(urlQueryScore);
        return JsonUtils.stringToJson(rs)
                .getJSONObject("result")
                .getJSONArray("list")
                .getJSONObject(0);
    }

    /**
     * 获取当前登录的手机号
     * @return 返回登录手机号，如果未登录，返回 ""
     */
    public boolean isLogined() {
        String urlLoadMobile = "http://shake.sd.chinamobile.com/shake?method=loadLoginMobile&r=" + Math.random();

        String rs = get(urlLoadMobile);

        String loginMobile = getFromResult(rs, "loginMobile");
        return loginMobile.equals(mobile);
    }

    /**
     * 调用httpservice的post，会设置和保存 cookie
     * @param url url
     * @param params 参数
     * @return 结果
     */
    private String post(String url, List<BasicNameValuePair> params) {

        updateCookieToService();

        String rs = httpService.post(url, params);

        updateCookieToLocal();

        return rs;
    }

    public String get(String url) {
        updateCookieToService();

        String rs = httpService.get(url);

        updateCookieToLocal();
        return rs;
    }

    public void updateCookieToService() {
        if (cookieService.isExist(mobile)) {
            CookieStore cookieStore = cookieService.getCookieStore(mobile);
            httpService.setCookieStore(cookieStore);
        }
    }

    public void updateCookieToLocal() {
        CookieStore cookieStore = httpService.getCookieStore();
        cookieService.saveCookie(mobile, cookieStore);
    }

    // set and get methods

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCookieService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    public void setHttpService(HttpService httpService) {
        this.httpService = httpService;
    }
}
