package com.xiayule.getll.utils;

import java.util.UUID;

/**
 * Created by tan on 14-7-22.
 */
public class SerialNumberUtils {
    public static String getSerialNumber() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
