package com.xiayule.getll.db.model;

import java.util.Date;

/**
 * 流量币的使用记录
 * 只包含兑换流量的记录
 * Created by tan on 14-11-25.
 */
public class FlowMoneyCost {
    /**
     * id
     */
    private Integer id;

    /**
     * 花费人
     */
    private String mobile;

    /**
     * 话费金额
     */
    private Double cost;

    /**
     * 话费时间
     */
    private Date time;

    /**
     * todo: 消费的种类(待完善):
     * 1 为兑换流量
     */
    private Date type;

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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getType() {
        return type;
    }

    public void setType(Date type) {
        this.type = type;
    }
}
