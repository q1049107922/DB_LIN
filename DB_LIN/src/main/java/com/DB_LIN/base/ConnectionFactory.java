package com.DB_LIN.base;


import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import com.DB_LIN.beans.ConnectionLIN;
import org.w3c.dom.Document;
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

            NodeList nl = doc.getElementsByTagName("connection");
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                String username = null;
                String url = null;
                String password = null;
                if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList conProList = node.getChildNodes();
                    for (int j = 0; j < conProList.getLength(); j++) {
                        Node conPro = conProList.item(j);
                        if (conPro != null && conPro.getNodeType() == Node.ELEMENT_NODE) {
                            if (conPro.getNodeName().toLowerCase().equals("url")) {
                                url = conPro.getTextContent();
                            }
                        }
                    }
                }
                ConnectionLIN connectionLIN =new ConnectionLIN();
                Connection con = DriverManager.getConnection(url, username, password);
                connectionLIN.setConnection(con);
                if (doc.getElementsByTagName("isForWrite").item(i).getFirstChild().getNodeValue().toLowerCase().equals("true")) {
                    //写库放在第一位
                    connectionLIN.setIsForWrite(true);
                    connectionLINs.add(0,connectionLIN);
                } else {
                    connectionLIN.setIsForWrite(false);
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
        int num = random.nextInt(connectionLINs.size())+1;
        return connectionLINs.get(num).getConnection();
    }

    public static Connection getIsForWriteConnection() {
        if (connectionLINs.size() == 0) {
            setConnectionList();
        }
        return connectionLINs.get(0).getConnection();
    }
}
