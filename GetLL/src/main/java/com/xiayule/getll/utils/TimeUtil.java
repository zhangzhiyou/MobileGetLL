package com.xiayule.getll.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tan on 14-6-25.
 */
public class TimeUtil {
    public static SimpleDateFormat sdf_line;

    static
    {
        sdf_line = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String getDate() {
        Calendar dt = Calendar.getInstance();

        return sdf_line.format(dt.getTime());
    }

    public static String getDateWithUnderLine(Calendar c) {
        return sdf_line.format(c.getTime());
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static void main(String[] args) {
        String log = "log_18369905136_" + getDate();
        System.out.println(log);
        String[] ss = log.split("_");
        System.out.println(ss[0] + " " + ss[1] + " " + ss[2]);
    }
}
