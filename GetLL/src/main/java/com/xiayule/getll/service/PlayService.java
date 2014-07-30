package com.xiayule.getll.service;

import net.sf.json.JSONObject;

/**
 * Created by tan on 14-7-21.
 */
public interface PlayService {
    public String getPassword();
    public int getRemainTimes();
    public String loginDo();
    public String draw();
    public double addDrawScore();
    public boolean isLogined();
    public void setMobile(String mobile);
    public JSONObject queryScore();
    /**
     * 同 queryScore， 但是返回的是官方返回的 string
     * @return
     */
    public String queryScoreWithSource();

    /**
     * 加载收支总和信息, 返回原json信息
     * @return
     */
    public String queryCreditSum();

    /**
     * 加载收支明细
     * @return
     */
    public String queryCreditDetail();

    public void autoPlay(String mobile);
}
