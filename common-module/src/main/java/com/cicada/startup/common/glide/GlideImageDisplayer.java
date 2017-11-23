package com.cicada.startup.common.glide;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.module.GlideModule;
import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;
import com.cicada.startup.common.glide.transform.GlideCircleTransform;
import com.cicada.startup.common.glide.transform.GlideRoundTransform;
import com.cicada.startup.common.utils.FileUtils;

import java.io.File;

/**
 * Created by hwp on 16/7/20.
 */
public class GlideImageDisplayer implements GlideModule {

    //混存图片的大小
    private static final int GLIDE_CACHE_SIZE = 1024 * 1024 * 250;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {


        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));

        //默认设置的RGB - 5555 图片质量清晰度存在一定的影响。故采用default 也可以提供质量
        //采用RGB-8888
        builder.setDecodeFormat(DecodeFormat.DEFAULT);

        //设置BitmapPool缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));

        //缓存图片到本地项目中
        if (android.os.Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            builder.setDiskCache(new DiskCache.Factory() {
                @Override
                public DiskCache build() {
                    // Careful: the external cache directory doesn't enforce permissions
                    try {
                        File imgCache = new File(FileUtils.getCacheImgPath(AppContext.getContext()));
                        return DiskLruCacheWrapper.get(imgCache, GLIDE_CACHE_SIZE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            Glide.setup(builder);
        }

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

    /**
     *
     1.Glide.with(context).resumeRequests()和 Glide.with(context).pauseRequests()

     当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，调用resumeRequests()恢复请求。这样是不是会好些呢？

     2.Glide.clear()

     当你想清除掉所有的图片加载请求时，这个方法可以帮助到你。
     */

    /**
     * @param context 当前context上下文
     * @param imgview imgaview 控件
     * @param url     路径地址
     */
    public static void display(Context context, ImageView imgview, String url) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .dontAnimate()
                .centerCrop()
                .into(imgview)
        ;
    }

    public static void display(Context context, ImageView imgview, String url, int placeholder, int error) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(error)
                .placeholder(placeholder)
                .into(imgview);
    }

    public static void display(Context context, ImageView imgview, String url, int placeholder, int width, int height) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(placeholder)
                .override(width, height)
                .placeholder(placeholder)
                .into(imgview);
    }

    /**
     * @param context
     * @param imgview
     * @param url
     */
    public static void display(Context context, ImageView imgview, int url) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(com.cicada.startup.common.R.mipmap.app_icon)
                .placeholder(com.cicada.startup.common.R.mipmap.app_icon)
                .into(imgview);
    }

    /**
     * @param context
     * @param imgview
     * @param url
     */
    public static void displayWithOutPlaceHolder(Context context, ImageView imgview, int url) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(com.cicada.startup.common.R.mipmap.app_icon)
                .into(imgview);
    }

    /**
     * 加载显示img 传入当加载的图片没有加载完成前，显示的默认图片
     *
     * @param imgview
     * @param url
     * @param loadingImg
     */
    public static void display(Context context, ImageView imgview, String url, int loadingImg) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(loadingImg)
                .placeholder(loadingImg)
                .dontAnimate()
                .into(imgview);
    }

    /**
     * 加载显现bitmap转化的图片
     *
     * @param imgview
     * @param url
     * @param transformation
     */
    public static void display(Context context, ImageView imgview, String url, BitmapTransformation transformation) {
        Glide.with(context).load(url)
                .transform(transformation)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgview);
    }

    /**
     * 加载显现bitmap转化的图片
     *
     * @param imgview
     * @param res
     * @param transformation
     */
    public static void display(Context context, ImageView imgview, int res, BitmapTransformation transformation) {
        Glide.with(context).load(res)
                .transform(transformation)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgview);
    }


    /**
     * 加载显现特定图形的转化的图片
     * 圆角，圆形
     *
     * @param imgview
     * @param url
     * @param transformation
     * @param loadingImg
     */
    public static void display(Context context, ImageView imgview, String url, BitmapTransformation transformation, int loadingImg) {
        Glide.with(context).load(url)
                .transform(transformation)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(loadingImg)
                .into(imgview);
    }

    /**
     * 加载网络图片
     */
    public static void displayImage(Context context, ImageView imageView, String url, int resId) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(resId)//设置占位图，在加载之前显示
                .error(resId)//在图像加载失败时显示
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载资源图片
     */
    public static void displayImage(Context context, ImageView imageView, int resId) {
        Glide.with(context).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载裁剪到固定尺寸图片
     */
    public static void displayImageWithSize(Context context, ImageView imageView, String url, int width, int hight) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).override(width, hight).crossFade().into(imageView);
    }

    /**
     * 加载Gif图片
     */
    public static void displayGifImage(Context context, ImageView imageView, String url, int resId) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resId).error(resId).into(imageView);
    }

    /**
     * 加载Gif图片
     */
    public static void displayGifImage(Context context, ImageView imageView, int resId, int defaultResId) {
        Glide.with(context).load(resId).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultResId).error(defaultResId).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void displayCircleImage(Context context, ImageView imageView, String url, int resId) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resId).error(resId)
                .transform(new GlideCircleTransform(context)).crossFade().into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void displayRoundImage(Context context, ImageView imageView, String url, int resId, int round) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resId)
                .error(resId).transform(new GlideRoundTransform(context, round)).into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void displayRoundImageWithRadius10(Context context, ImageView imageView, String url, int resId) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(resId)
                .fitCenter()
                .error(resId)
                .transform(new GlideRoundTransform(context, 10))
                .dontAnimate()
                .into(imageView);
    }

}
