/*
 * Created on 2006-3-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreateTableByDataEntity {

    private List createTableContent(String fileUrl, String tableName)
            throws IOException {
        LinkedList result = new LinkedList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileUrl));
            String sLine = "";
            while ((sLine = br.readLine()) != null) {
                sLine = sLine.trim();
                if (sLine.indexOf("private") >= 0) {
                    //用空格作分割符分割
                    String[] elements = sLine.split("\\s");
                    String fieldName = elements[2];
                    //如果有赋值语句必须去掉后面的语句才能取到字段
                    if (fieldName.indexOf("=") >= 0) {
                        fieldName = fieldName.substring(0, fieldName
                                .indexOf("="));
                    }
                    String targetLine = fieldName + " ";
                    if (elements[1].equalsIgnoreCase("long")) {
                        targetLine = targetLine + "Number,";
                    } else if (elements[1].equalsIgnoreCase("double")) {
                        targetLine = targetLine + "Number(21,6),";
                    } else if (elements[1].equalsIgnoreCase("String")) {
                        targetLine = targetLine + "Varchar2(100),";
                    } else if (elements[1].equalsIgnoreCase("Timestamp")) {
                        targetLine = targetLine + "Date,";
                    }
                    result.add(targetLine);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("error when reading file", e);
        } finally {
            br.close();
        }
        //清除最后一个逗号
        String lastLine = (String) result.removeLast();
        result.add(lastLine.substring(0, lastLine.length() - 1));
        return result;
    }

    private List createCommentContent(String fileUrl) throws IOException {
        LinkedList result = new LinkedList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileUrl));
            String sLine = "";
            while ((sLine = br.readLine()) != null) {
                sLine = sLine.trim();
                if (sLine.indexOf("//") >= 0) {
                    result.add("'" + sLine.substring(sLine.indexOf("//") + 2)
                            + "'");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("error when reading file", e);
        } finally {
            br.close();
        }
        return result;
    }
    
    private List createTableSpace(){
        List result=new LinkedList();
        result.add("tablespace insert your database name");
        result.add("pctfree 10");
        result.add("pctused 40");
        result.add("initrans 1");
        result.add("maxtrans 255");
        result.add("storage (");
        result.add("initial 64K");
        result.add("minextents 1");
        result.add("maxextents unlimited");
        result.add(");");
        return result;
    }
    
    private List createIndex(String tableName){
        List result=new LinkedList();
        result.add("create unique index PK_"+tableName+" on "+tableName+" (ID)");
        result.add("tablespace insert your database name");
        result.add("pctfree 10");
        result.add("initrans 2");
        result.add("maxtrans 255");
        result.add("storage (");
        result.add("initial 64K");
        result.add("minextents 1");
        result.add("maxextents unlimited");
        result.add(");");
        return result;
    }

    /**
     * 本方法有一个要求就是每个private字段有且只能有一行注释，可以和字段放在同一行
     * 也可以分两行放置
     * 请注意"//"的数量和private字段的数量一定要相同，否则会出错
     * 这里做得不好，限于时间紧迫
     * 这个方法是半自动化的，生成建表sql中的主要部分，但表空间和索引需要自己添加
     * 使用的时候在main方法里面调用本方法就可以
     * 默认生成table1.txt放在d:\下
     * @param fileUrl
     * @param tableName
     */
    public void createFile(String fileUrl, String tableName) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("d:/table1.txt", false));
            //表字段内容
            bw.write("create table " + tableName + " (");
            bw.newLine();
            List tableContent = createTableContent(fileUrl, tableName);
            for (Iterator i = tableContent.iterator(); i.hasNext();) {
                String sLine = (String) i.next();
                bw.write(sLine);
                bw.newLine();
            }
            bw.write(" )");
            bw.newLine();
            //表空间
            for(Iterator i=createTableSpace().iterator();i.hasNext();){
                bw.write((String)i.next());
                bw.newLine();
            }
            List commentContent = createCommentContent(fileUrl);
            //表注释
            for (int i = 0, n = commentContent.size(); i < n; i++) {
                String sLine = (String) commentContent.get(i);
                String sTableContent = (String) tableContent.get(i);
                //取字段名
                String[] elements = sTableContent.split("\\s");
                bw.write("comment on column " + tableName + "." + elements[0]
                        + " is " + sLine + ";");
                bw.newLine();
            }
            //索引
            for(Iterator i=createIndex(tableName).iterator();i.hasNext();){
                bw.write((String)i.next());
                bw.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException("error when writing file", e);
        } finally {
            try {
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("ok!");
    }

    public static void main(String[] args) {
        new CreateTableByDataEntity().createFile("d:/WorkingGroupSetting.java",
                "sett_templateGroupSetting");
    }
}