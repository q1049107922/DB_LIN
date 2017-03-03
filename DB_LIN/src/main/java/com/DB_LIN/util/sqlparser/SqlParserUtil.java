package com.DB_LIN.util.sqlparser;

import java.util.List;

/**
 * Created by b_lin on 2017/3/2.
 */
public class SqlParserUtil {
    /**
     * ��* ��������Ҫ���
     * ��* @param sql:Ҫ������sql���
     * ��* @return ���ؽ������
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
     ��* SQL�������Ľӿ�
     ��* @param sql:Ҫ������sql���
     ��* @return ���ؽ������
     ��*/
    public List<SqlSegment> getParsedSqlList(String sql) {
        sql = sql.trim();
        sql = sql.toLowerCase();
        sql = sql.replaceAll("\\s{1,}", " ");
        sql = "" + sql + " ENDOFSQL";
        //System.out.println(sql);
        return SingleSqlParserFactory.generateParser(sql).RetrunSqlSegments();
    }
}
