package org.aileen.mod.datasource.databind;

import lombok.Data;
import org.aileen.mod.datasource.enums.DBType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "datasource-mod.config")
public class DataSourceConfig {
    private String dbServer;
    private String dbName;
    private DriverComConfig mssql;
    private DriverComConfig mysql;

    public String getJdbcUrl(String dbType, String dbServer, String dbName) {
        DBType type = DBType.getDBType(dbType);
        String url = null;
        switch (type){
            case MSSQL:{
                url = mssql.getJdbcUrl();
                break;
            }
            case MYSQL:{
                url = mysql.getJdbcUrl();
                break;
            }
        }
        return url.replace(this.dbServer, dbServer).replace(this.dbName, dbName);
    }

    public String getDriverClassName(String dbType) {
        DBType type = DBType.getDBType(dbType);
        String driverClassName = null;
        switch (type){
            case MSSQL:{
                driverClassName = mssql.getDriverClassName();
                break;
            }
            case MYSQL:{
                driverClassName = mysql.getDriverClassName();
                break;
            }
        }
        return driverClassName;
    }

    @Data
    static class DriverComConfig {
        private String driverClassName;
        private String jdbcUrl;
    }
}

