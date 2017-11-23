package com.cicada.startup.common.ui.view.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 支持下拉刷新的RecyclerView
 * <p/>
 * 创建时间: 16/9/1 上午10:08 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */

public class PullToRefreshRecyclerView extends RecyclerView {



    public PullToRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }


}
