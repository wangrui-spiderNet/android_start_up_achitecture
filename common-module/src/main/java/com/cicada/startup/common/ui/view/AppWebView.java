package com.cicada.startup.common.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.utils.DeviceUtils;
import com.cicada.startup.common.utils.LogUtils;

/**
 * <p/>
 * 创建时间: 16/7/18 下午6:06 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class AppWebView extends WebView {
    protected OnScrollChangedCallback mOnScrollChangedCallback;
    public AppWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public AppWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AppWebView(Context context) {
        super(context);
        initView();
    }

    private void initView() {

        try {
            WebSettings settings = getSettings();
            settings.setAllowFileAccess(true);
            settings.setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT > 16) {
                settings.setMediaPlaybackRequiresUserGesture(false);
            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            }
            if (Build.VERSION.SDK_INT >= 21) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            settings.setDefaultTextEncodingName("UTF_8");
            settings.setSupportZoom(true);// 设置支持缩放控件
            settings.supportMultipleWindows();// 设置允许浮窗
            settings.setAllowFileAccess(true);// 设置允许访问文件
            // 下面的两个设置目的是让加载进来的页面自动适应webview宽高比
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setBuiltInZoomControls(false);// 隐藏缩放按钮
            settings.setUserAgentString(settings.getUserAgentString() + " AppName/yxb  (VersionName/" + DeviceUtils.getVersionName(getContext()) + ")");
            // 开启sql数据库
            settings.setDatabaseEnabled(true);
            // 设置sql 数据库路径 android4.1需求
            settings.setDatabasePath(AppContext.getAppRootDir() + "/html5webview/databases/");

            // 开启appcache
            settings.setAppCacheEnabled(true);
            // 设置缓存大小最大8m
            settings.setAppCacheMaxSize(1024 * 1024 * 8);
            settings.setAppCachePath(AppContext.getAppRootDir() + "/html5webview/appcache/");
            // 默认使用缓存
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);

            // Jellybean rightfully tried to lock this down. Too bad they didn't
            // give us a whitelist
            // 开启跨域
            if (Build.VERSION.SDK_INT > 15)
                Level16Apis.enableUniversalAccess(settings);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            if (Build.VERSION.SDK_INT >= 11) {
                settings.setAllowContentAccess(true);
            }
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= 7) {
                settings.setDomStorageEnabled(true);
            }
            clearFocus();
            clearView();
//            setVerticalScrollBarEnabled(true);
//            setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            settings.setGeolocationEnabled(true);// 启用地理定位
            String databasePath = AppContext.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
            settings.setGeolocationDatabasePath(databasePath);//
            //下载操作
            setDownloadListener(new DownloadOperationListener());
        } catch (Exception e) {
            LogUtils.e(this.getClass().getName(), e.getMessage());
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    @TargetApi(16)
    private static class Level16Apis {
        static void enableUniversalAccess(WebSettings settings) {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
    }

    private class DownloadOperationListener implements DownloadListener{
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppContext.getContext().startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setOnScrollChangedCallback(OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public static interface OnScrollChangedCallback {
        public void onScroll(int dx, int dy);
    }

}
