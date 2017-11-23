package com.cicada.startup.common.ui.activity;

/**
 * <p/>
 * 创建时间: 16/6/30 下午3:05 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public interface IBaseView {

    boolean isDestroy();

    void showWaitDialog();

    void dismissWaitDialog();
}
