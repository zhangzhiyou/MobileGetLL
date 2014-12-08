package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.CreditLogDao;
import com.xiayule.getll.db.model.CreditLog;
import com.xiayule.getll.domain.CreditRank;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tan on 14-12-3.
 */
public class CreditLogDaoImpl extends HibernateDaoSupport implements CreditLogDao {
    public CreditLog get(Integer id) {
        return getHibernateTemplate().get(CreditLog.class, id);
    }

    public void saveOrUpdate(CreditLog creditLog) {
        getHibernateTemplate().saveOrUpdate(creditLog);
    }

    public void update(CreditLog creditLog) {
        getHibernateTemplate().update(creditLog);
    }

    public void delete(Integer id) {
        getHibernateTemplate().delete(get(id));
    }

    public void delete(CreditLog creditLog) {
        getHibernateTemplate().delete(creditLog);
    }

    /**
     * 获得某日排行第一名信息
     * @param year 记录的年
     * @param month 记录的月
     * @param day 记录的日
     * @return 排行信息, 如果没有找到，返回null
     */
    public CreditRank queryRank(int year, int month, int day) {

        String hql = "select mobile, sum(credit) as sumCredit, time from CreditLog"
                + " where year(time) = ? and month(time) = ? and day(time) = ?"
                + " group by mobile order by sumCredit desc";

        Query query = currentSession().createQuery(hql);

        query.setFirstResult(0);
        query.setMaxResults(1);

        query.setParameter(0, year);
        query.setParameter(1, month);
        query.setParameter(2, day);

        List<Object[]> objsList = query.list();

        if (objsList.size() == 0) return null;

        Object[] firstObjs = objsList.get(0);

        return new CreditRank(1, (String)firstObjs[0], (Double)firstObjs[1]);
    }

    /**
     * 获得某日排行第一名信息
     * @param calendar 记录的日期
     * @return 排行信息, 如果没有找到，返回null
     */
    public CreditRank queryRank(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return queryRank(year, month, day);
    }

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
    public CreditRank queryRank(String mobile, int year, int month, int day) {

        String sql = "select rank, mobile, sumCredit " +
                "from (select @counter\\:=@counter+1 as rank, mobile, sumCredit " +
                "from (select mobile, sum(credit) as sumCredit from shake_log " +
                " where year(time) = :year and month(time) = :month and day(time) = :day " +
                "group by mobile order by sumCredit desc) as sumList, (select @counter\\:=0) as t) as rankList " +
                "where mobile = :mobile";

        SQLQuery query = currentSession().createSQLQuery(sql)
                .addScalar("rank", StandardBasicTypes.INTEGER)
                .addScalar("mobile", StandardBasicTypes.STRING)
                .addScalar("sumCredit", StandardBasicTypes.DOUBLE);

        // 设置参数
        query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("day", day);
        query.setParameter("mobile", mobile);

        // 查询
        List<Object[]> objsList = query.list();

        if (objsList.size() == 0) {
            return null;
        }

        Object[] firstObjs = objsList.get(0);

        // 封装
        CreditRank creditRank = new CreditRank((Integer)firstObjs[0], (String)firstObjs[1], (Double)firstObjs[2]);

        return creditRank;
    }

    /**
     * 查找某日某手机号的排行
     * @param mobile 手机号
     * @param calendar 日期
     * @return 排行信息, 如果没有找到，返回null
     * @see com.xiayule.getll.domain.CreditRank
     */
    public CreditRank queryRank(String mobile, Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return queryRank(mobile, year, month, day);
    }


    /*public Integer queryLogCount(Calendar c) {

    }*/

    public List<CreditLog> findAllVersionHistory() {
        return (List<CreditLog>)getHibernateTemplate().find("from CreditLog s order by s.time asc");
    }
}
