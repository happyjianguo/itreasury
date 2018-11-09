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
     * 判断是否要加签章
     * @param clientID  客户id
     * @param transTypeID 交易类型编号
     * @param nOfficeID 办事处ID
     * @param nCurrencyID 币种ID
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
			//银行收款 银行付款 内部转账 委托存款 保证金存款
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
				//添加"银行付款-拨子账户"业务类型
				lTransactionTypeID == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT ||
				//添加"企业资金上划"业务类型
				lTransactionTypeID == SETTConstant.TransactionType.CAPITALUP ||
				//添加"企业资金下拨"业务类型
				lTransactionTypeID == SETTConstant.TransactionType.CAPITALDOWN )
			{
				printInfo = this.getCurrentPrintInfo(lID);
			}
			//定期开立 通知存款开立
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
			//定期支取 通知存款支取
			else if( lTransactionTypeID==SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER 
				  || lTransactionTypeID==SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
			{
				printInfo=this.getFixDepositWithDrawPrintInfo(lID);
			}
			//定期续存
			else if( lTransactionTypeID==SETTConstant.TransactionType.FIXEDCONTINUETRANSFER )
			{
				printInfo=this.getFixDepositContinuePrintInfo(lID);
			}	
			//自营贷款发放 委托贷款发放
			else if( lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANGRANT 
				  || lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANGRANT )
			{
				printInfo=this.getLoanGrantPrintInfo(lID);
			}
			//自营贷款收回 委托贷款收回
			else if( lTransactionTypeID==SETTConstant.TransactionType.TRUSTLOANRECEIVE 
				  || lTransactionTypeID==SETTConstant.TransactionType.CONSIGNLOANRECEIVE )
			{
				printInfo=this.getLoanRepaymentPrintInfo(lID);
			}
			//贴现发放
			else if( lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTGRANT )
			{
				printInfo=this.getDiscountGrantPrintInfo(lID);
			}
			//贴现收回
			else if( lTransactionTypeID==SETTConstant.TransactionType.DISCOUNTRECEIVE )
			{
				printInfo=this.getDiscountRepaymentPrintInfo(lID);
			} 
			//总账
			else if( lTransactionTypeID==SETTConstant.TransactionType.GENERALLEDGER )
			{
				printInfo=this.getGLPrintInfo(lID);
			}
			//特殊业务//add by xwhe 2008-09-10 特殊业务子类型暂时只能这样区分了
			else if( lTransactionTypeID==SETTConstant.TransactionType.SPECIALOPERATION||lTransactionTypeID>1000 )
			{
				printInfo=this.getSpecialPrintInfo(lID);		
			}
			//一付多收 资金上收
			else if( lTransactionTypeID==SETTConstant.TransactionType.ONETOMULTI 
				  || lTransactionTypeID == SETTConstant.TransactionType.UPGATHERING )
			{
				printInfo=this.getOneMutiRepaymentPrintInfo(lID);
			}
			//利息费用支付
			else if( lTransactionTypeID==SETTConstant.TransactionType.INTERESTFEEPAYMENT )
			{
				printInfo=this.getInterestFeePaymentPrintInfo(lID);
			}
			//财务公司付款（证券投资结算）
			else if( lTransactionTypeID==SETTConstant.TransactionType.SECURITIESPAY )
			{
				//printInfo=this.getSecuritiesPrintInfo(lID);	//	15.财务公司付款（证券投资结算）
			}
			//银团贷款发放
			else if( lTransactionTypeID==SETTConstant.TransactionType.BANKGROUPLOANGRANT )
			{
				printInfo=this.getSynLoanGrantPrintInfo(lID);
			}
			//银团贷款收回
			else if( lTransactionTypeID==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE )
			{
				printInfo=this.getSynLoanRepaymentPrintInfo(lID);
			} 
			//交易费用处理
			else if( lTransactionTypeID==SETTConstant.TransactionType.TRANSFEE )
			{
				printInfo=this.getTransFeePrintInfo(lID);
			}
			//保证金开立
			else if( lTransactionTypeID == SETTConstant.TransactionType.OPENMARGIN )
			{
				printInfo = this.getTransOpenMarginDepositPrintInfo(lID);	
			}
			//保证金支取
			else if( lTransactionTypeID == SETTConstant.TransactionType.WITHDRAWMARGIN )
			{
				printInfo = this.getTransMarginWithdrawPrintInfo(lID);							
			}
			//多笔贷款收回
			else if( lTransactionTypeID == SETTConstant.TransactionType.MULTILOANRECEIVE )
			{
				System.out.println("多笔贷款收回这个没有做在settlement-print-control-co17.jsp中有定义");
			}
			else if( lTransactionTypeID == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT			//利息费用支付活期结息
				  || lTransactionTypeID == SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST		//定期存款计提应付利息（含冲销）
				  || lTransactionTypeID == SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST  	//通知存款计提应付利息（含冲销）
				  || lTransactionTypeID == SETTConstant.TransactionType.TRUST_LOAN_INTEREST  				//自营贷款结息(内部资金占用结息)
				  || lTransactionTypeID == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST  				//委托贷款结息
				  || lTransactionTypeID == SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST  	//活期存款计提应付利息（含冲销）
				  || lTransactionTypeID == SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST		//委托贷款计提应收利息（含冲销）
				  || lTransactionTypeID == SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST			//自营贷款计提应收利息（含冲销）
				  || lTransactionTypeID == SETTConstant.TransactionType.YT_LOAN_INTEREST                    //银团贷款结息
				  || lTransactionTypeID == SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST            //银团贷款计提应收利息（含冲销）
				  
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
			//暂时无此交易类型的打印
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
		
		//返回打印信息INFO类
		return printInfo;
	}
	//信贷资产转让收款，代收款，付款打印
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
	
	//银行收款 银行付款 内部转账 委托存款 保证金存款
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
				
				//委托存款(现在只发现委托存款有问题)
				//if ( resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.CONSIGNSAVE )
				//{
					//在银行付款中-bankid 为付款银行	
					if (resultInfo.getBankID() > 0)
					{
						printInfo.setPayBankID(resultInfo.getBankID());
					}
					
					//针对 "银行付款-拨子账户" 业务的逻辑判断
					//该业务在存的时候收款方数据存放的字段是付款方字段"npayaccountid",针对此业务进行转换
					if ( resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT )
					{
						if (resultInfo.getPayAccountID() > 0)
						{
							printInfo.setReceiveAccountID(resultInfo.getPayAccountID());
							printInfo.setPayAccountID(-1);
						}
					}
					
					//在银行收款中-bankid 为收款银行	
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
				
				//标识非结算系统产生的流水号
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
	
	//定期开立 通知存款开立
	public PrintInfo getFixDepositOpenPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
			TransFixedOpenInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				
				//标识非结算系统产生的流水号
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
	
	//定期支取 通知存款支取
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
				if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)
				{
					//支取金额
					if ( resultInfo.getDrawAmount() != 0.0 )
					{
						printInfo.setAmount(resultInfo.getDrawAmount());
					}
					//本金
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
				//其它利息
				if (resultInfo.getOtherInterest() != 0.0)
				{
					printInfo.setOtherInterest(resultInfo.getOtherInterest());
				}
				//收息账户号
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//else
				//{
				//	printInfo.setReceiveInterestAccountID(NameRef.getAccountIdByNo(resultInfo.getInterestExtAcctNo()));
				//}
				
				/*****这里取利率需要处理,如果是提前支取取 advancerate(活期利率) *****/
				//利率
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
				
				//活期利率
				if (resultInfo.getAdvanceRate() > 0)
				{
					printInfo.setNewRate(resultInfo.getAdvanceRate());
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
				if (resultInfo.getPayableInterest() != 0.0 || resultInfo.getPreDrawInterest() != 0.0)
				{
					printInfo.setPayableInterest( UtilOperation.Arith.add(resultInfo.getPayableInterest(), resultInfo.getPreDrawInterest()) );
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
				
				//增加收息账户号
				if (resultInfo.getInterestExtAcctNo() != null)
				{
					printInfo.setInterestextaccountno(resultInfo.getInterestExtAcctNo());
				}
				if (resultInfo.getNoticeDay() != 0)
				{
					printInfo.setNoticeDay(resultInfo.getNoticeDay());
				}
				
				//标识非结算系统产生的流水号
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
	
	//定期续存
	public PrintInfo getFixDepositContinuePrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
			TransFixedContinueInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
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
				//定期续存旧存单号
				if (resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getDepositNo());
				}
				//定期续存新存单号(2007-10-19)
				if (resultInfo.getNewDepositNo() != null && resultInfo.getNewDepositNo().length() > 0)
				{
					printInfo.setDepositBillNO(resultInfo.getNewDepositNo());
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
				//新利率(定期续存开户证实书显示新利率2007-10-19)
				if (resultInfo.getNewRate() != 0.0)
			 	{
					printInfo.setNewRate(resultInfo.getNewRate());
				}
				//旧利率
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
					//Boxu Update 2008年5月14日 修改成取本次支取利息(MWITHDRAWINTEREST)
					//Boxu Update 2009年2月18日 修改取定期利息，不包括活期利息
                    //minzhao Update 2009年4月2日 改回早期版本
					//printInfo.setPayableInterest(resultInfo.getPayableInterest());
					printInfo.setPayableInterest(resultInfo.getWithDrawInterest());
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
				//新存单开始(起始)日期(定期续存开户证实书显示新起始日期2007-10-19)
				if (resultInfo.getNewStartDate() != null)
				{
					printInfo.setNewStartDate(resultInfo.getNewStartDate());
				}
				//新存单结束(截止)日期(定期续存开户证实书显示新截止日期2007-10-19)
				if (resultInfo.getNewEndDate() != null)
				{
					printInfo.setNewEndDate(resultInfo.getNewEndDate());
				}
				//存款类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//是否本利续存
				if(resultInfo.getIsCapitalAndInterestTransfer() > 0)
				{
					printInfo.setIsCapitalAndInterestTransfer(resultInfo.getIsCapitalAndInterestTransfer());
					
					//如果是本息续存，则没有"存款利息计付通知单"清除利息单上的利息
					//printInfo.setRealInterest(0.0);
					printInfo.setRealInterestReceivable(0.0);
                    //printInfo.setPayableInterest(resultInfo.getPayableInterest());
					//minzhao Update 2009年4月2日 改回取利息合计（有活期利息的隐患）
					printInfo.setPayableInterest(resultInfo.getWithDrawInterest());
				}
				
				//标识非结算系统产生的流水号
				if (resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
				{
					printInfo.setInstructionNo(resultInfo.getInstructionNo());
				}
				
				//活期利息
				if (resultInfo.getCurrentInterest() != 0.0)
				{
					printInfo.setCurrentInterest(resultInfo.getCurrentInterest());
				}
				
				//活期利率
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
	
	//自营贷款发放 委托贷款发放
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
	
	//自营贷款收回 委托贷款收回
	public PrintInfo getLoanRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
			//判断是否银团贷款
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
				//活期存款帐户ID
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getDepositAccountID());
				}
				//收款银行ID
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
				//利息税费
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
				//实付利息税费
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
				//手续费开始日期
				if (resultInfo.getCommissionStart() != null)
				{
					printInfo.setCommissionStart(resultInfo.getCommissionStart());
				}
				//手续费结束日期
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
				//业务类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//贷款单位（委存账户）
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getConsignAccountID());
				}
				//委托方活期账户
				if(resultInfo.getConsignDepositAccountID()> 0 )
				{
					printInfo.setConsignDepositAccountID(resultInfo.getConsignDepositAccountID());
				}
				
                //贷款分段计息
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
	
	//贴现发放
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
				//收款方
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				}
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
				if (resultInfo.getInterestOfDiscount() != 0.0)
				{
					printInfo.setDiscountInterestAmount(resultInfo.getInterestOfDiscount());
				}
				
				//贴现单位(申请人)
				if (resultInfo.getSignBillClientName() != null)
				{
					printInfo.setApplicantName(resultInfo.getSignBillClientName());
				}
				
				//申请人账号
				if (resultInfo.getDiscountAccountID() > 0)
				{
					printInfo.setApplicantAccountNo( NameRef.getAccountNoByID( resultInfo.getDiscountAccountID() ) );
				}
				
				//申请人开户银行
				if (resultInfo.getBankName() != null)
				{
					printInfo.setApplicantAccountBankNo(resultInfo.getBankName());
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

	//贴现收回
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
				//执行日，即贴现日
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
				//当前贴现余额
				if (resultInfo.getMSumReceiveAmout() != 0.0)
				{
					printInfo.setCurrentBalance(resultInfo.getMSumReceiveAmout());
				}
				//付款银行ID,删除原因：在贴现收回交易中打印的特种转账借方凭证中付款方取退票处理中的活期账户号，
				//如果没有退票，则付款方显示为空
				/***** 2007-10-18 修改显示付款方信息, 不是退票也显示信息(活期或开户行) *****/
				if (resultInfo.getNBankID() > 0)
				{
					//printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getNBankID()));
					//printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNBankID()));
					printInfo.setPayBankID(resultInfo.getNBankID());
				}
				//付款方(收回处理活期账户)
				if (resultInfo.getNCurrentAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getNCurrentAccountID());
				}
				//付款方(退票处理活期账户)
				if (resultInfo.getNDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getNDepositAccountID());
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
				//贴现单位
				if (resultInfo.getNClientID() > 0)
				{
					printInfo.setApplicantName( NameRef.getClientNameByID(resultInfo.getNClientID()) );
				}
				//贴现账号
				if (resultInfo.getNDiscountAccountID() > 0)
				{
					printInfo.setApplicantAccountNo( NameRef.getAccountNoByID( resultInfo.getNDiscountAccountID() ) );
				}
				/***** 如果是退票处理, 收回显示金额为"退票金额" *****/
				//汇票金额
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
	
	//总账
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
				
				//总金额(取一个方向就行)
				double amount = 0.0;
				
				//付款方账户号
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 1)
				{
					amount += resultInfo.getAmount();
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				//收款方账户号
				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 2)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}				
				//账户金额
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAccountAmount(resultInfo.getAmount());
				}
				
				//总账一
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
				
				//总账二
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
				
				//总账三
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
				
				//总账四
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
				
				//总金额
				if (amount != 0.0)
				{
					printInfo.setAmount( amount );
				}
				
				//付款方账户号ID,据此可得付款方全称，付款方账户号
//				if (resultInfo.getAccountID() > 0 && resultInfo.getDirection() == 2)
//				{
//					printInfo.setPayAccountID(resultInfo.getAccountID());
//				}
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

	//特殊业务
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
				
				//收款方总账类ID
				if(resultInfo.getNreceivegeneralledgertypeid() > 0)
				{
					printInfo.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
				}
				
				//付款方总账类ID
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
				
				//应该取总账的方法,这样付值会有问题
				//if (resultInfo.getNpaygeneralledgertypeid() > 0)
				//{
				//	printInfo.setPayAccountID(resultInfo.getNpaygeneralledgertypeid());
				//}
				
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
				//add by xwhe 2008-08-07 查找审批人(只有一级审批的情况)
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
		
				//银行账号
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
	                					
					long payBankID = -1;//付款方银行ID
					long receiveBankID = -1;//收款方银行ID
					String payAccountNo = "";//付款方银行帐号
	 				String receiveAccountNo = "";//收款方银行帐号
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
					printInfo.setPayExtAccountNo(payAccountNo);//外部付款账户号
					
					printInfo.setExtAccountNo(receiveAccountNo);//外部收款账户号
					
					
				   }				
                 //added by xwhe 2008-12-16 对特殊业务里面子类型为其它的单独处理
				if(resultInfo!=null && resultInfo.getNoperationtypeid()==1003)
				{
					//执行日
					if (resultInfo.getDtexecute() != null)
					{
						printInfo.setExecuteDate(resultInfo.getDtexecute());
					}
					//交易编号
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
					
					long payBankID = -1;//付款方银行ID
					long receiveBankID = -1;//收款方银行ID
					String payAccountNo = "";//付款方银行帐号
	 				String receiveAccountNo = "";//收款方银行帐号
										
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
					
					printInfo.setPayExtAccountNo(payAccountNo);//外部付款账户号
					
					printInfo.setReceiveExtAccountNo(receiveAccountNo);//外部收款账户号
					
                    //付款方总账类ID
					if(resultInfo.getNpaygeneralledgertypeid() > 0)
					{
						printInfo.setPayGL(resultInfo.getNpaygeneralledgertypeid());
					}
                    //收款方总账类ID
					if(resultInfo.getNreceivegeneralledgertypeid() > 0)
					{
						printInfo.setReceiveGL(resultInfo.getNreceivegeneralledgertypeid());
					}
					//收款方为活期账户
					if (resultInfo.getNreceiveaccountid() > 0 )
					{
						printInfo.setReceiveAccountID(resultInfo.getNreceiveaccountid());
					}
                    //金额
					if (resultInfo.getMpayamount() != 0.0)
					{
						printInfo.setAmount(resultInfo.getMpayamount());
					}
                   //摘要
					if (resultInfo.getSabstract() !=null)
					{
					    printInfo.setAbstract(resultInfo.getSabstract() );
					}
					//录入人
					if (resultInfo.getNinputuserid() > 0)
					{
						printInfo.setInputUserID(resultInfo.getNinputuserid());
					}
					//复核人
					if (resultInfo.getNcheckuserid() > 0)
					{
						printInfo.setCheckUserID(resultInfo.getNcheckuserid());
					}
					//付款方向
					if (resultInfo.getNpaydirection() > 0)
					{
						printInfo.setNpaydirection(resultInfo.getNpaydirection());
					}
					//收款方向
					if (resultInfo.getNreceivedirection() > 0)
					{
						printInfo.setNerceivedirection(resultInfo.getNreceivedirection());
					}
                  //其它
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
	
	//一付多收
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
				//交易号
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				
				//金额
				if (resultInfo.getAmount() > 0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				
				//锦西代办处添加  多借多贷添加
				//if(resultInfo.getPayGL()!=-1)
				//{
				//	printInfo.setPayBankID(resultInfo.getBankID());
				//}
				
				if(resultInfo.getReceiveGL()!=-1)
				{
					printInfo.setReceiveBankID(resultInfo.getBankID());
				}
				
				//根据交易号查找相关勾账交易记录信息
				TransOnePayMultiReceiveInfo findCondition = new TransOnePayMultiReceiveInfo(); //查询条件
				TransOnePayMultiReceiveInfo transOnePayMultiReceiveInfo = null; //查询结果
				TransOnePayMultiReceiveInfo tempTransOnePayMultiReceiveInfo = null; //在遍历的过程中存储收付方信息 
				
				Collection resultCollection = null;
				boolean bFlagPay = false;
				long lIndexPay = 0;
				boolean bFlagReceive = false;
				long lIndexReceive = 0;
				String PrintName = "";
				
				//通过银行ID获得  "开户行打印名称"(全称显示)
				if ( resultInfo.getBankID() > 0 )
				{
					Sett_BranchDAO ldao = new Sett_BranchDAO();
					BranchInfo linfo = new BranchInfo();
					linfo = ldao.findByID(resultInfo.getBankID());
					
					//付款人全称
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
						if (resultInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY) //如果当前记录是付方
						{
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
								printInfo.setAmount(resultInfo.getAmount());							
								if (lIndexReceive == 1)
								{
									//收方唯一
									//设置付方的信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
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
									//仅设置付方的信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setPayAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
									{
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(resultInfo.getBankID())); //银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(resultInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(resultInfo.getBankID()));
									}
									if (resultInfo.getIsGL() > 0 || resultInfo.getPayGL() > 0) //if总账转账
									{
										printInfo.setPayGL(resultInfo.getPayGL());
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
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(resultInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
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
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
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
								//当前记录不是唯一的收方
								if (lIndexPay == 1)
								{
									//付方唯一
									printInfo.setAmount(resultInfo.getAmount());
									//设置收方信息
									if (resultInfo.getIsInternalVirement() > 0 || resultInfo.getAccountID() > 0) //if内部转账
									{
										printInfo.setReceiveAccountID(resultInfo.getAccountID());
									}
									if (resultInfo.getIsBank() > 0 || resultInfo.getBankID() > 0) //if银行转账(为应用模版,此处进行特殊处理,银行信息均设为外部账户号)
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
										//printInfo.setPayExtClientName(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtClientName(PrintName);
										
										printInfo.setPayExtAccountNo(IPrintTemplate.getBankAccountCodeByID(tempTransOnePayMultiReceiveInfo.getBankID()));				
										printInfo.setPayExtRemitInBank(NameRef.getBankNameByID(tempTransOnePayMultiReceiveInfo.getBankID())); //银行转账不写开户行信息
										printInfo.setPayExtRemitInProvince(IPrintTemplate.getBankAccountCityByID(tempTransOnePayMultiReceiveInfo.getBankID()));
										printInfo.setPayExtRemitInCity(IPrintTemplate.getBankAccountProvinceByID(tempTransOnePayMultiReceiveInfo.getBankID()));
									}
									if (tempTransOnePayMultiReceiveInfo.getIsGL() > 0 || tempTransOnePayMultiReceiveInfo.getPayGL() > 0) //if总账转账
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
	
	//利息费用支付
	public PrintInfo getInterestFeePaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		String strTemp = "";
		try
		{
			Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
			//判断是否银团贷款
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
				{
					lAccountType = NameRef.getAccountTypeByID(resultInfo.getLoanAccountID());
				}
	
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
				//modify by xwhe 2008-12-22
				if (SETTConstant.AccountType.isYTAccountType(lAccountType)) //银团
				{
					//交易类型
				//	printInfo.setTransTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
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
				
				//利息总额（贷款利息通知单中设置为本金）
				//printInfo.setAmount(
				//	DataFormat.formatDouble(
				//		DataFormat.formatDouble(resultInfo.getRealInterest())
				//			+ DataFormat.formatDouble(resultInfo.getRealCompoundInterest())
				//			+ DataFormat.formatDouble(resultInfo.getRealOverDueInterest())));
				
				//利息本金从子账户获得
				Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
				SubAccountAssemblerInfo subAccountInfo = new SubAccountAssemblerInfo();
				subAccountInfo = subAccountDAO.findByID(resultInfo.getSubAccountID());
				printInfo.setAmount(subAccountInfo.getSubAccountLoanInfo().getBalance());
				printInfo.setSubAccountID(resultInfo.getSubAccountID());
				
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
					printInfo.setAmount(resultInfo.getCurrentBalance());
				}
				//付息账号,也是付手续费和担保费的账户号
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPayInterestAccountID());
					printInfo.setPayCommissionAccountID(resultInfo.getPayInterestAccountID());
				}
				
				//还款客户ID
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setPayClientID( resultInfo.getClientID() );
				}
                //委托单位id modify by xwhe 2008-07-15
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getReceiveInterestAccountID()));
				}
				
                //贷款分段计息
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
	
	//换发证书 定期 通知存款 
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
	
	//银团贷款发放
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
				//放款类型
				//借款账户,即自营或者委托贷款账户号
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
				if (SETTConstant.AccountType.isYTAccountType(lAccountType)) //银团
				{
					//交易类型
				//	printInfo.setTransTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
					//账户类型
					printInfo.setAccountTypeID(lAccountType);
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

	//银团贷款收回
	public PrintInfo getSynLoanRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
			TransRepaymentLoanInfo resultInfo = null;
			resultInfo = dao.findByID(lID);
			
			//判断是否银团贷款
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
	
	//换开定期存单数据
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
				
				//已复核取  SDEPOSITBILLCHECKABSTRACT
				if ( resultInfo.getDepositBillStatusID() == SETTConstant.TransactionStatus.CHECK )
				{
					//换开定期存单复核摘要
					if (resultInfo.getDepositBillCHECKABSTRACT() !=null)
					{
					    printInfo.setAbstract(resultInfo.getDepositBillCHECKABSTRACT());
					}
				}
				//不是已复核
				else if ( resultInfo.getDepositBillStatusID() == SETTConstant.TransactionStatus.SAVE
					  ||  resultInfo.getDepositBillStatusID() == SETTConstant.TransactionStatus.APPROVALING 
					  ||  resultInfo.getDepositBillStatusID() == SETTConstant.TransactionStatus.APPROVALED )
				{
					//换开定期存单录入摘要
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
				
				//换开定期存单号
				if(resultInfo.getDepositBillNO()!=null)
				{
					printInfo.setDepositBillNO(resultInfo.getDepositBillNO());
				}

				//标识非结算系统产生的流水号
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
	
	//交易费用处理
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
				//交易费用类型
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
				
				/************************从此开始改写***********************/
				//FeeBankID
				//在银行付款中-bankid 为付款银行			
				if (resultInfo.getFeeBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getFeeBankID());
				}
				//在银行收款中-bankid 为收款银行			
				if (resultInfo.getFeeBankID() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getFeeBankID());
				}
				//NRELATEDACCOUNTID 对应账户号
				//NACCOUNTID 支付费用账户号
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				if (resultInfo.getRelatedAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getRelatedAccountID());
				}
				//NRELATEDCLIENTID 支付费用客户号
				if (resultInfo.getRelatedClientID() > 0)
				{
					printInfo.setReceiveClientID(resultInfo.getRelatedClientID());
				}
				if (resultInfo.getRelatedClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getRelatedClientID());
				}
				//abstractID 摘要ID 
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				//SEXTACCTNO 外部账户号
				//extAcctName 外部客户名称 
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
	
	//保证金开立
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

	//保证金支取
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
			//其它利息
			if (resultInfo.getOtherInterest() != 0.0)
			{
				printInfo.setOtherInterest(resultInfo.getOtherInterest());
			}
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
	
	//利息费用支付-活期结息, 定期存款计提应付利息（含冲销）
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
				//交易编号
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//付款方账号(这里是活期账户)
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID( resultInfo.getAccountID() );
				}
				//子账户ID
				if (resultInfo.getSubAccountID() > 0)
				{
					printInfo.setSubAccountID( resultInfo.getSubAccountID() );
				}
				//交易类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID( resultInfo.getTransactionTypeID() );
				}
				//活期本金(不是协定存款金额)
				if (resultInfo.getBaseBalance() > 0)
				{
					printInfo.setAmount( resultInfo.getBaseBalance() );
				}
				//计息开始日期
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setStartDate( resultInfo.getInterestStartDate() );
				}
				//计息结束日期
				if (resultInfo.getInterestEndDate() != null)
				{
					printInfo.setEndDate( resultInfo.getInterestEndDate() );
				}
				//利息计算天数
				if (resultInfo.getInterestDays() > 0)
				{
					//临时使用存储活期利息计算天数
					printInfo.setNoticeDay( resultInfo.getInterestDays() );
				}
				//计息开始日期(起息日)
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate( resultInfo.getInterestStartDate() );
				}
				//执行利率
				if (resultInfo.getRate() > 0)
				{
					printInfo.setRate( resultInfo.getRate() );
				}
				//利息
				if (resultInfo.getInterest() > 0)
				{
					printInfo.setCurrentInterest( resultInfo.getInterest() );
				}
				//利息（积数用到）//modify by xwhe 2008-10-26 
				if (resultInfo.getPecisionInterest() > 0)
				{
					printInfo.setPecisionInterestAmount(resultInfo.getPecisionInterest());
				}
				//收息账户
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID( resultInfo.getReceiveInterestAccountID() );
				}
				if(resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				//备注
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract( resultInfo.getAbstract() );
				}
				
				/*****  活期需要显示协定利息  *****/
				//协定利息本金
				if (resultInfo.getNegotiateBalance() > 0)
				{
					printInfo.setAccordInterestAmount( resultInfo.getNegotiateBalance() );
				}
				//协定利息利率
				if (resultInfo.getNegotiateRate() > 0)
				{
					printInfo.setAccordInterestRate( resultInfo.getNegotiateRate() );
				}
				//协定利息
				if (resultInfo.getNegotiateInterest() > 0)
				{
					printInfo.setAccordInterest( resultInfo.getNegotiateInterest() );
				}
				
				/******  自营贷款  ******/
				//账户类型
				if (resultInfo.getAccountTypeID() > 0)
				{
					printInfo.setAccountTypeID( resultInfo.getAccountTypeID() );
				}
				//正常利息
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
				//子账户
				if (resultInfo.getSubAccountID() > 0)
				{
					Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
					SubAccountLoanInfo subAccInfo = null;
					subAccInfo = subAccountDAO.findByID(resultInfo.getSubAccountID()).getSubAccountLoanInfo();
					printInfo.setLoanNoteID(subAccInfo.getLoanNoteID());
				}
               //委托单位id modify by xwhe 2008-07-15
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getReceiveInterestAccountID()));
				}
				//委贷利息收息账户 add by xwhe 2008-07-31
				if(resultInfo.getPayInterestAccountID()> 0)
				{
					printInfo.setPayInterestAccountID( resultInfo.getPayInterestAccountID() );
				}
				//结息日  add by xwhe 2008-08-04
				if(resultInfo.getInterestSettlementDate()!=null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestSettlementDate());
				}
				
				//modify by xwhe 2008-12-25
				if(resultInfo.getTransactionTypeID()== SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT)
				{
				//活期结息(协定)分段计息
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
                             //modify by xwhe 2008-11-20 暂时替换计提用
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
	                             //modify by xwhe 2008-11-20 暂时替换计提用
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
		
		//minzhao代码太乱了，按照交易类型分类取数
		//如果业务类型为信贷资产转让
		if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERREPAY
				||printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.AGENTTRANSFERREPAY 
				||printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERPAY )
		{
			//客户名称
			usedFields.put("ClientName", Env.getClientName());
			
			//执行日
			usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //显示YYYY年MM月DD日
			
			//起息日
			usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //显示YYYY年MM月DD日
			
			//到期日
			usedFields.put("OperationEndDate", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ));
			
			//当前时间
			usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //显示YYYY年MM月DD日
			
			//交易编号
			usedFields.put("TransNo", printOperationInfo.getTransNo());
			
			//办事处名称
			usedFields.put("OfficeName",NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
			
			//信贷资产转让业务开始取付款方
			//信贷资产转让收款付款方为外部账户，过滤此业务
			if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERREPAY)
			{
				CounterparBankDao bankdao=new CounterparBankDao();
				CounterpartBankInfo bankinfo =new CounterpartBankInfo();
				bankinfo=(CounterpartBankInfo)bankdao.findByID(printOperationInfo.getPayBankID(), CounterpartBankInfo.class);
				
				//付款人全称			
				usedFields.put("PayAccountName",bankinfo.getCounterparname());
				//付款方账号
				usedFields.put("PayAccountNo", bankinfo.getCounterpartbankno());
				//付款方开户行名称
				usedFields.put("PayBankName", bankinfo.getCounterpartbankname() );
			}
			//信贷资产转让代收收款付款方为内部账户
			else if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.AGENTTRANSFERREPAY )
			{
				
				//付款人全称
				usedFields.put("PayAccountName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));
				
				//付款方账号
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));
				
				//付款方开户行名称
				usedFields.put("PayBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
			}
			//信贷资产转让付款付款方总账或银行账户
			else if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERPAY )
			{
				if(printOperationInfo.getPayBankID()>0)//开户行
				{
					//付款人全称
					usedFields.put("PayAccountName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
					
					//付款方账号
					usedFields.put("PayAccountNo", NameRef.getBankAccountCodeByBankID( printOperationInfo.getPayBankID() ));
					
					//付款方开户行名称
					usedFields.put("PayBankName", NameRef.getBankNameByID( printOperationInfo.getPayBankID() ));
				}
				else//总账
				{
					//付款人全称
					usedFields.put("PayAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
					
					//付款方账号
					usedFields.put("PayAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getPayGL() ));
				}
			}
			
			//信贷资产转让开始取收款方
			//信贷资产转让收款收款方为总账或开行，过滤此业务
			if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERREPAY )
			{
				if(printOperationInfo.getReceiveBankID()>0)//开户行
				{
					//付款人全称
					usedFields.put("ReceiveAccountName", NameRef.getOfficeNameByID( printOperationInfo.getReceiveBankID() ));
					
					//付款方账号
					usedFields.put("ReceiveAccountNo", NameRef.getBankAccountCodeByBankID( printOperationInfo.getReceiveBankID() ));
					
					//付款方开户行名称
					usedFields.put("ReceiveBankName", NameRef.getBankNameByID( printOperationInfo.getReceiveBankID() ));
				}
				else//总账
				{
					//付款人全称
					usedFields.put("ReceiveAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
					
					//付款方账号
					usedFields.put("ReceiveAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getReceiveGL() ));
				}
			}
			//信贷资产转让代收收款收款方不显示收款方
			else if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.AGENTTRANSFERREPAY )
			{
				
				//付款人全称
				usedFields.put("ReceiveAccountName", "");
				
				//付款方账号
				usedFields.put("ReceiveAccountNo", "");
				
				//付款方开户行名称
				usedFields.put("ReceiveBankName", "");
			}
			//信贷资产转让付款收款方为外部账户
			else if(printOperationInfo.getTransactionTypeID() == SETTConstant.TransactionType.TRANSFERPAY )
			{

				CounterparBankDao bankdao=new CounterparBankDao();
				CounterpartBankInfo bankinfo =new CounterpartBankInfo();
				bankinfo=(CounterpartBankInfo)bankdao.findByID(printOperationInfo.getReceiveBankID(), CounterpartBankInfo.class);
				
				String province=bankinfo.getProvince();
				String city=bankinfo.getCity();
				//收款人地址
				usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(province+"省"+city+"市"));
				
				//付款人全称			
				usedFields.put("ReceiveAccountName",bankinfo.getCounterparname());
				//付款方账号
				usedFields.put("ReceiveAccountNo", bankinfo.getCounterpartbankno());
				//付款方开户行名称
				usedFields.put("ReceiveBankName", bankinfo.getCounterpartbankname() );
			}
			if (printOperationInfo.getAmount() != 0.0)
			{
				//本金金额小写
				usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() ));
				
				//本金金额大写
				usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
			}
			else
			{
				//本金金额小写
				usedFields.put("Amount", "0.00");
				
				String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
				
				//本金金额大写
				usedFields.put("ChineseAmount", strChineseAmount);
			}
			
			if (printOperationInfo.getInputUserID() > 0)
			{
				//录入人
				usedFields.put("InputUserName", NameRef.getUserNameByID( printOperationInfo.getInputUserID()) );
			}
			else
			{
				//录入人
				usedFields.put("InputUserName", "机制");
			}

			if (printOperationInfo.getCheckUserID() > 0)
			{
				//复核人
				usedFields.put("CheckUserName", NameRef.getUserNameByID( printOperationInfo.getCheckUserID()) );
			}
			else
			{
				if (printOperationInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK)
				{
					//复核人
					usedFields.put("CheckUserName", "机核");
				}
			}
			usedFields.put("Abstract", printOperationInfo.getAbstract());
			usedFields.put("CommissionFee", printOperationInfo.getCommission()+"");
			usedFields.put("ChineseCommissionFee", this.getChineseFormatAmount(printOperationInfo.getCommission(), 1));
			
			
		}
		//如果为其他业务类型
		else
		{
		//客户名称
		usedFields.put("ClientName", Env.getClientName());
		
		//执行日
		usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //显示YYYY年MM月DD日
		
		//起息日
		usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //显示YYYY年MM月DD日
		
		//到期日
		usedFields.put("OperationEndDate", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ));
		
		//当前时间
		usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //显示YYYY年MM月DD日
		
		//交易编号
		usedFields.put("TransNo", printOperationInfo.getTransNo());
		
		//付款方账户
		if(printOperationInfo.getPayAccountID() > 0)
		{
			//付款人全称
			usedFields.put("PayAccountName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));
			
			//付款方账号
			usedFields.put("PayAccountNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));
			
			//付款方开户行名称
			usedFields.put("PayBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
			
			//"银行付款落地处理"业务的付款方开户行必须从开户行设置中取得
			if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKPAY_NOTONLINE 
			 //银行付款
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
		
		//外部银行账户 "银行收款"业务的付款方为外部账户,过滤此业务
		else if( printOperationInfo.getPayBankID() > 0 && printOperationInfo.getTransTypeID() != SETTConstant.TransactionType.BANKRECEIVE )
		{
			Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
			BranchInfo branchInfo = new BranchInfo();
			branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());
			
			//付款人全称			
			if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION )
			{
				usedFields.put("PayAccountName", "国电财务有限公司");
			}
			else
			{
				usedFields.put("PayAccountName", DataFormat.formatString( branchInfo.getPrintName() ));
			}
			
			if( printOperationInfo.getTransTypeID() != SETTConstant.TransactionType.OPENFIXEDDEPOSIT && printOperationInfo.getTransTypeID() != SETTConstant.TransactionType.OPENNOTIFYACCOUNT )
			{
				//付款方账号
				usedFields.put("PayAccountNo", DataFormat.formatString( branchInfo.getBankAccountCode() ));
				
				//付款方开户行名称
				usedFields.put("PayBankName", DataFormat.formatString( branchInfo.getBranchName() ));
			}
			
			//付款方科目代码
			usedFields.put("PaySubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
		}
		else if ( printOperationInfo.getPayExtClientName() != null 
				&& !printOperationInfo.getPayExtClientName().equals("") )
		{
			//判断  一付多收(多借多贷)  另做处理
			if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.ONETOMULTI )
			{
				//付款人全称
				usedFields.put("PayAccountName", DataFormat.formatString( printOperationInfo.getPayExtClientName() ));
				
				//付款方账号
				usedFields.put("PayAccountNo", DataFormat.formatString( printOperationInfo.getPayExtAccountNo() ));
				
				//付款方开户行名称
				usedFields.put("PayBankName", DataFormat.formatString( printOperationInfo.getPayExtRemitInBank() ));
			}
		}
		else
		{
			if(printOperationInfo.getExtClientName() != null && printOperationInfo.getExtClientName().trim().length() > 0)
			{
				//付款人全称
				usedFields.put("PayAccountName", printOperationInfo.getExtClientName());
				
				//付款方账号
				usedFields.put("PayAccountNo", printOperationInfo.getExtAccountNo());
				
				//付款方开户行名称
				usedFields.put("PayBankName", printOperationInfo.getExtRemitInBank());
			}
			//总账	
			else
			{
				//付款人全称
				usedFields.put("PayAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
				
				//付款方账号
				usedFields.put("PayAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getPayGL() ));
				
				//付款方科目代码
				usedFields.put("PaySubjectCode", NameRef.getGLKNoByID( printOperationInfo.getPayGL() ));
								
			}
		}
		
		if (printOperationInfo.getReceiveAccountID() > 0)
		{
			//收款人全称
			usedFields.put("ReceiveAccountName", NameRef.getAccountNameByID( printOperationInfo.getReceiveAccountID() ));
			
			//收款方账号
			usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
			
			//收款方开户行名称
			usedFields.put("ReceiveBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
			
			//"银行收款"业务的收款方开户行必须从开户行设置中取得
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

			//电子回单柜打印财务公司所在地点global.evoucher.printclientaddress(global.xml)
			String clientAddress = "";
			clientAddress = Config.getProperty(ConfigConstant.GLOBAL_EVOUCHER_PRINTCLIENTADDRESS);
			//收款人汇入地址
			usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(clientAddress) );
		}
		//2007-8-31 "特殊交易"业务收款方有可能是银行账户
		else if ( (printOperationInfo.getReceiveBankID() > 0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION)
			   || (printOperationInfo.getReceiveBankID() > 0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRANSFEE)
			   || (printOperationInfo.getReceiveBankID() > 0 && printOperationInfo.getTransTypeID() >1000)
			   )
		{
			Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
			BranchInfo branchInfo = new BranchInfo();
			branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());
			
			//收款人全称
			if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION )
			{
				usedFields.put("ReceiveAccountName", "国电财务有限公司");
			}
			else
			{
				usedFields.put("ReceiveAccountName", DataFormat.formatString( branchInfo.getPrintName() ));
			}
			
			//收款方账号
			usedFields.put("ReceiveAccountNo", DataFormat.formatString( branchInfo.getBankAccountCode() ));
			
			//收款方开户行名称
			usedFields.put("ReceiveBankName", DataFormat.formatString( branchInfo.getBranchName() ));
			
			//收款方科目代码
			usedFields.put("ReceiveSubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
			
			String strTemp = "";
			if (branchInfo.getBranchProvince() != null && !branchInfo.getBranchProvince().equals("") ){
				if (branchInfo.getBranchProvince().indexOf("省") < 0)
				{
					strTemp = branchInfo.getBranchProvince() + "省";
				}
				else
				{
					strTemp = branchInfo.getBranchProvince();
				}
			}
			
			if (branchInfo.getBranchCity() != null && !branchInfo.getBranchCity().equals("")){
				if (branchInfo.getBranchCity().indexOf("市") < 0)
				{
					strTemp += branchInfo.getBranchCity() + "市";
				}
				else
				{
					strTemp += branchInfo.getBranchCity();
				}
			}	
			
			//收款人汇入地址
			usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(strTemp));
		}		
		else if ( printOperationInfo.getReceiveExtClientName() != null 
				&& !printOperationInfo.getReceiveExtClientName().equals("") )
		{
			//判断  一付多收(多借多贷)  另做处理
			if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.ONETOMULTI )
			{
				//收款人全称
				usedFields.put("ReceiveAccountName", DataFormat.formatString( printOperationInfo.getReceiveExtClientName() ));
				
				//收款方账号
				usedFields.put("ReceiveAccountNo", DataFormat.formatString( printOperationInfo.getReceiveExtAccountNo() ));
				
				//收款方开户行名称
				usedFields.put("ReceiveBankName", DataFormat.formatString( printOperationInfo.getReceiveExtRemitInBank() ));
			}
		}
		
		//开户行到总账业务类型，国电要求显示银行账户信息
		else if (printOperationInfo.getReceiveGL()> 0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION)
		{
            //收款方账号
			usedFields.put("ReceiveAccountNo", printOperationInfo.getReceiveExtAccountNo());
			
			//收款方开户行名称
			usedFields.put("ReceiveBankName", printOperationInfo.getExtRemitInBank());
			
		}
		else
		{
			if ( ( printOperationInfo.getExtClientName() != null
			  && printOperationInfo.getExtClientName().trim().length() > 0 )
			  || printOperationInfo.getReceiveBankID() > 0 )
			{
				//收款人全称
				usedFields.put("ReceiveAccountName", printOperationInfo.getExtClientName());
				
				//收款方账号
				usedFields.put("ReceiveAccountNo", printOperationInfo.getExtAccountNo());
				
				//收款方开户行名称
				usedFields.put("ReceiveBankName", printOperationInfo.getExtRemitInBank());
				
				//收款人汇入地点
				String strTemp = "";
				if ( printOperationInfo.getExtRemitInProvince() != null && !printOperationInfo.getExtRemitInProvince().equals("") )
				{
					if (printOperationInfo.getExtRemitInProvince().indexOf("省") < 0)
					{
						strTemp = printOperationInfo.getExtRemitInProvince() + "省";
					}
					else
					{
						strTemp = printOperationInfo.getExtRemitInProvince();
					}
				}
				
				if ( printOperationInfo.getExtRemitInCity() != null && !printOperationInfo.getExtRemitInCity().equals("") )
				{
					if (printOperationInfo.getExtRemitInCity().indexOf("市") < 0)
					{
						strTemp += printOperationInfo.getExtRemitInCity() + "市";
					}
					else
					{
						strTemp += printOperationInfo.getExtRemitInCity();
					}
				}
				//收款人汇入地址
				usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(strTemp));
			}
			//交易费用
			else if( printOperationInfo.getFeeTypeID() > 0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRANSFEE )
			{
				TransactionFeeTypeBiz interestSetting = new TransactionFeeTypeBiz();
				TransFeeTypeSetInfo transFeeTypeSetInfo = interestSetting.findTransactionFeeTypeByID(printOperationInfo.getFeeTypeID());
				
				//收款方科目代码
				if(transFeeTypeSetInfo.getSubjectCode() != null)
				{					
					usedFields.put("ReceiveAccountName", NameRef.getSubjectNameByCode(printOperationInfo.getOfficeID(), transFeeTypeSetInfo.getSubjectCode()));
					usedFields.put("ReceiveAccountNo", transFeeTypeSetInfo.getSubjectCode());
				}
			}
			//总账
			else 
			{
				//收款人全称
				usedFields.put("ReceiveAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				
				//收款方账号
				usedFields.put("ReceiveAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getReceiveGL() ));
				
				//收款方科目代码
				usedFields.put("ReceiveSubjectCode", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL() ));
								
			}
		}
		
		if (printOperationInfo.getAmount() != 0.0)
		{
			//本金金额小写
			usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() ));
			
			//本金金额大写
			usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
		}
		else
		{
			//本金金额小写
			usedFields.put("Amount", "0.00");
			
			String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
			
			//本金金额大写
			usedFields.put("ChineseAmount", strChineseAmount);
		}
		
		if (printOperationInfo.getInputUserID() > 0)
		{
			//录入人
			usedFields.put("InputUserName", NameRef.getUserNameByID( printOperationInfo.getInputUserID()) );
		}
		else
		{
			//录入人
			usedFields.put("InputUserName", "机制");
		}

		if (printOperationInfo.getCheckUserID() > 0)
		{
			//复核人
			usedFields.put("CheckUserName", NameRef.getUserNameByID( printOperationInfo.getCheckUserID()) );
		}
		else
		{
			if (printOperationInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK)
			{
				//复核人
				usedFields.put("CheckUserName", "机核");
			}
		}
		//add by xwhe 2008-08-07 
		if(printOperationInfo.getExamUserName()!=null && !printOperationInfo.getExamUserName().equals(""))
		{
			usedFields.put("ExamUser", printOperationInfo.getExamUserName());
		}
		
		if (printOperationInfo.getOfficeID() > 0)
		{
			//办事处名称
			usedFields.put("OfficeName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
		}
		
		if (printOperationInfo.getCurrencyID() > 0)
		{
			//币种
			usedFields.put("CurrencyName", Constant.CurrencyType.getName( printOperationInfo.getCurrencyID() ));
		}
		
		//摘要
		if(printOperationInfo.getInterestStartDate() != null)
		{
			String strAbstract = "";
			//定期续存-到期续存
			if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				strAbstract = "起息日：" + DataFormat.getChineseDateString( printOperationInfo.getStartDate() );
			}
			//活期存款计提应付利息,活期存款结息,定期存款计提,通知存款计提  add by xwhe 2008-08-04
			if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST ||
			   printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT||
			   printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST|| 
			   printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST
			   )
			{
				strAbstract = "起息日：" + DataFormat.getChineseDateString( printOperationInfo.getInterestClearDate());
			}
			else
			{   
				if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.SPECIALOPERATION 
						&& printOperationInfo.getOperationType()==1003)
				{
					
				}
				else
				{
				strAbstract = "起息日：" + DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() );
				}
			}
				
			if(printOperationInfo.getAbstract() != null && !printOperationInfo.getAbstract().equals(""))
			{
				strAbstract += "；";
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
		
		/*****特种转账贷方传票*****/
		//定期转活期（定期支取）  通知存款支取
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER 
		 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW )
		{
			//如果是定期支取或者通知存款支取，金额和收款方按照活期账户收款方进行设置
			//收款方
			if (printOperationInfo.getReceiveInterestAccountID() > 0)
			{			
				usedFields.put("InterestextAccountNo",  "上述利息,已照付你单位("+NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID())+")号账户");				
			}			
			//收息外部账户 //modify by xwhe 2008-08-15 非定期续存的收息方账户
			if(printOperationInfo.getInterestextaccountno() != null && !printOperationInfo.getInterestextaccountno().equals(""))
			{
				usedFields.put("InterestextAccountNo", "上述利息,已照付你单位("+DataFormat.formatString( printOperationInfo.getInterestextaccountno())+")号账户");
			}			
			//定期支取,部分支取
			if(printOperationInfo.getAccountAmount() > printOperationInfo.getDrawAmount() && printOperationInfo.getInterestStartDate().compareTo(printOperationInfo.getEndDate()) < 0)
	        {
                TransFixedOpenInfo transFixedOpenInfo = new TransFixedOpenInfo();
                Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
                transFixedOpenInfo = dao.findByOldDepositNo(printOperationInfo.getFixedDepositNo());
                usedFields.put("HNewDepositNo", DataFormat.formatString(transFixedOpenInfo.getDepositNo()));
                
                usedFields.put("HNewAmount",  this.getFormatAmount( transFixedOpenInfo.getAmount() ));
                
                usedFields.put("HNewChineseAmount", this.getChineseFormatAmount( transFixedOpenInfo.getAmount(), printOperationInfo.getCurrencyID() ));
                
                //新存单开始日期
                usedFields.put("HNewStartDate", DataFormat.getChineseDateString(transFixedOpenInfo.getStartDate()));
                //新存单结束日期
                usedFields.put("HNewEndDate", DataFormat.getChineseDateString(transFixedOpenInfo.getEndDate()));
                //新存单起息日
                usedFields.put("HNewInterestStartDate", DataFormat.getChineseDateString(transFixedOpenInfo.getInterestStartDate()));
                
                usedFields.put("HNewDepositTerm", DataFormat.formatString(String.valueOf(transFixedOpenInfo.getDepositTerm()) + "个月"));
                usedFields.put("HNewRate", DataFormat.formatRate(transFixedOpenInfo.getRate(), 6) + "%");
                usedFields.put("HNewAccountName", NameRef.getAccountNameByID(transFixedOpenInfo.getAccountID()));
                usedFields.put("HNewAccountNo", NameRef.getAccountNoByID(transFixedOpenInfo.getAccountID()));
                usedFields.put("HNewTransNo", DataFormat.formatString(transFixedOpenInfo.getTransNo()));
                if(transFixedOpenInfo.getInterestStartDate() != null)
                {
                	//modify by xwhe 2008-07-23 修改起息日为开始日               
                	 String strAbstract = "起息日：" + DataFormat.getChineseDateString(transFixedOpenInfo.getStartDate());
                    if(transFixedOpenInfo.getAbstract() != null && !transFixedOpenInfo.getAbstract().equals(""))
                    {
                        strAbstract = strAbstract + "；";
                        strAbstract = strAbstract + transFixedOpenInfo.getAbstract();
                    }
                    usedFields.put("HNewAbstract", strAbstract);
                }
                else
                {
                    usedFields.put("HNewAbstract", transFixedOpenInfo.getAbstract());
                }
	        }
			
			//通知存款部分支取
			if(printOperationInfo.getAccountAmount() > printOperationInfo.getDrawAmount() && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
                usedFields.put("HNewDepositNo", DataFormat.formatString(printOperationInfo.getFixedDepositNo()));
                
                usedFields.put("HNewAmount",  this.getFormatAmount( printOperationInfo.getDepositBalance() ));
                
                usedFields.put("HNewChineseAmount", this.getChineseFormatAmount( printOperationInfo.getDepositBalance(), printOperationInfo.getCurrencyID() ));
                
                //新存单开始日期
                usedFields.put("HNewStartDate", DataFormat.getChineseDateString(printOperationInfo.getStartDate()));
                
                //新存单起息日
                usedFields.put("HNewInterestStartDate", DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate()));
                
                usedFields.put("HNewDepositTerm", DataFormat.formatString(String.valueOf(printOperationInfo.getNoticeDay() - 10000) + "天"));
                
                usedFields.put("HNewRate", DataFormat.formatRate(printOperationInfo.getRate(), 6) + "%");
                
                usedFields.put("HNewAccountName", NameRef.getAccountNameByID(printOperationInfo.getAccountID()));
                
                usedFields.put("HNewAccountNo", NameRef.getAccountNoByID(printOperationInfo.getAccountID()));
                
                usedFields.put("HNewTransNo", DataFormat.formatString(printOperationInfo.getTransNo()));
                if(printOperationInfo.getInterestStartDate() != null)
                {
                    String strAbstract = "起息日：" + DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());
                    if(printOperationInfo.getAbstract() != null && !printOperationInfo.getAbstract().equals(""))
                    {
                        strAbstract = strAbstract + "；";
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
			//定期续存
			if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				//这里的账户ID是收息账户号(2007-10-19 收息账户号应该另起一个名称存储)
				if (printOperationInfo.getReceiveInterestAccountID() > 0)
				{					
					//modify by xwhe 2008-08-26
					if(printOperationInfo.getIsCapitalAndInterestTransfer()!=1)
					{
						usedFields.put("InterestextAccountNo", "上述利息,已照付你单位("+NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID())+")号账户");	
					}					
				}
				else if (printOperationInfo.getExtAccountNo() != null && !printOperationInfo.getExtAccountNo().equals(""))
				{
					//收款方账号(收息外部账户号)
					usedFields.put("InterestextAccountNo", DataFormat.formatString(printOperationInfo.getExtAccountNo()) );
				}
				
				//是否本利续存(是:续存本金金额 = 利息 + 本金金额)
				if(printOperationInfo.getIsCapitalAndInterestTransfer() == 1)
				{
					if(printOperationInfo.getRealInterest() != 0.0 || printOperationInfo.getAmount() != 0.0)
					{
						//本金金额小写
						usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getRealInterest() + printOperationInfo.getAmount() ));
						
						//本金金额大写
						usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getRealInterest() + printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
						
						printOperationInfo.setRealInterest(0.0);
					}
					else
					{
						//本金金额小写
						usedFields.put("Amount", "0.00");
						
						String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
						
						//本金金额大写
						usedFields.put("ChineseAmount", strChineseAmount);
					}
				}
				else
				{
					if(printOperationInfo.getAmount() != 0.0)
					{
						//本金金额小写
						usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() ));
						
						//本金金额大写
						usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
					}
					else
					{
						//本金金额小写
						usedFields.put("Amount", "0.00");
						
						String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
						
						//本金金额大写
						usedFields.put("ChineseAmount", strChineseAmount);
					}
				}
			}
			//其他业务
			else
			{
				//委托贷款收回
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
				}				
				//利息费用支付-活期结息
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT)
				{
					//收息账户号
					if (printOperationInfo.getReceiveInterestAccountID() > 0)
					{
						//收款方账号(收息账户号)
						usedFields.put("InterestextAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID()));
					}
				}
				
				//定期存款计提应付利息（含冲销）, 通知存款计提应付利息（含冲销）, 活期存款计提应付利息（含冲销）
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
		                	//利息记账到账户的计提科目上
		                    usedFields.put("InterestextAccountNo", DataFormat.formatString( subjectDefinitionInfo.getSsubjectName()+"-"+ subjectDefinitionInfo.getSsubjectCode() ));
		                }
					}
				}
					
				if(printOperationInfo.getAmount() != 0.0)
				{
					//本金金额小写
					usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() ));
					
					//本金金额大写
					usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
				}
				else
				{
					//本金金额小写
					usedFields.put("Amount", "0.00");
					
					String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
					
					//本金金额大写
					usedFields.put("ChineseAmount", strChineseAmount);
				}
			}
		}
				
		//担保收款(专门用于手续费打印,收款方为记账科目信息)
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
		
		//网银录入人  网银复核人  网银签认人
        if ( printOperationInfo.getTransNo() != null && !printOperationInfo.getTransNo().equals("") )
        {
			ShowWithDrawInfo info = new ShowWithDrawInfo();
			info.setTransNo( printOperationInfo.getTransNo() );
			info = PrintVoucher.getOBInfoByTransNo( info );
			
			//网银录入人
			usedFields.put("OBInputUserName", info.getOBInputUserName());
			
			//网银复核人
			usedFields.put("OBCheckUserName", info.getOBCheckUserName());
			
			//网银签认人
			usedFields.put("OBSignUserName", info.getOBSignUserName());
        }
		
		//显示特种转账借方传票
		//贴现收回
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.DISCOUNTRECEIVE)
		{
			if (printOperationInfo.getAmount() != 0.0 || printOperationInfo.getOverDueAmount() != 0.0)
			{
				//本金金额小写
				usedFields.put("Amount", this.getFormatAmount( printOperationInfo.getAmount() + printOperationInfo.getOverDueAmount() ));
				
				//本金金额大写
				usedFields.put("ChineseAmount", this.getChineseFormatAmount( printOperationInfo.getAmount() + printOperationInfo.getOverDueAmount(), printOperationInfo.getCurrencyID() ));
			}
			else
			{
				//本金金额小写
				usedFields.put("Amount", "0.00");
				
				String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
				
				//本金金额大写
				usedFields.put("ChineseAmount", strChineseAmount);
			}
		}
		
		//利率
		usedFields.put("Rate", DataFormat.formatRate( printOperationInfo.getRate() ));
		
		//定期续存
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
		{
			//新存单利率
		    usedFields.put("NewRate", DataFormat.formatRate( printOperationInfo.getNewRate(), 6 ));
		    
		    //新存单开始日期
		    usedFields.put("NewStartDate", DataFormat.getChineseDateString( printOperationInfo.getNewStartDate() ));
		    
		    //新存单结束日期
		    usedFields.put("NewEndDate", DataFormat.getChineseDateString( printOperationInfo.getNewEndDate() ));
		}
		
		//定期期限
		if(printOperationInfo.getFixedDepositTerm()<0)
		{
			usedFields.put("FixedDepositTerm", "");
		}
		else
		{
			usedFields.put("FixedDepositTerm", String.valueOf( printOperationInfo.getFixedDepositTerm() ));
		}
		
		//换开定期存单号
		if(printOperationInfo.getDepositBillNO() != null && !printOperationInfo.getDepositBillNO().equals(""))
		{
			usedFields.put("DepositBillNO", printOperationInfo.getDepositBillNO());
		}
		
		//业务名称
		if(printOperationInfo.getTransTypeID() != -1)
		{
			usedFields.put("TransactionTypeName", SETTConstant.TransactionType.getName( printOperationInfo.getTransTypeID() ));
		}
		
		//定期通知存单号(通知存款开户证实书存单号)
		if(printOperationInfo.getFixedDepositNo() != null && !printOperationInfo.getFixedDepositNo().equals(""))
		{
			usedFields.put("FixedDepositNo", printOperationInfo.getFixedDepositNo());
		}
		
		//"换开定期存单"需要旧存单号
		if(printOperationInfo.getFixedDepositNo() != null && !printOperationInfo.getFixedDepositNo().equals(""))
		{
			usedFields.put("OldFixedDepositNo", printOperationInfo.getFixedDepositNo());
		}
		
		//换开定期存单号(如果存在新开的存单号,则显示新开的存单号)
		if(printOperationInfo.getDepositBillNO() != null && !printOperationInfo.getDepositBillNO().equals(""))
		{
			usedFields.put("FixedDepositNo", printOperationInfo.getDepositBillNO());
		}
		
		//通知天数
		String sInsert = "";
		if (printOperationInfo.getNoticeDay() > 10000)
		{
			sInsert = printOperationInfo.getNoticeDay() - 10000 + "天";
		}
		else
		{
			sInsert = printOperationInfo.getNoticeDay() + "个月";
		}
		usedFields.put("NoticeType", sInsert);
		
		//定期品种
		if(printOperationInfo.getFixedDepositTerm() > 0)
		{
			usedFields.put("FixedDepositTerm", String.valueOf( printOperationInfo.getFixedDepositTerm() ).trim() + "个月" );
		}
		
		//定期通知利息支出
		if(printOperationInfo.getPayableInterest() != 0.0)
		{
			usedFields.put("PayableInterest", String.valueOf( new java.math.BigDecimal( printOperationInfo.getPayableInterest() ) ));
		}
		
		//定期通知利息合计
		//利息支出 + 活期利息 + 其它利息 + 本金金额
		usedFields.put("TotalInterest", DataFormat.formatListAmount(printOperationInfo.getPayableInterest() +printOperationInfo.getCurrentInterest() + printOperationInfo.getOtherInterest() + printOperationInfo.getAmount(), 2));
		
		/************** 自营贷款发放收回凭证打印 ******************/
		/************** 委托贷款发放收回凭证打印 ******************/
		
		//放款通知单号
		usedFields.put("PayFormNo", NameRef.getPayFormNoByID( printOperationInfo.getLoanNoteID() ));
		
		//合同号
		usedFields.put("ContractNo", NameRef.getContractNoByID( printOperationInfo.getContractID() ));
		
		//自营贷款收回  委托贷款收回
		if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE 
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANGRANT
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANGRANT
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANGRANT
				|| printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
		{
			//实际支付贷款利息
			if(printOperationInfo.getRealInterest() == 0.0)
			{
				usedFields.put("LoanRealInterest", "0.00");
			}
			else
			{
				usedFields.put("LoanRealInterest", DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ));
			}
			
			//实际支付贷款复利
			if(printOperationInfo.getRealCompoundInterest() == 0.0)
			{
				usedFields.put("LoanRealCompoundInterest", "0.00");
			}
			else
			{
				usedFields.put("LoanRealCompoundInterest", DataFormat.formatListAmount( printOperationInfo.getRealCompoundInterest(), 2 ));
			}
			
			//实际支付贷款逾期罚息
			if(printOperationInfo.getRealOverDueInterest() == 0.0)
			{
				usedFields.put("LoanRealOverDueInterest", "0.00");
			}
			else
			{
				usedFields.put("LoanRealOverDueInterest", DataFormat.formatListAmount( printOperationInfo.getRealOverDueInterest(), 2 ));
			}
			
			//放款通知单
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
				
				//手续费率(委托贷款发放凭证打印)
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANGRANT)
				{
					usedFields.put("ChargeRate", DataFormat.formatRate( lpfdinfo.getPoundage(), 6 ));
				}
				
				//合同利率
				usedFields.put("ContractRate", DataFormat.formatRate( lpfdinfo.getInterestRate(), 6 ));
				
				//委托单位
				usedFields.put("ConsignUnit", lpfdinfo.getClientName());
				
				//利息费用支付委托贷款的委托单位 modify by xwhe 2008-07-15
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
				{
				usedFields.put("ConsignClientName", NameRef.getClientNameByID(printOperationInfo.getConsignClientID()));
				}
				
				//贷款种类
				usedFields.put("LoanTypeName", LOANConstant.LoanType.getName( lpfdinfo.getLoanType() ));
				
				//贷款子类型
				if(lpfdinfo.getSubTypeID() > 0)
				{
					usedFields.put("SubLoanTypeName", LOANConstant.SubLoanType.getName( lpfdinfo.getSubTypeID() ));
				}
				
				//贷款期限
				usedFields.put("LoanInterval", DataFormat.getChineseDateString( lpfdinfo.getStart() ) + " 至 " + DataFormat.getChineseDateString( lpfdinfo.getEnd() ));
				
				//放款单期限 2008-09-08 add by xwhe 
				if(lpfdinfo.getDTSTART()!=null && lpfdinfo.getDTEND()!=null)
				{
                   //放款单期限
					usedFields.put("LoanNoteInterval", DataFormat.getChineseDateString( lpfdinfo.getDTSTART() ) + " 至 " + DataFormat.getChineseDateString( lpfdinfo.getDTEND() ));	
				}
				//到期日
				usedFields.put("OperationEndDate", DataFormat.getChineseDateString( lpfdinfo.getEnd() ));
				//modify by xwhe 2008-07-21 委贷收回取值 国电特殊要求
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
                    if(printOperationInfo.getConsignAccountID()>0)
                    {
				    //付款人全称
				    usedFields.put("ConsignAccountName", NameRef.getAccountNameByID( printOperationInfo.getConsignAccountID()));
				    //付款方账号
					usedFields.put("ConsignAccountNo", NameRef.getAccountNoByID( printOperationInfo.getConsignAccountID() ));
                    }
                    if(printOperationInfo.getConsignDepositAccountID()>0)
                    {
                    //收款方全称
    				usedFields.put("ConsignDepositAccountName", NameRef.getAccountNameByID( printOperationInfo.getConsignDepositAccountID()));
                    //收款方账号
    				usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID( printOperationInfo.getConsignDepositAccountID()));
                    }
				}
                //利息费用支付委托贷款活期账户 modify by xwhe 2008-07-28
				if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
				{
				usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));
				}
				//贷款利息
				if (printOperationInfo.getRealInterest() > 0)
				{
					//正常利息开始日期					
					usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getLatestInterestClearDate() ) );
					
					//正常利息结束日期
					usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getInterestClearDate() ) );
					
					//正常利息本金
					usedFields.put("NormalInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getAmount(), 2 ) );
					
					//正常利率
					usedFields.put("NormalInterestRate", DataFormat.formatRate(lpfdinfo.getInterestRate(), 6));
					
					//正常利息
					usedFields.put("NormalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ) );
					
					//正常利息天数	
					usedFields.put("NormalInterestDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(printOperationInfo.getLatestInterestClearDate(), printOperationInfo.getInterestClearDate(), 1)) + "") );
				}
			
				//复利
				//起始日期--放款日期后第一个复利设置日期，利率等于正常利息的利率
				if (printOperationInfo.getRealCompoundInterest() > 0)
				{
					//复利利息起息日期
					if (printOperationInfo.getCompoundInterestStart() != null)
					{
						usedFields.put("CompoundInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getCompoundInterestStart() ) );
					}
					
					//复利利息终息日期
					if (printOperationInfo.getCompoundInterestEnd() != null)
					{
						usedFields.put("CompoundInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getCompoundInterestEnd() ) );
					}
					else
					{
						usedFields.put("CompoundInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ) );
					}
					
					//复利利息天数
					if (printOperationInfo.getCompoundInterestStart() != null && printOperationInfo.getCompoundInterestEnd() != null)
					{
						usedFields.put("CompoundInterestDay", DataFormat.formatString( ( PrintVoucher.getIntervalDays( printOperationInfo.getCompoundInterestStart(), printOperationInfo.getCompoundInterestEnd(), 1)) + "" ));
					}
					else if (printOperationInfo.getCompoundInterestStart() != null)
					{
						usedFields.put("CompoundInterestDay", DataFormat.formatString( ( PrintVoucher.getIntervalDays( printOperationInfo.getCompoundInterestStart(), printOperationInfo.getExecuteDate(), 1)) + "" ));
					}
					
					//复利本金显示为空(修改 2007年11月12日 去表SETT_TRANSREPAYMENTLOAN的MCOMPOUNDAMOUNT利息费用支付)
					usedFields.put("CompoundInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(printOperationInfo.getCompoundAmount(), 2) );
					
					//复利利率
					usedFields.put("CompoundInterestRate", DataFormat.formatRate( printOperationInfo.getCompoundRate(), 6 ));
					
					//复利利息	 modify by xwhe 2008-08-04
					usedFields.put("CompoundInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealCompoundInterest(), 2 ));	
				}
				
				//逾期罚息
				//起始日期罚息通知单的罚息日期/上一次结息的日期（根据不同情况），利率取罚息通知单的罚息日期
				if (printOperationInfo.getRealOverDueInterest() > 0)
				{
					//逾期罚息利息起息日期
					if (printOperationInfo.getOverDueStart() != null)
					{
						usedFields.put("OverInterestDateStart", DataFormat.getChineseDateString(printOperationInfo.getOverDueStart()));
					}
					
					//逾期罚息利息终息日期
					if (printOperationInfo.getOverDueEnd() != null)
					{
						usedFields.put("OverInterestDateEnd", DataFormat.getChineseDateString(printOperationInfo.getOverDueEnd()));
					}
					else
					{
						usedFields.put("OverInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ) );
					}
					
					//逾期罚息利息天数
					if (printOperationInfo.getOverDueStart() != null && printOperationInfo.getOverDueEnd() != null)
					{
						usedFields.put("OverInterestDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(printOperationInfo.getOverDueStart(), printOperationInfo.getOverDueEnd(), 1)) + "" ));
					}
					else if (printOperationInfo.getOverDueStart() != null)
					{
						usedFields.put("OverInterestDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(printOperationInfo.getOverDueStart(), printOperationInfo.getExecuteDate(), 1)) + "" ));
					}
					
					//逾期罚息本金
					usedFields.put("OverInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount(printOperationInfo.getOverDueAmount(), 2) );
					if (printOperationInfo.getOverDueAmount() == 0.00)
					{
						usedFields.put("OverInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ "0.00");
					}
					
					//逾期罚息利率
					usedFields.put("OverInterestRate", DataFormat.formatRate( printOperationInfo.getOverDueRate(), 6 ));
					
					//逾期罚息利息 modify by xwhe 2008-08-04
					usedFields.put("OverInterest",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount(printOperationInfo.getRealOverDueInterest(), 2) );
				}
				
				//担保费
				//起始日期取放款通知单的起始日期/上一次结算但报费的日期，利率从放款通知单取
				if (printOperationInfo.getRealSuretyFee() > 0)
				{
					//担保费利息起息日期
					if(printOperationInfo.getSuretyFeeStart() != null)
					{
						usedFields.put("AssureFeeDateStart", DataFormat.getChineseDateString(printOperationInfo.getSuretyFeeStart()) );
					}
					
					//担保费利息终息日期
					if(printOperationInfo.getSuretyFeeEnd() != null)
					{
						usedFields.put("AssureFeeDateEnd", DataFormat.getChineseDateString(printOperationInfo.getSuretyFeeEnd()) );
					}
					
					//担保费利息天数
					usedFields.put("AssureFeeDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(printOperationInfo.getSuretyFeeStart(), printOperationInfo.getSuretyFeeEnd(), 1)) + "" ));
					
					//担保费利息本金 modify by xwhe 2008-08-04
					usedFields.put("AssureFeeAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(printOperationInfo.getSuretyFeeAmount(), 2) );
					if (printOperationInfo.getSuretyFeeAmount() == 0.0)
					{
						usedFields.put("AssureFeeAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+"0.00" );
					}
					
					//担保费利率
					usedFields.put("AssureFeeRate", DataFormat.formatRate( printOperationInfo.getSuretyFeeRate(), 6 ));
					
					//担保费利息 modify by xwhe 2008-08-04
					usedFields.put("AssureFee", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(printOperationInfo.getRealSuretyFee(), 2) );
				}
				
				//手续费
				if (printOperationInfo.getRealCommission() > 0)
				{
					//手续费利息起息日期
					if(lpfdinfo.getStart() != null)
					{
						usedFields.put("CommissionFeeDateStart", DataFormat.getChineseDateString( lpfdinfo.getStart() ) );
					}
					
					//手续费利息终息日期
					if(lpfdinfo.getEnd() != null)
					{
						usedFields.put("CommissionFeeDateEnd", DataFormat.getChineseDateString( lpfdinfo.getEnd() ) );
					}
					
					//手续费利息天数
					usedFields.put("CommissionFeeDay", DataFormat.formatString( (PrintVoucher.getIntervalDays(lpfdinfo.getStart(), lpfdinfo.getEnd(), 1)) + "") );
					
					//手续费利息本金 modify by xwhe 2008-08-04
					usedFields.put("CommissionFeeAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( lpfdinfo.getAmount(), 2 ) );
			 
                    //手续费利率
					usedFields.put("CommissionFeeRate", DataFormat.formatRate( lpfdinfo.getCommissionRate(), 6 ));
					//modify by xwhe 2008-08-04
					//手续费利息
					if(printOperationInfo.getRealCommission()>0.0)
					{
					usedFields.put("CommissionFee", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID() )+DataFormat.formatListAmount(printOperationInfo.getRealCommission(), 2) );
					
                    //手续费利息大写 add by xwhe 2008-07-28 modify by xwhe 2008-08-04
					usedFields.put("chineseCommissionFee",this.getChineseFormatAmount( printOperationInfo.getRealCommission(), printOperationInfo.getCurrencyID()));
					}
					usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
				}
				
				//利息税费(2007-10-18 保利要求在委托贷款利息单上显示"利息税费")
				if (printOperationInfo.getRealInterestTax() > 0)
				{
					usedFields.put("InterestTax", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount(printOperationInfo.getRealInterestTax(), 2) );
				}
				
				//自营贷款收回
				//累计还款
				if ((lpfdinfo.getAmount() - printOperationInfo.getCurrentBalance()) > 0)
				{
					usedFields.put("TotalRepaymentAmount", DataFormat.formatListAmount( lpfdinfo.getAmount() - printOperationInfo.getCurrentBalance(), 2 ) );
				}
				else
				{
					//本金金额小写
					usedFields.put("Amount", "0.00");
					
					String strChineseAmount = ChineseCurrency.showChinese("", printOperationInfo.getCurrencyID());
					
					//本金金额大写
					usedFields.put("ChineseAmount", strChineseAmount);
				}
				
				//免还利息 modify by xwhe 2008-08-04
				double remitInterest = DataFormat.formatDouble(printOperationInfo.getInterest() - printOperationInfo.getRealInterest());
				if (remitInterest > 0)
				{
					usedFields.put("RemitInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( remitInterest ,2 ) );
				}
				//实际划转金额  add by xwhe 2008-07-15 modify by xwhe 2008-08-04
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
					//利息合计小写
					usedFields.put("NoticeOpenTotalInterest",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+
														DataFormat.formatListAmount(
														DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
														DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
														DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()), 2 ) );
				
					//利息合计大写
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
					//贷款分段计息 add by xwhe 2008-08-22 暂时分三段
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
						usedFields.put("LoanAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount1(), 2 ));  //贷款积数1						  
						}
						
						String Interest11 = DataFormat.formatAmount(printOperationInfo.getInterest1());			
						usedFields.put("Interest11", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest11), 2 ));  //利息1					
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
						usedFields.put("LoanAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount2(), 2 ));  //贷款积数2
						 
						}						
						String Interest12 = DataFormat.formatAmount(printOperationInfo.getInterest2());
				
						usedFields.put("Interest12", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest12), 2 ));  //利息2					
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
						usedFields.put("LoanAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount3(), 2 )); //贷款积数3
							
						}
						
						String Interest13 = DataFormat.formatAmount(printOperationInfo.getInterest3());
						
						usedFields.put("Interest13", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest13), 2 ));  //利息3					
					}
					if(printOperationInfo.getStartDate1()!=null)
					{
						String BeginDate11 = "";  //起息日期1
						BeginDate11 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
						usedFields.put("InterestDateStart11", BeginDate11);  //起息日期1
					}
					if(printOperationInfo.getStartDate2()!=null)
					{
						String BeginDate12 = "";  //起息日期1
						BeginDate12 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
						usedFields.put("InterestDateStart12", BeginDate12);  //起息日期2
					}
					if(printOperationInfo.getStartDate3()!=null)
					{
						String BeginDate13= "";  //起息日期3
						BeginDate13 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
						usedFields.put("InterestDateStart13", BeginDate13);  //起息日期3
					}
					if(printOperationInfo.getEndDate1()!=null)
					{
						String EndDate11= "";  //计息日期1
						EndDate11 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
						usedFields.put("InterestDateEnd11", EndDate11);  //计息日期1
					}
					if(printOperationInfo.getEndDate2()!=null)
					{
						String EndDate12= "";  //计息日期2
						EndDate12 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
						usedFields.put("InterestDateEnd12", EndDate12);  //计息日期2
					}
					if(printOperationInfo.getEndDate3()!=null)
					{
						String EndDate13= "";  //计息日期2
						EndDate13 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
						usedFields.put("InterestDateEnd13", EndDate13);  //计息日期3
					}
					if(printOperationInfo.getRate1() != 0)
					{
						 String Rate11 = ""; //利率1
						 Rate11 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
						 usedFields.put("Rate11", Rate11);  //利率1
					}
					if(printOperationInfo.getRate2() != 0)
					{
						 String Rate12 = ""; //利率2
						 Rate12 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
						 usedFields.put("Rate12", Rate12);  //利率2
					}
					if(printOperationInfo.getRate3() != 0)
					{
						 String Rate13 = ""; //利率3
						 Rate13 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
						 usedFields.put("Rate13", Rate13);  //利率3
					}
					//add by xwhe 2008-09-03
					if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
					 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
					{
						usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3(), printOperationInfo.getCurrencyID() ));		
						
			            //利息合计小写 
						usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3() ));
					   
					}
				}
			}
		}
		
		/******  利息费用支付  开始  ******/
		if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTERESTFEEPAYMENT )
		{
			//正常利息
			if (printOperationInfo.getRealInterest() > 0)
			{
				//合同编号
				usedFields.put("ContractNo", DataFormat.formatString( NameRef.getContractcodeBySubAccountID(printOperationInfo.getSubAccountID()) ) );
				
				//还款单位名称
				usedFields.put("RepaymentUnitName", NameRef.getClientNameByAccountID(printOperationInfo.getPayAccountID()) );
				
				//正常利息开始日期
				usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ) );
				
				//正常利息结束日期
				usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ) );
				
				//正常利息本金 modify by xwhe 2008-08-04
				usedFields.put("NormalInterestAmount",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getAmount(), 2 ) );
				
				//正常利率
				usedFields.put("NormalInterestRate", DataFormat.formatRate( printOperationInfo.getRate(), 6 ) );
				
				//正常利息 modify by xwhe 2008-08-04
				usedFields.put("NormalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ) );
				
				//正常利息天数
				usedFields.put("NormalInterestDay", DataFormat.formatString( printOperationInfo.getNoticeDay()+"" ) );
			    
                //利息合计大写 modify by xwhe 2008-07-15 modify by xwhe 2008-08-04
				usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getRealInterest(), printOperationInfo.getCurrencyID() ));		
				
	            //利息合计小写 
				usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getRealInterest() ));
			   
                //实际划转金额  add by xwhe 2008-07-15
				double transferAmount = DataFormat.formatDouble(printOperationInfo.getRealInterest()-printOperationInfo.getRealInterestTax());
			    //委托方活期账户	2008-07-31
				if(printOperationInfo.getReceiveInterestAccountID()>0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST)
				{
					usedFields.put("ConsignDepositAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getReceiveInterestAccountID()));
				}
                //付款方活期账户2008-07-31
				if(printOperationInfo.getPayInterestAccountID()>0 && (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST 
						||printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_INTEREST))
				{
					usedFields.put("PayAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getPayInterestAccountID()));
				}
				if (transferAmount >= 0.0)
				{
					usedFields.put("TransferAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( transferAmount ,2 ) );
				}
				
			    //贷款分段计息 add by xwhe 2008-08-22 暂时分三段
				if(printOperationInfo.getInterest1()>0)
				{
					usedFields.put("LoanAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount1(), 2 ));  //贷款积数1
					
					String Interest11 = DataFormat.formatAmount(printOperationInfo.getInterest1());
					usedFields.put("Interest11", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest11), 2 ));  //利息1								
				}
				
				if(printOperationInfo.getInterest2()>0)
				{
					usedFields.put("LoanAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount2(), 2 ));  //贷款积数2					}
					
					String Interest12 = DataFormat.formatAmount(printOperationInfo.getInterest2());
					usedFields.put("Interest12", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest12), 2 ));  //利息2					
				}
				
				if(printOperationInfo.getInterest3()>0)
				{
					usedFields.put("LoanAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount3(), 2 ));  //贷款积数3
					
					String Interest13 = DataFormat.formatAmount(printOperationInfo.getInterest3());
					usedFields.put("Interest13", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest13), 2 ));  //利息3					
				}
				
				if(printOperationInfo.getStartDate1()!=null)
				{
					String BeginDate11 = "";  //起息日期1
					BeginDate11 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
					usedFields.put("InterestDateStart11", BeginDate11);  //起息日期1
				}
				if(printOperationInfo.getStartDate2()!=null)
				{
					String BeginDate12 = "";  //起息日期1
					BeginDate12 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
					usedFields.put("InterestDateStart12", BeginDate12);  //起息日期2
				}
				if(printOperationInfo.getStartDate3()!=null)
				{
					String BeginDate13= "";  //起息日期3
					BeginDate13 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
					usedFields.put("InterestDateStart13", BeginDate13);  //起息日期3
				}
				if(printOperationInfo.getEndDate1()!=null)
				{
					String EndDate11= "";  //计息日期1
					EndDate11 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
					usedFields.put("InterestDateEnd11", EndDate11);  //计息日期1
				}
				if(printOperationInfo.getEndDate2()!=null)
				{
					String EndDate12= "";  //计息日期2
					EndDate12 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
					usedFields.put("InterestDateEnd12", EndDate12);  //计息日期2
				}
				if(printOperationInfo.getEndDate3()!=null)
				{
					String EndDate13= "";  //计息日期2
					EndDate13 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
					usedFields.put("InterestDateEnd13", EndDate13);  //计息日期3
				}
				if(printOperationInfo.getRate1() != 0)
				{
					String Rate11 = ""; //利率1
					Rate11 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
					usedFields.put("Rate11", Rate11);  //利率1
				}
				if(printOperationInfo.getRate2() != 0)
				{
					String Rate12 = ""; //利率2
					Rate12 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
					usedFields.put("Rate12", Rate12);  //利率2
				}
				if(printOperationInfo.getRate3() != 0)
				{
					String Rate13 = ""; //利率3
					Rate13 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
					usedFields.put("Rate13", Rate13);  //利率3
				}
				
				//add by xwhe 2008-09-03
				if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
				 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3(), printOperationInfo.getCurrencyID() ));		
					
		            //利息合计小写 
					usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3() ));
				}
			}
		}
		/******  利息费用支付  结束  ******/		
		/******  自营贷款结息  委托贷款结息  开始  ******/
		if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST 
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_INTEREST)
		{
			//正常利息
			if (printOperationInfo.getRealInterest() > 0)
			{
				//合同编号
				usedFields.put("ContractNo", DataFormat.formatString( NameRef.getContractcodeBySubAccountID(printOperationInfo.getSubAccountID()) ) );
				
				//还款单位名称
				usedFields.put("RepaymentUnitName", NameRef.getClientNameByAccountID(printOperationInfo.getPayAccountID()) );
				
				//正常利息开始日期
				usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ) );
				
				//正常利息结束日期
				usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ) );
				
				//正常利息本金 modify by xwhe 2008-08-04
				usedFields.put("NormalInterestAmount",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getAmount(), 2 ) );
				
				//正常利率
				usedFields.put("NormalInterestRate", DataFormat.formatRate( printOperationInfo.getRate(), 6 ) );
				
				//正常利息 modify by xwhe 2008-08-04
				usedFields.put("NormalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ) );
				
				//正常利息天数
				usedFields.put("NormalInterestDay", DataFormat.formatString( printOperationInfo.getNoticeDay()+"" ) );
			    
                //利息合计大写 modify by xwhe 2008-07-15 modify by xwhe 2008-08-04
				usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getRealInterest(), printOperationInfo.getCurrencyID() ));		
				
	            //利息合计小写 
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
				
                //实际划转金额  add by xwhe 2008-07-15
				double transferAmount = DataFormat.formatDouble(printOperationInfo.getRealInterest()-printOperationInfo.getRealInterestTax());
			    //委托方活期账户	2008-07-31
				if(printOperationInfo.getReceiveInterestAccountID()>0 && printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST)
				{
					usedFields.put("ConsignDepositAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getReceiveInterestAccountID()));
				}
                //付款方活期账户2008-07-31
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
				
			    //贷款分段计息 add by xwhe 2008-08-22 暂时分三段
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
					usedFields.put("LoanAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount1(), 2 )); //贷款积数1					
					}					
					String Interest11 = DataFormat.formatAmount(printOperationInfo.getInterest1());
					usedFields.put("Interest11", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest11), 2 ));  //利息1								
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
					usedFields.put("LoanAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount2(), 2 )); //贷款积数2
				  
					}
					
					String Interest12 = DataFormat.formatAmount(printOperationInfo.getInterest2());
					usedFields.put("Interest12", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest12), 2 ));  //利息2					
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
						usedFields.put("LoanAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount3(), 2 )); //贷款积数3
					
					}
					
					String Interest13 = DataFormat.formatAmount(printOperationInfo.getInterest3());
					usedFields.put("Interest13", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest13), 2 ));  //利息3					
				}
				
				if(printOperationInfo.getStartDate1()!=null)
				{
					String BeginDate11 = "";  //起息日期1
					BeginDate11 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
					usedFields.put("InterestDateStart11", BeginDate11);  //起息日期1
				}
				if(printOperationInfo.getStartDate2()!=null)
				{
					String BeginDate12 = "";  //起息日期1
					BeginDate12 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
					usedFields.put("InterestDateStart12", BeginDate12);  //起息日期2
				}
				if(printOperationInfo.getStartDate3()!=null)
				{
					String BeginDate13= "";  //起息日期3
					BeginDate13 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
					usedFields.put("InterestDateStart13", BeginDate13);  //起息日期3
				}
				if(printOperationInfo.getEndDate1()!=null)
				{
					String EndDate11= "";  //计息日期1
					EndDate11 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
					usedFields.put("InterestDateEnd11", EndDate11);  //计息日期1
				}
				if(printOperationInfo.getEndDate2()!=null)
				{
					String EndDate12= "";  //计息日期2
					EndDate12 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
					usedFields.put("InterestDateEnd12", EndDate12);  //计息日期2
				}
				if(printOperationInfo.getEndDate3()!=null)
				{
					String EndDate13= "";  //计息日期2
					EndDate13 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
					usedFields.put("InterestDateEnd13", EndDate13);  //计息日期3
				}
				if(printOperationInfo.getRate1() != 0)
				{
					String Rate11 = ""; //利率1
					Rate11 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
					usedFields.put("Rate11", Rate11);  //利率1
				}
				if(printOperationInfo.getRate2() != 0)
				{
					String Rate12 = ""; //利率2
					Rate12 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
					usedFields.put("Rate12", Rate12);  //利率2
				}
				if(printOperationInfo.getRate3() != 0)
				{
					String Rate13 = ""; //利率3
					Rate13 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
					usedFields.put("Rate13", Rate13);  //利率3
				}
				
				//add by xwhe 2008-09-03
				if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE
				 || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3(), printOperationInfo.getCurrencyID() ));		
					
		            //利息合计小写 
					usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+this.getFormatAmount( printOperationInfo.getInterest1()+printOperationInfo.getInterest2()+printOperationInfo.getInterest3() ));
				}
			}
		}
		/******  自营贷款结息  委托贷款结息 结束  ******/
		
		/******  委托贷款计提应收利息（含冲销） 自营贷款计提应收利息（含冲销） 开始  ******/
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST 
		  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST)
		{
			//合同编号
			usedFields.put("ContractNo", DataFormat.formatString( NameRef.getContractcodeBySubAccountID(printOperationInfo.getSubAccountID()) ) );
			
			//还款单位名称
			usedFields.put("RepaymentUnitName", NameRef.getClientNameByAccountID(printOperationInfo.getPayAccountID()) );
			
			//正常利息开始日期
			usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ) );
			
			//正常利息结束日期
			usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ) );
			
			//正常利息本金 modify by xwhe 2008-08-04
			if(printOperationInfo.getAmount()>0.0)
			{
			usedFields.put("NormalInterestAmount", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount( printOperationInfo.getAmount(), 2 ) );
			}
			//协定积数 modify by xwhe 2008-09-19
			if(printOperationInfo.getRate()!=0)
			{
			String LoanAmass4 = DataFormat.formatAmount(
					UtilOperation.Arith.div(
						UtilOperation.Arith.mul(printOperationInfo.getRealInterest(), 360)
					  , UtilOperation.Arith.div(printOperationInfo.getRate(), 100)
					)
				);
			usedFields.put("LoanAmass4", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(LoanAmass4), 2 ));  //协定积数
			}
			
			//正常利率
			usedFields.put("NormalInterestRate", DataFormat.formatRate( printOperationInfo.getRate(), 6 ) );
			
			//正常利息 modify by xwhe 2008-08-04
			if(printOperationInfo.getRealInterest()>0.0)
			{
			usedFields.put("NormalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getRealInterest(), 2 ) );
			}
			//利息合计大写 modify by xwhe 2008-07-15 modify by xwhe 2008-08-04
			if(printOperationInfo.getRealInterest()> 0.0){
			usedFields.put("chineseOpenTotalInterest", this.getChineseFormatAmount( printOperationInfo.getRealInterest(), printOperationInfo.getCurrencyID() ));		
			
            //利息合计小写 
			usedFields.put("NoticeOpenTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ this.getFormatAmount( printOperationInfo.getRealInterest() ));
			}
			//正常利息天数			
			usedFields.put("NormalInterestDay", DataFormat.formatString( printOperationInfo.getNoticeDay()+"" ) );
		}
		/******  委托贷款计提应收利息（含冲销） 自营贷款计提应收利息（含冲销） 结束  ******/
		
		//贷款账户号
		usedFields.put("LoanAccountNo", NameRef.getAccountNoByID( printOperationInfo.getLoanAccountID() ));
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
		{
           //收款方账号
			usedFields.put("LoanAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID()));
		}		
		//借款单位(收款方客户)(自营贷款账户)
		//usedFields.put("LoanUnit", NameRef.getClientNameByID( printOperationInfo.getReceiveClientID() ));
		usedFields.put("LoanUnit", NameRef.getAccountNameByID( printOperationInfo.getLoanAccountID() ));
		
		//贷款合计利息
		//实际支付贷款利息 + 实际支付复利 + 实际支付逾期罚息 + 实际支付担保费
		usedFields.put("LoanTotalInterest", DataFormat.formatListAmount(printOperationInfo.getRealInterest() + printOperationInfo.getRealCompoundInterest() + printOperationInfo.getRealOverDueInterest() + printOperationInfo.getRealSuretyFee(), 2 ));
	
		//还款单位名称
		if (printOperationInfo.getPayClientID() > 0)
		{
			usedFields.put("RepaymentUnitName", NameRef.getClientNameByID( printOperationInfo.getPayClientID() ));
		}
			
		//逾期贷款利息
		usedFields.put("RealOverDueInterest", DataFormat.formatListAmount( printOperationInfo.getRealOverDueInterest(), 2 ));
		
		//贷款利息
		usedFields.put("LoanInterest", DataFormat.formatListAmount( printOperationInfo.getInterest(), 2 ) );
		
		//贷款利率
		usedFields.put("LoanRate", DataFormat.formatRate( printOperationInfo.getRate(), 6 ) );
		
		/********** 贴现发放凭证打印 **********/
		/********** 贴现收回凭证打印 **********/
		
		Sett_TransGrantDiscountDAO discountDao = new Sett_TransGrantDiscountDAO();
		DiscountCredenceInfo dcinfo = new DiscountCredenceInfo();
		dcinfo = discountDao.findDiscountCredenceByID( printOperationInfo.getDiscountNoteID() );
		
		//贴现合同号
		usedFields.put("DiscountContractCode", dcinfo.getDiscountContractCode());
		
		//贴现凭证号
		usedFields.put("DiscountCode", dcinfo.getCode());
		
		//汇票金额小写
		usedFields.put("DiscountBillAmount", DataFormat.formatListAmount( printOperationInfo.getDiscountBillAmount(), 2 ));
		
		//汇票金额大写
		usedFields.put("ChineseDiscountBillAmount", ChineseCurrency.showChinese(DataFormat.formatAmount( printOperationInfo.getDiscountBillAmount() ), printOperationInfo.getCurrencyID()));
		
		//贴现出票日
		usedFields.put("PublicDate", DataFormat.getChineseDateString( dcinfo.getPublicDate() ));
		
		//贴现到期日
		usedFields.put("AtTerm", DataFormat.getChineseDateString( dcinfo.getAtTerm() ));
		
		//贴现利息
		usedFields.put("DiscountInterestAmount", DataFormat.formatAmount( printOperationInfo.getDiscountInterestAmount()) );
		
		//贴现种类
		usedFields.put("DraftTypeName", LOANConstant.DraftType.getName( dcinfo.getDraftTypeID() ));
		
		//实付贴现金额小写
		usedFields.put("DiscountAmount", DataFormat.formatListAmount( printOperationInfo.getDiscountAmount(), 2) );
		
		//实付贴现金额大写
		usedFields.put("ChineseDiscountAmount", ChineseCurrency.showChinese(DataFormat.formatAmount( printOperationInfo.getDiscountAmount() ), printOperationInfo.getCurrencyID()) );
		
		//贷款余额(格式化余额,为"0"时显示"0.00")
		usedFields.put("CurrentBalance", DataFormat.formatListAmount( printOperationInfo.getCurrentBalance(), 2 ) );
		
		//贴现利率
		usedFields.put("DiscountRate", DataFormat.formatRate( dcinfo.getDiscountRate() , 6 ) );
		
		//汇票号码
		usedFields.put("BillNo", NameRef.getDiscountBillNoByID( printOperationInfo.getDiscountBillID() ) );
		
		//银行到账日
		usedFields.put("DateBankAccount", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ) );
		
		//贴现单位
		usedFields.put("ApplicantName", DataFormat.formatString( printOperationInfo.getApplicantName() ) );

		//贴现单位账号
		usedFields.put("ApplicantAccountName", DataFormat.formatString( printOperationInfo.getApplicantAccountNo() ) );
		
		//贴现单位开户银行
		usedFields.put("ApplicantBankName", DataFormat.formatString( printOperationInfo.getApplicantAccountBankNo() ) );
		
		if ( dcinfo != null )
		{
			//贴现日
			usedFields.put("DateDiscount", DataFormat.getChineseDateString(DataFormat.getDateTime(NameRef.getDiscountDateByDiscountBillID(printOperationInfo.getDiscountBillID()))));
		
		}
		
		//累计还款金额(贴现收回凭证打印)
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
		
		/**********  存款利息计付通知单(用于通知存款 定期存款的收回凭证)  **********/
		
		//账户所属单位名称
		usedFields.put("AccountClientName", NameRef.getClientNameByAccountID( printOperationInfo.getPayAccountID() ) );
		
		//取上次计提日期，作为计提日期的结束日期
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
		
		//存放数据变量
		String Amount1 = "";  //本金1
		String Amount2 = "";  //本金2
		String Amount3 = "";  //本金3
		String Amount4 = "";  //本金4
		String Interest1 = "";  //利息1
		String Interest2 = "";  //利息2
		String Interest3 = "";  //利息3
		String Interest4 = "";  //利息4
		String Interest5 = "";  //利息5
		String Rate1 = "";  //利率1
		String Rate2 = "";  //利率2
		String Rate3 = "";  //利率3
		String Rate4 = "";  //利率4
		String Rate5 = "";  //利率5
		String DayNumber1 = "";  //天数1
		String DayNumber2 = "";  //天数2
		String DayNumber3 = "";  //天数3
		String DayNumber4 = "";  //天数4
		String BeginDate1 = "";  //起息日期1
		String BeginDate2 = "";  //起息日期2
		String BeginDate3 = "";  //起息日期3
		String BeginDate4 = "";  //起息日期4
		String BeginDate5 = "";  //起息日期5
		String OverDate1 = "";  //终息日期1
		String OverDate2 = "";  //终息日期2
		String OverDate3 = "";  //终息日期3
		String OverDate4 = "";  //终息日期4
		String OverDate5 = "";  //终息日期5
		
		//国电需要增加2项
		String CurrentAmass = "";  //活期积数
		String AccordAmass = "";  //协定积数
		String CurrentAmass1 = "";  //活期积数1
		String CurrentAmass2 = "";  //活期积数2
		String CurrentAmass3 = "";  //活期积数3
		String AccordAmass1 = "";   //协定积数1
		String AccordAmass2 = "";   //协定积数2
		
		String NegotiateStartDate1 ="";//协定开始日期1
		String NegotiateEndtDate1 ="";//协定结息日期1
		String NegotiateStartDate2 ="";//协定开始日期2
		String NegotiateEndtDate2 ="";//协定结息日期2
		String NegotiateRate1 = "";//协定利率1
		String NegotiateRate2 = "";//协定利率1
		String NegotiateInterest1 ="";//协定利息
		String NegotiateInterest2 ="";//协定利息
		//计付利息合计
		String totalInterest = "";
		
		/***** 有活期利息 *****/
		//利息费用支付-活期结息, 定期存款计提应付利息（含冲销）, 通知存款计提应付利息（含冲销）, 活期存款计提应付利息（含冲销）
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
				//本金1，活期利息，活期利息利率，开始日期，结束日期，天数
				Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest1 = DataFormat.formatAmount(printOperationInfo.getCurrentInterest());
				
				Rate1 = DataFormat.formatRate( printOperationInfo.getRate(), 6 );
				
				BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
				
				OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());
				
				DayNumber1 = DataFormat.formatNumber( printOperationInfo.getNoticeDay() );				
				//活期积数
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
                //modify by xwhe 2008-08-21 活期分段计息暂时分三段
				if(printOperationInfo.getRate1() != 0){			
				
				Interest1 = DataFormat.format(printOperationInfo.getInterest1(),4);	
				
				Rate1 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
				
				BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
				
				OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
				DayNumber1 = Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getStartDate(),printOperationInfo.getEndDate1()));	
               //活期积数1
				if(printOperationInfo.getRate1() != 0)
				{					
                   //modify by xwhe 2008-12-24
					usedFields.put("CurrentAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount1(), 2 )); //活期积数1
				}
				}
				
				if(printOperationInfo.getRate2() != 0)
				{      
				
				Interest4 = DataFormat.format(printOperationInfo.getInterest2(),4);
				
				Rate4 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
				
				BeginDate4 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
				
				OverDate4 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
				DayNumber4 = Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getStartDate2(),printOperationInfo.getEndDate2()));	
               //活期积数2
				if(printOperationInfo.getRate2() != 0)
				{			
				//modify by xwhe 2008-12-24
				usedFields.put("CurrentAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount2(), 2 )); //活期积数2					
				}
				}
				if(printOperationInfo.getRate3()!=0)
				{
				Interest5 = DataFormat.format(printOperationInfo.getInterest3(),4);	
				
				Rate5 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
				
				BeginDate5 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
				
				OverDate5 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
               //活期积数3
				if(printOperationInfo.getRate3() != 0)
				{
				
                    //modify by xwhe 2008-12-24
					usedFields.put("CurrentAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( printOperationInfo.getSumBlanceAmount3(), 2 )); //活期积数3
					
				}
				}	
				//协定存款的算息(协定存款本金)
				//if (printOperationInfo.getAccordInterestAmount() > 0)
				//存在协定利息但是协定余额为0时，协定利息显示错误，minzhao 20090408
				if (printOperationInfo.getAccordInterest() > 0)
				{
					Amount2 = DataFormat.formatAmount(printOperationInfo.getAccordInterestAmount());
					
					//如果存在协定本金 2008年3月20日
					Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount() - printOperationInfo.getAccordInterestAmount());
					
					Interest2 = DataFormat.formatAmount(printOperationInfo.getAccordInterest());
					
					Rate2 = DataFormat.formatRate( printOperationInfo.getAccordInterestRate(), 6 );
					
					BeginDate2 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
					
					OverDate2 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());
					
					DayNumber2 = DataFormat.formatNumber( printOperationInfo.getNoticeDay() );
					
					//协定积数
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
				
				//利息合计
				totalInterest = (
						DataFormat.formatAmount(
								DataFormat.formatDouble(
										DataFormat.formatDouble(printOperationInfo.getCurrentInterest()) + DataFormat.formatDouble(printOperationInfo.getAccordInterest()))));
			}
		}
		else if ( printOperationInfo.getCurrentInterest() != 0.0 )
		{
			//如起息日在终止日期之前，即提前支取
			if ( printOperationInfo.getInterestStartDate() != null 
			  && printOperationInfo.getEndDate() != null 
			  && printOperationInfo.getInterestStartDate().before(printOperationInfo.getEndDate()) )
			{
				//本金1，活期利息，活期利息利率，开始日期，结束日期，天数
				Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest1 = DataFormat.formatAmount(printOperationInfo.getCurrentInterest());
				
				//不再从配置文件取,从表 sett_TransFixedWithDraw 取 advanceRate
				Rate1 = DataFormat.formatRate( printOperationInfo.getRate(), 6 );
				
				BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
				
				OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());
				
				//定期支取 定期续存
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//天数1
					//活期利息按实际天数显示
					DayNumber1 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1)) + "";
				}
				else
				{
					//通知支取
					if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						DayNumber1 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1)) + "";
					}
					else
					{
						DayNumber1 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1)) + "";
					}
				}
					
				//本金2，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
				if (printOperationInfo.getOtherInterest() != 0.0)
				{
					Amount2 = DataFormat.formatAmount(printOperationInfo.getAmount());
					
					Interest2 = (DataFormat.formatAmount(printOperationInfo.getOtherInterest()));
					
					BeginDate2 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
					
					OverDate2 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());
					
					//定期支取 定期续存
					if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
					  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER )
					{				
					}
					else
					{
						//通知支取
						if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{						
						}
						else
						{			
						}
					}
				}
				
				//利息合计
				totalInterest = ( 
						DataFormat.formatAmount(
								DataFormat.formatDouble(
										DataFormat.formatDouble(printOperationInfo.getCurrentInterest()) + DataFormat.formatDouble(printOperationInfo.getOtherInterest()))));
			}
			//如起息日在终止日期之后
			else
			{
				//本金1，利息支出，利息支出利率，开始日期，结束日期，天数
				Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest1 = (
					DataFormat.formatAmount(
						DataFormat.formatDouble(
							DataFormat.formatDouble(printOperationInfo.getPayableInterest())
								)));
				
				Rate1 = DataFormat.formatRate( printOperationInfo.getRate(), 6 );
				
				BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());

				OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());
				
				//定期支取 定期续存
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getEndDate(), 2)) + "");
				}
				else
				{
					if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						//通知支取
						DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getEndDate(), 1)) + "");
					}
					else
					{
						DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getEndDate(), 1)) + "");
					}
				}
				
				//本金2，活期利息，活期利息利率，开始日期，结束日期，天数
				Amount2 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest2 = DataFormat.formatAmount(printOperationInfo.getCurrentInterest());
				
				//不再从配置文件取,从表 sett_TransFixedWithDraw 取 advanceRate
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
				
				//定期支取 定期续存
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//活期利息按实际天数显示
					DayNumber2 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), 1)) + "");
				}
				else
				{
					//通知支取
					if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
					{
						DayNumber2 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), 1)) + "");
					}
					else
					{
						DayNumber2 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), 1)) + "");
					}
				}
				
				//其它利息
				if (printOperationInfo.getOtherInterest() != 0.0)
				{
					//本金3，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
					Amount3 = DataFormat.formatAmount(printOperationInfo.getAmount());
					
					Interest3 = (DataFormat.formatAmount(printOperationInfo.getOtherInterest()));
					
					BeginDate3 = DataFormat.getChineseDateString(printOperationInfo.getEndDate());

					OverDate3 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());

					//定期支取 定期续存
					if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
					  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
					{
						//DayNumber3 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "");
					}
					else
					{
						if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
						{
							//通知支取
							//DayNumber3 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "");
						}
						else
						{
							//DayNumber3 = ((PrintVoucher.getIntervalDays(printOperationInfo.getEndDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag) + 1) + "");
						}
					}
				}
				
				//利息合计 = 利息 + 到期后活期利息 + 其他利息
				totalInterest = (
						DataFormat.formatAmount(
								DataFormat.formatDouble(
										printOperationInfo.getPayableInterest() + printOperationInfo.getCurrentInterest() + printOperationInfo.getOtherInterest()
										)));
				
			}
		}
		//没有活期利息
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
					
					//不再从配置文件取,从表 sett_TransFixedWithDraw 取 advanceRate
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
			
			//本金1，利息支出，利息支出利率，开始日期，结束日期，天数
			Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
			
			Interest1 = DataFormat.formatAmount(interest);
			
			Rate1 = DataFormat.formatRate( rate, 6 );
			
			BeginDate1 = DataFormat.getChineseDateString(tsStartDate);

			OverDate1 = DataFormat.getChineseDateString(tsEndDate);

			//定期支取 定期续存
			if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
			{
				DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), tsEndDate, 2)) + "");
			}
			else 
			{
				if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
				{
					//通知支取
					DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), tsEndDate, 1)) + "");
				}
				else
				{
					DayNumber1 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), tsEndDate, 1)) + "");
				}
			}
			
			//利息合计
			totalInterest = (DataFormat.formatAmount(DataFormat.formatDouble(printOperationInfo.getPayableInterest())));
			
			//本金2，其它利息(可能有)，其它利息利率(无)，开始日期，结束日期，天数
			if (printOperationInfo.getOtherInterest() != 0.0)
			{
				Amount2 = DataFormat.formatAmount(printOperationInfo.getAmount());
				
				Interest2 = (DataFormat.formatAmount(printOperationInfo.getOtherInterest()));
				
				BeginDate2 = DataFormat.getChineseDateString(printOperationInfo.getStartDate());
				
				OverDate2 = DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate());

				//定期支取 定期续存
				if ( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER
				  || printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
				{
					//DayNumber2 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "";
				} 
				else 
				{
					//通知支取
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
				
		//存款种类
		String depositTypeName = SETTConstant.TransactionType.getName( printOperationInfo.getTransTypeID() );
		if (depositTypeName.indexOf("定期") >= 0)
		{
			depositTypeName = "定期支取";//modify by xwhe 2008-08-15
		}
		else if (depositTypeName.indexOf("通知") >= 0)
		{
			depositTypeName = "通知存款";
		} 
		else 
		{
			depositTypeName = "保证金存款";
		}
		if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXEDCONTINUETRANSFER)
		{
			depositTypeName = "到期续存";
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT)
		{
			depositTypeName = "活期结息";
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST)
		{
			depositTypeName = "定期存款计提利息";
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST)
		{
			depositTypeName = "通知存款计提利息";
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST)
		{
			depositTypeName = "活期存款计提利息";
		}
		
		usedFields.put("depositTypeName", depositTypeName);
		//modify by xwhe 2008-08-04 在存款利息计付通知单上实现在程序里面添加币种符号，便于区分当金额不存在时，不显示币种符号
		
		if (Amount1 != null && !Amount1.equals(""))
		{
			usedFields.put("Amount1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Amount1), 2 ));  //本金1
		}
		if (Amount2 != null && !Amount2.equals(""))
		{
			usedFields.put("Amount2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Amount2), 2 ));  //本金2
		}
		if (Amount3 != null && !Amount3.equals(""))
		{
			usedFields.put("Amount3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Amount3), 2 ));  //本金3
		}
		if (Amount4 != null && !Amount4.equals(""))
		{
			usedFields.put("Amount4", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Amount4), 2 ));  //本金4
		}
		
		if (Interest1 != null && !Interest1.equals(""))
		{
			usedFields.put("Interest1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(Interest1), 4 ));  //利息1
		}
		if (Interest2 != null && !Interest2.equals(""))
		{
			usedFields.put("Interest2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest2), 2 ));  //利息2
		}
		if (Interest3 != null && !Interest3.equals(""))
		{
			usedFields.put("Interest3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest3), 2 ));  //利息3
		}
		if (Interest4 != null && !Interest4.equals(""))
		{
			usedFields.put("Interest4", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(Interest4), 4 ));  //利息4
		}
		if (Interest5 != null && !Interest5.equals(""))
		{
			usedFields.put("Interest5", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(Interest5), 4 ));  //利息5
		}
		if (NegotiateInterest1 != null && !NegotiateInterest1.equals(""))
		{
			usedFields.put("NegotiateInterest1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(NegotiateInterest1), 4 ));  //协定利息1
		}
		if (NegotiateInterest2 != null && !NegotiateInterest2.equals(""))
		{
			usedFields.put("NegotiateInterest2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatNumber( Double.parseDouble(NegotiateInterest2), 4 ));  //协定利息2
		}
		
		usedFields.put("Rate1", Rate1);  //利率1
		usedFields.put("Rate2", Rate2);
		usedFields.put("Rate3", Rate3);
		usedFields.put("Rate4", Rate4);
		usedFields.put("Rate5", Rate5);
		usedFields.put("NegotiateRate1", NegotiateRate1);
		usedFields.put("NegotiateRate2", NegotiateRate2);
		
		usedFields.put("DayNumber1", DayNumber1);  //天数1
		usedFields.put("DayNumber2", DayNumber2);
		usedFields.put("DayNumber3", DayNumber3);
		usedFields.put("DayNumber4", DayNumber4);
		
		usedFields.put("BeginDate1", BeginDate1);  //起息日期1
		usedFields.put("BeginDate2", BeginDate2);
		usedFields.put("BeginDate3", BeginDate3);
		usedFields.put("BeginDate4", BeginDate4);
		usedFields.put("BeginDate5", BeginDate5);
		usedFields.put("NegotiateStartDate1", NegotiateStartDate1);
		usedFields.put("NegotiateStartDate2", NegotiateStartDate2);
		
		
		usedFields.put("OverDate1", OverDate1);  //终息日期1
		usedFields.put("OverDate2", OverDate2);
		usedFields.put("OverDate3", OverDate3);
		usedFields.put("OverDate4", OverDate4);
		usedFields.put("OverDate5", OverDate5);
		usedFields.put("NegotiateEndtDate1", NegotiateEndtDate1);
		usedFields.put("NegotiateEndtDate2", NegotiateEndtDate2);
		
		
		if (CurrentAmass != null && !CurrentAmass.equals(""))
		{
			usedFields.put("CurrentAmass", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(CurrentAmass), 2 ));  //活期积数
		}
		//modify by xwhe 2008-08-21
		if (CurrentAmass1 != null && !CurrentAmass1.equals(""))
		{
			usedFields.put("CurrentAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(CurrentAmass1), 2 ));  //活期积数1
		}
		if (CurrentAmass2 != null && !CurrentAmass2.equals(""))
		{
			usedFields.put("CurrentAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(CurrentAmass2), 2 ));  //活期积数2
		}
		if (CurrentAmass3 != null && !CurrentAmass3.equals(""))
		{
			usedFields.put("CurrentAmass3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(CurrentAmass3), 2 ));  //活期积数3
		}
		
		if (AccordAmass != null && !AccordAmass.equals(""))
		{
			usedFields.put("AccordAmass", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AccordAmass), 2 ));  //协定积数
			usedFields.put("CurrentAmassType", "正常");
			usedFields.put("AccordAmassType", "协定");
		}
		//add by xwhe 2008-08-26
		if (AccordAmass1 != null && !AccordAmass1.equals(""))
		{
			usedFields.put("AccordAmass1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AccordAmass1), 2 ));  //协定积数1
			usedFields.put("CurrentAmassType", "正常");
			usedFields.put("AccordAmassType", "协定");
		}
       //add by xwhe 2008-08-26
		if (AccordAmass2 != null && !AccordAmass2.equals(""))
		{
			usedFields.put("AccordAmass2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AccordAmass2), 2 ));  //协定积数1
			usedFields.put("CurrentAmassType", "正常");
			usedFields.put("AccordAmassType", "协定");
		}
		
		if(totalInterest != null && !totalInterest.equals(""))
		{
			//本息合计小写
			usedFields.put("AmountANDInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount(DataFormat.formatDouble(Double.parseDouble(totalInterest) + printOperationInfo.getAmount()), 2) );
		
			//本息合计大写
			usedFields.put("ChineseAmountANDInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ChineseCurrency.showChinese(DataFormat.formatAmount(DataFormat.formatDouble(Double.parseDouble(totalInterest) + printOperationInfo.getAmount())), printOperationInfo.getCurrencyID()) );
		}
		else
		{
			//本息合计小写
			usedFields.put("AmountANDInterest", DataFormat.formatListAmount(DataFormat.formatDouble(printOperationInfo.getAmount()), 2) );
		
			//本息合计大写
			usedFields.put("ChineseAmountANDInterest", ChineseCurrency.showChinese(DataFormat.formatAmount(DataFormat.formatDouble(printOperationInfo.getAmount())), printOperationInfo.getCurrencyID()) );
		}
			
		//计付利息合计小写
		if (totalInterest != null && !totalInterest.equals(""))
		{
			usedFields.put("InterestTotalInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(totalInterest), 2) );
		}
		else
		{
			usedFields.put("InterestTotalInterest",  Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+"0.00" );
		}
		
		//计付利息合计大写
		String strChineseAmount = ChineseCurrency.showChinese(totalInterest, printOperationInfo.getCurrencyID());
		usedFields.put("ChineseInterestTotal", strChineseAmount);
		
		/************* 贷款利息通知单 ****************/
		
		//借款人(借款单位名称)
		usedFields.put("DebitName", printOperationInfo.getBorrowClientName() );
		
		//委托单位名称(需求变更2004/04/12改为账户名称)
		usedFields.put("ConsignClientName", NameRef.getClientNameByID( printOperationInfo.getConsignClientID() ) );
		
		//账号类型
		if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_INTEREST)
		{
			usedFields.put("AccountTypeName", "自营贷款结息");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST)
		{
			usedFields.put("AccountTypeName", "委托贷款结息");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_INTEREST)
		{
			usedFields.put("AccountTypeName", "银团贷款结息");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST)
		{
			usedFields.put("AccountTypeName", "委托贷款计提利息");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST)
		{
			usedFields.put("AccountTypeName", "自营贷款计提利息");
		}
		else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST)
		{
			usedFields.put("AccountTypeName", "银团贷款计提利息");
		}
		else
		{
			usedFields.put("AccountTypeName", SETTConstant.AccountType.getName( printOperationInfo.getAccountTypeID() ) );
		}
		
		String AccountNo = "";  //账号
		String AccountName = "";  //账户名称
		
		//自营贷款收回  委托贷款收回
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
		 
		//账号
		usedFields.put("AccountNo", DataFormat.formatString( AccountNo ) );
		
		//账户名称
		usedFields.put("AccountName", DataFormat.formatString( AccountName ) );
		//add by xwhe 2008-07-15
		if(printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
		{
            //账号
			usedFields.put("LoanAccountNo", DataFormat.formatString( AccountNo ) );
		}
				
		//是否网银指令
		usedFields.put("IsEbankOperation", "否");
		//标识非结算系统产生的流水号
		if (printOperationInfo.getInstructionNo() != null && !printOperationInfo.getInstructionNo().equals(""))
		{
			//网银指令编号
			usedFields.put("InstructionNo", DataFormat.formatString( printOperationInfo.getInstructionNo() ));
			//是否网银指令
			usedFields.put("IsEbankOperation", "是");
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
			   //借方信息
				usedFields.put("Account", "账号");
				usedFields.put("ReceiveAccountNo", printOperationInfo.getReceiveExtAccountNo());
	           //收款方开户行名称
				usedFields.put("CurrentName", DataFormat.formatString( branchInfo.getBranchName() ));
			  //贷方信息
				usedFields.put("PayAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));
				usedFields.put("GLno", "科目号");
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
						
			}
		     //modify by xwhe 2008- 12 -16
			else if (printOperationInfo.getReceiveAccountID()>0&& printOperationInfo.getPayGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT  && printOperationInfo.getOperationType()==1003)
			{
				//借方信息
				usedFields.put("Account", "账号");
				usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));	
				usedFields.put("CurrentName",NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID()));
				//贷方信息
				usedFields.put("GLno", "科目号");		
				usedFields.put("PayAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));	
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));			    					
			}
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayAccountID()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				//借方信息
				usedFields.put("Account", "科目号");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				//贷方信息
				usedFields.put("GLno", "账号");		
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID(printOperationInfo.getPayAccountID()));	
				usedFields.put("LoanName", NameRef.getAccountNameByID(printOperationInfo.getPayAccountID()));
			}		
			 //modify by xwhe 2008- 12 -16
			else if (printOperationInfo.getReceiveAccountID()>0&& printOperationInfo.getPayBankID()> 0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID()); 
				//借方信息
				usedFields.put("Account", "账号");
				usedFields.put("ReceiveAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));
				usedFields.put("CurrentName", NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID()));
				//贷方信息
				usedFields.put("GLno", "账号");		
				usedFields.put("PayAccountNo", printOperationInfo.getPayExtAccountNo());
				usedFields.put("LoanName",  DataFormat.formatString( branchInfo.getBranchName() ));
					
			}
			 //modify by xwhe 2008- 12 -16
			else if (printOperationInfo.getReceiveBankID()> 0&& printOperationInfo.getPayAccountID()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());  
			   //借方信息
				usedFields.put("Account", "账号");
				usedFields.put("ReceiveAccountNo", printOperationInfo.getReceiveExtAccountNo());
	           //收款方开户行名称
				usedFields.put("CurrentName", DataFormat.formatString( branchInfo.getBranchName() ));
				//贷方信息
				usedFields.put("GLno", "账号");		
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID(printOperationInfo.getPayAccountID()));	
				usedFields.put("LoanName", NameRef.getAccountNameByID(printOperationInfo.getPayAccountID()));
						
			}
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayGL()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				//借方信息
				usedFields.put("Account", "科目号");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				//贷方信息
				usedFields.put("PayAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));
				usedFields.put("GLno", "科目号");
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
				}		
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayBankID()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNerceivedirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());  
				//借方信息
				usedFields.put("Account", "科目号");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				//贷方信息
				usedFields.put("GLno", "账号");		
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
			   //借方信息
				usedFields.put("ReceiveAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));
				usedFields.put("Account", "科目号");
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));		
			  //贷方信息
				usedFields.put("PayAccountNo", printOperationInfo.getReceiveExtAccountNo());
				usedFields.put("GLno", "账号");
				usedFields.put("LoanName",  DataFormat.formatString( branchInfo.getBranchName() ));
						
			}
		     //modify by xwhe 2008- 12 -16
			else if (printOperationInfo.getReceiveAccountID()>0&& printOperationInfo.getPayGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				//借方信息
				usedFields.put("Account", "科目号");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
				//贷方信息
				usedFields.put("GLno", "账号");		
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));	
				usedFields.put("LoanName", NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID()));				    					
			}
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayGL()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				//借方信息
				usedFields.put("Account", "科目号");
				usedFields.put("ReceiveAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getPayGL())));	
				usedFields.put("CurrentName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
				//贷方信息
				usedFields.put("PayAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));
				usedFields.put("GLno", "科目号");
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				}	
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayAccountID()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{
				//借方信息
				usedFields.put("Account", "账号");			
				usedFields.put("ReceiveAccountNo",NameRef.getAccountNoByID(printOperationInfo.getPayAccountID()));	
				usedFields.put("CurrentName",NameRef.getAccountNameByID(printOperationInfo.getPayAccountID()));
				//贷方信息
				usedFields.put("GLno", "科目号");		
				usedFields.put("PayAccountNo", DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));		
				usedFields.put("LoanName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
			}	
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayAccountID()>0 && printOperationInfo.getReceiveBankID()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
			{  
				
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID()); 				
				//借方信息
				usedFields.put("Account", "账号");			
				usedFields.put("ReceiveAccountNo",NameRef.getAccountNoByID(printOperationInfo.getPayAccountID()));	
				usedFields.put("CurrentName",NameRef.getAccountNameByID(printOperationInfo.getPayAccountID()));
				//贷方信息
				usedFields.put("GLno", "账号");		
				usedFields.put("PayAccountNo", printOperationInfo.getReceiveExtAccountNo());		
				usedFields.put("LoanName",DataFormat.formatString( branchInfo.getBranchName() ));
			}			 		
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayBankID()>0 && printOperationInfo.getReceiveAccountID()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());  
				//借方信息
				usedFields.put("Account", "账号");
				usedFields.put("ReceiveAccountNo",printOperationInfo.getPayExtAccountNo());	
				usedFields.put("CurrentName", DataFormat.formatString( branchInfo.getBranchName() ));
				//贷方信息
				usedFields.put("GLno", "账号");		
				usedFields.put("PayAccountNo",NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));
				usedFields.put("LoanName",  NameRef.getAccountNameByID(printOperationInfo.getReceiveAccountID()));
				}
			 //modify by xwhe 2008- 12 -16 
			else if (printOperationInfo.getPayBankID()>0 && printOperationInfo.getReceiveGL()>0 && printOperationInfo.getNpaydirection() ==SETTConstant.DebitOrCredit.DEBIT && printOperationInfo.getOperationType()==1003)
				{
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());  
				//借方信息
				usedFields.put("Account", "账号");
				usedFields.put("ReceiveAccountNo",printOperationInfo.getPayExtAccountNo());	
				usedFields.put("CurrentName", DataFormat.formatString( branchInfo.getBranchName() ));
				//贷方信息
				usedFields.put("GLno", "科目号");		
				usedFields.put("PayAccountNo",DataFormat.formatString(NameRef.getGLKNoByID(printOperationInfo.getReceiveGL())));
				usedFields.put("LoanName",  NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
				}			 
			}
		}
		
		//modify by xwhe 2008-12-22 银团发放
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANGRANT )
		{
             //贷款种类
			usedFields.put("LoanTypeName", SETTConstant.AccountGroupType.getName( printOperationInfo.getAccountTypeID()));
		}
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BANKGROUPLOANRECEIVE )
		{
             //贷款账号
			usedFields.put("LoanAccountNo", DataFormat.formatString( AccountNo ));
		}
		
		//总账业务
		if( printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.GENERALLEDGER )
		{
			//有账号的情况
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
			//没有账号,只有科目
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
	
	//格式化金额,处理科学计数法产生的问题
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
	
	//格式化金额,处理科学计数法产生的问题
	public String getChineseFormatAmount(double amount, long currencyID) throws Exception
	{
		String strChineseAmount = "零元";
		
		if(amount != 0.0)
		{
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			strChineseAmount = decimalFormat.format(amount);
			strChineseAmount = ChineseCurrency.showChinese(strChineseAmount, currencyID);
		}
		
		return strChineseAmount;
	}
	//得到连接
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