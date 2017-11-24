package com.cicada.startup.common.ui.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by zyh on 2016/11/11 0011.
 */

public class CustomListView extends ListView {
    private android.view.ViewGroup.LayoutParams params;
    private int old_count = 0;

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getCount() != old_count) {
            if (getChildAt(0) != null) {
                old_count = getCount();
                params = getLayoutParams();

                params.height = (getCount() - 1)
                        * (old_count > 0 ? getChildAt(0).getHeight()
                        + getDividerHeight() : 0)
                        + getChildAt(0).getHeight();

                setLayoutParams(params);
            }
        }
        super.onDraw(canvas);
    }
    
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;  //禁止滑动
        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}