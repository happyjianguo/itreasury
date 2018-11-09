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
	//�ܲ�-׼��������
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
	
	//�ܲ�-׼��������
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
	
	//��Ϣ����֧��-���ڽ�Ϣ, ���ڴ�����Ӧ����Ϣ����������
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
				if(resultInfo.getInterestTax()>0){ 
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
				
				//���ڽ�Ϣ(Э��)�ֶμ�Ϣ
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
                             //modify by xwhe 2008-11-20 ��ʱ�滻������
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

			//�ͻ�����
			usedFields.put("ClientName", Env.getOfficeName(printOperationInfo.getClientId()));//�ܲ�-������ҵ�� �Ŀͻ�Ϊĳ���������´�
			
			//ִ����
			usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //��ʾYYYY��MM��DD��
			
			//��Ϣ��
			usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //��ʾYYYY��MM��DD��

			//��ǰʱ��
			usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //��ʾYYYY��MM��DD��
			
			//���ױ��
			usedFields.put("TransNo", printOperationInfo.getTransNo());
			
			//������ǰ���´����ƣ��ܲ�-���ƣ�
			usedFields.put("OfficeName",NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));

			
			
			if(printOperationInfo.getPayAccountID()>0)
			{
				//������ȫ��
				//usedFields.put("PayAccountName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));
				usedFields.put("PayAccountName", NameRef.getOfficeNameByID( printOperationInfo.getClientId() ));
				
				//����˺�
				usedFields.put("PayAccountNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));
				

				
				if(printOperationInfo.getReceiveBankID()>0)
				{
					usedFields.put("PayBankName", NameRef.getBankNameByID( printOperationInfo.getReceiveBankID() ));					
				}
				else
				{
					//�������˾������������
					usedFields.put("PayBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
					
				}
				
				//�����Ŀ����
				usedFields.put("PaySubjectCode", DataFormat.formatString(NameRef.getSubjectByAccountID(printOperationInfo.getPayAccountID()) ));
			}
			else if(printOperationInfo.getPayBankID()>0){
				
				//������ȫ��
				usedFields.put("PayAccountName", NameRef.getOfficeNameByID( printOperationInfo.getClientId() ));
				
				//����˺�
				usedFields.put("PayAccountNo", NameRef.getBankAccountCodeByBankID( printOperationInfo.getPayBankID() ));
				
				//�������������
				usedFields.put("PayBankName", NameRef.getBankNameByID( printOperationInfo.getPayBankID() ));
				
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());
				//�����Ŀ����
				usedFields.put("PaySubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
			}
			
			if(printOperationInfo.getReceiveAccountID()>0)
			{
				//�տ���ȫ��
				//usedFields.put("ReceiveAccountName", NameRef.getAccountNameByID( printOperationInfo.getReceiveAccountID() ));
				usedFields.put("ReceiveAccountName", NameRef.getOfficeNameByID( printOperationInfo.getClientId() ));
				//�տ�˺�
				usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
	
				//�տ��������
				usedFields.put("ReceiveBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
				
				//�տ��Ŀ����
				usedFields.put("ReceiveSubjectCode", DataFormat.formatString(NameRef.getSubjectByAccountID(printOperationInfo.getReceiveAccountID()) ));
			}
			else if(printOperationInfo.getReceiveBankID()>0)//������
			{
				//�տ���ȫ��
				usedFields.put("ReceiveAccountName", NameRef.getOfficeNameByID( printOperationInfo.getClientId() ));
				
				//�տ�˺�
				usedFields.put("ReceiveAccountNo", NameRef.getBankAccountCodeByBankID( printOperationInfo.getReceiveBankID() ));
				
				//�տ����������
				usedFields.put("ReceiveBankName", NameRef.getBankNameByID( printOperationInfo.getReceiveBankID() ));
				
				if(printOperationInfo.getExtClientName() != null && printOperationInfo.getExtClientName().trim().length() > 0 && printOperationInfo.getTransactionTypeID()==SETTConstant.TransactionType.BAKRETURN)
				{
					//������ȫ��
					usedFields.put("ReceiveAccountName", printOperationInfo.getExtClientName());
					
					//����˺�
					usedFields.put("ReceiveAccountNo", printOperationInfo.getExtAccountNo());
					
					//�������������
					usedFields.put("ReceiveBankName", printOperationInfo.getExtRemitInBank());
				}
				Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
				BranchInfo branchInfo = new BranchInfo();
				branchInfo = sett_BranchDAO.findByID(printOperationInfo.getReceiveBankID());
				//�տ��Ŀ����
				usedFields.put("ReceiveSubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
			}



			if (printOperationInfo.getAmount() != 0.0)
			{
				//������Сд
				usedFields.put("Amount", PrintDataFormat.getFormatAmount( printOperationInfo.getAmount() ));
				
				//�������д
				usedFields.put("ChineseAmount", PrintDataFormat.getChineseFormatAmount( printOperationInfo.getAmount(), printOperationInfo.getCurrencyID() ));
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
			
			if (printOperationInfo.getCurrencyID() > 0)
			{
				//����
				usedFields.put("CurrencyName", Constant.CurrencyType.getName( printOperationInfo.getCurrencyID() ));
			}
			
			//ժҪ
			/*if(printOperationInfo.getInterestStartDate() != null)
			{
				String strAbstract = "";
				
				strAbstract = "��Ϣ�գ�" + DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() );
				
				if(printOperationInfo.getAbstract() != null && !printOperationInfo.getAbstract().equals(""))
				{
					strAbstract += "��";
					strAbstract += printOperationInfo.getAbstract();
				}

				usedFields.put("Abstract", strAbstract);
			}
			else
			{
				usedFields.put("Abstract", printOperationInfo.getAbstract());
			}*/
			usedFields.put("Abstract", printOperationInfo.getAbstract());
	
			// ǩ��������ȡִ����
			usedFields.put("SignatureDate", DataFormat.getDateString(printOperationInfo.getExecuteDate()));
			
			
			
			
			//��Ϣ�˻���
			if (printOperationInfo.getReceiveInterestAccountID() > 0)
			{
				//�տ�˺�(��Ϣ�˻���)
				usedFields.put("InterestextAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveInterestAccountID()));
			}
			
			
			if (printOperationInfo.getPayAccountID() > 0 && printOperationInfo.getTransactionTypeID()==SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST)
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
			
			//����
			usedFields.put("Rate", DataFormat.formatRate( printOperationInfo.getRate() ));
			
			
			//ҵ������
			if(printOperationInfo.getTransTypeID() != -1)
			{
				usedFields.put("TransactionTypeName", SETTConstant.TransactionType.getName( printOperationInfo.getTransTypeID() ));
			}
			
			//�˻�������λ����
			usedFields.put("AccountClientName", NameRef.getClientNameByAccountID( printOperationInfo.getPayAccountID() ) );
			
			
			
			//������ݱ���
			String Amount1 = "";  //����1
			String Amount2 = "";  //����2
			String Amount3 = "";  //����3
			String Amount4 = "";  //����4
			//add by dwj 20110503 �ֶ�ƽ�����
			String AmountAverage1 = "";  //����1
			String AmountAverage2 = "";  //����2
			String AmountAverage3 = "";  //����3
			String AmountAverage4 = "";  //����4
			//end add by dwj 20110503
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
			//add by dwj 20110503
			String DayNumber5 = "";  //����5
			//end add by dwj 20110503
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
			String NegotiateDays1="";//Э����������1
			String NegotiateDays2="";//Э����������2
			String NegotiateRate1 = "";//Э������1
			String NegotiateRate2 = "";//Э������1
			String NegotiateInterest1 ="";//Э����Ϣ
			String NegotiateInterest2 ="";//Э����Ϣ
			//�Ƹ���Ϣ�ϼ�
			String totalInterest = "";
			
			
			if(printOperationInfo.getPecisionInterestAmount() != 0.0)
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
							CurrentAmass = DataFormat.formatAmount(
								UtilOperation.Arith.div(
									//modify by xwhe 2008-10-28	
									//UtilOperation.Arith.mul(printOperationInfo.getCurrentInterest(), 360)
									UtilOperation.Arith.mul(printOperationInfo.getPecisionInterestAmount(), 360)
								  , UtilOperation.Arith.div(printOperationInfo.getRate(), 100)
								)
							);
						}
		                //modify by xwhe 2008-08-21 ���ڷֶμ�Ϣ��ʱ������
						if(printOperationInfo.getRate1() != 0){			
						
						Interest1 = DataFormat.formatAmount(printOperationInfo.getInterest1());
						
						Rate1 = DataFormat.formatRate( printOperationInfo.getRate1(), 6 );
						
						BeginDate1 = DataFormat.getChineseDateString(printOperationInfo.getStartDate1());
						//BeginDate1 = "2008��10��21��";
						
						OverDate1 = DataFormat.getChineseDateString(printOperationInfo.getEndDate1());
						DayNumber1 = Integer.toString(DataFormat.getIntervalDays( printOperationInfo.getStartDate(),printOperationInfo.getEndDate1()));	
						//���ڻ���1
						if(printOperationInfo.getRate1() != 0)
						{
							Connection conInternal = null;
							Sett_DailyAccountBalanceDAO dailAccountDAO = new Sett_DailyAccountBalanceDAO(conInternal);
							//��ʱע��2008-11-20
							//Timestamp sdate = DataFormat.getDateTime("2008-10-21");
							double CurrentAmass111 = dailAccountDAO.currentAmassBySubAccountIDAndDate(printOperationInfo.getSubAccountID(),printOperationInfo.getStartDate1(),printOperationInfo.getEndDate1());										
							CurrentAmass1 = CurrentAmass111+"";
							//add by dwj 20110503 ���ƽ�����
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
		               //���ڻ���2
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
							//add by dwj 20110503 ���ƽ�����
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
						//���ڻ���3
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
							
							//add by dwj 20110503 ���ƽ�����
							double dAmountAverage3 = CurrentAmass113/Double.parseDouble(DayNumber5);
							AmountAverage3 = dAmountAverage3 + "";
							//end add by dwj 20110503
						}
						}	
						//Э��������Ϣ(Э������)
						if (printOperationInfo.getAccordInterestAmount() > 0)
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
						
						//��Ϣ�ϼ�
						totalInterest = (
								DataFormat.formatAmount(
										DataFormat.formatDouble(
												DataFormat.formatDouble(printOperationInfo.getCurrentInterest()) + DataFormat.formatDouble(printOperationInfo.getAccordInterest()))));
					}
			}
			else if( printOperationInfo.getCurrentInterest() != 0.0 )
			{
				

				//����Ϣ������ֹ����֮ǰ������ǰ֧ȡ
				if ( printOperationInfo.getInterestStartDate() != null 
				  && printOperationInfo.getEndDate() != null 
				  && printOperationInfo.getInterestStartDate().before(printOperationInfo.getEndDate()) )
				{
					//����1��������Ϣ��������Ϣ���ʣ���ʼ���ڣ��������ڣ�����
					Amount1 = DataFormat.formatAmount(printOperationInfo.getAmount());
					
					Interest1 = DataFormat.formatAmount(printOperationInfo.getCurrentInterest());
					
					//Rate1 = DataFormat.formatAmount(Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.98));
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
							//DayNumber2 = (PrintVoucher.getIntervalDays(printOperationInfo.getStartDate(), printOperationInfo.getInterestStartDate(), intervalDaysFlag)) + "";
						}
						else
						{
							//֪֧ͨȡ
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
								//	- DataFormat.formatDouble(printOperationInfo.getCurrentInterest())
								//	- DataFormat.formatDouble(printOperationInfo.getOtherInterest())
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
					
					//Rate2 = DataFormat.formatAmount(Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.98));
					//���ٴ������ļ�ȡ,�ӱ� sett_TransFixedWithDraw ȡ advanceRate
					Rate2 = DataFormat.formatRate( printOperationInfo.getNewRate(), 6 );
					
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
			
			if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST)
			{
				depositTypeName = "���������Ϣ";
			}
			else if (printOperationInfo.getTransTypeID() == SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST)
			{
				depositTypeName = "�������������Ϣ";
			}
			usedFields.put("depositTypeName", depositTypeName);
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
				usedFields.put("Interest1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest1), 2 ));  //��Ϣ1
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
				usedFields.put("Interest4", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest4), 2 ));  //��Ϣ4
			}
			if (Interest5 != null && !Interest5.equals(""))
			{
				usedFields.put("Interest5", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(Interest5), 2 ));  //��Ϣ5
			}
			if (NegotiateInterest1 != null && !NegotiateInterest1.equals(""))
			{
				usedFields.put("NegotiateInterest1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(NegotiateInterest1), 2 ));  //Э����Ϣ1
			}
			if (NegotiateInterest2 != null && !NegotiateInterest2.equals(""))
			{
				usedFields.put("NegotiateInterest2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(NegotiateInterest2), 2 ));  //Э����Ϣ2
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
			usedFields.put("NegotiateDays1", NegotiateDays1);
			usedFields.put("NegotiateDays2", NegotiateDays2);
			
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
			
			//add by dwj 20110503 ƽ�����
			if (AmountAverage1 != null && !AmountAverage1.equals(""))
			{
				usedFields.put("AmountAverage1", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AmountAverage1), 2 ));  //���ڻ���3
			}
			if (AmountAverage2 != null && !AmountAverage2.equals(""))
			{
				usedFields.put("AmountAverage2", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AmountAverage2), 2 ));  //���ڻ���3
			}
			if (AmountAverage3 != null && !AmountAverage3.equals(""))
			{
				usedFields.put("AmountAverage3", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( Double.parseDouble(AmountAverage3), 2 ));  //���ڻ���3
			}
			//end add by dwj 20110503
			
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
			
			//�Ƹ���Ϣ�ϼƴ�д
			String strChineseAmount = ChineseCurrency.showChinese(totalInterest, printOperationInfo.getCurrencyID());
			usedFields.put("ChineseInterestTotal", strChineseAmount);
			
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
	}
	
	
	
}
