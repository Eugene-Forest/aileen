package org.aileen.mod.datasource.starter;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.units.AileenBeanUnit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.context.properties.ConfigurationPropertiesBeans;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

/**
 * 初始 Bean 注册触发组件
 *
 * @author Eugene-Forest
 * {@code @date} 2024/11/19
 */
@Configuration
@Slf4j
public class DataSourceStartRunner implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Environment environment;

    @Bean
    public AileenBeanUnit aileenBeanUnit() {
        AileenBeanUnit aileenBeanUnit = new AileenBeanUnit(applicationContext);
        log.debug("-- create AileenBeanUnit Bean --");
        return aileenBeanUnit;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        try {
            environment = applicationContext.getEnvironment();
        } catch (Exception e) {
            log.error("DataSourceStartRunner init failure!", e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            AileenBeanUnit aileenBeanUnit = beanFactory.getBean(AileenBeanUnit.class);
            log.debug("-- AileenBeanUnit initialized in postProcessBeanFactory --");
        } catch (BeansException e) {
            log.warn("AileenBeanUnit not found, creating dynamically --", e);
            AileenBeanUnit aileenBeanUnit = new AileenBeanUnit(applicationContext);
            beanFactory.registerSingleton("aileenBeanUnit", aileenBeanUnit);
            log.debug("-- Dynamically created and registered AileenBeanUnit Bean --");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        log.debug("-- DataSourceStartRunner.setApplicationContext --");
    }
}
