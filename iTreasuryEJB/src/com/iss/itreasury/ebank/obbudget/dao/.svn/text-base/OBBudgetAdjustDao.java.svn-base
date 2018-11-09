/*
 * Created on 2006-8-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obbudget.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetAdjustInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.Database;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetAdjustDao extends ITreasuryDAO  
{
	public OBBudgetAdjustDao()
	{
		super("OB_BUDGETADJUST");
		super.setUseMaxID();
	}

	public OBBudgetAdjustDao(String tableName)
	{
		super(tableName);
		super.setUseMaxID();
	}

	public OBBudgetAdjustDao(String tableName, Connection conn)
	{
		super(tableName, conn);
		super.setUseMaxID();
	}
	
	/**
	 * 判断 相同账户、相同日期 状态不为“已拒绝，已删除”的纪录    是否存在
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public boolean isRepeat (OBBudgetAdjustInfo info)throws Exception
	{
	    Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		try
		{
		    con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from ob_budgetadjust t where t.accountid=? and t.adjustdate=? " +
						 "and t.status not in("+OBConstant.OBBudgetStatus.DELETE+")");
			System.out.println("参数:"+info.getAccountID());
			System.out.println("参数:"+info.getAdjustdate());
			System.out.println("isRepeat sql==="+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,info.getAccountID());
			ps.setTimestamp(2,info.getAdjustdate());
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
//			关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				e.printStackTrace();
				throw new Exception("Gen_E001");
			}
		}
		
        return bReturn;   
	}
	
	public boolean isStatus (long ID,long[] lStatus) throws Exception
	{
		String strStatusSql = "(";
		for(int i=0;i<lStatus.length;i++)
		{
			strStatusSql += String.valueOf(lStatus[i])+",";
		}
		strStatusSql = strStatusSql.substring(0,strStatusSql.length()-1)+")";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		try
		{
		    con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from ob_budgetadjust t where t.id=? and t.status in "+strStatusSql);
			System.out.println("参数:"+ID);
			System.out.println("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,ID);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
//			关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return bReturn;
	}
	
	/**判断记录是否是该人录入,是否是该人复核
	 * 
	 * @return
	 */
	public boolean isComfirmer(long ID,long User,String action)throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bReturn = false;
		String subSql = "";
		if(action.equalsIgnoreCase("canclecheck"))
		{
			subSql = "checkuser";
		}
		else 
		{
			subSql = "inputuser";
		}
		try
		{
		    con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.* from ob_budgetadjust t where t.id=? and t."+subSql+"=?");
			System.out.println("参数ID:"+ID);
			System.out.println("参数User:"+User);
			System.out.println("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1,ID);
			ps.setLong(2,User);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    bReturn = true;
			}
//			关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return bReturn;
		
	}
	
	/**
	 * update
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	public void update (OBBudgetAdjustInfo info)throws ITreasuryDAOException
	{
		try
		{ 
			super.initDAO();
			super.update(info);
			super.finalizeDAO();
		}catch(Exception ie)
		{
			ie.printStackTrace();
			super.finalizeDAO();
		}
		finally
		{
			super.finalizeDAO();
		}
		
	}
	
	/**
	 * 修改状态
	 * @param ID
	 * @param lStatus
	 * @param lUserID
	 * @param strAction
	 * @return
	 * @throws Exception
	 */
	public long updateStatus(long ID, long lStatus, long lUserID, String strAction) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE ob_budgetadjust SET status = ? ");
			if (strAction.equals("check"))
			{
				 sb.append(" , CHECKUSER=?,CHECKDATE=sysdate");
			}
			if (strAction.equals("cancelcheck"))
			{
				 sb.append(" , CHECKUSER=?,CHECKDATE=sysdate");
			}
			if (strAction.equals("delete"))
			{
				sb.append(" , MODIFYUSER=?, MODIFYDATE=sysdate ");
			}
			if (strAction.equals("authing"))
			{
				sb.append("");
			}
			if(strAction.equals("transfer"))
			{
			    sb.append("");
			}
			sb.append(" where id = ?");
			System.out.println("参数lStatus:"+lStatus);
			System.out.println("参数lUserID:"+lUserID);
			System.out.println("参数ID:"+ID);
			System.out.println("updateStatus sql==="+sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lStatus);
			if(!strAction.equals("authing") && !strAction.equals("transfer"))
			{
			    ps.setLong(nIndex++, lUserID);
			}
			ps.setLong(nIndex++, ID);
			
			lResult = ps.executeUpdate();
			 
			// 关闭数据库资源
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
 
		catch (Exception e)
		{
		 
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
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
				 
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return lResult=ID;
		
	}
	
	public long matching (OBBudgetAdjustInfo info,long userid)throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		int index = 1;
		 
		try
		{
		    con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("select t.id from ob_budgetadjust t where t.budgetid=? and t.adjustdate=?" +
						 " and t.AMOUNT=? and t.STATUS=? and t.INPUTUSER != ?");
			System.out.println("isStatus sql======"+sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(index++,info.getBudgetID());
			ps.setTimestamp(index++,info.getAdjustdate());
			ps.setDouble(index++,info.getAmount());
			ps.setLong(index++,info.getStatus());
			ps.setLong(index++,userid);
			rs = ps.executeQuery();
			if (rs.next())
			{
			    lReturn = rs.getLong("id");
			}
//			关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return lReturn;	
	}
	 //复核查找
	public Collection checkquery(long clientid,long userid) throws Exception
	{
		Collection result=null;
		try{
			initDAO();
			String sbSQL="select * from ob_budgetadjust where 1=1";
			sbSQL+=" and clientid=?";
			sbSQL+=" and ( (checkuser= ? and status=2)   or (inputuser!=? and status=1)   )";
			
			prepareStatement(sbSQL);
			transPS.setLong(1,clientid);
			transPS.setLong(2,userid);
			transPS.setLong(3,userid);
			System.out.println("**************复核查找SQL:"+sbSQL);
			executeQuery();
			result=getDataEntitiesFromResultSet(OBBudgetAdjustInfo.class);
		}
		catch (Exception e)
		{
			
		    System.out.print(e.toString());
			throw new RemoteException("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				finalize();
			}
			catch (Throwable e)
			{
			    System.out.print(e.toString());
				throw new RemoteException("remote exception : " + e.toString());
			}
		}

		return result;
	}
	
}
