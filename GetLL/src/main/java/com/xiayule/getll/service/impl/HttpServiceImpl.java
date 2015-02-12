package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.CookieService;
import com.xiayule.getll.service.HttpService;
import com.xiayule.getll.utils.factory.HttpClientFactory;
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tan on 14-7-20.
 * Http 的 post 和 get，能够操作 cookie
 */
@Component
@Scope("prototype")
public class HttpServiceImpl implements HttpService {
    private static Logger logger = Logger.getLogger(HttpService.class);

    @Autowired
    private CookieService cookieService;

    @Autowired
    private HttpClientFactory httpClientFactory;

    private DefaultHttpClient getDefaultHttpClient(String mobile) {
        /*if (defaultHttpClient == null) {
            defaultHttpClient = new DefaultHttpClient();

            // 手冻管理 cookie
//            defaultHttpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);

            // 设置超时时间
            defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
            defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        }*/

        // 每次都生成新的 httpclient, 防止 cookie 混乱
        DefaultHttpClient defaultHttpClient = httpClientFactory.createHttpClient(mobile);

        return defaultHttpClient;
    }

    public String post(String mobile, String url, List<BasicNameValuePair> params) {
        try {
            HttpPost request = new HttpPost(url); // 根据内容来源地址创建一个Http请求

            initPostHeaders(request);

            if (params != null) {
                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); // 设置参数的编码
            }

            DefaultHttpClient client = getDefaultHttpClient(mobile);

            // 保存 cookies
//            client.setCookieStore(cookieStore);

            HttpResponse httpResponse = client.execute(request); // 发送请求并获取反馈


            // 解析返回的内容
            if (httpResponse.getStatusLine().getStatusCode() != 404) {
                String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);

                // 保存 cookie
                updateCookieToLocal(client, mobile);

                return result;
            }
        } catch (Exception e) {


        }
        return null;
    }

    public String get(String mobile, String url) {
        try {

            // 根据内容来源地址创建一个Http请求
            HttpGet request = new HttpGet(url);

            initGetHeaders(request);

            DefaultHttpClient client = getDefaultHttpClient(mobile);


            // 设置参数的编码
            HttpResponse httpResponse = client.execute(request); // 发送请求并获取反馈

            // 解析返回的内容
            if (httpResponse.getStatusLine().getStatusCode() != 404) {

                String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);

                // 保存 cookie
//                cookieStore = client.getCookieStore();
                updateCookieToLocal(client, mobile);

                return result;
            }
        } catch (Exception e) {
            logger.info("HttpServiceImpl post error(发生错误), e(" + e.toString() + ")");
        }

        return null;
    }

    public void updateCookieToLocal(DefaultHttpClient client, String mobile) {
        CookieStore cookieStore = client.getCookieStore();

//        System.out.println("updateCookieToLocal");
//        for (Cookie cookie : cookieStore.getCookies()) {
//            System.out.println(cookie);
//        }


        cookieService.saveCookie(mobile, cookieStore);
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

    private void initGetHeaders(HttpRequest request) {
        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        //get 使用没关系, post 不可以
        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Cache-Control", "no-cache");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        request.setHeader("Host", "shake.sd.chinamobile.com");
        request.setHeader("Origin", "http://shake.sd.chinamobile.com");
        request.setHeader("Referer", "http://shake.sd.chinamobile.com/");
        request.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36");

        // get 去掉这个就可以, post 没关系
//        request.setHeader("X-Requested-With", "XMLHttpRequest");
    }

    private void initPostHeaders(HttpRequest request) {
        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        //get 使用没关系, post 不可以
//        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Cache-Control", "no-cache");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        request.setHeader("Host", "shake.sd.chinamobile.com");
        request.setHeader("Origin", "http://shake.sd.chinamobile.com");
        request.setHeader("Referer", "http://shake.sd.chinamobile.com/");
        request.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36");

        // get 去掉这个就可以, post 没关系
        request.setHeader("X-Requested-With", "XMLHttpRequest");
    }


//    private void initHeaders(HttpRequest request) {
//        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
//        request.setHeader("Accept-Encoding", "gzip, deflate");
//        request.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
//        request.setHeader("Connection", "keep-alive");
//        request.setHeader("Cache-Control", "no-cache");
//        request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//        request.setHeader("Host", "shake.sd.chinamobile.com");
//        request.setHeader("Origin", "http://shake.sd.chinamobile.com");
//        request.setHeader("Referer", "http://shake.sd.chinamobile.com/");
//        request.setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:33.0) Gecko/20100101 Firefox/33.0");
//        request.setHeader("X-Requested-With", "XMLHttpRequest");
//    }

    // get and set methods

    public CookieService getCookieService() {
        return cookieService;
    }

    public void setCookieService(CookieService cookieService) {
        this.cookieService = cookieService;
    }
}
