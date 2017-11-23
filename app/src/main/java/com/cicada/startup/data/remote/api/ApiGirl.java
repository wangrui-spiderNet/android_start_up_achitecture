package com.cicada.startup.data.remote.api;

import com.cicada.startup.data.remote.model.GirlData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ApiGirl.java
 * <p>
 * Created by lijiankun on 17/7/8.
 */

public interface ApiGirl {

    @GET("api/data/福利/10/{index}")
    Call<GirlData> getGirlsData(@Path("index") int index);
}
