package com.xiayule.getll.db.service;

import com.xiayule.getll.db.model.MobileGroup;

import java.util.List;

/**
 * Created by tan on 15-3-31.
 */
public interface MobileGroupService {
    public MobileGroup get(String mobile);

    /**
     * 获得 mobile 所在组的其他手机号, 不包括要查询的手机号
     */
    public List<String> getGroup(String mobile);

    public void save(MobileGroup mobileGroup);

    public void delete(String mobile);

    public Boolean exist(String mobile);

    /**
     * 将两个手机号放入相同的组
     */
    public void addToGroup(String m1, String m2);
}
