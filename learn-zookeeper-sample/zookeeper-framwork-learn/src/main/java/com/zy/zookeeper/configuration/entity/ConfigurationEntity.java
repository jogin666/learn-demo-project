package com.zy.zookeeper.configuration.entity;

/**
 * @author: jogin
 * @date: 2020/9/13 9:32
 *
 * 配置信息实体类
 */
public class ConfigurationEntity {

    private String dbURL;
    private String userName;
    private String password;

    public ConfigurationEntity(String dbURL, String userName, String password) {
        this.dbURL = dbURL;
        this.userName = userName;
        this.password = password;
    }

    public String getDbURL() {
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ConfigurationEntity{" +
                "dbURL='" + dbURL + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
