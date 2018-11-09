/*
* Created on 2003-9-10
*
* To change the template for this generated file go to
* Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.util;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Database;
//import com.iss.itreasury.util.Log4j;
//import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.*;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j; 
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
public class OBOperation 
{

	//private static Log4j log4j = null;
	private static int CYCLE_NUM = 50;//等待的次数
	private static int SLEEP_TIME = 100;//每次等待的时间，单位毫秒
	private static String ENTITY_NAME = "OB_INSTRNO";
	//private static Log4j log=null;
	
	public OBOperation() 
	{ 
		//log = new Log4j(Constant.ModuleType.EBANK, this);		
	}

	public static boolean isLocked (String strTableName) throws Exception
	{
		
		Connection con = null;
		boolean bReturn = true;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer ();
			sb.append ("update OB_LockTable set nLocked=1 where nlocked=0 and sEntity=? ");
			System.out.println("sql:"+sb.toString ());
			ps = con.prepareStatement (sb.toString ());
			ps.setString (1, strTableName);
			int i=0;
			while(i < CYCLE_NUM)
			{
				long lCount = ps.executeUpdate ();
				if (lCount > 0)
				{
					bReturn = false;
					System.out.println("lock success");
					break;
				}
				Thread.currentThread ().sleep (SLEEP_TIME);
				i++;
			}
			ps.close ();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			ps = null;
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close ();
					ps = null;
				}
				if (con != null)
				{
					con.close ();
					con = null;
				}
			}
			catch (SQLException sqe)
			{
				//log4j.error("Exception in Database.isLocked():lock entity failed exception : "+ sqe.toString ()) ;
				throw new IException("Gen_E001");
			}
		}
		return bReturn;
	}

	public static long unlock (String strTableName) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			//log4j.info("Enter Database.unlock()");
			StringBuffer sb = new StringBuffer ();
			sb.append ("update OB_LockTable set nLocked=0 where nlocked=1 and sEntity=? ");
			ps = con.prepareStatement (sb.toString ());
			ps.setString (1, strTableName);
			lReturn = ps.executeUpdate ();
			if (lReturn>0) 
			{
				//log4j.info("Unlock table "+strTableName+" successfully!") ;
			} 
			else
			{ 
				//log4j.info("Failed to unlock table "+strTableName+" !"); 
			} 
			//log4j.info("Go out of  Database.unlock()");
			ps.close();
			ps = null;

		}
		catch (Exception e)
		{
			ps = null;
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close ();
					ps = null;
				}
				if (con != null)
				{
					con.close ();
					con = null;
				}
			}
			catch (SQLException sqe)
			{
				//log4j.error("Exception in Database.unlock:lock entity failed exception : " + sqe.toString ());
				throw new IException("Gen_E001");
			}
		}
		if (lReturn>0) 
		{
			//log4j.info("Unlock table "+strTableName+" successfully!") ;
		} 
		else
		{ 
			//log4j.info("Failed to unlock table "+strTableName+" !"); 
		} 
		return lReturn;
	}

	/**
	 * 得到最大的交易指令流水号
	 * Create Date: 2003-8-13
	 * @param lModuleTypeID  模块ID 
	 * @param conn 数据库连接
	 * @return long 新增加指令流水号
	 * @exception Exception
	 */
	public static long getInstrMaxID(long lModuleTypeID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lMaxID = -1;
		long lUpdateReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			// get max(id)+1 as PK
			
			//modified by zwsun, 2007-06-05 修改了指令序号的生成方式（时间（6位）+模块类型（2位）+基数（4位））			
			sb.append(" SELECT NVL(MAX(id),TO_CHAR(SYSDATE,'yyyymmdd') || LPAD('"+lModuleTypeID+"',2,'0') || '0000')+1 AS id \n");
			//sb.append(" SELECT NVL(MAX(id),TO_CHAR(SYSDATE,'yyyymmdd') || '0000')+1 AS id \n");
			sb.append(" FROM OB_INSTRNO \n");
			//modified by zwsun, 2007-06-05
			sb.append(" WHERE id > TO_CHAR(SYSDATE,'yyyymmdd') || LPAD('"+lModuleTypeID+"',2,'0') || '0000' \n");
			//sb.append(" WHERE id > TO_CHAR(SYSDATE,'yyyymmdd') || '0000' \n");
			sb.append(" AND  lmoduleid = " + lModuleTypeID);
			
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = Long.parseLong(rs.getString("ID"));
			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			//交易指令表的ID递增设置
			sb.setLength(0);
			sb.append("UPDATE OB_INSTRNO SET id  = " + lMaxID + " WHERE lmoduleid = " + lModuleTypeID);
			ps = con.prepareStatement(sb.toString());
			lUpdateReturn = ps.executeUpdate();
			ps.close();
			ps = null;

			if (lUpdateReturn < 1)
			{
				sb.setLength(0);
				//log4j.info("No such Module ,need insert a record to table OB_INSTRNO");
				sb.append("INSERT INTO OB_INSTRNO (id,lmoduleid) VALUES (" + lMaxID + "," + lModuleTypeID + ") ");
				ps = con.prepareStatement(sb.toString());
				lUpdateReturn = ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else
			{
				//log4j.info("Update OB_INSTRNO.id  successfully !");
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			//log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}

		}
		//log4j.info("BizCapital.getInstrMaxID() return lMaxID=:" + lMaxID);
		return lMaxID;
	}
	/**
	 * 得到最大的交易指令流水号
	 * Create Date: 2004-1-15
	 * @param lModuleTypeID  模块ID 
	 * @return String 新增加指令流水号
	 * @exception Exception
	 */
	public static String createInstrCode(long lModuleTypeID) throws Exception
	{
		String strInstr = "";
		boolean bLocked = true;
		try{
			bLocked = isLocked(ENTITY_NAME);
			if (!bLocked)
			{
			   //log.print("Call OBOperation.getInstrMaxID...");
			   strInstr = Long.toString( getInstrMaxID(lModuleTypeID) );
			   unlock(ENTITY_NAME);
			   return strInstr;
			}
			else
			{
			   //log.print("Exception in OBOperation.createInstrCode:数据库表被锁住");
			   throw  new IException("Gen_E001");
			}
		}catch(Exception e ){
			try{
				if (!bLocked) unlock(ENTITY_NAME);
			}catch(Exception e1){
				e1.printStackTrace();
				throw e1;
			}
			throw e;
		}
	}

	/**
	 * 判断是否刷新
	 * Create Date: 2004-1-15
	 * @param strClickCount点击次数  session 
	 * @return String 新增加指令流水号
	 * @exception Exception
	 */
	
	public static boolean  checkClick(String strClickCount, HttpSession session) 
	{
		long lngClickCount = 0;
		Log.print("Common.jsp.checkClick:parameter strClickCount="+strClickCount);
		if (strClickCount != null && !strClickCount.equals("")) 
		{
			lngClickCount = Long.parseLong(strClickCount);			
			String strOldClickCount = (String) session.getAttribute("clickCount");
						   Log.print("===common===18===strOldClickCount===" + strOldClickCount);
				if (strOldClickCount != null && !strOldClickCount.equals("")) 
				{
					if (lngClickCount == (Long.parseLong(strOldClickCount) + 1)) 
					{
						session.setAttribute("clickCount", strClickCount);
						return true;
					} 
				}
		} 
		else 
		{
			Log.print("===checkClick()===" + "传入的参数不是数字型字符串！");
		}
			return false;
	}
	/**
	 * 得到客户对应办事处的系统时间，正确的函数
	 */
	public static Timestamp getClientOpenDate (long lClientID,long lCurrencyID)
	{
		Connection conn = null;
		Timestamp tsDate = null;
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		String strSQL = null; //查询串
		long lOfficeID = 0;
		try
		{
			conn = Database.getConnection();

			strSQL = "select nOfficeID from client where id = ?";
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1,lClientID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lOfficeID = rs.getLong("nOfficeID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			tsDate = getOfficeOpenDate(lOfficeID,lCurrencyID);

		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close ();
					rs = null;
				}
				if (ps != null)
				{
					ps.close ();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		return tsDate;
	}
	

	/**
	 * 得到办事处的系统时间，正确的函数
	 */
	public static Timestamp getOfficeOpenDate (long lOfficeID,long lCurrencyID)
	{
		Connection conn = null;
		Timestamp tsDate = null;
		PreparedStatement ps = null; //
		ResultSet rs = null; //
		String strSQL = null; //查询串
		try
		{
			conn = Database.getConnection();
			/*
			strSQL = "select  to_char(dtOpenDate,'yyyy') as sYear,to_char(dtOpenDate,'mm') as sMonth,to_char(dtOpenDate,'dd') as sDay from officetime where nofficeid=? and ncurrencyid=? ";
			ps = conn.prepareStatement (strSQL);
			ps.setLong(1,lOfficeID);
			ps.setLong(2,lCurrencyID);
			*/
			strSQL = "select to_char(sysdate,'yyyy') as sYear,to_char(sysdate,'mm') as sMonth,to_char(sysdate,'dd') as sDay from dual ";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery ();
			if (rs.next ())
			{
				int nYear = Integer.parseInt (rs.getString ("sYear"));
				int nMonth = Integer.parseInt (rs.getString ("sMonth"));
				int nDay = Integer.parseInt (rs.getString ("sDay"));
				tsDate = DataFormat.getDateTime (nYear, nMonth, nDay, 0, 0, 0);
			}
			rs.close ();
			rs = null;
			ps.close ();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception exp)
		{
			try
			{
				if (rs != null)
				{
					rs.close ();
					rs = null;
				}
				if (ps != null)
				{
					ps.close ();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		return tsDate;
	}
	
//	得到下属单位ID  
	public static String getChildClientID(long Clientid)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String sReturn = "";
		
		try
		{
		   
		        
				con = Database.getConnection();
				String strSQL = "select c.id id from client c where c.id <>"+ Clientid +" and c.sgradecode like (select c.sgradecode from client c where c.id="+Clientid+")||'%'  ";
				//账户状态为正常
				System.out.print("child--"+strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				while (rs != null && rs.next()) 
				{
				    sReturn += rs.getLong("id")+",";
				}
				
				if (sReturn!=null && sReturn.length()>0)
				{
				    sReturn=sReturn.substring(0,sReturn.length()-1);
				}
				
			 
		     
			
		}
		catch (Exception e)
		{
			Log.print(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (rs1 != null)
				{
					rs1.close();
					rs1 = null;
				}
				if (ps1 != null)
				{
					ps1.close();
					ps1 = null;
				}
				if (con != null && !con.isClosed())
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				Log.print(e.toString());
			}
		}
		return sReturn;
	}
	
	public static double GetCurBalance (long acctID,long currency)
	{
	    Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		double dReturn = 0.00;
		
		
		try
		{
		   
		        
				con = Database.getConnection();
				String strSQL = "select a.n_usablebalance currentbalance from bs_bankaccountinfo t,bs_acctcurbalance a  where a.n_accountid=t.n_id and t.n_accountstatus=1 and t.n_rdstatus=1 and t.n_ischeck=1 ";
				//账户状态为正常
				if(acctID>0)
				{
				    strSQL+=" and t.n_id="+acctID;
				}
				if(currency>0)    
				{
				    strSQL+=" and t.n_currencytype="+currency;
				}
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next()) 
				{
				    dReturn = rs.getDouble("currentbalance");
				   
				}	
		}
		catch (Exception e)
		{
			Log.print(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (rs1 != null)
				{
					rs1.close();
					rs1 = null;
				}
				if (ps1 != null)
				{
					ps1.close();
					ps1 = null;
				}
				if (con != null && !con.isClosed())
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				Log.print(e.toString());
			}
		}
		return dReturn;
	}

}