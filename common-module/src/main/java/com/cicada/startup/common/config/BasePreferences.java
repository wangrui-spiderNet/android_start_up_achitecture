package com.cicada.startup.common.config;

import android.content.SharedPreferences;

/**
 * BasePreferences
 * <p>
 * Create time: 2017/2/17 15:26
 *
 * @author liuyun.
 */
public class BasePreferences {

    /**
     * 用户ID信息缓存－key
     */
    public static final String USER_ID = "user_id";

    /**
     * 登录证书缓存－key
     */
    public static final String LOGIN_TOKEN = "login_token";

    public static final String SERVER_TIME = "server_time";

    protected SharedPreferences app = null;


    public void setString(String key, String value) {
        app.edit().putString(key, value).commit();
    }

    public String getString(String key, String defaultValue) {
        return app.getString(key, defaultValue);

    }

    public void setInt(String key, int value) {
        app.edit().putInt(key, value).commit();
    }

    public int getInt(String key, int defaultValue) {
        return app.getInt(key, defaultValue);

    }

    public void setLong(String key, long value) {
        app.edit().putLong(key, value).commit();
    }

    public long getLong(String key, long defaultValue) {
        return app.getLong(key, defaultValue);

    }


    public void setBoolean(String key, boolean value) {
        app.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return app.getBoolean(key, defaultValue);
    }


    public void setFloat(String key, float value) {
        app.edit().putFloat(key, value).commit();
    }

    public float getFloat(String key, float defaultValue) {
        return app.getFloat(key, defaultValue);
    }

}
