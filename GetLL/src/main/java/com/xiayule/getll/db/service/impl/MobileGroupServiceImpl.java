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

    /**
     * 将两个手机号放入相同的组
     */
    public void addToGroup(String m1, String m2) {
        MobileGroup mobileGroup1 = get(m1);
        MobileGroup mobileGroup2 = get(m2);

        // 如果都已经有组了，则把2放入1内
        if (mobileGroup1 != null && mobileGroup2 != null) {
            delete(m2);
            mobileGroup2 = null;
        }  // 如果都没有组，先把1放入一个组内，再把2放入1组内
        else if (mobileGroup1 == null && mobileGroup2 == null) {
            mobileGroup1 = new MobileGroup(m1);
            save(mobileGroup1);
        }

        if (mobileGroup1 != null && mobileGroup2 == null) {
            String index = mobileGroup1.getGroupIndex();
            mobileGroup2 = new MobileGroup(m2, index);
            save(mobileGroup2);
        }
        else if (mobileGroup1 == null && mobileGroup2 != null) {
            String index = mobileGroup2.getGroupIndex();
            mobileGroup1 = new MobileGroup(m1, index);
            save(mobileGroup1);
        }
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
