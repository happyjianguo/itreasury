package com.iss.itreasury.evoucher.setting.dataentity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import com.iss.inut.workflow.ws.WorkflowManager;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao.CounterparBankDao;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartBankInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_TransInterestSettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.print.IPrintTemplate;
import com.iss.itreasury.settlement.print.PrintInfo;
import com.iss.itreasury.settlement.print.PrintVoucher;
import com.iss.itreasury.settlement.print.SubsectionInterest;
import com.iss.itreasury.settlement.print.templateinfo.ShowWithDrawInfo;
import com.iss.itreasury.settlement.print.templateinfo.SubsectionDateInfo;
import com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.ReportLossOrFreezeBean;
import com.iss.itreasury.settlement.setting.bizlogic.TransactionFeeTypeBiz;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.setting.dataentity.TransFeeTypeSetInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransOnePayMultiReceiveDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransRepaymentDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo;
import com.iss.itreasury.settlement.transfee.dao.Sett_TransFeeDAO;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.transferloancontract.dao.Sett_TransferLoanContractDepositDAO;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransChangeFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedContinueDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedChangeInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transgeneralledger.dao.Sett_TransGeneralLedgerDAO;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.transloan.dao.Sett_SyndicationLoanInterestDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.BankLoanDrawInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransMarginWithdrawDAO;
import com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransOpenMarginDepositDAO;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.bizlogic.InutApprovalRecordBiz;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.DataFormat;
import java.text.DecimalFormat;
import com.iss.itreasury.evoucher.setting.bizlogic.ElectronSignatureBiz;

public class SetDataEntityGD implements Serializable
{
	protected HashMap usedFields = new HashMap();
    /**
     * jzw 2010-05-24
     * �ж��Ƿ�Ҫ��ǩ��
     * @param clientID  �ͻ�id
     * @param transTypeID �������ͱ��
     * @param nOfficeID ���´�ID
     * @param nCurrencyID ����ID
     * @return String
     */  
	public String getIsSignature(long clientID,long transTypeID,long nOfficeID,long nCurrencyID,String billName,int isNetBank)throws ITreasuryDAOException
	{
		/*
		CounterparBankDao bankdao=new CounterparBankDao();
		String isSignature = "0";
		try {
			isSignature = bankdao.findIsSignature(clientID, transTypeID, nOfficeID, nCurrencyID,billName,isNetBank);
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
		}
		return isSignature;
		*/
		String isSignature = "0";
		ElectronSignatureBiz biz = new ElectronSignatureBiz();
		isSignature = biz.findIsSignature(clientID, transTypeID, nOfficeID, nCurrencyID, billName, isNetBank);
		return isSignature;
	}
	public PrintInfo getPrintInfo(long lID,long lTransactionTypeID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			//�����տ� ���и��� �ڲ�ת�� ί�д�� ��֤����
			if( lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_GATHERING ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY ||
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY_NOTONLINE ||
				lTransactionTypeID == SETTConstant.TransactionType.INTERNALVIREMENT ||
				lTransactionTypeID == SETTConstant.TransactionType.CONSIGNSAVE ||
				lTransactionTypeID == SETTConstant.TransactionType.CAUTIONMONEYSAVE ||
				//���"���и���-�����˻�"ҵ������
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT ||
				//���"��ҵ�ʽ��ϻ�"ҵ������
				lTransactionTypeID == SETTConstant.TransactionType.CAPITALUP ||
				//���"��ҵ�ʽ��²�"ҵ������
				lTransactionTypeID == SETTConstant.TransactionType.CAPITALDOWN )
			{
				printInfo = this.getCurrentPrintInfo(lID);
			}
			//���ڿ��� ֪ͨ����
			else if( lTransactionTypeID==SETTConstant.TransactionType.OPENFIXEDDEPOSIT 
				  || lTransactionTypeID==SETTConstant.TransactionType.OPENNOTIFYACCOUNT )
			{
				if ( lTransactionTypeID==SETTConstant.TransactionType.OPENFIXEDDEPOSIT )
				{
					printInfo=this.getFixDepositChangePrintInfo(lID);
				}
				
				if ( printInfo.getDepositBillNO().equals("") || printInfo.getDepositBillNO() == null || printInfo.getDepositBillNO().length() < 0 )
				{
					printInfo=this.getFixDepositOpenPrintInfo(lID);
				}
			}
			//����֧ȡ ֪ͨ���֧ȡ
			else if( lTransactionTypeID==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER 
				  || lTransactionTypeID==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
			{
				printInfo=this.getFixDepositWithDrawPrintInfo(lID);
			}
			//��������
			else if( lTransactionTypeID==SETTConstant.TransactionType.FIXEDCONTINUETRANSFER )
			{
				printInfo=this.getFixDepositContinuePrintInfo(lID);
			}	
			//��Ӫ����� ί�д����
			else if( lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANGRANT 
				  || lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANGRANT )
			{
				printInfo=this.getLoanGrantPrintInfo(lID);
			}
			//��Ӫ�����ջ� ί�д����ջ�
			else if( lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANRECEIVE 
				  || lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANRECEIVE )
			{
				printInfo=this.getLoanRepaymentPrintInfo(lID);
			}
			//���ַ���
			else if( lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTGRANT )
			{
				printInfo=this.getDiscountGrantPrintInfo(lID);
			}
			//�����ջ�
			else if( lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTRECEIVE )
			{
				printInfo=this.getDiscountRepaymentPrintInfo(lID);
			} 
			//����
			else if( lTransactionTypeID==SETTConstant.TransactionType.GENERALLEDGER )
			{
				printInfo=this.getGLPrintInfo(lID);
			}
			//����ҵ��//add by xwhe 2008-09-10 ����ҵ����������ʱֻ������������
			else if( lTransactionTypeID==SETTConstant.TransactionType.SPECIALOPERATION||lTransactionTypeID>1000 )
			{
				printInfo=this.getSpecialPrintInfo(lID);		
			}
			//һ������ �ʽ�����
			else if( lTransactionTypeID==SETTConstant.TransactionType.ONETOMULTI 
				  || lTransactionTypeID == SETTConstant.TransactionType.UPGATHERING )
			{
				printInfo=this.getOneMutiRepaymentPrintInfo(lID);
			}
			//��Ϣ����֧��
			else if( lTransactionTypeID==SETTConstant.TransactionType.INTERESTFEEPAYMENT )
			{
				printInfo=this.getInterestFeePaymentPrintInfo(lID);
			}
			//����˾���֤ȯͶ�ʽ��㣩
			else if( lTransactionTypeID==SETTConstant.TransactionType.SECURITIESPAY )
			{
				//printInfo=this.getSecuritiesPrintInfo(lID);	//	15.����˾���֤ȯͶ�ʽ��㣩
			}
			//���Ŵ����
			else if( lTransactionTypeID==SETTConstant.TransactionType.BANKGROUPLOANGRANT )
			{
				printInfo=this.getSynLoanGrantPrintInfo(lID);
			}
			//���Ŵ����ջ�
			else if( lTransactionTypeID==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE )
			{
				printInfo=this.getSynLoanRepaymentPrintInfo(lID);
			} 
			//���׷��ô���
			else if( lTransactionTypeID==SETTConstant.TransactionType.TRANSFEE )
			{
				printInfo=this.getTransFeePrintInfo(lID);
			}
			//��֤����
			else if( lTransactionTypeID == SETTConstant.TransactionType.OPENMARGIN )
			{
				printInfo = this.getTransOpenMarginDepositPrintInfo(lID);	
			}
			//��֤��֧ȡ
			else if( lTransactionTypeID == SETTConstant.TransactionType.WITHDRAWMARGIN )
			{
				printInfo = this.getTransMarginWithdrawPrintInfo(lID);							
			}
			//��ʴ����ջ�
			else if( lTransactionTypeID == SETTConstant.TransactionType.MULTILOANRECEIVE )
			{
				System.out.println("��ʴ����ջ����û������settlement-print-control-co17.jsp���ж���");
			}
			else if( lTransactionTypeID == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT			//��Ϣ����֧�����ڽ�Ϣ
				  || lTransactionTypeID == SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST		//���ڴ�����Ӧ����Ϣ����������
				  || lTransactionTypeID == SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST  	//֪ͨ������Ӧ����Ϣ����������
				  || lTransactionTypeID == SETTConstant.TransactionType.TRUST_LOAN_INTEREST  				//��Ӫ�����Ϣ(�ڲ��ʽ�ռ�ý�Ϣ)
				  || lTransactionTypeID == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST  				//ί�д����Ϣ
				  || lTransactionTypeID == SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST  	//���ڴ�����Ӧ����Ϣ����������
				  || lTransactionTypeID == SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST		//ί�д������Ӧ����Ϣ����������
				  || lTransactionTypeID == SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST			//��Ӫ�������Ӧ����Ϣ����������
				  || lTransactionTypeID == SETTConstant.TransactionType.YT_LOAN_INTEREST                    //���Ŵ����Ϣ
				  || lTransactionTypeID == SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST            //���Ŵ������Ӧ����Ϣ����������
				  
				  )
			{
				printInfo = this.getTransInterestSettlementPrintInfo(lID);
			}
			else if(lTransactionTypeID==SETTConstant.TransactionType.TRANSFERPAY
					||lTransactionTypeID==SETTConstant.TransactionType.TRANSFERREPAY
					||lTransactionTypeID==SETTConstant.TransactionType.AGENTTRANSFERREPAY)
			{
				printInfo = this.getTransAnountCcaftbrotherPrintInfo(lID);
			}
			//��ʱ�޴˽������͵Ĵ�ӡ
			else
			{
				printInfo=null;
			}
			
			if(printInfo != null)
			{
				printInfo.setTransTypeID(lTransactionTypeID);
			}
					
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//���ش�ӡ��ϢINFO��
		return printInfo;
	}
	//�Ŵ��ʲ�ת���տ���տ�����ӡ
	public PrintInfo getTransAnountCcaftbrotherPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransferLoanContractDepositDAO transferloandao = new Sett_TransferLoanContractDepositDAO();
			TransferLoanContractInfo resultInfo = null;
			resultInfo = transferloandao.findByID(lID);
			
			if (resultInfo != null)
			{
				if (resultInfo.getOFFICEID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOFFICEID());
				}
				if (resultInfo.getCURRENCYID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCURRENCYID());
				}
				if (resultInfo.getSTATUSID() > 0)
				{
					printInfo.setStatusID(resultInfo.getSTATUSID());
				}
				if (resultInfo.getAMOUNT() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAMOUNT());
				}
				if (resultInfo.getINTEREST() != 0.0)
				{
					printInfo.setInterest(resultInfo.getINTEREST());
				}
				if (resultInfo.getCOMMISSION() != 0.0)
				{
					printInfo.setCommission(resultInfo.getINTEREST());
				}
				
				if (resultInfo.getPAYBANKID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getPAYBANKID());
				}
				
				if (resultInfo.getRECEIVEBANKID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getRECEIVEBANKID());
				}
				
				if (resultInfo.getPAYGENRALLEDGERTYPEID() > 0)
				{
					printInfo.setPayGL(resultInfo.getPAYGENRALLEDGERTYPEID());
				}
				if (resultInfo.getRECGENERALLEDGERTYPEID() > 0)
				{
					printInfo.setReceiveGL(resultInfo.getRECGENERALLEDGERTYPEID());
				}
				
				if (resultInfo.getEXECUTE() != null)
				{
					printInfo.setExecuteDate(resultInfo.getEXECUTE());
				}
				if (resultInfo.getINTERESTSTART() !=null )
				{
					printInfo.setInterestStart(resultInfo.getINTERESTSTART());
				}
				if (resultInfo.getSTRANSNO() !="" )
				{
					printInfo.setTransNo(resultInfo.getSTRANSNO());
				}
				if (resultInfo.getTRANSACTIONTYPEID() >0 )
				{
					printInfo.setTransactionTypeID(resultInfo.getTRANSACTIONTYPEID());
				}
//				if (resultInfo.getTRANSFERTYPE() >0 )
//				{
//					printInfo.setTransTypeID(resultInfo.getTRANSFERTYPE());
//				}
				
				if (resultInfo.getSABSTRACT() != null && resultInfo.getSABSTRACT().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getSABSTRACT());
				}
				if (resultInfo.getINPUTUSERID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getINPUTUSERID());
				}
				if (resultInfo.getCHECKUSERID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCHECKUSERID());
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
	
	//�����տ� ���и��� �ڲ�ת�� ί�д�� ��֤����
	public PrintInfo getCurrentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransCurrentDepositDAO currentDepositDao = new Sett_TransCurrentDepositDAO();
			TransCurrentDepositInfo resultInfo = null;
			resultInfo = currentDepositDao.findByID(lID);
			
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
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				
				if (resultInfo.getPayAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getPayAccountID());
				}
				
				//ί�д��(����ֻ����ί�д��������)
				//if ( resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.CONSIGNSAVE )
				//{
					//�����и�����-bankid Ϊ��������	
					if (resultInfo.getBankID() > 0)
					{
						printInfo.setPayBankID(resultInfo.getBankID());
					}
					
					//��� "���и���-�����˻�" ҵ����߼��ж�
					//��ҵ���ڴ��ʱ���տ���ݴ�ŵ��ֶ��Ǹ���ֶ�"npayaccountid",��Դ�ҵ�����ת��
					if ( resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT )
					{
						if (resultInfo.getPayAccountID() > 0)
						{
							printInfo.setReceiveAccountID(resultInfo.getPayAccountID());
							printInfo.setPayAccountID(-1);
						}
					}
					
					//�������տ���-bankid Ϊ�տ�����	
					if (resultInfo.getBankID() > 0)
					{
						printInfo.setReceiveBankID(resultInfo.getBankID());
					}
				//}
				
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
				if (resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtClientName());
				}
				if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
				}
				if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
				}
				if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
				}
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
				
				//��ʶ�ǽ���ϵͳ��������ˮ��
				if (resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
				{
					printInfo.setInstructionNo(resultInfo.getInstructionNo());
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
	
	//���ڿ��� ֪ͨ����
	public PrintInfo getFixDepositOpenPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
			TransFixedOpenInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				
				//��ʶ�ǽ���ϵͳ��������ˮ��
				if (resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
				{
					printInfo.setInstructionNo(resultInfo.getInstructionNo());
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
	
	//����֧ȡ ֪ͨ���֧ȡ
	public PrintInfo getFixDepositWithDrawPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
			TransFixedDrawInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
			Sett_AccountDAO objAccountDAO = new Sett_AccountDAO();
			AccountInfo accountInfo = new AccountInfo();
			accountInfo = objAccountDAO.findByID(resultInfo.getAccountID());
			resultInfo.setAccountNo(accountInfo.getAccountNo());
			resultInfo.setAccountName(accountInfo.getAccountName());
			resultInfo.setMinSingleAmount(accountInfo.getMinSinglePayAmount());
			
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
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
				{
					//֧ȡ���
					if ( resultInfo.getDrawAmount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getDrawAmount());
					}
					//����
					else if( resultInfo.getAmount()!=0.0 )
					{
						printInfo.setAmount(resultInfo.getAmount());
					}
					
					if( resultInfo.getDrawAmount() != 0.0 )
					{
						printInfo.setDrawAmount( resultInfo.getDrawAmount() );
					}
	                if( resultInfo.getAmount() > resultInfo.getDrawAmount() )
	                {
	                	printInfo.setAccountAmount( resultInfo.getAmount() );
	                }
				}
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					if( resultInfo.getDrawAmount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getDrawAmount());
					}
					
					if( resultInfo.getDrawAmount() != 0.0 )
					{
						printInfo.setDrawAmount( resultInfo.getDrawAmount() );
					}
	                if( resultInfo.getAmount() > resultInfo.getDrawAmount() )
	                {
	                	printInfo.setAccountAmount( resultInfo.getAmount() );
	                }
	                
	                if( resultInfo.getDepositBalance() != 0.0 )
	                {
	                	printInfo.setDepositBalance( resultInfo.getDepositBalance() );
	                }
	                
	                if( resultInfo.getAccountID() > 0 )
	                {
	                	printInfo.setAccountID( resultInfo.getAccountID() );
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
				//������Ϣ
				if (resultInfo.getOtherInterest() != 0.0)
				{
					printInfo.setOtherInterest(resultInfo.getOtherInterest());
				}
				//��Ϣ�˻���
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//else
				//{
				//	printInfo.setReceiveInterestAccountID(NameRef.getAccountIdByNo(resultInfo.getInterestExtAcctNo()));
				//}
				
				/*****����ȡ������Ҫ����,�������ǰ֧ȡȡ advancerate(��������) *****/
				//����
				if ( resultInfo.getExecuteDate().before(resultInfo.getEndDate() )
				  || resultInfo.getStartDate().compareTo(resultInfo.getExecuteDate()) == 0 )
				{
					if (resultInfo.getAdvanceRate() != 0.0)
					{
						printInfo.setRate(resultInfo.getAdvanceRate());
					}
				}
				else
				{
					if (resultInfo.getRate() != 0.0)
					{
						printInfo.setRate(resultInfo.getRate());
					}
				}
				
				//��������
				if (resultInfo.getAdvanceRate() > 0)
				{
					printInfo.setNewRate(resultInfo.getAdvanceRate());
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
				if (resultInfo.getPayableInterest() != 0.0 || resultInfo.getPreDrawInterest() != 0.0)
				{
					printInfo.setPayableInterest( UtilOperation.Arith.add(resultInfo.getPayableInterest(), resultInfo.getPreDrawInterest()) );
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
				
				//������Ϣ�˻���
				if (resultInfo.getInterestExtAcctNo() != null)
				{
					printInfo.setInterestextaccountno(resultInfo.getInterestExtAcctNo());
				}
				if (resultInfo.getNoticeDay() != 0)
				{
					printInfo.setNoticeDay(resultInfo.getNoticeDay());
				}
				
				//��ʶ�ǽ���ϵͳ��������ˮ��
				if (resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
				{
					printInfo.setInstructionNo(resultInfo.getInstructionNo());
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
	
	//��������
	public PrintInfo getFixDepositContinuePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
			TransFixedContinueInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				//��������ɴ浥��
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//���������´浥��(2007-10-19)
				if (resultInfo.getNewDepositNo() != null && resultInfo.getNewDepositNo().length() > 0)
				{
					printInfo.setDepositBillNO(resultInfo.getNewDepositNo());
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
				//������(�������濪��֤ʵ����ʾ������2007-10-19)
				if (resultInfo.getNewRate() != 0.0)
			 	{
					printInfo.setNewRate(resultInfo.getNewRate());
				}
				//������
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
					//Boxu Update 2008��5��14�� �޸ĳ�ȡ����֧ȡ��Ϣ(MWITHDRAWINTEREST)
					//Boxu Update 2009��2��18�� �޸�ȡ������Ϣ��������������Ϣ
                    //minzhao Update 2009��4��2�� �Ļ����ڰ汾
					//printInfo.setPayableInterest(resultInfo.getPayableInterest());
					printInfo.setPayableInterest(resultInfo.getWithDrawInterest());
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
				//�´浥��ʼ(��ʼ)����(�������濪��֤ʵ����ʾ����ʼ����2007-10-19)
				if (resultInfo.getNewStartDate() != null)
				{
					printInfo.setNewStartDate(resultInfo.getNewStartDate());
				}
				//�´浥����(��ֹ)����(�������濪��֤ʵ����ʾ�½�ֹ����2007-10-19)
				if (resultInfo.getNewEndDate() != null)
				{
					printInfo.setNewEndDate(resultInfo.getNewEndDate());
				}
				//�������
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//�Ƿ�������
				if(resultInfo.getIsCapitalAndInterestTransfer() > 0)
				{
					printInfo.setIsCapitalAndInterestTransfer(resultInfo.getIsCapitalAndInterestTransfer());
					
					//����Ǳ�Ϣ���棬��û��"�����Ϣ�Ƹ�֪ͨ��"�����Ϣ���ϵ���Ϣ
					//printInfo.setRealInterest(0.0);
					printInfo.setRealInterestReceivable(0.0);
                    //printInfo.setPayableInterest(resultInfo.getPayableInterest());
					//minzhao Update 2009��4��2�� �Ļ�ȡ��Ϣ�ϼƣ��л�����Ϣ��������
					printInfo.setPayableInterest(resultInfo.getWithDrawInterest());
				}
				
				//��ʶ�ǽ���ϵͳ��������ˮ��
				if (resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
				{
					printInfo.setInstructionNo(resultInfo.getInstructionNo());
				}
				
				//������Ϣ
				if (resultInfo.getCurrentInterest() != 0.0)
				{
					printInfo.setCurrentInterest(resultInfo.getCurrentInterest());
				}
				
				//��������
				if (resultInfo.getAdvanceRate() > 0)
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
	
	//��Ӫ����� ί�д����
	public PrintInfo getLoanGrantPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
			TransGrantLoanInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
	
	//��Ӫ�����ջ� ί�д����ջ�
	public PrintInfo getLoanRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
			//�ж��Ƿ����Ŵ���
			if(resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				ArrayList list = null;
				Sett_SyndicationLoanInterestDAO sydao = new Sett_SyndicationLoanInterestDAO();
				list = (ArrayList)sydao.findBySyndicationLoanReceiveID(lID);
				resultInfo.setSyndicationLoanInterest(list);
			}
			
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
				//���ڴ���ʻ�ID
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getDepositAccountID());
				}
				//�տ�����ID
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
				//��Ϣ˰��
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
				//ʵ����Ϣ˰��
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
				//�����ѿ�ʼ����
				if (resultInfo.getCommissionStart() != null)
				{
					printInfo.setCommissionStart(resultInfo.getCommissionStart());
				}
				//�����ѽ�������
				if (resultInfo.getCommissionStart() != null)
				{
					printInfo.setCommissionFeeEnd(resultInfo.getCommissionStart());
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
				//���λ��ί���˻���
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getConsignAccountID());
				}
				//ί�з������˻�
				if(resultInfo.getConsignDepositAccountID()> 0 )
				{
					printInfo.setConsignDepositAccountID(resultInfo.getConsignDepositAccountID());
				}
				
                //����ֶμ�Ϣ
				if(resultInfo.getSubAccountID() > 0 && resultInfo.getOfficeID()>0 && resultInfo.getOfficeID() > 0 
				&& resultInfo.getInterestClear() != null
				&& resultInfo.getCurrentBalance() == 0.0)
				{
					Connection conInternal = null;
					//conInternal = getConnection();
					Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
					Vector vector = null;
					Vector vector1 = null;
					//vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getSubAccountID(),resultInfo.getLoanAccountID(),resultInfo.getOfficeID(),resultInfo.getOfficeID(),resultInfo.getInterestClear());			
					vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getTransactionTypeID(),resultInfo.getSubAccountID(),resultInfo.getLoanAccountID(),resultInfo.getOfficeID(),resultInfo.getCurrencyID(),resultInfo.getInterestClear());
					//vector1 = (Vector)dailAccountDAO.selectSettNegotiateInterest(resultInfo.getSubAccountID(),resultInfo.getLoanAccountID(),resultInfo.getOfficeID(),resultInfo.getCurrencyID(),resultInfo.getInterestClear());
					vector1 = (Vector)dailAccountDAO.selectSettNegotiateInterest(resultInfo.getSubAccountID(),resultInfo.getLoanAccountID(),resultInfo.getOfficeID(),resultInfo.getOfficeID(),resultInfo.getInterestClear());
					//cleanup(conInternal);
					if ( vector != null && vector.size()> 0) 
					{
						for (int i = 0; i < vector.size(); i++) 
						{ 
							DailyAccountBalanceInfo dailAccInfo = new DailyAccountBalanceInfo();
							dailAccInfo = (DailyAccountBalanceInfo) vector.elementAt(i);
							if(i==0){
								printInfo.setStartDate1(dailAccInfo.getStartDate());
								printInfo.setEndDate1(dailAccInfo.getDate());
								printInfo.setRate1(dailAccInfo.getInterestRate());
								printInfo.setInterest1(dailAccInfo.getInterest());
								printInfo.setSumBlanceAmount1(dailAccInfo.getMCurrensInterestbalance());
							}
							if(i==1)
							{
								printInfo.setStartDate2(dailAccInfo.getStartDate());
								printInfo.setEndDate2(dailAccInfo.getDate());
								printInfo.setRate2(dailAccInfo.getInterestRate());
								printInfo.setInterest2(dailAccInfo.getInterest());
								printInfo.setSumBlanceAmount2(dailAccInfo.getMCurrensInterestbalance());
							}
							if(i==2)
							{
								printInfo.setStartDate3(dailAccInfo.getStartDate());
								printInfo.setEndDate3(dailAccInfo.getDate());
								printInfo.setRate3(dailAccInfo.getInterestRate());
								printInfo.setInterest3(dailAccInfo.getInterest());
								printInfo.setSumBlanceAmount3(dailAccInfo.getMCurrensInterestbalance());
							}
						}
					}
					if ( vector1 != null && vector1.size()> 0) 
					{
						for (int i = 0; i < vector1.size(); i++) 
						{ 
							DailyAccountBalanceInfo dailAccInfo = new DailyAccountBalanceInfo();
							dailAccInfo = (DailyAccountBalanceInfo) vector1.elementAt(i);
							if(i==0){
								printInfo.setNegotiateStartDate1(dailAccInfo.getStartDate());
								printInfo.setNegotiateEndtDate1(dailAccInfo.getDate());
								printInfo.setNegotiateRate1(dailAccInfo.getInterestRate());
								printInfo.setNegotiateInterest1(dailAccInfo.getInterest());
								//modify by xwhe 2008-12-18
								printInfo.setSumNegoBlanceAmount1(dailAccInfo.getMAc_mnegoInterestbalance());
							}
							if(i==1)
							{
								printInfo.setNegotiateStartDate2(dailAccInfo.getStartDate());
								printInfo.setNegotiateEndtDate2(dailAccInfo.getDate());
								printInfo.setNegotiateRate2(dailAccInfo.getInterestRate());
								printInfo.setNegotiateInterest2(dailAccInfo.getInterest());
                                //modify by xwhe 2008-12-18
								printInfo.setSumNegoBlanceAmount2(dailAccInfo.getMAc_mnegoInterestbalance());
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
	
	//���ַ���
	public PrintInfo getDiscountGrantPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransGrantDiscountDAO dao = new Sett_TransGrantDiscountDAO();
			TransGrantDiscountInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				if (resultInfo.getInterestOfDiscount() != 0.0)
				{
					printInfo.setDiscountInterestAmount(resultInfo.getInterestOfDiscount());
				}
				
				//���ֵ�λ(������)
				if (resultInfo.getSignBillClientName() != null)
				{
					printInfo.setApplicantName(resultInfo.getSignBillClientName());
				}
				
				//�������˺�
				if (resultInfo.getDiscountAccountID() > 0)
				{
					printInfo.setApplicantAccountNo( NameRef.getAccountNoByID( resultInfo.getDiscountAccountID() ) );
				}
				
				//�����˿�������
				if (resultInfo.getBankName() != null)
				{
					printInfo.setApplicantAccountBankNo(resultInfo.getBankName());
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

	//�����ջ�
	public PrintInfo getDiscountRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransRepaymentDiscountDAO dao = new Sett_TransRepaymentDiscountDAO();
			TransRepaymentDiscountInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
			if (resultInfo != null)
			{
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
				//ִ���գ���������
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
				//��ǰ�������
				if (resultInfo.getMSumReceiveAmout() != 0.0)
				{
					printInfo.setCurrentBalance(resultInfo.getMSumReceiveAmout());
				}
				//��������ID,ɾ��ԭ���������ջؽ����д�ӡ������ת�˽跽ƾ֤�и��ȡ��Ʊ�����еĻ����˻��ţ�
				//���û����Ʊ���򸶿��ʾΪ��
				/***** 2007-10-18 �޸���ʾ�����Ϣ, ������ƱҲ��ʾ��Ϣ(���ڻ򿪻���) *****/
				if (resultInfo.getNBankID() > 0)
				{
					//printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getNBankID()));
					//printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNBankID()));
					printInfo.setPayBankID(resultInfo.getNBankID());
				}
				//���(�ջش�������˻�)
				if (resultInfo.getNCurrentAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getNCurrentAccountID());
				}
				//���(��Ʊ��������˻�)
				if (resultInfo.getNDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getNDepositAccountID());
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
				//���ֵ�λ
				if (resultInfo.getNClientID() > 0)
				{
					printInfo.setApplicantName( NameRef.getClientNameByID(resultInfo.getNClientID()) );
				}
				//�����˺�
				if (resultInfo.getNDiscountAccountID() > 0)
				{
					printInfo.setApplicantAccountNo( NameRef.getAccountNoByID( resultInfo.getNDiscountAccountID() ) );
				}
				/***** �������Ʊ����, �ջ���ʾ���Ϊ"��Ʊ���" *****/
				//��Ʊ���
				if (resultInfo.getMReturnedAmount() != 0.0)
				{
					printInfo.setDiscountBillAmount(resultInfo.getMReturnedAmount());
				}
				else if (resultInfo.getMAmount() != 0.0)
				{
					printInfo.setDiscountBillAmount(resultInfo.getMAmount());
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
	
	//����
	public PrintInfo getGLPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
			TransGeneralLedgerInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				
				//�ܽ��(ȡһ���������)
				double amount = 0.0;
				
				//����˻���
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 1)
				{
					amount += resultInfo.getAmount();
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				//�տ�˻���
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 2)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}				
				//�˻����
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAccountAmount(resultInfo.getAmount());
				}
				
				//����һ
				if( resultInfo.getGeneralLedgerOne () > 0 && resultInfo.getDirOne() == 1)
				{
					amount += resultInfo.getAmountOne();
					printInfo.setPayGL1(resultInfo.getGeneralLedgerOne ());
				}
				if( resultInfo.getGeneralLedgerOne () > 0 && resultInfo.getDirOne() == 2)
				{
					printInfo.setReceiveGL1(resultInfo.getGeneralLedgerOne ());
				}
				if( resultInfo.getAmountOne() > 0 )
				{
					printInfo.setAmount1(resultInfo.getAmountOne());
				}
				
				//���˶�
				if( resultInfo.getGeneralLedgerTwo() > 0 && resultInfo.getDirTwo() == 1)
				{
					amount += resultInfo.getAmountTwo();
					printInfo.setPayGL2(resultInfo.getGeneralLedgerTwo());
				}
				if( resultInfo.getGeneralLedgerTwo() > 0 && resultInfo.getDirTwo() == 2)
				{
					printInfo.setReceiveGL2(resultInfo.getGeneralLedgerTwo());
				}
				if( resultInfo.getAmountTwo() > 0 )
				{
					printInfo.setAmount2(resultInfo.getAmountTwo());
				}
				
				//������
				if( resultInfo.getGeneralLedgerThree () > 0 && resultInfo.getDirThree() == 1)
				{
					amount += resultInfo.getAmountThree();
					printInfo.setPayGL3(resultInfo.getGeneralLedgerThree());
				}
				if( resultInfo.getGeneralLedgerThree() > 0 && resultInfo.getDirThree() == 2)
				{
					printInfo.setReceiveGL3(resultInfo.getGeneralLedgerThree());
				}
				if( resultInfo.getAmountThree() > 0 )
				{
					printInfo.setAmount3(resultInfo.getAmountThree());
				}
				
				//������
				if( resultInfo.getGeneralLedgerFour () > 0 && resultInfo.getDirFour() == 1)
				{
					amount += resultInfo.getAmountFour();
					printInfo.setPayGL4(resultInfo.getGeneralLedgerFour());
				}
				if( resultInfo.getGeneralLedgerFour() > 0 && resultInfo.getDirFour() == 2)
				{
					printInfo.setReceiveGL4(resultInfo.getGeneralLedgerFour());
				}
				if( resultInfo.getAmountFour() > 0 )
				{
					printInfo.setAmount4(resultInfo.getAmountFour());
				}
				
				//�ܽ��
				if (amount != 0.0)
				{
					printInfo.setAmount( amount );
				}
				
				//����˻���ID,�ݴ˿ɵø��ȫ�ƣ�����˻���
//				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 2)
//				{
//					printInfo.setPayAccountID(resultInfo.getAccountID());
//				}
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

	//����ҵ��
	public PrintInfo getSpecialPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		
		try
		{
			Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
			TransSpecialOperationInfo resultInfo=null;
			resultInfo = tsodao.findByID(lID);

			if (resultInfo.getNpayloannoteid() > 0)
			{
				resultInfo.setNpaysubaccountid(-1);
			}
			if (resultInfo.getNreceiveloannoteid() > 0)
			{
				resultInfo.setNreceivesubaccountid(-1);
			}
			
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
				
				//�տ������ID
				if(resultInfo.getNreceivegeneralledgertypeid() > 0)
				{
					printInfo.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
				}
				
				//���������ID
				if(resultInfo.getNpaygeneralledgertypeid() > 0)
				{
					printInfo.setPayGL(resultInfo.getNpaygeneralledgertypeid());
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
				
				//Ӧ��ȡ���˵ķ���,������ֵ��������
				//if (resultInfo.getNpaygeneralledgertypeid() > 0)
				//{
				//	printInfo.setPayAccountID(resultInfo.getNpaygeneralledgertypeid());
				//}
				
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
				//add by xwhe 2008-08-07 ����������(ֻ��һ�����������)
				if(resultInfo.getNinputuserid()>0 && resultInfo.getNoperationtypeid()>0 && resultInfo.getNofficeid()>0 && resultInfo.getNcurrencyid()>0)
				{
					String transNo = resultInfo.getStransno();
					WorkflowManager workflowManager = null;
					workflowManager=WorkflowManager.instance(String.valueOf(resultInfo.getNinputuserid()));
				 	List approvalVector = new ArrayList();
				 	String[] temp = new String[100];
					if(!(transNo.equals("") || transNo.equals("-1") || transNo.equals("null") || transNo.equals("0")))
				   	{
				   		InutApprovalRecordBiz biz = new InutApprovalRecordBiz();
				   		InutApprovalRecordInfo qInfo = new InutApprovalRecordInfo();
				   		qInfo.setTransID(transNo);			   		
				   		qInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				   		qInfo.setOfficeID(resultInfo.getNofficeid());
				   		qInfo.setCurrencyID(resultInfo.getNcurrencyid());				 
				   		Collection c = biz.queryByCondition(qInfo);
				   		/*if(c!=null && c.size()>0)
				   		{
				   			Iterator it = c.iterator();
				   			while(it.hasNext())
				   			{
				   				InutApprovalRecordInfo tempInfo = (InutApprovalRecordInfo)it.next();
				   				List tempList = workflowManager.getHistoryAdvise(tempInfo.getApprovalEntryID());
				   				approvalVector.addAll(tempList);
				   			}
				   		}*/
				   		if (c!=null && c.size()!=0)
				   		{
				   			Iterator iter=c.iterator();
				   			while(iter.hasNext())
				   			{
				   				InutApprovalRecordInfo info = null;
				   				info=(InutApprovalRecordInfo)iter.next();
				   				temp[0]=NameRef.getUserNameByID(info.getLastAppUserID());
				   			}
				   		}
			   		}
					if(temp[0]!=null)
					{				
					printInfo.setExamUserName(temp[0].toString());					
					}
				}
		
				//�����˺�
				if (resultInfo.getSextaccountno()!= null)
				{
					printInfo.setReceiveExtAccountNo(resultInfo.getSextaccountno());
				}
				//modify by xwhe 2008-12-17
				if(resultInfo!=null && resultInfo.getNoperationtypeid()==1002)
				{
					TransSpecialOperationInfo  specialInfo = null;
					specialInfo = tsodao.findByID(resultInfo.getId());

					if (specialInfo.getNpayloannoteid() > 0)
					{
						specialInfo.setNpaysubaccountid(-1);
					}
					if (specialInfo.getNreceiveloannoteid() > 0)
					{
						specialInfo.setNreceivesubaccountid(-1);
					}
					
					
					Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
	                BranchInfo branchInfo = new BranchInfo();
	                					
					long payBankID = -1;//�������ID
					long receiveBankID = -1;//�տ����ID
					String payAccountNo = "";//��������ʺ�
	 				String receiveAccountNo = "";//�տ�����ʺ�
	 				//specialInfo = specialDelegation.findDetailByID( resultInfo.getId() );	
					
					if( specialInfo != null )						
					{
						payBankID = specialInfo.getNpaybankid();
						receiveBankID = specialInfo.getNreceivebankid();						
						if( payBankID > 0 )
						{
							payAccountNo  =  NameRef.getBankAccountCodeByBankID(payBankID);
						}
											
						if( receiveBankID > 0 )
						{                    
						    receiveAccountNo = NameRef.getBankAccountCodeByBankID(receiveBankID);
						    branchInfo = sett_BranchDAO.findByID(receiveBankID);
						    printInfo.setExtRemitInBank(branchInfo.getBranchName());
						}
						else
						{
							receiveAccountNo = specialInfo.getSextaccountno();
						}	
					}
					printInfo.setPayExtAccountNo(payAccountNo);//�ⲿ�����˻���
					
					printInfo.setExtAccountNo(receiveAccountNo);//�ⲿ�տ��˻���
					
					
				   }				
                 //added by xwhe 2008-12-16 ������ҵ������������Ϊ�����ĵ�������
				if(resultInfo!=null && resultInfo.getNoperationtypeid()==1003)
				{
					//ִ����
					if (resultInfo.getDtexecute() != null)
					{
						printInfo.setExecuteDate(resultInfo.getDtexecute());
					}
					//���ױ��
					if (resultInfo.getStransno() != null && resultInfo.getStransno().length() > 0)
					{
						printInfo.setTransNo(resultInfo.getStransno());
					}
										
					TransSpecialOperationInfo  specialInfo = null;
					specialInfo = tsodao.findByID(resultInfo.getId());

					if (specialInfo.getNpayloannoteid() > 0)
					{
						specialInfo.setNpaysubaccountid(-1);
					}
					if (specialInfo.getNreceiveloannoteid() > 0)
					{
						specialInfo.setNreceivesubaccountid(-1);
					}
					
					long payBankID = -1;//�������ID
					long receiveBankID = -1;//�տ����ID
					String payAccountNo = "";//��������ʺ�
	 				String receiveAccountNo = "";//�տ�����ʺ�
										
					//specialInfo = specialDelegation.findDetailByID( resultInfo.getId() );	
						
					if( specialInfo != null )						
					{
						payBankID = specialInfo.getNpaybankid();
						receiveBankID = specialInfo.getNreceivebankid();						
						if( payBankID > 0 )
						{
							payAccountNo  =  NameRef.getBankAccountCodeByBankID(payBankID);
						}
											
						if( receiveBankID > 0 )
						{                    
						    receiveAccountNo = NameRef.getBankAccountCodeByBankID(receiveBankID);
						}
						else
						{
							receiveAccountNo = specialInfo.getSextaccountno();
						}	
					}
					
					printInfo.setPayExtAccountNo(payAccountNo);//�ⲿ�����˻���
					
					printInfo.setReceiveExtAccountNo(receiveAccountNo);//�ⲿ�տ��˻���
					
                    //���������ID
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setPayGL(resultInfo.getNpaygeneralledgertypeid());
					}
                    //�տ������ID
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
					}
					//�տΪ�����˻�
					if (resultInfo.getNreceiveaccountid() > 0 )
					{
						printInfo.setReceiveAccountID(resultInfo.getNreceiveaccountid());
					}
                    //���
					if (resultInfo.getMpayamount() != 0.0)
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
                   //ժҪ
					if (resultInfo.getSabstract() !=null)
					{
					    printInfo.setAbstract(resultInfo.getSabstract() );
					}
					//¼����
					if (resultInfo.getNinputuserid() > 0)
					{
						printInfo.setInputUserID(resultInfo.getNinputuserid());
					}
					//������
					if (resultInfo.getNcheckuserid() > 0)
					{
						printInfo.setCheckUserID(resultInfo.getNcheckuserid());
					}
					//�����
					if (resultInfo.getNpaydirection() > 0)
					{
						printInfo.setNpaydirection(resultInfo.getNpaydirection());
					}
					//�տ��
					if (resultInfo.getNreceivedirection() > 0)
					{
						printInfo.setNerceivedirection(resultInfo.getNreceivedirection());
					}
                  //����
					if (resultInfo.getNoperationtypeid() > 0)
					{
						printInfo.setOperationType(resultInfo.getNoperationtypeid());
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
	
	//һ������
	public PrintInfo getOneMutiRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransOnePayMultiReceiveDAO dao = new Sett_TransOnePayMultiReceiveDAO();
			TransOnePayMultiReceiveInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				
				//���
				if (resultInfo.getAmount() > 0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				
				//�������촦���  ��������
				//if(resultInfo.getPayGL()!=-1)
				//{
				//	printInfo.setPayBankID(resultInfo.getBankID());
				//}
				
				if(resultInfo.getReceiveGL()!=-1)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				
				//���ݽ��׺Ų�����ع��˽��׼�¼��Ϣ
				TransOnePayMultiReceiveInfo findCondition = new TransOnePayMultiReceiveInfo(); //��ѯ����
				TransOnePayMultiReceiveInfo transOnePayMultiReceiveInfo = null; //��ѯ���
				TransOnePayMultiReceiveInfo tempTransOnePayMultiReceiveInfo = null; //�ڱ����Ĺ����д洢�ո�����Ϣ 
				
				Collection resultCollection = null;
				boolean bFlagPay = false;
				long lIndexPay = 0;
				boolean bFlagReceive = false;
				long lIndexReceive = 0;
				String PrintName = "";
				
				//ͨ������ID���  "�����д�ӡ����"(ȫ����ʾ)
				if ( resultInfo.getBankID() > 0 )
				{
					Sett_BranchDAO ldao = new Sett_BranchDAO();
					BranchInfo linfo = new BranchInfo();
					linfo = ldao.findByID(resultInfo.getBankID());
					
					//������ȫ��
					PrintName = linfo.getPrintName();
				}
					
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					findCondition.setTransNo(resultInfo.getTransNo());
					findCondition.setId(lID);
					
					Sett_TransOnePayMultiReceiveDAO trdao = new Sett_TransOnePayMultiReceiveDAO();
					resultCollection = trdao.findByConditions( findCondition, -1, false);
					
					if (resultCollection != null)
					{
						Iterator tempIterator = resultCollection.iterator();
						if (resultInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) //�����ǰ��¼�Ǹ���
						{
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
								printInfo.setAmount(resultInfo.getAmount());							
								if (lIndexReceive == 1)
								{
									//�շ�Ψһ
									//���ø�������Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
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
									//�����ø�������Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID())); //����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getPayGL() > 0) //if����ת��
									{
										printInfo.setPayGL(resultInfo.getPayGL());
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
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
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
										if(resultInfo.getExtClientName() != null && !resultInfo.getExtClientName().equals(""))
										{
											printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										}
										else
										{
											Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
											BranchInfo branchInfo = new BranchInfo();
											branchInfo = sett_BranchDAO.findByID(resultInfo.getBankID());
											printInfo.setReceiveExtClientName(branchInfo.getPrintName());
										}
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
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
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
										if(resultInfo.getExtClientName() != null && !resultInfo.getExtClientName().equals(""))
										{
											printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										}
										else
										{
											Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
											BranchInfo branchInfo = new BranchInfo();
											branchInfo = sett_BranchDAO.findByID(resultInfo.getBankID());
											printInfo.setReceiveExtClientName(branchInfo.getPrintName());
										}
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
								//��ǰ��¼����Ψһ���շ�
								if (lIndexPay == 1)
								{
									//����Ψһ
									printInfo.setAmount(resultInfo.getAmount());
									//�����շ���Ϣ
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if�ڲ�ת��
									{
										printInfo.setReceiveAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if����ת��(ΪӦ��ģ��,�˴��������⴦��,������Ϣ����Ϊ�ⲿ�˻���)
									{
										if(resultInfo.getExtClientName() != null && !resultInfo.getExtClientName().equals(""))
										{
											printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
										}
										else
										{
											Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
											BranchInfo branchInfo = new BranchInfo();
											branchInfo = sett_BranchDAO.findByID(resultInfo.getBankID());
											printInfo.setReceiveExtClientName(branchInfo.getPrintName());
										}
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
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));				
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID())); //����ת�˲�д��������Ϣ
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getPayGL() > 0) //if����ת��
									{
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
	
	//��Ϣ����֧��
	public PrintInfo getInterestFeePaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		String strTemp = "";
		try
		{
			Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
			//�ж��Ƿ����Ŵ���
			if(resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				ArrayList list = null;
				Sett_SyndicationLoanInterestDAO sydao = new Sett_SyndicationLoanInterestDAO();
				list = (ArrayList)sydao.findBySyndicationLoanReceiveID(lID);
				resultInfo.setSyndicationLoanInterest(list);
			}
			
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
				{
					lAccountType = NameRef.getAccountTypeByID(resultInfo.getLoanAccountID());
				}
	
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
				//modify by xwhe 2008-12-22
				if (SETTConstant.AccountType.isYTAccountType(lAccountType)) //����
				{
					//��������
				//	printInfo.setTransTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
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
					printInfo.setCompoundRate(resultInfo.getCompoundRate());
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
					printInfo.setOverDueRate(resultInfo.getOverDueRate());
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
					printInfo.setSuretyFeeRate(resultInfo.getSuretyFeeRate());
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
					printInfo.setCommissionRate(resultInfo.getCommissionRate());
				}
				if (resultInfo.getRealInterestTax() != 0.0)
				{
					printInfo.setRealInterestTax(resultInfo.getRealInterestTax());
				}
				
				//��Ϣ�ܶ������Ϣ֪ͨ��������Ϊ����
				//printInfo.setAmount(
				//	DataFormat.formatDouble(
				//		DataFormat.formatDouble(resultInfo.getRealInterest())
				//			+ DataFormat.formatDouble(resultInfo.getRealCompoundInterest())
				//			+ DataFormat.formatDouble(resultInfo.getRealOverDueInterest())));
				
				//��Ϣ��������˻����
				Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
				SubAccountAssemblerInfo subAccountInfo = new SubAccountAssemblerInfo();
				subAccountInfo = subAccountDAO.findByID(resultInfo.getSubAccountID());
				printInfo.setAmount(subAccountInfo.getSubAccountLoanInfo().getBalance());
				printInfo.setSubAccountID(resultInfo.getSubAccountID());
				
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
					printInfo.setAmount(resultInfo.getCurrentBalance());
				}
				//��Ϣ�˺�,Ҳ�Ǹ������Ѻ͵����ѵ��˻���
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPayInterestAccountID());
					printInfo.setPayCommissionAccountID(resultInfo.getPayInterestAccountID());
				}
				
				//����ͻ�ID
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setPayClientID( resultInfo.getClientID() );
				}
                //ί�е�λid modify by xwhe 2008-07-15
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getReceiveInterestAccountID()));
				}
				
                //����ֶμ�Ϣ
				if(resultInfo.getSubAccountID() > 0 && resultInfo.getOfficeID()>0 && resultInfo.getOfficeID() > 0 && resultInfo.getInterestClear()!=null)
				{
					Connection conInternal = null;
					//conInternal = getConnection();
					Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
					Vector vector = null;
					vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getTransactionTypeID(),resultInfo.getSubAccountID(),resultInfo.getLoanAccountID(),resultInfo.getOfficeID(),resultInfo.getOfficeID(),resultInfo.getInterestClear());			
					//cleanup(conInternal);	
					if ( vector != null && vector.size()> 0) 
					{
						for (int i = 0; i < vector.size(); i++) 
						{ 
							DailyAccountBalanceInfo dailAccInfo = new DailyAccountBalanceInfo();
							dailAccInfo = (DailyAccountBalanceInfo) vector.elementAt(i);
							if(i==0){
								printInfo.setStartDate1(dailAccInfo.getStartDate());
								printInfo.setEndDate1(dailAccInfo.getDate());
								printInfo.setRate1(dailAccInfo.getInterestRate());
								printInfo.setInterest1(dailAccInfo.getInterest());
								printInfo.setSumBlanceAmount1(dailAccInfo.getMCurrensInterestbalance());
							}
							if(i==1)
							{
								printInfo.setStartDate2(dailAccInfo.getStartDate());
								printInfo.setEndDate2(dailAccInfo.getDate());
								printInfo.setRate2(dailAccInfo.getInterestRate());
								printInfo.setInterest2(dailAccInfo.getInterest());
								printInfo.setSumBlanceAmount2(dailAccInfo.getMCurrensInterestbalance());
							}
							if(i==2)
							{
								printInfo.setStartDate3(dailAccInfo.getStartDate());
								printInfo.setEndDate3(dailAccInfo.getDate());
								printInfo.setRate3(dailAccInfo.getInterestRate());
								printInfo.setInterest3(dailAccInfo.getInterest());
								printInfo.setSumBlanceAmount3(dailAccInfo.getMCurrensInterestbalance());
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
	
	//����֤�� ���� ֪ͨ��� 
	public PrintInfo getDepositChangePrintInfo(long id) throws Exception
	{
	    ReportLossOrFreezeBean bean = new ReportLossOrFreezeBean();
		PrintInfo printInfo = new PrintInfo();
		try
		{ 
			TransFixedOpenInfo resultInfo = null;
			resultInfo = bean.findDepositById(id);

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
	
	//���Ŵ����
	public PrintInfo getSynLoanGrantPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
			TransGrantLoanInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				//modify by xwhe 2008-12-22
				long lAccountType = -1;
				if(resultInfo.getLoanAccountID() > 0)
				{	
				lAccountType = NameRef.getAccountTypeByID(resultInfo.getLoanAccountID());					
				}
                //modify by xwhe 2008-12-22
				if (SETTConstant.AccountType.isYTAccountType(lAccountType)) //����
				{
					//��������
				//	printInfo.setTransTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
					//�˻�����
					printInfo.setAccountTypeID(lAccountType);
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

	//���Ŵ����ջ�
	public PrintInfo getSynLoanRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
			//�ж��Ƿ����Ŵ���
			if(resultInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				ArrayList list = null;
				Sett_SyndicationLoanInterestDAO sydao = new Sett_SyndicationLoanInterestDAO();
				list = (ArrayList)sydao.findBySyndicationLoanReceiveID(lID);
				resultInfo.setSyndicationLoanInterest(list);
			}
			
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
	
	//�������ڴ浥����
	public PrintInfo getFixDepositChangePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();
			TransFixedChangeInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				
				//�Ѹ���ȡ  SDEPOSITBILLCHECKABSTRACT
				if ( resultInfo.getDepositBillStatusID() == SETTConstant.TransactionStatus.CHECK )
				{
					//�������ڴ浥����ժҪ
					if (resultInfo.getDepositBillCHECKABSTRACT() !=null)
					{
					    printInfo.setAbstract(resultInfo.getDepositBillCHECKABSTRACT());
					}
				}
				//�����Ѹ���
				else if ( resultInfo.getDepositBillStatusID() == SETTConstant.TransactionStatus.SAVE
					  ||  resultInfo.getDepositBillStatusID() == SETTConstant.TransactionStatus.APPROVALING 
					  ||  resultInfo.getDepositBillStatusID() == SETTConstant.TransactionStatus.APPROVALED )
				{
					//�������ڴ浥¼��ժҪ
					if (resultInfo.getDepositBillABSTRACT() !=null)
					{
					    printInfo.setAbstract(resultInfo.getDepositBillABSTRACT());
					}
				}
				else
				{
					if (resultInfo.getAbstract() != null)
					{
						printInfo.setAbstract(resultInfo.getAbstract());
					}
				}
				
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				
				//�������ڴ浥��
				if(resultInfo.getDepositBillNO()!=null)
				{
					printInfo.setDepositBillNO(resultInfo.getDepositBillNO());
				}

				//��ʶ�ǽ���ϵͳ��������ˮ��
				if (resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
				{
					printInfo.setInstructionNo(resultInfo.getInstructionNo());
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
	
	//���׷��ô���
	public PrintInfo getTransFeePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
			TransFeeInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				//���׷�������
				if (resultInfo.getFeeTypeID() > 0)
				{
					printInfo.setFeeTypeID(resultInfo.getFeeTypeID());
				}
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
				//�����и�����-bankid Ϊ��������			
				if (resultInfo.getFeeBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getFeeBankID());
				}
				//�������տ���-bankid Ϊ�տ�����			
				if (resultInfo.getFeeBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getFeeBankID());
				}
				//NRELATEDACCOUNTID ��Ӧ�˻���
				//NACCOUNTID ֧�������˻���
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getRelatedAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getRelatedAccountID());
				}
				//NRELATEDCLIENTID ֧�����ÿͻ���
				if (resultInfo.getRelatedClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getRelatedClientID());
				}
				if (resultInfo.getRelatedClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getRelatedClientID());
				}
				//abstractID ժҪID 
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				//SEXTACCTNO �ⲿ�˻���
				//extAcctName �ⲿ�ͻ����� 
				if (resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
				}
				if (resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
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
	
	//��֤����
	public PrintInfo getTransOpenMarginDepositPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
        {
			Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
			TransMarginOpenInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
		
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
			
            if( resultInfo.getCommissionAmount() != 0.0 )
            {
                printInfo.setCommissionAmount(resultInfo.getCommissionAmount());
            }
            if( resultInfo.getCommissionBankID() > 0 )
            {
                printInfo.setCommissionBankID(resultInfo.getCommissionBankID());
            }
            if( resultInfo.getCommissionCashFlowID() > 0 )
            {
                printInfo.setCommissionCashFlowID(resultInfo.getCommissionCashFlowID());
            }
            if( resultInfo.getCommissionCurrentAccountID() > 0 )
            {
                printInfo.setCommissionCurrentAccountID(resultInfo.getCommissionCurrentAccountID());
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

	//��֤��֧ȡ
    public PrintInfo getTransMarginWithdrawPrintInfo(long lID) throws Exception
    {
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransMarginWithdrawDAO dao = new Sett_TransMarginWithdrawDAO();
			TransMarginWithdrawInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
		
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
			//������Ϣ
			if (resultInfo.getOtherInterest() != 0.0)
			{
				printInfo.setOtherInterest(resultInfo.getOtherInterest());
			}
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
	
	//��Ϣ����֧��-���ڽ�Ϣ, ���ڴ�����Ӧ����Ϣ����������
	public PrintInfo getTransInterestSettlementPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransInterestSettlementDAO interestSettlementDAO = new Sett_TransInterestSettlementDAO();
			InterestSettmentInfo resultInfo = new InterestSettmentInfo();
			resultInfo = interestSettlementDAO.findByID(null, lID);
			
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
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//���ױ��
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//����˺�(�����ǻ����˻�)
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID( resultInfo.getAccountID() );
				}
				//���˻�ID
				if (resultInfo.getSubAccountID() > 0)
				{
					printInfo.setSubAccountID( resultInfo.getSubAccountID() );
				}
				//��������
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID( resultInfo.getTransactionTypeID() );
				}
				//���ڱ���(����Э�������)
				if (resultInfo.getBaseBalance() > 0)
				{
					printInfo.setAmount( resultInfo.getBaseBalance() );
				}
				//��Ϣ��ʼ����
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setStartDate( resultInfo.getInterestStartDate() );
				}
				//��Ϣ��������
				if (resultInfo.getInterestEndDate() != null)
				{
					printInfo.setEndDate( resultInfo.getInterestEndDate() );
				}
				//��Ϣ��������
				if (resultInfo.getInterestDays() > 0)
				{
					//��ʱʹ�ô洢������Ϣ��������
					printInfo.setNoticeDay( resultInfo.getInterestDays() );
				}
				//��Ϣ��ʼ����(��Ϣ��)
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate( resultInfo.getInterestStartDate() );
				}
				//ִ������
				if (resultInfo.getRate() > 0)
				{
					printInfo.setRate( resultInfo.getRate() );
				}
				//��Ϣ
				if (resultInfo.getInterest() > 0)
				{
					printInfo.setCurrentInterest( resultInfo.getInterest() );
				}
				//��Ϣ�������õ���//modify by xwhe 2008-10-26 
				if (resultInfo.getPecisionInterest() > 0)
				{
					printInfo.setPecisionInterestAmount(resultInfo.getPecisionInterest());
				}
				//��Ϣ�˻�
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID( resultInfo.getReceiveInterestAccountID() );
				}
				if(resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				//��ע
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract( resultInfo.getAbstract() );
				}
				
				/*****  ������Ҫ��ʾЭ����Ϣ  *****/
				//Э����Ϣ����
				if (resultInfo.getNegotiateBalance() > 0)
				{
					printInfo.setAccordInterestAmount( resultInfo.getNegotiateBalance() );
				}
				//Э����Ϣ����
				if (resultInfo.getNegotiateRate() > 0)
				{
					printInfo.setAccordInterestRate( resultInfo.getNegotiateRate() );
				}
				//Э����Ϣ
				if (resultInfo.getNegotiateInterest() > 0)
				{
					printInfo.setAccordInterest( resultInfo.getNegotiateInterest() );
				}
				
				/******  ��Ӫ����  ******/
				//�˻�����
				if (resultInfo.getAccountTypeID() > 0)
				{
					printInfo.setAccountTypeID( resultInfo.getAccountTypeID() );
				}
				//������Ϣ
				if (resultInfo.getInterest() > 0)
				{
					printInfo.setRealInterest( resultInfo.getInterest() );
				}
				if(resultInfo.getInterestTax()>0)
				{ 
					printInfo.setRealInterestTax((resultInfo.getInterestTax()));
				}
				if(resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				//���˻�
				if (resultInfo.getSubAccountID() > 0)
				{
					Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
					SubAccountLoanInfo subAccInfo = null;
					subAccInfo = subAccountDAO.findByID(resultInfo.getSubAccountID()).getSubAccountLoanInfo();
					printInfo.setLoanNoteID(subAccInfo.getLoanNoteID());
				}
               //ί�е�λid modify by xwhe 2008-07-15
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getReceiveInterestAccountID()));
				}
				//ί����Ϣ��Ϣ�˻� add by xwhe 2008-07-31
				if(resultInfo.getPayInterestAccountID()> 0)
				{
					printInfo.setPayInterestAccountID( resultInfo.getPayInterestAccountID() );
				}
				//��Ϣ��  add by xwhe 2008-08-04
				if(resultInfo.getInterestSettlementDate()!=null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestSettlementDate());
				}
				
				//modify by xwhe 2008-12-25
				if(resultInfo.getTransactionTypeID()== SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT)
				{
				//���ڽ�Ϣ(Э��)�ֶμ�Ϣ
				if(resultInfo.getSubAccountID() > 0 && resultInfo.getOfficeID()>0 && resultInfo.getOfficeID() > 0 && resultInfo.getInterestSettlementDate()!=null && resultInfo.getTransactionTypeID()!=SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST)
				{
					Connection conInternal = null;
					//conInternal = getConnection();
					Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
					Vector vector = null;
					Vector vector1 = null;
			   //	vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getSubAccountID(),resultInfo.getAccountID(),resultInfo.getOfficeID(),resultInfo.getOfficeID(),resultInfo.getInterestSettlementDate());			
					vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getTransactionTypeID(),resultInfo.getSubAccountID(),resultInfo.getAccountID(),resultInfo.getOfficeID(),resultInfo.getCurrencyID(),resultInfo.getInterestSettlementDate());
					vector1 = (Vector)dailAccountDAO.selectSettNegotiateInterest(resultInfo.getSubAccountID(),resultInfo.getAccountID(),resultInfo.getOfficeID(),resultInfo.getCurrencyID(),resultInfo.getInterestSettlementDate());
				//	cleanup(conInternal);	
					if ( vector != null && vector.size()> 0) 
					{
						for (int i = 0; i < vector.size(); i++) 
						{ 
							DailyAccountBalanceInfo dailAccInfo = new DailyAccountBalanceInfo();
							dailAccInfo = (DailyAccountBalanceInfo) vector.elementAt(i);
							if(i==0){	
							printInfo.setStartDate1(printInfo.getStartDate());
							printInfo.setEndDate1(dailAccInfo.getDate());
							printInfo.setRate1(dailAccInfo.getInterestRate());							
							printInfo.setInterest1(dailAccInfo.getInterest());
                             //modify by xwhe 2008-11-20 ��ʱ�滻������
							//printInfo.setInterest1(resultInfo.getInterest());
							printInfo.setSumBlanceAmount1(dailAccInfo.getMCurrensInterestbalance());
							}
							if(i==1)
							{
							printInfo.setStartDate2(dailAccInfo.getStartDate());
							printInfo.setEndDate2(dailAccInfo.getDate());
							printInfo.setRate2(dailAccInfo.getInterestRate());
							printInfo.setInterest2(dailAccInfo.getInterest());
							printInfo.setSumBlanceAmount2(dailAccInfo.getMCurrensInterestbalance());
							}
							if(i==2)
							{
							printInfo.setStartDate3(dailAccInfo.getStartDate());
							printInfo.setEndDate3(dailAccInfo.getDate());
							printInfo.setRate3(dailAccInfo.getInterestRate());
							printInfo.setInterest3(dailAccInfo.getInterest());
							printInfo.setSumBlanceAmount3(dailAccInfo.getMCurrensInterestbalance());
							}
						}
					}	
					if ( vector1 != null && vector1.size()> 0) 
					{
						for (int i = 0; i < vector1.size(); i++) 
						{ 
							DailyAccountBalanceInfo dailAccInfo = new DailyAccountBalanceInfo();
							dailAccInfo = (DailyAccountBalanceInfo) vector1.elementAt(i);
							if(i==0){
							printInfo.setNegotiateStartDate1(printInfo.getStartDate());
							printInfo.setNegotiateEndtDate1(dailAccInfo.getDate());
							printInfo.setNegotiateRate1(dailAccInfo.getInterestRate());
							printInfo.setNegotiateInterest1(dailAccInfo.getInterest());
							//modify by xwhe 2008-12-18
							printInfo.setSumNegoBlanceAmount1(dailAccInfo.getMAc_mnegoInterestbalance());
							}
							if(i==1)
							{
							printInfo.setNegotiateStartDate2(dailAccInfo.getStartDate());
							printInfo.setNegotiateEndtDate2(dailAccInfo.getDate());
							printInfo.setNegotiateRate2(dailAccInfo.getInterestRate());
							printInfo.setNegotiateInterest2(dailAccInfo.getInterest());
							//modify by xwhe 2008-12-18
							printInfo.setSumNegoBlanceAmount2(dailAccInfo.getMAc_mnegoInterestbalance());
							}							
						}
					}	
			
			     }
				}
				else
				{              
					if(resultInfo.getSubAccountID() > 0 && resultInfo.getOfficeID()>0 && resultInfo.getOfficeID() > 0 && resultInfo.getInterestSettlementDate()!=null && resultInfo.getTransactionTypeID()!=SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST)
					{
						Connection conInternal = null;
					//	conInternal = getConnection();
						Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
						Vector vector = null;
						Vector vector1 = null;
				      //vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getSubAccountID(),resultInfo.getAccountID(),resultInfo.getOfficeID(),resultInfo.getOfficeID(),resultInfo.getInterestSettlementDate());			
						vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getTransactionTypeID(),resultInfo.getSubAccountID(),resultInfo.getAccountID(),resultInfo.getOfficeID(),resultInfo.getCurrencyID(),resultInfo.getInterestSettlementDate());
						vector1 = (Vector)dailAccountDAO.selectSettNegotiateInterest(resultInfo.getSubAccountID(),resultInfo.getAccountID(),resultInfo.getOfficeID(),resultInfo.getCurrencyID(),resultInfo.getInterestSettlementDate());
					//	cleanup(conInternal);	
						if ( vector != null && vector.size()> 0) 
						{
							for (int i = 0; i < vector.size(); i++) 
							{ 
								DailyAccountBalanceInfo dailAccInfo = new DailyAccountBalanceInfo();
								dailAccInfo = (DailyAccountBalanceInfo) vector.elementAt(i);
								if(i==0){	
								printInfo.setStartDate1(dailAccInfo.getStartDate());
								printInfo.setEndDate1(dailAccInfo.getDate());
								printInfo.setRate1(dailAccInfo.getInterestRate());							
								printInfo.setInterest1(dailAccInfo.getInterest());
	                             //modify by xwhe 2008-11-20 ��ʱ�滻������
								//printInfo.setInterest1(resultInfo.getInterest());
								printInfo.setSumBlanceAmount1(dailAccInfo.getMCurrensInterestbalance());
								}
								if(i==1)
								{
								printInfo.setStartDate2(dailAccInfo.getStartDate());
								printInfo.setEndDate2(dailAccInfo.getDate());
								printInfo.setRate2(dailAccInfo.getInterestRate());
								printInfo.setInterest2(dailAccInfo.getInterest());
								printInfo.setSumBlanceAmount2(dailAccInfo.getMCurrensInterestbalance());
								}
								if(i==2)
								{
								printInfo.setStartDate3(dailAccInfo.getStartDate());
								printInfo.setEndDate3(dailAccInfo.getDate());
								printInfo.setRate3(dailAccInfo.getInterestRate());
								printInfo.setInterest3(dailAccInfo.getInterest());
								printInfo.setSumBlanceAmount3(dailAccInfo.getMCurrensInterestbalance());
								}
							}
						}	
						if ( vector1 != null && vector1.size()> 0) 
						{
							for (int i = 0; i < vector1.size(); i++) 
							{ 
								DailyAccountBalanceInfo dailAccInfo = new DailyAccountBalanceInfo();
								dailAccInfo = (DailyAccountBalanceInfo) vector1.elementAt(i);
								if(i==0){
								printInfo.setNegotiateStartDate1(dailAccInfo.getStartDate());
								printInfo.setNegotiateEndtDate1(dailAccInfo.getDate());
								printInfo.setNegotiateRate1(dailAccInfo.getInterestRate());
								printInfo.setNegotiateInterest1(dailAccInfo.getInterest());
								//modify by xwhe 2008-12-18
								printInfo.setSumNegoBlanceAmount1(dailAccInfo.getMAc_mnegoInterestbalance());
								}
								if(i==1)
								{
								printInfo.setNegotiateStartDate2(dailAccInfo.getStartDate());
								printInfo.setNegotiateEndtDate2(dailAccInfo.getDate());
								printInfo.setNegotiateRate2(dailAccInfo.getInterestRate());
								printInfo.setNegotiateInterest2(dailAccInfo.getInterest());
								//modify by xwhe 2008-12-18
								printInfo.setSumNegoBlanceAmount2(dailAccInfo.getMAc_mnegoInterestbalance());
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
    
	public HashMap TransformOperationData(PrintInfo printOperationInfo) throws Exception
	{
		
		//minzhao����̫���ˣ����ս������ͷ���ȡ��
		//���ҵ������Ϊ�Ŵ��ʲ�ת��
		if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERREPAY
				||printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.AGENTTRANSFERREPAY 
				||printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERPAY )
		{
			//�ͻ�����
			usedFields.put("ClientName", Env.getClientName());
			
			//ִ����
			usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //��ʾYYYY��MM��DD��
			
			//��Ϣ��
			usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //��ʾYYYY��MM��DD��
			
			//������
			usedFields.put("OperationEndDate", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ));
			
			//��ǰʱ��
			usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //��ʾYYYY��MM��DD��
			
			//���ױ��
			usedFields.put("TransNo", printOperationInfo.getTransNo());
			
			//���´�����
			usedFields.put("OfficeName",NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
			
			//�Ŵ��ʲ�ת��ҵ��ʼȡ���
			//�Ŵ��ʲ�ת���տ�Ϊ�ⲿ�˻������˴�ҵ��
			if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERREPAY)
			{
				CounterparBankDao bankdao=new CounterparBankDao();
				CounterpartBankInfo bankinfo =new CounterpartBankInfo();
				bankinfo=(CounterpartBankInfo)bankdao.findByID(printOperationInfo.getPayBankID(), CounterpartBankInfo.class);
				
				//������ȫ��			
				usedFields.put("PayAccountName",bankinfo.getCounterparname());
				//����˺�
				usedFields.put("PayAccountNo", bankinfo.getCounterpartbankno());
				//�������������
				usedFields.put("PayBankName", bankinfo.getCounterpartbankname() );
			}
			//�Ŵ��ʲ�ת�ô����տ�Ϊ�ڲ��˻�
			else if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.AGENTTRANSFERREPAY )
			{
				
				//������ȫ��
				usedFields.put("PayAccountName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));
				
				//����˺�
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));
				
				//�������������
				usedFields.put("PayBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
			}
			//�Ŵ��ʲ�ת�ø������˻������˻�
			else if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERPAY )
			{
				if(printOperationInfo.getPayBankID()>0)//������
				{
					//������ȫ��
					usedFields.put("PayAccountName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
					
					//����˺�
					usedFields.put("PayAccountNo", NameRef.getBankAccountCodeByBankID( printOperationInfo.getPayBankID() ));
					
					//�������������
					usedFields.put("PayBankName", NameRef.getBankNameByID( printOperationInfo.getPayBankID() ));
				}
				else//����
				{
					//������ȫ��
					usedFields.put("PayAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
					
					//����˺�
					usedFields.put("PayAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getPayGL() ));
				}
			}
			
			//�Ŵ��ʲ�ת�ÿ�ʼȡ�տ
			//�Ŵ��ʲ�ת���տ��տΪ���˻��У����˴�ҵ��
			if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERREPAY )
			{
				if(printOperationInfo.getReceiveBankID()>0)//������
				{
					//������ȫ��
					usedFields.put("ReceiveAccountName", NameRef.getOfficeNameByID( printOperationInfo.getReceiveBankID() ));
					
					//����˺�
					usedFields.put("ReceiveAccountNo", NameRef.getBankAccountCodeByBankID( printOperationInfo.getReceiveBankID() ));
					
					//�������������
					usedFields.put("ReceiveBankName", NameRef.getBankNameByID( printOperationInfo.getReceiveBankID() ));
				}
				else//����
				{
					//������ȫ��
					usedFields.put("ReceiveAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
					
					//����˺�
					usedFields.put("ReceiveAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getReceiveGL() ));
				}
			}
			//�Ŵ��ʲ�ת�ô����տ��տ����ʾ�տ
			else if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.AGENTTRANSFERREPAY )
			{
				
				//������ȫ��
				usedFields.put("ReceiveAccountName", "");
				
				//����˺�
				usedFields.put("ReceiveAccountNo", "");
				
				//�������������
				usedFields.put("ReceiveBankName", "");
			}
			//�Ŵ��ʲ�ת�ø����տΪ�ⲿ�˻�
			else if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERPAY )
			{

				CounterparBankDao bankdao=new CounterparBankDao();
				CounterpartBankInfo bankinfo =new CounterpartBankInfo();
				bankinfo=(CounterpartBankInfo)bankdao.findByID(printOperationInfo.getReceiveBankID(), CounterpartBankInfo.class);
				
				String province=bankinfo.getProvince();
				String city=bankinfo.getCity();
				//�տ��˵�ַ
				usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(province+"ʡ"+city+"��"));
				
				//������ȫ��			
				usedFields.put("ReceiveAccountName",bankinfo.getCounterparname());
				//����˺�
				usedFields.put("ReceiveAccountNo", bankinfo.getCounterpartbankno());
				//�������������
				usedFields.put("ReceiveBankName", bankinfo.getCounterpartbankname() );
			}
			if (printOperationInfo.getAmount() != 0.0)
			{
				//������Сд
				usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() ));
				
				//�������д
				usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
			}
			else
			{
				//������Сд
				usedFields.put("Amount", "0.00");
				
				String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
				
				//�������д
				usedFields.put("ChineseAmount", strChineseAmount);
			}
			
			if (printOperationInfo.getInputUserID() > 0)
			{
				//¼����
				usedFields.put("InputUserName", NameRef.getUserNameByID( printOperationInfo.getInputUserID()) );
			}
			else
			{
				//¼����
				usedFields.put("InputUserName", "����");
			}

			if (printOperationInfo.getCheckUserID() > 0)
			{
				//������
				usedFields.put("CheckUserName", NameRef.getUserNameByID( printOperationInfo.getCheckUserID()) );
			}
			else
			{
				if (printOperationInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					//������
					usedFields.put("CheckUserName", "����");
				}
			}
			usedFields.put("Abstract", printOperationInfo.getAbstract());
			usedFields.put("CommissionFee", printOperationInfo.getCommission()+"");
			usedFields.put("ChineseCommissionFee", this.getChineseFormatAmount(printOperationInfo.getCommission(), 1));
			
			
		}
		//���Ϊ����ҵ������
		else
		{
		//�ͻ�����
		usedFields.put("ClientName", Env.getClientName());
		
		//ִ����
		usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //��ʾYYYY��MM��DD��
		
		//��Ϣ��
		usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //��ʾYYYY��MM��DD��
		
		//������
		usedFields.put("OperationEndDate", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ));
		
		//��ǰʱ��
		usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //��ʾYYYY��MM��DD��
		
		//���ױ��
		usedFields.put("TransNo", printOperationInfo.getTransNo());
		
		//����˻�
		if(printOperationInfo.getPayAccountID() > 0)
		{
			//������ȫ��
			usedFields.put("PayAccountName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));
			
			//����˺�
			usedFields.put("PayAccountNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));
			
			//�������������
			usedFields.put("PayBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
			
			//"���и�����ش���"ҵ��ĸ�������б���ӿ�����������ȡ��
			if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKPAY_NOTONLINE 
			 //���и���
			 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKPAY )
			{
				if( printOperationInfo.getPayBankID() > 0 )
				{
					Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
					BranchInfo branchInfo = new BranchInfo();
					branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());
					
					usedFields.put("PayBank", DataFormat.formatString( branchInfo.getPrintName() ));
				}
			}
		}
		
		//�ⲿ�����˻� "�����տ�"ҵ��ĸ��Ϊ�ⲿ�˻�,���˴�ҵ��
		else if( printOperationInfo.getPayBankID() > 0 && printOperationInfo.getTransTypeID() != SETTConstant.TransactionType.BANKRECEIVE )
		{
			Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
			BranchInfo branchInfo = new BranchInfo();
			branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());
			
			//������ȫ��			
			if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION )
			{
				usedFields.put("PayAccountName", "����������޹�˾");
			}
			else
			{
				usedFields.put("PayAccountName", DataFormat.formatString( branchInfo.getPrintName() ));
			}
			
			if( printOperationInfo.getTransTypeID() != SETTConstant.TransactionType.OPENFIXEDDEPOSIT && printOperationInfo.getTransTypeID() != SETTConstant.TransactionType.OPENNOTIFYACCOUNT )
			{
				//����˺�
				usedFields.put("PayAccountNo", DataFormat.formatString( branchInfo.getBankAccountCode() ));
				
				//�������������
				usedFields.put("PayBankName", DataFormat.formatString( branchInfo.getBranchName() ));
			}
			
			//�����Ŀ����
			usedFields.put("PaySubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
		}
		else if ( printOperationInfo.getPayExtClientName() != null 
				&& !printOperationInfo.getPayExtClientName().equals("") )
		{
			//�ж�  һ������(�����)  ��������
			if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.ONETOMULTI )
			{
				//������ȫ��
				usedFields.put("PayAccountName", DataFormat.formatString( printOperationInfo.getPayExtClientName() ));
				
				//����˺�
				usedFields.put("PayAccountNo", DataFormat.formatString( printOperationInfo.getPayExtAccountNo() ));
				
				//�������������
				usedFields.put("PayBankName", DataFormat.formatString( printOperationInfo.getPayExtRemitInBank() ));
			}
		}
		else
		{
			if(printOperationInfo.getExtClientName() != null && printOperationInfo.getExtClientName().trim().length() > 0)
			{
				//������ȫ��
				usedFields.put("PayAccountName", printOperationInfo.getExtClientName());
				
				//����˺�
				usedFields.put("PayAccountNo", printOperationInfo.getExtAccountNo());
				
				//�������������
				usedFields.put("PayBankName", printOperationInfo.getExtRemitInBank());
			}
			//����	
			else
			{
				//������ȫ��
				usedFields.put("PayAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
				
				//����˺�
				usedFields.put("PayAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getPayGL() ));
				
				//�����Ŀ����
				usedFields.put("PaySubjectCode", NameRef.getGLKNoByID( printOperationInfo.getPayGL() ));
								
			}
		}
		
		if (printOperationInfo.getReceiveAccountID() > 0)
		{
			//�տ���ȫ��
			usedFields.put("ReceiveAccountName", NameRef.getAccountNameByID( printOperationInfo.getReceiveAccountID() ));
			
			//�տ�˺�
			usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
			
			//�տ����������
			usedFields.put("ReceiveBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
			
			//"�����տ�"ҵ����տ�����б���ӿ�����������ȡ��
			if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKRECEIVE )
			{
				if( printOperationInfo.getReceiveBankID() > 0 )
				{
					Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
					BranchInfo branchInfo = new BranchInfo();
					branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());
					
					usedFields.put("ReceiveBank", DataFormat.formatString( branchInfo.getPrintName() ));
				}
			}

			//���ӻص����ӡ����˾���ڵص�global.evoucher.printclientaddress(global.xml)
			String clientAddress = "";
			clientAddress = Config.getProperty(ConfigConstant.GLOBAL_EVOUCHER_PRINTCLIENTADDRESS);
			//�տ��˻����ַ
			usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(clientAddress) );
		}
		//2007-8-31 "���⽻��"ҵ���տ�п����������˻�
		else if ( (printOperationInfo.getReceiveBankID() > 0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION)
			   || (printOperationInfo.getReceiveBankID() > 0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRANSFEE)
			   || (printOperationInfo.getReceiveBankID() > 0 && printOperationInfo.getTransTypeID() >1000)
			   )
		{
			Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
			BranchInfo branchInfo = new BranchInfo();
			branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());
			
			//�տ���ȫ��
			if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION )
			{
				usedFields.put("ReceiveAccountName", "����������޹�˾");
			}
			else
			{
				usedFields.put("ReceiveAccountName", DataFormat.formatString( branchInfo.getPrintName() ));
			}
			
			//�տ�˺�
			usedFields.put("ReceiveAccountNo", DataFormat.formatString( branchInfo.getBankAccountCode() ));
			
			//�տ����������
			usedFields.put("ReceiveBankName", DataFormat.formatString( branchInfo.getBranchName() ));
			
			//�տ��Ŀ����
			usedFields.put("ReceiveSubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
			
			String strTemp = "";
			if (branchInfo.getBranchProvince() != null && !branchInfo.getBranchProvince().equals("") ){
				if (branchInfo.getBranchProvince().indexOf("ʡ") < 0)
				{
					strTemp = branchInfo.getBranchProvince() + "ʡ";
				}
				else
				{
					strTemp = branchInfo.getBranchProvince();
				}
			}
			
			if (branchInfo.getBranchCity() != null && !branchInfo.getBranchCity().equals("")){
				if (branchInfo.getBranchCity().indexOf("��") < 0)
				{
					strTemp += branchInfo.getBranchCity() + "��";
				}
				else
				{
					strTemp += branchInfo.getBranchCity();
				}
			}	
			
			//�տ��˻����ַ
			usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(strTemp));
		}		
		else if ( printOperationInfo.getReceiveExtClientName() != null 
				&& !printOperationInfo.getReceiveExtClientName().equals("") )
		{
			//�ж�  һ������(�����)  ��������
			if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.ONETOMULTI )
			{
				//�տ���ȫ��
				usedFields.put("ReceiveAccountName", DataFormat.formatString( printOperationInfo.getReceiveExtClientName() ));
				
				//�տ�˺�
				usedFields.put("ReceiveAccountNo", DataFormat.formatString( printOperationInfo.getReceiveExtAccountNo() ));
				
				//�տ����������
				usedFields.put("ReceiveBankName", DataFormat.formatString( printOperationInfo.getReceiveExtRemitInBank() ));
			}
		}
		
		//�����е�����ҵ�����ͣ�����Ҫ����ʾ�����˻���Ϣ
		else if (printOperationInfo.getReceiveGL()> 0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION)
		{
            //�տ�˺�
			usedFields.put("ReceiveAccountNo", printOperationInfo.getReceiveExtAccountNo());
			
			//�տ����������
			usedFields.put("ReceiveBankName", printOperationInfo.getExtRemitInBank());
			
		}
		else
		{
			if ( ( printOperationInfo.getExtClientName() != null
			  && printOperationInfo.getExtClientName().trim().length() > 0 )
			  || printOperationInfo.getReceiveBankID() > 0 )
			{
				//�տ���ȫ��
				usedFields.put("ReceiveAccountName", printOperationInfo.getExtClientName());
				
				//�տ�˺�
				usedFields.put("ReceiveAccountNo", printOperationInfo.getExtAccountNo());
				
				//�տ����������
				usedFields.put("ReceiveBankName", printOperationInfo.getExtRemitInBank());
				
				//�տ��˻���ص�
				String strTemp = "";
				if ( printOperationInfo.getExtRemitInProvince() != null && !printOperationInfo.getExtRemitInProvince().equals("") )
				{
					if (printOperationInfo.getExtRemitInProvince().indexOf("ʡ") < 0)
					{
						strTemp = printOperationInfo.getExtRemitInProvince() + "ʡ";
					}
					else
					{
						strTemp = printOperationInfo.getExtRemitInProvince();
					}
				}
				
				if ( printOperationInfo.getExtRemitInCity() != null && !printOperationInfo.getExtRemitInCity().equals("") )
				{
					if (printOperationInfo.getExtRemitInCity().indexOf("��") < 0)
					{
						strTemp += printOperationInfo.getExtRemitInCity() + "��";
					}
					else
					{
						strTemp += printOperationInfo.getExtRemitInCity();
					}
				}
				//�տ��˻����ַ
				usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(strTemp));
			}
			//���׷���
			else if( printOperationInfo.getFeeTypeID() > 0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRANSFEE )
			{
				TransactionFeeTypeBiz interestSetting = new TransactionFeeTypeBiz();
				TransFeeTypeSetInfo transFeeTypeSetInfo = interestSetting.findTransactionFeeTypeByID(printOperationInfo.getFeeTypeID());
				
				//�տ��Ŀ����
				if(transFeeTypeSetInfo.getSubjectCode() != null)
				{					
					usedFields.put("ReceiveAccountName", NameRef.getSubjectNameByCode(printOperationInfo.getOfficeID(), transFeeTypeSetInfo.getSubjectCode()));
					usedFields.put("ReceiveAccountNo", transFeeTypeSetInfo.getSubjectCode());
				}
			}
			//����
			else 
			{
				//�տ���ȫ��
				usedFields.put("ReceiveAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				
				//�տ�˺�
				usedFields.put("ReceiveAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getReceiveGL() ));
				
				//�տ��Ŀ����
				usedFields.put("ReceiveSubjectCode", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL() ));
								
			}
		}
		
		if (printOperationInfo.getAmount() != 0.0)
		{
			//������Сд
			usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() ));
			
			//�������д
			usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
		}
		else
		{
			//������Сд
			usedFields.put("Amount", "0.00");
			
			String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
			
			//�������д
			usedFields.put("ChineseAmount", strChineseAmount);
		}
		
		if (printOperationInfo.getInputUserID() > 0)
		{
			//¼����
			usedFields.put("InputUserName", NameRef.getUserNameByID( printOperationInfo.getInputUserID()) );
		}
		else
		{
			//¼����
			usedFields.put("InputUserName", "����");
		}

		if (printOperationInfo.getCheckUserID() > 0)
		{
			//������
			usedFields.put("CheckUserName", NameRef.getUserNameByID( printOperationInfo.getCheckUserID()) );
		}
		else
		{
			if (printOperationInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK)
			{
				//������
				usedFields.put("CheckUserName", "����");
			}
		}
		//add by xwhe 2008-08-07 
		if(printOperationInfo.getExamUserName()!=null && !printOperationInfo.getExamUserName().equals(""))
		{
			usedFields.put("ExamUser", printOperationInfo.getExamUserName());
		}
		
		if (printOperationInfo.getOfficeID() > 0)
		{
			//���´�����
			usedFields.put("OfficeName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
		}
		
		if (printOperationInfo.getCurrencyID() > 0)
		{
			//����
			usedFields.put("CurrencyName", Constant.CurrencyType.getName( printOperationInfo.getCurrencyID() ));
		}
		
		//ժҪ
		if(printOperationInfo.getInterestStartDate() != null)
		{
			String strAbstract = "";
			//��������-��������
			if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				strAbstract = "��Ϣ�գ�" + DataFormat.getChineseDateString( printOperationInfo.getStartDate() );
			}
			//���ڴ�����Ӧ����Ϣ,���ڴ���Ϣ,���ڴ�����,֪ͨ������  add by xwhe 2008-08-04
			if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST ||
			   printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT||
			   printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST|| 
			   printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST
			   )
			{
				strAbstract = "��Ϣ�գ�" + DataFormat.getChineseDateString( printOperationInfo.getInterestClearDate());
			}
			else
			{   
				if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION 
						&& printOperationInfo.getOperationType()==1003)
				{
					
				}
				else
				{
				strAbstract = "��Ϣ�գ�" + DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() );
				}
			}
				
			if(printOperationInfo.getAbstract() != null && !printOperationInfo.getAbstract().equals(""))
			{
				strAbstract += "��";
				strAbstract += printOperationInfo.getAbstract();
			}
            //modify by xwhe 2008-12-16
			if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION 
				&& printOperationInfo.getOperationType()==1003
				&& printOperationInfo.getAbstract() != null && !printOperationInfo.getAbstract().equals(""))
			{
				strAbstract = printOperationInfo.getAbstract();
			}
			usedFields.put("Abstract", strAbstract);
		}
		else
		{
			usedFields.put("Abstract", printOperationInfo.getAbstract());
		}
		
		/*****����ת�˴�����Ʊ*****/
		//����ת���ڣ�����֧ȡ��  ֪ͨ���֧ȡ
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER 
		 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
		{
			//����Ƕ���֧ȡ����֪ͨ���֧ȡ�������տ���ջ����˻��տ��������
			//�տ
			if (printOperationInfo.getReceiveInterestAccountID() > 0)
			{			
				usedFields.put("InterestextAccountNo",  "������Ϣ,���ո��㵥λ("+NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID())+")���˻�");				
			}			
			//��Ϣ�ⲿ�˻� //modify by xwhe 2008-08-15 �Ƕ����������Ϣ���˻�
			if(printOperationInfo.getInterestextaccountno() != null && !printOperationInfo.getInterestextaccountno().equals(""))
			{
				usedFields.put("InterestextAccountNo", "������Ϣ,���ո��㵥λ("+DataFormat.formatString( printOperationInfo.getInterestextaccountno())+")���˻�");
			}			
			//����֧ȡ,����֧ȡ
			if(printOperationInfo.getAccountAmount() > printOperationInfo.getDrawAmount() && printOperationInfo.getInterestStartDate().compareTo(printOperationInfo.getEndDate()) < 0)
	        {
                TransFixedOpenInfo transFixedOpenInfo = new TransFixedOpenInfo();
                Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
                transFixedOpenInfo = dao.findByOldDepositNo(printOperationInfo.getFixedDepositNo());
                usedFields.put("HNewDepositNo", DataFormat.formatString(transFixedOpenInfo.getDepositNo()));
                
                usedFields.put("HNewAmount",  this.getFormatAmount( transFixedOpenInfo.getAmount() ));
                
                usedFields.put("HNewChineseAmount", this.getChineseFormatAmount( transFixedOpenInfo.getAmount(), printOperationInfo.getCurrencyID() ));
                
                //�´浥��ʼ����
                usedFields.put("HNewStartDate", DataFormat.getChineseDateString(transFixedOpenInfo.getStartDate()));
                //�´浥��������
                usedFields.put("HNewEndDate", DataFormat.getChineseDateString(transFixedOpenInfo.getEndDate()));
                //�´浥��Ϣ��
                usedFields.put("HNewInterestStartDate", DataFormat.getChineseDateString(transFixedOpenInfo.getInterestStartDate()));
                
                usedFields.put("HNewDepositTerm", DataFormat.formatString(String.valueOf(transFixedOpenInfo.getDepositTerm()) + "����"));
                usedFields.put("HNewRate", DataFormat.formatRate(transFixedOpenInfo.getRate(), 6) + "%");
                usedFields.put("HNewAccountName", NameRef.getAccountNameByID(transFixedOpenInfo.getAccountID()));
                usedFields.put("HNewAccountNo", NameRef.getAccountNoByID(transFixedOpenInfo.getAccountID()));
                usedFields.put("HNewTransNo", DataFormat.formatString(transFixedOpenInfo.getTransNo()));
                if(transFixedOpenInfo.getInterestStartDate() != null)
                {
                	//modify by xwhe 2008-07-23 �޸���Ϣ��Ϊ��ʼ��               
                	 String strAbstract = "��Ϣ�գ�" + DataFormat.getChineseDateString(transFixedOpenInfo.getStartDate());
                    if(transFixedOpenInfo.getAbstract() != null && !transFixedOpenInfo.getAbstract().equals(""))
                    {
                        strAbstract = strAbstract + "��";
                        strAbstract = strAbstract + transFixedOpenInfo.getAbstract();
                    }
                    usedFields.put("HNewAbstract", strAbstract);
                }
                else
                {
                    usedFields.put("HNewAbstract", transFixedOpenInfo.getAbstract());
                }
	        }
			
			//֪ͨ����֧ȡ
			if(printOperationInfo.getAccountAmount() > printOperationInfo.getDrawAmount() && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
                usedFields.put("HNewDepositNo", DataFormat.formatString(printOperationInfo.getFixedDepositNo()));
                
                usedFields.put("HNewAmount",  this.getFormatAmount( printOperationInfo.getDepositBalance() ));
                
                usedFields.put("HNewChineseAmount", this.getChineseFormatAmount( printOperationInfo.getDepositBalance(), printOperationInfo.getCurrencyID() ));
                
                //�´浥��ʼ����
                usedFields.put("HNewStartDate", DataFormat.getChineseDateString(printOperationInfo.getStartDate()));
                
                //�´浥��Ϣ��
                usedFields.put("HNewInterestStartDate", DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate()));
                
                usedFields.put("HNewDepositTerm", DataFormat.formatString(String.valueOf(printOperationInfo.getNoticeDay() - 10000) + "��"));
                
                usedFields.put("HNewRate", DataFormat.formatRate(printOperationInfo.getRate(), 6) + "%");
                
                usedFields.put("HNewAccountName", NameRef.getAccountNameByID(printOperationInfo.getAccountID()));
                
                usedFields.put("HNewAccountNo", NameRef.getAccountNoByID(printOperationInfo.getAccountID()));
                
                usedFields.put("HNewTransNo", DataFormat.formatString(printOperationInfo.getTransNo()));
                if(printOperationInfo.getInterestStartDate() != null)
                {
                    String strAbstract = "��Ϣ�գ�" + DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());
                    if(printOperationInfo.getAbstract() != null && !printOperationInfo.getAbstract().equals(""))
                    {
                        strAbstract = strAbstract + "��";
                        strAbstract = strAbstract + printOperationInfo.getAbstract();
                    }
                    usedFields.put("HNewAbstract", strAbstract);
                }
                else
                {
                    usedFields.put("HNewAbstract", printOperationInfo.getAbstract());
                }
			}
		}
		else
		{
			//��������
			if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				//������˻�ID����Ϣ�˻���(2007-10-19 ��Ϣ�˻���Ӧ������һ�����ƴ洢)
				if (printOperationInfo.getReceiveInterestAccountID() > 0)
				{					
					//modify by xwhe 2008-08-26
					if(printOperationInfo.getIsCapitalAndInterestTransfer()!=1)
					{
						usedFields.put("InterestextAccountNo", "������Ϣ,���ո��㵥λ("+NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID())+")���˻�");	
					}					
				}
				else if (printOperationInfo.getExtAccountNo() != null && !printOperationInfo.getExtAccountNo().equals(""))
				{
					//�տ�˺�(��Ϣ�ⲿ�˻���)
					usedFields.put("InterestextAccountNo", DataFormat.formatString(printOperationInfo.getExtAccountNo()) );
				}
				
				//�Ƿ�������(��:���汾���� = ��Ϣ + ������)
				if(printOperationInfo.getIsCapitalAndInterestTransfer() == 1)
				{
					if(printOperationInfo.getRealInterest() != 0.0 || printOperationInfo.getAmount() != 0.0)
					{
						//������Сд
						usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getRealInterest() + printOperationInfo.getAmount() ));
						
						//�������д
						usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getRealInterest() + printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
						
						printOperationInfo.setRealInterest(0.0);
					}
					else
					{
						//������Сд
						usedFields.put("Amount", "0.00");
						
						String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
						
						//�������д
						usedFields.put("ChineseAmount", strChineseAmount);
					}
				}
				else
				{
					if(printOperationInfo.getAmount() != 0.0)
					{
						//������Сд
						usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() ));
						
						//�������д
						usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
					}
					else
					{
						//������Сд
						usedFields.put("Amount", "0.00");
						
						String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
						
						//�������д
						usedFields.put("ChineseAmount", strChineseAmount);
					}
				}
			}
			//����ҵ��
			else
			{
				//ί�д����ջ�
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
				}				
				//��Ϣ����֧��-���ڽ�Ϣ
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT)
				{
					//��Ϣ�˻���
					if (printOperationInfo.getReceiveInterestAccountID() > 0)
					{
						//�տ�˺�(��Ϣ�˻���)
						usedFields.put("InterestextAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID()));
					}
				}
				
				//���ڴ�����Ӧ����Ϣ����������, ֪ͨ������Ӧ����Ϣ����������, ���ڴ�����Ӧ����Ϣ����������
				else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST
					  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST
					  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST )
				{
					if (printOperationInfo.getPayAccountID() > 0)
					{
		                AccountBean accountbean = new AccountBean();
		                GLSubjectDefinitionInfo subjectDefinitionInfo = new GLSubjectDefinitionInfo();
		                subjectDefinitionInfo = accountbean.getSubjectByAccountID(printOperationInfo.getPayAccountID(), printOperationInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_PREDRAWINTEREST);
		                if(subjectDefinitionInfo != null)
		                {
		                	//��Ϣ���˵��˻��ļ����Ŀ��
		                    usedFields.put("InterestextAccountNo", DataFormat.formatString( subjectDefinitionInfo.getSsubjectName()+"-"+ subjectDefinitionInfo.getSsubjectCode() ));
		                }
					}
				}
					
				if(printOperationInfo.getAmount() != 0.0)
				{
					//������Сд
					usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() ));
					
					//�������д
					usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
				}
				else
				{
					//������Сд
					usedFields.put("Amount", "0.00");
					
					String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
					
					//�������д
					usedFields.put("ChineseAmount", strChineseAmount);
				}
			}
		}
				
		//�����տ�(ר�����������Ѵ�ӡ,�տΪ���˿�Ŀ��Ϣ)
        if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.OPENMARGIN)
        {
            if(printOperationInfo.getCommissionCurrentAccountID() > 0)
            {
                usedFields.put("CommissionPayAccountName", NameRef.getAccountNameByID(printOperationInfo.getCommissionCurrentAccountID()));
                usedFields.put("CommissionPayAccountNo", NameRef.getAccountNoByID(printOperationInfo.getCommissionCurrentAccountID()));
                usedFields.put("CommissionPayBankName", NameRef.getOfficeNameByID(printOperationInfo.getOfficeID()));
            } 
            else if(printOperationInfo.getCommissionBankID() > 0)
            {
                Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
                BranchInfo branchInfo = new BranchInfo();
                branchInfo = sett_BranchDAO.findByID(printOperationInfo.getCommissionBankID());
                usedFields.put("CommissionPayAccountName", DataFormat.formatString(branchInfo.getPrintName()));
                usedFields.put("CommissionPayAccountNo", DataFormat.formatString(branchInfo.getBankAccountCode()));
                usedFields.put("CommissionPayBankName", DataFormat.formatString(branchInfo.getBranchName()));
            }
            
            if(printOperationInfo.getReceiveAccountID() > 0)
            {
                AccountBean accountbean = new AccountBean();
                GLSubjectDefinitionInfo subjectDefinitionInfo = new GLSubjectDefinitionInfo();
                subjectDefinitionInfo = accountbean.getSubjectByAccountID(printOperationInfo.getReceiveAccountID(), printOperationInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_COMMISSION);
                if(subjectDefinitionInfo != null)
                {
                    usedFields.put("CommissionReceiveAccountName", DataFormat.formatString(subjectDefinitionInfo.getSsubjectName()));
                    usedFields.put("CommissionReceiveAccountNo", DataFormat.formatString(subjectDefinitionInfo.getSsubjectCode()));
                    usedFields.put("CommissionReceiveBankName", NameRef.getOfficeNameByID(printOperationInfo.getOfficeID()));
                }
            }
            //modify by xwhe 2008-08-04
            if(printOperationInfo.getCommissionAmount()>0.0)
            {
            usedFields.put("CommissionFee", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(printOperationInfo.getCommissionAmount(), 2));
            String strChineseAmount = ChineseCurrency.showChinese(DataFormat.formatAmount(printOperationInfo.getCommissionAmount()), printOperationInfo.getCurrencyID());
            usedFields.put("ChineseCommissionFee", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+strChineseAmount);
            }
        }
		
		//����¼����  ����������  ����ǩ����
        if ( printOperationInfo.getTransNo() != null && !printOperationInfo.getTransNo().equals("") )
        {
			ShowWithDrawInfo info = new ShowWithDrawInfo();
			info.setTransNo( printOperationInfo.getTransNo() );
			info = PrintVoucher.getOBInfoByTransNo( info );
			
			//����¼����
			usedFields.put("OBInputUserName", info.getOBInputUserName());
			
			//����������
			usedFields.put("OBCheckUserName", info.getOBCheckUserName());
			
			//����ǩ����
			usedFields.put("OBSignUserName", info.getOBSignUserName());
        }
		
		//��ʾ����ת�˽跽��Ʊ
		//�����ջ�
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.DISCOUNTRECEIVE)
		{
			if (printOperationInfo.getAmount() != 0.0 || printOperationInfo.getOverDueAmount() != 0.0)
			{
				//������Сд
				usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() + printOperationInfo.getOverDueAmount() ));
				
				//�������д
				usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount() + printOperationInfo.getOverDueAmount(), printOperationInfo.getCurrencyID() ));
			}
			else
			{
				//������Сд
				usedFields.put("Amount", "0.00");
				
				String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
				
				//�������д
				usedFields.put("ChineseAmount", strChineseAmount);
			}
		}
		
		//����
		usedFields.put("Rate", DataFormat.formatRate( printOperationInfo.getRate() ));
		
		//��������
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
		{
			//�´浥����
		    usedFields.put("NewRate", DataFormat.formatRate( printOperationInfo.getNewRate(), 6 ));
		    
		    //�´浥��ʼ����
		    usedFields.put("NewStartDate", DataFormat.getChineseDateString( printOperationInfo.getNewStartDate() ));
		    
		    //�´浥��������
		    usedFields.put("NewEndDate", DataFormat.getChineseDateString( printOperationInfo.getNewEndDate() ));
		}
		
		//��������
		if(printOperationInfo.getFixedDepositTerm()<0)
		{
			usedFields.put("FixedDepositTerm", "");
		}
		else
		{
			usedFields.put("FixedDepositTerm", String.valueOf( printOperationInfo.getFixedDepositTerm() ));
		}
		
		//�������ڴ浥��
		if(printOperationInfo.getDepositBillNO() != null && !printOperationInfo.getDepositBillNO().equals(""))
		{
			usedFields.put("DepositBillNO", printOperationInfo.getDepositBillNO());
		}
		
		//ҵ������
		if(printOperationInfo.getTransTypeID() != -1)
		{
			usedFields.put("TransactionTypeName", SETTConstant.TransactionType.getName( printOperationInfo.getTransTypeID() ));
		}
		
		//����֪ͨ�浥��(֪ͨ����֤ʵ��浥��)
		if(printOperationInfo.getFixedDepositNo() != null && !printOperationInfo.getFixedDepositNo().equals(""))
		{
			usedFields.put("FixedDepositNo", printOperationInfo.getFixedDepositNo());
		}
		
		//"�������ڴ浥"��Ҫ�ɴ浥��
		if(printOperationInfo.getFixedDepositNo() != null && !printOperationInfo.getFixedDepositNo().equals(""))
		{
			usedFields.put("OldFixedDepositNo", printOperationInfo.getFixedDepositNo());
		}
		
		//�������ڴ浥��(��������¿��Ĵ浥��,����ʾ�¿��Ĵ浥��)
		if(printOperationInfo.getDepositBillNO() != null && !printOperationInfo.getDepositBillNO().equals(""))
		{
			usedFields.put("FixedDepositNo", printOperationInfo.getDepositBillNO());
		}
		
		//֪ͨ����
		String sInsert = "";
		if (printOperationInfo.getNoticeDay() > 10000)
		{
			sInsert = printOperationInfo.getNoticeDay() - 10000 + "��";
		}
		else
		{
			sInsert = printOperationInfo.getNoticeDay() + "����";
		}
		usedFields.put("NoticeType", sInsert);
		
		//����Ʒ��
		if(printOperationInfo.getFixedDepositTerm() > 0)
		{
			usedFields.put("FixedDepositTerm", String.valueOf( printOperationInfo.getFixedDepositTerm() ).trim() + "����" );
		}
		
		//����֪ͨ��Ϣ֧��
		if(printOperationInfo.getPayableInterest() != 0.0)
		{
			usedFields.put("PayableInterest", String.valueOf( new java.math.BigDecimal( printOperationInfo.getPayableInterest() ) ));
		}
		
		//����֪ͨ��Ϣ�ϼ�
		//��Ϣ֧�� + ������Ϣ + ������Ϣ + ������
		usedFields.put("TotalInterest", DataFormat.formatListAmount(printOperationInfo.getPayableInterest() +printOperationInfo.getCurrentInterest() + printOperationInfo.getOtherInterest() + printOperationInfo.getAmount(), 2));
		
		/************** ��Ӫ������ջ�ƾ֤��ӡ ******************/
		/************** ί�д�����ջ�ƾ֤��ӡ ******************/
		
		//�ſ�֪ͨ����
		usedFields.put("PayFormNo", NameRef.getPayFormNoByID( printOperationInfo.getLoanNoteID() ));
		
		//��ͬ��
		usedFields.put("ContractNo", NameRef.getContractNoByID( printOperationInfo.getContractID() ));
		
		//��Ӫ�����ջ�  ί�д����ջ�
		if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE 
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANGRANT
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANGRANT
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANGRANT
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
		{
			//ʵ��֧��������Ϣ
			if(printOperationInfo.getRealInterest() == 0.0)
			{
				usedFields.put("LoanRealInterest", "0.00");
			}
			else
			{
				usedFields.put("LoanRealInterest", DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ));
			}
			
			//ʵ��֧�������
			if(printOperationInfo.getRealCompoundInterest() == 0.0)
			{
				usedFields.put("LoanRealCompoundInterest", "0.00");
			}
			else
			{
				usedFields.put("LoanRealCompoundInterest", DataFormat.formatListAmount( printOperationInfo.getRealCompoundInterest(), 2 ));
			}
			
			//ʵ��֧���������ڷ�Ϣ
			if(printOperationInfo.getRealOverDueInterest() == 0.0)
			{
				usedFields.put("LoanRealOverDueInterest", "0.00");
			}
			else
			{
				usedFields.put("LoanRealOverDueInterest", DataFormat.formatListAmount( printOperationInfo.getRealOverDueInterest(), 2 ));
			}
			
			//�ſ�֪ͨ��
			if(printOperationInfo.getLoanNoteID() > 0)
			{
				LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
				lpfdinfo.setLoadNoteID(printOperationInfo.getLoanNoteID());
				Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
				lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
				
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST
				||  printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST
				||  printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_INTEREST)
				{
					usedFields.put("ContractNo", DataFormat.formatString( lpfdinfo.getContractCode() ) );
					usedFields.put("RepaymentUnitName", NameRef.getClientNameByID( lpfdinfo.getClientId() ) );
				}
				
				//��������(ί�д����ƾ֤��ӡ)
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANGRANT)
				{
					usedFields.put("ChargeRate", DataFormat.formatRate( lpfdinfo.getPoundage(), 6 ));
				}
				
				//��ͬ����
				usedFields.put("ContractRate", DataFormat.formatRate( lpfdinfo.getInterestRate(), 6 ));
				
				//ί�е�λ
				usedFields.put("ConsignUnit", lpfdinfo.getClientName());
				
				//��Ϣ����֧��ί�д����ί�е�λ modify by xwhe 2008-07-15
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
				{
				usedFields.put("ConsignClientName", NameRef.getClientNameByID(printOperationInfo.getConsignClientID()));
				}
				
				//��������
				usedFields.put("LoanTypeName", LOANConstant.LoanType.getName( lpfdinfo.getLoanType() ));
				
				//����������
				if(lpfdinfo.getSubTypeID() > 0)
				{
					usedFields.put("SubLoanTypeName", LOANConstant.SubLoanType.getName( lpfdinfo.getSubTypeID() ));
				}
				
				//��������
				usedFields.put("LoanInterval", DataFormat.getChineseDateString( lpfdinfo.getStart() ) + " �� " + DataFormat.getChineseDateString( lpfdinfo.getEnd() ));
				
				//�ſ���� 2008-09-08 add by xwhe 
				if(lpfdinfo.getDTSTART()!=null && lpfdinfo.getDTEND()!=null)
				{
                   //�ſ����
					usedFields.put("LoanNoteInterval", DataFormat.getChineseDateString( lpfdinfo.getDTSTART() ) + " �� " + DataFormat.getChineseDateString( lpfdinfo.getDTEND() ));	
				}
				//������
				usedFields.put("OperationEndDate", DataFormat.getChineseDateString( lpfdinfo.getEnd() ));
				//modify by xwhe 2008-07-21 ί���ջ�ȡֵ ��������Ҫ��
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
                    if(printOperationInfo.getConsignAccountID()>0)
                    {
				    //������ȫ��
				    usedFields.put("ConsignAccountName", NameRef.getAccountNameByID( printOperationInfo.getConsignAccountID()));
				    //����˺�
					usedFields.put("ConsignAccountNo", NameRef.getAccountNoByID( printOperationInfo.getConsignAccountID() ));
                    }
                    if(printOperationInfo.getConsignDepositAccountID()>0)
                    {
                    //�տȫ��
    				usedFields.put("ConsignDepositAccountName", NameRef.getAccountNameByID( printOperationInfo.getConsignDepositAccountID()));
                    //�տ�˺�
    				usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID( printOperationInfo.getConsignDepositAccountID()));
                    }
				}
                //��Ϣ����֧��ί�д�������˻� modify by xwhe 2008-07-28
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
				{
				usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));
				}
				//������Ϣ
				if (printOperationInfo.getRealInterest() > 0)
				{
					//������Ϣ��ʼ����					
					usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getLatestInterestClearDate() ) );
					
					//������Ϣ��������
					usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getInterestClearDate() ) );
					
					//������Ϣ����
					usedFields.put("NormalInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getAmount(), 2 ) );
					
					//��������
					usedFields.put("NormalInterestRate", DataFormat.formatRate(lpfdinfo.getInterestRate(), 6));
					
					//������Ϣ
					usedFields.put("NormalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ) );
					
					//������Ϣ����	
					usedFields.put("NormalInterestDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(printOperationInfo.getLatestInterestClearDate(), printOperationInfo.getInterestClearDate(), 1)) + "") );
				}
			
				//����
				//��ʼ����--�ſ����ں��һ�������������ڣ����ʵ���������Ϣ������
				if (printOperationInfo.getRealCompoundInterest() > 0)
				{
					//������Ϣ��Ϣ����
					if (printOperationInfo.getCompoundInterestStart() != null)
					{
						usedFields.put("CompoundInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getCompoundInterestStart() ) );
					}
					
					//������Ϣ��Ϣ����
					if (printOperationInfo.getCompoundInterestEnd() != null)
					{
						usedFields.put("CompoundInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getCompoundInterestEnd() ) );
					}
					else
					{
						usedFields.put("CompoundInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ) );
					}
					
					//������Ϣ����
					if (printOperationInfo.getCompoundInterestStart() != null && printOperationInfo.getCompoundInterestEnd() != null)
					{
						usedFields.put("CompoundInterestDay", DataFormat.formatString( ( PrintVoucher.getIntervalDays( printOperationInfo.getCompoundInterestStart(), printOperationInfo.getCompoundInterestEnd(), 1)) + "" ));
					}
					else if (printOperationInfo.getCompoundInterestStart() != null)
					{
						usedFields.put("CompoundInterestDay", DataFormat.formatString( ( PrintVoucher.getIntervalDays( printOperationInfo.getCompoundInterestStart(), printOperationInfo.getExecuteDate(), 1)) + "" ));
					}
					
					//����������ʾΪ��(�޸� 2007��11��12�� ȥ��SETT_TRANSREPAYMENTLOAN��MCOMPOUNDAMOUNT��Ϣ����֧��)
					usedFields.put("CompoundInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(printOperationInfo.getCompoundAmount(), 2) );
					
					//��������
					usedFields.put("CompoundInterestRate", DataFormat.formatRate( printOperationInfo.getCompoundRate(), 6 ));
					
					//������Ϣ	 modify by xwhe 2008-08-04
					usedFields.put("CompoundInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealCompoundInterest(), 2 ));	
				}
				
				//���ڷ�Ϣ
				//��ʼ���ڷ�Ϣ֪ͨ���ķ�Ϣ����/��һ�ν�Ϣ�����ڣ����ݲ�ͬ�����������ȡ��Ϣ֪ͨ���ķ�Ϣ����
				if (printOperationInfo.getRealOverDueInterest() > 0)
				{
					//���ڷ�Ϣ��Ϣ��Ϣ����
					if (printOperationInfo.getOverDueStart() != null)
					{
						usedFields.put("OverInterestDateStart", DataFormat.getChineseDateString(printOperationInfo.getOverDueStart()));
					}
					
					//���ڷ�Ϣ��Ϣ��Ϣ����
					if (printOperationInfo.getOverDueEnd() != null)
					{
						usedFields.put("OverInterestDateEnd", DataFormat.getChineseDateString(printOperationInfo.getOverDueEnd()));
					}
					else
					{
						usedFields.put("OverInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ) );
					}
					
					//���ڷ�Ϣ��Ϣ����
					if (printOperationInfo.getOverDueStart() != null && printOperationInfo.getOverDueEnd() != null)
					{
						usedFields.put("OverInterestDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(printOperationInfo.getOverDueStart(), printOperationInfo.getOverDueEnd(), 1)) + "" ));
					}
					else if (printOperationInfo.getOverDueStart() != null)
					{
						usedFields.put("OverInterestDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(printOperationInfo.getOverDueStart(), printOperationInfo.getExecuteDate(), 1)) + "" ));
					}
					
					//���ڷ�Ϣ����
					usedFields.put("OverInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount(printOperationInfo.getOverDueAmount(), 2) );
					if (printOperationInfo.getOverDueAmount() == 0.00)
					{
						usedFields.put("OverInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ "0.00");
					}
					
					//���ڷ�Ϣ����
					usedFields.put("OverInterestRate", DataFormat.formatRate( printOperationInfo.getOverDueRate(), 6 ));
					
					//���ڷ�Ϣ��Ϣ modify by xwhe 2008-08-04
					usedFields.put("OverInterest",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount(printOperationInfo.getRealOverDueInterest(), 2) );
				}
				
				//������
				//��ʼ����ȡ�ſ�֪ͨ������ʼ����/��һ�ν��㵫���ѵ����ڣ����ʴӷſ�֪ͨ��ȡ
				if (printOperationInfo.getRealSuretyFee() > 0)
				{
					//��������Ϣ��Ϣ����
					if(printOperationInfo.getSuretyFeeStart() != null)
					{
						usedFields.put("AssureFeeDateStart", DataFormat.getChineseDateString(printOperationInfo.getSuretyFeeStart()) );
					}
					
					//��������Ϣ��Ϣ����
					if(printOperationInfo.getSuretyFeeEnd() != null)
					{
						usedFields.put("AssureFeeDateEnd", DataFormat.getChineseDateString(printOperationInfo.getSuretyFeeEnd()) );
					}
					
					//��������Ϣ����
					usedFields.put("AssureFeeDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(printOperationInfo.getSuretyFeeStart(), printOperationInfo.getSuretyFeeEnd(), 1)) + "" ));
					
					//��������Ϣ���� modify by xwhe 2008-08-04
					usedFields.put("AssureFeeAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(printOperationInfo.getSuretyFeeAmount(), 2) );
					if (printOperationInfo.getSuretyFeeAmount() == 0.0)
					{
						usedFields.put("AssureFeeAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+"0.00" );
					}
					
					//����������
					usedFields.put("AssureFeeRate", DataFormat.formatRate( printOperationInfo.getSuretyFeeRate(), 6 ));
					
					//��������Ϣ modify by xwhe 2008-08-04
					usedFields.put("AssureFee", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(printOperationInfo.getRealSuretyFee(), 2) );
				}
				
				//������
				if (printOperationInfo.getRealCommission() > 0)
				{
					//��������Ϣ��Ϣ����
					if(lpfdinfo.getStart() != null)
					{
						usedFields.put("CommissionFeeDateStart", DataFormat.getChineseDateString( lpfdinfo.getStart() ) );
					}
					
					//��������Ϣ��Ϣ����
					if(lpfdinfo.getEnd() != null)
					{
						usedFields.put("CommissionFeeDateEnd", DataFormat.getChineseDateString( lpfdinfo.getEnd() ) );
					}
					
					//��������Ϣ����
					usedFields.put("CommissionFeeDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(lpfdinfo.getStart(), lpfdinfo.getEnd(), 1)) + "") );
					
					//��������Ϣ���� modify by xwhe 2008-08-04
					usedFields.put("CommissionFeeAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( lpfdinfo.getAmount(), 2 ) );
			 
                    //����������
					usedFields.put("CommissionFeeRate", DataFormat.formatRate( lpfdinfo.getCommissionRate(), 6 ));
					//modify by xwhe 2008-08-04
					//��������Ϣ
					if(printOperationInfo.getRealCommission()>0.0)
					{
					usedFields.put("CommissionFee", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID() )+DataFormat.formatListAmount(printOperationInfo.getRealCommission(), 2) );
					
                    //��������Ϣ��д add by xwhe 2008-07-28 modify by xwhe 2008-08-04
					usedFields.put("chineseCommissionFee",this.getChineseFormatAmount( printOperationInfo.getRealCommission(), printOperationInfo.getCurrencyID()));
					}
					usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
				}
				
				//��Ϣ˰��(2007-10-18 ����Ҫ����ί�д�����Ϣ������ʾ"��Ϣ˰��")
				if (printOperationInfo.getRealInterestTax() > 0)
				{
					usedFields.put("InterestTax", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount(printOperationInfo.getRealInterestTax(), 2) );
				}
				
				//��Ӫ�����ջ�
				//�ۼƻ���
				if ((lpfdinfo.getAmount() - printOperationInfo.getCurrentBalance()) > 0)
				{
					usedFields.put("TotalRepaymentAmount", DataFormat.formatListAmount( lpfdinfo.getAmount() - printOperationInfo.getCurrentBalance(), 2 ) );
				}
				else
				{
					//������Сд
					usedFields.put("Amount", "0.00");
					
					String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
					
					//�������д
					usedFields.put("ChineseAmount", strChineseAmount);
				}
				
				//�⻹��Ϣ modify by xwhe 2008-08-04
				double remitInterest = DataFormat.formatDouble(printOperationInfo.getInterest() - printOperationInfo.getRealInterest());
				if (remitInterest > 0)
				{
					usedFields.put("RemitInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( remitInterest ,2 ) );
				}
				//ʵ�ʻ�ת���  add by xwhe 2008-07-15 modify by xwhe 2008-08-04
				double transferAmount = DataFormat.formatDouble(printOperationInfo.getRealInterest()-printOperationInfo.getRealInterestTax());
				if (transferAmount > 0)
				{
					usedFields.put("TransferAmount",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount( transferAmount ,2 ) );
				}
				
				//modify by xwhe 2008-08-04
				String NoticeOpenTotalInterest = DataFormat.formatListAmount(
					   DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
					   DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
					   DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()), 2);
				
				if (NoticeOpenTotalInterest != null && !NoticeOpenTotalInterest.equals(""))
				{
					//��Ϣ�ϼ�Сд
					usedFields.put("NoticeOpenTotalInterest",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+
														DataFormat.formatListAmount(
														DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
														DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
														DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()), 2 ) );
				
					//��Ϣ�ϼƴ�д
					usedFields.put("chineseOpenTotalInterest",
														ChineseCurrency.showChinese(
														DataFormat.formatAmount(
														DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
														DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
														DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest())),
														printOperationInfo.getCurrencyID() ) );
				}
			  
				
//				add by minzhao 20090410
				if (NoticeOpenTotalInterest != null && !NoticeOpenTotalInterest.equals(""))
				{
					usedFields.put("InterestTaxofbusiness",
							DataFormat.formatListAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05, 2 ) );
				}
				
				if (NoticeOpenTotalInterest != null && !NoticeOpenTotalInterest.equals(""))
				{
					usedFields.put("InterestTaxofconstruction",
							DataFormat.formatListAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.07, 2 ) );
				}
				
				if (NoticeOpenTotalInterest != null && !NoticeOpenTotalInterest.equals(""))
				{
					usedFields.put("InterestTaxofeducation",
							DataFormat.formatListAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.03, 2 ) );
				}
				
				//add ny minzhao end
				
//				add by minzhao 20090427
				if (NoticeOpenTotalInterest != null && !NoticeOpenTotalInterest.equals(""))
				{
					usedFields.put("chineseInterestTaxofbusiness",
							ChineseCurrency.showChinese(DataFormat.formatAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05, 2 ) ));
				}
				
				if (NoticeOpenTotalInterest != null && !NoticeOpenTotalInterest.equals(""))
				{
					usedFields.put("chineseInterestTaxofconstruction",
							ChineseCurrency.showChinese(DataFormat.formatAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.07, 2 ) ));
				}
				
				if (NoticeOpenTotalInterest != null && !NoticeOpenTotalInterest.equals(""))
				{
					usedFields.put("chineseInterestTaxofeducation",
							ChineseCurrency.showChinese(DataFormat.formatAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.03, 2 ) ));
				}
				
				//add ny minzhao end
				//add by xwhe 2008-09-03
				if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
				 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					//����ֶμ�Ϣ add by xwhe 2008-08-22 ��ʱ������
					if(printOperationInfo.getInterest1()>0)
					{
						if(printOperationInfo.getRate1()!=0)
						{
						//	String LoanAmass1 = DataFormat.formatAmount(
						//		UtilOperation.Arith.div(
						//			UtilOperation.Arith.mul(printOperationInfo.getInterest1(), 360)
						//		  , UtilOperation.Arith.div(printOperationInfo.getRate1(), 100)
						//		)
						//	);
						//modify by xwhe 2008-12-18
						usedFields.put("LoanAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount1(), 2 ));  //�������1						  
						}
						
						String Interest11 = DataFormat.formatAmount(printOperationInfo.getInterest1());			
						usedFields.put("Interest11", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest11), 2 ));  //��Ϣ1					
					}
					if(printOperationInfo.getInterest2()>0)
					{
						if(printOperationInfo.getRate2()!=0)
						{
						 //	String LoanAmass2 = DataFormat.formatAmount(
						 //		UtilOperation.Arith.div(
						 //			UtilOperation.Arith.mul(printOperationInfo.getInterest2(), 360)
						 //		  , UtilOperation.Arith.div(printOperationInfo.getRate2(), 100)
						 //		)
						 //	);
						usedFields.put("LoanAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount2(), 2 ));  //�������2
						 
						}						
						String Interest12 = DataFormat.formatAmount(printOperationInfo.getInterest2());
				
						usedFields.put("Interest12", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest12), 2 ));  //��Ϣ2					
					}
					if(printOperationInfo.getInterest3()>0)
					{
						if(printOperationInfo.getRate3()!=0)
						{
							String LoanAmass3 = DataFormat.formatAmount(
								UtilOperation.Arith.div(
									UtilOperation.Arith.mul(printOperationInfo.getInterest3(), 360)
								  , UtilOperation.Arith.div(printOperationInfo.getRate3(), 100)
								)
							);
						usedFields.put("LoanAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount3(), 2 )); //�������3
							
						}
						
						String Interest13 = DataFormat.formatAmount(printOperationInfo.getInterest3());
						
						usedFields.put("Interest13", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest13), 2 ));  //��Ϣ3					
					}
					if(printOperationInfo.getStartDate1()!=null)
					{
						String BeginDate11 = "";  //��Ϣ����1
						BeginDate11 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
						usedFields.put("InterestDateStart11", BeginDate11);  //��Ϣ����1
					}
					if(printOperationInfo.getStartDate2()!=null)
					{
						String BeginDate12 = "";  //��Ϣ����1
						BeginDate12 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
						usedFields.put("InterestDateStart12", BeginDate12);  //��Ϣ����2
					}
					if(printOperationInfo.getStartDate3()!=null)
					{
						String BeginDate13= "";  //��Ϣ����3
						BeginDate13 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
						usedFields.put("InterestDateStart13", BeginDate13);  //��Ϣ����3
					}
					if(printOperationInfo.getEndDate1()!=null)
					{
						String EndDate11= "";  //��Ϣ����1
						EndDate11 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
						usedFields.put("InterestDateEnd11", EndDate11);  //��Ϣ����1
					}
					if(printOperationInfo.getEndDate2()!=null)
					{
						String EndDate12= "";  //��Ϣ����2
						EndDate12 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
						usedFields.put("InterestDateEnd12", EndDate12);  //��Ϣ����2
					}
					if(printOperationInfo.getEndDate3()!=null)
					{
						String EndDate13= "";  //��Ϣ����2
						EndDate13 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
						usedFields.put("InterestDateEnd13", EndDate13);  //��Ϣ����3
					}
					if(printOperationInfo.getRate1() != 0)
					{
						 String Rate11 = ""; //����1
						 Rate11 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
						 usedFields.put("Rate11", Rate11);  //����1
					}
					if(printOperationInfo.getRate2() != 0)
					{
						 String Rate12 = ""; //����2
						 Rate12 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
						 usedFields.put("Rate12", Rate12);  //����2
					}
					if(printOperationInfo.getRate3() != 0)
					{
						 String Rate13 = ""; //����3
						 Rate13 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
						 usedFields.put("Rate13", Rate13);  //����3
					}
					//add by xwhe 2008-09-03
					if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
					 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3(), printOperationInfo.getCurrencyID() ));		
						
			            //��Ϣ�ϼ�Сд 
						usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3() ));
					   
					}
				}
			}
		}
		
		/******  ��Ϣ����֧��  ��ʼ  ******/
		if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT )
		{
			//������Ϣ
			if (printOperationInfo.getRealInterest() > 0)
			{
				//��ͬ���
				usedFields.put("ContractNo", DataFormat.formatString( NameRef.getContractcodeBySubAccountID(printOperationInfo.getSubAccountID()) ) );
				
				//���λ����
				usedFields.put("RepaymentUnitName", NameRef.getClientNameByAccountID(printOperationInfo.getPayAccountID()) );
				
				//������Ϣ��ʼ����
				usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ) );
				
				//������Ϣ��������
				usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ) );
				
				//������Ϣ���� modify by xwhe 2008-08-04
				usedFields.put("NormalInterestAmount",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getAmount(), 2 ) );
				
				//��������
				usedFields.put("NormalInterestRate", DataFormat.formatRate( printOperationInfo.getRate(), 6 ) );
				
				//������Ϣ modify by xwhe 2008-08-04
				usedFields.put("NormalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ) );
				
				//������Ϣ����
				usedFields.put("NormalInterestDay", DataFormat.formatString( printOperationInfo.getNoticeDay()+"" ) );
			    
                //��Ϣ�ϼƴ�д modify by xwhe 2008-07-15 modify by xwhe 2008-08-04
				usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getRealInterest(), printOperationInfo.getCurrencyID() ));		
				
	            //��Ϣ�ϼ�Сд 
				usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getRealInterest() ));
			   
                //ʵ�ʻ�ת���  add by xwhe 2008-07-15
				double transferAmount = DataFormat.formatDouble(printOperationInfo.getRealInterest()-printOperationInfo.getRealInterestTax());
			    //ί�з������˻�	2008-07-31
				if(printOperationInfo.getReceiveInterestAccountID()>0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST)
				{
					usedFields.put("ConsignDepositAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getReceiveInterestAccountID()));
				}
                //��������˻�2008-07-31
				if(printOperationInfo.getPayInterestAccountID()>0 && (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST 
						||printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_INTEREST))
				{
					usedFields.put("PayAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getPayInterestAccountID()));
				}
				if (transferAmount >= 0.0)
				{
					usedFields.put("TransferAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( transferAmount ,2 ) );
				}
				
			    //����ֶμ�Ϣ add by xwhe 2008-08-22 ��ʱ������
				if(printOperationInfo.getInterest1()>0)
				{
					usedFields.put("LoanAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount1(), 2 ));  //�������1
					
					String Interest11 = DataFormat.formatAmount(printOperationInfo.getInterest1());
					usedFields.put("Interest11", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest11), 2 ));  //��Ϣ1								
				}
				
				if(printOperationInfo.getInterest2()>0)
				{
					usedFields.put("LoanAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount2(), 2 ));  //�������2					}
					
					String Interest12 = DataFormat.formatAmount(printOperationInfo.getInterest2());
					usedFields.put("Interest12", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest12), 2 ));  //��Ϣ2					
				}
				
				if(printOperationInfo.getInterest3()>0)
				{
					usedFields.put("LoanAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount3(), 2 ));  //�������3
					
					String Interest13 = DataFormat.formatAmount(printOperationInfo.getInterest3());
					usedFields.put("Interest13", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest13), 2 ));  //��Ϣ3					
				}
				
				if(printOperationInfo.getStartDate1()!=null)
				{
					String BeginDate11 = "";  //��Ϣ����1
					BeginDate11 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
					usedFields.put("InterestDateStart11", BeginDate11);  //��Ϣ����1
				}
				if(printOperationInfo.getStartDate2()!=null)
				{
					String BeginDate12 = "";  //��Ϣ����1
					BeginDate12 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
					usedFields.put("InterestDateStart12", BeginDate12);  //��Ϣ����2
				}
				if(printOperationInfo.getStartDate3()!=null)
				{
					String BeginDate13= "";  //��Ϣ����3
					BeginDate13 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
					usedFields.put("InterestDateStart13", BeginDate13);  //��Ϣ����3
				}
				if(printOperationInfo.getEndDate1()!=null)
				{
					String EndDate11= "";  //��Ϣ����1
					EndDate11 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
					usedFields.put("InterestDateEnd11", EndDate11);  //��Ϣ����1
				}
				if(printOperationInfo.getEndDate2()!=null)
				{
					String EndDate12= "";  //��Ϣ����2
					EndDate12 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
					usedFields.put("InterestDateEnd12", EndDate12);  //��Ϣ����2
				}
				if(printOperationInfo.getEndDate3()!=null)
				{
					String EndDate13= "";  //��Ϣ����2
					EndDate13 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
					usedFields.put("InterestDateEnd13", EndDate13);  //��Ϣ����3
				}
				if(printOperationInfo.getRate1() != 0)
				{
					String Rate11 = ""; //����1
					Rate11 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
					usedFields.put("Rate11", Rate11);  //����1
				}
				if(printOperationInfo.getRate2() != 0)
				{
					String Rate12 = ""; //����2
					Rate12 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
					usedFields.put("Rate12", Rate12);  //����2
				}
				if(printOperationInfo.getRate3() != 0)
				{
					String Rate13 = ""; //����3
					Rate13 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
					usedFields.put("Rate13", Rate13);  //����3
				}
				
				//add by xwhe 2008-09-03
				if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
				 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3(), printOperationInfo.getCurrencyID() ));		
					
		            //��Ϣ�ϼ�Сд 
					usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3() ));
				}
			}
		}
		/******  ��Ϣ����֧��  ����  ******/		
		/******  ��Ӫ�����Ϣ  ί�д����Ϣ  ��ʼ  ******/
		if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST 
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_INTEREST)
		{
			//������Ϣ
			if (printOperationInfo.getRealInterest() > 0)
			{
				//��ͬ���
				usedFields.put("ContractNo", DataFormat.formatString( NameRef.getContractcodeBySubAccountID(printOperationInfo.getSubAccountID()) ) );
				
				//���λ����
				usedFields.put("RepaymentUnitName", NameRef.getClientNameByAccountID(printOperationInfo.getPayAccountID()) );
				
				//������Ϣ��ʼ����
				usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ) );
				
				//������Ϣ��������
				usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ) );
				
				//������Ϣ���� modify by xwhe 2008-08-04
				usedFields.put("NormalInterestAmount",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getAmount(), 2 ) );
				
				//��������
				usedFields.put("NormalInterestRate", DataFormat.formatRate( printOperationInfo.getRate(), 6 ) );
				
				//������Ϣ modify by xwhe 2008-08-04
				usedFields.put("NormalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ) );
				
				//������Ϣ����
				usedFields.put("NormalInterestDay", DataFormat.formatString( printOperationInfo.getNoticeDay()+"" ) );
			    
                //��Ϣ�ϼƴ�д modify by xwhe 2008-07-15 modify by xwhe 2008-08-04
				usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getRealInterest(), printOperationInfo.getCurrencyID() ));		
				
	            //��Ϣ�ϼ�Сд 
				usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getRealInterest() ));
			   
				
//				add by minzhao 20090410
				if (printOperationInfo.getRealInterest() > 0)
				{
					usedFields.put("InterestTaxofbusiness",
							DataFormat.formatListAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05, 2 ) );
				}
				
				if (printOperationInfo.getRealInterest() > 0)
				{
					usedFields.put("InterestTaxofconstruction",
							DataFormat.formatListAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.07, 2 ) );
				}
				
				if (printOperationInfo.getRealInterest() > 0)
				{
					usedFields.put("InterestTaxofeducation",
							DataFormat.formatListAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.03, 2 ) );
				}
				
				//add ny minzhao end
				
				
//				add by minzhao 20090427
				if (printOperationInfo.getRealInterest() > 0)
				{
					usedFields.put("chineseInterestTaxofbusiness",
							ChineseCurrency.showChinese(DataFormat.formatAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05, 2 ) ));
				}
				
				System.out.println(ChineseCurrency.showChinese(DataFormat.formatAmount(
						(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
								DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
								DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05, 2 ) ));
				
				if (printOperationInfo.getRealInterest() > 0)
				{
					usedFields.put("chineseInterestTaxofconstruction",
							ChineseCurrency.showChinese(DataFormat.formatAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.07, 2 ) ));
				}
				
				if (printOperationInfo.getRealInterest() > 0)
				{
					usedFields.put("chineseInterestTaxofeducation",
							ChineseCurrency.showChinese(DataFormat.formatAmount(
							(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
							DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.03, 2 ) ));
				}
				
				//add ny minzhao end
				
                //ʵ�ʻ�ת���  add by xwhe 2008-07-15
				double transferAmount = DataFormat.formatDouble(printOperationInfo.getRealInterest()-printOperationInfo.getRealInterestTax());
			    //ί�з������˻�	2008-07-31
				if(printOperationInfo.getReceiveInterestAccountID()>0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST)
				{
					usedFields.put("ConsignDepositAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getReceiveInterestAccountID()));
				}
                //��������˻�2008-07-31
				if(printOperationInfo.getPayInterestAccountID()>0 && (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST 
						||printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST
						||printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_INTEREST))
				{
					usedFields.put("PayAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getPayInterestAccountID()));
				}
				if (transferAmount >= 0.0)
				{
					usedFields.put("TransferAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( transferAmount ,2 ) );
				}
				
			    //����ֶμ�Ϣ add by xwhe 2008-08-22 ��ʱ������
				if(printOperationInfo.getInterest1()>0)
				{
					if(printOperationInfo.getRate1()!=0)
					{
					//	String LoanAmass1 = DataFormat.formatAmount(
					//		UtilOperation.Arith.div(
					//			UtilOperation.Arith.mul(printOperationInfo.getInterest1(), 360)
					//			, UtilOperation.Arith.div(printOperationInfo.getRate1(), 100)
					//		)
					//	);
					//modify by xwhe 2008-12-18
					usedFields.put("LoanAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount1(), 2 )); //�������1					
					}					
					String Interest11 = DataFormat.formatAmount(printOperationInfo.getInterest1());
					usedFields.put("Interest11", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest11), 2 ));  //��Ϣ1								
				}
				
				if(printOperationInfo.getInterest2()>0)
				{
					if(printOperationInfo.getRate2()!=0)
					{
					 //	String LoanAmass2 = DataFormat.formatAmount(
					 //		UtilOperation.Arith.div(
					 //			UtilOperation.Arith.mul(printOperationInfo.getInterest2(), 360)
					 //		  , UtilOperation.Arith.div(printOperationInfo.getRate2(), 100)
					 //		)
					 //	);
					 //modify by xwhe 2008-12-18
					usedFields.put("LoanAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount2(), 2 )); //�������2
				  
					}
					
					String Interest12 = DataFormat.formatAmount(printOperationInfo.getInterest2());
					usedFields.put("Interest12", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest12), 2 ));  //��Ϣ2					
				}
				
				if(printOperationInfo.getInterest3()>0)
				{
					if(printOperationInfo.getRate3()!=0)
					{
					 //	String LoanAmass3 = DataFormat.formatAmount(
					 //		UtilOperation.Arith.div(
					 // 			UtilOperation.Arith.mul(printOperationInfo.getInterest3(), 360)
					 //		  , UtilOperation.Arith.div(printOperationInfo.getRate3(), 100)
					 //		)
					 //	);
                     //modify by xwhe 2008-12-18
						usedFields.put("LoanAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount3(), 2 )); //�������3
					
					}
					
					String Interest13 = DataFormat.formatAmount(printOperationInfo.getInterest3());
					usedFields.put("Interest13", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest13), 2 ));  //��Ϣ3					
				}
				
				if(printOperationInfo.getStartDate1()!=null)
				{
					String BeginDate11 = "";  //��Ϣ����1
					BeginDate11 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
					usedFields.put("InterestDateStart11", BeginDate11);  //��Ϣ����1
				}
				if(printOperationInfo.getStartDate2()!=null)
				{
					String BeginDate12 = "";  //��Ϣ����1
					BeginDate12 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
					usedFields.put("InterestDateStart12", BeginDate12);  //��Ϣ����2
				}
				if(printOperationInfo.getStartDate3()!=null)
				{
					String BeginDate13= "";  //��Ϣ����3
					BeginDate13 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
					usedFields.put("InterestDateStart13", BeginDate13);  //��Ϣ����3
				}
				if(printOperationInfo.getEndDate1()!=null)
				{
					String EndDate11= "";  //��Ϣ����1
					EndDate11 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
					usedFields.put("InterestDateEnd11", EndDate11);  //��Ϣ����1
				}
				if(printOperationInfo.getEndDate2()!=null)
				{
					String EndDate12= "";  //��Ϣ����2
					EndDate12 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
					usedFields.put("InterestDateEnd12", EndDate12);  //��Ϣ����2
				}
				if(printOperationInfo.getEndDate3()!=null)
				{
					String EndDate13= "";  //��Ϣ����2
					EndDate13 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
					usedFields.put("InterestDateEnd13", EndDate13);  //��Ϣ����3
				}
				if(printOperationInfo.getRate1() != 0)
				{
					String Rate11 = ""; //����1
					Rate11 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
					usedFields.put("Rate11", Rate11);  //����1
				}
				if(printOperationInfo.getRate2() != 0)
				{
					String Rate12 = ""; //����2
					Rate12 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
					usedFields.put("Rate12", Rate12);  //����2
				}
				if(printOperationInfo.getRate3() != 0)
				{
					String Rate13 = ""; //����3
					Rate13 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
					usedFields.put("Rate13", Rate13);  //����3
				}
				
				//add by xwhe 2008-09-03
				if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
				 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3(), printOperationInfo.getCurrencyID() ));		
					
		            //��Ϣ�ϼ�Сд 
					usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3() ));
				}
			}
		}
		/******  ��Ӫ�����Ϣ  ί�д����Ϣ ����  ******/
		
		/******  ί�д������Ӧ����Ϣ���������� ��Ӫ�������Ӧ����Ϣ���������� ��ʼ  ******/
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST 
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST)
		{
			//��ͬ���
			usedFields.put("ContractNo", DataFormat.formatString( NameRef.getContractcodeBySubAccountID(printOperationInfo.getSubAccountID()) ) );
			
			//���λ����
			usedFields.put("RepaymentUnitName", NameRef.getClientNameByAccountID(printOperationInfo.getPayAccountID()) );
			
			//������Ϣ��ʼ����
			usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ) );
			
			//������Ϣ��������
			usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ) );
			
			//������Ϣ���� modify by xwhe 2008-08-04
			if(printOperationInfo.getAmount()>0.0)
			{
			usedFields.put("NormalInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount( printOperationInfo.getAmount(), 2 ) );
			}
			//Э������ modify by xwhe 2008-09-19
			if(printOperationInfo.getRate()!=0)
			{
			String LoanAmass4 = DataFormat.formatAmount(
					UtilOperation.Arith.div(
						UtilOperation.Arith.mul(printOperationInfo.getRealInterest(), 360)
					  , UtilOperation.Arith.div(printOperationInfo.getRate(), 100)
					)
				);
			usedFields.put("LoanAmass4", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(LoanAmass4), 2 ));  //Э������
			}
			
			//��������
			usedFields.put("NormalInterestRate", DataFormat.formatRate( printOperationInfo.getRate(), 6 ) );
			
			//������Ϣ modify by xwhe 2008-08-04
			if(printOperationInfo.getRealInterest()>0.0)
			{
			usedFields.put("NormalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ) );
			}
			//��Ϣ�ϼƴ�д modify by xwhe 2008-07-15 modify by xwhe 2008-08-04
			if(printOperationInfo.getRealInterest()> 0.0){
			usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getRealInterest(), printOperationInfo.getCurrencyID() ));		
			
            //��Ϣ�ϼ�Сд 
			usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ this.getFormatAmount( printOperationInfo.getRealInterest() ));
			}
			//������Ϣ����			
			usedFields.put("NormalInterestDay", DataFormat.formatString( printOperationInfo.getNoticeDay()+"" ) );
		}
		/******  ί�д������Ӧ����Ϣ���������� ��Ӫ�������Ӧ����Ϣ���������� ����  ******/
		
		//�����˻���
		usedFields.put("LoanAccountNo", NameRef.getAccountNoByID( printOperationInfo.getLoanAccountID() ));
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
		{
           //�տ�˺�
			usedFields.put("LoanAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID()));
		}		
		//��λ(�տ�ͻ�)(��Ӫ�����˻�)
		//usedFields.put("LoanUnit", NameRef.getClientNameByID( printOperationInfo.getReceiveClientID() ));
		usedFields.put("LoanUnit", NameRef.getAccountNameByID( printOperationInfo.getLoanAccountID() ));
		
		//����ϼ���Ϣ
		//ʵ��֧��������Ϣ + ʵ��֧������ + ʵ��֧�����ڷ�Ϣ + ʵ��֧��������
		usedFields.put("LoanTotalInterest", DataFormat.formatListAmount(printOperationInfo.getRealInterest() + printOperationInfo.getRealCompoundInterest() + printOperationInfo.getRealOverDueInterest() + printOperationInfo.getRealSuretyFee(), 2 ));
	
		//���λ����
		if (printOperationInfo.getPayClientID() > 0)
		{
			usedFields.put("RepaymentUnitName", NameRef.getClientNameByID( printOperationInfo.getPayClientID() ));
		}
			
		//���ڴ�����Ϣ
		usedFields.put("RealOverDueInterest", DataFormat.formatListAmount( printOperationInfo.getRealOverDueInterest(), 2 ));
		
		//������Ϣ
		usedFields.put("LoanInterest", DataFormat.formatListAmount( printOperationInfo.getInterest(), 2 ) );
		
		//��������
		usedFields.put("LoanRate", DataFormat.formatRate( printOperationInfo.getRate(), 6 ) );
		
		/********** ���ַ���ƾ֤��ӡ **********/
		/********** �����ջ�ƾ֤��ӡ **********/
		
		Sett_TransGrantDiscountDAO discountDao = new Sett_TransGrantDiscountDAO();
		DiscountCredenceInfo dcinfo = new DiscountCredenceInfo();
		dcinfo = discountDao.findDiscountCredenceByID( printOperationInfo.getDiscountNoteID() );
		
		//���ֺ�ͬ��
		usedFields.put("DiscountContractCode", dcinfo.getDiscountContractCode());
		
		//����ƾ֤��
		usedFields.put("DiscountCode", dcinfo.getCode());
		
		//��Ʊ���Сд
		usedFields.put("DiscountBillAmount", DataFormat.formatListAmount( printOperationInfo.getDiscountBillAmount(), 2 ));
		
		//��Ʊ����д
		usedFields.put("ChineseDiscountBillAmount", ChineseCurrency.showChinese(DataFormat.formatAmount( printOperationInfo.getDiscountBillAmount() ), printOperationInfo.getCurrencyID()));
		
		//���ֳ�Ʊ��
		usedFields.put("PublicDate", DataFormat.getChineseDateString( dcinfo.getPublicDate() ));
		
		//���ֵ�����
		usedFields.put("AtTerm", DataFormat.getChineseDateString( dcinfo.getAtTerm() ));
		
		//������Ϣ
		usedFields.put("DiscountInterestAmount", DataFormat.formatAmount( printOperationInfo.getDiscountInterestAmount()) );
		
		//��������
		usedFields.put("DraftTypeName", LOANConstant.DraftType.getName( dcinfo.getDraftTypeID() ));
		
		//ʵ�����ֽ��Сд
		usedFields.put("DiscountAmount", DataFormat.formatListAmount( printOperationInfo.getDiscountAmount(), 2) );
		
		//ʵ�����ֽ���д
		usedFields.put("ChineseDiscountAmount", ChineseCurrency.showChinese(DataFormat.formatAmount( printOperationInfo.getDiscountAmount() ), printOperationInfo.getCurrencyID()) );
		
		//�������(��ʽ�����,Ϊ"0"ʱ��ʾ"0.00")
		usedFields.put("CurrentBalance", DataFormat.formatListAmount( printOperationInfo.getCurrentBalance(), 2 ) );
		
		//��������
		usedFields.put("DiscountRate", DataFormat.formatRate( dcinfo.getDiscountRate() , 6 ) );
		
		//��Ʊ����
		usedFields.put("BillNo", NameRef.getDiscountBillNoByID( printOperationInfo.getDiscountBillID() ) );
		
		//���е�����
		usedFields.put("DateBankAccount", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ) );
		
		//���ֵ�λ
		usedFields.put("ApplicantName", DataFormat.formatString( printOperationInfo.getApplicantName() ) );

		//���ֵ�λ�˺�
		usedFields.put("ApplicantAccountName", DataFormat.formatString( printOperationInfo.getApplicantAccountNo() ) );
		
		//���ֵ�λ��������
		usedFields.put("ApplicantBankName", DataFormat.formatString( printOperationInfo.getApplicantAccountBankNo() ) );
		
		if ( dcinfo != null )
		{
			//������
			usedFields.put("DateDiscount", DataFormat.getChineseDateString(DataFormat.getDateTime(NameRef.getDiscountDateByDiscountBillID(printOperationInfo.getDiscountBillID()))));
		
		}
		
		//�ۼƻ�����(�����ջ�ƾ֤��ӡ)
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.DISCOUNTRECEIVE)
		{
			if ((IPrintTemplate.getDiscountTotalRepaymentAmount(printOperationInfo.getDiscountNoteID()) - printOperationInfo.getCurrentBalance()) > 0)
			{
				usedFields.put("TotalRepaymentAmount", DataFormat.formatListAmount(IPrintTemplate.getDiscountTotalRepaymentAmount(printOperationInfo.getDiscountNoteID()) - printOperationInfo.getCurrentBalance(), 2) );
			}
			else
			{
				usedFields.put("TotalRepaymentAmount", "0.00" );
			}
		}
		
		/**********  �����Ϣ�Ƹ�֪ͨ��(����֪ͨ��� ���ڴ����ջ�ƾ֤)  **********/
		
		//�˻�������λ����
		usedFields.put("AccountClientName", NameRef.getClientNameByAccountID( printOperationInfo.getPayAccountID() ) );
		
		//ȡ�ϴμ������ڣ���Ϊ�������ڵĽ�������
		SubAccountFixedInfo subAccountFixedInfo = null;
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		Timestamp tsLastPreDraw = null;
		subAccountAssemblerInfo = sett_SubAccountDAO.findByID(printOperationInfo.getSubAccountID());
		
		if (subAccountAssemblerInfo != null)
		{
			subAccountFixedInfo = subAccountAssemblerInfo.getSubAccountFixedInfo();
		}
		if (subAccountFixedInfo != null)
		{
			tsLastPreDraw = subAccountFixedInfo.getPreDrawDate();
		}
		
		//������ݱ���
		String Amount1 = "";  //����1
		String Amount2 = "";  //����2
		String Amount3 = "";  //����3
		String Amount4 = "";  //����4
		String Interest1 = "";  //��Ϣ1
		String Interest2 = "";  //��Ϣ2
		String Interest3 = "";  //��Ϣ3
		String Interest4 = "";  //��Ϣ4
		String Interest5 = "";  //��Ϣ5
		String Rate1 = "";  //����1
		String Rate2 = "";  //����2
		String Rate3 = "";  //����3
		String Rate4 = "";  //����4
		String Rate5 = "";  //����5
		String DayNumber1 = "";  //����1
		String DayNumber2 = "";  //����2
		String DayNumber3 = "";  //����3
		String DayNumber4 = "";  //����4
		String BeginDate1 = "";  //��Ϣ����1
		String BeginDate2 = "";  //��Ϣ����2
		String BeginDate3 = "";  //��Ϣ����3
		String BeginDate4 = "";  //��Ϣ����4
		String BeginDate5 = "";  //��Ϣ����5
		String OverDate1 = "";  //��Ϣ����1
		String OverDate2 = "";  //��Ϣ����2
		String OverDate3 = "";  //��Ϣ����3
		String OverDate4 = "";  //��Ϣ����4
		String OverDate5 = "";  //��Ϣ����5
		
		//������Ҫ����2��
		String CurrentAmass = "";  //���ڻ���
		String AccordAmass = "";  //Э������
		String CurrentAmass1 = "";  //���ڻ���1
		String CurrentAmass2 = "";  //���ڻ���2
		String CurrentAmass3 = "";  //���ڻ���3
		String AccordAmass1 = "";   //Э������1
		String AccordAmass2 = "";   //Э������2
		
		String NegotiateStartDate1 ="";//Э����ʼ����1
		String NegotiateEndtDate1 ="";//Э����Ϣ����1
		String NegotiateStartDate2 ="";//Э����ʼ����2
		String NegotiateEndtDate2 ="";//Э����Ϣ����2
		String NegotiateRate1 = "";//Э������1
		String NegotiateRate2 = "";//Э������1
		String NegotiateInterest1 ="";//Э����Ϣ
		String NegotiateInterest2 ="";//Э����Ϣ
		//�Ƹ���Ϣ�ϼ�
		String totalInterest = "";
		
		/***** �л�����Ϣ *****/
		//��Ϣ����֧��-���ڽ�Ϣ, ���ڴ�����Ӧ����Ϣ����������, ֪ͨ������Ӧ����Ϣ����������, ���ڴ�����Ӧ����Ϣ����������
		if ( (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST)
		  && printOperationInfo.getPecisionInterestAmount() != 0.0 )
		{
			if ( printOperationInfo.getStartDate() != null 
			  && printOperationInfo.getEndDate() != null 
			  && ( printOperationInfo.getStartDate().before(printOperationInfo.getEndDate()) 
			  || printOperationInfo.getStartDate().compareTo(printOperationInfo.getEndDate()) == 0 ) )
			{				
				//����1��������Ϣ��������Ϣ���ʣ���ʼ���ڣ��������ڣ�����
				Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest1 = DataFormat.formatAmount(printOperationInfo.getCurrentInterest());
				
				Rate1 = DataFormat.formatRate( printOperationInfo.getRate(), 6 );
				
				BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
				
				OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());
				
				DayNumber1 = DataFormat.formatNumber( printOperationInfo.getNoticeDay() );				
				//���ڻ���
				if(printOperationInfo.getRate() != 0)
				{
					Connection conInternal = null;					
					Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
					double CurrentAmass11 = dailAccountDAO.currentAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getStartDate(),printOperationInfo.getEndDate());
					CurrentAmass = CurrentAmass11+"";
				//	CurrentAmass = DataFormat.formatAmount(
				//		UtilOperation.Arith.div(
				//			//modify by xwhe 2008-10-28							
				//			UtilOperation.Arith.mul(printOperationInfo.getPecisionInterestAmount(), 360)
				//		  , UtilOperation.Arith.div(printOperationInfo.getRate(), 100)
				//		)
				//	);
				}
                //modify by xwhe 2008-08-21 ���ڷֶμ�Ϣ��ʱ������
				if(printOperationInfo.getRate1() != 0){			
				
				Interest1 = DataFormat.format(printOperationInfo.getInterest1(),4);	
				
				Rate1 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
				
				BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
				
				OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
				DayNumber1 = Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getStartDate(),printOperationInfo.getEndDate1()));	
               //���ڻ���1
				if(printOperationInfo.getRate1() != 0)
				{					
                   //modify by xwhe 2008-12-24
					usedFields.put("CurrentAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount1(), 2 )); //���ڻ���1
				}
				}
				
				if(printOperationInfo.getRate2() != 0)
				{      
				
				Interest4 = DataFormat.format(printOperationInfo.getInterest2(),4);
				
				Rate4 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
				
				BeginDate4 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
				
				OverDate4 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
				DayNumber4 = Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getStartDate2(),printOperationInfo.getEndDate2()));	
               //���ڻ���2
				if(printOperationInfo.getRate2() != 0)
				{			
				//modify by xwhe 2008-12-24
				usedFields.put("CurrentAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount2(), 2 )); //���ڻ���2					
				}
				}
				if(printOperationInfo.getRate3()!=0)
				{
				Interest5 = DataFormat.format(printOperationInfo.getInterest3(),4);	
				
				Rate5 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
				
				BeginDate5 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
				
				OverDate5 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
               //���ڻ���3
				if(printOperationInfo.getRate3() != 0)
				{
				
                    //modify by xwhe 2008-12-24
					usedFields.put("CurrentAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount3(), 2 )); //���ڻ���3
					
				}
				}	
				//Э��������Ϣ(Э������)
				//if (printOperationInfo.getAccordInterestAmount() > 0)
				//����Э����Ϣ����Э�����Ϊ0ʱ��Э����Ϣ��ʾ����minzhao 20090408
				if (printOperationInfo.getAccordInterest() > 0)
				{
					Amount2 = DataFormat.formatAmount(printOperationInfo.getAccordInterestAmount());
					
					//�������Э������ 2008��3��20��
					Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount() - printOperationInfo.getAccordInterestAmount());
					
					Interest2 = DataFormat.formatAmount(printOperationInfo.getAccordInterest());
					
					Rate2 = DataFormat.formatRate( printOperationInfo.getAccordInterestRate(), 6 );
					
					BeginDate2 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
					
					OverDate2 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());
					
					DayNumber2 = DataFormat.formatNumber( printOperationInfo.getNoticeDay() );
					
					//Э������
					if(printOperationInfo.getAccordInterestRate() != 0)
					{
						Connection conInternal = null;					
						Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
						double AccordAmass22 = dailAccountDAO.negoAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getStartDate(),printOperationInfo.getEndDate());
						AccordAmass = AccordAmass22+"";
						
					//	AccordAmass = DataFormat.formatAmount(
					//		UtilOperation.Arith.div(
					//			UtilOperation.Arith.mul(printOperationInfo.getAccordInterest(), 360)
					//		  , UtilOperation.Arith.div(printOperationInfo.getAccordInterestRate(), 100)
					//		)
					//	);
					}
					//add by xwhe  2008-08-26
					if(printOperationInfo.getNegotiateRate1() != 0)
					{
				    NegotiateStartDate1 = DataFormat.getChineseDateString(printOperationInfo.getNegotiateStartDate1());
					NegotiateEndtDate1 = DataFormat.getChineseDateString(printOperationInfo.getNegotiateEndtDate1());
				    NegotiateRate1 = DataFormat.formatRate( printOperationInfo.getNegotiateRate1(), 6 );				
					NegotiateInterest1 = DataFormat.format(printOperationInfo.getNegotiateInterest1(),4);
					
				    Connection conInternal = null;
					Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
					double AccordAmass11 = dailAccountDAO.negoAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getNegotiateStartDate1(),printOperationInfo.getNegotiateEndtDate1());				   					
					AccordAmass1 = AccordAmass11+"";
					}
                    //add by xwhe  2008-08-26
					if(printOperationInfo.getNegotiateRate2() != 0)
					{
					NegotiateStartDate2 = DataFormat.getChineseDateString(printOperationInfo.getNegotiateStartDate2());
					NegotiateEndtDate2 = DataFormat.getChineseDateString(printOperationInfo.getNegotiateEndtDate2());
					NegotiateRate2 = DataFormat.formatRate( printOperationInfo.getNegotiateRate2(), 6 );
					NegotiateInterest2 = DataFormat.format(printOperationInfo.getNegotiateInterest2(),4);
					Connection conInternal = null;					
					Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
					double AccordAmass12 = dailAccountDAO.negoAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getNegotiateStartDate2(),printOperationInfo.getNegotiateEndtDate2());
					AccordAmass2 = AccordAmass12+"";		
					}										
				}
				
				//��Ϣ�ϼ�
				totalInterest = (
						DataFormat.formatAmount(
								DataFormat.formatDouble(
										DataFormat.formatDouble(printOperationInfo.getCurrentInterest()) + DataFormat.formatDouble(printOperationInfo.getAccordInterest()))));
			}
		}
		else if ( printOperationInfo.getCurrentInterest() != 0.0 )
		{
			//����Ϣ������ֹ����֮ǰ������ǰ֧ȡ
			if ( printOperationInfo.getInterestStartDate() != null 
			  && printOperationInfo.getEndDate() != null 
			  && printOperationInfo.getInterestStartDate().before(printOperationInfo.getEndDate()) )
			{
				//����1��������Ϣ��������Ϣ���ʣ���ʼ���ڣ��������ڣ�����
				Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest1 = DataFormat.formatAmount(printOperationInfo.getCurrentInterest());
				
				//���ٴ������ļ�ȡ,�ӱ� sett_TransFixedWithDraw ȡ advanceRate
				Rate1 = DataFormat.formatRate( printOperationInfo.getRate(), 6 );
				
				BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
				
				OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());
				
				//����֧ȡ ��������
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//����1
					//������Ϣ��ʵ��������ʾ
					DayNumber1 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1)) + "";
				}
				else
				{
					//֪֧ͨȡ
					if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						DayNumber1 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1)) + "";
					}
					else
					{
						DayNumber1 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1)) + "";
					}
				}
					
				//����2��������Ϣ(������)��������Ϣ����(��)����ʼ���ڣ��������ڣ�����
				if (printOperationInfo.getOtherInterest() != 0.0)
				{
					Amount2 = DataFormat.formatAmount(printOperationInfo.getAmount());
					
					Interest2 = (DataFormat.formatAmount(printOperationInfo.getOtherInterest()));
					
					BeginDate2 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
					
					OverDate2 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());
					
					//����֧ȡ ��������
					if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
					  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER )
					{				
					}
					else
					{
						//֪֧ͨȡ
						if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{						
						}
						else
						{			
						}
					}
				}
				
				//��Ϣ�ϼ�
				totalInterest = ( 
						DataFormat.formatAmount(
								DataFormat.formatDouble(
										DataFormat.formatDouble(printOperationInfo.getCurrentInterest()) + DataFormat.formatDouble(printOperationInfo.getOtherInterest()))));
			}
			//����Ϣ������ֹ����֮��
			else
			{
				//����1����Ϣ֧������Ϣ֧�����ʣ���ʼ���ڣ��������ڣ�����
				Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest1 = (
					DataFormat.formatAmount(
						DataFormat.formatDouble(
							DataFormat.formatDouble(printOperationInfo.getPayableInterest())
								)));
				
				Rate1 = DataFormat.formatRate( printOperationInfo.getRate(), 6 );
				
				BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());

				OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());
				
				//����֧ȡ ��������
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getEndDate(), 2)) + "");
				}
				else
				{
					if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//֪֧ͨȡ
						DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getEndDate(), 1)) + "");
					}
					else
					{
						DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getEndDate(), 1)) + "");
					}
				}
				
				//����2��������Ϣ��������Ϣ���ʣ���ʼ���ڣ��������ڣ�����
				Amount2 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest2 = DataFormat.formatAmount(printOperationInfo.getCurrentInterest());
				
				//���ٴ������ļ�ȡ,�ӱ� sett_TransFixedWithDraw ȡ advanceRate
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					Rate2 = DataFormat.formatRate( printOperationInfo.getAdvanceRate(), 6 );
				}
				else
				{
					Rate2 = DataFormat.formatRate( printOperationInfo.getNewRate(), 6 );
				}
				
				BeginDate2 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());

				OverDate2 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());
				
				//����֧ȡ ��������
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//������Ϣ��ʵ��������ʾ
					DayNumber2 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), 1)) + "");
				}
				else
				{
					//֪֧ͨȡ
					if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						DayNumber2 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), 1)) + "");
					}
					else
					{
						DayNumber2 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), 1)) + "");
					}
				}
				
				//������Ϣ
				if (printOperationInfo.getOtherInterest() != 0.0)
				{
					//����3��������Ϣ(������)��������Ϣ����(��)����ʼ���ڣ��������ڣ�����
					Amount3 = DataFormat.formatAmount(printOperationInfo.getAmount());
					
					Interest3 = (DataFormat.formatAmount(printOperationInfo.getOtherInterest()));
					
					BeginDate3 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());

					OverDate3 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());

					//����֧ȡ ��������
					if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
					  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//DayNumber3 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "");
					}
					else
					{
						if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//֪֧ͨȡ
							//DayNumber3 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
						{
							//DayNumber3 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag) + 1) + "");
						}
					}
				}
				
				//��Ϣ�ϼ� = ��Ϣ + ���ں������Ϣ + ������Ϣ
				totalInterest = (
						DataFormat.formatAmount(
								DataFormat.formatDouble(
										printOperationInfo.getPayableInterest() + printOperationInfo.getCurrentInterest() + printOperationInfo.getOtherInterest()
										)));
				
			}
		}
		//û�л�����Ϣ
		else
		{
			Timestamp tsStartDate = printOperationInfo.getStartDate();
			Timestamp tsEndDate = null;
			double rate = 0.0;
			double interest = 0.0;
			
			if (printOperationInfo.getStartDate() != null && printOperationInfo.getExecuteDate() != null )
			{
				if (printOperationInfo.getExecuteDate().compareTo(printOperationInfo.getStartDate()) == 0)
				{
					tsEndDate = printOperationInfo.getExecuteDate();
					
					//���ٴ������ļ�ȡ,�ӱ� sett_TransFixedWithDraw ȡ advanceRate
					//rate = Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.98);
					rate = printOperationInfo.getRate();
					
					interest = printOperationInfo.getCurrentInterest();
				}
				else
				{
					tsEndDate = printOperationInfo.getEndDate();
					rate = printOperationInfo.getRate();
					interest = printOperationInfo.getPayableInterest() - printOperationInfo.getOtherInterest();
				}
			}
			else 
			{
				tsEndDate = printOperationInfo.getEndDate();
				rate = printOperationInfo.getRate();
				interest = printOperationInfo.getPayableInterest() - printOperationInfo.getOtherInterest();
			}
			
			//����1����Ϣ֧������Ϣ֧�����ʣ���ʼ���ڣ��������ڣ�����
			Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
			
			Interest1 = DataFormat.formatAmount(interest);
			
			Rate1 = DataFormat.formatRate( rate, 6 );
			
			BeginDate1 = DataFormat.getChineseDateString(tsStartDate);

			OverDate1 = DataFormat.getChineseDateString(tsEndDate);

			//����֧ȡ ��������
			if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), tsEndDate, 2)) + "");
			}
			else 
			{
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					//֪֧ͨȡ
					DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), tsEndDate, 1)) + "");
				}
				else
				{
					DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), tsEndDate, 1)) + "");
				}
			}
			
			//��Ϣ�ϼ�
			totalInterest = (DataFormat.formatAmount(DataFormat.formatDouble(printOperationInfo.getPayableInterest())));
			
			//����2��������Ϣ(������)��������Ϣ����(��)����ʼ���ڣ��������ڣ�����
			if (printOperationInfo.getOtherInterest() != 0.0)
			{
				Amount2 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest2 = (DataFormat.formatAmount(printOperationInfo.getOtherInterest()));
				
				BeginDate2 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
				
				OverDate2 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());

				//����֧ȡ ��������
				if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
				  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//DayNumber2 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "";
				} 
				else 
				{
					//֪֧ͨȡ
					if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//DayNumber2 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1)) + "";
					}
					else
					{
						//DayNumber2 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1)) + "");
					}
				}
			}
		}
				
		//�������
		String depositTypeName = SETTConstant.TransactionType.getName( printOperationInfo.getTransTypeID() );
		if (depositTypeName.indexOf("����") >= 0)
		{
			depositTypeName = "����֧ȡ";//modify by xwhe 2008-08-15
		}
		else if (depositTypeName.indexOf("֪ͨ") >= 0)
		{
			depositTypeName = "֪ͨ���";
		} 
		else 
		{
			depositTypeName = "��֤����";
		}
		if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
		{
			depositTypeName = "��������";
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT)
		{
			depositTypeName = "���ڽ�Ϣ";
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST)
		{
			depositTypeName = "���ڴ�������Ϣ";
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST)
		{
			depositTypeName = "֪ͨ��������Ϣ";
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST)
		{
			depositTypeName = "���ڴ�������Ϣ";
		}
		
		usedFields.put("depositTypeName", depositTypeName);
		//modify by xwhe 2008-08-04 �ڴ����Ϣ�Ƹ�֪ͨ����ʵ���ڳ���������ӱ��ַ��ţ��������ֵ�������ʱ������ʾ���ַ���
		
		if (Amount1 != null && !Amount1.equals(""))
		{
			usedFields.put("Amount1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Amount1), 2 ));  //����1
		}
		if (Amount2 != null && !Amount2.equals(""))
		{
			usedFields.put("Amount2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Amount2), 2 ));  //����2
		}
		if (Amount3 != null && !Amount3.equals(""))
		{
			usedFields.put("Amount3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Amount3), 2 ));  //����3
		}
		if (Amount4 != null && !Amount4.equals(""))
		{
			usedFields.put("Amount4", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Amount4), 2 ));  //����4
		}
		
		if (Interest1 != null && !Interest1.equals(""))
		{
			usedFields.put("Interest1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(Interest1), 4 ));  //��Ϣ1
		}
		if (Interest2 != null && !Interest2.equals(""))
		{
			usedFields.put("Interest2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest2), 2 ));  //��Ϣ2
		}
		if (Interest3 != null && !Interest3.equals(""))
		{
			usedFields.put("Interest3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest3), 2 ));  //��Ϣ3
		}
		if (Interest4 != null && !Interest4.equals(""))
		{
			usedFields.put("Interest4", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(Interest4), 4 ));  //��Ϣ4
		}
		if (Interest5 != null && !Interest5.equals(""))
		{
			usedFields.put("Interest5", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(Interest5), 4 ));  //��Ϣ5
		}
		if (NegotiateInterest1 != null && !NegotiateInterest1.equals(""))
		{
			usedFields.put("NegotiateInterest1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(NegotiateInterest1), 4 ));  //Э����Ϣ1
		}
		if (NegotiateInterest2 != null && !NegotiateInterest2.equals(""))
		{
			usedFields.put("NegotiateInterest2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(NegotiateInterest2), 4 ));  //Э����Ϣ2
		}
		
		usedFields.put("Rate1", Rate1);  //����1
		usedFields.put("Rate2", Rate2);
		usedFields.put("Rate3", Rate3);
		usedFields.put("Rate4", Rate4);
		usedFields.put("Rate5", Rate5);
		usedFields.put("NegotiateRate1", NegotiateRate1);
		usedFields.put("NegotiateRate2", NegotiateRate2);
		
		usedFields.put("DayNumber1", DayNumber1);  //����1
		usedFields.put("DayNumber2", DayNumber2);
		usedFields.put("DayNumber3", DayNumber3);
		usedFields.put("DayNumber4", DayNumber4);
		
		usedFields.put("BeginDate1", BeginDate1);  //��Ϣ����1
		usedFields.put("BeginDate2", BeginDate2);
		usedFields.put("BeginDate3", BeginDate3);
		usedFields.put("BeginDate4", BeginDate4);
		usedFields.put("BeginDate5", BeginDate5);
		usedFields.put("NegotiateStartDate1", NegotiateStartDate1);
		usedFields.put("NegotiateStartDate2", NegotiateStartDate2);
		
		
		usedFields.put("OverDate1", OverDate1);  //��Ϣ����1
		usedFields.put("OverDate2", OverDate2);
		usedFields.put("OverDate3", OverDate3);
		usedFields.put("OverDate4", OverDate4);
		usedFields.put("OverDate5", OverDate5);
		usedFields.put("NegotiateEndtDate1", NegotiateEndtDate1);
		usedFields.put("NegotiateEndtDate2", NegotiateEndtDate2);
		
		
		if (CurrentAmass != null && !CurrentAmass.equals(""))
		{
			usedFields.put("CurrentAmass", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(CurrentAmass), 2 ));  //���ڻ���
		}
		//modify by xwhe 2008-08-21
		if (CurrentAmass1 != null && !CurrentAmass1.equals(""))
		{
			usedFields.put("CurrentAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(CurrentAmass1), 2 ));  //���ڻ���1
		}
		if (CurrentAmass2 != null && !CurrentAmass2.equals(""))
		{
			usedFields.put("CurrentAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(CurrentAmass2), 2 ));  //���ڻ���2
		}
		if (CurrentAmass3 != null && !CurrentAmass3.equals(""))
		{
			usedFields.put("CurrentAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(CurrentAmass3), 2 ));  //���ڻ���3
		}
		
		if (AccordAmass != null && !AccordAmass.equals(""))
		{
			usedFields.put("AccordAmass", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AccordAmass), 2 ));  //Э������
			usedFields.put("CurrentAmassType", "����");
			usedFields.put("AccordAmassType", "Э��");
		}
		//add by xwhe 2008-08-26
		if (AccordAmass1 != null && !AccordAmass1.equals(""))
		{
			usedFields.put("AccordAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AccordAmass1), 2 ));  //Э������1
			usedFields.put("CurrentAmassType", "����");
			usedFields.put("AccordAmassType", "Э��");
		}
       //add by xwhe 2008-08-26
		if (AccordAmass2 != null && !AccordAmass2.equals(""))
		{
			usedFields.put("AccordAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AccordAmass2), 2 ));  //Э������1
			usedFields.put("CurrentAmassType", "����");
			usedFields.put("AccordAmassType", "Э��");
		}
		
		if(totalInterest != null && !totalInterest.equals(""))
		{
			//��Ϣ�ϼ�Сд
			usedFields.put("AmountANDInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(DataFormat.formatDouble(Double.parseDouble(totalInterest) + printOperationInfo.getAmount()), 2) );
		
			//��Ϣ�ϼƴ�д
			usedFields.put("ChineseAmountANDInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ChineseCurrency.showChinese(DataFormat.formatAmount(DataFormat.formatDouble(Double.parseDouble(totalInterest) + printOperationInfo.getAmount())), printOperationInfo.getCurrencyID()) );
		}
		else
		{
			//��Ϣ�ϼ�Сд
			usedFields.put("AmountANDInterest", DataFormat.formatListAmount(DataFormat.formatDouble(printOperationInfo.getAmount()), 2) );
		
			//��Ϣ�ϼƴ�д
			usedFields.put("ChineseAmountANDInterest", ChineseCurrency.showChinese(DataFormat.formatAmount(DataFormat.formatDouble(printOperationInfo.getAmount())), printOperationInfo.getCurrencyID()) );
		}
			
		//�Ƹ���Ϣ�ϼ�Сд
		if (totalInterest != null && !totalInterest.equals(""))
		{
			usedFields.put("InterestTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(totalInterest), 2) );
		}
		else
		{
			usedFields.put("InterestTotalInterest",  Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+"0.00" );
		}
		
		//�Ƹ���Ϣ�ϼƴ�д
		String strChineseAmount = ChineseCurrency.showChinese(totalInterest, printOperationInfo.getCurrencyID());
		usedFields.put("ChineseInterestTotal", strChineseAmount);
		
		/************* ������Ϣ֪ͨ�� ****************/
		
		//�����(��λ����)
		usedFields.put("DebitName", printOperationInfo.getBorrowClientName() );
		
		//ί�е�λ����(������2004/04/12��Ϊ�˻�����)
		usedFields.put("ConsignClientName", NameRef.getClientNameByID( printOperationInfo.getConsignClientID() ) );
		
		//�˺�����
		if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST)
		{
			usedFields.put("AccountTypeName", "��Ӫ�����Ϣ");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST)
		{
			usedFields.put("AccountTypeName", "ί�д����Ϣ");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_INTEREST)
		{
			usedFields.put("AccountTypeName", "���Ŵ����Ϣ");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST)
		{
			usedFields.put("AccountTypeName", "ί�д��������Ϣ");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST)
		{
			usedFields.put("AccountTypeName", "��Ӫ���������Ϣ");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST)
		{
			usedFields.put("AccountTypeName", "���Ŵ��������Ϣ");
		}
		else
		{
			usedFields.put("AccountTypeName", SETTConstant.AccountType.getName( printOperationInfo.getAccountTypeID() ) );
		}
		
		String AccountNo = "";  //�˺�
		String AccountName = "";  //�˻�����
		
		//��Ӫ�����ջ�  ί�д����ջ�
		if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE ||printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
		{
			if (printOperationInfo.getReceiveAccountID() > 0)
			{
				AccountName = NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID());

				AccountNo = NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID());
			}
			else
			{
				if (printOperationInfo.getPayAccountID() > 0)
				{
					AccountName = NameRef.getAccountNameByID(printOperationInfo.getPayAccountID());

					AccountNo = NameRef.getAccountNoByID(printOperationInfo.getPayAccountID());
				}
			}
		}
		else
		{
			if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
			{
			AccountNo = NameRef.getAccountNoByID( printOperationInfo.getConsignAccountID());
			}
			else
			{
			AccountName = NameRef.getAccountNameByID(printOperationInfo.getPayAccountID());
			
			AccountNo = NameRef.getAccountNoByID(printOperationInfo.getPayAccountID());
			}
		}
		 
		//�˺�
		usedFields.put("AccountNo", DataFormat.formatString( AccountNo ) );
		
		//�˻�����
		usedFields.put("AccountName", DataFormat.formatString( AccountName ) );
		//add by xwhe 2008-07-15
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
		{
            //�˺�
			usedFields.put("LoanAccountNo", DataFormat.formatString( AccountNo ) );
		}
				
		//�Ƿ�����ָ��
		usedFields.put("IsEbankOperation", "��");
		//��ʶ�ǽ���ϵͳ��������ˮ��
		if (printOperationInfo.getInstructionNo() != null && !printOperationInfo.getInstructionNo().equals(""))
		{
			//����ָ����
			usedFields.put("InstructionNo", DataFormat.formatString( printOperationInfo.getInstructionNo() ));
			//�Ƿ�����ָ��
			usedFields.put("IsEbankOperation", "��");
		}
		
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION || printOperationInfo.getTransTypeID() > 1000 )
		{
			if(printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT  && printOperationInfo.getOperationType()==1003)
			{	
			 //modify by xwhe 2008- 12 -16
			 if (printOperationInfo.getReceiveBankID()> 0&& printOperationInfo.getPayGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT  && printOperationInfo.getOperationType()==1003)
			{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());  
			   //�跽��Ϣ
				usedFields.put("Account", "�˺�");
				usedFields.put("ReceiveAccountNo", printOperationInfo.getReceiveExtAccountNo());
	           //�տ����������
				usedFields.put("CurrentName", DataFormat.formatString( branchInfo.getBranchName() ));
			  //������Ϣ
				usedFields.put("PayAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));
				usedFields.put("GLno", "��Ŀ��");
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
						
			}
		     //modify by xwhe 2008- 12 -16
			else if (printOperationInfo.getReceiveAccountID()>0&& printOperationInfo.getPayGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT  && printOperationInfo.getOperationType()==1003)
			{
				//�跽��Ϣ
				usedFields.put("Account", "�˺�");
				usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));	
				usedFields.put("CurrentName",NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID()));
				//������Ϣ
				usedFields.put("GLno", "��Ŀ��");		
				usedFields.put("PayAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));	
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));			    					
			}
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayAccountID()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				//�跽��Ϣ
				usedFields.put("Account", "��Ŀ��");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				//������Ϣ
				usedFields.put("GLno", "�˺�");		
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID(printOperationInfo.getPayAccountID()));	
				usedFields.put("LoanName", NameRef.getAccountNameByID(printOperationInfo.getPayAccountID()));
			}		
			 //modify by xwhe 2008- 12 -16
			else if (printOperationInfo.getReceiveAccountID()>0&& printOperationInfo.getPayBankID()> 0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID()); 
				//�跽��Ϣ
				usedFields.put("Account", "�˺�");
				usedFields.put("ReceiveAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));
				usedFields.put("CurrentName", NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID()));
				//������Ϣ
				usedFields.put("GLno", "�˺�");		
				usedFields.put("PayAccountNo", printOperationInfo.getPayExtAccountNo());
				usedFields.put("LoanName",  DataFormat.formatString( branchInfo.getBranchName() ));
					
			}
			 //modify by xwhe 2008- 12 -16
			else if (printOperationInfo.getReceiveBankID()> 0&& printOperationInfo.getPayAccountID()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());  
			   //�跽��Ϣ
				usedFields.put("Account", "�˺�");
				usedFields.put("ReceiveAccountNo", printOperationInfo.getReceiveExtAccountNo());
	           //�տ����������
				usedFields.put("CurrentName", DataFormat.formatString( branchInfo.getBranchName() ));
				//������Ϣ
				usedFields.put("GLno", "�˺�");		
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID(printOperationInfo.getPayAccountID()));	
				usedFields.put("LoanName", NameRef.getAccountNameByID(printOperationInfo.getPayAccountID()));
						
			}
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayGL()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				//�跽��Ϣ
				usedFields.put("Account", "��Ŀ��");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				//������Ϣ
				usedFields.put("PayAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));
				usedFields.put("GLno", "��Ŀ��");
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
				}		
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayBankID()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());  
				//�跽��Ϣ
				usedFields.put("Account", "��Ŀ��");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				//������Ϣ
				usedFields.put("GLno", "�˺�");		
				usedFields.put("PayAccountNo", printOperationInfo.getPayExtAccountNo());
				usedFields.put("LoanName",  DataFormat.formatString( branchInfo.getBranchName() ));
				}
			}
			else if(printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
	          //modify by xwhe 2008- 12 -16
			 if (printOperationInfo.getReceiveBankID()> 0&& printOperationInfo.getPayGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());  
			   //�跽��Ϣ
				usedFields.put("ReceiveAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));
				usedFields.put("Account", "��Ŀ��");
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));		
			  //������Ϣ
				usedFields.put("PayAccountNo", printOperationInfo.getReceiveExtAccountNo());
				usedFields.put("GLno", "�˺�");
				usedFields.put("LoanName",  DataFormat.formatString( branchInfo.getBranchName() ));
						
			}
		     //modify by xwhe 2008- 12 -16
			else if (printOperationInfo.getReceiveAccountID()>0&& printOperationInfo.getPayGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				//�跽��Ϣ
				usedFields.put("Account", "��Ŀ��");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
				//������Ϣ
				usedFields.put("GLno", "�˺�");		
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));	
				usedFields.put("LoanName", NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID()));				    					
			}
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayGL()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				//�跽��Ϣ
				usedFields.put("Account", "��Ŀ��");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
				//������Ϣ
				usedFields.put("PayAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));
				usedFields.put("GLno", "��Ŀ��");
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				}	
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayAccountID()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				//�跽��Ϣ
				usedFields.put("Account", "�˺�");			
				usedFields.put("ReceiveAccountNo",NameRef.getAccountNoByID(printOperationInfo.getPayAccountID()));	
				usedFields.put("CurrentName",NameRef.getAccountNameByID(printOperationInfo.getPayAccountID()));
				//������Ϣ
				usedFields.put("GLno", "��Ŀ��");		
				usedFields.put("PayAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));		
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
			}	
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayAccountID()>0 && printOperationInfo.getReceiveBankID()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{  
				
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID()); 				
				//�跽��Ϣ
				usedFields.put("Account", "�˺�");			
				usedFields.put("ReceiveAccountNo",NameRef.getAccountNoByID(printOperationInfo.getPayAccountID()));	
				usedFields.put("CurrentName",NameRef.getAccountNameByID(printOperationInfo.getPayAccountID()));
				//������Ϣ
				usedFields.put("GLno", "�˺�");		
				usedFields.put("PayAccountNo", printOperationInfo.getReceiveExtAccountNo());		
				usedFields.put("LoanName",DataFormat.formatString( branchInfo.getBranchName() ));
			}			 		
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayBankID()>0 && printOperationInfo.getReceiveAccountID()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());  
				//�跽��Ϣ
				usedFields.put("Account", "�˺�");
				usedFields.put("ReceiveAccountNo",printOperationInfo.getPayExtAccountNo());	
				usedFields.put("CurrentName", DataFormat.formatString( branchInfo.getBranchName() ));
				//������Ϣ
				usedFields.put("GLno", "�˺�");		
				usedFields.put("PayAccountNo",NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));
				usedFields.put("LoanName",  NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID()));
				}
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayBankID()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());  
				//�跽��Ϣ
				usedFields.put("Account", "�˺�");
				usedFields.put("ReceiveAccountNo",printOperationInfo.getPayExtAccountNo());	
				usedFields.put("CurrentName", DataFormat.formatString( branchInfo.getBranchName() ));
				//������Ϣ
				usedFields.put("GLno", "��Ŀ��");		
				usedFields.put("PayAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));
				usedFields.put("LoanName",  NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				}			 
			}
		}
		
		//modify by xwhe 2008-12-22 ���ŷ���
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANGRANT )
		{
             //��������
			usedFields.put("LoanTypeName", SETTConstant.AccountGroupType.getName( printOperationInfo.getAccountTypeID()));
		}
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE )
		{
             //�����˺�
			usedFields.put("LoanAccountNo", DataFormat.formatString( AccountNo ));
		}
		
		//����ҵ��
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.GENERALLEDGER )
		{
			//���˺ŵ����
			if(printOperationInfo.getPayAccountID() > 0 || printOperationInfo.getReceiveAccountID() > 0)
			{
				if(printOperationInfo.getPayAccountID() > 0)
				{
					usedFields.put("GenerPayAccount", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));
					usedFields.put("GenerPayAccountNo1", "");
				}
				else
				{
					usedFields.put("GenerReceiveAccount", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
					usedFields.put("GenerReceiveAccountNo1", "");
				}
				
				usedFields.put("GenerAmount1", DataFormat.formatListAmount( printOperationInfo.getAccountAmount(), 2 ));
				
				if(printOperationInfo.getPayGL1() > 0 || printOperationInfo.getReceiveGL1() > 0)
				{
					if(printOperationInfo.getPayGL1() > 0)
					{
						usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL1() ));
					}
					else
					{
						usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL1() ));
					}

					usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount1(), 2 ));
					
					if(printOperationInfo.getPayGL2() > 0 || printOperationInfo.getReceiveGL2() > 0)
					{
						if(printOperationInfo.getPayGL2() > 0)
						{
							usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL2() ));
						}
						else
						{
							usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL2() ));
						}
						
						usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount2(), 2 ));
						
						if(printOperationInfo.getPayGL3() > 0 || printOperationInfo.getReceiveGL3() > 0)
						{
							if(printOperationInfo.getPayGL3() > 0)
							{
								usedFields.put("GenerPayAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getPayGL3() ));
							}
							else
							{
								usedFields.put("GenerReceiveAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL3() ));
							}
							
							usedFields.put("GenerAmount4", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo5", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo5", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount5", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo5", "");
								usedFields.put("GenerReceiveAccountNo5", "");
							}
						}
						else
						{
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount4", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo4", "");
								usedFields.put("GenerReceiveAccountNo4", "");
							}
						}
					}
					else
					{
						if(printOperationInfo.getPayGL3() > 0 || printOperationInfo.getReceiveGL3() > 0)
						{
							if(printOperationInfo.getPayGL3() > 0)
							{
								usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL3() ));
							}
							else
							{
								usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL3() ));
							}
						
							usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount4", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo4", "");
								usedFields.put("GenerReceiveAccountNo4", "");
							}
						}
						else
						{
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo3", "");
								usedFields.put("GenerReceiveAccountNo3", "");
							}
						}
					}
				}
				else
				{
					if(printOperationInfo.getPayGL2() > 0 || printOperationInfo.getReceiveGL2() > 0)
					{
						if(printOperationInfo.getPayGL2() > 0)
						{
							usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL2() ));
						}
						else
						{
							usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL2() ));
						}
						
						usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount2(), 2 ));
						
						if(printOperationInfo.getPayGL3() > 0 || printOperationInfo.getReceiveGL3() > 0)
						{
							if(printOperationInfo.getPayGL3() > 0)
							{
								usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL3() ));
							}
							else
							{
								usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL3() ));
							}
						
							usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount4", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo4", "");
								usedFields.put("GenerReceiveAccountNo4", "");
							}
						}
						else
						{
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo3", "");
								usedFields.put("GenerReceiveAccountNo3", "");
							}
						}
					}
					else
					{
						if(printOperationInfo.getPayGL3() > 0 || printOperationInfo.getReceiveGL3() > 0)
						{
							if(printOperationInfo.getPayGL3() > 0)
							{
								usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL3() ));
							}
							else
							{
								usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL3() ));
							}
						
							usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo3", "");
								usedFields.put("GenerReceiveAccountNo3", "");
							}
						}
						else
						{
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{

								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo2", "");
								usedFields.put("GenerReceiveAccountNo2", "");
							}
						}
					}
				}
			}
			//û���˺�,ֻ�п�Ŀ
			else
			{
				if(printOperationInfo.getPayGL1() > 0 || printOperationInfo.getReceiveGL1() > 0)
				{
					if(printOperationInfo.getPayGL1() > 0)
					{
						usedFields.put("GenerPayAccountNo1", NameRef.getGLKNoByID( printOperationInfo.getPayGL1() ));
					}
					else
					{
						usedFields.put("GenerReceiveAccountNo1", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL1() ));
					}
					
					usedFields.put("GenerAmount1", DataFormat.formatListAmount( printOperationInfo.getAmount1(), 2 ));
					
					if(printOperationInfo.getPayGL2() > 0 || printOperationInfo.getReceiveGL2() > 0)
					{
						if(printOperationInfo.getPayGL2() > 0)
						{
							usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL2() ));
						}
						else
						{
							usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL2() ));
						}
						
						usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount2(), 2 ));
						
						if(printOperationInfo.getPayGL3() > 0 || printOperationInfo.getReceiveGL3() > 0)
						{
							if(printOperationInfo.getPayGL3() > 0)
							{
								usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL3() ));
							}
							else
							{
								usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL3() ));
							}
							
							usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo4", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount4", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo4", "");
								usedFields.put("GenerReceiveAccountNo4", "");
							}
						}
						else
						{
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo3", "");
								usedFields.put("GenerReceiveAccountNo3", "");
							}
						}
					}
					else
					{
						if(printOperationInfo.getPayGL3() > 0 || printOperationInfo.getReceiveGL3() > 0)
						{
							if(printOperationInfo.getPayGL3() > 0)
							{
								usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL3() ));
							}
							else
							{
								usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL3() ));
							}
						
							usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo3", "");
								usedFields.put("GenerReceiveAccountNo3", "");
							}
						}
						else
						{
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
									usedFields.put("GenerPayAccountNo2", "");
									usedFields.put("GenerReceiveAccountNo2", "");
							}
						}
					}
				}
				else
				{
					if(printOperationInfo.getPayGL2() > 0 || printOperationInfo.getReceiveGL2() > 0)
					{
						if(printOperationInfo.getPayGL2() > 0)
						{
							usedFields.put("GenerPayAccountNo1", NameRef.getGLKNoByID( printOperationInfo.getPayGL2() ));
						}
						else
						{
							usedFields.put("GenerReceiveAccountNo1", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL2() ));
						}
						
						usedFields.put("GenerAmount1", DataFormat.formatListAmount( printOperationInfo.getAmount2(), 2 ));
						
						if(printOperationInfo.getPayGL3() > 0 || printOperationInfo.getReceiveGL3() > 0)
						{
							if(printOperationInfo.getPayGL3() > 0)
							{
								usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL3() ));
							}
							else
							{
								usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL3() ));
							}
						
							usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo3", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount3", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo3", "");
								usedFields.put("GenerReceiveAccountNo3", "");
							}
						}
						else
						{
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo2", "");
								usedFields.put("GenerReceiveAccountNo2", "");
							}
						}
					}
					else
					{
						if(printOperationInfo.getPayGL3() > 0 || printOperationInfo.getReceiveGL3() > 0)
						{
							if(printOperationInfo.getPayGL3() > 0)
							{
								usedFields.put("GenerPayAccountNo1", NameRef.getGLKNoByID( printOperationInfo.getPayGL3() ));
							}
							else
							{
								usedFields.put("GenerReceiveAccountNo1", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL3() ));
							}
						
							usedFields.put("GenerAmount1", DataFormat.formatListAmount( printOperationInfo.getAmount3(), 2 ));
							

							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo2", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount2", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo2", "");
								usedFields.put("GenerReceiveAccountNo2", "");
							}
						}
						else
						{
							if(printOperationInfo.getPayGL4() > 0 || printOperationInfo.getReceiveGL4() > 0)
							{
								if(printOperationInfo.getPayGL4() > 0)
								{
									usedFields.put("GenerPayAccountNo1", NameRef.getGLKNoByID( printOperationInfo.getPayGL4() ));
								}
								else
								{
									usedFields.put("GenerReceiveAccountNo1", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL4() ));
								}
								
								usedFields.put("GenerAmount1", DataFormat.formatListAmount( printOperationInfo.getAmount4(), 2 ));
							}
							else
							{
								usedFields.put("GenerPayAccountNo1", "");
								usedFields.put("GenerReceiveAccountNo1", "");
							}
						}
					}
				}
			}
		}
	}
		return usedFields;
	}
	
	//��ʽ�����,�����ѧ����������������
	public String getFormatAmount(double amount) throws Exception
	{
		String strAmount = "0.00";
		
		if(amount != 0.0)
		{
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			strAmount = decimalFormat.format(amount);
			strAmount = DataFormat.formatListAmount(Double.parseDouble(strAmount), 2);
		}
		
		return strAmount;
	}
	
	//��ʽ�����,�����ѧ����������������
	public String getChineseFormatAmount(double amount, long currencyID) throws Exception
	{
		String strChineseAmount = "��Ԫ";
		
		if(amount != 0.0)
		{
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			strChineseAmount = decimalFormat.format(amount);
			strChineseAmount = ChineseCurrency.showChinese(strChineseAmount, currencyID);
		}
		
		return strChineseAmount;
	}
	//�õ�����
	protected Connection getConnection()
	{
		Connection con = null;
		try
		{
			
		con = Database.getConnection();
			
		}
		catch (Exception sqle)
		{
			sqle.printStackTrace();
		}
		return con;		
	}
	protected void cleanup(Connection con) throws SQLException
	{
		try
		{		
				con.close();
				con = null;
			
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	
}