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
		//�ͻ�����
		usedFields.put("ClientName", Env.getClientName());	
		//ִ����
		usedFields.put("ExecuteDate", DataFormat.getChineseDateString( printOperationInfo.getExecuteDate() ));  //��ʾYYYY��MM��DD��
		//��Ϣ��		
		usedFields.put("InterestStartDate", DataFormat.getChineseDateString( printOperationInfo.getInterestStartDate() ));  //��ʾYYYY��MM��DD��		
		//��ǰʱ��		
		usedFields.put("NowDate", DataFormat.getChineseDateString( Env.getSystemDate() ));  //��ʾYYYY��MM��DD��		
		//���ױ��
		usedFields.put("TransNo", printOperationInfo.getTransNo());
		//��λ
		usedFields.put("LoanUnit", NameRef.getAccountNameByID( printOperationInfo.getLoanAccountID() ));
		//���λ
		usedFields.put("RepaymentUnitName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));
		//����˻���
		usedFields.put("LoanAccountNo", NameRef.getAccountNoByID( printOperationInfo.getLoanAccountID() ));
		//����
		usedFields.put("CurrencyName", Constant.CurrencyType.getName( printOperationInfo.getCurrencyID() ));
		if(printOperationInfo.getPayAccountID() > 0)
		{
			//������ȫ��
			usedFields.put("PayAccountName", NameRef.getAccountNameByID( printOperationInfo.getPayAccountID() ));			
			//����˺�
			usedFields.put("PayAccountNo", NameRef.getAccountNoByID( printOperationInfo.getPayAccountID() ));			
			//�������������
			usedFields.put("PayBankName", NameRef.getOpenOrganizationNameByAccountID(printOperationInfo.getPayAccountID()));
			//���������
			usedFields.put("PayBank", NameRef.getOpenOrganizationNameByAccountID(printOperationInfo.getPayAccountID()));
		}
		if(printOperationInfo.getReceiveAccountID()>0){
			//�տ���ȫ��
			usedFields.put("ReceiveAccountName", NameRef.getAccountNameByID( printOperationInfo.getReceiveAccountID() ));
			//�տ����������
			usedFields.put("ReceiveBankName", NameRef.getOpenOrganizationNameByAccountID(printOperationInfo.getReceiveAccountID()));
			//�տ������
			usedFields.put("ReceiveBank", NameRef.getOpenOrganizationNameByAccountID(printOperationInfo.getReceiveAccountID()));
			//�տ�˺�
			usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
		}
		if(printOperationInfo.getAmount() != 0.0)
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
		//ժҪ
		String strAbstract = "��Ϣ�գ�" + DataFormat.getChineseDateString(printOperationInfo.getInterestStartDate()); 
		if(printOperationInfo.getAbstract()!=null&&printOperationInfo.getAbstract().trim().length()>0)
		{
			strAbstract = strAbstract + "��";
			strAbstract = strAbstract + printOperationInfo.getAbstract();
		}         
		usedFields.put("Abstract",strAbstract);
		//�������(��ʽ�����,Ϊ"0"ʱ��ʾ"0.00")
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
				//�ſ�����
				//����˻�,������˻���
				if (resultInfo.getLendingAccountID() > 0)
				{
					printInfo.setLoanAccountID(resultInfo.getLendingAccountID());
					printInfo.setPayAccountID(resultInfo.getLendingAccountID());
				}
				//�տ
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
				//�տ��˻�
				if (resultInfo.getLendingAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getLendingAccountID());
					printInfo.setCurrentBalance(NameRef.getBalanceByAccountID(resultInfo.getLendingAccountID() ,true));
				}
				//�����˻�
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
