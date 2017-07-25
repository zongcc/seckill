package com.zongcc.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

public class DateUtil {

    /**
     * Default date pattern
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * is date string
     *
     * @param str
     * @return
     */
    public static boolean isDateString(String str) {
        try {
            str2date(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * String to date
     *
     * @param str
     * @return
     * @throws java.text.ParseException
     */
    public static Date str2date(String str) {
        try {
            return str2date(str, DEFAULT_DATE_PATTERN);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * String to date with custom pattern
     *
     * @param str
     * @param pattern
     * @return
     * @throws java.text.ParseException
     */
    public static Date str2date(String str, String pattern) {
        try {
            if (StringUtils.isBlank(pattern)) {
                return DateUtils.parseDate(str, new String[]{DEFAULT_DATE_PATTERN});
            } else {
                return DateUtils.parseDate(str, new String[]{pattern});
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Date to string
     *
     * @param date
     * @return
     */
    public static String date2str(Date date) {
        return date2str(date, DEFAULT_DATE_PATTERN);
    }

    /**
     * Date to string with custom pattern
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String date2str(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            return DateFormatUtils.format(date, DEFAULT_DATE_PATTERN);
        } else {
            return DateFormatUtils.format(date, pattern);
        }
    }

    /**
     * Add year
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addYears(Date date, int amount) {
        return DateUtils.addYears(date, amount);
    }

    /**
     * Add month
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    /**
     * Add week
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    /**
     * Add day
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * Add hour
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * Add minute
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    /**
     * Add second
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addSeconds(Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /**
     * truncate
     *
     * @param date
     * @param field
     * @return
     */
    public static Date truncate(Date date, int field) {
        return DateUtils.truncate(date, field);
    }

    /**
     * Time interval
     *
     * @param first
     * @param second
     * @return
     */
    public static int dayDiff(Date first, Date second) {

        long msPerDay = 1000 * 60 * 60 * 24;
        long diff = (first.getTime() / msPerDay)
                - (second.getTime() / msPerDay);

        Long convertLong = new Long(diff);
        return convertLong.intValue();
    }

    /**
     * @param l
     * @return
     */
    public static String long2date(long l) {
        Date date = new Date(l);
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将毫秒数换算成x天x时x分x秒x毫秒
     *
     * @param ms
     * @return
     */
    public static String format(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        //String strDay = day < 10 ? "0" + day : "" + day;

        //String strHour = hour < 10 ? "0" + hour : "" + hour;
        long hours = day * 24 + hour;

        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return hours + ":" + strMinute + "'" + strSecond + "\"";
    }

    public static long day(long mil) {
        return (long) Math.ceil(mil / (1000 * 60 * 60 * 24.0));
    }
}
