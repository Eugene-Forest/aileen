package org.aileen.mod.auth.enums;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/12/9
 */
public enum RequestEncryptType {
    AES("AES"),
    RSA("RSA"),
    Base64("Base64"),
    ;

    private String value;

    RequestEncryptType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RequestEncryptType matchValue(String value) {
        for (RequestEncryptType type : RequestEncryptType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
