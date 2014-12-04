package com.xiayule.getll.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by tan on 14-12-4.
 */
public class CreditUtils {
    public static Double parseCredit(String winName) {
        if (hasCredit(winName)) {
            double credit = Double.parseDouble(winName.replace("个流量币", ""));
            return credit;
        }
        return 0.0;
    }

    // 如果获得的流量币，就要 计数
    // 解析出来获得的流量币
    // 如果获得流量币，一般都是 '0.1个流量币' 这样的形式
    public static Boolean hasCredit(String winName) {
        return winName.contains("个流量币");
    }
}
