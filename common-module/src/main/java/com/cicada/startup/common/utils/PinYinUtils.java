/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 * 
 * Project Name: Zhiliao
 * $Id: PinYinUtils.java 2014年11月15日 上午11:23:03 $ 
 */
package com.cicada.startup.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.pinyin4android.PinyinUtil;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 汉字转拼音处理类
 * <p/>
 * 创建时间: 2014年11月15日 上午11:23:03 <br/>
 * 
 * @author liuyun
 * @version
 * @since v0.1
 */
public class PinYinUtils {

	/**
	 * 返回汉字拼音首字母，字母原样返回，都转换为大写
	 * 
	 * @author xnjiang
	 * @param context
	 * @param input
	 * @return
	 * @since v0.0.1
	 */
	@SuppressLint("DefaultLocale")
	public static String getPinYinFirst(Context context, String input) {
		String returnPY = "";
		if (input != null) {
			if (input != null) {
				char[] charAll = input.toCharArray();
				for (char cc : charAll) {
					if (isAllChinese(Character.toString(cc))) {
						String strFirst = PinyinUtil.toPinyin(context, Character.toString(cc));
						returnPY += strFirst.substring(0, 1);
					} else {
						returnPY += Character.toString(cc);
					}
				}
			}
		}
		return returnPY.toUpperCase(Locale.getDefault());
	}

	/**
	 * 汉字返回拼音，字母原样返回，都转换为小写(默认取得的拼音全大写)
	 * 
	 * @author xnjiang
	 * @param context
	 * @param input
	 * @return
	 * @since v0.0.1
	 */
	@SuppressLint("DefaultLocale")
	public static String getPinYinAll(Context context, String input) {
		String returnPY = "";
		if (input != null) {
			char[] charAll = input.toCharArray();
			for (char cc : charAll) {
				if (isAllChinese(Character.toString(cc))) {
					returnPY += PinyinUtil.toPinyin(context, Character.toString(cc));
				} else {
					returnPY += Character.toString(cc);
				}
			}
		}
		return returnPY.toUpperCase(Locale.getDefault());
	}

	/**
	 * 将字符串转换成ASCII码
	 * 
	 * @param cnStr
	 * @return String
	 */
	public static String getASCII_CN(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * 判断字符串是否全是中文汉字(正则表达式)
	 * 
	 * @param strInput
	 * @return
	 */
	public static boolean isAllChinese(String strInput) {
		if (strInput != null && !strInput.trim().equalsIgnoreCase("")) {
			// 此正则表达式用来判断是否为中文(\\u4E00-\\u9FA5\\uF900-\\uFA2D是指汉字的Unicode编码范围 )
			Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uF900-\uFA2D]+");
			Matcher matcher = pattern.matcher(strInput);
			if (matcher.matches()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否全是a-z的字母(正则表达式)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAllChar(String str) {
		String regex = "[A-Za-z]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
}
