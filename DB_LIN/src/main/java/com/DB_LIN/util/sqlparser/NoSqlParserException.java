package com.DB_LIN.util.sqlparser;

/**
 * Created by b_lin on 2017/3/2.
 */
public class NoSqlParserException extends Exception{
    private static final long serialVersionUID = 1L;
    NoSqlParserException()
    {

    }
    NoSqlParserException(String sql)
    {
        //���ø��෽��
        super(sql);
    }
}
