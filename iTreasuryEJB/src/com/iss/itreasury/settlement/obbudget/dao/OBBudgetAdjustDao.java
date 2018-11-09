/*
 * Created on 2006-9-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.obbudget.dao;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.obbudget.dataentity.OBBudgetAdjustInfo;
import com.iss.itreasury.settlement.obbudget.dataentity.OBBudgetInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetAdjustDao  extends SettlementDAO 
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
						 "and t.status not in("+Constant.OBBudgetStatus.DELETE+","+Constant.OBBudgetStatus.REFUSE+")");
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
	
	/**
	 * 修改状态
	 * @param ID
	 * @param lStatus
	 * @param lUserID
	 * @param strAction
	 * @return
	 * @throws Exception
	 */
	public long updateStatus(long ID, long lStatus, long lUserID, String strAction,String reason) throws Exception
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
				sb.append("  ,refusereason='"+reason+"'");
			}
			if(strAction.equals("transfer"))
			{
			    sb.append("");
			}
			if(strAction.equals("refuse"))
			{
			    sb.append(" ,refusereason='"+reason+"'");
			}
			sb.append(" where id = ?");
			System.out.println("参数lStatus:"+lStatus);
			System.out.println("参数lUserID:"+lUserID);
			System.out.println("参数ID:"+ID);
			System.out.println("updateStatus sql==="+sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lStatus);
			if(!strAction.equals("authing") && !strAction.equals("transfer")&& !strAction.equals("refuse"))
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
	
	/**
	 * 查找预算
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public Collection findAdjustByCon() throws Exception
	{
	    	Vector vResult = new Vector();
	        Connection con = null;
	        OBBudgetInfo info = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			long lReturn = -1;
			int index = 1;
			 
			try
			{
			    con = Database.getConnection();
				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append("select t.*,a.startdate startdate,a.enddate  enddate ,a.sname sname,a.amount budgetamount,a.id budgetid " +
						"from ob_budgetadjust t,ob_budget a where t.budgetid=a.id and t.status="+Constant.OBBudgetStatus.CHECK+" order by t.clientid ,a.id,t.adjustdate desc");
				System.out.println("isStatus sql======"+sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				while (rs.next())
				{
				    info = new OBBudgetInfo();
				   info.setId(rs.getLong("id"));
				   info.setAccountID(rs.getLong("ACCOUNTID"));
				   info.setAmount(rs.getDouble("budgetamount"));
				   info.setClientID(rs.getLong("CLIENTID"));
				   info.setSname(rs.getString("SNAME"));
				   info.setStartdate(rs.getTimestamp("STARTDATE"));
				   info.setEnddate(rs.getTimestamp("ENDDATE"));
				   info.setStatus(rs.getLong("STATUS"));
				   info.setInputdate(rs.getTimestamp("INPUTDATE"));
				   info.setInputuser(rs.getLong("INPUTUSER"));
				   info.setAdjustamount(rs.getDouble("amount"));
				   info.setAdjustdate(rs.getTimestamp("adjustdate"));
				   vResult.add(info);
				   
				}
//				关闭数据库资源
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
 
        return vResult;
	}
	
	/**
	 * 查找预算
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public double findBudgetAdjustAmount(OBBudgetAdjustInfo cinfo) throws Exception
	{
	    	Vector vResult = new Vector();
	        double dreturn = 0.00;
	        Connection con = null;
	        OBBudgetAdjustInfo info = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			long lReturn = -1;
			int index = 1;
			 
			try
			{
			    con = Database.getConnection();
				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append("select t.amount" +
						" from ob_budgetadjust t where t.status="+Constant.OBBudgetStatus.AUTHED+" and  t.accountid="+cinfo.getAccountID()+"" +
								" and to_char(t.adjustdate,'yyyy-mm-dd')= '"+cinfo.getAdjustdate().toString().substring(0,10)+"'" );
				System.out.println("isStatus sql======"+sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				while (rs.next())
				{
				    
				    
				    dreturn = rs.getDouble("amount");
				    
				   
				}
//				关闭数据库资源
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
 
        return dreturn;
	}
}
