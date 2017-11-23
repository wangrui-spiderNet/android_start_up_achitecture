package com.cicada.startup.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期控件工具类
 * <p>
 * 创建时间: 2014年12月18日 上午10:34:56 <br/>
 *
 * @since v0.0.1
 */
public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();
    /**
     * 1天=86400000毫秒
     */
    public static final long DAY_OF_MILLISECOND = 24 * 60 * 60 * 1000;
    /**
     * 1小时=3600000毫秒
     */
    public static final long HOUR_OF_MILLISECOND = 60 * 60 * 1000;
    /**
     * 1分钟=60000毫秒
     */
    public static final long MINUTE_OF_MILLISECOND = 60 * 1000;
    /**
     * 1秒钟=1000毫秒
     */
    public static final long SECOND_OF_MILLISECOND = 1000;

    /**
     * 获取－年
     */
    public static String format_yyyy = "yyyy";
    /**
     * 获取－月
     */
    public static String format_MM = "MM";
    /**
     * 获取－天
     */
    public static String format_dd = "dd";
    /**
     * 获取－时
     */
    public static String format_HH = "HH";
    /**
     * 获取－分
     */
    public static String format_mm = "mm";
    /**
     * 获取－秒
     */
    public static String format_ss = "ss";

    /**
     * 英文时分秒日期格式：2014-01-01 6:30:30
     */
    public static String format_yyyy_MM_dd_HH_mm_ss_12_EN = "yyyy-MM-dd hh:mm:ss";

    /**
     * 英文时分秒日期格式：2014-01-01 6:30:30
     */
    public static String format_yyyy_MM_dd_HH_mm_ss_12_EN_point = "yyyy.MM.dd hh:mm:ss";


    /**
     * 英文时分秒日期格式：2014-01-01 18:30:30
     */
    public static String format_yyyy_MM_dd_HH_mm_ss_24_EN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 英文时分秒日期格式：2014-01-01 18:30:30
     */
    public static String format_yyyy_MM_dd_HH_mm_ss_24_EN_point = "yyyy.MM.dd HH:mm:ss";
    /**
     * 中文时分秒日期格式：2014年01月01日 6时30分30秒
     */
    public static String format_yyyy_MM_dd_HH_mm_ss_12_CN = "yyyy年MM月dd日 hh时mm分ss秒";
    /**
     * 中文时分秒日期格式：2014年01月01日 18时30分30秒
     */
    public static String format_yyyy_MM_dd_HH_mm_ss_24_CN = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 英文时分日期格式：2014-01-01 6:30
     */
    public static String format_yyyy_MM_dd_HH_mm_12_EN = "yyyy-MM-dd hh:mm";
    /**
     * 英文时分日期格式：2014-01-01 18:30
     */
    public static String format_yyyy_MM_dd_HH_mm_24_EN = "yyyy-MM-dd HH:mm";
    /**
     * 中文时分日期格式：2014年01月01日 6时30分
     */
    public static String format_yyyy_MM_dd_HH_mm_12_CN = "yyyy年MM月dd日 hh时mm分";
    /**
     * 中文时分日期格式：2014年01月01日 18时30分
     */
    public static String format_yyyy_MM_dd_HH_mm_24_CN = "yyyy年MM月dd日 HH时mm分";

    /**
     * 英文日期格式：2014-01-01
     */
    public static String format_yyyy_MM_dd_EN = "yyyy-MM-dd";
    public static String format_yyyy_MM_EN = "yyyy-MM";
    public static final String FORMAT_YY_MM_DD_EN_SLASH = "yy/MM/dd";
    /**
     * 中文日期格式：2014年01月01日
     */
    public static String format_yyyy_MM_dd_CN = "yyyy年MM月dd日";
    /**
     * 中文日期格式：2014年01月
     */
    public static String format_yyyy_MM_CN = "yyyy年MM月";

    /**
     * 英文格式：01-01
     */
    public static String format_MM_dd_EN = "MM-dd";
    /**
     * 中文格式：01月01日
     */
    public static String format_MM_dd_CN = "MM月dd日";

    /**
     * 英文格式：2014
     */
    public static String format_YYYY_EN = "yyyy";
    /**
     * 中文格式：2014年
     */
    public static String format_YYYY_CN = "yyyy年";

    /**
     * 英文格式：01
     */
    public static String format_MM_EN = "MM";
    /**
     * 中文格式：01月
     */
    public static String format_MM_CN = "MM月";

    /**
     * 英文格式：01
     */
    public static String format_dd_EN = "dd";
    /**
     * 中文格式：01日
     */
    public static String format_dd_CN = "dd日";

    /**
     * 英文时间格式：6:30:30
     */
    public static String format_HH_mm_ss_12_EN = "hh:mm:ss";
    /**
     * 英文时间格式：18:30:30
     */
    public static String format_HH_mm_ss_24_EN = "HH:mm:ss";
    /**
     * 中文时间格式：6时30分30秒
     */
    public static String format_HH_mm_ss_12_CN = "hh时mm分ss秒";
    /**
     * 中文时间格式：18时30分30秒
     */
    public static String format_HH_mm_ss_24_CN = "HH时mm分ss秒";

    /**
     * 英文时间格式：6:30
     */
    public static String format_HH_mm_12_EN = "hh:mm";
    /**
     * 英文时间格式：18:30
     */
    public static String format_HH_mm_24_EN = "HH:mm";
    /**
     * 中文时间格式：6时30分
     */
    public static String format_HH_mm_12_CN = "hh时mm分";
    /**
     * 中文时间格式：18时30分
     */
    public static String format_HH_mm_24_CN = "HH时mm分";

    /**
     * 英文时分秒日期格式：2014-01-01 18:30:30
     */
    public static String format_yyyy_MM_dd_HH_mm_ss_SSS_24_EN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 日期格式：2014.01.01
     */
    public static String format_yyyy_MM_dd_point = "yyyy.MM.dd";

    private static Calendar mCalendar = Calendar.getInstance();

    /**
     * 日期
     */
    public static final String JUSTNOW = "刚刚";
    public static final String MINUTE_AGO = "分钟前";
    public static final String HOUR_AGO = "小时前";

    public static final String TODAY = "今天";
    public static final String YESTERDAY = "昨天";
    public static final String BEFORE_YESTERDAY = "前天";

    public static final String TOMORROW = "明天";
    public static final String AFTER_TOMORROW = "后天";

    public static final String SECOND_AFTER = "秒后";
    public static final String MINUTE_AFTER = "分钟后";
    public static final String HOUR_AFTER = "小时后";

    public static final String MONDAY_EN = "MON";
    public static final String TUESDAY_EN = "TUE";
    public static final String WEDNESDAY_EN = "WED";
    public static final String THURSDAY_EN = "THU";
    public static final String FRIDAY_EN = "FRI";
    public static final String SATURDAY_EN = "SAT";
    public static final String SUNDAY_EN = "SUN";

    public static final String MONDAY_CN = "星期一";
    public static final String TUESDAY_CN = "星期二";
    public static final String WEDNESDAY_CN = "星期三";
    public static final String THURSDAY_CN = "星期四";
    public static final String FRIDAY_CN = "星期五";
    public static final String SATURDAY_CN = "星期六";
    public static final String SUNDAY_CN = "星期日";

    /**
     * 获取设备采用的时间制式(12小时制式或者24小时制式) <br>
     * 注意: 在模拟器上获取的时间制式为空
     *
     * @param context
     * @return
     * @since v0.0.1
     */
    public static int getTIME_12_24(Context context) {
        String strTimeFormat_12_24 = "";
        try {
            ContentResolver cv = context.getContentResolver();
            strTimeFormat_12_24 = android.provider.Settings.System.getString(cv, android.provider.Settings.System.TIME_12_24);
            if (strTimeFormat_12_24 != null && "24".equals(strTimeFormat_12_24)) {
                return 24;
            }
            if (strTimeFormat_12_24 != null && "12".equals(strTimeFormat_12_24)) {
                return 12;
            }
            return 24;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return 24;
        }
    }

    /**
     * 获取时间显示制式：上午／下午 or AM／PM <br>
     * 如果是24制，则返回""
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getTIME_AM_PM(Date dateIn) {
        String am_pm_String = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
//        if (calendar.get(Calendar.AM_PM) == 0) {
//            // 中文显示格式
//            if (Locale.getDefault().getLanguage().equalsIgnoreCase("zh")) {
//                am_pm_String = "上午";
//            } else {
//                am_pm_String = "AM";
//            }
//        } else {
//            // 中文显示格式
//            if (Locale.getDefault().getLanguage().equalsIgnoreCase("zh")) {
//                am_pm_String = "下午";
//            } else {
//                am_pm_String = "PM";
//            }
//        }
        return am_pm_String;
    }

    /**
     * 获取今天的日期
     *
     * @return
     * @since v0.0.1
     */
    public static Date getDateNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 判断给定日期是否是今天
     *
     * @return
     * @since v0.0.1
     */
    public static boolean isToday(Date dateIn) {
        return isSameDayOfDate(getDateNow(), dateIn);
    }

    /**
     * 判断给定日期是否是昨天
     *
     * @return
     * @since v0.0.1
     */
    public static boolean isYesterday(Date dateIn) {
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(getDateNow());
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);

        // 判断是否跨天
        int d_over = calendarNow.get(Calendar.DAY_OF_YEAR) - calendarIn.get(Calendar.DAY_OF_YEAR);
        // 判断是否跨年
        int y_over = calendarNow.get(Calendar.YEAR) - calendarIn.get(Calendar.YEAR);

        if (y_over == 0 && d_over == 1) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断给定日期是否是今天
     *
     * @return
     * @since v0.0.1
     */
    public static boolean isTomorrow(Date dateIn) {
        Calendar compared = Calendar.getInstance();
        compared.setTime(dateIn);

        return isSameDayOfDate(Calendar.getInstance(), compared);
    }

    /**
     * 加减n年后的日期
     *
     * @param dateIn
     * @param intYear
     * @return
     * @since v0.0.1
     */
    public static Date getDateAddYears(Date dateIn, int intYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.add(Calendar.YEAR, intYear);
        return calendar.getTime();
    }

    /**
     * 加减n月后的日期
     *
     * @param dateIn
     * @param intMonth
     * @return
     * @since v0.0.1
     */
    public static Date getDateAddMonths(Date dateIn, int intMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.add(Calendar.MONTH, intMonth);
        return calendar.getTime();
    }

    /**
     * 加减n天后的日期
     *
     * @param dateIn
     * @param intDay
     * @return
     * @since v0.0.1
     */
    public static Date getDateAddDays(Date dateIn, int intDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.add(Calendar.DAY_OF_YEAR, intDay);
        return calendar.getTime();
    }

    /**
     * 加减n小时后的日期
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static Date getDateAddHours(Date dateIn, int intHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.add(Calendar.HOUR, intHour);
        return calendar.getTime();
    }

    /**
     * 加减n分钟后的日期
     *
     * @param dateIn
     * @param intMinute
     * @return
     * @since v0.0.1
     */
    public static Date getDateAddMinutes(Date dateIn, int intMinute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.add(Calendar.MINUTE, intMinute);
        return calendar.getTime();
    }

    /**
     * 加减n秒后的日期
     *
     * @param dateIn
     * @param intSecond
     * @return
     * @since v0.0.1
     */
    public static Date getDateAddSeconds(Date dateIn, int intSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.add(Calendar.SECOND, intSecond);
        return calendar.getTime();
    }

    /**
     * 判断两个日期是否在同一天.
     *
     * @param dateLeft
     * @param dateRight
     * @return
     * @since v0.0.1
     */
    public static boolean isSameDayOfDate(Date dateLeft, Date dateRight) {
        Calendar ltime = Calendar.getInstance();
        ltime.setTime(dateLeft);
        Calendar rTime = Calendar.getInstance();
        rTime.setTime(dateRight);
        return isSameDayOfDate(ltime, rTime);
    }

    /**
     * 判断两个日期是否在同一天.
     *
     * @param calendarLeft
     * @param calendarRight
     * @return
     */
    public static boolean isSameDayOfDate(Calendar calendarLeft, Calendar calendarRight) {
        if (calendarLeft == null || calendarRight == null)
            return false;

        if (Math.abs(calendarLeft.getTimeInMillis() - calendarRight.getTimeInMillis()) > DAY_OF_MILLISECOND) {
            return false;
        }

        return calendarLeft.get(Calendar.YEAR) == calendarRight.get(Calendar.YEAR) && calendarLeft.get(Calendar.MONTH) == calendarRight.get(Calendar.MONTH) && calendarLeft.get(Calendar.DAY_OF_MONTH) == calendarRight.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日期是所在年份的第几月
     *
     * @param dateIn
     * @return
     */
    public static int getMonthOfYear(Date dateIn) {
        mCalendar.setTime(dateIn);
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期是所在年份的第几周
     *
     * @param dateIn
     * @return
     */
    public static int getWeekOfYear(Date dateIn) {
        mCalendar.setTime(dateIn);
        return mCalendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取日期是所在月份的第几周
     *
     * @param dateIn
     * @return
     */
    public static int getWeekOfMonth(Date dateIn) {
        mCalendar.setTime(dateIn);
        return mCalendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取日期是所在年份的第几天
     *
     * @param dateIn
     * @return
     */
    public static int getDayOfYear(Date dateIn) {
        mCalendar.setTime(dateIn);
        return mCalendar.get(Calendar.DAY_OF_YEAR);

    }

    /**
     * 获取日期是所在月份的第几天
     *
     * @param dateIn
     * @return
     */
    public static int getDayOfMonth(Date dateIn) {
        mCalendar.setTime(dateIn);
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日期是星期几
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDayOfWeekEN(Date dateIn) {
        Calendar target = Calendar.getInstance();
        target.setTime(dateIn);
        int dayForWeek = 0;
        dayForWeek = target.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                return SUNDAY_EN;
            case 2:
                return MONDAY_EN;
            case 3:
                return TUESDAY_EN;
            case 4:
                return WEDNESDAY_EN;
            case 5:
                return THURSDAY_EN;
            case 6:
                return FRIDAY_EN;
            case 7:
                return SATURDAY_EN;
            default:
                return "NA";
        }
    }

    /**
     * 获取日期是星期几
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDayOfWeekCN(Date dateIn) {
        Calendar target = Calendar.getInstance();
        target.setTime(dateIn);
        int dayForWeek = 0;
        dayForWeek = target.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                return SUNDAY_CN;
            case 2:
                return MONDAY_CN;
            case 3:
                return TUESDAY_CN;
            case 4:
                return WEDNESDAY_CN;
            case 5:
                return THURSDAY_CN;
            case 6:
                return FRIDAY_CN;
            case 7:
                return SATURDAY_CN;
            default:
                return "NA";
        }
    }

    /**
     * 获取日期所在月份的第一天日期
     *
     * @param dateIn 指定日期
     * @return 月份的第一天
     */
    public static Date getDayOfMonthFirst(Date dateIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取日期所在月份的最后一天日期
     *
     * @param dateIn 指定日期
     * @return 月份的最后一天
     */
    public static Date getDayOfMonthLast(Date dateIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取某一天的起始时间.
     *
     * @param dateIn
     * @return
     */
    public static Date getTimeOfDayStart(Date dateIn) {
        mCalendar.setTime(dateIn);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

        return mCalendar.getTime();
    }

    /**
     * 获取某一天的结束时间.
     *
     * @param dateIn
     * @return
     */
    public static Date getTimeOfDayEnd(Date dateIn) {
        mCalendar.setTime(dateIn);
        mCalendar.set(Calendar.HOUR_OF_DAY, 23);
        mCalendar.set(Calendar.MINUTE, 59);
        mCalendar.set(Calendar.SECOND, 59);
        mCalendar.set(Calendar.MILLISECOND, 999);
        return mCalendar.getTime();
    }

    /**
     * 两个日期之间的差值：年
     *
     * @param dateLeft
     * @param dateRight
     * @return
     * @since v0.0.1
     */
    public static long getDateDifferenceYears(Date dateLeft, Date dateRight) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateLeft);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateRight);

        long returnYears = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
        return returnYears;
    }

    /**
     * 两个日期之间的差值：月
     *
     * @param dateLeft
     * @param dateRight
     * @return
     * @since v0.0.1
     */
    public static long getDateDifferenceMonths(Date dateLeft, Date dateRight) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateLeft);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateRight);

        long returnYears = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
        long returnMonths = calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);
        returnMonths = returnYears * 12 + returnMonths;
        return returnMonths;
    }

    /**
     * 两个日期之间的差值：天
     *
     * @param dateLeft
     * @param dateRight
     * @return
     * @since v0.0.1
     */
    public static long getDateDifferenceDays(Date dateLeft, Date dateRight) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateLeft);
        calendar1.set(Calendar.HOUR_OF_DAY, 1);
        calendar1.set(Calendar.SECOND, 2);
        calendar1.set(Calendar.MINUTE, 2);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateRight);
        calendar2.set(Calendar.HOUR_OF_DAY, 1);
        calendar2.set(Calendar.SECOND, 1);
        calendar2.set(Calendar.MINUTE, 1);
        long returnDays = (calendar1.getTimeInMillis() - calendar2.getTimeInMillis()) / DAY_OF_MILLISECOND;
        return returnDays;
    }

    /**
     * 两个日期之间的差值：小时
     *
     * @param dateLeft
     * @param dateRight
     * @return
     * @since v0.0.1
     */
    public static long getDateDifferenceHours(Date dateLeft, Date dateRight) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateLeft);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateRight);

        long returnHours = (calendar1.getTimeInMillis() - calendar2.getTimeInMillis()) / HOUR_OF_MILLISECOND;
        return returnHours;
    }

    /**
     * 两个日期之间的差值：分钟
     *
     * @param dateLeft
     * @param dateRight
     * @return
     * @since v0.0.1
     */
    public static long getDateDifferenceMinutes(Date dateLeft, Date dateRight) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateLeft);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateRight);

        long returnMinutes = (calendar1.getTimeInMillis() - calendar2.getTimeInMillis()) / MINUTE_OF_MILLISECOND;
        return returnMinutes;
    }

    /**
     * 两个日期之间的差值：秒
     *
     * @param dateLeft
     * @param dateRight
     * @return
     * @since v0.0.1
     */
    public static long getDateDifferenceSeconds(Date dateLeft, Date dateRight) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateLeft);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateRight);

        long returnSeconds = (calendar1.getTimeInMillis() - calendar2.getTimeInMillis()) / 1000;
        return returnSeconds;
    }

    /**
     * 获取日期的：年 <br>
     * 例如：2014
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static int getDateYearInt(Date dateIn) {
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);
        return calendarIn.get(Calendar.YEAR);
    }

    /**
     * 获取日期的：年 <br>
     * 例如："2014"
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateYearString(Date dateIn) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_yyyy);
        return sdf.format(dateIn);
    }

    /**
     * 获取日期的：月 <br>
     * 例如：9 (Calendar.MONTH 返回的月需要加上1)
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static int getDateMonthInt(Date dateIn) {
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);
        return calendarIn.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的：月 <br>
     * 例如："09"
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateMonthString(Date dateIn) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_MM);
        return sdf.format(dateIn);
    }

    /**
     * 获取日期的：日 <br>
     * 例如：31 or 1
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static int getDateDayInt(Date dateIn) {
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);
        return calendarIn.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日期的：日 <br>
     * 例如："31" or "01"
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateDayString(Date dateIn) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_dd);
        return sdf.format(dateIn);
    }

    /**
     * 获取日期的：小时 <br>
     * 例如：9
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static int getDateHourInt(Date dateIn) {
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);
        return calendarIn.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的：小时 <br>
     * 例如："09"
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateHourString(Date dateIn) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_HH);
        return sdf.format(dateIn);
    }

    /**
     * 获取日期的：分钟 <br>
     * 例如：9
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static int getDateMinuteInt(Date dateIn) {
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);
        return calendarIn.get(Calendar.MINUTE);
    }

    /**
     * 获取日期的：分钟 <br>
     * 例如："09"
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateMinuteString(Date dateIn) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_mm);
        return sdf.format(dateIn);
    }

    /**
     * 获取日期的：秒 <br>
     * 例如：9
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static int getDateSecondInt(Date dateIn) {
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);
        return calendarIn.get(Calendar.SECOND);
    }

    /**
     * 获取日期的：秒 <br>
     * 例如："09"
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateSecondString(Date dateIn) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_ss);
        return sdf.format(dateIn);
    }

    // TODO: 各种字符串转化日期格式的处理
    /*************************************** 各种字符串转化日期格式的处理 ***************************************/
    /**
     * 将字符串(标准格式)======>日期时间
     *
     * @return
     */
    public static String getStringToDate_YYYY_MM_DD_HH_MM_SS_EN_point(Context context, Long lngTimeStamp) {
        String format = format_yyyy_MM_dd_HH_mm_ss_24_EN_point;
        if (getTIME_12_24(context) == 12) {
            format = format_yyyy_MM_dd_HH_mm_ss_12_EN_point;
        }
        SimpleDateFormat StringToDate = new SimpleDateFormat(format);
        return StringToDate.format(getTimeStampToDate(lngTimeStamp));
    }

    /**
     * 将字符串(中文格式)======>日期时间
     *
     * @param stringValue "yyyy年MM月dd日 HH时mm分ss秒"
     * @return
     */
    public static Date getStringToDate_YYYY_MM_DD_HH_MM_SS_CN(Context context, String stringValue) {
        String format = format_yyyy_MM_dd_HH_mm_ss_24_CN;
        if (getTIME_12_24(context) == 12) {
            format = format_yyyy_MM_dd_HH_mm_ss_12_CN;
        }
        SimpleDateFormat StringToDate = new SimpleDateFormat(format);
        Date returnDate = new Date();
        try {
            returnDate = StringToDate.parse(stringValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 将字符串(标准格式)======>日期时间
     *
     * @param stringValue "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static Date getStringToDate_YYYY_MM_DD_HH_MM_EN(Context context, String stringValue) {
        String format = format_yyyy_MM_dd_HH_mm_24_EN;
        if (getTIME_12_24(context) == 12) {
            format = format_yyyy_MM_dd_HH_mm_12_EN;
        }
        SimpleDateFormat StringToDate = new SimpleDateFormat(format);
        Date returnDate = new Date();
        try {
            returnDate = StringToDate.parse(stringValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 将字符串(中文格式)======>日期时间
     *
     * @param stringValue "yyyy年MM月dd日 HH时mm分"
     * @return
     */
    public static Date getStringToDate_YYYY_MM_DD_HH_MM_CN(Context context, String stringValue) {
        String format = format_yyyy_MM_dd_HH_mm_24_CN;
        if (getTIME_12_24(context) == 12) {
            format = format_yyyy_MM_dd_HH_mm_12_CN;
        }
        SimpleDateFormat StringToDate = new SimpleDateFormat(format);
        Date returnDate = new Date();
        try {
            returnDate = StringToDate.parse(stringValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 将字符串(标准格式)======>日期时间
     *
     * @param stringValue "yyyy-MM-dd"
     * @return
     */
    public static Date getStringToDate_YYYY_MM_DD_EN(String stringValue) {
        SimpleDateFormat StringToDate = new SimpleDateFormat(format_yyyy_MM_dd_EN);
        Date returnDate = new Date();
        try {
            returnDate = StringToDate.parse(stringValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    /**
     * 将字符串(中文格式)======>日期时间
     *
     * @param stringValue "yyyy年MM月dd日"
     * @return
     */
    public static Date getStringToDate_YYYY_MM_DD_CN(String stringValue) {
        SimpleDateFormat StringToDate = new SimpleDateFormat(format_yyyy_MM_dd_EN);
        Date returnDate = new Date();
        try {
            returnDate = StringToDate.parse(stringValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnDate;
    }

    // TODO:日期转化各种字符串格式的处理
    /*************************************** 日期转化各种字符串格式的处理 ***************************************/
    /**
     * 将日期转换为英文字符串（2014-01-01 12:30:30）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_YYYY_MM_DD_HH_MM_SS_EN(Context context, Date dateIn) {
        String format = format_yyyy_MM_dd_HH_mm_ss_24_EN;
        if (getTIME_12_24(context) == 12) {
            format = format_yyyy_MM_dd_HH_mm_ss_12_EN;
        }
        SimpleDateFormat DateToString = new SimpleDateFormat(format);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为中文字符串（2014年01月01日 12时30分30秒）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_YYYY_MM_DD_HH_MM_SS_CN(Context context, Date dateIn) {
        String format = format_yyyy_MM_dd_HH_mm_ss_24_CN;
        if (getTIME_12_24(context) == 12) {
            format = format_yyyy_MM_dd_HH_mm_ss_12_CN;
        }
        SimpleDateFormat DateToString = new SimpleDateFormat(format);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为英文字符串（2014-01-01 12:30）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_YYYY_MM_DD_HH_MM_EN(Context context, Date dateIn) {
        String format = format_yyyy_MM_dd_HH_mm_24_EN;
        if (getTIME_12_24(context) == 12) {
            format = format_yyyy_MM_dd_HH_mm_12_EN;
        }
        SimpleDateFormat DateToString = new SimpleDateFormat(format);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为中文字符串（2014年01月01日 12时30分）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_YYYY_MM_DD_HH_MM_CN(Context context, Date dateIn) {
        String format = format_yyyy_MM_dd_HH_mm_24_CN;
        if (getTIME_12_24(context) == 12) {
            format = format_yyyy_MM_dd_HH_mm_12_CN;
        }
        SimpleDateFormat DateToString = new SimpleDateFormat(format);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为英文字符串（2014-01-01）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_YYYY_MM_DD_EN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_yyyy_MM_dd_EN);
        return DateToString.format(dateIn);
    }

    public static String getStringToString_YYYY_MM_DD_EN(String datein) {
        Date date = getTimeStampToDate(datein);
        SimpleDateFormat DateToString = new SimpleDateFormat(format_yyyy_MM_dd_EN);
        return DateToString.format(date);
    }

    public static String getDateToString_YYYY_MM_EN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_yyyy_MM_EN);
        return DateToString.format(dateIn);
    }

    public static String getDateToString_YYYY_MM_DD_EN_SLASHN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(FORMAT_YY_MM_DD_EN_SLASH);
        return DateToString.format(dateIn);
    }


    /**
     * 将日期转换为中文字符串（2014年01月01日）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_YYYY_MM_DD_CN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_yyyy_MM_dd_CN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期时间戳转换为中文字符串（2014年01月）
     */
    public static String getTimeStampToString_YYYY_MM_DD_CN(long lngTimeStamp) {
        Date dateIn = getTimeStampToDate(lngTimeStamp);
        SimpleDateFormat DateToString = new SimpleDateFormat(format_yyyy_MM_CN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为英文字符串（01-01）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_MM_DD_EN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_MM_dd_EN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为中文字符串（01月01日）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_MM_DD_CN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_MM_dd_CN);
        return DateToString.format(dateIn);
    }


    /**
     * 将日期转换为英文字符串（2014）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_YYYY_EN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_YYYY_EN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为中文字符串（2014年）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_YYYY_CN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_YYYY_CN);
        return DateToString.format(dateIn);
    }

    public static String getDateToString_YYYY_MM_DD_point(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_yyyy_MM_dd_point);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为英文字符串（01）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_MM_EN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_MM_EN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为中文字符串（01月）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_MM_CN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_MM_CN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为英文字符串（01）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_DD_EN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_dd_EN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为中文字符串（01日）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_DD_CN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_dd_CN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为英文字符串（12:30:30） <br>
     * 其中处理了12/24制式对应的 AM/PM 和 上午／下午的问题
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_HH_MM_SS_EN(Context context, Date dateIn) {
        if (getTIME_12_24(context) == 12) {
            SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_ss_12_EN);
            return getTIME_AM_PM(dateIn) + " " + DateToString.format(dateIn);
        } else {
            SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_ss_24_EN);
            return DateToString.format(dateIn);
        }
    }

    /**
     * 将日期转换为中文字符串（12时30分30秒） <br>
     * 其中处理了12/24制式对应的 AM/PM 和 上午／下午的问题
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_HH_MM_SS_CN(Context context, Date dateIn) {
        if (getTIME_12_24(context) == 12) {
            SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_ss_12_CN);
            return getTIME_AM_PM(dateIn) + " " + DateToString.format(dateIn);
        } else {
            SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_ss_24_CN);
            return DateToString.format(dateIn);
        }
    }

    /**
     * 将日期转换为英文字符串（12:30）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_HH_MM_EN(Context context, Date dateIn) {
        if (getTIME_12_24(context) == 12) {
            SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_12_EN);
            return getTIME_AM_PM(dateIn) + " " + DateToString.format(dateIn);
        } else {
            SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_24_EN);
            return DateToString.format(dateIn);
        }
    }

    public static String getDateToString_HH_MM_EN_24(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_24_EN);
        return DateToString.format(dateIn);
    }

    /**
     * 将日期转换为中文字符串（12时30分）
     *
     * @return
     * @since v0.0.1
     */
    public static String getDateToString_HH_MM_CN(Context context, Date dateIn) {
        if (getTIME_12_24(context) == 12) {
            SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_12_CN);
            return getTIME_AM_PM(dateIn) + " " + DateToString.format(dateIn);
        } else {
            SimpleDateFormat DateToString = new SimpleDateFormat(format_HH_mm_24_CN);
            return DateToString.format(dateIn);
        }
    }

    /**
     * 获取两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String getDateYearAndDay(String date1, String date2) {
        try {
            Calendar calst = Calendar.getInstance();
            Calendar caled = Calendar.getInstance();

            if (TextUtils.isEmpty(date1)) {
                date1 = String.valueOf(System.currentTimeMillis());
            }

            caled.setTime(new Date(Long.parseLong(date1)));
            calst.setTime(new Date(Long.parseLong(date2)));

            caled.set(Calendar.HOUR_OF_DAY, 0);
            caled.set(Calendar.MINUTE, 0);
            caled.set(Calendar.SECOND, 0);
            caled.set(Calendar.MILLISECOND, 0);

            calst.set(Calendar.HOUR_OF_DAY, 0);
            calst.set(Calendar.MINUTE, 0);
            calst.set(Calendar.SECOND, 0);
            calst.set(Calendar.MILLISECOND, 0);

            //得到两个日期相差的天数
            long days = (caled.getTime().getTime() - calst.getTime().getTime()) / (1000 * 60 * 60 * 24);
            String year = (days > 365 ? (int) days / 365 + "岁" : "");
            String moth = days > 365 ? (((int) days % 365 / 30 == 0) ? "1个月"
                    : ((int) days % 365 / 30 + "个月")) : ((days / 30 == 0) ? "" : (days) / 30 + "个月");
            String day = days % 365 % 30 + "天";

            String msg = !year.equals("") ? year + moth : moth + day;
            LogUtils.d("hwp", "================" + msg);
            return msg + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final int YEAR_RETURN = 0;

    public static final int MONTH_RETURN = 1;

    public static final int DAY_RETURN = 2;

    public static final int HOUR_RETURN = 3;

    public static final int MINUTE_RETURN = 4;

    public static final int SECOND_RETURN = 5;

    public static final String YYYYMMDD = "yyyy-MM-dd";

    public static long getBetween(String beginTime, String endTime, String formatPattern, int returnPattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYYMMDD);
            Date beginDate = new Date(Long.parseLong(beginTime));
            Date endDate = new Date(Long.parseLong(endTime));

            Calendar beginCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            beginCalendar.setTime(beginDate);
            endCalendar.setTime(endDate);
            switch (returnPattern) {
                case YEAR_RETURN:
                    return DateUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR);
                case MONTH_RETURN:
                    return DateUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR) * 12 + DateUtils.getByField(beginCalendar, endCalendar, Calendar.MONTH);
                case DAY_RETURN:
                    return DateUtils.getTime(beginDate, endDate) / (24 * 60 * 60 * 1000);
                case HOUR_RETURN:
                    return DateUtils.getTime(beginDate, endDate) / (60 * 60 * 1000);
                case MINUTE_RETURN:
                    return DateUtils.getTime(beginDate, endDate) / (60 * 1000);
                case SECOND_RETURN:
                    return DateUtils.getTime(beginDate, endDate) / 1000;
                default:
                    return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static long getByField(Calendar beginCalendar, Calendar endCalendar, int calendarField) {
        return endCalendar.get(calendarField) - beginCalendar.get(calendarField);
    }

    private static long getTime(Date beginDate, Date endDate) {
        return endDate.getTime() - beginDate.getTime();
    }

    /*
    *计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟
    * 根据差值返回多长之间前或多长时间后
    * */
    public static String getDistanceTime(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag;
        if (time1 < time2) {
            diff = time2 - time1;
            flag = "前";
        } else {
            diff = time1 - time2;
            flag = "后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天" + flag;
        if (hour != 0) return hour + "小时" + flag;
        if (min != 0) return min + "分钟" + flag;
        return "刚刚";
    }

    /**
     * 得到当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }


    // TODO:时间戳和日期转换方式处理
    /*************************************** 时间戳和日期转换方式处理 ***************************************/
    /**
     * 将成日期转化时间戳
     *
     * @return long
     * @since v0.0.1
     */
    public static long getDateToTimeStamp(Date dateIn) {
        mCalendar.setTime(dateIn);
        return mCalendar.getTimeInMillis();
    }

    /**
     * 将成日期转化时间戳
     *
     * @return String
     * @since v0.0.1
     */
    public static String getDateToTimeStampString(Date dateIn) {
        return String.valueOf(getDateToTimeStamp(dateIn));
    }

    /**
     * 将时间戳转化成日期
     *
     * @return
     * @since v0.0.1
     */
    public static Date getTimeStampToDate(long lngTimeStamp) {
        if (String.valueOf(lngTimeStamp).length() == 10) {
            mCalendar.setTimeInMillis(lngTimeStamp * 1000);
        } else {
            mCalendar.setTimeInMillis(lngTimeStamp);
        }
        return mCalendar.getTime();
    }

    /**
     * 将时间戳转化成日期
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static Date getTimeStampToDate(String strTimeStamp) {
        return getTimeStampToDate(Long.parseLong(strTimeStamp));
    }

    // TODO:时间戳转化各种字符串格式的处理
    /*************************************** 时间戳转化各种字符串格式的处理 ***************************************/
    /**
     * 将日期转换为英文字符串（2014-01-01 12:30:30）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToStringYYYY_MM_DD_HH_MM_SS_EN(Context context, String strTimeStamp) {
        return getDateToString_YYYY_MM_DD_HH_MM_SS_EN(context, getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToStringYYYY_MM_DD_HH_MM_SS_EN(Context context, long lngTimeStamp) {
        return getDateToString_YYYY_MM_DD_HH_MM_SS_EN(context, getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 将日期转换为中文字符串（2014年01月01日 12时30分30秒）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToStringYYYY_MM_DD_HH_MM_SS_CN(Context context, String strTimeStamp) {
        return getDateToString_YYYY_MM_DD_HH_MM_SS_CN(context, getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToStringYYYY_MM_DD_HH_MM_SS_CN(Context context, long lngTimeStamp) {
        return getDateToString_YYYY_MM_DD_HH_MM_SS_CN(context, getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long date2Time(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static Long strToDate(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /*
     * 获取距离现在多长时间，转换为年月日
     */
    public static String getBetweenTime(int historYear, int historyMonth, int historyDay) {
        String date = "";
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Calendar birthday = new GregorianCalendar(historYear, historyMonth, historyDay, 0, 0, 0);//2010年10月12日，month从0开始  （6代表的是5月）
        Calendar now = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), 23, 59, 59);//2010年10月12日，month从0开始
        int day = now.get(Calendar.DATE) - birthday.get(Calendar.DATE);
        int month = now.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (day < 0) {
            month -= 1;
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }
        if (year <= 0) {
            date = month + "个月" + day + "天";
        } else {
            date = year + "岁" + month + "个月" + day + "天";
        }
        return date;
    }

    /**
     * 将日期转换为英文字符串（2014-01-01 12:30）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToStringYYYY_MM_DD_HH_MM_EN(Context context, String strTimeStamp) {
        return getDateToString_YYYY_MM_DD_HH_MM_EN(context, getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToStringYYYY_MM_DD_HH_MM_EN(Context context, long lngTimeStamp) {
        return getDateToString_YYYY_MM_DD_HH_MM_EN(context, getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 将日期转换为中文字符串（2014年01月01日 12时30分）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToStringYYYY_MM_DD_HH_MM_CN(Context context, String strTimeStamp) {
        return getDateToString_YYYY_MM_DD_HH_MM_CN(context, getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToStringYYYY_MM_DD_HH_MM_CN(Context context, long lngTimeStamp) {
        return getDateToString_YYYY_MM_DD_HH_MM_CN(context, getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 将日期转换为英文字符串（2014-01-01）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToYYYY_MM_DD_EN(String strTimeStamp) {
        return getDateToString_YYYY_MM_DD_EN(getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToYYYY_MM_DD_EN(long lngTimeStamp) {
        return getDateToString_YYYY_MM_DD_EN(getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 将日期转换为中文字符串（2014年01月01日）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToYYYY_MM_DD_CN(String strTimeStamp) {
        return getDateToString_YYYY_MM_DD_CN(getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToYYYY_MM_DD_CN(long lngTimeStamp) {
        return getDateToString_YYYY_MM_DD_CN(getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 将日期转换为英文字符串（01-01）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToString_MM_DD_EN(String strTimeStamp) {
        return getDateToString_MM_DD_EN(getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToString_MM_DD_EN(long lngTimeStamp) {
        return getDateToString_MM_DD_EN(getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 将日期转换为中文字符串（01月01日）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToString_MM_DD_CN(String strTimeStamp) {
        return getDateToString_MM_DD_CN(getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToString_MM_DD_CN(long lngTimeStamp) {
        return getDateToString_MM_DD_CN(getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 将日期转换为英文字符串（12:30:30）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToString_HH_MM_SS_EN(Context context, String strTimeStamp) {
        return getDateToString_HH_MM_SS_EN(context, getTimeStampToDate(strTimeStamp));
    }

    public static String getTimeStampToString_HH_MM_SS_EN(Context context, long lngTimeStamp) {
        return getDateToString_HH_MM_SS_EN(context, getTimeStampToDate(String.valueOf(lngTimeStamp)));
    }

    /**
     * 将日期转换为中文字符串（12时30分30秒）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToString_HH_MM_SS_CN(Context context, String strTimeStamp) {
        return getDateToString_HH_MM_SS_CN(context, getTimeStampToDate(strTimeStamp));
    }

    /**
     * 将日期转换为英文字符串（12:30）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToString_HH_MM_EN(Context context, String strTimeStamp) {
        return getDateToString_HH_MM_EN(context, getTimeStampToDate(strTimeStamp));
    }

    /**
     * 获取日期
     * *今天，显示时分，24小时制。
     * 昨天及昨天之前，显示月日
     * 今年之前，显示年月日
     */
    public static String getTime(Long date) {
        String result = "";
        if (date.longValue() <= 0) {
            return result;
        }
        Date nowDate = new Date(System.currentTimeMillis());
        Date publishdate = new Date(date);
        SimpleDateFormat sdsd = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int currrentYear = Integer.parseInt(sdsd.format(nowDate));
        int publishYear = Integer.parseInt(sdsd.format(publishdate));
        if (currrentYear == publishYear) {//发布日期的年份是否和现在的年份一致
            String currentDate = sdf.format(nowDate);
            String pubDate = sdf.format(publishdate);
            if (TextUtils.equals(currentDate, pubDate)) {//发布日期在今天
                SimpleDateFormat sdfs = new SimpleDateFormat("HH:mm");
                result = sdfs.format(new Date(date));
            } else {
                SimpleDateFormat sdfd = new SimpleDateFormat("MM-dd HH:mm");
                result = sdfd.format(new Date(date));
            }
        } else {
            SimpleDateFormat sdfsd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            result = sdfsd.format(new Date(date));
        }
        return result;
    }

    public static boolean compareDate(String starTime, String endTime) {
        boolean small = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd kk:mm");
            Date startDate = sdf.parse(starTime);
            Date endDate = sdf.parse(endTime);
            if (endDate.getTime() < startDate.getTime()) {
                small = true;
            } else {
                small = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return small;
    }

    /**
     * 将日期转换为中文字符串（12时30分）
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToString_HH_MM_CN(Context context, String strTimeStamp) {
        return getDateToString_HH_MM_CN(context, getTimeStampToDate(strTimeStamp));
    }

    // TODO:特定的时间字符串显示格式处理
    /*************************************** 特定的时间字符串显示格式处理 ***************************************/
    /**
     * 将给定日期和当前时间的差值转为英文字符串显示(5d ago或 5d after)
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateDifferenceToStringEN(Date dateIn) {
        String strOut = "1d";
        String strFrontOrBack = " ago";

        Calendar c1 = Calendar.getInstance();
        c1.setTime(getDateNow());
        Calendar c2 = Calendar.getInstance();
        c2.setTime(dateIn);
        int lngDuration = (c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR)) * 24 * 60 * 60;
        lngDuration += (c1.get(Calendar.HOUR_OF_DAY) - c2.get(Calendar.HOUR_OF_DAY)) * 60 * 60;
        lngDuration += (c1.get(Calendar.MINUTE) - c2.get(Calendar.MINUTE)) * 60;
        lngDuration += (c1.get(Calendar.SECOND) - c2.get(Calendar.SECOND)) * 1;
        if (lngDuration >= 0) {
            strFrontOrBack = " ago";
        } else {
            strFrontOrBack = " after";
        }
        lngDuration = Math.abs(lngDuration);
        int YY = 0;
        int MM = 0;
        int ww = 0;
        int dd = lngDuration / 24 * 60 * 60;
        if (dd > 7) {
            ww = dd / 7;
            if (ww > 4)
                MM = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
            if (MM > 12)
                YY = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        }
        int hh = lngDuration / 60 * 60;
        int mm = lngDuration / 60;
        int ss = lngDuration % 60;

        if (YY > 0) {
            strOut = Math.abs(YY) + "Y";
        } else {
            if (MM > 0) {
                strOut = Math.abs(MM) + "M";
            } else {
                if (ww > 0) {
                    strOut = Math.abs(ww) + "w";
                } else {
                    if (dd > 0) {
                        strOut = Math.abs(dd) + "d";
                    } else {
                        if (hh > 0) {
                            strOut = Math.abs(hh) + "h";
                        } else {
                            if (mm > 0) {
                                strOut = Math.abs(mm) + "m";
                            } else {
                                strOut = Math.abs(ss) + "s";
                            }
                        }
                    }
                }
            }
        }
        return strOut + strFrontOrBack;
    }

    /**
     * 将给定时间戳和当前时间的差值转为英文字符串显示(5d ago或 5d after)
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampDifferenceToStringEN(String strTimeStamp) {
        return getDateDifferenceToStringEN(getTimeStampToDate(strTimeStamp));
    }

    /**
     * 将给定日期和当前时间的差值转为字符串(5天前 或 5天后)
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateDifferenceToStringCN(Date dateIn) {
        String strOut = "0天";
        String strFrontOrBack = "前";
        Calendar c1 = Calendar.getInstance();
        c1.setTime(getDateNow());
        Calendar c2 = Calendar.getInstance();
        c2.setTime(dateIn);
        int lngDuration = (c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR)) * 24 * 60 * 60;
        lngDuration += (c1.get(Calendar.HOUR_OF_DAY) - c2.get(Calendar.HOUR_OF_DAY)) * 60 * 60;
        lngDuration += (c1.get(Calendar.MINUTE) - c2.get(Calendar.MINUTE)) * 60;
        lngDuration += (c1.get(Calendar.SECOND) - c2.get(Calendar.SECOND)) * 1;
        if (lngDuration >= 0) {
            strFrontOrBack = "前";
        } else {
            strFrontOrBack = "后";
        }
        lngDuration = Math.abs(lngDuration);
        int YY = 0;
        int MM = 0;
        int ww = 0;
        int dd = lngDuration / 24 * 60 * 60;
        if (dd > 7) {
            ww = dd / 7;
            if (ww > 4)
                MM = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
            if (MM > 12)
                YY = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        }
        int hh = lngDuration / 60 * 60;
        int mm = lngDuration / 60;
        int ss = lngDuration % 60;

        if (YY > 0) {
            strOut = Math.abs(YY) + "年";
        } else {
            if (MM > 0) {
                strOut = Math.abs(MM) + "月";
            } else {
                if (ww > 0) {
                    strOut = Math.abs(ww) + "周";
                } else {
                    if (dd > 0) {
                        strOut = Math.abs(dd) + "天";
                    } else {
                        if (hh > 0) {
                            strOut = Math.abs(hh) + "时";
                        } else {
                            if (mm > 0) {
                                strOut = Math.abs(mm) + "分";
                            } else {
                                strOut = Math.abs(ss) + "秒";
                            }
                        }
                    }
                }
            }
        }
        return strOut + strFrontOrBack;
    }

    /**
     * 将给定时间戳和当前时间的差值转为中文字符串(5天前 或 5天后)
     *
     * @param strTimeStamp
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampDifferenceToStringCN(String strTimeStamp) {
        return getDateDifferenceToStringCN(getTimeStampToDate(strTimeStamp));
    }

    /**
     * 将给定日期和当前时间的差值转为英文剩余时间显示(0d0h0m0s ago 或 0d0h0m0s after)
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateToStringRemainEN(Date dateIn) {

        String strOut = "0d0h0m0s";
        String strFrontOrBack = " ago";
        Long lngDuration = getDateDifferenceSeconds(getDateNow(), dateIn);
        if (lngDuration >= 0) {
            strFrontOrBack = " ago";
        } else {
            strFrontOrBack = " after";
        }
        lngDuration = Math.abs(lngDuration);
        int nDay = (int) (lngDuration / (24 * 60 * 60));
        lngDuration = lngDuration - nDay * (24 * 60 * 60);
        int nHours = (int) (lngDuration / (60 * 60));
        lngDuration = lngDuration - nHours * (60 * 60);
        int nMinutes = (int) (lngDuration / 60);
        lngDuration = lngDuration - nMinutes * 60;
        int nSeconds = (int) (lngDuration % 60);
        strOut = "";
        if (nDay > 0) {
            strOut += nDay + "d";
        }
        if (nHours > 0) {
            strOut += nHours + "h";
        }
        if (nMinutes > 0) {
            strOut += nMinutes + "m";
        }
        if (nSeconds > 0) {
            strOut += nSeconds + "s";
        }
        return strOut + strFrontOrBack;
    }

    /**
     * 将给定时间戳和当前时间的差值转为英文剩余时间显示(0d0h0m0s ago 或 0d0h0m0s after)
     *
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToStringRemainEN(String strTimeStamp) {
        return getDateToStringRemainEN(getTimeStampToDate(strTimeStamp));
    }

    /**
     * 将给定日期和当前时间的差值转为中文剩余时间显示(0天0时0分前 或 0天0时0分后)
     *
     * @param dateIn
     * @return
     * @since v0.0.1
     */
    public static String getDateToStringRemainCN(Date dateIn) {

        String strOut = "0天0时0分";
        String strFrontOrBack = "前";
        Long lngDuration = getDateDifferenceSeconds(getDateNow(), dateIn);
        if (lngDuration >= 0) {
            strFrontOrBack = "前";
        } else {
            strFrontOrBack = "后";
        }
        lngDuration = Math.abs(lngDuration);
        int nDay = (int) (lngDuration / (24 * 60 * 60));
        lngDuration = lngDuration - nDay * (24 * 60 * 60);
        int nHours = (int) (lngDuration / (60 * 60));
        lngDuration = lngDuration - nHours * (60 * 60);
        int nMinutes = (int) (lngDuration / 60);
        lngDuration = lngDuration - nMinutes * 60;
        int nSeconds = (int) (lngDuration % 60);
        strOut = "";
        if (nDay > 0) {
            strOut += nDay + "天";
        }
        if (nHours > 0) {
            strOut += nHours + "时";
        }
        if (nMinutes > 0) {
            strOut += nMinutes + "分";
        }
        if (nSeconds > 0) {
            strOut += nSeconds + "秒";
        }
        return strOut + strFrontOrBack;
    }

    /**
     * 将给定时间戳和当前时间的差值转为中文剩余时间显示(0天0时0分前 或 0天0时0分后)
     *
     * @return
     * @since v0.0.1
     */
    public static String getTimeStampToStringRemainCN(String strTimeStamp) {
        return getDateToStringRemainCN(getTimeStampToDate(strTimeStamp));
    }

    /**
     * 特殊日期格式转换A <br>
     * 首页信息时间格式显示
     *
     * @param dateIn
     * @param boolTimerMessage true－定时消息，false－正常消息
     * @return
     * @since v0.0.1
     */
    public static String getDateToStringSpecialA(Context context, Date dateIn, boolean boolTimerMessage) {
        Calendar calendarNow = Calendar.getInstance();
//        if (AppSharedPreferences.getInstance().getServerTimeStampNow() > 1000l) {
//            if (dateIn.getTime() > AppSharedPreferences.getInstance().getServerTimeStampNow()) {
//                calendarNow.setTime(getDateNow());
//            } else {
//                calendarNow.setTimeInMillis(AppSharedPreferences.getInstance().getServerTimeStampNow());
//            }
//        } else {
        calendarNow.setTime(getDateNow());
//        }
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);

        long intervalMilliSeconds = calendarNow.getTimeInMillis() - calendarIn.getTimeInMillis();
        int s = (int) (intervalMilliSeconds / SECOND_OF_MILLISECOND);
        int m = (int) (intervalMilliSeconds / MINUTE_OF_MILLISECOND);
        int h = (int) (intervalMilliSeconds / HOUR_OF_MILLISECOND);
        // 判断是否跨天
        int d_over = calendarNow.get(Calendar.DAY_OF_YEAR) - calendarIn.get(Calendar.DAY_OF_YEAR);
        // 判断是否跨年
        int y_over = calendarNow.get(Calendar.YEAR) - calendarIn.get(Calendar.YEAR);
        if (boolTimerMessage) {
            if (s >= 0 && s < 60) {
                s = 0 - s;
            } else if (s >= 60) {
                return getDateToString_YYYY_MM_DD_HH_MM_EN(context, dateIn);
            }
        } else {
            if (s < 0 && s > -60) {
                s = 0 - s;
            } else if (s <= -60) {
                return getDateToString_YYYY_MM_DD_HH_MM_EN(context, dateIn);
            }
        }
        // 当前时间之前的处理
        if (s >= 0) {
            if (m < 1) {
                return JUSTNOW;
            } else if (m >= 1 && m < 60) {
                return m + MINUTE_AGO;
            }
            if (d_over == 0 && h >= 1 && h < 24) {
                return h + HOUR_AGO;
            } else if (d_over == 1 && h < 6) {
                return h + HOUR_AGO;
            } else if (d_over == 1 && h >= 6) {
                return YESTERDAY + getDateToString_HH_MM_EN(context, dateIn);
            }
            if (y_over == 0) {
                return getDateToString_MM_DD_EN(dateIn) + " " + getDayOfWeekCN(dateIn);
            } else {
                return getDateToString_YYYY_MM_DD_EN(dateIn) + " " + getDayOfWeekCN(dateIn);
            }
        } else {// 当前时间之后的处理
            if (Math.abs(s) < 60) {
                return Math.abs(s) + SECOND_AFTER;
            }
            if (Math.abs(m) < 60) {
                return Math.abs(m) + MINUTE_AFTER;
            }
            if (d_over == 0) {
                return getDateToString_HH_MM_EN(context, dateIn);
            }
            if (d_over == 1) {
                return TOMORROW + " " + getDateToString_HH_MM_EN(context, dateIn);
            }
            if (y_over == 0) {
                return getDateToString_MM_DD_EN(dateIn) + " " + getDayOfWeekCN(dateIn) + " " + getDateToString_HH_MM_EN(context, dateIn);
            } else {
                return getDateToString_MM_DD_EN(dateIn) + " " + getDayOfWeekCN(dateIn) + " " + getDateToString_HH_MM_EN(context, dateIn);
            }
        }
    }

    /**
     * 特殊日期格式转换B <br>
     * 首页信息详情时间格式显示
     *
     * @param dateIn
     * @return
     */
    public static String getDateToStringSpecialB(Context context, Date dateIn) {
        Calendar calendarNow = Calendar.getInstance();
//        if (AppSharedPreferences.getInstance().getServerTimeStampNow() > 1000l) {
//            if (dateIn.getTime() > AppSharedPreferences.getInstance().getServerTimeStampNow()) {
//                calendarNow.setTime(getDateNow());
//            } else {
//                calendarNow.setTimeInMillis(AppSharedPreferences.getInstance().getServerTimeStampNow());
//            }
//        } else {
        calendarNow.setTime(getDateNow());
//        }
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);

        // 判断是否跨年
        int y_over = calendarNow.get(Calendar.YEAR) - calendarIn.get(Calendar.YEAR);
        // 当前时间之前的处理
        if (y_over == 0) {
            return getDateToString_MM_DD_EN(dateIn) + " " + getDayOfWeekCN(dateIn) + " " + getDateToString_HH_MM_EN(context, dateIn);
        } else {
            return getDateToString_YYYY_MM_DD_EN(dateIn) + " " + getDayOfWeekCN(dateIn) + " " + getDateToString_HH_MM_EN(context, dateIn);
        }
    }

    /**
     * 特殊日期格式转换C <br>
     * 聊天和消息提醒时间格式显示
     * <p>
     * 除了接送助手和聊天 时间显示规则：
     * 今天显示，时分，24小时制：ah:mm
     * 昨天及昨天之前显示，月日：MM.dd
     * 去年之前显示年月日：yyyy.MM.dd
     *
     * @param dateIn
     * @return
     */
    public static String getDateToStringSpecialC(Context context, Date dateIn) {
        Calendar calendarNow = Calendar.getInstance();
//        if (AppSharedPreferences.getInstance().getServerTimeStampNow() > 1000l) {
//            if (dateIn.getTime() > AppSharedPreferences.getInstance().getServerTimeStampNow()) {
//                calendarNow.setTime(getDateNow());
//            } else {
//                calendarNow.setTimeInMillis(AppSharedPreferences.getInstance().getServerTimeStampNow());
//            }
//        } else {
        calendarNow.setTime(getDateNow());
//        }
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);

        // 判断是否跨天
        int d_over = calendarNow.get(Calendar.DAY_OF_YEAR) - calendarIn.get(Calendar.DAY_OF_YEAR);
        // 判断是否跨年
        int y_over = calendarNow.get(Calendar.YEAR) - calendarIn.get(Calendar.YEAR);

        if (d_over == 0) {
            return getDateToString_HH_MM_EN(context, dateIn);
        }
        if (d_over == 1) {
            return YESTERDAY + " " + getDateToString_HH_MM_EN(context, dateIn);
        }
        // 当前时间之前的处理
        if (y_over == 0) {
            return getDateToString_MM_DD_EN(dateIn) + " " + getDateToString_HH_MM_EN(context, dateIn);
        } else {
            return getDateToString_YYYY_MM_DD_EN(dateIn) + " " + getDateToString_HH_MM_EN(context, dateIn);
        }
    }

    /**
     * 特殊日期格式转换D <br>
     * 我的发布时间格式显示
     * <p>
     * 接送助手（这个方法只负责返回date部分）：
     * 今天显示：今天时分：ah:mm
     * 昨天显示：昨天时分：ah:mm
     * 之前显示：年月日时分  yyyy.MM.dd ah:mm
     *
     * @param dateIn
     * @return
     */
    public static String getDateToStringSpecialD(Date dateIn) {
        Calendar calendarNow = Calendar.getInstance();
//        if (AppSharedPreferences.getInstance().getServerTimeStampNow() > 1000l) {
//            if (dateIn.getTime() > AppSharedPreferences.getInstance().getServerTimeStampNow()) {
//                calendarNow.setTime(getDateNow());
//            } else {
//                calendarNow.setTimeInMillis(AppSharedPreferences.getInstance().getServerTimeStampNow());
//            }
//        } else {
        calendarNow.setTime(getDateNow());
//        }
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);

        // 判断是否跨天
        int d_over = calendarNow.get(Calendar.DAY_OF_YEAR) - calendarIn.get(Calendar.DAY_OF_YEAR);
        // 判断是否跨年
        int y_over = calendarNow.get(Calendar.YEAR) - calendarIn.get(Calendar.YEAR);

        if (d_over == 0) {
            return TODAY;
        }
        if (d_over == 1) {
            return YESTERDAY;
        }
        // 当前时间之前的处理
//        if (y_over == 0) {
//            return new SimpleDateFormat("MM").format(dateIn) + "." + new SimpleDateFormat("dd").format(dateIn);
//        } else {
        return getDateToString_YYYY_MM_DD_point(dateIn);
//        }
    }


    /**
     * 特殊日期格式转换D <br>
     * 我的发布时间格式显示
     *
     * @param dateIn
     * @return
     */
    public static String getDateToStringSpecialE(Date dateIn) {
        Calendar calendarNow = Calendar.getInstance();
//        if (AppSharedPreferences.getInstance().getServerTimeStampNow() > 1000l) {
//            if (dateIn.getTime() > AppSharedPreferences.getInstance().getServerTimeStampNow()) {
//                calendarNow.setTime(getDateNow());
//            } else {
//                calendarNow.setTimeInMillis(AppSharedPreferences.getInstance().getServerTimeStampNow());
//            }
//        } else {
        calendarNow.setTime(getDateNow());
//        }
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(dateIn);

        // 判断是否跨天
        int d_over = calendarNow.get(Calendar.DAY_OF_YEAR) - calendarIn.get(Calendar.DAY_OF_YEAR);
        // 判断是否跨年
        int y_over = calendarNow.get(Calendar.YEAR) - calendarIn.get(Calendar.YEAR);

        // if (d_over == 0) {
        // return TODAY;
        // }
        // if (d_over == 1) {
        // return YESTERDAY;
        // }
        // 当前时间之前的处理
        if (y_over == 0) {
            return getDateToString_MM_CN(dateIn) + "-" + getDateToString_DD_CN(dateIn);
        } else {
            return getDateToString_YYYY_CN(dateIn) + "-" + getDateToString_MM_CN(dateIn) + "-" + getDateToString_DD_CN(dateIn);
        }
    }

    public static boolean isSameYear(Date date1) {
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(getDateNow());
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(date1);

        // 判断是否跨年
        int y_over = calendarNow.get(Calendar.YEAR) - calendarIn.get(Calendar.YEAR);
        if (y_over == 0) {
            return true;
        } else {
            return false;
        }

    }

    public static String getDateToStringSpecialF(String dateIn) {
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(getDateNow());
        Calendar calendarIn = Calendar.getInstance();
        calendarIn.setTime(new Date(Long.parseLong(dateIn)));

        // 判断是否跨天
        int d_over = calendarNow.get(Calendar.DAY_OF_YEAR) - calendarIn.get(Calendar.DAY_OF_YEAR);
        // 判断是否跨年
        int y_over = calendarNow.get(Calendar.YEAR) - calendarIn.get(Calendar.YEAR);

        if (d_over == 0) {
            SimpleDateFormat DateToString = new SimpleDateFormat("HH:mm");
            return DateToString.format(Long.parseLong(dateIn));
        } else {
            if (y_over == 0) {
                SimpleDateFormat DateToString = new SimpleDateFormat("MM月dd");
                return DateToString.format(Long.parseLong(dateIn));
            } else {
                SimpleDateFormat DateToString = new SimpleDateFormat("yyyy.MM.dd");
                return DateToString.format(Long.parseLong(dateIn));
            }
        }
    }

    /**
     * 对话：
     * 当天：时分 ah:mm
     * 昨天：昨天时分 昨天 ah:mm
     * 间隔一周内：星期几 时分 EEEE ah:mm
     * 超过一周的：年月日 时分 yyyy.MM.dd ah:mm
     *
     * @param date
     * @return
     */
    public static String getDateToStringSpecialH(Date date) {

        if (isToday(date)) {//今天
            return getDateToString_HH_MM_EN_24(date);
        } else if (isYesterday(date)) {//昨天
            return YESTERDAY + " " + getDateToString_HH_MM_EN_24(date);
        } else if (isSameWeek(getDateNow(), date)) {//本周内
            return getWeekDay(date) + " " + getDateToString_HH_MM_EN_24(date);
        } else {//本周以外
            return getDateToString_YYYY_MM_DD_point(date) + " " + getDateToString_HH_MM_EN_24(date);

        }

    }


    /**
     * 比较两个日期（不包括时间）
     *
     * @param calendar
     * @param antherCalender
     * @return
     */
    public static int compareDate(Calendar calendar, Calendar antherCalender) {
        if (isSameDayOfDate(calendar, antherCalender)) {
            return 0;
        }
        return calendar.compareTo(antherCalender);

    }

    /**
     * 获取星期
     *
     * @param date
     * @return
     */
    public static String getWeekDay(Date date) {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }


    public static String getDateStringFormat_yyyy_MM_dd_HH_mm_ss_SSS_24_EN(Date dateIn) {
        SimpleDateFormat DateToString = new SimpleDateFormat(format_yyyy_MM_dd_HH_mm_ss_SSS_24_EN);
        return DateToString.format(dateIn);
    }


    /**
     * 判断两个日期是否在同一周
     *
     * @param today
     * @param date2
     * @return
     */
    public static boolean isSameWeek(Date today, Date date2) {

        if (isSameYear(date2)) {
            Calendar c1 = Calendar.getInstance();
            c1.setFirstDayOfWeek(Calendar.MONDAY);
            c1.setTime(today);
            Calendar c2 = Calendar.getInstance();
            c2.setFirstDayOfWeek(Calendar.MONDAY);
            c2.setTime(date2);

            return c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR);
        } else {
            return false;
        }


    }

    public static long getDateStmp(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取本周一日期
     *
     * @return
     */
    public static long getMondayOFWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        int mondayPlus = 1 - day_of_week;
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        return monday.getTime();
    }

    public static String getFormatDate(Long date, String partten){
        if(null != date && date.longValue() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(partten);
            return sdf.format(new Date(date));
        }else{
            return "";
        }
    }

    //根据日期转换为指定格式
    //今年显示 月日 其他显示 年月日
    public static String getFormatDateToPartten(Long date){
        if(null != date && date.longValue() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            int year = Integer.parseInt(sdf.format(new Date(date)));
            int currentYear = Integer.parseInt(sdf.format(new Date(System.currentTimeMillis())));
            if (year == currentYear) {
                SimpleDateFormat sdfs = new SimpleDateFormat("MM.dd HH:mm");
                return sdfs.format(new Date(date));
            } else {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                return sd.format(new Date(date));
            }
        }else{
            return "";
        }
    }

}
