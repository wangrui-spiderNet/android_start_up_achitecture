package com.cicada.startup.common.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

import com.cicada.startup.common.R;
import com.cicada.startup.common.ui.wight.wheelview.OnWheelChangedListener;
import com.cicada.startup.common.ui.wight.wheelview.WheelView;
import com.cicada.startup.common.ui.wight.wheelview.WheelViewDate;
import com.cicada.startup.common.utils.DateUtils;
import com.cicada.startup.common.utils.LogUtils;
import com.cicada.startup.common.utils.ToastUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 选择日期
 * <p>
 * Create time: 16/7/12 9:53
 *
 * @author liuyun.
 */
public class ChooseDateHelper {

    private PopupWindow dateSelectorPopupWindow;
    private View mParent;
    private SelectDateInterface selectDateInterface;
    private SelectTimeInterface selectTimeInterface;
    private Context mContext;

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    private WheelView mYearWheelView;
    private WheelView mMonthWheelView;
    private WheelView mDayWheelView;
    private WheelView mHourWheelView;
    private WheelView mMinWheelView;


    private static final int DEFAULT_YEAR = 1970;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private Calendar calendar = Calendar.getInstance();

    private int view_type = 0;//0 显示年月日；1只显示年月；2显示年月日时分
    public static final int YEAR_MONTH_VISIBLE = 1;
    public static final int YEAR_MONTH_DAY_VISIBLE = 0;
    public static final int YEAR_MONTH_GAY_HOUR_MIN_VISIBLE = 2;
    private String Tag;//用做全勤宝宝和其他区分的标记
    public ChooseDateHelper(Context context) {
        mContext = context;
    }

    public ChooseDateHelper(Context context, int view_type) {
        mContext = context;
        this.view_type = view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    /**
     * 初始化日期选择器
     *
     * @param parentView 显示日期选择器的view
     * @param year       当前显示的日期-year
     * @param month      当前显示的日期-moth
     * @param day        当前显示的日期-day
     * @author liuyun
     * @since v0.1
     */
    public void initDateSelector(View parentView, int year, int month, int day) {
        mParent = parentView;
        calendar.setTime(new Date());
        this.year = year;
        this.month = month;
        this.day = day;
        if (year <= 0) {
            this.year = calendar.get(Calendar.YEAR);
        }
        if (month <= 0) {
            this.month = calendar.get(Calendar.MONTH);
        }
        if (day <= 0) {
            this.day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        initView();
        initWheelView();
    }

    /**
     * 初始化日期选择器
     *
     * @param parentView 显示日期选择器的view
     * @param year       当前显示的日期-year
     * @param month      当前显示的日期-moth
     * @param day        当前显示的日期-day
     * @author wangrui
     * @since v0.1
     */
    public void initTimeSelector(View parentView, int year, int month, int day, int hour, int minute) {
        mParent = parentView;
        calendar.setTime(new Date());
        this.year = year;
        this.month = month;
        this.day = day;
        if (year <= 0) {
            this.year = calendar.get(Calendar.YEAR);
        }
        if (month <= 0) {
            this.month = calendar.get(Calendar.MONTH);
        }
        if (day <= 0) {
            this.day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        if (hour <= 0) {
            this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        }

        if (minute <= 0) {
            this.minute = calendar.get(Calendar.MINUTE);
        }
        initView();
        initWheelView();
    }

    private void initView() {
        // 创建一个popupwindow的载体View
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.activity_date_selector, null);
        // 实例化PopupWindow
        if (dateSelectorPopupWindow == null) {
            dateSelectorPopupWindow = new PopupWindow(popupView, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
        }
        // 这两个属性设置点击popup以外区域隐藏
        dateSelectorPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        dateSelectorPopupWindow.setOutsideTouchable(true);
        dateSelectorPopupWindow.setAnimationStyle(R.style.popup_animation);

        mYearWheelView = (WheelView) popupView.findViewById(R.id.wheelviewYear);
        mMonthWheelView = (WheelView) popupView.findViewById(R.id.wheelviewMonth);
        mDayWheelView = (WheelView) popupView.findViewById(R.id.wheelviewDay);
        mHourWheelView = (WheelView) popupView.findViewById(R.id.wheelviewHour);
        mMinWheelView = (WheelView) popupView.findViewById(R.id.wheelviewMinute);

        if (view_type == YEAR_MONTH_DAY_VISIBLE) {
            mDayWheelView.setVisibility(View.VISIBLE);
            mHourWheelView.setVisibility(View.GONE);
            mMinWheelView.setVisibility(View.GONE);
        } else if (view_type == YEAR_MONTH_VISIBLE) {
            mDayWheelView.setVisibility(View.GONE);
            mHourWheelView.setVisibility(View.GONE);
            mMinWheelView.setVisibility(View.GONE);
        } else if (view_type == YEAR_MONTH_GAY_HOUR_MIN_VISIBLE) {
            mDayWheelView.setVisibility(View.VISIBLE);
            mHourWheelView.setVisibility(View.VISIBLE);
            mMinWheelView.setVisibility(View.VISIBLE);
        }

        popupView.findViewById(R.id.linearLayoutCancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismissPopup();
            }
        });

        popupView.findViewById(R.id.buttonCancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismissPopup();
            }
        });
        popupView.findViewById(R.id.buttonSure).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(TextUtils.isEmpty(Tag)){
                    String dateStr = year + "-" + (month + 1) + "-" + (day + 1);
                    if (validDate(dateStr)) {
                        if (selectDateInterface != null) {
                            selectDateInterface.selectedDate(year, month, day + 1);
                        } else if (selectTimeInterface != null) {
                            selectTimeInterface.selectedTime(year, month, day + 1, hour, minute);
                        }
                    } else {
                        ToastUtils.showToastImage(mContext, mContext.getString(R.string.choose_date_future), 0);
                    }
                }else{
                    int currentYear = DateUtils.getDateYearInt(new Date());
                    int currentMonth = DateUtils.getDateMonthInt(new Date());
                    if(year == currentYear) {
                        if ((month + 1) > currentMonth - 1) {
                            ToastUtils.showToastImage(mContext, mContext.getString(R.string.choose_date_future), 0);
                        } else {
                            if (selectDateInterface != null) {
                                selectDateInterface.selectedDate(year, month, day + 1);
                            }
                        }
                    }else{
                        if (selectDateInterface != null) {
                            selectDateInterface.selectedDate(year, month, day + 1);
                        }
                    }
                }
                dismissPopup();
            }
        });
    }

    private void initWheelView() {
        mYearWheelView.setIsShowCenterRect(false);
        mYearWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mYearWheelView.addChangingListener(new MyWheelViewOnChangingListener());

        mMonthWheelView.setIsShowCenterRect(false);
        mMonthWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mMonthWheelView.addChangingListener(new MyWheelViewOnChangingListener());

        mDayWheelView.setIsShowCenterRect(false);
        mDayWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mDayWheelView.addChangingListener(new MyWheelViewOnChangingListener());

        mHourWheelView.setIsShowCenterRect(false);
        mHourWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mHourWheelView.addChangingListener(new MyWheelViewOnChangingListener());

        mMinWheelView.setIsShowCenterRect(false);
        mMinWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mMinWheelView.addChangingListener(new MyWheelViewOnChangingListener());

        setCurWheelView();
        mMonthWheelView.setVisibleItems(5);
        mDayWheelView.setVisibleItems(5);
        mYearWheelView.setVisibleItems(5);
        mHourWheelView.setVisibleItems(5);
        mMinWheelView.setVisibleItems(5);
    }

    /**
     * 设置当前时间的滚轮
     */
    private void setCurWheelView() {

        if (view_type == 2) {
            new WheelViewDate(mContext, mYearWheelView, mMonthWheelView, mDayWheelView, mHourWheelView, mMinWheelView, DEFAULT_YEAR, calendar.get(Calendar.YEAR), year, month, day, hour, minute, true, false);
        } else {
            new WheelViewDate(mContext, mYearWheelView, mMonthWheelView, mDayWheelView, DEFAULT_YEAR, calendar.get(Calendar.YEAR), year, month, day, true, false);
        }

    }

    /**
     * 滚轮滑动监听器
     */
    private class MyWheelViewOnChangingListener implements OnWheelChangedListener {

        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (R.id.wheelviewYear == wheel.getId()) {
                year = newValue + DEFAULT_YEAR;
            } else if (R.id.wheelviewMonth == wheel.getId()) {
                month = newValue;
            } else if (R.id.wheelviewDay == wheel.getId()) {
                day = newValue;
            } else if (R.id.wheelviewHour == wheel.getId()) {
                hour = newValue;
            } else if (R.id.wheelviewMinute == wheel.getId()) {
                minute = newValue;
            }
            LogUtils.d("hwp", "year=" + year + "  mouth=" + month + " day=" + day + " hour=" + hour + " minute=" + minute);
        }
    }

    /**
     * 校验选择的日期
     */
    private boolean validDate(String dateStr) {
        boolean isValid = false;
        Date date = DateUtils.getStringToDate_YYYY_MM_DD_EN(dateStr);
        Date nowDate = DateUtils.getDateNow();
        if (DateUtils.getDateDifferenceDays(date, nowDate) > 0) {
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }

    public void showDateSelector() {
        if (!dateSelectorPopupWindow.isShowing()) {
            dateSelectorPopupWindow.getContentView().measure(0, 0);
            dateSelectorPopupWindow.showAtLocation(mParent, Gravity.CENTER, 0, 0);
            dateSelectorPopupWindow.update();
        }
    }

    private void dismissPopup() {
        if (dateSelectorPopupWindow != null) {
            dateSelectorPopupWindow.dismiss();
        }
    }

    public void setSelectDateInterface(SelectDateInterface selectDateInterface) {
        this.selectDateInterface = selectDateInterface;
    }

    public interface SelectDateInterface {
        void selectedDate(int selectedYear, int selectedMonth, int selectedDay);
    }

    public void setSelectTimeInterface(SelectTimeInterface selectTimeInterface) {
        this.selectTimeInterface = selectTimeInterface;
    }

    public interface SelectTimeInterface {
        void selectedTime(int selectYear, int selectMonth, int selectDay, int selectHour, int selectMin);
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
