package org.aileen.mod.datasource.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.dynamic.DynamicDataSource;
import org.aileen.mod.datasource.exceptions.DataSourceModExceptionFactory;
import org.aileen.mod.datasource.loader.AccountSetDataLoader;
import org.aileen.mod.datasource.loader.DataSourceLoader;
import org.aileen.mod.datasource.model.AccountSet;
import org.aileen.mod.datasource.databind.DataSourceConfig;
import org.aileen.mod.datasource.model.DataSourceData;
import org.aileen.mod.datasource.units.AileenBeanUnit;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
@Slf4j
@Component
public class AileenMybatisConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private AileenBeanUnit aileenBeanUnit;

    @Autowired
    private DataSourceLoader dataSourceLoader;

    @Autowired
    private AccountSetDataLoader accountSetDataLoader;

    @Autowired
    private DataSourceConfig dataSourceConfig;

    private final String logicNames = "datasource-mod.logic.names";
    private final String logicMap = "datasource-mod.logic.maps.";
    private final String default_dataSource = "datasource-mod.logic.default";
    private final String mybatisLocation = "datasource-mod.mapper-locations.";
    private final String mybatisBasePackage = "datasource-mod.mapper-base-package.";
    private final String mybatisConfig = "datasource-mod.mybatis-config";
    private final String config = "datasource-mod.config";

    private List<String> dataSourceNames;

    private String defaultAccount;

    /**
     * key: dbName, value: alias name
     */
//    private Map<String, String> logicNameMap;
    @PostConstruct
    public void init() {
        try {
            log.info("-- DataSourceMod MybatisConfig init --");
//        Map<String, String> logicNameMap = getLogicNameMap();
            // 在创建数据源之前，检查账套数据与配置文件是否构成合理映射

            // 一个Map对应一个Dy
            // i一个Account对应一个Dy的Ele
            //
            for (String dsName : getDataSourceNames()) {
                log.info("-- init {} DataSource --", dsName);
                //读取配置
//            String dataSourceNames = logicNameMap.get(dsName);
                Set<String> dataSourceNameSet = new HashSet<>();
//            if (StringUtils.isNotBlank(dataSourceNames)) {
//                dataSourceNameSet.addAll(Arrays.asList(dataSourceNames.split(",")));
//            }
//            if (dataSourceNameSet.isEmpty()) {
//                log.error("{} 账套不存在有效数据源定义。", dsName);
//                continue;
//            }
                String mapperLocations = environment.getProperty(mybatisLocation + dsName);
                String mapperBasePackage = environment.getProperty(mybatisBasePackage + dsName);
                Resource[] mapperLocationResources = getMapperLocations(mapperLocations);
                String defaultAccountSetName = accountSetDataLoader.getDefaultAccountSetName();
                //创建数据源
                DataSource dataSource = createDataSource(dsName, defaultAccountSetName);
                //拦截器
                //TODO: mybatis sql 拦截器
                //创建 SqlSessionFactory ,用来映射指定路径XML文件
                SqlSessionFactory sqlSessionFactory = createSqlSessionFactory(dataSource, dsName, mapperLocationResources);
                SqlSessionTemplate sqlSessionTemplate = createSqlSessionTemplate(sqlSessionFactory);
                DataSourceTransactionManager dataSourceTransactionManager = createDataSourceTransactionManager(dataSource);
                //注册bean，分别注册 DataSource 、 SqlSessionFactory 、 事务管理器
                register(dsName, dataSource);
                register(dsName, sqlSessionFactory);
                register(dsName, sqlSessionTemplate);
                register(dsName, dataSourceTransactionManager);
                //MapperScanner 扫描
                MapperScannerConfigurer mapperScannerConfigurer = createMapperScannerConfigurer(mapperBasePackage, null, dsName);
                mapperScannerConfigurer.postProcessBeanFactory(aileenBeanUnit.getDefaultListableBeanFactory());
                log.info("-- init {} DataSource success --", dsName);
            }
        } catch (Throwable e) {
            log.error("-- DataSourceMod MybatisConfig init error --", e);
            throw DataSourceModExceptionFactory.raiseException("-- DataSourceMod MybatisConfig init error --");
        }
    }

    private Resource[] getMapperLocations(String mapperLocations) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String[] mapperLocationsArr = mapperLocations.split(",");
        Resource[] resources = new Resource[mapperLocationsArr.length];
        for (int i = 0; i < mapperLocationsArr.length; i++) {
            resources[i] = resolver.getResource(mapperLocationsArr[i].trim());
        }
        return resources;
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
    private DataSource createDataSource(String dbId, String defaultAccountSetName) {
        // 每个账套有多个数据源，其会映射到不同的XML
        // 每个DataSource会对应一个sqlSessionFactory，而sqlSessionFactory才是真正映射XML的对象
        // 对于同一个动态数据源，它的所有可选数据源都是使用同一个sqlSessionFactory
        // 故
        Map<String, DataSourceData> dataSourceDataMap = accountSetDataLoader.getDataSourceDataMap(dbId);
        Set<String> accountSetNames = dataSourceDataMap.keySet();
        DynamicDataSource dynamicDataSource = new DynamicDataSource(dbId, accountSetNames);
        DataSource defaultDataSource = null;
        Map<Object, Object> targetDataSources = new HashMap<>();

        for (String accountSetName : dataSourceDataMap.keySet()) {
            DataSourceData dataSourceData = dataSourceDataMap.get(accountSetName);
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dataSourceConfig.getJdbcUrl(dataSourceData.getDBType(), dataSourceData.getDBServer(), dataSourceData.getDBName()));
            config.setDriverClassName(dataSourceConfig.getDriverClassName(dataSourceData.getDBType()));
            config.setUsername(dataSourceData.getDBUser());
            config.setPassword(dataSourceData.getDBPassword());
            log.debug(config.getJdbcUrl());
            log.debug(config.getUsername());
            log.debug(config.getPassword());
            log.debug(config.getDriverClassName());
            HikariDataSource dataSource = new HikariDataSource(config);

            targetDataSources.put(accountSetName, dataSource);
            if (accountSetName.equals(defaultAccountSetName)) {
                defaultDataSource = dataSource;
            }
        }
        if (defaultDataSource == null) {
            DataSourceModExceptionFactory.raiseException("[{0}]不存在默认账套[{1}]的默认数据源定义。", dbId, defaultAccountSetName);
        }

        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        return dynamicDataSource;
    }


    private String register(String logicName, DataSource dataSource) {
        String beanName = "dataSource_" + logicName;
        aileenBeanUnit.registerSingleton(beanName, dataSource);
        log.info("-- DataSourceMod MybatisConfig register DataSource [{}] --", beanName);
        return beanName;
    }

    private String register(String logicName, SqlSessionFactory sqlSessionFactory) {
        String beanName = "sqlSessionFactory_" + logicName;
        aileenBeanUnit.registerSingleton(beanName, sqlSessionFactory);
        log.info("-- DataSourceMod MybatisConfig register SqlSessionFactory [{}] --", beanName);
        return beanName;
    }

    private String register(String logicName, SqlSessionTemplate sqlSessionTemplate) {
        String beanName = "sqlSessionTemplate_" + logicName;
        aileenBeanUnit.registerSingleton(beanName, sqlSessionTemplate);
        log.info("-- DataSourceMod MybatisConfig register SqlSessionTemplate [{}] --", beanName);
        return beanName;
    }

    private String register(String logicName, DataSourceTransactionManager dataSourceTransactionManager) {
        String beanName = "dataSourceTransactionManager_" + logicName;
        aileenBeanUnit.registerSingleton(beanName, dataSourceTransactionManager);
        log.info("-- DataSourceMod MybatisConfig register DataSourceTransactionManager [{}] --", beanName);
        return beanName;
    }


    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String dataSourceName,
                                                      Resource[] mapperLocations) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置 Mapper XML 文件的位置
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);

        // 设置 MyBatis 配置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(environment.getProperty("datasource-mod.mybatis-config.cache-enabled", Boolean.class, true));
        configuration.setMapUnderscoreToCamelCase(environment.getProperty("datasource-mod.mybatis-config.map-underscore-to-camel-case", Boolean.class, false));
        configuration.setCallSettersOnNulls(environment.getProperty("datasource-mod.mybatis-config.call-setters-on-nulls", Boolean.class, false));
        configuration.setReturnInstanceForEmptyRow(environment.getProperty("datasource-mod.mybatis-config.return-instance-for-empty-row", Boolean.class, false));
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean.getObject();
    }

    private SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    private DataSourceTransactionManager createDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    /**
     * 获取数据源名称与逻辑名称的映射关系, key: dataSourceName, value: logicName
     */
//    public Map<String, String> getLogicNameMap() {
//        if (logicNameMap != null) {
//            return logicNameMap;
//        }
//        String[] namesArray = environment.getProperty(logicNames, String[].class);
//        List<String> names = namesArray != null ? Arrays.asList(namesArray) : new ArrayList<>();
//        logicNameMap = new HashMap<>();
//        for (String name : names) {
//            String logicName = environment.getProperty(logicMap + name);
//            logicNameMap.put(name, logicName);
//        }
//        return logicNameMap;
//    }

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
