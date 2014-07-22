package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.RedisService;
import com.xiayule.getll.service.SubscriberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by tan on 14-7-22.
 */
public class SubscriberServiceImpl implements SubscriberService {
    private RedisService redisService;

    /**
     * 某个 手机号 是否已经订购服务
     * @param mobile
     * @return
     */
    public boolean isSubscribe(String mobile) {
        return redisService.exists("sub_" + mobile);
    }

    /**
     * 订阅服务
     * @param mobile 要订阅的手机号
     * @param serial 使用的序列号
     * @return
     */
    public boolean subscribe(String mobile, String serial) {
        // 检测序列号是否合法
        if (SerialNumberManager.isValid(serial)) {
            redisService.set("sub_" + mobile, serial);

            // TODO:删除已经使用的序列号

            return true;
        }

        return false;
    }

    public void unSubscribe(String mobile) {
        redisService.del("sub_" + mobile);
    }

    public List<String> getAllSubscriber() {
        Set<String> subs = redisService.keys("sub_*");

        List<String> subList = new ArrayList<String>();

        for (String e : subs) {
            subList.add(e.replace("sub_", ""));
        }

        return subList;
    }

    // get and set methods

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
