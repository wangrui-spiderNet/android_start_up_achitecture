package com.cicada.startup.common.http.domain;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.utils.DeviceUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * 请求数据
 * <p>
 * 创建时间: 16/4/12 下午4:16 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class Request {


    private ClientInfo clientInfo;
    private String style;
    private Map<String, Object> data = new Hashtable<>();


    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static class Builder {
        private ClientInfo clientInfo = new ClientInfo();
        private String style = "black";
        private Map<String, Object> data = new Hashtable<>();

        public Builder() {

        }

        public Builder withClientInfo(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
            return this;
        }

        public Builder withStyle(String style) {
            this.style = style;
            return this;
        }

        public Builder withData(Map<String, Object> data) {
            if(data != null){
                this.data = data;
            }
            return this;
        }

        public Builder withParam(String key, Object value) {
            if(value != null){
                this.data.put(key, value);
            }
            return this;
        }

        public Request build() {
            Request request = new Request();
            request.setStyle(this.style);
            request.setClientInfo(this.clientInfo);
            this.data.put("app", "yxb");
            request.setData(this.data);
            return request;
        }
    }

    public static class ClientInfo {
        private String version = DeviceUtils.getVersionName(AppContext.getContext());
        private String clientType = "android";
        private String clientModel = DeviceUtils.getDeviceModel();
        private String clientOs = DeviceUtils.getOS();
        private String deviceId = DeviceUtils.getUUID(AppContext.getContext());
        private String cNet = DeviceUtils.getNetworkType(AppContext.getContext());

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }

        public String getClientModel() {
            return clientModel;
        }

        public void setClientModel(String clientModel) {
            this.clientModel = clientModel;
        }

        public String getClientOs() {
            return clientOs;
        }

        public void setClientOs(String clientOs) {
            this.clientOs = clientOs;
        }

        public String getcNet() {
            return cNet;
        }

        public void setcNet(String cNet) {
            this.cNet = cNet;
        }
    }
}
