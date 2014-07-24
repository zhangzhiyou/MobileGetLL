package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.CreditService;
import com.xiayule.getll.service.RedisService;

import java.nio.LongBuffer;

/**
 * Created by tan on 14-7-24.
 * 流量币 计数
 */
public class CreditServiceImpl implements CreditService {
    private RedisService redisService;

    /**
     * 增加流量币
     * @param mobile
     * @param credit
     */
    public void addCredit(String mobile, double credit) {
        redisService.zincrby("score", credit, mobile);
    }


    public Long getRank(String mobile) {
        return redisService.zrevrank("score", mobile)+1;
    }

    public double getCredit(String mobile) {
        return redisService.zscore("score", mobile);
    }

    // get and set methods

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
