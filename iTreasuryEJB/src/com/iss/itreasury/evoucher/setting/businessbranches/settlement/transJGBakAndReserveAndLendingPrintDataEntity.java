package com.iss.itreasury.evoucher.setting.businessbranches.settlement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.iss.inut.workflow.ws.WorkflowManager;
import com.iss.itreasury.evoucher.util.PrintDataFormat;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.print.PrintInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transreserve.dao.TransReserveDao;
import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.bizlogic.InutApprovalRecordBiz;
import com.iss.itreasury.system.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

public class transJGBakAndReserveAndLendingPrintDataEntity implements Serializable {

	protected HashMap usedFields = new HashMap();

	
	public HashMap getOperationHashMap(long transTypeID, long lID) throws Exception
	{		
		PrintInfo printInfo = new PrintInfo();
		
		printInfo=this.getJGPrintInfo(lID,transTypeID);

		if(printInfo != null)
		{
			this.TransJGPrintData(printInfo);
		}			
		return usedFields;
	}
	
	//特殊业务
	public PrintInfo getJGPrintInfo(long lID,long transTypeID) throws Exception
	{
		PrintInfo printInfo = new PrintInfo();
		
		try
		{
			Sett_TransSpecialOperationDAO tsodao = new Sett_TransSpecialOperationDAO();
			TransSpecialOperationInfo resultInfo=null;
			resultInfo = tsodao.findByID(lID);

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
				if(resultInfo.getNinputuserid()>0  && resultInfo.getNofficeid()>0 && resultInfo.getNcurrencyid()>0)
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

                TransSpecialOperationDelegation  specialDelegation = new TransSpecialOperationDelegation();
				
				TransSpecialOperationInfo  specialInfo = new TransSpecialOperationInfo();			
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
                BranchInfo branchInfo = new BranchInfo();
                					
				long payBankID = -1;//付款方银行ID
				long receiveBankID = -1;//收款方银行ID
				String payAccountNo = "";//付款方银行帐号
 				String receiveAccountNo = "";//收款方银行帐号
 				specialInfo = specialDelegation.findDetailByID( resultInfo.getId() );	
				
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

				if (resultInfo.getNtransactiontypeid() > 0)
				{
					printInfo.setTransactionTypeID(resultInfo.getNtransactiontypeid());
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
	
	
	public void TransJGPrintData(PrintInfo printOperationInfo) throws Exception
	{

		//客户名称
		usedFields.put("ClientName", Env.getClientName());
		
		//执行日
		//usedFields.put("ExecuteDate", DataFormat.formatDate( printOperationInfo.getExecuteDate() ));  //显示YYYY-MM-DD
		usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //显示YYYY年MM月DD日
		
		//起息日
		//usedFields.put("InterestStartDate", DataFormat.formatDate( printOperationInfo.getInterestStartDate() ));  //显示YYYY-MM-DD
		usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //显示YYYY年MM月DD日
		
		//到期日
		usedFields.put("OperationEndDate", DataFormat.getChineseDateString( printOperationInfo.getEndDate() ));
		
		//当前时间
		//usedFields.put("NowDate", DataFormat.formatDate( Env.getSystemDate() ));  //显示YYYY-MM-DD
		usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //显示YYYY年MM月DD日
		
		//交易编号
		usedFields.put("TransNo", printOperationInfo.getTransNo());
			

		//外部银行账户 "银行收款"业务的付款方为外部账户,过滤此业务
		if( printOperationInfo.getPayBankID() > 0 )
		{
			Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
			BranchInfo branchInfo = new BranchInfo();
			branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());

			usedFields.put("PayAccountName",Env.getClientName());

				//付款方账号
			usedFields.put("PayAccountNo", DataFormat.formatString( branchInfo.getBankAccountCode() ));
				
				//付款方开户行名称
			usedFields.put("PayBankName", DataFormat.formatString( branchInfo.getBranchName() ));

			
			//付款方科目代码
			usedFields.put("PaySubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
		}
		else if(printOperationInfo.getPayGL()>0)
		{

			//付款人全称
			usedFields.put("PayAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getPayGL() ));
			
			//付款方账号
			//usedFields.put("PayAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getPayGL() ));
			//江琪  修改 2011-04-06 国机需求 将 特殊业务 选 总账账户 时 的 账号 显示为 其对应 科目号
			usedFields.put("PayAccountNo", NameRef.getGLKNoByID( printOperationInfo.getPayGL() ));
			
			//付款方开户行名称
			usedFields.put("PayBankName", DataFormat.formatString( Env.getClientName()));
			
			//付款方科目代码
			usedFields.put("PaySubjectCode", NameRef.getGLKNoByID( printOperationInfo.getPayGL() ));
			
		}
		
		if ( printOperationInfo.getReceiveBankID() > 0 )
		{
	
			Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
			BranchInfo branchInfo = new BranchInfo();
			branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());
			
			//收款人全称
			usedFields.put("ReceiveAccountName", Env.getClientName());

			
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
//		else if(printOperationInfo.getExtClientName() != null && printOperationInfo.getExtClientName().trim().length() > 0)
//		{
//			//付款人全称
//			usedFields.put("PayAccountName", printOperationInfo.getExtClientName());
//			
//			//付款方账号
//			usedFields.put("PayAccountNo", printOperationInfo.getExtAccountNo());
//			
//			//付款方开户行名称
//			usedFields.put("PayBankName", printOperationInfo.getExtRemitInBank());
//		}
		else if(printOperationInfo.getReceiveGL()>0)
		{
			
            //收款人全称
            usedFields.put("ReceiveAccountName", NameRef.getGLTypeDescByID( printOperationInfo.getReceiveGL() ));
			//江琪  修改 2011-04-06 国机需求 将 特殊业务 选 总账账户 时 的 账号 显示为 其对应 科目号
			//收款方账号
            //usedFields.put("ReceiveAccountNo", NameRef.getGLTypeNoByID( printOperationInfo.getReceiveGL() ));
            usedFields.put("ReceiveAccountNo", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL() ));
            
			//收款方开户行名称
			usedFields.put("ReceiveBankName", DataFormat.formatString( Env.getClientName()));
			
            //收款方科目代码
            usedFields.put("ReceiveSubjectCode", NameRef.getGLKNoByID( printOperationInfo.getReceiveGL() ));

			
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
			if(printOperationInfo.getInterestStartDate() != null)
			{
				String strAbstract = "";

				strAbstract = printOperationInfo.getAbstract();

				usedFields.put("Abstract", strAbstract);
			}
			else
			{
				usedFields.put("Abstract", printOperationInfo.getAbstract());
			}
	
			//账户所属单位名称
			//usedFields.put("AccountClientName", NameRef.getClientNameByAccountID( printOperationInfo.getPayAccountID() ) );
			//add bu xiangzhou 签章中日期取执行日
			usedFields.put("SignatureDate", DataFormat.getDateString(printOperationInfo.getExecuteDate()));

	}
	
	
	



}
