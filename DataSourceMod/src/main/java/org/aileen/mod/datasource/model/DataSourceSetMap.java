package org.aileen.mod.datasource.model;

/**
 * 自定义数据源配置对象
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
public class DataSourceSetMap {
    private String jdbcUrl;
    private String driverClassName;
    private String IP_Placeholder;
    private String Port_Placeholder;
    private String DbName_Placeholder;

    public DataSourceSetMap() {
    }

    public DataSourceSetMap(String jdbcUrl, String driverClassName, String IP_Placeholder, String port_Placeholder, String dbName_Placeholder) {
        this.jdbcUrl = jdbcUrl;
        this.driverClassName = driverClassName;
        this.IP_Placeholder = IP_Placeholder;
        this.Port_Placeholder = port_Placeholder;
        DbName_Placeholder = dbName_Placeholder;
    }

    public String getJdbcUrl(String ip, String port, String dbName) {
        return jdbcUrl.replace(IP_Placeholder, ip).replace(Port_Placeholder, port).replace(DbName_Placeholder, dbName);
    }

    public String getDriverClassName(){
        return driverClassName;
    }


}
