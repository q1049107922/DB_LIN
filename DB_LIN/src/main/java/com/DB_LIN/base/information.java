package com.DB_LIN.base;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by b_lin on 2017/1/23.
 */
public class information extends Driver {
    public information() throws SQLException {

        //连接MySql数据库，用户名和密码都是root
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "root";
        try {
            Connection con =
                    DriverManager.getConnection(url, username, password);

        } catch (SQLException se) {
            System.out.println("数据库连接失败！");
            se.printStackTrace();
        }
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }


}
