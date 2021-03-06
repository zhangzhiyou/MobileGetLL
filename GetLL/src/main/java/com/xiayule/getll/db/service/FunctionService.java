package com.xiayule.getll.db.service;

import com.xiayule.getll.db.model.Function;

import java.util.List;

/**
 * Created by tan on 15-2-10.
 */
public interface FunctionService {
    public Function getByMobile(String mobile);

    public Boolean statusForFriend(String mobile);

    public Boolean statusAutoReceive(String mobile);

    public void setForFriend(String mobile, Boolean forFriend);

    public void setAutoReceive(String mobile, Boolean autoReceive);

    public void save(Function function);

    public List<String> getAllForFriend();

    public List<String> getAllAutoReceive();

    /**
     * 获得所有有效的订阅朋友摇奖的
     * @return
     */
    public List<String> getAlValidlForFriend();

    /**
     * 获得所有有效的订阅自动领取的摇奖的
     */
    public List<String> getAllValidAutoReceive();
}
