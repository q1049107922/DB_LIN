package com.DB_LIN.base;

import java.beans.Statement;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
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
public class ConnectionFactory {

    public static List<Statement> statementList = new ArrayList<Statement>();

    /*
    * 初始化所有的数据库连接
    * */
    public ConnectionFactory() {
        setConnectionList();
    }

    private void setConnectionList() {
        try {
            File f = new File("mysqlConfig.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList nl = doc.getElementsByTagName("connection");
            for (int i = 0; i < nl.getLength(); i++) {
                Connection con =
                        DriverManager.getConnection(doc.getElementsByTagName("url").item(i).getFirstChild().getNodeValue()
                                , doc.getElementsByTagName("username").item(i).getFirstChild().getNodeValue()
                                , doc.getElementsByTagName("password").item(i).getFirstChild().getNodeValue());
                statementList.add((Statement) con.createStatement());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatement() {
        // statement用来执行SQL语句
        Random random = new Random();
        //目前 DB0為寫入庫，其他為只讀
        int num = random.nextInt(statementList.size() - 1) + 1;
        Statement statement = (Statement) statementList.get(num);
        return statement;
    }
}
