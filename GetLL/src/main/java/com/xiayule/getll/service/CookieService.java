package com.xiayule.getll.service;

import org.apache.http.client.CookieStore;

/**
 * Created by tan on 14-7-21.
 */
public interface CookieService {
    public CookieStore getCookieStore(String mobile);
    public void saveCookie(String mobile, CookieStore cookieStore);
    public boolean isExist(String mobile);
    /**
     * 删除 redis 中缓存的 cookie
     * @param mobile 需要删除的手机号
     */
    public void deleteCookie(String mobile);

//    public Map<String, String> getCookies(String mobile);
}
