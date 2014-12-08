package com.xiayule.getll.db.dao;

import com.xiayule.getll.db.model.CreditLog;
import com.xiayule.getll.domain.CreditRank;

import java.util.Calendar;
import java.util.List;

/**
 * Created by tan on 14-12-3.
 */
public interface CreditLogDao {
    public CreditLog get(Integer id);

    public void saveOrUpdate(CreditLog creditLog);

    public void update(CreditLog creditLog);

    public void delete(Integer id);

    public void delete(CreditLog creditLog);

    /**
     * 获得某日排行第一名信息
     * @param year 记录的年
     * @param month 记录的月
     * @param day 记录的日
     * @return 排行信息, 如果没有找到，返回null
     */
    public CreditRank queryRank(int year, int month, int day);

    /**
     * 获得某日排行第一名信息
     * @param calendar 记录的日期
     * @return 排行信息, 如果没有找到，返回null
     */
    public CreditRank queryRank(Calendar calendar);

    /**
     * 查找手机号某日的排行
     * @param mobile 手机号
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 排行信息, 如果没有找到，返回null
     * @see com.xiayule.getll.domain.CreditRank
     */
    @SuppressWarnings("JpaQueryApiInspection")
    public CreditRank queryRank(String mobile, int year, int month, int day);

    /**
     * 查找某日某手机号的排行
     * @param mobile 手机号
     * @param calendar 日期
     * @return 排行信息, 如果没有找到，返回null
     * @see com.xiayule.getll.domain.CreditRank
     */
    public CreditRank queryRank(String mobile, Calendar calendar);

    public List<CreditLog> findAllVersionHistory();

}
