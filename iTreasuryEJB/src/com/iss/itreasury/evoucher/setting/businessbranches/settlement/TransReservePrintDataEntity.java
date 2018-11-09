package com.iss.itreasury.evoucher.setting.businessbranches.settlement;

import java.io.Serializable;
import java.util.HashMap;

import com.iss.itreasury.evoucher.util.PrintDataFormat;
import com.iss.itreasury.settlement.print.PrintInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transreserve.dao.TransReserveDao;
import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

public class TransReservePrintDataEntity implements Serializable {

	protected HashMap usedFields = new HashMap();
	
	
	
	public HashMap getOperationHashMap(long transTypeID, long lID) throws Exception
	{		
		PrintInfo printInfo = new PrintInfo();
		
		if( transTypeID == SETTConstant.TransactionType.RESERVEUPRECEIVE )
		{
			printInfo=this.getReserveUpReceivePrintInfo(lID);
		}
		else if(transTypeID == SETTConstant.TransactionType.RESERVERETURN)
		{
			printInfo=this.getReserveReturnPrintInfo(lID);
		}
		else
		{
			printInfo=null;
		}
		if(printInfo != null)
		{
			this.TransReservePrintData(printInfo);
		}			
		return usedFields;
	}
	//总部-准备金上收
	public PrintInfo getReserveUpReceivePrintInfo(long lID ) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransReserveDao reserveDepositDao = new TransReserveDao();
			TransReserveInfo resultInfo = null;
			resultInfo = reserveDepositDao.findByID(lID);
			
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
				else if (resultInfo.getBankid() > 0 )
				{
					printInfo.setPayBankID(resultInfo.getBankid());
				}
				
				if (resultInfo.getReserveaccountid() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReserveaccountid());
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getReserveaccountid()) );
					
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
	public PrintInfo getReserveReturnPrintInfo(long lID ) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		try
		{
			TransReserveDao reserveDepositDao = new TransReserveDao();
			TransReserveInfo resultInfo = null;
			resultInfo = reserveDepositDao.findByID(lID);
			
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

				if (resultInfo.getBakaccountid() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getBakaccountid());
					printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getBakaccountid()) );
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

	
	public void TransReservePrintData(PrintInfo printOperationInfo) throws Exception
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
				
				if(printOperationInfo.getExtClientName() != null && printOperationInfo.getExtClientName().trim().length() > 0 && printOperationInfo.getTransactionTypeID()==SETTConstant.TransactionType.RESERVERETURN)
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
	
			//签章中日期取执行日
			usedFields.put("SignatureDate", DataFormat.getDateString(printOperationInfo.getExecuteDate()));

	}
	
	
	

}
