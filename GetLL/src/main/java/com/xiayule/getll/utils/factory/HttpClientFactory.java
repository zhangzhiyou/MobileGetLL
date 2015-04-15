package com.xiayule.getll.utils.factory;

import com.xiayule.getll.service.CookieService;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用于创建HttpClient的工厂类
 * Created by tan on 14-11-25.
 */
@Component
public class HttpClientFactory {
    @Autowired
    private CookieService cookieService;

    private static CredentialsProvider credsProvider;

    private static HttpHost proxy;

    // ace http 需要代理
    static {
        credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope("10.242.175.127", 3128),
                new UsernamePasswordCredentials("1911504709953557_default_88", "rf4ode4c86"));

        proxy = new HttpHost("10.242.175.127", 3128, "http");
    }

    public DefaultHttpClient createHttpClient(String mobile) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        defaultHttpClient.setCredentialsProvider(credsProvider);

        defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);

        // 设置超时时间
        defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

        if (cookieService.isExist(mobile)) {
            CookieStore cookieStore = cookieService.getCookieStore(mobile);

            /*System.out.println("cookieStore: " + cookieStore);
            List<Cookie> cookieList = cookieStore.getCookies();
            for (Cookie c : cookieList) {
                System.out.println(c);
            }
*/

            defaultHttpClient.setCookieStore(cookieStore);
        }

        return defaultHttpClient;
    }
}
