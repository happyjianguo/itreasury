/*
 * Created on 2003-10-28
 *
 * InterestQueryResultInfo.java
 */
package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;

/**
 * @author xiren Li
 *
 * 利息费用结算结息处理数据访问对象，主要实现的功能包括：
 * 1.查询结息记录。
 * 2.计提利息。
 * 3.冲销计提利息。
 * 4.结算利息。
 * 
 * 注意事项:为了使该数据访问对象对容器的事务和非容器的事务都提供支持，
 *          不在每个方法内部取得数据库的连接，而是由外界传入，由调用
 *          该对象方法的外界对象来维护事务。鉴于此，该类不必继承
 *          于SettlementDAO.
 */

public class Sett_LoanNoticeDAO extends SettlementDAO
{

	/**Logger instance*/
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 新增结息表数据的方法：
	 * 逻辑说明：
	 * 
	 * @param info, InterestSettmentInfo, 结息交易实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long add(Connection con, LoanNoticeInfo info) throws Exception
	{		
		long lReturn = -1;
		Connection conInternal = null;
		if(con==null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			//利用数据库的取ID;			
			//long id= getMaxID(con);
			long id = getMaxID(conInternal);
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO sett_LoanNotice \n");
			buffer.append("(ID,nOfficeID,nCurrencyID, \n");
			buffer.append("nAccountID, \n");
			buffer.append("nSubAccountID,sContractCode,sAssureCodeCode, \n");
			buffer.append("sPayFormCode,sBorrowClientName,sAssureClientName, \n");
			buffer.append("nFornType,sFormYear,sFormCode, \n");
			buffer.append("nFormNo,dtExecute,dtInterest, \n");
			buffer.append("mOpenAmount,dtStart,dtEnd, \n");
			buffer.append("nIntervalNum,mContractInterestRate,mBalance, \n");
			buffer.append("mExecuteInterestRate,mNewExecuteInterestRate,dtInterestRateValid, \n");
			buffer.append("mInterest,mSuretyFee,mCompoundInterest, \n");
			buffer.append("mCommissionRate,mCommission,mOverCommissionRate,mOverDueInterest, \n");
			buffer.append("mAllInterest,mTotal,nStatusID,dtInterestStart) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?) \n");

			ps = conInternal.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setLong(index++, info.getAccountID());
			ps.setLong(index++, info.getSubAccountID());
			ps.setString(index++, info.getContractNo());
			ps.setString(index++, info.getAssureContractNo());
			ps.setString(index++, info.getPayFormNo());
			ps.setString(index++, info.getBorrowClientName());
			ps.setString(index++, info.getAssureClientName());
			ps.setLong(index++, info.getFormTypeID());
			ps.setString(index++, info.getFormYear());
			ps.setString(index++, info.getFormNo());
			ps.setLong(index++, info.getFormNum());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setTimestamp(index++, info.getClearInterestDate());
			ps.setDouble(index++, info.getLoanAmount());
			ps.setTimestamp(index++, info.getLoanStartDate());
			ps.setTimestamp(index++, info.getLoanEndDate());
			ps.setLong(index++, info.getLoanTerm());
			ps.setDouble(index++, info.getOriginalContractRate());
			ps.setDouble(index++, info.getLoanBalance());
			ps.setDouble(index++, info.getExecuteRate());
			ps.setDouble(index++, info.getNewExecuteRate());
			ps.setTimestamp(index++, info.getExecuteRateValidDate());
			ps.setDouble(index++, info.getInterest());
			ps.setDouble(index++, info.getSuretyFee());
			ps.setDouble(index++, info.getCompoundInterest());
			ps.setDouble(index++, info.getCommissionRate());
			ps.setDouble(index++, info.getCommission());
			ps.setDouble(index++, info.getOverDueInterestRate());
			ps.setDouble(index++, info.getOverDueInterest());
			ps.setDouble(index++, info.getAllInterest());
			ps.setDouble(index++, info.getTotalInterest());
			ps.setLong(index++, info.getStatusID());
			ps.setTimestamp(index++, info.getInterestStartDate());

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
				if(con==null)
				{				
					cleanup(conInternal);
				}
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
	 * 修改状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(Connection con, long lID, long lStatusID) throws Exception
	{
		long lReturn = -1;
		Connection conInternal = null;
		if(con==null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_LoanNotice set nStatusID=? where ID=?");
			ps = conInternal.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);		
		
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
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
				if(con==null)
				{	
					//conInternal.commit();			
					cleanup(conInternal);
				}
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
	 * 修改结息表数据的方法：
	 * 逻辑说明：
	 * 
	 * @param info, InterestSettmentInfo, 结息交易实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long update(Connection con, LoanNoticeInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conInternal = null;
		if(con==null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_LoanNotice set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?, \n");
			buffer.append("nAccountID=?, \n");
			buffer.append("nSubAccountID=?,sContractCode=?,sAssureCodeCode=?, \n");
			buffer.append("sPayFormCode=?,sBorrowClientName=?,sAssureClientName=?, \n");
			buffer.append("nFornType=?,sFormYear=?,sFormCode=?=?, \n");
			buffer.append("nFormNo=?,dtExecute=?,dtInterest=?, \n");
			buffer.append("mOpenAmount=?,dtStart=?,dtEnd=?, \n");
			buffer.append("nIntervalNum=?,mContractInterestRate=?,mBalance=?, \n");
			buffer.append("mExecuteInterestRate=?,mNewExecuteInterestRate=?,dtInterestRateValid=?, \n");
			buffer.append("mInterest=?,mSuretyFee=?,mCompoundInterest=?, \n");
			buffer.append("mCommissionRate=?,mCommission=?,mOverCommissionRate=?,mOverDueInterest=?, \n");
			buffer.append("mAllInterest=?,mTotal=?,nStatusID=?,dtInterestStart=? \n");
			buffer.append("where ID=? \n");

			ps = conInternal.prepareStatement(buffer.toString());
			log.info(buffer.toString());

			int index = 1;

			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setLong(index++, info.getAccountID());
			ps.setLong(index++, info.getSubAccountID());
			ps.setString(index++, info.getContractNo());
			ps.setString(index++, info.getAssureContractNo());
			ps.setString(index++, info.getPayFormNo());
			ps.setString(index++, info.getBorrowClientName());
			ps.setString(index++, info.getAssureClientName());
			ps.setLong(index++, info.getFormTypeID());
			ps.setString(index++, info.getFormYear());
			ps.setString(index++, info.getFormNo());
			ps.setLong(index++, info.getFormNum());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setTimestamp(index++, info.getClearInterestDate());
			ps.setDouble(index++, info.getLoanAmount());
			ps.setTimestamp(index++, info.getLoanStartDate());
			ps.setTimestamp(index++, info.getLoanEndDate());
			ps.setLong(index++, info.getLoanTerm());
			ps.setDouble(index++, info.getOriginalContractRate());
			ps.setDouble(index++, info.getLoanBalance());
			ps.setDouble(index++, info.getExecuteRate());
			ps.setDouble(index++, info.getNewExecuteRate());
			ps.setTimestamp(index++, info.getExecuteRateValidDate());
			ps.setDouble(index++, info.getInterest());
			ps.setDouble(index++, info.getSuretyFee());
			ps.setDouble(index++, info.getCompoundInterest());
			ps.setDouble(index++, info.getCommissionRate());
			ps.setDouble(index++, info.getCommission());
			ps.setDouble(index++, info.getOverDueInterestRate());
			ps.setDouble(index++, info.getOverDueInterest());
			ps.setDouble(index++, info.getAllInterest());
			ps.setDouble(index++, info.getTotalInterest());
			ps.setLong(index++, info.getStatusID());
			ps.setLong(index++, info.getID());
			ps.setTimestamp(index++, info.getInterestStartDate());

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
				if(con==null)
				{				
					cleanup(conInternal);
				}
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
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private long getMaxID(Connection con) throws Exception
	{
		long lMaxID = -1;
		Connection conInternal = null;
		if(con==null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select nvl(max(id)+1,1) from sett_LoanNotice ";
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
			if(con==null)
			{				
				cleanup(conInternal);
			}
		}
		return lMaxID;
	}
	/**
	 * 得到催收次数
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getMaxFormNum(Connection con,String strPayFormNo) throws Exception
	{
		long lMaxID = -1;
		Connection conInternal = null;
		if(con==null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String strSQL = "select nvl(max(nFormNo)+1,1) from sett_LoanNotice where sPayFormCode= ?  ";
			ps = conInternal.prepareStatement(strSQL);
			ps.setString(1,strPayFormNo);
			rs = ps.executeQuery();			
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
			if(lMaxID<=0)
			{
				lMaxID=1;
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
			if(con==null)
			{				
				cleanup(conInternal);
			}
		}
		
		return lMaxID;
	}
	/**
	 * 得到通知书号
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public String getMaxFormNo(Connection con,String strFormYear,long nFormTypeID) throws Exception
	{
		String strReturn ="";
		Connection conInternal = null;
		if(con==null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{			
			StringBuffer buffer = new StringBuffer();			
			buffer.append("select max(sFormCode) as sequence  \n");
			buffer.append("from  SETT_LOANNOTICE where sFormYear=? and nFornType=?  and nstatusid<>"+SETTConstant.TransactionStatus.DELETED+" \n");			

			ps = conInternal.prepareStatement(buffer.toString());
			int index = 1;
			ps.setString(index++, strFormYear);
			ps.setLong(index++, nFormTypeID);

			rs = ps.executeQuery();
			if (rs.next())
			{
				strReturn = rs.getString("sequence");
				if(strReturn!=null && !strReturn.equals(""))
				{				
					int sequence = Integer.valueOf(strReturn).intValue();
					sequence++;
					strReturn = DataFormat.formatInt(sequence, 4);
				}
				else
				{
					strReturn = "0001";				
				}
			}
			else
			{
				strReturn = "0001";
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
			if(con==null)
			{				
				cleanup(conInternal);
			}
		}
		return strReturn;
	}
	/*private long getID(Connection conn) throws Exception
	{
		long id = -1;		
		StringBuffer sb = new StringBuffer();
		sb.append("select SEQ_TRANSINTERESTSETTLEMENT.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		rs.close();
		ps.close();
		conn.close();
		return id;
	}*/

}
