package org.aileen.mod.auth.config;

import com.alibaba.fastjson.JSON;
import org.aileen.mod.auth.anno.EncryptRequest;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.units.AnnoUnits;
import org.aileen.mod.auth.units.CryptoUnits;
import org.aileen.mod.auth.units.SignKeyUnits;
import org.aileen.mod.kit.Base64Kit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/9
 */
@RestControllerAdvice(annotations = EncryptRequest.class)
//@RestControllerAdvice(basePackages = {"org.tutor"})
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(EncodeResponseBodyAdvice.class);

//    @Autowired
//    private RedisUtil redisUtil;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return AnnoUnits.ifExistAnno(returnType.getMethod(), EncryptRequest.class);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.debug("EncodeResponseBodyAdvice beforeBodyWrite");
        if (body == null) {
            return null;
        }
        Method method = returnType.getMethod();
        if (method == null) {
            log.warn("method is null");
            throw new RuntimeException("method is null");
        }
        EncryptRequest encryptRequest = AnnoUnits.getAnno(method, EncryptRequest.class);
        if (encryptRequest == null) {
            return body;
        }
        // 否则进行加密
        RequestEncryptType type = encryptRequest.encryptType();
        log.debug("接口返回加密 encryptType:{}", type);
        switch (type) {
            case AES: {
                return CryptoUnits.defaultEncrypt(JSON.toJSONString(body));
            }
            case RSA: {
                return SignKeyUnits.defaultSignMessage(JSON.toJSONString(body));
            }
            case Base64: {
                return Base64Kit.encode(JSON.toJSONString(body));
            }
            default: {
                throw new RuntimeException("不支持的加密类型");
            }
        }
    }
}
