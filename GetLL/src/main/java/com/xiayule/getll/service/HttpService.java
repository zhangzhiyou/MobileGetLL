package com.xiayule.getll.service;

import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/**
 * Created by tan on 14-7-21.
 */
public interface HttpService {
    public String get(String url);
    public String post(String url, List<BasicNameValuePair> params);

    public void setCookieStore(CookieStore cookieStore);
    public CookieStore getCookieStore();
}
