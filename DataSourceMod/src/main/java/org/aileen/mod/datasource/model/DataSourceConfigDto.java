package org.aileen.mod.datasource.model;

import lombok.Data;
import org.aileen.mod.datasource.enums.DBType;

@Data
public class DataSourceConfigDto {
    private String dbServer;
    private String dbName;
    private DriverComConfigDto mssql;
    private DriverComConfigDto mysql;

    public String getJdbcUrl(String dbType, String dbServer, String dbName) {
        DBType type = DBType.getDBType(dbType);
        String template;
        switch (type){
            case MSSQL:{
                template = mssql.getJdbcUrl();
                break;
            }
            case MYSQL:{
                template = mysql.getJdbcUrl();
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported DB type: " + type);
        }
        // 先替换占位符，避免 dbServer/dbName 内容互相干扰
        return template.replace(this.dbServer, dbServer).replace(this.dbName, dbName);
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
}
