package org.aileen.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aileen.demo.service.ClientDemoService;
import org.aileen.demo.units.UsageUnits;
import org.aileen.mod.httpclient.dto.SimpleHttpProxyDto;
import org.aileen.mod.httpclient.units.OkClientKit;
import org.aileen.mod.kit.dto.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/29
 */
@Service
@Slf4j
public class ClientDemoServiceImpl implements ClientDemoService {

    @Autowired
    private OkClientKit okClientKit;

    @Override
    public WebResult proxyHttp(SimpleHttpProxyDto dto) {
//        if (dto.getRequestType().equals("get")) {
//            try {
//                if (dto.isEncode()) {
//
//                    String res = okClientKit.get(dto.getUrl()).getData();
//                    log.info("请求原文：");
//                    log.info(res);
//                    String result = CryptoUnits.defaultDecrypt(res);
//                    log.info("解密：");
//                    log.info(result);
//                    return WebResult.success(result);
//                } else {
//
//                    String res = okClientKit.get(dto.getUrl()).getData();
//                    log.info("请求原文：");
//                    log.info(res);
//                    return WebResult.success(res);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        return WebResult.error();
    }

    @Override
    public WebResult proxyLogin() {
//        SimpleHttpProxyDto dto = new SimpleHttpProxyDto();
//        dto.setUrl("http://localhost:9091/datasource/login");
//        Map<String, String> m = new HashMap<>();
//        m.put("name", "admin");
//        m.put("password", "admin");
//        okClientKit.post(dto.getUrl(), JSON.toJSONString(m));
        return null;
    }

    @Override
    public WebResult proxyAnalyseGet() {
        return okClientKit.simpleHttpProxy(UsageUnits.getAnalyseGetRequestDto());
    }

    @Override
    public WebResult proxyAnalysePost() {
        return okClientKit.simpleHttpProxy(UsageUnits.getAnalysePostRequestDto());
    }
}
