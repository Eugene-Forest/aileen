package org.aileen.mod.datasource.units;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
public class DynamicDataSourceUnit extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

}
