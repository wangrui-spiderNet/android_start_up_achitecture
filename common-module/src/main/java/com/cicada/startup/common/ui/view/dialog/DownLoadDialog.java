package com.cicada.startup.common.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;
import com.cicada.startup.common.download.DownLoadPresenter;
import com.cicada.startup.common.utils.DeviceUtils;
import com.cicada.startup.common.utils.FileUtils;
import com.cicada.startup.common.utils.LogUtils;
import com.cicada.startup.common.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * TODO
 * <p>
 * Create time: 2017/8/9 10:21
 *
 * @author liuyun.
 */
public class DownLoadDialog extends Dialog {

    Context context;
    private TextView tv_version_name, tv_download_state;
    private ProgressBar pb_download;
    private String downloadUrl;
    private String versionName;
    private String apkPath;
    private int progress = 0;
    private long totalFileSize = 0l;
    private long currentFileSize = 0l;

    public DownLoadDialog(Context context) {
        super(context, R.style.MyDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadPath() {
        return apkPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.apkPath = downloadPath;
    }

    public static class Builder {
        private Context context;
        private String downloadUrl;
        private String versionName;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder withUrl(String url) {
            this.downloadUrl = url;
            return this;
        }

        public Builder withVersionName(String versionName) {
            this.versionName = versionName;
            return this;
        }

        public DownLoadDialog build() {
            DownLoadDialog dialog = new DownLoadDialog(this.context);
            try {
                dialog.setDownloadUrl(downloadUrl);
                dialog.setVersionName(versionName);
                String apkPath = AppContext.getDownLoadApkPath(versionName);
                dialog.setDownloadPath(apkPath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Window win = dialog.getWindow();
            win.setBackgroundDrawableResource(R.color.transparent);
            win.getDecorView().setPadding(0, 0, 0, 0);
            win.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            win.setAttributes(lp);
            return dialog;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download);
        initView();
        downLoad();
    }

    private void initView() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        tv_download_state = (TextView) findViewById(R.id.tv_download_state);
        pb_download = (ProgressBar) findViewById(R.id.pb_download);
        tv_version_name.setText(versionName);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pb_download.getLayoutParams();
        lp.weight = DeviceUtils.getScreenWidth(context);
        pb_download.setLayoutParams(lp);
    }

    private void downLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 启动后台服务下载apk
                Call<ResponseBody> call = DownLoadPresenter.getInstance().getApi().downloadFile(downloadUrl);
                try {
                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful()) {
                        LogUtils.d(TAG, "server contacted and has file");
                        boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                        LogUtils.d(TAG, "file download was a success? " + writtenToDisk);
                        if (writtenToDisk) {
                            mHandler.sendEmptyMessage(1);
                        } else {
                            mHandler.sendEmptyMessage(0);
                        }
                    } else {
                        LogUtils.d(TAG, "server contact failed");
                        call.cancel();
                        downloadFailed();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    call.cancel();
                    downloadFailed();
                }
            }
        }).start();

    }


    private void downloadSuccess() {
        this.dismiss();
        FileUtils.ApkInstall(context, apkPath);
    }

    private void downloadFailed() {
        this.dismiss();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToastImage(context, "更新失败", 0);
            }
        });
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

                totalFileSize = body.contentLength();
                currentFileSize = 0l;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    currentFileSize += read;
                    int pro = (int) (currentFileSize * 100 / totalFileSize);
                    if (pro > progress) {
                        progress = pro;
                        mHandler.sendEmptyMessage(2);
                        LogUtils.d(TAG, "file download: " + currentFileSize + " of " + totalFileSize);
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

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    downloadFailed();
                    break;
                case 1:
                    downloadSuccess();
                    break;
                case 2:
                    updateProgress();
                    break;
            }
        }
    };

    /**
     * 显示进度
     */
    private void updateProgress() {
        double totalSize = ((int) (totalFileSize * 100 / (1024 * 1024))) / 100.0;
        double currentSize = ((int) (currentFileSize * 100 / (1024 * 1024))) / 100.0;
        BigDecimal bd = new BigDecimal(currentSize);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        String currentSizeStr = bd.toString();
        tv_download_state.setText(progress + "%" + "(" + currentSizeStr + "M/" + totalSize + "M)");
        pb_download.setProgress(progress);
    }


}
