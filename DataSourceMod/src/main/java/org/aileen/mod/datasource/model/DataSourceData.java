package org.aileen.mod.datasource.model;

import lombok.Data;

/**
 * @author Eugene-Forest
 * @date 2020/08/05
 * @description 数据源数据
 */
@Data
public class DataSourceData {
    private String Id;
    private String DBId;
    private String DBName;
    private String DBType;
    private String DBServer;
    private String DBUser;
    private String DBPassword;
    public DataSourceData() {}
}