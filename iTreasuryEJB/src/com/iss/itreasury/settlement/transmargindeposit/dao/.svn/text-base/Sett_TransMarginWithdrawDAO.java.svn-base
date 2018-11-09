
package com.iss.itreasury.settlement.transmargindeposit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginInterestInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class Sett_TransMarginWithdrawDAO extends SettlementDAO
{

	public final static int	ORDERBY_TRANSACTIONNOID				= 0;												// 交易号

	public final static int	ORDERBY_ACCOUNTNO					= 1;												// 保证金账户号

	public final static int	ORDERBY_CLIENTNAME					= 2;												// 保证金客户名称

	public final static int	ORDERBY_DEPOSITNO					= 3;												// 保证金存款单据号

	public final static int	ORDERBY_CURRENTACCOUNTNO			= 4;												// 本金转至活期账户号

	public final static int	ORDERBY_INTERESTCURRENTACCOUNTNO	= 5;												// 利息转至活期账户号

	public final static int	ORDERBY_AMOUNT						= 6;												// 金额

	public final static int	ORDERBY_INTEREST					= 7;												// 利息

	public final static int	ORDERBY_INTERESTSTARTDATE			= 8;												// 起息日

	public final static int	ORDERBY_EXECUTEDATE					= 9;												// 执行日

	public final static int	ORDERBY_STATUSID					= 10;												// 状态

	public final static int	ORDERBY_DRAWAMOUNT					= 11;												// 支取金额

	public final static int	ORDERBY_ABSTRACT					= 12;												// 摘要

	/**
	 * 日志添加
	 */
	private Log4j			log									= new Log4j(Constant.ModuleType.SETTLEMENT, this);


	/**
	 * 新增保证金支取交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            TransMarginWithdrawInfo, 保证金支取交易实体类
	 * @return long, 新生成记录的标识
	 * @throws IException
	 */
	public long add(TransMarginWithdrawInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 利用数据库的序列号取ID;
			long id = super.getSett_TransMarginWithdrawID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO sett_transmarginwithdraw \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nClientID,nAccountID, \n");
			buffer.append("sDepositNO,mRate, \n");
			buffer.append("dtStart,dtEnd, \n");
			buffer.append("nSubAccountID,nCurrentAccountID, \n");
			buffer.append("nPayTypeID,nBankID,sExtAccountNo,sExtClientName,sRemitInBank, \n");
			buffer.append("sRemitInProvince,sRemitInCity,nCashFlowID,mAmount, \n");
			buffer.append("mDrawAmount,mDepositBalance,dtInterestStart,dtExecute, \n");
			buffer.append("sCapitalExtBankNo,sSealNo,nSealBankID, \n");
			//buffer.append("mWithDrawInterest,nIsPreDraw,nIsTally, \n");
			//buffer.append("mTotalUnpayInterest,mPayableInterest,mCurrentInterest,mOtherInterest, \n");
			//buffer.append("nReceiveInterestAccountID,nInterestPayTypeID,nInterestBankID,sInterestExtAccountNo, \n");
			//buffer.append("sInterestExtClientName,sInterestRemitInBank,sInterestRemitInProvince,sInterestRemitInCity, \n");
			//buffer.append("nInterestCashFlowID,nConfirmOfficeID,sInterestExtBankNo,nCapitalAndInterestDealWay, \n");
			buffer.append("dtModify,dtInput,nInputUserID,nCheckUserID,nAbstractID, \n");
			buffer.append("sAbstract,sCheckAbstract,nStatusID, \n");
			buffer.append("sInstructionNo,nContractID,nLoanNoteID) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?) \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());

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
			//ps.setLong(index++, info.getNoticeDay());
			ps.setLong(index++, info.getSubAccountID());
			ps.setLong(index++, info.getCurrentAccountID());
			ps.setLong(index++, info.getPayTypeID());
			ps.setLong(index++, info.getBankID());
			// ps.setLong(index++,info.getExtAcctID());
			ps.setString(index++, info.getExtAcctNo());
			ps.setString(index++, info.getExtClientName());
			ps.setString(index++, info.getRemitInBank());
			ps.setString(index++, info.getRemitInProvince());
			ps.setString(index++, info.getRemitInCity());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getAmount());
			ps.setDouble(index++, info.getDrawAmount());
			ps.setDouble(index++, info.getDepositBalance());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setString(index++, info.getCapitalExtBankNo());
			ps.setString(index++, info.getSealNo());
			ps.setLong(index++, info.getSealBankID());
			//ps.setDouble(index++, info.getPreDrawInterest());
			//ps.setDouble(index++, info.getStrikePreDrawInterest());
			//ps.setDouble(index++, info.getDrawInterest());
			//ps.setLong(index++, info.getIsPreDraw());
			//ps.setLong(index++, info.getIsTally());
			//ps.setDouble(index++, info.getTotalUnpayInterest());
			//ps.setDouble(index++, info.getPayableInterest());
			//ps.setDouble(index++, info.getCurrentInterest());
			//ps.setDouble(index++, info.getOtherInterest());
			//ps.setLong(index++, info.getReceiveInterestAccountID());
			//ps.setLong(index++, info.getInterestPayTypeID());
			//ps.setLong(index++, info.getInterestBankID());
			// ps.setLong(index++,info.getInterestExtAcctID());
			//ps.setString(index++, info.getInterestExtAcctNo());
			//ps.setString(index++, info.getInterestExtClientName());
			//ps.setString(index++, info.getInterestRemitInBank());
			//ps.setString(index++, info.getInterestRemitInProvince());
			//ps.setString(index++, info.getInterestRemitInCity());
			//ps.setLong(index++, info.getInterestCashFlowID());
			//ps.setLong(index++, info.getConfirmOfficeID());
			//ps.setString(index++, info.getInterestExtBankNo());
			//ps.setLong(index++, info.getCapitalAndInterestDealWay());
			ps.setTimestamp(index++,info.getModifyDate());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getStatusID());
			ps.setString(index++, info.getInstructionNo());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getLoanNoticeID());

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
	 * 修改保证金支取转活期交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            TransMarginWithdrawInfo, 保证金交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long update(TransMarginWithdrawInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update sett_transmarginwithdraw set \n");
			buffer.append("nOfficeID=?,nCurrencyID=?,sTransNo=?,\n");
			buffer.append("nTransactionTypeID=?,nClientID=?,nAccountID=?,sDepositNO=?,\n");
			buffer.append("mRate=?,dtStart=?,dtEnd=?,\n");
			buffer.append("nSubAccountID=?,\n");
			buffer.append("nCurrentAccountID=?,nPayTypeID=?,nBankID=?,sExtAccountNo=?,sExtClientName=?,\n");
			buffer.append("sRemitInBank=?,sRemitInProvince=?,sRemitInCity=?,nCashFlowID=?,\n");
			buffer.append("mAmount=?,mDrawAmount=?,\n");
			buffer.append("mDepositBalance=?,dtInterestStart=?,dtExecute=?,sCapitalExtBankNo=?,\n");
			buffer.append("sSealNo=?,nSealBankID=?,\n");
			//buffer.append("mWithDrawInterest=?,nIsPreDraw=?,nIsTally=?,mTotalUnpayInterest=?,\n");
			//buffer.append("nReceiveInterestAccountID=?,\n");
			//buffer.append("nInterestPayTypeID=?,nInterestBankID=?,sInterestExtAccountNo=?,sInterestExtClientName=?,\n");
			//buffer.append("sInterestRemitInBank=?,sInterestRemitInProvince=?,sInterestRemitInCity=?,nInterestCashFlowID=?,\n");
			//buffer.append("nConfirmOfficeID=?,sInterestExtBankNo=?,nCapitalAndInterestDealWay=?,dtModify=sysdate,\n");
			buffer.append("dtModify=sysdate,dtInput=?,nInputUserID=?,nCheckUserID=?,nAbstractID=?,sAbstract=?,sCheckAbstract=?,\n");
			buffer.append("nStatusID=?,sInstructionNo=? ,nContractID=?,nLoanNoteID = ? ");
			buffer.append("where ID=? \n");

			ps = con.prepareStatement(buffer.toString());
			log.info(buffer.toString());
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
			ps.setLong(index++, info.getSubAccountID());
			ps.setLong(index++, info.getCurrentAccountID());
			ps.setLong(index++, info.getPayTypeID());
			ps.setLong(index++, info.getBankID());
			// ps.setLong(index++,info.getExtAcctID());
			ps.setString(index++, info.getExtAcctNo());
			ps.setString(index++, info.getExtClientName());
			ps.setString(index++, info.getRemitInBank());
			ps.setString(index++, info.getRemitInProvince());
			ps.setString(index++, info.getRemitInCity());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getAmount());
			ps.setDouble(index++, info.getDrawAmount());
			ps.setDouble(index++, info.getDepositBalance());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setString(index++, info.getCapitalExtBankNo());
			ps.setString(index++, info.getSealNo());
			ps.setLong(index++, info.getSealBankID());
			//ps.setDouble(index++, info.getPreDrawInterest());
			//ps.setDouble(index++, info.getStrikePreDrawInterest());
			//ps.setDouble(index++, info.getDrawInterest());
			//ps.setLong(index++, info.getIsPreDraw());
			//ps.setLong(index++, info.getIsTally());
			//ps.setDouble(index++, info.getTotalUnpayInterest());
			//ps.setDouble(index++, info.getPayableInterest());
			//ps.setDouble(index++, info.getCurrentInterest());
			//ps.setDouble(index++, info.getOtherInterest());
			//ps.setLong(index++, info.getReceiveInterestAccountID());
			//ps.setLong(index++, info.getInterestPayTypeID());
			//ps.setLong(index++, info.getInterestBankID());
			// ps.setLong(index++,info.getInterestExtAcctID());
			//ps.setString(index++, info.getInterestExtAcctNo());
			//ps.setString(index++, info.getInterestExtClientName());
			//ps.setString(index++, info.getInterestRemitInBank());
			//ps.setString(index++, info.getInterestRemitInProvince());
			//ps.setString(index++, info.getInterestRemitInCity());
			//ps.setLong(index++, info.getInterestCashFlowID());
			//ps.setLong(index++, info.getConfirmOfficeID());
			//ps.setString(index++, info.getInterestExtBankNo());
			//ps.setLong(index++, info.getCapitalAndInterestDealWay());
			// ps.setTimestamp(index++,info.getModifyDate());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getStatusID());
			ps.setString(index++, info.getInstructionNo());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getLoanNoticeID());
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
	 * 根据标识查询保证金支取/转活期交易明细的方法： 逻辑说明：
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginWithdrawInfo, 保证金交易实体类
	 * @throws Exception
	 */
	public TransMarginWithdrawInfo findByID(long lID) throws Exception
	{

		TransMarginWithdrawInfo info = new TransMarginWithdrawInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from sett_transmarginwithdraw where id=? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getDeposit(info, rs);
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
	 * 根据标识查询 保证金支取交易明细的方法： 逻辑说明：
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginWithdrawInfo, 保证金交易实体类
	 * @throws Exception
	 */
	public TransMarginWithdrawInfo findByTransNo(String strTransNo) throws Exception
	{

		TransMarginWithdrawInfo info = new TransMarginWithdrawInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from sett_transmarginwithdraw where sTransNo=? ";
			ps = con.prepareStatement(strSQL);
			ps.setString(1, strTransNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getDeposit(info, rs);
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
	 * 根据条件判断保证金交易是否重复的方法： 逻辑说明：
	 * 
	 * @param FixedContinueInfo
	 *            searchInfo , 保证金交易实体类
	 * @return boolean , false 重复
	 * @throws Exception
	 */
	public boolean checkIsUsed(long lID,long statusid) throws Exception
	{ 
		System.out.println("eeeeeeeeeeeeeeeeee:"+lID+" and :"+statusid);
		boolean rtnFlg = false; 
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try { 

			String strSQL = "select * from loan_assuremanagementform where id=? and  statusid = ? ";
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
	 * 修改保证金支取交易状态的方法： 逻辑说明：
	 * 
	 * @param lID,
	 *            long, 交易标识
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
			buffer.append("update sett_transmarginwithdraw set nStatusID=?,dtModify=sysdate where ID=?");
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
	private TransMarginWithdrawInfo getDeposit(TransMarginWithdrawInfo info, ResultSet rs) throws Exception
	{

		info = new TransMarginWithdrawInfo();
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
			info.setSubAccountID(rs.getLong("nSubAccountID"));
			info.setCurrentAccountID(rs.getLong("nCurrentAccountID"));
			info.setPayTypeID(rs.getLong("nPayTypeID"));
			info.setBankID(rs.getLong("nBankID"));
			// info.setExtAcctID(rs.getLong("nExtAccountID"));
			info.setExtAcctNo(rs.getString("sExtAccountNo"));
			info.setExtClientName(rs.getString("sExtClientName"));
			info.setRemitInBank(rs.getString("sRemitInBank"));
			info.setRemitInProvince(rs.getString("sRemitInProvince"));
			info.setRemitInCity(rs.getString("sRemitInCity"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setAmount(rs.getDouble("mAmount"));
			info.setDrawAmount(rs.getDouble("mDrawAmount"));
			info.setDepositBalance(rs.getDouble("mDepositBalance"));
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));
			info.setExecuteDate(rs.getTimestamp("dtExecute"));
			info.setCapitalExtBankNo(rs.getString("sCapitalExtBankNo"));
			//info.setInterestExtBankNo(rs.getString("sInterestExtBankNo"));
			info.setSealNo(rs.getString("sSealNo"));
			info.setSealBankID(rs.getLong("nSealBankID"));
			
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setContractID(rs.getLong("nContractID"));
			info.setLoanNoticeID(rs.getLong("nLoanNoteID"));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return info;

	}


	/**
	 * 得到状态条件
	 * 
	 * @param info
	 * @return
	 */
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


	/**
	 * 得到排序条件
	 * 
	 * @param info
	 * @return
	 */
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
			case ORDERBY_INTERESTCURRENTACCOUNTNO :
			{
				order = " ORDER BY nReceiveInterestAccountID ";
			}
				break;
			case ORDERBY_AMOUNT :
			{
				order = " ORDER BY mAmount ";
			}
				break;
			case ORDERBY_INTEREST :
			{
				order = " ORDER BY mCurrentInterest ";
			}
				break;
			case ORDERBY_INTERESTSTARTDATE :
			{
				order = " ORDER BY dtInterestStart ";
			}
				break;
			case ORDERBY_EXECUTEDATE :
			{
				order = " ORDER BY dtExecute ";
			}
				break;
			case ORDERBY_STATUSID :
			{
				order = " ORDER BY nStatusID ";
			}
				break;
			case ORDERBY_DRAWAMOUNT :
			{
				order = " ORDER BY mDrawAmount ";
			}
				break;
			case ORDERBY_ABSTRACT :
			{
				order = " ORDER BY sAbstract ";
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
	 * 根据状态查询的方法： 逻辑说明：
	 * 
	 * @param QueryByStatusConditionInfo ,
	 *            按状态查询的查询条件实体类。
	 * @return Collection ,包含TransMarginWithdrawInfo查询结果实体类的记录集
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
			// 状态查 询条件
			String query = "";
			if (info.getStatus() != null) {
				query = getQueryString(info);
			}
			else {
				return arrResult;
			}
			// 排序条件
			String order = getOrderString(info);
			// 业务处理
			if (info.getTypeID() == 0) {
				/*
				 * buffer.append("select sTransNo,nTransActionTypeID
				 * ,nAccountID,\n");
				 * buffer.append("nClientID,sDepositNo,nCurrentAccountID,\n");
				 * buffer.append("nReceiveInterestAccountID,\n");
				 * buffer.append("nBankID,mAmount,mDrawAmount,mWithDrawInterest,dtInterestStart,\n");
				 * buffer.append("dtExecute,sAbstract,nStatusID \n");
				 */
				buffer.append("select * \n");
				buffer.append("from sett_transmarginwithdraw \n");
				buffer.append("where  \n");
				buffer.append("nOfficeID=? and \n");
				buffer.append("nCurrencyID=? and nTransActionTypeID=? and \n");
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

			}
			else if (info.getTypeID() == 1) // 业务复核
			{
				/*
				 * buffer.append("select sTransNo,nTransActionTypeID
				 * ,nAccountID,\n");
				 * buffer.append("nClientID,sDepositNo,nCurrentAccountID,\n");
				 * buffer.append("nReceiveInterestAccountID,\n");
				 * buffer.append("nBankID,mAmount,mDrawAmount,mWithDrawInterest,dtInterestStart,\n");
				 * buffer.append("dtExecute,sAbstract,nStatusID \n");
				 */
				buffer.append("select * \n");
				buffer.append("from sett_transmarginwithdraw \n");
				buffer.append("where  \n");
				buffer.append("nOfficeID=? and \n");
				buffer.append("nCurrencyID=? and nTransActionTypeID=? and \n");
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

			}

			rs = ps.executeQuery();
			while (rs.next()) {
				TransMarginWithdrawInfo resultInfo = new TransMarginWithdrawInfo();

				/*
				 * resultInfo.setTransNo(rs.getString("sTransNo"));
				 * resultInfo.setTransactionTypeID(rs.getLong("nTransActionTypeID"));
				 * resultInfo.setAccountNo(NameRef.getAccountNoByID(rs.getLong("nAccountID")));
				 * resultInfo.setClientName(NameRef.getClientNameByID(rs.getLong("nClientID")));
				 * resultInfo.setDepositNo(rs.getString("sDepositNo"));
				 * resultInfo.setCurrentAccountNo(NameRef.getAccountNoByID(rs.getLong("nCurrentAccountID")));
				 * resultInfo.setReceiveInterestAccountNo(NameRef.getAccountNoByID(rs.getLong("nReceiveInterestAccountID")));
				 * resultInfo.setBankName(NameRef.getBankNameByID(rs.getLong("nBankID")));
				 * resultInfo.setAmount(rs.getDouble("mAmount"));
				 * resultInfo.setDrawAmount(rs.getDouble("mDrawAmount"));
				 * resultInfo.setDrawInterest(rs.getDouble("mWithDrawInterest"));
				 * resultInfo.setStartDate(rs.getTimestamp("dtInterestStart"));
				 * resultInfo.setEndDate(rs.getTimestamp("dtExecute"));
				 * resultInfo.setAbstract(rs.getString("sAbstract"));
				 * resultInfo.setStatusID(rs.getLong("nStatusID"));
				 */
				resultInfo = this.getDeposit(resultInfo, rs);
				arrResult.add(resultInfo);
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
	 * 复核匹配的方法： 逻辑说明：
	 * 
	 * @param TransMarginWithdrawInfo,保证金支取交易复核匹配查询条件实体类
	 * @return Collection ,包含TransMarginWithdrawInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransMarginWithdrawInfo info) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = null;

			/**
			 * 保证金支取(参照定期)
			 * 
			 * //基本信息 定期账户号,定期客户名称，定期存款单据号（不要） 本金，提前支取金额，其他利息，定期支取起息日
			 * 本金转至活期账户号，活期客户名称， 活期付款方式 付款开户行 利息转至活期账户号，活期客户名称， 利息付款方式 付款开户行
			 * 本金处理方法（汇总，分笔） 缺省条件：办事处，币种，当前交易状态(未复核),录入人不是操作者
			 */
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.WITHDRAWMARGIN) 
			{
				buffer = new StringBuffer();
				
				buffer.append("select * from sett_transmarginwithdraw  \n");
				buffer.append("where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
				buffer.append("and nInputUserID <>? and nAccountID=? \n");
				buffer.append("and sDepositNo=? and mAmount=? and mDrawAmount=?  \n");
				buffer.append("and nCurrentAccountID=? and nBankID=? \n");
				buffer.append("order by ID \n");

				ps = con.prepareStatement(buffer.toString());
				
				log.info("  匹配SQL \n"+buffer.toString());

				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				System.out.println("info.getOfficeID() = "+info.getOfficeID());
				
				ps.setLong(index++, info.getCurrencyID());
				System.out.println("info.getCurrencyID() = "+info.getCurrencyID());
				
				ps.setLong(index++, info.getStatusID());
				System.out.println("info.getStatusID() = "+info.getStatusID());
				
				ps.setLong(index++, info.getInputUserID());
				System.out.println("info.getInputUserID() = "+info.getInputUserID());
				
				ps.setLong(index++, info.getAccountID());
				System.out.println("info.getAccountID() = "+info.getAccountID());
				
				ps.setString(index++, info.getDepositNo());
				System.out.println("info.getDepositNo() = "+info.getDepositNo());
				
				ps.setDouble(index++, info.getAmount());
				System.out.println("info.getAmount() = "+info.getAmount());
				
				ps.setDouble(index++, info.getDrawAmount());
				System.out.println("info.getDrawAmount() = "+info.getDrawAmount());
				
				ps.setLong(index++, info.getCurrentAccountID());
				System.out.println("info.getCurrentAccountID() = "+info.getCurrentAccountID());
				
				ps.setLong(index++, info.getBankID());
				System.out.println("info.getBankID() = "+info.getBankID());
				
				rs = ps.executeQuery();
				while (rs.next()) {
					TransMarginWithdrawInfo depositInfo = new TransMarginWithdrawInfo();
					depositInfo = getDeposit(depositInfo, rs);
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
	 * @param transNo
	 * @return
	 */
	public TransMarginInterestInfo IfindByTransNo(String transNo) throws Exception{
		// TODO Auto-generated method stub
		TransMarginInterestInfo info = null;
		Connection con = getConnection();
		PreparedStatement ps = null;		
		ResultSet rs = null;
		try
		{
			System.out.println("--------DAO:IfindByTransNo-----");
			String strSQL = "select * from sett_transinterestsettlement where stransNo=? ";	

							
			ps = con.prepareStatement(strSQL);
			ps.setString(1,transNo);			
			rs=ps.executeQuery();
			if(rs.next())
			{
				info = new TransMarginInterestInfo();
				info.setID(rs.getLong("ID"));
				info.setOfficeID(rs.getLong("nOfficeID"));
				info.setCurrencyID(rs.getLong("nCurrencyID"));
				info.setTransNo(rs.getString("sTransNo"));
				info.setTransactionTypeID(rs.getLong("ntransactiontypeid"));
				info.setAccountID(rs.getLong("naccountid"));
				info.setSubAccountID(rs.getLong("nsubaccountid"));
				info.setAccountTypeID(rs.getLong("naccounttypeid"));
				info.setInterestType(rs.getLong("ninteresttype"));
				info.setOperationType(rs.getLong("noperationtype"));
				info.setInterestSettment(rs.getTimestamp("dtinterestsettlement"));
				info.setInterestStart(rs.getTimestamp("dtintereststart"));
				info.setInterestEnd(rs.getTimestamp("dtinterestend"));
				info.setInterestDays(rs.getLong("ninterestdays"));
				info.setBaseBalance(rs.getLong("mbasebalance"));
				info.setRate(rs.getDouble("mrate"));
				info.setPecisionInterest(rs.getLong("mpecisioninterest"));
				info.setInterest(rs.getDouble("minterest"));
				info.setNegotiateBalance(rs.getLong("mnegotiatebalance"));
				info.setNegotiateRate(rs.getDouble("mnegotiaterate"));
				

				info.setNegotiatePecisionInterest(rs.getLong("mnegotiatepecisioninterest"));
				info.setNegotiateInterest(rs.getLong("mnegotiateinterest"));
				info.setInterestTaxRate(rs.getDouble("minteresttaxrate"));
				info.setInterestTax(rs.getLong("minteresttax"));
				info.setPayInterestAccountID(rs.getLong("npayinterestaccountid"));
				

				info.setReceiveInterestAccountID(rs.getLong("nreceiveinterestaccountid"));
				info.setExecute(rs.getTimestamp("dtexecute"));
				info.setInputUserID(rs.getLong("ninputuserid"));
				info.setAbstract(rs.getString("sabstract"));
				info.setCheckAbstract(rs.getString("scheckabstract"));
				info.setIsSave(rs.getLong("nissave"));
				info.setIsKeepAccount(rs.getLong("niskeepaccount"));
				info.setIsSuccess(rs.getLong("nissuccess"));
				info.setFaultReason(rs.getString("sfaultreason"));
				

				info.setInterestFeeSettingDetailID(rs.getLong("ninterestfeesettingdetailid"));
				info.setAutoExecute(rs.getTimestamp("dtautoexecute"));
				info.setBatchNo(rs.getLong("nbatchno"));
				info.setStatusID(rs.getLong("nstatusid"));
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
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}					
		}
		return info;
	}
	/**
	 * 
	 * @param id
	 * @param statusID
	 * @throws SQLException
	 */
	public void updateAssureFormStatus(long id, long statusID) throws SQLException
	{
		System.out.println("updateeeeee:"+id+"   and    "+statusID);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "loan_assuremanagementform set statusid=? where id=?");
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