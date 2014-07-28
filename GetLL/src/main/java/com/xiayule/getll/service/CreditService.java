package com.xiayule.getll.service;

/**
 * Created by tan on 14-7-24.
 */
public interface CreditService {
    public void addCredit(String mobile, double credit);

    public Long getRankByTotalAtHere(String mobile);

    public double getCredit(String mobile);

    /**
     * 设置 date 日期获得的流量总数
     * @param mobile 摇奖的手机号
     * @param date 摇奖日期, date存储格式为 2014-07-27
     * @param credit 流量币数量
     */
    public void setDayCredit(String mobile, String date, double credit);

    /**
     * 设置 date 日期获得的流量总数, 日期默认为当天
     * @param mobile 摇奖的手机号
     * @param credit 流量币数量
     */
    public void setDayCredit(String mobile, double credit);

    /**
     * 获取指定日期的 每日记录
     * @param mobile 摇奖的手机号
     * @param date 指定日期的流量币获得数，格式为 2014-07-27
     * @return
     */
    public Double getDayCredit(String mobile, String date);

    /**
     * 获取指定的 每日记录
     * @param key 格式为 dayCredit_18369905136_2014-07-27
     * @return
     */
    public Double getDayCredit(String key);

    /**
     * 获取存储的 每日记录 所有日期
     * 结果保证时间按照 新 -> 旧 排序
     * @param mobile 摇奖的手机号
     * @return
     */
    public String[] getDaysOfCredit(String mobile);
}
