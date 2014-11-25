package com.xiayule.getll.factory;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

/**
 * Created by tan on 14-11-25.
 */
public class HttpClientFactory {
    public static DefaultHttpClient createHttpClient() {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        // 设置超时时间
        defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

        return defaultHttpClient;
    }
}
