package com.cicada.startup.common.domain;

import java.io.Serializable;

/**
 * Created by zhangyuanhong on 2017/3/30.
 */
public class LoginResponse implements Serializable{
    private boolean hasConfirm;
    private String token;
    private int isSchoolMasterNewVersion;
    private int isSchoolMaster;
    private long userId;
    private String loginNumber;
    private String hasUserInfo;
    private String hasCradMachine;
    private long schoolId;
    private LiteUserContext liteUserContext;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(String loginNumber) {
        this.loginNumber = loginNumber;
    }

    public String getHasUserInfo() {
        return hasUserInfo;
    }

    public void setHasUserInfo(String hasUserInfo) {
        this.hasUserInfo = hasUserInfo;
    }

    public boolean isHasConfirm() {
        return hasConfirm;
    }

    public void setHasConfirm(boolean hasConfirm) {
        this.hasConfirm = hasConfirm;
    }

    public String getHasCradMachine() {
        return hasCradMachine;
    }

    public void setHasCradMachine(String hasCradMachine) {
        this.hasCradMachine = hasCradMachine;
    }

    public LiteUserContext getLiteUserContext() {
        return liteUserContext;
    }

    public void setLiteUserContext(LiteUserContext liteUserContext) {
        this.liteUserContext = liteUserContext;
    }

    public int getIsSchoolMasterNewVersion() {
        return isSchoolMasterNewVersion;
    }

    public void setIsSchoolMasterNewVersion(int isSchoolMasterNewVersion) {
        this.isSchoolMasterNewVersion = isSchoolMasterNewVersion;
    }

    public int getIsSchoolMaster() {
        return isSchoolMaster;
    }

    public void setIsSchoolMaster(int isSchoolMaster) {
        this.isSchoolMaster = isSchoolMaster;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }
}
