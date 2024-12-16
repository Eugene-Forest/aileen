package org.aileen.demo.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aileen.demo.entity.SimpleHttpProxyDto;
import org.aileen.demo.service.ClientDemoService;
import org.aileen.demo.units.HttpClientUtils;
import org.aileen.mod.auth.entity.WebResult;
import org.aileen.mod.auth.units.CryptoUnits;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/29
 */
@Service
@Slf4j
public class ClientDemoServiceImpl implements ClientDemoService {


    @Override
    public WebResult proxyHttp(SimpleHttpProxyDto dto) {
        if (dto.getRequestType().equals("get")) {
            try {
                if (dto.isEncode()) {

                    String res = HttpClientUtils.get(dto.getUrl());
                    log.info("请求原文：");
                    log.info(res);
                    String result = CryptoUnits.defaultDecrypt(res);
                    log.info("解密：");
                    log.info(result);
                    return WebResult.success(result);
                } else {

                    String res = HttpClientUtils.get(dto.getUrl());
                    log.info("请求原文：");
                    log.info(res);
                    return WebResult.success(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return WebResult.error();
    }

    @Override
    public WebResult proxyLogin() {
        try {
            SimpleHttpProxyDto dto = new SimpleHttpProxyDto();
            dto.setUrl("http://localhost:9091/datasource/login");
            Map<String, String> m = new HashMap<>();
            m.put("name", "admin");
            m.put("password", "admin");
            HttpClientUtils.post(dto.getUrl(), JSON.toJSONString(m));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
