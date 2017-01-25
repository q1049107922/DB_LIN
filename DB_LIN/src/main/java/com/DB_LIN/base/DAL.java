package com.DB_LIN.base;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by b_lin on 2017/1/23.
 */
public class DAL {

    //public
    public DAL() {

    }

    public ResultSet select(String sql, Object... args) throws SQLException {
        PreparedStatement preparedStatement = ConnectionFactory.getIsForReadConnection().prepareStatement(sql);

        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            System.out.print(result.getString("Id") + " ");
            System.out.print(result.getString("user") + " ");
            System.out.print(result.getString("productId") + " ");
        }
        return result;
    }

    public int insert(String sql) throws SQLException {
        PreparedStatement preparedStatement = ConnectionFactory.getIsForWriteConnection().prepareStatement(sql);
        return preparedStatement.executeUpdate();
    }
}
