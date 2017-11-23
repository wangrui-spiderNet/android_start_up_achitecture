package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author houwenpeng
 * @version V1.0
 * @Package daydaybaby
 * @Title com.cicada.daydaybaby.common.ui.view
 * @date 16/8/25
 * @Description:
 */
public class ToobarView extends RelativeLayout {


    public ToobarView(Context context) {
        super(context);
        initView(context);
    }

    public ToobarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ToobarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context cotext){

    }
}
