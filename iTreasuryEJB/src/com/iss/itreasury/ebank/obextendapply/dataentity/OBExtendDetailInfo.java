/*
 * Created on 2004-1-29
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obextendapply.dataentity;

import java.sql.*;
import java.io.Serializable;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBExtendDetailInfo  implements Serializable
{
	public OBExtendDetailInfo()
	{
		super();
	}

	// The following variables are added by wli@isoftstone.com
	public long ExtendID; //չ�ں�ͬID
	public long ContractID; //��ͬID
	public long ExtendApplyID; //չ�������ID
	public long LoanID; //nLoanID:��������ID
	public long LoanTypeID; //��������
	public String ExtendCode; //չ�ں�ͬ���
	public String ContractCode; //��ͬ���
	public String ApplyCode; //������
	public long ExtendType; //չ������

	public long ClientID; //��λID
	public String ClientName; //��λ����
	public double ExtendAmount; //չ�ڽ��
	public Timestamp ExtendStart; //չ����ʼ����
	public Timestamp ExtendEnd; //չ�ڽ�������
	public long ExtendIntervalNum; //չ������
	public double ExtendInterestRate; //չ������
	public long StatusID; //չ�ں�ͬ״̬
	public long InputUserID; //¼����ID
	public String InputUserName; //¼��������
	public long CheckUserID; //������ID
	public String CheckUserName; //����������
	public long CurrencyID; //����
	public long PageCount; //��¼��
	public long CheckNum; //������
	public String DocName; //��ͬ�ı��ļ���
	public String TempletName; //ģ���ļ���strTempletName
	public String JSPFILENAME;//jsp�ļ���
	public void setApplyCode(String ApplyCode)
	{
		this.ApplyCode = ApplyCode;
	}

	public void setCheckNum(long CheckNum)
	{
		this.CheckNum = CheckNum;
	}

	public void setCheckUserID(long CheckUserID)
	{
		this.CheckUserID = CheckUserID;
	}

	public void setCheckUserName(String CheckUserName)
	{
		this.CheckUserName = CheckUserName;
	}

	public void setClientID(long ClientID)
	{
		this.ClientID = ClientID;
	}

	public void setClientName(String ClientName)
	{
		this.ClientName = ClientName;
	}

	public void setContractCode(String ContractCode)
	{
		this.ContractCode = ContractCode;
	}

	public void setContractID(long ContractID)
	{
		this.ContractID = ContractID;
	}

	public void setCurrencyID(long CurrencyID)
	{
		this.CurrencyID = CurrencyID;
	}

	public void setDocName(String DocName)
	{
		this.DocName = DocName;
	}

	public void setExtendAmount(double ExtendAmount)
	{
		this.ExtendAmount = ExtendAmount;
	}

	public void setExtendApplyID(long ExtendApplyID)
	{
		this.ExtendApplyID = ExtendApplyID;
	}

	public void setExtendCode(String ExtendCode)
	{
		this.ExtendCode = ExtendCode;
	}

	public void setExtendEnd(Timestamp ExtendEnd)
	{
		this.ExtendEnd = ExtendEnd;
	}

	public void setExtendID(long ExtendID)
	{
		this.ExtendID = ExtendID;
	}

	public void setExtendInterestRate(double ExtendInterestRate)
	{
		this.ExtendInterestRate = ExtendInterestRate;
	}

	public void setExtendIntervalNum(long ExtendIntervalNum)
	{
		this.ExtendIntervalNum = ExtendIntervalNum;
	}

	public void setExtendStart(Timestamp ExtendStart)
	{
		this.ExtendStart = ExtendStart;
	}

	public void setExtendType(long ExtendType)
	{
		this.ExtendType = ExtendType;
	}

	public void setInputUserID(long InputUserID)
	{
		this.InputUserID = InputUserID;
	}

	public void setInputUserName(String InputUserName)
	{
		this.InputUserName = InputUserName;
	}

	public void setJSPFILENAME(String JSPFILENAME)
	{
		this.JSPFILENAME = JSPFILENAME;
	}

	public void setLoanID(long LoanID)
	{
		this.LoanID = LoanID;
	}

	public void setLoanTypeID(long LoanTypeID)
	{
		this.LoanTypeID = LoanTypeID;
	}

	public void setPageCount(long PageCount)
	{
		this.PageCount = PageCount;
	}

	public void setStatusID(long StatusID)
	{
		this.StatusID = StatusID;
	}

	public void setTempletName(String TempletName)
	{
		this.TempletName = TempletName;
	}

	public String getApplyCode()
	{
		return ApplyCode;
	}

	public long getCheckNum()
	{
		return CheckNum;
	}

	public long getCheckUserID()
	{
		return CheckUserID;
	}

	public String getCheckUserName()
	{
		return CheckUserName;
	}

	public long getClientID()
	{
		return ClientID;
	}

	public String getClientName()
	{
		return ClientName;
	}

	public String getContractCode()
	{
		return ContractCode;
	}

	public long getContractID()
	{
		return ContractID;
	}

	public long getCurrencyID()
	{
		return CurrencyID;
	}

	public String getDocName()
	{
		return DocName;
	}

	public double getExtendAmount()
	{
		return ExtendAmount;
	}

	public long getExtendApplyID()
	{
		return ExtendApplyID;
	}

	public String getExtendCode()
	{
		return ExtendCode;
	}

	public Timestamp getExtendEnd()
	{
		return ExtendEnd;
	}

	public long getExtendID()
	{
		return ExtendID;
	}

	public double getExtendInterestRate()
	{
		return ExtendInterestRate;
	}

	public long getExtendIntervalNum()
	{
		return ExtendIntervalNum;
	}

	public Timestamp getExtendStart()
	{
		return ExtendStart;
	}

	public long getExtendType()
	{
		return ExtendType;
	}

	public long getInputUserID()
	{
		return InputUserID;
	}

	public String getInputUserName()
	{
		return InputUserName;
	}

	public String getJSPFILENAME()
	{
		return JSPFILENAME;
	}

	public long getLoanID()
	{
		return LoanID;
	}

	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	public long getPageCount()
	{
		return PageCount;
	}

	public long getStatusID()
	{
		return StatusID;
	}

	public String getTempletName()
	{
		return TempletName;
	}

}
