package org.aileen.mod.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aileen.mod.auth.anno.EncryptRequest;
import org.aileen.mod.auth.anno.NoToken;
import org.aileen.mod.auth.enums.RequestEncryptType;
import org.aileen.mod.auth.helper.AmHttpHelper;
import org.aileen.mod.auth.units.AnnoUnits;
import org.aileen.mod.auth.units.TokenUnits;
import org.aileen.mod.auth.verify.DefaultVerifySign;
import org.aileen.mod.crypto.CryptoUnits;
import org.aileen.mod.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

/**
 * 网络请求拦截器 - 支持Token登录验证
 *
 * @author Eugene-Forest
 */
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    private static final String TOKEN_PREFIX = "auth:token:";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.warn("拦截处理URL:" + request.getRequestURL().toString());

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 1. 检查是否标注了@NoToken，跳过Token校验
            NoToken noToken = AnnoUnits.getAnno(handlerMethod, NoToken.class);
            if (noToken != null) {
                log.debug("方法 [{}] 标注 @NoToken，跳过Token校验", handlerMethod.getMethod().getName());
                return true;
            }

            // 2. 检查是否需要RSA签名验证
            EncryptRequest encryptRequest = AnnoUnits.getAnno(handlerMethod, EncryptRequest.class);
            if (encryptRequest != null && encryptRequest.encryptType() == RequestEncryptType.RSA) {
                boolean isVerify = DefaultVerifySign.defaultVerify(request);
                if (isVerify) {
                    String password = CryptoUnits.generatePassword();
                    redisUtil.set("password", password);
                    redisUtil.expire("password", 1, TimeUnit.SECONDS);
                    return true;
                } else {
                    writeUnauthorizedResponse(response, "验签失败", "签名验证失败，请检查请求签名");
                    return false;
                }
            }

            // 3. Token登录验证
            String authHeader = request.getHeader(AmHttpHelper.AUTHORIZATION_HEADER);
            if (authHeader == null || authHeader.isBlank()) {
                writeUnauthorizedResponse(response, "缺少认证信息", "请在请求头中携带 Authorization Token");
                return false;
            }

            // 提取Token（支持 "Bearer xxx" 格式）
            String token = authHeader;
            if (authHeader.startsWith(AmHttpHelper.BEARER_PREFIX)) {
                token = authHeader.substring(AmHttpHelper.BEARER_PREFIX.length());
            }

            // 4. 验证JWT签名
            if (!TokenUnits.verify(token)) {
                writeUnauthorizedResponse(response, "Token无效", "Token 验证失败，可能已过期");
                return false;
            }

            // 5. 解析用户名
            String username = TokenUnits.getUsername(token);
            if (username == null) {
                writeUnauthorizedResponse(response, "Token无效", "无法解析用户信息");
                return false;
            }

            // 6. 校验Redis中Token是否有效（防止登出后Token仍可用）
            String redisKey = TOKEN_PREFIX + username;
            String storedToken = redisUtil.get(redisKey);
            if (storedToken == null || !storedToken.equals(token)) {
                writeUnauthorizedResponse(response, "Token已失效", "请重新登录");
                return false;
            }

            // 将用户名写入request属性，供Controller使用
            request.setAttribute("currentUsername", username);
            log.debug("用户 [{}] Token校验通过", username);
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    /**
     * 写入401未授权响应
     */
    private void writeUnauthorizedResponse(HttpServletResponse response, String error, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"" + error + "\",\"message\":\"" + message + "\"}");
    }
}
