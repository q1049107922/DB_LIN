package com.DB_LIN.manager;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by b_lin on 2017/3/15.
 */
public class DataNode {

    protected List<DataSource> dataNode ;
    public boolean isForWrite;
    public String id;
    public DataNode(String id){

    }

    public void init(){

    }

}
