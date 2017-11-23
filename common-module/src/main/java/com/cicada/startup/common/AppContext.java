package com.cicada.startup.common;

import android.content.Context;

import com.cicada.startup.common.config.AppEnvConfig;
import com.cicada.startup.common.manager.AppManager;
import com.cicada.startup.common.utils.FileUtils;

import java.io.File;

/**
 * AppContext
 * <p>
 * 创建时间: 16/5/3 下午2:51 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class AppContext {


    public static Context instance;
    public static AppEnvConfig appEnvConfig;

    public static void init(Context context, AppEnvConfig envConfig) {
        if (null == instance) {
            instance = context;
            appEnvConfig = envConfig;
            AppManager.getInstance().init();
        }
    }

    public static boolean isRelease() {
        if (AppEnvConfig.RELEASE.getIndex() == appEnvConfig.getIndex()
                || AppEnvConfig.RELEASE_PRE.getIndex() == appEnvConfig.getIndex()) {
            return true;
        }
        return false;
    }

    public static Context getContext() {
        return instance;
    }

    public static AppEnvConfig getAppEnv() {
        return appEnvConfig;
    }


    /**
     * 获得当前应用的根文件存放路径
     */
    public static String getAppRootDir() {
        return FileUtils.getAppRootPath(getContext());
    }

    /**
     * 获得头像文件路径，该头像路径为所有用户公用
     */
    public static String getCacheImageDir() {
        return FileUtils.getCacheImgPath(getContext());
    }

    /**
     * 获得应用错误log信息存储路径
     */
    public static String getAppCrashLogDir() {
        return FileUtils.getCacheLog(getContext());
    }

    /**
     * 获得当前用户的图片文件存放路径
     */
    public static String getAppSaveImageDir() {
        return FileUtils.getSaveImage(getContext());
    }

    public static String getCacheVideoPath() {
        return FileUtils.getCacheVideoPath(getContext());
    }

    public static String getCacheAudioPath() {
        return FileUtils.getCacheAudioPath(getContext());
    }


    public static String getDownLoadApkPath(String versionName) {
        return FileUtils.getDownloadPath(getContext()) + File.separator + "cicada_" + versionName + ".apk";
    }
}
