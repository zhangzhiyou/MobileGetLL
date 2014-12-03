package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.ShakeLogDao;
import com.xiayule.getll.db.model.ShakeLog;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 14-12-3.
 */
public class ShakeLogDaoImpl extends HibernateDaoSupport implements ShakeLogDao {
    public ShakeLog get(Integer id) {
        return getHibernateTemplate().get(ShakeLog.class, id);
    }

    public void saveOrUpdate(ShakeLog shakeLog) {
        getHibernateTemplate().saveOrUpdate(shakeLog);
    }

    public void update(ShakeLog shakeLog) {
        getHibernateTemplate().update(shakeLog);
    }

    public void delete(Integer id) {
        getHibernateTemplate().delete(get(id));
    }

    public void delete(ShakeLog shakeLog) {
        getHibernateTemplate().delete(shakeLog);
    }

    public List<ShakeLog> findAllVersionHistory() {
        return (List<ShakeLog>)getHibernateTemplate().find("from ShakeLog s order by s.time asc");
    }
}
