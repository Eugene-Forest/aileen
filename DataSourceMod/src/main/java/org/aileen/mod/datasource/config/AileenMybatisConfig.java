package org.aileen.mod.datasource.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.dynamic.DynamicDataSource;
import org.aileen.mod.datasource.loader.AccountSetDataLoader;
import org.aileen.mod.datasource.loader.DataSourceLoader;
import org.aileen.mod.datasource.model.AccountSet;
import org.aileen.mod.datasource.databind.DataSourceConfig;
import org.aileen.mod.datasource.units.AileenBeanUnit;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    /**
     * key: dbName, value: alias name
     */
//    private Map<String, String> logicNameMap;
    @PostConstruct
    public void init() {
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
            String defaultSourceAliasName = environment.getProperty(default_dataSource);
            //创建数据源
//            DataSource dataSource = createDataSource(dsName, dataSourceNameSet, defaultSourceAliasName);
            //拦截器
            //TODO: mybatis sql 拦截器
            //创建 SqlSessionFactory ,用来映射指定路径XML文件
            SqlSessionFactory sqlSessionFactory = createSqlSessionFactory();
            //注册bean，分别注册 DataSource 、 SqlSessionFactory 、 事务管理器

            //MapperScanner 扫描
        }
    }

    /**
     * 用来检查账套配置和Xml配置是否合理映射
     */
    private boolean checkAccountSetAndConfig() {



        return true;
    }


    private DataSource createDataSource(String dataSourceName, String defaultAccount) {
        List<AccountSet> accountSets = accountSetDataLoader.getAccountSets();
        Set<String> accountSetNames = accountSets.stream()
                .map(AccountSet::getAccountSetName)
                .collect(Collectors.toSet());
        if (!accountSetNames.contains(defaultAccount)) {
            log.error("{} 默认账套不在可选账套范围内！。", defaultAccount);
            throw new Exception("");
        }
        DynamicDataSource dynamicDataSource = new DynamicDataSource(dataSourceName, accountSetNames);
        DataSource defaultDataSource = null;
        Map<Object, Object> targetDataSources = new HashMap<>();

        for (AccountSet accountSet : accountSets) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlserver://192.168.8.13:1433;databaseName=test");
            config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            config.setUsername("sa");
            config.setPassword("");
            HikariDataSource dataSource = new HikariDataSource(config);

            targetDataSources.put(accountSet.getAccountSetName(), dataSource);
            if (accountSet.getAccountSetName().equals(defaultAccount)) {
                defaultDataSource = dataSource;
            }
        }

        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        return dynamicDataSource;
    }


    private String register(String logicName, DataSource dataSource) {

        return null;//beanName
    }

    private String register(String logicName, SqlSessionFactory sqlSessionFactory) {
        return null;
    }

    private String register(String logicName, SqlSessionTemplate sqlSessionTemplate) {
        return null;
    }

    private String register(String logicName, DataSourceTransactionManager dataSourceTransactionManager) {
        return null;
    }


    private SqlSessionFactory createSqlSessionFactory() {
        return null;
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
}
