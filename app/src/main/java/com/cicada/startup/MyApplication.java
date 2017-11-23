package com.cicada.startup;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.cicada.startup.data.local.db.AppDatabaseManager;
import com.cicada.startup.utils.Consts;

/**
 * MyApplication.java
 * <p>
 * Created by lijiankun on 17/7/9.
 */

public class MyApplication extends Application {

    private static MyApplication INSTANCE = null;

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        AppDatabaseManager.getInstance().createDB(this);
        if (Consts.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
