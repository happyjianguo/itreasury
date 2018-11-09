/*
 * Created on 2005-5-10
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.iss.itreasury.fcinterface.bankportal.constant.DBConnectionType;
import com.iss.itreasury.fcinterface.bankportal.constant.DatabaseType;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * 数据库对象<br>
 * 提供多连接方式，多数据库的支持
 * @author mxzhou
 */
public class Database
{
	/**日志对象*/
	private static Logger log = new Logger(Database.class);
	/**静态数据源对象*/
    private static DataSource m_datasource = null;
    /**
     * 构造方法
     */
    public Database()
    {
        super();
    }
    
    /**
     * 获取指定方式的数据库连接<br>
     * 连接方式有系统环境指定
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws SystemException
    {
        Connection conn = null;
        try
        {
        	String dbConnectionType = Env.getEnvConfigItem(Env.DB_CONNECTION_TYPE);
        	if(DBConnectionType.TYPE4.equalsIgnoreCase(dbConnectionType))
            {
        		conn = get_type4_connection();
            }
        	else if(DBConnectionType.JDBC.equalsIgnoreCase(dbConnectionType))
        	{
        		conn = get_jdbc_connection();
        	}
        	else
        	{
        		throw new SystemException("未知的数据库连接类型："+dbConnectionType);
        	}
        }
        catch (Exception e)
        {
                throw new SystemException("BP_0032:获取数据库连接时出现异常，出错原因："+e.getMessage(), e);
        }
        return conn;
    }
    
    /**
     * 用任意方式获取数据库连接<br>
     * 当前如果获取type4连接失败会再继续连接jdbc
     * @return
     * @throws Exception
     */
    public static Connection getAnyConnection() throws Exception
    {
        Connection conn = null;
        // Lookup the datasource
        try
        {
            conn = get_type4_connection();
        }
        catch (Exception e_type4)
        {
            try
            {
                conn = get_jdbc_connection();
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        return conn;
    }
    
    
    public static Connection get_type4_connection() throws Exception
    {
//        log.info("enter Database.get_type4_connection()");
        Connection conn = null;
        //Lookup the datasource
        try
        {
            if (m_datasource == null)
            {
				String type4Name = Env.getEnvConfigItem(Env.DB_TYPE4_NAME);
                InitialContext ctx = new javax.naming.InitialContext();
//                log.info("查询type4:"+ "java:comp/env/"+type4Name);
                m_datasource = (javax.sql.DataSource) ctx.lookup("java:comp/env/"+type4Name);//名称形如"java:comp/env/jdbc/cpf/type4"
            }
        }
        catch (NamingException ne)
        {
        	log.error("lookup DB JNDI Name failed by type4 driver. " + ne.toString());
            throw ne;
        }
        //Get the connection
        try
        {
            conn = m_datasource.getConnection();
        }
        catch (SQLException sqe)
        {
        	log.error("connect db failed by type4 driver. " + sqe.toString());
            throw sqe;
        }
        return conn;
    }

    public static Connection get_jdbc_connection() throws Exception
    {
//        log.info("enter Database.get_jdbc_connection()");
        Connection conn = null;
        try
        {
        	String dbType = Env.getEnvConfigItem(Env.DB_TYPE);
        	if(DatabaseType.getName(DatabaseType.ORACLE).equalsIgnoreCase(dbType))
        	{
	            String DB_IP = Env.getEnvConfigItem(Env.DB_IP);
	            String DB_SID = Env.getEnvConfigItem(Env.DB_SID);
	            String DB_USERNAME = Env.getEnvConfigItem(Env.DB_USERNAME);
	            String DB_PASSWORD = Env.getEnvConfigItem(Env.DB_PASSWORD);
	            String DB_PORT = Env.getEnvConfigItem(Env.DB_PORT);
	
	            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	            String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
	
//	            log.debug("DB_URL = " + dbURL);
//	            log.debug("DB_USERNAME = " + DB_USERNAME);
//	            log.debug("DB_PASSWORD = " + DB_PASSWORD);
	
	            Class.forName(jdbcDriver).newInstance();
	            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
        	}
        	else if(DatabaseType.getName(DatabaseType.MYSQL).equalsIgnoreCase(dbType))
        	{
        		
        	}
        	else if(DatabaseType.getName(DatabaseType.DB2).equals(dbType))
        	{
        		String DB_IP = Env.getEnvConfigItem(Env.DB_IP);
        		String DB_SID = Env.getEnvConfigItem(Env.DB_SID);
	            String DB_USERNAME = Env.getEnvConfigItem(Env.DB_USERNAME);
	            String DB_PASSWORD = Env.getEnvConfigItem(Env.DB_PASSWORD);
	            String DB_PORT = Env.getEnvConfigItem(Env.DB_PORT);
	
	            String jdbcDriver = "com.ibm.db2.jcc.DB2Driver";
	            String dbURL = "jdbc:db2://" + DB_IP + ":" + DB_PORT + "/" + DB_SID;

	            Class.forName(jdbcDriver).newInstance();
	            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
        	
        	}
        	else
        	{
        		throw new SystemException("未知的数据库类型："+dbType);
        	}
        }
        catch (SQLException sqe)
        {
            log.error("connect db failed by oracle jdbc driver. " + sqe.toString());
            throw sqe;
        }
        return conn;
    }

    public static DataSource getDatasource()
    {
        return m_datasource;
    }

}