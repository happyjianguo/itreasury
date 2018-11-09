package com.iss.itreasury.settlement.transloan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transloan.dataentity.ContractFormInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
/**
 * Title:        		iTreasury
 * Description:         该DAO为结算模块中使用的对于贷款模块的数据查询提供方法
*           Copyright:                     Copyright (c) 2003 Company:
* iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-11-17
 */
public class Loan_DAO extends SettlementDAO
{
	public final static int LOAN_TYPE_COMMON = 0;
	public final static int LOAN_TYPE_DISCOUNT = 1;
	public Loan_DAO()
	{
	}
	public Loan_DAO(Connection conn)
	{
		super(conn);
	}
	/**根据放款通知单号获取账户模块需要的合同信息*/
	public ContractFormInfo getContractInfoByLoanNoteID(long id) throws SQLException
	{
		long contractID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ContractFormInfo resInfo = new ContractFormInfo();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select cf.nsubtypeid,cf.NCONSIGNCLIENTID, cf.NINTERVALNUM,cf.DTSTARTDATE,cf.id, cf.NCHARGERATETYPEID,cf.NLOANID from loan_payform pf, loan_contractform cf");
			sbSQL.append(" where pf.id = " + id + " and cf.id = pf.NCONTRACTID ");
			log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				long typeID = rs.getLong(1);
				long consignClientID = rs.getLong(2);
				long intervalNum = rs.getLong(3);
				Timestamp startDate = rs.getTimestamp(4);
				long contractId = rs.getLong(5);
				long chargeRateTypeId = rs.getLong(6);
				long lLoanID = rs.getLong(7);
				resInfo.setLoanTypeID(typeID);
				resInfo.setClientID(consignClientID);
				resInfo.setIntervalNum(intervalNum);
				resInfo.setLoanStart(startDate);
				resInfo.setContractID(contractId);
				resInfo.setChargeRateType(chargeRateTypeId);
				resInfo.setLoanID(lLoanID);
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return resInfo;
	}
	/**根据贴现凭证号获取账户模块需要的合同信息*/
	public ContractFormInfo getContractInfoByCredenceID(long id) throws SQLException
	{
		long contractID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ContractFormInfo resInfo = new ContractFormInfo();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(
				" select cf.nsubtypeid,cf.NBORROWCLIENTID, cf.NINTERVALNUM,cf.DTSTARTDATE,cf.id, cf.NCHARGERATETYPEID,cf.NDISCOUNTTYPEID from LOAN_DISCOUNTCREDENCE pf, loan_contractform cf");
			sbSQL.append(" where pf.id = " + id + " and cf.id = pf.NCONTRACTID ");
			log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				long typeID = rs.getLong(1);
				long clientID = rs.getLong(2);
				long intervalNum = rs.getLong(3);
				Timestamp startDate = rs.getTimestamp(4);
				long contractId = rs.getLong(5);
				long chargeRateTypeId = rs.getLong(6);
				long discountTypeID = rs.getLong(7);
				resInfo.setLoanTypeID(typeID);
				resInfo.setClientID(clientID);
				resInfo.setIntervalNum(intervalNum);
				resInfo.setLoanStart(startDate);
				resInfo.setContractID(contractId);
				resInfo.setChargeRateType(chargeRateTypeId);
				resInfo.setDiscountTypeID(discountTypeID);
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return resInfo;
	}
	/**根据贴现凭证号获取账户模块需要的贴现凭证类型*/
	public long getAcceptPoTypeIDByDiscountCredenceID(long discountCredenceID) throws SQLException
	{
		long acceptPoTypeID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select NACCEPTPOTYPEID from LOAN_DISCOUNTcontractBILL WHERE NDISCOUNTCREDENCEID = " + discountCredenceID);
			log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				acceptPoTypeID = rs.getLong(1);
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return acceptPoTypeID;
	}
	/**仅仅取了放款通知单的部分信息，请根据需要补齐!!!!!!*/
	public LoanPayFormDetailInfo getLoanPayFormDetailInfoByID(long loanPayFromID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		LoanPayFormDetailInfo resInfo = new LoanPayFormDetailInfo();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from loan_payform ");
			sbSQL.append(" where id = " + loanPayFromID);
			log.debug(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				double commissionRate = rs.getDouble("MCOMMISSIONRATE");
				Timestamp dtStart = rs.getTimestamp("DTSTART");
				Timestamp dtEnd = rs.getTimestamp("DTEND");
				Timestamp dtOutDate = rs.getTimestamp("DTOUTDATE");
				resInfo.setCommissionRate(commissionRate);
				resInfo.setStart(dtStart);
				resInfo.setEnd(dtEnd);
				resInfo.setOutDate(dtOutDate);
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return resInfo;
	}
	public long getIntervalNumOfFromContractInfo(long contractID) throws SQLException
	{
		long interval = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//String sql = "select round((DTSTARTDATE-DTENDDATE)/31) interval,NINTERVALNUM from LOAN_contractform where id = " + contractID;
		//modify by zwxiao 2010-06-17 由于贴现发放按照贷款期限和汇票种类来设置科目的时候，而贴现合同的NINTERVALNUM为空
		//这样的话计算出的interval的值为负数，导致于后面查询科目的时候有问题，所以调换一个开始日期和结束日期的位置
		String sql = "select round(MONTHS_BETWEEN(DTENDDATE,DTSTARTDATE)) interval,NINTERVALNUM from LOAN_contractform where id = " + contractID;
		try
		{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				interval = rs.getLong("NINTERVALNUM");
				System.out.println(interval);
				if (interval == 0)
					interval = rs.getLong("interval");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return interval;
	}
	/**
	 * 更新冲销/未冲销状态
	 * @param id
	 * @param isAbatement
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public boolean changeIsabatementForBill(long id, long isAbatement) throws ITreasuryDAOException
	{
		Vector bill = new Vector();
		Vector credence = new Vector();
		bill.add(Long.toString(id));
		return this.changeIsabatementForBill(bill, credence, isAbatement);
	}
	/**
	* 更新冲销/未冲销状态
	* @param billId是需要修改的票据记录id
	* @param credenceId 是需要修改凭证的记录id
	* @param isAbatement SETTConstant.DiscountBillAbatementStatus.NO/YES
	* @return boolean 
	*/
	public boolean changeIsabatementForBill(Vector billId, Vector credenceId, long isAbatement) throws ITreasuryDAOException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer strBillWhere = new StringBuffer();
		StringBuffer strCredenceWhere = new StringBuffer();
		conn = this.getConnection();
		try
		{
			//conn.setAutoCommit(false);
			if (billId == null || billId.size() == 0)
			{
			}
			else
			{
				strBillWhere.append(" ( ");
				for (int i = 0; i < billId.size(); i++)
				{
					if (i == billId.size() - 1)
					{
						strBillWhere.append((String) billId.get(i));
					}
					else
					{
						strBillWhere.append((String) billId.get(i) + ",");
					}
				}
				strBillWhere.append(" ) ");
				pstmt = conn.prepareStatement(" update loan_discountcontractbill set NISABATEMENT=? where id in " + strBillWhere.toString());
				pstmt.setLong(1, isAbatement);
				pstmt.executeUpdate();
			}
			if (credenceId == null || credenceId.size() == 0)
			{
			}
			else
			{
				strCredenceWhere.append(" ( ");
				for (int i = 0; i < credenceId.size(); i++)
				{
					if (i == credenceId.size() - 1)
					{
						strCredenceWhere.append((String) credenceId.get(i));
					}
					else
					{
						strCredenceWhere.append((String) credenceId.get(i) + ",");
					}
				}
				strCredenceWhere.append(" ) ");
				pstmt = conn.prepareStatement(" update loan_discountcontractbill NISABATEMENT=? where ndiscountcredenceid in " + strCredenceWhere.toString());
				pstmt.setLong(1, isAbatement);
				pstmt.executeUpdate();
			}
			//conn.commit();
		}
		catch (SQLException e)
		{
			throw new ITreasuryDAOException("冲销/未冲销状态更新失败!!", e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
				{
				    pstmt.close();
				    pstmt = null;
				}
				if (conn != null)
				{
				    conn.close();
				    conn = null;
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		//}
		return true;
	}
	/**
	 * Method updateDiscountBillSellStatusID.
	 * 更新贴现票据是否可以卖出状态
	 * @param lDiscoutBillID
	 * @param lSellStatusID
	 * @return long
	 * @throws ITreasuryDAOException
	 */
	public long updateDiscountBillSellStatusID(long lDiscoutBillID, long lSellStatusID) throws ITreasuryDAOException
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			sql = " update LOAN_DISCOUNTCONTRACTBILL set NSELLSTATUSID=" + lSellStatusID + " where id=" + lDiscoutBillID;
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			lReturn= ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ITreasuryDAOException("更改贴现票据状态失败!", e);
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
				if (conn != null)
				{
				    conn.close();
				    conn = null;
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return lReturn;
	}
	/**
	 * Method getContractInfoByContractID.
	 * 根据合同id获取关于合同的部分信息（如贷款类型，转贴现类型:买断或者回购，转贴现买入卖出类型）
	 * @param lContractID
	 * @return ContractFormInfo
	 */
	public ContractFormInfo getContractInfoByContractID(long lContractID) throws ITreasuryDAOException
	{
		ContractFormInfo returnInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try
		{
			sql = "select * from Loan_ContractForm where id=" + lContractID;
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				returnInfo = new ContractFormInfo();
				returnInfo.setLoanTypeID(rs.getLong("nsubtypeid"));
				returnInfo.setDiscountTypeID(rs.getLong("NDISCOUNTTYPEID"));
				returnInfo.setReDiscountInOrOut(rs.getLong("NINOROUT"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ITreasuryDAOException("查询合同信息失败!", e);
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
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return returnInfo;
	}
	/**
	 * Method IsReDiscountBuyOutContract.
	 * 是否是转贴现卖出买断型合同
	 * @param lContractID
	 * @return boolean
	 * @throws ITreasuryDAOException
	 */
	public boolean IsReDiscountBuyBreakOutContract(long lContractID) throws ITreasuryDAOException
	{
		boolean bReturn = false;
		ContractFormInfo contractFormInfo = getContractInfoByContractID(lContractID);
		if (contractFormInfo != null)
		{
			if (contractFormInfo.getLoanTypeID() == LOANConstant.LoanType.ZTX
				&& contractFormInfo.getDiscountTypeID() == LOANConstant.TransDiscountType.BUYBREAK
				&& contractFormInfo.getReDiscountInOrOut() == LOANConstant.TransDiscountInOrOut.OUT)
			{
				bReturn = true;
			}
		}
		return bReturn;
	}
	/**
	 * Method IsReDiscountBuyBreakInContract.
	 * 是否是转贴现买入买断型合同
	 * @param lContractID
	 * @return boolean
	 * @throws ITreasuryDAOException
	 */
	public boolean IsReDiscountBuyBreakInContract(long lContractID) throws ITreasuryDAOException
	{
		boolean bReturn = false;
		ContractFormInfo contractFormInfo = getContractInfoByContractID(lContractID);
		if (contractFormInfo != null)
		{
			if (contractFormInfo.getLoanTypeID() == LOANConstant.LoanType.ZTX
				&& contractFormInfo.getDiscountTypeID() == LOANConstant.TransDiscountType.BUYBREAK
				&& contractFormInfo.getReDiscountInOrOut() == LOANConstant.TransDiscountInOrOut.IN)
			{
				bReturn = true;
			}
		}
		return bReturn;
	}
	/**
	 * Method getDiscountBillAbatementStatusIDByBillID.
	 * 根据贴现票据id获取贴现票据的冲销状态
	 * @param lBillID
	 * @return long
	 * @throws ITreasuryDAOException
	 */
	public long getDiscountBillAbatementStatusIDByBillID(long lBillID) throws ITreasuryDAOException
	{
		long lStatusID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try
		{
			sql = "select nvl(NISABATEMENT,0) abatementStatus from LOAN_DISCOUNTCONTRACTBILL where id=" + lBillID;
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lStatusID = rs.getLong("abatementStatus");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ITreasuryDAOException("查询票据冲销状态失败!", e);
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
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return lStatusID;
	}
	
	public static void main(String[] args) throws SQLException
	{
		Loan_DAO dao = new Loan_DAO();
		System.out.println(dao.getIntervalNumOfFromContractInfo(4));
	}
}
