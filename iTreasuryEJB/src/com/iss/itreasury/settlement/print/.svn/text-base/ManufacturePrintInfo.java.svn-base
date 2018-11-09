/*
 * Created on 2004-3-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.closesystem.securities.SecGLWithBean;
import com.iss.itreasury.closesystem.settlement.SettGLWithinDao;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.bizdelegation.TransFeeDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransGeneralLedgerDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransMarginDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransSecuritiesDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.ReportLossOrFreezeBean;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedChangeInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransMarginWithdrawDAO;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginInterestInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.util.*;
/**
 * @author gqzhang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ManufacturePrintInfo
{
	protected Log4j Log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * Method getTransFeePrintInfo.
	 * 设置交易费用处理
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	
	//18.交易费用处理
	public PrintInfo getTransFeePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			System.out.println("==========交易费用业务封装到PI里========开始!");
			TransFeeDelegation transFeeDelegation = new TransFeeDelegation();
			TransFeeInfo resultInfo = null;
			resultInfo = transFeeDelegation.findByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				
				printInfo.setTransTypeID( SETTConstant.TransactionType.TRANSFEE );
				
				/************************从此开始改写***********************/
				
				//FeeBankID
				
//				在银行付款中-bankid 为付款银行			
				if (resultInfo.getFeeBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getFeeBankID());
				}
				//在银行收款中-bankid 为收款银行			
				if (resultInfo.getFeeBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getFeeBankID());
				}
				
				//NRELATEDACCOUNTID			对应账户号
				//NACCOUNTID				支付费用账户号			
				
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getRelatedAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getRelatedAccountID());
				}
				
				//NRELATEDCLIENTID		支付费用客户号
				//								不知道
				
				if (resultInfo.getRelatedClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getRelatedClientID());
				}
				if (resultInfo.getRelatedClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getRelatedClientID());
				}
				
				//abstractID 			摘要ID 
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				
				//SEXTACCTNO			外部账户号
				//extAcctName			外部客户名称 
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				Log.print("外部账户号:" + printInfo.getExtAccountNo());
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
				}
				Log.print("外部客户名称:" + printInfo.getExtClientName());

				/*
				//在银行付款中-bankid 为付款银行			
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//在银行收款中-bankid 为收款银行			
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				
				//
				 
				if (resultInfo.getPayAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getPayAccountID());
				}
				if (resultInfo.getReceiveAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReceiveAccountID());
				}
				
				//
				
				if (resultInfo.getReceiveClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getReceiveClientID());
				}
				if (resultInfo.getPayClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getPayClientID());
				}
				
				//
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				
				//
				if (resultInfo.getExtAccountNo() != null && resultInfo.getExtAccountNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAccountNo());
				}
				Log.print("外部账户号:" + printInfo.getExtAccountNo());
				
				//
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtClientName());
				}
				Log.print("外部客户名称:" + printInfo.getExtClientName());
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				Log.print("外部银行:" + printInfo.getExtRemitInBank());
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				Log.print("外部银行省:" + printInfo.getExtRemitInProvince());
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				Log.print("外部银行市:" + printInfo.getExtRemitInCity());
				if (resultInfo.getAbstractStr() != null && resultInfo.getAbstractStr().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstractStr());
				}
				*/
				
				
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
			}
			System.out.println("==========交易费用业务封装到PI里========结束!");
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	
	/**
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public PrintInfo getChangeDepositPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			ReportLossOrFreezeBean delegation = new ReportLossOrFreezeBean();
			ReportLossOrFreezeInfo resultInfo = null;
			resultInfo = delegation.findByID(lID);
			if (resultInfo != null)
			{
				if (resultInfo.getTransNo() != null)
				{
					printInfo.setTransNo(resultInfo.getTransNo());	
				}
				if (resultInfo.getTransActionType() > 0)
				{
					printInfo.setTransactionTypeID(resultInfo.getTransActionType());
				}
				if (resultInfo.getClientId() > 0)
				{
					printInfo.setClientId(resultInfo.getClientId());
				}
				if (resultInfo.getAccountId() > 0)
				{
					printInfo.setAccountID(resultInfo.getAccountId());
				}
				if (resultInfo.getOldDepositNo() != null)
				{
					printInfo.setOldDepositNo(resultInfo.getOldDepositNo());
				}
				if (resultInfo.getNewDepositNo() != null)
				{
					printInfo.setNewDepositNo(resultInfo.getNewDepositNo());
				}
				if (resultInfo.getEffectiveDate() != null)
				{
					printInfo.setEffectiveDate(resultInfo.getEffectiveDate());
				}
				if (resultInfo.getFreezeTerm() > 0)
				{
					printInfo.setFreezeTerm(resultInfo.getFreezeTerm());
				}
				if (resultInfo.getFreezeEndDate() != null)
				{
					printInfo.setFreezeEndDate(resultInfo.getFreezeEndDate());
				}
				if (resultInfo.getFreezeAmount() != 0.0)
				{
					printInfo.setFreezeAmount(resultInfo.getFreezeAmount());
				}
				if (resultInfo.getSubAccountOldStatus() > 0)
				{
					printInfo.setSubAccountOldStatus(resultInfo.getSubAccountOldStatus());
				}
				if (resultInfo.getSubAccountNewStatus() > 0)
				{
					printInfo.setSubAccountNewStatus(resultInfo.getSubAccountNewStatus());
				}
				if (resultInfo.getExecuteGovernment() != null)
				{
					printInfo.setExecuteGovernment(resultInfo.getExecuteGovernment());
				}
				if (resultInfo.getApplyCompany() != null)
				{
					printInfo.setApplyCompany(resultInfo.getApplyCompany());
				}
				if (resultInfo.getLawWritNo() != null)
				{
					printInfo.setLawWritNo(resultInfo.getLawWritNo());
				}
				if (resultInfo.getAbstract() != null)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				if (resultInfo.getInputUserId() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserId());
				}
				if (resultInfo.getInputDate() != null)
				{
					printInfo.setInputDate(resultInfo.getInputDate());
				}
				if (resultInfo.getCheckUserId() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserId());
				}
				if (resultInfo.getCheckDate() != null)
				{
					printInfo.setCheckDate(resultInfo.getCheckDate());
				}
				if (resultInfo.getModifyDate() != null)
				{
					printInfo.setModifyDate(resultInfo.getModifyDate());
				}
				if (resultInfo.getStatus() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatus());
				}
				if (resultInfo.getOfficeId() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeId());
				}
				if (resultInfo.getCurrencyId() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyId());
				}
				if (resultInfo.getFreezeType() > 0)
				{
					printInfo.setFreezeType(resultInfo.getFreezeType());
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	
	//	1.银行收款 银行付款 内部转账 委托存款 保证金存款
	public PrintInfo getCurrentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
			TransCurrentDepositInfo resultInfo = null;
			resultInfo = depositDelegation.findBySett_TransCurrentDepositID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
//				//在银行付款中-bankid 为付款银行			
//				if (resultInfo.getBankID() > 0)
//				{
//					printInfo.setPayBankID(resultInfo.getBankID());
//				}
//				//在银行收款中-bankid 为收款银行			
//				if (resultInfo.getBankID() > 0)
//				{
//					printInfo.setReceiveBankID(resultInfo.getBankID());
//				}
				if (resultInfo.getPayAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getPayAccountID());
				}
				if (resultInfo.getReceiveAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReceiveAccountID());
				}
				if (resultInfo.getReceiveClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getReceiveClientID());
				}
				if (resultInfo.getPayClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getPayClientID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getExtAccountNo() != null && resultInfo.getExtAccountNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAccountNo());
				}
				Log.print("外部账户号:" + printInfo.getExtAccountNo());
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtClientName());
				}
				Log.print("外部客户名称:" + printInfo.getExtClientName());
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				Log.print("外部银行:" + printInfo.getExtRemitInBank());
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				Log.print("外部银行省:" + printInfo.getExtRemitInProvince());
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				Log.print("外部银行市:" + printInfo.getExtRemitInCity());
				if (resultInfo.getAbstractStr() != null && resultInfo.getAbstractStr().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstractStr());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	//	15.财务公司付款
	public PrintInfo getSecuritiesPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransSecuritiesDelegation depositDelegation = new TransSecuritiesDelegation();
			TransSecuritiesInfo resultInfo = null;
			resultInfo = depositDelegation.findByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_RECEIVE)
				{
					printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
				}
				else if(resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.SEC_WTLC_PAY)
				{
					printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());
				}
				//在银行付款中-bankid 为付款银行			
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
					printInfo.setPayExtAccountNo(NameRef.getBankAccountCodeByID(-1, resultInfo.getBankID()));
					printInfo.setPayExtClientName(NameRef.getBankAccountNameByID(-1, resultInfo.getBankID()));
					printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID()));
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getExtAccountNo() != null && resultInfo.getExtAccountNo().length() > 0)
				{
					printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
				}
				Log.print("外部账户号:" + printInfo.getExtAccountNo());
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
				}
				Log.print("外部客户名称:" + printInfo.getExtClientName());
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());
				}
				Log.print("外部银行:" + printInfo.getExtRemitInBank());
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				Log.print("外部银行省:" + printInfo.getExtRemitInProvince());
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
				}
				Log.print("外部银行市:" + printInfo.getExtRemitInCity());
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				if (resultInfo.getDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getFixDepositOpenPrintInfo.
	 * 设置定期开立 通知存款开立
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	2.定期开立 通知存款开立
	public PrintInfo getFixDepositOpenPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//查找开立信息
			TransFixedOpenInfo resultInfo = null;
			resultInfo = depositDelegation.openFindByID(lID);
			//添加开立信息
			if (resultInfo != null)
			{
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//开户日
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//起息日
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//开立客户ID，可据此得开户名称
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//账号，收款方账户号
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				//存单号
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//金额
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//付款方账户号ID,据此可得付款方全称，付款方账户号
				if (resultInfo.getCurrentAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
				}
				//付款方银行，据此可得银行名称
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//付款方
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//付款人汇入行名称
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				//省
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				//市
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				//期限
				if (resultInfo.getDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
				}
				//利率
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//品种
				if (resultInfo.getNoticeDay() > 0)
				{
					printInfo.setNoticeDay(resultInfo.getNoticeDay());
				}
				
				//added by mzh_fu 2007/04/11开始日期
				if (resultInfo.getStartDate() != null)
				{
					printInfo.setStartDate(resultInfo.getStartDate());
				}
				
				//结束日期
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//摘要
				if (resultInfo.getAbstract() !=null)
				{
				    printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}

	/**
	 * Method getFixDepositWithDrawPrintInfo.
	 * 设置定期支取 通知存款支取
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	3.定期支取 通知存款支取
	public PrintInfo getFixDepositWithDrawPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//查找支取信息
			TransFixedDrawInfo resultInfo = null;
			resultInfo = depositDelegation.drawFindByID(lID);
			//添加支取信息
			if (resultInfo != null)
			{
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//本金
//				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
//				{
//					if (resultInfo.getAmount() != 0.0)
//					{
//						printInfo.setAmount(resultInfo.getAmount());
//					}
//				}
				//－－－－－－－－－－－－－－－中交修改  打印不需要取本金，而需要打印提前支取的金额－－－－－－－－－－－－－－－－－－－－－－
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
					{
						if (resultInfo.getDrawAmount() != 0.0)
						{
							printInfo.setAmount(resultInfo.getDrawAmount());
						}
						else if(resultInfo.getAmount()!=0.0)
						{
							printInfo.setAmount(resultInfo.getAmount());
						}
					}
				
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					if (resultInfo.getDrawAmount() != 0.0)
					{
						printInfo.setAmount(resultInfo.getDrawAmount());
					}
				}
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtClientName());
				}
				//收款人汇入行名称
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				//收款人汇入省
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				//收款人汇入市
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//付款人账户id,付款方账号,付款人全称
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				//收款方人账号Id，可得收款人账户号，收款人全称
				if (resultInfo.getCurrentAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());
				}
				//收款人开户行id，可得PayBankCode,PayBankName，ReceiveBankCode
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				//子定期账户id
				if (resultInfo.getSubAccountID() > 0)
				{
					printInfo.setSubAccountID(resultInfo.getSubAccountID());
				}
				//账户号码，客户名称，据上面得付款人账户id取
				//利息
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					if (resultInfo.getDrawInterest() != 0.0)
					{
						printInfo.setPayableInterest(resultInfo.getDrawInterest());
					}
				}
				else
				{
					if (resultInfo.getTotalInterest() != 0.0)
					{
						printInfo.setPayableInterest(resultInfo.getTotalInterest());
					}
				}
				//活期利息
				if (resultInfo.getCurrentInterest() != 0.0)
				{
					printInfo.setCurrentInterest(resultInfo.getCurrentInterest());
				}
				Log.print("活期利息:" + printInfo.getCurrentInterest());
				//其它利息
				if (resultInfo.getOtherInterest() != 0.0)
				{
					printInfo.setOtherInterest(resultInfo.getOtherInterest());
				}
				Log.print("其它利息:" + printInfo.getOtherInterest());
				//收息账户号
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//利率
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//存单号码
				if (resultInfo.getDepositNo() != null || resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//存款类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//开始日期
				if (resultInfo.getStartDate() != null)
				{
					printInfo.setStartDate(resultInfo.getStartDate());
				}
				//结束日期
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//如果是通知存款，结束日期等于起息日
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					printInfo.setEndDate(resultInfo.getInterestStartDate());
					Log.print("通知结束日:" + printInfo.getEndDate());
				}
				
				
				//DepositNo存单编号
				if (resultInfo.getDepositNo() != null)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//支取日期,执行日期
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//实付利息,利息支付
				if (resultInfo.getPayableInterest() != 0.0)
				{
					printInfo.setPayableInterest(resultInfo.getPayableInterest());
				}
				//本息合计TotalInterest
				if (resultInfo.getTotalInterest() != 0.0)
				{
					printInfo.setTotalInterest(resultInfo.getTotalInterest());
				}
				//本息合计TotalInterest
				if (resultInfo.getAccountName() != null)
				{
					printInfo.setAccountName(resultInfo.getAccountName());
				}
				//本息合计TotalInterest
				if (resultInfo.getAccountNo() != null)
				{
					printInfo.setAccountNo(resultInfo.getAccountNo());
				}
//				活期利率
				if (resultInfo.getAdvanceRate() != 0.0)
				{
					printInfo.setAdvanceRate(resultInfo.getAdvanceRate());
				}
				
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * 
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public PrintInfo getMarginDepsitPrintInfo(String transNo) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
			TransMarginInterestInfo  resultInfo = null;
			
			resultInfo = dao.IfindByTransNo(transNo);
			System.out.println("gangkaishi ======="+resultInfo.getInterest());
			if(resultInfo != null)
			{
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getTransNo() != null)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransactionTypeID(resultInfo.getTransactionTypeID());
				}
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getSubAccountID() > 0)
				{
					printInfo.setSubAccountID(resultInfo.getSubAccountID());
				}
				if (resultInfo.getAccountTypeID() > 0)
				{
					printInfo.setAccountTypeID(resultInfo.getAccountTypeID());
				}
				if (resultInfo.getInterestType() > 0)
				{
					printInfo.setInterestType(resultInfo.getInterestType());
				}
				if (resultInfo.getOperationType() > 0)
				{
					printInfo.setOperationType(resultInfo.getOperationType());
				}
				if (resultInfo.getInterestSettment() != null)
				{
					printInfo.setInterestSettment(resultInfo.getInterestSettment());
				}
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStart(resultInfo.getInterestStart());
				}
				if (resultInfo.getInterestEnd() != null)
				{
					printInfo.setInterestEnd(resultInfo.getInterestEnd());
				}
				if (resultInfo.getInterestDays() > 0)
				{
					printInfo.setInterestDays(resultInfo.getInterestDays());
				}
				if (resultInfo.getBaseBalance() > 0)
				{
					printInfo.setBaseBalance(resultInfo.getBaseBalance());
				}
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				if (resultInfo.getNegotiateRate() != 0.0)
				{
					printInfo.setNegotiateRate(resultInfo.getNegotiateRate());
				}
				if (resultInfo.getInterestTaxRate() != 0.0)
				{
					printInfo.setInterestTaxRate(resultInfo.getInterestTaxRate());
				}
				if (resultInfo.getPecisionInterest() > 0)
				{
					printInfo.setPecisionInterest(resultInfo.getPecisionInterest());
				}
				if (resultInfo.getInterest() != 0.0)
				{
					printInfo.setInterest(resultInfo.getInterest());
				}
				if (resultInfo.getNegotiateBalance() > 0)
				{
					printInfo.setNegotiateBalance(resultInfo.getNegotiateBalance());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getNegotiatePecisionInterest() > 0)
				{
					printInfo.setNegotiatePecisionInterest(resultInfo.getNegotiatePecisionInterest());
				}
				if (resultInfo.getNegotiateInterest() > 0)
				{
					printInfo.setNegotiateInterest(resultInfo.getNegotiateInterest());
				}
				if (resultInfo.getInterestTax() > 0)
				{
					printInfo.setInterestTax(resultInfo.getInterestTax());
				}
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				if (resultInfo.getAbstract() != null)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getFaultReason() != null)
				{
					printInfo.setFaultReason(resultInfo.getFaultReason());
				}
				if (resultInfo.getIsSuccess() > 0)
				{
					printInfo.setIsSuccess(resultInfo.getIsSuccess());
				}
				if (resultInfo.getIsKeepAccount() > 0)
				{
					printInfo.setIsKeepAccount(resultInfo.getIsKeepAccount());
				}
				if (resultInfo.getIsSave() > 0)
				{
					printInfo.setIsSave(resultInfo.getIsSave());
				}
				if (resultInfo.getCheckAbstract() != null)
				{
					printInfo.setCheckAbstract(resultInfo.getCheckAbstract());
				}
				if (resultInfo.getAutoExecute() != null)
				{
					printInfo.setAutoExecute(resultInfo.getAutoExecute());
				}
				if (resultInfo.getBatchNo() > 0)
				{
					printInfo.setBatchNo(resultInfo.getBatchNo());
				}
				if (resultInfo.getInterestFeeSettingDetailID() > 0)
				{
					printInfo.setInterestFeeSettingDetailID(resultInfo.getInterestFeeSettingDetailID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}

				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}

				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}		
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getFixDepositContinuePrintInfo.
	 * 设置定期续存
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	4.定期续存
	public PrintInfo getFixDepositContinuePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//查找续存信息
			TransFixedContinueInfo resultInfo = null;
			resultInfo = depositDelegation.continueFindByID(lID);
			//添加续存信息
			if (resultInfo != null)
			{
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				//定期续存时开户日即开户日
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//起息日
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//利息外部银行
				if (resultInfo.getInterestExtAcctNo() != null && resultInfo.getInterestExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getInterestExtAcctNo());
				}
				if (resultInfo.getInterestExtClientName() != null && resultInfo.getInterestExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getInterestExtClientName());
				}
				if (resultInfo.getInterestRemitInBank() != null && resultInfo.getInterestRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getInterestRemitInBank());
				}
				if (resultInfo.getInterestRemitInProvince() != null && resultInfo.getInterestRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getInterestRemitInProvince());
				}
				if (resultInfo.getInterestRemitInCity() != null && resultInfo.getInterestRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getInterestRemitInCity());
				}
				//金额
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//存单号
				if (resultInfo.getNewDepositNo() != null && resultInfo.getNewDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getNewDepositNo());
				}
				//定期账户ID,可取账户户名,账号(在定期续存中收方和付方都是定期账户号)
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				//新存单定期存款期限
				if (resultInfo.getNewDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getNewDepositTerm());
				}
				//利率
				if (resultInfo.getNewRate() != 0.0)
			 	{
					printInfo.setNewRate(resultInfo.getNewRate());
				}
				//利率
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//付款方账户ID ，可得付款账户号(在定期续存中收方和付方都是定期账户号)
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				//合计利息
				if (resultInfo.getWithDrawInterest() != 0.0)
				{
					printInfo.setRealInterest(resultInfo.getWithDrawInterest());
				}
				//计提利息
				if (resultInfo.getPreDrawInterest() > 0)
				{
					printInfo.setRealInterestReceivable(resultInfo.getPreDrawInterest());
				}
				//利息支付
				if (resultInfo.getPayableInterest() > 0)
				{
					printInfo.setPayableInterest(resultInfo.getPayableInterest());
				}
				//收息账户号ID,据此可取收息账户号
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//定期子账户，据此可得终息信息
				if (resultInfo.getSubAccountID() > 0)
				{
					printInfo.setSubAccountID(resultInfo.getSubAccountID());
				}
				//旧存单开始(起始)日期
				if (resultInfo.getStartDate() != null)
				{
					printInfo.setStartDate(resultInfo.getStartDate());
				}
				//旧存单结束(截止)日期
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//存款类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				
				//是否本利续存		add by feiye 2006.5.17
				if(resultInfo.getIsCapitalAndInterestTransfer() > 0){
					printInfo.setIsCapitalAndInterestTransfer(resultInfo.getIsCapitalAndInterestTransfer());
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	
	/**
	 * Method getTransOpenMarginDepositPrintInfo
	 * 保证金开立 
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	public PrintInfo getTransOpenMarginDepositPrintInfo(long lID) throws Exception{
		PrintInfo printInfo = new PrintInfo();
		try
        {
		TransMarginDepositDelegation depositDelegation = new TransMarginDepositDelegation();
		TransMarginOpenInfo resultInfo = null;
		resultInfo = depositDelegation.openFindByID(lID);
        if (resultInfo != null)
		{
			if (resultInfo.getOfficeID() > 0)
			{
				printInfo.setOfficeID(resultInfo.getOfficeID());
			}
			if (resultInfo.getCurrencyID() > 0)
			{
				printInfo.setCurrencyID(resultInfo.getCurrencyID());
			}
			if (resultInfo.getStatusID() > 0)
			{
				printInfo.setStatusID(resultInfo.getStatusID());
			}
			if (resultInfo.getBankID() > 0)
			{
				printInfo.setPayBankID(resultInfo.getBankID());
			}
			if (resultInfo.getAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getAccountID());
			}
			if (resultInfo.getAbstractID() > 0)
			{
				printInfo.setAbstractID(resultInfo.getAbstractID());
			}
			if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getAbstract());
			}
			if (resultInfo.getInputUserID() > 0)
			{
				printInfo.setInputUserID(resultInfo.getInputUserID());
			}
			if (resultInfo.getCheckUserID() > 0)
			{
				printInfo.setCheckUserID(resultInfo.getCheckUserID());
			}
			if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getTransNo());
			}
			//开户日
			if (resultInfo.getExecuteDate() != null)
			{
				printInfo.setExecuteDate(resultInfo.getExecuteDate());
			}
			//起息日
			if (resultInfo.getInterestStartDate() != null)
			{
				printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
			}
			//开立客户ID，可据此得开户名称
			if (resultInfo.getClientID() > 0)
			{
				printInfo.setReceiveClientID(resultInfo.getClientID());
			}
			//账号，收款方账户号
			if (resultInfo.getAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getAccountID());
			}
			//存单号
			if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
			{
				printInfo.setFixedDepositNo(resultInfo.getDepositNo());
			}
			//金额
			if (resultInfo.getAmount() != 0.0)
			{
				printInfo.setAmount(resultInfo.getAmount());
			}
			//付款方账户号ID,据此可得付款方全称，付款方账户号
			if (resultInfo.getCurrentAccountID() > 0)
			{
				printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
			}
			//付款方银行，据此可得银行名称
			if (resultInfo.getBankID() > 0)
			{
				printInfo.setPayBankID(resultInfo.getBankID());
			}
			//付款方
			if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
			{
				printInfo.setFixedDepositNo(resultInfo.getDepositNo());
			}
			//付款人汇入行名称
			if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
			}
			//省
			if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
			{
				printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			//市
			if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
			{
				printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
			}
			//期限
			if (resultInfo.getDepositTerm() > 0)
			{
				printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
			}
			//利率
			if (resultInfo.getRate() != 0.0)
			{
				printInfo.setRate(resultInfo.getRate());
			}
			//品种
			if (resultInfo.getNoticeDay() > 0)
			{
				printInfo.setNoticeDay(resultInfo.getNoticeDay());
			}
			//结束日期
			if (resultInfo.getEndDate() != null)
			{
				printInfo.setEndDate(resultInfo.getEndDate());
			}
			//摘要
			if (resultInfo.getAbstract() !=null)
			{
			    printInfo.setAbstract(resultInfo.getAbstract());
			}
			if (resultInfo.getTransactionTypeID() > 0)
			{
				printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
			}
		}
     }
      catch (Exception exp)
      {
	   exp.printStackTrace();
	    throw exp;
      }
      finally
      {
      }
      return printInfo;
      }
		
	
	 /**
	 * Method getTransMarginWithdrawPrintInfo.
	 * 保证金支取
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
    public PrintInfo getTransMarginWithdrawPrintInfo(long lID) throws Exception{
		PrintInfo printInfo = new PrintInfo();
		try
		{
		TransMarginDepositDelegation depositDelegation = new TransMarginDepositDelegation();
		TransMarginWithdrawInfo resultInfo = null;
		resultInfo = depositDelegation.drawFindByID(lID);
		if (resultInfo != null)
		{
			if (resultInfo.getOfficeID() > 0)
			{
				printInfo.setOfficeID(resultInfo.getOfficeID());
			}
			if (resultInfo.getCurrencyID() > 0)
			{
				printInfo.setCurrencyID(resultInfo.getCurrencyID());
			}
			if (resultInfo.getStatusID() > 0)
			{
				printInfo.setStatusID(resultInfo.getStatusID());
			}
			if (resultInfo.getAbstractID() > 0)
			{
				printInfo.setAbstractID(resultInfo.getAbstractID());
			}
			if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getAbstract());
			}
			if (resultInfo.getInputUserID() > 0)
			{
				printInfo.setInputUserID(resultInfo.getInputUserID());
			}
			if (resultInfo.getCheckUserID() > 0)
			{
				printInfo.setCheckUserID(resultInfo.getCheckUserID());
			}
			if (resultInfo.getExecuteDate() != null)
			{
				printInfo.setExecuteDate(resultInfo.getExecuteDate());
			}
			if (resultInfo.getInterestStartDate() != null)
			{
				printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
			}
			if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getTransNo());
			}
			//本金		
			if (resultInfo.getAmount() != 0.0)
			{
				printInfo.setAmount(resultInfo.getDrawAmount());
			}
		
			
			if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
			{
				printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
			}
			if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
			{
				printInfo.setExtClientName(resultInfo.getExtClientName());
			}
			//收款人汇入行名称
			if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
			}
			//收款人汇入省
			if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
			{
				printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			//收款人汇入市
			if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
			{
				printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
			}
			if (resultInfo.getClientID() > 0)
			{
				printInfo.setReceiveClientID(resultInfo.getClientID());
			}
			//付款人账户id,付款方账号,付款人全称
			if (resultInfo.getAccountID() > 0)
			{
				printInfo.setPayAccountID(resultInfo.getAccountID());
			}
			//收款方人账号Id，可得收款人账户号，收款人全称
			if (resultInfo.getCurrentAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());
			}
			//收款人开户行id，可得PayBankCode,PayBankName，ReceiveBankCode
			if (resultInfo.getBankID() > 0)
			{
				printInfo.setReceiveBankID(resultInfo.getBankID());
			}
			//子定期账户id
			if (resultInfo.getSubAccountID() > 0)
			{
				printInfo.setSubAccountID(resultInfo.getSubAccountID());
			}
			//账户号码，客户名称，据上面得付款人账户id取
			//利息
			if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				if (resultInfo.getDrawInterest() != 0.0)
				{
					printInfo.setPayableInterest(resultInfo.getDrawInterest());
				}
			}
			else
			{
				if (resultInfo.getTotalInterest() != 0.0)
				{
					printInfo.setPayableInterest(resultInfo.getTotalInterest());
				}
			}
			//活期利息
			if (resultInfo.getCurrentInterest() != 0.0)
			{
				printInfo.setCurrentInterest(resultInfo.getCurrentInterest());
			}
			Log.print("活期利息:" + printInfo.getCurrentInterest());
			//其它利息
			if (resultInfo.getOtherInterest() != 0.0)
			{
				printInfo.setOtherInterest(resultInfo.getOtherInterest());
			}
			Log.print("其它利息:" + printInfo.getOtherInterest());
			//收息账户号
			if (resultInfo.getReceiveInterestAccountID() > 0)
			{
				printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
			}
			//利率
			if (resultInfo.getRate() != 0.0)
			{
				printInfo.setRate(resultInfo.getRate());
			}
			//存单号码
			if (resultInfo.getDepositNo() != null || resultInfo.getDepositNo().length() > 0)
			{
				printInfo.setFixedDepositNo(resultInfo.getDepositNo());
			}
			//存款类型
			if (resultInfo.getTransactionTypeID() > 0)
			{
				printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
			}
			//开始日期
			if (resultInfo.getStartDate() != null)
			{
				printInfo.setStartDate(resultInfo.getStartDate());
			}
			//结束日期
			if (resultInfo.getEndDate() != null)
			{
				printInfo.setEndDate(resultInfo.getEndDate());
			}
			//如果是通知存款，结束日期等于起息日
			if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				printInfo.setEndDate(resultInfo.getInterestStartDate());
				Log.print("通知结束日:" + printInfo.getEndDate());
			}
		}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	
	
	
	/**
	 * Method getLoanGrantPrintInfo.
	 * 设置自营贷款发放 委托贷款发放
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	5.自营贷款发放 委托贷款发放
	public PrintInfo getLoanGrantPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
			TransGrantLoanInfo resultInfo = null;
			resultInfo = transLoanDelegation.grantFindDetailByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				//set 打印参数
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getLoanContractID() > 0)
				{
					printInfo.setContractID(resultInfo.getLoanContractID());
				}
				if (resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				//放款类型
				//借款账户,即自营或者委托贷款账户号
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setLoanAccountID(resultInfo.getLoanAccountID());
				}
				Log.print("==========贷款账户号：" + printInfo.getLoanAccountID());
				Log.print("==========委托贷款账户号：" + NameRef.getAccountNoByID(printInfo.getLoanAccountID()));
				//收款方
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getDepositAccountID()));
				}
				//付款方  
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getLoanAccountID());
				}
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getLoanAccountID()));
				}
				if (resultInfo.getExtendFormID() > 0)
				{
					printInfo.setExtendFormID(resultInfo.getExtendFormID());
				}
				if (resultInfo.getPayTypeID() > 0)
				{
					printInfo.setPayTypeID(resultInfo.getPayTypeID());
				}
				//收款方--从银行--added by gqzhang 2003.11.29	
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				//外部账户号		
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				//	外部客户名称
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
				}
				//外部汇入行
				if (resultInfo.getBankName() != null && resultInfo.getBankName().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getBankName());
				}
				//收款方--从银行--added by gqzhang 2003.11.29
				if (resultInfo.getInterestTaxRateVauleDate() != null)
				{
					printInfo.setInterestTaxRateVauleDate(resultInfo.getInterestTaxRateVauleDate());
				}
				if (resultInfo.getInterestTaxRate() != 0.0)
				{
					printInfo.setInterestTaxRate(resultInfo.getInterestTaxRate());
				}
				if (resultInfo.getPayCommisionAccountID() > 0)
				{
					printInfo.setPayCommissionAccountID(resultInfo.getPayCommisionAccountID());
				}
				if (resultInfo.getReceiveSuretyFeeAccountID() > 0)
				{
					printInfo.setReceiveSuretyFeeAccountID(resultInfo.getReceiveSuretyFeeAccountID());
				}
				if (resultInfo.getPaySuretyFeeAccountID() > 0)
				{
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPaySuretyFeeAccountID());
				}
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getLoanRepaymentPrintInfo.
	 * 设置自营贷款收回 委托贷款收回
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	6.自营贷款收回 委托贷款收回
	public PrintInfo getLoanRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = transLoanDelegation.repaymentFindDetailByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				//set 打印参数
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				//上次结息日
				if (resultInfo.getLatestInterestClear() != null)
				{
					printInfo.setLatestInterestClearDate(resultInfo.getLatestInterestClear());
				}
				//结息日
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestClear());
				}
				//起息日
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				//委托单位id
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getConsignAccountID()));
				}
				//账户类型
				if (resultInfo.getLoanAccountID() > 0)
				{
					Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
					AccountInfo accountInfo = sett_AccountDAO.findByID(resultInfo.getLoanAccountID());
					if (accountInfo != null)
					{
						printInfo.setAccountTypeID(accountInfo.getAccountTypeID());
					}
				}
				//收款方
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getLoanAccountID());
				}
				//付款方客户	
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getClientID());
				}
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getDepositAccountID());
				}
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//当前余额
				if (resultInfo.getCurrentBalance() != 0.0)
				{
					printInfo.setCurrentBalance(resultInfo.getCurrentBalance());
				}
				if (resultInfo.getLoanContractID() > 0)
				{
					printInfo.setContractID(resultInfo.getLoanContractID());
				}
				if (resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				if (resultInfo.getPaySuretyAccountID() > 0)
				{
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPaySuretyAccountID());
				}
				if (resultInfo.getReceiveSuretyAccountID() > 0)
				{
					printInfo.setReceiveSuretyFeeAccountID(resultInfo.getReceiveSuretyAccountID());
				}
				if (resultInfo.getCommissionAccountID() > 0)
				{
					printInfo.setPayCommissionAccountID(resultInfo.getCommissionAccountID());
				}
				if (resultInfo.getFreeFormID() > 0)
				{
					printInfo.setFreeFormID(resultInfo.getFreeFormID());
				}
				if (resultInfo.getBillNo() != null && resultInfo.getBillNo().length() > 0)
				{
					printInfo.setBillNo(resultInfo.getBillNo());
				}
				if (resultInfo.getBillTypeID() > 0)
				{
					printInfo.setBillTypeID(resultInfo.getBillTypeID());
				}
				if (resultInfo.getBillBankID() > 0)
				{
					printInfo.setBillBankID(resultInfo.getBillBankID());
				}
				if (resultInfo.getExtBankNo() != null && resultInfo.getExtBankNo().length() > 0)
				{
					printInfo.setExtBankNo(resultInfo.getExtBankNo());
				}
				if (resultInfo.getPreFormID() > 0)
				{
					printInfo.setPreFormID(resultInfo.getPreFormID());
				}
				if (resultInfo.getInterestBankID() > 0)
				{
					printInfo.setPayInterestBankID(resultInfo.getInterestBankID());
				}
				if (resultInfo.getSuretyBankID() > 0)
				{
					printInfo.setPaySuertyFeeBankID(resultInfo.getSuretyBankID());
				}
				if (resultInfo.getCommissionBankID() > 0)
				{
					printInfo.setPayCommissionBankID(resultInfo.getCommissionBankID());
				}
				if (resultInfo.getInterest() != 0.0)
				{
					printInfo.setInterest(resultInfo.getInterest());
				}
				if (resultInfo.getInterestReceiveAble() != 0.0)
				{
					printInfo.setInterestReceivable(resultInfo.getInterestReceiveAble());
				}
				if (resultInfo.getInterestIncome() != 0.0)
				{
					printInfo.setInterestIncome(resultInfo.getInterestIncome());
				}
				if (resultInfo.getInterestTax() != 0.0)
				{
					printInfo.setInterestTax(resultInfo.getInterestTax());
				}
				if (resultInfo.getRealInterest() != 0.0)
				{
					printInfo.setRealInterest(resultInfo.getRealInterest());
				}
				if (resultInfo.getRealInterestReceiveAble() != 0.0)
				{
					printInfo.setRealInterestReceivable(resultInfo.getRealInterestReceiveAble());
				}
				if (resultInfo.getRealInterestIncome() != 0.0)
				{
					printInfo.setRealInterestIncome(resultInfo.getRealInterestIncome());
				}
				if (resultInfo.getRealInterestTax() != 0.0)
				{
					printInfo.setRealInterestTax(resultInfo.getRealInterestTax());
				}
				if (resultInfo.getRealCompoundInterest() != 0.0)
				{
					printInfo.setRealCompoundInterest(resultInfo.getRealCompoundInterest());
				}
				if (resultInfo.getCompoundAmount() != 0.0)
				{
					printInfo.setCompoundAmount(resultInfo.getCompoundAmount());
				}
				if (resultInfo.getCompoundInterest() != 0.0)
				{
					printInfo.setCompoundInterest(resultInfo.getCompoundInterest());
				}
				if (resultInfo.getCompoundRate() != 0.0)
				{
					printInfo.setCompoundRate(resultInfo.getCompoundRate() * 100);
				}
				if (resultInfo.getCompoundInterestStart() != null)
				{
					printInfo.setCompoundInterestStart(resultInfo.getCompoundInterestStart());
				}
				if (resultInfo.getRealOverDueInterest() != 0.0)
				{
					printInfo.setRealOverDueInterest(resultInfo.getRealOverDueInterest());
				}
				if (resultInfo.getOverDueAmount() != 0.0)
				{
					printInfo.setOverDueAmount(resultInfo.getOverDueAmount());
				}
				if (resultInfo.getOverDueInterest() != 0.0)
				{
					printInfo.setOverDueInterest(resultInfo.getOverDueInterest());
				}
				if (resultInfo.getOverDueStart() != null)
				{
					printInfo.setOverDueStart(resultInfo.getOverDueStart());
				}
				if (resultInfo.getOverDueRate() != 0.0)
				{
					printInfo.setOverDueRate(resultInfo.getOverDueRate() * 100);
				}
				if (resultInfo.getRealSuretyFee() != 0.0)
				{
					printInfo.setRealSuretyFee(resultInfo.getRealSuretyFee());
				}
				if (resultInfo.getSuretyFee() != 0.0)
				{
					printInfo.setSuretyFee(resultInfo.getSuretyFee());
				}
				if (resultInfo.getSuretyFeeStart() != null)
				{
					printInfo.setSuretyFeeStart(resultInfo.getSuretyFeeStart());
				}
				if (resultInfo.getSuretyFeeRate() != 0.0)
				{
					printInfo.setSuretyFeeRate(resultInfo.getSuretyFeeRate() * 100);
				}
				if (resultInfo.getRealCommission() != 0.0)
				{
					printInfo.setRealCommission(resultInfo.getRealCommission());
				}
				if (resultInfo.getCommission() != 0.0)
				{
					printInfo.setCommission(resultInfo.getCommission());
				}
				if (resultInfo.getCommissionStart() != null)
				{
					printInfo.setCommissionStart(resultInfo.getCommissionStart());
				}
				if (resultInfo.getCommissionRate() != 0.0)
				{
					printInfo.setCommissionRate(resultInfo.getCommissionRate() * 100);
				}
				if (resultInfo.getAdjustInterest() != 0.0)
				{
					printInfo.setAdjustInterest(resultInfo.getAdjustInterest());
				}
				//业务类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//贷款单位
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getConsignAccountID());
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getDiscountGrantPrintInfo.
	 * 设置 贴现发放
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	7.贴现发放
	public PrintInfo getDiscountGrantPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
			TransGrantDiscountInfo resultInfo = null;
			resultInfo = transDiscountDelegation.grantFindDetailByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				//set 打印参数
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				//收款方
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
				Log.print("收款方：" + resultInfo.getDepositAccountID());
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getDepositAccountID()));
				}
				//收款方--从银行--added by gqzhang 2003.11.29	
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				//外部账户号		
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				//	外部客户名称
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
				}
				//外部汇入行
				if (resultInfo.getBankName() != null && resultInfo.getBankName().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getBankName());
				}
				//收款方--从银行--added by gqzhang 2003.11.29
				//付款方  
				if (resultInfo.getDiscountAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getDiscountAccountID());
				}
				if (resultInfo.getDiscountAccountID() > 0)
				{
					printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getDiscountAccountID()));
				}
				//汇票金额
				if (resultInfo.getDiscountBillAmount() != 0.0)
				{
					printInfo.setDiscountBillAmount(resultInfo.getDiscountBillAmount());
				}
				//Log.print("汇票金额C006:"+printInfo.getDiscountBillAmount());
				//贴现汇票ID(贴现号码)
				if (resultInfo.getDiscountNoteID() > 0)
				{
					printInfo.setDiscountNoteID(resultInfo.getDiscountNoteID());
				}
				//持票人账号,进而获取持票人名称，持票人开户银行名称？？
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
				//实际贴现金额
				if (resultInfo.getDiscountAmount() != 0.0)
				{
					printInfo.setDiscountAmount(resultInfo.getDiscountAmount());
					printInfo.setAmount(resultInfo.getDiscountAmount());
				}
				//贴现利息
				if (resultInfo.getInterest() != 0.0)
				{
					printInfo.setDiscountInterestAmount(resultInfo.getInterest());
				}
				//贴现率，实际贴现金额/汇票金额
				/******************************
				// 一下参数需要在贷款中设置
				// 贴现模版中需要的参数
					public String DiscountType = ""; //贴现种类？   
					public String DateBillOut = "";//出票日？
					public String DateBillEnd = "";//到期日？
					public String BillOutName = "";//汇票出票人名称
					public String BillOutAccount = "";//汇票出票人账号
					public String BillOutBankName = "";//汇票出票开户行
					**************************/
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getDiscountRepaymentPrintInfo.
	 * 设置贴现收回
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	8.贴现收回
	public PrintInfo getDiscountRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
			TransRepaymentDiscountInfo resultInfo = null;
			resultInfo = transDiscountDelegation.repaymentFindDetailByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				//set 打印参数
				if (resultInfo.getNOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getNOfficeID());
				}
				if (resultInfo.getNCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getNCurrencyID());
				}
				if (resultInfo.getNStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getNStatusID());
				}
				if (resultInfo.getNTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getNTransactionTypeID());
				}
				if(resultInfo.getNDiscountContractID() >0)
				{
					printInfo.setContractID(resultInfo.getNDiscountContractID());
				}
				//  执行日，即贴现日
				if (resultInfo.getDtExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getDtExecute());
				}
				//起息日，即银行到账日
				if (resultInfo.getDtInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getDtInterestStart());
				}
				if (resultInfo.getSTransNo() != null && resultInfo.getSTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getSTransNo());
				}
				if (resultInfo.getSAbstract() != null && resultInfo.getSAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getSAbstract());
				}
				if (resultInfo.getNInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getNInputUserID());
				}
				if (resultInfo.getNCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getNCheckUserID());
				}
				//单据号
				if (resultInfo.getNDiscountNoteID() > 0)
				{
					printInfo.setFixedDepositNo(NameRef.getDiscountCredenceNoByID(resultInfo.getNDiscountNoteID()));
				}
				//贴现汇票ID(根据此ID取贴现汇票种类)
				if (resultInfo.getNDiscountNoteID() > 0)
				{
					printInfo.setDiscountNoteID(resultInfo.getNDiscountNoteID());
				}
				//汇票ID,(根据此ID可取汇票号码)
				if (resultInfo.getNDiscountBillID() > 0)
				{
					printInfo.setDiscountBillID(resultInfo.getNDiscountBillID());
				}
				//贴现单位账号，即收款方单位，根据此ID可以取贴现单位名称  
				if (resultInfo.getNDiscountAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getNDiscountAccountID());
				}
				//贴现收回金额
				if (resultInfo.getMAmount() != 0.0)
				{
					printInfo.setDiscountAmount(resultInfo.getMAmount());
					printInfo.setAmount(resultInfo.getMAmount()); //为了打印 特种转账借贷凭证 的金额
				}
				Log.print("贴现收回金额:" + printInfo.getDiscountAmount());
				//当前贴现余额
				if (resultInfo.getMSumReceiveAmout() != 0.0)
				{
					printInfo.setCurrentBalance(resultInfo.getMSumReceiveAmout());
				}
				Log.print("贴现收回余额:" + printInfo.getCurrentBalance());
				//付款银行ID,删除原因：在贴现收回交易中打印的特种转账借方凭证中付款方取退票处理中的活期账户号，
				//如果没有退票，则付款方显示为空
				/*	if (resultInfo.getNBankID() > 0)
					{
						printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getNBankID()));
						printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNBankID()));
					}
					*/
				//付款方
				if (resultInfo.getNDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getNDepositAccountID());
					Log.print("付款方账户号：" + printInfo.getPayAccountID());
				}
				//退票金额
				if (resultInfo.getMReturnedAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getMReturnedAmount());
				}
				if (resultInfo.getMOverDueInterest() > 0)
				{
					printInfo.setOverDueAmount(resultInfo.getMOverDueInterest());
				}
				Log.print("退票金额:" + resultInfo.getMReturnedAmount());
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getGLPrintInfo.
	 * 设置总账
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	9.总账
	public PrintInfo getGLPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransGeneralLedgerDelegation generalDelegation = new TransGeneralLedgerDelegation();
			TransGeneralLedgerInfo resultInfo = null;
			resultInfo = generalDelegation.findByID(lID);
			
			if (resultInfo != null)
			{
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getPayExtBankNo() != null && resultInfo.getPayExtBankNo().length() > 0)
				{
					//printInfo.setPayBankID(resultInfo.getBankID());
				}
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 1)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
					//printInfo.setPayGL();
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//开户日
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//起息日
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//开立客户ID，可据此得开户名称
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//账号，收款方账户号
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 1)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 2)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}								
				if( resultInfo.getGeneralLedgerOne () > 0 && resultInfo.getDirOne() == 1)
					printInfo.setPayGL(resultInfo.getGeneralLedgerOne ());
				if( resultInfo.getGeneralLedgerOne () > 0 && resultInfo.getDirOne() == 2)
					printInfo.setReceiveGL(resultInfo.getGeneralLedgerOne ());
				if( resultInfo.getGeneralLedgerTwo() > 0 && resultInfo.getDirTwo() == 1)
					printInfo.setPayGL(resultInfo.getGeneralLedgerTwo());
				if( resultInfo.getGeneralLedgerTwo() > 0 && resultInfo.getDirTwo() == 2)
					printInfo.setReceiveGL(resultInfo.getGeneralLedgerTwo());
				if( resultInfo.getGeneralLedgerOne () > 0 && resultInfo.getDirThree() == 1)
					printInfo.setPayGL(resultInfo.getGeneralLedgerThree());
				if( resultInfo.getGeneralLedgerThree() > 0 && resultInfo.getDirThree() == 2)
					printInfo.setReceiveGL(resultInfo.getGeneralLedgerThree());

				//金额
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//付款方账户号ID,据此可得付款方全称，付款方账户号
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 2)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
//				//付款方银行，据此可得银行名称
//				if (resultInfo.getBankID() > 0)
//				{
//					printInfo.setPayBankID(resultInfo.getBankID());
//				}
				//付款方
//				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
//				{
//					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
//				}
				//付款人汇入行名称
				if (resultInfo.getPayExtBankNo()!= null && resultInfo.getPayExtBankNo().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getPayExtBankNo());
				}
				//省
//				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
//				{
//					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
//				}
//				//市
//				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
//				{
//					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
//				}
//				//期限
//				if (resultInfo.getDepositTerm() > 0)
//				{
//					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
//				}
//				//利率
//				if (resultInfo.getRate() != 0.0)
//				{
//					printInfo.setRate(resultInfo.getRate());
//				}
//				//品种
//				if (resultInfo.getNoticeDay() > 0)
//				{
//					printInfo.setNoticeDay(resultInfo.getNoticeDay());
//				}
//				//结束日期
//				if (resultInfo.getEndDate() != null)
//				{
//					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//摘要
				if (resultInfo.getAbstract() !=null)
				{
				    printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getTransActionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransActionTypeID());
				}
			
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getSpecialPrintInfo.
	 * 设置特殊业务
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	10.特殊业务
	public PrintInfo getSpecialPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		
		try
		{
			TransSpecialOperationDelegation depositDelegation = new TransSpecialOperationDelegation();
			TransSpecialOperationInfo resultInfo=null;
			resultInfo = depositDelegation.findDetailByID(lID);
			
			
			
			if (resultInfo != null)
			{
				if (resultInfo.getNofficeid() > 0)
				{
					printInfo.setOfficeID(resultInfo.getNofficeid());
				}
				if (resultInfo.getNcurrencyid() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getNcurrencyid());
				}
				if (resultInfo.getNstatusid() > 0)
				{
					printInfo.setStatusID(resultInfo.getNstatusid());
				}
				if (resultInfo.getNreceiveaccountid() > 0 )
				{
					printInfo.setReceiveAccountID(resultInfo.getNreceiveaccountid());
				}
				if(resultInfo.getNreceivebankid() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getNreceivebankid());
				}
				if(resultInfo.getNreceivegeneralledgertypeid() > 0)
				{
					printInfo.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
				}
				if (resultInfo.getNabstractid() > 0)
				{
					printInfo.setAbstractID(resultInfo.getNabstractid());
				}
				if (resultInfo.getSabstract() != null && resultInfo.getSabstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getSabstract());
				}
				if (resultInfo.getNinputuserid() > 0)
				{
					printInfo.setInputUserID(resultInfo.getNinputuserid());
				}
				if (resultInfo.getNcheckuserid() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getNcheckuserid());
				}
				if (resultInfo.getStransno() != null && resultInfo.getStransno().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getStransno());
				}
				//开户日
				if (resultInfo.getDtexecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getDtexecute());
				}
				//起息日
				if (resultInfo.getDtintereststart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getDtintereststart());
				}
				//开立客户ID，可据此得开户名称
//				if (resultInfo.getClientID() > 0)
//				{
//					printInfo.setReceiveClientID(resultInfo.getClientID());
//				}
				//付方账户号
				if (resultInfo.getNpayaccountid() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getNpayaccountid());
				}
				if (resultInfo.getNpaybankid() > 0)
				{
					printInfo.setPayBankID(resultInfo.getNpaybankid());
				}
				if (resultInfo.getNpaygeneralledgertypeid() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getNpaygeneralledgertypeid());
				}
				
				//存单号
				if (resultInfo.getSpayfixeddepositno() != null && resultInfo.getSpayfixeddepositno().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getSpayfixeddepositno());
				}
				//金额
				if (resultInfo.getMpayamount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getMpayamount());
				}
				//付款人汇入行名称
				if (resultInfo.getSremitinbank()!= null && resultInfo.getSremitinbank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getSremitinbank());
				}
				//省
				if (resultInfo.getSremitinprovince() != null && resultInfo.getSremitinprovince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getSremitinprovince());
				}
				//市
				if (resultInfo.getSremitincity() != null && resultInfo.getSremitincity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getSremitincity());
				}

				//摘要
				if (resultInfo.getSabstract() !=null)
				{
				    printInfo.setAbstract(resultInfo.getSabstract() );
				}
				if (resultInfo.getNtransactiontypeid() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getNtransactiontypeid());
				}
			
			}
		}
			
			
			
		
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getOneMutiRepaymentPrintInfo
	 * 设置一付多收.
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	11.一付多收
	public PrintInfo getOneMutiRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
			TransOnePayMultiReceiveInfo resultInfo = null;
			resultInfo = depositDelegation.findBySett_TransOnePayMultiReceiveID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//交易号
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				
				//锦西代办处添加  多借多贷添加
				System.out.println("得到多借多贷里的银行ID："+resultInfo.getBankID());
				if(resultInfo.getPayGL()!=-1){
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				if(resultInfo.getReceiveGL()!=-1){
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				System.out.println("得到付款方银行ID："+printInfo.getPayBankID());
				System.out.println("得到收款方银行ID："+printInfo.getReceiveBankID());
				
				//根据交易号查找相关勾账交易记录信息
				TransOnePayMultiReceiveInfo findCondition = new TransOnePayMultiReceiveInfo(); //查询条件
				TransOnePayMultiReceiveInfo transOnePayMultiReceiveInfo = null; //查询结果
				TransOnePayMultiReceiveInfo tempTransOnePayMultiReceiveInfo = null; //在遍历的过程中存储收付方信息 
				Collection resultCollection = null;
				boolean bFlagPay = false;
				long lIndexPay = 0;
				boolean bFlagReceive = false;
				long lIndexReceive = 0;
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					findCondition.setTransNo(resultInfo.getTransNo());
					resultCollection = depositDelegation.findByConditions(findCondition, -1, false);
					if (resultCollection != null)
					{
						Iterator tempIterator = resultCollection.iterator();
						if (resultInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) //如果当前记录是付方
						{
							Log.print("当前记录为付方");
							while (tempIterator.hasNext())
							{
								transOnePayMultiReceiveInfo = (TransOnePayMultiReceiveInfo) tempIterator.next();
								if ((transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) && (transOnePayMultiReceiveInfo.getId() != resultInfo.getId()))
								{
									//存在和当前记录不同的付方
									bFlagPay = true;
									lIndexPay++;
								}
								if (transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE)
								{
									bFlagReceive = true;
									lIndexReceive++;
									tempTransOnePayMultiReceiveInfo = transOnePayMultiReceiveInfo;
								}
							}
							if (bFlagPay == false)
							{
								//当前记录是唯一的付方
								Log.print("当前记录为唯一付方");
								printInfo.setAmount(resultInfo.getAmount());								
								if (lIndexReceive == 1)
								{
									//收方唯一
									Log.print("收方唯一");
									//设置付方的信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID())); //银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getPayGL() > 0) //if总账转账
									{
										printInfo.setPayGL(resultInfo.getPayGL());
									}
									//设置收方信息	
									if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0 || tempTransOnePayMultiReceiveInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setReceiveAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0 || tempTransOnePayMultiReceiveInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getReceiveGL() > 0) //if总账转账
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
								}
								else
								{
									//收方不唯一
									Log.print("收方不唯一");
									//仅设置付方的信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										Log.print("付方-为内部转账");
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										Log.print("付方-为银行");
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID())); //银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getPayGL() > 0) //if总账转账
									{
										Log.print("付方-为总账");
										printInfo.setPayGL(resultInfo.getPayGL());
										Log.print("总账ID" + resultInfo.getPayGL());
									}
								}
							}
							else
							{
								//当前记录不是唯一的付方
								if (lIndexReceive == 1)
								{
									//收方唯一
									printInfo.setAmount(resultInfo.getAmount());
									//设置付方信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID())); //银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getPayGL() > 0) //if总账转账
									{
										printInfo.setPayGL(resultInfo.getPayGL());
									}
									//设置收方信息	
									if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0 || tempTransOnePayMultiReceiveInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setReceiveAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0 || tempTransOnePayMultiReceiveInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getReceiveGL() > 0) //if总账转账
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
								}
							}
						}
						else //如果当前记录是收方
							{
							Log.print("当前记录为收方");
							while (tempIterator.hasNext())
							{
								transOnePayMultiReceiveInfo = (TransOnePayMultiReceiveInfo) tempIterator.next();
								if (transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY)
								{
									bFlagPay = true;
									lIndexPay++;
									tempTransOnePayMultiReceiveInfo = transOnePayMultiReceiveInfo;
								}
								if ((transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE) && (transOnePayMultiReceiveInfo.getId() != resultInfo.getId()))
								{
									//存在与当前记录不同的收方
									bFlagReceive = true;
									lIndexReceive++;
								}
							}
							if (bFlagReceive == false)
							{
								//当前记录是唯一的收方
								Log.print("当前记录为唯一收方");
								printInfo.setAmount(resultInfo.getAmount());
								if (lIndexPay == 1)
								{
									//付方唯一
									//设置收方的信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setReceiveAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getReceiveGL() > 0) //if总账转账
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
									//设置付方信息	
									if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0 || tempTransOnePayMultiReceiveInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setPayAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0 || tempTransOnePayMultiReceiveInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID())); //银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getPayGL () > 0) //if总账转账
									{
										printInfo.setPayGL(resultInfo.getPayGL());
									}
								}
								else
								{
									//付方不唯一
									//仅设置收方的信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setReceiveAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getReceiveGL() > 0) //if总账转账
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
								}
							}
							else
							{
								Log.print("当前记录不是唯一的收方");
								//当前记录不是唯一的收方
								if (lIndexPay == 1)
								{
									//付方唯一
									Log.print("付方唯一");
									printInfo.setAmount(resultInfo.getAmount());
									//设置收方信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										Log.print("收方为内部转账");
										printInfo.setReceiveAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										Log.print("收方为银行");
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //银行转账不写开户行信息
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getReceiveGL() > 0) //if总账转账
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
									//设置付方信息	
									if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0 || tempTransOnePayMultiReceiveInfo.getAccountID() > 0) //if内部转账
									{
										Log.print("付方内部转账");
										printInfo.setPayAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0 || tempTransOnePayMultiReceiveInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										Log.print("付方银行");
										printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID())); //银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getPayGL() > 0) //if总账转账
									{
										Log.print("付方总账");
										printInfo.setPayGL(resultInfo.getPayGL());
									}
								}
							}
						}
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getInterestFeePaymentPrintInfo.
	 * 设置利息费用支付
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//14.利息费用支付
	public PrintInfo getInterestFeePaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		String strTemp = "";
		try
		{
			TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = transLoanDelegation.repaymentFindDetailByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				//set 打印参数
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				//上次结息日
				if (resultInfo.getLatestInterestClear() != null)
				{
					printInfo.setLatestInterestClearDate(resultInfo.getLatestInterestClear());
				}
				//结息日
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestClear());
				}
				//起息日
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//在利息费用支付中付方账户,也是付息账户号,付手续费，担保费账户号
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getPayInterestAccountID());
				}
				//付方银行，当付款方为银行时，开户行显示为空
				if (resultInfo.getInterestBankID() > 0)
				{
					printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getInterestBankID()));
					printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getInterestBankID()));
				}
				//收方账户
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//交易类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//在利息费用支付中，根据账户号设置业务类型
				long lAccountType = -1;
				if (resultInfo.getLoanAccountID() > 0)
					lAccountType = NameRef.getAccountTypeByID(resultInfo.getLoanAccountID());
	
				if (SETTConstant.AccountType.isTrustAccountType(lAccountType)) //自营
				{
					//交易类型
					printInfo.setTransTypeID(SETTConstant.TransactionType.TRUSTLOANRECEIVE);
					//账户类型
					printInfo.setAccountTypeID(lAccountType);
				}
				if (SETTConstant.AccountType.isConsignAccountType(lAccountType)) //委贷
				{
					//交易类型
					printInfo.setTransTypeID(SETTConstant.TransactionType.CONSIGNLOANRECEIVE);
					//账户类型
					printInfo.setAccountTypeID(lAccountType);
				}
				//借据号
				if (resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				//合同号
				if (resultInfo.getLoanContractID() > 0)
				{
					printInfo.setContractID(resultInfo.getLoanContractID());
				}
				if (resultInfo.getRealInterest() != 0.0)
				{
					printInfo.setRealInterest(resultInfo.getRealInterest());
				}
				if (resultInfo.getRealCompoundInterest() != 0.0)
				{
					printInfo.setRealCompoundInterest(resultInfo.getRealCompoundInterest());
				}
				if (resultInfo.getCompoundInterestStart() != null)
				{
					printInfo.setCompoundInterestStart(resultInfo.getCompoundInterestStart());
				}
				if (resultInfo.getCompoundAmount() != 0.0)
				{
					printInfo.setCompoundAmount(resultInfo.getCompoundAmount());
				}
				if (resultInfo.getCompoundRate() != 0.0)
				{
					printInfo.setCompoundRate(resultInfo.getCompoundRate() * 100);
				}
				if (resultInfo.getRealOverDueInterest() != 0.0)
				{
					printInfo.setRealOverDueInterest(resultInfo.getRealOverDueInterest());
				}
				if (resultInfo.getOverDueStart() != null)
				{
					printInfo.setOverDueStart(resultInfo.getOverDueStart());
				}
				if (resultInfo.getOverDueAmount() != 0.0)
				{
					printInfo.setOverDueAmount(resultInfo.getOverDueAmount());
				}
				if (resultInfo.getOverDueRate() != 0.0)
				{
					printInfo.setOverDueRate(resultInfo.getOverDueRate() * 100);
				}
				if (resultInfo.getRealSuretyFee() != 0.0)
				{
					printInfo.setRealSuretyFee(resultInfo.getRealSuretyFee());
				}
				if (resultInfo.getSuretyFeeStart() != null)
				{
					printInfo.setSuretyFeeStart(resultInfo.getSuretyFeeStart());
				}
				if (resultInfo.getSuretyFeeRate() != 0.0)
				{
					printInfo.setSuretyFeeRate(resultInfo.getSuretyFeeRate() * 100);
				}
				if (resultInfo.getRealCommission() != 0.0)
				{
					printInfo.setRealCommission(resultInfo.getRealCommission());
				}
				if (resultInfo.getCommissionStart() != null)
				{
					printInfo.setCommissionStart(resultInfo.getCommissionStart());
				}
				if (resultInfo.getCommissionRate() != 0.0)
				{
					Log.print("====手续费利率1：" + resultInfo.getCommissionRate());
					Log.print("====手续费利率2：" + resultInfo.getCommissionRate() * 100);
					printInfo.setCommissionRate(DataFormat.formatDouble(resultInfo.getCommissionRate() * 100, 6));
					Log.print("====手续费利率3：" + printInfo.getCommissionRate());
				}
				if (resultInfo.getRealInterestTax() != 0.0)
				{
					printInfo.setRealInterestTax(resultInfo.getRealInterestTax());
				}
				//利息总额（贷款利息通知单中设置为本金）
				printInfo.setAmount(
					DataFormat.formatDouble(
						DataFormat.formatDouble(resultInfo.getRealInterest())
							+ DataFormat.formatDouble(resultInfo.getRealCompoundInterest())
							+ DataFormat.formatDouble(resultInfo.getRealOverDueInterest())));
				//借款单位
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setBorrowClientID(NameRef.getClientIDByAccountID(resultInfo.getLoanAccountID()));
				}
				//账户
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getLoanAccountID());
				}
				//利息本金
				if (resultInfo.getCurrentBalance() != 0.0)
				{
					printInfo.setLoanBalance(resultInfo.getCurrentBalance());
				}
				//付息账号,也是付手续费和担保费的账户号
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPayInterestAccountID());
					printInfo.setPayCommissionAccountID(resultInfo.getPayInterestAccountID());
				}
				
				
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getFixDepositOpenPrintInfo.
	 * 设置定期开立 通知存款开立
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	13.换发证书 定期 通知存款 
	public PrintInfo getDepositChangePrintInfo(long id) throws Exception
	{
	    ReportLossOrFreezeBean bean = new ReportLossOrFreezeBean();
		PrintInfo printInfo = new PrintInfo();
		try
		{ 
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//查找开立信息
			TransFixedOpenInfo resultInfo = null;
			resultInfo = bean.findDepositById(id);
			//添加开立信息
			if (resultInfo != null)
			{
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//开户日
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//起息日
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//开立客户ID，可据此得开户名称
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//账号，收款方账户号
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				//存单号
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//金额
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//付款方账户号ID,据此可得付款方全称，付款方账户号
				if (resultInfo.getCurrentAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
				}
				//付款方银行，据此可得银行名称
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//付款方
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//付款人汇入行名称
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				//省
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				//市
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				//期限
				if (resultInfo.getDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
				}
				//利率
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//品种
				if (resultInfo.getNoticeDay() > 0)
				{
					printInfo.setNoticeDay(resultInfo.getNoticeDay());
				}
				//结束日期
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//摘要
				if (resultInfo.getAbstract() !=null)
				{
				    printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getSynLoanGrantPrintInfo.
	 * 设置银团贷款发放
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//16.银团贷款发放
	public PrintInfo getSynLoanGrantPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
			TransGrantLoanInfo resultInfo = null;
			resultInfo = transLoanDelegation.grantFindDetailByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				//set 打印参数
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getLoanContractID() > 0)
				{
					printInfo.setContractID(resultInfo.getLoanContractID());
				}
				if (resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				//放款类型
				//借款账户,即自营或者委托贷款账户号
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setLoanAccountID(resultInfo.getLoanAccountID());
				}
				//收款方
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getDepositAccountID()));
				}
				//付款方  
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getLoanAccountID());
				}
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getLoanAccountID()));
				}
				if (resultInfo.getExtendFormID() > 0)
				{
					printInfo.setExtendFormID(resultInfo.getExtendFormID());
				}
				if (resultInfo.getPayTypeID() > 0)
				{
					printInfo.setPayTypeID(resultInfo.getPayTypeID());
				}
				//收款方--从银行--added by gqzhang 2003.11.29	
				if (resultInfo.getBankID() > 0)
				{
					//printInfo.setReceiveBankID(resultInfo.getBankID());
					printInfo.setPayBankID(resultInfo.getBankID());
					
				}
				//外部账户号		
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				//	外部客户名称
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
				}
				//外部汇入行
				if (resultInfo.getBankName() != null && resultInfo.getBankName().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getBankName());
				}
				//收款方--从银行--added by gqzhang 2003.11.29
				if (resultInfo.getInterestTaxRateVauleDate() != null)
				{
					printInfo.setInterestTaxRateVauleDate(resultInfo.getInterestTaxRateVauleDate());
				}
				if (resultInfo.getInterestTaxRate() != 0.0)
				{
					printInfo.setInterestTaxRate(resultInfo.getInterestTaxRate());
				}
				if (resultInfo.getPayCommisionAccountID() > 0)
				{
					printInfo.setPayCommissionAccountID(resultInfo.getPayCommisionAccountID());
				}
				if (resultInfo.getReceiveSuretyFeeAccountID() > 0)
				{
					printInfo.setReceiveSuretyFeeAccountID(resultInfo.getReceiveSuretyFeeAccountID());
				}
				if (resultInfo.getPaySuretyFeeAccountID() > 0)
				{
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPaySuretyFeeAccountID());
				}
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//代理费利率
				if (resultInfo.getCommissionRate() > 0)
				{
					printInfo.setCommissionRate(resultInfo.getCommissionRate());
				}
				//贷款发放明细表
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				Vector vctDetail = new Vector();
				Collection collDetail = null;
				BankLoanDrawInfo bankLoanDrawInfo = null;
				collDetail = bankLoanQuery.findByFormID(resultInfo.getLoanNoteID());
				if (collDetail != null)
				{
					Iterator it = collDetail.iterator();
					while (it.hasNext())
					{
						bankLoanDrawInfo = (BankLoanDrawInfo) it.next();
						vctDetail.add(bankLoanDrawInfo);
					}
				}
				if (vctDetail != null)
				{
					Log.print("====== detail not null");
					printInfo.setDetail(vctDetail);
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	/**
	 * Method getSynLoanRepaymentPrintInfo.
	 * 设置银团收回
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//17.银团贷款收回
	public PrintInfo getSynLoanRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = transLoanDelegation.repaymentFindDetailByID(lID);
			if (resultInfo != null)
			{
				Log.print("result not null");
				//set 打印参数
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				//上次结息日
				if (resultInfo.getLatestInterestClear() != null)
				{
					printInfo.setLatestInterestClearDate(resultInfo.getLatestInterestClear());
				}
				//结息日
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestClear());
				}
				//起息日
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				//委托单位id
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getConsignAccountID()));
				}
				//账户类型
				if (resultInfo.getLoanAccountID() > 0)
				{
					Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
					AccountInfo accountInfo = sett_AccountDAO.findByID(resultInfo.getLoanAccountID());
					if (accountInfo != null)
					{
						printInfo.setAccountTypeID(accountInfo.getAccountTypeID());
					}
				}
				//收款方
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getLoanAccountID());
				}
				//付款方客户	
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getClientID());
				}
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getDepositAccountID());
				}
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//当前余额
				if (resultInfo.getCurrentBalance() != 0.0)
				{
					printInfo.setCurrentBalance(resultInfo.getCurrentBalance());
				}
				if (resultInfo.getLoanContractID() > 0)
				{
					printInfo.setContractID(resultInfo.getLoanContractID());
				}
				if (resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				if (resultInfo.getPaySuretyAccountID() > 0)
				{
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPaySuretyAccountID());
				}
				if (resultInfo.getReceiveSuretyAccountID() > 0)
				{
					printInfo.setReceiveSuretyFeeAccountID(resultInfo.getReceiveSuretyAccountID());
				}
				if (resultInfo.getCommissionAccountID() > 0)
				{
					printInfo.setPayCommissionAccountID(resultInfo.getCommissionAccountID());
				}
				if (resultInfo.getFreeFormID() > 0)
				{
					printInfo.setFreeFormID(resultInfo.getFreeFormID());
				}
				if (resultInfo.getBillNo() != null && resultInfo.getBillNo().length() > 0)
				{
					printInfo.setBillNo(resultInfo.getBillNo());
				}
				if (resultInfo.getBillTypeID() > 0)
				{
					printInfo.setBillTypeID(resultInfo.getBillTypeID());
				}
				if (resultInfo.getBillBankID() > 0)
				{
					printInfo.setBillBankID(resultInfo.getBillBankID());
				}
				if (resultInfo.getExtBankNo() != null && resultInfo.getExtBankNo().length() > 0)
				{
					printInfo.setExtBankNo(resultInfo.getExtBankNo());
				}
				if (resultInfo.getPreFormID() > 0)
				{
					printInfo.setPreFormID(resultInfo.getPreFormID());
				}
				if (resultInfo.getInterestBankID() > 0)
				{
					printInfo.setPayInterestBankID(resultInfo.getInterestBankID());
				}
				if (resultInfo.getSuretyBankID() > 0)
				{
					printInfo.setPaySuertyFeeBankID(resultInfo.getSuretyBankID());
				}
				if (resultInfo.getCommissionBankID() > 0)
				{
					printInfo.setPayCommissionBankID(resultInfo.getCommissionBankID());
				}
				if (resultInfo.getInterest() != 0.0)
				{
					printInfo.setInterest(resultInfo.getInterest());
				}
				if (resultInfo.getInterestReceiveAble() != 0.0)
				{
					printInfo.setInterestReceivable(resultInfo.getInterestReceiveAble());
				}
				if (resultInfo.getInterestIncome() != 0.0)
				{
					printInfo.setInterestIncome(resultInfo.getInterestIncome());
				}
				if (resultInfo.getInterestTax() != 0.0)
				{
					printInfo.setInterestTax(resultInfo.getInterestTax());
				}
				if (resultInfo.getRealInterest() != 0.0)
				{
					printInfo.setRealInterest(resultInfo.getRealInterest());
				}
				if (resultInfo.getRealInterestReceiveAble() != 0.0)
				{
					printInfo.setRealInterestReceivable(resultInfo.getRealInterestReceiveAble());
				}
				if (resultInfo.getRealInterestIncome() != 0.0)
				{
					printInfo.setRealInterestIncome(resultInfo.getRealInterestIncome());
				}
				if (resultInfo.getRealInterestTax() != 0.0)
				{
					printInfo.setRealInterestTax(resultInfo.getRealInterestTax());
				}
				if (resultInfo.getRealCompoundInterest() != 0.0)
				{
					printInfo.setRealCompoundInterest(resultInfo.getRealCompoundInterest());
				}
				if (resultInfo.getCompoundAmount() != 0.0)
				{
					printInfo.setCompoundAmount(resultInfo.getCompoundAmount());
				}
				if (resultInfo.getCompoundInterest() != 0.0)
				{
					printInfo.setCompoundInterest(resultInfo.getCompoundInterest());
				}
				if (resultInfo.getCompoundRate() != 0.0)
				{
					printInfo.setCompoundRate(resultInfo.getCompoundRate() * 100);
				}
				if (resultInfo.getCompoundInterestStart() != null)
				{
					printInfo.setCompoundInterestStart(resultInfo.getCompoundInterestStart());
				}
				if (resultInfo.getRealOverDueInterest() != 0.0)
				{
					printInfo.setRealOverDueInterest(resultInfo.getRealOverDueInterest());
				}
				if (resultInfo.getOverDueAmount() != 0.0)
				{
					printInfo.setOverDueAmount(resultInfo.getOverDueAmount());
				}
				if (resultInfo.getOverDueInterest() != 0.0)
				{
					printInfo.setOverDueInterest(resultInfo.getOverDueInterest());
				}
				if (resultInfo.getOverDueStart() != null)
				{
					printInfo.setOverDueStart(resultInfo.getOverDueStart());
				}
				if (resultInfo.getOverDueRate() != 0.0)
				{
					printInfo.setOverDueRate(resultInfo.getOverDueRate() * 100);
				}
				if (resultInfo.getRealSuretyFee() != 0.0)
				{
					printInfo.setRealSuretyFee(resultInfo.getRealSuretyFee());
				}
				if (resultInfo.getSuretyFee() != 0.0)
				{
					printInfo.setSuretyFee(resultInfo.getSuretyFee());
				}
				if (resultInfo.getSuretyFeeStart() != null)
				{
					printInfo.setSuretyFeeStart(resultInfo.getSuretyFeeStart());
				}
				if (resultInfo.getSuretyFeeRate() != 0.0)
				{
					printInfo.setSuretyFeeRate(resultInfo.getSuretyFeeRate() * 100);
				}
				if (resultInfo.getRealCommission() != 0.0)
				{
					printInfo.setRealCommission(resultInfo.getRealCommission());
				}
				if (resultInfo.getCommission() != 0.0)
				{
					printInfo.setCommission(resultInfo.getCommission());
				}
				if (resultInfo.getCommissionStart() != null)
				{
					printInfo.setCommissionStart(resultInfo.getCommissionStart());
				}
				if (resultInfo.getCommissionRate() != 0.0)
				{
					printInfo.setCommissionRate(resultInfo.getCommissionRate() * 100);
				}
				if (resultInfo.getAdjustInterest() != 0.0)
				{
					printInfo.setAdjustInterest(resultInfo.getAdjustInterest());
				}
				//业务类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//贷款单位
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getConsignAccountID());
				}
				//还款余额
				if (resultInfo.getCurrentBalance() > 0)
				{
					printInfo.setCurrentBalance(resultInfo.getCurrentBalance());
				}
				//计算牵头行以及参与行的本金
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				Vector vctDetail = new Vector();
				Vector vctWithOutHead = new Vector();
				Collection collDetail = null;
				BankLoanDrawInfo bankLoanDrawInfo = null;
				collDetail = bankLoanQuery.findByFormID(printInfo.getLoanNoteID());
				System.out.println("放款通知单ID:" + printInfo.getLoanNoteID());
				Iterator it = null;
				//贷款利息明细
				ArrayList arrayList = null;
				SyndicationLoanInterestInfo interestInfo = null;
				vctDetail = new Vector();
				it = null;
				arrayList = resultInfo.getSyndicationLoanInterest();
				if (arrayList != null)
				{
					it = arrayList.iterator();
					while (it.hasNext())
					{
						interestInfo = (SyndicationLoanInterestInfo) it.next();
						vctDetail.add(interestInfo);
					}
				}
				if (vctDetail != null)
				{
					Log.print("====== detail not null");
					printInfo.setDetail(vctDetail);
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	
	//	12.换开定期存单数据    2006.3.26    feiye   
	public PrintInfo getFixDepositChangePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//查找开立信息
			TransFixedChangeInfo resultInfo = null;
			resultInfo = depositDelegation.changeFindByID(lID);
			//添加开立信息
			if (resultInfo != null)
			{
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getStatusID() > 0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//开户日
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//起息日
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//开立客户ID，可据此得开户名称
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//账号，收款方账户号
				if (resultInfo.getAccountID() > 0)
				{
					//设置到外部账号里面
					//printInfo.setReceiveAccountID(-1);
					//得到账号
					printInfo.setExtAccountNo(NameRef.getAccountNoByID(resultInfo.getAccountID()));
					//得到户名
					printInfo.setExtClientName(NameRef.getAccountNameByID(resultInfo.getAccountID()));
				}
				//收款方--从银行--added by feiye 2006.3.27	
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				//存单号
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//金额
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//付款方账户号ID,据此可得付款方全称，付款方账户号
				if (resultInfo.getCurrentAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
				}
				//付款方银行，据此可得银行名称
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//付款方
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//付款人汇入行名称
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				//省
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				//市
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				//期限
				if (resultInfo.getDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
				}
				//利率
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//品种
				if (resultInfo.getNoticeDay() > 0)
				{
					printInfo.setNoticeDay(resultInfo.getNoticeDay());
				}
				//结束日期
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//摘要
				if (resultInfo.getAbstract() !=null)
				{
				    printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//换开定期存单号
				if(resultInfo.getDepositBillNO()!=null){
					printInfo.setDepositBillNO(resultInfo.getDepositBillNO());
				}

			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
		}
		return printInfo;
	}
	
	/*
	 *对于浦发财务要求补打凭证用    2006.4.10 add by feiye 
	 *
	 * 针对不同的业务类型高用不同的打印得到信息类，以获得相应的printInfo(PI)信息 
	 * 
	 */
	public PrintInfo getPrintInfo(long lID,long lTransactionTypeID) throws Exception{
		
		System.out.println("调用补打凭证信息----------（开始）lTransactionType:"+lTransactionTypeID);
		System.out.println("调用补打凭证信息----------（开始）lID:"+lID);
		PrintInfo printInfo = new PrintInfo();
		try
		{
			
			 //1.银行收款 银行付款 内部转账 委托存款 保证金存款
			if(lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE ||	//银行收款
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_GATHERING ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY ||		//银行付款
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY ||
				lTransactionTypeID == SETTConstant.TransactionType.INTERNALVIREMENT ||	//内部转账
				lTransactionTypeID == SETTConstant.TransactionType.CONSIGNSAVE ||		//委托存款
				lTransactionTypeID == SETTConstant.TransactionType.CAUTIONMONEYSAVE)		//保证金存款
			{
			
				System.out.println("调用补打凭证信息----------（活期账户组)-----------(开始)");
				printInfo = this.getCurrentPrintInfo(lID);			//	1.银行收款 银行付款 内部转账 委托存款 保证金存款
				System.out.println("调用补打凭证信息----------（活期账户组)-----------(结束)");
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.OPENFIXEDDEPOSIT ||	//定期开立 
					lTransactionTypeID==SETTConstant.TransactionType.OPENNOTIFYACCOUNT    //通知存款开立
			)
			{
				printInfo=this.getFixDepositOpenPrintInfo(lID);	//	2.定期开立 通知存款开立
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER ||	//定期支取  
					lTransactionTypeID==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW    //通知存款支取
			)
			{
				printInfo=this.getFixDepositWithDrawPrintInfo(lID);	//	3.定期支取 通知存款支取
			}	
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.FIXEDCONTINUETRANSFER 	//定期续存    
			)
			{
				printInfo=this.getFixDepositContinuePrintInfo(lID);	//	4.定期续存
			}	
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANGRANT ||	//自营贷款发放
					lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANGRANT 	//委托贷款发放
				
			)
			{
				printInfo=this.getLoanGrantPrintInfo(lID);	//	5.自营贷款发放 委托贷款发放
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANRECEIVE ||	//自营贷款收回
					lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANRECEIVE 	//委托贷款收回
			)
			{
				printInfo=this.getLoanRepaymentPrintInfo(lID);	//	6.自营贷款收回 委托贷款收回
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTGRANT 	//贴现发放
			)
			{
				printInfo=this.getDiscountGrantPrintInfo(lID);	//	7.贴现发放
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTRECEIVE 	//贴现收回
			)
			{
				printInfo=this.getDiscountRepaymentPrintInfo(lID);	//	8.贴现收回
			} 
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.GENERALLEDGER 	//总账
			)
			{
				printInfo=this.getGLPrintInfo(lID);	//	9.总账
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.SPECIALOPERATION 	//特殊业务
			)
			{
				printInfo=this.getSpecialPrintInfo(lID);	//	10.特殊业务				
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.ONETOMULTI || 	//一付多收
					lTransactionTypeID == SETTConstant.TransactionType.UPGATHERING	//资金上收
			)
			{
				printInfo=this.getOneMutiRepaymentPrintInfo(lID);	//	11.一付多收
			}
			
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTRECEIVE 	//换开定期存单数据	--kkf:没有作处理
			)
			{
				printInfo=this.getFixDepositChangePrintInfo(lID);	//	12.换开定期存单数据    2006.3.26    feiye
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.INTERESTFEEPAYMENT 	//利息费用支付
			)
			{
				printInfo=this.getInterestFeePaymentPrintInfo(lID);	//	14.利息费用支付
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.SECURITIESPAY 	//财务公司付款（证券投资结算）
			)
			{
				printInfo=this.getSecuritiesPrintInfo(lID);	//	15.财务公司付款（证券投资结算）
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.BANKGROUPLOANGRANT 	//银团贷款发放
			)
			{
				printInfo=this.getSynLoanGrantPrintInfo(lID);	//	16.银团贷款发放
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE 	//银团贷款收回
			)
			{
				printInfo=this.getSynLoanRepaymentPrintInfo(lID);	//	17.银团贷款收回
			} 
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.TRANSFEE 	//交易费用处理
			)
			{
				printInfo=this.getTransFeePrintInfo(lID);	//	18.交易费用处理
			}
			else if(
					lTransactionTypeID == SETTConstant.TransactionType.OPENMARGIN  //保证金开立
			)
			{
					
					Log.print("保证金开立");
					printInfo = this.getTransOpenMarginDepositPrintInfo(lID);
					
			}
			else if(
					lTransactionTypeID == SETTConstant.TransactionType.WITHDRAWMARGIN	//保证金支取
			)
			{
				Log.print("保证金支取");
				printInfo = this.getTransMarginWithdrawPrintInfo(lID);							
			}
			else if(
					lTransactionTypeID == SETTConstant.TransactionType.MULTILOANRECEIVE	//多笔贷款收回
			)
			{
				Log.print("多笔贷款收回这个没有做在settlement-print-control-co17.jsp中有定义");
			}
			else{
				printInfo=null;
				System.out.println("没有得到实际的业务类型所获得必要的打印信息的方法！,printInfo的返回值为空");
			}
			
			if(printInfo!=null){
				printInfo.setTransTypeID(lTransactionTypeID);
				System.out.println("调用补打凭证信息----------（结束）printInfo.getAmount():"+printInfo.getAmount());
			}
			
			
			/*
		        SETTConstant.TransactionType.CASHRECEIVE = 46; //现金收款

		        SETTConstant.TransactionType.CHECKPAY = 3; //支票付款

		        SETTConstant.TransactionType.CASHPAY = 4; //现金付款

		        SETTConstant.TransactionType.DRAFTPAY = 5; //票汇付款

		        

		        SETTConstant.TransactionType.CONSIGNRECEIVE = 7; //委托收款

		        

		       

		        SETTConstant.TransactionType.NATIONALDEBT_BUY = 10; //国债买入

		        SETTConstant.TransactionType.NATIONALDEBT_SELL = 34; //国债卖出

		        

		        SETTConstant.TransactionType.OTHERPAY = 47; //其他付款

		        SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE = 52; //下属单位银行收款

		        SETTConstant.TransactionType.SUBCLIENT_BANKPAY = 53; //下属单位银行付款

		        //定期业务
		        

		        SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER = 13; //定期转活期（定期支取）  

		       

		        //通知业务
		        

		       

		        //贷款业务
		        

		        

		        

		        

		        

		        

		        SETTConstant.TransactionType. = 23; //多笔贷款收回

		        //银团贷款
		        

		        

		        //委托业务（中油专用）
		        SETTConstant.TransactionType.CONSIGNUPRECEIVE = 24; //委托上收资金

		        SETTConstant.TransactionType.CONSIGNUPSAVE = 25; //上存资金调回及发放负息资金

		        SETTConstant.TransactionType.CONSIGNUPSAVERECEIVE = 26; //上存资金－上收及调回

		        SETTConstant.TransactionType.SHORTDEBTRECEIVE = 27; //还短负

		        SETTConstant.TransactionType.CONSIGNCAPITALOPERATION = 28; //客户委托资金调拨

		        SETTConstant.TransactionType.SHORTLOANGRANT = 29; //发放短期贷款

		        SETTConstant.TransactionType.CYCLOANGRANT = 30; //发放循环贷款

		        //其它
		        

		        

		        

		        SETTConstant.TransactionType.COMPATIBILITY = 108; //兼容交易

		        SETTConstant.TransactionType.TRANSABATEMENT = 109; //转贴现卖出自动冲销

		        //34已经被占用
		        SETTConstant.TransactionType.SHORTLOANRECEIVE = 35; //短期贷款收回

		        SETTConstant.TransactionType.INTERESTGRANT = 36; //发放利息

		        SETTConstant.TransactionType.SHUTDOWN = 37; //关机

		        SETTConstant.TransactionType.CYCLOANRECEIVE = 38; //循环贷款收回

		        SETTConstant.TransactionType.BANKUPSAVE = 40; //银行上收

		        SETTConstant.TransactionType.BANKUPRECEIVE = 41; //银行调回

		        SETTConstant.TransactionType.BANKINTEREST = 42; //银行发放负息资金

		        SETTConstant.TransactionType.CYCCONSIGNLOANGRANT = 43; //循环委托贷款发放

		        SETTConstant.TransactionType.CYCCONSIGNLOANRECEIVE = 44; //循环委托贷款收回

		        

		        //46已经被占用
		        //47已经被占用
		        //证券投资结算
		        SETTConstant.TransactionType.SECURITIESRECEIVE = 48; //财务公司收款（证券投资结算）

		       

		        SETTConstant.TransactionType.SEC_WTLC_RECEIVE = 56; //委托理财收款（证券投资结算）

		        SETTConstant.TransactionType.SEC_WTLC_PAY = 57; //委托理财付款（证券投资结算）

		        SETTConstant.TransactionType.SEC_ZQCX_RECEIVE = 58; //债券承销收款（证券投资结算）

		        SETTConstant.TransactionType.SEC_ZQCX_PAY = 59; //债券承销付款（证券投资结算）

		        //资金集中管理业务（国机专用）
		        SETTConstant.TransactionType.GRANT_DEBIT_INTEREST = 50; //发放负息资金

		        SETTConstant.TransactionType.RECEIVE_DEBIT_INTEREST = 51; //收回负息资金

		        //利息交易类型
		        SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT = 101; //利息费用支付活期结息

		        SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST = 102; //定期存款计提应付利息（含冲销）

		        SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST = 103; //自营贷款计提应收利息（含冲销）

		        SETTConstant.TransactionType.TRUST_LOAN_INTEREST = 104; //自营贷款结息

		        SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE = 105; //自营贷款结担保费

		        SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST = 106; //委托贷款结息

		        SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE = 107; //委托贷款结手续费

		        //108已经被占用
		        //109已经被占用
		        //银行收款业务(海尔专用)
		        SETTConstant.TransactionType.CHECK_RECEIVE = 110; //支票收款

		        SETTConstant.TransactionType.REMIT_RECEIVE = 111; //汇款收款

		        SETTConstant.TransactionType.OTHER_RECEIVE = 112; //其它收款

		        //银行付款业务(海尔专用)
		        SETTConstant.TransactionType.CHECK_PAY = 116; //支票付款

		        SETTConstant.TransactionType.REMIT_PAY = 117; //汇款付款

		        SETTConstant.TransactionType.TAX_PAY = 118; //税单付款

		        SETTConstant.TransactionType.OTHER_PAY = 119; //其它付款

		        //表外业务(上海电气专用)
		        SETTConstant.TransactionType.DELEGATION_INCOME_OFFBALANCE = 120;	//1.	代保管有价值品类表外业务收入
		        SETTConstant.TransactionType.DELEGATION_PAYOUT_OFFBALANCE = 121;	//1.	代保管有价值品类表外业务付出

		        SETTConstant.TransactionType.CONSIGN_INCOME_OFFBALANCE = 122;		//2.	贷款未收利息类表外业务收入
		        SETTConstant.TransactionType.CONSIGN_PAYOUT_OFFBALANCE = 123;		//2.	贷款未收利息类表外业务付出

		        SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE = 124;		//3.	商业汇票贴现类表外业务收入
		        SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE = 125;		//3.	商业汇票贴现类表外业务付出

		        SETTConstant.TransactionType.ASSURE_INCOME_OFFBALANCE = 126;		//4.	开出保函凭信类表外业务收入
		        SETTConstant.TransactionType.ASSURE_PAYOUT_OFFBALANCE = 127;		//4.	开出保函凭信类表外业务付出
		        //冻结、挂失
		        SETTConstant.TransactionType.REPORTLOSS = 130;		//挂失
		        
		        SETTConstant.TransactionType.REPORTFIND = 131;		//解挂
		        
		        SETTConstant.TransactionType.CHANGECERTIFICATE= 132;		//焕发证书
		        
		        SETTConstant.TransactionType.FREEZE = 133;		//冻结
		        
		        SETTConstant.TransactionType.DEFREEZE = 134;		//解冻
		        
		        //联通公司专用
				SETTConstant.TransactionType.FUND_REQUEST = 140;		//资金申领,活期类业务
				SETTConstant.TransactionType.FUND_RETURN = 141;		//资金上存,活期类业务
		        
				
		        
		        //收取手续费
		        SETTConstant.TransactionType.COMMISSION = 161;//交易手续费收取
		        
				//票据管理专用
				SETTConstant.TransactionType.BILL_REGISTER=201;		
				SETTConstant.TransactionType.BILL_USE=202;
				
				//南航财务新增 银行付款 交易类型
				SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY = 221;//银行付款－财司代付
				SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT = 222;//银行付款-拨子账户
		        
		        //南航财务新增 银行收款 交易类型
		        SETTConstant.TransactionType.BANKRECEIVE_GATHERING   = 231;//银行收款－上收款项 
		        SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT   = 232;//银行收款－成员单位收款 
		        SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT = 233;//银行收款－转成员单位收款
				
				//银行接口新增
		        SETTConstant.TransactionType.INITIATIVE_TURNIN = 501; //主动上收业务
		        //方便程序处理，用来区分是本金或利息的指令
		        SETTConstant.TransactionType.DRAW_PRINCIPAL = 502; //通知、定期支取本金
		        SETTConstant.TransactionType.DRAW_INTEREST = 503; //通知、定期支取利息

			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return printInfo;	//返回打印信息INFO类
	}
}
