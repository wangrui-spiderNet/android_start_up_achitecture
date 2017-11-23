package com.cicada.startup.common.download;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;
import com.cicada.startup.common.utils.FileUtils;
import com.cicada.startup.common.utils.LogUtils;
import com.cicada.startup.common.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * download
 * <p>
 * Create time: 2017/1/11 11:15
 */
public class DownLoadAppBackService extends IntentService {

    public static final String BUNDLE_KEY_DOWNLOAD_URL = "download_url";
    public static final String BUNDLE_KEY_VERSION_NAME = "version_name";
    public static final String ACTION_UPDATE_STATE = "action_update_state";
    public static final String IS_DOWNLOAD_SUCCESS = "is_download_success";

    private static String TAG = "DownLoadAppBackService";
    private NotificationManager manager;
    private int notifiId = 789;
    private int progress = 0;
    private String versionName;
    private String apkPath;
    // 定义Map来保存Notification对象
    private Map<Integer, Notification> map = null;
    private static Context context;

    public DownLoadAppBackService() {
        super("DownLoadAppBackService");
    }

    public static void startDownLoadAppService(Context cxt, String versionName, String url) {
        ActivityManager manager = (ActivityManager) cxt.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (DownLoadAppBackService.class.getName().equals(service.service.getClassName())) {
                Log.d(TAG, "-->不再启动服务，已经在下载了");
                return;//退出不再继续执行
            }
        }
        context = cxt;
        Intent startServiceIntent = new Intent(cxt, DownLoadAppBackService.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString(BUNDLE_KEY_VERSION_NAME, versionName);
        bundle2.putString(BUNDLE_KEY_DOWNLOAD_URL, url);
        startServiceIntent.putExtras(bundle2);
        Log.d(TAG, "-->启动了下载服务");
        cxt.startService(startServiceIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        //NotificationManager 是一个系统Service，必须通过 getSystemService()方法来获取。
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        map = new HashMap<>();
        this.versionName = intent.getExtras().getString(BUNDLE_KEY_VERSION_NAME);
        this.apkPath = AppContext.getDownLoadApkPath(versionName);
        String url = intent.getExtras().getString(BUNDLE_KEY_DOWNLOAD_URL);
        showNotification(notifiId);
        LogUtils.d(TAG, "-->同步下载开始");
        // 启动后台服务下载apk
        Call<ResponseBody> call = DownLoadPresenter.getInstance().getApi().downloadFile(url);
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                LogUtils.d(TAG, "server contacted and has file");
                boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                LogUtils.d(TAG, "file download was a success? " + writtenToDisk);
                cancel(notifiId);
                if (writtenToDisk) {
                    updateDownloadState(true);
                    FileUtils.ApkInstall(context, apkPath);
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToastImage(context, "更新失败", 0);
                        }
                    });
                }
            } else {
                LogUtils.d(TAG, "server contact failed");
                cancel(notifiId);
                call.cancel();
                updateDownloadState(false);
                stopSelf();
                ToastUtils.showToastImage(context, "更新失败", 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            call.cancel();
            stopSelf();
            updateDownloadState(false);
        }
        LogUtils.d(TAG, "-->同步下载结束");
    }

    /**
     * 写入
     *
     * @param body
     * @return
     */
    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File futureStudioIconFile = new File(apkPath);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    int pro = (int) (fileSizeDownloaded * 100 / fileSize);
                    if (pro > progress) {
                        progress = pro;
                        updateProgress(notifiId, pro, fileSize, fileSizeDownloaded);
                        LogUtils.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 显示通知
     */
    public void showNotification(int notificationId) {
        if (!map.containsKey(notificationId)) {
            String tickerText = "新版本已经开始更新";
            Notification notification = new Notification();
            notification.tickerText = tickerText;
            notification.when = System.currentTimeMillis();
            notification.icon = R.drawable.app_notification_icon;
            // 设置通知的特性: 通知被点击后，自动消失
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            // 设置通知的显示视图
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_download);
            notification.contentView = remoteViews;
            // 发出通知
            manager.notify(notificationId, notification);
            map.put(notificationId, notification);// 存入Map中
        }
    }

    /**
     * 取消通知操作
     *
     * @param notificationId
     */
    public void cancel(int notificationId) {
        manager.cancel(notificationId);
        map.remove(notificationId);
    }

    /**
     * 显示进度
     *
     * @param notificationId
     * @param progress
     */
    public void updateProgress(int notificationId, int progress, double total, double current) {
        this.progress = progress;
        Notification notify = map.get(notificationId);
        if (null != notify) {
            double totalSize = ((int) (total * 100 / (1024 * 1024))) / 100.0;
            double currentSize = ((int) (current * 100 / (1024 * 1024))) / 100.0;
            BigDecimal bd = new BigDecimal(currentSize);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            String currentSizeStr = bd.toString();
            // 修改进度条
            notify.contentView.setTextViewText(R.id.tv_version_name, "v" + versionName);
            notify.contentView.setTextViewText(R.id.tv_download_state,
                    progress + "%" + "(" + currentSizeStr + "M/" + totalSize + "M)"
            );
            notify.contentView.setProgressBar(R.id.pb_download, 100, progress, false);
            manager.notify(notificationId, notify);
        }
    }

    private void updateDownloadState(boolean isSuccess) {
        Intent intent = new Intent(ACTION_UPDATE_STATE);
        intent.putExtra(IS_DOWNLOAD_SUCCESS, isSuccess);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
