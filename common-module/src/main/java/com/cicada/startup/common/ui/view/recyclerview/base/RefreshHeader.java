package com.cicada.startup.common.ui.view.recyclerview.base;

/**
 * 下拉刷新header
 * <p/>
 * 创建时间: 16/9/1 上午10:16 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */

public interface RefreshHeader {

    int STATE_NORMAL = 0; //初始状态 --->下拉刷新
    int STATE_RELEASE_TO_REFRESH = 1; //松开立即刷新
    int STATE_REFRESHING = 2;    //刷新中
    int STATE_REFRESH_DONE = 3;  //刷新完成

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();

}
