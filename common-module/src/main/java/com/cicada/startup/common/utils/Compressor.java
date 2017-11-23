package com.cicada.startup.common.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;

/**
 * Created by zyh on 2017/8/16.
 */
public class Compressor {
    public static Compressor instance;
    //max width and height values of the compressed image is taken as 612x816
    private int maxWidth = 1024;
    private int maxHeight = 1024;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int quality = 80;
    private String destinationDirectoryPath;

    public static Compressor getInstance(Context context){
        if(null == instance){
            synchronized (Compressor.class){
                if(null == instance){
                    instance = new Compressor(context);
                }
            }
        }
        return instance;
    }

    public Compressor(Context context) {
        destinationDirectoryPath = FileUtils.getCacheImgPath(context);
    }

    public Compressor setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public Compressor setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public Compressor setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public Compressor setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String destinationDirectoryPath) {
        this.destinationDirectoryPath = destinationDirectoryPath;
        return this;
    }

    public File compressToFile(File imageFile) {
        try {
            return compressToFile(imageFile, imageFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return ImageUtil.compressImage(imageFile, maxWidth, maxHeight, compressFormat, quality,
                destinationDirectoryPath + File.separator + compressedFileName);
    }

    public Bitmap compressToBitmap(File imageFile) {
        return ImageUtil.decodeSampledBitmapFromFile(imageFile, maxWidth, maxHeight);
    }

}
