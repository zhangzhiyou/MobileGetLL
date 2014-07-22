package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.HttpService;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;


/**
 * Created by tan on 14-7-20.
 */
public class HttpServiceImpl implements HttpService {
    private static DefaultHttpClient defaultHttpClient;
    private static CookieStore cookieStore;

    private static DefaultHttpClient getDefaultHttpClient() {
        if (defaultHttpClient == null) {
            defaultHttpClient = new DefaultHttpClient();

            // 手冻管理 cookie
//            defaultHttpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);

            // 设置超时时间
            defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
            defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        }

        return defaultHttpClient;
    }

    public String post(String url, List<BasicNameValuePair> params) {
        try {
            HttpPost request = new HttpPost(url); // 根据内容来源地址创建一个Http请求

            initHeaders(request);

            if (params != null) {
                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); // 设置参数的编码
            }

            DefaultHttpClient client = getDefaultHttpClient();

            // 保存 cookies
            client.setCookieStore(cookieStore);

            HttpResponse httpResponse = client.execute(request); // 发送请求并获取反馈

            // 解析返回的内容
            if (httpResponse.getStatusLine().getStatusCode() != 404) {
                String result = EntityUtils.toString(httpResponse.getEntity());

                // 设置 cookies
                cookieStore = client.getCookieStore();

                return result;
            }
        } catch (Exception e) {

        }
        return null;
    }

    public String get(String url) {
        try {
            // 根据内容来源地址创建一个Http请求
            HttpGet request = new HttpGet(url);

            DefaultHttpClient client = getDefaultHttpClient();

            client.setCookieStore(cookieStore);

            // 设置参数的编码
            HttpResponse httpResponse = client.execute(request); // 发送请求并获取反馈
            // 解析返回的内容
            if (httpResponse.getStatusLine().getStatusCode() != 404) {

                String result = EntityUtils.toString(httpResponse.getEntity());

                // 保存 cookie
                cookieStore = client.getCookieStore();

                return result;
            }
        } catch (Exception e) {

        }
        return null;
    }


    private void printHeaders(Header[] headers) {
        System.out.println("======Header Begin====");

        for (Header header : headers) {
            System.out.println(header.getName() + ":" + header.getValue());
        }

        System.out.println("======Header end====");
    }

    private void printCookies(DefaultHttpClient c) {
        System.out.println("======Cookie Begin====");
        // 查看cookie
        List<Cookie> cookies = c.getCookieStore().getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + ":" + cookie.getValue());
        }
        System.out.println("======Cookie end====");
    }

    private void initHeaders(HttpRequest request) {
        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        request.setHeader("Accept-Encoding", "gzip,deflate,sdch");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        request.setHeader("Origin", "http://shake.sd.chinamobile.com");
        request.setHeader("Referer", "http://shake.sd.chinamobile.com/");
        request.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
    }

    // get and set methods


    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
