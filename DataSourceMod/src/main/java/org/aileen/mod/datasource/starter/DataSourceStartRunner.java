package org.aileen.mod.datasource.starter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.config.AileenMybatisConfig;
import org.aileen.mod.datasource.exceptions.DataSourceModExceptionFactory;
import org.aileen.mod.datasource.loader.AccountSetDataLoader;
import org.aileen.mod.datasource.model.DataSourceConfigDto;
import org.aileen.mod.datasource.model.DataSourceSet;
import org.aileen.mod.datasource.model.DriverComConfigDto;
import org.aileen.mod.datasource.utils.AileenBeanUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * 初始 Bean 注册触发组件
 *
 * @author Eugene-Forest
 * {@code @date} 2024/11/19
 */
@Component
@Slf4j
public class DataSourceStartRunner implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Environment environment;

    private static final String DataSourceFilePath = "datasource-mod.file-path";
    private static final String NacosEnable = "datasource-mod.nacos-enable";
    private static final String NacosDataId = "datasource-mod.nacos-data-id";
    private static final String NacosGroup = "datasource-mod.nacos-group";
    private static final String NacosNamespace = "datasource-mod.nacos-namespace";
    private static final String NacosServerAddr = "datasource-mod.nacos-server-addr";
    private static final String NacosUsername = "datasource-mod.nacos-username";
    private static final String NacosPassword = "datasource-mod.nacos-password";

    private static final String DataSourceConfigDBServer = "datasource-mod.config.dbServer";
    private static final String DataSourceConfigDbName = "datasource-mod.config.dbName";
    private static final String DataSourceConfigMssqlDriverClassName = "datasource-mod.config.mssql.driverClassName";
    private static final String DataSourceConfigMysqlDriverClassName = "datasource-mod.config.mysql.driverClassName";
    private static final String DataSourceConfigMssqlJdbcUrl = "datasource-mod.config.mssql.jdbcUrl";
    private static final String DataSourceConfigMysqlJdbcUrl = "datasource-mod.config.mysql.jdbcUrl";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        // Take-care: 如果通过手动创建单例 Bean 的方式，会使得部分实现了特性接口的 Bean, 不会随着 Spring 的自动装配自动调用对应的方法。
        // 例如： DynamicDataSource 不会自动调用 afterPropertiesSet 方法，从而导致动态数据源的初始化异常。
        log.debug("-- DataSourceStartRunner.postProcessBeanDefinitionRegistry --");
        if (applicationContext == null) {
            log.error("-- DataSourceStartRunner.postProcessBeanDefinitionRegistry -- applicationContext is null");
            DataSourceModExceptionFactory.raiseException("applicationContext is null, check [DataSourceStartRunner].");
        }
        try {
            environment = applicationContext.getEnvironment();
        } catch (Exception e) {
            log.error("DataSourceStartRunner init failure!", e);
        }
        AileenBeanUtils aileenBeanUtils = new AileenBeanUtils(applicationContext);
        //获取所有数据源配置，并注册到容器中, 然后创建 AileenMybatisConfig Bean 进行 DataSource 的统一注册
        try {
            boolean nacosEnable = environment.getProperty(NacosEnable, Boolean.class, false);
            ObjectMapper objectMapper = new ObjectMapper();
            DataSourceSet dataSourceSet = null;
            if (nacosEnable) {
                String nacosServerAddr = environment.getProperty(NacosServerAddr, String.class, "127.0.0.1:8848");
                String dataId = environment.getProperty(NacosDataId, String.class, "datasource-set");
                String group = environment.getProperty(NacosGroup, String.class, "DEFAULT_GROUP");
                String namespace = environment.getProperty(NacosNamespace, String.class, "");
                String username = environment.getProperty(NacosUsername, String.class, "");
                String password = environment.getProperty(NacosPassword, String.class, "");

                Properties properties = new Properties();
                properties.put("serverAddr", nacosServerAddr);
                if (!namespace.isEmpty()) {
                    properties.put("namespace", namespace); // 如果 namespace 不为空，则设置
                }
                if (!username.isEmpty() && !password.isEmpty()) {
                    properties.put("username", username);
                    properties.put("password", password);
                }
                // TODO: 问题： 手动创建 Nacos 服务获取配置，自动创建了监听配置变化的服务，目前没有考虑好如何实现动态数据源配置的动态动态变化，需要关闭监听服务
                ConfigService configService = NacosFactory.createConfigService(properties);
                String configContent = configService.getConfig(dataId, group, 5000);
                if (StringUtils.isBlank(configContent)) {
                    log.error("Nacos configuration not found for dataId: {}, group: {}, namespace: {}", dataId, group, namespace);
                    DataSourceModExceptionFactory.raiseException("Nacos configuration not found");
                }
                log.debug("Loading configuration from Nacos with dataId: {}, group: {}, namespace: {}", dataId, group, namespace);
                dataSourceSet = objectMapper.readValue(configContent, new TypeReference<DataSourceSet>() {
                });
                // 继续处理 dataSourceSe
            } else {
                String dataSourceFilePath = environment.getProperty(DataSourceFilePath, String.class, "classpath:datasource/datasourceset.json");
                Resource resource = applicationContext.getResource(dataSourceFilePath);
                dataSourceSet = objectMapper.readValue(resource.getInputStream(), new TypeReference<DataSourceSet>() {
                });
            }
            if (dataSourceSet == null) {
                // 账套数据载入失败
                DataSourceModExceptionFactory.raiseException("DataSourceSet not create");
            }
            log.debug(dataSourceSet.toString());
            // 手动绑定配置到 DataSourceConfigDto
            DataSourceConfigDto manualDataSourceConfigDto = new DataSourceConfigDto();
            manualDataSourceConfigDto.setDbServer(environment.getProperty(DataSourceConfigDBServer, String.class, "[dbServer]"));
            manualDataSourceConfigDto.setDbName(environment.getProperty(DataSourceConfigDbName, String.class, "[dbName]"));

            DriverComConfigDto mssqlConfig = new DriverComConfigDto();
            mssqlConfig.setJdbcUrl(environment.getProperty(DataSourceConfigMssqlJdbcUrl, String.class, "jdbc:sqlserver://[dbServer]:1433;databaseName=[dbName];encrypt=false;trustServerCertificate=true"));
            mssqlConfig.setDriverClassName(environment.getProperty(DataSourceConfigMssqlDriverClassName, String.class, "com.microsoft.sqlserver.jdbc.SQLServerDriver"));
            manualDataSourceConfigDto.setMssql(mssqlConfig);

            DriverComConfigDto mysqlConfig = new DriverComConfigDto();
            mysqlConfig.setJdbcUrl(environment.getProperty(DataSourceConfigMysqlJdbcUrl, String.class, "jdbc:mysql://[dbServer]:3306/[dbName]?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&allowPublicKeyRetrieval=true"));
            mysqlConfig.setDriverClassName(environment.getProperty(DataSourceConfigMysqlDriverClassName, String.class, "com.mysql.cj.jdbc.Driver"));
            manualDataSourceConfigDto.setMysql(mysqlConfig);

            AccountSetDataLoader accountSetDataLoader = new AccountSetDataLoader(dataSourceSet);
            AileenMybatisConfig aileenMybatisConfig = new AileenMybatisConfig(
                    environment, aileenBeanUtils, accountSetDataLoader, manualDataSourceConfigDto);
            aileenMybatisConfig.init();
            aileenBeanUtils.registerSingleton("aileenMybatisConfig", aileenMybatisConfig);
            aileenBeanUtils.registerSingleton("accountSetDataLoader", accountSetDataLoader);
            aileenBeanUtils.registerSingleton("dataSourceSet", dataSourceSet);
            aileenBeanUtils.registerSingleton("aileenBeanUnit", aileenBeanUtils);
            log.debug("-- Dynamically created and registered AileenMybatisConfig Bean --");
        } catch (IOException e) {
            log.error("-- DataSourceSet not found --", e);
            DataSourceModExceptionFactory.raiseException(e);
        } catch (NacosException e) {
            log.error("-- NacosException --", e);
            DataSourceModExceptionFactory.raiseException(e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        log.debug("DataSourceStartRunner.postProcessBeanFactory --");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        log.debug("-- DataSourceStartRunner.setApplicationContext --");
    }
}
