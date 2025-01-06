package org.aileen.mod.auth.config;

import org.aileen.mod.auth.anno.EncryptRequest;
import org.aileen.mod.auth.entity.DecodeHttpInputMessage;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.units.AnnoUnits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 处理请求体
 *
 * @author Eugene-Forest
 * {@code @date} 2024/12/9
 */
@RestControllerAdvice(annotations = EncryptRequest.class)
//@RestControllerAdvice(basePackages = {"org.tutor"})
public class DecodeRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private static final Logger log = LoggerFactory.getLogger(DecodeRequestBodyAdvice.class);

//    @Autowired
//    private RedisUtil redisUtil;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return AnnoUnits.ifExistAnno(methodParameter.getMethod(), EncryptRequest.class);
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        //在请求体转换之前执行
        log.debug("DecodeRequestBodyAdvice: beforeBodyRead");
        Method method = parameter.getMethod();
        if (method == null) {
            log.warn("method is null");
            throw new RuntimeException("method is null");
        }
        EncryptRequest encryptRequest = AnnoUnits.getAnno(method, EncryptRequest.class);
        if (encryptRequest == null) {
            return inputMessage;
        }
        RequestEncryptType requestEncryptType = encryptRequest.encryptType();
        HttpHeaders httpHeaders = inputMessage.getHeaders();
        //获取请求头上的加密标识
        boolean isHttpEncrypt = false;
        if (httpHeaders.containsKey("encrypt")) {
            List<String> encryptValues = httpHeaders.get("encrypt");
            if (encryptValues != null && !encryptValues.isEmpty()) {
                String encryptValue = encryptValues.get(0);
                RequestEncryptType matchValue = RequestEncryptType.matchValue(encryptValue);
                if(matchValue != null){
                    if (!encryptRequest.decryptRequestBody()) {
                        throw new RuntimeException("接口的请求体没有定义加密");
                    }
                    if(requestEncryptType != matchValue){
                        throw new RuntimeException("加密类型与接口方法体的加密类型不一致");
                    }
                    isHttpEncrypt = true;

                }
            }
        }
        if (isHttpEncrypt) {
            HttpInputMessage decodeHttpInputMessage;
            switch (requestEncryptType) {
                case AES: {
                    //TODO: get password from redis；待验证
                    decodeHttpInputMessage = new DecodeHttpInputMessage(inputMessage, "password");
                    break;
                }
                case Base64: {
                    decodeHttpInputMessage = new DecodeHttpInputMessage(inputMessage);
                    break;
                }
                case RSA: {
                    //TODO: 待验证
                    decodeHttpInputMessage = new DecodeHttpInputMessage(inputMessage, type);
                    break;
                }
                default: {
                    throw new RuntimeException("加密类型错误");
                }
            }
            return decodeHttpInputMessage;
        } else {
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("DecodeRequestBodyAdvice: afterBodyRead");
        try {

        } catch (Exception e) {
            log.error("DecodeRequestBodyAdvice: afterBodyRead error", e);
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("DecodeRequestBodyAdvice: handleEmptyBody");
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }
}
