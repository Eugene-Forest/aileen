package org.aileen.mod.auth.config;

import org.aileen.mod.auth.anno.EncryptRequest;
import org.aileen.mod.auth.entity.DecodeHttpInputMessage;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.helper.AmHttpHelper;
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
        //获取请求头上的加密标识; 需要HTTP请求头来判断请求体是否进行了加密和加密的手段，方便解密
        boolean isHttpEncrypt = false;
        if (httpHeaders.containsKey(AmHttpHelper.ENCRYPT_HEADER)) {
            List<String> encryptValues = httpHeaders.get(AmHttpHelper.ENCRYPT_HEADER);
            if (encryptValues != null && !encryptValues.isEmpty()) {
                String encryptValue = encryptValues.get(0);
                RequestEncryptType matchValue = RequestEncryptType.matchValue(encryptValue);
                if(matchValue != null){
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
                    //默认AES解密
                    decodeHttpInputMessage = new DecodeHttpInputMessage(inputMessage, requestEncryptType);
                    break;
                }
                case Base64: {
                    //Base64解码
                    decodeHttpInputMessage = new DecodeHttpInputMessage(inputMessage);
                    break;
                }
                case RSA: {
                    //默认RSA解密
                    decodeHttpInputMessage = new DecodeHttpInputMessage(inputMessage, requestEncryptType);
                    break;
                }
                default: {
                    throw new RuntimeException("加密类型错误");
                }
            }
            return decodeHttpInputMessage;
        } else {
            throw new RuntimeException("请求头缺少加密标识");
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
