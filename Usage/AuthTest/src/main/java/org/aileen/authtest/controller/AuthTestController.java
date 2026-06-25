package org.aileen.authtest.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.aileen.mod.auth.anno.NoToken;
import org.aileen.mod.auth.entity.LoginResult;
import org.aileen.mod.auth.entity.LoginUser;
import org.aileen.mod.auth.login.LoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录验证测试控制器
 */
@RestController
@RequestMapping("/auth-test")
public class AuthTestController {

    @Autowired
    private LoginHelper loginHelper;

    /**
     * 登录接口 - 不需要Token验证
     */
    @NoToken
    @PostMapping("/login")
    public LoginResult login(@RequestBody LoginUser loginUser) {
        return loginHelper.login(loginUser);
    }

    /**
     * 登出接口 - 不需要Token验证（也可去掉@NoToken让其走拦截器验证）
     */
    @NoToken
    @GetMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        String username = (String) request.getAttribute("currentUsername");
        Map<String, Object> result = new HashMap<>();
        if (username != null) {
            loginHelper.logout(username);
            result.put("code", 200);
            result.put("message", "登出成功");
        } else {
            result.put("code", 401);
            result.put("message", "未登录");
        }
        return result;
    }

    /**
     * 获取用户信息 - 需要Token验证
     */
    @GetMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        String username = (String) request.getAttribute("currentUsername");
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("username", username);
        result.put("message", "获取用户信息成功");
        return result;
    }
}
