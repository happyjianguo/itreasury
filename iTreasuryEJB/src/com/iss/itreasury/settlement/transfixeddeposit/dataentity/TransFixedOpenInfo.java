/*
 * Created on 2003-9-9
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * @author xrli
 *	���ڿ�������ʵ���ࣺ
 *	1�����б�����ΪPrivate,����ֻ����setXXX�������õ�ֻ����getXXX������
 *	2���������������͡�Ĭ��ֵ��˵��
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFixedOpenInfo implements Serializable
{
	//����Ϣ
	private long ID = -1; //Ψһ��ʶ
	private String TransNo = ""; //���ױ��
	private long TransactionTypeID = -1; //�������ͣ����ڿ���������֧ȡ����������ת�桢֪ͨ������֪֧ͨȡ��
	private long OfficeID = -1; //���´���ʶ
	private long CurrencyID = -1; //���ֱ�ʶ
	private long ClientID = -1; //���ڿͻ���ʶ
	private String ClientNo = ""; //���ڿͻ����
	private String ClientName = ""; //���ڿͻ�����
	private long AccountID = -1; //�����˻�ID
	private String AccountNo = ""; //�����˻����	
	private String DepositNo = ""; //�浥��ţ��Ӷ����˻���ţ�
	private String CertificationNo = ""; //֤ʵ���
	private long CertificationBankID = -1; //֤ʵ�鷢������ID		
	private double Rate = 0.0; //����
	private Timestamp StartDate = null; //��ʼ(��ʼ)����
	private Timestamp EndDate = null; //����(��ֹ)����
	private long DepositTerm = -1; //���ڴ�����ޣ��£�
	private long InterestPlanID = -1; //���ʼƻ�
	private long NoticeDay = -1; //֪ͨ���֧ȡ���ڣ��죩/֪ͨ������ͣ�1��/7�죩
	
	private long CurrentAccountID = -1; //�������˻�ID
	private String CurrentAccountNo = ""; //�������˻���
	private String CurrentAccountClientName = ""; //�������˻��ͻ�����
	
	private long BankID = -1; //������ID
	private String BankName = ""; //����������
	private long CashFlowID = -1; //�ֽ�����ID
	private String CashFlowDesc = ""; //�ֽ���������
	
	private double Amount = 0.0; //���׽��
	
	private Timestamp InterestStartDate = null; //��Ϣ��
	private Timestamp ExecuteDate = null; //ִ����
	private Timestamp InputDate = null; //¼����
	
	private Timestamp ModifyDate = null; //�޸�ʱ�䣺ʱ����
	
	private long AbstractID = -1; //ժҪID
	private String Abstract = ""; //ժҪ
	
	private long InputUserID = -1; //¼����ID
	private String InputUserName = ""; //¼��������
	private long CheckUserID = -1; //������ID
	private String CheckUserName = ""; //����������
	private String CheckAbstract = ""; //����/ȡ������ժҪ
	private long StatusID = -1; //����״̬
	
	private String InstructionNo = ""; //��ʶ�ǽ���ϵͳ��������ˮ��
	private String ConsignVoucherNo = ""; //ί�и���ƾ֤��
	private String ConsignPassword = ""; //ί�и���ƾ֤������
	private String BillNo = ""; //Ʊ�ݺ�
	private long BillTypeID = -1; //Ʊ������ID
	private long BillBankID = -1; //Ʊ�ݷ�������ID
	
	private long ExtAcctID = -1; //�ǲ���˾�˻�ID
	private String ExtAcctNo = ""; //�ǲ���˾�˻���
	private String ExtClientName = ""; //�ǲ���˾�ͻ�����
	private String RemitInBank = ""; //�ǲ���˾��������
	private String RemitInProvince = ""; //ʡ
	private String RemitInCity = ""; //��
	
	private String ExtBankNo = ""; //��������к�
	private String SealNo = ""; //ӡ������
	private long SealBankID = -1; //ӡ������������ID
	private InutParameterInfo inutParameterInfo = null;
	
	private long isAutoContinue = -1;
	private long autocontinuetype = -1;
	private long autocontinueaccountid = -1;

	/**
	 * ��ȡ -- ժҪ
	 * @return
	 */
	public String getAbstract()
	{
		return Abstract;
	}

	/**
	 * ��ȡ -- �������˻�ID
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * ��ȡ -- �������˻����
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}

	/**
	 * ��ȡ -- ���׽��
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}	

	/**
	 * ��ȡ -- ��/������ID
	 * @return
	 */
	public long getBankID()
	{
		return BankID;
	}

	/**
	 * ��ȡ -- ��/����������
	 * @return
	 */
	public String getBankName()
	{
		return BankName;
	}

	/**
	 * ��ȡ -- Ʊ�ݷ�������ID
	 * @return
	 */
	public long getBillBankID()
	{
		return BillBankID;
	}

	/**
	 * ��ȡ -- Ʊ�ݺ�
	 * @return
	 */
	public String getBillNo()
	{
		return BillNo;
	}

	/**
	 * ��ȡ -- Ʊ������ID
	 * @return
	 */
	public long getBillTypeID()
	{
		return BillTypeID;
	}
	

	/**
	 * ��ȡ -- �ֽ���������
	 * @return
	 */
	public String getCashFlowDesc()
	{
		return CashFlowDesc;
	}

	/**
	 * ��ȡ -- �ֽ�����ID
	 * @return
	 */
	public long getCashFlowID()
	{
		return CashFlowID;
	}

	/**
	 * ��ȡ -- ֤ʵ�鷢������ID
	 * @return
	 */
	public long getCertificationBankID()
	{
		return CertificationBankID;
	}

	/**
	 * ��ȡ -- ֤ʵ���
	 * @return
	 */
	public String getCertificationNo()
	{
		return CertificationNo;
	}

	/**
	 * ��ȡ -- ����/ȡ������ժҪ
	 * @return
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}

	/**
	 * ��ȡ -- ������ID
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * ��ȡ -- ����������
	 * @return
	 */
	public String getCheckUserName()
	{
		return CheckUserName;
	}

	/**
	 * ��ȡ -- ��
	 * @return
	 */
	public String getRemitInCity()
	{
		return RemitInCity;
	}

	/**
	 * ��ȡ -- ���ڿͻ���ʶ
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * ��ȡ -- ���ڿͻ�����
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * ��ȡ -- ���ڿͻ����
	 * @return
	 */
	public String getClientNo()
	{
		return ClientNo;
	}

	/**
	 * ��ȡ -- ί�и���ƾ֤������
	 * @return
	 */
	public String getConsignPassword()
	{
		return ConsignPassword;
	}

	/**
	 * ��ȡ -- ί�и���ƾ֤��
	 * @return
	 */
	public String getConsignVoucherNo()
	{
		return ConsignVoucherNo;
	}

	/**
	 * ��ȡ -- ���ֱ�ʶ
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * ��ȡ -- ��/�������˻��ͻ�����
	 * @return
	 */
	public String getCurrentAccountClientName()
	{
		return CurrentAccountClientName;
	}

	/**
	 * ��ȡ -- ��/�������˻�ID
	 * @return
	 */
	public long getCurrentAccountID()
	{
		return CurrentAccountID;
	}

	/**
	 * ��ȡ -- ��/�������˻���
	 * @return
	 */
	public String getCurrentAccountNo()
	{
		return CurrentAccountNo;
	}

	/**
	 * ��ȡ -- �浥��ţ��Ӷ����˻���ţ�
	 * @return
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}

	/**
	 * ��ȡ -- ���ڴ�����ޣ��£�
	 * @return
	 */
	public long getDepositTerm()
	{
		return DepositTerm;
	}

	/**
	 * ��ȡ -- ����(��ֹ)����
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}

	/**
	 * ��ȡ -- ִ����
	 * @return
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}

	/**
	 * ��ȡ -- �ǲ���˾�˻�ID
	 * @return
	 */
	public long getExtAcctID()
	{
		return ExtAcctID;
	}

	/**
	 * ��ȡ -- �ǲ���˾�˻�����
	 * @return
	 */
	public String getExtClientName()
	{
		return ExtClientName;
	}

	/**
	 * ��ȡ -- �ǲ���˾�˻���
	 * @return
	 */
	public String getExtAcctNo()
	{
		return ExtAcctNo;
	}

	
	/**
	 * ��ȡ -- ��������к�
	 * @return
	 */
	public String getExtBankNo()
	{
		return ExtBankNo;
	}

	/**
	 * ��ȡ -- Ψһ��ʶ
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * ��ȡ -- ¼����ID
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * ��ȡ -- ¼��������
	 * @return
	 */
	public String getInputUserName()
	{
		return InputUserName;
	}

	/**
	 * ��ȡ -- ��ʶ�ǽ���ϵͳ��������ˮ��
	 * @return
	 */
	public String getInstructionNo()
	{
		return InstructionNo;
	}

	/**
	 * ��ȡ -- ���ʼƻ�
	 * @return
	 */
	public long getInterestPlanID()
	{
		return InterestPlanID;
	}

	/**
	 * ��ȡ -- ��Ϣ��
	 * @return
	 */
	public Timestamp getInterestStartDate()
	{
		return InterestStartDate;
	}

	/**
	 * ��ȡ -- �޸�ʱ�䣺ʱ����
	 * @return
	 */
	public Timestamp getModifyDate()
	{
		return ModifyDate;
	}

	/**
	 * ��ȡ -- ֪ͨ���֧ȡ���ڣ��죩
	 * @return
	 */
	public long getNoticeDay()
	{
		return NoticeDay;
	}

	/**
	 * ��ȡ -- ���´���ʶ
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	
	/**
	 * ��ȡ -- ʡ
	 * @return
	 */
	public String getRemitInProvince()
	{
		return RemitInProvince;
	}

	/**
	 * ��ȡ -- ����
	 * @return
	 */
	public double getRate()
	{
		return Rate;
	}

	/**
	 * ��ȡ -- ӡ������������ID
	 * @return
	 */
	public long getSealBankID()
	{
		return SealBankID;
	}

	/**
	 * ��ȡ -- ӡ������
	 * @return
	 */
	public String getSealNo()
	{
		return SealNo;
	}

	/**
	 * ��ȡ -- ��ʼ(��ʼ)����
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}

	/**
	 * ��ȡ -- ����״̬
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	
	/**
	 * ��ȡ -- �������ͣ����ڿ���������֧ȡ����������ת�桢֪ͨ������֪֧ͨȡ��
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}

	
	/**
	 * ��ȡ -- ���ױ��
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * ���� -- ժҪ
	 * @param string
	 */
	public void setAbstract(String string)
	{
		if (string != null)
		{
			Abstract = string;
		}
	}

	/**
	 * ���� -- �������˻�ID
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * ���� -- �������˻����
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		if (string != null)
		{
			AccountNo = string;
		}
	}

	/**
	 * ���� -- ���׽��
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}

	

	/**
	 * ���� -- ��/������ID
	 * @param l
	 */
	public void setBankID(long l)
	{
		BankID = l;
	}

	/**
	 * ���� -- ��/����������
	 * @param string
	 */
	public void setBankName(String string)
	{
		if (string != null)
		{
			BankName = string;
		}
	}

	/**
	 * ���� -- Ʊ�ݷ�������ID
	 * @param l
	 */
	public void setBillBankID(long l)
	{
		BillBankID = l;
	}

	/**
	 * ���� -- Ʊ�ݺ�
	 * @param string
	 */
	public void setBillNo(String string)
	{
		if (string != null)
		{
			BillNo = string;
		}
	}

	/**
	 * ���� -- Ʊ������ID
	 * @param l
	 */
	public void setBillTypeID(long l)
	{
		BillTypeID = l;
	}

	
	/**
	 * ���� -- �ֽ���������
	 * @param string
	 */
	public void setCashFlowDesc(String string)
	{
		if (string != null)
		{
			CashFlowDesc = string;
		}
	}

	/**
	 * ���� -- �ֽ�����ID
	 * @param l
	 */
	public void setCashFlowID(long l)
	{
		CashFlowID = l;
	}

	/**
	 * ���� -- ֤ʵ�鷢������ID
	 * @param l
	 */
	public void setCertificationBankID(long l)
	{
		CertificationBankID = l;
	}

	/**
	 * ���� -- ֤ʵ���
	 * @param string
	 */
	public void setCertificationNo(String string)
	{
		if (string != null)
		{
			CertificationNo = string;
		}
	}

	/**
	 * ���� -- ����/ȡ������ժҪ
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{
		if (string != null)
		{
			CheckAbstract = string;
		}
	}

	/**
	 * ���� -- ������ID
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	/**
	 * ���� -- ����������
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		if (string != null)
		{
			CheckUserName = string;
		}
	}

	/**
	 * ���� -- ��
	 * @param string
	 */
	public void setRemitInCity(String string)
	{
		if (string != null)
		{
			RemitInCity = string;
		}
	}

	/**
	 * ���� -- ���ڿͻ���ʶ
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * ���� -- ���ڿͻ�����
	 * @param string
	 */
	public void setClientName(String string)
	{
		if (string != null)
		{
			ClientName = string;
		}
	}

	/**
	 * ���� -- ���ڿͻ����
	 * @param string
	 */
	public void setClientNo(String string)
	{
		if (string != null)
		{
			ClientNo = string;
		}
	}

	/**
	 * ���� -- ί�и���ƾ֤������
	 * @param string
	 */
	public void setConsignPassword(String string)
	{
		if (string != null)
		{
			ConsignPassword = string;
		}
	}

	/**
	 * ���� -- ί�и���ƾ֤��
	 * @param string
	 */
	public void setConsignVoucherNo(String string)
	{
		if (string != null)
		{
			ConsignVoucherNo = string;
		}
	}

	/**
	 * ���� -- ���ֱ�ʶ
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * ���� -- ��/�������˻��ͻ�����
	 * @param string
	 */
	public void setCurrentAccountClientName(String string)
	{
		if (string != null)
		{
			CurrentAccountClientName = string;
		}
	}

	/**
	 * ���� -- ��/�������˻�ID
	 * @param l
	 */
	public void setCurrentAccountID(long l)
	{
		CurrentAccountID = l;
	}

	/**
	 * ���� -- ��/�������˻���
	 * @param string
	 */
	public void setCurrentAccountNo(String string)
	{
		if (string != null)
		{
			CurrentAccountNo = string;
		}
	}

	


	/**
	 * ���� -- �浥��ţ��Ӷ����˻���ţ�
	 * @param string
	 */
	public void setDepositNo(String string)
	{
		if (string != null)
		{
			DepositNo = string;
		}
	}

	/**
	 * ���� -- ���ڴ�����ޣ��£�
	 * @param l
	 */
	public void setDepositTerm(long l)
	{
		DepositTerm = l;
	}

	/**
	 * ���� -- ����(��ֹ)����
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		EndDate = timestamp;
	}

	/**
	 * ���� -- ִ����
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		ExecuteDate = timestamp;
	}

	/**
	 * ���� -- �ǲ���˾�˻�ID
	 * @param l
	 */
	public void setExtAcctID(long l)
	{
		ExtAcctID = l;
	}

	/**
	 * ���� -- �ǲ���˾�˻�����
	 * @param string
	 */
	public void setExtClientName(String string)
	{
		if (string != null)
		{
			ExtClientName = string;
		}
	}

	/**
	 * ���� -- �ǲ���˾�˻���
	 * @param string
	 */
	public void setExtAcctNo(String string)
	{
		if (string != null)
		{
			ExtAcctNo = string;
		}
	}	

	/**
	 * ���� -- ��������к�
	 * @param string
	 */
	public void setExtBankNo(String string)
	{
		if (string != null)
		{
			ExtBankNo = string;
		}
	}

	/**
	 * ���� -- Ψһ��ʶ
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * ���� -- ¼����ID
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * ���� -- ¼��������
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		if (string != null)
		{
			InputUserName = string;
		}
	}

	/**
	 * ���� -- ��ʶ�ǽ���ϵͳ��������ˮ��
	 * @param string
	 */
	public void setInstructionNo(String string)
	{
		if (string != null)
		{
			InstructionNo = string;
		}
	}

	/**
	 * ���� -- ���ʼƻ�
	 * @param l
	 */
	public void setInterestPlanID(long l)
	{
		InterestPlanID = l;
	}

	/**
	 * ���� -- ��Ϣ��
	 * @param timestamp
	 */
	public void setInterestStartDate(Timestamp timestamp)
	{
		InterestStartDate = timestamp;
	}

	/**
	 * ���� -- �޸�ʱ�䣺ʱ����
	 * @param timestamp
	 */
	public void setModifyDate(Timestamp timestamp)
	{
		ModifyDate = timestamp;
	}

	/**
	 * ���� -- ֪ͨ���֧ȡ���ڣ��죩
	 * @param l
	 */
	public void setNoticeDay(long l)
	{
		NoticeDay = l;
	}

	/**
	 * ���� -- ���´���ʶ
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	

	/**
	 * ���� -- ʡ
	 * @param string
	 */
	public void setRemitInProvince(String string)
	{
		if (string != null)
		{
			RemitInProvince = string;
		}
	}

	/**
	 * ���� -- ����
	 * @param d
	 */
	public void setRate(double d)
	{
		Rate = d;
	}

	/**
	 * ���� -- ӡ������������ID
	 * @param l
	 */
	public void setSealBankID(long l)
	{
		SealBankID = l;
	}

	/**
	 * ���� -- ӡ������
	 * @param string
	 */
	public void setSealNo(String string)
	{
		if (string != null)
		{
			SealNo = string;
		}
	}

	/**
	 * ���� -- ��ʼ(��ʼ)����
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		StartDate = timestamp;
	}

	/**
	 * ���� -- ����״̬
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}	

	/**
	 * ���� -- �������ͣ����ڿ���������֧ȡ����������ת�桢֪ͨ������֪֧ͨȡ��
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}

	/**
	 * ���� -- ���ױ��
	 * @param string
	 */
	public void setTransNo(String string)
	{
		if (string != null)
		{
			TransNo = string;
		}
	}

	/**
	 * �õ�ժҪID
	 * @return
	 */
	public long getAbstractID() {
		return AbstractID;
	}

	/**
	 * ����ժҪID
	 * @param l
	 */
	public void setAbstractID(long l) {
		AbstractID = l;
	}

	/**
	 * �õ��ǲ���˾��������
	 * @return
	 */
	public String getRemitInBank() {
		return RemitInBank;
	}

	/**
	 * ���÷ǲ���˾��������
	 * @param string
	 */
	public void setRemitInBank(String string) {
		RemitInBank = string;
	}

	/**
	 * �õ�¼������
	 * @return
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}

	/**
	 * ����¼������
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp) {
		InputDate = timestamp;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public long getIsAutoContinue() {
		return isAutoContinue;
	}

	public void setIsAutoContinue(long isAutoContinue) {
		this.isAutoContinue = isAutoContinue;
	}

	public long getAutocontinuetype() {
		return autocontinuetype;
	}

	public void setAutocontinuetype(long autocontinuetype) {
		this.autocontinuetype = autocontinuetype;
	}

	public long getAutocontinueaccountid() {
		return autocontinueaccountid;
	}

	public void setAutocontinueaccountid(long autocontinueaccountid) {
		this.autocontinueaccountid = autocontinueaccountid;
	}

}
