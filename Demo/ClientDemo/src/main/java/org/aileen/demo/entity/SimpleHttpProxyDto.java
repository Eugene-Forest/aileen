package org.aileen.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 简单的http请求代理信息
 * @author Eugene-Forest
 * {@code @date} 2024/11/29
 */
@Getter
@Setter
public class SimpleHttpProxyDto {

    private String url;
    private String requestType;
    private Map<String, String> headers;
    private String body;
}
