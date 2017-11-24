package com.cicada.startup.common.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cicada.startup.common.R;
import com.cicada.startup.common.glide.GlideImageDisplayer;
import com.cicada.startup.common.ui.activity.IBaseView;
import com.cicada.startup.common.ui.wight.LoadingDialog;
import com.cicada.startup.common.utils.ToastUtils;
import com.cicada.startup.common.utils.UiHelper;
import com.tendcloud.tenddata.TCAgent;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by zyh on 2016/8/17.
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    protected int mLayoutId = 0;// 布局Id
    protected View rootView;
    protected Unbinder unbinder;
    protected boolean isDestory = false;

    public BaseFragment(int resId) {
        mLayoutId = resId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(mLayoutId, null);
            unbinder = ButterKnife.bind(this, rootView);
            InitView();
        }

        return rootView;
    }

    /**
     * 初始化控件,在子类中相当于onCreateView()方法
     */
    protected abstract void InitView();

    /**
     * 根据id获取View
     *
     * @param id
     * @return View
     */
    public View findViewById(int id) {
        return rootView.findViewById(id);
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
        ToastUtils.showToastImage(getActivity(), msg, 0);
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
        Intent intent = new Intent(getActivity(), cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
    }

    /**
     * 带着数据，从本页面跳转到另外一个页面
     * 包含多个共享元素的转场动画
     */
    public void startActivity(Class<?> cls, Object obj, Pair<View, String>... sharedElements) {
        Intent intent = new Intent(getActivity(), cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), sharedElements);
        ActivityCompat.startActivity(getActivity(), intent, compat.toBundle());
    }

    /**
     * 带着数据，设置返回码，从本页面跳转到下个页面，重写onActivityResult可以获取从下个页面带回来的数据
     *
     * @param cls 需要跳转到的页面
     * @param obj 传递给下个页面的数据
     */
    public void startActivityForResult(Class<?> cls, Object obj, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
    }

    /**
     * 带着数据，设置返回码，从本页面跳转到下个页面，重写onActivityResult可以获取从下个页面带回来的数据
     * 包含多个共享元素的转场动画
     *
     * @param cls 需要跳转到的页面
     * @param obj 传递给下个页面的数据
     */
    public void startActivityForResult(Class<?> cls, Object obj, int requestCode, Pair<View, String>... sharedElements) {
        Intent intent = new Intent(getActivity(), cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), sharedElements);
        ActivityCompat.startActivityForResult(getActivity(), intent, requestCode, compat.toBundle());
    }

    /**
     * 加载网络图片
     * 默认的加载中、失败图片
     */
    public void displayImage(ImageView imageView, String url) {
        GlideImageDisplayer.displayImage(getActivity(), imageView, url, R.drawable.default_image);
    }

    /**
     * 加载网络图片
     * 指定加载中、失败图片
     */
    public void displayImage(ImageView imageView, String url, int resId) {
        GlideImageDisplayer.displayImage(getActivity(), imageView, url, resId);
    }

    /**
     * 加载资源图片
     */
    public void displayImage(ImageView imageView, int resId) {
        GlideImageDisplayer.displayImage(getActivity(), imageView, resId);
    }

    /**
     * 加载裁剪到固定尺寸图片
     */
    public void displayImageWithSize(ImageView imageView, String url, int width, int hight) {
        GlideImageDisplayer.displayImageWithSize(getActivity(), imageView, url, width, hight);
    }

    /**
     * 加载Gif图片
     */
    public void displayGif(ImageView imageView, String url, int resId) {
        GlideImageDisplayer.displayGifImage(getActivity(), imageView, url, resId);
    }

    /**
     * 加载圆形图片
     * 默认的加载中、失败图片
     */
    public void displayCircleImage(ImageView imageView, String url) {
        GlideImageDisplayer.displayCircleImage(getActivity(), imageView, url, R.drawable.default_image);
    }

    /**
     * 加载圆形图片
     * 指定加载中、失败图片
     */
    public void displayCircleImage(ImageView imageView, String url, int resId) {
        GlideImageDisplayer.displayCircleImage(getActivity(), imageView, url, resId);
    }

    /**
     * 加载圆角图片
     * 默认的加载中、失败图片
     */
    public void displayRoundImage(ImageView imageView, String url, int round) {
        GlideImageDisplayer.displayRoundImage(getActivity(), imageView, url, R.drawable.default_image, round);
    }

    /**
     * 加载圆角图片
     * 指定的加载中、失败图片
     */
    public void displayRoundImage(ImageView imageView, String url, int round, int resId) {
        GlideImageDisplayer.displayRoundImage(getActivity(), imageView, url, resId, round);
    }

    private String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        TCAgent.onPageStart(getActivity(), this.getTAG());
    }

    @Override
    public void onPause() {
        super.onPause();
        TCAgent.onPageEnd(getActivity(), this.getTAG());
    }

    @Override
    public void onDestroy() {
        dismissWaitDialog();
//        unbinder.unbind();
        isDestory = true;
        UiHelper.hideSoftInput(getActivity());
        super.onDestroy();
    }

    private LoadingDialog dialog;

    /**
     * 显示进度对话框
     *
     * @param canCancel
     */
    private void showWaitDialog(final boolean canCancel) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null == dialog) {
                    dialog = new LoadingDialog.Builder().setMessage(getString(R.string.dialog_title_waiting))
                            .setCanCancel(canCancel)
                            .setCanceledOnTouchOutside(false).create(getActivity());
                }
                dialog.show();
            }
        });

    }

    @Override
    public boolean isDestroy() {
        return isDestory;
    }

    @Override
    public void showWaitDialog() {
        showWaitDialog(true);
    }

    @Override
    public void dismissWaitDialog() {
        if (null != getActivity()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.dismiss(dialog);
                }
            });
        } else {
            LoadingDialog.dismiss(dialog);
        }
    }
}
