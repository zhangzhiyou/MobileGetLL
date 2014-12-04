package com.xiayule.getll.db.service;

/**
 * Created by tan on 14-12-3.
 */
public interface ShakeLogService {
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
}
