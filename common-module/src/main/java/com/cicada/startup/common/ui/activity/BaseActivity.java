package com.cicada.startup.common.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;
import com.cicada.startup.common.cache.CacheManager;
import com.cicada.startup.common.manager.AppManager;
import com.cicada.startup.common.ui.view.LoadingDialog;
import com.cicada.startup.common.utils.LogUtils;
import com.cicada.startup.common.utils.StatusBarUtil;
import com.cicada.startup.common.utils.ToastUtils;
import com.cicada.startup.common.utils.UiHelper;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;
import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p/>
 * 创建时间: 16/6/28 下午2:48 <br/>å
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */


public class BaseActivity extends AppCompatActivity implements IBaseView {

    private static final int BASE_LAYOUT_RES_ID = R.layout.activity_base;
    private static final LinearLayout.LayoutParams LAYOUT_PARAMS =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    private LinearLayout parentView;
    private Toolbar toolbar;
    private TextView textViewTitle;
    private TextView textViewRightTitle;
    private ImageView rightRedPoint;
    private ImageView imageViewShare;
    private ImageView imageViewSetting;
    private boolean isDestroy = false;
    private Unbinder unbinder;
    protected CacheManager cacheManager;
    protected Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.isDestroy = false;
        super.onCreate(savedInstanceState);
        setContentView(BASE_LAYOUT_RES_ID);
        mContext = this;
        getWindow().setWindowAnimations(R.style.ActivityAnim);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        init();
    }

    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgent.onPageStart(this, this.getTAG());
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgent.onPageEnd(this, this.getTAG());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        this.isDestroy = true;
        dismissWaitDialog();
        if (null != unbinder) {
            unbinder.unbind();
        }
        AppManager.getInstance().removeActivity(this);
        UiHelper.hideSoftInput(this);
        super.onDestroy();
    }

    private void init() {
        initToolBar();
        AppManager.getInstance().addActivity(this);

//        try {
//            this.cacheManager = new CacheManager(this);
//        } catch (IOException e) {
//            Log.e(this.getClass().getSimpleName(), e.getMessage());
//        }
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (BASE_LAYOUT_RES_ID == layoutResID) {
            super.setContentView(layoutResID);
            parentView = (LinearLayout) findViewById(R.id.base_parent_view);
            toolbar = (Toolbar) findViewById(R.id.toolbar_view);
            textViewTitle = (TextView) findViewById(R.id.tv_title);
            textViewRightTitle = (TextView) findViewById(R.id.tv_right_title);
            imageViewShare = (ImageView) findViewById(R.id.toobal_share);
            imageViewSetting = (ImageView) findViewById(R.id.toobal_setting);
            rightRedPoint = (ImageView) findViewById(R.id.right_red_point);
        } else {
            parentView.addView(getLayoutInflater().inflate(layoutResID, null), LAYOUT_PARAMS);
            unbinder = ButterKnife.bind(this);
        }
    }

    public ViewGroup getParentView() {
        return parentView;
    }

    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setStatusBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.hideSoftInput(BaseActivity.this);
                finish();
            }
        });
    }


    protected void setToolbarStyleWhite() {
        getToolbar().setNavigationIcon(R.drawable.button_back_black);
        getToolbar().setBackgroundColor(getResources().getColor(R.color.text_color_white));
    }

    protected void setStatusBar() {
        setToolbarStyleWhite();
//        if (StatusBarUtil.SYSTEM_OTHERS == StatusBarUtil.StatusBarLightMode(this)) {
//            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 200);
//        } else {
//            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
//        }

        StatusBarUtil.setColor(this, getResources().getColor(R.color.text_color_black));
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    public TextView getRightTitleView() {
        this.textViewRightTitle.setVisibility(View.VISIBLE);
        return this.textViewRightTitle;
    }

    public ImageView getRightRedPoint() {
        this.rightRedPoint.setVisibility(View.VISIBLE);
        return this.rightRedPoint;
    }


    public void setViewTitle(@StringRes int resId) {
        setViewTitle(resId, Color.parseColor("#ffffff"));
    }

    public void setViewTitle(CharSequence title) {
        if (null != textViewTitle) {
            textViewTitle.setText(title);
        }
    }

    protected void setViewTitle(@StringRes int resId, int color) {
        if (null != textViewTitle) {
            textViewTitle.setText(resId);
            textViewTitle.setTextColor(color);
        }
    }

    public void setViewTitle(CharSequence title, int color) {
        if (null != textViewTitle) {
            textViewTitle.setText(title);
            textViewTitle.setTextColor(color);
        }
    }


    public void setToolbarBackageColor(int color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(color);
        }
    }

    public void setToolbarVisible(boolean isVisible) {
        toolbar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setShareVisiable(boolean isVisible) {
        imageViewShare.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setSettingVisiable(boolean isVisible) {
        imageViewSetting.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public ImageView getImageViewSetting() {
        return imageViewSetting;
    }

    public ImageView getShare() {
        return imageViewShare;
    }

    @Override
    public boolean isDestroy() {
        return isDestroy;
    }

    private LoadingDialog dialog;

    /**
     * 显示进度对话框
     *
     * @param canCancel
     */
    public void showWaitDialog(final boolean canCancel) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null == dialog) {
                    dialog = new LoadingDialog.Builder().setMessage(getString(R.string.dialog_title_waiting))
                            .setCanCancel(canCancel)
                            .setCanceledOnTouchOutside(false).create(mContext);
                }
                dialog.show();
            }
        });

    }

    /**
     * 提示信息
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        ToastUtils.showToastImage(AppContext.getContext(), msg, 0);
    }

    /**
     * 提示信息
     *
     * @param id
     */
    public void showToast(int id) {
        if (id == 0 || id == -1) {
            return;
        }
        ToastUtils.toastShort(id);
    }

    /**
     * 从本页面跳转到另外一个页面
     *
     * @param cls 需要跳转到的页面
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 带着数据，从本页面跳转到另外一个页面
     *
     * @param cls 需要跳转到的页面
     * @param obj 传递给下个页面的数据
     */
    public void startActivity(Class<?> cls, Object obj) {
        Intent intent = new Intent(this, cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
    }

    /**
     * 带着数据，从本页面跳转到另外一个页面
     * 包含多个共享元素的转场动画
     */
    public void startActivity(Class<?> cls, Object obj, Pair<View, String>... sharedElements) {
        Intent intent = new Intent(this, cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements);
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }

    /**
     * 带着数据，设置返回码，从本页面跳转到下个页面，重写onActivityResult可以获取从下个页面带回来的数据
     *
     * @param cls 需要跳转到的页面
     * @param obj 传递给下个页面的数据
     */
    public void startActivityForResult(Class<?> cls, Object obj, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
    }

    /**
     * 带着数据，设置返回码，从本页面跳转到下个页面，重写onActivityResult可以获取从下个页面带回来的数据
     * 包含多个共享元素的转场动画
     *
     * @param cls 需要跳转到的页面
     * @param obj 传递给下个页面的数据
     */
    public void startActivityForResult(Class<?> cls, Object obj, int requestCode, Pair<View, String>... sharedElements) {
        Intent intent = new Intent(this, cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements);
        ActivityCompat.startActivityForResult(this, intent, requestCode, compat.toBundle());
    }


    @Override
    public void showWaitDialog() {
        showWaitDialog(true);
    }

    @Override
    public void dismissWaitDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.dismiss(dialog);
            }
        });
    }

}
