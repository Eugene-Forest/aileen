package org.aileen.mod.auth.units;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token 工具类
 *
 * @author Eugene-Forest
 */
public class TokenUnits {

    // 设置过期时间（30天，单位毫秒）
    public static final long EXPIRE_DATE = 30 * 60 * 100000L;

    // token秘钥
    private static final String TOKEN_SECRET = "ZCfasfhuaUUHufguGuwu2020BQWE";

    /**
     * 生成JWT Token
     *
     * @param username 用户名
     * @param tokenId  Token唯一标识
     * @return JWT Token字符串
     */
    public static String token(String username, String tokenId) {
        String token = "";
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("tokenId", tokenId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    /**
     * 验证Token签名是否有效
     *
     * @param token JWT Token字符串
     * @return 是否有效
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从Token中解析用户名
     *
     * @param token JWT Token字符串
     * @return 用户名，解析失败返回null
     */
    public static String getUsername(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String username = "zhangsan";
        String tokenId = "test-token-id";
        String token = token(username, tokenId);
        System.out.println("Token: " + token);
        boolean isValid = verify(token);
        System.out.println("验证结果: " + isValid);
        String parsedUsername = getUsername(token);
        System.out.println("解析用户名: " + parsedUsername);
    }
}
