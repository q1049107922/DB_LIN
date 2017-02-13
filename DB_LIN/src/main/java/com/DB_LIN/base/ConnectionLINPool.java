package com.DB_LIN.base;

import com.DB_LIN.beans.ConnectionLIN;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by b_lin on 2017/2/9.
 */
public class ConnectionLINPool/* implements DataSource*/ {

    static int size;
    //创建一个连接池
    private static List<ConnectionLINProvider> usedPool = new LinkedList<ConnectionLINProvider>();

    private static List<ConnectionLINProvider> unUsedPool = new LinkedList<ConnectionLINProvider>();

    public static ThreadLocal<ConnectionLINProvider> currentConnectionLIN = new ThreadLocal<ConnectionLINProvider>();

    //初始化10个连接
    static {
        try {

            for (int i = 0; i < 10; i++) {
                ConnectionLINProvider connp = new ConnectionLINProvider();
                unUsedPool.add(connp);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("数据库连接失败，请检查配置");
        }
    }

    public ConnectionLINPool() {

    }

    /*
    *
    * */
    public ConnectionLINProvider getConnectionFromPool() {
        if (currentConnectionLIN.get() == null) {
            ConnectionLINProvider conn = unUsedPool.get(0);
            usedPool.add(conn);
            unUsedPool.remove(conn);
            currentConnectionLIN.set(conn);
        }
        return currentConnectionLIN.get();
    }

    //释放资源
    public void release() {
        try {
            currentConnectionLIN.get().close();
            unUsedPool.add(currentConnectionLIN.get());
            usedPool.remove(currentConnectionLIN.get());
            currentConnectionLIN.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionLIN getConnectionForRead() {
        return getConnectionFromPool().getConnectionForRead();
    }

    public ConnectionLIN getConnectionForWrite() {
        return getConnectionFromPool().getConnectionForWrite();
    }

    public ConnectionLINProvider getCurrentConnectionLIN(){
        return currentConnectionLIN.get();
    }
}
