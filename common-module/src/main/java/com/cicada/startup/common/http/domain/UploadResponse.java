package com.cicada.startup.common.http.domain;

/**
 * TODO 功能描述
 * <p>
 * 创建时间: 16/7/14 下午4:55 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class UploadResponse<T> {
    /**
     * code : 200
     * data : {"height":668,"width":523,"state":0,"url":"http://cicadafile.qiniudn.com/14159434292347GfS0oxPgN.png"}
     */

    private int code;
    /**
     * height : 668
     * width : 523
     * state : 0
     * url : http://cicadafile.qiniudn.com/14159434292347GfS0oxPgN.png
     */

    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
