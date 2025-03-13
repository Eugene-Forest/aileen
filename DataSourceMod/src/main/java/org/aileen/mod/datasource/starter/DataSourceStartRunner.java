package org.aileen.mod.datasource.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

/**
 * 初始 Bean 注册触发组件
 *
 * @author Eugene-Forest
 * {@code @date} 2024/11/19
 */
//@Component
@Slf4j
public class DataSourceStartRunner implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Environment environment;

    private static Map<String, String> logicNameMap;
    private static List<String> mustDataSources;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        try {
            environment = applicationContext.getEnvironment();

//            TcBeanUnit tcBeanUnit = applicationContext.getBean(TcBeanUnit.class);
//            TcMybatisLoader tcMybatisLoader = new TcMybatisLoader(environment,tcBeanUnit);
//            tcMybatisLoader.init();
//            logicNameMap = tcMybatisLoader.getLogicNameMap();
//            mustDataSources = tcMybatisLoader.getDataSourceNames();
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
