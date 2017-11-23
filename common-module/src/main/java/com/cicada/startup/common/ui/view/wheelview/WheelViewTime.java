package com.cicada.startup.common.ui.view.wheelview;

import android.content.Context;

import com.cicada.startup.common.utils.UiHelper;

public class WheelViewTime {
    private Context context;
    private WheelView hour;
    private WheelView minute;
    private NumericWheelAdapter hourAdapter;
    private int mCurHour;
    private int mCurMinute;
    private NumericWheelAdapter minuteAdapter;
    private int hourSpeed = 1;
    private int minuteSpeed = 1;

    public WheelViewTime(Context context, WheelView hour, WheelView minute, int mCurHour, int mCurMinute, boolean hasLabel, int hourSpeed, int minuteSpeed) {
        this.context = context;
        this.hour = hour;
        this.minute = minute;
        this.mCurHour = mCurHour;
        this.mCurMinute = mCurMinute;
        this.hourSpeed = hourSpeed;
        this.minuteSpeed = minuteSpeed;

        setHour();
        setMinute();
        if (hasLabel) {
            hour.setLabel("时");
            minute.setLabel("分");
        }
    }

    private void setHour() {
        hourAdapter = new NumericWheelAdapter(0, 23, hourSpeed, "%02d");
        hour.setAdapter(hourAdapter);
        hour.setCurrentItem(mCurHour);
        hour.setCyclic(true);
        hour.TEXT_SIZE = UiHelper.getTimeTextSize(context);
        hour.setVisibleItems(3);
    }

    private void setMinute() {
        minuteAdapter = new NumericWheelAdapter(0, 59, minuteSpeed, "%02d");
        minute.setAdapter(minuteAdapter);
        minute.setCurrentItem(mCurMinute);
        minute.setCyclic(true);
        minute.TEXT_SIZE = UiHelper.getTimeTextSize(context);
        minute.setVisibleItems(3);
    }

    public void updateMinute(int _minute) {
        minute.setCurrentItem(_minute);
    }

    public void updateHour(int _hour) {
        hour.setCurrentItem(_hour);
    }
}
