package org.aileen.mod.datasource.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.model.DataSourceSet;
import org.aileen.mod.datasource.starter.DataSourceStartRunner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @author qaz22
 * {@code @date} 2025/4/11
 * {@code @project} aileen
 */
@AutoConfiguration(before = DataSourceStartRunner.class)
@Configurable
@Slf4j
public class DataSourceDatabindConfig {

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

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    @ConditionalOnProperty(name = "datasource-mod.nacos-enable", havingValue = "false")
    @Bean
    public DataSourceSet dataSourceSetLocal() {
        try {
            log.info("Loading configuration from local file: {}", filePath);
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            DataSourceSet dataSourceSet = objectMapper.readValue(resource.getInputStream(), new TypeReference<DataSourceSet>() {
            });
            return dataSourceSet;
        } catch (IOException e) {
            log.error("Failed to load local configuration: {}", e.getMessage(), e);
            return new DataSourceSet(); // 或者返回一个默认的 DataSourceSet 实例
        }
    }

    @ConditionalOnProperty(name = "datasource-mod.nacos-enable", havingValue = "true")
    @Bean
    public DataSourceSet datasourceSetNacos() {
        try {
            ConfigService configService = NacosFactory.createConfigService(nacosServerAddr);
            log.info("Loading configuration from Nacos");
            String configContent = configService.getConfig(dataId, group, 5000);
            DataSourceSet dataSourceSet = objectMapper.readValue(configContent, new TypeReference<DataSourceSet>() {
            });
            return dataSourceSet;
        } catch (NacosException | IOException e) {
            log.error("Failed to load Nacos configuration: {}", e.getMessage(), e);
            return new DataSourceSet(); // 或者返回一个默认的 DataSourceSet 实例
        }
    }
}
