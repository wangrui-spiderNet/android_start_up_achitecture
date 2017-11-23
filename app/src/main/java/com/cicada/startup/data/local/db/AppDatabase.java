package com.cicada.startup.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.cicada.startup.data.local.db.dao.GirlDao;
import com.cicada.startup.data.local.db.dao.ZhihuDao;
import com.cicada.startup.data.local.db.entity.Girl;
import com.cicada.startup.data.local.db.entity.ZhihuStory;

/**
 * AppDatabase.java
 * <p>
 * Created by lijiankun on 17/7/5.
 */

@Database(entities = {Girl.class, ZhihuStory.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract GirlDao girlDao();

    public abstract ZhihuDao zhihuDao();
}
