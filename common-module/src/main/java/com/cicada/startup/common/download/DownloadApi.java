package com.cicada.startup.common.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * TODO
 * <p>
 * Create time: 2017/1/11 14:48
 *
 */
public interface DownloadApi {
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
