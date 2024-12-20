package org.aileen.mod.httpclient.dto;

import java.util.Map;

/**
 * 简单的http请求代理信息
 * @author Eugene-Forest
 * {@code @date} 2024/11/29
 */
public class SimpleHttpProxyDto {

    private String url;
    private String requestType;
    private Map<String, String> headers;
    private String body;
    private boolean isEncode = true;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isEncode() {
        return isEncode;
    }

    public void setEncode(boolean encode) {
        isEncode = encode;
    }
}
