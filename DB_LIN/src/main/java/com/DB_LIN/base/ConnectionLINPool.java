package com.DB_LIN.base;

import com.DB_LIN.beans.ConnectionLIN;
import com.DB_LIN.beans.PoolConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * Created by b_lin on 2017/2/9.
 */
public class ConnectionLINPool/* implements DataSource*/ {

    //创建一个连接池
    private static Queue<ConnectionLINProvider> usedPool = new ConcurrentLinkedQueue<ConnectionLINProvider>();

    private static Queue<ConnectionLINProvider> unUsedPool = new ConcurrentLinkedQueue<ConnectionLINProvider>();

    public static ThreadLocal<ConnectionLINProvider> currentConnectionLIN = new ThreadLocal<ConnectionLINProvider>();

    protected static PoolConfig poolConfig = null;

    //初始化10个连接
    static {
        try {
            File f = new File("mysqlConfig.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            Element pool = (Element) doc.getElementsByTagName("pool").item(0);
            poolConfig.setInitialSize(Integer.parseInt(pool.getElementsByTagName("initialSize").item(0).getFirstChild().getNodeValue()));
            poolConfig.setMaxSize(Integer.parseInt(pool.getElementsByTagName("maxSize").item(0).getFirstChild().getNodeValue()));
            poolConfig.setMaxFreeSize(Integer.parseInt(pool.getElementsByTagName("maxFreeSize").item(0).getFirstChild().getNodeValue()));
            poolConfig.setInCreaseSize(Integer.parseInt(pool.getElementsByTagName("inCreaseSize").item(0).getFirstChild().getNodeValue()));
            poolConfig.setTimeOut(Integer.parseInt(pool.getElementsByTagName("timeOut").item(0).getFirstChild().getNodeValue()));

            for (int i = 0; i < poolConfig.getInitialSize(); i++) {
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
    public ConnectionLINProvider getCurrentConnectionLIN() {
        try {
            if (currentConnectionLIN.get() == null) {
                ConnectionLINProvider conn = null;
                long startTime = System.currentTimeMillis();
                for (; ; ) {
                    if ((conn = unUsedPool.poll()) != null) {
                        usedPool.add(conn);
                        currentConnectionLIN.set(conn);
                        break;
                    } else {
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
    public boolean increasePool() {
        if (usedPool.size() + unUsedPool.size() + poolConfig.getInCreaseSize() < poolConfig.getMaxSize()) {
            ConnectionLINProvider connp = new ConnectionLINProvider();
            unUsedPool.add(connp);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        for (int i = 1; i < poolConfig.getInCreaseSize(); i++) {
                            ConnectionLINProvider connp = new ConnectionLINProvider();
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

    //释放资源
    public void release() {
        try {
            ConnectionLINProvider conn = currentConnectionLIN.get();
            if (conn != null) {
                conn.close();
                unUsedPool.add(currentConnectionLIN.get());
                usedPool.remove(currentConnectionLIN.get());
                currentConnectionLIN.remove();
            }
            decreasePool();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * 另起线程去除缩容
    * */
    private void decreasePool() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                int size = unUsedPool.size() + usedPool.size();
                if (size > poolConfig.getMaxSize()) {//max
                    for (int i = 0; i < (size - poolConfig.getMaxSize()); i++) {
                        if (unUsedPool.isEmpty()) {
                            break;
                        }
                        unUsedPool.remove();
                    }
                }
            }
        }.start();
    }

    public ConnectionLIN getConnectionForRead() {
        return getCurrentConnectionLIN().getConnectionForRead();
    }

    public ConnectionLIN getConnectionForWrite() {
        return getCurrentConnectionLIN().getConnectionForWrite();
    }

}
