package com.cicada.startup.common.config;

import android.content.Context;

import com.cicada.startup.common.AppContext;

/**
 * BaseUserPreferences
 * <p>
 * Create time: 2017/2/17 15:28
 *
 * @author liuyun.
 */
public class BaseUserPreferences extends BasePreferences {
    protected static BaseUserPreferences instance;

    protected BaseUserPreferences() {
        app = AppContext.getContext().getSharedPreferences("user_"
                        + BaseAppPreferences.getInstance().getUserId(),
                Context.MODE_PRIVATE);

    }


    public static BaseUserPreferences getInstance() {
        if (null == instance) {
            synchronized (BaseUserPreferences.class) {
                if (null == instance) {
                    instance = new BaseUserPreferences();
                }
            }
        }

        return instance;
    }
}
