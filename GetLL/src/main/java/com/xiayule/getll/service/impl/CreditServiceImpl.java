package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.CreditService;
import com.xiayule.getll.service.RedisService;
import com.xiayule.getll.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by tan on 14-7-24.
 * 流量币 计数
 */
@Component("creditService")
public class CreditServiceImpl implements CreditService {
    @Autowired
    private RedisService redisService;

    /**
     * 增加流量币
     * @param mobile
     * @param credit
     */
    public void addCredit(String mobile, double credit) {
        redisService.zincrby("score", credit, mobile);
    }


    public Long getRankByTotalAtHere(String mobile) {
        return redisService.zrevrank("score", mobile)+1;
    }

    public double getCredit(String mobile) {
        return redisService.zscore("score", mobile);
    }

    /**
     * 设置 date 日期获得的流量总数
     * @param mobile 摇奖的手机号
     * @param date 摇奖日期, date存储格式为 2014-07-27
     * @param credit 流量币数量
     */
    public void setDayCredit(String mobile, String date, double credit) {
        redisService.setex("dayCredit_" + mobile + "_" + date,
                TimeUtils.getMaxValidTimeWithSecond() + 500,//设置过期时间为1周+200秒
                String.valueOf(credit));
    }

    /**
     * 设置 date 日期获得的流量总数, 日期默认为当天
     * @param mobile 摇奖的手机号
     * @param credit 流量币数量
     */
    public void setDayCredit(String mobile, double credit) {
        String date = TimeUtils.getTodayDate();

        setDayCredit(mobile, date, credit);
    }

    /**
     * 获取指定日期的 每日记录
     * @param mobile 摇奖的手机号
     * @param date 指定日期的流量币获得数，格式为 2014-07-27
     * @return
     */
    public Double getDayCredit(String mobile, String date) {
        return Double.parseDouble(redisService.get("dayCredit_" + mobile + "_" + date));
    }

    /**
     * 获取指定的 每日记录
     * @param key 格式为 dayCredit_18369905136_2014-07-27
     * @return
     */
    public Double getDayCredit(String key) {
        return Double.parseDouble(redisService.get(key));
    }

    /**
     * 获取存储的 每日记录 所有日期
     * 结果保证时间按照 新 -> 旧 排序
     * @param mobile 摇奖的手机号
     * @return
     */
    public String[] getDaysOfCredit(String mobile) {
        List<String> days = new ArrayList<String>();

        // 获得所有记录
        Set<String> sdays = redisService.keys("dayCredit_" + mobile + "_" + "*");

        // 将 set 转换为 array
        String[] arrDays = new String[sdays.size()];
        sdays.toArray(arrDays);

        // 排序
        Arrays.sort(arrDays, Collections.reverseOrder());

        return arrDays;
    }

    // get and set methods

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
