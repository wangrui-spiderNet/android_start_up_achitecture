package com.cicada.startup.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 纯的文件相关的操作
 * <p>
 * 创建时间: 2014-8-19 下午2:30:18 <br/>
 */
@SuppressLint("DefaultLocale")
public class FileUtils {

    public static final String BACKUP_EXT = "_backup";

    private static final String[] moreCachePath = {
            AppContext.getContext().getResources().getString(R.string.CachePath),
            AppContext.getContext().getResources().getString(R.string.CachePathFile),
            AppContext.getContext().getResources().getString(R.string.CachePathXml),
            AppContext.getContext().getResources().getString(R.string.CachePathFile),
            AppContext.getContext().getResources().getString(R.string.CachePathApk),
            AppContext.getContext().getResources().getString(R.string.CachePathXmlPics),
            AppContext.getContext().getResources().getString(R.string.CachePathLoadingPics),
            AppContext.getContext().getResources().getString(R.string.CachePathGroupPic),
            AppContext.getContext().getResources().getString(R.string.CachePathFilePics),
            AppContext.getContext().getResources().getString(R.string.CacheLogPath),
            AppContext.getContext().getResources().getString(R.string.CacheDownloadFile),
            AppContext.getContext().getResources().getString(R.string.CachePathVideo),
            AppContext.getContext().getResources().getString(R.string.CacheSaveImage),
            AppContext.getContext().getResources().getString(R.string.CachePathFileCache)
    };


    /**
     * 获得Root路径
     * 如果有sdcard，则路径为 /mnt/sdcard/;
     * 没有sdcard  isIndude 为true时，路径为/data/data/com.xxxx.xxxx/
     * isIndude 为false时，路径为null
     *
     * @param context
     * @param isIndude
     * @return
     */
    public static String getRootPath(Context context, boolean isIndude) {
        String rootPath = null;
        if (android.os.Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            if (isIndude) {
                rootPath = context.getFilesDir().getAbsolutePath();
            }
        }

        return rootPath;
    }

    public static String createFileDir(Context context, String basePath, String cache, String name) {
        StringBuffer sb = new StringBuffer().append(basePath).append(File.separator)
                .append(context.getString(R.string.CachePath));
        if (!TextUtils.isEmpty(cache)) {
            sb.append(cache);
        }
        if (name != null) {
            sb.append(File.separator).append(name);
        }
        return sb.toString();
    }

    /**
     * 获取应用路径地址
     *
     * @param context
     * @return
     */
    public static String getAppRootPath(Context context) {
        String basePath = getRootPath(context, true);
        if (basePath != null) {
            try {
                File newFile = new File(createFileDir(context, basePath, null, null));
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取文件缓存
     * @param context
     * @return
     */
    public static String getCacheFileCachePath(Context context){
        String basePath = getRootPath(context, true);
        String cachePath = context.getResources().getString(R.string.CachePathFileCache);
        if (basePath != null && cachePath != null) {
            try {
                File newFile = new File(createFileDir(context, basePath, cachePath, null));
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getCacheVideoPath(Context context) {
        String basePath = getRootPath(context, true);
        String cachePath = context.getResources().getString(R.string.CachePathVideo);
        if (basePath != null && cachePath != null) {
            try {
                File newFile = new File(createFileDir(context, basePath, cachePath, null));
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
public static String getCacheAudioPath(Context context) {
        String basePath = getRootPath(context, true);
        String cachePath = context.getResources().getString(R.string.CachePathAudio);
        if (basePath != null && cachePath != null) {
            try {
                File newFile = new File(createFileDir(context, basePath, cachePath, null));
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getDownloadPath(Context context) {
        String basePath = getRootPath(context, true);
        String cachePath = context.getResources().getString(R.string.CacheDownloadFile);
        if (basePath != null && cachePath != null) {
            try {
                File newFile = new File(createFileDir(context, basePath, cachePath, null));
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取图片缓存路径
     *
     * @param context
     * @return
     */
    public static String getCacheImgPath(Context context) {
        String basePath = getRootPath(context, true);
//        getSdInfo(context);
        String cachePath = context.getResources().getString(R.string.CachePathFilePics);
        if (basePath != null && cachePath != null) {
            try {
                File newFile = new File(createFileDir(context, basePath, cachePath, null));
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取日志文件
     * @param context
     * @return
     */
    public static String getCacheLog(Context context){
        String basePath = getRootPath(context, true);
        String cachePath = context.getResources().getString(R.string.CacheLogPath);
        if (basePath != null && cachePath != null) {
            try {
                File newFile = new File(createFileDir(context, basePath, cachePath, null));
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 用户保存的图片
     * @param context
     * @return
     */
    public static String getSaveImage(Context context){
        String basePath = getRootPath(context, true);
        String cachePath = context.getResources().getString(R.string.CacheSaveImage);
        if (basePath != null && cachePath != null) {
            try {
                File newFile = new File(createFileDir(context, basePath, cachePath, null));
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取缓存数据
     *
     * @return
     */
    public static String getCacheSize() {
        final String cachePath = createFileDir(AppContext.getContext().getApplicationContext(),
                getRootPath(AppContext.getContext().getApplicationContext(), true), null,
                null);
        File file = new File(cachePath);
        return getSize(getDirSize(file));
    }

    public static long getDirSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {
                long size = file.length();
                return size;
            }
        } else {
            return (long) 0.0;
        }
    }

    /**
     * get size
     *
     * @param size
     * @return
     */
    public static String getSize(long size) {
        DecimalFormat df = new DecimalFormat("0.0");
        StringBuffer sb = new StringBuffer();
        if (size > 1024 * 1024 * 1024) {
            sb.append(df.format(size / 1024f / 1024f / 1024f)).append("GB");
        } else if (size > 1024 * 1024) {
            sb.append(df.format(size / 1024f / 1024f)).append("MB");
        } else if (size > 1024) {
            sb.append(df.format(size / 1024f)).append("KB");
        } else {
            sb.append(size).append("B");
        }
        return sb.toString();
    }


    /**
     * 根据文件名找出文件路径。
     * @param context
     * @param targetName
     */
    public static String findFileByName(Context context,String targetName){
        if(targetName == null || targetName.length() == 0){
            return null;
        }
        String filePath = null;
        StringBuffer sb = new StringBuffer().append(getRootPath(context, true)).append(File.separator)
                .append(context.getString(R.string.CachePath));

        List<String> listPath = new ArrayList<>();
        seachFile(context, sb.toString(), targetName,listPath);
        if(listPath.size()>0){
            filePath = listPath.get(0);
        }
        LogUtils.d("hwp","pics path=="+filePath);
        return filePath;
    }
    /*
     * 递归查找
     */
    private static String seachFile(Context context,String filepath,String targetName,List<String> paths){
        String filePath = null;
        File[] files = new File(filepath).listFiles();
        if(files!=null && files.length>0){
            for(int i=0;i<files.length;i++){
                if(files[i].isFile()){
                    if(targetName.equals(files[i].getName())){
                        paths.add(files[i].getAbsolutePath());
                        return filePath = files[i].getAbsolutePath();
                    }
                }else if(files[i].isDirectory()){
                    if(targetName.equals(files[i].getName())){
                        paths.add(files[i].getAbsolutePath());
                        return filePath = files[i].getAbsolutePath();
                    }
                    seachFile(context, files[i].getAbsolutePath(), targetName,paths);
                }
            }
        }
        return filePath;
    }
    /**
     * 快速删除根据文件名得到的第一个文件(包含文件路径)
     * @param context
     * @param targetName
     */
    public static boolean quickDelAppCacheByName(Context context,String targetName){
        synchronized (FileUtils.class){
            try {
                final String cachePath = createFileDir(context, getRootPath(context, true), null,
                        moreCachePath[0]);
                String path = findFileByName(context, targetName);
                if(path!=null){
                    boolean renameToFileCache = false;
                    File cache = new File(path);
                    if(cache!=null && cache.exists()){
                        File c = new File(cachePath + BACKUP_EXT);
                        renameToFileCache = cache.renameTo(c);
                        if(!renameToFileCache){
                            if (c.exists()) {
                                Process process = Runtime.getRuntime().exec("rm -r " + c.getPath());
                                process.waitFor();
                            }
                        }
                    }
                    if(cache.exists()){
                        quickDelAppCacheByName(context,targetName);
                        return true;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void recursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                recursionDeleteFile(f);
            }
            file.delete();
        }
    }


    /**
     * 快速删除cache
     *
     * @param mContext
     * @return
     */
    public static boolean quickDelCache(Context mContext) {
        final String cachePath = createFileDir(mContext, getRootPath(mContext, true), null,
                null);
        for (int i = 0; i < moreCachePath.length; i++) {
            StringBuffer strbuff = new StringBuffer();
            strbuff.append(cachePath).append(moreCachePath[i]);
            File cacheFile = new File(strbuff.toString());
            if (cacheFile.exists()) {
                boolean renameToFileCache = cacheFile.renameTo(new File(strbuff.toString() + BACKUP_EXT));
                if (renameToFileCache) {
                    final File file = new File(strbuff.toString());
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
            }
        }
        rmBackMoreCacheFile(mContext);
        return true;
    }

    private static void rmBackMoreCacheFile(final Context mContext) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String cachePath = createFileDir(mContext, getRootPath(mContext, false),
                            null, null);
                    for (int i = 0; i < moreCachePath.length; i++) {
                        StringBuffer strbuff = new StringBuffer();
                        strbuff.append(cachePath).append(moreCachePath[i]).append(BACKUP_EXT);
                        File cacheFile = new File(strbuff.toString());
                        if (cacheFile.exists()) {
                            Process process = Runtime.getRuntime().exec("rm -r " + cacheFile.getPath());
                            process.waitFor();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static void getSdInfo(Context context){
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(sm, params);
            for (int i = 0; i < ((String[]) invoke).length; i++) {
                StatFs stat = getStatFs(((String[]) invoke)[i]);
                LogUtils.d("hwp",((String[])invoke)[i] + "剩余空间:" + calculateSizeInMB(stat));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StatFs getStatFs(String path) {
        try {
            return new StatFs(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static float calculateSizeInMB(StatFs stat) {
        if (stat != null)
            return stat.getAvailableBlocks() * (stat.getBlockSize() / (1024f * 1024f));
        return 0.0f;
    }
    //****************************old fileutils *******************************


    private static final String TAG = FileUtils.class.getSimpleName();
    public final static String FILENAME = "fileName";
    public final static String FILEPATH = "filePath";
    public final static String FILEDATE = "fileDate";
    public final static String FILESIZE = "fileSize";
    public final static String FILETYPE = "fileType";


    /**
     * (所有文件扫瞄)扫描完成后的回调，获取文件列表必须实现
     */
    public interface OnFileListAllCallback {
        /**
         * 返回查询的文件列表(所有文件信息，不含目录信息)
         *
         * @param list 文件列表 ，文件信息包含（fileName，filePath）
         */
        public void SearchFileListAll(List<Map<String, Object>> list);
    }

    /**
     * (当前目录层级扫瞄)扫描完成后的回调，获取文件列表必须实现
     */
    public interface OnFileListCurrentLevelCallback {
        /**
         * 返回当前文件路径下的文件和目录
         *
         * @param list 文件列表 ，文件信息包含（fileName，filePath）
         */
        public void SearchFileListCurrentLevel(List<Map<String, Object>> list);
    }

    /**
     * 获取指定目录下的所有文件列表(扫瞄所有层级，不含目录信息)
     *
     * @param path               文件夹路径
     * @param isShow             show 只显示特定文件 hide 过滤掉特定文件
     * @param strKeyword         文件名关键字过滤
     * @param type               文件类型（".jpg"）多个类型中间用&隔开（".jpg&.png&.gif"）
     * @param onFileListCallback 回调函数必须实现，否则得不到搜索结果
     */
    public static void getFileListAll(String path, String isShow, String strKeyword, String type, final OnFileListAllCallback onFileListCallback) {

        new AsyncTask<String, String, String>() {
            ArrayList<Map<String, Object>> listAll = new ArrayList<Map<String, Object>>();

            @Override
            protected void onPostExecute(String result) {
                sortArrayList(listAll, "", true);
                onFileListCallback.SearchFileListAll(listAll);
            }

            @Override
            protected String doInBackground(String... params) {
                File file = new File(params[0]);
                if (isFileExist(file)) {
                    String showState = params[1];
                    String strKeyword = params[2];
                    String[] extensions = params[3].split("&");
                    scanSDCardAll(file, listAll, showState, strKeyword, extensions);
                }
                return null;
            }

        }.execute(path, isShow, strKeyword, type);
    }

    /**
     * 获取指定目录下的所有文件列表(扫瞄所有层级，不含目录信息)
     *
     * @param file       根目录文件
     * @param listAll    存放文件信息的数组
     * @param isShow     show 只显示特定文件 hide 过滤掉特定文件
     * @param strKeyword 文件名关键字过滤
     * @param extensions 文件类型（".jpg"）多个类型中间用&隔开（".jpg&.png&.gif"）
     */
    private static void scanSDCardAll(File file, List<Map<String, Object>> listAll, String isShow, String strKeyword, String... extensions) {
        Map<String, Object> mapFile;
        if (file.isDirectory()) {
            ExtensionFileFilter filter = new ExtensionFileFilter(isShow, strKeyword, extensions);
            File[] files = file.listFiles(filter);
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File childFile = files[i];
                    if (childFile.isFile()) {
                        mapFile = new HashMap<String, Object>();
                        String filePath = childFile.getPath();
                        String fileName = childFile.getName();
                        long fileDate = childFile.lastModified();
                        long fileSize = getFileSize(childFile, false);
                        String fileType = getFileExtension(childFile);
                        mapFile.put(FILENAME, fileName);
                        mapFile.put(FILEPATH, filePath);
                        mapFile.put(FILEDATE, fileDate);
                        mapFile.put(FILESIZE, fileSize);
                        mapFile.put(FILETYPE, fileType);
                        listAll.add(mapFile);
                    } else {
                        scanSDCardAll(childFile, listAll, isShow, strKeyword, extensions);
                    }
                }
            }
        } else {
            if (file.isFile()) {
                mapFile = new HashMap<String, Object>();
                String fileName = file.getName();
                String filePath = file.getPath();
                long fileDate = file.lastModified();
                long fileSize = getFileSize(file, false);
                String fileType = getFileExtension(file);
                mapFile.put(FILENAME, fileName);
                mapFile.put(FILEPATH, filePath);
                mapFile.put(FILEDATE, fileDate);
                mapFile.put(FILESIZE, fileSize);
                mapFile.put(FILETYPE, fileType);
                listAll.add(mapFile);
            }
        }
    }

    /**
     * 获取指定目录下的文件列表(当前层级的文件、目录)
     *
     * @param path               文件夹路径
     * @param isShow             show 只显示特定文件 hide 过滤掉特定文件
     * @param strKeyword         文件名关键字过滤
     * @param type               文件类型（".jpg"）多个类型中间用&隔开（".jpg&.png&.gif"）
     * @param onFileListCallback 回调函数必须实现，否则得不到搜索结果
     */
    public static void getFileListCurrentLevel(String path, String isShow, String strKeyword, String type, final OnFileListCurrentLevelCallback onFileListCallback) {

        new AsyncTask<String, String, String>() {
            ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            @Override
            protected void onPostExecute(String result) {
                onFileListCallback.SearchFileListCurrentLevel(list);
            }

            @Override
            protected String doInBackground(String... params) {
                File file = new File(params[0]);
                if (isFileExist(file)) {
                    String showState = params[1];
                    String strKeyword = params[2];
                    String[] extensions = params[3].split("&");
                    scanSDCardCurrentLevel(file, list, showState, strKeyword, extensions);
                }
                return null;
            }

        }.execute(path, isShow, strKeyword, type);
    }

    /**
     * 获取指定目录下的文件列表(当前层级的文件、目录)
     *
     * @param file       根目录文件
     * @param list       存放文件信息的数组
     * @param isShow     show 只显示特定文件 hide 过滤掉特定文件
     * @param strKeyword 文件名关键字过滤
     * @param extensions 文件类型（".jpg"）多个类型中间用&隔开（".jpg&.png&.gif"）
     */
    private static void scanSDCardCurrentLevel(File file, List<Map<String, Object>> list, String isShow, String strKeyword, String... extensions) {
        Map<String, Object> mapFile;
        if (file.isDirectory()) {
            ExtensionFileFilter filter = new ExtensionFileFilter(isShow, strKeyword, extensions);
            File[] files = file.listFiles(filter);
            if (files != null) {
                List<Map<String, Object>> listDir = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> listFile = new ArrayList<Map<String, Object>>();

                for (File childFile : files) {
                    if (childFile.isDirectory()) {
                        mapFile = new HashMap<String, Object>();
                        String filePath = childFile.getPath();
                        String fileName = childFile.getName();
                        long fileDate = childFile.lastModified();
                        long fileSize = getFileSize(childFile, false);
                        String fileType = getFileExtension(childFile);
                        mapFile.put(FILENAME, fileName);
                        mapFile.put(FILEPATH, filePath);
                        mapFile.put(FILEDATE, fileDate);
                        mapFile.put(FILESIZE, fileSize);
                        mapFile.put(FILETYPE, fileType);
                        listDir.add(mapFile);
                    } else {
                        mapFile = new HashMap<String, Object>();
                        String filePath = childFile.getPath();
                        String fileName = childFile.getName();
                        long fileDate = childFile.lastModified();
                        long fileSize = getFileSize(childFile, false);
                        String fileType = getFileExtension(childFile);
                        mapFile.put(FILENAME, fileName);
                        mapFile.put(FILEPATH, filePath);
                        mapFile.put(FILEDATE, fileDate);
                        mapFile.put(FILESIZE, fileSize);
                        mapFile.put(FILETYPE, fileType);
                        listFile.add(mapFile);
                    }
                }
                sortArrayList(listDir, FILENAME, true);
                sortArrayList(listFile, FILENAME, true);
                list = combineArrayList(listDir, listFile);
            }
        }
    }

    /**
     * 文件过滤器
     *
     * @author Stone.J
     */
    public static class ExtensionFileFilter implements FileFilter {
        private String isShow;
        private String strKeyword;
        private String[] extensions;

        /**
         * @param isShow     show添加 hide排除
         * @param extensions 过滤的后缀名如：.png、.jpg
         */
        public ExtensionFileFilter(String isShow, String strKeyword, String... extensions) {
            this.isShow = isShow;
            strKeyword = strKeyword;
            this.extensions = extensions;
        }

        @Override
        public boolean accept(File pathname) {
            String strFileName = pathname.getName();
            if (pathname.isDirectory()) {
                return true;
            } else {
                if (extensions == null || extensions.length == 0) {
                    return true;
                } else {
                    if (isShow.equalsIgnoreCase("show")) {
                        for (String str : extensions) {
                            if (strFileName.toLowerCase(Locale.getDefault()).endsWith(str.toLowerCase(Locale.getDefault()))) {
                                if (!TextUtils.isEmpty(strKeyword)) {
                                    if (strFileName.toLowerCase(Locale.getDefault()).contains(strKeyword.toLowerCase(Locale.getDefault()).trim())) {
                                        return true;
                                    }
                                } else {
                                    return true;
                                }
                            }
                        }
                        return false;
                    } else {
                        for (String str : extensions) {
                            if (strFileName.toLowerCase(Locale.getDefault()).endsWith(str.toLowerCase(Locale.getDefault()))) {
                                if (!TextUtils.isEmpty(strKeyword)) {
                                    if (strFileName.toLowerCase(Locale.getDefault()).contains(strKeyword.toLowerCase(Locale.getDefault()).trim())) {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
    }

    public static class FileComparator implements Comparator<Object> {
        private String sortKey;
        private boolean sortOrder;

        /**
         * 排序比较器
         *
         * @param sortKey   排序字段
         * @param sortOrder 排序方式 true 升序 false 降序
         */
        public FileComparator(String sortKey, boolean sortOrder) {
            this.sortKey = sortKey;
            this.sortOrder = sortOrder;
        }

        @Override
        @SuppressWarnings("unchecked")
        public int compare(Object o1, Object o2) {
            if (TextUtils.isEmpty(sortKey)) {
                return -1;
            }
            if (o1 instanceof Map) {
                Map<String, Object> map1 = (Map<String, Object>) o1;
                Map<String, Object> map2 = (Map<String, Object>) o2;
                if (!map1.containsKey(sortKey) || !map2.containsKey(sortKey)) {
                    return -1;
                }
                if (sortOrder) {
                    return map1.get(sortKey).toString().compareToIgnoreCase(map2.get(sortKey).toString());
                } else {
                    return map2.get(sortKey).toString().compareToIgnoreCase(map1.get(sortKey).toString());
                }
            } else if (o1 instanceof String) {
                String str1 = (String) o1;
                String str2 = (String) o2;
                if (sortOrder) {
                    return str1.compareToIgnoreCase(str2);
                } else {
                    return str2.compareToIgnoreCase(str1);
                }
            } else if (o1 instanceof File) {
                File file1 = (File) o1;
                File file2 = (File) o2;
                String filename1 = file1.getName();
                String filename2 = file2.getName();
                if (sortOrder) {
                    return filename1.compareToIgnoreCase(filename2);
                } else {
                    return filename2.compareToIgnoreCase(filename1);
                }
            }
            return -1;
        }

    }

    /**
     * 数组排序功能
     *
     * @param list    排序数据
     * @param sortKey 排序字段
     * @param order   排序方式 true 升序 false 降序
     */
    public static void sortArrayList(List<?> list, final String sortKey, final boolean order) {
        Collections.sort(list, new Comparator<Object>() {
            @Override
            @SuppressWarnings("unchecked")
            public int compare(Object o1, Object o2) {
                if (TextUtils.isEmpty(sortKey)) {
                    return -1;
                }
                if (o1 instanceof Map) {
                    Map<String, Object> map1 = (Map<String, Object>) o1;
                    Map<String, Object> map2 = (Map<String, Object>) o2;
                    if (!map1.containsKey(sortKey) || !map2.containsKey(sortKey)) {
                        return -1;
                    }
                    if (order) {
                        return map1.get(sortKey).toString().compareToIgnoreCase(map2.get(sortKey).toString());
                    } else {
                        return map2.get(sortKey).toString().compareToIgnoreCase(map1.get(sortKey).toString());
                    }
                } else if (o1 instanceof String) {
                    String str1 = (String) o1;
                    String str2 = (String) o2;
                    if (order) {
                        return str1.compareToIgnoreCase(str2);
                    } else {
                        return str2.compareToIgnoreCase(str1);
                    }
                } else if (o1 instanceof File) {
                    File file1 = (File) o1;
                    File file2 = (File) o2;
                    String filename1 = file1.getName();
                    String filename2 = file2.getName();
                    if (order) {
                        return filename1.compareToIgnoreCase(filename2);
                    } else {
                        return filename2.compareToIgnoreCase(filename1);
                    }
                }
                return -1;
            }
        });
    }

    /**
     * 合并两个数组对象成为一个数组对象
     *
     * @param <T>
     * @param a1
     * @param a2
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> combineArrayList(List<T> a1, List<T> a2) {
        for (Object obj : a2) {
            a1.add((T) obj);
        }
        return a1;
    }

    /**
     * 调用系统功能重新扫描指定的文件夹,写入系统媒体数据库
     *
     * @param context
     * @param strFileName
     * @since v0.0.1
     */
    public static void scanMediaFileDataBase(Context context, String strFileName) {
        // 通常在我们的项目中，可能会遇到写本地文件，最常用的就是图片文件，在这之后需要通知系统重新扫描SD卡，
        // 在Android4.4之前也就是以发送一个Action为“Intent.ACTION_MEDIA_MOUNTED”的广播通知执行扫描。如下：

        // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
        // Uri.parse("file://" + strRefreshDir)));

        // 但在Android4.4中，则会抛出以下异常：
        // W/ActivityManager( 498): Permission Denial: not allowed to send
        // broadcast android.intent.action.MEDIA_MOUNTED from pid=2269,
        // uid=20016
        // 那是因为Android4.4中限制了系统应用才有权限使用广播通知系统扫描SD卡。
        // 解决方式：
        // 使用MediaScannerConnection执行具体文件或文件夹进行扫描。
        // MediaScannerConnection.scanFile(context, new
        // String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()
        // + "/" + strFileName}, null, null);

        // 判断目录如果是文件，就获取其上一级路径也进行刷新
        String strFileParent = new File(strFileName).isFile() ? new File(strFileName).getParentFile().getPath() : strFileName;
        MediaScannerConnection.scanFile(context, new String[]{strFileName, strFileParent}, null, null);
    }

    /**
     * 检测SD卡是否存在
     */
    public static boolean isSDExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 多个SD卡存在时 获取外置SD卡路径<br>
     *
     * @return
     */
    public static String getExternalStorageDirectory() {
        Map<String, String> map = System.getenv();
        String[] values = new String[map.values().size()];
        map.values().toArray(values);
        String path = values[values.length - 1]; // 外置SD卡的路径
        if (path.startsWith("/mnt/") && !Environment.getExternalStorageDirectory().getAbsolutePath().equals(path)) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * 获取SD卡根目录路径
     *
     * @return e.g. /storage/sdcard0/
     */
    public static String getSDCardFilesPath() {
        if (!isSDExist()) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getPath() + "/";
    }

    /**
     * 获取SD卡根目下的下载目录路径
     *
     * @return e.g. /storage/sdcard0/Download/
     */
    public static String getSDCardDownloadPath() {
        if (!isSDExist()) {
            return null;
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
    }

    /**
     * 获取SD卡根目下的缓存目录路径
     *
     * @return e.g. /storage/sdcard0/Download/cache/
     */
    public static String getSDCardCachePath() {
        if (!isSDExist()) {
            return null;
        }
        return Environment.getDownloadCacheDirectory().getPath() + "/";
    }

    /**
     * 私有文件目录路径
     *
     * @param ctx
     * @return /data/data/{package name}/files/
     */
    public static String getSystemFilesPath(Context ctx) {
        return ctx.getFilesDir().getPath() + "/";
    }

    /**
     * 系统文件中的缓存目录路径
     *
     * @param ctx
     * @return /data/data/{package name}/cache/
     */
    public static String getSystemCachePath(Context ctx) {
        return ctx.getCacheDir().getPath() + "/";
    }

    /**
     * 删除一个文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file.isDirectory())
            return false;
        return file.delete();
    }

    /**
     * 删除一个文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }

    /**
     * 删除一个目录（可以是非空目录）
     *
     * @param dir
     */
    public static boolean deleteDir(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteDir(file);// 递归
            }
        }
        dir.delete();
        return true;
    }

    /**
     * 删除一个目录（可以是非空目录）
     *
     * @param path
     */
    public static boolean deleteDir(String path) {
        return deleteDir(new File(path));
    }

    /**
     * 拷贝一个文件,srcFile源文件，destFile目标文件
     *
     * @param srcFile  文件、目录
     * @param destFile 文件、目录
     * @return
     */
    public static boolean copyFileTo(File srcFile, File destFile) {
        if (!srcFile.exists())
            return false;// 判断是否存在
        File inputFile = srcFile;
        File outputFile = destFile;
        if (srcFile.isDirectory()) {
            if (!destFile.isDirectory()) {
                return false;
            }
            String newPath = destFile.getPath() + "/" + srcFile.getName();
            createDir(newPath);
            copyFilesTo(srcFile.getPath(), newPath);
        }
        if (destFile.isDirectory() && destFile.exists()) {
            outputFile = new File(destFile.getPath() + "/" + srcFile.getName());
        } else if (destFile.getParent() == null) {
            return false;
        }
        try {
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
            int readLen = 0;
            byte[] buf = new byte[4096];
            while ((readLen = fis.read(buf)) != -1) {
                fos.write(buf, 0, readLen);
            }
            fos.flush();
            fos.close();
            fis.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 拷贝一个文件,srcFile源文件，destFile目标文件
     *
     * @param srcPath  文件、目录的路径
     * @param destPath 文件、目录的路径
     * @return
     */
    public static boolean copyFileTo(String srcPath, String destPath) {
        return copyFileTo(new File(srcPath), new File(destPath));
    }

    /**
     * 拷贝目录下的所有文件到指定目录
     *
     * @param srcDir  完整目录
     * @param destDir 完整目录
     * @return
     */
    public static boolean copyFilesTo(File srcDir, File destDir) {
        if (!srcDir.exists() || !destDir.exists())
            return false;// 判断是否存在
        if (!srcDir.isDirectory() || !destDir.isDirectory())
            return false;// 判断是否是目录
        try {
            File[] srcFiles = srcDir.listFiles();
            for (int i = 0; i < srcFiles.length; i++) {
                if (srcFiles[i].isFile()) {
                    // 获得目标文件
                    File destFile = new File(destDir.getPath() + "//" + srcFiles[i].getName());
                    copyFileTo(srcFiles[i], destFile);
                } else if (srcFiles[i].isDirectory()) {
                    File theDestDir = new File(destDir.getPath() + "//" + srcFiles[i].getName());
                    copyFilesTo(srcFiles[i], theDestDir);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 拷贝目录下的所有文件到指定目录
     *
     * @param srcPath  完整目录的路径
     * @param destPath 完整目录的路径
     * @return
     */
    public static boolean copyFilesTo(String srcPath, String destPath) {
        return copyFilesTo(new File(srcPath), new File(destPath));
    }

    /**
     * 移动一个文件、目录到一个新的文件夹
     *
     * @param srcFile  文件、目录
     * @param destFile 完整目录
     */
    public static boolean moveFileTo(File srcFile, File destFile) {
        // File (or directory) to be moved
        if (!srcFile.exists()) {
            return false;
        }
        // Destination directory
        if (!destFile.exists() || !destFile.isDirectory()) {
            return false;
        }
        // Move file to new directory
        boolean success = srcFile.renameTo(new File(destFile, srcFile.getName()));

        return success;
    }

    /**
     * 移动一个文件、目录到一个新的文件夹
     *
     * @param srcPath  文件、目录的路径
     * @param destPath 完整目录的路径
     */
    public static boolean moveFileTo(String srcPath, String destPath) {
        return moveFileTo(new File(srcPath), new File(destPath));
    }

    /**
     * 移动目录下的所有文件到指定目录
     *
     * @param srcDir  完整目录
     * @param destDir 完整目录
     * @return
     */
    public static boolean moveFilesTo(File srcDir, File destDir) {
        if (!srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }
        File[] srcDirFiles = srcDir.listFiles();
        for (int i = 0; i < srcDirFiles.length; i++) {
            moveFileTo(srcDirFiles[i], destDir);
        }
        return true;
    }

    /**
     * 移动目录下的所有文件到指定目录
     *
     * @param srcPath  完整目录的路径
     * @param destPath 完整目录的路径
     * @return
     */
    public static boolean moveFilesTo(String srcPath, String destPath) {
        return moveFilesTo(new File(srcPath), new File(destPath));
    }

    /**
     * 给文件或目录重命名
     *
     * @param targetFile 文件、目录
     * @param newName    新的文件名字（单个文件要记得加上后缀.xxx）
     * @return
     */
    public static boolean renameFile(File targetFile, String newName) {
        if (!targetFile.exists() || targetFile.getParentFile() == null) {
            return false;
        }
        boolean success = targetFile.renameTo(new File(targetFile.getParentFile(), newName));
        return success;
    }

    /**
     * 给文件或目录重命名
     *
     * @param targetPath 文件、目录的路径
     * @param newName    新的文件名字（单个文件要记得加上后缀.xxx）
     * @return
     */
    public static boolean renameFile(String targetPath, String newName) {
        return renameFile(new File(targetPath), newName);
    }

    /**
     * 将二进制流写入文件
     *
     * @param path
     * @param is
     * @return
     */
    public static boolean writeFileFromStream(String path, InputStream is) {
        boolean result = false;
        FileOutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(path);
            os = new FileOutputStream(file, false);
            bos = new BufferedOutputStream(os);
            int readLen = 0;
            byte[] buf = new byte[4096];
            while ((readLen = is.read(buf)) != -1) {
                bos.write(buf, 0, readLen);
            }
            bos.flush();
            bos.close();
            os.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 将字符串写入文件
     *
     * @param path
     * @param data
     * @return
     */
    public static boolean writeFileFromString(String path, String data) {
        boolean result = false;
        FileWriter fw = null;
        try {
            File file = new File(path);
            fw = new FileWriter(file);
            fw.write(data);
            fw.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 创建目录文件夹
     */
    public static void createDir(String path) {
        try {
            if (!TextUtils.isEmpty(path)) {
                File newDir = new File(path);
                // if this directory does not exists, make one.
                if (!newDir.exists()) {
                    if (!newDir.mkdirs()) {
                        Log.e("--CopyAssets--", "cannot create directory.");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 根据给定的文件的完整路径，判断 并创建文件夹 及文件
     *
     * @param filePath
     * @return
     * @since v0.0.1
     */
    public static boolean createDirAndFile(String filePath) {
        boolean isSuccess = false;
        File file = new File(filePath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 检测文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
        try {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                return file.exists();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * 检测文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        try {
            return file.exists();
        } catch (Exception ex) {
        }

        return false;
    }

    /**
     * 比较两个文件是否相同
     *
     * @param file1
     * @param file2
     * @return true 相同,false 不同
     */
    public static boolean isCompareFiles(File file1, File file2) {
        if (file1.getPath().equalsIgnoreCase(file2.getPath())) {
            return true;
        }
        return false;
    }

    /**
     * 比较两个文件是否相同
     *
     * @param path1
     * @param path2
     * @return true 相同,false 不同
     */
    public static boolean isCompareFiles(String path1, String path2) {
        if (path1.equalsIgnoreCase(path2)) {
            return true;
        } else {
            return isCompareFiles(new File(path1), new File(path2));
        }
    }

    /**
     * 建立私有文件
     *
     * @param ctx
     * @param fileName 私有文件夹下的路径如：database\aa.db3
     * @return
     * @throws IOException
     */
    public static File creatSystemDataFile(Context ctx, String fileName) {
        try {
            File file = new File(getSystemFilesPath(ctx) + fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 建立私有目录
     *
     * @param ctx
     * @param dirName 私有文件夹下的路径如：database\aa.db3
     * @return
     */
    public static File creatSystemDataDir(Context ctx, String dirName) {
        File dir = new File(getSystemFilesPath(ctx) + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 删除私有文件
     *
     * @param ctx
     * @param fileName
     * @return
     */
    public static boolean deleteSystemDataFile(Context ctx, String fileName) {
        File file = new File(getSystemFilesPath(ctx) + fileName);
        return deleteFile(file);
    }

    /**
     * 删除私有目录
     *
     * @param ctx
     * @param dirName
     * @return
     */
    public static boolean deleteSystemDataDir(Context ctx, String dirName) {
        File file = new File(getSystemFilesPath(ctx) + dirName);
        return deleteDir(file);
    }

    /**
     * 更改私有文件名
     *
     * @param ctx
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean renameSystemDataFile(Context ctx, String oldName, String newName) {
        File oldFile = new File(getSystemFilesPath(ctx) + oldName);
        File newFile = new File(getSystemFilesPath(ctx) + newName);
        return oldFile.renameTo(newFile);
    }

    /**
     * 在私有目录下进行文件复制
     *
     * @param ctx
     * @param srcFileName
     * @param destFileName
     * @return
     * @throws IOException
     */
    public static boolean copySystemDataFileTo(Context ctx, String srcFileName, String destFileName) {
        try {
            File srcFile = new File(getSystemFilesPath(ctx) + srcFileName);
            File destFile = new File(getSystemFilesPath(ctx) + destFileName);
            return copyFileTo(srcFile, destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 复制私有目录里指定目录的所有文件
     *
     * @param ctx
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */
    public static boolean copySystemDataFilesTo(Context ctx, String srcDirName, String destDirName) {
        try {
            File srcDir = new File(getSystemFilesPath(ctx) + srcDirName);
            File destDir = new File(getSystemFilesPath(ctx) + destDirName);
            return copyFilesTo(srcDir, destDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 移动私有目录下的单个文件
     *
     * @param ctx
     * @param srcFileName
     * @param destFileName
     * @return
     * @throws IOException
     */
    public static boolean moveSystemDataFileTo(Context ctx, String srcFileName, String destFileName) {
        try {
            File srcFile = new File(getSystemFilesPath(ctx) + srcFileName);
            File destFile = new File(getSystemFilesPath(ctx) + destFileName);
            return moveFileTo(srcFile, destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 移动私有目录下的指定目录下的所有文件
     *
     * @param ctx
     * @param srcDirName
     * @param destDirName
     * @return
     * @throws IOException
     */
    public static boolean moveSystemDataFilesTo(Context ctx, String srcDirName, String destDirName) {
        try {
            File srcDir = new File(getSystemFilesPath(ctx) + srcDirName);
            File destDir = new File(getSystemFilesPath(ctx) + destDirName);
            return moveFilesTo(srcDir, destDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将文件写入应用私有的files目录。如:writeFile("test.txt");
     *
     * @param ctx
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean writeSystemFile(Context ctx, String fileName, String content) {
        try {
            OutputStream os = ctx.openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE);
            os.write(content.getBytes());
            os.flush();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 在原有文件上继续写文件。如:appendFile("test.txt");
     *
     * @param ctx
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean appendSystemFile(Context ctx, String fileName, String content) {
        try {
            OutputStream os = ctx.openFileOutput(fileName, Context.MODE_APPEND);
            os.write(content.getBytes());
            os.flush();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从应用的私有目录files读取文件。如:readFile("test.txt");
     *
     * @param ctx
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readSystemFile(Context ctx, String fileName) {
        try {
            InputStream is = ctx.openFileInput(fileName);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (is.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            is.close();
            arrayOutputStream.close();
            return new String(arrayOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 写数据到SD中的文件
     *
     * @param filePath
     * @param fileContent
     */
    public static void writeFileSdcardFile(String filePath, String fileContent) {
        try {

            FileOutputStream fout = new FileOutputStream(filePath);
            byte[] bytes = fileContent.getBytes();

            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的读写权限
     *
     * @param path
     * @return (-1)-不能访问,0-只读,1-读写
     */
    public static int getFilePermission(String path) {
        return getFilePermission(new File(path));
    }

    /**
     * 获取文件的读写权限
     *
     * @param f
     * @return (-1)-不能访问,0-只读,1-读写
     */
    public static int getFilePermission(File f) {
        int intPermission = 0;
        if (!f.canRead() && !f.canWrite()) {
            intPermission = -1;
        }
        if (f.canRead()) {
            if (f.canWrite()) {
                intPermission = 1;
            } else {
                intPermission = 0;
            }
        }
        return intPermission;
    }

    /***
     * 获取文件个数
     ***/
    public static int getFileCount(String path) {// 递归求取目录文件个数
        return getFileCount(new File(path));
    }

    /***
     * 获取文件个数
     ***/
    public static int getFileCount(File f) {// 递归求取目录文件个数
        int size = 0;
        if (f.isDirectory()) {
            File flist[] = f.listFiles();
            size = flist.length;
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileCount(flist[i]);
                    size--;
                }
            }
        } else {
            size = 1;
        }
        return size;
    }

    /**
     * 获取文件大小 (单位：kb)
     *
     * @param path
     * @param boolFolderCount 是否统计文件夹大小（文件夹统计比较耗时）
     * @return 文件默认返回 0
     */
    public static long getFileSize(String path, boolean boolFolderCount) {
        return getFileSize(new File(path), boolFolderCount);
    }

    /**
     * 获取文件大小 (单位：kb)
     *
     * @param f
     * @param boolFolderCount 是否统计文件夹大小 （文件夹统计比较耗时）
     * @return 文件默认返回 0
     */
    public static long getFileSize(File f, boolean boolFolderCount) {
        long size = 0;
        try {
            if (f.isFile()) {// 文件处理
                if (f.exists()) {
                    size = f.length();
                }
            } else {// 文件夹处理
                if (boolFolderCount) {
                    File flist[] = f.listFiles();
                    for (int i = 0; i < flist.length; i++) {
                        if (flist[i].isDirectory()) {
                            size = size + getFileSize(flist[i], boolFolderCount);
                        } else {
                            size = size + flist[i].length();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size / 1024;
    }

    /***
     * 转换文件大小单位(B/KB/MB/GB)
     ***/
    public static String formatFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < (1024 * 1024)) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < (1024 * 1024 * 1024)) {
            fileSizeString = df.format((double) fileS / (1024 * 1024)) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / (1024 * 1024 * 1024)) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 获取Phone容量信息(单位：Bytes)
     *
     * @return
     */
    public static String getPhoneCapacity() {
        // 获取本机信息
        File data = Environment.getDataDirectory();
        StatFs statFs = new StatFs(data.getPath());
        int availableBlocks = statFs.getAvailableBlocks();// 可用存储块的数量
        int blockCount = statFs.getBlockCount();// 总存储块的数量

        int size = statFs.getBlockSize();// 每块存储块的大小

        int totalSize = blockCount * size;// 总存储量

        int availableSize = availableBlocks * size;// 可用容量

        String phoneCapacity = formatFileSize(availableSize) + "/" + formatFileSize(totalSize);

        return phoneCapacity;
    }

    /**
     * 获取SDCard容量信息(单位：Bytes)
     *
     * @return
     */
    public static String getSDCardCapacity() {
        // 获取sdcard信息
        File sdData = Environment.getExternalStorageDirectory();
        StatFs sdStatFs = new StatFs(sdData.getPath());

        int sdAvailableBlocks = sdStatFs.getAvailableBlocks();// 可用存储块的数量
        int sdBlockcount = sdStatFs.getBlockCount();// 总存储块的数量
        int sdSize = sdStatFs.getBlockSize();// 每块存储块的大小
        int sdTotalSize = sdBlockcount * sdSize;
        int sdAvailableSize = sdAvailableBlocks * sdSize;

        String sdcardCapacity = formatFileSize(sdAvailableSize) + "/" + formatFileSize(sdTotalSize);
        return sdcardCapacity;
    }

    /**
     * 读取Assets目录下的文件内容到List<String>
     *
     * @param context
     * @return
     */
    public static List<String> readAssetsFile2List(Context context, String assetsFileName) {
        try {
            List<String> list = new ArrayList<String>();
            InputStream in = context.getResources().getAssets().open(assetsFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * InputStreamReader先指定以UTF8读取文件，再进行读取读取操作：
     *
     * @param context
     * @param assetName
     * @return
     * @since v0.0.1
     */
    public static String readAssetsFile2String3(Context context, String assetName) {
        StringBuilder sb = new StringBuilder("");
        String content = "";
        try {
            InputStream is = context.getAssets().open(assetName);
            if (is != null) {
                BufferedReader d = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while (d.ready()) {
                    sb.append(d.readLine() + "\n");
                }
                content = sb.toString();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 复制Assets目录下的单个文件到指定文件夹
     *
     * @param ctx
     * @param assetsFile
     * @param newDir
     * @return
     */
    public static boolean copyAssetsFile2Dir(Context ctx, String assetsFile, String newDir) {
        try {
            // 在样例文件夹下创建文件
            File fileTarget = new File(newDir, assetsFile);
            // 如果文件已经存在则跳出
            if (fileTarget.exists()) {
                return true;
            }

            InputStream input = ctx.getAssets().open(assetsFile);
            OutputStream output = new FileOutputStream(fileTarget);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            input.close();
            output.close();
            buffer = null;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 复制Assets目录下的单个文件到指定文件夹
     *
     * @param ctx
     * @param assetsDir
     * @param newDir
     */
    public static void copyAssetsDir2Dir(Context ctx, String assetsDir, String newDir) {
        String[] files;
        try {
            files = ctx.getResources().getAssets().list(assetsDir);
        } catch (IOException e1) {
            return;
        }
        File fileTarget = new File(newDir);
        // if this directory does not exists, make one.
        if (!fileTarget.exists()) {
            if (!fileTarget.mkdirs()) {
                Log.e("--CopyAssets--", "cannot create directory.");
            }
        }
        for (int i = 0; i < files.length; i++) {
            try {
                String fileName = files[i];
                if (fileName.compareTo("images") == 0 || fileName.compareTo("sounds") == 0 || fileName.compareTo("webkit") == 0) {
                    continue;
                }
                // 判断是否为文件夹，通过是否含有“.”来区分文件夹
                if (!fileName.contains(".")) {
                    if (0 == assetsDir.length()) {
                        copyAssetsDir2Dir(ctx, fileName, newDir + fileName + "/");
                    } else {
                        copyAssetsDir2Dir(ctx, assetsDir + "/" + fileName, newDir + fileName + "/");
                    }
                    continue;
                }

                File outFile = new File(fileTarget, fileName);
                // 判断文件是否存在，存在跳过，不存在就复制
                if (outFile.exists()) {
                    continue; // outFile.delete();
                }
                InputStream input = null;
                if (0 != assetsDir.length())
                    input = ctx.getAssets().open(assetsDir + "/" + fileName);
                else
                    input = ctx.getAssets().open(fileName);
                OutputStream output = new FileOutputStream(outFile);
                // Transfer bytes from in to out
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                input.close();
                output.close();
                buffer = null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件名
     *
     * @param file
     * @return
     */
    public static String getFileName(File file) {
        if (file == null) {
            return "";
        }
        return file.getName();
    }

    /**
     * 获取文件名
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        return getFileName(new File(path));
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param file
     * @return
     */
    public static String getFileNameNoExtension(File file) {
        if (file == null) {
            return "";
        }
        String filename = file.getName();
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param path
     * @return
     */
    public static String getFileNameNoExtension(String path) {
        return getFileNameNoExtension(new File(path));
    }

    /**
     * 获取文件扩展名(不包含前面那个.)
     *
     * @param file
     * @return
     */
    public static String getFileExtension(File file) {
        if (file == null || file.isDirectory()) {
            return "";
        }
        String filename = file.getName();
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 获取文件扩展名(不包含前面那个.)
     *
     * @param path
     * @return
     */
    public static String getFileExtension(String path) {
        return getFileExtension(new File(path));
    }

    /**
     * <b>通过文件file对象获取文件标识MimeType</b><br>
     * <br>
     * MIME type的缩写为(Multipurpose Internet Mail Extensions)代表互联网媒体类型(Internet
     * media type)，
     * MIME使用一个简单的字符串组成，最初是为了标识邮件Email附件的类型，在html文件中可以使用content-type属性表示，
     * 描述了文件类型的互联网标准。MIME类型能包含视频、图像、文本、音频、应用程序等数据。
     *
     * @param file
     * @return
     */
    public static String getFileMimeTypeFromFile(File file) {
        String extension = getFileExtension(file);
        extension = extension.replace(".", "");
        if (extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("wps")) {
            extension = "doc";
        } else if (extension.equalsIgnoreCase("xlsx")) {
            extension = "xls";
        } else if (extension.equalsIgnoreCase("pptx")) {
            extension = "ppt";
        }
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(extension)) {
            // 获得txt文件类型的MimeType
            return mimeTypeMap.getMimeTypeFromExtension(extension);
        } else {
            if (extension.equalsIgnoreCase("dwg")) {
                return "application/x-autocad";
            } else if (extension.equalsIgnoreCase("dxf")) {
                return "application/x-autocad";
            } else if (extension.equalsIgnoreCase("ocf")) {
                return "application/x-autocad";
            } else {
                return "*/*";
            }
        }
    }

    /**
     * <b>通过文件的扩展名Extension获取文件标识MimeType</b><br>
     * <br>
     * MIME type的缩写为(Multipurpose Internet Mail Extensions)代表互联网媒体类型(Internet
     * media type)，
     * MIME使用一个简单的字符串组成，最初是为了标识邮件Email附件的类型，在html文件中可以使用content-type属性表示，
     * 描述了文件类型的互联网标准。MIME类型能包含视频、图像、文本、音频、应用程序等数据。
     *
     * @param extension
     * @return
     */
    public static String getFileMimeTypeFromExtension(String extension) {
        extension = extension.replace(".", "");
        if (extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("wps")) {
            extension = "doc";
        } else if (extension.equalsIgnoreCase("xlsx")) {
            extension = "xls";
        } else if (extension.equalsIgnoreCase("pptx")) {
            extension = "ppt";
        }
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasExtension(extension)) {
            // 获得txt文件类型的MimeType
            return mimeTypeMap.getMimeTypeFromExtension(extension);
        } else {
            if (extension.equalsIgnoreCase("dwg")) {
                return "application/x-autocad";
            } else if (extension.equalsIgnoreCase("dxf")) {
                return "application/x-autocad";
            } else if (extension.equalsIgnoreCase("ocf")) {
                return "application/x-autocad";
            } else {
                return "*/*";
            }
        }
    }

    /**
     * <b>通过文件标识MimeType获取文件的扩展名Extension</b><br>
     * <br>
     * MIME type的缩写为(Multipurpose Internet Mail Extensions)代表互联网媒体类型(Internet
     * media type)，
     * MIME使用一个简单的字符串组成，最初是为了标识邮件Email附件的类型，在html文件中可以使用content-type属性表示，
     * 描述了文件类型的互联网标准。MIME类型能包含视频、图像、文本、音频、应用程序等数据。
     *
     * @param mimeType
     * @return
     */
    public static String getFileExtensionFromMimeType(String mimeType) {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (mimeTypeMap.hasMimeType(mimeType)) {
            // 获得"text/html"类型所对应的文件类型如.txt、.jpeg
            return mimeTypeMap.getExtensionFromMimeType(mimeType);
        } else {
            return "";
        }
    }

    /**
     * 获取gmail附件的名称和大小
     *
     * @param context
     * @param documentUri
     * @return
     */
    public static String getAttachmetName(Context context, Uri documentUri) {
        if (null != documentUri) {
            final String uriString = documentUri.toString();
            String documentFilename = null;

            final int mailIndexPos = uriString.lastIndexOf("/attachments");
            if (mailIndexPos != -1) {
                final Uri curi = documentUri;
                final String[] projection = new String[]{OpenableColumns.DISPLAY_NAME};
                final Cursor cursor = context.getContentResolver().query(curi, projection, null, null, null);
                if (cursor != null) {
                    final int attIdx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (attIdx != -1) {
                        cursor.moveToFirst();
                        documentFilename = cursor.getString(attIdx);
                    }
                    cursor.close();
                }
            }
            return documentFilename;
        }
        return null;
    }

    /**
     * 获取文件的绝对路径，相应地可以改成其他多媒体类型如audio等等
     *
     * @param context 必须是Activity的实例
     * @param uri
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getAbsoluteImagePath(Context context, Uri uri) {
        String strAbsolutePath = "";
        String[] proj = {MediaColumns.DATA};
        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = ((Activity) context).managedQuery(uri, proj, null, null, null);
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        strAbsolutePath = cursor.getString(column_index);
        cursor.close();
        return strAbsolutePath;
    }


    /**
     * 复制文件到目标文件夹
     */
    public static boolean copy(String srcPath, String destPath) {
        try {
            File file = new File(destPath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }

            FileInputStream fis = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(destPath);

            int size = 0;
            byte[] buf = new byte[1024];
            while ((size = fis.read(buf)) != -1)
                fos.write(buf, 0, size);

            fos.close();
            fis.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkAndMakeDir(String fileDir) {
        File file = new File(fileDir);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isJpgFile(String fileName) {

        if (fileName != null) {
            if (fileName.endsWith(".jpg") || fileName.endsWith(".JPG")) {
                return true;
            }

            int pointIndex = fileName.lastIndexOf('.');
            if (pointIndex != -1) {
                String suffix = fileName.substring(pointIndex, fileName.length());
                if (suffix.equalsIgnoreCase(".jpg")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean fileRename(String oldPath, String newPath) {
        File file = new File(oldPath);
        if (!file.exists()) {
            return false;
        } else {
            file.renameTo(new File(newPath));
            return true;
        }
    }

    /********************* 取得文件夹下的音乐路径 传入目录名称、目录路径 ************************************/
    // TODO 取得文件夹下的音乐路径 传入目录名称、目录路径

    /**
     * 取得文件夹下的音乐路径 传入目录名称、目录路径
     *
     * @param context
     * @param rootPath
     * @return
     * @since v0.0.1
     */
    public static List<Map<String, Object>> getAudiosFromFolder(Context context, String rootPath) {
        List<Map<String, Object>> listPhotoInfo = new ArrayList<Map<String, Object>>();
        Map<String, Object> mItem = null;
        try {
            // 获取系通图片管理的数据库信息
            ContentResolver mContentResolver = context.getContentResolver();

            String[] projection = {BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST, MediaColumns.DATA, AudioColumns.DURATION, MediaColumns.SIZE};

            // String strSelection = Media._ID +
            // " in (select image_id from thumbnails) "
            String strSelection = BaseColumns._ID + "!='' ";
            if (!TextUtils.isEmpty(rootPath)) {
                strSelection += " and " + MediaColumns.DATA + " like '" + rootPath + "%' ";
            }
            Cursor cur = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, strSelection, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if (cur.moveToFirst()) {
                do {
                    // 歌曲ID：MediaStore.Audio.Media._ID
                    int audio_id = cur.getInt(cur.getColumnIndexOrThrow(BaseColumns._ID));

                    // 歌曲的名称 ：MediaStore.Audio.Media.TITLE
                    String audio_tilte = cur.getString(cur.getColumnIndexOrThrow(MediaColumns.TITLE));

                    // 歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                    String audio_album = cur.getString(cur.getColumnIndexOrThrow(AudioColumns.ALBUM));

                    // 歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                    String audio_artist = cur.getString(cur.getColumnIndexOrThrow(AudioColumns.ARTIST));

                    // 歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                    String audio_url = cur.getString(cur.getColumnIndexOrThrow(MediaColumns.DATA));

                    // 歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                    int audio_duration = cur.getInt(cur.getColumnIndexOrThrow(AudioColumns.DURATION));

                    // 歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                    long audio_size = cur.getLong(cur.getColumnIndexOrThrow(MediaColumns.SIZE));

                    if (!audio_url.equals("")) {
                        mItem = new HashMap<String, Object>();
                        mItem.put("audio_id", audio_id);
                        mItem.put("audio_tilte", audio_tilte);
                        mItem.put("audio_album", audio_album);
                        mItem.put("audio_artist", audio_artist);
                        mItem.put("audio_url", audio_url);
                        mItem.put("audio_duration", audio_duration);
                        mItem.put("audio_size", audio_size);
                        listPhotoInfo.add(mItem);
                        // Log.i(TAG, "getAudiosFromFolder !rootPath = " +
                        // rootPath + ",photo_path = " + photo_path);
                    } else {
                        continue;
                    }
                } while (cur.moveToNext());
                Log.i(TAG, "getAudiosFromFolder 扫描结束!AudiosCount = " + listPhotoInfo.size());
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPhotoInfo;
    }

    /********************* 获取手机通讯录联系人信息 ************************************/
    // TODO 获取手机通讯录联系人信息

    /**
     * 获取手机通讯录联系人信息(参数 strContactsName,strContactsNumber同时为""或null查询全部通讯录)
     *
     * @param strContactsName   联系人姓名(模糊查询,默认全部)
     * @param strContactsNumber 联系人电话(模糊查询,默认全部)
     * @return 返回一个List数组包含的属性<br>
     * <b>contactsName</b> 联系人姓名<br>
     * <b>contactsNamePY</b> 联系人姓名 (拼音简码)<br>
     * <b>contactsNumber</b> 手机号<br>
     * <b>contactsEmail</b> 电子邮箱<br>
     * <b>contactsSelected</b> 选择状态(<b>true</b>选中，<b>false</b>未选中)<br>
     */
    public static List<Map<String, Object>> getContactsList(Context context, String strContactsName, String strContactsNumber) {
        List<Map<String, Object>> mListContacters = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> mHashMap = new HashMap<String, Object>();
        // 获取库Phone表字段(联系人显示名称 、手机号码、联系人的ID)
        String[] projection = null;// {Phone.CONTACT_ID,
        // Phone.DISPLAY_NAME,Phone.NUMBER};
        // 设置查询条件
        String strSelection = null;// " LENGTH(TRIM(" + Phone.DISPLAY_NAME
        // +"))>1 ";
        // 设置排序方式，排序字段可以设置多个
        String strOrderBy = null;// " sort_key_alt, " + Phone.DISPLAY_NAME ;
        // // 添加查询条件联系人姓名和联系人电话
        // if (!strContactsName.equalsIgnoreCase("")) {
        // strSelection += Phone.DISPLAY_NAME + " LIKE " + "'%" +
        // strContactsName + "%'";
        // }
        // if (!strContactsNumber.equalsIgnoreCase("")) {
        // strSelection = Phone.NUMBER + " LIKE " + "'" + strContactsNumber +
        // "%'";
        // }

        ContentResolver cr = context.getContentResolver();
        Cursor c_name = cr.query(ContactsContract.Contacts.CONTENT_URI, projection, strSelection, null, strOrderBy);
        while (c_name.moveToNext()) {
            // 得到联系人ID
            String contactsID = c_name.getString(c_name.getColumnIndex(BaseColumns._ID));
            // 得到联系人名称
            String contactsName = c_name.getString(c_name.getColumnIndex(PhoneLookup.DISPLAY_NAME));
            // 得到手机号码
            String contactsNumber = "";
            // 得到联系人email
            String contactsEmail = "";

            // 获取与联系人ID相同的手机号码,可能不止一个
            contactsNumber = "";
            Cursor c_number = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactsID, null, null);
            while (c_number.moveToNext()) {
                String number = c_number.getString(c_number.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
                number = number.replace("-", "");
                if (number != null && !number.trim().equalsIgnoreCase(""))
                    contactsNumber = number;
            }
            c_number.close();

            // 获取与联系人ID相同的电子邮件,可能不止一个
            contactsEmail = "";
            Cursor c_email = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactsID, null, null);
            while (c_email.moveToNext()) {
                String email = c_email.getString(c_email.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));
                if (email != null && !email.trim().equalsIgnoreCase(""))
                    contactsEmail = email;
            }
            c_email.close();

            // if (StoneFunctions.isEmail(contactsEmail) ||
            // StoneFunctions.isMobileNumber(contactsNumber)) {
            mHashMap = new HashMap<String, Object>();
            mHashMap.put("contactsNamePY", "");
            mHashMap.put("contactsName", contactsName);
            mHashMap.put("contactsNumber", contactsNumber);
            mHashMap.put("contactsEmail", contactsEmail);
            mHashMap.put("contactsSelected", false);
            mListContacters.add(mHashMap);
            Log.i(TAG, "contactsName = " + contactsName + "contactsNumber = " + contactsNumber + "contactsEmail = " + contactsEmail);
            // }
        }
        c_name.close();
        return mListContacters;
    }

    /**
     * 读取给定文件中的内容信息并返回
     *
     * @param fileName
     * @return
     * @throws IOException
     * @since v0.0.1
     */
    public static String readFileValue(String fileName) throws IOException {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
                FileInputStream inputStream = new FileInputStream(fileName);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.close();
                inputStream.close();
                byte[] data = outputStream.toByteArray();
                return new String(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "readFileValue is Error! ErrorCode = " + e.getMessage());
        }
        return "";
    }

    /**
     * 删除SD卡中给定位置的文件
     */
    public static Boolean DeleteFileFromSD(String strURL) {
        if (strURL == null || "".equals(strURL)) {
            return true;
        }
        // /**SD卡目录获取操作*/
        // //判断SD卡是否插入
        // Result=Environment.getExternalStorageState().equalsIgnoreCase(android.os.Environment.MEDIA_MOUNTED);
        // //获得SD卡根目录：
        // File sdFileRoot = Environment.getExternalStorageDirectory();
        // //获得私有根目录(程序根目录)：
        // String fileRoot = SQLiteContext.getFilesDir()+"\\";
        File myFile = new File(strURL);
        boolean Result = false;
        if (FileUtils.isSDExist()) {
            // 删除文件夹
            Result = myFile.delete();
        }

        // /**文件夹或文件夹操作：*/
        // //建立文件或文件夹
        // if (myFile.isDirectory())//判断是文件或文件夹
        // {
        // Result=myFile.mkdir(); //建立文件夹
        // //获得文件夹的名称：
        // String FileName = myFile.getName();
        // //列出文件夹下的所有文件和文件夹名
        // File[] files = myFile.listFiles();
        // //获得文件夹的父目录
        // String parentPath = myFile.getParent();
        // //修改文件夹名字
        // File myFileNew=new File(parentPath+FileName);
        // Result=myFile.renameTo(myFileNew);
        // //删除文件夹
        // Result=myFile.delete();
        // }
        // else
        // {
        // if (!myFile.exists()) {
        // try {
        // Result=myFile.createNewFile();//建立文件
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // //获得文件或文件夹的名称：
        // String FileName = myFile.getName();
        // //获得文件的父目录
        // String parentPath = myFile.getParent();
        // //修改文件名字
        // File myFileNew=new File(parentPath+FileName);
        // Result=myFile.renameTo(myFileNew);
        // //删除文件夹
        // Result=myFile.delete();
        // }
        return Result;
    }

    /**
     * 判断SD卡中给定位置的文件是否存在
     *
     * @param strURL
     * @return true 存在 false 不存在
     */
    public static Boolean checkFileExists(String strURL) {
        if (strURL == null || "".equals(strURL)) {
            return false;
        }
        File myFile = new File(strURL);
        boolean Result = true;
        if (FileUtils.isSDExist()) {
            Result = myFile.exists(); // 判断文件是否存在
        }
        return Result;
    }

    /**
     * 将Double型数据的小数做保留处理
     *
     * @param dblValue 输入数值
     * @param intPoint 保留位数
     * @return
     */
    public static double getDoublePoint(double dblValue, int intPoint) {
        try {
            double returnDouble;
            double parm = Math.pow(10, intPoint);
            returnDouble = ((int) (dblValue * parm)) / parm;
            return returnDouble;
        } catch (Exception e) {
            return dblValue;
        }
    }

    /**
     * 将Double型数据的小数做保留处理
     *
     * @param v     输入数值
     * @param scale 保留位数
     * @return
     */
    public static double getDoubleRound(Double v, int scale) {
        try {
            BigDecimal b = null == v ? new BigDecimal("0.0") : new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            return v;
        }
    }

    /**
     * 将Double型数据的小数做保留处理后转成字符串
     *
     * @param v     输入数值
     * @param scale 保留位数
     * @return
     */
    public static String getDouble2Sting(Double v, int scale) {
        try {
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(scale);
            format.setMinimumFractionDigits(scale);
            return format.format(v);
        } catch (Exception e) {
            return v.toString();
        }
    }

    /**
     * 取得指定子串在字符串中出现的次数。
     *
     * @param strMain 要扫描的字符串
     * @param strSub  子字符串
     * @return 子串在字符串中出现的次数，如果字符串为<code>null</code>或空，则返回<code>0</code>
     */
    public static int getSubStringCount(String strMain, String strSub) {
        if ((strMain == null) || (strMain.length() == 0) || (strSub == null) || (strSub.length() == 0)) {
            return 0;
        }
        int count = 0;
        int index = 0;

        while ((index = strMain.indexOf(strSub, index)) >= 0) {
            count++;
            index += strSub.length();
        }
        return count;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param dbInput 需要四舍五入的数字
     * @param scale   小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String DoubleFormat(double dbInput, int scale) {
        String strResult = "0";
        // 方式一：
        BigDecimal bd = new BigDecimal(Double.toString(dbInput));
        strResult = bd.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();

        // //方式二：
        // //#.00 表示两位小数 #.0000四位小数 以此类推...
        // String strFormat="#.0";
        // for(int i=1;i<scale;i++){
        // strFormat+="0";
        // }
        // DecimalFormat df = new DecimalFormat(strFormat);
        // strResult = df.format(dbInput);
        //
        // //方式三：
        // //%.2f %. 表示 小数点前任意位数 2 表示两位小数 格式后的结果为f 表示浮点型
        // strFormat="%."+scale +"f";
        // strResult = String.format(Locale.getDefault(),strFormat, dbInput);
        return strResult;
    }


    /********************* 对安装包，类文件，以及APP文件的安装、卸载处理 ************************************/
    // TODO: 以下操作时对安装包，类文件，以及APP文件的安装、卸载处理

    /**
     * 安装APK程序代码(测试成功)
     *
     * @param context
     * @param apkPath
     */
    public static void ApkInstall(Context context, String apkPath) {
        File fileAPK = new File(apkPath);
        if (fileAPK.exists() && fileAPK.getName().toLowerCase(Locale.getDefault()).endsWith(".apk")) {
            Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(fileAPK), "application/vnd.android.package-archive");
            context.startActivity(install);// 安装

            // Intent intent = new Intent(Intent.ACTION_VIEW);
            // intent.setDataAndType(Uri.fromFile(new File(apkPath)),
            // "application/vnd.android.package-archive");
            // context.startActivity(intent);
        }
    }


    /**
     * 获取当前应用程序版本编码
     *
     * @param context
     * @return eg. 1
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
            if (versionCode < 1) {
                return 1;
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }


    /**
     * 检测该包名所对应类是否存在（eg.com.org.MainActivity）
     *
     * @param className
     * @return
     */
    public static boolean checkClassNameExists(String className) {
        if (className == null || "".equals(className)) {
            return false;
        }
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }


    /**
     * 根据文件mimeType调用系统相关程序打开
     *
     * @param context
     * @param file
     */
    public static void openFileBySystemApp(Context context, File file) {
        String mimeType = getFileMimeTypeFromFile(file);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        // 设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), mimeType);
        context.startActivity(intent);
    }


    /**
     * Map按键Key排序(sort by key)
     *
     * @param oriMap
     * @return
     * @since v0.0.1
     */
    public static Map<Integer, String> sortMapByKey(Map<Integer, String> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<Integer, String> sortedMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
            @Override
            public int compare(Integer key1, Integer key2) {
                int intKey1 = 0, intKey2 = 0;
                try {
                    intKey1 = getInt(String.valueOf(key1));
                    intKey2 = getInt(String.valueOf(key2));
                } catch (Exception e) {
                    intKey1 = 0;
                    intKey2 = 0;
                }
                return intKey1 - intKey2;
            }
        });
        sortedMap.putAll(oriMap);
        return sortedMap;
    }

    private static int getInt(String str) {
        int i = 0;
        try {
            Pattern p = Pattern.compile("^\\d+");
            Matcher m = p.matcher(str);
            if (m.find()) {
                i = Integer.valueOf(m.group());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Map按值Value排序(sort by value)
     *
     * @param oriMap
     * @return
     * @since v0.0.1
     */
    public static Map<String, String> sortMapByValue(Map<String, String> oriMap) {
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        if (oriMap != null && !oriMap.isEmpty()) {
            List<Entry<String, String>> entryList = new ArrayList<Entry<String, String>>(oriMap.entrySet());
            Collections.sort(entryList, new Comparator<Entry<String, String>>() {
                @Override
                public int compare(Entry<String, String> entry1, Entry<String, String> entry2) {
                    int value1 = 0, value2 = 0;
                    try {
                        value1 = getInt(entry1.getValue());
                        value2 = getInt(entry2.getValue());
                    } catch (NumberFormatException e) {
                        value1 = 0;
                        value2 = 0;
                    }
                    return value2 - value1;
                }
            });
            Iterator<Entry<String, String>> iter = entryList.iterator();
            Entry<String, String> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }

    /**
     * 删除上传文件
     *
     * @param tempFilePath
     */
    public static void deleteUploadTempPic(String tempFilePath) {
        if (getFileName(tempFilePath).contains(ImageUtil.TEMP_FILE_NAME)) {
            deleteFile(tempFilePath);
        }
    }

    public static void deleteUploadTempPic(List<String> files) {
        for (String file : files) {
            deleteUploadTempPic(file);
        }

    }


    public static String getCameraImagePath() {
        FileUtils.checkAndMakeDir(new StringBuilder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()).append("/Camera/").toString());
        return (new StringBuilder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()).append("/Camera/")).toString();
    }


}
