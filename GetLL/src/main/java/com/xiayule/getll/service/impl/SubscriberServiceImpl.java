package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.RedisService;
import com.xiayule.getll.service.RegisterCodeService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by tan on 14-7-22.
 */
public class SubscriberServiceImpl implements SubscriberService {
    private RedisService redisService;
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
            redisService.setex("sub_" + mobile, TimeUtils.getSecondOfOneWeek(), registerCode);
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
     * 获取注册码有效期剩余时间
     * @param mobile 要获取的手机号
     * @return 剩余时间，单位为 秒
     * 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     */
    public Long getTTL(String mobile) {
        return redisService.ttl("sub_" + mobile);
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
}
