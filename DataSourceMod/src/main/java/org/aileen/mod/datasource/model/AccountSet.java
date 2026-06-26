package org.aileen.mod.datasource.model;

import lombok.Data;

import java.util.List;

/**
 * 账套
 */
@Data
public class AccountSet {
    /** 账套ID */
    private Long accountSetId;
    /** 账套名 */
    private String accountSetName;
    /** 服务名 */
    private String serviceName;
    /** 是否默认 */
    private boolean defaulted;
    /** 账套数据源 */
    private List<DataSourceData> data;
}
