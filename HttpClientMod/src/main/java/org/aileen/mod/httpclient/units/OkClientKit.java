package org.aileen.mod.httpclient.units;

import okhttp3.*;
import org.aileen.mod.kit.dto.WebResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        return get(url, null);
    }

    public WebResult<String> get(String url, Map<String, String> headers) {
        try {
            WebResult<String> result = WebResult.success();
            // 创建请求对象
            Request.Builder requestBuilder = new Request.Builder().url(url);
            if (headers != null) {
                // 遍历插入请求头
                addCustomHeader(headers, requestBuilder);
            }
            // 构建请求对象
            Request request = requestBuilder.build();

            return getStringWebResult(result, request);
        } catch (IOException e) {
            log.error("请求第三方接口出现错误，错误信息为:{}", e.getMessage());
            return WebResult.error();
        }
    }

    @NotNull
    private WebResult<String> getStringWebResult(WebResult<String> result, Request request) throws IOException {
        try (Response response = okHttpClient.newCall(request).execute()) {
            result.setCode(response.code());
            result.setMessage(response.message());
            if (response.body() != null) {
                result.setData(response.body().string());
            }
            return result;
        }
    }

    /**
     * 添加自定义请求头
     */
    private void addCustomHeader(@NotNull Map<String, String> headers, @NotNull Request.Builder requestBuilder) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (entry.getKey().equals("Authorization")) {
                requestBuilder.addHeader(entry.getKey(), "Bearer " + entry.getValue());
            } else {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    public WebResult<String> post(String url, String json) {
        return post(url, json, null);
    }

    public WebResult<String> post(String url, String json, Map<String, String> headers) {
        try {
            WebResult<String> result = WebResult.success();
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json, JSON);
            // 创建请求对象
            Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
            if (headers != null) {
                addCustomHeader(headers, requestBuilder);
            }
            Request request = requestBuilder.build();

            return getStringWebResult(result, request);
        } catch (IOException e) {
            log.error("请求第三方接口出现错误，错误信息为:{}", e.getMessage());
            return WebResult.error();
        }
    }
}
