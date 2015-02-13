package com.xiayule.getll.db.model;

import com.xiayule.getll.utils.Constants;

import java.util.Calendar;
import java.util.Date;

/**
 * 用户 Model
 * Created by tan on 14-10-26.
 */
public class MobileAccount {
    private Integer id;

    private String mobile;

    private String nick;

    private String avatar;

    private String email;

    /**
     * 截止日期
     * 形式为 2015-01-01 这种形式
     */
    private Calendar endTime;

    public MobileAccount() {
        updateEndTime();
    }

    /**
     * 续期
     */
    public void updateEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, Constants.TTL_VALID_DAY);
        endTime = calendar;
    }

    public boolean valid() {
        Calendar c = Calendar.getInstance();
        if (c.after(endTime)) {
            return false;
        } else {
            return true;
        }
    }

    // set and get methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
