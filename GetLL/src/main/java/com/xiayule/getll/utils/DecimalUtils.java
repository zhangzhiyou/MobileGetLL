package com.xiayule.getll.utils;

import java.text.DecimalFormat;

/**
 * Created by tan on 14-7-24.
 */
public class DecimalUtils {
    private static DecimalFormat df = new DecimalFormat("0.0");

    public static String formatDecial(double decimal) {
        return df.format(decimal);
    }

    /**
     * 将小数转换为百分比
     * @param percent 小数表示的百分比
     * @return
     */
    public static String formatPersont(double percent) {
        DecimalFormat dft = new DecimalFormat("0%");
        return dft.format(percent);
    }

    public static void main(String[] args) {
        System.out.println(formatDecial(0211.2345));
    }
}
