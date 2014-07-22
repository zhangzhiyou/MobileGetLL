package com.xiayule.getll.service;

import java.util.List;

/**
 * Created by tan on 14-7-22.
 */
public interface SubscriberService {
    /**
     * 某个 手机号 是否已经订购服务
     * @param mobile
     * @return
     */
    public boolean isSubscribe(String mobile);

    /**
     * 订阅服务
     * @param mobile 要订阅的手机号
     * @param serial 使用的序列号
     * @return 序列号合法、成功订阅返回真
     */
    public boolean subscribe(String mobile, String serial);

    public void unSubscribe(String mobile);

    // get and set methods

    public void setRedisService(RedisService redisService);

    public List<String> getAllSubscriber();
}
