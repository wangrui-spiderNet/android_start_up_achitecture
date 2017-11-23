package com.cicada.startup.common.domain;

/**
 *
 * <p>
 * 创建时间: 16/8/9 下午2:43 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class VersionUpdate {

    /**
     * downLoadUrl : http://dev.imzhiliao.com/yishitang.apk
     * updateType : 1
     * version : 1.0.0.5
     * versionCode : 5
     * versionIntro : 益师堂APP测试
     * versionPics :
     * versionSize : 13M
     */

    private String downLoadUrl;
    private int updateType;
    private String version;
    private int versionCode;
    private String versionIntro;
    private String versionPics;
    private String versionSize;

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionIntro() {
        return versionIntro;
    }

    public void setVersionIntro(String versionIntro) {
        this.versionIntro = versionIntro;
    }

    public String getVersionPics() {
        return versionPics;
    }

    public void setVersionPics(String versionPics) {
        this.versionPics = versionPics;
    }

    public String getVersionSize() {
        return versionSize;
    }

    public void setVersionSize(String versionSize) {
        this.versionSize = versionSize;
    }
}
