package org.aileen.mod.datasource.model;

import lombok.Data;

import java.util.List;

/**
 * 账套
 */
@Data
public class AccountSet {
    /** 账套ID */
    private Long AccountSetId;
    /** 账套名 */
    private String AccountSetName;
    /** 服务名 */
    private String ServiceName;
    /** 是否默认 */
    private Boolean IsDefault;
    /** 账套数据源 */
    private List<DataSourceData> data;
}
