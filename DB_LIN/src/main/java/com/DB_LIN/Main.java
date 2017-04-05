package com.DB_LIN;

import com.DB_LIN.base.DAL;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here

        int s = 585 / 100;
        try {
            getTableNameBySql("select * from user u join user2 u2 on u.id=u2.id");

            DAL dal = new DAL();

            dal.select("select * from t_user");
            int n = dal.insert("insert into t_userProduct(`id`,`userId`,`productName`) values ('id','1_0u001','ÍÛ¹þ¹þ')");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTableNameBySql(String sql) throws JSQLParserException{
        CCJSqlParserManager parser=new CCJSqlParserManager();
        StringReader reader=new StringReader(sql);
        List<String> list=new ArrayList<String>();
        Statement stmt=parser.parse(new StringReader(sql));
        if (stmt instanceof Select) {
            Select selectStatement = (Select) stmt;
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

            List tableList = tablesNamesFinder.getTableList(selectStatement);
            for (Iterator iter = tableList.iterator(); iter.hasNext();) {
                String tableName=iter.next().toString();
                list.add(tableName);
            }
        }
        return list;
    }
    /*
    * sql like 'select * from db.table'
    *
    * return like 'select * from db1.table' or 'select * from db2.table'
    * */
    public void select(String sql) {

    }

}
