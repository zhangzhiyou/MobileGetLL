package com.xiayule.getll.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tan on 14-6-25.
 */
public class TimeUtils {
    public static SimpleDateFormat sdf_line;

    static
    {
        sdf_line = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String getTodayDate() {
        Calendar dt = Calendar.getInstance();

        return sdf_line.format(dt.getTime());
    }

    /**
     * 单位是秒
     * @return
     */
    public static int getMaxValidTimeWithSecond() {
        return 60*60*24*Constants.TTL_VALID_DAY-10;
    }

    public static String getDateWithUnderLine(Calendar c) {
        return sdf_line.format(c.getTime());
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static void main(String[] args) {
      /*  String log = "log_18369905136_" + getTodayDate();
        System.out.println(log);
        String[] ss = log.split("_");
        System.out.println(ss[0] + " " + ss[1] + " " + ss[2]);*/

        System.out.println(Double.parseDouble("0.1"));
    }
}
