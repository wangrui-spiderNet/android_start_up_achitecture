package com.cicada.startup.common.download;

import android.app.Activity;
import android.content.Context;

import com.cicada.startup.common.utils.LogUtils;
import com.cicada.startup.common.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 下载
 * <p>
 * Create time: 2017/2/21 17:37
 *
 */
public class AppDownLoadManager {
    private Context context;
    private static String TAG = AppDownLoadManager.class.getSimpleName();

    public AppDownLoadManager(Context context) {
        this.context = context;
    }


    /**
     * 下载文件
     *
     * @param fillPath 文件存储目标路径
     * @param url      下载路径
     */
    public void downLoad(final String fillPath, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<ResponseBody> call = DownLoadPresenter.getInstance().getApi().downloadFile(url);
                try {
                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful()) {
                        LogUtils.d(TAG, "server contacted and has file");
                        boolean writtenToDisk = writeResponseBodyToDisk(response.body(), fillPath);
                        LogUtils.d(TAG, "file download was a success? " + writtenToDisk);
                        if (writtenToDisk) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.toastShort(context, "下载成功");
                                }
                            });

                        } else {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.toastShort(context, "下载失败");
                                }
                            });
                        }
                    } else {
                        LogUtils.d(TAG, "server contact failed");
                        call.cancel();
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(context, "下载失败");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    call.cancel();
                }
            }
        }).start();

    }

    private int progress = 0;

    /**
     * 写入
     *
     * @param body
     * @return
     */
    private boolean writeResponseBodyToDisk(ResponseBody body, String fillPath) {
        try {
            File futureStudioIconFile = new File(fillPath);
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
                    LogUtils.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    if (pro > progress) {
                        progress = pro;
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

}
