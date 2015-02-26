package com.xiayule.getll.db.service;

import com.xiayule.getll.domain.CreditRank;

/**
 * Created by tan on 14-12-3.
 */
public interface CreditLogService {
    /**
     * 记录日志
     * @param mobile 要记录的手机号
     * @param credit 摇到的手机号
     */
    public void log(String mobile, Double credit, Integer type);

    /**
     *
     * 记录登录获取流量币记录
     * @param mobile 手机号
     * @param credit 获得的流量币
     */
    public void logLoginCredit(String mobile, Double credit);

    /**
     * 记录摇奖获取流量币
     * @param mobile 手机号
     * @param credit 获得的流量币
     */
    public void logShakeCredit(String mobile, Double credit);

    /**
     * 记录领取获得的流量币
     * @param mobile 手机号
     * @param credit 获得的流量币
     */
    public void logReceiveCredit(String mobile, Double credit);

    /**
     * 获得昨日排行第一名记录
     * @return
     */
    public CreditRank queryYestodayFirstRank();

    /**
     * 获得今日摇奖人数
     * @return
     */
    public Integer queryMobileCount();

    /**
     * 获得昨日具体手机号的排名
     * @param mobile
     * @return
     */
    public CreditRank queryYestoDayRank(String mobile);

    /**
     * 获得昨日摇奖人数
     * @return
     */
    public Integer queryYesterdayMobileCount();
}
