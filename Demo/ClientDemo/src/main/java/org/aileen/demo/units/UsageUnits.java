package org.aileen.demo.units;

import com.alibaba.fastjson.JSON;
import org.aileen.mod.httpclient.dto.SimpleHttpProxyDto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
public class UsageUnits {


    private static final String HttpAnalyseBaseUrl = "http://localhost:8089";

    public static SimpleHttpProxyDto getAnalyseGetRequestDto() {
        SimpleHttpProxyDto dto = new SimpleHttpProxyDto();
        dto.setUrl(HttpAnalyseBaseUrl + "/analyse/get?id=112233");
        dto.setRequestType("get");
        Map map = new HashMap();
        map.put("method", "get");
        map.put("path", "get");
        map.put("token", "token12345");
        map.put("Authorization","Authorization12345");
        dto.setHeaders(map);
        dto.setEncode(false);
        return dto;
    }

    public static SimpleHttpProxyDto getAnalysePostRequestDto() {
        SimpleHttpProxyDto dto = new SimpleHttpProxyDto();
        dto.setUrl(HttpAnalyseBaseUrl + "/analyse/post");
        Map map = new HashMap();
        map.put("method", "get");
        map.put("path", "get");
        map.put("token", "token12345");
        map.put("Authorization","Authorization12345");
        dto.setRequestType("post");
        dto.setBody("hello world!");
        dto.setHeaders(map);
        dto.setEncode(false);
        return dto;
    }


    public static void main(String[] args) {
        SimpleHttpProxyDto dto = getAnalyseGetRequestDto();
        System.out.println(JSON.toJSONString(dto));
    }
}
