package org.aileen.mod.datasource.dynamic;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceConfig {



    public DataSource masterDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlserver://192.168.8.13:1433;databaseName=test");
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        config.setUsername("sa");
        config.setPassword("123456");
        return new HikariDataSource(config);
    }

    public DataSource dynamicDataSource(){
        DynamicDataSource dataSource = new DynamicDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource());
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(masterDataSource());
        return dataSource;
    }
}
