package com.iss.itreasury.audit.datamaintenance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Log;


 
/**
 * 
 * @author yiwang
 * @version
 */
public class Database
{
    public static DataSource m_datasource = null;

    /** Creates new Database */
    public Database()
    {
        super();
    }

    public static Connection get_1104jdbc_connection() throws Exception
    {
        Connection conn = null;
        try
        {
            String DB_IP = Config.getProperty( Config.GLOBAL_1104DB_IP ,"");
            String DB_SID = Config.getProperty( Config.GLOBAL_1104DB_SID ,"");
            String DB_USERNAME = Config.getProperty( Config.GLOBAL_1104DB_USERNAME ,"");
            String DB_PASSWORD = Config.getProperty( Config.GLOBAL_1104DB_PASSWORD ,"");
            String DB_PORT = Config.getProperty( Config.GLOBAL_1104DB_PORT ,"");

            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;

            Log.print("dbURL = " + dbURL);
            Log.print("DB_USERNAME = " + DB_USERNAME);
            Log.print("DB_PASSWORD = " + DB_PASSWORD);

            Class.forName(jdbcDriver).newInstance();
            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
        }
        catch (SQLException sqe)
        {
            Log.print("connect db failed by oracle jdbc driver. " + sqe.toString());
            throw sqe;
        }
        return conn;
    }

    public static DataSource getDatasource()
    {
        return m_datasource;
    }

}


