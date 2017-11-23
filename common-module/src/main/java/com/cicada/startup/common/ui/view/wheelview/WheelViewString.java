package com.cicada.startup.common.ui.view.wheelview;

import android.content.Context;

import com.cicada.startup.common.utils.UiHelper;

import java.util.List;

public class WheelViewString {
    private WheelView wheelView;
    private StringWheelAdapter wheelViewAdapter;
    private int mCurIndex;
    private Context context;

    private List<String> mList;

    /**
     * Creates a new instance of WheelViewString.
     *
     * @param context
     * @param wheelView
     * @param curIndex
     */
    public WheelViewString(Context context, WheelView wheelView, int curIndex) {
        this.context = context;
        this.wheelView = wheelView;
        this.mCurIndex = curIndex;
        setWheelViewwheelViewScrollListener();
        setWheelListener();
    }

    /**
     * Creates a new instance of WheelViewString.
     *
     * @param context
     * @param wheelView
     * @param list
     * @param mCurwheelView
     */
    public WheelViewString(Context context, WheelView wheelView, List<String> list, int mCurwheelView) {
        this.context = context;
        this.wheelView = wheelView;
        this.mList = list;
        this.mCurIndex = mCurwheelView;
        setWheelView();
        setWheelListener();
    }

    /**
     * 初始化WheelView的DatewheelView数据
     */
    private void setWheelView() {
        wheelViewAdapter = new StringWheelAdapter(mList);
        wheelView.setAdapter(wheelViewAdapter);
        wheelView.setCyclic(false);
        wheelView.setCurrentItem(mCurIndex);
        wheelView.TEXT_SIZE = UiHelper.getDateTextSize(context);
        wheelView.setVisibleItems(mList.size()>5 ? 5:mList.size());
    }

    /**
     * 设置当滚动结束时初始化WeekwheelView的数据监听器
     */
    private void setWheelViewwheelViewScrollListener() {
        wheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
            }
        });
        setWheelView();
    }

    /**
     * 设置滚轮监听器
     */
    private void setWheelListener() {
        OnWheelChangedListener wheelListener_wheelView = new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

            }
        };
        wheelView.addChangingListener(wheelListener_wheelView);
    }

}
