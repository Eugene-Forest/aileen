package org.aileen.mod.auth.interceptor;

import org.aileen.mod.auth.anno.EncryptRequest;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.units.AnnoUnits;
import org.aileen.mod.crypto.CryptoUnits;
import org.aileen.mod.auth.verify.DefaultVerifySign;
import org.aileen.mod.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求拦截器
 *
 * @author Eugene-Forest
 * {@code @date} 2024/11/6
 * {@code @project} TuTorSelenium
 */
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前执行，允许您在请求被传递给控制器之前进行一些预处理操作，
        // 比如鉴权、日志记录等。如果返回true，则继续执行请求处理链；如果返回false，则中断请求处理。
        String servletPath = request.getServletPath();
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.warn("拦截处理URL:" + request.getRequestURL().toString());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            NoToken noToken = AnnoUnits.getAnno(handlerMethod, NoToken.class);
//            if(noToken == null){
//
//            } else{
//                log.debug("不需要签名校验[{}]请求。", handlerMethod.getMethod().getName());
//            }
            //需要签名校验
            log.debug("校验[{}]请求。", handlerMethod.getMethod().getName());
            EncryptRequest encryptRequest = AnnoUnits.getAnno(handlerMethod, EncryptRequest.class);
            if (encryptRequest == null) {
                //不需要加密解密
                return true;
            } else {
                //需要加密解密
                if (encryptRequest.encryptType() == RequestEncryptType.RSA) {
                    boolean isVerify = DefaultVerifySign.defaultVerify(request);
                    if (isVerify) {
                        //生成加密密匙
                        String password = CryptoUnits.generatePassword();
                        redisUtil.set("password", password);
                        redisUtil.expire("password", 1, TimeUnit.SECONDS);
                        return true;
                    } else {
                        throw new RuntimeException("验签失败");
                    }
                } else {
                    return true;
//                    String password = redisUtil.get("password");
//                    if (password == null) {
//                        //throw message
//                        return false;
//                    } else {
//                        //TODO: 需要校验token
//                        return true;
//                    }
                }
            }
        }
        return true; // 返回true继续执行，返回false中断执行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后、视图渲染之前执行。您可以在这里对模型和视图进行操作，但不能改变视图。通常用于修改响应内容、添加额外的模型数据等。
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在整个请求完成后执行，包括视图渲染。可以用于清理资源、记录请求处理时间等操作。
    }
}
