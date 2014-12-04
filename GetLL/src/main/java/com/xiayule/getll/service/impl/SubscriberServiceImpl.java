package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.RedisService;
import com.xiayule.getll.service.RegisterCodeService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.utils.Constants;
import com.xiayule.getll.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by tan on 14-7-22.
 */
@Component
public class SubscriberServiceImpl implements SubscriberService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private RegisterCodeService registerCodeService;

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
     * @param registerCode 使用的序列号
     * @return
     */
    public boolean subscribe(String mobile, String registerCode) {
        // 检测序列号是否合法
        if (registerCodeService.isValid(registerCode)) {
            // 首先注册
            redisService.setex("sub_" + mobile, TimeUtils.getMaxValidTimeWithSecond(), registerCode);
            // 然后将序列号删除
            registerCodeService.removeRegisterCode(registerCode);

            return true;
        }

        return false;
    }

    public void unSubscribe(String mobile) {
        redisService.del("sub_" + mobile);
    }

    /**
     * 注册为朋友摇奖
     */
    public void subForFriend(String mobile) {
        redisService.sadd("forFriend", mobile);
    }

    /**
     * 取消订阅为朋友摇奖
     */
    public void unsubForFriend(String mobile) {
        redisService.srem("forFriend", mobile);
    }

    /**
     * 是否订阅了为朋友摇奖
     * @return
     */
    public Boolean isSubForFriend(String mobile) {
        return redisService.sismember("forFriend", mobile);
    }

    public void subAutoReceiveGifts(String mobile) {
        redisService.sadd("autoReceive", mobile);
    }

    public void unsubAutoReceiveGifts(String mobile) {
        redisService.srem("autoReceive", mobile);
    }

    public Boolean isSubAutoReceiveGifts(String mobile) {
        return redisService.sismember("autoReceive", mobile);
    }

    /**
     * 获得所有的订阅为朋友摇奖的服务
     * @return
     */
    public Set<String> getAllSubscriberForFriend() {
        return redisService.smembers("forFriend");
    }

    /**
     * 获得所有订阅自动领取流量币的用户
     * @return
     */
    public Set<String> getAllSubscriberAutoReceive() {
        return redisService.smembers("autoReceive");
    }


    /**
     * 获取注册码有效期剩余时间
     * @param mobile 要获取的手机号
     * @return 剩余时间，单位为 秒
     * 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     */
    public Long getTTL(String mobile) {
        return redisService.ttl("sub_" + mobile);
    }

    /**
     * 获取有效期对应的天数
     * 如果少于规定的天数,则自动在后面添加文字(点我续期)
     */
    public String getTTLDays(String m) {
        // 如果没有订阅，或者到期，会被 struts 拦截，因此不用考虑到期的情况
        Long remainSeconds = getTTL(m);

        // 将秒数转换为天
        Long days = remainSeconds / 60 / 60 / 24 + 1;

        String strDays = null;

        if (days <= Constants.TTL_XUQI_DAY) strDays = days + "天(点我续期)";
        else strDays = days + "天";

        return strDays;
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

    public void setRegisterCodeService(RegisterCodeService registerCodeService) {
        this.registerCodeService = registerCodeService;
    }

    /**
     * 返回redis 中保存的 订阅者的数量
     * @return
     */
    public int countNumbers() {
        // todo： 这样会很耗费时间，建议修改方法
        Set<String> numbers = redisService.keys("sub_*");
        return numbers.size();
    }
}
