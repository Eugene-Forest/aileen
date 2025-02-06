package org.aileen.demo.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aileen.demo.service.ClientDemoService;
import org.aileen.demo.units.UsageUnits;
import org.aileen.mod.auth.units.CryptoUnits;
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
        WebResult result = okClientKit.simpleHttpProxy(dto);
        if(WebResult.isSuccess(result)) {
            String res = CryptoUnits.defaultDecrypt(result.getData().toString());
            result.setData(res);
            return result;
        }
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

    @Override
    public WebResult proxyAnalysePostJson(Integer id) {
        WebResult result = WebResult.error();
        switch (id) {
            case 1: {
                result = okClientKit.simpleHttpProxy(UsageUnits.getAnalysePostRequestDto2());
                if(WebResult.isSuccess(result)){
                    String res = CryptoUnits.defaultDecrypt(result.getData().toString());
                    result.setData(res);
                }
            }
//            case 2:
//                return okClientKit.simpleHttpProxy(UsageUnits.getAnalysePostRequestDto3());
//            case 3:
//                return okClientKit.simpleHttpProxy(UsageUnits.getAnalysePostRequestDto4());
        }
        return result;
    }
}
