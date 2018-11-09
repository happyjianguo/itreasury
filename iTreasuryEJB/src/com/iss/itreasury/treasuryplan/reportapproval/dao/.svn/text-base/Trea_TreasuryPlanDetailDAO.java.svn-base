/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.treasuryplan.reportapproval.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.*;
import com.iss.itreasury.system.dataentity.*;
import java.util.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.iss.itreasury.treasuryplan.util.*;

import com.iss.itreasury.treasuryplan.reportapproval.dataentity.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Trea_TreasuryPlanDetailDAO extends TreasuryPlanDAO
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
	public Trea_TreasuryPlanDetailDAO()
	{
		super("Trea_TreasuryPlanDetail");
	}
	public Trea_TreasuryPlanDetailDAO(Connection conn){
		super("Trea_TreasuryPlanDetail",conn);
	}		
	/**
	 * @author yuanxue
	 * @param 
	 * @throws Exception 
	 * 
	 */
	public ArrayList findDetailDateByversionID(long versionID) throws Exception{
	    ArrayList list = new ArrayList();
		StringBuffer strSQL = null;
        PreparedStatement ps = null;
	     ResultSet rs = null;
		 Connection conn = null;	
		 String temp= "";	    
		 try{
			 strSQL = new StringBuffer();
			 strSQL.append(" select distinct(transactiondate) from Trea_TreasuryPlanDetail e where 1=1");
			 strSQL.append(" and e.treasuryplanid ="+versionID);
			 strSQL.append(" order by e.transactiondate asc");  //使查询结果按日期排序
			 log4j.info(strSQL.toString());
			 conn = Database.getConnection();			 
			 ps = conn.prepareStatement(strSQL.toString());
			 rs = ps.executeQuery();
			while(rs.next())
			{
               list.add(DataFormat.getDateString(rs.getTimestamp("transactiondate")));
			} 

		 }
		 catch (IException e)
			{
				e.printStackTrace();
				throw e;
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
					if (conn != null)
					{
						conn.close();
						conn = null;
					}
				}
				catch (Exception _ex)
				{
					System.out.println("关闭数据库连接时发生数据库错误！");
				}
			}

		return list;
		
		
	}

	/**
	 * @author yuanxue
	 * @param amount 传入的金额数据，
	 * @param versionId 版本号
	 * @param lineno 行列号
	 * @param startDate  开始日期
	 * @param endDate  结算日期
	 * @return flag 返回标志
	 * @throws Exception 
	 */
	public long setAdjustAmountByCondition(double []amount,long lineID,long versionID,Timestamp startDate,Timestamp endDate) throws Exception{
		 StringBuffer strSQL = null;
         PreparedStatement ps = null;
	     ResultSet rs = null;
		 Connection conn = null;	

		 try{
			 strSQL.append("update Trea_TreasuryPlanDetail set planamount \n");
			 strSQL.append("= ?" );
			 strSQL.append(" where lineID =?" );
			 strSQL.append(" and treasuryplanid =?");
			 strSQL.append(" and transactiondate = ?");
		 	
			
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		 
		return -1; 
	}
	/**
	 * 查询模板树总共有几级 
	 */
	public long getLevelCount(long planVersionID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		long lTemp = -1;
		
		try
		{
			sbSQL = new StringBuffer();
			sbSQL.append(" select max(linelevel) \n");
			sbSQL.append(" from Trea_TreasuryPlanDetail \n");
			sbSQL.append(" where TREASURYPLANID ="+planVersionID+" \n");
			
			log4j.info(sbSQL.toString());
			conn = Database.getConnection();
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lTemp = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception _ex)
			{
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}
		return lTemp;
	}		
	/**
	 * 查询模板树总共有几级 
	 */
	public Collection findPlanDetailByVersionID(long planVersionID) throws Exception
	{
		StringBuffer sbSQL = null;
		Collection c = null;
		Vector v = new Vector();
		
		try
		{
			this.initDAO() ;
			sbSQL = new StringBuffer();
			sbSQL.append(" select * \n");
			sbSQL.append(" from Trea_TreasuryPlanDetail \n");
			sbSQL.append(" where TREASURYPLANID ="+planVersionID+" \n");
			sbSQL.append(" order by lineNO,TransactionDate \n");
			
			prepareStatement( sbSQL.toString() );
			executeQuery();
			c=getDataEntitiesFromResultSet( TreasuryPlanDetailInfo.class );
			if (c!=null)
			{
				ArrayList a = (ArrayList)c;
				int count=a.size() ;
				for (int i=0;i<count;i++)
				{
					v.add(a.get(i));				
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally{
			finalizeDAO();
		}
		return v;
	}	
	
	/**
	 * 更新预测数据（注意：是部分更新）
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public void update(TreasuryPlanDetailInfo info) throws Exception
	{
		//Connection con = null;
		//PreparedStatement ps = null;		
		StringBuffer sbSQL = null;
		try 
		{		
			initDAO();
			//con = Database.getConnection();
		
			sbSQL = new StringBuffer();		
			sbSQL.append(" update Trea_TreasuryPlanDetail \n");
			sbSQL.append(" set PlanAmount=? \n");
			sbSQL.append(" where treasuryPlanID=? and LineID=? and TransactionDate=? \n");
			log4j.info(sbSQL.toString());
			PreparedStatement ps = prepareStatement(sbSQL.toString());
			//ps = con.prepareStatement(sbSQL.toString());
			ps.setDouble(1,info.getPlanAmount());
			//ps.setTimestamp(2,Env.getSystemDate());
			ps.setLong(2,info.getTreasuryPlanID());
			ps.setLong(3,info.getLineID());
			ps.setTimestamp(4,info.getTransactionDate());
			
			//ps.executeUpdate();
			executeUpdate();
			finalizeDAO();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}

	/**
	 * 更新预测数据（注意：是部分更新）
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public void deleteByVersion(long lID) throws Exception
	{
		StringBuffer sbSQL = null;
		try 
		{		
			initDAO();
		
			sbSQL = new StringBuffer();		
			sbSQL.append(" delete from Trea_TreasuryPlanDetail \n");
			sbSQL.append(" where treasuryPlanID=? ");
			log4j.info(sbSQL.toString());
			PreparedStatement ps = prepareStatement(sbSQL.toString());
			//ps = con.prepareStatement(sbSQL.toString());
			ps.setDouble(1,lID);
			
			executeUpdate();
			finalizeDAO();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			finalizeDAO();
		}
	}
	
	public double sumAmountOfSubItems(long officeID,long currencyID,Timestamp forecastDate,long lineID,long versionID) throws Exception{
		double amount = -1;
		//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
        PreparedStatement localPS = null;
        ResultSet         localRS = null;
		try {
			String strForecastDate = transferTimestampToTo_DateString(forecastDate);		
			initDAO();
			String strSQL = "Select sum(PLANAMOUNT)as sums from trea_treasuryplandetail a,trea_treasuryplan b where a.TREASURYPLANID = b.id and b.Officeid = "+ officeID + " and  b.Currencyid = " + currencyID
			+ " and a.TransactionDate = "+strForecastDate+" and a.ParentLineID   = " + lineID +" and PLANAMOUNT != 0.0 and a.TREASURYPLANID = " +versionID;
			localPS = prepareStatement(strSQL);
			localRS = executeQuery();
			if(localRS.next()){
				amount = localRS.getDouble("sums");
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
		    if (localRS != null) {
                localRS.close();
                localRS = null;
            }
            if (localPS != null) {
                localPS.close();
                localPS = null;
            }
			finalizeDAO();
		}
		return amount;		
	}	
	
	
	public long getLevelCountByCondition(long officeID,long currencyID,Timestamp forecastDate,long versionID) throws Exception
	{
		StringBuffer sbSQL = null;
		long lTemp = -1;
		//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
		String strForecastDate = transferTimestampToTo_DateString(forecastDate);		
		
		sbSQL = new StringBuffer();
		sbSQL.append(" select max(a.linelevel) \n");
		sbSQL.append(" from Trea_TreasuryPlanDetail a, Trea_TreasuryPlan b where a.TREASURYPLANID = b.id and b.officeid = "+ officeID);
		sbSQL.append(" and b.currencyid = " + currencyID);
		sbSQL.append(" and a.TransactionDate = "+strForecastDate);
		sbSQL.append(" and a.TREASURYPLANID = "+versionID);
		
		try {
			PreparedStatement localPS = prepareStatement(sbSQL.toString());
			ResultSet localRS = localPS.executeQuery();
			if (localRS.next())
			{
				lTemp = localRS.getLong(1);
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();			
		}
		return lTemp;
	}
	
	
	public Collection getSameLevelLeafDestinationData(long officeID,long currencyID,Timestamp forecastDate,long lineLevel,long versionID) throws Exception{
		StringBuffer sbSQL = null;
		//String strCurrentDate = transferTimestampToTo_DateString(currentDate);
		String strForecastDate = transferTimestampToTo_DateString(forecastDate);				
		//Collection c = getDataEntitiesFromResultSet(null);
		ArrayList list;
		try {
			sbSQL = new StringBuffer();
			sbSQL.append(" select lineID from Trea_TreasuryPlanDetail a, Trea_TreasuryPlan b where a.TREASURYPLANID = b.id and b.officeid = "+ officeID);
			sbSQL.append(" and b.currencyid = " + currencyID);
			sbSQL.append(" and a.TransactionDate = "+strForecastDate);
			sbSQL.append(" and a.lineLevel = "+lineLevel + " and a.isleaf = 0");
			sbSQL.append(" and a.TREASURYPLANID = "+versionID);
			sbSQL.append(" group by lineID ");
			PreparedStatement localPS = prepareStatement(sbSQL.toString());
			ResultSet localRS = executeQuery();
			list = new ArrayList();
			while(localRS.next()){
				long lineid = localRS.getLong("lineid");
				list.add(new Long(lineid));
			}
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally{
			finalizeDAO();			
		}

		return list;
		
	}	
	
	 public void updateAmountByTransactionDateAndLineID(long officeID,long currencyID,Timestamp transactionDate,long lineID,double amount,long versionID) throws Exception{
		try {
			initDAO();
			String strForecastDate = transferTimestampToTo_DateString(transactionDate);
			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append("update Trea_TreasuryPlanDetail set planamount = "+amount+" where  1=1 \n");
			bufferSQL.append(" and TransactionDate = "+strForecastDate+" and lineid = "+lineID);
			bufferSQL.append(" and TREASURYPLANID = "+versionID);


			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			finalizeDAO();			
		}	
	 }	
}
