package com.cicada.startup.common.http.upload;

import android.os.Handler;
import android.os.Looper;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;
import com.cicada.startup.common.domain.UploadToken;
import com.cicada.startup.common.http.BaseURL;
import com.cicada.startup.common.http.domain.UploadResult;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

/**
 * 上传文件
 * <p>
 * 创建时间: 16/7/14 下午4:52 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class UploadFileHelper {

    public static final int UPLOAD_SUCCESS_CODE = 200;
    private static Retrofit instance;
    private static UploadFileHelper uploadFileHelper;
    private UploadManager manager = new UploadManager();
    private List<UploadResult> data = new ArrayList<>();
    private List<UploadResult> failureList = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private int count = 0;

    public static UploadFileHelper getInstance() {
        if (null == uploadFileHelper) {
            synchronized (UploadFileHelper.class) {
                if (null == uploadFileHelper) {
                    uploadFileHelper = new UploadFileHelper();
                }
            }
        }
        return uploadFileHelper;
    }

//    private static <T> T createService(Class<T> clazz) {
//
//        if (null == instance) {
//            synchronized (UploadFileHelper.class) {
//                if (null == instance) {
//
//                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//                    OkHttpClient client = new OkHttpClient.Builder()
////                            .addNetworkInterceptor(new StethoInterceptor())
//                            .addInterceptor(new TokenInterceptor())
//                            .addInterceptor(logging)
//                            .connectTimeout(60 * 5, TimeUnit.SECONDS)
//                            .readTimeout(60 * 5, TimeUnit.SECONDS)
//                            .writeTimeout(60 * 5, TimeUnit.SECONDS)
//                            .build();
//                    instance = new Retrofit.Builder()
//                            .baseUrl(BaseURL.getBaseURL())
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                            .client(client)
//                            .build();
//                }
//            }
//        }
//
//        return instance.create(clazz);
//    }
//
//
//    private static class TokenInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request.Builder newBuilder = chain.request().newBuilder();
//            HttpUrl url = chain.request().url();
//            newBuilder.url(url + "?token=" + BaseAppPreferences.getInstance().getLoginToken());
//            return chain.proceed(newBuilder.build());
//        }
//    }

//    public static void uploadFiles(final List<String> files, final MultiUploadListener listener) {
//
//        Observable<UploadResponse<List<UploadResult>>> uploadObservable = getUploadObservable(files);
//        uploadObservable
//                .subscribe(new DefaultSubscriber<UploadResponse<List<UploadResult>>>() {
//                    @Override
//                    public void onSuccess(UploadResponse<List<UploadResult>> result) {
//                        if (UPLOAD_SUCCESS_CODE == result.getCode()) {
//                            List<UploadResult> data = result.getData();
//                            listener.onSuccess(data, failureList);
//                            FileUtils.deleteUploadTempPic(files);
//                        } else {
//                            onFailure(BaseURL.APP_EXCEPTION_HTTP_500,
//                                    AppContext.getContext().getString(R.string.app_exception_server));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String errorCode, String errorMessage) {
//                        listener.onFailure(errorCode, errorMessage);
//                        FileUtils.deleteUploadTempPic(files);
//                    }
//                });
//    }

    public void uploadFiles(final UploadToken uploadToken, final List<UploadResult> files, final MultiUploadListener listener) {
        data.clear();
        failureList.clear();
        count = 0;
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < files.size(); i++) {
                    UploadResult uploadResult = files.get(i);
                    upload(uploadResult, files.size(), uploadToken, listener);
                }
            }
        }.start();
    }

    private void upload(final UploadResult path, final int totalCount, final UploadToken uploadToken, final MultiUploadListener listener) {
        File file = new File(path.getUrl());
        manager.put(file, null, uploadToken.getToken(), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                try {
                    if (info.isOK()) {
                        count++;
                        String imgLink = response.getString("key");
                        path.setUrl("http://" + uploadToken.getMy_domain().trim() + "/" + imgLink.trim());
                        if (!data.contains(path)) {
                            data.add(path);
                        }
                    } else {
                        count++;
                        if (!failureList.contains(path)) {
                            failureList.add(path);
                        }
                    }
                    if (count == totalCount) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(data, failureList);
                            }
                        });
                    }
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(BaseURL.APP_EXCEPTION_HTTP_500,
                                    AppContext.getContext().getString(R.string.app_exception_server));
                        }
                    });
                    e.printStackTrace();
                }
            }
        }, null);
    }

//    public static Observable<UploadResponse<List<UploadResult>>> getUploadObservable(List<String> files) {
//        Map<String, RequestBody> params = new HashMap<>();
//
//        for (String path : files) {
//            File file = new File(path);
//            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            params.put("files\"; filename=\"" + file.getName(), photoRequestBody);
//        }
//        return createService(UploadFileApi.class)
//                .uploadFiles(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }


//    public static void uploadImage(final String filePath, final UploadListener listener) {
//        File file = new File(filePath);
//        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("*/*"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
//        createService(UploadFileApi.class)
//                .uploadFile(part)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultSubscriber<UploadResponse<UploadResult>>() {
//                    @Override
//                    public void onSuccess(UploadResponse<UploadResult> result) {
//                        if (UPLOAD_SUCCESS_CODE == result.getCode()) {
//                            UploadResult data = result.getData();
//                            listener.onSuccess(data);
//                            FileUtils.deleteUploadTempPic(filePath);
//                        } else {
//                            onFailure(BaseURL.APP_EXCEPTION_HTTP_500,
//                                    AppContext.getContext().getString(R.string.app_exception_server));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String errorCode, String errorMessage) {
//                        listener.onFailure(errorCode, errorMessage);
//                        //FileUtils.deleteUploadTempPic(filePath);
//                    }
//                });
//    }

    public void uploadImage(final String filePath, final UploadToken uploadToken, final UploadListener listener) {
        File file = new File(filePath);
        manager.put(file, file.getName(), uploadToken.getToken(), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                try {
                    if (info.isOK()) {
                        String imgLink = response.getString("key");
                        final UploadResult result = new UploadResult();
                        result.setUrl("http://" + uploadToken.getMy_domain().trim() + "/" + imgLink.trim());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(result);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFailure(BaseURL.APP_EXCEPTION_HTTP_500,
                                        AppContext.getContext().getString(R.string.app_exception_server));
                            }
                        });
                    }
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(BaseURL.APP_EXCEPTION_HTTP_500,
                                    AppContext.getContext().getString(R.string.app_exception_server));
                        }
                    });
                    e.printStackTrace();
                }
            }
        }, null);
    }


    public interface UploadListener {
        void onSuccess(UploadResult result);

        void onFailure(String errorCode, String errorMessage);
    }

    public interface MultiUploadListener {
        void onSuccess(List<UploadResult> result, List<UploadResult> failureList);

        void onFailure(String errorCode, String errorMessage);
    }
}
