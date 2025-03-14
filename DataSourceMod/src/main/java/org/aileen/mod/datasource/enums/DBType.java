package org.aileen.mod.datasource.enums;
/** **/
public enum DBType {
    MYSQL("0"),
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
