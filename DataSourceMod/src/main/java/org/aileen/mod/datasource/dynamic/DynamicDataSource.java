package org.aileen.mod.datasource.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.loader.DataSourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Set;

@Slf4j
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
        log.info("Select {}",DataSourceLoader.getDataSource());
        return DataSourceLoader.getDataSource();
    }

    public String getDataSourceName() {
        return dataSourceName;
    }
}
