package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.VersionHistoryDao;
import com.xiayule.getll.db.model.VersionHistory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 14-11-26.
 */
public class VersionHistoryDaoImpl extends HibernateDaoSupport implements VersionHistoryDao {
    public VersionHistory get(Integer id) {
        return getHibernateTemplate().get(VersionHistory.class, id);
    }

    public Integer save(VersionHistory versionHistory) {
        return (Integer) getHibernateTemplate()
                .save(versionHistory);
    }

    public void update(VersionHistory versionHistory) {
        getHibernateTemplate().update(versionHistory);
    }

    public void delete(Integer id) {
        getHibernateTemplate().delete(get(id));
    }

    public void delete(VersionHistory versionHistory) {
        getHibernateTemplate().delete(versionHistory);
    }

    public List<VersionHistory> findAllVersionHistory() {
        return (List<VersionHistory>)getHibernateTemplate().find("from VersionHistory");
    }
}
