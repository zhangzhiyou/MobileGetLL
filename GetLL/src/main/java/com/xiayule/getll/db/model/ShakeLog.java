package com.xiayule.getll.db.model;

import java.util.Date;

/**
 * 摇奖获得流量币记录
 * Created by tan on 14-12-3.
 */
public class ShakeLog {
    private Integer id;

    private String mobile;

    private Double credit;

    private Date time;

    public ShakeLog() {}

    public ShakeLog(String mobile, Double credit) {
        this.mobile = mobile;
        this.credit = credit;
        this.time = new Date();
    }

    @Override
    public String toString() {
        return "id:" + id + " mobile:" + mobile + " credit:" + credit + " time:" + time;
    }

    // set and get methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
