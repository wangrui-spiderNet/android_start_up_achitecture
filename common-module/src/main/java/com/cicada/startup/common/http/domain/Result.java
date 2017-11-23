/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 * 
 * Project Name: Zhiliao
 * $Id: ResponseData.java 2014年10月28日 下午2:48:16 $ 
 */
package com.cicada.startup.common.http.domain;

import com.cicada.startup.common.http.BaseURL;

/**
 * 服务器返回的基础数据
 * <p/>
 * 创建时间: 2014年11月5日 下午3:39:53 <br/>
 *
 * @author xnjiang
 * @since v0.0.1
 */
public class Result<T> {
    /**
     * 服务器当前时间
     */
    private long ts;
    /**
     * 响应结果code
     */
    private String rtnCode;
    /**
     * 提示语
     */
    private String msg;

    /**
     * 业务相关数据
     */
    private T bizData;

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBizData() {
        return bizData;
    }

    public void setBizData(T bizData) {
        this.bizData = bizData;
    }

    public long getTs() {
        return ts;
    }

    /**
     * 服务器当前时间
     *
     * @since v0.0.1
     */
    public void setTs(long ts) {
        this.ts = ts;
    }

    public boolean isSuccess() {
        if (BaseURL.APP_BUSINESS_SUCCESS.equalsIgnoreCase(this.rtnCode)) {
            return true;
        } else {
            return false;
        }
    }
}
