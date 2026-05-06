package org.aileen.mod.datasource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.aileen.mod.crypto.CryptoUnits;

/**
 * @author Eugene-Forest
 * @date 2020/08/05
 * @description 数据源数据
 */
@Data
public class DataSourceData {
    @JsonProperty("id")
    private String Id;
    @JsonProperty("dbid")
    private String DBId;
    @JsonProperty("dbname")
    private String DBName;
    @JsonProperty("dbtype")
    private String DBType;
    @JsonProperty("dbserver")
    private String DBServer;
    @JsonProperty("dbuser")
    private String DBUser;
    @JsonProperty("dbpassword")
    private String DBPassword;

    public DataSourceData() {}


    public String getDBServer() {
        return CryptoUnits.defaultDecrypt(DBServer);
    }
//
//    public String getDBUser() {
//        return CryptoUnits.defaultDecrypt(DBUser);
//    }

    public String getDBPassword() {
        return CryptoUnits.defaultDecrypt(DBPassword);
    }
}