package com.xiayule.getll.db.service.impl;

import com.xiayule.getll.db.dao.CreditLogDao;
import com.xiayule.getll.db.model.CreditLog;
import com.xiayule.getll.db.service.CreditLogService;
import com.xiayule.getll.domain.CreditRank;

import java.util.Calendar;

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

    /**
     * 记录领取获得的流量币
     * @param mobile 手机号
     * @param credit 获得的流量币
     */
    public void logReceiveCredit(String mobile, Double credit) {
        log(mobile, credit, CreditLog.LOG_RECEIVE);
    }

    /**
     * 获得昨日排行第一名记录
     * @return
     */
    public CreditRank queryYestodayFirstRank() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);

        return creditLogDao.queryRank(c);
    }

    /**
     * 获得昨日摇奖人数
     * @return
     */
    public Integer queryYesterdayMobileCount() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);

        return creditLogDao.queryLogMobileCount(c);
    }

    /**
     * 获得今日摇奖人数
     * @return
     */
    public Integer queryMobileCount() {
        Calendar c = Calendar.getInstance();

        return creditLogDao.queryLogMobileCount(c);
    }


    /**
     * 获得昨日具体手机号的排名
     * @param mobile
     * @return
     */
    public CreditRank queryYestoDayRank(String mobile) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);

        return creditLogDao.queryRank(mobile, c);
    }

    // set and get methods

    public CreditLogDao getCreditLogDao() {
        return creditLogDao;
    }

    public void setCreditLogDao(CreditLogDao creditLogDao) {
        this.creditLogDao = creditLogDao;
    }
}
