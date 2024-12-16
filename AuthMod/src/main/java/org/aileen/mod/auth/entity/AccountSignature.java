package org.aileen.mod.auth.entity;

/**
 * 账套签名
 * @author Eugene-Forest
 * {@code @date} 2024/11/27
 */
public class AccountSignature extends SimpleSignature{

    private String user;
    private String dbName;
    private String serverName;
    private String bodyString;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getBodyString() {
        return bodyString;
    }

    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
    }
}
