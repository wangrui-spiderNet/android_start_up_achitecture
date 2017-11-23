package com.cicada.startup.common.config;

/**
 * App环境配置
 * <p/>
 * 创建时间: 16/5/3 上午11:31 <br/>
 *
 * @since v0.0.1
 */
public enum AppEnvConfig {

    /**
     * 本地环境
     */
    LOCAL(0,
            "本地环境",
            "http://121.196.213.112",
            "https://121.196.213.112/"),
    /**
     * 开发环境
     */
    DEVELOP(1,
            "开发环境",
            "http://dev.imzhiliao.com",
            "http://dev.imzhiliao.com/"),
    /**
     * 测试环境
     */
    TEST(2,
            "测试环境",
            "http://test.imzhiliao.com",
            "https://test.imzhiliao.com/"),
    /**
     * 预发布环境
     */
    RELEASE_PRE(3,
            "预发布环境",
            "http://imzhiliao.com:10000",
            "http://imzhiliao.com/"),
    /**
     * 正式环境
     */
    RELEASE(4,
            "正式环境",
            "http://imzhiliao.com:9000",
            "http://imzhiliao.com/");

    private int index;
    private String name;
    private String apiUrl;
    private String webUrl;

    AppEnvConfig(int index, String name, String apiUrl, String webUrl) {
        this.index = index;
        this.name = name;
        this.apiUrl = apiUrl;
        this.webUrl = webUrl;
    }

    public int getIndex() {
        return index;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getName() {
        return name;
    }

    public static AppEnvConfig typeOf(String appEnvName) {
        return valueOf(appEnvName);
    }
}
