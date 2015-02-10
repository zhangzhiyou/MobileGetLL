package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.CookieDao;
import com.xiayule.getll.db.model.Cookie;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 15-2-10.
 */
public class CookieDaoImpl extends HibernateDaoSupport implements CookieDao {



    public Cookie getByMobile(String mobile) {
        List<Cookie> cookieList = (List<Cookie>) getHibernateTemplate().find("from Cookie c where c.mobile=?", mobile);
        if (cookieList.size() <= 0) {
            return null;
        } else {
            return cookieList.get(0);
        }
    }

    /**
     * 根据手机号来保存
     * @param cookie
     */
    public void saveOrUpdate(Cookie cookie) {
        // 先查找
        Cookie c = getByMobile(cookie.getMobile());

        if (c == null) { // 不存在, 则创建
            getHibernateTemplate().save(cookie);
        } else { // 存在，则更新
            c.update(cookie);
            getHibernateTemplate().update(c);
        }
    }

    public Boolean exist(String mobile) {
        String hql = "select count(*) from Cookie c where mobile=?";

        Query query = currentSession().createQuery(hql);
        query.setString(0, mobile);

        return (Long)query.uniqueResult() > 0;
    }

    public void delete(String mobile) {
        getHibernateTemplate().delete("mobile", mobile);
    }
}
