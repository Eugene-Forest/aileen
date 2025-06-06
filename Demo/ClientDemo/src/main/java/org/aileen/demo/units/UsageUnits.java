package org.aileen.demo.units;

import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.helper.AmHttpHelper;
import org.aileen.mod.auth.units.CryptoUnits;
import org.aileen.mod.httpclient.dto.SimpleHttpProxyDto;
import org.aileen.mod.kit.Base64Kit;

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
//        dto.setEncode(false);
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
        map.put(AmHttpHelper.ENCRYPT_HEADER, RequestEncryptType.Base64.getValue());//添加加密标识
        dto.setRequestType("post");
        String body = "{\"name\":\"admin\",\"password\":\"admin\"}";
        String encodeBody = Base64Kit.encode(body);
        dto.setBody(encodeBody);
//        dto.setBody("hello world!");
        dto.setHeaders(map);
//        dto.setEncode(false);
        return dto;
    }

    public static SimpleHttpProxyDto getAnalysePostRequestDto2() {
        SimpleHttpProxyDto dto = new SimpleHttpProxyDto();
        dto.setUrl(HttpAnalyseBaseUrl + "/analyse/aes/post");
        dto.setRequestType("post");
        String body = "{\"name\":\"admin\",\"password\":\"admin\"}";
        Map map = new HashMap();
        AmHttpHelper.addEncryptHeader(map, RequestEncryptType.AES);//添加加密标识
        String encodeBody = CryptoUnits.defaultEncrypt(body);
        dto.setBody(encodeBody);
        dto.setHeaders(map);
        return dto;
    }


//    public static void main(String[] args) {
//        SimpleHttpProxyDto dto = getAnalyseGetRequestDto();
//        System.out.println(JSON.toJSONString(dto));
//    }
}
