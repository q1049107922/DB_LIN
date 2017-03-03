package com.DB_LIN.util.sqlparser;

import java.util.List;

/**
 * Created by b_lin on 2017/3/2.
 */
public class Test {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //String test="select  a from  b " +
        //    "\n"+"where      a=b";
        //test=test.replaceAll("\\s{1,}", " ");
        //System.out.println(test);
        //程序的入口
        String testSql = "select c1,c2,c3     from    t1,t2 where condi3=3 " + "\n" + "    or condi4=5 order by o1,o2";
        testSql = "select * from user u where u.userId='1' limit 0,10";
        SqlParserUtil test = new SqlParserUtil();
        String result = test.getParsedSql(testSql);
        System.out.println(result);
        List<SqlSegment> resultList=test.getParsedSqlList(testSql);//保存解析结果
    }
}
