import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.aileen.mod.httpclient.config.OkHttpConfig;
import org.aileen.mod.httpclient.units.OkClientKit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/16
 */
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {OkHttpConfig.class})
@TestPropertySource(locations = {"classpath:application.yml"})
public class TestClientApp {

//    @Autowired
//    private OkHttpClient okHttpClient;

//    @InjectMocks
//    private OkClientKit okClientKit;

    private MockWebServer mockWebServer;

    private static final Logger log = LoggerFactory.getLogger(TestClientApp.class);

    @BeforeEach
    public void setUp() {
        try {
            mockWebServer = new MockWebServer();
            mockWebServer.start();
        } catch (IOException e) {
            log.error("mockWebServer start error", e);
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            log.error("mockWebServer shutdown error", e);
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() {
//        try {
//            // Arrange
//            String expectedResponse = "Hello, World!";
//            mockWebServer.enqueue(new MockResponse().setBody(expectedResponse));
//
//
//            // Act
//            String actualResponse = okClientKit.get();
//
//            // Assert
//            assertEquals(expectedResponse, actualResponse);
//
//            // Verify the request was made to the correct URL
//            RecordedRequest request = mockWebServer.takeRequest();
//            assertEquals("/", request.getPath());
//        } catch (Exception e) {
//            log.info("test error", e);
//        }
    }

}
