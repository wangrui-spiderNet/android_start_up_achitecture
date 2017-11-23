package com.cicada.startup.common.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.config.AppEnvConfig;
import com.cicada.startup.common.manager.AppManager;
import com.cicada.startup.common.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxuanxi
 */
public class AppException extends Exception implements UncaughtExceptionHandler {
    private static final long serialVersionUID = 1L;
    private static Context mContext;

    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setExCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setExMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AppException(Context context) {
        AppException.mContext = context;
    }


    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
//            CicadaIMSDKHelper.getInstance().stopMQTT(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 上传异常到友盟
        if (Looper.getMainLooper().getThread() != thread) {
            // 如果是后台线程崩溃,则忽略.
            return;
        }
        if (!handleException(ex)) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            AppManager.getInstance().finishAllActivity();
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            AppManager.getInstance().finishAllActivity();
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        if (!(AppContext.getAppEnv() == AppEnvConfig.RELEASE)) {
            // 获取设备参数信息
            getDeviceInfo(mContext);
            // 保存日志文件
            saveCrashLogToFile(ex);
        }
        return true;
    }

    /**
     * 用来存储设备信息和异常信息 Map<String,String> : mLogInfo
     *
     * @since 2013-3-21下午8:46:15
     */
    private Map<String, String> mLogInfo = new HashMap<String, String>();

    /**
     * getDeviceInfo:{获取设备参数信息}
     *
     * @param paramContext
     * @throws
     * @since I used to be a programmer like you, then I took an arrow in the
     * knee　Ver 1.0 2013-3-24下午12:30:02 Modified By Stone.J
     */
    public void getDeviceInfo(Context paramContext) {
        try {
            // 获得包管理器
            PackageManager mPackageManager = paramContext.getPackageManager();
            // 得到该应用的信息，即主Activity
            PackageInfo mPackageInfo = mPackageManager.getPackageInfo(paramContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (mPackageInfo != null) {
                String versionName = mPackageInfo.versionName == null ? "null" : mPackageInfo.versionName;
                String versionCode = mPackageInfo.versionCode + "";
                mLogInfo.put("versionName", versionName);
                mLogInfo.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        // 反射机制
        Field[] mFields = Build.class.getDeclaredFields();
        // 迭代Build的字段key-value 此处的信息主要是为了在服务器端手机各种版本手机报错的原因
        for (Field field : mFields) {
            try {
                field.setAccessible(true);
                mLogInfo.put(field.getName(), field.get("").toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * saveCrashLogToFile:{将崩溃的Log保存到本地}<br>
     * 可拓展，将Log上传至指定服务器路径
     *
     * @param paramThrowable
     * @return FileName
     * @throws
     * @since I used to be a programmer like you, then I took an arrow in the
     * knee　Ver 1.0 2013-3-24下午12:31:01 Modified By Stone.J
     */
    private String saveCrashLogToFile(Throwable paramThrowable) {
        StringBuffer mStringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : mLogInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            mStringBuffer.append(key + "=" + value + "\r\n");
        }
        Writer mWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mWriter);
        paramThrowable.printStackTrace(mPrintWriter);
        paramThrowable.printStackTrace();
        Throwable mThrowable = paramThrowable.getCause();
        // 迭代栈队列把所有的异常信息写入writer中
        while (mThrowable != null) {
            mThrowable.printStackTrace(mPrintWriter);
            // 换行 每个个异常栈之间换行
            mPrintWriter.append("\r\n");
            mThrowable = mThrowable.getCause();
        }
        // 记得关闭
        mPrintWriter.close();
        String mResult = mWriter.toString();
        mStringBuffer.append(mResult);
        // 保存文件，设置文件名
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String mTime = mSimpleDateFormat.format(new Date());
        String mFileName = "CrashLog_" + mTime + ".log";
        try {
            File mDirectory = new File(AppContext.getAppCrashLogDir());
            if (!mDirectory.exists()) {
                mDirectory.mkdir();
            }
            String mFilePath = AppContext.getAppCrashLogDir() + File.separator + mFileName;
            if (FileUtils.isFileExist(mFilePath)) {
                FileUtils.writeFileSdcardFile(mFilePath, "\n\n\n" + mStringBuffer.toString());
            } else {
                FileOutputStream mFileOutputStream = new FileOutputStream(mFilePath);
                mFileOutputStream.write(mStringBuffer.toString().getBytes());
                mFileOutputStream.close();
            }
            return mFilePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
