/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-6
 */
package com.iss.itreasury.treasuryplan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * 资金计划DAO基类
 */
public class TreasuryPlanDAO extends ITreasuryDAO {

	private Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	/**
	 * 数据库访问的目标模块
	 * */
	private long moduleID = Constant.ModuleType.PLAN;

	/**
	 * 资金计划使用的数据库连接
	 * */
	protected Connection tpConnection = null;
 
	
	/**
	 * 临时变量，以后移入配置文件
	 * 
	 * */
	final static String RAWDATA_DB_IP = "global.db.ip";
	final static String RAWDATA_DB_PORT = "global.db.port";	
	final static String RAWDATA_DB_SID = "global.db.sid";
	final static String RAWDATA_DB_USERNAME = "global.db.username";
	final static String RAWDATA_DB_PASSWORD = "global.db.password";	
	final static String TP_DB_IP = "global.db.ip";	
	final static String TP_DB_PORT = "global.db.port";	
	final static String TP_DB_SID = "global.db.sid";	
	final static String TP_DB_USERNAME = "global.db.username";
	final static String TP_DB_PASSWORD = "global.db.password";		
	
	public TreasuryPlanDAO(){

	}
	
	public TreasuryPlanDAO(String tableName){
		super(tableName);
	}
	
	public TreasuryPlanDAO(String tableName,Connection conn){
		super(tableName,conn);
	}		
	
	public TreasuryPlanDAO(long mouduleId){
		this.moduleID = mouduleId;
	}		
	

	/**
	 * 根据模块类型获取改模块对应的数据库连接
	 * 如果是资金结算模块
	 * */
	protected Connection establishConnectionByModuleID(long moduleID) throws Exception{
		String DB_IP = "";
		String DB_PORT = "";		
		String DB_SID = "";
		String DB_USERNAME = "";
		String DB_PASSWORD = "";
		

		boolean isReadOnly = true;
		switch((int)moduleID){
			case (int)TPConstant.ModuleType.SETTLEMENT:{
				DB_IP = RAWDATA_DB_IP;
				DB_PORT = RAWDATA_DB_PORT;		
				DB_SID = RAWDATA_DB_SID;
				DB_USERNAME = RAWDATA_DB_USERNAME;
				DB_PASSWORD = RAWDATA_DB_PASSWORD;				
			}
			break;
			case (int)TPConstant.ModuleType.LOAN:{
				DB_IP = RAWDATA_DB_IP;
				DB_PORT = RAWDATA_DB_PORT;		
				DB_SID = RAWDATA_DB_SID;						
				DB_USERNAME = RAWDATA_DB_USERNAME;
				DB_PASSWORD = RAWDATA_DB_PASSWORD;								
			}
			break;
			case (int)TPConstant.ModuleType.SECURITIES:{
				DB_IP = RAWDATA_DB_IP;
				DB_PORT = RAWDATA_DB_PORT;		
				DB_SID = RAWDATA_DB_SID;						
				DB_USERNAME = RAWDATA_DB_USERNAME;
				DB_PASSWORD = RAWDATA_DB_PASSWORD;								
			}
			break;
			case (int)TPConstant.ModuleType.GENERALLEDGER:{
				DB_IP = RAWDATA_DB_IP;
				DB_PORT = RAWDATA_DB_PORT;		
				DB_SID = RAWDATA_DB_SID;						
				DB_USERNAME = RAWDATA_DB_USERNAME;
				DB_PASSWORD = RAWDATA_DB_PASSWORD;								
			}
			break;
			case (int)TPConstant.ModuleType.PLAN:{
				DB_IP = TP_DB_IP;
				DB_PORT = TP_DB_PORT;		
				DB_SID = TP_DB_SID;				
				DB_USERNAME = TP_DB_USERNAME;
				DB_PASSWORD = TP_DB_PASSWORD;								
				isReadOnly = false;
			}			
			break;									
		}

		try{
			log.debug(" Enter method -- getConnectionByModuleID");

/*			String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
			String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;

			log.debug("dbURL = " + dbURL);
			log.debug("DB_USERNAME = " + DB_USERNAME);
			log.debug("DB_PASSWORD = " + DB_PASSWORD);

			Class.forName(jdbcDriver).newInstance();*/
			if(isReadOnly){
				//super.transConn  = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
				//super.transConn.setReadOnly(isReadOnly);
				log.debug("-------------isReadOnly:---Database.getConnection()()");
				super.transConn  = Database.getConnection();
				
				super.transConn.setReadOnly(isReadOnly);								
			}else
				//tpConnection = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
				log.debug("-------------Database.getConnection()");
			     
			   tpConnection = Database.getConnection();
				


						
		}		
		catch (SQLException sqe)
		{
			Log.print("connect db failed by oracle jdbc driver. " + sqe.toString());
			throw sqe;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tpConnection;				


	}
	
	/**
	 * 根据模块类别返回正在使用的数据库连接
	 * */
	public Connection getConnectionByModuleID(long mouduleID) throws Exception{
		if(mouduleID == Constant.ModuleType.PLAN)
			return tpConnection;
		else
			return transConn;
	}
	

	
	/**
	 * 数据库逻辑删除操作
	 * @param id　　　
	 * @param 
	 * @return
	 * @throws ITreasuryDAOException
	 */	
	public void delete(long id)  throws Exception{
		//To be modify the delete status defined in Constant
		updateStatus(id, 0);
		
	}

	/**
	 * 数据库更新操作操作
	 * @param id　　　
	 * @param statusID 需要更新到的状态
	 * @return
	 * @throws ITreasuryDAOException
	 */		
	public void updateStatus(long id, long statusID) throws Exception{

			try {
				initDAO();
				StringBuffer buffer = new StringBuffer();
				buffer.append("UPDATE \n");
				buffer.append(strTableName);
				buffer.append(" SET STATUSID = " + statusID);
				//TBD: maybe need add update execute date
				buffer.append("\n  WHERE ID = " + id);
				String strSQL = buffer.toString();
				log.debug(strSQL);
				prepareStatement(strSQL);
				executeQuery();
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw e;
			} finally{
				finalizeDAO();
			}

	}
	
	public String getClientCodeByID(long clientID) throws Exception{
		String res;
		try {
			res = null;
			initDAO();
			String strSQL = "select SCODE from client where id = "+ clientID;
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if(localRS.next())
				res = localRS.getString("scode");
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally{
			finalizeDAO();
		}
		return res;
	}
	
	
	public String getSECAccountCodeByID(long accountID) throws Exception{
		String res = null;
		try {
			initDAO();
			String strSQL = "select CODE from sec_account where id = "+ accountID;
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if(localRS.next())
				res = localRS.getString("code");
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally{
			finalizeDAO();
		}
		return res;
	}
	
	public String getCounterpartNameByID(long counterpartID) throws Exception{
		String res = null;
		try {
			initDAO();
			String strSQL = "select CODE from sec_Counterpart where id = "+ counterpartID;
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if(localRS.next())
				res = localRS.getString("CODE");
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally{
			finalizeDAO();
		}
		return res;
	}
	
	public String getSecuritiesNameByID(long SecuritiesID) throws Exception{
		String res = null;
		try {
			initDAO();
			String strSQL = "select NAME from sec_Securities where id = "+ SecuritiesID;
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if(localRS.next())
				res = localRS.getString("NAME");
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			finalizeDAO();
		}
		return res;
	}			

	public String getSecuritiesTransNameByID(long transTypeID) throws Exception{
		String res = null;
		try {
			initDAO();
			String strSQL = "select Name from SEC_TransactionType where id= "+ transTypeID;
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			if(localRS.next())
				res = localRS.getString("NAME");
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
		return res;
	}			
	
	public void closeModuleConn(long moduleTypeID) throws Exception{
		if(moduleTypeID == TPConstant.ModuleType.PLAN && tpConnection != null)
			tpConnection.close();
		else if(transConn != null){
			transConn.close();
		}
	}
	
	
	protected String transferTimestampToTo_DateString(Timestamp date){
		String time = date.toString();
		time = time.substring(0, 10);
		return "to_date('"+time+"','yyyy-mm-dd')";
	}
	
	protected String transferTimestampTo_DateString(Timestamp date){
		String time = date.toString();
		time = time.substring(0, 10);
		return time;
	}
	
	public void deleteExtractDataByDate(Timestamp date) throws Exception{
		String strSQL = "delete from "+strTableName;
		if(date != null){
			String strDate = transferTimestampToTo_DateString(date);
			strSQL += " where transactiondate = "+strDate;
		}

		try {
			prepareStatement(strSQL);
			executeQuery();
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}
	
	public void deleteExtractDataByDate(Timestamp sDate,Timestamp eDate) throws Exception{
		String strSQL = "delete from "+strTableName;

			String strSDate = transferTimestampToTo_DateString(sDate);
			String strEDate = transferTimestampToTo_DateString(eDate);
			strSQL += " where transactiondate >= "+strSDate;
			strSQL += " and transactiondate <= "+strEDate;

		try
		{
			prepareStatement(strSQL);
			executeQuery();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			finalizeDAO(); 
		}
	}	
}
