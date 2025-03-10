package org.aileen.mod.datasource.starter;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.config.TCMybatisConfig;
import org.aileen.mod.datasource.units.TCBeanUnit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 初始 Bean 注册触发组件
 *
 * @author Eugene-Forest
 * {@code @date} 2024/11/19
 */
@Component
@Slf4j
public class DataSourceStartRunner implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    //TODO: 这个组件是最开始的执行的组件

    private ApplicationContext applicationContext;

    private Environment environment;

    private static Map<String, String> logicNameMap;
    private static List<String> mustDataSources;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        try {
            environment = applicationContext.getEnvironment();

            TCBeanUnit tcBeanUnit = new TCBeanUnit();
            TCMybatisConfig tcMybatisConfig = new TCMybatisConfig(environment, tcBeanUnit);
            tcMybatisConfig.init();
            logicNameMap = tcMybatisConfig.getLogicNameMap();
            mustDataSources = tcMybatisConfig.getDataSourceNames();
            log.info("DataSourceStartRunner init finished!");
        } catch (Exception e) {
            log.error("DataSourceStartRunner init failure!", e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取Spring上下文
        this.applicationContext = applicationContext;
    }

    public static Map<String, String> getLogicNameMap() {
        return logicNameMap;
    }

    public static List<String> getMustDataSources() {
        return mustDataSources;
    }
}
