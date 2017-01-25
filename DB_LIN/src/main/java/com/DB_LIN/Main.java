package com.DB_LIN;

import com.DB_LIN.base.DAL;
import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        // write your code here
        DAL dal = new DAL();
        try {
            dal.select("select * from account");
            int n = dal.insert("insert into account (user,productId) values ('user1','product2')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
    * sql like 'select * from db.table'
    *
    * return like 'select * from db1.table' or 'select * from db2.table'
    * */
    public void select(String sql) {

    }

}
