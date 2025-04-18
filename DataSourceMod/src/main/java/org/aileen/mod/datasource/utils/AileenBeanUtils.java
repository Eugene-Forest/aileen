package org.aileen.mod.datasource.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.Nullable;

/**
 * Bean 工具类
 * @author Eugene-Forest
 * {@code @date} 2024/11/19
 */
@Slf4j
public class AileenBeanUtils {

    private ApplicationContext applicationContext;

    public AileenBeanUtils(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 注册单例 Bean
     *
     * @param beanName       Bean 名称
     * @param singletonObject 单例对象
     */
    public void registerSingleton(String beanName, Object singletonObject) {
        try {
            log.warn("Registering singleton bean: " + beanName);
            DefaultListableBeanFactory beanFactory = getBeanFactory();
            beanFactory.registerSingleton(beanName, singletonObject);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register singleton bean: " + beanName, e);
        }
    }

//    /**
//     * 注册 Bean 定义
//     *
//     * @param beanName      Bean 名称
//     * @param requiredType  Bean 类型
//     */
//    public void registerBeanDefinition(String beanName, Class<?> requiredType) {
//        try {
//            DefaultListableBeanFactory beanFactory = getBeanFactory();
//            beanFactory.registerBeanDefinition(beanName, beanFactory.getBeanDefinition(requiredType.getName()));
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to register bean definition: " + beanName, e);
//        }
//    }

    /**
     * 注册 Bean 定义
     * @param beanName Bean 名称
     * @param beanDefinition Bean 定义
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition){
        try {
            DefaultListableBeanFactory beanFactory = getBeanFactory();
            beanFactory.registerBeanDefinition(beanName, beanDefinition);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register bean definition: " + beanName, e);
        }
    }

    /**
     * 根据名称和类型获取 Bean
     *
     * @param beanName      Bean 名称
     * @param requiredType  Bean 类型
     * @param <T>           泛型类型
     * @return              Bean 实例
     */
    @Nullable
    public <T> T getBean(String beanName, Class<T> requiredType) {
        try {
            return applicationContext.getBean(beanName, requiredType);
        } catch (BeansException e) {
            log.error("Failed to get bean: " + beanName, e);
            return null;
        }
    }

    /**
     * 根据类型获取 Bean
     *
     * @param requiredType  Bean 类型
     * @param <T>           泛型类型
     * @return              Bean 实例
     */
    @Nullable
    public <T> T getBean(Class<T> requiredType) {
        try {
            return applicationContext.getBean(requiredType);
        } catch (BeansException e) {
            log.error("Failed to get bean of type: " + requiredType.getName(), e);
            return null;
        }
    }

    /**
     * 获取 DefaultListableBeanFactory
     *
     * @return DefaultListableBeanFactory 实例
     */
    public DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return getBeanFactory();
    }

    /**
     * 私有方法：获取 DefaultListableBeanFactory
     *
     * @return DefaultListableBeanFactory 实例
     */
    private DefaultListableBeanFactory getBeanFactory() {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        AutowireCapableBeanFactory autowireCapableBeanFactory = configurableApplicationContext.getAutowireCapableBeanFactory();
        return (DefaultListableBeanFactory) autowireCapableBeanFactory;
    }
}
