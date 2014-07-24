package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.CookieService;
import com.xiayule.getll.service.RedisService;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.*;

/**
 * Created by tan on 14-7-20.
 * 操作本地cookie的类
 */
public class CookieServiceImpl implements CookieService {
    private RedisService redisService;

    /**
     * 存储 cookie
     * @param mobile 对应的手机号
     * @param cookieStore 要存储的 cookies
     */
    public void saveCookie(String mobile, CookieStore cookieStore) {
        List<Cookie> cookies = cookieStore.getCookies();
        saveCookie(mobile, cookies);
    }

    /**
     * 存储 cookie
     * @param mobile 对应的手机号
     * @param cookies 要存储的 cookies
     */
    public void saveCookie(String mobile, List<Cookie> cookies) {
        Map<String, String> data = new HashMap<String, String>();

        for (Cookie cookie : cookies) {
            String key = cookie.getName();
            String value = cookie.getValue();

            data.put(key, value);
        }

        redisService.hmset(mobile, data);
    }

    public List<Cookie> getCookies(String mobile) {
        List<Cookie> cookies = new ArrayList<Cookie>();

        Map<String, String> data = redisService.hgetAll(mobile);

        Set<String> keys = data.keySet();
        for (String key : keys) {
            String value = data.get(key);

            BasicClientCookie cookie = new BasicClientCookie(key, value);
            // 设置 cookie
            cookie.setDomain("shake.sd.chinamobile.com");
            cookie.setPath("/");
            //设置过期时间为 100 年后
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, 100);
            cookie.setExpiryDate(c.getTime());

            cookies.add(cookie);
        }

        return cookies;
    }


    public CookieStore getCookieStore(String mobile) {
        List<Cookie> cookies = getCookies(mobile);

        CookieStore cookieStore = new BasicCookieStore();

        for (Cookie cookie : cookies) {
            cookieStore.addCookie(cookie);
        }

        return cookieStore;
    }

    @Override
    public boolean isExist(String mobile) {
        return redisService.exists(mobile);
    }

    // get and set methods

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
