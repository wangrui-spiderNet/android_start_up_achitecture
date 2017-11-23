package com.cicada.startup.common.ui.view.wheelview;

import android.content.Context;

import com.cicada.startup.common.utils.UiHelper;

import java.util.Arrays;
import java.util.List;

public class WheelViewDate {

    private WheelView year;
    private WheelView month;
    private WheelView day;
    private WheelView week;
    private WheelView hour;
    private WheelView minute;

    private NumericWheelAdapter yearAdapter;
    private int START_YEAR;
    private int END_YEAR;
    private int mCurYear;
    private Context context;
    private NumericWheelAdapter monthAdapter;
    private NumericWheelAdapter hourAdapter;
    private NumericWheelAdapter minuteAdapter;
    private int mCurMonth;
    private int mCurDay;
    private int mCurHour;
    private int mCurMinute;
    private boolean cyclic = true;

    // 添加大小月月份并将其转换为list,方便之后的判断
    String[] mMonthsBig = {"1", "3", "5", "7", "8", "10", "12"};
    String[] mMonthsLittle = {"4", "6", "9", "11"};
    final List<String> mListBig = Arrays.asList(mMonthsBig);
    final List<String> mListLittle = Arrays.asList(mMonthsLittle);

    /**
     * 设置滚轮
     *
     * @param context    上下文对象
     * @param year       年滚轮
     * @param month      月滚轮
     * @param day        日滚轮
     * @param week       周滚轮
     * @param START_YEAR 起始年
     * @param END_YEAR   结束年
     * @param mCurYear   定位到哪年
     * @param mCurMonth  定位到哪月
     * @param mCurDay    定位到哪日
     */
    public WheelViewDate(Context context, WheelView year, WheelView month, WheelView day, WheelView week, int START_YEAR, int END_YEAR, int mCurYear, int mCurMonth, int mCurDay, boolean hasLabel, boolean cyclic) {
        this.context = context;
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
        this.cyclic = cyclic;
        this.START_YEAR = START_YEAR;
        this.END_YEAR = END_YEAR;
        this.mCurYear = mCurYear;
        this.mCurMonth = mCurMonth;
        this.mCurDay = mCurDay;
        if (hasLabel) {
            year.setLabel("年");
            month.setLabel("月");
            day.setLabel("日");
        }
        setWheelViewYearScrollListener();
        setWheelViewMonthScrollListener();
        setWheelViewDayScrollListener();
        setWheelListener();
    }

    /**
     * 设置滚轮
     *
     * @param context    上下文对象
     * @param year       年滚轮
     * @param month      月滚轮
     * @param day        日滚轮
     * @param START_YEAR 起始年
     * @param END_YEAR   结束年
     * @param mCurYear   定位到哪年
     * @param mCurMonth  定位到哪月
     * @param mCurDay    定位到哪日
     */
    public WheelViewDate(Context context, WheelView year, WheelView month, WheelView day, int START_YEAR, int END_YEAR, int mCurYear, int mCurMonth, int mCurDay, boolean hasLabel, boolean yearCyclic) {
        this.context = context;
        this.year = year;
        this.month = month;
        this.day = day;
        this.START_YEAR = START_YEAR;
        this.END_YEAR = END_YEAR;
        this.mCurYear = mCurYear;
        this.mCurMonth = mCurMonth;
        this.mCurDay = mCurDay;
        this.cyclic = yearCyclic;
        if (hasLabel) {
            year.setLabel("年");
            month.setLabel("月");
            day.setLabel("日");
        }
        setWheelViewDateYear();
        setWheelViewDateMonth();
        setWheelViewDateDay();
        setWheelListener();
    }

    /**
     * 设置滚轮
     *
     * @param context    上下文对象
     * @param year       年滚轮
     * @param month      月滚轮
     * @param day        日滚轮
     * @param START_YEAR 起始年
     * @param END_YEAR   结束年
     * @param mCurYear   定位到哪年
     * @param mCurMonth  定位到哪月
     * @param mCurDay    定位到哪日
     */
    public WheelViewDate(Context context, WheelView year, WheelView month, WheelView day, WheelView hour,WheelView minute,int START_YEAR, int END_YEAR, int mCurYear, int mCurMonth, int mCurDay, int mHour,int mMinute,boolean hasLabel, boolean yearCyclic) {
        this.context = context;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;

        this.START_YEAR = START_YEAR;
        this.END_YEAR = END_YEAR;
        this.mCurYear = mCurYear;
        this.mCurMonth = mCurMonth;
        this.mCurDay = mCurDay;
        this.mCurHour = mHour;
        this.mCurMinute = mMinute;

        this.cyclic = yearCyclic;
        if (hasLabel) {
            year.setLabel("年");
            month.setLabel("月");
            day.setLabel("日");
            hour.setLabel("时");
            minute.setLabel("分");
        }
        setWheelViewDateYear();
        setWheelViewDateMonth();
        setWheelViewDateDay();
        setWheelViewDateHour();
        setWheelViewDateMinute();

        setWheelListener();
    }

    /**
     * 初始化WheelView的DateYear数据
     */
    private void setWheelViewDateYear() {
        yearAdapter = new NumericWheelAdapter(START_YEAR, END_YEAR, 1);
        year.setAdapter(yearAdapter);
        year.setCyclic(cyclic);
        year.setCurrentItem(mCurYear - START_YEAR);
        year.TEXT_SIZE = UiHelper.getDateTextSize(context);
    }

    /**
     * 初始化WheelView的DateHour数据
     */
    private void setWheelViewDateHour() {
        hourAdapter = new NumericWheelAdapter(0, 23, 1, "%02d");
        hour.setAdapter(hourAdapter);
        hour.setCyclic(cyclic);
        hour.setCurrentItem(mCurHour);
        hour.TEXT_SIZE = UiHelper.getTimeTextSize(context);
    }

    /**
     * 初始化WheelView的Minute数据
     */
    private void setWheelViewDateMinute() {
        minuteAdapter = new NumericWheelAdapter(0, 59, 1, "%02d");
        minute.setAdapter(minuteAdapter);
        minute.setCyclic(cyclic);
        minute.setCurrentItem(mCurMinute);
        minute.TEXT_SIZE = UiHelper.getTimeTextSize(context);
    }

    /**
     * 设置当滚动结束时初始化WeekYear的数据监听器
     */
    private void setWheelViewYearScrollListener() {
        year.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
            }
        });
        setWheelViewDateYear();
    }

    /**
     * 初始化WheelView的DateMonth数据
     */
    private void setWheelViewDateMonth() {
        monthAdapter = new NumericWheelAdapter(1, 12, 1, "%02d");
        month.setAdapter(monthAdapter);
        month.setCyclic(true);
        month.setCurrentItem(mCurMonth);
        month.TEXT_SIZE = UiHelper.getDateTextSize(context);
    }

    /**
     * 设置当滚动结束时初始化WeekDay的数据监听器
     */
    private void setWheelViewMonthScrollListener() {
        month.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
            }
        });
        setWheelViewDateMonth();
    }

    /**
     * 初始化WheelView的DateDay数据
     */
    private void setWheelViewDateDay() {
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (mListBig.contains(String.valueOf(mCurMonth + 1))) {
            day.setAdapter(new NumericWheelAdapter(1, 31, 1, "%02d"));
        } else if (mListLittle.contains(String.valueOf(mCurMonth + 1))) {
            day.setAdapter(new NumericWheelAdapter(1, 30, 1, "%02d"));
        } else {
            // 闰年
            if ((mCurYear % 4 == 0 && mCurYear % 100 != 0) || mCurYear % 400 == 0)
                day.setAdapter(new NumericWheelAdapter(1, 29, 1, "%02d"));
            else
                day.setAdapter(new NumericWheelAdapter(1, 28, 1, "%02d"));
        }
        day.setCyclic(true);
        day.setCurrentItem(mCurDay - 1);
        day.TEXT_SIZE = UiHelper.getDateTextSize(context);
    }

    /**
     * 设置当滚动结束时初始化WeekDay的数据监听器
     */
    private void setWheelViewDayScrollListener() {
        day.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
            }
        });
        setWheelViewDateDay();
    }

    /**
     * 设置年月滚轮监听器
     */
    private void setWheelListener() {
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (mListBig.contains(String.valueOf(month.getCurrentItem() + 1))) {
                    day.setAdapter(new NumericWheelAdapter(1, 31, 1, "%02d"));
                } else if (mListLittle.contains(String.valueOf(month.getCurrentItem() + 1))) {
                    day.setAdapter(new NumericWheelAdapter(1, 30, 1, "%02d"));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0)
                        day.setAdapter(new NumericWheelAdapter(1, 29, 1, "%02d"));
                    else
                        day.setAdapter(new NumericWheelAdapter(1, 28, 1, "%02d"));
                }
            }
        };

        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (mListBig.contains(String.valueOf(month_num))) {
                    day.setAdapter(new NumericWheelAdapter(1, 31, 1, "%02d"));
                } else if (mListLittle.contains(String.valueOf(month_num))) {
                    day.setAdapter(new NumericWheelAdapter(1, 30, 1, "%02d"));
                } else {
                    if (((year.getCurrentItem() + START_YEAR) % 4 == 0 && (year.getCurrentItem() + START_YEAR) % 100 != 0) || (year.getCurrentItem() + START_YEAR) % 400 == 0)
                        day.setAdapter(new NumericWheelAdapter(1, 29, 1, "%02d"));
                    else
                        day.setAdapter(new NumericWheelAdapter(1, 28, 1, "%02d"));
                }
            }
        };

        year.addChangingListener(wheelListener_year);
        month.addChangingListener(wheelListener_month);
    }

    public void updateYear(int _year) {
        year.setCurrentItem(_year);
    }

    public void updateMonth(int _month) {
        month.setCurrentItem(_month);
    }

    public void updateDay(int _day) {
        day.setCurrentItem(_day);
    }

    public void updateHour(int _hour){
        hour.setCurrentItem(_hour);
    }

    public void updateMinute(int _munite){
        minute.setCurrentItem(_munite);
    }
}
