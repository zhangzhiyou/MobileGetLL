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
     *
     * @return 如果非山东移动号码，返回空
     */
    public String loginDo(String mobile, String password);

    public double addDrawScore(String mobile);
    public String addDrawScoreWithSource(String cookieMobile);

    public boolean isLogined(String mobile);

    //todo: 测试
    public String get(String mobile, String url);

    public JSONObject queryScore(String mobile);

    /**
     * 摇奖，返回 json 数据
     * @return json 数据
     */
    public String drawWithSource(String cookieMobile);

    /**
     * 进行摇奖
     * @return 获得的金币数量
     */
    public String draw(String cookieMobile);

    /**
     * 获取积分兑换列表请求路径
     *
     * @return 原文
     */
    public String queryPrize(String mobile);

    /**
     * 同 queryScore， 但是返回的是官方返回的 string
     *
     * @return
     */
    public String queryScoreWithSource(String mobile);

    /**
     * 加载收支总和信息, 返回原json信息
     *
     * @return
     */
    public String queryCreditSum(String mobile);

    /**
     * 加载收支明细
     *
     * @return
     */
    public String queryCreditDetail(String mobile, String type, String startNum);

    /**
     * 获取其他密码，比如兑换流量币的密码, 返回原json
     */
    public String getOtherPassword(String realMobile, String paramMobile, String type, Boolean isLogin);


    /**
     * 获取流量
     *
     * @param mobile
     * @param exchangeID 要兑换的 id， 1 为 5m 流量
     * @return
     */
    public String exchangePrize(String mobile, String exchangeID, String type, String password);

//    public void autoPlay(String mobile);

    /**
     * 转赠
     *
     * @param realMobile    自己的手机号（存在 cookie中的）
     * @param paramMobile   参数
     * @param password
     * @param smsContext
     * @param transferGifts
     * @return
     */
    public String transferGifts(String realMobile, String paramMobile, String password, String smsContext, String transferGifts);

    /**
     * 获得未领流量币信息
     */
    public String getTransferGiftsList(String cookieMobile, String queryType, String type, String status);

    /**
     * 领取流量币
     *
     * @param cookieMobile
     * @param paramId      领取id，多个id用逗号隔开
     */
    public String transferGiftsReceive(String cookieMobile, String paramId);

    /**
     * 获取流量套餐详细信息
     *
     * @return
     */
    public String getPackage(String cookieMobile);

    public String loadLoginMobile(String mobile);

    /**
     * 设置朋友摇奖帐号
     * 重复使用会覆盖之前设置的朋友帐号
     * 摇奖完毕后，要再次使用该方法设置自己成的手机号
     * @param cookieMobile 要使用该服务的手机号
     * @param friendMobile 朋友的手机号
     * @return 流量汇返回值
     */
    public String setDrawMobile(String cookieMobile, String friendMobile);

    /**
     * 设置流量汇提醒
     * @return
     */
    public String smsNoticeSet(String cookieMobile, String type, String value);


    /**
     * 流量汇各类提醒状态
     * @return
     */
    public String smsNoticeSetQuery(String cookieMobile);

    /**
     * 校验昵称是否存在
     */
    public String ifExistNickName(String cookieMobile, String nickName);

    /**
     * 修改昵称
     */
    public String changeNickName(String cookieMobile, String nickName);

    public String refreshNickName(String cookieMobile);
}
