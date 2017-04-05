package com.DB_LIN.util.func;

import com.DB_LIN.beans.ConnectionLIN;
import com.DB_LIN.beans.DataNodeConfig;
import com.DB_LIN.beans.DataSourceConfig;
import com.DB_LIN.beans.PoolConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.util.Properties;

/**
 * Created by b_lin on 2017/2/8.
 */
public class XmlToBean {

    public ConnectionLIN XmlToConnectionLIN() {


        return null;
    }

    public static Element getDataSourceElementByProperty(String propertyValue,String propertyName) {
        File f = new File("mysqlConfig.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList databases = doc.getElementsByTagName("database");
            for (int i = 0; i < databases.getLength(); i++) {
                Element db = (Element)databases.item(i);
                if(db.getAttribute(propertyName).equals(propertyValue)){
                    return db;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DataNodeConfig getDataNodeConfigById(String id){

        return null;
    }

    //public static

    public static DataSourceConfig getDataSourceConfigByTag(String tag){
        DataSourceConfig ds = new DataSourceConfig();
        Element db = getDataSourceElementByProperty(tag,"tag");
        ds.setTag(tag);
        ds.setDbName(db.getAttribute("dbName"));
        ds.setParentId(db.getAttribute("parentId"));
        ds.setId(db.getAttribute("id"));
        ds.setUrl(db.getElementsByTagName("url").item(0).getFirstChild().getNodeValue());
        ds.setUsername(db.getElementsByTagName("username").item(0).getFirstChild().getNodeValue());
        ds.setPassword(db.getElementsByTagName("password").item(0).getFirstChild().getNodeValue());
        return ds;
    }

    public static PoolConfig getPoolConfig(){
        PoolConfig poolConfig = null;
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

        } catch (Exception e) {
            throw new ExceptionInInitializerError("数据库连接失败，请检查配置");
        }
        return poolConfig;
    }

}
