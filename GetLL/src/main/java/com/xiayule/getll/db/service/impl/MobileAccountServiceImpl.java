package com.xiayule.getll.db.service.impl;

import com.xiayule.getll.db.dao.MobileAccountDao;
import com.xiayule.getll.db.model.MobileAccount;
import com.xiayule.getll.db.service.MobileAccountService;

import java.util.List;

/**
 * Created by tan on 15-2-11.
 */
public class MobileAccountServiceImpl implements MobileAccountService {
    private MobileAccountDao mobileAccountDao;

    @Override
    public void save(MobileAccount mobileAccount) {
        mobileAccountDao.save(mobileAccount);
    }

    @Override
    public void update(MobileAccount mobileAccount) {
        mobileAccountDao.update(mobileAccount);
    }

    @Override
    public MobileAccount getByMobile(String mobile) {
        return mobileAccountDao.getByMobile(mobile);
    }

    @Override
    public void updateEndTime(String mobile) {
        MobileAccount mobileAccount = getByMobile(mobile);
        if (mobileAccount == null) {
            return;
        } else {
            mobileAccount.updateEndTime();
            update(mobileAccount);
        }
    }

    /**
     * 获得所有的有效用户
     */
    public List<String> getAllValid() {
        return mobileAccountDao.getAllValid();
    }

    /**
     * 统计当前有效用户的数量
     * @return
     */
    public Long countValid() {
        return mobileAccountDao.countValid();
    }

    public void delete(String mobile) {
        mobileAccountDao.delete(mobile);
    }

    // set and get methods


    public MobileAccountDao getMobileAccountDao() {
        return mobileAccountDao;
    }

    public void setMobileAccountDao(MobileAccountDao mobileAccountDao) {
        this.mobileAccountDao = mobileAccountDao;
    }
}
