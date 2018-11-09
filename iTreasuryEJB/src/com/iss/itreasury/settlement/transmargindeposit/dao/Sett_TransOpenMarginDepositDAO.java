
package com.iss.itreasury.settlement.transmargindeposit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

public class Sett_TransOpenMarginDepositDAO extends SettlementDAO
{

	public final static int	ORDERBY_TRANSACTIONNOID		= 0;												// 交易号

	public final static int	ORDERBY_ACCOUNTNO			= 1;												// 保证金账户号

	public final static int	ORDERBY_CLIENTNAME			= 2;												// 保证金客户名称

	public final static int	ORDERBY_DEPOSITNO			= 3;												// 保证金存款单据号

	public final static int	ORDERBY_CURRENTACCOUNTNO	= 4;												// 活期账户号

	public final static int	ORDERBY_BILLNO				= 5;												// 票据号

	public final static int	ORDERBY_CONSIGNVOUCHERNO	= 6;												// 委托付款凭证号

	public final static int	ORDERBY_BANKNAME			= 7;												// 开户行

	public final static int	ORDERBY_AMOUNT				= 8;												// 金额

	public final static int	ORDERBY_STARTDATE			= 9;												// 起始日

	public final static int	ORDERBY_ENDDATE				= 10;												// 到期日

	public final static int	ORDERBY_STATUSID			= 11;												// 状态

	public final static int	ORDERBY_ABSTRACT			= 12;												// 摘要

	/**
	 * 日志添加
	 */
	private Log4j			log							= new Log4j(Constant.ModuleType.SETTLEMENT, this);


	/**
	 * 新增保证金开立交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            TransMarginOpenInfo, 保证金开立交易实体类
	 * @return long, 新生成记录的标识
	 * @throws Exception
	 *             不用try catch 不用管直接抛出即可，因为是修改结果，所以才会有此现象
	 */
	public long add(TransMarginOpenInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 利用数据库的序列号取ID;
			long id = super.getSett_TransMarginOpenID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO Sett_TransOpenMarginDeposit \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nClientID,nAccountID, \n");
			buffer.append("sDepositNO,mRate, \n");
			buffer.append("dtStart,dtEnd, \n");
			buffer.append("nCurrentAccountID, \n");
			buffer.append("nBankID,nCashFlowID,mAmount, \n");
			buffer.append("dtInterestStart,dtExecute, \n");
			buffer.append("dtModify,dtInput,nInputUserID,nCheckUserID,nAbstractID, \n");
			buffer.append("sAbstract,sCheckAbstract,nStatusID, \n");
			buffer.append("sInstructionNo,sConsignVoucherNo,sConsignPassword, \n");
			buffer.append("sBillNo,nBillTypeID,nBillBankID, \n");
			buffer.append("nExtAccountID, \n");
			buffer.append("sExtBankNO,sSealNO,nSealBankID,nContractID,nLoanNoteID \n");
			buffer.append(",mCommissionAmount,nCommissionCurrentAccount,nCommissionBankID,nCommissionCashFlowID  \n");
			buffer.append(",isInterest,nInterestAccountID )\n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?, \n");
			buffer.append("?,sysdate,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?,? \n");
			buffer.append(",?,?,?,?,?,?) \n");
			
			
			ps = con.prepareStatement(buffer.toString());
			log.info(" 新增保证金开立交易:  \n" + buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getClientID());
			ps.setLong(index++, info.getAccountID());
			ps.setString(index++, info.getDepositNo());
			//ps.setLong(index++, info.getCertificationBankID());
			ps.setDouble(index++, info.getRate());
			ps.setTimestamp(index++, info.getStartDate());
			ps.setTimestamp(index++, info.getEndDate());
			//ps.setLong(index++, info.getDepositTerm());
			//ps.setLong(index++, info.getInterestPlanID());
			ps.setLong(index++, info.getCurrentAccountID());
			ps.setLong(index++, info.getBankID());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getAmount());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getStatusID());
			ps.setString(index++, info.getInstructionNo());
			ps.setString(index++, info.getConsignVoucherNo());
			ps.setString(index++, info.getConsignPassword());
			ps.setString(index++, info.getBillNo());
			ps.setLong(index++, info.getBillTypeID());
			ps.setLong(index++, info.getBillBankID());
			ps.setLong(index++, info.getExtAcctID());
			ps.setString(index++, info.getExtBankNo());
			ps.setString(index++, info.getSealNo());
			ps.setLong(index++, info.getSealBankID());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getLoanNoticeID());
			
			ps.setDouble(index++, info.getCommissionAmount());
			ps.setLong(index++, info.getCommissionCurrentAccountID());
			ps.setLong(index++, info.getCommissionBankID());
			ps.setLong(index++, info.getCommissionCashFlowID());
			ps.setLong(index++, info.getIsInterest());
			ps.setLong(index++, info.getInterestAccountID());

			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 修改保证金开立交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            TransMarginOpenInfo, 保证金 开立交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransMarginOpenInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update Sett_TransOpenMarginDeposit set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?,sTransNo=?,\n");
			buffer.append("nTransactionTypeID=?,nClientID=?,nAccountID=?,sDepositNO=?,\n");
			buffer.append("mRate=?,dtStart=?,dtEnd=?,\n");
			//buffer.append("nDepositTerm=?,\n");
			buffer.append("nCurrentAccountID=?,nBankID=?,nCashFlowID=?,mAmount=?,\n");
			buffer.append("dtInterestStart=?,dtExecute=?,dtModify=sysdate,dtInput=?,\n");
			buffer.append("nInputUserID=?,nCheckUserID=?,nAbstractID=?,sAbstract=?,sCheckAbstract=?,\n");
			buffer.append("nStatusID=?,sInstructionNo=?,sConsignVoucherNo=?,sConsignPassword=?,\n");
			buffer.append("sBillNo=?,nBillTypeID=?,nBillBankID=?,nExtAccountID=?,\n");
			buffer.append("sExtBankNO=?,sSealNO=?,\n");
			buffer.append("nSealBankID=? ,nContractID = ?,nLoanNoteID = ? , ");
			buffer.append("mCommissionAmount=? ,nCommissionCurrentAccount = ?,nCommissionBankID = ? , nCommissionCashFlowID = ? ");
			buffer.append(",isInterest=? ,nInterestAccountID = ? ");
			buffer.append("where ID=? \n");

			ps = con.prepareStatement(buffer.toString());
			log.info("修改保证金开立交易 :  \n " + buffer.toString());

			int index = 1;

			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getClientID());
			ps.setLong(index++, info.getAccountID());
			ps.setString(index++, info.getDepositNo());
			//ps.setLong(index++, info.getCertificationBankID());
			ps.setDouble(index++, info.getRate());
			ps.setTimestamp(index++, info.getStartDate());
			ps.setTimestamp(index++, info.getEndDate());
			//ps.setLong(index++, info.getDepositTerm());
			//ps.setLong(index++, info.getInterestPlanID());
			//ps.setLong(index++, info.getNoticeDay());
			ps.setLong(index++, info.getCurrentAccountID());
			ps.setLong(index++, info.getBankID());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getAmount());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getStatusID());
			ps.setString(index++, info.getInstructionNo());
			ps.setString(index++, info.getConsignVoucherNo());
			ps.setString(index++, info.getConsignPassword());
			ps.setString(index++, info.getBillNo());
			ps.setLong(index++, info.getBillTypeID());
			ps.setLong(index++, info.getBillBankID());
			ps.setLong(index++, info.getExtAcctID());
			ps.setString(index++, info.getExtBankNo());
			ps.setString(index++, info.getSealNo());
			ps.setLong(index++, info.getSealBankID());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getLoanNoticeID());
			ps.setDouble(index++,info.getCommissionAmount());
			ps.setLong(index++,info.getCommissionCurrentAccountID());
			ps.setLong(index++,info.getCommissionBankID());
			ps.setLong(index++,info.getCommissionCashFlowID());
			ps.setLong(index++,info.getIsInterest());
			ps.setLong(index++,info.getInterestAccountID());

			ps.setLong(index++, info.getID());

			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = info.getID();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 根据标识查询保证金交易明细的方法： 逻辑说明：
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginOpenInfo, 保证金交易实体类
	 * @throws Exception
	 */
	public TransMarginOpenInfo findByID(long lID) throws Exception
	{

		TransMarginOpenInfo info = new TransMarginOpenInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from Sett_TransOpenMarginDeposit where id=? ";
			log.info("根据标识查询保证金交易明细 :  \n " + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}


	/**
	 * 根据交易号查询保证金交易明细的方法： 逻辑说明：
	 * 
	 * @param strTransNo
	 *            String , 交易号
	 * @return TransMarginOpenInfo, 保证金交易实体类
	 * @throws Exception
	 */
	public TransMarginOpenInfo findByTransNo(String strTransNo) throws Exception
	{

		TransMarginOpenInfo info = new TransMarginOpenInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from  Sett_TransOpenMarginDeposit  where sTransNo=? ";
			log.info("根据交易号查询保证金交易明细 :  \n " + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, strTransNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getOpenDeposit(info, rs);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}


	/**
	 * 根据条件查询存款单据号是否重复的方法： 逻辑说明：
	 * 
	 * @param TransMarginOpenInfo,
	 *            保证金交易实体类
	 * @return boolean false 重复
	 * @throws Exception
	 */
	public boolean checkDepositNo(TransMarginOpenInfo info) throws Exception
	{

		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtnFlg = true;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select sDepositNo from Sett_TransOpenMarginDeposit where \n");
			buffer.append("sDepositNo=? and nAccountID=? and ID<>? and \n");
			buffer.append("nStatusID <> ? \n");

			ps = con.prepareStatement(buffer.toString());
			log.info("根据条件查询存款单据号是否重复 : \n" + buffer.toString());
			int index = 1;
			ps.setString(index++, info.getDepositNo());
			ps.setLong(index++, info.getAccountID());
			ps.setLong(index++, info.getID());
			ps.setLong(index++, SETTConstant.TransactionStatus.DELETED);

			rs = ps.executeQuery();
			if (rs.next()) {
				rtnFlg = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return rtnFlg;
	}

/*
	*//**
	 * 根据条件判断保证金交易是否重复的方法： 逻辑说明：
	 * 
	 * @param FixedContinueInfo
	 *            searchInfo , 保证金交易实体类
	 * @return boolean , false 重复
	 * @throws Exception
	 *//*
	public boolean checkIsDuplicate(TransFixedContinueInfo searchInfo) throws Exception
	{

		boolean rtnFlg = true;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from Sett_TransOpenMarginDeposit where id=? ";

			log.info("判断保证金交易是否重复 : \n" + strSQL);
			ps = con.prepareStatement(strSQL);
			// ps.setLong(1,lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				rtnFlg = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return rtnFlg;
	}

*/
	/**
	 * 修改保证金开立交易状态的方法： 逻辑说明：
	 * 
	 * @param lID,
	 *            long, 本金交易标识
	 * @param lStatusID,
	 *            long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update Sett_TransOpenMarginDeposit set nStatusID=? where ID=?");
			log.info("修改保证金开立交易状态 : \n" + buffer.toString());
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0) {
				lReturn = lID;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}


	/**
	 * 设置保证金交易结果集： 逻辑说明：
	 * 
	 * @throws Exception
	 */
	private TransMarginOpenInfo getOpenDeposit(TransMarginOpenInfo info, ResultSet rs) throws Exception
	{

		info = new TransMarginOpenInfo();
		try {
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setClientID(rs.getLong("nClientID"));
			info.setAccountID(rs.getLong("nAccountID"));
			info.setDepositNo(rs.getString("sDepositNo"));
			//info.setCertificationBankID(rs.getLong("nCertificationBankID"));
			info.setRate(rs.getDouble("mRate"));
			info.setStartDate(rs.getTimestamp("dtStart"));
			info.setEndDate(rs.getTimestamp("dtEnd"));
			//info.setDepositTerm(rs.getLong("nDepositTerm"));
			//info.setInterestPlanID(rs.getLong("nInterestPlanID"));
			//info.setNoticeDay(rs.getLong("nNoticeDay"));
			info.setCurrentAccountID(rs.getLong("nCurrentAccountID"));
			info.setBankID(rs.getLong("nBankID"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setAmount(rs.getDouble("mAmount"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setConsignVoucherNo(rs.getString("sConsignVoucherNo"));
			info.setConsignPassword(rs.getString("sConsignPassword"));
			info.setBillNo(rs.getString("sBillNo"));
			info.setBillTypeID(rs.getLong("nBillTypeID"));
			info.setBillBankID(rs.getLong("nBillBankID"));
			info.setExtAcctID(rs.getLong("nExtAccountID"));
			info.setExtBankNo(rs.getString("sExtBankNo"));
			info.setSealNo(rs.getString("sSealNo"));
			info.setSealBankID(rs.getLong("nSealBankID"));
			info.setContractID(rs.getLong("nContractID"));
			info.setLoanNoticeID(rs.getLong("nLoanNoteID"));
			
			info.setCommissionAmount(rs.getDouble("mCommissionAmount"));
			info.setCommissionBankID(rs.getLong("nCommissionBankID"));
			info.setCommissionCashFlowID(rs.getLong("nCommissionCashFlowID"));
			info.setCommissionCurrentAccountID(rs.getLong("nCommissionCurrentAccount"));
			
			info.setIsInterest(rs.getLong("isInterest"));
			info.setInterestAccountID(rs.getLong("nInterestAccountID"));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return info;

	}


	/**
	 * 根据状态查询的方法： 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo ,
	 *            按状态查询的查询条件实体类。
	 * @return Collection ,包含TransMarginOpenInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			// 状态查询条件
			String query = "";
			if( info != null && info.getTypeID() != 2 )
			{
				if (info.getStatus() != null) {
					query = getQueryString(info);
				}
				else {
					return arrResult;
				}	
			}

			// 排序条件
			String order = getOrderString(info);
			// 业务处理
			if (info.getTypeID() == 0) {
				// 处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）

				buffer.append("select * \n");
				buffer.append("from Sett_TransOpenMarginDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("(" + query + ") \n");
				buffer.append("and nInputUserID=? \n");
				// buffer.append("order by ID \n");
				buffer.append("" + order + "");
				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());
				ps.setLong(index++, info.getUserID());

				rs = ps.executeQuery();
				while (rs.next()) {
					TransMarginOpenInfo resultInfo = new TransMarginOpenInfo();

					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			}
			else if (info.getTypeID() == 1) // 业务复核
			{
				// 处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）

				buffer.append("select * \n");
				buffer.append("from Sett_TransOpenMarginDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("(" + query + ") \n");
				buffer.append("and nCheckUserID=? and dtExecute=? \n");
				// buffer.append("order by ID \n");
				buffer.append("" + order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());

				rs = ps.executeQuery();
				while (rs.next()) {
					TransMarginOpenInfo resultInfo = new TransMarginOpenInfo();

					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);

				}
			}
			else if(info.getTypeID() == 2)
			{

				//此分支用在保证金存款每天计算利息
				buffer.append("select * \n");
				buffer.append("from Sett_TransOpenMarginDeposit \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=?  \n");
				buffer.append("and nAccountID=? \n");
				buffer.append("and nStatusID=? \n");
				
				ps = con.prepareStatement(buffer.toString());
				
				System.out.println("--------fdfdfdfd--------"+buffer.toString());
				
				System.out.println("--------info.getOfficeID()--------"+info.getOfficeID());
				System.out.println("--------info.getCurrencyID()--------"+info.getCurrencyID());
				System.out.println("--------info.getTransactionTypeID()--------"+info.getTransactionTypeID());
				System.out.println("--------info.getAccountID()--------"+info.getAccountID());
				System.out.println("--------SETTConstant.TransactionStatus.CHECK--------"+SETTConstant.TransactionStatus.CHECK);
				
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());
				ps.setLong(index++, info.getAccountID());
				ps.setLong(index++, SETTConstant.TransactionStatus.CHECK);

				rs = ps.executeQuery();
				while (rs.next()) {
					TransMarginOpenInfo resultInfo = new TransMarginOpenInfo();

					resultInfo = getOpenDeposit(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return arrResult;

	}


	private String getQueryString(QueryByStatusConditionInfo info)
	{

		String query;
		query = "nStatusID=";
		for (int i = 0; i < info.getStatus().length; i++) {
			if (i < info.getStatus().length - 1) {

				query = query + info.getStatus()[i] + " or nStatusID=";
			}
			else {
				query = query + info.getStatus()[i];
			}
		}
		return query;
	}


	private String getOrderString(QueryByStatusConditionInfo info)
	{

		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case ORDERBY_TRANSACTIONNOID :
			{
				order = " ORDER BY sTransNo ";
			}
				break;
			case ORDERBY_ACCOUNTNO :
			{
				order = " ORDER BY nAccountID ";
			}
				break;
			case ORDERBY_CLIENTNAME :
			{
				order = " ORDER BY nClientID ";
			}
				break;
			case ORDERBY_DEPOSITNO :
			{
				order = " ORDER BY sDepositNo ";
			}
				break;
			case ORDERBY_CURRENTACCOUNTNO :
			{
				order = " ORDER BY nCurrentAccountID ";
			}
				break;
			case ORDERBY_CONSIGNVOUCHERNO :
			{
				order = " ORDER BY sConsignVoucherNo ";
			}
				break;
			case ORDERBY_BILLNO :
			{
				order = " ORDER BY sBillNo ";
			}
				break;
			case ORDERBY_BANKNAME :
			{
				order = " ORDER BY nBankID ";
			}
				break;
			case ORDERBY_AMOUNT :
			{
				order = " ORDER BY mAmount ";
			}
				break;
			case ORDERBY_STARTDATE :
			{
				order = " ORDER BY dtStart ";
			}
				break;
			case ORDERBY_ENDDATE :
			{
				order = " ORDER BY dtEnd ";
			}
				break;
			case ORDERBY_ABSTRACT :
			{
				order = " ORDER BY sAbstract ";
			}
				break;
			case ORDERBY_STATUSID :
			{
				order = " ORDER BY nStatusID ";
			}
				break;
			default :
			{
				isNeedOrderBy = false;
			}
				break;
		}
		if (isNeedOrderBy) {
			if (info.isDesc())
				order = order + " DESC \n";
			else
				order = order + " ASC \n";
		}
		else {
			order = "ORDER BY ID DESC \n";
		}
		return order;
	}


	/**
	 * 复核匹配的方法： 逻辑说明：
	 * 
	 * @param TransMarginOpenInfo,保证金存款交易复核匹配查询条件实体类
	 * @return Collection ,包含TransMarginOpenInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransMarginOpenInfo info) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = null;

			/**
			 * 保证金开立 基本信息 保证金账户号,定期客户名称，保证金存款单据号（不要）， 起始日期，存款期限，利率 // 存款来源
			 * 活期账户号， * 开户行， 金额 缺省条件：办事处，币种，当前交易状态(未复核),录入人不是操作者
			 */
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.OPENMARGIN) {
				buffer = new StringBuffer();
				buffer.append("select * from Sett_TransOpenMarginDeposit \n");
				buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
				buffer.append("and nInputUserID <>? and nAccountID=? and \n");
				buffer.append("dtStart=?  and mRate=? and \n");
				buffer.append("nCurrentAccountID=? \n");
				// buffer.append("nCurrentAccountID=? and sConsignVoucherNo=?
				// \n");
				buffer.append("and nBankID=? and nCashFlowID=? and mAmount=?\n");
				buffer.append(" and nContractID = ? and nLoanNoteID =? \n");
				
				buffer.append(" and mCommissionAmount = ? and nCommissionCurrentAccount =? \n");
				buffer.append(" and nCommissionBankID = ? and nCommissionCashFlowID =? \n");
				
				buffer.append("order by ID \n");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());

				int index = 1;
				
				ps.setLong(index++, info.getOfficeID());
				log.info("info.getOfficeID():" + info.getOfficeID());
				
				ps.setLong(index++, info.getCurrencyID());
				log.info("info.getCurrencyID():" + info.getCurrencyID());
				
				ps.setLong(index++, info.getStatusID());
				log.info("info.getStatusID():" + info.getStatusID());
				
				ps.setLong(index++, info.getInputUserID());
				log.info("info.getInputUserID():" + info.getInputUserID());
				
				ps.setLong(index++, info.getAccountID());
				log.info("info.getAccountID():" + info.getAccountID());
				
				ps.setTimestamp(index++, info.getStartDate());
				log.info("info.getStartDate():" + info.getStartDate());
				
				ps.setDouble(index++, info.getRate());
				log.info("info.getRate():" + info.getRate());
				
				ps.setLong(index++, info.getCurrentAccountID());
				log.info("info.getCurrentAccountID():" + info.getCurrentAccountID());
				
				ps.setLong(index++, info.getBankID());
				log.info("info.getBankID():" + info.getBankID());
				
				ps.setLong(index++, info.getCashFlowID());
				log.info("info.getCashFlowID():" + info.getCashFlowID());
				
				ps.setDouble(index++, info.getAmount());
				log.info("info.getAmount():" + info.getAmount());
				
				ps.setDouble(index++, info.getContractID());
				log.info("info.getContractID():" + info.getContractID());
				
				ps.setDouble(index++, info.getLoanNoticeID());
				log.info("info.getLoanNoticeID():" + info.getLoanNoticeID());
				
				ps.setDouble(index++, info.getCommissionAmount());
				log.info("info.getCommissionAmount():" + info.getCommissionAmount());
				
				ps.setLong(index++, info.getCommissionCurrentAccountID());
				log.info("info.getCommissionCurrentAccountID():" + info.getCommissionCurrentAccountID());
				
				ps.setLong(index++, info.getCommissionBankID());
				log.info("info.getCommissionBankID():" + info.getCommissionBankID());
				
				ps.setLong(index++, info.getCommissionCashFlowID());
				log.info("info.getCommissionCashFlowID():" + info.getCommissionCashFlowID());
				
				rs = ps.executeQuery();
				while (rs.next()) {
					TransMarginOpenInfo depositInfo = new TransMarginOpenInfo();
					depositInfo = getOpenDeposit(depositInfo, rs);
					arrResult.add(depositInfo);
				}

			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {

				cleanup(rs);
				cleanup(ps);
				cleanup(con);

			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
		return arrResult;
	}
	/**
	 * 
	 * @author chuanliu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public boolean checkIsUsed(long lID,long statusid) throws Exception
	{ 
		System.out.println("eeeeeeeeeeeeeeeeee:"+lID+" and :"+statusid);
		boolean rtnFlg = false; 
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try { 

			String strSQL = "select * from loan_assurechargeform where id=? and  statusid = ? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1,lID);
			ps.setLong(2,statusid);
			rs = ps.executeQuery();
			if (rs.next()) {
				rtnFlg = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return rtnFlg;
	}

	/**
	 * 
	 * @author chuanliu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public void updateAssureFormStatus(long id, long statusID) throws SQLException
	{
		System.out.println("updateeeeee:"+id+"   and    "+statusID);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "loan_assurechargeform set statusid=? where id=?");
			pstmt.setLong(1, statusID);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
	}
}