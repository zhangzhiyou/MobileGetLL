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
     * 记录流量币日志
     * @param mobile 要记录的手机号
     * @param credit 摇到的手机号
     * @param type 记录的类型
     * @see com.xiayule.getll.db.model.ShakeLog
     */
    public void log(String mobile, Double credit, Integer type) {
        ShakeLog shakeLog = new ShakeLog(mobile, credit, type);
        shakeLogDao.saveOrUpdate(shakeLog);
    }

    /**
     *
     * 记录登录获取流量币记录
     * @param mobile 手机号
     * @param credit 获得的流量币
     */
    public void logLoginCredit(String mobile, Double credit) {
        log(mobile, credit, ShakeLog.LOG_LOGIN);
    }

    /**
     * 记录摇奖获取流量币
     * @param mobile 手机号
     * @param credit 获得的流量币
     */
    public void logShakeCredit(String mobile, Double credit) {
        log(mobile, credit, ShakeLog.LOG_SHAKE);
    }

    // set and get methods

    public ShakeLogDao getShakeLogDao() {
        return shakeLogDao;
    }

    public void setShakeLogDao(ShakeLogDao shakeLogDao) {
        this.shakeLogDao = shakeLogDao;
    }
}
