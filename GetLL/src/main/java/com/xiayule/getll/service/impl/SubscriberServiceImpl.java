package com.xiayule.getll.service.impl;

import com.xiayule.getll.db.model.Function;
import com.xiayule.getll.db.model.MobileAccount;
import com.xiayule.getll.db.service.FunctionService;
import com.xiayule.getll.db.service.MobileAccountService;
import com.xiayule.getll.service.RedisService;
import com.xiayule.getll.service.RegisterCodeService;
import com.xiayule.getll.service.SubscriberService;
import com.xiayule.getll.utils.Constants;
import com.xiayule.getll.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by tan on 14-7-22.
 */
@Component
public class SubscriberServiceImpl implements SubscriberService {
//    @Autowired
//    private RedisService redisService;

    @Autowired
    private MobileAccountService mobileAccountService;

//    @Autowired
//    private RegisterCodeService registerCodeService;

    @Autowired
    private FunctionService functionService;

    /**
     * 某个 手机号 是否已经订购服务
     * @param mobile
     * @return
     */
    public boolean isSubscribe(String mobile) {
//        return redisService.exists("sub_" + mobile);
        MobileAccount mobileAccount = mobileAccountService.getByMobile(mobile);

        if (mobileAccount == null) {
            return false;
        } else {
            return mobileAccount.valid();
        }
    }

    /**
     * 订阅服务
     * @param mobile 要订阅的手机号
     * @return
     */
    public void subscribe(String mobile) {

        MobileAccount mobileAccount = mobileAccountService.getByMobile(mobile);

        if (mobileAccount == null) {// 新用户
            mobileAccount = new MobileAccount();
            mobileAccount.setMobile(mobile);

            mobileAccountService.save(mobileAccount);

            //如果以前没有设置过使用过本站服务
            Function function = functionService.getByMobile(mobile);

            if (function == null) {
                // 初始化该用户的设置
                function = new Function(mobile);
                functionService.save(function);
            }

        } else { // 过期用户
            mobileAccount.updateEndTime();
            mobileAccountService.update(mobileAccount);
        }

        /*// 检测序列号是否合法
        if (registerCodeService.isValid(registerCode)) {
            // 首先注册
            redisService.setex("sub_" + mobile, TimeUtils.getMaxValidTimeWithSecond(), registerCode);
            // 然后将序列号删除
            registerCodeService.removeRegisterCode(registerCode);

            //如果以前没有设置过使用过本站服务
            Function function = functionService.getByMobile(mobile);

            if (function == null) {
                // 初始化该用户的设置
                function = new Function(mobile);
                functionService.save(function);
            }

            return true;
        }

        return false;*/
    }

    public void unSubscribe(String mobile) {
//        redisService.del("sub_" + mobile);

    }

    /**
     * 注册为朋友摇奖
     */
    public void subForFriend(String mobile) {
//        redisService.sadd("forFriend", mobile);
        functionService.setForFriend(mobile, true);
    }

    /**
     * 取消订阅为朋友摇奖
     */
    public void unsubForFriend(String mobile) {
//        redisService.srem("forFriend", mobile);
        functionService.setForFriend(mobile, false);
    }

    /**
     * 是否订阅了为朋友摇奖
     * @return
     */
    public Boolean isSubForFriend(String mobile) {

//        return redisService.sismember("forFriend", mobile);
        return functionService.statusForFriend(mobile);
    }

    public void subAutoReceiveGifts(String mobile) {
//        redisService.sadd("autoReceive", mobile);
        functionService.setAutoReceive(mobile, true);
    }

    public void unsubAutoReceiveGifts(String mobile) {
//        redisService.srem("autoReceive", mobile);
        functionService.setAutoReceive(mobile, false);
    }

    public Boolean isSubAutoReceiveGifts(String mobile) {
//        return redisService.sismember("autoReceive", mobile);
        return functionService.statusAutoReceive(mobile);
    }

    /**
     * 获得所有的订阅为朋友摇奖的服务
     * @return
     */
    public List<String> getAllSubscriberForFriend() {
//        return redisService.smembers("forFriend");
        return functionService.getAllForFriend();
    }

    /**
     * 获得所有订阅自动领取流量币的用户
     * @return
     */
    public List<String> getAllSubscriberAutoReceive() {
//        return redisService.smembers("autoReceive");
        return functionService.getAllAutoReceive();
    }


    /**
     * 获取注册码有效期剩余时间
     * @param mobile 要获取的手机号
     * @return 剩余时间，单位为 秒
     * 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     */
    public Long getTTL(String mobile) {
//        return redisService.ttl("sub_" + mobile);
        MobileAccount mobileAccount = mobileAccountService.getByMobile(mobile);

        Calendar endTime = mobileAccount.getEndTime();
        Calendar nowTime = Calendar.getInstance();

        return (endTime.getTimeInMillis() - nowTime.getTimeInMillis()) / 1000;
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
//        Set<String> subs = redisService.keys("sub_*");
//
//        List<String> subList = new ArrayList<String>();
//
//        for (String e : subs) {
//            subList.add(e.replace("sub_", ""));
//        }

//        return subList;
        return null;
    }

    // get and set methods

//    public void setRedisService(RedisService redisService) {
//        this.redisService = redisService;
//    }

//    public void setRegisterCodeService(RegisterCodeService registerCodeService) {
//        this.registerCodeService = registerCodeService;
//    }

    /**
     * 返回中保存的 订阅者的数量
     * @return
     */
    public Long countNumbers() {
        // todo： 这样会很耗费时间，建议修改方法
//        Set<String> numbers = redisService.keys("sub_*");
//        return numbers.size();


        return mobileAccountService.countValid();
    }
}
