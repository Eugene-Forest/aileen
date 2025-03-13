package org.aileen.mod.datasource.loader;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.units.AileenBeanUnit;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
@Slf4j
@Component
public class AileenMybatisLoader {

    private Environment environment;

    private AileenBeanUnit aileenBeanUnit;

    private final String logicNames = "datasourcemod.logic.names";
    private final String logicMap = "datasourcemod.logic.maps.";
    private final String default_dataSource = "datasourcemod.default";
    private final String mybatisLocation = "datasourcemod.mapper-locations.";
    private final String mybatisBasePackage = "datasourcemod.mapper-base-package.";


    private List<String> dataSourceNames;
    private Map<String, String> logicNameMap;

    @Autowired
    public AileenMybatisLoader(Environment environment, AileenBeanUnit aileenBeanUnit) {
        this.environment = environment;
        this.aileenBeanUnit = aileenBeanUnit;
    }

    public void init() {
        log.info("-- DataSourceMod MybatisConfig --");
        Map<String, String> logicNameMap = getLogicNameMap();
        for (String logicName : logicNameMap.keySet()) {
            log.info("-- init {} DataSource --", logicName);
            //读取配置
            String mapperLocations = environment.getProperty(mybatisLocation + logicName);
            String mapperBasePackage = environment.getProperty(mybatisBasePackage + logicName);

            //创建数据源

            //拦截器

            //sqlSessionFactory

            //注册bean

            //MapperScanner
        }
    }

    private DataSource createDataSource(String logicName, String dbName) {
        return null;
    }

    private String register(String logicName, DataSource dataSource) {

        return null;//beanName
    }

    private SqlSessionFactory createSqlSessionFactory() {
        return null;
    }

    public Map<String, String> getLogicNameMap() {
        if (logicNameMap != null) {
            return logicNameMap;
        }
        String[] namesArray = environment.getProperty(logicNames, String[].class);
        List<String> names = namesArray != null ? Arrays.asList(namesArray) : new ArrayList<>();
        logicNameMap = new HashMap<>();
        for (String name : names) {
            String logicName = environment.getProperty(logicMap + name);
            logicNameMap.put(name, logicName);
        }
        return logicNameMap;
    }

    public List<String> getDataSourceNames() {
        if (dataSourceNames != null) {
            return dataSourceNames;
        }
        String[] namesArray = environment.getProperty(default_dataSource, String[].class);
        dataSourceNames = namesArray != null ? Arrays.asList(namesArray) : new ArrayList<>();
        return dataSourceNames;
    }
}
