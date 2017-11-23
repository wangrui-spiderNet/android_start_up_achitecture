package com.cicada.startup.common.http.upload;

import com.cicada.startup.common.http.domain.UploadResponse;
import com.cicada.startup.common.http.domain.UploadResult;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * TODO 功能描述
 * <p>
 * 创建时间: 16/7/14 下午4:52 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public interface UploadFileApi {


    /**
     * 单个文件上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("/file/upload/savefile.shtml")
    Observable<UploadResponse<UploadResult>> uploadFile(@Part MultipartBody.Part file);

    /**
     * 上传多文件
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("/file/upload/multi")
    Observable<UploadResponse<List<UploadResult>>> uploadFiles(@PartMap Map<String, RequestBody> params);


}
