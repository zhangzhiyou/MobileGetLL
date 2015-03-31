package com.xiayule.getll.db.service;

import com.xiayule.getll.db.model.MobileGroup;

import java.util.List;

/**
 * Created by tan on 15-3-31.
 */
public interface MobileGroupService {
    public MobileGroup get(String mobile);

    public List<String> getGroup(String mobile);

    public void save(MobileGroup mobileGroup);

    public void delete(String mobile);

    public Boolean exist(String mobile);
}
