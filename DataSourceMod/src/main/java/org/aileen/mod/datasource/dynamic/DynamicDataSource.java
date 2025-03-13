package org.aileen.mod.datasource.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private String logicName;
    private String dataSourceName;

//    public DynamicDataSource(String logicName, String dataSourceName) {
//        this.logicName = logicName;
//        this.dataSourceName = dataSourceName;
//    }


    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }


//    @Override
//    protected DataSource determineTargetDataSource() {
//        //TODO
//        return super.determineTargetDataSource();
//    }
}
