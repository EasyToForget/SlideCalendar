package com.smile.slidecalendar.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {

    /**
     * 常用时间格式
     */
    public final static String FORMAT_yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_yyyyMMdd_HHmm = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_yyyyMMdd_HHmm_CHINESE = "yyyy年MM月dd日 HH:mm";
    public final static String FORMAT_yyyyMMdd = "yyyyMMdd";
    public final static String FORMAT_yyyyMMdd_CHINESE = "yyyy年MM月dd日";
    public final static String FORMAT_yyyy_S_MM_S_dd_HHmm = "yyyy/MM/dd HH:mm";
    public final static String FORMAT_yyyy_S_MM_dd = "yyyy/MM/dd";
    public final static String FORMAT_MMdd_HHmm = "MM-dd HH:mm";
    public final static String FORMAT_yyyy = "yyyy";
    public final static String FORMAT_M = "M";
    public final static String FORMAT_dd = "dd";
    public final static String FORMAT_HHmmss = "HH:mm:ss";
    public final static String FORMAT_HHMM = "HH:mm";
    public final static String FORMAT_MMdd_HHmm_CHINESE = "MM月dd日 HH:mm";
    public final static String FORMAT_MMddHHmmss = "MM-dd HH:mm:ss";

    /**
     * 根据时间戳获得指定格式日期
     * @param format 时间格式
     * @param time 时间戳 秒
     * @return 日期
     */
    public static String formatDate(final String format, final long time) {
        SimpleDateFormat sdf;

        if (!TextUtils.isEmpty(format)) {
            sdf = new SimpleDateFormat(format,
                    Locale.CHINA);
        } else {
            sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss",
                    Locale.CHINA);
        }
        return sdf.format(new Date(time));
    }

    /**
     * 根据传入日期获得对应时间戳
     * @param format 时间格式
     * @param date 时间
     * @return 时间戳 毫秒
     */
    public static long formatDate(final String format, final String date) {
        SimpleDateFormat sdf;

        if (!TextUtils.isEmpty(format)) {
            sdf = new SimpleDateFormat(format,
                    Locale.CHINA);
        } else {
            sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss",
                    Locale.CHINA);
        }

        try {
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
