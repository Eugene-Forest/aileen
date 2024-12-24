package org.aileen.mod.httpclient.units;

import okhttp3.*;
import org.aileen.mod.kit.dto.WebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * OkHttp网络请求封装工具类
 *
 * @author Eugene-Forest
 * {@code @date} 2024/12/20
 */
@Component
public class OkClientKit {

    private static final Logger log = LoggerFactory.getLogger(OkClientKit.class);

    private final OkHttpClient okHttpClient;

    @Autowired
    public OkClientKit(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public WebResult<String> get(String url) {
        try {
            WebResult<String> result = WebResult.success();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                result.setCode(response.code());
                result.setMessage(response.message());
                result.setData(response.body().string());
                return result;
            }
        } catch (IOException e) {
            log.error("请求第三方接口出现错误，错误信息为:{}", e.getMessage());
            return WebResult.error();
        }
    }

    public WebResult<String> post(String url, String json) {
        try {
            WebResult<String> result = WebResult.success();
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                result.setCode(response.code());
                result.setMessage(response.message());
                result.setData(response.body().string());
                return result;
            }
        } catch (IOException e) {
            log.error("请求第三方接口出现错误，错误信息为:{}", e.getMessage());
            return WebResult.error();
        }
    }
}
