package com.DB_LIN.base;

import com.DB_LIN.beans.DataSourceConfig;
import com.DB_LIN.beans.PoolConfig;
import com.DB_LIN.util.func.XmlToBean;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;


/**
 * Created by b_lin on 2017/3/13.
 */
public class DataSourceLIN implements DataSource {

    //创建一个连接池
    private static Queue<Connection> usedPool = new ConcurrentLinkedQueue<Connection>();

    private static Queue<Connection> unUsedPool = new ConcurrentLinkedQueue<Connection>();

    public static ThreadLocal<Connection> currentConnectionLIN = new ThreadLocal<Connection>();

    protected static PoolConfig poolConfig = XmlToBean.getPoolConfig();

    DataSource datasource;
    public String tag;
    public String parentId;
    public String id;
    private DataSourceConfig dataSourceConfig;

    public DataSourceLIN(DataSourceConfig config) throws SQLException {
        //dataSourceConfig = XmlToBean.getDataSourceConfigByTag(tag);
        this.dataSourceConfig = config;
        this.tag = dataSourceConfig.getTag();
        this.id = dataSourceConfig.getId();
        this.parentId = dataSourceConfig.getParentId();
        for (int i = 0; i < poolConfig.getInitialSize(); i++) {
            Connection connp = DriverManager.getConnection(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
            unUsedPool.add(connp);
        }
    }

    public DataSourceLIN(DataSource dataSource) {
        this.datasource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        if (datasource != null) {
            return datasource.getConnection();
        }
        try {
            if (currentConnectionLIN.get() == null) {
                Connection conn = null;
                long startTime = System.currentTimeMillis();
                for (; ; ) {
                    if ((conn = unUsedPool.poll()) != null) {
                        usedPool.add(conn);
                        currentConnectionLIN.set(conn);
                        break;
                    } else {
                        Thread.currentThread().sleep(100);//
                        if (increasePool()) {
                            break;
                        }
                    }
                    if ((System.currentTimeMillis() - startTime) >= poolConfig.getTimeOut()) {
                        break;
                    }
                }
                if (conn == null) {
                    throw new ExceptionInInitializerError("获取数据库连接超时");
                }
                usedPool.add(conn);
                currentConnectionLIN.set(conn);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("获取数据库连接失败");
        }
        return currentConnectionLIN.get();
    }

    /*
    * 另起一个线程扩容线程池
    * */
    public boolean increasePool() throws SQLException {
        if (usedPool.size() + unUsedPool.size() + poolConfig.getInCreaseSize() < poolConfig.getMaxSize()) {
            Connection connp = DriverManager.getConnection(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
            unUsedPool.add(connp);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        for (int i = 1; i < poolConfig.getInCreaseSize(); i++) {
                            Connection connp = DriverManager.getConnection(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
                            unUsedPool.add(connp);
                        }
                    } catch (Exception e) {
                        throw new ExceptionInInitializerError("数据库连接失败，请检查配置");
                    }
                }
            }.start();
            return true;
        } else {
            return false;
        }
    }


    public Connection getConnection(String username, String password) throws SQLException {
        if (datasource != null) {
            return datasource.getConnection(username, password);
        }
        throw new SQLException("do not support this function");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.getClass().isAssignableFrom(iface);
    }

    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            return (T) this;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.sql.CommonDataSource#getLogWriter()
     */
    public PrintWriter getLogWriter() throws SQLException {
        if (datasource == null)
            throw new SQLException("do not support this function");

        return datasource.getLogWriter();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
     */
    public void setLogWriter(PrintWriter out) throws SQLException {
        if (datasource == null)
            throw new SQLException("do not support this function");

        datasource.setLogWriter(out);

    }

    /*
     * (non-Javadoc)
     *
     * @see javax.sql.CommonDataSource#setLoginTimeout(int)
     */
    public void setLoginTimeout(int seconds) throws SQLException {
        if (datasource == null)
            throw new SQLException("do not support this function");

        datasource.setLoginTimeout(seconds);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.sql.CommonDataSource#getLoginTimeout()
     */

    public int getLoginTimeout() throws SQLException {
        if (datasource == null)
            throw new SQLException("do not support this function");

        return datasource.getLoginTimeout();
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
