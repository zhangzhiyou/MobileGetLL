package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.MobileAccountDao;
import com.xiayule.getll.db.model.MobileAccount;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.management.monitor.StringMonitorMBean;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tan on 15-2-11.
 */
public class MobileAccountDaoImpl extends HibernateDaoSupport implements MobileAccountDao {

    public void save(MobileAccount mobileAccount) {
        getHibernateTemplate().save(mobileAccount);
    }

    public void update(MobileAccount mobileAccount) {
        getHibernateTemplate().update(mobileAccount);
    }

    public MobileAccount getByMobile(String mobile) {
        List<MobileAccount> mobileAccountList = (List<MobileAccount>) getHibernateTemplate().find("from MobileAccount m where m.mobile=?", mobile);
        if (mobileAccountList.size() <= 0) {
            return null;
        } else {
            return mobileAccountList.get(0);
        }
    }

    /**
     * 获得所有的有效用户
     */
    public List<String> getAllValid() {
        // 获得当前日期
        Calendar calendar = Calendar.getInstance();

        String hql = "select m.mobile from MobileAccount m where m.endTime>=:date";

        Query query = currentSession().createQuery(hql);
        query.setDate("date", calendar.getTime());

        return query.list();
    }

    /**
     * 统计当前有效用户的数量
     * @return
     */
    public Long countValid() {
        // 获得当前日期
        Calendar calendar = Calendar.getInstance();

        String hql = "select count(*) from MobileAccount m where m.endTime>=:date";

        Query query = currentSession().createQuery(hql);
        query.setDate("date", calendar.getTime());

        return (Long)query.uniqueResult();
    }

    @Override
    public void delete(String mobile) {
        String hql = "delete from MobileAccount m where m.mobile=?";

        Query query = currentSession().createQuery(hql);
        query.setString(0, mobile);

        query.executeUpdate();
    }
}
