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

    public static String formatDate(Calendar c) {
        return sdf_line.format(c.getTime());
    }

//    减一个月
    public static void sub30Days(Calendar calendar) {
        calendar.add(Calendar.DAY_OF_MONTH, -30);
    }

    /**
     * 获得问候语
     * @return
     */
    public static String getGreetings() {
        Calendar c = Calendar.getInstance();

        // 当前的小时，24制
        int hour = c.get(Calendar.HOUR_OF_DAY);

        if(hour >= 0 && hour < 6) return "凌晨好";
        else if (hour < 9) return "早上好";
        else if (hour < 12) return "上午好";
        else if (hour < 14) return "中午好";
        else if (hour < 17) return "下午好";
        else if (hour < 19) return "傍晚好";
        else if (hour < 24) return "晚上好";
        else return "您好";
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
