package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.MobileGroupDao;
import com.xiayule.getll.db.model.MobileGroup;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 15-3-31.
 */
public class MobileGroupDaoImpl extends HibernateDaoSupport implements MobileGroupDao {

    public MobileGroup get(String mobile) {
        List<MobileGroup> mobileGroups = (List<MobileGroup>) getHibernateTemplate().find("from MobileGroup as m where m.mobile=?", mobile);

        if (mobileGroups.size()>0) {
            return mobileGroups.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<String> getGroup(String mobile) {

        MobileGroup mobileGroup = get(mobile);
        if (mobileGroup == null) {
            return null;
        }

        String hql = "select mobile from MobileGroup m where m.mobile <> :mobile and m.index = :index";

        Query query = currentSession().createQuery(hql);
        query.setParameter("mobile", mobile);
        query.setParameter("index", mobileGroup.getIndex());

        return (List<String>) query.list();
    }

    @Override
    public void save(MobileGroup mobileGroup) {
        getHibernateTemplate().save(mobileGroup);
    }


    public void save(String mobile, String index) {
        MobileGroup mobileGroup = new MobileGroup(mobile);
        getHibernateTemplate().save(mobileGroup);
    }

    @Override
    public void delete(String mobile) {
        String hql = "delete from MobileGroup m where m.mobile=?";

        Query query = currentSession().createQuery(hql);
        query.setString(0, mobile);

        query.executeUpdate();
    }

    @Override
    public Boolean exist(String mobile) {
        String hql = "select count(*) from MobileGroup m where m.mobile=?";

        Query query = currentSession().createQuery(hql);
        query.setString(0, mobile);

        return (Long)query.uniqueResult() > 0;
    }
}
