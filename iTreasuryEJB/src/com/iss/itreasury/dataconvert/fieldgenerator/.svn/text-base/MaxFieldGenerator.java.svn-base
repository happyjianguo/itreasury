/*
 * Created on 2006-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.fieldgenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.TRF_Exception;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MaxFieldGenerator implements FieldGenerator{
    private Connection connection=null;
    private boolean isInTrasaction=false;
    
    /**
     * 可以通过设置connection的方式让generator运行在事务中
     * @param connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
        isInTrasaction=true;
    }
    
    /**
     * 用取最大值加一的方式取字段值
     * @throws TRF_Exception
     */
    public Object generateValue(String tableName,String fieldName) {
        StringBuffer strSql=new StringBuffer();
        strSql.append("select nvl(max(to_number(");
        strSql.append(fieldName+"))+1,1) \n");
        strSql.append("from "+tableName);
        Object result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //不在事务中或者事务中的connection已经被关闭置空才能获取新的connection
            if(!isInTrasaction||connection==null||connection.isClosed()){
                connection=DataBaseUtil.getConnection();
                isInTrasaction=false;
            }
            ps = connection.prepareStatement(strSql.toString());
            rs = ps.executeQuery();
            rs.next();
            result = rs.getObject(1);
        } catch (Exception e) {
            throw new TRF_Exception("database error occurs",e);
        } finally {
            if(!isInTrasaction){
               DataBaseUtil.closeDataBaseResource(connection,null,ps,rs);
            }
        }
        return result;
    }
}
