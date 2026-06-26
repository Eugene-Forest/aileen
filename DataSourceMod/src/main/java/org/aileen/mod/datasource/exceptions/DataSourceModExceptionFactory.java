package org.aileen.mod.datasource.exceptions;

import java.text.MessageFormat;

public class DataSourceModExceptionFactory {

    public static void raiseException(String message) {
        throw new DataSourceModException(message);
    }

    /**
     * 抛出异常
     * @param pattern  {} 作为占位符的信息模板
     * @param args 替换占位符的参数
     */
    public static void raiseException(String pattern, Object... args) {
        throw new DataSourceModException(MessageFormat.format(pattern, args));
    }

    public static void raiseException(Throwable cause) {
        throw new DataSourceModException(cause);
    }

}
