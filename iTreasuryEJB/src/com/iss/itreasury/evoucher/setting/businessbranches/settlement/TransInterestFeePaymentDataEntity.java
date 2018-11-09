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
			
		}
		//������
		else if( printOperationInfo.getPayBankID() > 0)
		{
			Sett_BranchDAO sett_BranchDAO = new Sett_BranchDAO();
			BranchInfo branchInfo = new BranchInfo();
			branchInfo = sett_BranchDAO.findByID(printOperationInfo.getPayBankID());			
			//������ȫ��			
			usedFields.put("PayAccountName", DataFormat.formatString( branchInfo.getPrintName() ));
			//����˺�
			usedFields.put("PayAccountNo", DataFormat.formatString( branchInfo.getBankAccountCode() ));
			//�������������
			usedFields.put("PayBankName", DataFormat.formatString( branchInfo.getBranchName() ));			
			//�����Ŀ����
			usedFields.put("PaySubjectCode", DataFormat.formatString( branchInfo.getSubjectCode() ));
		}else if(printOperationInfo.getExtClientName() != null && printOperationInfo.getExtClientName().trim().length() > 0)
		{
			//������ȫ��
			usedFields.put("PayAccountName", printOperationInfo.getExtClientName());
			
			//����˺�
			usedFields.put("PayAccountNo", printOperationInfo.getExtAccountNo());
			
			//�������������
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
		//�տ���ȫ��
		usedFields.put("ReceiveAccountName", NameRef.getAccountNameByID( printOperationInfo.getReceiveAccountID() ));
		
		//�տ�˺�
		usedFields.put("ReceiveAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
		usedFields.put("ReceiveAccountCurrentNo", NameRef.getAccountNoByID( printOperationInfo.getConsignDepositAccountID()));
		usedFields.put("ReceiveAccountDepositNo", NameRef.getAccountNoByID( printOperationInfo.getConsignAccountID() ) );
		
		//�տ����������
		usedFields.put("ReceiveBankName", NameRef.getOfficeNameByID( printOperationInfo.getOfficeID() ));
		//���ӻص����ӡ����˾���ڵص�global.evoucher.printclientaddress(global.xml)
		String clientAddress = "";
		clientAddress = Config.getProperty(ConfigConstant.GLOBAL_EVOUCHER_PRINTCLIENTADDRESS);
		//�տ��˻����ַ
		usedFields.put("ReceiveRemitInAddress", DataFormat.formatString(clientAddress) );
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
		//����
		usedFields.put("Rate", DataFormat.formatRate( printOperationInfo.getRate() ));
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
		//�ſ�֪ͨ����
		usedFields.put("PayFormNo", NameRef.getCommonPayFormNoByID( printOperationInfo.getLoanNoteID() ));
		
		//��ͬ��
		usedFields.put("ContractNo", NameRef.getContractNoByID( printOperationInfo.getContractID() ));
		
		//�ſ�֪ͨ��
		if(printOperationInfo.getLoanNoteID() > 0)
		{

			LoanPayFormDetailInfo lpfdinfo = new LoanPayFormDetailInfo();
			lpfdinfo.setLoadNoteID(printOperationInfo.getLoanNoteID());
			Sett_TransGrantLoanDAO stdao = new Sett_TransGrantLoanDAO();
			lpfdinfo = stdao.findPayFormDetailByCondition(lpfdinfo);
						
			//��ͬ����
			usedFields.put("ContractRate", DataFormat.formatRate( lpfdinfo.getInterestRate(), 6 ));
			
			//��ͬ��������
			usedFields.put("OverDueRate", DataFormat.formatRate( lpfdinfo.getOverDueInterestRate(), 6 ));
			
			//ί�е�λ
			usedFields.put("ConsignUnit", lpfdinfo.getClientName());
			
			//��Ϣ����֧��ί�д����ί�е�λ modify by xwhe 2008-07-15
			usedFields.put("ConsignClientName", NameRef.getClientNameByID(printOperationInfo.getConsignClientID()));			
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
			
            //��Ϣ����֧��ί�д�������˻� modify by xwhe 2008-07-28
			usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID(printOperationInfo.getReceiveAccountID()));
			
			//������Ϣ
			if (printOperationInfo.getRealInterest() > 0)
			{
				//������Ϣ��ʼ����					
				usedFields.put("NormalInterestDateStart", DataFormat.getChineseDateString( printOperationInfo.getLatestInterestClearDate() ) );
				
				//������Ϣ��������
				usedFields.put("NormalInterestDateEnd", DataFormat.getChineseDateString(DataFormat.getPreviousDate( printOperationInfo.getInterestClearDate() )) );
				
				
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
				if(lpfdinfo.getIsRemitOverDueInterest() == 0)
				{
					usedFields.put("CompoundInterestRate", DataFormat.formatRate(lpfdinfo.getInterestRate(), 6 ));
				}
				else
				{
					usedFields.put("CompoundInterestRate", DataFormat.formatRate(lpfdinfo.getOverDueInterestRate(), 6 ));
				}
				
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
				if(lpfdinfo.getIsRemitOverDueInterest() == 0)
				{
					usedFields.put("OverInterestRate", DataFormat.formatRate(0.0 , 6 ));
				}
				else
				{
					usedFields.put("OverInterestRate", DataFormat.formatRate(lpfdinfo.getOverDueInterestRate(), 6 ));
				}
				
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
								
                //���������� ȡ�ſ�֪ͨ������������		
				usedFields.put("CommissionFeeRate", DataFormat.formatRate( lpfdinfo.getCommissionRate(), 6 ));				
				//��������Ϣ
				if(printOperationInfo.getRealCommission()>0.0)
				{
					usedFields.put("CommissionFee", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID() )+DataFormat.formatListAmount(printOperationInfo.getRealCommission(), 2) );
					
                    //��������Ϣ��д add by xwhe 2008-07-28 modify by xwhe 2008-08-04
					usedFields.put("chineseCommissionFee",PrintDataFormat.getChineseFormatAmount( printOperationInfo.getRealCommission(), printOperationInfo.getCurrencyID()));
					usedFields.put("ChineseCommissionFee",PrintDataFormat.getChineseFormatAmount( printOperationInfo.getRealCommission(), printOperationInfo.getCurrencyID()));
				}
				usedFields.put("ConsignDepositAccountNo", NameRef.getAccountNoByID( printOperationInfo.getReceiveAccountID() ));
			}
			
			//��Ϣ˰��(2007-10-18 ����Ҫ����ί�д�����Ϣ������ʾ"��Ϣ˰��")
			if (printOperationInfo.getRealInterestTax() > 0)
			{
				usedFields.put("InterestTax", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+ DataFormat.formatListAmount(printOperationInfo.getRealInterestTax(), 2) );
				usedFields.put("ChineseInterestTax", ChineseCurrency.showChinese(DataFormat.formatListAmount(printOperationInfo.getRealInterestTax(), 2) , printOperationInfo.getCurrencyID()));
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
			
			//�⻹��Ϣ 
			double remitInterest = DataFormat.formatDouble(printOperationInfo.getInterest() - printOperationInfo.getRealInterest());
			if (remitInterest > 0)
			{
				usedFields.put("RemitInterest", Constant.CurrencyType.getSymbol(printOperationInfo.getCurrencyID())+DataFormat.formatListAmount( remitInterest ,2 ) );
			}
			//ʵ�ʻ�ת���  
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
		//��λ(�տ�ͻ�)(��Ӫ�����˻�)
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

		if(printOperationInfo.getAccountTypeID() >0)
		{
			usedFields.put("AccountTypeName", SETTConstant.AccountType.getName( printOperationInfo.getAccountTypeID() ) );
		}
		if(printOperationInfo.getRealInterest() > 0.00)		{
		
			//Boxu 2008��12��18�� ������ʾ�г�ͻ,ͳһ��ʾҵ������
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
		//�˺�
		usedFields.put("AccountNo",NameRef.getAccountNoByID( printOperationInfo.getConsignAccountID()));
	
		
		
		//add by xiangzhou ����ǩ����ʾλ�� 20110921
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
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setEndDate(resultInfo.getInterestClear());
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

				if (resultInfo.getCommissionAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getCommissionAccountID());
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
				//��Ϣ�˻�
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
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
                //ͨ����ͬ�Ų���ί�е�λid

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
				
                //����ֶμ�Ϣ
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
