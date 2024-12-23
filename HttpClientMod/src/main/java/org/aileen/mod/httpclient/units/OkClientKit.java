package org.aileen.mod.httpclient.units;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public String get() {
        Request request = new Request.Builder().url("http://www.baidu.com").build();

        Call call = okHttpClient.newCall(request);

        try(Response response = call.execute()){
            assert response.body() != null;
            return response.body().string();
        }catch (Exception e){
            log.error("请求异常", e);
            return null;
        }
    }
}
