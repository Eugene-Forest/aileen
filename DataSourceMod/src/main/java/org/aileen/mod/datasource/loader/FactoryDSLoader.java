package org.aileen.mod.datasource.loader;

import org.aileen.mod.datasource.interfaces.DataSourceSetLoader;

public class FactoryDSLoader {

    public static DataSourceSetLoader getDataSourceSetLoader(String filePath , Integer model) {
        DataSourceSetLoader dataSourceSetLoader = null;
        switch (model) {
            case 0:
                dataSourceSetLoader = new DSFileLoader(filePath);
                break;
            case 1:
                dataSourceSetLoader = new DSHttpLoader();
                break;
            case 2:
                dataSourceSetLoader = new DSMixLoader();
                break;
            default:
                dataSourceSetLoader = new DSFileLoader(filePath);
                break;
        }
        return dataSourceSetLoader;
    }
}
