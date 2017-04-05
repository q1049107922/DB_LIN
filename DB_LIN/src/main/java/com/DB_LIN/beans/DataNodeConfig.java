package com.DB_LIN.beans;

import java.util.List;

/**
 * Created by b_lin on 2017/3/16.
 */
public class DataNodeConfig {
    private List<DataSourceConfig> dataSourceConfigs;
    private String id ;
    private boolean isReadOnly;


    public List<DataSourceConfig> getDataSourceConfigs() {
        return dataSourceConfigs;
    }

    public void setDataSourceConfigs(List<DataSourceConfig> dataSourceConfigs) {
        this.dataSourceConfigs = dataSourceConfigs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }
}
