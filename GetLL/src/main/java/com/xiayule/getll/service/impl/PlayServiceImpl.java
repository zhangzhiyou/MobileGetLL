package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.*;
import com.xiayule.getll.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 14-7-20.
 * 摇奖等进行操作的类
 */
public class PlayServiceImpl implements PlayService {
    // 输出到系统日志
    private static Logger logger = Logger.getLogger(PlayService.class);

    private HttpService httpService;
    private CookieService cookieService;
    private CreditLogService creditLogService;
    private CreditService creditService;

    private String mobile;

    public PlayServiceImpl(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public void autoPlay(String mobile) {
        this.setMobile(mobile);

        logger.info("JobTask:" + "执行任务:" + "订阅者:" + mobile);

        // 如果未登录, 就登录
        if (!this.isLogined()) {
            logger.info(mobile + " 未登录, 进行登录");
            creditLogService.log(mobile, "未登录, 进行登录");

            this.loginDo();

            logger.info(mobile + " 登录成功");
            creditLogService.log(mobile, "登录成功");
        }

        // 累加每日奖励, 并接收返回结果
        double firstShakeGiveCredit = this.addDrawScore();

        // 流量币计数
        if (firstShakeGiveCredit > 0) {
            creditService.addCredit(mobile, firstShakeGiveCredit);
        }

        // 如果已经登录
        int remainTimes = this.getRemainTimes();

        logger.info(mobile + " 还剩 " + remainTimes + " 次");
        creditLogService.log(mobile, "还剩 " + remainTimes + " 次");

        if (remainTimes > 0) {
            // 有可能抽奖过程中获得再一次奖励

            int cnt = 0;
            do {
                String winName = this.draw();

                creditLogService.log(mobile, "第" + (++cnt) + "次摇奖,获得奖励:"
                        + winName);
                logger.info(mobile + " 第" + cnt + "次摇奖,获得奖励:"
                        + winName);

                // 如果获得的流量币，就要 计数
                // 解析出来获得的流量币
                // 如果获得流量币，一般都是 '0.1个流量币' 这样的形式
                if (winName.contains("个流量币")) {
                    double credit = Double.parseDouble(winName.replace("个流量币", ""));
                    creditService.addCredit(mobile, credit);
                }

                this.addDrawScore();

                remainTimes = Integer.parseInt(this.queryScore().getString("times"));

                try {
                    // 等待 3 秒，保险起见
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                creditLogService.log(mobile, " 剩余次数:" + remainTimes);
                logger.info(mobile + " 剩余次数:" + remainTimes);
            } while (remainTimes > 0);
        }

        // 总结性的日志，要放在 list 的最前面
        // 查询分数
        JSONObject queryScore = this.queryScore();

        creditService.setDayCredit(mobile, queryScore.getDouble("todayCredit"));

        creditLogService.log(mobile, "总计: 连续登录:" + queryScore.getString("count_1") + "天"
                + " 今日总计:" + queryScore.getString("todayCredit")
                + " 当前流量币: " + queryScore.getString("credit"));

        logger.info(mobile + " 总计: 连续登录:" + queryScore.getString("count_1") + "天"
                + " 今日总计:" + queryScore.getString("todayCredit")
                + " 当前流量币: " + queryScore.getString("credit"));
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

        System.out.println("remainsTimes:" + result);

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
        //TODO: DRAW WIN ID
        //TODO:如果想要订购业务，可以在这里增加代码
        String winId = getFromResult(json, "winID");

        if (winName == null || winName.equals("")) {
            winName = "没有获得任何奖品";
        }

        return winName;
    }


    public double addDrawScore() {
        String urlAddDrawScore = "http://shake.sd.chinamobile.com/score?method=addDrawScore";

        String json = get(urlAddDrawScore);

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
    public JSONObject queryScore() {
        String rs = queryScoreWithSource();
        return JsonUtils.stringToJson(rs)
                .getJSONObject("result")
                .getJSONArray("list")
                .getJSONObject(0);
    }

    /**
     * 同 queryScore， 但是返回的是官方返回的 string
     * @return
     */
    public String queryScoreWithSource() {
        String urlQueryScore = "http://shake.sd.chinamobile.com/score?method=queryScore";
        String rs = get(urlQueryScore);
        return rs;
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
     * 加载收支总和信息, 返回原json信息
     * @return
     */
    public String queryCreditSum() {
        String urlQueryCreditSumPath = "http://shake.sd.chinamobile.com/flowScore?method=querCreditSum";

        String rs = post(urlQueryCreditSumPath, null);
        return rs;
    }

    /**
     * 加载收支明细
     * @return
     */
    public String  queryCreditDetail() {
        String paramType = ServletActionContext.getRequest().getParameter("type");
        String paramStartNum = ServletActionContext.getRequest().getParameter("startNum");

        String urlQueryCreditDetail = "http://shake.sd.chinamobile.com/flowScore?method=queryCreditDetail&type=" +  paramType + "&startNum=" + paramStartNum;

        String rs = post(urlQueryCreditDetail, null);

        return rs;
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

    public void setCreditLogService(CreditLogService creditLogService) {
        this.creditLogService = creditLogService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }
}
