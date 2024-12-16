package org.aileen.mod.auth.units;

import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 注解工具类
 * @author Eugene-Forest
 * {@code @date} 2024/12/10
 */
public class AnnoUnits {

    /**
     * 通用的获取方法或者方法的类上是否存在某注解的方法
     *
     * @param handlerMethod 搜索的方法
     * @param annoClass     需要搜索的注解的类型
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnno(HandlerMethod handlerMethod, Class<T> annoClass) {
        Method method = handlerMethod.getMethod();
        return getAnno(method, annoClass);
    }

    /**
     * 通用的获取方法或者方法的类上是否存在某注解的方法
     *
     * @param method 搜索的方法
     * @param annoClass     需要搜索的注解的类型
     * @param <T>
     * @return
     */
    public static <T extends Annotation> T getAnno(Method method, Class<T> annoClass) {
        T annoClassItem = method.getAnnotation(annoClass);//方法注解
        if (annoClassItem == null) {
            Class<?> aClass = method.getDeclaringClass();
            annoClassItem = aClass.getAnnotation(annoClass);//类注解
        }
        return annoClassItem;
    }

    /**
     * 通用的获取方法或者方法的类上是否存在某注解的方法
     * @param handlerMethod
     * @param annoClass
     * @return
     * @param <T>
     */
    public static <T extends Annotation> boolean ifExistAnno(HandlerMethod handlerMethod, Class<T> annoClass) {
        T annoClassItem = getAnno(handlerMethod, annoClass);
        return annoClassItem != null;
    }

    /**
     * 通用的获取方法或者方法的类上是否存在某注解的方法
     * @param method
     * @param annoClass
     * @return
     * @param <T>
     */
    public static <T extends Annotation> boolean ifExistAnno(Method method, Class<T> annoClass) {
        T annoClassItem = getAnno(method, annoClass);
        return annoClassItem != null;
    }

}
