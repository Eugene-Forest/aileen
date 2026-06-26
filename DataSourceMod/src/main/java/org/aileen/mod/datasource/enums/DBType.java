package org.aileen.mod.datasource.enums;

/**
 * 数据库类型枚举
 * value 与 datasourceset.json 中的 dbtype 字段对应
 */
public enum DBType {
    /** MySQL 数据库 */
    MYSQL("0"),
    /** Microsoft SQL Server 数据库 */
    MSSQL("1");

    private String value;

    DBType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DBType getDBType(String value) {
        for (DBType dbType : DBType.values()) {
            if (dbType.getValue().equals(value)) {
                return dbType;
            }
        }
        throw new IllegalArgumentException("Invalid DBType value: " + value);
    }
}
