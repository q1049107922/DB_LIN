package com.DB_LIN.beans;

import java.sql.Connection;
import java.util.List;

/**
 * Created by b_lin on 2017/1/25.
 */
public class ConnectionLIN {
    private String id;
    private Boolean isForWrite;
    private List<PartDBLIN> databases;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PartDBLIN> getPartDBLIN() {
        return databases;
    }

    public void setPartDBLIN(List<PartDBLIN> databases) {
        this.databases = databases;
    }

    public Boolean getIsForWrite() {
        return isForWrite;
    }

    public void setIsForWrite(Boolean isForWrite) {
        this.isForWrite = isForWrite;
    }
}
