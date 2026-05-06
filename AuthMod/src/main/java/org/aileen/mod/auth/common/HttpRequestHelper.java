package org.aileen.mod.auth.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/27
 */
public class HttpRequestHelper {

    public static String getBodyString(HttpServletRequest request) {
        try {
            return StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
