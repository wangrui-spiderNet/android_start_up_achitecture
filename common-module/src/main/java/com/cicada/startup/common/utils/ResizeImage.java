package com.cicada.startup.common.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
/**
 * 图片压缩并保存
 * @author yhzhang
 *
 */
public class ResizeImage {

	public static String compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 800f;//
		float ww = 480f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;
		newOpts.inJustDecodeBounds = false;
		newOpts.inPreferredConfig = Config.RGB_565;
		newOpts.inPurgeable = true;
		newOpts.inInputShareable = true;
		
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		String storName = UUID.randomUUID().toString()
				+ srcPath.substring(srcPath.lastIndexOf("."), srcPath.length());
		File f = new File(Environment.getExternalStorageDirectory(),storName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			Log.i("AAA", "已经保存");
		} catch (FileNotFoundException e) {
			Log.i("AAA", "找不到文件");
		} catch (IOException e) {
			Log.i("AAA", "文件读写错误");
		}
		Log.v("1111111", String.valueOf(f.length()));
		return f.getAbsolutePath();
	}
	
	public static String convertBitmap(String filePath){
		ByteArrayInputStream isBm = null;
		File file = null;
		try {
			Bitmap bitmap = null;				
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			BitmapFactory.decodeStream(fis, null, o);
			fis.close();
			final int REQUIRED_SIZE = 200;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inSampleSize = scale;
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, op);
			fis.close();	
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
			int options = 100;
			while ( baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
			     baos.reset();//重置baos即清空baos  
			     bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
			     options -= 10;//每次都减少10  
			}  
			isBm = new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return saveBitmap(BitmapFactory.decodeStream(isBm, null, null), file.getName());
	}

	/**
	 * 保存照相后的图片
	 * @return 
	 */
	public static String saveBitmap(final Bitmap bitmap, String fileName) {
		File tempFile = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			String pictureName = String.valueOf(System.currentTimeMillis()) + fileName;
			tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/yuanxiaobao/saveimage/", pictureName);
			if (tempFile.exists()) {
				tempFile.delete();
			}
			try {
				FileOutputStream out = new FileOutputStream(tempFile);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.v("22222222", String.valueOf(tempFile.length()));
		return tempFile.getAbsolutePath();
	}
	
	public static void deleteFile(String srcFile) {
		File file = new File(srcFile);
		if (file.exists()) { 
			if (file.isFile()) { 
				file.delete(); 
			}
		}
	}
	
	//使用BitmapFactory.Options的inSampleSize参数来缩放  
    public static Drawable createBitmap(String path,int width,int height){  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;//不加载bitmap到内存中  
        BitmapFactory.decodeFile(path,options);   
        int outWidth = options.outWidth;  
        int outHeight = options.outHeight;  
        options.inDither = false;  
        options.inPreferredConfig = Config.ARGB_8888;
        options.inSampleSize = 1;            
        if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0){  
            int sampleSize=(outWidth/width+outHeight/height)/2;  
            Log.d("tag", "sampleSize = " + sampleSize);  
            options.inSampleSize = sampleSize;  
        }        
        options.inJustDecodeBounds = false;  
        return new BitmapDrawable(BitmapFactory.decodeFile(path, options));       
    }  
}
