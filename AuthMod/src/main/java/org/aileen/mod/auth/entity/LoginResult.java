package org.aileen.mod.auth.entity;

/**
 * 登录响应体
 *
 * @author Eugene-Forest
 */
public class LoginResult {
    private int code;
    private String message;
    private String token;
    private long expireIn;

    public LoginResult() {}

    public LoginResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public LoginResult(int code, String message, String token, long expireIn) {
        this.code = code;
        this.message = message;
        this.token = token;
        this.expireIn = expireIn;
    }

    public static LoginResult success(String token, long expireIn) {
        return new LoginResult(200, "登录成功", token, expireIn);
    }

    public static LoginResult fail(String message) {
        return new LoginResult(401, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }
}
