package com.xiayule.getll.service;

import net.sf.json.JSONObject;

/**
 * Created by tan on 14-7-21.
 */
public interface PlayService {
    public String getPassword(String mobile);
    public int getRemainTimes(String mobile);
    /**
     * 通过手机号登录
     * @return 如果非山东移动号码，返回空
     */
    public String loginDo(String mobile, String password);
    public String draw(String mobile);
    public double addDrawScore(String mobile);
    public boolean isLogined(String mobile);
    public JSONObject queryScore(String mobile);

    /**
     *  获取积分兑换列表请求路径
     * @return 原文
     */
    public String queryPrize(String mobile);

    /**
     * 同 queryScore， 但是返回的是官方返回的 string
     * @return
     */
    public String queryScoreWithSource(String mobile);

    /**
     * 加载收支总和信息, 返回原json信息
     * @return
     */
    public String queryCreditSum(String mobile);

    /**
     * 加载收支明细
     * @return
     */
    public String  queryCreditDetail(String mobile, String type, String startNum);

    /**
     * 获取其他密码，比如兑换流量币的密码, 返回原json
     */
    public String getOtherPassword(String realMobile, String paramMobile, String type, Boolean isLogin);


    /**
     * 获取流量
     * @param mobile
     * @param exchangeID 要兑换的 id， 1 为 5m 流量
     * @return
     */
    public String exchangePrize(String mobile, String exchangeID, String type, String password);

    public void autoPlay(String mobile);

    /**
     * 转赠
     * @param realMobile 自己的手机号（存在 cookie中的）
     * @param paramMobile 参数
     * @param password
     * @param smsContext
     * @param transferGifts
     * @return
     */
    public String transferGifts(String realMobile, String paramMobile, String password, String smsContext, String transferGifts);
}
