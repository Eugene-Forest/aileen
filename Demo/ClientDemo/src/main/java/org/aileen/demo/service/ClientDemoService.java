package org.aileen.demo.service;


import org.aileen.mod.kit.dto.WebResult;
import org.aileen.mod.httpclient.dto.SimpleHttpProxyDto;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/29
 */
public interface ClientDemoService {

    WebResult proxyHttp(SimpleHttpProxyDto dto);

    WebResult proxyLogin();

    WebResult proxyAnalyseGet();

    WebResult proxyAnalysePost();
}
