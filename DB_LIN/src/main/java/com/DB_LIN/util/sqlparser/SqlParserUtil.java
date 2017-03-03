package com.DB_LIN.util.sqlparser;

import java.util.List;

/**
 * Created by b_lin on 2017/3/2.
 */
public class SqlParserUtil {
    /**
     * 　* 方法的主要入口
     * 　* @param sql:要解析的sql语句
     * 　* @return 返回解析结果
     *
     */
    public String getParsedSql(String sql) {
        sql = sql.trim();
        sql = sql.toLowerCase();
        sql = sql.replaceAll("\\s{1,}", " ");
        sql = "" + sql + " ENDOFSQL";
        System.out.println(sql);
        return SingleSqlParserFactory.generateParser(sql).getParsedSql();
    }

    /*
     　* SQL语句解析的接口
     　* @param sql:要解析的sql语句
     　* @return 返回解析结果
     　*/
    public List<SqlSegment> getParsedSqlList(String sql) {
        sql = sql.trim();
        sql = sql.toLowerCase();
        sql = sql.replaceAll("\\s{1,}", " ");
        sql = "" + sql + " ENDOFSQL";
        //System.out.println(sql);
        return SingleSqlParserFactory.generateParser(sql).RetrunSqlSegments();
    }
}
