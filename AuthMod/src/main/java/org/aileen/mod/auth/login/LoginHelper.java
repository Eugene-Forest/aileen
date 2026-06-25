package org.aileen.mod.auth.login;

import org.aileen.mod.auth.entity.LoginResult;
import org.aileen.mod.auth.entity.LoginUser;
import org.aileen.mod.auth.entity.AuthAccount;
import org.aileen.mod.auth.mapper.AccountMapper;
import org.aileen.mod.auth.units.TokenUnits;
import org.aileen.mod.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录验证核心逻辑
 *
 * @author Eugene-Forest
 */
@Component
public class LoginHelper {

    private static final Logger log = LoggerFactory.getLogger(LoginHelper.class);

    private static final String TOKEN_PREFIX = "auth:token:";

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 用户登录
     *
     * @param loginUser 登录请求体
     * @return 登录结果
     */
    public LoginResult login(LoginUser loginUser) {
        return login(loginUser, null);
    }

    /**
     * 用户登录（携带登录IP）
     *
     * @param loginUser 登录请求体
     * @param loginIp   登录IP地址
     * @return 登录结果
     */
    public LoginResult login(LoginUser loginUser, String loginIp) {
        if (loginUser == null
                || loginUser.getUsername() == null || loginUser.getUsername().isBlank()
                || loginUser.getPassword() == null || loginUser.getPassword().isBlank()) {
            return LoginResult.fail("用户名或密码不能为空");
        }

        // 从数据库查询账号
        AuthAccount account = accountMapper.selectByUsername(loginUser.getUsername());
        if (account == null) {
            log.warn("登录失败: 账号 [{}] 不存在", loginUser.getUsername());
            return LoginResult.fail("用户名或密码错误");
        }

        // 校验账号状态
        if (account.getStatus() == null || account.getStatus() != 1) {
            log.warn("登录失败: 账号 [{}] 状态异常, status={}", loginUser.getUsername(), account.getStatus());
            return LoginResult.fail("账号已被禁用或锁定");
        }

        // 校验密码（BCrypt）
        if (!PASSWORD_ENCODER.matches(loginUser.getPassword(), account.getPassword())) {
            log.warn("登录失败: 用户 [{}] 密码错误", loginUser.getUsername());
            return LoginResult.fail("用户名或密码错误");
        }

        // 生成唯一tokenId，用于Redis中存储和校验
        String tokenId = UUID.randomUUID().toString();
        String token = TokenUnits.token(loginUser.getUsername(), tokenId);
        if (token == null) {
            return LoginResult.fail("Token 生成失败");
        }

        // 将token存入Redis，设置过期时间
        String redisKey = TOKEN_PREFIX + loginUser.getUsername();
        redisUtil.set(redisKey, token);
        redisUtil.expire(redisKey, TokenUnits.EXPIRE_DATE, TimeUnit.MILLISECONDS);

        // 更新最后登录信息
        try {
            accountMapper.updateLoginInfo(account.getId(), loginIp, LocalDateTime.now());
        } catch (Exception e) {
            log.error("更新用户 [{}] 登录信息失败", loginUser.getUsername(), e);
        }

        log.info("用户 [{}] 登录成功", loginUser.getUsername());
        return LoginResult.success(token, TokenUnits.EXPIRE_DATE);
    }

    /**
     * 用户登出，删除Redis中的token
     *
     * @param username 用户名
     */
    public void logout(String username) {
        if (username != null && !username.isBlank()) {
            String redisKey = TOKEN_PREFIX + username;
            redisUtil.delete(redisKey);
            log.info("用户 [{}] 已登出", username);
        }
    }

    /**
     * 校验token是否有效（与Redis中存储的token比对）
     *
     * @param username 用户名
     * @param token    待校验的token
     * @return 是否有效
     */
    public boolean isTokenValid(String username, String token) {
        String redisKey = TOKEN_PREFIX + username;
        String storedToken = redisUtil.get(redisKey);
        return token.equals(storedToken);
    }
}
