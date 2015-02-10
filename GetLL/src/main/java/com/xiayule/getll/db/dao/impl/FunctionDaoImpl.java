package com.xiayule.getll.db.dao.impl;

import com.xiayule.getll.db.dao.FunctionDao;
import com.xiayule.getll.db.model.Function;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by tan on 15-2-10.
 */
public class FunctionDaoImpl extends HibernateDaoSupport implements FunctionDao {

    public Function getByMobile(String mobile) {
        List<Function> functionList = (List<Function>) getHibernateTemplate().find("from Function f where f.mobile=?", mobile);
        if (functionList.size() <= 0) {
            return null;
        } else {
            return functionList.get(0);
        }
    }

    public void save(Function function) {
        getHibernateTemplate().save(function);
    }

    public void updateAutoReceive(String mobile, Boolean autoReceive) {
        String hql = "update Function f set f.autoReceive = ? where f.mobile = ?";
        Query query = currentSession().createQuery(hql);
        query.setBoolean(0, autoReceive);
        query.setString(1, mobile);

        query.executeUpdate();
    }

    public void updateForFriend(String mobile, Boolean forFriend) {
        String hql = "update Function f set f.forFriend = ? where f.mobile = ?";
        Query query = currentSession().createQuery(hql);
        query.setBoolean(0, forFriend);
        query.setString(1, mobile);

        query.executeUpdate();
    }


    public List<String> getAllForFriend() {
        //todo: 查找的同时去掉没有订阅的
        String hql = "select mobile from Function where forFriend=true";
        Query query = currentSession().createQuery(hql);
        List<String> mobiles = query.list();
        return mobiles;
    }

    public List<String> getAllAutoReceive() {
        //todo: 查找所有自动领取的, 去掉没有订阅的
        String hql = "select mobile from Function where autoReceive=true";
        Query query = currentSession().createQuery(hql);
        List<String> mobiles = query.list();
        return mobiles;
    }
}
