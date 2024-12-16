package org.aileen.mod.auth.anno;


import org.aileen.mod.auth.enums.RequestEncryptType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 网络请求的出入数据加密注解
 *
 * @author Eugene-Forest
 * {@code @date} 2024/12/10
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptRequest {
    /**
     * RequestBody 是否解密，默认解密
     */
    boolean decryptRequestBody() default true;

    /**
     * 出参是否加密，默认加密
     */
    boolean encryptResult() default true;

    /**
     * 加密类型；默认为 AES 加密
     */
    RequestEncryptType encryptType() default RequestEncryptType.AES;
}
