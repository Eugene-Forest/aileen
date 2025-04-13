package org.aileen.mod.datasource.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.dynamic.DynamicDataSource;
import org.aileen.mod.datasource.exceptions.DataSourceModExceptionFactory;
import org.aileen.mod.datasource.loader.AccountSetDataLoader;
import org.aileen.mod.datasource.model.AccountSet;
import org.aileen.mod.datasource.model.DataSourceConfigDto;
import org.aileen.mod.datasource.model.DataSourceData;
import org.aileen.mod.datasource.utils.AileenBeanUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.io.IOException;
import java.util.*;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
@Slf4j
public class AileenMybatisConfig {

    private Environment environment;

    private AileenBeanUtils aileenBeanUtils;

    private AccountSetDataLoader accountSetDataLoader;

    private DataSourceConfigDto dataSourceConfigDto;

    private final String logicNames = "datasource-mod.logic.names";
    private final String logicMap = "datasource-mod.logic.maps.";
    private final String default_dataSource = "datasource-mod.logic.default";
    private final String mybatisLocation = "datasource-mod.mapper-locations.";
    private final String mybatisBasePackage = "datasource-mod.mapper-base-package.";
    private final String mybatisConfig = "datasource-mod.mybatis-config";
    private final String config = "datasource-mod.config";

    private List<String> dataSourceNames;

    private String defaultAccount;


    public AileenMybatisConfig(Environment environment, AileenBeanUtils aileenBeanUtils, AccountSetDataLoader accountSetDataLoader, DataSourceConfigDto dataSourceConfigDto) {
        this.environment = environment;
        this.aileenBeanUtils = aileenBeanUtils;
        this.accountSetDataLoader = accountSetDataLoader;
        this.dataSourceConfigDto = dataSourceConfigDto;
    }

    /**
     * key: dbName, value: alias name
     */
//    private Map<String, String> logicNameMap;
    public void init() {
        try {
            log.debug("-- DataSourceMod MybatisConfig init --");
//        Map<String, String> logicNameMap = getLogicNameMap();
            // 在创建数据源之前，检查账套数据与配置文件是否构成合理映射

            // 一个Map对应一个Dy
            // i一个Account对应一个Dy的Ele
            //
            for (String dsName : getDataSourceNames()) {
                log.debug("-- Create {} DataSource --", dsName);
                String mapperLocations = environment.getProperty(mybatisLocation + dsName);
                String mapperBasePackage = environment.getProperty(mybatisBasePackage + dsName);
                if (mapperLocations == null || mapperBasePackage == null) {
                    DataSourceModExceptionFactory.raiseException("没有定义 {} 数据源的Mapper扫描配置", dsName);
                }
                Resource[] mapperLocationResources = getMapperLocations(mapperLocations);
                String defaultAccountSetName = accountSetDataLoader.getDefaultAccountSetName();
                //注册 BeanDefinition ，分别注册 DataSource 、 SqlSessionFactory 、 事务管理器 的 BeanDefinition
                // 创建数据源定义
                BeanDefinition dynamicDataSourceBeanDefinition = createDynamicDataSourceBeanDefinition(dsName, defaultAccountSetName);
                // 注册数据源定义
                String dataSourceBeanName = registerDataSource(dsName, dynamicDataSourceBeanDefinition);
                //拦截器
                //TODO: mybatis sql 拦截器
                //创建 SqlSessionFactory 定义 ,用来映射指定路径XML文件
                BeanDefinition sqlSessionFactoryBeanDefinition = createSqlSessionFactoryDefinition(dataSourceBeanName, mapperLocationResources);
                String sqlSessionFactoryBeanName = registerSqlSessionFactory(dsName, sqlSessionFactoryBeanDefinition);
                BeanDefinition sqlSessionTemplateBeanDefinition = createSqlSessionTemplateBeanDefinition(sqlSessionFactoryBeanName);
                String sqlSessionTemplateBeanName = registerSessionTemplate(dsName, sqlSessionTemplateBeanDefinition);
                BeanDefinition dataSourceTransactionManagerBeanDefinition = createDataSourceTransactionManagerBeanDefinition(dataSourceBeanName);
                registerDataSourceTransactionManager(dsName, dataSourceTransactionManagerBeanDefinition);
                //MapperScanner 扫描
                MapperScannerConfigurer mapperScannerConfigurer = createMapperScannerConfigurer(mapperBasePackage, sqlSessionFactoryBeanName, sqlSessionTemplateBeanName);
                mapperScannerConfigurer.postProcessBeanDefinitionRegistry(aileenBeanUtils.getDefaultListableBeanFactory());
                log.debug("-- Create {} DataSource BeanDefinition success --", dsName);
            }
            log.info("-- DataSourceMod MybatisConfig init success --");
        } catch (Throwable e) {
            log.error("-- DataSourceMod MybatisConfig init error --", e);
            throw DataSourceModExceptionFactory.raiseException("-- DataSourceMod MybatisConfig init error --");
        }
    }

    private Resource[] getMapperLocations(String mapperLocations) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String[] mapperLocationsArr = mapperLocations.split(",");
        List<Resource> resources = new ArrayList<>();
        for (int i = 0; i < mapperLocationsArr.length; i++) {
            Resource[] resList = resolver.getResources(mapperLocationsArr[i].trim());
            resources.addAll(Arrays.asList(resList));
        }
        return resources.toArray(new Resource[0]);
    }

    private MapperScannerConfigurer createMapperScannerConfigurer(String mapperBasePackage,
                                                                  String sqlSessionFactoryName,
                                                                  String sqlSessionTemplateName) {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(mapperBasePackage);
        if (sqlSessionFactoryName != null) {
            mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryName);
        }
        if (sqlSessionTemplateName != null) {
            mapperScannerConfigurer.setSqlSessionTemplateBeanName(sqlSessionTemplateName);
        }
        return mapperScannerConfigurer;
    }

    /**
     * 用来检查账套配置和Xml配置是否合理映射
     */
//    private boolean checkAccountSetAndConfig() {
//        //最少有一个账套，而数据源则对应实际的配置进行判断，同样也必须要有至少一个数据源
//        //首先，检查数据源配置是否合理
//        // * 通过数据源列表配置，查询所有的 xml、mapper 是否对应且有效
//        List<AccountSet> accountSets = accountSetDataLoader.getAccountSets();
//        Set<String> accountSetNames = accountSets.stream()
//                .map(AccountSet::getAccountSetName)
//                .collect(Collectors.toSet());
//        if (!accountSetNames.contains(defaultAccount)) {
//            log.error("{} 默认账套不在可选账套范围内！。", defaultAccount);
//            throw new Exception("");
//        }
//
//        return true;
//    }
    private BeanDefinition createDynamicDataSourceBeanDefinition(String dbId, String defaultAccountSetName) {
        // 每个账套有多个数据源，其会映射到不同的XML
        // 每个DataSource会对应一个sqlSessionFactory，而sqlSessionFactory才是真正映射XML的对象
        // 对于同一个动态数据源，它的所有可选数据源都是使用同一个sqlSessionFactory
        Map<String, DataSourceData> dataSourceDataMap = accountSetDataLoader.getDataSourceDataMap(dbId);
        Set<String> accountSetNames = dataSourceDataMap.keySet();
        Map<Object, Object> targetDataSources = new HashMap<>();

        //创建Bean定义
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DynamicDataSource.class);
        //设置 DynamicDataSource 的构造参数
        builder.addConstructorArgValue(dbId);
        builder.addConstructorArgValue(accountSetNames);
        //设置 DynamicDataSource 的 targetDataSources 和 defaultTargetDataSource
        for (String accountSetName : dataSourceDataMap.keySet()) {
            DataSourceData dataSourceData = dataSourceDataMap.get(accountSetName);
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dataSourceConfigDto.getJdbcUrl(dataSourceData.getDBType(), dataSourceData.getDBServer(), dataSourceData.getDBName()));
            config.setDriverClassName(dataSourceConfigDto.getDriverClassName(dataSourceData.getDBType()));
            config.setUsername(dataSourceData.getDBUser());
            config.setPassword(dataSourceData.getDBPassword());
            HikariDataSource dataSource = new HikariDataSource(config);

            targetDataSources.put(accountSetName, dataSource);
            if (accountSetName.equals(defaultAccountSetName)) {
                // 设置默认数据源
                builder.addPropertyValue("defaultTargetDataSource", dataSource);
            }
        }
        // 如果默认账套不存在，则抛出异常
        if (!targetDataSources.containsKey(defaultAccountSetName)) {
            DataSourceModExceptionFactory.raiseException("[{0}]不存在默认账套[{1}]的默认数据源定义。", dbId, defaultAccountSetName);
        }
        // 设置 DynamicDataSource 的 targetDataSources
        builder.addPropertyValue("targetDataSources", targetDataSources);
        return builder.getBeanDefinition();
    }

    private String registerDataSource(String logicName, BeanDefinition dataSourceBeanDefinition) {
        String beanName = "dataSource_" + logicName;
        aileenBeanUtils.registerBeanDefinition(beanName, dataSourceBeanDefinition);
        log.debug("-- DataSourceMod MybatisConfig register DataSource [{}] --", beanName);
        return beanName;
    }

    private String registerSqlSessionFactory(String logicName, BeanDefinition sqlSessionFactoryBeanDefinition) {
        String beanName = "sqlSessionFactory_" + logicName;
        aileenBeanUtils.registerBeanDefinition(beanName, sqlSessionFactoryBeanDefinition);
        log.debug("-- DataSourceMod MybatisConfig register SqlSessionFactory [{}] --", beanName);
        return beanName;
    }

    private String registerSessionTemplate(String logicName, BeanDefinition sqlSessionTemplateBeanDefinition) {
        String beanName = "sqlSessionTemplate_" + logicName;
        aileenBeanUtils.registerBeanDefinition(beanName, sqlSessionTemplateBeanDefinition);
        log.debug("-- DataSourceMod MybatisConfig register SqlSessionTemplate [{}] --", beanName);
        return beanName;
    }

    private String registerDataSourceTransactionManager(String logicName, BeanDefinition dataSourceTransactionManagerBeanDefinition) {
        String beanName = "dataSourceTransactionManager_" + logicName;
        aileenBeanUtils.registerBeanDefinition(beanName, dataSourceTransactionManagerBeanDefinition);
        return beanName;
    }

    private BeanDefinition createSqlSessionFactoryDefinition(String dataSourceBeanName,
                                                             Resource[] mapperLocations) throws Exception {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class);
        // 设置数据源, 通过引用的方式
        builder.addPropertyReference("dataSource", dataSourceBeanName);
        // 设置 Mapper XML 文件的位置
        builder.addPropertyValue("mapperLocations", mapperLocations);

        // 设置 MyBatis 配置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(environment.getProperty("datasource-mod.mybatis-config.cache-enabled", Boolean.class, true));
        configuration.setMapUnderscoreToCamelCase(environment.getProperty("datasource-mod.mybatis-config.map-underscore-to-camel-case", Boolean.class, false));
        configuration.setCallSettersOnNulls(environment.getProperty("datasource-mod.mybatis-config.call-setters-on-nulls", Boolean.class, false));
        configuration.setReturnInstanceForEmptyRow(environment.getProperty("datasource-mod.mybatis-config.return-instance-for-empty-row", Boolean.class, false));

        builder.addPropertyValue("configuration", configuration);

        return builder.getBeanDefinition();
    }

    private BeanDefinition createSqlSessionTemplateBeanDefinition(String sqlSessionFactoryBeanName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionTemplate.class);
        builder.addConstructorArgReference(sqlSessionFactoryBeanName);
        return builder.getBeanDefinition();
    }

    private BeanDefinition createDataSourceTransactionManagerBeanDefinition(String dataSourceBeanName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceTransactionManager.class);
        builder.addPropertyReference("dataSource", dataSourceBeanName);
        return builder.getBeanDefinition();
    }

    /**
     * 获取数据源名称
     */
    public List<String> getDataSourceNames() {
        if (dataSourceNames != null) {
            return dataSourceNames;
        }
        String[] namesArray = environment.getProperty(logicNames, String[].class);
        dataSourceNames = namesArray != null ? Arrays.asList(namesArray) : new ArrayList<>();
        return dataSourceNames;
    }

    public String getDefaultAccount() {
        if (defaultAccount != null) {
            return defaultAccount;
        }
        String defaultAccountName = environment.getProperty(default_dataSource);
        List<AccountSet> accountSets = accountSetDataLoader.getAccountSets();
        AccountSet account = accountSets.stream()
                .filter(accountSet -> accountSet.getAccountSetName().equals(defaultAccountName))
                .findFirst().orElse(null);
        if (account == null) {
            account = accountSets.get(0);
            defaultAccount = account.getAccountSetName();
        } else {
            defaultAccount = defaultAccountName;
        }
        return defaultAccount;
    }
}
