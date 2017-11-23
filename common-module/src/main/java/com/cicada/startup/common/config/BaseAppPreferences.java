package com.cicada.startup.common.config;

import android.content.Context;

import com.cicada.startup.common.AppContext;

/**
 * BaseAppPreferences
 * <p>
 * Create time: 2017/2/17 15:27
 *
 * @author liuyun.
 */
public class BaseAppPreferences extends BasePreferences {

    protected static BaseAppPreferences instance;

    protected BaseAppPreferences() {
        app = AppContext.getContext().getSharedPreferences("app",
                Context.MODE_PRIVATE);

    }

    public static BaseAppPreferences getInstance() {
        if (null == instance) {
            synchronized (BaseAppPreferences.class) {
                if (null == instance) {
                    instance = new BaseAppPreferences();
                }
            }
        }

        return instance;
    }

    public long getUserId() {
        return getLong(USER_ID, 0l);
    }

    public void setUserId(long userId) {
        setLong(USER_ID, userId);
    }

    public String getLoginToken() {
        return getString(LOGIN_TOKEN, "");
    }

    public void setLoginToken(String loginToken) {
        setString(LOGIN_TOKEN, loginToken);
    }

    /**
     * 获取服务器当前时间
     */
    public long getServerTimeStampNow() {
        return getLong(SERVER_TIME, System.currentTimeMillis());
    }

    /**
     * 设置服务器当前时间
     */
    public void setServerTimeStampNow(long lngTimeStamp) {
        setLong(SERVER_TIME, lngTimeStamp);
    }
}
