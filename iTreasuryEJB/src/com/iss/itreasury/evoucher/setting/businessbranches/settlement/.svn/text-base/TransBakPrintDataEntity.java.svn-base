package com.iss.itreasury.evoucher.setting.businessbranches.settlement;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.evoucher.util.PrintDataFormat;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_TransInterestSettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.print.PrintInfo;
import com.iss.itreasury.settlement.print.PrintVoucher;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transbakreserve.dao.TransBakReserveDao;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

public class TransBakPrintDataEntity implements Serializable
{
	protected HashMap usedFields = new HashMap();
	
	
	
	public HashMap getOperationHashMap(long transTypeID, long lID) throws Exception
	{		
		PrintInfo printInfo = new PrintInfo();
		
		if( transTypeID == SETTConstant.TransactionType.BAKUPRECEIVE )
		{
			printInfo=this.getBakUpReceivePrintInfo(lID);
		}
		else if(transTypeID == SETTConstant.TransactionType.BAKRETURN)
		{
			printInfo=this.getBakReturnPrintInfo(lID);
		}
		else if(transTypeID == SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST || transTypeID == SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST)
		{
			printInfo=this.getTransBakInterestPrintInfo(lID);
		}
		else
		{
			printInfo=null;
		}
		if(printInfo != null)
		{
			this.TransBakPrintData(printInfo);
		}			
		return usedFields;
	}
	//总部-准备金上收
	public PrintInfo getBakUpReceivePrintInfo(long lID ) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransBakReserveDao bakDepositDao = new TransBakReserveDao();
			TransBakReserveInfo resultInfo = null;
			resultInfo = bakDepositDao.findByID(lID);
			
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
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				
				if (resultInfo.getReserveaccountid() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getReserveaccountid());
					printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getReserveaccountid()));
				}
				else if (resultInfo.getBankid() > 0 )
				{
					printInfo.setPayBankID(resultInfo.getBankid());
				}
				
				if (resultInfo.getBakaccountid() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getBakaccountid());
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getBakaccountid()) );
					
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
				if (resultInfo.getDtexecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getDtexecute());
				}
				if (resultInfo.getDtintereststart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getDtintereststart());
				}
				if (resultInfo.getStransno() != null && resultInfo.getStransno().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getStransno());
				}
				
				if (resultInfo.getNofficeid() > 0)
				{
					printInfo.setOfficeID(resultInfo.getNofficeid());
				}
				if (resultInfo.getNcurrencyid() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getNcurrencyid());
				}
				if (resultInfo.getAmount() > 0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				if(resultInfo.getNtransactiontypeid()>0)
				{
					printInfo.setTransactionTypeID(resultInfo.getNtransactiontypeid());
					
				}
				if(resultInfo.getBranchid()>0)
				{
					printInfo.setClientId(resultInfo.getBranchid());
					
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
	
	//总部-准备金上收
	public PrintInfo getBakReturnPrintInfo(long lID ) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransBakReserveDao bakDepositDao = new TransBakReserveDao();
			TransBakReserveInfo resultInfo = null;
			resultInfo = bakDepositDao.findByID(lID);
			
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
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
					
				if (resultInfo.getBakaccountid() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getBakaccountid());
					printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getBakaccountid()));
				}

				if (resultInfo.getReserveaccountid() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReserveaccountid());
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getReserveaccountid()) );
				}
				else if (resultInfo.getBankid() > 0)
				{
					printInfo.setReceiveBankID(resultInfo.getBankid());
				}
								
				if (resultInfo.getSextaccountno() != null && resultInfo.getSextaccountno().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getSextaccountno());
				}
				if (resultInfo.getSextclientname() != null && resultInfo.getSextclientname().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getSextclientname());
				}
				if (resultInfo.getSremitinbank() != null && resultInfo.getSremitinbank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getSremitinbank());
				}
				if (resultInfo.getSremitinprovince() != null && resultInfo.getSremitinprovince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getSremitinprovince());
				}
				if (resultInfo.getSremitincity() != null && resultInfo.getSremitincity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getSremitincity());
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
				if (resultInfo.getDtexecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getDtexecute());
				}
				if (resultInfo.getDtintereststart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getDtintereststart());
				}
				if (resultInfo.getStransno() != null && resultInfo.getStransno().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getStransno());
				}
				
				if (resultInfo.getNofficeid() > 0)
				{
					printInfo.setOfficeID(resultInfo.getNofficeid());
				}
				if (resultInfo.getNcurrencyid() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getNcurrencyid());
				}
				if (resultInfo.getAmount() > 0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				if(resultInfo.getNtransactiontypeid()>0)
				{
					printInfo.setTransactionTypeID(resultInfo.getNtransactiontypeid());
					
				}
				if(resultInfo.getBranchid()>0)
				{
					printInfo.setClientId(resultInfo.getBranchid());
					
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
	public PrintInfo getTransBakInterestPrintInfo(long lID) throws Exception
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
				if(resultInfo.getInterestTax()>0){ 
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
				
				//活期结息(协定)分段计息
				if(resultInfo.getSubAccountID() > 0 && resultInfo.getOfficeID()>0 && resultInfo.getOfficeID() > 0 && resultInfo.getInterestSettlementDate()!=null && resultInfo.getTransactionTypeID()!=SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST)
				{
					Connection conInternal = null;
					Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
					Vector vector = null;
					Vector vector1 = null;
					vector = (Vector)dailAccountDAO.selectSettlementInterest(resultInfo.getSubAccountID(),resultInfo.getAccountID(),resultInfo.getOfficeID(),resultInfo.getOfficeID(),resultInfo.getInterestSettlementDate());			
					vector1 = (Vector)dailAccountDAO.selectSettNegotiateInterest(resultInfo.getSubAccountID(),resultInfo.getAccountID(),resultInfo.getOfficeID(),resultInfo.getOfficeID(),resultInfo.getInterestSettlementDate());	
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
							}
							if(i==1)
							{
							printInfo.setStartDate2(dailAccInfo.getStartDate());
							printInfo.setEndDate2(dailAccInfo.getDate());
							printInfo.setRate2(dailAccInfo.getInterestRate());
							printInfo.setInterest2(dailAccInfo.getInterest());
							}
							if(i==2)
							{
							printInfo.setStartDate3(dailAccInfo.getStartDate());
							printInfo.setEndDate3(dailAccInfo.getDate());
							printInfo.setRate3(dailAccInfo.getInterestRate());
							printInfo.setInterest3(dailAccInfo.getInterest());
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
							}
							if(i==1)
							{
							printInfo.setNegotiateStartDate2(dailAccInfo.getStartDate());
							printInfo.setNegotiateEndtDate2(dailAccInfo.getDate());
							printInfo.setNegotiateRate2(dailAccInfo.getInterestRate());
							printInfo.setNegotiateInterest2(dailAccInfo.getInterest());
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

	
	public void TransBakPrintData(PrintInfo printOperationInfo) throws Exception
	{

			//客户名称
			usedFields.put("ClientName", Env.getOfficeName(printOperationInfo.getClientId()));//总部-备付金业务 的客户为某个机构办事处
			
			//执行日
			usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //显示YYYY年MM月DD日
			
			//起息日
			usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //显示YYYY年MM月DD日

			//当前时间
			usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //显示YYYY年MM月DD日
			
			//交易编号
			usedFields.put("TransNo", printOperationInfo.getTransNo());
			
			//操作当前办事处名称（总部-名称）
			usedFields.put("OfficeName",NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));

			
			
			if(printOperationInfo.getPayAccountID()>0)
			{
				//付款人全称
				//usedFields.put("PayAccountName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));
				usedFields.put("PayAccountName", NameRef.getOfficeNameByID( printOperationInfo.getClientId() ));
				
				//付款方账号
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));
				

				
				if(printOperationInfo.getReceiveBankID()>0)
				{
					usedFields.put("PayBankName", NameRef.getBankNameByID( printOperationInfo.getReceiveBankID() ));					
				}
				else
				{
					//付款方财务公司（机构）名称
					usedFields.put("PayBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
					
				}
				
				//付款方科目代码
				usedFields.put("PaySubjectCode", DataFormat.formatString(NameRef.getSubjectByAccountID(printOperationInfo.getPayAccountID()) ));
			}
			else if(printOperationInfo.getPayBankID()>0){
				
				//付款人全称
				usedFields.put("PayAccountName", NameRef.getOfficeNameByID( printOperationInfo.getClientId() ));
				
				//付款方账号
				usedFields.put("PayAccountNo", NameRef.getBankAccountCodeByBankID( printOperationInfo.getPayBankID() ));
				
				//付款方开户行名称
				usedFields.put("PayBankName", NameRef.getBankNameByID( printOperationInfo.getPayBankID() ));
				
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());
				//付款方科目代码
				usedFields.put("PaySubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
			}
			
			if(printOperationInfo.getReceiveAccountID()>0)
			{
				//收款人全称
				//usedFields.put("ReceiveAccountName", NameRef.getAccountNameByID( printOperationInfo.getReceiveAccountID() ));
				usedFields.put("ReceiveAccountName", NameRef.getOfficeNameByID( printOperationInfo.getClientId() ));
				//收款方账号
				usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
	
				//收款方机构名称
				usedFields.put("ReceiveBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
				
				//收款方科目代码
				usedFields.put("ReceiveSubjectCode", DataFormat.formatString(NameRef.getSubjectByAccountID(printOperationInfo.getReceiveAccountID()) ));
			}
			else if(printOperationInfo.getReceiveBankID()>0)//开户行
			{
				//收款人全称
				usedFields.put("ReceiveAccountName", NameRef.getOfficeNameByID( printOperationInfo.getClientId() ));
				
				//收款方账号
				usedFields.put("ReceiveAccountNo", NameRef.getBankAccountCodeByBankID( printOperationInfo.getReceiveBankID() ));
				
				//收款方开户行名称
				usedFields.put("ReceiveBankName", NameRef.getBankNameByID( printOperationInfo.getReceiveBankID() ));
				
				if(printOperationInfo.getExtClientName() != null && printOperationInfo.getExtClientName().trim().length() > 0 && printOperationInfo.getTransactionTypeID()==SETTConstant.TransactionType.BAKRETURN)
				{
					//付款人全称
					usedFields.put("ReceiveAccountName", printOperationInfo.getExtClientName());
					
					//付款方账号
					usedFields.put("ReceiveAccountNo", printOperationInfo.getExtAccountNo());
					
					//付款方开户行名称
					usedFields.put("ReceiveBankName", printOperationInfo.getExtRemitInBank());
				}
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());
				//收款方科目代码
				usedFields.put("ReceiveSubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
			}



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
			usedFields.put("Abstract", printOperationInfo.getAbstract());
			
			if (printOperationInfo.getCurrencyID() > 0)
			{
				//币种
				usedFields.put("CurrencyName", Constant.CurrencyType.getName( printOperationInfo.getCurrencyID() ));
			}
			
			//摘要
			/*if(printOperationInfo.getInterestStartDate() != null)
			{
				String strAbstract = "";
				
				strAbstract = "起息日：" + DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() );
				
				if(printOperationInfo.getAbstract() != null && !printOperationInfo.getAbstract().equals(""))
				{
					strAbstract += "；";
					strAbstract += printOperationInfo.getAbstract();
				}

				usedFields.put("Abstract", strAbstract);
			}
			else
			{
				usedFields.put("Abstract", printOperationInfo.getAbstract());
			}*/
			usedFields.put("Abstract", printOperationInfo.getAbstract());
	
			// 签章中日期取执行日
			usedFields.put("SignatureDate", DataFormat.getDateString(printOperationInfo.getExecuteDate()));
			
			
			
			
			//收息账户号
			if (printOperationInfo.getReceiveInterestAccountID() > 0)
			{
				//收款方账号(收息账户号)
				usedFields.put("InterestextAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID()));
			}
			
			
			if (printOperationInfo.getPayAccountID() > 0 && printOperationInfo.getTransactionTypeID()==SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST)
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
			
			//利率
			usedFields.put("Rate", DataFormat.formatRate( printOperationInfo.getRate() ));
			
			
			//业务名称
			if(printOperationInfo.getTransTypeID() != -1)
			{
				usedFields.put("TransactionTypeName", SETTConstant.TransactionType.getName( printOperationInfo.getTransTypeID() ));
			}
			
			//账户所属单位名称
			usedFields.put("AccountClientName", NameRef.getClientNameByAccountID( printOperationInfo.getPayAccountID() ) );
			
			
			
			//存放数据变量
			String Amount1 = "";  //本金1
			String Amount2 = "";  //本金2
			String Amount3 = "";  //本金3
			String Amount4 = "";  //本金4
			//add by dwj 20110503 分段平均金额
			String AmountAverage1 = "";  //本金1
			String AmountAverage2 = "";  //本金2
			String AmountAverage3 = "";  //本金3
			String AmountAverage4 = "";  //本金4
			//end add by dwj 20110503
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
			//add by dwj 20110503
			String DayNumber5 = "";  //天数5
			//end add by dwj 20110503
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
			String NegotiateDays1="";//协定解析天数1
			String NegotiateDays2="";//协定解析天数2
			String NegotiateRate1 = "";//协定利率1
			String NegotiateRate2 = "";//协定利率1
			String NegotiateInterest1 ="";//协定利息
			String NegotiateInterest2 ="";//协定利息
			//计付利息合计
			String totalInterest = "";
			
			
			if(printOperationInfo.getPecisionInterestAmount() != 0.0)
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
							CurrentAmass = DataFormat.formatAmount(
								UtilOperation.Arith.div(
									//modify by xwhe 2008-10-28	
									//UtilOperation.Arith.mul(printOperationInfo.getCurrentInterest(), 360)
									UtilOperation.Arith.mul(printOperationInfo.getPecisionInterestAmount(), 360)
								  , UtilOperation.Arith.div(printOperationInfo.getRate(), 100)
								)
							);
						}
		                //modify by xwhe 2008-08-21 活期分段计息暂时分三段
						if(printOperationInfo.getRate1() != 0){			
						
						Interest1 = DataFormat.formatAmount(printOperationInfo.getInterest1());
						
						Rate1 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
						
						BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
						//BeginDate1 = "2008年10月21日";
						
						OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
						DayNumber1 = Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getStartDate(),printOperationInfo.getEndDate1()));	
						//活期积数1
						if(printOperationInfo.getRate1() != 0)
						{
							Connection conInternal = null;
							Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
							//暂时注掉2008-11-20
							//Timestamp sdate = DataFormat.getDateTime("2008-10-21");
							double CurrentAmass111 = dailAccountDAO.currentAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getStartDate1(),printOperationInfo.getEndDate1());										
							CurrentAmass1 = CurrentAmass111+"";
							//add by dwj 20110503 添加平均金额
							double dAmountAverage1 = CurrentAmass111/Double.parseDouble(DayNumber1);
							AmountAverage1 = dAmountAverage1 + "";
							//end add by dwj 20110503
							//	CurrentAmass1 = DataFormat.formatAmount(
						//		UtilOperation.Arith.div(
						//			UtilOperation.Arith.mul(printOperationInfo.getInterest1(), 360)
						//		  , UtilOperation.Arith.div(printOperationInfo.getRate1(), 100)
						//		)
						//	);
						}
						}
						
						if(printOperationInfo.getRate2() != 0)
						{      
						
						Interest4 = DataFormat.formatAmount(printOperationInfo.getInterest2());
						
						Rate4 = DataFormat.formatRate( printOperationInfo.getRate2(), 6 );
						
						BeginDate4 = DataFormat.getChineseDateString(printOperationInfo.getStartDate2());
						
						OverDate4 = DataFormat.getChineseDateString(printOperationInfo.getEndDate2());
						DayNumber4 = Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getStartDate2(),printOperationInfo.getEndDate2()));	
		               //活期积数2
						if(printOperationInfo.getRate2() != 0)
						{
							//modify by xwhe 2008-11-28
							Connection conInternal = null;
							Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
							
							double CurrentAmass112 = dailAccountDAO.currentAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getStartDate2(),printOperationInfo.getEndDate2());
						//	CurrentAmass2 = DataFormat.formatAmount(
						//		UtilOperation.Arith.div(
						//			UtilOperation.Arith.mul(printOperationInfo.getInterest2(), 360)
						//		  , UtilOperation.Arith.div(printOperationInfo.getRate2(), 100)
						//		)
						//	);
							CurrentAmass2 = CurrentAmass112+"";
							//add by dwj 20110503 添加平均金额
							double dAmountAverage2 = CurrentAmass112/Double.parseDouble(DayNumber4);
							AmountAverage2 = dAmountAverage2 + "";
							//end add by dwj 20110503
						}
						}
						if(printOperationInfo.getRate3()!=0)
						{
		                Interest5 = DataFormat.formatAmount(printOperationInfo.getInterest3());
						
						Rate5 = DataFormat.formatRate( printOperationInfo.getRate3(), 6 );
						
						BeginDate5 = DataFormat.getChineseDateString(printOperationInfo.getStartDate3());
						
						OverDate5 = DataFormat.getChineseDateString(printOperationInfo.getEndDate3());
						//add by dwj 20110503
						DayNumber5 = Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getStartDate3(),printOperationInfo.getEndDate3()));	
						//end add by dwj 20110503
						//活期积数3
						if(printOperationInfo.getRate3() != 0)
						{
						//	CurrentAmass3 = DataFormat.formatAmount(
						//		UtilOperation.Arith.div(
						//			UtilOperation.Arith.mul(printOperationInfo.getInterest3(), 360)
						//		  , UtilOperation.Arith.div(printOperationInfo.getRate3(), 100)
						//		)
						//	);
		                //modify by xwhe 2008-11-28
							Connection conInternal = null;
							Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
							double CurrentAmass113 = dailAccountDAO.currentAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getStartDate3(),printOperationInfo.getEndDate3());
							CurrentAmass3 = CurrentAmass113+"";
							
							//add by dwj 20110503 添加平均金额
							double dAmountAverage3 = CurrentAmass113/Double.parseDouble(DayNumber5);
							AmountAverage3 = dAmountAverage3 + "";
							//end add by dwj 20110503
						}
						}	
						//协定存款的算息(协定存款本金)
						if (printOperationInfo.getAccordInterestAmount() > 0)
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
								AccordAmass = DataFormat.formatAmount(
									UtilOperation.Arith.div(
										UtilOperation.Arith.mul(printOperationInfo.getAccordInterest(), 360)
									  , UtilOperation.Arith.div(printOperationInfo.getAccordInterestRate(), 100)
									)
								);
							}
							//add by xwhe  2008-08-26
							if(printOperationInfo.getNegotiateRate1() != 0)
							{
						    NegotiateStartDate1 = DataFormat.getChineseDateString(printOperationInfo.getNegotiateStartDate1());
							NegotiateEndtDate1 = DataFormat.getChineseDateString(printOperationInfo.getNegotiateEndtDate1());
						    NegotiateRate1 = DataFormat.formatRate( printOperationInfo.getNegotiateRate1(), 6 );
							NegotiateDays1=Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getNegotiateStartDate1(),printOperationInfo.getNegotiateEndtDate1()));	
							NegotiateRate1 = DataFormat.formatRate( printOperationInfo.getNegotiateRate1(), 6 );
						    NegotiateInterest1 = DataFormat.formatAmount(printOperationInfo.getNegotiateInterest1());
							
						    Connection conInternal = null;
							Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
							double AccordAmass11 = dailAccountDAO.negoAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getNegotiateStartDate1(),printOperationInfo.getNegotiateEndtDate1());
						   
							//AccordAmass1 = DataFormat.formatAmount(
							//		UtilOperation.Arith.div(
							//			UtilOperation.Arith.mul(printOperationInfo.getNegotiateInterest1(), 360)
							//		  , UtilOperation.Arith.div(printOperationInfo.getNegotiateRate1(), 100)
							//		)
							//	);
							AccordAmass1 = AccordAmass11+"";
							}
		                    //add by xwhe  2008-08-26
							if(printOperationInfo.getNegotiateRate2() != 0)
							{
							NegotiateStartDate2 = DataFormat.getChineseDateString(printOperationInfo.getNegotiateStartDate2());
							NegotiateEndtDate2 = DataFormat.getChineseDateString(printOperationInfo.getNegotiateEndtDate2());
							NegotiateDays2=Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getNegotiateStartDate2(),printOperationInfo.getNegotiateEndtDate2()));	
							NegotiateRate2 = DataFormat.formatRate( printOperationInfo.getNegotiateRate2(), 6 );
							NegotiateInterest2 = DataFormat.formatAmount(printOperationInfo.getNegotiateInterest2());
							Connection conInternal = null;
							Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
							double AccordAmass12 = dailAccountDAO.negoAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getNegotiateStartDate2(),printOperationInfo.getNegotiateEndtDate2());
							AccordAmass2 = AccordAmass12+"";
						//	AccordAmass2 = DataFormat.formatAmount(
						//			UtilOperation.Arith.div(
						//				UtilOperation.Arith.mul(printOperationInfo.getNegotiateInterest2(), 360)
						//			  , UtilOperation.Arith.div(printOperationInfo.getNegotiateRate2(), 100)
						//			)
						//		);
							}
						}
						
						//利息合计
						totalInterest = (
								DataFormat.formatAmount(
										DataFormat.formatDouble(
												DataFormat.formatDouble(printOperationInfo.getCurrentInterest()) + DataFormat.formatDouble(printOperationInfo.getAccordInterest()))));
					}
			}
			else if( printOperationInfo.getCurrentInterest() != 0.0 )
			{
				

				//如起息日在终止日期之前，即提前支取
				if ( printOperationInfo.getInterestStartDate() != null 
				  && printOperationInfo.getEndDate() != null 
				  && printOperationInfo.getInterestStartDate().before(printOperationInfo.getEndDate()) )
				{
					//本金1，活期利息，活期利息利率，开始日期，结束日期，天数
					Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
					
					Interest1 = DataFormat.formatAmount(printOperationInfo.getCurrentInterest());
					
					//Rate1 = DataFormat.formatAmount(Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.98));
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
							//DayNumber2 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "";
						}
						else
						{
							//通知支取
							if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
							{
								//DayNumber2 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "";
							}
							else
							{
								//DayNumber2 = ((PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), 1) + intervalDaysFlag) + "");
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
								//	- DataFormat.formatDouble(printOperationInfo.getCurrentInterest())
								//	- DataFormat.formatDouble(printOperationInfo.getOtherInterest())
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
					
					//Rate2 = DataFormat.formatAmount(Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.98));
					//不再从配置文件取,从表 sett_TransFixedWithDraw 取 advanceRate
					Rate2 = DataFormat.formatRate( printOperationInfo.getNewRate(), 6 );
					
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
			
			if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST)
			{
				depositTypeName = "备付金存款结息";
			}
			else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST)
			{
				depositTypeName = "备付金存款计提利息";
			}
			usedFields.put("depositTypeName", depositTypeName);
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
				usedFields.put("Interest1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest1), 2 ));  //利息1
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
				usedFields.put("Interest4", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest4), 2 ));  //利息4
			}
			if (Interest5 != null && !Interest5.equals(""))
			{
				usedFields.put("Interest5", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest5), 2 ));  //利息5
			}
			if (NegotiateInterest1 != null && !NegotiateInterest1.equals(""))
			{
				usedFields.put("NegotiateInterest1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(NegotiateInterest1), 2 ));  //协定利息1
			}
			if (NegotiateInterest2 != null && !NegotiateInterest2.equals(""))
			{
				usedFields.put("NegotiateInterest2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(NegotiateInterest2), 2 ));  //协定利息2
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
			usedFields.put("NegotiateDays1", NegotiateDays1);
			usedFields.put("NegotiateDays2", NegotiateDays2);
			
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
			
			//add by dwj 20110503 平均金额
			if (AmountAverage1 != null && !AmountAverage1.equals(""))
			{
				usedFields.put("AmountAverage1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AmountAverage1), 2 ));  //活期积数3
			}
			if (AmountAverage2 != null && !AmountAverage2.equals(""))
			{
				usedFields.put("AmountAverage2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AmountAverage2), 2 ));  //活期积数3
			}
			if (AmountAverage3 != null && !AmountAverage3.equals(""))
			{
				usedFields.put("AmountAverage3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AmountAverage3), 2 ));  //活期积数3
			}
			//end add by dwj 20110503
			
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
			
			//计付利息合计大写
			String strChineseAmount = ChineseCurrency.showChinese(totalInterest, printOperationInfo.getCurrencyID());
			usedFields.put("ChineseInterestTotal", strChineseAmount);
			
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
	}
	
	
	
}
