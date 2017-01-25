package com.DB_LIN.beans;

import java.sql.Connection;

/**
 * Created by b_lin on 2017/1/25.
 */
public class ConnectionLIN {
    private Boolean isForWrite;
    private Connection connection;

    public Boolean getIsForWrite() {
        return isForWrite;
    }

    public void setIsForWrite(Boolean isForWrite) {
        this.isForWrite = isForWrite;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
