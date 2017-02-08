package com.DB_LIN.beans;

import java.sql.Connection;

/**
 * Created by b_lin on 2017/1/26.
 */
public class PartDBLIN {
    private String id;
    private String parentId;
    private String tag;
    private Connection connection;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
