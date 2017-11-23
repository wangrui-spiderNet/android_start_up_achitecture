package com.cicada.startup.common.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * TODO 功能描述
 * <p/>
 * 创建时间: 15/10/20 下午4:10 <br/>
 *
 * @since v0.0.1
 */
public class SystemUtils {


    /**
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
