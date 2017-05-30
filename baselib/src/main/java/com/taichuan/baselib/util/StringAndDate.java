package com.taichuan.baselib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 此类编写了各种String和Date型的各种格式转换
 *
 * @author 桂
 */
public class StringAndDate {

    private static SimpleDateFormat dateformat;

    /**
     * yy-MM-dd HH:mm:ss
     */
    public static Date stringtoDate(String dateString) {
        Date date;
        try {
            dateformat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");//大写H为24小时制,小写为12小时
            date = dateformat.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String变为date的yy-MM-dd
     *
     * @return
     */
    public static Date stringToyy_MM_dd(String str) {
        try {
            dateformat = new SimpleDateFormat("yy-MM-dd");
            return dateformat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String变为date的yy-MM-dd
     *
     * @return
     */
    public static Date stringToyyyy_MM_dd(String str) {
        Date date;
        try {
            dateformat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateformat.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String 变为 date 的EEE
     */
    public static Date stringToEEE(String str) {
        Date date;
        try {
            dateformat = new SimpleDateFormat("EEE");
            date = dateformat.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date stringToyy_MM(String dateString) {
        Date date;
        try {
            dateformat = new SimpleDateFormat("yy-MM");
            date = dateformat.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date stringtoHH_mm_ss(String dateString) {
        try {
            dateformat = new SimpleDateFormat("HH:mm:ss");//大写H为24小时制,小写为12小时
            Date date = dateformat.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String dateToyy_MM_dd_HH_mm_ss(Date date) {
        dateformat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyy_MM_dd_HH_mm(Date date) {
        dateformat = new SimpleDateFormat("yy-MM-dd HH:mm");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyy_MM_dd_Enter_HH_mm(Date date) {
        dateformat = new SimpleDateFormat("yy-MM-dd\n HH:mm");
        String str_date = dateformat.format(date);
        return str_date;
    }

    /**
     * date类型转换成String yy-MM-dd
     */
    public static String dateToyy_MM_dd(Date date) {
        dateformat = new SimpleDateFormat("yy-MM-dd");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyy(Date date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yy");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyyyy(Date date) {
        dateformat = new SimpleDateFormat("yyyy");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToMM(Date date) {
        dateformat = new SimpleDateFormat("MM");
        String str_date = dateformat.format(date);
        return str_date;
    }


    public static String dateToWeek(Date date) {
        dateformat = new SimpleDateFormat("EEE");
        String str_week = dateformat.format(date);
        return str_week;
    }

    public static String dateToHH_mm_ss(Date date) {
        dateformat = new SimpleDateFormat("HH:mm:ss");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToHH_mm(Date date) {
        dateformat = new SimpleDateFormat("HH:mm");
        String str_date = dateformat.format(date);
        return str_date;
    }

    /**
     * return  String型   15-07
     */
    public static String dateToyy_MM(Date date) {
        dateformat = new SimpleDateFormat("yy-MM");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyy年MM月(Date date) {
        dateformat = new SimpleDateFormat("yy年MM月");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyyyy年MM月dd日(Date date) {
        dateformat = new SimpleDateFormat("yyyy年MM月dd日");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyyyy_MM_dd(Date date) {
        dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyyyy_MMdd(Date date) {
        dateformat = new SimpleDateFormat("yyyy-MMdd");
        String str_date = dateformat.format(date);
        return str_date;
    }

    public static String dateToyyyy_MM_dd_HH_mm_ss(Date date) {
        dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str_date = dateformat.format(date);
        return str_date;
    }


    /**
     * return  String型    31
     */
    public static String dateTodd(Date date) {
        dateformat = new SimpleDateFormat("dd");
        String str_date = dateformat.format(date);
        return str_date;
    }

    /**
     * 将Date型的 yy-MM-dd转换成String型的星期几
     *
     * @param date
     * @return
     */
    public static String dateyy_MM_ddToEEEE(Date date) {
        int int_week = date.getDay();
        String str_week = null;
        if (int_week == 0) str_week = "周日";
        if (int_week == 1) str_week = "周一";
        if (int_week == 2) str_week = "周二";
        if (int_week == 3) str_week = "周三";
        if (int_week == 4) str_week = "周四";
        if (int_week == 5) str_week = "周五";
        if (int_week == 6) str_week = "周六";
        return str_week;

    }

    /**
     * 将Long型 毫秒 转换成 String 型 yy-MM-dd HH:mm:ss
     */
    public static String longToDate(Long millisecond) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String str_date = dateformat.format(millisecond);
        return str_date;
    }

    /**
     * 将整秒转换成 HH:mm:ss的格式，</br>
     * 例如： 130秒 转换 成 02:10
     *
     * @return
     */
    public static String secondTommss(int time) {
        int hour = time / 3600;
        int minute = time / 60;
        int second = time % 60;
        String str_minute = minute + "";
        String str_second = second + "";
        String str_hour = hour + "";
        if (str_minute.length() == 1) {
            str_minute = "0" + str_minute;
        }
        if (str_second.length() == 1) {
            str_second = "0" + str_second;
        }
        if (str_hour.length() == 1) {
            str_hour = "0" + str_hour;
        }
//        return str_hour.equals("00") ? "" : (str_hour + ":") + str_minute + ":" + str_second;
        return str_hour + ":" + str_minute + ":" + str_second;
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss 转化成 时间戳
     */
    public static String yy_MM_dd_HH_mm_ssToTimeStamp(String dataStr) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            timeStamp = sdf.parse("2016-07-07 14:29:54").getTime() + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }
}