package com.cicada.startup.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

/**
 * SharePreference工具类
 *
 * @author zyh
 */
public class PreferencesUtil {
    private static String PreferenceName = "Constant";

    /**
     * 获取SharePreference中的String类型值
     */
    public static String getStringPreferences(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        // 获取数据
        return sp.getString(name, "");
    }

    /**
     * 将String信息存入Preferences
     *
     * @param context
     * @param name
     * @param value
     */
    public static void setPreferences(Context context, String name, String value) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    /**
     * 获取SharePreference中的值
     */
    public static boolean getBooleanPreferences(Context context, String name, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        // 获取数据
        return sp.getBoolean(name, defValue);
    }

    /**
     * 将boolean信息存入Preferences
     *  @param context
     * @param name
     * @param value
     */
    public static boolean setPreferences(Context context, String name, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.commit();
        return value;
    }

    /**
     * 获取SharePreference中的int类型值
     */
    public static int getIntPreferences(Context context, String name, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        // 获取数据
        return sp.getInt(name, defValue);
    }

    /**
     * 将int信息存入Preferences
     *
     * @param context
     * @param name
     * @param value
     */
    public static void setPreferences(Context context, String name, int value) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    /**
     * 获取SharePreference中的值
     */
    public static float getFloatPreferences(Context context, String name, float defValue) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        // 获取数据
        return sp.getFloat(name, defValue);
    }

    /**
     * 将float信息存入Preferences
     *
     * @param context
     * @param name
     * @param value
     */
    public static void setPreferences(Context context, String name, float value) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putFloat(name, value);
        editor.commit();
    }

    /**
     * 将Object信息Base64后存入Preferences
     *
     * @param context
     * @param name
     * @param obj
     */

    public static <T> void setPreferences(Context context, String name, T obj) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        if (obj == null) {
            editor.putString(name, null);
            editor.commit();
            return;
        }
        try {
            ByteArrayOutputStream toByte = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(toByte);
            oos.writeObject(obj);
            // 对byte[]进行Base64编码
            String obj64 = new String(Base64.encode(toByte.toByteArray(), Base64.DEFAULT));
            editor.putString(name, obj64);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SharePreference中值，Base64解码之后传出
     *
     * @param context
     * @param name
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPreferences(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        try {
            String obj64 = sp.getString(name, "");
            if (TextUtils.isEmpty(obj64)) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(obj64, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除SharePreference中存的所有值
     *
     * @param context
     */
    public static void clearPreference(Context context){
        SharedPreferences sp = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 将Object信息Base64后存入Preferences
     *
     * @param context
     * @param name
     * @param obj
     */

    public static <T> void setHistoryData(Context context, String name, T obj) {
        SharedPreferences sp = context.getSharedPreferences("HistoryData", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        if (obj == null) {
            editor.putString(name, null);
            editor.commit();
            return;
        }
        try {
            ByteArrayOutputStream toByte = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(toByte);
            oos.writeObject(obj);
            // 对byte[]进行Base64编码
            String obj64 = new String(Base64.encode(toByte.toByteArray(), Base64.DEFAULT));
            editor.putString(name, obj64);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SharePreference中值，Base64解码之后传出
     *
     * @param context
     * @param name
     */
    @SuppressWarnings("unchecked")
    public static <T> T getHistoryData(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences("HistoryData", Context.MODE_PRIVATE);
        try {
            String obj64 = sp.getString(name, "");
            if (TextUtils.isEmpty(obj64)) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(obj64, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除SharePreference中存的所有值
     *
     * @param context
     */
    public static void clearHistoryData(Context context){
        SharedPreferences sp = context.getSharedPreferences("HistoryData", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}
