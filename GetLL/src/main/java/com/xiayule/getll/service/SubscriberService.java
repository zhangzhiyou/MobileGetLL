package com.xiayule.getll.service;

import java.util.List;
import java.util.Set;

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

    /**
     * 获取注册码有效期剩余时间
     * @param mobile 要获取的手机号
     * @return 剩余时间，单位为 秒
     * 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     */
    public Long getTTL(String mobile);

    // get and set methods

    public void setRedisService(RedisService redisService);

    public List<String> getAllSubscriber();

    /**
     * 返回redis 中保存的 订阅者的数量
     */
    public int countNumbers();

    /**
     * 注册为朋友摇奖
     */
    public void subForFriend(String mobile);

    /**
     * 取消订阅为朋友摇奖
     */
    public void unsubForFriend(String mobile);

    /**
     * 是否订阅了为朋友摇奖
     * @return
     */
    public Boolean isSubForFriend(String mobile);

    /**
     * 获得所有的订阅为朋友摇奖的服务
     * @return
     */
    public Set<String> getAllSubscriberForFriend();

}
