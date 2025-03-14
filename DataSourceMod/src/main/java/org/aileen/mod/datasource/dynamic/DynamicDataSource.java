package org.aileen.mod.datasource.dynamic;

import org.aileen.mod.datasource.loader.DataSourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Set;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private String aliasName;
    private Set<String> accountSetNames;

    public DynamicDataSource(String dbAliasName, Set<String> accountSetNames) {
        this.aliasName = dbAliasName;
        this.accountSetNames = accountSetNames;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceLoader.getDataSource();
    }

    public String getDbAliasName() {
        return aliasName;
    }
}
