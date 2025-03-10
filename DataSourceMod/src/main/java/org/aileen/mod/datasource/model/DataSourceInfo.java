package org.aileen.mod.datasource.model;

import javax.sql.DataSource;
import java.util.Map;

/** 数据源信息 */
public class DataSourceInfo {
    private String name;
    private Map<String, DataSource> dataSourceMap;

    public DataSourceInfo() {
    }

    public DataSourceInfo(String name, Map<String, DataSource> dataSourceMap) {
        this.name = name;
        this.dataSourceMap = dataSourceMap;
    }

    public String getName() {
        return name;
    }

    public Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }
}
