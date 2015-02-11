package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.RedisService;
import com.xiayule.getll.service.RegisterCodeService;
import com.xiayule.getll.utils.RegisterCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tan on 14-7-29.
 */
//@Component
public class RegisterCodeServiceImpl implements RegisterCodeService {
    private int EXPIRE_TIME = 15*24*60*60;// 15天的秒数

//    @Autowired
    private RedisService redisService;

    /**
     * 生成注册码，将注册码存储如 redis，并将生成的注册码返回
     * @return
     */
    public String generateRegisterCode() {
        String registerCode = RegisterCodeUtils.generateRegisterCode();

        // 将生成的注册码存放在 redis
        redisService.sadd("registerCodes", registerCode);
        return registerCode;
    }

    /**
     * 检查注册号是否合法
     * @param registerCode
     * @return
     */
    public boolean isValid(String registerCode) {
        return redisService.sismember("registerCodes", registerCode);
    }

    /**
     * 删除指定注册号
     * @param registerCode
     */
    public void removeRegisterCode(String registerCode) {
        redisService.srem("registerCodes", registerCode);
    }

    // set and get methos

//    public void setRedisService(RedisService redisService) {
//        this.redisService = redisService;
//    }
}
