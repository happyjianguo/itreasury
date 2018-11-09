/*
 * Created on 2006-3-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.iss.itreasury.util.Database;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DataBaseUtil {
    /**
     * 关闭数据库资源
     * @param con
     * @param stmt
     * @param ps
     * @param rs
     * @throws RuntimeException 关闭资源发生异常时
     */
    public static void closeDataBaseResource(Connection con, Statement stmt,
            PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null&&!con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库关闭异常发生", e);
        }
    }
    
    /**
     * 用于处理回滚的辅助方法
     * 处理掉回滚过程中抛出的异常
     * @param con
     */
    public static void rollBackConnection(Connection con){
        try {
            if(con!=null){
               con.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库回滚异常发生", e);
        }
    }
    
    /**
     * @throws RuntimeException 获取连接异常
     * @return
     */
    public static Connection getConnection(){
        Connection result=null;
        try {
            result=Database.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("获取连接过程中发生异常");
        }
        return result;
    }
}