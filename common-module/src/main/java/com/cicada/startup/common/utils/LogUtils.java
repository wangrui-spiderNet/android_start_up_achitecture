package com.cicada.startup.common.utils;

import android.util.Log;

import com.cicada.startup.common.AppContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类
 * <p>
 * 创建时间: 2014-8-19 上午11:58:53 <br/>
 *
 * @author xnjiang
 * @since v0.0.1
 */
public class LogUtils {

    private static final String PATH_SDCARD_DIR = AppContext.getAppCrashLogDir();
    private static final String MYLOGFILEName = "_Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
    private static final boolean DEBUG = true;

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
            writeLog2File(tag, message);
        }
    }

    public static void d(String tag, String message, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, message, tr);
            writeLog2File(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
            writeLog2File(tag, message);
        }
    }

    public static void i(String tag, String message, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, message, tr);
            writeLog2File(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG) {
            Log.w(tag, message);
            writeLog2File(tag, message);
        }
    }

    public static void w(String tag, String message, Throwable tr) {
        if (DEBUG) {
            Log.w(tag, message, tr);
            writeLog2File(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
            writeLog2File(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable tr) {
        if (DEBUG) {
            Log.d(tag, message, tr);
            writeLog2File(tag, message);
        }
    }

    /**
     * http log method
     */
    public static void http(String className, String message) {
        if (DEBUG) {
            Log.d("httpMessage", className + " : " + message);
            writeLog2File(className, message);
        }
    }

    private static void writeLog2File(String tag, String log) {
        Date nowTime = new Date();
        FileUtils.createDir(PATH_SDCARD_DIR);
        String needWriteFile = logfile.format(nowTime);
        String needWriteMessage = myLogSdf.format(nowTime)
                + "    " + tag + "    " + log;
        File file = new File(PATH_SDCARD_DIR, needWriteFile
                + MYLOGFILEName);
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
