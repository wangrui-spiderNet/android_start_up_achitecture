package com.cicada.startup.common.ui.wight.recyclerview.dragrecyclerview;

import android.view.View;

public interface RecycleCallBack {
    //item的点击事件
    void itemOnClick(int position, View view);

    void onMove(int from, int to);
}
