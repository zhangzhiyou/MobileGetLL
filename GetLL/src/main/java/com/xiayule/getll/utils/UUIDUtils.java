package com.xiayule.getll.utils;

import java.util.UUID;

/**
 * Created by tan on 15-3-31.
 */
public class UUIDUtils {
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
