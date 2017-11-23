/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: Jiaxiao
 * $Id: ImageUtils.java 2014-8-19 上午11:34:59 $
 */
package com.cicada.startup.common.utils;

import android.R.string;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片相关处理工具类
 * <p>
 * 创建时间: 2014-8-19 上午11:34:59 <br/>
 */
@SuppressLint("NewApi")
public class ImageUtil {
    public static final String TEMP_FILE_NAME = "upload_temp_file_";
    private static String TAG = "ImageUtil";

    /**
     * 设定上传图片的最大尺寸Width值
     */
    public final static int UPLOADPHOTO_WIDTH_MAX = 1024;
    /**
     * 设定上传图片的最大尺寸Height值
     */
    public final static int UPLOADPHOTO_HEIGHT_MAX = 1024;
    /**
     * 设定上传图片的最大物理大小值(默认单位KB)
     */
    public final static int UPLOADPHOTO_SIZE_MAX = 200;
    /**
     * 设定图片的保存或压缩质量
     */
    public final static int UPLOADPHOTO_QUALITY = 80;

    public static interface OnConvertListener {
        void onSuccess(String filePath);

        void onFail();
    }

    public static boolean convertToJpg(final String srcFilePath, final String destFilePath) {
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destFilePath));
            Bitmap bitmap = BitmapFactory.decodeFile(srcFilePath);
            boolean compressed = bitmap.compress(CompressFormat.JPEG, UPLOADPHOTO_QUALITY, out);
            out.flush();
            out.close();

            bitmap.recycle();

            return compressed;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return false;
    }

    // 生成圆角图片
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            final float roundPx = 17;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    /**
     * 将 bitmap 压缩后保存到文件
     */
    public static boolean bitmapToFile(Bitmap bitmap, String filePath, int quality) {
        if (bitmap == null || filePath == null) {
            return false;
        }
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, quality, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过文件路径来获得 Bitmap 对象
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapFromFile(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 通过文件路径来获得 Bitmap 对象,并对Bitmap进行缩放处理放置内存益处
     *
     * @param filePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapFromFile(String filePath, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 获取图片的大小.
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        // 获取缩放大小
        int inSampleSize = calculateInSampleSize(opts, width, height);

        opts.inSampleSize = inSampleSize;
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;

        return BitmapFactory.decodeFile(filePath, opts);
    }

    public static Bitmap getRoundCornerBitmapFromFile(String filePath, int roundPixels) {
        return getRoundCornerImage(BitmapFactory.decodeFile(filePath), roundPixels);
    }

    private static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels) {
        if (bitmap == null) {
            return null;
        }
        // 创建一个和原始图片一样大小位图
        Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        // 创建带有位图roundConcerImage的画布
        Canvas canvas = new Canvas(roundConcerImage);
        // 创建画笔
        Paint paint = new Paint();
        // 创建一个和原始图片一样大小的矩形
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        // 去锯齿
        paint.setAntiAlias(true);
        // 画一个和原始图片一样大小的圆角矩形
        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
        // 设置相交模式
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // 把图片画到矩形去
        canvas.drawBitmap(bitmap, null, rect, paint);
        return roundConcerImage;
    }

    /**
     * 获取缩略图
     */
    public static Bitmap getThumbnail(Bitmap bmp, int width, int height) {
        return ThumbnailUtils.extractThumbnail(bmp, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /************************************************** 主要是图片处理操作的函数(START) **************************************************/

    /**
     * 计算地图距离时使用，单位(米)
     */
    private static Double EARTH_RADIUS = 6378137.0;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /** */
    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 功能：经度到像素X值
     */
    public static double lngToX(double lng, int zoom) {
        return (lng + 180) * (256L << zoom) / 360;
    }

    /**
     * 功能：像素X到经度
     */
    public static double XToLng(double pixelX, int zoom) {
        return pixelX * 360 / (256L << zoom) - 180;
    }

    /**
     * 功能：纬度到像素Y
     */
    public static double latToY(double lat, int zoom) {
        double siny = Math.sin(lat * Math.PI / 180);
        double y = Math.log((1 + siny) / (1 - siny));

        return (128 << zoom) * (1 - y / (2 * Math.PI));
    }

    /**
     * 功能：像素Y到纬度
     */
    public static double YToLat(double pixelY, int zoom) {
        double y = 2 * Math.PI * (1 - pixelY / (128 << zoom));
        double z = Math.pow(Math.E, y);
        double siny = (z - 1) / (z + 1);

        return Math.asin(siny) * 180 / Math.PI;
    }

    /**
     * 产生一个随机数
     */
    public static int getRandomInt() {
        int intLimit[] = {-1, 1};
        int a = intLimit[(int) (Math.random() * 2)];
        return a;
    }


    /**
     * 以下为从Raw文件中读取：
     *
     * @return
     */
    public static InputStream getStreamFromResID(Context context, int resId) {
        try {
            InputStream stream = context.getResources().openRawResource(resId);
            return stream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	/*
     * 11. assets文件夹资源的访问
	 *
	 * assets文件夹里面的文件都是保持原始的文件格式，需要用AssetManager以字节流的形式读取文件。
	 *
	 * 1. 先在Activity里面调用getAssets()来获取AssetManager引用。
	 *
	 * 2. 再用AssetManager的open(String fileName, int
	 * accessMode)方法则指定读取的文件以及访问模式就能得到输入流InputStream。
	 *
	 * 3. 然后就是用已经open file 的inputStream读取文件，读取完成后记得inputStream.close()。
	 *
	 * 4.调用AssetManager.close()关闭AssetManager。 需要注意的是，来自Resources和Assets
	 * 中的文件只可以读取而不能进行写的操作
	 */

    /**
     * 通过资源文件名称、获取源文件ID
     *
     * @param resFolder 资源文件夹 drawable、raw、string
     * @param fileName
     * @return
     */
    public static int getResIDFromResName(Context context, String resFolder, String fileName) {
        /** 通过资源名称找到资源图片 */
        int resID = -1;
        try {
            fileName = fileName.replace(".png", "");
            fileName = fileName.replace(".jpg", "");
            fileName = fileName.replace(".jpeg", "");
            fileName = fileName.replace(".gif", "");
            if (resFolder.trim().length() > 0) {
                resID = context.getResources().getIdentifier(fileName, resFolder, context.getPackageName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resID;
    }

    /**
     * 将二进制流转成字符串
     *
     * @param stream
     * @return
     */
    public static String getStringFromStream(InputStream stream) {
        try {
            InputStreamReader inputReader = new InputStreamReader(stream);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            inputReader.close();
            stream.close();
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 以下为从Raw文件中读取
     *
     * @param context
     * @param resFolder 资源文件夹 drawable、raw
     * @param fileName  资源文件名
     * @return
     */
    public static InputStream getStreamFromResFileName(Context context, String resFolder, String fileName) {
        try {
            InputStream stream = context.getResources().openRawResource(getResIDFromResName(context, resFolder, fileName));
            return stream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以下为直接从assets读取
     */
    public static InputStream getStreamFromAssetsFileName(Context context, String fileName) {
        try {
            InputStream stream = context.getResources().getAssets().open(fileName);
            return stream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param fileName 资源文件名
     * @return
     */
    public static Bitmap getBitmapFromAssetsFileName(Context context, String fileName) {
        try {
            return getBitmapFromStream(getStreamFromAssetsFileName(context, fileName), 1);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromAssetsFileName is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resName 资源文件名
     * @return
     */
    public static Bitmap getBitmapFromResFileName(Context context, String resName) {
        try {
            return getBitmapFromResFileID(context, getResIDFromResName(context, "drawable", resName));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromResFileName is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 通过string资源中的name获取vlaue
     *
     * @param context
     * @param resName 资源文件名
     * @return
     */
    public static String getStringFromResName1(Context context, String resName) {
        try {
            return context.getResources().getString(getResIDFromResName(context, "string", resName));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getStringFromResName is Error! ErrorCode = " + e.getMessage());
        }
        return "";
    }

    /**
     * 通过string资源中的name获取vlaue
     *
     * @param context
     * @param resName 资源文件名
     * @return
     */
    public static String getStringFromResName2(Context context, String resName) {
        Class<string> c = string.class;
        Field field;
        int value = 0;
        try {
            field = c.getDeclaredField(resName);
            value = field.getInt(null);
            return context.getResources().getString(value);
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId   资源文件Id
     * @return
     */
    public static Bitmap getBitmapFromResFileID(Context context, int resId) {
        try {
            return getBitmapFromStream(getStreamFromResID(context, resId), 1);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromResFileID is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param fileName 资源文件名
     * @return
     */
    public static Drawable getDrawableFromAssetsFileName(Context context, String fileName) {
        try {
            BitmapDrawable drawable = null;
            InputStream stream = getStreamFromAssetsFileName(context, fileName);
            drawable = new BitmapDrawable(stream);
            stream.close();
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromAssetsFileName is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId   资源文件Id
     * @return
     */
    public static Drawable getDrawableFromResFileID(Context context, int resId) {
        try {
            BitmapDrawable drawable = null;
            InputStream stream = getStreamFromResID(context, resId);
            drawable = new BitmapDrawable(stream);
            stream.close();
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromResFileID is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param fileName 资源文件名
     * @return
     */
    public static Drawable getDrawableFromResFileName(Context context, String fileName) {
        try {
            return getDrawableFromResFileID(context, getResIDFromResName(context, "drawable", fileName));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromResFileName is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 获取两张图片叠加后的结果合成图片 Composite 图像的合成，可以通过在同一个Canvas中绘制两张图片。
     * 只是在绘制第二章图片的时候，需要给Paint指定一个变幻模式TransferMode。
     * 在Android中有一个XFermode所有的变幻模式都是这个类的子类
     * 我们需要用到它的一个子类PorterDuffXfermode,关于这个类，其中用到PorterDuff类
     * 这个类很简单，就包含一个Enum是Mode，其中定义了一组规则，这组规则就是如何将 一张图像和另一种图像进行合成
     * 关于图像合成有四种模式，LIGHTEN,DRAKEN,MULTIPLY,SCREEN
     *
     * @param srcBitmap
     * @param dstBitmap
     * @return
     */
    public static Bitmap getOverlayBitmap(Bitmap srcBitmap, Bitmap dstBitmap, int x, int y) {
        if (srcBitmap == null) {
            return null;
        }
        if (dstBitmap == null) {
            return srcBitmap;
        }
        try {
            int intWidth = srcBitmap.getWidth();
            int intHeight = srcBitmap.getHeight();
            // int fgWidth = foreground.getWidth();
            // int fgHeight = foreground.getHeight();
            // 下面这个Bitmap中创建的函数就可以创建一个空的Bitmap,创建一个新的和SRC长度宽度一样的位图
            Bitmap returnBitmap = Bitmap.createBitmap(intWidth, intHeight, Config.ARGB_8888);
            Paint paint = new Paint();
            Canvas canvas = new Canvas(returnBitmap);
            // 首先在 0，0坐标开始绘制第一张图片，很简单.
            canvas.drawBitmap(srcBitmap, 0, 0, paint);

            // 在绘制第二张图片的时候，我们需要指定一个Xfermode
            // 这里采用Multiply模式，这个模式是将两张图片的对应的点的像素相乘
            // ，再除以255，然后以新的像素来重新绘制显示合成后的图像
            paint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
            canvas.drawBitmap(dstBitmap, x, y, paint);// 在 0，0坐标开始画入fg
            // ，可以从任意位置画入

            // save all clip
            canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
            // store
            canvas.restore();// 存储
            return returnBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapByRotate is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    // /**
    // * 图片圆角
    // * @param bitmap
    // * @return
    // */
    // public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
    // Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
    // bitmap.getHeight(), Config.ARGB_8888);
    // Canvas canvas = new Canvas(output);
    //
    // final int color = 0xff424242;
    // final Paint paint = new Paint();
    // final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    // final RectF rectF = new RectF(rect);
    // final float roundPx = 12;
    //
    // paint.setAntiAlias(true);
    // canvas.drawARGB(0, 0, 0, 0);
    // paint.setColor(color);
    // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    //
    // paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    // canvas.drawBitmap(bitmap, rect, rect, paint);
    // return output;
    // }
    //
    // /**
    // * 图片缩放、翻转和旋转图片
    // * @param bmpOrg
    // * @param rotate
    // * @return
    // */
    // public static android.graphics.Bitmap gerZoomRotateBitmap(
    // android.graphics.Bitmap bmpOrg, int rotate) {
    // // 获取图片的原始的大小
    // int width = bmpOrg.getWidth();
    // int height = bmpOrg.getHeight();
    //
    // int newWidth = 300;
    // int newheight = 300;
    // // 定义缩放的高和宽的比例
    // float sw = ((float) newWidth) / width;
    // float sh = ((float) newheight) / height;
    // // 创建操作图片的用的Matrix对象
    // android.graphics.Matrix matrix = new android.graphics.Matrix();
    // // 缩放翻转图片的动作
    // // sw sh的绝对值为绽放宽高的比例，sw为负数表示X方向翻转，sh为负数表示Y方向翻转
    // matrix.postScale(sw, sh);
    // // 旋转30*
    // matrix.postRotate(rotate);
    // //创建一个新的图片
    // android.graphics.Bitmap resizeBitmap = android.graphics.Bitmap
    // .createBitmap(bmpOrg, 0, 0, width, height, matrix, true);
    // return resizeBitmap;
    // }

    /**
     * 给图片添加边框
     *
     * @param oldBitmap 原图片
     * @param resBitmap 边框资源
     * @return
     */
    public static Bitmap getBitmapForFrame(Bitmap oldBitmap, Bitmap resBitmap) {
        Drawable[] array = new Drawable[2];
        array[0] = new BitmapDrawable(oldBitmap);
        Bitmap b = getBitmapZoom(resBitmap, oldBitmap.getWidth(), oldBitmap.getHeight());
        array[1] = new BitmapDrawable(b);
        LayerDrawable layer = new LayerDrawable(array);
        return getBitmapByDrawable(layer);
    }

    /**
     * 图片旋转处理 ,按用户给定的旋转角度旋转
     */
    public static Bitmap getBitmapByRotate(Bitmap bitmap, float floRotationAngle) {
        try {
            int Width = bitmap.getWidth();
            int Height = bitmap.getHeight();
            Matrix matrix = new Matrix();// 创建一个矩阵
            matrix.postRotate(floRotationAngle, Width / 2.0f, Height * 1.0f);// 设置旋转角度和旋转点坐标,默认旋转点为图片中心
            // 把原始位图通过矩阵旋转后返回新位图
            return Bitmap.createBitmap(bitmap, 0, 0, Width, Height, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapByRotate is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 图片缩放处理、按用户给定的缩放比例处理，按缩放后的尺寸返回
     */
    public static Bitmap getBitmapByZoomScale(Bitmap bitmap, float scale) {
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        try {
            // 计算缩放率
            Log.i(TAG, "getBitmapByZoomScale =" + scale + ",处理前尺寸" + bitmap.getHeight() + "X" + bitmap.getWidth());
            Log.i(TAG, "getBitmapByZoomScale =" + scale + ",处理后尺寸" + bitmap.getHeight() * scale + "X" + bitmap.getWidth() * scale);
            // 缩放图片动作
            matrix.postScale(scale, scale);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapByZoomScale is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 图片尺寸缩放处理 ,按用户给定宽度 ，高度 、运算后进行等比缩放
     */
    public static Bitmap getBitmapZoom(Bitmap bitmap, int newWidth, int newHeight) {
        float floScale = 1.0f;
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        try {
            // 获取这个图片的宽和高的最大值
            float intMAXOld = Math.max(bitmap.getWidth(), bitmap.getHeight());
            float intMAXNew = Math.max(newWidth, newHeight);

            // 计算缩放率，新尺寸除原始尺寸
            floScale = intMAXNew / intMAXOld;
            if (floScale < 1.0f) {
                // 缩放图片动作
                matrix.postScale(floScale, floScale);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } else {
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapZoom is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 图片尺寸缩放、裁剪处理 ,按用户给定宽度 ，高度 返回
     */
    public static Bitmap getBitmapByZoomAndCut(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap returnBmp = null;
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        try {

            // 计算缩放率，新尺寸除原始尺寸
            float scaleWidth = ((float) newWidth) / bitmap.getWidth();
            float scaleHeight = ((float) newHeight) / bitmap.getHeight();
            float scale = 1;
            if (scaleWidth > scaleHeight) {
                scale = scaleWidth;
            } else {
                scale = scaleHeight;
            }
            Log.i(TAG, "getBitmapByZoomAndCut = " + bitmap.getHeight() + "X" + bitmap.getWidth());
            // 缩放图片动作
            matrix.postScale(scale, scale);
            returnBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Log.i(TAG, "getBitmapByZoomAndCut = " + returnBmp.getHeight() + "X" + returnBmp.getWidth());

            // 获取这个图片的宽和高
            int x = (returnBmp.getWidth() - newWidth) / 2;
            int y = (returnBmp.getHeight() - newHeight) / 2;
            // 设置透明度、开始裁剪
            matrix.setTranslate(0, 0);
            returnBmp = Bitmap.createBitmap(returnBmp, x, y, newWidth, newHeight, matrix, true);
            Log.i(TAG, "getBitmapByZoomAndCut = " + returnBmp.getHeight() + "X" + returnBmp.getWidth());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapByZoomAndCut is Error! ErrorCode = " + e.getMessage());
        }
        return returnBmp;
    }

    /**
     * 图片切割，按用户给定的切割位置切割
     **/
    public static Bitmap getBitmapByCut(Bitmap bitmap, float x, float y, float width, float height) {
        try {
            // 设置透明度
            Matrix m_Matrix = new Matrix();
            m_Matrix.setTranslate(0, 0);
            return Bitmap.createBitmap(bitmap, (int) x, (int) y, (int) width, (int) height, m_Matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapByCut is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 将图片进行切图处理 (从中间开始切割)
     *
     * @param scrBitmap
     * @param limitWidth
     * @param limitHeight
     * @return
     */
    public static Bitmap getBitmapByCut(Bitmap scrBitmap, int limitWidth, int limitHeight) {
        // 宽/实际显示宽度<1 高/实际显示高度<1
        // 宽/实际显示宽度<1 高/实际显示高度>1
        // 宽/实际显示宽度>1 高/实际显示高度>1
        // 宽/实际显示宽度>1 高/实际显示高度<1
        float srcScale = scrBitmap.getWidth() / scrBitmap.getHeight();
        float dstScale = limitWidth / limitHeight;
        float scale = 0;
        if (dstScale > srcScale) {
            scale = dstScale;
        } else {
            scale = srcScale;
        }
        Bitmap bitmap = Bitmap.createBitmap(limitWidth, limitHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        canvas.drawBitmap(scrBitmap, matrix, new Paint());
        return bitmap;
    }

    /**
     * 把图片处理成圆型图片
     *
     * @param imageBitmap 传入图片对象
     * @param pixels      设置圆型图片的半径，如果半径<图片最小边则返回圆角图片，如果半径>=图片最小边则返回圆形图片 如果传入值=0
     *                    则强制生成圆形图片
     * @param isOnline    是否在线如果在线就原图，否则灰度调整
     * @return
     */
    public static Bitmap getBitmapByRoundCorner(Bitmap imageBitmap, int pixels, boolean isOnline) {
        Bitmap output = null;
        try {
            if (imageBitmap != null) {
                int intSmallSize = imageBitmap.getWidth() > imageBitmap.getHeight() ? imageBitmap.getHeight() : imageBitmap.getWidth();
                if (pixels < 1) {
                    pixels = intSmallSize;
                }
                // bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                // intSmallSize,intSmallSize); //创建一个指定大小居中的缩略图
                output = Bitmap.createBitmap(intSmallSize, intSmallSize, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                final int color = 0xff424242;
                final Paint paint = new Paint();
                final Rect rect = new Rect(0, 0, intSmallSize, intSmallSize);
                final RectF rectF = new RectF(rect);
                final float roundPx = pixels;
                paint.setAntiAlias(true);
                if (!isOnline) {
                    ColorMatrix cmGray = new ColorMatrix();
                    cmGray.setSaturation(0);
                    ColorMatrixColorFilter filterGray = new ColorMatrixColorFilter(cmGray);
                    paint.setColorFilter(filterGray);
                }
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
                paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
                canvas.drawBitmap(imageBitmap, rect, rect, paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapByRoundCorner is Error! ErrorCode = " + e.getMessage());
        }
        return output;
    }

    /**
     * 把View绘制到Bitmap上 (屏幕截图功能)
     *
     * @param view   需要绘制的View
     * @param width  该View的宽度
     * @param height 该View的高度
     * @return 返回Bitmap对象
     */
    public static Bitmap getBitmapFromView(View view, int width, int height) {
        try {
            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            view.measure(widthSpec, heightSpec);
            view.layout(0, 0, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromView is Error! ErrorCode = " + e.getMessage());
        }
        return null;
    }

    /**
     * 通过网络、本地路径，返回缩放后的Bitmap
     *
     * @param strPhotoPath
     * @param intSampleSize 给定的缩放比例(缩小到原来的1/n)
     * @return
     */
    public static Bitmap getBitmapFromURL(String strPhotoPath, int intSampleSize) {
        if (strPhotoPath == null || "".equals(strPhotoPath)) {
            return null;
        }
        InputStream stream;
        BitmapFactory.Options m_Options = new BitmapFactory.Options();
        m_Options.inSampleSize = intSampleSize < 1 ? 1 : intSampleSize;
        m_Options.inPurgeable = true;
        try {
            Bitmap m_ReturnBitmap = null;
            if (URLUtil.isHttpUrl(strPhotoPath)) {
                // 获取网络图片
                URL url = new URL(strPhotoPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                stream = conn.getInputStream();
                m_ReturnBitmap = BitmapFactory.decodeStream(stream, null, m_Options);
                stream.close();
            } else {
                // 获取本地图片
                File f = new File(strPhotoPath);
                if (f.exists()) {
                    stream = new FileInputStream(f);
                    m_ReturnBitmap = BitmapFactory.decodeStream(stream, null, m_Options);
                    stream.close();
                }
            }
            return m_ReturnBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromURL is Error ! strPhotoPath = " + strPhotoPath);
        }
        return null;
    }

    /**
     * 通过二进制流，返回缩放后的Bitmap
     *
     * @param stream
     * @param intSampleSize 给定的缩放比例(缩小到原来的1/n)
     * @return
     */
    public static Bitmap getBitmapFromStream(InputStream stream, int intSampleSize) {
        try {
            BitmapFactory.Options m_Options = new BitmapFactory.Options();
            m_Options.inSampleSize = intSampleSize < 1 ? 1 : intSampleSize;
            m_Options.inPurgeable = true;
            Bitmap m_ReturnBitmap = BitmapFactory.decodeStream(stream, null, m_Options);
            stream.close();
            return m_ReturnBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapFromStream is Error !");
        }
        return null;
    }

    /***************************************
     * Exif信息开始
     ************************************************/
    // true :正序 false: 反序
    private static boolean isBigEndian = false;

    // set Big Endian 49 49 4D 4D
    private static void setEndian(int a, int b) {
        int sss1 = Integer.parseInt("49", 16);
        int sss2 = Integer.parseInt("4D", 16);
        if (a == sss1 && a == b) {
            isBigEndian = false;
        }
        if (b == sss2 && a == b) {
            isBigEndian = true;
        }
    }

    /**
     * 查找Exif Base Address 45 78 69 66
     *
     * @param header
     * @return int
     */
    private static int getBaseAddress(byte[] header) {
        int baseAddress = -1;
        int exifAddress = -1;
        if (header != null) {
            int length = header.length;
            int tmpIndex;
            for (int i = 0; i < length; i++) {
                int sss1 = Integer.parseInt("45", 16);
                int sss2 = Integer.parseInt("78", 16);
                int sss3 = Integer.parseInt("69", 16);
                int sss4 = Integer.parseInt("66", 16);
                if (header[i] == sss1) {
                    tmpIndex = i;
                    if (header[++tmpIndex] == sss2) {
                        if (header[++tmpIndex] == sss3) {
                            if (header[++tmpIndex] == sss4) {
                                exifAddress = i;
                                baseAddress = exifAddress + 6;
                                break;
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return baseAddress;
    }

    /**
     * Find ExifPoint Address 87 69 00 04 00 00 00 01
     *
     * @param header
     * @param exifAddress : Exif Base Address
     * @return
     */
    private static int findExifPoint(byte[] header, int exifAddress) {
        int exifPointAddress = -1;
        final int sss1 = Integer.parseInt("87", 16);
        final int sss2 = Integer.parseInt("69", 16);
        final int sss3 = Integer.parseInt("04", 16);
        final int sss4 = Integer.parseInt("01", 16);

        int length = header.length;
        length -= exifAddress;

        int tmp1 = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int tmp4 = 0;
        int tmp5 = 0;
        int tmp6 = 0;
        int tmp7 = 0;
        int tmp8 = 0;

        if (isBigEndian) {
            for (int i = exifAddress; i < length; i++) {
                if (i + 8 > length) {
                    return -1;
                }
                tmp1 = header[i];
                tmp2 = header[i + 1];
                tmp3 = header[i + 2];
                tmp4 = header[i + 3];
                tmp5 = header[i + 4];
                tmp6 = header[i + 5];
                tmp7 = header[i + 6];
                tmp8 = header[i + 7];

                tmp1 = handleInt(tmp1);
                tmp2 = handleInt(tmp2);
                tmp3 = handleInt(tmp3);
                tmp4 = handleInt(tmp4);
                tmp5 = handleInt(tmp5);
                tmp6 = handleInt(tmp6);
                tmp7 = handleInt(tmp7);
                tmp8 = handleInt(tmp8);

                if (tmp1 == sss1 && tmp2 == sss2) {
                    if (tmp3 == sss3 && tmp4 == 0 && tmp5 == 0 && tmp6 == 0 && tmp7 == 0 && tmp8 == sss4) {
                        return i;
                    } else {
                        continue;
                    }
                }
            }
        } else {
            for (int i = exifAddress; i < length; i++) {
                if (i + 8 > length) {
                    return -1;
                }
                tmp1 = header[i];
                tmp2 = header[i + 1];
                tmp3 = header[i + 2];
                tmp4 = header[i + 3];
                tmp5 = header[i + 4];
                tmp6 = header[i + 5];
                tmp7 = header[i + 6];
                tmp8 = header[i + 7];

                tmp1 = handleInt(tmp1);
                tmp2 = handleInt(tmp2);
                tmp3 = handleInt(tmp3);
                tmp4 = handleInt(tmp4);
                tmp5 = handleInt(tmp5);
                tmp6 = handleInt(tmp6);
                tmp7 = handleInt(tmp7);
                tmp8 = handleInt(tmp8);

                if (tmp1 == sss2 && tmp2 == sss1) {
                    if (tmp3 == sss3 && tmp4 == 0 && tmp5 == sss4 && tmp6 == 0 && tmp7 == 0 && tmp8 == 0) {
                        return i;
                    } else {
                        continue;
                    }
                }
            }
        }
        return exifPointAddress;
    }

    private static int handleInt(int num) {
        if (num < 0) {
            num += 256;
        }
        return num;
    }

    /**
     * 獲取Exif的Tag的地址
     *
     * @param header
     * @param exifPoint
     * @return
     */
    private static int getExifTagAddress(byte[] header, int exifPoint) {
        byte[] exifOffset = new byte[4];
        System.arraycopy(header, exifPoint + 8, exifOffset, 0, 4);
        int exifTagStartAddress = -1;
        int tmp1 = handleInt(exifOffset[0]);
        int tmp2 = handleInt(exifOffset[1]);
        int tmp3 = handleInt(exifOffset[2]);
        int tmp4 = handleInt(exifOffset[3]);
        if (isBigEndian) {
            tmp3 = tmp3 << 8;
            tmp2 = tmp2 << 8;
            tmp1 = tmp1 << 8;
            exifTagStartAddress = tmp4 + tmp3 + tmp2 + tmp1;
        } else {
            int x1 = tmp2 << 8;
            int x2 = tmp3 << 16;
            int x3 = tmp4 << 24;
            exifTagStartAddress = tmp1 + x1 + x2 + x3;
        }

        return exifTagStartAddress;
    }

    /**
     * 获取Exif Item count
     *
     * @param header
     * @param exifTagAddress
     * @return
     */
    private static int getExifItemCount(byte[] header, int exifTagAddress) {
        int tmp1 = header[exifTagAddress];
        int tmp2 = header[exifTagAddress + 1];
        int count = -1;
        if (isBigEndian) {
            count = tmp1 << 8 + tmp2;
        } else {
            int x = tmp2 << 8;
            count = x + tmp1;
        }
        count = handleInt(count);
        return count;
    }

    /**
     * @param buffer
     * @param tagLeft
     * @param tagRight
     * @param tagCount
     * @param exifBaseAddress
     * @return
     */
    private static int findExifValueAddress(byte[] buffer, String tagLeft, String tagRight, int tagCount, int exifBaseAddress) {

        int tmpTagLeft = Integer.parseInt(tagLeft, 16);
        int tmpTagRight = Integer.parseInt(tagRight, 16);
        if (isBigEndian) {
            for (int i = 0; i < tagCount; i++) {
                int tmpIndex = i * 12;
                int tmp3 = buffer[tmpIndex];
                int tmp4 = buffer[tmpIndex + 1];
                tmp3 = handleInt(tmp3);
                tmp4 = handleInt(tmp4);

                if (tmp3 == tmpTagLeft && tmp4 == tmpTagRight) {
                    int type = buffer[tmpIndex + 3];
                    if (type == 3) {
                        return tmpIndex + 8;
                    } else if (type == 4) {
                        int offset1 = buffer[tmpIndex + 8];
                        int offset2 = buffer[tmpIndex + 9];
                        int offset3 = buffer[tmpIndex + 10];
                        int offset4 = buffer[tmpIndex + 11];

                        offset1 = handleInt(offset1);
                        offset2 = handleInt(offset2);
                        offset3 = handleInt(offset3);
                        offset4 = handleInt(offset4);

                        offset1 = offset1 << 24;
                        offset2 = offset2 << 16;
                        offset3 = offset3 << 8;

                        return offset1 + offset2 + offset3 + offset4 + exifBaseAddress;
                    }
                }
            }
        } else {
            for (int i = 0; i < tagCount; i++) {
                int tmpIndex = i * 12;
                int tmp3 = buffer[tmpIndex];
                int tmp4 = buffer[tmpIndex + 1];
                tmp3 = handleInt(tmp3);
                tmp4 = handleInt(tmp4);
                if (tmp3 == tmpTagRight && tmp4 == tmpTagLeft) {
                    int type = buffer[tmpIndex + 2];
                    // short
                    if (type == 3) {
                        return tmpIndex + 8;
                    } else if (type == 4) {
                        // long
                        int offset1 = buffer[tmpIndex + 8];
                        int offset2 = buffer[tmpIndex + 9];
                        int offset3 = buffer[tmpIndex + 10];
                        int offset4 = buffer[tmpIndex + 11];

                        offset1 = handleInt(offset1);
                        offset2 = handleInt(offset2);
                        offset3 = handleInt(offset3);
                        offset4 = handleInt(offset4);

                        offset2 = offset2 << 8;
                        offset3 = offset3 << 16;
                        offset4 = offset4 << 24;
                        return offset1 + offset2 + offset3 + offset4 + exifBaseAddress;
                    }

                }
            }
        }
        return -1;
    }

    /**
     * set image width or height
     *
     * @param header
     * @param value
     */
    private static void setImageValue(byte[] header, int start, int value) {

        int l = 0xff & value; // low
        int h = (value >> 8) & 0x00ff; // height
        if (isBigEndian) {
            header[start + 2] = (byte) l;
            header[start + 3] = (byte) h;
        } else {
            header[start] = (byte) l;
            header[start + 1] = (byte) h;
        }
    }

    private static byte[] setWidthAndHeight(byte[] header, int width, int height) {
        int exifBaseAddress = getBaseAddress(header);
        if (exifBaseAddress == -1) {
            return header;
        }
        setEndian(header[exifBaseAddress], header[exifBaseAddress + 1]);
        int exifPoint = findExifPoint(header, exifBaseAddress);
        if (exifPoint == -1) {
            return header;
        }
        int exifTagAddress = getExifTagAddress(header, exifPoint);
        if (exifTagAddress == -1) {
            return header;
        }
        int exifItemCount = getExifItemCount(header, exifTagAddress + exifBaseAddress);
        if (exifItemCount == -1) {
            return header;
        }

        // Exif Tag byte count
        int ExifTagByteCount = exifItemCount * 12;

        byte[] exifTag = new byte[ExifTagByteCount];
        System.arraycopy(header, exifBaseAddress + exifTagAddress + 2, exifTag, 0, ExifTagByteCount);

        int imageWidthTagStart = findExifValueAddress(exifTag, "A0", "02", exifItemCount, exifBaseAddress);
        int imageHeightTagStart = findExifValueAddress(exifTag, "A0", "03", exifItemCount, exifBaseAddress);

        imageWidthTagStart = exifBaseAddress + exifTagAddress + 2 + imageWidthTagStart;
        imageHeightTagStart = exifTagAddress + exifBaseAddress + 2 + imageHeightTagStart;
        if (imageWidthTagStart > -1) {
            setImageValue(header, imageWidthTagStart, width);
        }

        if (imageHeightTagStart > -1) {
            setImageValue(header, imageHeightTagStart, height);
        }
        return header;
    }

    /**
     * 用来计算Exif信息的结束位置
     *
     * @param b 文件头中的前6位标志信息
     * @return 返回Exif信息结束的位置
     */
    private static int checkFFDB(byte[] b) {
        int highByte = b[4];
        int lowByte = b[5];

        if (highByte < 0) {
            highByte += 256;
        }
        highByte = (highByte << 8);
        if (lowByte < 0) {
            lowByte += 256;
        }
        return (highByte + lowByte + 4);
    }

    /**
     * 写入正确的Exif信息到图片文件
     *
     * @param strOldFile    带有原始Exif信息的图片路径
     * @param strTargetFile 处理后不带Exif信息的图片路径
     * @param strNewFile    带有正确Exif信息的图片路径
     * @return
     */
    public static boolean setExifInfo(String strOldFile, String strTargetFile, String strNewFile) {
        try {
            int intNewWidth = 0;
            int intNewHeight = 0;
            // 获取给定路径的图片尺寸大小
            BitmapFactory.Options m_Options = new BitmapFactory.Options();
            m_Options.inJustDecodeBounds = true;// 确保图片不加载到内存
            BitmapFactory.decodeFile(strTargetFile, m_Options);// 获取给定路径的图片尺寸大小
            intNewWidth = m_Options.outWidth;
            intNewHeight = m_Options.outHeight;
            m_Options.inJustDecodeBounds = false;
            m_Options = null;

            // 原文件 头读取
            InputStream inputStreamOldFile = new BufferedInputStream(new FileInputStream(strOldFile));
            // 文件图片信息读取
            InputStream inputStreamTargetFile = new BufferedInputStream(new FileInputStream(strTargetFile));
            // 最终保存图片文件
            FileOutputStream fileOutputStreamNewFile = new FileOutputStream(strNewFile);

            final int length = 1024;
            byte buffer[] = new byte[length];
            inputStreamOldFile.read(buffer, 0, 6);
            int headerLength = checkFFDB(buffer);
            int haveReaderCount = 0;
            int ret;
            byte header[] = new byte[headerLength];
            System.arraycopy(buffer, 0, header, 0, 6);
            headerLength -= 6;
            while (haveReaderCount < headerLength) {
                if (headerLength - haveReaderCount < length) {
                    ret = inputStreamOldFile.read(header, haveReaderCount + 6, headerLength - haveReaderCount);
                } else {
                    ret = inputStreamOldFile.read(header, haveReaderCount + 6, length);
                }
                haveReaderCount += ret;
            }

            Log.i("TAG", "Ready setWidthAndHeight ");

            setWidthAndHeight(header, intNewWidth, intNewHeight);

            fileOutputStreamNewFile.write(header, 0, header.length);

            ret = -1;
            inputStreamTargetFile.read(buffer, 0, 6);
            ret = checkFFDB(buffer);
            inputStreamTargetFile.skip(ret - 6);
            while (true) {
                ret = inputStreamTargetFile.read(buffer);
                if (ret > -1) {
                    fileOutputStreamNewFile.write(buffer, 0, ret);
                } else {
                    break;
                }
            }

            fileOutputStreamNewFile.flush();
            inputStreamOldFile.close();
            inputStreamTargetFile.close();
            fileOutputStreamNewFile.close();
            inputStreamOldFile = null;
            inputStreamTargetFile = null;
            fileOutputStreamNewFile = null;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /********************************************************* Exif结束 ************************************************/

    /**
     * 释放Bitmap所占的系统资源
     *
     * @param bitmap
     */
    public static void setBitmapRecycled(Bitmap bitmap) {
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * 将字节流转成字符串
     *
     * @param is 二进制字节流
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        try {
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                is.close();
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 连接到网络（ 抽取的公共方法）
     *
     * @param urlStr 文件所在的网络地址
     * @return InputStream
     */
    public static InputStream getInputStreamFromUrl(String urlStr) {
        InputStream inputStream = null;
        try {
            // 创建一个URL对象
            URL url = new URL(urlStr);
            // 根据URL对象创建一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // IO流读取数据
            inputStream = urlConn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 取得文件夹下的(原图)路径 传入目录名称、目录路径
     */
    public static List<Map<String, Object>> getPhotosFromFolder(Context context, String rootPath) {
        List<Map<String, Object>> listPhotoInfo = new ArrayList<Map<String, Object>>();
        Map<String, Object> mItem = null;
        try {
            // 获取系通图片管理的数据库信息
            ContentResolver mContentResolver = context.getContentResolver();

            String[] projection = {BaseColumns._ID, MediaColumns.DATA, MediaColumns.DISPLAY_NAME, ImageColumns.LATITUDE, ImageColumns.LONGITUDE, MediaColumns.DATE_ADDED, MediaColumns.DATE_MODIFIED};

            // String strSelection = Media._ID +
            // " in (select image_id from thumbnails) "
            String strSelection = BaseColumns._ID + "!='' " + " and " + MediaColumns.SIZE + " >= 1024" + " and " + MediaColumns.TITLE + " not in ('icon')  ";
            if (!TextUtils.isEmpty(rootPath)) {
                strSelection += " and " + MediaColumns.DATA + " like '" + rootPath + "%' ";
            }
            String strOrderBy = MediaColumns.DATE_ADDED + " desc ";
            Cursor cur = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, projection, strSelection, null, strOrderBy);
            if (cur.moveToFirst()) {
                String photo_id = "";
                String photo_path = "";
                String photo_name = "";
                String photo_lat = "0";
                String photo_lon = "0";
                String photo_add_date = "" + System.currentTimeMillis();
                String photo_edit_date = "" + System.currentTimeMillis();
                do {
                    photo_id = cur.getString(0) != null ? cur.getString(0) : "";
                    photo_path = cur.getString(1) != null ? cur.getString(1) : "";
                    photo_name = cur.getString(2) != null ? cur.getString(2) : "";
                    photo_lat = cur.getString(3) != null ? cur.getString(3) : "0";
                    photo_lon = cur.getString(4) != null ? cur.getString(4) : "0";
                    photo_add_date = cur.getString(5) != null ? cur.getString(5) : "" + System.currentTimeMillis();
                    photo_edit_date = cur.getString(6) != null ? cur.getString(6) : "" + System.currentTimeMillis();
                    if (!photo_path.equals("")) {
                        mItem = new HashMap<String, Object>();
                        mItem.put("photo_id", photo_id);
                        mItem.put("photo_path", photo_path);
                        mItem.put("photo_name", photo_name);
                        mItem.put("photo_lat", photo_lat);
                        mItem.put("photo_lon", photo_lon);
                        mItem.put("photo_add_date", photo_add_date);
                        mItem.put("photo_edit_date", photo_edit_date);
                        mItem.put("photo_upload", "0");
                        listPhotoInfo.add(mItem);
                        // Log.i(TAG, "getPhotosFromFolder !rootPath = " +
                        // rootPath + ",photo_path = " + photo_path);
                    } else {
                        continue;
                    }
                } while (cur.moveToNext());
                Log.i(TAG, "getPhotosFromFolder 扫描结束!PhotosCount = " + listPhotoInfo.size());
                // if (mStoneBaseSQLite != null) {
                // mStoneBaseSQLite.insertUploadPhotos(listPhotoInfo);
                // }
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPhotoInfo;
    }

    /**
     * 通过图片路径获取在本地数据库的photo_id
     */
    public static List<Map<String, Object>> getPhotoInfoByPhotoPath(Context context, String strPhotoPath) {
        List<Map<String, Object>> listPhotoInfo = new ArrayList<Map<String, Object>>();
        Map<String, Object> mItem = null;
        try {
            // 获取系通图片管理的数据库信息
            ContentResolver mContentResolver = context.getContentResolver();

            String[] projection = {BaseColumns._ID, MediaColumns.DATA, MediaColumns.DISPLAY_NAME, ImageColumns.LATITUDE, ImageColumns.LONGITUDE, MediaColumns.DATE_ADDED, MediaColumns.DATE_MODIFIED};

            // String strSelection = Media._ID +
            // " in (select image_id from thumbnails) "
            String strSelection = BaseColumns._ID + "!='' " + " and " + MediaColumns.SIZE + " >= 1024" + " and " + MediaColumns.TITLE + " not in ('icon')  ";
            if (!TextUtils.isEmpty(strPhotoPath)) {
                strSelection += " and " + MediaColumns.DATA + " like '%" + strPhotoPath + "' ";
            }
            String strOrderBy = MediaColumns.DATE_ADDED + " desc ";
            Cursor cur = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, projection, strSelection, null, strOrderBy);
            if (cur.moveToFirst()) {
                String photo_id = "";
                String photo_path = "";
                String photo_name = "";
                String photo_lat = "0";
                String photo_lon = "0";
                String photo_add_date = "" + System.currentTimeMillis();
                String photo_edit_date = "" + System.currentTimeMillis();
                do {
                    photo_id = cur.getString(0) != null ? cur.getString(0) : "";
                    photo_path = cur.getString(1) != null ? cur.getString(1) : "";
                    photo_name = cur.getString(2) != null ? cur.getString(2) : "";
                    photo_lat = cur.getString(3) != null ? cur.getString(3) : "0";
                    photo_lon = cur.getString(4) != null ? cur.getString(4) : "0";
                    photo_add_date = cur.getString(5) != null ? cur.getString(5) : "" + System.currentTimeMillis();
                    photo_edit_date = cur.getString(6) != null ? cur.getString(6) : "" + System.currentTimeMillis();
                    if (!photo_path.equals("")) {
                        mItem = new HashMap<String, Object>();
                        mItem.put("photo_id", photo_id);
                        mItem.put("photo_path", photo_path);
                        mItem.put("photo_name", photo_name);
                        mItem.put("photo_lat", photo_lat);
                        mItem.put("photo_lon", photo_lon);
                        mItem.put("photo_add_date", photo_add_date);
                        mItem.put("photo_edit_date", photo_edit_date);
                        mItem.put("photo_upload", "0");
                        listPhotoInfo.add(mItem);
                        // Log.i(TAG, "getPhotosFromFolder !rootPath = " +
                        // rootPath + ",photo_path = " + photo_path);
                    } else {
                        continue;
                    }
                } while (cur.moveToNext());
                Log.i(TAG, "getPhotosFromFolder 扫描结束!PhotosCount = " + listPhotoInfo.size());
                // if (mStoneBaseSQLite != null) {
                // mStoneBaseSQLite.insertUploadPhotos(listPhotoInfo);
                // }
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPhotoInfo;
    }

    /**
     * 通过图片路径获取在本地数据库的photo_id
     */
    public static String getPhotoIDByPhotoPath(Context context, String strPhotoPath) {
        String localPhoto_id = "-1";
        try {
            // 获取系通图片管理的数据库信息
            ContentResolver mContentResolver = context.getContentResolver();

            String[] projection = {BaseColumns._ID, MediaColumns.DATA, MediaColumns.DISPLAY_NAME, ImageColumns.LATITUDE, ImageColumns.LONGITUDE, MediaColumns.DATE_ADDED, MediaColumns.DATE_MODIFIED};

            // String strSelection = Media._ID +
            // " in (select image_id from thumbnails) "
            String strSelection = BaseColumns._ID + "!='' " + " and " + MediaColumns.SIZE + " >= 1024" + " and " + MediaColumns.TITLE + " not in ('icon')  ";
            if (!TextUtils.isEmpty(strPhotoPath)) {
                strSelection += " and " + MediaColumns.DATA + " like '%" + strPhotoPath + "' ";
            }
            String strOrderBy = MediaColumns.DATA + " asc ";
            Cursor cur = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, projection, strSelection, null, strOrderBy);
            if (cur.moveToFirst()) {
                do {
                    localPhoto_id = cur.getString(0) != null ? cur.getString(0) : "-1";
                    break;
                } while (cur.moveToNext());
            }
            if (cur != null) {
                cur.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localPhoto_id;
    }

    /**
     * 获取视频缩略图(1)通过内容提供器来获取,缺点就是必须更新媒体库才能看到最新的视频的缩略图
     *
     * @param context
     * @param Videopath
     * @return
     */
    public static Bitmap getVideoThumbnail(Context context, String Videopath) {
        ContentResolver testcr = context.getContentResolver();
        String[] projection = {MediaColumns.DATA, BaseColumns._ID,};
        String whereClause = MediaColumns.DATA + " = '" + Videopath + "'";
        Cursor cursor = testcr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, whereClause, null, null);
        int _id = 0;
        String videoPath = "";
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        if (cursor.moveToFirst()) {
            int _idColumn = cursor.getColumnIndex(BaseColumns._ID);
            int _dataColumn = cursor.getColumnIndex(MediaColumns.DATA);
            do {
                _id = cursor.getInt(_idColumn);
                videoPath = cursor.getString(_dataColumn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.i(TAG, "getVideoThumbnail videoPath = " + videoPath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Config.RGB_565;
        Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(testcr, _id, Images.Thumbnails.MINI_KIND, options);
        return bitmap;
    }

    /**
     * 获取视频缩略图(2)人为创建缩略图要耗费一点时间
     *
     * @param videoPath
     * @param width
     * @param height
     * @param kind
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 根据图片路径获取缩略图
     *
     * @param context
     * @param Imagepath
     * @return
     */
    public static Bitmap getImageThumbnail(Context context, String Imagepath) {
        ContentResolver testcr = context.getContentResolver();
        String[] projection = {MediaColumns.DATA, BaseColumns._ID,};
        String whereClause = MediaColumns.DATA + " = '" + Imagepath + "'";
        Cursor cursor = testcr.query(Media.EXTERNAL_CONTENT_URI, projection, whereClause, null, null);
        int _id = 0;
        String imagePath = "";
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        if (cursor.moveToFirst()) {

            int _idColumn = cursor.getColumnIndex(BaseColumns._ID);
            int _dataColumn = cursor.getColumnIndex(MediaColumns.DATA);

            do {
                _id = cursor.getInt(_idColumn);
                imagePath = cursor.getString(_dataColumn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Config.RGB_565;
        Bitmap bitmap = Images.Thumbnails.getThumbnail(testcr, _id, Images.Thumbnails.MINI_KIND, options);
        return bitmap;
    }

    /**
     * 通过路径、不需要将图片加载到内存中获取图片的尺寸信息，
     *
     * @param strPhotoPath 图片路径
     * @return 返回值Point.x＝图片宽度，Point.y=图片高度
     */
    public static Point getImageSize(String strPhotoPath) {
        Point imageSize = new Point();
        try {
            // 获取给定路径的图片尺寸大小
            BitmapFactory.Options m_Options = new BitmapFactory.Options();
            m_Options.inJustDecodeBounds = true;// 确保图片不加载到内存
            BitmapFactory.decodeFile(strPhotoPath, m_Options);// 获取给定路径的图片尺寸大小
            // 计算图片缩放比例
            imageSize.x = m_Options.outWidth;
            imageSize.y = m_Options.outHeight;
            m_Options.inJustDecodeBounds = false;
            m_Options = null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getImageSize is Error! ErrorCode = " + e.getMessage());
        }
        return imageSize;
    }

    /**
     * 通过路径、宽高限制。返回图片的最合适的缩小倍数
     *
     * @param strPhotoPath 图片路径
     * @param intMaxHeight 给定的宽度
     * @param intMaxWidth  给定的高度
     * @return 返回值>1图片大于设定小值
     */
    public static float getBitmapOptionsSize(String strPhotoPath, int intMaxWidth, int intMaxHeight) {
        float inSampleSize = 1.0f;
        try {
            // 获取给定路径的图片尺寸大小
            BitmapFactory.Options m_Options = new BitmapFactory.Options();
            m_Options.inJustDecodeBounds = true;// 确保图片不加载到内存
            BitmapFactory.decodeFile(strPhotoPath, m_Options);// 获取给定路径的图片尺寸大小
            // 计算图片缩放比例
            final int maxDefault = Math.max(intMaxWidth, intMaxHeight);
            final int maxOutput = Math.max(m_Options.outWidth, m_Options.outHeight);
            if (maxOutput > maxDefault) {
                inSampleSize = (float) maxOutput / (float) maxDefault;
            }
            m_Options.inJustDecodeBounds = false;
            m_Options = null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapMaxSize is Error! ErrorCode = " + e.getMessage());
        }
        return inSampleSize;
    }

    /**
     * 通过路径获取图片的体积大小、返回(KB)
     */
    public static int getBitmapMemerySize(String strURL) {
        try {
            int ReturnSize = 0;
            InputStream stream = new FileInputStream(new File(strURL));
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
            byte[] imagesize = BitmapToBytes(bitmap);
            ReturnSize = imagesize.length / 1024;

            return ReturnSize;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapMemerySize(String strURL) is Error! ErrorCode = " + e.getMessage());
            return 0;
        }
    }

    /**
     * 通过路径和缩放系数获取图片的体积大小、返回(KB)
     */
    public static int getBitmapMemerySize(String strURL, int intSize) {
        try {
            BitmapFactory.Options m_Options = new BitmapFactory.Options();
            m_Options.inSampleSize = intSize;
            int ReturnSize = 0;
            InputStream stream = new FileInputStream(new File(strURL));
            Bitmap bitmap = BitmapFactory.decodeStream(stream, null, m_Options);
            stream.close();
            byte[] imagesize = BitmapToBytes(bitmap);
            ReturnSize = imagesize.length / 1024;

            return ReturnSize;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapMemerySize(String strURL,int intSize) is Error! ErrorCode = " + e.getMessage());
            return 0;
        }
    }

    /**
     * 通过Bitmap对象获取图片的体积大小、返回(KB)
     */
    public static int getBitmapMemerySize(Bitmap bitmap) {
        try {
            int ReturnSize = 0;
            byte[] imagesize = BitmapToBytes(bitmap);
            ReturnSize = imagesize.length / 1024;

            return ReturnSize;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapMemerySize(Bitmap bitmap) is Error! ErrorCode = " + e.getMessage());
            return 0;
        }
    }

    /**
     * 通过 Drawable对象获取图片的体积大小、返回(KB)
     */
    public static int getBitmapMemerySize(Drawable db) {
        try {
            int ReturnSize = 0;
            Bitmap bitmap = getBitmapByDrawable(db);
            byte[] imagesize = BitmapToBytes(bitmap);
            ReturnSize = imagesize.length / 1024;

            return ReturnSize;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapMemerySize(Drawable db) is Error! ErrorCode = " + e.getMessage());
            return 0;
        }
    }

    /**
     * 图片转化Bitmap → Drawable
     */
    public static Drawable getDrawableByBitmap(Bitmap bm) {
        try {
            if (bm == null) {
                return null;
            } else {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
                return bitmapDrawable;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getDrawableByBitmap is Error! ErrorCode = " + e.getMessage());
            return null;
        }
    }

    /**
     * 图片转化Drawable → Bitmap
     */
    public static Bitmap getBitmapByDrawable(Drawable drawableIn) {
        try {
            if (drawableIn == null) {
                return null;
            } else {
                Bitmap m_ReturnBitmap = Bitmap.createBitmap(drawableIn.getIntrinsicWidth(), drawableIn.getIntrinsicHeight(), drawableIn.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
                Canvas canvas = new Canvas(m_ReturnBitmap);
                // canvas.setBitmap(bitmap);
                drawableIn.setBounds(0, 0, drawableIn.getIntrinsicWidth(), drawableIn.getIntrinsicHeight());
                drawableIn.draw(canvas);
                return m_ReturnBitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getBitmapByDrawable is Error! ErrorCode = " + e.getMessage());
            return null;
        }
    }

    /**
     * 图片转化Bitmap → byte[]
     */
    public static byte[] BitmapToBytes(Bitmap bitmapIn) {
        if (bitmapIn == null) {
            return null;
        } else {
            ByteArrayOutputStream Returnbyte = new ByteArrayOutputStream();
            bitmapIn.compress(CompressFormat.JPEG, UPLOADPHOTO_QUALITY, Returnbyte);
            return Returnbyte.toByteArray();
        }
    }

    /**
     * 图片转化byte[] → Bitmap
     */
    public Bitmap BytesToBimap(byte[] byteIn) {
        if (byteIn.length != 0) {
            return BitmapFactory.decodeByteArray(byteIn, 0, byteIn.length);
        } else {
            return null;
        }
    }

    /**
     * 判断SD卡是否插入
     */
    public static boolean ExistsSDCard() {
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    /**
     * 将给定的网络、本地图片按给定的缩小比例、保存到SD卡的给定位置
     *
     * @param strOldPath
     * @param strNewPath
     * @param intSampleSize 给定的缩放比例(缩小到原来的1/n)
     * @return
     */
    public static Boolean SaveBitmapToSDCardBySampleSize(Context context, String strOldPath, String strNewPath, float intSampleSize) {
        if (strOldPath == null || "".equals(strOldPath)) {
            return false;
        }
        if (strNewPath == null || "".equals(strNewPath)) {
            return false;
        }
        boolean returnResult = false;
        InputStream stream;
        BitmapFactory.Options m_Options = new BitmapFactory.Options();
        m_Options.inSampleSize = (int) (intSampleSize < 1 ? 1 : Math.floor(intSampleSize));
        m_Options.inPurgeable = true;
        try {
            Bitmap m_ReturnBitmap = null;
            if (URLUtil.isHttpUrl(strOldPath)) {
                // 获取网络图片
                URL url = new URL(strOldPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                stream = conn.getInputStream();
                m_ReturnBitmap = BitmapFactory.decodeStream(stream, null, m_Options);
                stream.close();
            } else {
                // 获取本地图片
                File f = new File(strOldPath);
                if (f.exists()) {
                    m_ReturnBitmap = loadBitmap(strOldPath, m_Options, true);
                }
            }
            // 计算图片缩放比例
            float floScale = 1.0f;
            final int maxDefault = Math.max(UPLOADPHOTO_WIDTH_MAX, UPLOADPHOTO_HEIGHT_MAX);
            final int maxOutput = Math.max(m_ReturnBitmap.getWidth(), m_ReturnBitmap.getHeight());
            if (maxOutput > maxDefault) {
                floScale = (float) maxOutput / (float) maxDefault;
            }
            m_ReturnBitmap = getBitmapByZoomScale(m_ReturnBitmap, 1.0f / floScale);
            returnResult = SaveBitmapToSD(context, m_ReturnBitmap, strNewPath);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "SaveBitmapToSDCardBySampleSize is Error ! strOldPath = " + strOldPath);
        }
        return returnResult;
    }

    /**
     * * 处理图片大小,保存图片到SD卡
     *
     * @param strOldPath   原始路径
     * @param strNewPath   新的路径
     * @param intMaxWidth  设定的最大宽度
     * @param intMaxHeight 设定的最大高度
     * @return true 成功 false 失败
     */
    public static Boolean SaveBitmapToSDCardByMaxSize(Context context, String strOldPath, String strNewPath, int intMaxWidth, int intMaxHeight) {
        if (strOldPath == null || "".equals(strOldPath)) {
            return false;
        }
        if (strNewPath == null || "".equals(strNewPath)) {
            return false;
        }
        boolean returnResult = false;
        if (ExistsSDCard() == true) {
            try {
                // 获取给定路径的图片尺寸大小
                BitmapFactory.Options m_Options = new BitmapFactory.Options();
                m_Options.inJustDecodeBounds = true;// 确保图片不加载到内存
                BitmapFactory.decodeFile(strOldPath, m_Options);// 获取给定路径的图片尺寸大小
                // 计算图片缩放比例
                final int maxDefault = Math.max(intMaxWidth, intMaxHeight);
                final int maxOutput = Math.max(m_Options.outWidth, m_Options.outHeight);
                float inSampleSize = 1.0f;
                if (maxOutput > maxDefault) {
                    inSampleSize = (float) maxOutput / (float) maxDefault;
                }
                m_Options.inJustDecodeBounds = false;
                m_Options.inInputShareable = true;
                m_Options.inPurgeable = true;
                Bitmap m_ReturnBitmap = null;
                if (inSampleSize > 1) {
                    m_Options.inSampleSize = Math.round(inSampleSize);
                    InputStream stream = new FileInputStream(new File(strOldPath));
                    m_ReturnBitmap = BitmapFactory.decodeStream(stream, null, m_Options);
                    stream.close();
                    m_ReturnBitmap = getBitmapZoom(m_ReturnBitmap, intMaxWidth, intMaxHeight);
                } else {
                    m_Options.inSampleSize = 1;
                    InputStream stream = new FileInputStream(new File(strOldPath));
                    m_ReturnBitmap = BitmapFactory.decodeStream(stream, null, m_Options);
                    stream.close();
                }
                // 保存图片信息
                returnResult = SaveBitmapToSD(context, m_ReturnBitmap, strNewPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnResult;
    }

    /**
     * 保存图片到SD卡的给定位置
     */
    public static Boolean SaveBitmapToSD(Context context, Bitmap saveBitmap, String strSavePath) {
        boolean Result = false;
        try {
            if (ExistsSDCard() == true && saveBitmap != null) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                saveBitmap.compress(CompressFormat.JPEG, UPLOADPHOTO_QUALITY, out);
                byte[] array = out.toByteArray();
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(strSavePath);
                    outStream.write(array);
                    outStream.close();
                    setBitmapRecycled(saveBitmap);
                    Result = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            scanRefreshMediaFileDataBase(context, strSavePath);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "SaveBitmapToSD is Error ! strWebURL = " + strSavePath);
        }
        return Result;
    }

    /**
     * 保存图片到SD卡的给定位置
     */
    public static Boolean SaveBitmapToSD(Context context, byte[] saveByte, String strSavePath) {
        boolean Result = false;
        if (ExistsSDCard() == true) {
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(strSavePath);
                outStream.write(saveByte);
                outStream.close();
                saveByte = null;
                Result = true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        scanRefreshMediaFileDataBase(context, strSavePath);
        return Result;
    }

    /**
     * 删除SD卡中给定位置的文件
     */
    public static Boolean DeleteFileFromSD(Context context, String strURL) {
        if (strURL == null || "".equals(strURL)) {
            return true;
        }
        File myFile = new File(strURL);
        boolean Result = false;
        if (ExistsSDCard() == true && myFile.exists()) {
            // 删除文件夹
            Result = myFile.delete();
        }
        scanRefreshMediaFileDataBase(context, strURL);
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
        if (ExistsSDCard() == true) {
            Result = myFile.exists(); // 判断文件是否存在
        }
        return Result;
    }

    /**
     * 获取图片真实类型png，gif，bmp，jpeg，jpg
     *
     * @param strPhotoPath
     * @return png，gif，bmp，jpeg，jpg
     */
    public static String checkImageType(String strPhotoPath) {
        String strImageType = FileUtils.getFileExtension(strPhotoPath);
        try {
            // 获取给定路径的图片尺寸大小
            BitmapFactory.Options m_Options = new BitmapFactory.Options();
            m_Options.inJustDecodeBounds = true;// 确保图片不加载到内存
            BitmapFactory.decodeFile(strPhotoPath, m_Options);// 获取给定路径的图片尺寸大小
            // 获取图片真实类型png，gif，bmp，jpeg，jpg
            strImageType = m_Options.outMimeType;
            String[] aa = strImageType.split("/");
            strImageType = aa[1];
            m_Options.inJustDecodeBounds = false;
            m_Options = null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "checkImageType is Error! ErrorCode = " + e.getMessage());
        }
        return strImageType;
    }

    /**
     * 图片按给定比例大小精确压缩处理后的路径
     *
     * @param strPhotoPath
     * @return
     * @see #UPLOADPHOTO_WIDTH_MAX
     * @see #UPLOADPHOTO_HEIGHT_MAX
     */
    public static String compressUploadPhoto(Context context, String strPhotoPath, String saveImageDir) {
        long mStartTime = System.currentTimeMillis();
        String strPhotoPathOutput = strPhotoPath;
        if (ImageUtil.checkFileExists(strPhotoPath)) {
            // 检查上传图片是否满足设定的大小限制
            float inSampleSize = ImageUtil.getBitmapOptionsSize(strPhotoPath, UPLOADPHOTO_WIDTH_MAX, UPLOADPHOTO_HEIGHT_MAX);
            if (inSampleSize > 1.0f) {
                // 如果是png图片缩放前先要转成jpg否则不能正式显示
                String strTempPicName = saveImageDir + "/" + TEMP_FILE_NAME + System.currentTimeMillis() + ".jpg";
                if (checkImageType(strPhotoPath).equalsIgnoreCase("png")) {
                    if (ImageUtil.SaveBitmapToSDCardBySampleSize(context, strPhotoPath, strTempPicName, 1)){
                        strPhotoPathOutput = strTempPicName;
                    }
//                    if (strPhotoPath.contains(TEMP_FILE_NAME)) {
//                        FileUtils.deleteFile(strPhotoPath);
//                    }
                } else {
                    if (ImageUtil.SaveBitmapToSDCardBySampleSize(context, strPhotoPath, strTempPicName, inSampleSize)) {
                        strPhotoPathOutput = strTempPicName;
                    } /*else {
                        FileUtils.deleteFile(strPhotoPath);
                    }*/
                }
            }
        }
        long mEndTime = System.currentTimeMillis();
        Log.i(TAG, "getUploadPhotoPathCompress Use Times = " + String.valueOf((mEndTime - mStartTime)));
        return strPhotoPathOutput;
    }

    /**
     * 图片按给定大小通过质量压缩方法处理
     *
     * @param bitmapInput
     * @return
     * @see #UPLOADPHOTO_SIZE_MAX
     */
    public static Bitmap compressPhoto(Bitmap bitmapInput) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapInput.compress(CompressFormat.JPEG, UPLOADPHOTO_QUALITY, byteArrayOutputStream);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = UPLOADPHOTO_QUALITY;
        while (byteArrayOutputStream.toByteArray().length / 1024 > UPLOADPHOTO_SIZE_MAX) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
            byteArrayOutputStream.reset();// 重置byteArrayOutputStream即清空byteArrayOutputStream
            bitmapInput.compress(CompressFormat.JPEG, options, byteArrayOutputStream);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param photoPath 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPhotoDegree(String photoPath) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(photoPath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /************************************************** 主要是图片处理操作的函数(END) **************************************************/

    // TODO: 图片特效处理

    /**
     * 毛玻璃效果的实现
     *
     * @param context
     * @param sentBitmap 待处理图片
     * @param radius     模糊深度 1～255之间
     * @return
     */
    public static Bitmap FastBlurImage(Context context, Bitmap sentBitmap, int radius) {
        if (radius < 1) {
            radius = 1;
        }
        if (radius > 255) {
            radius = 255;
        }
        if (VERSION.SDK_INT > 16) {
            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

            final RenderScript rs = RenderScript.create(context);
            final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            if (VERSION.SDK_INT > 15) {
                final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                script.setRadius(radius /* e.g. 3.f */);
                script.setInput(input);
                script.forEach(output);
                output.copyTo(bitmap);
            }
            return bitmap;
        }

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        // Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int temp = 256 * divsum;
        int dv[] = new int[temp];
        for (i = 0; i < temp; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    /**
     * 1.直接在图片上写文字
     *
     * @param photo
     * @param strMark
     * @return
     */
    public static Bitmap addBitmapMarkString(Bitmap photo, String strMark) {
        int width = photo.getWidth(), hight = photo.getHeight();
        Bitmap icon = Bitmap.createBitmap(width, hight, Config.ARGB_8888); // 建立一个空的BItMap
        Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

        Paint photoPaint = new Paint(); // 建立画笔
        photoPaint.setDither(true); // 获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);// 过滤一些

        Rect src = new Rect(0, 0, photo.getWidth(), photo.getHeight());// 创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
        canvas.drawBitmap(photo, src, dst, photoPaint);// 将photo 缩放或则扩大到
        // dst使用的填充区photoPaint

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
        textPaint.setTextSize(20.0f);// 字体大小
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
        textPaint.setColor(Color.RED);// 采用的颜色
        // textPaint.setShadowLayer(3f, 1,
        // 1,this.getResources().getColor(android.R.color.background_dark));//影音的设置
        canvas.drawText(strMark, 20, 26, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return icon;
    }

    /**
     * 将两个图片合成 <br>
     * 1.Bitmap mark = BitmapFactory.decodeResource(this.getResources(),
     * R.drawable.icon); <br>
     * 2.Bitmap photo = BitmapFactory.decodeResource(this.getResources(),
     * R.drawable.text); <br>
     * 3.Bitmap a = createBitmap(photo,mark); <br>
     * 4.image.setImageBitmap(a); <br>
     * 5.saveMyBitmap(a);
     *
     * @param src
     * @param bitmapMark
     * @return
     */
    public static Bitmap addBitmapMarkBitmap(Bitmap src, Bitmap bitmapMark) {
        if (src == null) {
            return null;
        }

        int w = src.getWidth();
        int h = src.getHeight();
        int ww = bitmapMark.getWidth();
        int wh = bitmapMark.getHeight();

        // create the new blank bitmap

        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        // 创建一个新的和SRC长度宽度一样的位图

        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(bitmapMark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store

        cv.restore();// 存储
        return newb;
    }

    /**
     * 加水印 也可以加文字
     *
     * @param src
     * @param watermark
     * @param title
     * @return
     */
    public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark, String title) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        // 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        Paint paint = new Paint();
        // 加入图片
        if (watermark != null) {
            int ww = watermark.getWidth();
            int wh = watermark.getHeight();
            paint.setAlpha(50);
            // cv.drawBitmap(watermark, 0, 0, paint);// 在src的左上角画入水印
            cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// 在src的右下角画入水印

        }
        // 加入文字
        if (title != null) {
            String familyName = "宋体";
            Typeface font = Typeface.create(familyName, Typeface.NORMAL);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.RED);
            textPaint.setTypeface(font);
            textPaint.setTextSize(40);
            // 这里是自动换行的
            // StaticLayout layout = new
            // StaticLayout(title,textPaint,w,Alignment.ALIGN_OPPOSITE,1.0F,0.0F,true);
            // layout.draw(cv);
            // 文字就加左上角算了
            cv.drawText(title, w - 400, h - 40, textPaint);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        return newBitmap;
    }

    /**
     * 调用系统功能重新扫描指定的文件夹,写入系统媒体数据库
     *
     * @param context
     * @param strFileName
     */
    public static void scanRefreshMediaFileDataBase(Context context, String strFileName) {
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
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 加载图片，是否自动调整方向
     *
     * @param imgPath
     * @param adjustOritation
     * @return
     */
    public static Bitmap loadBitmap(String imgPath, BitmapFactory.Options options, boolean adjustOritation) {
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
        if (adjustOritation) {
            int bitmapDegree = getBitmapDegree(imgPath);
            if (bitmapDegree > 0) {
                return rotateBitmapByDegree(bitmap, bitmapDegree);
            }
        }

        return bitmap;
    }

    public static File compressImage(File imageFile, int reqWidth, int reqHeight, Bitmap.CompressFormat compressFormat, int quality, String destinationPath) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(destinationPath);
            // write the compressed bitmap at the destination specified by destinationPath.
            decodeSampledBitmapFromFile(imageFile, reqWidth, reqHeight).compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        return new File(destinationPath);
    }

    public static Bitmap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    }

    private static int cInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}