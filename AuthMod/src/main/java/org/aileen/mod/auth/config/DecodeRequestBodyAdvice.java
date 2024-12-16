package org.aileen.mod.auth.config;

import org.aileen.mod.auth.anno.EncryptRequest;
import org.aileen.mod.auth.entity.DecodeHttpInputMessage;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.units.AnnoUnits;
import org.aileen.mod.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 处理请求体
 *
 * @author Eugene-Forest
 * {@code @date} 2024/12/9
 */
//@RestControllerAdvice(annotations = EncryptRequest.class)
//@RestControllerAdvice(basePackages = {"org.tutor"})
public class DecodeRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private static final Logger log = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return AnnoUnits.ifExistAnno(methodParameter.getMethod(), EncryptRequest.class);
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        //在请求体转换之前执行
        log.debug("DecodeRequestBodyAdvice: beforeBodyRead");
        HttpHeaders httpHeaders = inputMessage.getHeaders();
        log.debug("DecodeRequestBodyAdvice: httpHeaders: {}", httpHeaders.toString());
        boolean isDecode = false;
        Method method = parameter.getMethod();
        if (method == null) {
            log.warn("method is null");
            throw new RuntimeException("method is null");
        }
        EncryptRequest encryptRequest = AnnoUnits.getAnno(method, EncryptRequest.class);
        RequestEncryptType type = encryptRequest.encryptType();
        isDecode = encryptRequest.decryptRequestBody();
        if (isDecode) {
            if (type == RequestEncryptType.AES) {
                String password = redisUtil.get("password");
                if (password != null) {
                    return new DecodeHttpInputMessage(inputMessage, password);
                } else {
                    //TODO: throw error message
                    throw new RuntimeException("password is null, not login");
                }
            } else {
                return new DecodeHttpInputMessage(inputMessage, type);
            }
        } else {
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("DecodeRequestBodyAdvice: afterBodyRead");
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("DecodeRequestBodyAdvice: handleEmptyBody");
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }
}
