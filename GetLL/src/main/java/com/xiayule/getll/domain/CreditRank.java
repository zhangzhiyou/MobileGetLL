package com.xiayule.getll.domain;

import com.xiayule.getll.db.model.CreditLog;

/**
 * Created by tan on 14-12-7.
 */
public class CreditRank {
    private Integer rank;
    private String mobile;
    private Double credit;

    public CreditRank(Integer rank, String mobile, Double credit) {
        this.rank = rank;
        this.mobile = mobile;
        this.credit = credit;
    }

    public CreditRank(String mobile, double credit) {
        this.mobile = mobile;
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "rank:" + rank + " mobile:" + mobile + " credit:" + credit;
    }


    // set and get methods


    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }
}
