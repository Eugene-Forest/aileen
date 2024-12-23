import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.aileen.mod.httpclient.config.OkHttpConfig;
import org.aileen.mod.httpclient.units.OkClientKit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
@ExtendWith(MockitoExtension.class)
public class TestClientApp {

    private OkClientKit okClientKit;

    private MockWebServer mockWebServer;

    private static final Logger log = LoggerFactory.getLogger(TestClientApp.class);

    @BeforeEach
    public void setUp() {
        try {
            mockWebServer = new MockWebServer();
            mockWebServer.start();
            //初始化OkClientKit
            OkHttpConfig okHttpConfig = new OkHttpConfig(10, 10, 10);
            OkHttpClient okHttpClient = okHttpConfig.okHttpClient();
            okClientKit = new OkClientKit(okHttpClient);
            log.debug("setup finish");
        } catch (IOException e) {
            log.error("mockWebServer start error", e);
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            mockWebServer.shutdown();
            log.debug("tearDown finish");
        } catch (IOException e) {
            log.error("mockWebServer shutdown error", e);
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSimpleGet() {
        try {
            // 使用 MockWebServer 模拟服务器响应
            String expectedResponse = "{\"key\":\"value\"}";
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody(expectedResponse));
            // 构造请求 URL
            String url = mockWebServer.url("/getMessage").toString();
            // Kit 发送 GET 请求
            String actualResponse = okClientKit.get(url);
            // 判断结果
            assertEquals(expectedResponse, actualResponse);

        } catch (Exception e) {
            log.info("test error", e);
            throw new RuntimeException(e);
        }
    }
}
