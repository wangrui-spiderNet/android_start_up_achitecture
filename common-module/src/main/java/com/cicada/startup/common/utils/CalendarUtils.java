package com.cicada.startup.common.utils;

import java.util.Calendar;

/**
 * TODO
 * <p>
 * Create time: 2017/7/21 10:40
 *
 * @author liuyun.
 */
public class CalendarUtils {
    /**
     * 获取日历当天最早的时间：00：00：00
     *
     * @param calendarIn
     * @param minDay     是否是本月第一天
     * @return
     */
    public static long getCalendarMinTime(Calendar calendarIn, boolean minDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendarIn.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendarIn.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendarIn.get(Calendar.DAY_OF_MONTH));
        if (minDay) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        }
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取日历当天最晚的时间：23：59：59
     * 实际取的是下一天的最早时间
     *
     * @param calendarIn
     * @return
     */
    public static long getCalendarMaxTime(Calendar calendarIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendarIn.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendarIn.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendarIn.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }
}
