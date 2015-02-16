package com.xiayule.getll.utils.factory;

import com.xiayule.getll.service.CookieService;
import org.apache.http.client.CookieStore;
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

    public DefaultHttpClient createHttpClient(String mobile) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

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
