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
    public String loginDo(String mobile);
    public String draw(String mobile);
    public double addDrawScore(String mobile);
    public boolean isLogined(String mobile);
    public JSONObject queryScore(String mobile);
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

    public void autoPlay(String mobile);
}
