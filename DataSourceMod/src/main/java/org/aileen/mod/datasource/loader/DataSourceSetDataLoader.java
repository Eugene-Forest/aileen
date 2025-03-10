package org.aileen.mod.datasource.loader;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.config.DataSourceSetConfig;

@Slf4j
public class DataSourceSetDataLoader {

    private static DataSourceSetConfig dataSourceSetConfig;

    private static boolean isInit = false;

    public static void load(){
        if(!isInit){
            log.info("loading datasource >>>");
            isInit = true;
        }
    }
}
