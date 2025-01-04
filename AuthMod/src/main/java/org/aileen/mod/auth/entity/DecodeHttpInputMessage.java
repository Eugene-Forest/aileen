package org.aileen.mod.auth.entity;


import com.alibaba.fastjson.JSON;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.units.CryptoUnits;
import org.aileen.mod.auth.units.SignKeyUnits;
import org.aileen.mod.kit.Base64Kit;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/9
 */
public class DecodeHttpInputMessage implements HttpInputMessage {
    private static final Logger log = LoggerFactory.getLogger(DecodeHttpInputMessage.class);
    private HttpHeaders headers;
    private InputStream body;

    public DecodeHttpInputMessage(HttpInputMessage inputMessage, RequestEncryptType type) throws IOException {
        this.headers = inputMessage.getHeaders();
        String content = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);
        if (type == RequestEncryptType.AES) {
            this.body = IOUtils.toInputStream(CryptoUnits.defaultDecrypt(content));
        } else {
            this.body = IOUtils.toInputStream(SignKeyUnits.defaultDecryptMessage(content));
        }
    }

    public DecodeHttpInputMessage(HttpInputMessage inputMessage, String password) throws IOException {
        this.headers = inputMessage.getHeaders();
        String content = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);
        this.body = IOUtils.toInputStream(CryptoUnits.decrypt(content, password));
    }

    public DecodeHttpInputMessage(HttpInputMessage inputMessage) throws IOException {
        String content = IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8);
        String message = Base64Kit.decode(content);
        Object object = JSON.parseObject(message, Object.class);
        log.debug("DecodeHttpInputMessage: message: {}", message);
        this.body = IOUtils.toInputStream(JSON.toJSONString(object));//默认Base64解码
        log.debug("final");
    }

    @Override
    public InputStream getBody() {
        log.debug("获取body");
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
