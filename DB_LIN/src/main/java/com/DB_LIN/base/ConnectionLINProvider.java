package com.DB_LIN.base;


import java.io.File;
import javax.naming.ConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import com.DB_LIN.beans.ConnectionLIN;
import com.DB_LIN.beans.PartDBLIN;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by b_lin on 2017/1/23.
 */
public class ConnectionLINProvider {
    //一个线程只需要读和写两个链接即可；无需浪费过多的读库链接
    private ConnectionLIN connForRead;

    private ConnectionLIN connForWrite;

    /*
    * 初始化所有的数据库连接
    * */
    public ConnectionLINProvider() {
        setConnectionList();
    }

    private void setConnectionList() {
        setConnForRead();
        setConnForWrite();
    }

    private void setConnForWrite() {
        try {
            File f = new File("mysqlConfig.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            NodeList conn = doc.getElementsByTagName("databases");
            for (int i = 0; i < conn.getLength(); i++) {
                Element node = (Element) conn.item(i);
                ConnectionLIN connectionLIN = new ConnectionLIN();
                connectionLIN.setId(node.getAttribute("id"));
                connectionLIN.setIsForWrite(Boolean.parseBoolean(node.getAttribute("isForWrite")));
                if (connectionLIN.getIsForWrite()) {
                    NodeList dbList = node.getElementsByTagName("database");
                    List<PartDBLIN> partDBLINList = new ArrayList<PartDBLIN>();
                    for (int k = 0; k < dbList.getLength(); k++) {
                        PartDBLIN partDBLIN = new PartDBLIN();
                        Element db = (Element) dbList.item(k);
                        partDBLIN.setId(db.getAttribute("id"));
                        partDBLIN.setParentId(db.getAttribute("parentId"));
                        partDBLIN.setTag(db.getAttribute("tag"));
                        Connection con = DriverManager.getConnection(
                                db.getElementsByTagName("url").item(0).getFirstChild().getNodeValue()
                                , db.getElementsByTagName("username").item(0).getFirstChild().getNodeValue()
                                , db.getElementsByTagName("password").item(0).getFirstChild().getNodeValue()
                        );
                        partDBLIN.setConnection(con);
                        partDBLINList.add(partDBLIN);
                    }
                    connectionLIN.setPartDBLIN(partDBLINList);
                    connForWrite = connectionLIN;
                } else {
                    //没有写库
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setConnForRead() {
        try {
            File f = new File("mysqlConfig.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList conn = doc.getElementsByTagName("databases");
            //随机一个读库
            Random ran = new Random();
            int i = ran.nextInt(conn.getLength());
            Element node = (Element) conn.item(i);
            ConnectionLIN connectionLIN = new ConnectionLIN();
            connectionLIN.setId(node.getAttribute("id"));
            connectionLIN.setIsForWrite(Boolean.parseBoolean(node.getAttribute("isForWrite")));
            if (!connectionLIN.getIsForWrite()) {//读库
                NodeList dbList = node.getElementsByTagName("database");
                List<PartDBLIN> partDBLINList = new ArrayList<PartDBLIN>();
                for (int k = 0; k < dbList.getLength(); k++) {
                    PartDBLIN partDBLIN = new PartDBLIN();
                    Element db = (Element) dbList.item(k);
                    partDBLIN.setId(db.getAttribute("id"));
                    partDBLIN.setParentId(db.getAttribute("parentId"));
                    partDBLIN.setTag(db.getAttribute("tag"));
                    Connection con = DriverManager.getConnection(
                            db.getElementsByTagName("url").item(0).getFirstChild().getNodeValue()
                            , db.getElementsByTagName("username").item(0).getFirstChild().getNodeValue()
                            , db.getElementsByTagName("password").item(0).getFirstChild().getNodeValue()
                    );
                    partDBLIN.setConnection(con);
                    partDBLINList.add(partDBLIN);
                }
                connectionLIN.setPartDBLIN(partDBLINList);
                connForRead = connectionLIN;
                return;
            } else {
                setConnForRead();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConnectionLIN getConnectionForRead() {
        if (connForRead == null) {
            setConnForRead();
        }
        return connForRead;
    }

    public ConnectionLIN getConnectionForWrite() {
        if (connForWrite == null) {
            setConnForWrite();
        }
        return connForWrite;
    }

    public void close() throws SQLException {
        for (int i = 0; i < connForRead.getPartDBLIN().size(); i++) {
            PartDBLIN db = connForRead.getPartDBLIN().get(i);
            db.getConnection().close();
        }
        for (int i = 0; i < connForRead.getPartDBLIN().size(); i++) {
            PartDBLIN db = connForRead.getPartDBLIN().get(i);
            db.getConnection().close();
        }
    }
}
