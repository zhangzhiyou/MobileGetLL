package com.xiayule.getll.service;

/**
 * Created by tan on 14-7-24.
 */
public interface CreditService {
    public void addCredit(String mobile, double credit);

    public Long getRank(String mobile);

    public double getCredit(String mobile);
}
