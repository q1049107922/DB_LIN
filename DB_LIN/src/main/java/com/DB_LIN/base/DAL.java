package com.DB_LIN.base;

import com.mysql.jdbc.Driver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by b_lin on 2017/1/23.
 */
public class DAL {
    private static ConnectionLINPool conn;

    public static ConnectionLINPool getConn() {
        if (conn == null) {
            conn = new ConnectionLINPool();
        }
        return conn;
    }

    //public
    public DAL() {

    }

    public ResultSet select(String sql, Object... args) throws SQLException {
        PreparedStatement preparedStatement = getConn().getConnectionForRead().getPartDBLIN().get(0)
                .getConnection().prepareStatement(sql);
        
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            System.out.print(result.getString("Id") + " ");
            System.out.print(result.getString("userName") + " ");
            System.out.print(result.getString("money") + " ");
        }
        conn.release();
        return result;
    }

    public int insert(String sql) throws SQLException {
        PreparedStatement preparedStatement = getConn().getConnectionForWrite().getPartDBLIN().get(0)
                .getConnection().prepareStatement(sql);
        return preparedStatement.executeUpdate();
    }

}
