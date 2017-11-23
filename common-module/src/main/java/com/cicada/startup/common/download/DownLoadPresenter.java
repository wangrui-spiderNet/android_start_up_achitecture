package com.cicada.startup.common.download;


import com.cicada.startup.common.http.BaseURL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TODO
 * <p>
 * Create time: 2017/1/16 13:48
 *
 */
public class DownLoadPresenter {

    private static volatile DownLoadPresenter mInstance;
    private DownloadApi mApi;

    private DownLoadPresenter() {

    }

    public static DownLoadPresenter getInstance() {
        if (mInstance == null) {
            synchronized (DownLoadPresenter.class) {
                if (mInstance == null) {
                    mInstance = new DownLoadPresenter();
                }
            }
        }
        return mInstance;
    }

    public DownloadApi getApi() {
        if (mApi == null) {
            synchronized (DownLoadPresenter.class) {
                if (mApi == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BaseURL.getBaseURL())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    mApi = retrofit.create(DownloadApi.class);
                }
            }
        }
        return mApi;
    }
}
