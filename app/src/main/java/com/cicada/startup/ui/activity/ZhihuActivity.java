package com.cicada.startup.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cicada.startup.MyApplication;
import com.cicada.startup.R;
import com.cicada.startup.data.Injection;
import com.cicada.startup.data.remote.model.ZhihuStoryDetail;
import com.cicada.startup.ui.listener.AppBarStateChangeListener;
import com.cicada.startup.ui.widget.MarqueeText;
import com.cicada.startup.viewmodel.ZhihuViewModel;

public class ZhihuActivity extends BaseActivity {

    public static final String ZHIHU_TITLE = "zhihu_title";

    public static final String ZHIHU_ID = "zhihu_id";

    private ImageView mIVZhihuHeader = null;

    private String mZhihuTitle = null;

    private WebView mWebView = null;

    private String mZhihuId = null;


    public static void startZhihuActivity(Activity activity, String zhihuId, String zhihuTitle) {
        if (activity == null || TextUtils.isEmpty(zhihuId)
                || TextUtils.isEmpty(zhihuTitle)) {
            return;
        }
        Intent intent = new Intent(activity, ZhihuActivity.class);
        intent.putExtra(ZhihuActivity.ZHIHU_ID, zhihuId);
        intent.putExtra(ZhihuActivity.ZHIHU_TITLE, zhihuTitle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        readIntent();
        initView();
        subscribeUI();
    }

    private void subscribeUI() {
        ZhihuViewModel.Factory factory = new ZhihuViewModel.Factory(MyApplication.getInstance(),
                Injection.getDataRepository(MyApplication.getInstance()), mZhihuId);
        ZhihuViewModel zhihuViewModel = ViewModelProviders.of(this, factory).get(ZhihuViewModel.class);
        zhihuViewModel.getZhihuDetail().observe(this, new Observer<ZhihuStoryDetail>() {
            @Override
            public void onChanged(@Nullable ZhihuStoryDetail detail) {
                if (detail == null) {
                    return;
                }
                Glide.with(ZhihuActivity.this)
                        .load(detail.getImage())
                        .centerCrop()
                        .into(mIVZhihuHeader);
                mWebView.loadUrl(detail.getShare_url());
/*                if (TextUtils.isEmpty(detail.getBody())) {
                    mWebView.loadUrl(detail.getShare_url());
                } else {
                    String body = detail.getBody();
                    String[] cssList = detail.getCss();
                    String htmlTemp = WebUtil.buildHtmlWithCss(body, cssList, false);
                    mWebView.loadDataWithBaseURL(WebUtil.BASE_URL, htmlTemp, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
                }*/
            }
        });
    }

    private void initView() {
        mIVZhihuHeader = (ImageView) findViewById(R.id.iv_zhihu_head);

        mWebView = (WebView) findViewById(R.id.wv_zhihu);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        //settings.setUseWideViewPort(true);造成文字太小
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient());


        final CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
        final MarqueeText marqueeText = (MarqueeText)findViewById(R.id.toolbar_title);
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_layout);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.tb_zhihu);
        initToolbar(toolbar, true, mZhihuTitle);
        marqueeText.setText(mZhihuTitle);
        toolbarLayout.setTitle(mZhihuTitle);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    marqueeText.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    marqueeText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void readIntent() {
        mZhihuId = this.getIntent().getStringExtra(ZHIHU_ID);
        mZhihuTitle = this.getIntent().getStringExtra(ZHIHU_TITLE);
    }
}
