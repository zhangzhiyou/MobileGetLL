package com.xiayule.getll.db.service.impl;

import com.xiayule.getll.db.dao.CreditLogDao;
import com.xiayule.getll.db.model.CreditLog;
import com.xiayule.getll.db.service.CreditLogService;

/**
 * Created by tan on 14-12-3.
 */
public class CreditLogServiceImpl implements CreditLogService {
    private CreditLogDao creditLogDao;

    /**
     * 记录流量币日志
     * @param mobile 要记录的手机号
     * @param credit 摇到的手机号
     * @param type 记录的类型
     * @see com.xiayule.getll.db.model.CreditLog
     */
    public void log(String mobile, Double credit, Integer type) {
        CreditLog creditLog = new CreditLog(mobile, credit, type);
        creditLogDao.saveOrUpdate(creditLog);
    }

    /**
     *
     * 记录登录获取流量币记录
     * @param mobile 手机号
     * @param credit 获得的流量币
     */
    public void logLoginCredit(String mobile, Double credit) {
        log(mobile, credit, CreditLog.LOG_LOGIN);
    }

    /**
     * 记录摇奖获取流量币
     * @param mobile 手机号
     * @param credit 获得的流量币
     */
    public void logShakeCredit(String mobile, Double credit) {
        log(mobile, credit, CreditLog.LOG_SHAKE);
    }

    // set and get methods

    public CreditLogDao getCreditLogDao() {
        return creditLogDao;
    }

    public void setCreditLogDao(CreditLogDao creditLogDao) {
        this.creditLogDao = creditLogDao;
    }
}
