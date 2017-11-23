package com.cicada.startup.common.http.retrofit;

import android.text.TextUtils;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.config.BaseAppPreferences;
import com.cicada.startup.common.domain.LoginResponse;
import com.cicada.startup.common.http.BaseURL;
import com.cicada.startup.common.utils.PreferencesUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Retrofit
 * <p>
 * 创建时间: 16/5/3 上午10:50 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class RetrofitUtils {

    private static Retrofit instance;


    public static <T> T createService(Class<T> clazz) {

        if (null == instance) {
            synchronized (RetrofitUtils.class) {
                if (null == instance) {

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder()
//                            .addNetworkInterceptor(new StethoInterceptor())
                            .addInterceptor(new TokenInterceptor())
                            .addInterceptor(logging)
                            .connectTimeout(60 * 5, TimeUnit.SECONDS)
                            .readTimeout(60 * 5, TimeUnit.SECONDS)
                            .writeTimeout(60 * 5, TimeUnit.SECONDS)
                            .build();
                    instance = new Retrofit.Builder()
                            .baseUrl(BaseURL.getBaseURL())
                            .addConverterFactory(ResponseConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }

        return instance.create(clazz);
    }


    private static class TokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder newBuilder = chain.request().newBuilder();
            HttpUrl url = chain.request().url();
            LoginResponse response = PreferencesUtil.getPreferences(AppContext.getContext(), "user_info");
            if (null != response) {
                if (!TextUtils.isEmpty(response.getToken())) {
                    newBuilder.url(url + "?token=" + response.getToken());
                }
            } else {
                newBuilder.url(url + "?token=" + BaseAppPreferences.getInstance().getLoginToken());
            }
            return chain.proceed(newBuilder.build());
        }
    }

    /**
     * @param clazz
     * @param urlParams url后拼接的参数 (&pageIndex=1&pageSize=100 ...)
     * @param <T>
     * @return
     */
    public static <T> T createUrlParamsService(Class<T> clazz, String urlParams) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TokenAndParamsInterceptor(urlParams))
                .addInterceptor(logging)
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .writeTimeout(60 * 5, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL.getBaseURL())
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(clazz);
    }

    private static class TokenAndParamsInterceptor implements Interceptor {

        private String urlParams;

        public TokenAndParamsInterceptor(String urlParams) {
            this.urlParams = urlParams;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder newBuilder = chain.request().newBuilder();
            HttpUrl url = chain.request().url();
            LoginResponse response = PreferencesUtil.getPreferences(AppContext.getContext(), "user_info");
            if (null != response) {
                if (!TextUtils.isEmpty(response.getToken())) {
                    newBuilder.url(url + "?token=" + response.getToken() + urlParams);
                }
            } else {
                newBuilder.url(url + "?token=" + BaseAppPreferences.getInstance().getLoginToken() + urlParams);
            }
            return chain.proceed(newBuilder.build());

        }
    }
}
