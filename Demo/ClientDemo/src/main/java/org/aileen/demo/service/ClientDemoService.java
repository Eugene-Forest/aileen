package org.aileen.demo.service;


import org.aileen.demo.entity.SimpleHttpProxyDto;
import org.aileen.mod.auth.entity.WebResult;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/29
 */
public interface ClientDemoService {

    WebResult proxyHttp(SimpleHttpProxyDto dto);

    WebResult proxyLogin();
}
