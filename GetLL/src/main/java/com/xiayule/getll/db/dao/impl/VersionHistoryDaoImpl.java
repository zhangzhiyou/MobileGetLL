package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.VersionHistoryDao;
import com.xiayule.getll.db.model.HistoryVersion;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 14-11-26.
 */
public class VersionHistoryDaoImpl extends HibernateDaoSupport implements VersionHistoryDao {
    public HistoryVersion get(Integer id) {
        return getHibernateTemplate().get(HistoryVersion.class, id);
    }

    public Integer save(HistoryVersion historyVersion) {
        return (Integer) getHibernateTemplate()
                .save(historyVersion);
    }

    public void saveOrUpdate(HistoryVersion historyVersion) {
        getHibernateTemplate().saveOrUpdate(historyVersion);
    }

    public void update(HistoryVersion historyVersion) {
        getHibernateTemplate().update(historyVersion);
    }

    public void delete(Integer id) {
        getHibernateTemplate().delete(get(id));
    }

    public void delete(HistoryVersion historyVersion) {
        getHibernateTemplate().delete(historyVersion);
    }

    public List<HistoryVersion> findAllVersionHistory() {
        return (List<HistoryVersion>)getHibernateTemplate().find("from HistoryVersion h order by h.time desc");
    }
}
