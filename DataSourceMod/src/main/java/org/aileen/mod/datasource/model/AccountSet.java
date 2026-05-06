package org.aileen.mod.datasource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 账套
 */
@Data
public class AccountSet {
    /** 账套ID */
    @JsonProperty("accountSetID")
    private Long AccountSetId;
    /** 账套名 */
    @JsonProperty("accountSetName")
    private String AccountSetName;
    /** 服务名 */
    @JsonProperty("serviceName")
    private String ServiceName;
    /** 是否默认 */
    @JsonProperty("isDefault")
    private Boolean IsDefault;
    /** 账套数据源 */
    private List<DataSourceData> data;
}
