package com.cicada.startup.common.http.domain;

/**
 *
 * <p>
 * 创建时间: 16/8/5 上午10:48 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class UploadResult {

    private int height;
    private int width;
    private int state;
    private String url;
    int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
