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
}
