package com.xiayule.getll.service.impl;

/**
 * Created by tan on 14-7-22.
 */
public class SerialNumberManager {
    //TODO: 序列号是否合法
    public static boolean isValid(String serial) {
        if (serial.equals("try")) {
            return true;
        }
        return false;
    }
}
