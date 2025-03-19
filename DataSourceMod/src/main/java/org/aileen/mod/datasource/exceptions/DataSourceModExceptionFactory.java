package org.aileen.mod.datasource.exceptions;

import java.text.MessageFormat;

public class DataSourceModExceptionFactory {

    public static DataSourceModException raiseException(String message) throws DataSourceModException {
        throw new DataSourceModException(message);
    }

    /**
     * 抛出异常
     * @param pattern  {} 作为占位符的信息模板
     * @param args 替换占位符的参数
     * @return
     * @throws DataSourceModException
     */
    public static DataSourceModException raiseException(String pattern, Object... args) throws DataSourceModException {
        throw new DataSourceModException(MessageFormat.format(pattern, args));
    }
}
