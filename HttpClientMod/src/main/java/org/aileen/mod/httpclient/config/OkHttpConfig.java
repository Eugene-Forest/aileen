package org.aileen.mod.httpclient.config;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * OkHTTP 配置类
 *
 * @author Eugene-Forest
 * {@code @date} 2024/12/21
 */
@Configuration
public class OkHttpConfig {

    private static final Logger log = LoggerFactory.getLogger(OkHttpConfig.class);

    @Value("${okhttp.connectTimeout}")
    private int connectTimeout;

    @Value("${okhttp.readTimeout}")
    private int readTimeout;

    @Value("${okhttp.writeTimeout}")
    private int writeTimeout;


    public OkHttpConfig() {
    }

    public OkHttpConfig(int connectTimeout, int readTimeout, int writeTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        log.debug("okHttpClient init; connectTimeout:{},readTimeout:{},writeTimeout:{}", connectTimeout, readTimeout, writeTimeout);
        return new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build();
    }
}
