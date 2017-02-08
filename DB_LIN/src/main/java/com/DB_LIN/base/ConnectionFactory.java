package com.DB_LIN.base;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import com.DB_LIN.beans.ConnectionLIN;
import com.DB_LIN.beans.PartDBLIN;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by b_lin on 2017/1/23.
 */
public class ConnectionFactory {

    public static List<ConnectionLIN> connectionLINs = new ArrayList<ConnectionLIN>();

    /*
    * 初始化所有的数据库连接
    * */
    public ConnectionFactory() {
        setConnectionList();
    }


    private static synchronized void setConnectionList() {
        try {
            connectionLINs = new ArrayList<ConnectionLIN>();
            File f = new File("mysqlConfig.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            NodeList conn = doc.getElementsByTagName("databases");
            for (int i = 0; i < conn.getLength(); i++) {
                Element node = (Element) conn.item(i);
                ConnectionLIN connectionLIN = new ConnectionLIN();
                connectionLIN.setId(node.getAttribute("id"));
                connectionLIN.setIsForWrite(Boolean.parseBoolean(node.getAttribute("isForWrite")));
                //NodeList dbList = node.getChildNodes();
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
                if (connectionLIN.getIsForWrite()) {
                    //写库放在第一位
                    connectionLINs.add(0, connectionLIN);
                } else {
                    connectionLINs.add(connectionLIN);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getIsForReadConnection() {
        if (connectionLINs.size() == 0) {
            setConnectionList();
        }
        Random random = new Random();
        int num = random.nextInt(connectionLINs.size()) + 1;
        return connectionLINs.get(num).getPartDBLIN().get(0).getConnection();
    }

    public static Connection getIsForWriteConnection() {
        if (connectionLINs.size() == 0) {
            setConnectionList();
        }
        return connectionLINs.get(0).getPartDBLIN().get(0).getConnection();
    }
}
