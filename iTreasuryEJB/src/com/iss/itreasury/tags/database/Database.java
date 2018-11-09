/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.tags.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 
 * @author yiwang
 * @version
 */
public class Database implements IDatabase
{
    public static DataSource m_datasource = null;

    /** Creates new Database */
    public Database()
    {
        super();
    }

    public static Connection getConnection() throws Exception
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
            catch (Exception e_jdbc)
            {
                throw e_jdbc;
            }
        }
        if (conn != null)
        {
            
        }
        /////记录创建的连接

        return conn;
    }

    public static Connection get_type2_connection() throws Exception
    {
        Connection conn = null;
        // Lookup the datasource
        try
        {
            if (m_datasource == null)
            {

                InitialContext ctx = new javax.naming.InitialContext();
                m_datasource = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/cpf/type2");
            }
        }
        catch (NamingException ne)
        {
            System.out.print("lookup DB JNDI Name failed by type2 driver. " + ne.toString());
            throw ne;
        }

        // Get the connection
        try
        {
            conn = m_datasource.getConnection();
        }
        catch (SQLException sqe)
        {
            System.out.print("connect db failed by type2 driver. " + sqe.toString());
            throw sqe;
        }
        return conn;
    }

    public static Connection get_type4_connection() throws Exception
    {
        Connection conn = null;
        // Lookup the datasource
        try
        {
            if (m_datasource == null)
            {
                InitialContext ctx = new javax.naming.InitialContext();
                m_datasource = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/cpf/type4");
            }
        }
        catch (NamingException ne)
        {
            System.out.print("lookup DB JNDI Name failed by type4 driver. " + ne.toString());
            throw ne;
        }

        // Get the connection
        try
        {
            conn = m_datasource.getConnection();
        }
        catch (SQLException sqe)
        {
            System.out.print("connect db failed by type4 driver. " + sqe.toString());
            throw sqe;
        }
        return conn;
    }

    public static Connection get_jdbc_connection() throws Exception
    {
        Connection conn = null;
        /*try
        {
            String DB_IP = Config.getProperty( Config.GLOBAL_DB_IP ,"");
            String DB_SID = Config.getProperty( Config.GLOBAL_DB_SID ,"");
            String DB_USERNAME = Config.getProperty( Config.GLOBAL_DB_USERNAME ,"");
            String DB_PASSWORD = Config.getProperty( Config.GLOBAL_DB_PASSWORD ,"");
            String DB_PORT = Config.getProperty( Config.GLOBAL_DB_PORT ,"");

            System.out.print(" Enter method -- Database.get_jdbc_connection()");

            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;

            System.out.print("dbURL = " + dbURL);
            System.out.print("DB_USERNAME = " + DB_USERNAME);
            System.out.print("DB_PASSWORD = " + DB_PASSWORD);

            Class.forName(jdbcDriver).newInstance();
            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
        }
        catch (SQLException sqe)
        {
            System.out.print("connect db failed by oracle jdbc driver. " + sqe.toString());
            throw sqe;
        }*/
        return conn;
    }

    public static DataSource getDatasource()
    {
        return m_datasource;
    }

	public Connection getTagConnection() throws Exception
	{
		return getConnection();
	}
    
    

}