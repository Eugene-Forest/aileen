package org.aileen.mod.httpclient.units;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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

    public String get(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("请求第三方接口出现错误，错误信息为:{}", e.getMessage());
            return null;
        }
    }

    public String post(String url, String json) {
        try {
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("请求第三方接口出现错误，错误信息为:{}", e.getMessage());
            return null;
        }
    }
}
