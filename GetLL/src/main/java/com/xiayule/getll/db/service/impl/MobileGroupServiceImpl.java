package com.xiayule.getll.db.service.impl;

import com.xiayule.getll.db.dao.MobileGroupDao;
import com.xiayule.getll.db.model.MobileGroup;
import com.xiayule.getll.db.service.MobileGroupService;

import java.util.List;

/**
 * Created by tan on 15-3-31.
 */
public class MobileGroupServiceImpl implements MobileGroupService {
    private MobileGroupDao mobileGroupDao;

    public MobileGroup get(String mobile) {
        return mobileGroupDao.get(mobile);
    }

    @Override
    public List<String> getGroup(String mobile) {
        return mobileGroupDao.getGroup(mobile);
    }

    @Override
    public void save(MobileGroup mobileGroup) {
        mobileGroupDao.save(mobileGroup);
    }


    @Override
    public void delete(String mobile) {
        mobileGroupDao.delete(mobile);
    }

    @Override
    public Boolean exist(String mobile) {
        return mobileGroupDao.exist(mobile);
    }


    public void setMobileGroupDao(MobileGroupDao mobileGroupDao) {
        this.mobileGroupDao = mobileGroupDao;
    }
}
