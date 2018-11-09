package com.iss.itreasury.settlement.transferloancontract.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.ContractdetailInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferContractInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetailConditionInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetialResultInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeDetailInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositQueryInfo;
import com.iss.itreasury.settlement.transferinterest.dataentity.TransferInterestRecordInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetailConditionInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetialResultInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-9-8
 */
public class Sett_TransferLoanContractDepositDAO extends SettlementDAO
{
	
	private StringBuffer strSelectSQLBuffer = null;
	
	public Sett_TransferLoanContractDepositDAO()
	{
		super.strTableName = "sett_transferloanamount";		
	}
	/**
	 * 方法说明：新增交易记录
	 * @param TransferLoanContractInfo
	 * @return : long - 返回新增交易记录ID
	 * @throws IException
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public long add(TransferLoanContractInfo info) throws Exception
	{
		log.info("in sett_transferloanamount.add()");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			long id = getSett_RANSFERAMOUNTDEPOSITID();
			info.setID(id);
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("INSERT INTO  \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("(" + getFieldsSQLString(-1, DAO_OPERATION_ADD, null) + " \n");
			strSQLBuffer.append(") VALUES \n");			
			strSQLBuffer.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?) \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			addDatatoPrepareStatement(info, ps, DAO_OPERATION_ADD);
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(rs);
			cleanup(con);
		}
		return info.getID();
	}
	/**
	 * 方法说明：修改交易记录
	 * @param info :TransferLoanContractInfo
	 * @return : long - 返回交易记录ID
	 * @throws IException
	 */
	public long update(TransferLoanContractInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + "\n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_UPDATE, null) + " \n");
			strSQLBuffer.append(" WHERE ID = ?");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			int lastIndex = addDatatoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);
			ps.setLong(lastIndex, info.getID());
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);		
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return info.getID();
	}
	/**
	 * 方法说明：删除信贷资产转让交易记录
	 * @param info :Sett_TransCurrentDepositInfo
	 * @return : long - 返回被删除交易记录ID
	 * @throws IException
	 */
	public long delete(long id) throws Exception
	{
		this.updateStatus(id, SETTConstant.TransactionStatus.DELETED);
		return id;
	}
	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransferLoanContractInfo findByID(long transDepositID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransferLoanContractInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from ");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, transDepositID);
			rs = ps.executeQuery();
			res = this.resultToInfo(rs);
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}
	/**
	 * 方法说明：根据ID得到转让信息
	 * @param TransferLoanContractInfo
	 * @return TransferLoanContractInfo
	 * @throws IException
	 */
	public TransferLoanContractInfo findByID(TransferLoanContractInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransferLoanContractInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from ");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, info.getID());
			rs = ps.executeQuery();
			res = this.resultToInfo(rs);
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}
	/**
	 * 方法说明：修改状态
	 * @param StatusID  
	 * @return long  
	 * @throws IException
	 */
	public long updateStatus(long id, long StatusID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("StatusID = ?, nModify = sysdate \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, StatusID);
			ps.setLong(2, id);
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}
	/**
	 * 方法说明：根据查询条件组合，查询出符合的记录
	 *  Method findByConditions.
	 * @param sett_TransCurrentDepositInfo
	 * @param orderBySequence 
	 * @return Collection
	 */
	public Collection findByConditions(TransferLoanContractInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT ");
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, null));
			strSQLBuffer.append(" FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, info) + " \n");			
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			res = getInfoFromResultSet(rs);
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}
	/**
	 * 方法说明：修改复核人
	 * @param 
	 * @return long  
	 * @throws IException
	 */
	public long updateCheckUser(long id, long checkUserID) throws Exception
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
			strSQLBuffer.append(" checkuserid = ?, NMODIFY = sysdate \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, checkUserID);
			ps.setLong(2, id);
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}
	/**
	 * 方法说明：根据状态查找
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return Collection 
	 * @throws IException
	 */
	public Collection findByStatus(long statusID) throws Exception
	{
		log.info("findByStatus");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(strSelectSQLBuffer);
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" WHERE nStatusID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, statusID);
			log.info(strSQL);
			rs = ps.executeQuery();
			res = getInfoFromResultSet(rs);
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}
	/**
	 * 方法说明：根据ID查找修改时间
	 * @param transCurrentDepositID  
	 * @return Timestamp 
	 * @throws IException
	 */
	public Timestamp findTouchDate(long transCurrentDepositID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT NMODIFY FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, transCurrentDepositID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				res = rs.getTimestamp(1);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}
	

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param TransferLoanContractInfo info
	 * @return TransferLoanContractInfo
	 */
	public TransferLoanContractInfo match(TransferLoanContractInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransferLoanContractInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from ");
			strSQLBuffer.append(strTableName + " \n");
			//strSQLBuffer.append(" WHERE \n");
			String str = getFieldsSQLString(SETTConstant.TransactionType.TRANSFERPAY, DAO_OPERATION_MATCH, info);

			if (str == null) 
	        return null;
			strSQLBuffer.append(str);
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			
			rs = ps.executeQuery();
			Collection c = getInfoFromResultSet(rs);
			if (c.size() == 0)
				return null;
			else
			{
				res = (TransferLoanContractInfo) ((ArrayList) c).get(0);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
		
	}
	/**
	 * @return last index of PerpareStatement
	 * @param stmt this is a reference and param will be modified 
	 * 
	 */
	private int addDatatoPrepareStatement(TransferLoanContractInfo info, PreparedStatement stmt, int operation) throws Exception
	{
		PreparedStatement ps = stmt;
		int index = 1;
		ps.setLong(index++, info.getID());
		ps.setLong(index++, info.getOFFICEID());
		ps.setLong(index++, info.getCURRENCYID());
		ps.setString(index++, info.getSTRANSNO());
		ps.setLong(index++, info.getTRANSACTIONTYPEID());
		ps.setLong(index++, info.getNOTICEID());
		ps.setLong(index++, info.getPAYBANKID());
		ps.setLong(index++, info.getPAYGENRALLEDGERTYPEID());
		ps.setDouble(index++, info.getAMOUNT());
		ps.setDouble(index++, info.getINTEREST());
		ps.setDouble(index++, info.getCOMMISSION());
		ps.setLong(index++, info.getRECEIVEBANKID());
		ps.setLong(index++, info.getRECGENERALLEDGERTYPEID());		
		ps.setTimestamp(index++, info.getINTERESTSTART());
		ps.setTimestamp(index++, info.getEXECUTE());
		ps.setLong(index++, info.getINPUTUSERID());
		ps.setString(index++, info.getSABSTRACT());
		ps.setString(index++, info.getSCHECKABSTRACT());
		ps.setLong(index++, info.getSTATUSID());
		ps.setLong(index++, info.getCHECKUSERID());
		
		if (operation == DAO_OPERATION_ADD)
		{
			ps.setTimestamp(index++, info.getINPUTDATE());
		}  
		else if (operation == DAO_OPERATION_UPDATE)
		{
		}
		ps.setLong(index++, info.getABSTRACTID());
		ps.setLong(index++, info.getTRANSFERCONTRACTID());
		ps.setLong(index++, info.getTRANSFERTYPE());
		ps.setLong(index++, info.getCOUNTERPARTID());
		System.out.println("**********************===="+index);
		return index;
	}
	/**
	 * 将ResultSet转换为TransferLoanContractInfo 
	 * @param rset ResultSet
	 * @return TransferLoanContractInfo
	 * @throws SQLException
	 */
  private static TransferLoanContractInfo resultToInfo(ResultSet rset) throws Exception
{
	 TransferLoanContractInfo info = new TransferLoanContractInfo();
    if (rset.next())
  {
	
	info.setID(rset.getLong("ID"));
	info.setOFFICEID(rset.getLong("officeid"));
	info.setCURRENCYID(rset.getLong("currencyid"));
	info.setSTRANSNO(rset.getString("stransno") == null ? "" : rset.getString("stransno"));
	info.setTRANSACTIONTYPEID(rset.getLong("transactiontypeid"));
	info.setNOTICEID(rset.getLong("noticeid"));
	info.setPAYBANKID(rset.getLong("paybankid"));
	info.setPAYGENRALLEDGERTYPEID(rset.getLong("PAYGENRALLEDGERTYPEID"));
	info.setAMOUNT(rset.getDouble("amount"));
	info.setINTEREST(rset.getDouble("interest"));
	info.setCOMMISSION(rset.getDouble("commission"));
	info.setRECEIVEBANKID(rset.getLong("RECEIVEBANKID"));
	info.setRECGENERALLEDGERTYPEID(rset.getLong("RECGENERALLEDGERTYPEID"));
	info.setINTERESTSTART(rset.getTimestamp("intereststart"));
	info.setEXECUTE(rset.getTimestamp("execute"));
	info.setINPUTUSERID(rset.getLong("inputuserid"));
	info.setSABSTRACT(rset.getString("sabstract"));
	info.setSCHECKABSTRACT(rset.getString("scheckabstract"));
	info.setABSTRACTID(rset.getLong("abstractid"));
	info.setSTATUSID(rset.getLong("statusid"));
	info.setCHECKUSERID(rset.getLong("checkuserid"));
	info.setNMODIFY(rset.getTimestamp("nmodify"));
	info.setINPUTDATE(rset.getTimestamp("inputdate"));
	info.setTRANSFERCONTRACTID(rset.getLong("transfercontractid"));
	info.setTRANSFERTYPE(rset.getLong("transfertype"));
	info.setCOUNTERPARTID(rset.getLong("counterpartid"));
	
  }
    return info;
}
	private Collection getInfoFromResultSet(ResultSet rset) throws Exception
	{
		ArrayList list = new ArrayList();
		while (rset.next())
		{
			TransferLoanContractInfo info = new TransferLoanContractInfo();
			info.setID(rset.getLong("ID"));
			info.setOFFICEID(rset.getLong("officeid"));
			info.setCURRENCYID(rset.getLong("currencyid"));
			info.setSTRANSNO(rset.getString("stransno") == null ? "" : rset.getString("stransno"));
			info.setTRANSACTIONTYPEID(rset.getLong("transactiontypeid"));
			info.setNOTICEID(rset.getLong("noticeid"));
			info.setPAYBANKID(rset.getLong("paybankid"));
			info.setPAYGENRALLEDGERTYPEID(rset.getLong("PAYGENRALLEDGERTYPEID"));
			info.setAMOUNT(rset.getDouble("amount"));
			info.setINTEREST(rset.getDouble("interest"));
			info.setCOMMISSION(rset.getDouble("commission"));
			info.setRECEIVEBANKID(rset.getLong("RECEIVEBANKID"));
			info.setRECGENERALLEDGERTYPEID(rset.getLong("RECGENERALLEDGERTYPEID"));
			info.setINTERESTSTART(rset.getTimestamp("intereststart"));
			info.setEXECUTE(rset.getTimestamp("execute"));
			info.setINPUTUSERID(rset.getLong("inputuserid"));
			info.setSABSTRACT(rset.getString("sabstract"));
			info.setSCHECKABSTRACT(rset.getString("scheckabstract"));
			info.setABSTRACTID(rset.getLong("abstractid"));
			info.setSTATUSID(rset.getLong("statusid"));
			info.setCHECKUSERID(rset.getLong("checkuserid"));
			info.setNMODIFY(rset.getTimestamp("nmodify"));
			info.setINPUTDATE(rset.getTimestamp("inputdate"));	
			info.setTRANSFERTYPE(rset.getLong("transfertype"));
			info.setTRANSFERCONTRACTID(rset.getLong("transfercontractid"));
			list.add(info);
		}
		return list;
	}
	private Collection getQueryInfoFromResultSet(ResultSet rs) throws Exception
	{
		ArrayList resList = new ArrayList();
		while (rs.next())
		{
			TransCurrentDepositQueryInfo info = new TransCurrentDepositQueryInfo();
			int index = 1;
			info.setId(rs.getLong(index++));
			info.setTransNo(rs.getString(index++));
			info.setTransactionTypeID(rs.getLong(index++));
			info.setPayAccountID(rs.getLong(index++));
			long clientID = rs.getLong(index++);
			info.setPayClientID(clientID);
			info.setPayClientName(NameRef.getClientNameByID(clientID));
			info.setConsignVoucherNo(rs.getString(index++));
			info.setAmount(rs.getDouble(index++));
			info.setInterestStartDate(rs.getTimestamp(index++));
			info.setExecuteDate(rs.getTimestamp(index++));
			info.setAbstractStr(rs.getString(index++));
			info.setStatusID(rs.getLong(index++));
			resList.add(info);
		}
		return resList;
	}
	/**
	 *@param1 transactionType transaction type, sucha as check payment, -1 mean
	 *that this function do not need know transaction type
	*/
	private String getFieldsSQLString(long transactionType, int operation, TransferLoanContractInfo info)
	{
		StringBuffer strSQLBuffer = new StringBuffer();
		String strRes = "";
		if ((operation == DAO_OPERATION_ADD || operation == DAO_OPERATION_FIND || operation == DAO_OPERATION_BATCHCHECK) && info == null)
		{
			
			strSQLBuffer.append(" ID, \n");
			strSQLBuffer.append("OfficeID, \n");
			strSQLBuffer.append("CurrencyID, \n");
			strSQLBuffer.append("sTransNo, \n");
			strSQLBuffer.append("TransactionTypeID, \n");
			strSQLBuffer.append("NoticeID, \n");
			strSQLBuffer.append("PayBankID, \n");
			strSQLBuffer.append("PAYGENRALLEDGERTYPEID, \n");
			strSQLBuffer.append("AMOUNT, \n");
			strSQLBuffer.append("INTEREST, \n");
			strSQLBuffer.append("COMMISSION, \n");
			strSQLBuffer.append("RECEIVEBANKID, \n");
			strSQLBuffer.append("RECGENERALLEDGERTYPEID, \n");
			strSQLBuffer.append("INTERESTSTART, \n");
			strSQLBuffer.append("EXECUTE, \n");
			strSQLBuffer.append("NMODIFY, \n");
			strSQLBuffer.append("INPUTUSERID, \n");
			strSQLBuffer.append("SABSTRACT, \n");
			strSQLBuffer.append("SCHECKABSTRACT, \n");
			strSQLBuffer.append("STATUSID, \n");
			strSQLBuffer.append("CHECKUSERID, \n");	
			strSQLBuffer.append("INPUTDATE, \n");
			strSQLBuffer.append("ABSTRACTID, \n");
			strSQLBuffer.append("TRANSFERCONTRACTID, \n");	
			strSQLBuffer.append("TRANSFERTYPE, \n");
			strSQLBuffer.append("COUNTERPARTID \n");	
			strRes = strSQLBuffer.toString();
		}
		else if (operation == DAO_OPERATION_UPDATE)
		{
			strSQLBuffer.append(" ID = ?, \n");
			strSQLBuffer.append("OfficeID = ?, \n");
			strSQLBuffer.append("CurrencyID = ?, \n");
			strSQLBuffer.append("sTransNo = ?, \n");
			strSQLBuffer.append("TransactionTypeID = ?, \n");
			strSQLBuffer.append("NoticeID = ?, \n");
			strSQLBuffer.append("PayBankID = ?, \n");
			strSQLBuffer.append("PAYGENRALLEDGERTYPEID = ?, \n");
			strSQLBuffer.append("AMOUNT = ?, \n");
			strSQLBuffer.append("INTEREST = ?, \n");
			strSQLBuffer.append("COMMISSION = ?, \n");
			strSQLBuffer.append("RECEIVEBANKID = ?, \n");			
			strSQLBuffer.append("RECGENERALLEDGERTYPEID = ?, \n");
			strSQLBuffer.append("INTERESTSTART = ?, \n");
			strSQLBuffer.append("EXECUTE = ?, \n");		
			strSQLBuffer.append("NMODIFY = sysdate, \n");
			strSQLBuffer.append("INPUTUSERID = ?, \n");
			strSQLBuffer.append("SABSTRACT = ?, \n");
			strSQLBuffer.append("SCHECKABSTRACT = ?, \n");
			strSQLBuffer.append("STATUSID = ?, \n");
			strSQLBuffer.append("CHECKUSERID = ?, \n");
			strSQLBuffer.append("ABSTRACTID = ?, \n");
			strSQLBuffer.append("TRANSFERCONTRACTID = ?, \n");	
			strSQLBuffer.append("TRANSFERTYPE = ?, \n");
			strSQLBuffer.append("COUNTERPARTID = ? \n");	           
            strRes = strSQLBuffer.toString();
		}
		else if (operation == DAO_OPERATION_FIND && info != null)
		{
			boolean isNeedWhere = false;
			long id = info.getID();
			if (id != -1)
			{
				strSQLBuffer.append(" AND ID = " + id + " \n");
				isNeedWhere = true;
			}
			long officeID = info.getOFFICEID();
			if (officeID != -1)
			{
				strSQLBuffer.append(" AND officeID =" + officeID + " \n");
				isNeedWhere = true;
			}
			long currencyID = info.getCURRENCYID();
			if (currencyID != -1)
			{
				strSQLBuffer.append(" AND CurrencyID = " + currencyID + " \n");
				isNeedWhere = true;
			}
			String strTransNo = info.getSTRANSNO();
			if (strTransNo != null && strTransNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sTransNo = '" + strTransNo + "' \n");
				isNeedWhere = true;
			}
			long transactionTypeID = info.getTRANSACTIONTYPEID();
			if (transactionTypeID != -1)
			{
				strSQLBuffer.append(" AND TransactionTypeID = " + transactionTypeID + " \n");
				isNeedWhere = true;
			}
			long noticeID = info.getNOTICEID();
			if (noticeID != -1)
			{
				strSQLBuffer.append(" AND NoticeID =" + noticeID + " \n");
				isNeedWhere = true;
			}
			long payBankID = info.getPAYBANKID();
			if (payBankID != -1)
			{
				strSQLBuffer.append(" AND payBankID =" + payBankID + " \n");
				isNeedWhere = true;
			}
			long PAYGENRALLEDGERTYPEID = info.getPAYGENRALLEDGERTYPEID();
			if (PAYGENRALLEDGERTYPEID != -1)
			{
				strSQLBuffer.append(" AND PAYGENRALLEDGERTYPEID =" + PAYGENRALLEDGERTYPEID + " \n");
				isNeedWhere = true;
			}			
			double amount = info.getAMOUNT();
			if (amount != 0.0)
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(amount);
				strSQLBuffer.append(" AND mAmount =" + strAmountTemp + " \n");
				isNeedWhere = true;
			}
			double interest = info.getINTEREST();
			if (interest != 0.0)
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(interest);
				strSQLBuffer.append(" AND interest =" + strAmountTemp + " \n");
				isNeedWhere = true;
			}
			double commission = info.getCOMMISSION();
			if (commission != 0.0)
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(commission);
				strSQLBuffer.append(" AND commission =" + strAmountTemp + " \n");
				isNeedWhere = true;
			}
			long receiveBankID = info.getRECEIVEBANKID();
			if (receiveBankID != -1)
			{
				strSQLBuffer.append(" AND receiveBankID =" + receiveBankID + " \n");
				isNeedWhere = true;
			}
			long RECGENERALLEDGERTYPEID = info.getRECGENERALLEDGERTYPEID();
			if (RECGENERALLEDGERTYPEID != -1)
			{
				strSQLBuffer.append(" AND RECGENERALLEDGERTYPEID =" + RECGENERALLEDGERTYPEID + " \n");
				isNeedWhere = true;
			}
			Timestamp interestStartDate = info.getINTERESTSTART();
			if (interestStartDate != null)
			{
				String time = interestStartDate.toString();
				time = time.substring(0, 10);
				strSQLBuffer.append(" AND INTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			Timestamp execDate = info.getEXECUTE();
			if (execDate != null)
			{
				String time = execDate.toString();
				time = time.substring(0, 10);
				strSQLBuffer.append(" AND EXECUTE = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			long inputUserID = info.getINPUTUSERID();
			if (inputUserID != -1)
			{
				strSQLBuffer.append(" AND InputUserID =" + inputUserID + " \n");
				isNeedWhere = true;
			}
			long checkUserID = info.getCHECKUSERID();
			if (checkUserID != -1)
			{
				strSQLBuffer.append(" AND CheckUserID =" + checkUserID + " \n");
				isNeedWhere = true;
			}
			String strAbs = info.getSABSTRACT();
			if (strAbs != null && strAbs.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND SABSTRACT = '" + strAbs + "' \n");
				isNeedWhere = true;
			}
			String strCheckAbs = info.getSCHECKABSTRACT();
			if (strCheckAbs != null && strCheckAbs.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND SCHECKABSTRACT = '" + strCheckAbs + "' \n");
				isNeedWhere = true;
			}
			long[] statusIDs = info.getStatusIDs();
			if (statusIDs != null)
			{
				strSQLBuffer.append(" AND (StatusID =" + statusIDs[0] + " \n");
				for (int i = 1; i < statusIDs.length; i++)
				{
					strSQLBuffer.append(" OR StatusID =" + statusIDs[i] + " \n");
				}
				strSQLBuffer.append(")");
				isNeedWhere = true;
			}		
			strRes = strSQLBuffer.toString();
			if (strRes.startsWith(" AND"))
				strRes = strRes.substring(4);
			if (isNeedWhere)
				strRes = " WHERE " + strRes;
		}
		else if (operation == DAO_OPERATION_MATCH && info != null)
		{
			boolean isNeedWhere = false;
			if (info.isNeedMatch("id"))
			{
				strSQLBuffer.append(" AND ID =" + info.getID() + " \n");
				isNeedWhere = false;
			}
			if (info.isNeedMatch("OFFICEID"))
			{
				strSQLBuffer.append(" AND OFFICEID =" + info.getOFFICEID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("CURRENCYID"))
			{
				strSQLBuffer.append(" AND CURRENCYID =" + info.getCURRENCYID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("TRANSACTIONTYPEID"))
			{
				strSQLBuffer.append(" AND TRANSACTIONTYPEID =" + info.getTRANSACTIONTYPEID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("NOTICEID"))
			{
				strSQLBuffer.append(" AND NOTICEID =" + info.getNOTICEID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("PAYBANKID"))
			{
				strSQLBuffer.append(" AND PAYBANKID =" + info.getPAYBANKID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("PAYGENRALLEDGERTYPEID"))
			{
				strSQLBuffer.append(" AND PAYGENRALLEDGERTYPEID =" + info.getPAYGENRALLEDGERTYPEID() + " \n");
				isNeedWhere = true;
			}
			double amount = info.getAMOUNT();
			if (info.isNeedMatch("AMOUNT"))
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(amount);
				strSQLBuffer.append(" AND AMOUNT =" + strAmountTemp+ " \n");
				isNeedWhere = true;
			}
			double interest = info.getINTEREST();
			if (info.isNeedMatch("INTEREST"))
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(interest);
				strSQLBuffer.append(" AND INTEREST =" + strAmountTemp + " \n");
				isNeedWhere = true;
			}
			double commission = info.getCOMMISSION();
			if (info.isNeedMatch("COMMISSION"))
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(commission);
				strSQLBuffer.append(" AND COMMISSION =" + strAmountTemp + " \n");
				isNeedWhere = true;
			}
			Timestamp interestStartDate = info.getINTERESTSTART();
			if (info.isNeedMatch("INTERESTSTART"))
			{
				String time="";
				if(interestStartDate!=null)
				{
					time = interestStartDate.toString();
					time = time.substring(0, 10);
				}
				strSQLBuffer.append(" AND INTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("INPUTUSERID"))
			{
				strSQLBuffer.append(" AND INPUTUSERID !=" + info.getINPUTUSERID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("STATUSID"))
			{
				strSQLBuffer.append(" AND STATUSID =" + info.getSTATUSID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("RECEIVEBANKID")&& info.getTRANSACTIONTYPEID()!= SETTConstant.TransactionType.TRANSFERPAY)
			{
				strSQLBuffer.append(" AND receivebankid =" + info.getRECEIVEBANKID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch("RECGENERALLEDGERTYPEID")&&info.getTRANSACTIONTYPEID()!= SETTConstant.TransactionType.TRANSFERPAY)
			{
				strSQLBuffer.append(" AND recgeneralledgertypeid =" + info.getRECGENERALLEDGERTYPEID() + " \n");
				isNeedWhere = true;
			}
			strRes = strSQLBuffer.toString();
			if (strRes.startsWith(" AND"))
				strRes = strRes.substring(4);
			if (isNeedWhere)
				strRes = " WHERE " + strRes;
		}
		else if (operation == DAO_OPERATION_BATCHCHECK && info != null)
		{
			
			
		}
		return strRes;
	}
	
	public TransCurrentDepositInfo findByTransNo(String strTransNo) throws Exception{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransCurrentDepositInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from "+ this.strTableName + " where nstatusid > 0 and stransno = "+ strTransNo);

			ps = con.prepareStatement(strSQLBuffer.toString());

			rs = ps.executeQuery();
			Collection c = getInfoFromResultSet(rs);
			if (c.size() == 0)
				return null;
			else
			{
				//Must just have single macthed record
				res = (TransCurrentDepositInfo) ((ArrayList) c).get(0);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;		
		
		
	}
	//add by zcwang 2007-3-29 网银指令接受后修改其状态
	public long updateStatusAndTransNo(FinanceInfo info) throws Exception
	{
	    long lReturn = -1;
	    Connection con = null;
		try
		{
			con = getConnection();
			OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
			lReturn = financeDao.updateStatusAndTransNo(con, info);
			cleanup(con);
		}
		finally
		{
			cleanup(con);
		}
		return lReturn;
		
	}

	public long findstatusAndTransNO(FinanceInfo info) throws Exception
	{
	    long lReturn = -1;
	    Connection con = null;
		try
		{
			con = getConnection();
			OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
			lReturn = financeDao.findstatusAndTransNO(con, info);
			cleanup(con);
		}
		finally
		{
			cleanup(con);
		}
		return lReturn;
		
	}

	/**
	 * 查询代收收款通知单和收款业务明细组装数据
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection findNoticeAndAgentDetial(NoticeAndAgentDetailConditionInfo info) throws Exception
	{
		Collection coll = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		sql +=" select a.id noticeDetailID,a.Amount noticeDetailAmount, a.interest noticeDetailInterest,a.rate noticeDetailRate,a.payaccountid noticeDetailPayAccountID,b.id loanContractID,b.sContractCode loanContractCode,b.nborrowclientid, c.id loanPayNoticeID,c.scode loanPayNoticeCode,a.contractdetailid  \n";
		sql +=" from cra_noticecontractdetail a, loan_contractform b, loan_payform c   \n";
		sql +=" where a.contractid = b.id  AND a.payformid = c.id   and a.statusid >0 and a.NOTICEFORMID=? and a.officeid = ? and a.currencyid = ?  \n";
		//initDAO();
		//prepareStatement(sql);
		int index = 1;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(index++, info.getTransferNoticeFormId());
			ps.setLong(index++,info.getOfficeID());
			ps.setLong(index++,info.getCurrencyID());
			rs = ps.executeQuery();
			coll = new ArrayList();
			while(rs!= null && rs.next())
			{
				NoticeAndAgentDetialResultInfo resultInfo = new NoticeAndAgentDetialResultInfo();
				resultInfo.setOfficeID(info.getOfficeID());
				resultInfo.setCurrencyID(info.getCurrencyID());
				resultInfo.setNoticeFormId(info.getTransferNoticeFormId());
				resultInfo.setNoticeDetailID(rs.getLong("noticeDetailID"));
				resultInfo.setLoancontractCode(rs.getString("loanContractCode"));
				resultInfo.setNborrowclientid(rs.getLong("nborrowclientid"));
				resultInfo.setLoanContractID(rs.getLong("loanContractID"));
				resultInfo.setLoanPayNoticeID(rs.getLong("loanPayNoticeID"));
				resultInfo.setBalance(rs.getDouble("noticeDetailAmount"));
				resultInfo.setInterest(rs.getDouble("noticeDetailInterest"));
				resultInfo.setRate(rs.getDouble("noticeDetailRate"));
				resultInfo.setPayAccountID(rs.getLong("noticeDetailPayAccountID"));
				resultInfo.setCraContractDetailID(rs.getLong("contractdetailid"));
				
				resultInfo.setLastClearInterestDate(getLastClearInterestDate(resultInfo));
			
				if(info.getTransferLoanAmountID()>0)
				{
					resultInfo.setTransferLoanAmountID(info.getTransferLoanAmountID());
					resultInfo.setPayAccountID(getTransferAgentPayAccountID(resultInfo));
					
				}
				coll.add(resultInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询转让合同明细出错");
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
				if(con!=null)
				{
					con.close();
					con = null;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return coll;
	}
	/**
	 * 得到卖出卖断对成员单位的子账户的上次结息日
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public Timestamp getTransferClientAccountClearInterest(NoticeAndAgentDetialResultInfo conditionInfo)throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp tReturn = null;
		StringBuffer sql = new StringBuffer(); 
		sql.append("select case when t2.dtinterestsettlement is null then  t1.startdate  else  t2.dtinterestsettlement end clearinterest \n");
		sql.append("  from cra_transfercontractform t1, (select t.cracontractid, max(t.dtinterestsettlement)  dtinterestsettlement from sett_transferintersetrecord t where   t.NINTERESTTYPE ="+SETTConstant.InterestFeeType.INTEREST+"  and t.statusid > 0 group by t.cracontractid) t2   where t1.id = t2.cracontractid(+)  and  t1.id  = (select CRACONTRACTID from cra_transfernoticeform where ID=?)  and t1.officeid = ? and t1.currencyid = ?  and t1.STATUSID >0 \n");
		int index = 1;
		try {
			
			con = Database.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(index++, conditionInfo.getNoticeFormId());
			ps.setLong(index++,conditionInfo.getOfficeID());
			ps.setLong(index++,conditionInfo.getCurrencyID());
			rs = ps.executeQuery();
			if(rs.next())
			{
				tReturn = rs.getTimestamp("clearinterest");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询转让合同明细对应上次结息日出错");
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
				if(con!=null)
				{
					con.close();
					con = null;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return tReturn;
	}
	/**
	 * 查询转让子合同（即转让合同明细）的上次结息日
	 * 
	 * @param contractDetailInfo
	 * @return
	 * @throws ITreasuryDAOException 
	 */
	public Timestamp getLastClearInterestDate(NoticeAndAgentDetialResultInfo info) throws ITreasuryDAOException
	{
		Timestamp tsResult = null;
		
		try{
			initDAO();
			
			StringBuffer sql = new StringBuffer(); 
			
			sql.append(" select lastcleardate \n");
			sql.append("   from (select min(intereststart) lastcleardate \n");
			sql.append("           from sett_transferloandetail \n");
			sql.append("           where 1 = 1 \n");
			sql.append("           and contractdetailID = " + info.getCraContractDetailID() + " \n");
			sql.append("           and officeid = "+ info.getOfficeID() + "\n");
			sql.append("           and currencyid = " + info.getCurrencyID() + " \n");
			sql.append("           and STATUSID > 0 \n");
			sql.append("        union \n");
			sql.append("        select max(t.dtinterestsettlement) lastcleardate \n");
			sql.append("          from sett_transferintersetrecord t \n");
			sql.append("         where t.NINTERESTTYPE = " + SETTConstant.InterestFeeType.INTEREST + " \n");
			sql.append("           and t.statusid > 0 \n");
			sql.append("           and t.cracontractdetailid = " + info.getCraContractDetailID() + " \n");
			sql.append("         group by t.cracontractdetailid) \n");
			sql.append(" order by lastcleardate \n");
			
			prepareStatement(sql.toString());
			executeQuery();
			
			if(this.transRS.next())
			{
				tsResult = this.transRS.getTimestamp("lastcleardate");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("查询转让合同明细的上次结息日出错，" + e.getMessage(), e);
		}
		finally
		{
			this.finalizeDAO();
		}
		return tsResult;
	}
	/**
	 * 得到卖出卖断付款账户ID
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public long getTransferAgentPayAccountID(NoticeAndAgentDetialResultInfo conditionInfo)throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long  lReturn = -1;
		StringBuffer sql = new StringBuffer(); 
		sql.append(" select t.payaccountid  \n");
		sql.append(" from sett_transferagentamount t where t.officeid = ? and t.currencyid = ?  and t.transferloanamountid = ? and t.cranoticedetailid = ?  and t.statusid>0  \n");
		int index = 1;
		try {
			
			con = Database.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(index++,conditionInfo.getOfficeID());
			ps.setLong(index++,conditionInfo.getCurrencyID());
			ps.setLong(index++, conditionInfo.getTransferLoanAmountID());
			ps.setLong(index++,conditionInfo.getNoticeDetailID());
		
			rs = ps.executeQuery();
			if(rs.next())
			{
				lReturn = rs.getLong("payaccountid");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询转让合同明细对应上次结息日出错");
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
				if(con!=null)
				{
					con.close();
					con = null;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return lReturn;
	}
	/**
	 * 得到卖出回购上次计提日期
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public TransferInterestRecordInfo getTransferPreDrawDateInterest(TransferContractInfo qInfo)throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer(); 
		TransferInterestRecordInfo recordInfo = new TransferInterestRecordInfo();
		
		sql = new StringBuffer();//结息记录表里面有值
		sql.append(" select t.predrawinterest  ,t.dtinterestsettlement dtinterestsettlement from sett_transferintersetrecord t ");
		sql.append(" where t.dtinterestsettlement = ( ");
		sql.append(" select max(b.DTINTERESTSETTLEMENT)  from SETT_TRANSFERINTERSETRECORD b \n");
		sql.append(" where b.statusid = " + Constant.RecordStatus.VALID);
		sql.append(" and b.cracontractid = "+ qInfo.getId());
		sql.append(" and b.NINTERESTTYPE = "+ SETTConstant.InterestOperateType.PREDRAWINTEREST);
		sql.append(" and b.officeid = "+ qInfo.getOfficeId());
		sql.append(" and b.currencyid = "+ qInfo.getCurrencyId());
		sql.append(" ) and t.cracontractid = " + qInfo.getId());
		sql.append(" and t.NINTERESTTYPE = "+ SETTConstant.InterestOperateType.PREDRAWINTEREST);
		sql.append(" and t.officeid = "+ qInfo.getOfficeId());
		sql.append(" and t.currencyid = "+ qInfo.getCurrencyId());
		
		try {
			
			con = Database.getConnection();
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			if(rs.next())
			{						
				recordInfo.setDtinterestsettlement(rs.getTimestamp("dtinterestsettlement"));
				recordInfo.setPreDrawInterest(rs.getDouble("predrawinterest"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询转让合同明细对应上次计提日期出错");
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
				if(con!=null)
				{
					con.close();
					con = null;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return recordInfo;
	}
	
	/**
	 * 方法说明：修改交易记录
	 * @param info :TransferLoanContractInfo
	 * @return : long - 返回交易记录ID
	 * @throws IException
	 */
	public long updateCheckStatus(TransferLoanContractInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + "\n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_UPDATE, null) + " \n");
			strSQLBuffer.append(" WHERE ID = ?");
			strSQLBuffer.append(" and statusid = ?");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			int lastIndex = addDatatoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);
			ps.setLong(lastIndex, info.getID());
			if(info.getSTATUSID()==SETTConstant.TransactionStatus.SAVE)
			{
				ps.setLong(lastIndex+1, SETTConstant.TransactionStatus.CHECK);
			}
			else
			{
				ps.setLong(lastIndex+1, SETTConstant.TransactionStatus.SAVE);
			}
			lReturn=ps.executeUpdate();
			log.info(strSQL);
			cleanup(rs);
			cleanup(ps);
			cleanup(con);		
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lReturn;
	}
	
	public TransferLoanContractInfo findInfoByTransNo(TransferLoanContractInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransferLoanContractInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from "+ this.strTableName + " where statusid > 0 and stransno = "+ info.getSTRANSNO());

			ps = con.prepareStatement(strSQLBuffer.toString());
			System.out.println(strSQLBuffer.toString());
			rs = ps.executeQuery();
			Collection c = getInfoFromResultSet(rs);
			if (c.size() == 0)
				return null;
			else
			{
				//Must just have single macthed record
				res = (TransferLoanContractInfo) ((ArrayList) c).get(0);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;	
	}
}
