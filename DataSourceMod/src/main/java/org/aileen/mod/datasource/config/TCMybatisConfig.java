package org.aileen.mod.datasource.config;

import org.aileen.mod.datasource.units.TCBeanUnit;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
public class TCMybatisConfig {

    private Environment environment;
    private TCBeanUnit tcBeanUnit;

    private List<String> dataSourceNames;
    private Map<String,String> logicNameMap;

    public TCMybatisConfig(Environment environment, TCBeanUnit tcBeanUnit) {
        this.environment = environment;
        this.tcBeanUnit = tcBeanUnit;
    }

    private DataSource createDataSource() {
        return null;
    }

    private String register(String logicName, DataSource dataSource) {

        return null;//beanName
    }

    private SqlSessionFactory createSqlSessionFactory() {
        return null;
    }

}
