package com.iss.itreasury.glinterface.u850;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.iss.itreasury.system.setting.dataentity.DatabaseConnectionSetInfo;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
//import javax.xml.parsers.* ;
//import org.w3c.dom.* ;
//import org.xml.sax.* ;
/*
 * Copyright (c) 1999-2001 AsiaEC.com. All Rights Reserved.
 * 总体功能说明：
 *      此函数在Weblogic Server启动的时候，被作为初始的Class加载，加载的CLass是InitServer.class
 *      此函数有如下功能
 *      1. 连接数据库：connection Pool和非Connection Pool两种
 *      2. 获得初始的Context
 *      3. 获得数据库系统的各种格式的时间
 *      4. 获得邮件服务器的地址
 *      5. 得到一个T3Service
 * 作者：张立安
 * 时间：2001－7－31
 * 修改：XXX               时间：2001－07－31
 */
public class ConnectionSQLServer
{
	/**
	 * 测试连接Sqls数据库     (专门接受GLSettingInfo数据的参数)
	 * 
	 * @return Connection
	 */
	public static boolean getConnection(GlSettingInfo info) throws Exception {
		boolean flag = false;
		Connection conn = null;
		try {
				Log.print("************测试数据库连接(SQL Server)开始*************");
				Properties props = new Properties();
				props.put("user", info.getGlDBUserName());
				props.put("password", info.getGlDBPassWord());
				props.put("server", info.getGlDBIP() + ":"
								+ info.getGlDBPort());
				props.put("DatabaseName", info.getGlDBDatabaseName());
				String jdbcDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
				String dbURL = "jdbc:microsoft:sqlserver://" + info.getGlDBIP()
						+ ":" + info.getGlDBPort();

				Class.forName(jdbcDriver).newInstance();
				Log.print("sqlserver properties1 : " + props.toString());
				Log.print("sqlserver dburl : " + dbURL);
				conn = DriverManager.getConnection(dbURL, props);

				if (conn != null) {
					Log.print("************测试数据库连接(SQL Server)结束*****((成功!))********");
					conn.close();
					conn = null;
					flag = true;
				}
				else{
					Log.print("************测试数据库连接(SQL Server)结束*****((失败!))********");
				}
		} catch (Exception e) {
			System.out.println("ConnectDB() IllegalAccess error:"
					+ e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return flag;
	}
	
	/**
	 * 测试连接Oracle数据库     (专门接受GLSettingInfo数据的参数)
	 * 
	 * @return Connection
	 */
	
	
	public static boolean getOracleConnection(GlSettingInfo info) throws Exception {
		boolean flag = false;
		Connection conn = null;

		try {
				Log.print("************测试数据库连接(Oracle)开始*************");
	            String DB_IP = info.getGlDBIP();			//IP
	            String DB_SID = info.getGlDBDatabaseName();	//库名称
	            String DB_USERNAME = info.getGlDBUserName();		//用户名
	            String DB_PASSWORD = DataFormat.formatNullString(info.getGlDBPassWord());	//密码
	            String DB_PORT = info.getGlDBPort();		//端口
				
				Log.print(" Enter method --getOracleConnection() ");

				String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	            String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
	            
	            Log.print("dbURL = " + dbURL);
	            Log.print("DB_USERNAME = " + DB_USERNAME);
	            Log.print("DB_PASSWORD = " + DB_PASSWORD);

	            Class.forName(jdbcDriver).newInstance();
	            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);

				if (conn != null) {
					Log.print("************测试数据库连接(Oracle)结束*****((成功!))********");
					conn.close();
					conn = null;
					flag = true;
				}
				else{
					Log.print("************测试数据库连接(Oracle)结束*****((失败!))********");
				}
					
		} catch (Exception e) {
			System.out.println("ConnectDB() IllegalAccess error:"
					+ e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return flag;
	}
	
	
	/**
	 * 测试连接Sqls数据库 浪潮专用
	 * @return Connection
	 */
	public static boolean getConnection(DatabaseConnectionSetInfo info) throws Exception
	{
		boolean flag = false;
		Connection conn = null;
		
		try
		{		
			//			
				Properties props = new Properties();
				props.put("user", info.getDatabaseUser());
				props.put("password", info.getDatabasePassword());
				props.put("server", info.getDatabaseIP() + ":" + info.getDatabasePort());
				props.put("DatabaseName",info.getDatabaseName());
				String jdbcDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
				String dbURL = "jdbc:microsoft:sqlserver://" + info.getDatabaseIP() + ":" + info.getDatabasePort();
				Class.forName(jdbcDriver).newInstance();
				Log.print("sqlserver properties1 : " + props.toString());
				Log.print("sqlserver dburl : " + dbURL);
				conn = DriverManager.getConnection(dbURL, props);
				
				if(conn!=null)
				{
					conn.close();
					conn = null;
					flag= true;
				}				
			
		}		
		catch(Exception e)
		{
			System.out.println("ConnectDB() IllegalAccess error:" + e.getMessage());
			e.printStackTrace();
			//throw new SQLException("ConnectDB() IllegalAccess error:" + e.getMessage());
			return false;
		}
		finally
		{
			if(conn!=null)
			{
				conn.close();
				conn = null;				
			}
		}	
		return flag;
	}
	
	/**
	 * 此方法根据数据库中保存的参数，直接连接Sqls数据库
	 * @return Connection
	 */
	public static Connection getU8Connection(long lOfficeID,long lCurrencyID,long lType) throws Exception
	{
		Connection conn = null;

		try
		{		
				//得到配置信息
				GlSettingInfo glSettingInfo=new GlSettingInfo();
				glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID,lType);
			
				Properties props = new Properties();
				props.put("user", glSettingInfo.getGlDBUserName());
				props.put("password", DataFormat.formatNullString(glSettingInfo.getGlDBPassWord()));
				props.put("server", glSettingInfo.getGlDBIP() + ":" + glSettingInfo.getGlDBPort());
				props.put("DatabaseName",glSettingInfo.getGlDBDatabaseName());
				String jdbcDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
				String dbURL = "jdbc:microsoft:sqlserver://" + glSettingInfo.getGlDBIP() + ":" + glSettingInfo.getGlDBPort();
				Class.forName(jdbcDriver).newInstance();
				Log.print("sqlserver properties3 : " + props.toString());
				Log.print("sqlserver dbur3 : " + dbURL);
				conn = DriverManager.getConnection(dbURL, props);
			
		}		
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("总帐结口连接失败");
		}		
		
		return conn;
	}
	public static void main (String args[]) throws Exception{
		GlSettingInfo info=new GlSettingInfo();
		 info.setGlDBIP("10.211.12.12");			//IP
         info.setGlDBDatabaseName("prod");	//库名称
         info.setGlDBUserName("apps");		//用户名
         info.setGlDBPassWord("apps");	//密码
         info.setGlDBPort("1521");		//端口
		boolean abc=ConnectionSQLServer.getOracleConnection(info);
		if(abc)
			System.out.println("aaaaaaaa");
		else
			System.out.println("bbbbbbbb");
		
	}
}