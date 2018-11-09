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
 *	���ڴ������ת�潻��ʵ���ࣺ
 *	1�����б�����ΪPrivate,����ֻ����setXXX�������õ�ֻ����getXXX������
 *	2���������������͡�Ĭ��ֵ��˵��
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFixedContinueInfo implements Serializable
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
	private long AccountID = -1; //�������˻�ID
	private String AccountNo = ""; //�������˻����
	private String DepositNo = ""; //�浥��ţ��Ӷ����˻���ţ�
	private String CertificationNo = ""; //֤ʵ���
	private long CertificationBankID = -1; //֤ʵ�鷢������ID
	private String SealNo = ""; //ӡ������
	private long SealBankID = -1; //ӡ������������ID
	private double Rate = 0.0; //����
	private Timestamp StartDate = null; //��ʼ(��ʼ)����
	private Timestamp EndDate = null; //����(��ֹ)����
	private long DepositTerm = -1; //���ڴ�����ޣ��£�
	private long InterestPlanID = -1; //���ʼƻ�
	private long SubAccountID = -1; //���˻�ID
	private double Amount = 0.0; //������
	private Timestamp ExecuteDate = null; //����ת��ִ����
	
	private String NewDepositNo = ""; //�´浥��ţ��Ӷ����˻���ţ�
	private String NewCertificationNo = ""; //��֤ʵ���
	private long NewCertificationBankID = -1; //��֤ʵ�鷢������ID
	private double NewRate = 0.0; //�´浥����
	private Timestamp NewStartDate = null; //�´浥��ʼ(��ʼ)����
	private Timestamp NewEndDate = null; //�´浥����(��ֹ)����
	private long NewDepositTerm = -1; //�´浥���ڴ�����ޣ��£�
	private long NewInterestPlanID = -1; //�´浥���ʼƻ�
	private double NewAmount = 0.0; //�´浥������
	private String NewSealNo = ""; //��ӡ������
	private long NewSealBankID = -1; //��ӡ������������ID	
	private double PreDrawInterest = 0.0; //������Ϣ
	private double PayableInterest = 0.0; //��Ϣ֧��
	private double WithDrawInterest = 0.0; //����֧ȡ��Ϣ����Ϣ�ϼƣ�
	private long IsCapitalAndInterestTransfer = -1; //�Ƿ�������  1 �� -1 ��
	private long ReceiveInterestAccountID = -1; //��Ϣ�˻�ID
	private String ReceiveInterestAccountNo = ""; //��Ϣ�˻���
	private long InterestPayTypeID = -1; //��Ϣ���ʽ
	private long InterestBankID = -1; //��Ϣ������ID
	private String InterestBankName = ""; //��Ϣ����������
	private String InterestExtAcctNo = ""; //��Ϣ�ǲ���˾�˻���
	private String InterestExtClientName = ""; //��Ϣ�ǲ���˾�ͻ�����
	private String InterestRemitInBank = ""; //��Ϣ�ǲ���˾��������
	private String InterestRemitInProvince = ""; //ʡ
	private String InterestRemitInCity = ""; //��	
	private long InterestCashFlowID = -1; //��Ϣ�ֽ�����ID
	private String InterestCashFlowDesc = ""; //��Ϣ�ֽ���������
	private String InterestExtBankNo;  //��Ϣ�����к�	
	private Timestamp InterestStartDate = null; //��Ϣ��
	private Timestamp ModifyDate = null; //�޸�ʱ�䣺ʱ����
	private Timestamp InputDate = null; //¼������
	private long AbstractID = -1; //ժҪID
	private String Abstract = ""; //ժҪ
	private long InputUserID = -1; //¼����ID
	private String InputUserName = ""; //¼��������
	private long CheckUserID = -1; //������ID
	private String CheckUserName = ""; //����������
	private String CheckAbstract = ""; //����/ȡ������ժҪ
	private long StatusID = -1; //����״̬
	private String InstructionNo = ""; //��ʶ�ǽ���ϵͳ��������ˮ��		
	private InutParameterInfo inutParameterInfo = null;
	private double advanceRate = 0.0;  //���������������
	private double CurrentInterest = 0.0; //������Ϣ
	private long isAutoContinue = -1;
	private long autocontinuetype = -1;
	private long autocontinueaccountid = -1;
	
	public double getCurrentInterest() {
		return CurrentInterest;
	}

	public void setCurrentInterest(double currentInterest) {
		CurrentInterest = currentInterest;
	}

	public double getAdvanceRate() {
		return advanceRate;
	}

	public void setAdvanceRate(double advanceRate) {
		this.advanceRate = advanceRate;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

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
	 * ��ȡ -- ���ֱ�ʶ
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
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
	 * ��ȡ -- ���´���ʶ
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}	

	/**
	 * ��ȡ -- ������Ϣ
	 * @return
	 */
	public double getPreDrawInterest()
	{
		return PreDrawInterest;
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
	 * ��ȡ -- �Ӷ����˻�ID
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
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
	 * ���� -- ���ֱ�ʶ
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
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
	 * ���� -- ���´���ʶ
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	

	/**
	 * ���� -- ������Ϣ
	 * @param d
	 */
	public void setPreDrawInterest(double d)
	{
		PreDrawInterest = d;
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
	 * ���� -- �Ӷ����˻�ID
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
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
	 * �õ���Ϣ����ID
	 * @return
	 */
	public long getInterestBankID() {
		return InterestBankID;
	}

	/**
	 * �õ���Ϣ��������
	 * @return
	 */
	public String getInterestBankName() {
		return InterestBankName;
	}

	/**
	 * �õ���Ϣ�ֽ���������
	 * @return
	 */
	public String getInterestCashFlowDesc() {
		return InterestCashFlowDesc;
	}

	/**
	 * �õ���Ϣ�ֽ�����ID
	 * @return
	 */
	public long getInterestCashFlowID() {
		return InterestCashFlowID;
	}

	/**
	 * �õ��У���Ϣ��أ�
	 * @return
	 */
	public String getInterestRemitInCity() {
		return InterestRemitInCity;
	}

	/**
	 * �õ��ǲ���˾�˻����ƣ���Ϣ��أ�
	 * @return
	 */
	public String getInterestExtClientName() {
		return InterestExtClientName;
	}

	/**
	 * �õ��ǲ���˾�˻�ID����Ϣ��أ�
	 * @return
	 */
	public String getInterestExtAcctNo() {
		return InterestExtAcctNo;
	}

	/**
	 * �õ��ǲ���˾��������
	 * @return
	 */
	public String getInterestRemitInBank() {
		return InterestRemitInBank;
	}

	/**
	 * �õ���Ϣ�����к�
	 * @return
	 */
	public String getInterestExtBankNo() {
		return InterestExtBankNo;
	}

	/**
	 * �õ���Ϣ���ʽID
	 * @return
	 */
	public long getInterestPayTypeID() {
		return InterestPayTypeID;
	}

	/**
	 * �õ� ʡ����Ϣ��أ�
	 * @return
	 */
	public String getInterestRemitInProvince() {
		return InterestRemitInProvince;
	}

	/**
	 * �õ��Ƿ�������
	 * @return
	 */
	public long getIsCapitalAndInterestTransfer() {
		return IsCapitalAndInterestTransfer;
	}

	/**
	 * �õ��´浥����
	 * @return
	 */
	public double getNewAmount() {
		return NewAmount;
	}

	/**
	 * �õ��´浥֤ʵ�鷢������ID
	 * @return
	 */
	public long getNewCertificationBankID() {
		return NewCertificationBankID;
	}

	/**
	 * �õ���֤ʵ���
	 * @return
	 */
	public String getNewCertificationNo() {
		return NewCertificationNo;
	}

	/**
	 * �õ��´浥��
	 * @return
	 */
	public String getNewDepositNo() {
		return NewDepositNo;
	}

	/**
	 * �õ��´浥�������
	 * @return
	 */
	public long getNewDepositTerm() {
		return NewDepositTerm;
	}

	/**
	 * �õ��´浥������
	 * @return
	 */
	public Timestamp getNewEndDate() {
		return NewEndDate;
	}

	/**
	 * �õ��´浥���ʼƻ�
	 * @return
	 */
	public long getNewInterestPlanID() {
		return NewInterestPlanID;
	}

	/**
	 * �õ��´浥����
	 * @return
	 */
	public double getNewRate() {
		return NewRate;
	}

	/**
	 * �õ��´浥��ʼ��
	 * @return
	 */
	public Timestamp getNewStartDate() {
		return NewStartDate;
	}

	/**
	 * �õ���Ϣ֧��
	 * @return
	 */
	public double getPayableInterest() {
		return PayableInterest;
	}

	/**
	 * �õ���Ϣ�˻�ID
	 * @return
	 */
	public long getReceiveInterestAccountID() {
		return ReceiveInterestAccountID;
	}

	/**
	 * �õ���Ϣ�˻���
	 * @return
	 */
	public String getReceiveInterestAccountNo() {
		return ReceiveInterestAccountNo;
	}

	/**
	 * ������Ϣ����ID
	 * @param l
	 */
	public void setInterestBankID(long l) {
		InterestBankID = l;
	}

	/**
	 * ������Ϣ��������
	 * @param string
	 */
	public void setInterestBankName(String string) {
		InterestBankName = string;
	}

	/**
	 * ������Ϣ�ֽ���������
	 * @param string
	 */
	public void setInterestCashFlowDesc(String string) {
		InterestCashFlowDesc = string;
	}

	/**
	 * ������Ϣ�ֽ�����ID
	 * @param l
	 */
	public void setInterestCashFlowID(long l) {
		InterestCashFlowID = l;
	}

	/**
	 * �����У���Ϣ��أ�
	 * @param string
	 */
	public void setInterestRemitInCity(String string) {
		InterestRemitInCity = string;
	}

	/**
	 * ���÷ǲ���˾�˻�������Ϣ��أ�
	 * @param string
	 */
	public void setInterestExtClientName(String string) {
		InterestExtClientName = string;
	}

	/**
	 * ���÷ǲ���˾�˻��ţ���Ϣ��أ�
	 * @param string
	 */
	public void setInterestExtAcctNo(String string) {
		InterestExtAcctNo = string;
	}

	/**
	 * ���÷ǲ���˾��������
	 * @param string
	 */
	public void setInterestRemitInBank(String string) {
		InterestRemitInBank = string;
	}

	/**
	 * ������Ϣ�����к�
	 * @param string
	 */
	public void setInterestExtBankNo(String string) {
		InterestExtBankNo = string;
	}

	/**
	 * ������Ϣ���ʽID
	 * @param l
	 */
	public void setInterestPayTypeID(long l) {
		InterestPayTypeID = l;
	}

	/**
	 * ����ʡ����Ϣ��أ�
	 * @param string
	 */
	public void setInterestRemitInProvince(String string) {
		InterestRemitInProvince = string;
	}

	/**
	 * �����Ƿ�������
	 * @param l
	 */
	public void setIsCapitalAndInterestTransfer(long l) {
		IsCapitalAndInterestTransfer = l;
	}

	/**
	 * �����´浥����
	 * @param d
	 */
	public void setNewAmount(double d) {
		NewAmount = d;
	}

	/**
	 * ������֤ʵ�鷢������ID
	 * @param l
	 */
	public void setNewCertificationBankID(long l) {
		NewCertificationBankID = l;
	}

	/**
	 * ������֤ʵ���
	 * @param string
	 */
	public void setNewCertificationNo(String string) {
		NewCertificationNo = string;
	}

	/**
	 * �����´��ݺ�
	 * @param string
	 */
	public void setNewDepositNo(String string) {
		NewDepositNo = string;
	}

	/**
	 * �����´浥�������
	 * @param l
	 */
	public void setNewDepositTerm(long l) {
		NewDepositTerm = l;
	}

	/**
	 * �����´浥������
	 * @param timestamp
	 */
	public void setNewEndDate(Timestamp timestamp) {
		NewEndDate = timestamp;
	}

	/**
	 * �����´浥���ʼƻ�
	 * @param l
	 */
	public void setNewInterestPlanID(long l) {
		NewInterestPlanID = l;
	}

	/**
	 * �����´浥����
	 * @param d
	 */
	public void setNewRate(double d) {
		NewRate = d;
	}

	/**
	 * �����´浥��ʼ����
	 * @param timestamp
	 */
	public void setNewStartDate(Timestamp timestamp) {
		NewStartDate = timestamp;
	}

	/**
	 * ������Ϣ֧��
	 * @param d
	 */
	public void setPayableInterest(double d) {
		PayableInterest = d;
	}

	/**
	 * ������Ϣ�˻�ID
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l) {
		ReceiveInterestAccountID = l;
	}

	/**
	 * ������Ϣ�˻���
	 * @param string
	 */
	public void setReceiveInterestAccountNo(String string) {
		ReceiveInterestAccountNo = string;
	}

	/**
	 * �õ�����֧ȡ��Ϣ
	 * @return
	 */
	public double getWithDrawInterest() {
		return PayableInterest + PreDrawInterest;
	}

	/**
	 * ���ñ���֧ȡ��Ϣ
	 * @param d
	 */
	public void setWithDrawInterest(double d) {
		WithDrawInterest = d;
	}

	/**
	 * @return
	 */
	public long getNewSealBankID()
	{
		return NewSealBankID;
	}

	/**
	 * @return
	 */
	public String getNewSealNo()
	{
		return NewSealNo;
	}

	/**
	 * @param l
	 */
	public void setNewSealBankID(long l)
	{
		NewSealBankID = l;
	}

	/**
	 * @param string
	 */
	public void setNewSealNo(String string)
	{
		NewSealNo = string;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
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
