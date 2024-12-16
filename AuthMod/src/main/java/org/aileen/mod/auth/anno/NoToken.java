package org.aileen.mod.auth.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法体或者类上添加此注解将不会进入签名验证
 *
 * @author Eugene-Forest
 * {@code @date} 2024/11/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NoToken {
}
//理论上，自定义注解需要通过切面控制注解行为，但是这里的注解似乎只有标识的作用，
// 真正起作用的是拦截器里面通过方法体是否含有此注解来进行签名验证