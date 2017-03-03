package com.DB_LIN.util.sqlparser;

/**
 * Created by b_lin on 2017/3/2.
 */
public class DeleteSqlParser extends BaseSingleSqlParser{
    public DeleteSqlParser(String originalSql) {
        super(originalSql);
    }
    @Override
    protected void initializeSegments() {
        segments.add(new SqlSegment("(delete from)(.+)( where | ENDOFSQL)","[,]"));
        segments.add(new SqlSegment("(where)(.+)( ENDOFSQL)","(and|or)"));
    }
}