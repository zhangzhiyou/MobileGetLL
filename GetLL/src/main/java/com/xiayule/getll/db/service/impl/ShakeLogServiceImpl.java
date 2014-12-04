package com.xiayule.getll.db.service.impl;

import com.xiayule.getll.db.dao.ShakeLogDao;
import com.xiayule.getll.db.model.ShakeLog;
import com.xiayule.getll.db.service.ShakeLogService;

/**
 * Created by tan on 14-12-3.
 */
public class ShakeLogServiceImpl implements ShakeLogService {
    private ShakeLogDao shakeLogDao;

    /**
     * 记录日志
     * @param mobile 要记录的手机号
     * @param credit 摇到的手机号
     */
    public void log(String mobile, double credit) {
        ShakeLog shakeLog = new ShakeLog(mobile, credit);
        shakeLogDao.saveOrUpdate(shakeLog);
    }

    // set and get methods

    public ShakeLogDao getShakeLogDao() {
        return shakeLogDao;
    }

    public void setShakeLogDao(ShakeLogDao shakeLogDao) {
        this.shakeLogDao = shakeLogDao;
    }
}
