package org.aileen.mod.datasource.exceptions;

public class DataSourceModException extends RuntimeException{
    public DataSourceModException(String message) {
        super(message);
    }

    public DataSourceModException(Throwable cause){
        super(cause);
    }
}
