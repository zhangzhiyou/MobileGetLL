package com.xiayule.getll.service;

import org.apache.http.client.CookieStore;

/**
 * Created by tan on 14-7-21.
 */
public interface CookieService {
    public CookieStore getCookieStore(String mobile);
    public void saveCookie(String mobile, CookieStore cookieStore);
    public boolean isExist(String mobile);
}
