package org.aileen.mod.datasource.loader;

import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.config.DataSourceSetConfig;
import org.aileen.mod.datasource.model.AccountSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
@Slf4j
@Component
public class DataSourceLoader {

    @Autowired
    private AccountSetDataLoader accountSetDataLoader;

    /** 线程安全的数据源配置 */
    private static final ThreadLocal<String> contextHolder = ThreadLocal.withInitial(() -> null);
    /** 所有数据库源 */
//    private static Map<String, Map<String, DataSource>> allDataSourceMap = new HashMap<>();

    public static void setDataSource(String logicName, String dataSourceName) {
        contextHolder.set(logicName + ":" + dataSourceName);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }

}
