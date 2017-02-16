package com.DB_LIN.base;

import com.DB_LIN.beans.ConnectionLIN;

import javax.sql.DataSource;
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

    static int poolSize;
    //����һ�����ӳ�
    private static Queue<ConnectionLINProvider> usedPool = new ConcurrentLinkedQueue<ConnectionLINProvider>();

    private static Queue<ConnectionLINProvider> unUsedPool = new ConcurrentLinkedQueue<ConnectionLINProvider>();

    public static ThreadLocal<ConnectionLINProvider> currentConnectionLIN = new ThreadLocal<ConnectionLINProvider>();

    //��ʼ��10������
    static {
        try {
            for (int i = 0; i < 10; i++) {
                ConnectionLINProvider connp = new ConnectionLINProvider();
                unUsedPool.add(connp);
            }
            poolSize = 10;
        } catch (Exception e) {
            throw new ExceptionInInitializerError("���ݿ�����ʧ�ܣ���������");
        }
    }

    public ConnectionLINPool() {

    }

    /*
    *
    * */
    public ConnectionLINProvider getConnectionFromPool() {
        if (currentConnectionLIN.get() == null) {
            if(unUsedPool.isEmpty()){
                increasePool();
            }
            ConnectionLINProvider conn = unUsedPool.poll();
            usedPool.add(conn);
            currentConnectionLIN.set(conn);
        }
        return currentConnectionLIN.get();
    }

    /*
    * ����һ���߳������̳߳�
    * */
    public void increasePool(){
        ConnectionLINProvider connp = new ConnectionLINProvider();
        unUsedPool.add(connp);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    for (int i = 0; i < 4; i++) {
                        ConnectionLINProvider connp = new ConnectionLINProvider();
                        unUsedPool.add(connp);
                    }
                } catch (Exception e) {
                    throw new ExceptionInInitializerError("���ݿ�����ʧ�ܣ���������");
                }
            }
        }.start();
    }

    //�ͷ���Դ
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
    * �����߳�ȥ������
    * */
    private void decreasePool(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                int ss = unUsedPool.size();
                if(ss>100){//max
                    for (int i = 0; i < (ss-100); i++) {
                        if(unUsedPool.isEmpty()){
                            break;
                        }
                        unUsedPool.remove();
                    }
                }
            }
        }.start();
    }

    public ConnectionLIN getConnectionForRead() {
        return getConnectionFromPool().getConnectionForRead();
    }

    public ConnectionLIN getConnectionForWrite() {
        return getConnectionFromPool().getConnectionForWrite();
    }

    public ConnectionLINProvider getCurrentConnectionLIN() {
        return currentConnectionLIN.get();
    }
}
