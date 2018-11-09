package com.iss.itreasury.evoucher.setting.businessbranches.settlement;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.iss.itreasury.settlement.print.PrintInfo;
import com.iss.itreasury.settlement.transinternallend.dao.Sett_TransInternalLendDAO;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.evoucher.util.PrintDataFormat;

public class TransInternalLendDateEntity {
	protected HashMap usedFields = new HashMap();	
	public HashMap getOperationHashMap(long transTypeID, long lID) throws Exception
	{		
		PrintInfo printInfo = new PrintInfo();
		printInfo = this.getPrintInfo(lID, transTypeID);
		
		if(printInfo != null)
		{
			this.TransformOperationData(printInfo);
		}			
		return usedFields;
	}
	public PrintInfo getPrintInfo(long lID,long lTransactionTypeID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			if( lTransactionTypeID == SETTConstant.TransactionType.INERLENDING )
			{
				printInfo=this.getTransInternalLendPrintInfo(lID);
			}
			else if(lTransactionTypeID == SETTConstant.TransactionType.INERLENDINGRETURN)
			{
				printInfo=this.getTransInternalLendRepaymentPrintInfo(lID);
			}
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
		return printInfo;
	}
	
	public void TransformOperationData(PrintInfo printOperationInfo) throws Exception
	{
		//客户名称
		usedFields.put("ClientName", Env.getClientName());	
		//执行日
		usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //显示YYYY年MM月DD日
		//起息日		
		usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //显示YYYY年MM月DD日		
		//当前时间		
		usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //显示YYYY年MM月DD日		
		//交易编号
		usedFields.put("TransNo", printOperationInfo.getTransNo());
		//借款单位
		usedFields.put("LoanUnit", NameRef.getAccountNameByID( printOperationInfo.getLoanAccountID() ));
		//还款单位
		usedFields.put("RepaymentUnitName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));
		//拆借账户号
		usedFields.put("LoanAccountNo", NameRef.getAccountNoByID( printOperationInfo.getLoanAccountID() ));
		//币种
		usedFields.put("CurrencyName", Constant.CurrencyType.getName( printOperationInfo.getCurrencyID() ));
		if(printOperationInfo.getPayAccountID() > 0)
		{
			//付款人全称
			usedFields.put("PayAccountName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));			
			//付款方账号
			usedFields.put("PayAccountNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));			
			//付款方开户行名称
			usedFields.put("PayBankName", NameRef.getOpenOrganizationNameByAccountID(printOperationInfo.getPayAccountID()));
			//付款方开户行
			usedFields.put("PayBank", NameRef.getOpenOrganizationNameByAccountID(printOperationInfo.getPayAccountID()));
		}
		if(printOperationInfo.getReceiveAccountID()>0){
			//收款人全称
			usedFields.put("ReceiveAccountName", NameRef.getAccountNameByID( printOperationInfo.getReceiveAccountID() ));
			//收款方开户行名称
			usedFields.put("ReceiveBankName", NameRef.getOpenOrganizationNameByAccountID(printOperationInfo.getReceiveAccountID()));
			//收款方开户行
			usedFields.put("ReceiveBank", NameRef.getOpenOrganizationNameByAccountID(printOperationInfo.getReceiveAccountID()));
			//收款方账号
			usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
		}
		if(printOperationInfo.getAmount() != 0.0)
		{
			//本金金额小写
			usedFields.put("Amount", PrintDataFormat.getFormatAmount( printOperationInfo.getAmount() ));
			
			//本金金额大写
			usedFields.put("ChineseAmount", PrintDataFormat.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
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
		//摘要
		String strAbstract = "起息日：" + DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate()); 
		if(printOperationInfo.getAbstract()!=null&&printOperationInfo.getAbstract().trim().length()>0)
		{
			strAbstract = strAbstract + "；";
			strAbstract = strAbstract + printOperationInfo.getAbstract();
		}         
		usedFields.put("Abstract",strAbstract);
		//贷款余额(格式化余额,为"0"时显示"0.00")
		usedFields.put("CurrentBalance", DataFormat.formatListAmount( printOperationInfo.getCurrentBalance(), 2 ) );
		
	}	
	public PrintInfo getTransInternalLendPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
			TransInternalLendInfo resultInfo = null;
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
				if (resultInfo.getTransNO() != null && resultInfo.getTransNO().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNO());
				}
				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
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
				//放款类型
				//借款账户,即拆借账户号
				if (resultInfo.getLendingAccountID() > 0)
				{
					printInfo.setLoanAccountID(resultInfo.getLendingAccountID());
					printInfo.setPayAccountID(resultInfo.getLendingAccountID());
				}
				//收款方
				if (resultInfo.getReserveAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReserveAccountID());
				}								
				if (resultInfo.getLendingAccountID() > 0)
				{
					printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getLendingAccountID()));
				}
			}
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}		
		return printInfo;
	}
	public PrintInfo getTransInternalLendRepaymentPrintInfo(long lID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
			TransInternalLendInfo resultInfo = null;
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
				if (resultInfo.getTransNO() != null && resultInfo.getTransNO().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNO());
				}
				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
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
				//收款账户
				if (resultInfo.getLendingAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getLendingAccountID());
					printInfo.setCurrentBalance(NameRef.getBalanceByAccountID(resultInfo.getLendingAccountID() ,true));
				}
				//付款账户
				if (resultInfo.getReserveAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getReserveAccountID());
				}								
				if (resultInfo.getReserveAccountID() > 0)
				{
					printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getReserveAccountID()));
				}				
			}
			
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}		
		return printInfo;
	}

}
