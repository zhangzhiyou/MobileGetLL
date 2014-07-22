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
    public String addDrawScore();
    public boolean isLogined();
    public void setMobile(String mobile);
    public JSONObject queryScore();
}
