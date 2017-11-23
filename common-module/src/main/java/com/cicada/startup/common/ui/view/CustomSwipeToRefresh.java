package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.webkit.WebView;

public class CustomSwipeToRefresh extends SwipeRefreshLayout {

    private int scaleTouchSlop;
    private final int yScrollBuffer = 0;
    private float preX;

    private WebView webView;

    public CustomSwipeToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    public CustomSwipeToRefresh(Context context, WebView webView) {
        super(context);
        scaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.webView = webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    //解决swipeRefresh和webview滚动冲突
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float instanceX = Math.abs(moveX - preX);

                // 容差值大概是24，再加上60
                if (instanceX > scaleTouchSlop + 60) {
                    return false;
                }
                int scrollY = webView.getScrollY();
                if (scrollY > yScrollBuffer) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }
}