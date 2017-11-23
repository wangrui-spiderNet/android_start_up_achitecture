package com.cicada.startup.common.domain;

/**
 * Created by zyh on 2017/5/18.
 */
public class UploadToken {
    private String expires;
    private String my_domain;
    private String token;

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getMy_domain() {
        return my_domain;
    }

    public void setMy_domain(String my_domain) {
        this.my_domain = my_domain;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
