package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.CreditLogDao;
import com.xiayule.getll.db.model.CreditLog;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

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

    public List<CreditLog> findAllVersionHistory() {
        return (List<CreditLog>)getHibernateTemplate().find("from CreditLog s order by s.time asc");
    }
}
