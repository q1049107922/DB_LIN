package com.DB_LIN.beans;

import java.util.List;

/**
 * Created by b_lin on 2017/3/16.
 */
public class Configurations {
    private PoolConfig poolConfig;
    private List<DataNodeConfig> dataNodeConfigs;

    public PoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(PoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public List<DataNodeConfig> getDataNodeConfigs() {
        return dataNodeConfigs;
    }

    public void setDataNodeConfigs(List<DataNodeConfig> dataNodeConfigs) {
        this.dataNodeConfigs = dataNodeConfigs;
    }
}
