package com.xiayule.getll.db.model;

import java.util.Date;

/**
 * 摇奖获得流量币记录
 * Created by tan on 14-12-3.
 */
public class ShakeLog {

    /**
     * 摇奖获得的流量币
     */
    public static final Integer LOG_SHAKE = 0;

    /**
     * 每日登录获得的流量币
     */
    public static final Integer LOG_LOGIN = 1;

    private Integer id;

    private String mobile;

    private Double credit;

    private Date time;

    private Integer type;

    public ShakeLog() {}

    public ShakeLog(String mobile, Double credit, Integer type) {
        this.mobile = mobile;
        this.credit = credit;
        this.type = type;
        this.time = new Date();
    }

    @Override
    public String toString() {
        return "id:" + id + " mobile:" + mobile + " credit:" + credit
                + " type:" + type + " time:" + time;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
