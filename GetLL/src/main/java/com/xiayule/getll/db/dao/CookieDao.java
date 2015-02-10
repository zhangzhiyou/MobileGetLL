package com.xiayule.getll.db.dao;


import com.xiayule.getll.db.model.Cookie;

/**
 * Created by tan on 15-2-10.
 */
public interface CookieDao {
    public Cookie getByMobile(String mobile);

    public void saveOrUpdate(Cookie cookie);

    public Boolean exist(String mobile);

    public void delete(String mobile);
}
