package org.aileen.mod.httpclient.enums;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/26
 */
public enum SimpleHttpProxyType {
    GET("get"),
    POST("post");

    private String value;

    SimpleHttpProxyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
