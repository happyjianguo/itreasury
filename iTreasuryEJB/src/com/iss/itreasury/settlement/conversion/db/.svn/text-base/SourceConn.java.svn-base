/*
 * Created on 2004-11-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SourceConn {
	private  String driver="oracle.jdbc.driver.OracleDriver";
	private  String url;
	private  String user;
	private  String password;
	private  Connection conn;
	//private  String path = SourceConn.class.getResource("").getPath();
	//private  SourceConn sc = new SourceConn();

	/**
	 * 
	 */
	
	public SourceConn() {
		this.initParameter();
        this.initConn();
	}
	/*
	 private  SourceConn newInstance(){
		initParameter();
		try {
			if(conn==null || conn.isClosed())
			   initConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sc;
	}*/
	
	private  void initParameter(){
		InputStream input = null;
		try {
			input = new FileInputStream("haier_wb_db.properties");
			Properties p = new Properties();
			p.load(input);
			driver=p.getProperty("sourceDriver","oracle.jdbc.driver.OracleDriver");
			url=p.getProperty("sourceURL","jdbc:oracle:thin:@192.168.9.100:1522:db");
			user = p.getProperty("sourceUser","haier");
			password = p.getProperty("sourcePassword","haier");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(input!=null)
				   input.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    }
	}
	private void initConn(){
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, password);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}
	
	public Connection getConn(){
		try {
			conn = getConnection();
		} catch (Exception e) {
			this.initParameter();
			this.initConn();
		}
		if(conn==null){
			System.out.println("source conn is null");
		}else{
			System.out.println("Call source conn");
		}
		return conn;
	}
	
	public static DataSource m_datasource = null;
	public static Connection getConnection() throws Exception
	{
		Connection conn = null;
		// Lookup the datasource
		try
		{
			if (m_datasource == null)
			{
				InitialContext ctx = new javax.naming.InitialContext();
				m_datasource = (javax.sql.DataSource) ctx.lookup("jdbc/cpf/type4");
			}
		}
		catch (NamingException ne)
		{
			Log.print("lookup DB JNDI Name failed by type4 driver. " + ne.toString());
			throw ne;
		}

		// Get the connection
		try
		{
			conn = m_datasource.getConnection();
		}
		catch (SQLException sqe)
		{
			Log.print("connect db failed by type4 driver. " + sqe.toString());
			throw sqe;
		}
		return conn;
	}

	public static void main(String[] args) {
	}
}
