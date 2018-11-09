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
	 * ���ý��׷��ô���
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	
	//18.���׷��ô���
	public PrintInfo getTransFeePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			System.out.println("==========���׷���ҵ���װ��PI��========��ʼ!");
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
				
				/************************�Ӵ˿�ʼ��д***********************/
				
				//FeeBankID
				
//				�����и�����-bankid Ϊ��������			
				if (resultInfo.getFeeBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getFeeBankID());
				}
				//�������տ���-bankid Ϊ�տ�����			
				if (resultInfo.getFeeBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getFeeBankID());
				}
				
				//NRELATEDACCOUNTID			��Ӧ�˻���
				//NACCOUNTID				֧�������˻���			
				
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getRelatedAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getRelatedAccountID());
				}
				
				//NRELATEDCLIENTID		֧�����ÿͻ���
				//								��֪��
				
				if (resultInfo.getRelatedClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getRelatedClientID());
				}
				if (resultInfo.getRelatedClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getRelatedClientID());
				}
				
				//abstractID 			ժҪID 
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				
				//SEXTACCTNO			�ⲿ�˻���
				//extAcctName			�ⲿ�ͻ����� 
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				Log.print("�ⲿ�˻���:" + printInfo.getExtAccountNo());
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
				}
				Log.print("�ⲿ�ͻ�����:" + printInfo.getExtClientName());

				/*
				//�����и�����-bankid Ϊ��������			
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//�������տ���-bankid Ϊ�տ�����			
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
				Log.print("�ⲿ�˻���:" + printInfo.getExtAccountNo());
				
				//
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtClientName());
				}
				Log.print("�ⲿ�ͻ�����:" + printInfo.getExtClientName());
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				Log.print("�ⲿ����:" + printInfo.getExtRemitInBank());
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				Log.print("�ⲿ����ʡ:" + printInfo.getExtRemitInProvince());
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				Log.print("�ⲿ������:" + printInfo.getExtRemitInCity());
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
			System.out.println("==========���׷���ҵ���װ��PI��========����!");
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
	
	//	1.�����տ� ���и��� �ڲ�ת�� ί�д�� ��֤����
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
//				//�����и�����-bankid Ϊ��������			
//				if (resultInfo.getBankID() > 0)
//				{
//					printInfo.setPayBankID(resultInfo.getBankID());
//				}
//				//�������տ���-bankid Ϊ�տ�����			
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
				Log.print("�ⲿ�˻���:" + printInfo.getExtAccountNo());
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtClientName());
				}
				Log.print("�ⲿ�ͻ�����:" + printInfo.getExtClientName());
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				Log.print("�ⲿ����:" + printInfo.getExtRemitInBank());
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				Log.print("�ⲿ����ʡ:" + printInfo.getExtRemitInProvince());
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				Log.print("�ⲿ������:" + printInfo.getExtRemitInCity());
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
	//	15.����˾����
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
				//�����и�����-bankid Ϊ��������			
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
				Log.print("�ⲿ�˻���:" + printInfo.getExtAccountNo());
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
				}
				Log.print("�ⲿ�ͻ�����:" + printInfo.getExtClientName());
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());
				}
				Log.print("�ⲿ����:" + printInfo.getExtRemitInBank());
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				Log.print("�ⲿ����ʡ:" + printInfo.getExtRemitInProvince());
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
				}
				Log.print("�ⲿ������:" + printInfo.getExtRemitInCity());
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
	 * ���ö��ڿ��� ֪ͨ����
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	2.���ڿ��� ֪ͨ����
	public PrintInfo getFixDepositOpenPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//���ҿ�����Ϣ
			TransFixedOpenInfo resultInfo = null;
			resultInfo = depositDelegation.openFindByID(lID);
			//��ӿ�����Ϣ
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
				//������
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//��Ϣ��
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//�����ͻ�ID���ɾݴ˵ÿ�������
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//�˺ţ��տ�˻���
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				//�浥��
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//���
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//����˻���ID,�ݴ˿ɵø��ȫ�ƣ�����˻���
				if (resultInfo.getCurrentAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
				}
				//������У��ݴ˿ɵ���������
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//���
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//�����˻���������
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				//ʡ
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				//��
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				//����
				if (resultInfo.getDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
				}
				//����
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//Ʒ��
				if (resultInfo.getNoticeDay() > 0)
				{
					printInfo.setNoticeDay(resultInfo.getNoticeDay());
				}
				
				//added by mzh_fu 2007/04/11��ʼ����
				if (resultInfo.getStartDate() != null)
				{
					printInfo.setStartDate(resultInfo.getStartDate());
				}
				
				//��������
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//ժҪ
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
	 * ���ö���֧ȡ ֪ͨ���֧ȡ
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	3.����֧ȡ ֪ͨ���֧ȡ
	public PrintInfo getFixDepositWithDrawPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//����֧ȡ��Ϣ
			TransFixedDrawInfo resultInfo = null;
			resultInfo = depositDelegation.drawFindByID(lID);
			//���֧ȡ��Ϣ
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
				//����
//				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
//				{
//					if (resultInfo.getAmount() != 0.0)
//					{
//						printInfo.setAmount(resultInfo.getAmount());
//					}
//				}
				//�������������������������������н��޸�  ��ӡ����Ҫȡ���𣬶���Ҫ��ӡ��ǰ֧ȡ�Ľ�������������������������������������������
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
				//�տ��˻���������
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				//�տ��˻���ʡ
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				//�տ��˻�����
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//�������˻�id,����˺�,������ȫ��
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				//�տ���˺�Id���ɵ��տ����˻��ţ��տ���ȫ��
				if (resultInfo.getCurrentAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());
				}
				//�տ��˿�����id���ɵ�PayBankCode,PayBankName��ReceiveBankCode
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				//�Ӷ����˻�id
				if (resultInfo.getSubAccountID() > 0)
				{
					printInfo.setSubAccountID(resultInfo.getSubAccountID());
				}
				//�˻����룬�ͻ����ƣ�������ø������˻�idȡ
				//��Ϣ
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
				//������Ϣ
				if (resultInfo.getCurrentInterest() != 0.0)
				{
					printInfo.setCurrentInterest(resultInfo.getCurrentInterest());
				}
				Log.print("������Ϣ:" + printInfo.getCurrentInterest());
				//������Ϣ
				if (resultInfo.getOtherInterest() != 0.0)
				{
					printInfo.setOtherInterest(resultInfo.getOtherInterest());
				}
				Log.print("������Ϣ:" + printInfo.getOtherInterest());
				//��Ϣ�˻���
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//����
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//�浥����
				if (resultInfo.getDepositNo() != null || resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//�������
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//��ʼ����
				if (resultInfo.getStartDate() != null)
				{
					printInfo.setStartDate(resultInfo.getStartDate());
				}
				//��������
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//�����֪ͨ���������ڵ�����Ϣ��
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					printInfo.setEndDate(resultInfo.getInterestStartDate());
					Log.print("֪ͨ������:" + printInfo.getEndDate());
				}
				
				
				//DepositNo�浥���
				if (resultInfo.getDepositNo() != null)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//֧ȡ����,ִ������
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//ʵ����Ϣ,��Ϣ֧��
				if (resultInfo.getPayableInterest() != 0.0)
				{
					printInfo.setPayableInterest(resultInfo.getPayableInterest());
				}
				//��Ϣ�ϼ�TotalInterest
				if (resultInfo.getTotalInterest() != 0.0)
				{
					printInfo.setTotalInterest(resultInfo.getTotalInterest());
				}
				//��Ϣ�ϼ�TotalInterest
				if (resultInfo.getAccountName() != null)
				{
					printInfo.setAccountName(resultInfo.getAccountName());
				}
				//��Ϣ�ϼ�TotalInterest
				if (resultInfo.getAccountNo() != null)
				{
					printInfo.setAccountNo(resultInfo.getAccountNo());
				}
//				��������
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
	 * ���ö�������
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	4.��������
	public PrintInfo getFixDepositContinuePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//����������Ϣ
			TransFixedContinueInfo resultInfo = null;
			resultInfo = depositDelegation.continueFindByID(lID);
			//���������Ϣ
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
				//��������ʱ�����ռ�������
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//��Ϣ��
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//��Ϣ�ⲿ����
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
				//���
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//�浥��
				if (resultInfo.getNewDepositNo() != null && resultInfo.getNewDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getNewDepositNo());
				}
				//�����˻�ID,��ȡ�˻�����,�˺�(�ڶ����������շ��͸������Ƕ����˻���)
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				//�´浥���ڴ������
				if (resultInfo.getNewDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getNewDepositTerm());
				}
				//����
				if (resultInfo.getNewRate() != 0.0)
			 	{
					printInfo.setNewRate(resultInfo.getNewRate());
				}
				//����
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//����˻�ID ���ɵø����˻���(�ڶ����������շ��͸������Ƕ����˻���)
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				//�ϼ���Ϣ
				if (resultInfo.getWithDrawInterest() != 0.0)
				{
					printInfo.setRealInterest(resultInfo.getWithDrawInterest());
				}
				//������Ϣ
				if (resultInfo.getPreDrawInterest() > 0)
				{
					printInfo.setRealInterestReceivable(resultInfo.getPreDrawInterest());
				}
				//��Ϣ֧��
				if (resultInfo.getPayableInterest() > 0)
				{
					printInfo.setPayableInterest(resultInfo.getPayableInterest());
				}
				//��Ϣ�˻���ID,�ݴ˿�ȡ��Ϣ�˻���
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//�������˻����ݴ˿ɵ���Ϣ��Ϣ
				if (resultInfo.getSubAccountID() > 0)
				{
					printInfo.setSubAccountID(resultInfo.getSubAccountID());
				}
				//�ɴ浥��ʼ(��ʼ)����
				if (resultInfo.getStartDate() != null)
				{
					printInfo.setStartDate(resultInfo.getStartDate());
				}
				//�ɴ浥����(��ֹ)����
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//�������
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				
				//�Ƿ�������		add by feiye 2006.5.17
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
	 * ��֤���� 
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
			//������
			if (resultInfo.getExecuteDate() != null)
			{
				printInfo.setExecuteDate(resultInfo.getExecuteDate());
			}
			//��Ϣ��
			if (resultInfo.getInterestStartDate() != null)
			{
				printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
			}
			//�����ͻ�ID���ɾݴ˵ÿ�������
			if (resultInfo.getClientID() > 0)
			{
				printInfo.setReceiveClientID(resultInfo.getClientID());
			}
			//�˺ţ��տ�˻���
			if (resultInfo.getAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getAccountID());
			}
			//�浥��
			if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
			{
				printInfo.setFixedDepositNo(resultInfo.getDepositNo());
			}
			//���
			if (resultInfo.getAmount() != 0.0)
			{
				printInfo.setAmount(resultInfo.getAmount());
			}
			//����˻���ID,�ݴ˿ɵø��ȫ�ƣ�����˻���
			if (resultInfo.getCurrentAccountID() > 0)
			{
				printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
			}
			//������У��ݴ˿ɵ���������
			if (resultInfo.getBankID() > 0)
			{
				printInfo.setPayBankID(resultInfo.getBankID());
			}
			//���
			if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
			{
				printInfo.setFixedDepositNo(resultInfo.getDepositNo());
			}
			//�����˻���������
			if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
			}
			//ʡ
			if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
			{
				printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			//��
			if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
			{
				printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
			}
			//����
			if (resultInfo.getDepositTerm() > 0)
			{
				printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
			}
			//����
			if (resultInfo.getRate() != 0.0)
			{
				printInfo.setRate(resultInfo.getRate());
			}
			//Ʒ��
			if (resultInfo.getNoticeDay() > 0)
			{
				printInfo.setNoticeDay(resultInfo.getNoticeDay());
			}
			//��������
			if (resultInfo.getEndDate() != null)
			{
				printInfo.setEndDate(resultInfo.getEndDate());
			}
			//ժҪ
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
	 * ��֤��֧ȡ
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
			//����		
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
			//�տ��˻���������
			if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
			}
			//�տ��˻���ʡ
			if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
			{
				printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			//�տ��˻�����
			if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
			{
				printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
			}
			if (resultInfo.getClientID() > 0)
			{
				printInfo.setReceiveClientID(resultInfo.getClientID());
			}
			//�������˻�id,����˺�,������ȫ��
			if (resultInfo.getAccountID() > 0)
			{
				printInfo.setPayAccountID(resultInfo.getAccountID());
			}
			//�տ���˺�Id���ɵ��տ����˻��ţ��տ���ȫ��
			if (resultInfo.getCurrentAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());
			}
			//�տ��˿�����id���ɵ�PayBankCode,PayBankName��ReceiveBankCode
			if (resultInfo.getBankID() > 0)
			{
				printInfo.setReceiveBankID(resultInfo.getBankID());
			}
			//�Ӷ����˻�id
			if (resultInfo.getSubAccountID() > 0)
			{
				printInfo.setSubAccountID(resultInfo.getSubAccountID());
			}
			//�˻����룬�ͻ����ƣ�������ø������˻�idȡ
			//��Ϣ
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
			//������Ϣ
			if (resultInfo.getCurrentInterest() != 0.0)
			{
				printInfo.setCurrentInterest(resultInfo.getCurrentInterest());
			}
			Log.print("������Ϣ:" + printInfo.getCurrentInterest());
			//������Ϣ
			if (resultInfo.getOtherInterest() != 0.0)
			{
				printInfo.setOtherInterest(resultInfo.getOtherInterest());
			}
			Log.print("������Ϣ:" + printInfo.getOtherInterest());
			//��Ϣ�˻���
			if (resultInfo.getReceiveInterestAccountID() > 0)
			{
				printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
			}
			//����
			if (resultInfo.getRate() != 0.0)
			{
				printInfo.setRate(resultInfo.getRate());
			}
			//�浥����
			if (resultInfo.getDepositNo() != null || resultInfo.getDepositNo().length() > 0)
			{
				printInfo.setFixedDepositNo(resultInfo.getDepositNo());
			}
			//�������
			if (resultInfo.getTransactionTypeID() > 0)
			{
				printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
			}
			//��ʼ����
			if (resultInfo.getStartDate() != null)
			{
				printInfo.setStartDate(resultInfo.getStartDate());
			}
			//��������
			if (resultInfo.getEndDate() != null)
			{
				printInfo.setEndDate(resultInfo.getEndDate());
			}
			//�����֪ͨ���������ڵ�����Ϣ��
			if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				printInfo.setEndDate(resultInfo.getInterestStartDate());
				Log.print("֪ͨ������:" + printInfo.getEndDate());
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
	 * ������Ӫ����� ί�д����
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	5.��Ӫ����� ί�д����
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
				//set ��ӡ����
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
				//�ſ�����
				//����˻�,����Ӫ����ί�д����˻���
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setLoanAccountID(resultInfo.getLoanAccountID());
				}
				Log.print("==========�����˻��ţ�" + printInfo.getLoanAccountID());
				Log.print("==========ί�д����˻��ţ�" + NameRef.getAccountNoByID(printInfo.getLoanAccountID()));
				//�տ
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getDepositAccountID()));
				}
				//���  
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
				//�տ--������--added by gqzhang 2003.11.29	
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				//�ⲿ�˻���		
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				//	�ⲿ�ͻ�����
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
				}
				//�ⲿ������
				if (resultInfo.getBankName() != null && resultInfo.getBankName().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getBankName());
				}
				//�տ--������--added by gqzhang 2003.11.29
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
	 * ������Ӫ�����ջ� ί�д����ջ�
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	6.��Ӫ�����ջ� ί�д����ջ�
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
				//set ��ӡ����
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
				//�ϴν�Ϣ��
				if (resultInfo.getLatestInterestClear() != null)
				{
					printInfo.setLatestInterestClearDate(resultInfo.getLatestInterestClear());
				}
				//��Ϣ��
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestClear());
				}
				//��Ϣ��
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
				//ί�е�λid
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getConsignAccountID()));
				}
				//�˻�����
				if (resultInfo.getLoanAccountID() > 0)
				{
					Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
					AccountInfo accountInfo = sett_AccountDAO.findByID(resultInfo.getLoanAccountID());
					if (accountInfo != null)
					{
						printInfo.setAccountTypeID(accountInfo.getAccountTypeID());
					}
				}
				//�տ
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getLoanAccountID());
				}
				//����ͻ�	
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
				//��ǰ���
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
				//ҵ������
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//���λ
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
	 * ���� ���ַ���
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	7.���ַ���
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
				//set ��ӡ����
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
				//�տ
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
				Log.print("�տ��" + resultInfo.getDepositAccountID());
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getDepositAccountID()));
				}
				//�տ--������--added by gqzhang 2003.11.29	
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				//�ⲿ�˻���		
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				//	�ⲿ�ͻ�����
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
				}
				//�ⲿ������
				if (resultInfo.getBankName() != null && resultInfo.getBankName().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getBankName());
				}
				//�տ--������--added by gqzhang 2003.11.29
				//���  
				if (resultInfo.getDiscountAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getDiscountAccountID());
				}
				if (resultInfo.getDiscountAccountID() > 0)
				{
					printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getDiscountAccountID()));
				}
				//��Ʊ���
				if (resultInfo.getDiscountBillAmount() != 0.0)
				{
					printInfo.setDiscountBillAmount(resultInfo.getDiscountBillAmount());
				}
				//Log.print("��Ʊ���C006:"+printInfo.getDiscountBillAmount());
				//���ֻ�ƱID(���ֺ���)
				if (resultInfo.getDiscountNoteID() > 0)
				{
					printInfo.setDiscountNoteID(resultInfo.getDiscountNoteID());
				}
				//��Ʊ���˺�,������ȡ��Ʊ�����ƣ���Ʊ�˿����������ƣ���
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
				//ʵ�����ֽ��
				if (resultInfo.getDiscountAmount() != 0.0)
				{
					printInfo.setDiscountAmount(resultInfo.getDiscountAmount());
					printInfo.setAmount(resultInfo.getDiscountAmount());
				}
				//������Ϣ
				if (resultInfo.getInterest() != 0.0)
				{
					printInfo.setDiscountInterestAmount(resultInfo.getInterest());
				}
				//�����ʣ�ʵ�����ֽ��/��Ʊ���
				/******************************
				// һ�²�����Ҫ�ڴ���������
				// ����ģ������Ҫ�Ĳ���
					public String DiscountType = ""; //�������ࣿ   
					public String DateBillOut = "";//��Ʊ�գ�
					public String DateBillEnd = "";//�����գ�
					public String BillOutName = "";//��Ʊ��Ʊ������
					public String BillOutAccount = "";//��Ʊ��Ʊ���˺�
					public String BillOutBankName = "";//��Ʊ��Ʊ������
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
	 * ���������ջ�
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	8.�����ջ�
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
				//set ��ӡ����
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
				//  ִ���գ���������
				if (resultInfo.getDtExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getDtExecute());
				}
				//��Ϣ�գ������е�����
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
				//���ݺ�
				if (resultInfo.getNDiscountNoteID() > 0)
				{
					printInfo.setFixedDepositNo(NameRef.getDiscountCredenceNoByID(resultInfo.getNDiscountNoteID()));
				}
				//���ֻ�ƱID(���ݴ�IDȡ���ֻ�Ʊ����)
				if (resultInfo.getNDiscountNoteID() > 0)
				{
					printInfo.setDiscountNoteID(resultInfo.getNDiscountNoteID());
				}
				//��ƱID,(���ݴ�ID��ȡ��Ʊ����)
				if (resultInfo.getNDiscountBillID() > 0)
				{
					printInfo.setDiscountBillID(resultInfo.getNDiscountBillID());
				}
				//���ֵ�λ�˺ţ����տ��λ�����ݴ�ID����ȡ���ֵ�λ����  
				if (resultInfo.getNDiscountAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getNDiscountAccountID());
				}
				//�����ջؽ��
				if (resultInfo.getMAmount() != 0.0)
				{
					printInfo.setDiscountAmount(resultInfo.getMAmount());
					printInfo.setAmount(resultInfo.getMAmount()); //Ϊ�˴�ӡ ����ת�˽��ƾ֤ �Ľ��
				}
				Log.print("�����ջؽ��:" + printInfo.getDiscountAmount());
				//��ǰ�������
				if (resultInfo.getMSumReceiveAmout() != 0.0)
				{
					printInfo.setCurrentBalance(resultInfo.getMSumReceiveAmout());
				}
				Log.print("�����ջ����:" + printInfo.getCurrentBalance());
				//��������ID,ɾ��ԭ���������ջؽ����д�ӡ������ת�˽跽ƾ֤�и��ȡ��Ʊ�����еĻ����˻��ţ�
				//���û����Ʊ���򸶿��ʾΪ��
				/*	if (resultInfo.getNBankID() > 0)
					{
						printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getNBankID()));
						printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNBankID()));
					}
					*/
				//���
				if (resultInfo.getNDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getNDepositAccountID());
					Log.print("����˻��ţ�" + printInfo.getPayAccountID());
				}
				//��Ʊ���
				if (resultInfo.getMReturnedAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getMReturnedAmount());
				}
				if (resultInfo.getMOverDueInterest() > 0)
				{
					printInfo.setOverDueAmount(resultInfo.getMOverDueInterest());
				}
				Log.print("��Ʊ���:" + resultInfo.getMReturnedAmount());
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
	 * ��������
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	9.����
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
				//������
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//��Ϣ��
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//�����ͻ�ID���ɾݴ˵ÿ�������
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//�˺ţ��տ�˻���
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

				//���
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//����˻���ID,�ݴ˿ɵø��ȫ�ƣ�����˻���
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 2)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
//				//������У��ݴ˿ɵ���������
//				if (resultInfo.getBankID() > 0)
//				{
//					printInfo.setPayBankID(resultInfo.getBankID());
//				}
				//���
//				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
//				{
//					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
//				}
				//�����˻���������
				if (resultInfo.getPayExtBankNo()!= null && resultInfo.getPayExtBankNo().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getPayExtBankNo());
				}
				//ʡ
//				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
//				{
//					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
//				}
//				//��
//				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
//				{
//					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
//				}
//				//����
//				if (resultInfo.getDepositTerm() > 0)
//				{
//					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
//				}
//				//����
//				if (resultInfo.getRate() != 0.0)
//				{
//					printInfo.setRate(resultInfo.getRate());
//				}
//				//Ʒ��
//				if (resultInfo.getNoticeDay() > 0)
//				{
//					printInfo.setNoticeDay(resultInfo.getNoticeDay());
//				}
//				//��������
//				if (resultInfo.getEndDate() != null)
//				{
//					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//ժҪ
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
	 * ��������ҵ��
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	10.����ҵ��
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
				//������
				if (resultInfo.getDtexecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getDtexecute());
				}
				//��Ϣ��
				if (resultInfo.getDtintereststart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getDtintereststart());
				}
				//�����ͻ�ID���ɾݴ˵ÿ�������
//				if (resultInfo.getClientID() > 0)
//				{
//					printInfo.setReceiveClientID(resultInfo.getClientID());
//				}
				//�����˻���
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
				
				//�浥��
				if (resultInfo.getSpayfixeddepositno() != null && resultInfo.getSpayfixeddepositno().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getSpayfixeddepositno());
				}
				//���
				if (resultInfo.getMpayamount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getMpayamount());
				}
				//�����˻���������
				if (resultInfo.getSremitinbank()!= null && resultInfo.getSremitinbank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getSremitinbank());
				}
				//ʡ
				if (resultInfo.getSremitinprovince() != null && resultInfo.getSremitinprovince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getSremitinprovince());
				}
				//��
				if (resultInfo.getSremitincity() != null && resultInfo.getSremitincity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getSremitincity());
				}

				//ժҪ
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
	 * ����һ������.
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	11.һ������
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
				//���׺�
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				
				//�������촦���  ��������
				System.out.println("�õ�������������ID��"+resultInfo.getBankID());
				if(resultInfo.getPayGL()!=-1){
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				if(resultInfo.getReceiveGL()!=-1){
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				System.out.println("�õ��������ID��"+printInfo.getPayBankID());
				System.out.println("�õ��տ����ID��"+printInfo.getReceiveBankID());
				
				//���ݽ��׺Ų�����ع��˽��׼�¼��Ϣ
				TransOnePayMultiReceiveInfo findCondition = new TransOnePayMultiReceiveInfo(); //��ѯ����
				TransOnePayMultiReceiveInfo transOnePayMultiReceiveInfo = null; //��ѯ���
				TransOnePayMultiReceiveInfo tempTransOnePayMultiReceiveInfo = null; //�ڱ����Ĺ����д洢�ո�����Ϣ 
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
						if (resultInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) //�����ǰ��¼�Ǹ���
						{
							Log.print("��ǰ��¼Ϊ����");
							while (tempIterator.hasNext())
							{
								transOnePayMultiReceiveInfo = (TransOnePayMultiReceiveInfo) tempIterator.next();
								if ((transOnePayMultiReceiveInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) && (transOnePayMultiReceiveInfo.getId() != resultInfo.getId()))
								{
									//���ں͵�ǰ��¼��ͬ�ĸ���
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
								//��ǰ��¼��Ψһ�ĸ���
								Log.print("��ǰ��¼ΪΨһ����");
								printInfo.setAmount(resultInfo.getAmount());								
								if (lIndexReceive == 1)
								{
									//�շ�Ψһ
									Log.print("�շ�Ψһ");
									//���ø�������Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID())); //����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getPayGL() > 0) //if����ת��
									{
										printInfo.setPayGL(resultInfo.getPayGL());
									}
									//�����շ���Ϣ	
									if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0 || tempTransOnePayMultiReceiveInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setReceiveAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0 || tempTransOnePayMultiReceiveInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getReceiveGL() > 0) //if����ת��
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
								}
								else
								{
									//�շ���Ψһ
									Log.print("�շ���Ψһ");
									//�����ø�������Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										Log.print("����-Ϊ�ڲ�ת��");
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										Log.print("����-Ϊ����");
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID())); //����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getPayGL() > 0) //if����ת��
									{
										Log.print("����-Ϊ����");
										printInfo.setPayGL(resultInfo.getPayGL());
										Log.print("����ID" + resultInfo.getPayGL());
									}
								}
							}
							else
							{
								//��ǰ��¼����Ψһ�ĸ���
								if (lIndexReceive == 1)
								{
									//�շ�Ψһ
									printInfo.setAmount(resultInfo.getAmount());
									//���ø�����Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID())); //����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getPayGL() > 0) //if����ת��
									{
										printInfo.setPayGL(resultInfo.getPayGL());
									}
									//�����շ���Ϣ	
									if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0 || tempTransOnePayMultiReceiveInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setReceiveAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0 || tempTransOnePayMultiReceiveInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getReceiveGL() > 0) //if����ת��
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
								}
							}
						}
						else //�����ǰ��¼���շ�
							{
							Log.print("��ǰ��¼Ϊ�շ�");
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
									//�����뵱ǰ��¼��ͬ���շ�
									bFlagReceive = true;
									lIndexReceive++;
								}
							}
							if (bFlagReceive == false)
							{
								//��ǰ��¼��Ψһ���շ�
								Log.print("��ǰ��¼ΪΨһ�շ�");
								printInfo.setAmount(resultInfo.getAmount());
								if (lIndexPay == 1)
								{
									//����Ψһ
									//�����շ�����Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setReceiveAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getReceiveGL() > 0) //if����ת��
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
									//���ø�����Ϣ	
									if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0 || tempTransOnePayMultiReceiveInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setPayAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0 || tempTransOnePayMultiReceiveInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID())); //����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getPayGL () > 0) //if����ת��
									{
										printInfo.setPayGL(resultInfo.getPayGL());
									}
								}
								else
								{
									//������Ψһ
									//�������շ�����Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setReceiveAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getReceiveGL() > 0) //if����ת��
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
								}
							}
							else
							{
								Log.print("��ǰ��¼����Ψһ���շ�");
								//��ǰ��¼����Ψһ���շ�
								if (lIndexPay == 1)
								{
									//����Ψһ
									Log.print("����Ψһ");
									printInfo.setAmount(resultInfo.getAmount());
									//�����շ���Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										Log.print("�շ�Ϊ�ڲ�ת��");
										printInfo.setReceiveAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										Log.print("�շ�Ϊ����");
										printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										printInfo.setReceiveExtAccountNo(resultInfo.getExtAccountNo());
										printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank()); //����ת�˲�д��������Ϣ
										printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
										printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getReceiveGL() > 0) //if����ת��
									{
										printInfo.setReceiveGL(resultInfo.getReceiveGL());
									}
									//���ø�����Ϣ	
									if (tempTransOnePayMultiReceiveInfo.getIsInternalVirement() > 0 || tempTransOnePayMultiReceiveInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										Log.print("�����ڲ�ת��");
										printInfo.setPayAccountID(tempTransOnePayMultiReceiveInfo.getAccountID());
									}
									if (tempTransOnePayMultiReceiveInfo.getIsBank() > 0 || tempTransOnePayMultiReceiveInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										Log.print("��������");
										printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID())); //����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getPayGL() > 0) //if����ת��
									{
										Log.print("��������");
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
	 * ������Ϣ����֧��
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//14.��Ϣ����֧��
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
				//set ��ӡ����
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
				//�ϴν�Ϣ��
				if (resultInfo.getLatestInterestClear() != null)
				{
					printInfo.setLatestInterestClearDate(resultInfo.getLatestInterestClear());
				}
				//��Ϣ��
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestClear());
				}
				//��Ϣ��
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
				//����Ϣ����֧���и����˻�,Ҳ�Ǹ�Ϣ�˻���,�������ѣ��������˻���
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getPayInterestAccountID());
				}
				//�������У������Ϊ����ʱ����������ʾΪ��
				if (resultInfo.getInterestBankID() > 0)
				{
					printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getInterestBankID()));
					printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getInterestBankID()));
				}
				//�շ��˻�
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//��������
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//����Ϣ����֧���У������˻�������ҵ������
				long lAccountType = -1;
				if (resultInfo.getLoanAccountID() > 0)
					lAccountType = NameRef.getAccountTypeByID(resultInfo.getLoanAccountID());
	
				if (SETTConstant.AccountType.isTrustAccountType(lAccountType)) //��Ӫ
				{
					//��������
					printInfo.setTransTypeID(SETTConstant.TransactionType.TRUSTLOANRECEIVE);
					//�˻�����
					printInfo.setAccountTypeID(lAccountType);
				}
				if (SETTConstant.AccountType.isConsignAccountType(lAccountType)) //ί��
				{
					//��������
					printInfo.setTransTypeID(SETTConstant.TransactionType.CONSIGNLOANRECEIVE);
					//�˻�����
					printInfo.setAccountTypeID(lAccountType);
				}
				//��ݺ�
				if (resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				//��ͬ��
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
					Log.print("====����������1��" + resultInfo.getCommissionRate());
					Log.print("====����������2��" + resultInfo.getCommissionRate() * 100);
					printInfo.setCommissionRate(DataFormat.formatDouble(resultInfo.getCommissionRate() * 100, 6));
					Log.print("====����������3��" + printInfo.getCommissionRate());
				}
				if (resultInfo.getRealInterestTax() != 0.0)
				{
					printInfo.setRealInterestTax(resultInfo.getRealInterestTax());
				}
				//��Ϣ�ܶ������Ϣ֪ͨ��������Ϊ����
				printInfo.setAmount(
					DataFormat.formatDouble(
						DataFormat.formatDouble(resultInfo.getRealInterest())
							+ DataFormat.formatDouble(resultInfo.getRealCompoundInterest())
							+ DataFormat.formatDouble(resultInfo.getRealOverDueInterest())));
				//��λ
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setBorrowClientID(NameRef.getClientIDByAccountID(resultInfo.getLoanAccountID()));
				}
				//�˻�
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getLoanAccountID());
				}
				//��Ϣ����
				if (resultInfo.getCurrentBalance() != 0.0)
				{
					printInfo.setLoanBalance(resultInfo.getCurrentBalance());
				}
				//��Ϣ�˺�,Ҳ�Ǹ������Ѻ͵����ѵ��˻���
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
	 * ���ö��ڿ��� ֪ͨ����
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//	13.����֤�� ���� ֪ͨ��� 
	public PrintInfo getDepositChangePrintInfo(long id) throws Exception
	{
	    ReportLossOrFreezeBean bean = new ReportLossOrFreezeBean();
		PrintInfo printInfo = new PrintInfo();
		try
		{ 
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//���ҿ�����Ϣ
			TransFixedOpenInfo resultInfo = null;
			resultInfo = bean.findDepositById(id);
			//��ӿ�����Ϣ
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
				//������
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//��Ϣ��
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//�����ͻ�ID���ɾݴ˵ÿ�������
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//�˺ţ��տ�˻���
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				//�浥��
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//���
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//����˻���ID,�ݴ˿ɵø��ȫ�ƣ�����˻���
				if (resultInfo.getCurrentAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
				}
				//������У��ݴ˿ɵ���������
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//���
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//�����˻���������
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				//ʡ
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				//��
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				//����
				if (resultInfo.getDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
				}
				//����
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//Ʒ��
				if (resultInfo.getNoticeDay() > 0)
				{
					printInfo.setNoticeDay(resultInfo.getNoticeDay());
				}
				//��������
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//ժҪ
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
	 * �������Ŵ����
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//16.���Ŵ����
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
				//set ��ӡ����
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
				//�ſ�����
				//����˻�,����Ӫ����ί�д����˻���
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setLoanAccountID(resultInfo.getLoanAccountID());
				}
				//�տ
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getDepositAccountID()));
				}
				//���  
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
				//�տ--������--added by gqzhang 2003.11.29	
				if (resultInfo.getBankID() > 0)
				{
					//printInfo.setReceiveBankID(resultInfo.getBankID());
					printInfo.setPayBankID(resultInfo.getBankID());
					
				}
				//�ⲿ�˻���		
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				//	�ⲿ�ͻ�����
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
				}
				//�ⲿ������
				if (resultInfo.getBankName() != null && resultInfo.getBankName().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getBankName());
				}
				//�տ--������--added by gqzhang 2003.11.29
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
				//���������
				if (resultInfo.getCommissionRate() > 0)
				{
					printInfo.setCommissionRate(resultInfo.getCommissionRate());
				}
				//�������ϸ��
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
	 * ���������ջ�
	 * @param lID
	 * @return PrintInfo
	 * @throws Exception
	 */
	//17.���Ŵ����ջ�
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
				//set ��ӡ����
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
				//�ϴν�Ϣ��
				if (resultInfo.getLatestInterestClear() != null)
				{
					printInfo.setLatestInterestClearDate(resultInfo.getLatestInterestClear());
				}
				//��Ϣ��
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestClear());
				}
				//��Ϣ��
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
				//ί�е�λid
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getConsignAccountID()));
				}
				//�˻�����
				if (resultInfo.getLoanAccountID() > 0)
				{
					Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
					AccountInfo accountInfo = sett_AccountDAO.findByID(resultInfo.getLoanAccountID());
					if (accountInfo != null)
					{
						printInfo.setAccountTypeID(accountInfo.getAccountTypeID());
					}
				}
				//�տ
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getLoanAccountID());
				}
				//����ͻ�	
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
				//��ǰ���
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
				//ҵ������
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//���λ
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getConsignAccountID());
				}
				//�������
				if (resultInfo.getCurrentBalance() > 0)
				{
					printInfo.setCurrentBalance(resultInfo.getCurrentBalance());
				}
				//����ǣͷ���Լ������еı���
				BankLoanQuery bankLoanQuery = new BankLoanQuery();
				Vector vctDetail = new Vector();
				Vector vctWithOutHead = new Vector();
				Collection collDetail = null;
				BankLoanDrawInfo bankLoanDrawInfo = null;
				collDetail = bankLoanQuery.findByFormID(printInfo.getLoanNoteID());
				System.out.println("�ſ�֪ͨ��ID:" + printInfo.getLoanNoteID());
				Iterator it = null;
				//������Ϣ��ϸ
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
	
	//	12.�������ڴ浥����    2006.3.26    feiye   
	public PrintInfo getFixDepositChangePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
			//���ҿ�����Ϣ
			TransFixedChangeInfo resultInfo = null;
			resultInfo = depositDelegation.changeFindByID(lID);
			//��ӿ�����Ϣ
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
				//������
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//��Ϣ��
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				//�����ͻ�ID���ɾݴ˵ÿ�������
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getClientID());
				}
				//�˺ţ��տ�˻���
				if (resultInfo.getAccountID() > 0)
				{
					//���õ��ⲿ�˺�����
					//printInfo.setReceiveAccountID(-1);
					//�õ��˺�
					printInfo.setExtAccountNo(NameRef.getAccountNoByID(resultInfo.getAccountID()));
					//�õ�����
					printInfo.setExtClientName(NameRef.getAccountNameByID(resultInfo.getAccountID()));
				}
				//�տ--������--added by feiye 2006.3.27	
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				//�浥��
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//���
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//����˻���ID,�ݴ˿ɵø��ȫ�ƣ�����˻���
				if (resultInfo.getCurrentAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
				}
				//������У��ݴ˿ɵ���������
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//���
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//�����˻���������
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				//ʡ
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				//��
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
				//����
				if (resultInfo.getDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
				}
				//����
				if (resultInfo.getRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getRate());
				}
				//Ʒ��
				if (resultInfo.getNoticeDay() > 0)
				{
					printInfo.setNoticeDay(resultInfo.getNoticeDay());
				}
				//��������
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//ժҪ
				if (resultInfo.getAbstract() !=null)
				{
				    printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//�������ڴ浥��
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
	 *�����ַ�����Ҫ�󲹴�ƾ֤��    2006.4.10 add by feiye 
	 *
	 * ��Բ�ͬ��ҵ�����͸��ò�ͬ�Ĵ�ӡ�õ���Ϣ�࣬�Ի����Ӧ��printInfo(PI)��Ϣ 
	 * 
	 */
	public PrintInfo getPrintInfo(long lID,long lTransactionTypeID) throws Exception{
		
		System.out.println("���ò���ƾ֤��Ϣ----------����ʼ��lTransactionType:"+lTransactionTypeID);
		System.out.println("���ò���ƾ֤��Ϣ----------����ʼ��lID:"+lID);
		PrintInfo printInfo = new PrintInfo();
		try
		{
			
			 //1.�����տ� ���и��� �ڲ�ת�� ί�д�� ��֤����
			if(lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE ||	//�����տ�
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_GATHERING ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY ||		//���и���
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY ||
				lTransactionTypeID == SETTConstant.TransactionType.INTERNALVIREMENT ||	//�ڲ�ת��
				lTransactionTypeID == SETTConstant.TransactionType.CONSIGNSAVE ||		//ί�д��
				lTransactionTypeID == SETTConstant.TransactionType.CAUTIONMONEYSAVE)		//��֤����
			{
			
				System.out.println("���ò���ƾ֤��Ϣ----------�������˻���)-----------(��ʼ)");
				printInfo = this.getCurrentPrintInfo(lID);			//	1.�����տ� ���и��� �ڲ�ת�� ί�д�� ��֤����
				System.out.println("���ò���ƾ֤��Ϣ----------�������˻���)-----------(����)");
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.OPENFIXEDDEPOSIT ||	//���ڿ��� 
					lTransactionTypeID==SETTConstant.TransactionType.OPENNOTIFYACCOUNT    //֪ͨ����
			)
			{
				printInfo=this.getFixDepositOpenPrintInfo(lID);	//	2.���ڿ��� ֪ͨ����
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER ||	//����֧ȡ  
					lTransactionTypeID==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW    //֪ͨ���֧ȡ
			)
			{
				printInfo=this.getFixDepositWithDrawPrintInfo(lID);	//	3.����֧ȡ ֪ͨ���֧ȡ
			}	
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.FIXEDCONTINUETRANSFER 	//��������    
			)
			{
				printInfo=this.getFixDepositContinuePrintInfo(lID);	//	4.��������
			}	
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANGRANT ||	//��Ӫ�����
					lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANGRANT 	//ί�д����
				
			)
			{
				printInfo=this.getLoanGrantPrintInfo(lID);	//	5.��Ӫ����� ί�д����
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANRECEIVE ||	//��Ӫ�����ջ�
					lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANRECEIVE 	//ί�д����ջ�
			)
			{
				printInfo=this.getLoanRepaymentPrintInfo(lID);	//	6.��Ӫ�����ջ� ί�д����ջ�
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTGRANT 	//���ַ���
			)
			{
				printInfo=this.getDiscountGrantPrintInfo(lID);	//	7.���ַ���
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTRECEIVE 	//�����ջ�
			)
			{
				printInfo=this.getDiscountRepaymentPrintInfo(lID);	//	8.�����ջ�
			} 
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.GENERALLEDGER 	//����
			)
			{
				printInfo=this.getGLPrintInfo(lID);	//	9.����
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.SPECIALOPERATION 	//����ҵ��
			)
			{
				printInfo=this.getSpecialPrintInfo(lID);	//	10.����ҵ��				
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.ONETOMULTI || 	//һ������
					lTransactionTypeID == SETTConstant.TransactionType.UPGATHERING	//�ʽ�����
			)
			{
				printInfo=this.getOneMutiRepaymentPrintInfo(lID);	//	11.һ������
			}
			
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTRECEIVE 	//�������ڴ浥����	--kkf:û��������
			)
			{
				printInfo=this.getFixDepositChangePrintInfo(lID);	//	12.�������ڴ浥����    2006.3.26    feiye
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.INTERESTFEEPAYMENT 	//��Ϣ����֧��
			)
			{
				printInfo=this.getInterestFeePaymentPrintInfo(lID);	//	14.��Ϣ����֧��
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.SECURITIESPAY 	//����˾���֤ȯͶ�ʽ��㣩
			)
			{
				printInfo=this.getSecuritiesPrintInfo(lID);	//	15.����˾���֤ȯͶ�ʽ��㣩
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.BANKGROUPLOANGRANT 	//���Ŵ����
			)
			{
				printInfo=this.getSynLoanGrantPrintInfo(lID);	//	16.���Ŵ����
			}
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE 	//���Ŵ����ջ�
			)
			{
				printInfo=this.getSynLoanRepaymentPrintInfo(lID);	//	17.���Ŵ����ջ�
			} 
			else if(
					lTransactionTypeID==SETTConstant.TransactionType.TRANSFEE 	//���׷��ô���
			)
			{
				printInfo=this.getTransFeePrintInfo(lID);	//	18.���׷��ô���
			}
			else if(
					lTransactionTypeID == SETTConstant.TransactionType.OPENMARGIN  //��֤����
			)
			{
					
					Log.print("��֤����");
					printInfo = this.getTransOpenMarginDepositPrintInfo(lID);
					
			}
			else if(
					lTransactionTypeID == SETTConstant.TransactionType.WITHDRAWMARGIN	//��֤��֧ȡ
			)
			{
				Log.print("��֤��֧ȡ");
				printInfo = this.getTransMarginWithdrawPrintInfo(lID);							
			}
			else if(
					lTransactionTypeID == SETTConstant.TransactionType.MULTILOANRECEIVE	//��ʴ����ջ�
			)
			{
				Log.print("��ʴ����ջ����û������settlement-print-control-co17.jsp���ж���");
			}
			else{
				printInfo=null;
				System.out.println("û�еõ�ʵ�ʵ�ҵ����������ñ�Ҫ�Ĵ�ӡ��Ϣ�ķ�����,printInfo�ķ���ֵΪ��");
			}
			
			if(printInfo!=null){
				printInfo.setTransTypeID(lTransactionTypeID);
				System.out.println("���ò���ƾ֤��Ϣ----------��������printInfo.getAmount():"+printInfo.getAmount());
			}
			
			
			/*
		        SETTConstant.TransactionType.CASHRECEIVE = 46; //�ֽ��տ�

		        SETTConstant.TransactionType.CHECKPAY = 3; //֧Ʊ����

		        SETTConstant.TransactionType.CASHPAY = 4; //�ֽ𸶿�

		        SETTConstant.TransactionType.DRAFTPAY = 5; //Ʊ�㸶��

		        

		        SETTConstant.TransactionType.CONSIGNRECEIVE = 7; //ί���տ�

		        

		       

		        SETTConstant.TransactionType.NATIONALDEBT_BUY = 10; //��ծ����

		        SETTConstant.TransactionType.NATIONALDEBT_SELL = 34; //��ծ����

		        

		        SETTConstant.TransactionType.OTHERPAY = 47; //��������

		        SETTConstant.TransactionType.SUBCLIENT_BANKRECEIVE = 52; //������λ�����տ�

		        SETTConstant.TransactionType.SUBCLIENT_BANKPAY = 53; //������λ���и���

		        //����ҵ��
		        

		        SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER = 13; //����ת���ڣ�����֧ȡ��  

		       

		        //֪ͨҵ��
		        

		       

		        //����ҵ��
		        

		        

		        

		        

		        

		        

		        SETTConstant.TransactionType. = 23; //��ʴ����ջ�

		        //���Ŵ���
		        

		        

		        //ί��ҵ������ר�ã�
		        SETTConstant.TransactionType.CONSIGNUPRECEIVE = 24; //ί�������ʽ�

		        SETTConstant.TransactionType.CONSIGNUPSAVE = 25; //�ϴ��ʽ���ؼ����Ÿ�Ϣ�ʽ�

		        SETTConstant.TransactionType.CONSIGNUPSAVERECEIVE = 26; //�ϴ��ʽ����ռ�����

		        SETTConstant.TransactionType.SHORTDEBTRECEIVE = 27; //���̸�

		        SETTConstant.TransactionType.CONSIGNCAPITALOPERATION = 28; //�ͻ�ί���ʽ����

		        SETTConstant.TransactionType.SHORTLOANGRANT = 29; //���Ŷ��ڴ���

		        SETTConstant.TransactionType.CYCLOANGRANT = 30; //����ѭ������

		        //����
		        

		        

		        

		        SETTConstant.TransactionType.COMPATIBILITY = 108; //���ݽ���

		        SETTConstant.TransactionType.TRANSABATEMENT = 109; //ת���������Զ�����

		        //34�Ѿ���ռ��
		        SETTConstant.TransactionType.SHORTLOANRECEIVE = 35; //���ڴ����ջ�

		        SETTConstant.TransactionType.INTERESTGRANT = 36; //������Ϣ

		        SETTConstant.TransactionType.SHUTDOWN = 37; //�ػ�

		        SETTConstant.TransactionType.CYCLOANRECEIVE = 38; //ѭ�������ջ�

		        SETTConstant.TransactionType.BANKUPSAVE = 40; //��������

		        SETTConstant.TransactionType.BANKUPRECEIVE = 41; //���е���

		        SETTConstant.TransactionType.BANKINTEREST = 42; //���з��Ÿ�Ϣ�ʽ�

		        SETTConstant.TransactionType.CYCCONSIGNLOANGRANT = 43; //ѭ��ί�д����

		        SETTConstant.TransactionType.CYCCONSIGNLOANRECEIVE = 44; //ѭ��ί�д����ջ�

		        

		        //46�Ѿ���ռ��
		        //47�Ѿ���ռ��
		        //֤ȯͶ�ʽ���
		        SETTConstant.TransactionType.SECURITIESRECEIVE = 48; //����˾�տ֤ȯͶ�ʽ��㣩

		       

		        SETTConstant.TransactionType.SEC_WTLC_RECEIVE = 56; //ί������տ֤ȯͶ�ʽ��㣩

		        SETTConstant.TransactionType.SEC_WTLC_PAY = 57; //ί����Ƹ��֤ȯͶ�ʽ��㣩

		        SETTConstant.TransactionType.SEC_ZQCX_RECEIVE = 58; //ծȯ�����տ֤ȯͶ�ʽ��㣩

		        SETTConstant.TransactionType.SEC_ZQCX_PAY = 59; //ծȯ�������֤ȯͶ�ʽ��㣩

		        //�ʽ��й���ҵ�񣨹���ר�ã�
		        SETTConstant.TransactionType.GRANT_DEBIT_INTEREST = 50; //���Ÿ�Ϣ�ʽ�

		        SETTConstant.TransactionType.RECEIVE_DEBIT_INTEREST = 51; //�ջظ�Ϣ�ʽ�

		        //��Ϣ��������
		        SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT = 101; //��Ϣ����֧�����ڽ�Ϣ

		        SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST = 102; //���ڴ�����Ӧ����Ϣ����������

		        SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST = 103; //��Ӫ�������Ӧ����Ϣ����������

		        SETTConstant.TransactionType.TRUST_LOAN_INTEREST = 104; //��Ӫ�����Ϣ

		        SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE = 105; //��Ӫ����ᵣ����

		        SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST = 106; //ί�д����Ϣ

		        SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE = 107; //ί�д����������

		        //108�Ѿ���ռ��
		        //109�Ѿ���ռ��
		        //�����տ�ҵ��(����ר��)
		        SETTConstant.TransactionType.CHECK_RECEIVE = 110; //֧Ʊ�տ�

		        SETTConstant.TransactionType.REMIT_RECEIVE = 111; //����տ�

		        SETTConstant.TransactionType.OTHER_RECEIVE = 112; //�����տ�

		        //���и���ҵ��(����ר��)
		        SETTConstant.TransactionType.CHECK_PAY = 116; //֧Ʊ����

		        SETTConstant.TransactionType.REMIT_PAY = 117; //����

		        SETTConstant.TransactionType.TAX_PAY = 118; //˰������

		        SETTConstant.TransactionType.OTHER_PAY = 119; //��������

		        //����ҵ��(�Ϻ�����ר��)
		        SETTConstant.TransactionType.DELEGATION_INCOME_OFFBALANCE = 120;	//1.	�������м�ֵƷ�����ҵ������
		        SETTConstant.TransactionType.DELEGATION_PAYOUT_OFFBALANCE = 121;	//1.	�������м�ֵƷ�����ҵ�񸶳�

		        SETTConstant.TransactionType.CONSIGN_INCOME_OFFBALANCE = 122;		//2.	����δ����Ϣ�����ҵ������
		        SETTConstant.TransactionType.CONSIGN_PAYOUT_OFFBALANCE = 123;		//2.	����δ����Ϣ�����ҵ�񸶳�

		        SETTConstant.TransactionType.DISCOUNT_INCOME_OFFBALANCE = 124;		//3.	��ҵ��Ʊ���������ҵ������
		        SETTConstant.TransactionType.DISCOUNT_PAYOUT_OFFBALANCE = 125;		//3.	��ҵ��Ʊ���������ҵ�񸶳�

		        SETTConstant.TransactionType.ASSURE_INCOME_OFFBALANCE = 126;		//4.	��������ƾ�������ҵ������
		        SETTConstant.TransactionType.ASSURE_PAYOUT_OFFBALANCE = 127;		//4.	��������ƾ�������ҵ�񸶳�
		        //���ᡢ��ʧ
		        SETTConstant.TransactionType.REPORTLOSS = 130;		//��ʧ
		        
		        SETTConstant.TransactionType.REPORTFIND = 131;		//���
		        
		        SETTConstant.TransactionType.CHANGECERTIFICATE= 132;		//����֤��
		        
		        SETTConstant.TransactionType.FREEZE = 133;		//����
		        
		        SETTConstant.TransactionType.DEFREEZE = 134;		//�ⶳ
		        
		        //��ͨ��˾ר��
				SETTConstant.TransactionType.FUND_REQUEST = 140;		//�ʽ�����,������ҵ��
				SETTConstant.TransactionType.FUND_RETURN = 141;		//�ʽ��ϴ�,������ҵ��
		        
				
		        
		        //��ȡ������
		        SETTConstant.TransactionType.COMMISSION = 161;//������������ȡ
		        
				//Ʊ�ݹ���ר��
				SETTConstant.TransactionType.BILL_REGISTER=201;		
				SETTConstant.TransactionType.BILL_USE=202;
				
				//�Ϻ��������� ���и��� ��������
				SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY = 221;//���и����˾����
				SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT = 222;//���и���-�����˻�
		        
		        //�Ϻ��������� �����տ� ��������
		        SETTConstant.TransactionType.BANKRECEIVE_GATHERING   = 231;//�����տ���տ��� 
		        SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT   = 232;//�����տ��Ա��λ�տ� 
		        SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT = 233;//�����տת��Ա��λ�տ�
				
				//���нӿ�����
		        SETTConstant.TransactionType.INITIATIVE_TURNIN = 501; //��������ҵ��
		        //������������������Ǳ������Ϣ��ָ��
		        SETTConstant.TransactionType.DRAW_PRINCIPAL = 502; //֪ͨ������֧ȡ����
		        SETTConstant.TransactionType.DRAW_INTEREST = 503; //֪ͨ������֧ȡ��Ϣ

			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return printInfo;	//���ش�ӡ��ϢINFO��
	}
}
