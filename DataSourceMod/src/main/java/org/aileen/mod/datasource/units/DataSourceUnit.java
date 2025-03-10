package org.aileen.mod.datasource.units;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.config.DataSourceSetConfig;
import org.aileen.mod.datasource.config.TCMybatisConfig;
import org.aileen.mod.datasource.model.DataSourceInfo;
import org.aileen.mod.datasource.starter.DataSourceStartRunner;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
@Slf4j
public class DataSourceUnit {
    /** 线程安全的数据源配置 */
    private static final ThreadLocal<DataSourceInfo> THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    /** 所有数据库源 */
    private static Map<String, Map<String, DataSource>> allDataSourceMap = new HashMap<>();

    public static void initDataSources(DataSourceSetConfig dataSourceSetConfig){
        Map<String, String> logicNameMap = DataSourceStartRunner.getLogicNameMap();
        List<String> dataSourceNames = DataSourceStartRunner.getMustDataSources();
//        allDataSourceMap =
    }
}
