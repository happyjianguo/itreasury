package com.iss.itreasury.evoucher.setting.businessbranches.settlement;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.evoucher.util.PrintDataFormat;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.print.IPrintTemplate;
import com.iss.itreasury.settlement.print.PrintInfo;
import com.iss.itreasury.settlement.print.PrintVoucher;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_SyndicationLoanInterestDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

public class TransInterestFeePaymentDataEntity implements Serializable {
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
			printInfo=this.getInterestFeePaymentPrintInfo(lID);		
			
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
			
		}
		//开户行
		else if( printOperationInfo.getPayBankID() > 0)
		{
			Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
			BranchInfo branchInfo = new BranchInfo();
			branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());			
			//付款人全称			
			usedFields.put("PayAccountName", DataFormat.formatString( branchInfo.getPrintName() ));
			//付款方账号
			usedFields.put("PayAccountNo", DataFormat.formatString( branchInfo.getBankAccountCode() ));
			//付款方开户行名称
			usedFields.put("PayBankName", DataFormat.formatString( branchInfo.getBranchName() ));			
			//付款方科目代码
			usedFields.put("PaySubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
		}else if(printOperationInfo.getExtClientName() != null && printOperationInfo.getExtClientName().trim().length() > 0)
		{
			//付款人全称
			usedFields.put("PayAccountName", printOperationInfo.getExtClientName());
			
			//付款方账号
			usedFields.put("PayAccountNo", printOperationInfo.getExtAccountNo());
			
			//付款方开户行名称
			usedFields.put("PayBankName", printOperationInfo.getExtRemitInBank());
		}
		if(printOperationInfo.getPayAccountID() < 0&&printOperationInfo.getPayInterestAccountID()>0)
		{
			usedFields.put("PayAccountNo",  NameRef.getAccountNoByID(printOperationInfo.getPayInterestAccountID()));
		}
		
		usedFields.put("PayInterestAccountID", NameRef.getAccountNoByID( printOperationInfo.getPayInterestAccountID() ));
		usedFields.put("ReceiveInterestAccountID", NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID() ));
		usedFields.put("ConsignClientNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));
		if (printOperationInfo.getReceiveAccountID() > 0)
		{
			usedFields.put("ConsignClientNo", NameRef.getAccountNoByID( printOperationInfo.getConsignAccountID() ));
		}
		//收款人全称
		usedFields.put("ReceiveAccountName", NameRef.getAccountNameByID( printOperationInfo.getReceiveAccountID() ));
		
		//收款方账号
		usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
		usedFields.put("ReceiveAccountCurrentNo", NameRef.getAccountNoByID( printOperationInfo.getConsignDepositAccountID()));
		usedFields.put("ReceiveAccountDepositNo", NameRef.getAccountNoByID( printOperationInfo.getConsignAccountID() ) );
		
		//收款方开户行名称
		usedFields.put("ReceiveBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
		//电子回单柜打印财务公司所在地点global.evoucher.printclientaddress(global.xml)
		String clientAddress = "";
		clientAddress = Config.getProperty(ConfigConstant.GLOBAL_EVOUCHER_PRINTCLIENTADDRESS);
		//收款人汇入地址
		usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(clientAddress) );
		if (printOperationInfo.getAmount() != 0.0)
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
		//利率
		usedFields.put("Rate", DataFormat.formatRate( printOperationInfo.getRate() ));
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
		//放款通知单号
		usedFields.put("PayFormNo", NameRef.getCommonPayFormNoByID( printOperationInfo.getLoanNoteID() ));
		
		//合同号
		usedFields.put("ContractNo", NameRef.getContractNoByID( printOperationInfo.getContractID() ));
		
		//放款通知单
		if(printOperationInfo.getLoanNoteID() > 0)
		{

			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(printOperationInfo.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
						
			//合同利率
			usedFields.put("ContractRate", DataFormat.formatRate( lpfdinfo.getInterestRate(), 6 ));
			
			//合同逾期利率
			usedFields.put("OverDueRate", DataFormat.formatRate( lpfdinfo.getOverDueInterestRate(), 6 ));
			
			//委托单位
			usedFields.put("ConsignUnit", lpfdinfo.getClientName());
			
			//利息费用支付委托贷款的委托单位 modify by xwhe 2008-07-15
			usedFields.put("ConsignClientName", NameRef.getClientNameByID(printOperationInfo.getConsignClientID()));			
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
			
            //利息费用支付委托贷款活期账户 modify by xwhe 2008-07-28
			usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));
			
			//贷款利息
			if (printOperationInfo.getRealInterest() > 0)
			{
				//正常利息开始日期					
				usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getLatestInterestClearDate() ) );
				
				//正常利息结束日期
				usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString(DataFormat.getPreviousDate( printOperationInfo.getInterestClearDate() )) );
				
				
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
				if(lpfdinfo.getIsRemitOverDueInterest() == 0)
				{
					usedFields.put("CompoundInterestRate", DataFormat.formatRate(lpfdinfo.getInterestRate(), 6 ));
				}
				else
				{
					usedFields.put("CompoundInterestRate", DataFormat.formatRate(lpfdinfo.getOverDueInterestRate(), 6 ));
				}
				
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
				if(lpfdinfo.getIsRemitOverDueInterest() == 0)
				{
					usedFields.put("OverInterestRate", DataFormat.formatRate(0.0 , 6 ));
				}
				else
				{
					usedFields.put("OverInterestRate", DataFormat.formatRate(lpfdinfo.getOverDueInterestRate(), 6 ));
				}
				
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
								
                //手续费利率 取放款通知单的手续费率		
				usedFields.put("CommissionFeeRate", DataFormat.formatRate( lpfdinfo.getCommissionRate(), 6 ));				
				//手续费利息
				if(printOperationInfo.getRealCommission()>0.0)
				{
					usedFields.put("CommissionFee", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID() )+DataFormat.formatListAmount(printOperationInfo.getRealCommission(), 2) );
					
                    //手续费利息大写 add by xwhe 2008-07-28 modify by xwhe 2008-08-04
					usedFields.put("chineseCommissionFee",PrintDataFormat.getChineseFormatAmount( printOperationInfo.getRealCommission(), printOperationInfo.getCurrencyID()));
					usedFields.put("ChineseCommissionFee",PrintDataFormat.getChineseFormatAmount( printOperationInfo.getRealCommission(), printOperationInfo.getCurrencyID()));
				}
				usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
			}
			
			//利息税费(2007-10-18 保利要求在委托贷款利息单上显示"利息税费")
			if (printOperationInfo.getRealInterestTax() > 0)
			{
				usedFields.put("InterestTax", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount(printOperationInfo.getRealInterestTax(), 2) );
				usedFields.put("ChineseInterestTax", ChineseCurrency.showChinese(DataFormat.formatListAmount(printOperationInfo.getRealInterestTax(), 2) , printOperationInfo.getCurrencyID()));
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
			
			//免还利息 
			double remitInterest = DataFormat.formatDouble(printOperationInfo.getInterest() - printOperationInfo.getRealInterest());
			if (remitInterest > 0)
			{
				usedFields.put("RemitInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( remitInterest ,2 ) );
			}
			//实际划转金额  
			double transferAmount = DataFormat.formatDouble(printOperationInfo.getRealInterest()-printOperationInfo.getRealInterestTax());
			if (transferAmount > 0)
			{
				usedFields.put("TransferAmount",Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount( transferAmount ,2 ) );
			}			
			
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

				usedFields.put("InterestTaxofbusiness",
						DataFormat.formatListAmount(
						(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05, 2 ) );
		
				usedFields.put("InterestTaxofconstruction",
						DataFormat.formatListAmount(
						(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.07, 2 ) );
		
				usedFields.put("InterestTaxofeducation",
						DataFormat.formatListAmount(
						(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.03, 2 ) );			
			

				usedFields.put("chineseInterestTaxofbusiness",
						ChineseCurrency.showChinese(DataFormat.formatAmount(
						(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05, 2 ) ));
		
				usedFields.put("chineseInterestTaxofconstruction",
						ChineseCurrency.showChinese(DataFormat.formatAmount(
						(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.07, 2 ) ));
		
				usedFields.put("chineseInterestTaxofeducation",
						ChineseCurrency.showChinese(DataFormat.formatAmount(
						(DataFormat.formatDouble(printOperationInfo.getRealInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealCompoundInterest()) + 
						DataFormat.formatDouble(printOperationInfo.getRealOverDueInterest()))*0.05*0.03, 2 ) ));
			}	  
				
		}		
		//借款单位(收款方客户)(自营贷款账户)
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

		if(printOperationInfo.getAccountTypeID() >0)
		{
			usedFields.put("AccountTypeName", SETTConstant.AccountType.getName( printOperationInfo.getAccountTypeID() ) );
		}
		if(printOperationInfo.getRealInterest() > 0.00)		{
		
			//Boxu 2008年12月18日 这里显示有冲突,统一显示业务类型
			usedFields.put("BusinessTypeName", SETTConstant.AccountType.getName( printOperationInfo.getAccountTypeID() ) );
		}
		if(printOperationInfo.getRealCommission() > 0.00)
		{			
			usedFields.put("BusinessTypeName", SETTConstant.AccountType.getName( printOperationInfo.getAccountTypeID() ) );
			
 		 	if(printOperationInfo.getPayCommissionAccountID() > 0) 
            {
                usedFields.put("CommissionPayAccountName", NameRef.getAccountNameByID(printOperationInfo.getPayCommissionAccountID()));
                usedFields.put("CommissionPayAccountNo", NameRef.getAccountNoByID(printOperationInfo.getPayCommissionAccountID()));
                usedFields.put("CommissionPayBankName", NameRef.getOfficeNameByID(printOperationInfo.getOfficeID()));
            } 
            else if(printOperationInfo.getPayCommissionBankID() > 0)
            {
                Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
                BranchInfo branchInfo = new BranchInfo();
                branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayCommissionBankID());
                usedFields.put("CommissionPayAccountName", DataFormat.formatString(branchInfo.getPrintName()));  
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
		}
		//账号
		usedFields.put("AccountNo",NameRef.getAccountNoByID( printOperationInfo.getConsignAccountID()));
	
		
		
		//add by xiangzhou 配置签章显示位置 20110921
		String signPosition = Config.getProperty(ConfigConstant.GLOBAL_SIGNATURE_POSITION, "0;0");
		if(signPosition.indexOf(";")>0){
			usedFields.put("signPositionX", signPosition.split(";")[0]);
			usedFields.put("signPositionY", signPosition.split(";")[1]);
		}else{
			usedFields.put("signPositionX", "0");
			usedFields.put("signPositionY", "0");
		}
		
		String signDatePosition = Config.getProperty(ConfigConstant.GLOBAL_SIGNATUREDATE_POSITION, "0;0");
		if(signDatePosition.indexOf(";")>0){
			usedFields.put("signDatePositionX", signDatePosition.split(";")[0]);
			usedFields.put("signDatePositionY", signDatePosition.split(";")[1]);
		}else{
			usedFields.put("signDatePositionX", "0");
			usedFields.put("signDatePositionY", "0");
		}
		
	}
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
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setEndDate(resultInfo.getInterestClear());
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

				if (resultInfo.getCommissionAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getCommissionAccountID());
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
				//收息账户
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
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
                //通过合同号查找委托单位id

				if (resultInfo.getLoanContractID() > 0)
				{
					ContractInfo cInfo = new ContractInfo();
					ContractDao cDao = new ContractDao();
					cInfo = cDao.findContractInfoByID(resultInfo.getLoanContractID());
					printInfo.setConsignClientID(cInfo.getClientID());
				}
				
				if (resultInfo.getLoanRepaymentRate() > 0)
				{
					printInfo.setRate(resultInfo.getLoanRepaymentRate()*100);
				}
				
                //贷款分段计息
				if(resultInfo.getSubAccountID() > 0 && resultInfo.getOfficeID()>0 && resultInfo.getOfficeID() > 0 && resultInfo.getInterestClear()!=null)
				{
					Connection conInternal = null;
					Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
					Vector vector = null;
					vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getTransactionTypeID(),resultInfo.getSubAccountID(),resultInfo.getLoanAccountID(),resultInfo.getOfficeID(),resultInfo.getOfficeID(),resultInfo.getInterestClear());			
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

}
