package com.cicada.startup.data;

import android.arch.lifecycle.LiveData;

import com.cicada.startup.data.local.db.entity.Girl;
import com.cicada.startup.data.local.db.entity.ZhihuStory;
import com.cicada.startup.data.remote.model.ZhihuStoryDetail;

import java.util.List;

/**
 * DataSource.java
 * <p>
 * Created by lijiankun on 17/7/7.
 */

public interface DataSource {

    /**
     * Girl 相关方法
     */
    LiveData<List<Girl>> getGirlList(int index);

    LiveData<Boolean> isLoadingGirlList();


    /**
     * Zhihu 相关方法
     */
    LiveData<List<ZhihuStory>> getLastZhihuList();

    LiveData<List<ZhihuStory>> getMoreZhihuList(String date);

    LiveData<ZhihuStoryDetail> getZhihuDetail(String id);

    LiveData<Boolean> isLoadingZhihuList();
}
