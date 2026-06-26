package org.aileen.mod.datasource.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.crypto.CryptoUnits;

/**
 * 数据源数据
 *
 * @author Eugene-Forest
 * @date 2020/08/05
 */
@Data
@Slf4j
public class DataSourceData {
    private String id;
    private String dbId;
    private String dbName;
    private String dbType;
    private String dbServer;
    private String dbUser;
    private String dbPassword;

    public DataSourceData() {}

    public String getDbServer() {
        return CryptoUnits.defaultDecrypt(dbServer);
    }

    public String getDbPassword() {
        return CryptoUnits.defaultDecrypt(dbPassword);
    }
}
