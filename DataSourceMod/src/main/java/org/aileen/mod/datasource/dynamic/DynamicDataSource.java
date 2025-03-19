package org.aileen.mod.datasource.dynamic;

import org.aileen.mod.datasource.loader.DataSourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Set;

public class DynamicDataSource extends AbstractRoutingDataSource {
    /** 数据源名称 */
    private String dataSourceName;
    /** 账套名 */
    private Set<String> accountSetNames;

    public DynamicDataSource(String dbAliasName, Set<String> accountSetNames) {
        this.dataSourceName = dbAliasName;
        this.accountSetNames = accountSetNames;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceLoader.getDataSource();
    }

    public String getDataSourceName() {
        return dataSourceName;
    }
}
