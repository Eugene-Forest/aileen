package org.aileen.mod.auth.entity;

/**
 * 简单签名
 * @author Eugene-Forest
 * {@code @date} 2024/11/27
 */
public class SimpleSignature {
    /** 签名 */
    private String sign;
    /** 时间戳 */
    private String dateTime;

    private boolean isVerify;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }
}
