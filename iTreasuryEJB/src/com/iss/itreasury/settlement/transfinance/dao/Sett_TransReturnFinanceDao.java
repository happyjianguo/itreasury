/*
 * Created on 2006-03-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transfinance.dao;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.leasehold.dao.LoanLeaseholdRepayFormDao;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransQueryFinanceNewInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceNewInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
//added by xiong fei 2010-07-16
import com.iss.itreasury.loan.repayplan.dao.RepayPlanDao;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.SessionMng;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.TreeMap;

import java.util.Iterator;;

/**
 * @author feiye 融资租凭存款交易的--还款--DAO类：
 *         1、所有变量都为Private,设置只能用setXXX方法，得到只能用getXXX方法。 2、包含变量、类型、默认值、说明 To
 *         change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class Sett_TransReturnFinanceDao extends SettlementDAO
{

	public final static int	ORDERBY_TRANSNO		= 0;	//交易号	
	public final static int	ORDERBY_CONTRACTID			= 1;	//贷款合同号
	public final static int	ORDERBY_RETURNFORMID			= 2;//还款通知单编号												

	public final static int	ORDERBY_CORPUSAMOUNT	= 3;	//本金金额											
	public final static int	ORDERBY_INTERESTAMOUNT				= 4;//利息金额												
	public final static int	ORDERBY_BAILAMOUNT	= 5;	//保证金金额	  								

	public final static int	ORDERBY_INTERESTSTART				= 6;	//起息日											
	public final static int	ORDERBY_EXECUTE			= 7;		//执行日												
	public final static int	ORDERBY_ABSTRACT				= 8;	//摘要											
	public final static int	ORDERBY_STATUSID			= 9;		//状态										
	
	/**
	 * 日志添加
	 */
	private Log4j			log							= new Log4j(Constant.ModuleType.SETTLEMENT, this);


	/**
	 * 新增融资租赁还款交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            SettTransReturnFinanceInfo, 融资租赁还款交易实体类
	 * @return long, 新生成记录的标识
	 * @throws Exception
	 *             不用try catch 不用管直接抛出即可，因为是修改结果，所以才会有此现象
	 *             	 * 刘洋修改 5-2
	 */
	public long add(TransReturnFinanceInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 利用数据库的序列号取ID;
			long id = super.getSett_TransReturnFinanceID();
			info.setID(id);
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO Sett_TransReturnFinance \n");
			buffer.append("(ID,nOfficeID,nCurrencyID,sTransNo, \n");
			buffer.append("nTransactionTypeID,nStatusID,dtModify, \n");
			buffer.append("dtInput,dtCheck,nInputUserID,nCheckUserID, \n");			
			buffer.append("nAbstractID,sAbstract,sCheckAbstract,nContractID, \n");
			buffer.append("NReturnFormID,nReturnCorpusAccountID, \n");
			buffer.append("nReturnCorpusBankID,mCorpusAmount,nReturnInterestAccountID, \n");
			buffer.append("nReturnInterestBankID,mInterestAmount, \n");			
			buffer.append("nReturnBailAccountID,mBailAmount, \n");
			buffer.append("dtInterestStart, \n");
			buffer.append("dtExecute,sInstructionNo,nCashFlowID, \n");
			//added by xiong fei 2010-07-19 添加的三个新字段
			buffer.append("mRate,issue,CONTRACTPLANDETAILID,ISDELAYPAYMENT) \n");
			buffer.append("VALUES  \n");
			buffer.append("(?,?,?,?,?,?,sysdate,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?,?, \n");
			buffer.append("?,?,?,?,?,?,?,?,?,?) \n");
			
			ps = con.prepareStatement(buffer.toString());
			log.info(" 融资租赁还款交易:  \n" + buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getID());
			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getStatusID());
			ps.setTimestamp(index++, info.getInputDate());	
			ps.setTimestamp(index++, info.getCheckDate());			
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getReturnFormID());
			ps.setLong(index++, info.getReturnCorpusAccountID());
			
			
			ps.setLong(index++, info.getReturnCorpusBankID());
			ps.setDouble(index++, info.getCorpusAmount());
			ps.setLong(index++, info.getReturnInterestAccountID());
			ps.setLong(index++, info.getReturnInterestBankID());
			ps.setDouble(index++, info.getInterestAmount());
			ps.setLong(index++, info.getReturnBailAccountID());
			ps.setDouble(index++, info.getBailAmount());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setString(index++, info.getInstructionNo());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getRate());

			//added by xiong fei 2010-07-19 新增的三个字段
			info=this.addInfo(info,"add");
			ps.setLong(index++,info.getIssue());//期数
			ps.setLong(index++, info.getLoanContractPlanDetailID());//计划明细ID
			ps.setLong(index++, info.getIsDelay());
			
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
	 * 修改融资租赁还款交易的方法： 逻辑说明：
	 * 
	 * @param info,
	 *            TransReturnFinanceInfo, 融资租赁 还款交易实体类
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 * 刘洋修改 5-2
	 */
	public long update(TransReturnFinanceInfo info) throws Exception
	{

		long lReturn = -1;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			/*
			 * 
			 * NCONTRACTID	NUMBER			合同ID（loan_contractForm表）
			 	NUMBER			还款通知单ID (loan_ leaseholdrepayform表)
			 	NUMBER	Y	-1	还本金账户ID
				NUMBER	Y	-1	还本金银行ID
			 	NUMBER(21,6)	Y	0	本次还本金金额
			 	NUMBER	Y	-1	还利息账户ID
			 	NUMBER	Y	-1	还利息银行ID
			 	NUMBER(21,6)	Y	0	本次还利息金额
			 	NUMBER	Y	-1	扣除保证金账户ID
			 	NUMBER(21,6)	Y	0	本次扣除保证金金额
			 */
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update Sett_TransReturnFinance set \n");
			buffer.append(" nOfficeID=?,nCurrencyID=?,sTransNo=?,\n");
			buffer.append(" nTransactionTypeID=?,nStatusID=?,dtModify=sysdate,dtInput=?,dtCheck=?,\n");
			buffer.append(" nInputUserID=?,nCheckUserID=?,nAbstractID=?,sAbstract=?,\n");
			buffer.append(" sCheckAbstract=?,nContractID=?,NReturnFormID=?,\n");
			buffer.append(" nReturnCorpusAccountID=?,nReturnCorpusBankID=?,mCorpusAmount=?,nReturnInterestAccountID=?,\n");
			buffer.append(" nReturnInterestBankID=?,mInterestAmount=?, \n");
			buffer.append(" nReturnBailAccountID=?,mBailAmount=?, \n");
			buffer.append(" dtInterestStart=?,dtExecute=?,sInstructionNo=?,nCashFlowID=?,\n");
			//added by xiong fei 2010-07-19 新增的三个字段
			buffer.append(" mRate=? , issue=?,CONTRACTPLANDETAILID=?,ISDELAYPAYMENT=? \n");
			buffer.append(" where ID=? \n");
								
			ps = con.prepareStatement(buffer.toString());
			log.info("修改融资租赁还款交易 :  \n " + buffer.toString());

			int index = 1;

			ps.setLong(index++, info.getOfficeID());
			ps.setLong(index++, info.getCurrencyID());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getTransactionTypeID());
			ps.setLong(index++, info.getStatusID());
			ps.setTimestamp(index++, info.getInputDate());
			ps.setTimestamp(index++, info.getCheckDate());
			
			ps.setLong(index++, info.getInputUserID());
			ps.setLong(index++, info.getCheckUserID());
			ps.setLong(index++, info.getAbstractID());
			ps.setString(index++, info.getAbstract());
			ps.setString(index++, info.getCheckAbstract());
			ps.setLong(index++, info.getContractID());
			ps.setLong(index++, info.getReturnFormID());
			
			ps.setLong(index++, info.getReturnCorpusAccountID());
			ps.setLong(index++, info.getReturnCorpusBankID());
			ps.setDouble(index++, info.getCorpusAmount());
			ps.setLong(index++, info.getReturnInterestAccountID());
			ps.setLong(index++, info.getReturnInterestBankID());
			ps.setDouble(index++, info.getInterestAmount());
			ps.setLong(index++, info.getReturnBailAccountID());
			ps.setDouble(index++, info.getBailAmount());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getExecuteDate());
			ps.setString(index++, info.getInstructionNo());
			ps.setLong(index++, info.getCashFlowID());
			ps.setDouble(index++, info.getRate());
			//added by xiong fei 2010-07-19 新增的三个字段
			TransReturnFinanceInfo info1 = new TransReturnFinanceInfo();
			info1 = info;
			info1 = this.addInfo(info1,"update");
			ps.setLong(index++, info1.getIssue());//期数
			ps.setLong(index++, info1.getLoanContractPlanDetailID());//计划明细ID
			ps.setLong(index++, info1.getIsDelay());//是否延付
			
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
	 * 根据标识查询融资租赁还款交易明细的方法： 逻辑说明：
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginOpenInfo, 融资租赁还款交易实体类
	 * @throws Exception
	 */
	public TransReturnFinanceInfo findByID(long lID) throws Exception
	{

		TransReturnFinanceInfo info = new TransReturnFinanceInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from Sett_TransReturnFinance where id=? ";
			log.info("根据标识查询融资租赁还款交易明细 :  \n " + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getReturnFinance(info, rs);
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
	 * 根据交易号查询融资租赁还款交易明细的方法： 逻辑说明：
	 * 
	 * @param strTransNo
	 *            String , 交易号
	 * @return TransReturnFinanceInfo, 融资租赁还款交易实体类
	 * @throws Exception
	 */
	public TransReturnFinanceInfo findByTransNo(String strTransNo) throws Exception
	{

		TransReturnFinanceInfo info = new TransReturnFinanceInfo();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String strSQL = "select * from  Sett_TransReturnFinance  where sTransNo=? ";
			log.info("根据交易号查询融资租赁还款交易明细 :  \n " + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, strTransNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = getReturnFinance(info, rs);
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

/*
		/**
	 * 修改融资租赁还款交易状态的方法： 逻辑说明：
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
			buffer.append("update Sett_TransReturnFinance set nStatusID=? where ID=?");
			log.info("修改融资租赁还款交易状态 : \n" + buffer.toString());
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
	 * 设置融资租赁还款交易结果集： 逻辑说明：
	 * 
	 * @throws Exception
	 */
	private TransReturnFinanceInfo getReturnFinance(TransReturnFinanceInfo info, ResultSet rs) throws Exception
	{
		info = new TransReturnFinanceInfo();
		try {
			//基本信息
			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("nOfficeID"));
			info.setCurrencyID(rs.getLong("nCurrencyID"));
			info.setTransNo(rs.getString("sTransNo"));
			info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
			info.setStatusID(rs.getLong("nStatusID"));
			info.setModifyDate(rs.getTimestamp("dtModify"));
			info.setInputDate(rs.getTimestamp("dtInput"));
			info.setCheckDate(rs.getTimestamp("dtCheck"));
			info.setInputUserID(rs.getLong("nInputUserID"));
			info.setCheckUserID(rs.getLong("nCheckUserID"));
			info.setAbstractID(rs.getLong("nAbstractID"));
			info.setAbstract(rs.getString("sAbstract"));
			info.setCheckAbstract(rs.getString("sCheckAbstract"));
	
		/*
			NCONTRACTID	NUMBER			合同ID（loan_contractForm表）
NReturnFormID	NUMBER			
nReturn Corpus AccountID	NUMBER	Y	-1	
nReturn Corpus BankID	NUMBER	Y	-1	
m CorpusAmount	NUMBER(21,6)	Y	0	
nReturnInterest AccountID	NUMBER	Y	-1	
nReturnInterest BankID	NUMBER	Y	-1	
m InterestAmount	NUMBER(21,6)	Y	0	
nReturn Bail AccountID	NUMBER	Y	-1	

		*/	
			
			//融资租赁业务信息（还款）
			info.setContractID(rs.getLong("NCONTRACTID"));		//合同ID（loan_contractForm表）
			info.setReturnFormID(rs.getLong("NReturnFormID"));	//还款通知单ID(loan_ leaseholdrepayform表)
			info.setReturnCorpusAccountID(rs.getLong("nReturnCorpusAccountID"));	//还本金账户ID
			info.setReturnCorpusBankID(rs.getLong("nReturnCorpusBankID"));	//还本金银行ID
			info.setCorpusAmount(rs.getDouble("mCorpusAmount"));	//本次还本金金额
			info.setReturnInterestAccountID(rs.getLong("nReturnInterestAccountID"));	//还利息账户ID
			info.setReturnInterestBankID(rs.getLong("nReturnInterestBankID"));	//还利息银行ID
			info.setInterestAmount(rs.getDouble("mInterestAmount"));	//本次还利息金额
			info.setReturnBailAccountID(rs.getLong("nReturnBailAccountID"));	//扣除保证金账户ID
			info.setBailAmount(rs.getDouble("mBailAmount"));	//本次扣除保证金金额
			
			info.setExecuteDate(rs.getTimestamp("dtExecute"));	//业务执行日
			info.setInterestStartDate(rs.getTimestamp("dtInterestStart"));	//融资租赁还款起息日

			//扩展的信息
			info.setInstructionNo(rs.getString("sInstructionNo"));
			info.setCashFlowID(rs.getLong("nCashFlowID"));
			info.setRate(rs.getDouble("mRate"));
			
			//合同
			if(info.getContractID()>0){
				System.out.println("取贷款合同信息-------------(开始)");
				ContractDao contractDao=new ContractDao();
				ContractInfo contractInfo=new ContractInfo();
				contractInfo=contractDao.findByID(info.getContractID());
				info.setContractCode(contractInfo.getContractCode());//合同编号
				info.setContractFinanceClientID(contractInfo.getBorrowClientID());//承租单位客户ID
				info.setContractFinanceClientCode(contractInfo.getBorrowClientCode());//承租单位客户编号			
				info.setContractFinanceClientName(contractInfo.getBorrowClientName());//承租单位客户名称
				info.setContractFinanceStartDate(contractInfo.getLoanStart());//租赁开始日期
				info.setContractFinanceEndDate(contractInfo.getLoanEnd());//租赁开始日期
				info.setContractFinanceTerm(contractInfo.getIntervalNum());//租凭期限
				info.setContractFinanceRate(contractInfo.getRate());//租赁利率
				info.setContractBailAmount(contractInfo.getRecognizanceAmount());//合同融资租赁保证金金额
				info.setContractHireAmount(contractInfo.getLoanAmount());//合同融资租赁租金金额
				/***********************获得合同已收金额,未收金额***************************/
				//Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
				long contractIds[] = {info.getContractID()};
				List hkye = this.getRepaymentBalance(contractIds);//已还款总额数
				
				if(hkye!=null && hkye.size() > 0)
				{
					TransReturnFinanceNewInfo trfn = (TransReturnFinanceNewInfo)hkye.get(0);
					info.setContractHireAmountForYS(trfn.getAmountfrom());//已收融资租赁保证金金额	只读	显示此合同下已收取的融资租赁保证金金额
					info.setContractHireAmountForWS(info.getContractHireAmount()-trfn.getAmountfrom());//未收融资租赁保证金金额	只读	融资租赁保证金金额-已收融资租赁保证金金额
				}
				else
				{
					info.setContractHireAmountForYS(0.0);//已收融资租赁保证金金额	只读	显示此合同下已收取的融资租赁保证金金额
					info.setContractHireAmountForWS(info.getContractHireAmount());//未收融资租赁保证金金额	只读	融资租赁保证金金额-已收融资租赁保证金金额
				}
				/**********************获得合同已收金额,未收金额***************************/
				
				info.setContractBailAmountForYS(contractInfo.getReturnedRecognizanceAmount());//已收融资租赁保证金金额	只读	显示此合同下已收取的融资租赁保证金金额
				info.setContractBailAmountForWS(contractInfo.getRecognizanceAmount()-contractInfo.getReturnedRecognizanceAmount());//未收融资租赁保证金金额	只读	融资租赁保证金金额-已收融资租赁保证金金额
				System.out.println("dafsfsafasdfasdfasdf");
				
				//增加的信息
				
				//---------------
				//info.setContractHireAmountForYS(contractInfo.getCurrentRecognizanceAmount());//已收融资租赁租金金额	只读	显示此合同下已收取的融资租赁租金金额
				//-----------------
				//info.setContractHireAmountForWS(contractInfo.getLoanAmount()-contractInfo.getCurrentRecognizanceAmount());//未收融资租赁租金金额	只读	融资租赁租金金额-已收融资租赁租金金额
				System.out.println("取贷款合同信息-------------(结束)");
			}
			
			//还款单
			if(info.getReturnFormID()>0){
				System.out.println("取贷款还款通知单信息-------------(开始)");
				LoanLeaseholdRepayFormDao loanLeaseholdRepayFormDao=new LoanLeaseholdRepayFormDao();
				LeaseholdRepayNoticeInfo leaseholdRepayNoticeInfo=new LeaseholdRepayNoticeInfo();
				leaseholdRepayNoticeInfo=loanLeaseholdRepayFormDao.findRepayNoticeByID(info.getReturnFormID());
				info.setReceiveFormCode(leaseholdRepayNoticeInfo.getCode());//还款通知单编号	只读	还款通知单信息
				info.setReceiveFormDate(leaseholdRepayNoticeInfo.getInputDate());//还款日期	只读	还款通知单信息
				
				//还款时必须得到收款通知单的ID
				info.setReceiveFormID(leaseholdRepayNoticeInfo.getAssureChargeFormID());    //add by feiye 2006.5.24
				
				System.out.println("取贷款还款通知单信息-------------(结束)");
			}
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
	 * @return Collection ,包含TransReturnFinanceInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection findByStatus(QueryByStatusConditionInfo info)	throws Exception 
	{
		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			//String strSQL ="";
			StringBuffer buffer = new StringBuffer();
			//状态查询条件
			String query ="";
			if(info.getStatus()!=null)
			{
				query = getQueryString(info);
			}
			else
			{
				return arrResult;
			}
			
			if(con==null){
				System.out.println("得到数据库连接为空!");
			}else{
				System.out.println("得到数据库连接不为空!");
			}
			System.out.println("nOfficeID:"+info.getOfficeID()+":AAA");
			System.out.println("nCurrencyID:"+info.getCurrencyID()+":AAA");
			System.out.println("nTransActionTypeID:"+info.getTransactionTypeID()+":AAA");
			System.out.println("nInputUserID:"+info.getUserID()+":AAA");
			System.out.println("getTypeID:"+info.getTypeID()+":AAA");
			System.out.println("stauts:"+info.getStatus().toString()+":AAA");
			System.out.println("stauts:"+info.getDate()+":AAA");
			
			//排序条件
			String order = getOrderString(info);			
			//业务处理
			if (info.getTypeID() == 0) 
			{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）			
											
								
				buffer.append("select * \n");
				buffer.append("from Sett_TransReturnFinance \n");
				buffer.append("where \n");
				buffer.append("nOfficeID=? \n");
				buffer.append("and nCurrencyID=? and nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");
				buffer.append("and nInputUserID=? \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");
				ps = con.prepareStatement(buffer.toString());				
				log.info(buffer.toString());
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				
				rs = ps.executeQuery();
				
				int j=0;
				while (rs.next()) 
				{
					System.out.println("  取得第几条数据:"+(++j));
					TransReturnFinanceInfo resultInfo = new TransReturnFinanceInfo();
					
					resultInfo = getReturnFinance(resultInfo, rs);
					arrResult.add(resultInfo);
				}
			} 
			else if (info.getTypeID() == 1) //业务复核
				{
				//处理时的查找不用加日期（因为有关机处理，暂存与未复核查全部就可以了）				
				
				buffer.append("select * \n");
				buffer.append("from Sett_TransReturnFinance a \n");
				buffer.append("where \n");
				buffer.append("a.nOfficeID=? \n");
				buffer.append("and a.nCurrencyID=? and a.nTransActionTypeID=? and \n");
				buffer.append("("+ query + ") \n");				
				buffer.append("and a.nCheckUserID=? and a.dtExecute=? \n");
				//add by zwxiao 2010-08-18 过滤掉批量还款的交易
				buffer.append("and a.stransno not in (select TRADENUM from sett_quantityrepaymentbalance b where a.ncontractid = b.contractid) \n");
				//buffer.append("order by ID \n");
				buffer.append(""+ order + "");

				ps = con.prepareStatement(buffer.toString());
				log.info(buffer.toString());				
				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				ps.setLong(index++, info.getTransactionTypeID());				
				ps.setLong(index++, info.getUserID());
				ps.setTimestamp(index++, info.getDate());								

				rs = ps.executeQuery();
				while (rs.next()) 
				{
					TransReturnFinanceInfo resultInfo = new TransReturnFinanceInfo();
					
					resultInfo = getReturnFinance(resultInfo, rs);
					arrResult.add(resultInfo);

				}
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
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

	//排序条件
	private String getOrderString(QueryByStatusConditionInfo info)
	{
		String order = "";
		boolean isNeedOrderBy = true;
		switch (info.getOrderByType())
		{
			case ORDERBY_TRANSNO :
			{
				order = " ORDER BY sTransNo ";	//交易号
			}
				break;
			case ORDERBY_CONTRACTID :
			{
				order = " ORDER BY nContractID ";	//贷款合同号
			}
				break;
			case ORDERBY_RETURNFORMID :
			{
				order = " ORDER BY NReturnFormID ";		//还款通知单编号
			}
				break;
			case ORDERBY_CORPUSAMOUNT :
			{
				order = " ORDER BY mCorpusAmount ";	//本金金额
			}
				break;
			case ORDERBY_INTERESTAMOUNT :
			{
				order = " ORDER BY mInterestAmount ";	//利息金额
			}
				break;
			case ORDERBY_BAILAMOUNT :
			{
				order = " ORDER BY mBailAmount ";	//保证金金额
			}
				break;
			case ORDERBY_INTERESTSTART :			//起息日
			{
				order = " ORDER BY dtInterestStart ";
			}
				break;
			case ORDERBY_EXECUTE :		//执行日	
			{
				order = " ORDER BY dtExecute ";
			}
				break;
			case ORDERBY_ABSTRACT :			//摘要
			{
				order = " ORDER BY SABSTRACT ";
			}
				break;
			case ORDERBY_STATUSID :			//状态
			{
				order = " ORDER BY NSTATUSID ";
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
	 * @param TransReturnFinanceInfo,融资租赁还款存款交易复核匹配查询条件实体类
	 * @return Collection ,包含TransReturnFinanceInfo查询结果实体类的记录集
	 * @throws Exception
	 */
	public Collection match(TransReturnFinanceInfo info) throws Exception
	{

		ArrayList arrResult = new ArrayList();
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer buffer = null;

			/**
			 * 	融资租赁还款			基本信息：
			 * 承租单位编号						要不要，值得考虑一下
			 * 合同号ID				NCONTRACTID
			 * 还款通知单ID			NReturnFormID
			 * 
			 * 
			 * nReturnCorpusAccountID	NUMBER	Y	-1	还本金账户ID
			  nReturnCorpusBankID	NUMBER	Y	-1	还本金银行ID
			  mCorpusAmount	NUMBER(21,6)	Y	0	本次还本金金额
			  
			  
			  nReturnInterestAccountID	NUMBER	Y	-1	还利息账户ID
			  nReturnInterestBankID	NUMBER	Y	-1	还利息银行ID
			  mInterestAmount	NUMBER(21,6)	Y	0	本次还利息金额

			 nReturnBailAccountID	NUMBER	Y	-1	扣除保证金账户ID
			 mBailAmount	NUMBER(21,6)	Y	0	本次扣除保证金金额
			 
			 //mReturnSumAmount	NUMBER(21,6)	Y	0	本次还款总金额   考虑加不加了(*************) 
			 * 
			 * 当前交易状态(未复核),录入人不是操作者 
			 */
			
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.RETURNFINANCE) {
				buffer = new StringBuffer();
				buffer.append(" select * from Sett_TransReturnFinance \n");
				buffer.append(" where nOfficeID=? and nCurrencyID=? and nStatusID=? \n");
				buffer.append(" and nInputUserID <>? and NCONTRACTID=? and \n");
				buffer.append(" nReturnFormID=? and nReturnCorpusAccountID=? and nReturnCorpusBankID=? and \n");
				buffer.append(" mCorpusAmount=? and nReturnInterestAccountID=? and nReturnInterestBankID=? \n");
				buffer.append(" and mInterestAmount=? and nReturnBailAccountID=? and mBailAmount=? \n");
				buffer.append(" and dtExecute =? and dtInterestStart=? \n");
				buffer.append(" order by ID \n");

				ps = con.prepareStatement(buffer.toString());
				System.out.println(buffer.toString());

				int index = 1;
				ps.setLong(index++, info.getOfficeID());
				System.out.println("info.getOfficeID():" + info.getOfficeID());
				ps.setLong(index++, info.getCurrencyID());
				System.out.println("info.getCurrencyID():" + info.getCurrencyID());
				ps.setLong(index++, info.getStatusID());
				System.out.println("info.getStatusID():" + info.getStatusID());
				ps.setLong(index++, info.getInputUserID());
				System.out.println("info.getInputUserID():" + info.getInputUserID());
				ps.setLong(index++, info.getContractID());
				System.out.println("info.getContractID():" + info.getContractID());
				ps.setLong(index++, info.getReturnFormID());
				System.out.println("info.getReturnFormID():" + info.getReturnFormID());
				ps.setLong(index++, info.getReturnCorpusAccountID());
				System.out.println("info.getReturnCorpusAccountID():" + info.getReturnCorpusAccountID());
				ps.setLong(index++, info.getReturnCorpusBankID());
				System.out.println("info.getReturnCorpusBankID():" + info.getReturnCorpusBankID());
				ps.setDouble(index++, info.getCorpusAmount());
				System.out.println("info.getCorpusAmount():" + info.getCorpusAmount());
				ps.setLong(index++, info.getReturnInterestAccountID());
				System.out.println("info.getReturnInterestAccountID():" + info.getReturnInterestAccountID());
				ps.setLong(index++, info.getReturnInterestBankID());
				System.out.println("info.getReturnInterestBankID():" + info.getReturnInterestBankID());
				ps.setDouble(index++, info.getInterestAmount());
				System.out.println("info.getInterestAmount():" + info.getInterestAmount());
				ps.setLong(index++, info.getReturnBailAccountID());
				System.out.println("info.getReturnBailAccountID():" + info.getReturnBailAccountID());
				ps.setDouble(index++, info.getBailAmount());
				System.out.println("info.getBailAmount():" + info.getBailAmount());
				ps.setTimestamp(index++, info.getExecuteDate());
				System.out.println("info.getExecuteDate():" + info.getExecuteDate());
				ps.setTimestamp(index++, info.getInterestStartDate());
				System.out.println("info.getInterestStartDate():" + info.getInterestStartDate());
				
				rs = ps.executeQuery();
				while (rs.next()) {
					TransReturnFinanceInfo depositInfo = new TransReturnFinanceInfo();
					depositInfo = getReturnFinance(depositInfo, rs);	
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
	 * 融资租赁还款继续功能： 逻辑说明：
	 * 
	 * 根据合同ID和还款通知单ID查找相应的贷款合同信息及还款通知单信息
	 * 
	 * @param lID
	 *            long , 交易的ID
	 * @return TransMarginOpenInfo, 融资租赁还款交易实体类
	 * @throws Exception
	 * 	 * 刘洋修改 5-2
	 */
	public TransReturnFinanceInfo next(TransReturnFinanceInfo infoTemp) throws Exception
	{
		TransReturnFinanceInfo info = new TransReturnFinanceInfo();
		try {
			//合同
			if(infoTemp.getContractID()>0){
				System.out.println("取贷款合同信息-------------(开始)");
				ContractDao contractDao=new ContractDao();
				ContractInfo contractInfo=new ContractInfo();
				contractInfo=contractDao.findByID(infoTemp.getContractID());
				info.setContractID(infoTemp.getContractID());//合同ID
				info.setContractCode(contractInfo.getContractCode());//合同编号
				info.setContractFinanceClientID(contractInfo.getBorrowClientID());//承租单位客户ID
				info.setContractFinanceClientCode(contractInfo.getBorrowClientCode());//承租单位客户编号			
				info.setContractFinanceClientName(contractInfo.getBorrowClientName());//承租单位客户名称
				info.setContractFinanceStartDate(contractInfo.getLoanStart());//租赁开始日期
				info.setContractFinanceEndDate(contractInfo.getLoanEnd());//租赁开始日期
				info.setContractFinanceTerm(contractInfo.getIntervalNum());//租凭期限
				info.setContractFinanceRate(contractInfo.getRate());//租赁利率
				
				info.setContractBailAmount(contractInfo.getRecognizanceAmount());//合同融资租赁保证金金额
				info.setContractBailAmountForYS(contractInfo.getReturnedRecognizanceAmount());//已收融资租赁保证金金额	只读	显示此合同下已收取的融资租赁保证金金额
				info.setContractBailAmountForWS(contractInfo.getRecognizanceAmount()-contractInfo.getReturnedRecognizanceAmount());//未收融资租赁保证金金额	只读	融资租赁保证金金额-已收融资租赁保证金金额
				System.out.println("dafsfsafasdfasdfasdf");
				
				/*
				 * add by yunchang
				 * date 2010-08-19
				 * function 累计已收保证金金额（结算--贷款--还款通知单--业务处理）
				 */
				info.setContractHireAmountForYSALL(contractInfo.getContractHireAmountForYSALL());
				//增加的信息
				info.setContractHireAmount(contractInfo.getLoanAmount());//合同融资租赁租金金额
				//---------------
				//info.setContractHireAmountForYS(contractInfo.getCurrentRecognizanceAmount());//已收融资租赁租金金额	只读	显示此合同下已收取的融资租赁租金金额
				//-----------------
				//Modified by zwsun, 2007/8/18
				//double amountYS=findYSByContractId(info.getContractID(),infoTemp.getOfficeID(),infoTemp.getCurrencyID());
				//info.setContractHireAmountForYS(findYSByContractId(info.getContractID(),infoTemp.getOfficeID(),infoTemp.getCurrencyID()));
				double amountYS = findRZZLYSByContractId(info.getContractID(),infoTemp.getOfficeID(),infoTemp.getCurrencyID());
				info.setContractHireAmountForYS(amountYS);
				info.setContractHireAmountForWS(contractInfo.getLoanAmount()-amountYS);				
				//info.setContractHireAmountForWS(contractInfo.getLoanAmount()-contractInfo.getCurrentRecognizanceAmount());//未收融资租赁租金金额	只读	融资租赁租金金额-已收融资租赁租金金额
				System.out.println("取贷款合同信息-------------(结束)");
			}
			
			//还款单
			if(infoTemp.getReturnFormID()>0){
				System.out.println("取贷款还款通知单信息-------------(开始)");
				LoanLeaseholdRepayFormDao loanLeaseholdRepayFormDao=new LoanLeaseholdRepayFormDao();
				LeaseholdRepayNoticeInfo leaseholdRepayNoticeInfo=new LeaseholdRepayNoticeInfo();
				leaseholdRepayNoticeInfo=loanLeaseholdRepayFormDao.findRepayNoticeByID(infoTemp.getReturnFormID());
				info.setReturnFormID(infoTemp.getReturnFormID());
				info.setReceiveFormCode(leaseholdRepayNoticeInfo.getCode());//还款通知单编号	只读	还款通知单信息
				info.setReceiveFormDate(leaseholdRepayNoticeInfo.getInputDate());//还款日期	只读	还款通知单信息
				
				//得到其它的一些信息  辅助的信息
				info.setInterestAmount(leaseholdRepayNoticeInfo.getInterestAmount());//还利息金额
				System.out.println("==还款利息金额为:"+info.getInterestAmount());	
				info.setCorpusAmount(leaseholdRepayNoticeInfo.getAmount());//还租金金额（本金）
				System.out.println("==还款租金金额为:"+info.getCorpusAmount());
				info.setBailAmount(leaseholdRepayNoticeInfo.getRecognizanceAmount());//扣保证金金额
				System.out.println("==还款扣保证金金额为:"+info.getBailAmount());
				info.setReturnBailAccountID(leaseholdRepayNoticeInfo.getRecognizanceAccountId());//扣保证金的账户
				System.out.println("==还款扣保证金的账户为:"+info.getReturnBailAccountID());
				
				//还款时必须得到收款通知单的ID
				info.setReceiveFormID(leaseholdRepayNoticeInfo.getAssureChargeFormID());    //add by feiye 2006.5.24
				
				System.out.println("取贷款还款通知单信息-------------(结束)");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			
		}
		return info;
	}
	
	/**
	 * Added by zwsun ,2007/8/18
	 * 计算已收金额
	 */
	public double findRZZLYSByContractId(long lContractId,long officeId,long currencyId) throws Exception {
		double mAmout=0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		Connection con = null;

		try {
			con = Database.getConnection();
			strSQL = "select sum(mcorpusamount) from sett_transreturnfinance where ncontractId="+lContractId
				+ " and nofficeId="+ officeId 
				+ " and ncurrencyId=" + currencyId
				+ " and nstatusId= 3" ;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				mAmout = rs.getDouble(1);
			}				
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(con!=null)
				con.close();
		}
		return mAmout;
	}	
	
	/**
	 * Added by zwsun ,2007/8/18
	 * 计算已收金额
	 */
	public double findYSByContractId(long lContractId, long officeId, long currencyId) throws Exception {
		double mAmout=0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		Connection con = null;

		try {
			con = Database.getConnection();
			strSQL = "select sum(amount) from loan_leaseholdrepayform where contractId="+lContractId
				+ " and officeId="+ officeId 
				+ " and currencyId=" + currencyId
				+ " and nstatusId=" + LOANConstant.LoanPayNoticeStatus.USED;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				mAmout = rs.getDouble(1);
			}				
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(con!=null)
				con.close();
		}
		return mAmout;
	}	
	
	
	/**
	 * 检查还款通知单的状态是否正常
	 * @param lPayFormID
	 * @return
	 * @throws SQLException
	 */
	public boolean checkPayForm(long lRetrunFormID,long lStatusToCheck)throws SQLException{
		boolean blnIsOK=false;
		Connection conn=null;
		try{
			Log.print("校验放款通知单状态......");
			Log.print("放款通知单ID:"+lRetrunFormID);
			conn = this.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String strSql = "select nstatusid from LOAN_LEASEHOLDREPAYFORM where id="+lRetrunFormID
				+" and nstatusid="+lStatusToCheck;
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			if (rs.next()){
				blnIsOK = true;
			}
			this.cleanup(rs);
			this.cleanup(ps);
		}catch(SQLException sqlExp){throw sqlExp;}
		finally{
			this.cleanup(conn);
		}
		return blnIsOK;
	}

	public void updateLoanReturnFormStatus(long id, long statusID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement("update " + "LOAN_LEASEHOLDREPAYFORM set nStatusId=? where id=?");
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
	
	
	public static void main(String []args){
	
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		ArrayList arrResult = new ArrayList();
		
		TransReturnFinanceInfo info=new TransReturnFinanceInfo();
		TransReturnFinanceInfo aa=new TransReturnFinanceInfo();
		
		
		try {
			aa=dao.next(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(aa!=null ){
			System.out.println("不为空");
		}else{
			System.out.println("为空");
		}
		
		
		/*
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();

		TransReturnFinanceInfo info=new TransReturnFinanceInfo();
		info.setTransactionTypeID(SETTConstant.TransactionType.RECEIVEFINANCE);
		
		Collection c=null;
		try {
			c = dao.match(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(c==null){
			System.out.println("得到的数据为空!");
			
		}else{
			System.out.println("得到的数据不为空!"+c.size());
			
		}
		
		
				
		
		ArrayList arrResult = new ArrayList();
		
		QueryByStatusConditionInfo info=new QueryByStatusConditionInfo();
		info.setCurrencyID(1);
		info.setOfficeID(1);
		info.setTransactionTypeID(401);
		long status[]={1,2};
		info.setStatus(status);
		info.setTypeID(0);
		info.setUserID(1);

				try {
			arrResult=(ArrayList)dao.next(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(arrResult!=null ){
			System.out.println("不为空");
		}else{
			System.out.println("为空");
		}
		*/
	}
	
	
	/**
	 * @author afzhu
	 * @param queryEntity 查询实体
	 * @return
	 */
	public List getFinanceRentList(TransQueryFinanceNewInfo queryEntity) throws Exception
	{
		StringBuffer sql=new StringBuffer("select distinct v.*,(select sname from userinfo where id = c.ninputuserid) as sname,c.dtinputdate,(select saccountno from sett_account where id=v.collectionrentaccountid) as currentAccount,(select saccountno from sett_account where id=v.marginAccountID) as marginAccount  from SETT_VTRANSRETURNFINANCE v,loan_contractform c");
		//String sql="select distinct v.*,(select sname from userinfo where id = c.ninputuserid) as sname,c.dtinputdate,(select saccountno from sett_account where id=v.currentAccountID) as currentAccount,(select saccountno from sett_account where id=v.marginAccountID) as marginAccount  from SETT_VTRANSRETURNFINANCE v,loan_contractform c where v.subtypeID=100 and v.contractID=c.id and  v.planDate<=to_date('2030-06-12','yyyy-MM-dd') and v.ntypeid=10 and v.nstatusid=5 and v.nofficeid=1 and v.ncurrencyid=3";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res=null;
		List returnList=new ArrayList();
		TransReturnFinanceNewInfo returnEntity = null;
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		try
		{
			conn = this.getConnection();
			//如果开始合同号为空，结束合同号不为空的情况下
			
			if((queryEntity.getContractIDFrom()==null || "".equals(queryEntity.getContractIDFrom())) && (queryEntity.getContractIDTo()!=null && !"".equals(queryEntity.getContractIDTo())))
			{
				sql.append(" where v.subtypeID=? and v.contractID=c.id and (v.contractCode<=?) and v.planDate<=to_date(?,'yyyy-MM-dd') and v.ntypeid=? and v.nstatusid=? and v.nofficeid=? and v.ncurrencyid=? order by v.contractID,v.issue");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, queryEntity.getLoanType());
				pstmt.setString(2, queryEntity.getContractIDTo());
				pstmt.setString(3, queryEntity.getExecuteDate());
				pstmt.setLong(4, queryEntity.getNtypeid());//贷款类型ID
				pstmt.setLong(5, queryEntity.getNstatusid());//合同状态id
				pstmt.setLong(6, queryEntity.getNofficeid());//办事处ID
				pstmt.setLong(7, queryEntity.getNcurrencyid());//币种ID
			}
			//如果开始合同号不为空，结束合同号为空的情况下
			else if((queryEntity.getContractIDFrom()!=null && !"".equals(queryEntity.getContractIDFrom())) && (queryEntity.getContractIDTo()==null || "".equals(queryEntity.getContractIDTo())))
			{
				sql.append(" where v.subtypeID=? and v.contractID=c.id and (v.contractCode>=?) and v.planDate<=to_date(?,'yyyy-MM-dd')	and v.ntypeid=? and v.nstatusid=? and v.nofficeid=? and v.ncurrencyid=? order by v.contractID,v.issue");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, queryEntity.getLoanType());
				pstmt.setString(2, queryEntity.getContractIDFrom());
				pstmt.setString(3, queryEntity.getExecuteDate());
				pstmt.setLong(4, queryEntity.getNtypeid());//贷款类型ID
				pstmt.setLong(5, queryEntity.getNstatusid());//合同状态id
				pstmt.setLong(6, queryEntity.getNofficeid());//办事处ID
				pstmt.setLong(7, queryEntity.getNcurrencyid());//币种ID
			}
			//如果两个合同号都不为空的情况下
			else if((queryEntity.getContractIDFrom()!=null && !"".equals(queryEntity.getContractIDFrom())) && (queryEntity.getContractIDTo()!=null && !"".equals(queryEntity.getContractIDTo())))
			{
				sql.append(" where v.subtypeID=? and v.contractID=c.id and (v.contractCode>=? and v.contractCode<=?) and v.planDate<=to_date(?,'yyyy-MM-dd')  and v.ntypeid=? and v.nstatusid=? and v.nofficeid=? and v.ncurrencyid=? order by v.contractID,v.issue");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, queryEntity.getLoanType());
				pstmt.setString(2, queryEntity.getContractIDFrom());
				pstmt.setString(3, queryEntity.getContractIDTo());
				pstmt.setString(4, queryEntity.getExecuteDate());
				pstmt.setLong(5, queryEntity.getNtypeid());//贷款类型ID
				pstmt.setLong(6, queryEntity.getNstatusid());//合同状态id
				pstmt.setLong(7, queryEntity.getNofficeid());//办事处ID
				pstmt.setLong(8, queryEntity.getNcurrencyid());//币种ID
			}
			//如果两个合同号都为空的情况下
			else
			{
				sql.append(" where v.subtypeID=? and v.contractID=c.id and  v.planDate<=to_date(?,'yyyy-MM-dd') and v.ntypeid=? and v.nstatusid=? and v.nofficeid=? and v.ncurrencyid=? order by v.contractID,v.issue");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, queryEntity.getLoanType());
				pstmt.setString(2, queryEntity.getExecuteDate());
				pstmt.setLong(3, queryEntity.getNtypeid());//贷款类型ID
				pstmt.setLong(4, queryEntity.getNstatusid());//合同状态id
				pstmt.setLong(5, queryEntity.getNofficeid());//办事处ID
				pstmt.setLong(6, queryEntity.getNcurrencyid());//币种ID
			}
			//pstmt = conn.prepareStatement(sql.toString());
			System.out.println("==========================="+sql.toString()+"=============================");
			res=pstmt.executeQuery();
			while(res.next())
			{
				returnEntity=new TransReturnFinanceNewInfo();
				returnEntity.setClientCode(res.getString("ClientCode"));//客户编号
				returnEntity.setClientName(res.getString("ClientName"));//客户名称
				returnEntity.setContractCode(res.getString("ContractCode"));//合同编号
				returnEntity.setContractID(res.getInt("ContractID"));//合同ID
				returnEntity.setCurrentAccountID(res.getInt("collectionrentaccountid"));//活期账户ID
				returnEntity.setCurrentPaymentAmount(Double.parseDouble(df.format(res.getDouble("CurrentPaymentAmount"))));//本期还款金额
				returnEntity.setEndDate(res.getDate("EndDate"));//结束时间
				returnEntity.setInterest(Double.parseDouble(df.format(res.getDouble("Interest"))));//利息
				returnEntity.setIssue(res.getInt("Issue"));//期数
				returnEntity.setMarginAccountID(res.getInt("MarginAccountID"));//保证金账户ID
				returnEntity.setPlanDate(res.getDate("PlanDate"));//还款日期
				returnEntity.setPrincipal(Double.parseDouble(df.format(res.getDouble("Principal"))));//本金
				returnEntity.setRate(res.getDouble("Rate"));//还款利率
				//returnEntity.setRepaymentBalance(res.getDouble("RepaymentBalance"));//还款余额
				returnEntity.setStartDate(res.getDate("StartDate"));//开始时间
				returnEntity.setSubtypeID(res.getInt("SubtypeID"));//贷款子类型id
				returnEntity.setSname(res.getString("sname"));//生成人
				returnEntity.setDtinputdate(res.getDate("dtinputdate"));//生成时间
				returnEntity.setCurrentAccount(res.getString("CurrentAccount"));//活期账户
				returnEntity.setMarginAccount(res.getString("MarginAccount"));//保证金账户
				returnEntity.setMexamineamount(Double.parseDouble(df.format(res.getDouble("mexamineamount"))));//批准金额
				returnEntity.setNofficeid(res.getLong("nofficeid"));//办事处ID
				returnEntity.setNcurrencyid(res.getLong("ncurrencyid"));//币种
				returnEntity.setNstatusid(res.getLong("nstatusid"));//合同状态ID
				returnEntity.setNtypeid(res.getLong("ntypeid"));//贷款类型ID
				returnEntity.setContractDetailID(res.getInt("contractdetailID"));//合同明细ID
				//add by zwxiao 2010-08-08 修改还款余额的显示问题,先将其初始化为合同金额，再在下面进行计算
				returnEntity.setRepaymentBalance(returnEntity.getMexamineamount());
				returnList.add(returnEntity);
			}
			
			
		}
		catch(Exception  e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(res);
				this.cleanup(pstmt);
				this.cleanup(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return returnList;
	}
	
	/**
	 * @author afzhu
	 * @param contractId 合同ID数组
	 * @return
	 */
	public List getFinanceRentListByContractId(int contractId[],String exeDate) throws Exception
	{
		Connection conn = null;//匡算实体连接
		PreparedStatement pstmt = null;
		ResultSet res=null;//匡算实体查询结果集
		//StringBuffer sql = new StringBuffer("select distinct v.*,(select sname from userinfo where id = c.ninputuserid) as sname,c.dtinputdate,(select saccountno from sett_account where id=v.currentAccountID) as currentAccount,(select saccountno from sett_account where id=v.marginAccountID) as marginAccount  from SETT_VTRANSRETURNFINANCE v,Sett_TransReturnFinance t,loan_contractform c where v.contractID=c.id   and v.contractID=t.NCONTRACTID and v.issue not in(select issue from Sett_TransReturnFinance where ncontractid=v.contractID ) and c.id in (");
		StringBuffer sql = new StringBuffer("select * from SETT_VTRANSRETURNFINANCE v where v.issue not in(select issue from Sett_TransReturnFinance where ncontractid=v.contractID ) and v.planDate<=to_date(?,'yyyy-MM-dd')  and v.contractID in (");
		String param="";
		List returnList=new ArrayList();
		TransReturnFinanceNewInfo returnEntity = null;
		try
		{
			conn=this.getConnection();
			for(int i=0;i<contractId.length;i++)
			{
				param=param+"?";
				if(i!=contractId.length-1)
				{
					param=param+",";
				}
				else
				{
					param=param+")";
					
				}
			}
			sql.append(param);
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,exeDate);
			for(int j=1;j<=contractId.length;j++)
			{
				pstmt.setInt(j+1,contractId[j-1]);
			}
			res=pstmt.executeQuery();
			while(res.next())
			{
				returnEntity=new TransReturnFinanceNewInfo();
				returnEntity.setClientCode(res.getString("ClientCode"));//客户编号
				returnEntity.setClientName(res.getString("ClientName"));//客户名称
				returnEntity.setContractCode(res.getString("ContractCode"));//合同编号
				returnEntity.setContractID(res.getInt("ContractID"));//合同ID
				returnEntity.setCurrentAccountID(res.getInt("collectionrentaccountid"));//活期账户ID
				returnEntity.setCurrentPaymentAmount(res.getDouble("CurrentPaymentAmount"));//本期还款金额
				returnEntity.setEndDate(res.getDate("EndDate"));//结束时间
				returnEntity.setInterest(res.getDouble("Interest"));//利息
				returnEntity.setIssue(res.getInt("Issue"));//期数
				returnEntity.setMarginAccountID(res.getInt("MarginAccountID"));//保证金账户ID
				returnEntity.setPlanDate(res.getDate("PlanDate"));//还款日期
				returnEntity.setPrincipal(res.getDouble("Principal"));//本金
				returnEntity.setRate(res.getDouble("Rate"));//还款利率
				//returnEntity.setRepaymentBalance(res.getDouble("RepaymentBalance"));//还款余额
				returnEntity.setStartDate(res.getDate("StartDate"));//开始时间
				returnEntity.setSubtypeID(res.getInt("SubtypeID"));//贷款子类型id
				returnEntity.setMexamineamount(res.getDouble("mexamineamount"));//批准金额
				//returnEntity.setSname(res.getString("sname"));//生成人
				//returnEntity.setDtinputdate(res.getDate("dtinputdate"));//生成时间
				//returnEntity.setCurrentAccount(res.getString("CurrentAccount"));//活期账户
				//returnEntity.setMarginAccount(res.getString("MarginAccount"));//保证金账户
				returnList.add(returnEntity);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(res);
				this.cleanup(pstmt);
				this.cleanup(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return returnList;
	}
	/**
	 * @author afzhu
	 * @return
	 * 查询合同打印最大流水号
	 */
	public TransReturnFinanceNewInfo.SubReturnFinance getContractNum() throws Exception 
	{
		Connection conn = null;//合同打印流水号连接
		PreparedStatement pstmt = null;
		ResultSet res=null;//合同打印最大流水号查询结果
		String countsql="select * from rentpayinformdedetail where id=(select max(id) from rentpayinformdedetail)";
		int contractNum =0;
		TransReturnFinanceNewInfo.SubReturnFinance sub=new TransReturnFinanceNewInfo().new SubReturnFinance();//内部类--租金支付通知书和租金催收通知书实体
		try{
			conn=this.getConnection();
			pstmt = conn.prepareStatement(countsql);
			res=pstmt.executeQuery();
			while(res.next())
			{
				sub.setId(res.getInt("id"));
				sub.setContractId(res.getInt("CONTRACTID"));//合同ID
				sub.setContractNum(res.getString("CONTRACTNUM"));//合同编号
				sub.setContractYear(res.getDate("CONTRACTYEAR"));//合同年份
				sub.setNum(res.getInt("NUM"));//合同打印流水号
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(res);
				this.cleanup(pstmt);
				this.cleanup(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return sub;
	}
	
	/**
	 * @author afzhu
	 * @param sub 存储合同流水号实体
	 * @throws Exception
	 */
	public void saveFinance(TransReturnFinanceNewInfo.SubReturnFinance sub) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res=null;
		String sql = "insert into rentpayinformdedetail values((select max(id)+1 from rentpayinformdedetail),?,?,to_date(?,'yyyy'),?)";
		try{
			conn=this.getConnection();
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1,sub.getId()+1);
			pstmt.setInt(1, sub.getContractId());
			pstmt.setString(2,sub.getContractNum().trim());
			pstmt.setString(3,sub.getScontractYear().trim());
			pstmt.setInt(4,sub.getNum());
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(res);
				this.cleanup(pstmt);
				this.cleanup(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * @author 		afzhu
	 * @param 		contractids 合同号数组
	 * @return 		每笔合同的还款额
	 * @modify 	 	yunchang
	 * @date 		2010-08-11
	 * @function 	解决当没有任何一笔还款时的情况
	 */
	public List getRepaymentBalance(long contractids[]) throws Exception
	{
		Log.print("==========================================================================================");
		Log.print("进入方法：Sett_TransReturnFinanceDao.getRepaymentBalance(long contractid,String kuangSuanDate):");
		Log.print("==========================================================================================");		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet=null;
		StringBuffer sqlStringBuffer = new StringBuffer();
		List returnList=new ArrayList();		
		
		try
		{
			sqlStringBuffer.append(" select b.id as ncontractid, nvl(amountfrom, 0) as amountfrom from (select ncontractid, sum(mcorpusamount) as amountfrom ");
			sqlStringBuffer.append(" from sett_transreturnfinance where nstatusid = " + SETTConstant.TransactionStatus.CHECK + " group by ncontractid) a, loan_contractform b");
			sqlStringBuffer.append(" where a.ncontractid(+) = b.id and b.id in (");
			
			connection = this.getConnection();
			String param="";
			TransReturnFinanceNewInfo returnEntity = null;
		
			for(int i=0;i<contractids.length;i++)
			{
				param=param+"?";
				if(i!=contractids.length-1)
				{
					param=param+",";
				}
				else
				{
					param=param+")";
					
				}
			}
			sqlStringBuffer.append(param);
			preparedStatement = connection.prepareStatement(sqlStringBuffer.toString());
			for(int j=1;j<=contractids.length;j++)
			{
				preparedStatement.setLong(j,contractids[j-1]);
			}
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				returnEntity=new TransReturnFinanceNewInfo();
				returnEntity.setContractID(resultSet.getInt("ncontractid"));//合同ID
				returnEntity.setAmountfrom(resultSet.getDouble("amountfrom"));//已还款总额数
				returnList.add(returnEntity);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try 
			{	
				this.cleanup(resultSet);
				this.cleanup(preparedStatement);
				this.cleanup(connection);
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return returnList;
	}
	/*public TransReturnFinanceNewInfo checkAccount(TransReturnFinanceNewInfo trfn,String balanceType) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res=null;
		String sql="select sa.nstatusid,sb.mbalance,sc.naccountgroupid  from sett_account sa,sett_subaccount sb,Sett_AccountType sc where sa.id=sb.naccountid and sa.id in (?,?) and sa.naccounttypeid=sc.id order by sb.id desc";
		try
		{
			conn=this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, trfn.getCurrentAccountID());
			pstmt.setInt(2, trfn.getMarginAccountID());
			res=pstmt.executeQuery();
			if(LOANConstant.BalanceType.CURRENTACCOUNT == Integer.parseInt(balanceType))//如果结算方式是活期账户
			{
				while(res.next())
				{
					if(SETTConstant.AccountGroupType.CURRENT == res.getInt("naccountgroupid"))//如果为活期账户
					{
						
						if(res.getInt("nstatusid") != SETTConstant.AccountStatus.NORMAL)//如果账户不是正常的
						{
							trfn.setRemark("失败:"+SETTConstant.AccountStatus.getName(res.getInt("nstatusid")));
							trfn.setFlag(-1);//设置该记录状态为失败
							break;
						}
						else //如果账户是正常的,判断账户余额是否够扣
						{
							if(res.getInt("mbalance")-trfn.getCurrentPaymentAmount() < 0)
							{
								trfn.setRemark("失败:账户余额不足");
								trfn.setFlag(-1);//设置该记录状态为失败
							}
						}
					}
				}
			}
			else if(LOANConstant.BalanceType.FIRST_CURRENTACCOUNT_LATE_RECOGNIZANCEACCOUNT == Integer.parseInt(balanceType))//如果结算方式是先活期后保证金
			{
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(pstmt);
				this.cleanup(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return trfn;
	}
	*/
	/**
	 * 批量还款----链接查找---设置累计迟付
	 * @author afzhu
	 * @param transNo
	 * @throws Exception
	 */
	public void updateLjcf(String transNo[]) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer sql= new StringBuffer("update Sett_TransReturnFinance set ISDELAYPAYMENT=1 where stransno in( ");
		String param="";
		try{
			for(int i=0;i<transNo.length;i++)
			{
				param=param+"?";
				if(i!=transNo.length-1)
				{
					param=param+",";
				}
				else
				{
					param=param+")";
					
				}
			}
			sql.append(param);
			conn=this.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			for(int j=1;j<=transNo.length;j++)
			{
				pstmt.setString(j,transNo[j-1]);
			}
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(pstmt);
				this.cleanup(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public List checkAccount(List trfn_list,String balanceType) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res=null;
		TransReturnFinanceNewInfo trfn = null;
		List returnList = new ArrayList();
		double countPayment = 0;
		//String sql="select sa.nstatusid,sb.mbalance,sc.naccountgroupid  from sett_account sa,sett_subaccount sb,Sett_AccountType sc where sa.id=sb.naccountid and sa.id in (?,?) and sa.naccounttypeid=sc.id order by sb.id desc";
		//包含对方账户的sql语句
		//String sql="select sa.nstatusid,sb.mbalance,sc.naccountgroupid,sb.id as subaccountid,(select distinct la.collectionrentaccountid from loan_assurechargeform la where la.contractid=?) as collectionrentaccountid ,(select sat.saccountno  from sett_account sat where sat.id=(select distinct la.collectionrentaccountid from loan_assurechargeform la where la.contractid=?)) as collectionrentaccountcode,(select ssat.id from sett_subaccount ssat where ssat.naccountid = (select distinct la.collectionrentaccountid from loan_assurechargeform la where la.contractid=?)) as collectionrentsubaccountid,(select SNAME from client where id = (select sat.NCLIENTID from sett_account sat  where sat.id = (select distinct la.collectionrentaccountid  from loan_assurechargeform la where la.contractid = ?))) as collectionrentaccountName  from sett_account sa,sett_subaccount sb,Sett_AccountType sc where sa.id=sb.naccountid and sa.id in (?,?) and sa.naccounttypeid=sc.id order by sb.id desc";
		//String sql="select sa.nstatusid,sb.mbalance,sc.naccountgroupid,sb.id as subaccountid  from sett_account sa,sett_subaccount sb,Sett_AccountType sc where sa.id=sb.naccountid and sa.id in (?,?) and sa.naccounttypeid=sc.id order by sb.id desc";
		StringBuffer sql =new StringBuffer();
					/* sql.append("select * from ");	
					 sql.append("((select d.id as subaccountid, d.al_nloannoteid, d.nstatusid, ");	
					 sql.append("(case when d.AC_NFIRSTLIMITTYPEID>0 then ");
					 sql.append("(d.mbalance - d.MUNCHECKPAYMENTAMOUNT + ");
					 sql.append("d.AC_MFIRSTLIMITAMOUNT-d.AC_MCAPITALLIMITAMOUNT) ");
					 sql.append("when d.AC_NSECONDLIMITTYPEID>0 then ");
					 sql.append("(d.mbalance - d.MUNCHECKPAYMENTAMOUNT + ");
					 sql.append("d.AC_MSECONDLIMITAMOUNT-d.AC_MCAPITALLIMITAMOUNT) ");
					 sql.append("when d.AC_NTHIRDLIMITTYPEID>0 then ");
					 sql.append("(d.mbalance - d.MUNCHECKPAYMENTAMOUNT + ");
					 sql.append("d.AC_MTHIRDLIMITAMOUNT-d.AC_MCAPITALLIMITAMOUNT) ");
					 sql.append(" else (d.mbalance - d.MUNCHECKPAYMENTAMOUNT - d.AC_MCAPITALLIMITAMOUNT)");
					 sql.append("end)  as mbalance ,");
					 sql.append(" e.naccountgroupid ");
					 sql.append("from sett_account c, sett_subaccount d, Sett_AccountType e ");	
					 sql.append("where c.id = d.naccountid and d.nstatusid>0 ");	
					 sql.append("and c.naccounttypeid = e.id ");	
					 sql.append("and c.id in (?) ");	
					 sql.append("union ");	
					 sql.append("select d.id as subaccountid, d.al_nloannoteid, d.nstatusid, ");	
					 sql.append("(case when d.AC_NFIRSTLIMITTYPEID>0 then ");
					 sql.append("(d.mbalance - d.MUNCHECKPAYMENTAMOUNT + ");
					 sql.append("d.AC_MFIRSTLIMITAMOUNT-d.AC_MCAPITALLIMITAMOUNT) ");
					 sql.append("when d.AC_NSECONDLIMITTYPEID>0 then ");
					 sql.append("(d.mbalance - d.MUNCHECKPAYMENTAMOUNT + ");
					 sql.append("d.AC_MSECONDLIMITAMOUNT-d.AC_MCAPITALLIMITAMOUNT) ");
					 sql.append("when d.AC_NTHIRDLIMITTYPEID>0 then ");
					 sql.append("(d.mbalance - d.MUNCHECKPAYMENTAMOUNT + ");
					 sql.append("d.AC_MTHIRDLIMITAMOUNT-d.AC_MCAPITALLIMITAMOUNT) ");
					 sql.append(" else (d.mbalance - d.MUNCHECKPAYMENTAMOUNT - d.AC_MCAPITALLIMITAMOUNT)");
					 sql.append("end)  as mbalance ,");
					 sql.append(" e.naccountgroupid ");
					 sql.append("from loan_contractform a, loan_assurechargeform b,sett_account c, sett_subaccount d, Sett_AccountType e ");	
					 sql.append("where 1=1  ");	
					 sql.append("and b.id = d.al_nloannoteid and d.nstatusid>0 ");	
					 sql.append("and a.id = b.contractid ");	
					 sql.append("and c.id = d.naccountid ");	
					 sql.append("and c.naccounttypeid = e.id ");	
					 sql.append("and a.id = ? ");	
					 sql.append("and c.id in (?))) aa ");	
					 sql.append("order by aa.subaccountid ");	*/
					 sql.append("select * from ");	
					 sql.append("((select d.id as subaccountid, d.al_nloannoteid, d.nstatusid, (d.mbalance-d.MUNCHECKPAYMENTAMOUNT+d.AC_MFIRSTLIMITAMOUNT) as mbalance, e.naccountgroupid ");	
					 sql.append("from sett_account c, sett_subaccount d, Sett_AccountType e ");	
					 sql.append("where c.id = d.naccountid and d.nstatusid>0 ");	
					 sql.append("and c.naccounttypeid = e.id ");	
					 sql.append("and c.id in (?) ");	
					 sql.append("union ");	
					 sql.append("select d.id as subaccountid, d.al_nloannoteid, d.nstatusid, (d.mbalance-d.MUNCHECKPAYMENTAMOUNT+d.AC_MFIRSTLIMITAMOUNT) as mbalance, e.naccountgroupid ");	
					 sql.append("from loan_contractform a, loan_assurechargeform b,sett_account c, sett_subaccount d, Sett_AccountType e ");	
					 sql.append("where 1=1  ");	
					 sql.append("and b.id = d.al_nloannoteid and d.nstatusid>0 ");	
					 sql.append("and a.id = b.contractid ");	
					 sql.append("and c.id = d.naccountid ");	
					 sql.append("and c.naccounttypeid = e.id ");	
					 sql.append("and a.id = ? ");	
					 sql.append("and c.id in (?))) aa ");	
					 sql.append("order by aa.subaccountid ");	
		try
		{
			trfn = (TransReturnFinanceNewInfo) trfn_list.get(0);
			conn=this.getConnection();
			pstmt = conn.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pstmt.setInt(1, trfn.getCurrentAccountID());
			pstmt.setInt(2, trfn.getContractID());
			pstmt.setInt(3, trfn.getMarginAccountID());
			res=pstmt.executeQuery();
			if(LOANConstant.BalanceType.CURRENTACCOUNT == Integer.parseInt(balanceType))//如果结算方式是活期账户
			{
				while(res.next())
				{
					if(SETTConstant.AccountGroupType.CURRENT == res.getInt("naccountgroupid"))//如果为活期账户
					{
						
						for(int i=0;i<trfn_list.size();i++)
						{
							trfn = (TransReturnFinanceNewInfo) trfn_list.get(i);
							trfn.setFlag(0);//默认设置为成功
							trfn.setTransSubAccountID(res.getInt("subaccountid"));//交易子账户ID
							trfn.setAccountOperation("{count:1,detail:"+res.getInt("subaccountid")+"-"+trfn.getCurrentPaymentAmount()+"-1}");//设置账户操作明细
							//if(i==0)trfn.setCurrentPaymentAmount(1700359790);
							
							//add by zwxiao 2010-07-06 加上合同是否做了保后处理通知单的判断
							if(checkContract(trfn.getContractID())){
								trfn.setRemark("失败:该合同已经保后处理过了,不能再继续进行还款,请检查！");
								trfn.setFlag(-1);//设置该记录状态为失败
							}else{
								if(res.getInt("nstatusid") != SETTConstant.AccountStatus.NORMAL)//如果账户不是正常的
								{
									trfn.setRemark("失败:"+SETTConstant.AccountStatus.getName(res.getInt("nstatusid")));
									trfn.setFlag(-1);//设置该记录状态为失败
								}
								else //如果账户是正常的,判断账户余额是否够扣
								{
									countPayment +=trfn.getCurrentPaymentAmount();//把一组的每期还款余额加在一起
									if(res.getDouble("mbalance")-countPayment < 0)
									{
										trfn.setRemark("失败:活期账户余额不足");
										trfn.setFlag(-1);//设置该记录状态为失败
									}
								
								}
							}
							returnList.add(trfn);
						}
					}
				}
			}
			else if(LOANConstant.BalanceType.FIRST_CURRENTACCOUNT_LATE_RECOGNIZANCEACCOUNT == Integer.parseInt(balanceType))//如果结算方式是先活期后保证金
			{
			
				int flag = 0;//标识变量(扣款账户类型)0为扣活期账户,1为扣保证金账户
				double currentAccountMoneyBalance = 0;//活期账户不够扣时的剩下的还款余额
				double marginAccountTotalBalance = 0;//保证金账户总余额
				int detailcount = 0;//账户操作明细总数
				String detail = "";//账户操作明细操作累计
				Map accountBalance = new TreeMap();
				while(res.next())//循环累加保证金账户余额
				{
					if(SETTConstant.AccountGroupType.MARGIN == res.getInt("naccountgroupid"))//如果为保证金账户
					{
						accountBalance.put(new Integer(res.getInt("subaccountid")), new Double(res.getDouble("mbalance")));//保证金账户ID做key,保证金账户余额做value
						marginAccountTotalBalance+=res.getDouble("mbalance");
					}
				}
				res.beforeFirst();
				lable:
				while(res.next())
				{
					
					if(SETTConstant.AccountGroupType.CURRENT == res.getInt("naccountgroupid") && flag==0)//如果为活期账户
					{
						
						for(int i=0;i<trfn_list.size();i++)
						{
							trfn = (TransReturnFinanceNewInfo) trfn_list.get(i);
							trfn.setFlag(0);//默认设置为结算成功
							trfn.setTransSubAccountID(res.getInt("subaccountid"));//交易子账户ID
							//trfn.setAccountOperation("{count:1,detail:"+res.getInt("subaccountid")+"-"+trfn.getCurrentPaymentAmount()+"}");//设置账户操作明细
							//trfn.setCurrentPaymentAmount(1600356590);
							if(res.getInt("nstatusid") != SETTConstant.AccountStatus.NORMAL)//如果账户不是正常的
							{
								res.beforeFirst();//如果活期账户不正常,那把结果集游标返回开始
								flag=1;//把扣款类型改为保证金账户扣款
								break;
							}
							else //如果账户是正常的,判断账户余额是否够扣
							{
								countPayment +=trfn.getCurrentPaymentAmount();//把一组的每期还款余额加在一起
								if(res.getDouble("mbalance")-countPayment < 0)
								{
									currentAccountMoneyBalance = countPayment - res.getDouble("mbalance");//把活期账户扣款后剩下的余额计算出
									detailcount = 1;
									detail = res.getInt("subaccountid")+"-"+(trfn.getCurrentPaymentAmount()-currentAccountMoneyBalance)+"-1";
									res.beforeFirst();//如果活期账户不够扣,那把结果集游标返回开始
									flag=1;//把扣款类型改为保证金账户扣款
									continue lable;
								}
								else//如果活期账户够扣款
								{
									trfn.setAccountOperation("{count:1,detail:"+res.getInt("subaccountid")+"-"+trfn.getCurrentPaymentAmount()+"-1}");//设置账户操作明细
									trfn_list.remove(i);//把已经成功用活期账户扣款的记录删除,防止用保证金账户扣款时重复操作
									i=i-1;
								}
							}
							returnList.add(trfn);
						}
					 }
					if(SETTConstant.AccountGroupType.MARGIN == res.getInt("naccountgroupid")&& flag==1)//如果为保证金账户
					{
						
						double currentmarginAccountBalance = 0;//当前保证金账户余额(也就是每一条保证金账户余额)
						int currentmarginAccountId=0;//当前保证金账户ID
						int go_on = 0;//判断一组当中的后续记录是否还需还款,0为用-1为不用
						for(int i=0;i<trfn_list.size();i++)
						{
							trfn = (TransReturnFinanceNewInfo) trfn_list.get(i);
							trfn.setFlag(0);//默认设置为结算成功
							trfn.setTransSubAccountID(res.getInt("subaccountid"));//交易子账户ID
							//add by zwxiao 2010-07-06 加上合同是否做了保后处理通知单的判断
							if(checkContract(trfn.getContractID())){
								trfn.setRemark("失败:该合同已经保后处理过了,不能再继续进行还款,请检查！");
								trfn.setFlag(-1);//设置该记录状态为失败
							}else{
								if(go_on==-1)
								{
									trfn.setRemark("失败:保证金账户余额不足");
									trfn.setFlag(-1);
									returnList.add(trfn);
									continue;
								}
								if(res.getInt("nstatusid") !=  SETTConstant.AccountStatus.NORMAL)//如果保证金账户状态为正常
								{
									//trfn.setRemark("失败:保证金账户"+SETTConstant.AccountStatus.getName(res.getInt("nstatusid")));
									trfn.setRemark("失败:保证金账户为不正常");
									trfn.setFlag(-1);//设置该记录状态为失败
								}
								else
								{
									
									if(currentAccountMoneyBalance!=0)//判断活期账户是否扣过款,不为0证明存在活期账户扣款余下的金额,在保证金里还要继续扣
									{
										if(marginAccountTotalBalance - currentAccountMoneyBalance<0)
										{
											trfn.setRemark("失败:保证金账户余额不足");
											trfn.setFlag(-1);//设置该记录状态为失败
											go_on=-1;//如果保证金账户总余额扣取当前的款不足的话,那么后续的记录也不用判断了,直接就为失败
										}
										else
										{
											//Iterator iter =(Iterator) accountBalance.entrySet().iterator();
											while(accountBalance.entrySet().iterator().hasNext())
											{
												Map.Entry entry = (Map.Entry)accountBalance.entrySet().iterator().next();
												currentmarginAccountId = ((Integer)entry.getKey()).intValue();
												currentmarginAccountBalance = ((Double)entry.getValue()).doubleValue();
												if(currentmarginAccountBalance - currentAccountMoneyBalance >=0)
												{
													
													trfn.setAccountOperation("{count:"+(detailcount+1)+",detail:"+detail+";"+currentmarginAccountId+"-"+currentAccountMoneyBalance+"-2}");//设置账户操作明细
													accountBalance.put(new Integer(currentmarginAccountId), new Double(currentmarginAccountBalance-currentAccountMoneyBalance));//把该账户扣过款的余额在存回去
													marginAccountTotalBalance = marginAccountTotalBalance - currentAccountMoneyBalance;//扣款成功后,更新保证金账户总额数
													currentAccountMoneyBalance = 0;
													break;
												}
												else
												{
													detailcount+=1;
													detail = detail+";"+currentmarginAccountId+"-"+currentmarginAccountBalance+"-2";
													currentAccountMoneyBalance = currentAccountMoneyBalance-currentmarginAccountBalance;
													accountBalance.remove(new Integer(currentmarginAccountId));//扣过的账户直接删除
													marginAccountTotalBalance = marginAccountTotalBalance - currentmarginAccountBalance;//扣款成功后,更新保证金账户总额数
												}
											}
										}
									}
									else
									{
										double currentmarginAccountAmount = trfn.getCurrentPaymentAmount();
										detailcount=0;
										detail= "";
										if(marginAccountTotalBalance - trfn.getCurrentPaymentAmount()<0)
										{
											trfn.setRemark("失败:保证金账户余额不足");
											trfn.setFlag(-1);//设置该记录状态为失败
											go_on = -1;
										}
										else
										{
											//Iterator iter =(Iterator) accountBalance.entrySet().iterator();
											while(accountBalance.entrySet().iterator().hasNext())
											{
												Map.Entry entry = (Map.Entry)accountBalance.entrySet().iterator().next();
												currentmarginAccountId = ((Integer)entry.getKey()).intValue();
												currentmarginAccountBalance = ((Double)entry.getValue()).doubleValue();
												if(currentmarginAccountBalance - currentmarginAccountAmount >0)
												{
													if("".equals(detail))
													{
														detail = currentmarginAccountId+"-"+currentmarginAccountAmount+"-2";
													}
													else
													{
														detail = detail+";"+currentmarginAccountId+"-"+currentmarginAccountAmount+"-2";
													}
													trfn.setAccountOperation("{count:"+(detailcount+1)+",detail:"+detail+"}");//设置账户操作明细
													accountBalance.put(new Integer(currentmarginAccountId), new Double(currentmarginAccountBalance-currentmarginAccountAmount));//把该账户扣过款的余额在存回去
													marginAccountTotalBalance = marginAccountTotalBalance - currentmarginAccountAmount;//如果扣款成功,更新保证金账户总额数
													currentmarginAccountAmount = 0;
													break;
												}
												else
												{
													detailcount+=1;
													if("".equals(detail))
													{
														detail = currentmarginAccountId+"-"+currentmarginAccountBalance+"-2";
													}
													else
													{
														detail = detail+";"+currentmarginAccountId+"-"+currentmarginAccountBalance+"-2";
													}
													accountBalance.remove(new Integer(currentmarginAccountId));//扣过的账户直接删除
													currentmarginAccountAmount = currentmarginAccountAmount - currentmarginAccountBalance;
													marginAccountTotalBalance = marginAccountTotalBalance - currentmarginAccountBalance;//如果扣款成功,更新保证金账户总额数
												}
											}
										}
									}
								}
							}
							returnList.add(trfn);
						}
						break;
					}
				  }
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(res);
				this.cleanup(pstmt);
				this.cleanup(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return returnList;
	}
	
	/**
	 * 批量还款----存储账户明细
	 * @author afzhu
	 */
	public void saveAccountDetailInfo(TransAccountDetailInfo tadi) throws Exception
	{
		sett_TransAccountDetailDAO detailDAO = new sett_TransAccountDetailDAO();
		detailDAO.add(tadi);
//		String sql = "insert into sett_transaccountdetail(id,NOFFICEID,NCURRENCYID,NTRANSACTIONTYPEID,DTEXECUTE,STRANSNO,NTRANSACCOUNTID,NSUBACCOUNTID,MAMOUNT,NTRANSDIRECTION,NOPPACCOUNTID,NOPPSUBACCOUNTID,OPPACCOUNTNO,OPPACCOUNTNAME,NSTATUSID) values(?,?,?,?,to_date(?,'yyyy-MM-dd'),?,?,?,?,?,?,?,?,?,?)";
//		Connection con = null;
//		PreparedStatement ps = null;
//		long maxID =0;//结算表中最大ID值
//		try
//		{
//			maxID=getAllTableMaxID("sett_transaccountdetail")+1;
//			con = getConnection();
//			ps = con.prepareStatement(sql);
//			ps.setLong(1, maxID);
//			ps.setLong(2, tadi.getOfficeID());//办事处ID
//			ps.setLong(3, tadi.getCurrencyID());//币种
//			ps.setLong(4, tadi.getTransactionTypeID());//交易类型
//			ps.setString(5,DataFormat.getDateString(tadi.getDtExecute()));//执行日
//			ps.setString(6, tadi.getTransNo());//交易号
//			ps.setLong(7, tadi.getTransAccountID());//交易账户ID 
//			ps.setLong(8, tadi.getTransSubAccountID());//交易子账户ID
//			ps.setDouble(9, tadi.getAmount());//交易发生额
//			ps.setLong(10, tadi.getTransDirection());//交易方向
//			ps.setLong(11, tadi.getOppAccountID());//对方账户ID
//			ps.setLong(12, tadi.getOppSubAccountID());//对方子账户ID
//			ps.setString(13, tadi.getOppAccountNo());//对方账户号
//			ps.setString(14, tadi.getOppAccountName());//对方账户名称
//			ps.setLong(15, tadi.getStatusID());//状态
//			ps.execute();
//							
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw e;
//		}
//		finally
//		{
//			try {
//				this.cleanup(ps);
//				this.cleanup(con);
//				//this.cleanup(res);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
	}
	
	/**
	 * 批量还款---账户更新
	 * @author afzhu
	 * @param trfn
	 * @param flag; 0代表扣款,1代表收款
	 */
	public void accountDeduckMoney(String detailOp,int flag) throws Exception
	{
		
		String sql = "update sett_subaccount set MBALANCE=MBALANCE+? where id =?";//收款
		String sql1 = "update sett_subaccount set MBALANCE=MBALANCE-? where id =?";//付款
		Connection con = null;
		PreparedStatement ps = null;
		try{
			if(flag==0)//扣款
			{
				con = getConnection();
				ps = con.prepareStatement(sql1);
				ps.setDouble(1,Double.parseDouble(detailOp.split("-")[1]));
				ps.setInt(2, Integer.parseInt(detailOp.split("-")[0]));
				ps.executeUpdate();
			}
			else//收款
			{
				con = getConnection();
				ps = con.prepareStatement(sql);
				ps.setDouble(1,Double.parseDouble(detailOp.split("-")[1]));
				ps.setInt(2, Integer.parseInt(detailOp.split("-")[0]));
				ps.executeUpdate();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(ps);
				this.cleanup(con);
				//this.cleanup(res);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 批量还款---链接查找---删除操作
	 * @author afzhu
	 * @param detailOpp
	 * @param flag 0表示对账户做更新,1表示对会计分录和账户明细做更新
	 */
	public void hrefFindDelete(String detailOppOrTransNo,int flag) throws Exception
	{
	
		try{
			if(flag==0)
			{
				accountDeduckMoney(detailOppOrTransNo,1);//更新子账户表
			}
			else
			{
				updateTransReturnFinance(detailOppOrTransNo);//更新结算表
				updateGenerateGLEntry(detailOppOrTransNo);//更新会计分录
				updateAccountDetailInfo(detailOppOrTransNo);//更新账户明细表
				deleteQuantityRepaymentBalance(detailOppOrTransNo);//删除连接查找记录
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 批量还款---更新会计分录
	 * @author afzhu
	 * @param transNo
	 */
	public void updateGenerateGLEntry(String transNo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		String sql ="update sett_glentry set NSTATUSID=0 where STRANSNO=?";
		try
		{
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, transNo);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(ps);
				this.cleanup(con);
				//this.cleanup(res);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 批量还款---更新账户明细
	 * @author afzhu
	 * @param transNo
	 */
	public void updateAccountDetailInfo(String transNo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update sett_transaccountdetail set NSTATUSID=0 where STRANSNO=?";
		try
		{
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, transNo);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(ps);
				this.cleanup(con);
				//this.cleanup(res);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 批量还款---更新结算记录
	 * @author afzhu
	 * @param transNo
	 */
	public void updateTransReturnFinance(String transNo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update Sett_TransReturnFinance set NSTATUSID=0,ISSUE=0 where STRANSNO=?";
		try
		{
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, transNo);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(ps);
				this.cleanup(con);
				//this.cleanup(res);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 批量还款----删除连接查找记录
	 * @author afzhu
	 * @param transNo
	 */
	public void deleteQuantityRepaymentBalance(String transNo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from SETT_QUANTITYREPAYMENTBALANCE where TRADENUM=?";
		try
		{
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, transNo);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(ps);
				this.cleanup(con);
				//this.cleanup(res);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 批量还款----存储结算记录
	 * @author afzhu
	 * @param trfn
	 */
	public void saveTransReturnFinance(TransReturnFinanceNewInfo trfn) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long maxID =0;//结算表中最大ID值
		String sql = "insert into Sett_TransReturnFinance(id,Nofficeid,Ncurrencyid,STRANSNO,NTRANSACTIONTYPEID,NSTATUSID,DTMODIFY,DTINPUT,DTCHECK,NINPUTUSERID,NCHECKUSERID,NCONTRACTID,NRETURNCORPUSACCOUNTID,MCORPUSAMOUNT,NRETURNINTERESTACCOUNTID,MINTERESTAMOUNT,NRETURNBAILACCOUNTID,MBAILAMOUNT,DTINTERESTSTART,DTEXECUTE,MRATE,ISSUE,CONTRACTPLANDETAILID) values (?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),?,?,?)";
		try{
			//maxID = getAllTableMaxID("Sett_TransReturnFinance") + 1;
			maxID = getSett_Transreturnfinance();
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.setLong(1, maxID);//ID
			ps.setLong(2, trfn.getNofficeid());//办事处ID
			ps.setLong(3, trfn.getNcurrencyid());//币种ID
			ps.setString(4, trfn.getTransno());//交易号
			ps.setLong(5, trfn.getTransactionTypeID());//交易类型
			ps.setLong(6, SETTConstant.TransactionStatus.CHECK);//交易状态
			ps.setString(7, DataFormat.formatDate(new Date(),DataFormat.FMT_DATE_YYYYMMDD_HHMMSS));//修改日期
			ps.setString(8, trfn.getSysDateS());//录入时间(开机日)
			ps.setString(9, trfn.getSysDateS());//复核时间(开机日)
			ps.setLong(10, trfn.getUserID());//录入人
			ps.setLong(11, trfn.getUserID());//复核人
			ps.setLong(12, trfn.getContractID());//合同ID
			ps.setLong(13, trfn.getCurrentAccountID());//还本金账户ID
			//ps.setDouble(14, trfn.getCurrentMoney());//本次还本金金额
			ps.setDouble(14, trfn.getPrincipal());//本次还本金金额
			ps.setLong(15, trfn.getCurrentAccountID());//还利息账户ID
			ps.setDouble(16, trfn.getInterest());//本次还利息金额
			ps.setLong(17, trfn.getMarginAccountID());//扣除保证金账户ID
			ps.setDouble(18, trfn.getMarginMoney());//本次还保证金金额
			ps.setString(19, trfn.getSysDateS());//执行日(开机日)
			ps.setString(20, trfn.getSysDateS());//执行日(开机日)
			ps.setDouble(21, trfn.getRate());//利率
			ps.setInt(22, trfn.getIssue());//期数
			ps.setDouble(23, trfn.getContractDetailID());//合同计划明细ID
			ps.execute();
			 
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				//this.cleanup(res);
				this.cleanup(ps);
				this.cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * 批量还款---生成连接查询记录
	 * @author afzhu
	 * @param trfn
	 * @throws Exception
	 */
	public void createHrefFind(TransReturnFinanceNewInfo trfn,String detailOpp) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long maxID =0;//结算表中最大ID值
		String sql = "insert into SETT_QUANTITYREPAYMENTBALANCE(ID,CONTRACTID,CLIENTCODE,CLIENTNAME,CONTRACTCODE,STARTDATE,ENDDATE,ISSUE,REPAYMENTBALANCE,RATE,CURRENTRECURRENTPAYMENTBALANCE,CURRENTACCOUNT,CURRENTACCOUNTID,MARGINACCOUNT,MARGINACCOUNTID,CURRENTACCOUNTREPAYMENTBALANCE,MARGINACCOUNTREPAYMENTBALANCE,CREATEUSER,CREATEUSERID,CREATEDATE,TRADENUM,ACCOUNTDETAIL_OPERATION,GLENTRY_OPERATION,SUBACCOUNT_OPERATION,BALANCE_OPERATION,INTEREST,PRINCIPAL) values(?,?,?,?,?,to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd'),?,?,?,?,?,?,?)";
		try{
			maxID = getAllTableMaxID("SETT_QUANTITYREPAYMENTBALANCE")+1;
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.setLong(1, maxID);//ID
			ps.setLong(2, trfn.getContractID());//合同ID
			ps.setString(3, trfn.getClientCode());//客户编号
			ps.setString(4, trfn.getClientName());//客户名称
			ps.setString(5, trfn.getContractCode());//合同编号
			ps.setString(6, DataFormat.formatDate(trfn.getStartDate(),DataFormat.FMT_DATE_YYYYMMDD));//开始时间
			ps.setString(7, DataFormat.formatDate(trfn.getEndDate(),DataFormat.FMT_DATE_YYYYMMDD));//开始时间
			ps.setInt(8, trfn.getIssue());//期数
			ps.setDouble(9, trfn.getRepaymentBalance());//还款余额
			ps.setDouble(10, trfn.getRate());//利率
			ps.setDouble(11, trfn.getCurrentPaymentAmount());//本期还款金额
			ps.setString(12, trfn.getCurrentAccount());//活期账户
			ps.setLong(13, trfn.getCurrentAccountID());//活期账户ID
			ps.setString(14, trfn.getMarginAccount());//保证金账户
			ps.setLong(15, trfn.getMarginAccountID());//保证金账户ID
			ps.setDouble(16, trfn.getCurrentMoney());//活期账户还款金额
			ps.setDouble(17, trfn.getMarginMoney());//保证金账户还款金额
			ps.setString(18, trfn.getSname());//生成人
			ps.setLong(19, trfn.getUserID());//生成人ID
			ps.setString(20, trfn.getSysDateS());//生成日期(开机日)
			ps.setString(21, trfn.getTransno());//交易号
			ps.setString(22, "");//账户明细操作字段
			ps.setString(23, "");//会计分录操作字段
			ps.setString(24, detailOpp);//子账户操作字段
			ps.setString(25, "");//结算操作字段
			ps.setDouble(26, trfn.getInterest());//利息
			ps.setDouble(27,trfn.getPrincipal());//本金
			ps.execute();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				//this.cleanup(res);
				this.cleanup(ps);
				this.cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 
	 * 批量还款---链接查找
	 * @author afzhu
	 * @param userid 当前用户ID
	 * @param exeDate 执行日(开机日)
	 * @return
	 * @throws Exception
	 */
	public List getHrefFindList(long userid,String exeDate) throws Exception
	{
		List returnList = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet res=null;
		TransReturnFinanceNewInfo trfn = null;
		String sql = "select * from SETT_QUANTITYREPAYMENTBALANCE where CREATEUSERID=? and CREATEDATE = to_date(?,'yyyy-MM-dd') order by TRADENUM";
		try{
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.setLong(1, userid);
			ps.setString(2, exeDate);
			res = ps.executeQuery();
			while(res.next())
			{
				trfn=new TransReturnFinanceNewInfo();
				trfn.setClientCode(res.getString("CLIENTCODE"));//客户编号
				trfn.setClientName(res.getString("CLIENTNAME"));//客户名称
				trfn.setContractCode(res.getString("CONTRACTCODE"));//合同编号
				trfn.setContractID(res.getInt("CONTRACTID"));//合同ID
				trfn.setCurrentAccountID(res.getInt("CURRENTACCOUNTID"));//活期账户ID
				trfn.setCurrentPaymentAmount(res.getDouble("CURRENTRECURRENTPAYMENTBALANCE"));//本期还款金额
				trfn.setEndDate(res.getDate("ENDDATE"));//结束时间
				trfn.setInterest(res.getDouble("Interest"));//利息
				trfn.setIssue(res.getInt("ISSUE"));//期数
				trfn.setMarginAccountID(res.getInt("MARGINACCOUNTID"));//保证金账户ID
				//trfn.setPlanDate(res.getDate("PlanDate"));//还款日期
				trfn.setPrincipal(res.getDouble("Principal"));//本金
				trfn.setRate(res.getDouble("RATE"));//还款利率
				trfn.setRepaymentBalance(res.getDouble("REPAYMENTBALANCE"));//还款余额
				trfn.setStartDate(res.getDate("STARTDATE"));//开始时间
				//trfn.setSubtypeID(res.getInt("SubtypeID"));//贷款子类型id
				trfn.setSname(res.getString("CREATEUSER"));//生成人
				trfn.setUserID(res.getLong("CREATEUSERID"));//生成人ID
				trfn.setDtinputdate(res.getDate("CREATEDATE"));//生成时间
				trfn.setCurrentAccount(res.getString("CURRENTACCOUNT"));//活期账户
				trfn.setMarginAccount(res.getString("MARGINACCOUNT"));//保证金账户
				//trfn.setMexamineamount(res.getDouble("mexamineamount"));//批准金额
				//trfn.setNofficeid(res.getLong("nofficeid"));//办事处ID
				//trfn.setNcurrencyid(res.getLong("ncurrencyid"));//币种
				//trfn.setNstatusid(res.getLong("nstatusid"));//合同状态ID
				//trfn.setNtypeid(res.getLong("ntypeid"));//贷款类型ID
				//trfn.setContractDetailID(res.getInt("contractdetailID"));//合同明细ID
				trfn.setCurrentMoney(res.getDouble("CURRENTACCOUNTREPAYMENTBALANCE"));//活期账户还款金额
				trfn.setMarginMoney(res.getDouble("MARGINACCOUNTREPAYMENTBALANCE"));//保证金账户还款金额
				trfn.setTransno(res.getString("TRADENUM"));//交易号
				trfn.setAccountOperation(res.getString("SUBACCOUNT_OPERATION"));//子账户操作明细
				returnList.add(trfn);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(res);
				this.cleanup(ps);
				this.cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return returnList;
	}
	
	/**
	 * 
	 * 批量还款---链接查找
	 * @author afzhu
	 * @param userid 当前用户ID
	 * @param exeDate 执行日(开机日)
	 * @return
	 * @throws Exception
	 */
	public TransReturnFinanceNewInfo getHrefFindListByTransNo(String sTransNo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet res=null;
		TransReturnFinanceNewInfo trfn = null;
		String sql = "select * from SETT_QUANTITYREPAYMENTBALANCE where TRADENUM=? ";
		try{
			con = getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, sTransNo);
			res = ps.executeQuery();
			while(res.next())
			{
				trfn=new TransReturnFinanceNewInfo();
				trfn.setClientCode(res.getString("CLIENTCODE"));//客户编号
				trfn.setClientName(res.getString("CLIENTNAME"));//客户名称
				trfn.setContractCode(res.getString("CONTRACTCODE"));//合同编号
				trfn.setContractID(res.getInt("CONTRACTID"));//合同ID
				trfn.setCurrentAccountID(res.getInt("CURRENTACCOUNTID"));//活期账户ID
				trfn.setCurrentPaymentAmount(res.getDouble("CURRENTRECURRENTPAYMENTBALANCE"));//本期还款金额
				trfn.setEndDate(res.getDate("ENDDATE"));//结束时间
				trfn.setInterest(res.getDouble("Interest"));//利息
				trfn.setIssue(res.getInt("ISSUE"));//期数
				trfn.setMarginAccountID(res.getInt("MARGINACCOUNTID"));//保证金账户ID
				//trfn.setPlanDate(res.getDate("PlanDate"));//还款日期
				trfn.setPrincipal(res.getDouble("Principal"));//本金
				trfn.setRate(res.getDouble("RATE"));//还款利率
				trfn.setRepaymentBalance(res.getDouble("REPAYMENTBALANCE"));//还款余额
				trfn.setStartDate(res.getDate("STARTDATE"));//开始时间
				//trfn.setSubtypeID(res.getInt("SubtypeID"));//贷款子类型id
				trfn.setSname(res.getString("CREATEUSER"));//生成人
				trfn.setUserID(res.getLong("CREATEUSERID"));//生成人ID
				trfn.setDtinputdate(res.getDate("CREATEDATE"));//生成时间
				trfn.setCurrentAccount(res.getString("CURRENTACCOUNT"));//活期账户
				trfn.setMarginAccount(res.getString("MARGINACCOUNT"));//保证金账户
				//trfn.setMexamineamount(res.getDouble("mexamineamount"));//批准金额
				//trfn.setNofficeid(res.getLong("nofficeid"));//办事处ID
				//trfn.setNcurrencyid(res.getLong("ncurrencyid"));//币种
				//trfn.setNstatusid(res.getLong("nstatusid"));//合同状态ID
				//trfn.setNtypeid(res.getLong("ntypeid"));//贷款类型ID
				//trfn.setContractDetailID(res.getInt("contractdetailID"));//合同明细ID
				trfn.setCurrentMoney(res.getDouble("CURRENTACCOUNTREPAYMENTBALANCE"));//活期账户还款金额
				trfn.setMarginMoney(res.getDouble("MARGINACCOUNTREPAYMENTBALANCE"));//保证金账户还款金额
				trfn.setTransno(res.getString("TRADENUM"));//交易号
				trfn.setAccountOperation(res.getString("SUBACCOUNT_OPERATION"));//子账户操作明细
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(res);
				this.cleanup(ps);
				this.cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return trfn;
	}
	
	/**
	 * 
	 * 获得所有表的最大ID值
	 * @author afzhu
	 * @param tableName
	 * @return
	 */
	public long getAllTableMaxID(String tableName) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet res=null;
		String sql = "select max(id) as maxid from "+tableName;
		long maxID =0;//最大ID值
		try{
			con = getConnection();
			ps = con.prepareStatement(sql);
			res = ps.executeQuery();
			while(res.next())
			{
				maxID = res.getLong("maxid");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try {
				this.cleanup(res);
				this.cleanup(ps);
				this.cleanup(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return maxID;
	}
	
	protected long getSett_Transreturnfinance() throws Exception
	{
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select seq_transreturnfinance.nextval nextid from dual");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			id = rs.getLong("nextid");
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}
	/**
	 * 计算扣保证金的和
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public double sumMbailamount(long lID) throws Exception
	{
		double result = 0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try {
			strSQL = "select sum(mbailamount) from sett_transreturnfinance where ncontractid = "+lID+" and nstatusid = "+SETTConstant.TransactionStatus.CHECK+" group by ncontractid";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				result = rs.getDouble(1);
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
		return result;
	}

	/**
	 * 计算扣本金的和
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public double sumMcorpusAmount(long lID) throws Exception
	{
		double result = 0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try {
			strSQL = "select nvl(sum(MCORPUSAMOUNT),0) from sett_transreturnfinance where ncontractid = "+lID+" and nstatusid = "+SETTConstant.TransactionStatus.CHECK;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				result = rs.getDouble(1);
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
		return result;
	}	
	/**
	 * @author yunchang
	 * @date 2010-07-02
	 * @function 融资租赁-收款-已收保证金金额
	 * @param long 
	 * @return double
	 * @throws Exception
	 */
	public double getMbailamount(long constractID) throws Exception
	{
		double tempMbailamount = 0;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String strSQL = null;
		try 
		{	
			strSQL = "select nvl(sum(mbailamount),0) receiveAmount from sett_transreceivefinance b where b.ncontractid = " + constractID + " and b.nstatusid = "+SETTConstant.TransactionStatus.CHECK;
			preparedStatement = connection.prepareStatement(strSQL);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null && resultSet.next())
			{
				tempMbailamount = resultSet.getDouble(1);
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
				cleanup(resultSet);
				cleanup(preparedStatement);
				cleanup(connection);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}
		return tempMbailamount;		
	}
	
	//add by zwxiao 2010-07-06 检查合同是否已经做过保后处理
	private boolean checkContract(long contractID)  throws Exception
	{
		boolean bReturn = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res=null;
		try 
		{
			StringBuffer sql =new StringBuffer();
			sql.append(" select * from loan_recognizancenoticeform a where a.contractid = "+contractID+" and a.statusid != 0 ");
			conn=this.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			res=pstmt.executeQuery();
			while(res.next()){
				bReturn = true;
			}
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			try 
			{
				cleanup(res);
				cleanup(pstmt);
				cleanup(conn);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}	
		return bReturn;
	}
	
	/**
	 * added by xiong fei 2010-07-16
	 * 提前还款复核成功后重新安排还款计划
	 * @param constractID
	 * @return long
	 * @throws Exception
	 * @modify by yunchang
	 * @date 2010-11-25 15:40
	 * @function 修改提前还款之后重新拍计划
	 */
	public long ajustPlan(long contractID,Timestamp ts) throws Exception
	{
		long returnRes = -1;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer strSQL1 = new StringBuffer();//从计划明细表里查询出该合同下的总期数和总金额
		StringBuffer strSQL2 = new StringBuffer();//从结算表里查出该合同还款的最大期数和已还金额
		
		long issue = -1;//总期数
		double amount = 0.0;//总金额
		long issueD = -1;//已还期数
		double amountD = 0.0;//已还金额总数
		long remainIssue = -1;//剩余期数
		double principalsBalace = 0.0;//剩余本金
		
		double adjustInterest = 0.0;//调整后的每期利息
    	double adjustPrincipal = 0.0;//调整后的每期本金
    	
    	double mrate = 0.0;//合同利率
    	long nPayType = -1;//付款方式
    	long lRepayNum = -1;//每一期付款月数
    	long lInterestCountType = -1;//还贷方式
    	
    	Collection repayColl = null;//存放要改的记录
    	Collection resultColl = null;//存放改后的记录
        /*
         * add by yunchang
         * date 2010-08-27 11点26分
         * 增加   租赁物到期残值(融资租赁)
         * 增加   租赁物到期残值(融资租赁)所产生的利息
         */
        double mMatureNominalAmount = 0.0d; 
        double mMatureNominalRateAmount = 0.0d;	 
    	
    	try
		{
    		ContractDao dao = new ContractDao();
    		if(contractID == -1)
    		{
    			throw new Exception("合同ID为空");
    		}
    		/*
    		 * 获取合同最新的利率
    		 */
    		mrate = dao.getLatelyRateForRZZL(contractID,ts).getRate();
    		if(mrate == 0)
    		{
    			throw new Exception("合同利率为空");
    		}
    		/*
    		 * 获取合同详细的信息
    		 */
    		ContractInfo contractInfo = dao.findByID(contractID);
    		if(contractInfo!=null)
    		{
    			nPayType = contractInfo.getAssureChargeTypeID();//付款方式，按月或季等
    			lInterestCountType = contractInfo.getInterestCountTypeID();//还贷方式，等额本金或等额本息
    			/*
    			 * add by yunchang
    			 * date 2010-08-31 15:43
    			 * function 查询出到期残值
    			 */
    			mMatureNominalAmount = contractInfo.getMatureNominalAmount();
    			
    		}
			/*
			 * 判断类型
			 */
			switch((int)nPayType)
			{
				case (int)LOANConstant.ChargeRatePayType.YEAR :
					lRepayNum = 12;
					break;
				case (int)LOANConstant.ChargeRatePayType.HALFYEAR :
					lRepayNum = 6;
					break;
				case (int)LOANConstant.ChargeRatePayType.QUARTER : 
					lRepayNum = 3;
					break;
				case (int)LOANConstant.ChargeRatePayType.MONTH :
					lRepayNum = 1;
					break;	
			}
			strSQL1.append(" select max(issue) as issueD,sum(mcorpusamount) as mamountD ");
			strSQL1.append(" from sett_transreturnfinance where ncontractid = "+contractID+" and nstatusid = 3 ");
			System.out.println("===============================================================================");
			System.out.println("查询出最近的期数，以及金额数=" + strSQL1.toString());
			System.out.println("===============================================================================");			
			pstmt = conn.prepareStatement(strSQL1.toString());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				issueD = rs.getLong("issueD");//已还期数
				amountD = rs.getDouble("mamountD");//已还金额总数
			}
			
			strSQL2.append(" select count(*) as issue,nvl(sum(a.MAMOUNT),0.00) as amount ");
			strSQL2.append(" from loan_loancontractplandetail a ");
			strSQL2.append(" where 1=1  and a.ncontractplanid =( ");
			strSQL2.append("     select max(id) as maxplanid ");
			strSQL2.append("     from  loan_loancontractplan ");
			strSQL2.append("     where  NSTATUSID = 1 and NISUSED = 2 and NCONTRACTID = '"+contractID+"' ");
			strSQL2.append("     ) ");
			System.out.println("===============================================================================");
			System.out.println("查询出总的期数以及总的金额数=" + strSQL1.toString());
			System.out.println("===============================================================================");						
			pstmt = conn.prepareStatement(strSQL2.toString());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				issue = rs.getLong("issue");//总期数
				amount = rs.getDouble("amount");//总金额
			}
			
			remainIssue = issue - issueD;//剩余期数
			principalsBalace = DataFormat.formatDouble(amount - amountD);//剩余总额
			
			//String strSQL3 = " select a.mcorpusamount as mamount ,a.minterestamount as minterest from sett_transreturnfinance a where a.ncontractid ="+contractID+" and a.issue ="+issueD;
			/*
			 * modify by yunchang
			 * date 2010-12-01 11:30
			 * function 最近一期所还款项目的本金金额以及利息金额
			 */
			String strSQL3 = " select sum(a.mcorpusamount) as mamount ,sum(a.minterestamount) as minterest from sett_transreturnfinance a where a.ncontractid ="+contractID+" and a.issue ="+issueD;			
			double mamount = 0.0; 
			double minterest = 0.0;
			System.out.println("===============================================================================");
			System.out.println("查询本期还款的本金金额以及利息" + strSQL3.toString());
			System.out.println("===============================================================================");						
			pstmt = conn.prepareStatement(strSQL3);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				mamount = rs.getDouble("mamount");
				minterest = rs.getDouble("minterest");
			}
			double payment_interest_rate = mrate*lRepayNum/12/100; 
			/*
			 * add by yunchang
			 * date 2010-08-31 15:54
			 * 增加   租赁物到期残值(融资租赁) 每期所产生的利息
			 */
			mMatureNominalRateAmount = mMatureNominalAmount * payment_interest_rate;
		    /*
		     * add by yunchang
		     * date 2010-08-27 13:27
		     * function 等本每次还款的金额   添加上到期残值所产生的利息
		     * 	        先进行加和，然后再进行格式化。
		     */			
			double principalTemp = (principalsBalace - mMatureNominalAmount)*payment_interest_rate*Math.pow((1+payment_interest_rate),remainIssue)/(Math.pow((1+payment_interest_rate),remainIssue)-1);
		    principalTemp = principalTemp + mMatureNominalRateAmount ;
			principalTemp = DataFormat.formatDouble(principalTemp);
			
			double principalsum =0.00;//等额本息使用，累计计算已经计划过的金额
			RepayPlanDao repayDao = new RepayPlanDao();
			/*
			 * 获取合同下还款计划的信息
			 */
			repayColl = repayDao.findRepayPlan(contractID);
			if(repayColl == null)
			{
				throw new Exception("获取还款计划失败");
			}
			
			long lCount = repayColl.size();
			long lIndex = 0;
			
			Iterator iterator = repayColl.iterator();
			resultColl = new ArrayList();
			while(iterator.hasNext())
			{
				RepayPlanInfo repayPlanInfo = (RepayPlanInfo)iterator.next();
				if(repayPlanInfo.getIssue()<issueD)
				{
					//如果是已还计划则跳过
					resultColl.add(repayPlanInfo);
					lIndex++;
					continue;
				}
				if(repayPlanInfo.getIssue()==issueD)
				{
					//设置本期的还款本金和利息
					repayPlanInfo.setMINTERESTAMOUNT(minterest);
					repayPlanInfo.setDPlanPayAmount(mamount);
					repayPlanInfo.setLateRateString(DataFormat.formatRate(mrate) );
					resultColl.add(repayPlanInfo);
					lIndex++;
					continue;
				}
				/*
				 * add by yunchang
				 * date 2010-08-31 16:08
				 * function 计算利息的时候一定要加上到期残值所产生的利息
				 *          再计算的时候一定要注意，先进行加和然后再计算利息
				 */		
				adjustInterest = (((principalsBalace - mMatureNominalAmount)-principalsum)*mrate/12/100) * lRepayNum;
				adjustInterest = adjustInterest + mMatureNominalRateAmount ;				
				adjustInterest = DataFormat.formatDouble(adjustInterest) ;
				if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEAMOUNT)//等额本息
				{
					if(lIndex == lCount-1)
					{	
						//如果是最后一期，就特殊处理一下，处理逻辑：1.每期的金额相加为总和为合同金额 2.每期的还款的金额相同 3.如果存在最后剩下几分钱的本期的情况就调整本金在调整利息
						adjustPrincipal = (principalsBalace - mMatureNominalAmount) - principalsum;
						adjustInterest = principalTemp - adjustPrincipal;
					}
					else
					{
						adjustPrincipal = principalTemp - adjustInterest;
					}
				}
				else if(lInterestCountType == LOANConstant.InterestCountType.AVERAGEPRINCIPAL)//等额本金
				{
					adjustPrincipal = DataFormat.formatDouble((principalsBalace - mMatureNominalAmount)/remainIssue);
				}
				principalsum+=adjustPrincipal;
				repayPlanInfo.setMINTERESTAMOUNT(adjustInterest);
				repayPlanInfo.setDPlanPayAmount(adjustPrincipal);
				repayPlanInfo.setLateRateString(DataFormat.formatRate(mrate) );
				resultColl.add(repayPlanInfo);
				lIndex++;
			}
			returnRes = repayDao.savePlan(resultColl,contractInfo);
			if(returnRes != 1)
			{
				throw new Exception("更新计划时出现异常");
			}
		}
    	catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(pstmt);
				cleanup(conn);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}
		return returnRes;		
	}
	
	/**
	 * added by xiong fei 2010-07-16
	 * 提前还款复核成功后重新安排还款计划
	 * @param constractID
	 * @return long
	 * @throws Exception
	 */
	public long deletePlan(long contractID) throws Exception
	{
		long returnRes = -1;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer strSQL1 = new StringBuffer();//从计划明细表里删除计划信息
		StringBuffer strSQL2 = new StringBuffer();//从计划版本表里删除计划版本信息
		StringBuffer strSQL3 = new StringBuffer();//从计划版本表里删除计划版本信息
		long id = 0;
    	try
		{
    		ContractDao dao = new ContractDao();
    		if(contractID == -1){
    			throw new Exception("合同ID为空");
    		}
    		String sql = "SELECT MAX(ID) AS ID FROM LOAN_LOANCONTRACTPLAN WHERE NCONTRACTID = ?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setLong(1, contractID);
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			id = rs.getLong("ID");
    		}
    		if(rs!=null){
    			rs.close();
    		}
    		if(pstmt!=null){
    			pstmt.close();
    		}
    		strSQL1.append(" DELETE FROM LOAN_LOANCONTRACTPLANDETAIL A WHERE A.NCONTRACTPLANID = ?");
    		pstmt = conn.prepareStatement(strSQL1.toString());
    		pstmt.setLong(1, id);
    		returnRes = pstmt.executeUpdate();
    		if(returnRes == 0 ){
    			throw new Exception("删除还款计划时出现异常");
    		}
    		if(pstmt!=null){
    			pstmt.close();
    		}
    		strSQL2.append(" DELETE FROM LOAN_LOANCONTRACTPLAN WHERE ID = ?");
    		pstmt = conn.prepareStatement(strSQL2.toString());
    		pstmt.setLong(1,id);
    		returnRes = pstmt.executeUpdate();
    		if(returnRes == 0 ){
    			throw new Exception("删除计划版本时出现异常");
    		}
    		strSQL3.append(" DELETE FROM loan_planmodifyform WHERE ID = (select max(id) from loan_planmodifyform where ncontractid = ?)");
    		pstmt = conn.prepareStatement(strSQL3.toString());
    		pstmt.setLong(1,contractID);
    		returnRes = pstmt.executeUpdate();
    		if(returnRes == 0 ){
    			throw new Exception("删除计划版本时出现异常");
    		}
		}
    	catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}finally 
		{
			try 
			{
				cleanup(rs);
				cleanup(pstmt);
				cleanup(conn);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}
		return returnRes;		
	}
	
	
	/** 
	 * added by xiong fei 2010-07-19
	 * 在往结算表里添加时，查找出新增的三个字段的值
	 * 提前还款时,肯定不是延付，故是否延付为0
	 * @param info
	 * @return TransReturnFinanceInfo
	 * @throws Exception
	 * @modify by yunchang
	 * @date 2010-12-01 10:19
	 * @function 功能是为了完成提前还款的排班计划重新安排
	 */
	private TransReturnFinanceInfo addInfo(TransReturnFinanceInfo info,String flag)  throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res=null;
		TransReturnFinanceInfo info1 = new TransReturnFinanceInfo();
		long contractID = 0;
		LoanLeaseholdRepayFormDao loanLeaseholdRepayFormDao = null;
		String tempISSUE="";
		try 
		{
			if(info == null)
			{
				info1 = info;
				throw new Exception("addInfo:传入的对象为空");
			}
			contractID = info.getContractID();
			StringBuffer sql =new StringBuffer();
			//sql.append(" select id,issue from ( ");
			//sql.append(" SELECT id, ");
			//sql.append(" RANK() OVER(PARTITION BY NCONTRACTPLANID ORDER BY DTPLANDATE) as issue ");
			//sql.append(" FROM loan_loancontractplandetail ");
			//sql.append(" where ncontractplanid = ( ");
			//sql.append("    select max(id) from  loan_loancontractplan ");
			//sql.append("    where  NSTATUSID = 1 and NISUSED = 2 and NCONTRACTID = '"+contractID+"') ");
			//sql.append(" ) where issue=( ");
			/*
			 * modify by yunchang
			 * date 2010-12-01 09:43
			 * function 做期数的调整
			 */
			sql.append(" select id, issue from (select id, rank() over(partition by ncontractplanid order by dtplandate) as issue ");
			sql.append(" from loan_loancontractplandetail where ncontractplanid = (select max(id) from loan_loancontractplan ");
			sql.append(" where nstatusid = 1 and nisused = 2 and ncontractid = '" + contractID + "')) where issue = ( ");			
			
			//这里的期数定为已还最大期数加1
			if(flag.equals("add"))
			{
				//保存,如果是第一次出现在结算表，则max(issue)查出来会是NULL,故采用了空处理
				loanLeaseholdRepayFormDao = new LoanLeaseholdRepayFormDao();
				tempISSUE = loanLeaseholdRepayFormDao.getLastIssue(contractID, DataFormat.getDateString(Env.getSystemDate(info.getOfficeID(),info.getCurrencyID())));
				//sql.append(" nvl((select max(issue)+1 from sett_transreturnfinance where ncontractid='" + contractID + "' and nstatusid = 3),1))");
				sql.append("" + tempISSUE + "");
				System.out.println("=====================================================================================");
				System.out.println("进入还款计划新增时刻！");
				System.out.println("=====================================================================================");
			}
			else
			{
				//更新
				sql.append(" select max(issue) from sett_transreturnfinance where ncontractid='"+contractID+"' and nstatusid <> 0)");
				System.out.println("=====================================================================================");
				System.out.println("进入还款计划更新时刻！");
				System.out.println("=====================================================================================");				
			}
			
			conn=this.getConnection();
			System.out.println("===========================================================================================");
			System.out.println("提前还款计划表的计划期数的安排的SQL="+sql.toString());
			System.out.println("===========================================================================================");
			pstmt = conn.prepareStatement(sql.toString());
			res=pstmt.executeQuery();
			while(res.next())
			{
				info1.setIssue(res.getLong("issue"));
				info1.setIsDelay(0);//提前付款不会出现延付
				info1.setLoanContractPlanDetailID(res.getLong("id"));
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
				cleanup(res);
				cleanup(pstmt);
				cleanup(conn);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}	
		return info1;
	}
	
	/**
	 * @param contractids
	 * 
	 * 判断批量还款--链接查找--删除是否存在提前还款
	 * 
	 */
	public String checkHrefFindDel(String contractids) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet res=null;
		StringBuffer sql = new StringBuffer("select contractid,max(issue) as issue from (select * from loan_leaseholdrepayform order by issue desc) where  issue is not null  and  contractid in(  ");
		String param = "";
		String contractID[] = contractids.split("&");
		String returnValue = "";
		try 
		{
			for(int i=0;i<contractID.length;i++)
			{
				param=param+"?";
				if(i!=contractID.length-1)
				{
					param=param+",";
				}
				else
				{
					param=param+")";
					
				}
			}
			sql.append(param);
			sql.append(" group by contractid");
			conn=this.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			for(int j=1;j<=contractID.length;j++)
			{
				pstmt.setLong(j,Long.parseLong(contractID[j-1]));
			}
			 res = pstmt.executeQuery();
			 while(res.next())
			 {
				 returnValue = returnValue + "contract:";
				 returnValue = returnValue + res.getLong("contractid");
				 returnValue = returnValue + ",";
				 returnValue = returnValue + "issue:";
				 returnValue = returnValue + res.getLong("issue");
				 returnValue = returnValue + "&";
			 }
			
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			try 
			{
				cleanup(res);
				cleanup(pstmt);
				cleanup(conn);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}	
		
		return returnValue;
	}
			
}