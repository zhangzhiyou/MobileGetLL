package com.xiayule.getll.db.dao;

import com.xiayule.getll.db.model.MobileGroup;

import java.util.List;

/**
 * Created by tan on 15-3-31.
 */
public interface MobileGroupDao {

    public MobileGroup get(String mobile);

    public List<String> getGroup(String mobile);

    public void save(MobileGroup mobileGroup);

    /**
     * 将mobile从所在组删除
     */
    public void delete(String mobile);

    /**
     * 查看是否存在
     */
    public Boolean exist(String mobile);
}
