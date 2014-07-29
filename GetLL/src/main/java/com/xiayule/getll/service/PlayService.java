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

    public void autoPlay(String mobile);
}
