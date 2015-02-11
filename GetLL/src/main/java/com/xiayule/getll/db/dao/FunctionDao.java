package com.xiayule.getll.db.dao;

import com.xiayule.getll.db.model.Function;

import java.util.List;

/**
 * Created by tan on 15-2-10.
 */
public interface FunctionDao {
    public Function getByMobile(String mobile);

    public void save(Function function);

    public void updateAutoReceive(String mobile, Boolean autoReceive);

    public void updateForFriend(String mobile, Boolean forFriend);

    public List<String> getAllForFriend();

    public List<String> getAllAutoReceive();

    /**
     * 获得所有有效的订阅朋友摇奖的
     * @return
     */
    public List<String> getAllValidlForFriend();

    /**
     * 获得所有有效的订阅自动领取的摇奖的
     */
    public List<String> getAllValidAutoReceive();
}
