package com.smile.slidecalendar.utils;

import java.util.Calendar;

/**
 * @author Smile Wei
 * @since 2015/10/21.
 */
public class CalendarUtil {

    /**
     * 计算当前月份和2014年12月的月份差，例如：当前月份为2015年10月，则月份差为11
     *
     * @return 月份差
     */
    public static int getMonthDiff() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (year < 2015) {
            return 0;
        }
        int month = calendar.get(Calendar.MONTH) + 1;
        return (year - 2015) * 12 + month + 1;
    }

    /**
     * 获取选择的calendar
     *
     * @param pageNumber 当前页数
     * @return calendar
     */

    public static Calendar getSelectCalendar(int pageNumber) {
        Calendar calendar = Calendar.getInstance();
        int diff = getMonthDiff() - 1;
        if (pageNumber > diff) {
            for (int i = 0; i < pageNumber - diff; i++) {
                calendar = setNextViewItem(calendar);
            }
        } else if (pageNumber < diff) {
            for (int i = 0; i < diff - pageNumber; i++) {
                calendar = setPrevViewItem(calendar);
            }
        }
        return calendar;
    }


    /**
     * 获取上个月的calendar
     *
     * @param calendar calendar
     * @return calendar
     */
    private static Calendar setPrevViewItem(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        month--;

        // 如果当前月为负数的话显示上一年
        if (month == -1) {
            month = 11;
            year--;
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置日为当月1日
        calendar.set(Calendar.MONTH, month); // 设置月
        calendar.set(Calendar.YEAR, year); // 设置年
        return calendar;
    }

    /**
     * 获取下个月的calendar
     *
     * @param calendar calendar
     * @return calendar
     */
    private static Calendar setNextViewItem(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        month++;
        if (month == 12) {
            month = 0;
            year++;
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }
}
