package org.aileen.mod.datasource.config;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.interfaces.DataSourceSetLoader;

@Slf4j
public class DataSourceSetConfig {

    private static final String IP = "datasourceset.ip";
    private static final String PORT = "datasourceset.port";
    private static final String JDBC_URL = "datasourceset.jdbcUrl";
    private static final String DRIVER_CLASS_NAME = "datasourceset.driverClassName";

    private DataSourceSetLoader dataSourceSetLoader;


}
