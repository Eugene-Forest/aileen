package org.aileen.datasourceset.dto;

import lombok.Data;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/23
 */
@Data
public class DataSourceDto {
    private String Id;
    private String DBId;
    private String DBName;
    private String DBType;
    private String DBServer;
    private String DBUser;
    private String DBPassword;
    public DataSourceDto() {}
}