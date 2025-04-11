package org.aileen.mod.datasource.starter;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.model.DataSourceSet;
import org.aileen.mod.datasource.units.AileenBeanUnit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

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

    @Value("${datasource-mod.nacos-enable:false}")
    private boolean nacosEnable;

    @Value("${datasource-mod.file-path}")
    private String filePath;

    @Value("${nacos.server-addr}")
    private String nacosServerAddr;

    @Value("${nacos.data-id}")
    private String dataId;

    @Value("${nacos.group}")
    private String group;


    @Deprecated
    @Bean
    public static AileenBeanUnit aileenBeanUnit(ApplicationContext applicationContext) {
        return new AileenBeanUnit(applicationContext);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.debug("-- DataSourceStartRunner.postProcessBeanDefinitionRegistry --");
        try {
            environment = applicationContext.getEnvironment();
        } catch (Exception e) {
            log.error("DataSourceStartRunner init failure!", e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        AileenBeanUnit aileenBeanUnit;
        try {
            aileenBeanUnit = beanFactory.getBean(AileenBeanUnit.class);
            log.debug("-- AileenBeanUnit initialized in postProcessBeanFactory --");
        } catch (BeansException e) {
            log.warn("AileenBeanUnit not found, creating dynamically --", e);
            aileenBeanUnit = new AileenBeanUnit(applicationContext);
            beanFactory.registerSingleton("aileenBeanUnit", aileenBeanUnit);
            log.debug("-- Dynamically created and registered AileenBeanUnit Bean --");
        }
        //获取所有数据源配置，并注册到容器中, 然后创建 AileenMybatisConfig Bean 进行 DataSource 的统一注册
        try {
            //TODO: 只能先不关工具自动装配的事情，在定义和实例化中将 AileenMybatisConfig 所需要的配置和工具创建出来，实例化这个配置类
            // 然后想一下如何做到把动态创建的bean能够被IDEA识别到，做到编辑器不报错
            // ..做一个动态创建的config,然后根据条件来动态创建对应的bean，这些创建逻辑都是假的，应为必定存在对应的bean存在的条件，以此做到避免IDEA报错提示
            DataSourceSet dataSourceSet = beanFactory.getBean(DataSourceSet.class);

        }catch (BeansException e) {
            log.error("-- DataSourceSet not found --", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        log.debug("-- DataSourceStartRunner.setApplicationContext --");
    }
}
