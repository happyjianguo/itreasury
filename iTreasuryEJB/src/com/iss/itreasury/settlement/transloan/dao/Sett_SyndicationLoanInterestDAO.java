/*
 * Created on 2003-9-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transloan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

//import oracle.net.ano.SupervisorService;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_SyndicationLoanInterestDAO extends SettlementDAO
{
	/**
	 * 日志添加
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * constructor
	 * @param conn
	 */
	public Sett_SyndicationLoanInterestDAO(Connection conn)
	{
		super(conn);
	}
	/**
		* constructor
		* @param conn
		*/
	public Sett_SyndicationLoanInterestDAO()
	{

	}
	/**
	 * 新增银团贷款利息的方法：
	 * 逻辑说明：
	 * 
	 * @param info, SyndicationLoanInterestInfo, 银团贷款利息实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long add(SyndicationLoanInterestInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//利用数据库的序列号取ID;
			/*long id = getMaxID();
			info.setID(id);*/
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO Sett_SyndicationLoanInterest \n");
			buffer.append("(ID,NSYNDICATIONLOANRECEIVEID,NCONTRACTID,NFORMID, \n");
			buffer.append("MINTEREST,MCOMPUNDINTEREST,MFORPEITINTEREST,SBANKNAME, \n");
			buffer.append("MRATE,NISHEAD,nStatusID,nBankID,mAmount)  \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,?,?,?,?,?,?,?) \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getSyndicationLoanReceiveID());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getFormID());
			ps.setDouble(index++, info.getInterest());
			ps.setDouble(index++, info.getCompoundInterest());
			ps.setDouble(index++, info.getForpeitInterest());
			ps.setString(index++, info.getBankName());
			ps.setDouble(index++, info.getRate());		
			ps.setLong(index++, info.getIsHead());	
			ps.setLong(index++, info.getStatusID());
			ps.setLong(index++, info.getBankID());
			ps.setDouble(index++, info.getAmount());

			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 修改银团贷款利息的方法：
	 * 逻辑说明：
	 * 
	 * @param info,  SyndicationLoanInterestInfo, 银团贷款利息实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(SyndicationLoanInterestInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
						
			buffer.append("update Sett_SyndicationLoanInterest set \n");
			buffer.append("NSYNDICATIONLOANRECEIVEID=?,NCONTRACTID=?,NFORMID=?, \n");
			buffer.append("MINTEREST=?,MCOMPUNDINTEREST=?,MFORPEITINTEREST=?,SBANKNAME=?, \n");
			buffer.append("MRATE=?,NISHEAD=?,nStatusID=?,nBankID=?,mAmount=?  \n");
			buffer.append("where ID=? \n");
			
			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;

			ps.setLong(index++, info.getSyndicationLoanReceiveID());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getFormID());
			ps.setDouble(index++, info.getInterest());
			ps.setDouble(index++, info.getCompoundInterest());
			ps.setDouble(index++, info.getForpeitInterest());
			ps.setString(index++, info.getBankName());
			ps.setDouble(index++, info.getRate());		
			ps.setLong(index++, info.getIsHead());	
			ps.setLong(index++, info.getStatusID());
			ps.setLong(index++, info.getBankID());
			ps.setDouble(index++, info.getAmount());
			ps.setLong(index++, info.getID());
			

			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 根据标识查询银团贷款利息明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lSyndicationLoanReceiveID long , 银团贷款收回交易的ID
	 * @return 
	 * @throws Exception
	 */
	public Collection findBySyndicationLoanReceiveID(long lSyndicationLoanReceiveID) throws Exception
	{
		ArrayList rtnList = new ArrayList();
		SyndicationLoanInterestInfo info = new SyndicationLoanInterestInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select * from Sett_SyndicationLoanInterest where nSyndicationLoanReceiveID=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lSyndicationLoanReceiveID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = getRs(info, rs);
				rtnList.add(info);
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return rtnList;
	}
	
	/**
	 * 根据标识查询银团贷款财务公司明细的方法：
	 * 逻辑说明：
	 * 
	 * @param lSyndicationLoanReceiveID long , 银团贷款收回交易的ID
	 * @return 
	 * @throws Exception
	 */
	public SyndicationLoanInterestInfo findAmount(long lSyndicationLoanReceiveID) throws Exception
	{		
		SyndicationLoanInterestInfo info = new SyndicationLoanInterestInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select * from Sett_SyndicationLoanInterest where nSyndicationLoanReceiveID=? and nIshead=1 ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lSyndicationLoanReceiveID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = getRs(info, rs);				
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	/**
	 * 修改银团贷款利息交易状态的方法：
	 * 逻辑说明：
	 * @param lSyndicationLoanReceiveID, long, 贷款收回交易标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lSyndicationLoanReceiveID, long lStatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("update Sett_SyndicationLoanInterest set nStatusID=? where nSyndicationLoanReceiveID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lSyndicationLoanReceiveID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lSyndicationLoanReceiveID;
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	

	/**
	 * 物理删除
	 * @param lSyndicationLoanReceiveID
	 * @return
	 * @throws Exception
	 */
	public long deletePhysicsSyndicationLoan(long lSyndicationLoanReceiveID) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete  from  Sett_SyndicationLoanInterest where nSyndicationLoanReceiveID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lSyndicationLoanReceiveID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lSyndicationLoanReceiveID;
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
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 设置银团贷款利息结果集： 
	 * 逻辑说明：
	 * @throws Exception
	 */
	private SyndicationLoanInterestInfo getRs(SyndicationLoanInterestInfo info, ResultSet rs) throws Exception
	{
		info = new SyndicationLoanInterestInfo();
		try
		{
			info.setID(rs.getLong("ID"));
			info.setSyndicationLoanReceiveID(rs.getLong("nSyndicationLoanReceiveID"));
			info.setContractID(rs.getLong("nContractID"));
			info.setFormID(rs.getLong("nFormID"));			
			info.setInterest(rs.getDouble("mInterest"));
			info.setCompoundInterest(rs.getDouble("mCompundInterest"));
			info.setForpeitInterest(rs.getDouble("mForpeitInterest"));			
			info.setBankName(rs.getString("sBankName"));			
			info.setRate(rs.getDouble("mRate"));
			info.setIsHead(rs.getLong("nIsHead"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setAmount(rs.getDouble("mAmount"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return info;

	}
	/**
	 * 得到状态条件
	 * @param info
	 * @return
	 */
	private String getQueryString(QueryByStatusConditionInfo info)
	{
		String query;
		query = "nStatusID=";
		for (int i = 0; i < info.getStatus().length; i++)
		{
			if (i < info.getStatus().length - 1)
			{

				query = query + info.getStatus()[i] + " or nStatusID=";
			}
			else
			{
				query = query + info.getStatus()[i];
			}
		}
		return query;
	}

	/**
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getMaxID() throws Exception
	{
		long lMaxID = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select nvl(max(id)+1,1) from Sett_SyndicationLoanInterest ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		return lMaxID;
	}
	

	
}
