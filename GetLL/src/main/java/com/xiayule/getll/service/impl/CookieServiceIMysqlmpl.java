package com.xiayule.getll.service.impl;

import com.xiayule.getll.db.dao.CookieDao;
import com.xiayule.getll.db.model.Cookie;
import com.xiayule.getll.service.CookieService;
import com.xiayule.getll.utils.factory.CookieFactory;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tan on 15-2-10.
 */
public class CookieServiceIMysqlmpl implements CookieService {

    private CookieDao cookieDao;

    @Override
    public CookieStore getCookieStore(String mobile) {
        // 获得 cookie
        Cookie cookie = cookieDao.getByMobile(mobile);

        CookieStore cookieStore = new BasicCookieStore();

        // 如果查找不到，返回空的
        if (cookie == null)
            return cookieStore;

        // 取出model cookie中保存的信息
        BasicClientCookie jSessionId = CookieFactory.newShakeCookie(Cookie.JSESSIONID, cookie.getjSessionId());
        BasicClientCookie loginToken = CookieFactory.newShakeCookie(Cookie.LOGINTOKEN, cookie.getLoginToken());
        BasicClientCookie nickName = CookieFactory.newShakeCookie(Cookie.NICKNAME, cookie.getNickName());
        BasicClientCookie provWap = CookieFactory.newShakeCookie(Cookie.PROVWAP, cookie.getPrivWap());
        BasicClientCookie userSign = CookieFactory.newShakeCookie(Cookie.USERSIGN, cookie.getUserSignWap());

        // 创建需要的cookie
        cookieStore.addCookie(jSessionId);
        cookieStore.addCookie(loginToken);
        cookieStore.addCookie(nickName);
        cookieStore.addCookie(provWap);
        cookieStore.addCookie(userSign);

        return cookieStore;
    }

    public Map<String, String> getCookies(String mobile) {
        Map<String, String> cookies = new HashMap<String, String>();

        Cookie cookie = cookieDao.getByMobile(mobile);

        cookies.put(Cookie.JSESSIONID, cookie.getjSessionId());
        cookies.put(Cookie.LOGINTOKEN, cookie.getLoginToken());
        cookies.put(Cookie.NICKNAME, cookie.getNickName());
        cookies.put(Cookie.PROVWAP, cookie.getPrivWap());
        cookies.put(Cookie.USERSIGN, cookie.getUserSignWap());

        return cookies;
    }


    @Override
    public void saveCookie(String mobile, CookieStore cookieStore) {

        Cookie cookie = new Cookie();

        cookie.setMobile(mobile);

        List<org.apache.http.cookie.Cookie> webCookies = cookieStore.getCookies();

        // 提取信息封装如 model
        for (org.apache.http.cookie.Cookie c : webCookies) {

            String key = c.getName();

            if (key.equalsIgnoreCase(Cookie.JSESSIONID)) {
                cookie.setjSessionId(c.getValue());
            } else if (key.equalsIgnoreCase(Cookie.LOGINTOKEN)) {
                cookie.setLoginToken(c.getValue());
            } else if (key.equalsIgnoreCase(Cookie.NICKNAME)) {
                cookie.setNickName(c.getValue());
            } else if (key.equalsIgnoreCase(Cookie.PROVWAP)) {
                cookie.setPrivWap(c.getValue());
            } else if (key.equalsIgnoreCase(Cookie.USERSIGN)) {
                cookie.setUserSignWap(c.getValue());
            }
        }

        // 持久划
        cookieDao.saveOrUpdate(cookie);
    }

    @Override
    public boolean isExist(String mobile) {
        return cookieDao.exist(mobile);
    }

    @Override
    public void deleteCookie(String mobile) {
        cookieDao.delete(mobile);
    }

    // set and get methods


    public CookieDao getCookieDao() {
        return cookieDao;
    }

    public void setCookieDao(CookieDao cookieDao) {
        this.cookieDao = cookieDao;
    }
}
