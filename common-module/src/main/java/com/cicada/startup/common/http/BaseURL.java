package com.cicada.startup.common.http;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.config.BaseAppPreferences;
import com.cicada.startup.common.utils.LogUtils;

/**
 * BaseURL
 * <p>
 * 创建时间: 16/5/3 上午10:57 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class BaseURL {


    /************************************************** 服务器错误信息定义 *****************************************/
    // TODO:服务器错误信息定义


    /**
     * 服务器连接错误500（内部服务错误）
     */
    public static final String APP_EXCEPTION_HTTP_500 = "500";
    /**
     * 服务器数据解析错误、以及任何未定义的错误
     */
    public static final String APP_EXCEPTION_HTTP_OTHER = "-100";
    /**
     * 业务成功代码：0000000<br>
     */
    public static final String APP_BUSINESS_SUCCESS = "0000000";

    /**
     * 服务器网络连接(超时)错误
     */
    public static final String APP_EXCEPTION_HTTP_TIMEOUT = "0";

    /**
     * 获取当前环境－服务器域名、地址
     */
    public static String getBaseURL() {
        return AppContext.appEnvConfig.getApiUrl();
    }


    /**
     * 请假
     * <p>
     * 传参 ?token= &customerType=
     *
     * @return
     */
    public static String getLeaveUrl(int customerType, long schoolId) {
        String path = "yxbAppWeb/leave/leaveListApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 请假详情
     * <p>
     * 传参 ?leaveId= &token=
     *
     * @return
     */
    public static String getLeaveDetailUrl(int customerType, String leaveId, long schoolId) {
        String path = "yxbAppWeb/leave/leaveDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&leaveId=" + leaveId
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 叮嘱
     * <p>
     * ?token= &customerType=
     *
     * @return
     */
    public static String getExhortUrl(int customerType, long schoolId) {
        String path = "yxbAppWeb/told/toldListApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 叮嘱详情
     * <p>
     * //传参 ?toldId=  &token=
     *
     * @return
     */
    public static String getExhortDetailUrl(int customerType, String toldId, long schoolId) {
        String path = "yxbAppWeb/told/toldDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&toldId=" + toldId
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }


    /**
     * 亲子作业
     * <p>
     * ?token= &customerType=
     *
     * @return
     */
    public static String getHomeWorkUrl(int customerType, long schoolId) {
        String path = "yxbAppWeb/homework/homeworkListApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 亲子作业详情
     * <p>
     * //传参 ?token= &customerType=
     *
     * @return
     */
    public static String getHomeWorkDetailUrl(int customerType, String homeworkId, long schoolId) {
        String path = "yxbAppWeb/homework/homeworkDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&homeworkId=" + homeworkId
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 园所通知
     * <p>
     * ?token= &customerType=
     *
     * @return
     */
    public static String getSchoolNoticeUrl(int customerType, long schoolId) {
        String path = "yxbAppWeb/notice/noticeListApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 园所通知详情
     * <p>
     * ?token= &noticeId= &rad(三位随机数)
     *
     * @return
     */
    public static String getSchoolNoticeDetailUrl(int customerType, String noticeId, long schoolId) {
        String path = "yxbAppWeb/notice/noticeDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&noticeId=" + noticeId
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }


    /**
     * 成长档案临时页面
     *
     * @return
     */
    public static String getGrowUpUrl(int customerType, long schoolId) {
        String path = "yxbAppWeb/growth/growthProfileApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }


    /**
     * 知了讲堂
     *
     * @param customerType
     * @param schoolId
     * @return
     */
    public static String getNewsUrl(int customerType, long schoolId) {
        String path = "yxbAppWeb/headlines/homeApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    private static int getRad() {
        int d = (int) (Math.random() * 900);
        d += 100;
        LogUtils.d("==rad==", String.valueOf(d));
        return d;
    }

    /**
     * 育儿头条详情
     *
     * @param articleId
     * @return
     */
    public static String getNewsDetailUrl(long articleId) {
        String path = "yxbAppWeb/headlines/articleDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&articleId=" + articleId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 交费助手
     *
     * @param customerType
     * @param schoolId
     * @return
     */
    public static String getPaymentListUrl(int customerType, long schoolId) {
        String path = "yxbAppWeb/payment/paymentListApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 交费助手-账单详情
     *
     * @param customerType
     * @param paymentId
     * @return
     */
    public static String getPaymentDetailUrl(int customerType, String paymentId, long schoolId) {
        String path = "yxbAppWeb/payment/paymentDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&paymentId=" + paymentId
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 活动中心
     *
     * @param customerType
     * @param schoolId
     * @return
     */
    public static String getActivityCenterUrl(int customerType, long schoolId) {
        String path = "yxbAppWeb/headlines/activityListApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 活动详情
     *
     * @param activityId
     * @return
     */
    public static String getActivityDetailUrl(long activityId) {
        String path = "yxbAppWeb/headlines/activityDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&activityId=" + activityId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 视频详情
     *
     * @param liveId
     * @return
     */
    public static String getVideoDetailUrl(long liveId) {
        String path = "yxbAppWeb/headlines/videoDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&liveId=" + liveId
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 积分商城url
     *
     * @return
     */
    public static String getCreditStoreUrl() {
        String url = AppContext.appEnvConfig.getApiUrl() + "/credit/credit/dbUserAutoLogin?token=" + BaseAppPreferences.getInstance().getLoginToken() + "&redirect=";
        return url;
    }

    /**
     * 园小宝服务协议
     *
     * @return
     */
    public static String getYXBServiceUrl() {
        return AppContext.appEnvConfig.getWebUrl() + "zlWebsite/protocol/user.html";
    }

    /**
     * 新鲜事分享协议
     *
     * @return
     */
    public static String getFreshShareUrl(String messageId, String classIds) {
        String path = "yxbAppWeb/novelty/noveltyDetailApp.html";
        String param = "?token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&messageId=" + messageId
                + "&classIds=" + classIds
                + "&rad=" + getRad();
        return AppContext.appEnvConfig.getWebUrl() + path + param;
    }

    /**
     * 获取积分规则
     *
     * @return
     */
    public static String getIntegrationUrl() {
        return AppContext.appEnvConfig.getWebUrl() + "/zlWebsite/help/view/scoreRule.html";
    }

    /**
     * 获取拼接上域名、token和rad的url
     *
     * @param url
     * @return
     */
    public static String getUrlWithHostAndTokenAndRad(String url) {
        return AppContext.appEnvConfig.getWebUrl() + url + "&token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&rad=" + getRad();
    }

    /**
     * 获取拼接上域名、token和customerType、schoolId的url
     */
    public static String getDefaultAppUrl(String url, long schoolId, int customerType) {
        return AppContext.appEnvConfig.getWebUrl() + url + "&token=" + BaseAppPreferences.getInstance().getLoginToken()
                + "&customerType=" + customerType
                + "&schoolId=" + schoolId
                + "&rad=" + getRad();
    }


}
