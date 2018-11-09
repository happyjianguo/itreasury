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
 * ���幦��˵����
 *      �˺�����Weblogic Server������ʱ�򣬱���Ϊ��ʼ��Class���أ����ص�CLass��InitServer.class
 *      �˺��������¹���
 *      1. �������ݿ⣺connection Pool�ͷ�Connection Pool����
 *      2. ��ó�ʼ��Context
 *      3. ������ݿ�ϵͳ�ĸ��ָ�ʽ��ʱ��
 *      4. ����ʼ��������ĵ�ַ
 *      5. �õ�һ��T3Service
 * ���ߣ�������
 * ʱ�䣺2001��7��31
 * �޸ģ�XXX               ʱ�䣺2001��07��31
 */
public class ConnectionSQLServer
{
	/**
	 * ��������Sqls���ݿ�     (ר�Ž���GLSettingInfo���ݵĲ���)
	 * 
	 * @return Connection
	 */
	public static boolean getConnection(GlSettingInfo info) throws Exception {
		boolean flag = false;
		Connection conn = null;
		try {
				Log.print("************�������ݿ�����(SQL Server)��ʼ*************");
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
					Log.print("************�������ݿ�����(SQL Server)����*****((�ɹ�!))********");
					conn.close();
					conn = null;
					flag = true;
				}
				else{
					Log.print("************�������ݿ�����(SQL Server)����*****((ʧ��!))********");
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
	 * ��������Oracle���ݿ�     (ר�Ž���GLSettingInfo���ݵĲ���)
	 * 
	 * @return Connection
	 */
	
	
	public static boolean getOracleConnection(GlSettingInfo info) throws Exception {
		boolean flag = false;
		Connection conn = null;

		try {
				Log.print("************�������ݿ�����(Oracle)��ʼ*************");
	            String DB_IP = info.getGlDBIP();			//IP
	            String DB_SID = info.getGlDBDatabaseName();	//������
	            String DB_USERNAME = info.getGlDBUserName();		//�û���
	            String DB_PASSWORD = DataFormat.formatNullString(info.getGlDBPassWord());	//����
	            String DB_PORT = info.getGlDBPort();		//�˿�
				
				Log.print(" Enter method --getOracleConnection() ");

				String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	            String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
	            
	            Log.print("dbURL = " + dbURL);
	            Log.print("DB_USERNAME = " + DB_USERNAME);
	            Log.print("DB_PASSWORD = " + DB_PASSWORD);

	            Class.forName(jdbcDriver).newInstance();
	            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);

				if (conn != null) {
					Log.print("************�������ݿ�����(Oracle)����*****((�ɹ�!))********");
					conn.close();
					conn = null;
					flag = true;
				}
				else{
					Log.print("************�������ݿ�����(Oracle)����*****((ʧ��!))********");
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
	 * ��������Sqls���ݿ� �˳�ר��
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
	 * �˷����������ݿ��б���Ĳ�����ֱ������Sqls���ݿ�
	 * @return Connection
	 */
	public static Connection getU8Connection(long lOfficeID,long lCurrencyID,long lType) throws Exception
	{
		Connection conn = null;

		try
		{		
				//�õ�������Ϣ
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
			throw new IException("���ʽ������ʧ��");
		}		
		
		return conn;
	}
	public static void main (String args[]) throws Exception{
		GlSettingInfo info=new GlSettingInfo();
		 info.setGlDBIP("10.211.12.12");			//IP
         info.setGlDBDatabaseName("prod");	//������
         info.setGlDBUserName("apps");		//�û���
         info.setGlDBPassWord("apps");	//����
         info.setGlDBPort("1521");		//�˿�
		boolean abc=ConnectionSQLServer.getOracleConnection(info);
		if(abc)
			System.out.println("aaaaaaaa");
		else
			System.out.println("bbbbbbbb");
		
	}
}