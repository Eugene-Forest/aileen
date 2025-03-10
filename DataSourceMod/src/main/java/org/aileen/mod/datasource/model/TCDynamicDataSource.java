package org.aileen.mod.datasource.model;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class TCDynamicDataSource extends AbstractRoutingDataSource {

    private String logicName;
    private String dataSourceName;

    public TCDynamicDataSource(String logicName, String dataSourceName) {
        this.logicName = logicName;
        this.dataSourceName = dataSourceName;
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }


    @Override
    protected DataSource determineTargetDataSource() {
        //TODO
        return super.determineTargetDataSource();
    }
}
