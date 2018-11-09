package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestSettlement;
import com.iss.itreasury.settlement.interest.dataentity.InterestFeeSettingDetailInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.QueryInterestFeeSettingDetailInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Sett_InterestFeeSettingDetailDAO extends SettlementDAO
{

	/**
	 * Constructor for Sett_InterestFeeSettingDetailDAO.
	 */
	public Sett_InterestFeeSettingDetailDAO()
	{
		super.strTableName = "Sett_InterestFeeSettingDetail";
	}

	/**
	 * Constructor for Sett_InterestFeeSettingDetailDAO.
	 * @param conn
	 */
	public Sett_InterestFeeSettingDetailDAO(Connection conn)
	{
		super(conn);
		super.strTableName = "Sett_InterestFeeSettingDetail";
	}

	public long add(InterestFeeSettingDetailInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		try
		{
			con = getConnection();

			info.setID(this.nextSequenceNo(con));

			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("INSERT INTO  \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("(");

			strSQLBuffer.append("ID,");
			strSQLBuffer.append("NOFFICEID,");
			strSQLBuffer.append("NCURRENCYID,");
			strSQLBuffer.append("NACCOUNTID,");
			strSQLBuffer.append("NSUBACCOUNTID,");
			strSQLBuffer.append("NINTERESTTYPE,");
			strSQLBuffer.append("NOPERATIONTYPE,");
			strSQLBuffer.append("DTINTERESTSTART,");
			strSQLBuffer.append("DTINTERESTEND,");
			strSQLBuffer.append("NINTERESTDAYS,");
			strSQLBuffer.append("MBASEBALANCE,");
			strSQLBuffer.append("MRATE,");
			strSQLBuffer.append("MPECISIONINTEREST,");
			strSQLBuffer.append("MINTEREST,");
			strSQLBuffer.append("MNEGOTIATEBALANCE,");
			strSQLBuffer.append("MNEGOTIATERATE,");
			strSQLBuffer.append("MNEGOTIATEPECISIONINTEREST,");
			strSQLBuffer.append("MNEGOTIATEINTEREST,");
			strSQLBuffer.append("MINTERESTTAXRATE,");
			strSQLBuffer.append("MINTERESTTAX,");
			strSQLBuffer.append("NISSAVE,");
			strSQLBuffer.append("NISKEEPACCOUNT,");
			strSQLBuffer.append("NISSUCCESS,");
			strSQLBuffer.append("SFAULTREASON,");
			strSQLBuffer.append("NINTERESTFEESETTINGID,");
			strSQLBuffer.append("NSTATUSID ");

			strSQLBuffer.append(") VALUES \n");
			strSQLBuffer.append("( ?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?) \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			//ps will be modified in function addDatatoPrepareStatement 
			addDatatoPrepareStatement(info, ps, DAO_OPERATION_ADD);

			if (ps.executeUpdate() == 1)
			{
				lResult = info.getID();
			}

		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lResult;
	}
	private int addDatatoPrepareStatement(InterestFeeSettingDetailInfo info, PreparedStatement stmt, int operation)
		throws Exception
	{

		PreparedStatement ps = stmt;
		int index = 1;

		ps.setLong(index++, info.getID());
		ps.setLong(index++, info.getOfficeID());
		ps.setLong(index++, info.getCurrencyID());
		ps.setLong(index++, info.getAccountID());
		ps.setLong(index++, info.getSubAccountID());
		ps.setLong(index++, info.getInterestType());
		ps.setLong(index++, info.getOperationType());
		ps.setTimestamp(index++, info.getInterestStartDate());
		ps.setTimestamp(index++, info.getInterestEndDate());
		ps.setLong(index++, info.getInterestDays());
		ps.setDouble(index++, info.getBaseBalance());
		ps.setDouble(index++, info.getRate());
		ps.setDouble(index++, info.getPecisionInterest());
		ps.setDouble(index++, info.getInterest());
		ps.setDouble(index++, info.getNegotiateBalance());
		ps.setDouble(index++, info.getNegotiateRate());
		ps.setDouble(index++, info.getNegotiatePecisionInterest());
		ps.setDouble(index++, info.getNegotiateInterest());
		ps.setDouble(index++, info.getInterestTaxRate());
		ps.setDouble(index++, info.getInterestTax());
		ps.setLong(index++, info.getIsSave());
		ps.setLong(index++, info.getIsKeepAccount());
		ps.setLong(index++, info.getIsSuccess());
		ps.setString(index++, info.getFaultReason());
		ps.setLong(index++, info.getInterestFeeSettingID());
		ps.setLong(index++, info.getStatusID());

		return index;
	}
	public long delete(long lID) throws Exception
	{
		//logic delete, so just update status as deleted
		this.updateStatus(lID, SETTConstant.TransactionStatus.DELETED);
		return lID;
	}

	//	public InterestFeeSettingDetailInfo findByID(long lID) throws Exception
	//	{
	//		return null;
	//	}

	public Collection findByConditions(QueryInterestFeeSettingDetailInfo conditionInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer(256);
			StringBuffer strWhereSQLBuffer = new StringBuffer(128);

			strSQLBuffer.append("SELECT settingDetail.* ");
			strSQLBuffer.append(", account.SACCOUNTNO ");
			strSQLBuffer.append(", account.SNAME ");
			strSQLBuffer.append(", account.NACCOUNTTYPEID ");
			strSQLBuffer.append(", contract.CONTRACTCODE ");
			strSQLBuffer.append(", contract.LOANPAYCODE ");
			strSQLBuffer.append(", contract.LOANTYPEID ");
			strSQLBuffer.append(", contract.CONTRACTPERIOD ");
			strSQLBuffer.append(", contract.CONTRACTYEAR ");
			strSQLBuffer.append(", contract.CONSIGNCLIENTID ");
			strSQLBuffer.append(" FROM ");
			strSQLBuffer.append(strTableName + " settingDetail \n");
			strSQLBuffer.append(", sett_interestfeesetting setting \n");
			strSQLBuffer.append(", sett_account account, sett_subaccount subAccount \n");
			strSQLBuffer.append(", sett_vcontractinfo contract \n");
			strSQLBuffer.append(" WHERE ");
			strSQLBuffer.append(" settingDetail.NACCOUNTID=account.ID \n");
			strSQLBuffer.append(" AND settingDetail.NINTERESTFEESETTINGID=setting.ID \n");
			strSQLBuffer.append(" AND settingDetail.NSUBACCOUNTID=subAccount.ID \n");
			strSQLBuffer.append(" AND subAccount.AL_nLoanNoteID = contract.LOANPAYID(+) \n");

			if (conditionInfo.getOfficeID() != -1)
			{
				strSQLBuffer.append(" AND settingDetail.NOFFICEID = " + conditionInfo.getOfficeID() + " \n");

			}

			if (conditionInfo.getCurrencyID() != -1)
			{
				strSQLBuffer.append(" AND settingDetail.NCURRENCYID = " + conditionInfo.getCurrencyID() + " \n");

			}
			if (conditionInfo.getAccountID() != -1)
			{
				//主账户
				strSQLBuffer.append(" AND settingDetail.NACCOUNTID = " + conditionInfo.getAccountID() + " \n");

			}
			if (conditionInfo.getSubAccountID() != -1)
			{
				//子账户
				strSQLBuffer.append(" AND settingDetail.NSUBACCOUNTID = " + conditionInfo.getSubAccountID() + " \n");

			}
			if (conditionInfo.getInterestType() != -1)
			{
				//利息类型
				strSQLBuffer.append(" AND settingDetail.NINTERESTTYPE = " + conditionInfo.getInterestType() + " \n");

			}
			if (conditionInfo.getIsKeepAccount() != -1)
			{
				//is '是否记账';
				strSQLBuffer.append(" AND settingDetail.NISKEEPACCOUNT = " + conditionInfo.getIsKeepAccount() + " \n");

			}
			if (conditionInfo.getIsSave() != -1)
			{
				//is '是否保存';
				strSQLBuffer.append(" AND settingDetail.NISSAVE = " + conditionInfo.getIsSave() + " \n");

			}
			if (conditionInfo.getGrantStatusID() != -1)
			{
				//is '发放状态';
				strSQLBuffer.append(" AND settingDetail.NISSUCCESS = " + conditionInfo.getGrantStatusID() + " \n");

			}
			if (conditionInfo.getStatusID() != -1)
			{
				//is '记录状态'; 
				strSQLBuffer.append(" AND settingDetail.NSTATUSID = " + conditionInfo.getStatusID() + " \n");

			}
			if (conditionInfo.getInterestFeeSettingID() != -1)
			{
				strSQLBuffer.append(
					" AND settingDetail.NINTERESTFEESETTINGID = " + conditionInfo.getInterestFeeSettingID() + " \n");

			}
			
			//关联设置表
			if (conditionInfo.getInterestDate() != null)
			{
				strSQLBuffer.append(
					" AND setting.DTINTEREST >= to_date('"
						+ DataFormat.getDateString(conditionInfo.getInterestDate())
						+ "', 'yyyy-mm-dd') \n");

			}

			//关联账户表
			if (conditionInfo.getAccountNoStart() != null && conditionInfo.getAccountNoStart().length() > 0)
			{
				//is '起始账户号';  
				strSQLBuffer.append(" AND account.SACCOUNTNO >= '" + conditionInfo.getAccountNoStart() + "' \n");

			}
			if (conditionInfo.getAccountNoEnd() != null && conditionInfo.getAccountNoEnd().length() > 0)
			{
				//is '终止账户号';  
				strSQLBuffer.append(" AND account.SACCOUNTNO <= '" + conditionInfo.getAccountNoEnd() + "' \n");

			}
			if (conditionInfo.getAccountStatusID() != -1)
			{
				//is '账户状态';	  
				strSQLBuffer.append(" AND account.NSTATUSID = " + conditionInfo.getAccountStatusID() + " \n");

			}
			if (conditionInfo.getLoanStatusID() != -1)
			{
				//is '贷款状态'; 
				strSQLBuffer.append(" AND subAccount.NSTATUSID = " + conditionInfo.getLoanStatusID() + " \n");

			}
			if (conditionInfo.getIsClearNull() == SETTConstant.BooleanValue.ISTRUE)
			{
				//is '是否滤空';
				strSQLBuffer.append(" AND subAccount.MBALANCE > 0 \n");

			}

			//关联合同视图

			if (conditionInfo.getContractNoStart() != null && conditionInfo.getContractNoStart().length() > 0)
			{
				//is '起始合同号';  
				strSQLBuffer.append(" AND contract.CONTRACTCODE >= '" + conditionInfo.getContractNoStart() + "' \n");

			}
			if (conditionInfo.getContractNoEnd() != null && conditionInfo.getContractNoEnd().length() > 0)
			{
				//is '终止合同号';  
				strSQLBuffer.append(" AND contract.CONTRACTCODE <= '" + conditionInfo.getContractNoEnd() + "' \n");

			}
			if (conditionInfo.getLoanNotesNoStart() != null && conditionInfo.getLoanNotesNoStart().length() > 0)
			{
				//is '起始放款通知单号';       
				strSQLBuffer.append(" AND contract.LOANPAYCODE >= '" + conditionInfo.getLoanNotesNoStart() + "' \n");

			}
			if (conditionInfo.getLoanNotesNoEnd() != null && conditionInfo.getLoanNotesNoEnd().length() > 0)
			{
				//is '终止放款通知单号';       
				strSQLBuffer.append(" AND contract.LOANPAYCODE <= '" + conditionInfo.getLoanNotesNoEnd() + "' \n");

			}
			if (conditionInfo.getLoanTypeID() != -1)
			{
				//is '贷款种类';               
				strSQLBuffer.append(" AND contract.LOANTYPEID = " + conditionInfo.getLoanTypeID() + " \n");

			}
			if (conditionInfo.getLoanIntervalNumMonth() != -1)
			{
				//is '贷款期限';          
				strSQLBuffer.append(
					" AND contract.CONTRACTPERIOD = " + conditionInfo.getLoanIntervalNumMonth() + " \n");

			}
			if (conditionInfo.getLoanIntervalNumYear() != null && conditionInfo.getLoanIntervalNumYear().length() > 0)
			{
				//is '贷款年期'; 	          
				strSQLBuffer.append(" AND contract.CONTRACTYEAR = '" + conditionInfo.getLoanIntervalNumYear() + "' \n");

			}
			if (conditionInfo.getConsignClientID() != -1)
			{
				//is '委托方客户ID';           
				strSQLBuffer.append(" AND contract.CONSIGNCLIENTID = " + conditionInfo.getConsignClientID() + " \n");

			}

			strSQLBuffer.append(" ORDER BY account.SACCOUNTNO \n");

			log.info(strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			rs = ps.executeQuery();

			result = new ArrayList();
			while (rs.next())
			{
				InterestFeeSettingDetailInfo info = new InterestFeeSettingDetailInfo();

				info.setID(rs.getLong("ID"));
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setCurrencyID(rs.getLong("NCURRENCYID"));
				info.setAccountID(rs.getLong("NACCOUNTID"));
				info.setSubAccountID(rs.getLong("NSUBACCOUNTID"));
				info.setInterestType(rs.getLong("NINTERESTTYPE"));
				info.setOperationType(rs.getLong("NOPERATIONTYPE"));
				info.setInterestStartDate(rs.getTimestamp("DTINTERESTSTART"));
				info.setInterestEndDate(rs.getTimestamp("DTINTERESTEND"));
				info.setInterestDays(rs.getLong("NINTERESTDAYS"));
				info.setBaseBalance(rs.getDouble("MBASEBALANCE"));
				info.setRate(rs.getDouble("MRATE"));
				info.setPecisionInterest(rs.getDouble("MPECISIONINTEREST"));
				info.setInterest(rs.getDouble("MINTEREST"));
				info.setNegotiateBalance(rs.getDouble("MNEGOTIATEBALANCE"));
				info.setNegotiateRate(rs.getDouble("MNEGOTIATERATE"));
				info.setNegotiatePecisionInterest(rs.getDouble("MNEGOTIATEPECISIONINTEREST"));
				info.setNegotiateInterest(rs.getDouble("MNEGOTIATEINTEREST"));
				info.setInterestTaxRate(rs.getDouble("MINTERESTTAXRATE"));
				info.setInterestTax(rs.getDouble("MINTERESTTAX"));
				info.setIsSave(rs.getLong("NISSAVE"));
				info.setIsKeepAccount(rs.getLong("NISKEEPACCOUNT"));
				info.setIsSuccess(rs.getLong("NISSUCCESS"));
				info.setFaultReason(rs.getString("SFAULTREASON"));
				info.setInterestFeeSettingID(rs.getLong("NINTERESTFEESETTINGID"));
				info.setStatusID(rs.getLong("NSTATUSID"));

				info.setAccountTypeID(rs.getLong("NACCOUNTTYPEID"));

				info.setAccountNo(rs.getString("SACCOUNTNO"));
				info.setAccountName(rs.getString("SNAME"));
				info.setContractNo(rs.getString("CONTRACTCODE"));
				info.setLoanNotesNo(rs.getString("LOANPAYCODE"));
				info.setLoanTypeID(rs.getLong("LOANTYPEID"));
				info.setLoanIntervalNumMonth(rs.getString("CONTRACTPERIOD"));
				info.setLoanIntervalNumYear(rs.getString("CONTRACTYEAR"));
				info.setConsignClientID(rs.getLong("CONSIGNCLIENTID"));

				result.add(info);
			}

			result = (result.size() > 0) ? result : null;
		}

		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}

	private Collection getInfoFromResultSet(ResultSet rs) throws Exception
	{

		ArrayList list = new ArrayList();
		while (rs.next())
		{
			InterestFeeSettingDetailInfo info = new InterestFeeSettingDetailInfo();

			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("NOFFICEID"));
			info.setCurrencyID(rs.getLong("NCURRENCYID"));

			info.setAccountID(rs.getLong("NACCOUNTID"));
			info.setSubAccountID(rs.getLong("NSUBACCOUNTID"));
			info.setInterestType(rs.getLong("NINTERESTTYPE"));
			info.setOperationType(rs.getLong("NOPERATIONTYPE"));
			info.setInterestStartDate(rs.getTimestamp("DTINTERESTSTART"));
			info.setInterestEndDate(rs.getTimestamp("DTINTERESTEND"));
			info.setInterestDays(rs.getLong("NINTERESTDAYS"));
			info.setBaseBalance(rs.getDouble("MBASEBALANCE"));
			info.setRate(rs.getDouble("MRATE"));
			info.setPecisionInterest(rs.getDouble("MPECISIONINTEREST"));
			info.setInterest(rs.getDouble("MINTEREST"));
			info.setNegotiateBalance(rs.getDouble("MNEGOTIATEBALANCE"));
			info.setNegotiateRate(rs.getDouble("MNEGOTIATERATE"));
			info.setNegotiatePecisionInterest(rs.getDouble("MNEGOTIATEPECISIONINTEREST"));
			info.setNegotiateInterest(rs.getDouble("MNEGOTIATEINTEREST"));
			info.setInterestTaxRate(rs.getDouble("MINTERESTTAXRATE"));
			info.setInterestTax(rs.getDouble("MINTERESTTAX"));
			info.setIsSave(rs.getLong("NISSAVE"));
			info.setIsKeepAccount(rs.getLong("NISKEEPACCOUNT"));
			info.setIsSuccess(rs.getLong("NISSUCCESS"));
			info.setFaultReason(rs.getString("SFAULTREASON"));
			info.setInterestFeeSettingID(rs.getLong("NINTERESTFEESETTINGID"));
			info.setStatusID(rs.getLong("NSTATUSID"));

			list.add(info);
		}

		list = (list.size() > 0) ? list : null;

		return list;
	}

	public long update(InterestFeeSettingDetailInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + "\n");
			strSQLBuffer.append(" SET \n");

			strSQLBuffer.append("ID=?,");
			strSQLBuffer.append("NOFFICEID=?,");
			strSQLBuffer.append("NCURRENCYID=?,");
			strSQLBuffer.append("NACCOUNTID=?,");
			strSQLBuffer.append("NSUBACCOUNTID=?,");
			strSQLBuffer.append("NINTERESTTYPE=?,");
			strSQLBuffer.append("NOPERATIONTYPE=?,");
			strSQLBuffer.append("DTINTERESTSTART=?,");
			strSQLBuffer.append("DTINTERESTEND=?,");
			strSQLBuffer.append("NINTERESTDAYS=?,");
			strSQLBuffer.append("MBASEBALANCE=?,");
			strSQLBuffer.append("MRATE=?,");
			strSQLBuffer.append("MPECISIONINTEREST=?,");
			strSQLBuffer.append("MINTEREST=?,");
			strSQLBuffer.append("MNEGOTIATEBALANCE=?,");
			strSQLBuffer.append("MNEGOTIATERATE=?,");
			strSQLBuffer.append("MNEGOTIATEPECISIONINTEREST=?,");
			strSQLBuffer.append("MNEGOTIATEINTEREST=?,");
			strSQLBuffer.append("MINTERESTTAXRATE=?,");
			strSQLBuffer.append("MINTERESTTAX=?,");
			strSQLBuffer.append("NISSAVE=?,");
			strSQLBuffer.append("NISKEEPACCOUNT=?,");
			strSQLBuffer.append("NISSUCCESS=?,");
			strSQLBuffer.append("SFAULTREASON=?,");
			strSQLBuffer.append("NINTERESTFEESETTINGID=?,");
			strSQLBuffer.append("NSTATUSID=?");

			strSQLBuffer.append(" WHERE ID = ?");

			String strSQL = strSQLBuffer.toString();

			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			int lastIndex = addDatatoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);
			//Set value of where condition
			System.out.println(lastIndex);
			ps.setLong(lastIndex, info.getID());
			ps.executeUpdate();
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return info.getID();
	}
	/**
	 * 方法说明：修改记录状态
	 * @see com.iss.itreasury.dao.SettlementDAO#updateStatus(long, long)
	 */
	public long updateStatus(long id, long statusID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("NSTATUSID = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, statusID);
			ps.setLong(2, id);
			ps.executeUpdate();

		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}

	/**
	 * 方法说明：修改记录执行状态
	 * @see com.iss.itreasury.dao.SettlementDAO#updateStatus(long, long)
	 */
	public long updateIsSuccessStatus(long id, long isSuccess) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("NISSUCCESS = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, isSuccess);
			ps.setLong(2, id);
			ps.executeUpdate();

		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}
		
	/**
	 * 方法说明：批量修改是否保存状态
	 * @param lRecordIDs 待修改的所有记录的id
	 * @param isSave @see com.iss.itreasury.settlement.util.SETTConstant.
	 * BooleanValue
	 * @return boolean true:成功 fasle:失败
	 * @throws Exception
	 */
	public boolean updateIsSaveStatus(long[] lRecordIDs, long isSave) throws Exception
	{
		boolean result = true;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (lRecordIDs == null || lRecordIDs.length == 0)
		{
			return false;
		}

		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("NISSAVE = ? \n");
			strSQLBuffer.append(" WHERE ID in(\n");
			strSQLBuffer.append(lRecordIDs[0]);
			for (int i = 1; i < lRecordIDs.length; i++)
			{
				strSQLBuffer.append("," + lRecordIDs[i]);
			}
			strSQLBuffer.append(")\n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, isSave);
			if (ps.executeUpdate() != lRecordIDs.length)
			{
				throw new IException("更新记录的数量不等于指定的数量");
			}

		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}

	/**
	 * 方法说明：批量修改是否记账状态
	 * @param lRecordIDs 待修改的所有记录的id
	 * @param isKeepAccount @see com.iss.itreasury.settlement.util.SETTConstant.
	 * BooleanValue
	 * @return boolean true:成功 fasle:失败
	 * @throws Exception
	 */
	public boolean updateIsKeepAccountStatus(long[] lRecordIDs, long isKeepAccount) throws Exception
	{
		boolean result = true;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (lRecordIDs == null || lRecordIDs.length == 0)
		{
			return false;
		}

		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("NISKEEPACCOUNT = ? \n");
			strSQLBuffer.append(" WHERE ID in(\n");
			strSQLBuffer.append(lRecordIDs[0]);
			for (int i = 1; i < lRecordIDs.length; i++)
			{
				strSQLBuffer.append("," + lRecordIDs[i]);
			}
			strSQLBuffer.append(")\n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, isKeepAccount);
			if (ps.executeUpdate() != lRecordIDs.length)
			{
				throw new IException("更新记录的数量不等于指定的数量");
			}

		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}

	/**
	 * 获得SequenceNo
	 * @param conn
	 * @return long
	 */
	private long nextSequenceNo(Connection conn)
	{
		PreparedStatement psSK = null;
		ResultSet rsSK = null;
		long lResult = -1;
		try
		{

			psSK = conn.prepareStatement("SELECT MAX(ID) + 1  AS MaxSequenceNo FROM " + this.strTableName);
			rsSK = psSK.executeQuery();
			if (rsSK.next())
			{
				lResult = rsSK.getLong(1);
			}
			if (lResult <= 0)
				lResult = 1;
		}
		catch (Exception e)
		{
			try
			{
				cleanup(rsSK);
				cleanup(psSK);
			}
			catch (Exception exec)
			{
				System.out.println(exec.getMessage());
			}
		}
		return lResult;
	}

	public Collection findAllUnSettingAccount(long lOfficeID, long lCurrencyID, Timestamp dtCheckDate) throws Exception
	{
		ArrayList collResult = null;
		long[] lArrayTemp = null;
		InterestFeeSettingDetailInfo info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			StringBuffer strSQLBuffer = new StringBuffer(5120);
			
			strSQLBuffer.append("SELECT \n");
			strSQLBuffer.append("	unsetting.AccountID, unsetting.SubAccountID,  \n");
			strSQLBuffer.append("	unsetting.AccountTypeID, unsetting.InterestFeeTypeID, \n");
			strSQLBuffer.append("	account.SACCOUNTNO , account.SNAME , account.NACCOUNTTYPEID , \n");
			strSQLBuffer.append("	contract.CONTRACTCODE , contract.LOANPAYCODE , contract.LOANTYPEID , \n"); 
			strSQLBuffer.append("	contract.CONTRACTPERIOD , contract.CONTRACTYEAR , contract.CONSIGNCLIENTID   \n");
			strSQLBuffer.append("FROM  \n");
			strSQLBuffer.append("( \n");
			strSQLBuffer.append("	( \n");
			strSQLBuffer.append("		--活期的可设置信息 \n");
			strSQLBuffer.append("		select  \n");
			strSQLBuffer.append("			account.id AS AccountID, subaccount.id AS SubAccountID, \n"); 
			strSQLBuffer.append("			account.naccounttypeid AS AccountTypeID, "+SETTConstant.InterestFeeType.INTEREST+" AS InterestFeeTypeID \n"); 
			strSQLBuffer.append("		from sett_account account, sett_subaccount subaccount \n"); 
			strSQLBuffer.append("		where \n"); 
			strSQLBuffer.append("			account.nofficeid="+lOfficeID+" \n");
			strSQLBuffer.append("			AND account.ncurrencyid="+lCurrencyID+" \n");
			strSQLBuffer.append("			AND account.naccounttypeid IN( \n"); 
			//设置活期账户类型编码
			lArrayTemp = SETTConstant.AccountType.getCurrentAccountTypeCode(lCurrencyID,lOfficeID);
			if(lArrayTemp != null)
			{
				strSQLBuffer.append("			");
				strSQLBuffer.append(lArrayTemp[0]); 
				for(int i =1; i < lArrayTemp.length; i++)
				{
					strSQLBuffer.append(",");
					strSQLBuffer.append(lArrayTemp[i]);
				}
				lArrayTemp = null;
			}
			else
			{			
				strSQLBuffer.append("			-2");
			}			
			strSQLBuffer.append("			) \n");
			strSQLBuffer.append("			AND subaccount.naccountid=account.id \n"); 
			strSQLBuffer.append("			AND subaccount.dtClearInterest = to_date('"+DataFormat.getDateString(dtCheckDate)+"','yyyy-mm-dd') \n");
			strSQLBuffer.append("		UNION \n");
			strSQLBuffer.append("		--定期的可设置信息 \n");
			strSQLBuffer.append("		select \n"); 
			strSQLBuffer.append("			account.id AS AccountID, subaccount.id AS SubAccountID, \n");
			strSQLBuffer.append("			account.naccounttypeid AS AccountTypeID, InterestFeeTypeID \n"); 
			strSQLBuffer.append("		from sett_account account, sett_subaccount subaccount, \n"); 
			strSQLBuffer.append("		( \n");
			strSQLBuffer.append("			select "+SETTConstant.InterestFeeType.INTEREST+" InterestFeeTypeID from dual \n");
			//strSQLBuffer.append("			union \n");
			//strSQLBuffer.append("			select 6 InterestFeeTypeID from dual \n");
			strSQLBuffer.append("		) \n");
			strSQLBuffer.append("		where \n");
			strSQLBuffer.append("			account.nofficeid="+lOfficeID+" \n");
			strSQLBuffer.append("			AND account.ncurrencyid="+lCurrencyID+" \n");
			strSQLBuffer.append("			AND account.naccounttypeid IN( \n"); 
			//设置定期账户类型编码
			lArrayTemp = SETTConstant.AccountType.getFixAccountTypeCode(lCurrencyID,lOfficeID);
			if(lArrayTemp != null)
			{
				strSQLBuffer.append("			");
				strSQLBuffer.append(lArrayTemp[0]); 
				for(int i =1; i < lArrayTemp.length; i++)
				{
					strSQLBuffer.append(",");
					strSQLBuffer.append(lArrayTemp[i]);
				}
				lArrayTemp = null;
			}
			else
			{			
				strSQLBuffer.append("			-2");
			}
			strSQLBuffer.append("			) \n");
			strSQLBuffer.append("			AND subaccount.naccountid=account.id \n"); 
			strSQLBuffer.append("			AND subaccount.dtClearInterest = to_date('"+DataFormat.getDateString(dtCheckDate)+"','yyyy-mm-dd') \n");
			strSQLBuffer.append("		UNION \n");
			strSQLBuffer.append("		--贷款账户的可设置信息 \n");
			strSQLBuffer.append("		select \n"); 
			strSQLBuffer.append("			account.id  AS AccountID , subaccount.id AS SubAccountID, \n");
			strSQLBuffer.append("			account.naccounttypeid AS AccountTypeID, InterestFeeTypeID \n"); 
			strSQLBuffer.append("		from sett_account account, sett_subaccount subaccount, \n"); 
			strSQLBuffer.append("		( \n");
			strSQLBuffer.append("			select "+SETTConstant.InterestFeeType.INTEREST+" InterestFeeTypeID from dual \n");
			strSQLBuffer.append("			union \n");
			strSQLBuffer.append("			select "+SETTConstant.InterestFeeType.COMMISION+" InterestFeeTypeID from dual \n");
			strSQLBuffer.append("			union \n");
			strSQLBuffer.append("			select "+SETTConstant.InterestFeeType.ASSURE+" InterestFeeTypeID from dual \n");
			strSQLBuffer.append("		) \n");
			strSQLBuffer.append("		where \n"); 
			strSQLBuffer.append("			account.nofficeid="+lOfficeID+" \n");
			strSQLBuffer.append("			AND account.ncurrencyid="+lCurrencyID+" \n");
			strSQLBuffer.append("			AND account.naccounttypeid IN( \n"); 
			//设置定期账户类型编码
			lArrayTemp = SETTConstant.AccountType.getLoanAccountTypeCode(lCurrencyID,lOfficeID);
			if(lArrayTemp != null)
			{
				strSQLBuffer.append("			");
				strSQLBuffer.append(lArrayTemp[0]); 
				for(int i =1; i < lArrayTemp.length; i++)
				{
					strSQLBuffer.append(",");
					strSQLBuffer.append(lArrayTemp[i]);
				}
				lArrayTemp = null;
			}
			else
			{			
				strSQLBuffer.append("			-2");
			}
			strSQLBuffer.append("			) \n");
			strSQLBuffer.append("			AND subaccount.naccountid=account.id \n"); 
			strSQLBuffer.append("			AND subaccount.dtClearInterest = to_date('"+DataFormat.getDateString(dtCheckDate)+"','yyyy-mm-dd') \n");
			strSQLBuffer.append("	) \n");
			strSQLBuffer.append("	MINUS \n");
			strSQLBuffer.append("	--已设置信息 \n");
			strSQLBuffer.append("	select \n"); 
			strSQLBuffer.append("		setting.naccountid AS AccountID, setting.nsubaccountid AS SubAccountID, \n");
			strSQLBuffer.append("		account.naccounttypeid AS AccountTypeID, setting.ninteresttype AS InterestFeeTypeID \n"); 
			strSQLBuffer.append("	from sett_interestfeesettingdetail setting, sett_account account,sett_interestfeesetting sett \n");
			strSQLBuffer.append("	where \n"); 
			strSQLBuffer.append("		setting.nofficeid="+lOfficeID+" \n");
			strSQLBuffer.append("		AND setting.ncurrencyid="+lCurrencyID+" \n");
			strSQLBuffer.append("		AND setting.nstatusid="+SETTConstant.TransactionStatus.SAVE+" \n"); 
			strSQLBuffer.append("		AND account.id=setting.naccountid \n");
			strSQLBuffer.append("		AND setting.ninterestfeesettingid=sett.id \n");
			strSQLBuffer.append("		AND sett.dtInterest >= to_date('"+DataFormat.getDateString(dtCheckDate)+"','yyyy-mm-dd') \n");
			strSQLBuffer.append(") unsetting \n");
			strSQLBuffer.append(", sett_account account \n");
			strSQLBuffer.append(", sett_subaccount subAccount \n");
			strSQLBuffer.append(", sett_vcontractinfo contract \n");
			strSQLBuffer.append("WHERE \n");  
			strSQLBuffer.append("	unsetting.AccountID=account.ID \n");
			strSQLBuffer.append("	AND unsetting.SubAccountID=subAccount.ID \n");
			strSQLBuffer.append("	AND subAccount.AL_nLoanNoteID = contract.LOANPAYID(+) \n");
			strSQLBuffer.append("ORDER BY unsetting.AccountID \n"); 
			
			String strSQL = strSQLBuffer.toString();

			log.info(strSQL);
			ps = conn.prepareStatement(strSQL);

			rs = ps.executeQuery();

			collResult = new ArrayList(256);
			
			while (rs.next())
			{
				info = new InterestFeeSettingDetailInfo();
				
				info.setAccountID(rs.getLong("AccountID"));
				info.setSubAccountID(rs.getLong("SubAccountID"));
				info.setAccountTypeID(rs.getLong("AccountTypeID"));
				info.setInterestType(rs.getLong("InterestFeeTypeID"));
				
				info.setAccountNo(rs.getString("SACCOUNTNO"));
				info.setAccountName(rs.getString("SNAME"));
				info.setContractNo(rs.getString("CONTRACTCODE"));
				info.setLoanNotesNo(rs.getString("LOANPAYCODE"));
				info.setLoanTypeID(rs.getLong("LOANTYPEID"));
				info.setLoanIntervalNumMonth(rs.getString("CONTRACTPERIOD"));
				info.setLoanIntervalNumYear(rs.getString("CONTRACTYEAR"));
				info.setConsignClientID(rs.getLong("CONSIGNCLIENTID"));

				collResult.add(info);
			}
			
			//计算利息
			InterestSettlement interestSettlement = new InterestSettlement();
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conn);
			InterestOperation interestOperation = new InterestOperation(conn);
			InterestBatch interestBatch = new InterestBatch(conn);

			for(int i = 0; i < collResult.size(); i++)
			{
				info = (InterestFeeSettingDetailInfo)collResult.get(i);
				
				log.debug("=============算息==============");
				log.debug(
					"当前处理的记录 AccountID:"
					+ info.getAccountID()
					+ " SubAccountID:"
					+ info.getSubAccountID());
					
				InterestQueryResultInfo calcResultInfo = null;
				try
				{	
					//算息
					calcResultInfo =
						interestSettlement.getInterestInfo(
							"",
							0,
							"",
							"",
							info.getSubAccountID(),
							info.getAccountID(),
							info.getAccountTypeID(),
							lOfficeID,
							lCurrencyID,
							dtCheckDate,
							Env.getSystemDate(conn,lOfficeID,lCurrencyID),
							info.getInterestType(),
							interestOperation,
							interestBatch,
							transDetailDAO);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

				if (calcResultInfo != null)
				{
					//保存起息日
					info.setInterestStartDate(calcResultInfo.getStartDate());
					//保存终息日
					info.setInterestEndDate(calcResultInfo.getEndDate());
					//保存利息
					info.setInterest(calcResultInfo.getInterest());
					//保存计息天数
					info.setInterestDays(calcResultInfo.getDays());
					//保存计息金额
					info.setBaseBalance(calcResultInfo.getBalance());
					//保存执行利率
					info.setRate(calcResultInfo.getInterestRate());
					//保存精确的利息值
					info.setPecisionInterest(calcResultInfo.getPecisionInterest());
					//保存利息
					info.setInterest(calcResultInfo.getInterest());
					//保存协定存款计息金额
					info.setNegotiateBalance(calcResultInfo.getNegotiateBalance());
					//保存协定利率
					info.setNegotiateRate(calcResultInfo.getNegotiateInterestRate());
					//保存精确的协定利息值
					info.setNegotiatePecisionInterest(calcResultInfo.getNegotiatePecisionInterest());
					//保存协定利息值
					info.setNegotiateInterest(calcResultInfo.getNegotiateInterest());
					//保存利息税率
					info.setInterestTaxRate(calcResultInfo.getInterestTaxRate());
					//保存利息税
					info.setInterestTax(calcResultInfo.getInterestTaxCharge());
				}
				else
				{
					System.out.println(
						"Calculate Interest failed. AccountID:"
							+ info.getSubAccountID()
							+ " SubAccountID:"
							+ info.getAccountID());
				}				
			}			
			
			collResult = (collResult.size()>0)?collResult:null;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return collResult;
	}

}
