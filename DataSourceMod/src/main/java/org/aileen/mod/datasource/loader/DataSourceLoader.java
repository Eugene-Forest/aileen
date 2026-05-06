package org.aileen.mod.datasource.loader;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/20
 */
@Slf4j
public class DataSourceLoader {
    /**
     * 线程安全的数据源配置
     */
    private static final ThreadLocal<String> contextHolder = ThreadLocal.withInitial(() -> null);

    public static void setDataSource(String logicName) {
        contextHolder.set(logicName);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }

}
