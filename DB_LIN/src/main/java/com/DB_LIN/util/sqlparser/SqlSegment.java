package com.DB_LIN.util.sqlparser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by b_lin on 2017/3/2.
 */
public class SqlSegment{
    private static final String Crlf = "@";
    private static final String FourSpace = "����";
/** *//**
     ��* Sql���Ƭ�ο�ͷ����
     ��*/
    private String start;
/** *//**
     ��* Sql���Ƭ���м䲿��
     ��*/
    private String body;
/** *//**
     ��* Sql���Ƭ�ν�������
     ��*/
    private String end;
/** *//**
     ��* ���ڷָ��м䲿�ֵ�������ʽ
     ��*/
    private String bodySplitPattern;
/** *//**
     ��* ��ʾƬ�ε�������ʽ
     ��*/
    private String segmentRegExp;
/** *//**
     ��* �ָ���BodyСƬ��
     ��*/
    private List<String> bodyPieces;
/** *//**
     ��* ���캯��
     ��* @param segmentRegExp ��ʾ���SqlƬ�ε�������ʽ
     ��* @param bodySplitPattern ���ڷָ�body��������ʽ
     ��*/
    public SqlSegment(String segmentRegExp,String bodySplitPattern){
        start="";
        body="";
        end="";
        this.segmentRegExp=segmentRegExp;
        this.bodySplitPattern=bodySplitPattern;
        this.bodyPieces=new ArrayList<String>();

    }
/** *//**
     ��* ��sql�в��ҷ���segmentRegExp�Ĳ��֣�����ֵ��start,body,end������������
     ��* @param sql
     ��*/
    public void parse(String sql){
        Pattern pattern=Pattern.compile(segmentRegExp,Pattern.CASE_INSENSITIVE);
        for(int i=0;i<=sql.length();i++)
        {
            String shortSql=sql.substring(0, i);
            //����������Ӿ��Ƿ���ȷ
            //System.out.println(shortSql);
            Matcher matcher=pattern.matcher(shortSql);
            while(matcher.find())
            {
                start=matcher.group(1);
                body=matcher.group(2);
                //����body����
                //System.out.println(body);
                end=matcher.group(3);
                //������Ӧ��end����
                //System.out.println(end);
                parseBody();
                return;
            }
        }
    }
/** *//**
     ��* ����body����
     ��*
     ��*/
    private void parseBody(){

        List<String> ls=new ArrayList<String>();
        Pattern p = Pattern.compile(bodySplitPattern,Pattern.CASE_INSENSITIVE);
        // �������ǰ��ո�
        body=body.trim();
        Matcher m = p.matcher(body);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        while(result)
        {
            m.appendReplacement(sb, m.group(0) + Crlf);
            result = m.find();
        }
        m.appendTail(sb);
        // �ٰ��ո����
        String[] arr=sb.toString().split(" ");
        int arrLength=arr.length;
        for(int i=0;i<arrLength;i++)
        {
            String temp=FourSpace+arr[i];
            if(i!=arrLength-1)
            {
                //temp=temp+Crlf;
            }
            ls.add(temp);
        }
        bodyPieces=ls;
    }
/** *//**
     ��* ȡ�ý����õ�SqlƬ��
     ��* @return
     ��*/
    public String getParsedSqlSegment(){
        StringBuffer sb=new StringBuffer();
        sb.append(start+Crlf);
        for(String piece:bodyPieces)
        {
            sb.append(piece+Crlf);
        }
        return sb.toString();
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body=body;
    }

    public String getEnd()
    {
        return end;
    }

    public void setEnd(String end)
    {
        this.end=end;
    }

    public String getStart()
    {
        return start;
    }


    public void setStart(String start)
    {
        this.start=start;
    }


}