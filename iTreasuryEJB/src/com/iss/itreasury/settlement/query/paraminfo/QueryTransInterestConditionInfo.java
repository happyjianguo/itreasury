/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryTransInterestConditionInfo implements Serializable
{
	private long OfficeID = -1;//���´�
	private long CurrencyID = -1;//����
	
	private long InterestSettlementType = -1;//��Ϣ����(1,���㽻�ף�2����Ϣ��¼)
	private long[] InterestType = null;//��Ϣ����
	
	private long ClientIDStart = -1;//�ͻ�ID���ɣ�
	private String ClientNoStart = "";//�ͻ���ţ��ɣ�
	private long ClientIDEnd = -1;//�ͻ�ID������
	private String ClientNoEnd = "";//�ͻ���ţ�����
	private long AccountIDStart = -1;//�˻����ɣ�
	private String AccountNoStart = "";//�˻��ţ��ɣ�
	private long AccountIDEnd = -1;//�˻�������
	private String AccountNoEnd = "";//�˻��ţ�����
	//add by 2012-05-18 ���ָ���˻����
	private String appointAccountNo = "";
	private long AccountTypeID = -1;//�˻�����ID
	
	private long EnterpriseTypeID1 = -1;//�ͻ�����1
	private long EnterpriseTypeID2 = -1;//�ͻ�����2
	private long EnterpriseTypeID3 = -1;//�ͻ�����3
	private long EnterpriseTypeID4 = -1;//�ͻ�����4
	private long EnterpriseTypeID5 = -1;//�ͻ�����5
	private long EnterpriseTypeID6 = -1;//�ͻ�����6
			
	private String FixedDepositNoStart = "";//���ڴ浥�ţ��ɣ�
	private String FixedDepositNoEnd = "";//���ڴ浥�ţ�����	
	private String NotifyDepositNoStart = "";//֪ͨ�浥�ţ��ɣ�
	private String NotifyDepositNoEnd = "";//֪ͨ�浥�ţ�����	
	private long DepositTerm = -1;// �������
	
	private String ContractNoStart = "";//��ͬ�ţ��ɣ�
	private String ContractNoEnd = "";//��ͬ�ţ�����	
	private String PayFormNoStart = "";//�ſ�֪ͨ��(����ƾ֤)ID���ɣ�
	private String PayFormNoEnd = "";//�ſ�֪ͨ��(����ƾ֤)ID������	
	private long LoanTypeID = -1;//��������
	private long LoanTerm = -1;//��������
	private long LoanYear = -1;//��������
	
	private long ConsignClientID = -1;//ί�з��ͻ�ID
	private long ClientNature = -1;//�ͻ�����
	
	private Timestamp AutoExecuteDateStart = null;//��Ϣ�Զ�ִ��ʱ�䣨�ɣ�
	private Timestamp AutoExecuteDateEnd = null;//��Ϣ�Զ�ִ��ʱ�䣨����
	private Timestamp ExecuteDateStart = null;//����ִ���գ��ɣ�
	private Timestamp ExecuteDateEnd = null;//����ִ���գ�����		
	private Timestamp CalInterestDateStart = null;//��Ϣ���ڣ��ɣ�
	private Timestamp CalInterestDateEnd = null;//��Ϣ���ڣ�����	
	
	private long OrderID = -1;//��������ID
	private long DESC = -1;//������ID
	private long PageCount = -1;//ÿҳ��¼����
	
	private String TransNo = "";//���׺�

	private long ExtendAttribute1 = -1;//��չ����1
    private long ExtendAttribute2 = -1;//��չ����2
    private long ExtendAttribute3 = -1;//��չ����3
    private long ExtendAttribute4 = -1;//��չ����4
	
	/**
	 * @return
	 */
	public long getAccountIDEnd()
	{
		return AccountIDEnd;
	}

	/**
	 * @return
	 */
	public long getAccountIDStart()
	{
		return AccountIDStart;
	}

	/**
	 * @return
	 */
	public String getAccountNoEnd()
	{
		return AccountNoEnd;
	}

	/**
	 * @return
	 */
	public String getAccountNoStart()
	{
		return AccountNoStart;
	}

	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @return
	 */
	public Timestamp getAutoExecuteDateEnd()
	{
		return AutoExecuteDateEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getAutoExecuteDateStart()
	{
		return AutoExecuteDateStart;
	}

	/**
	 * @return
	 */
	public Timestamp getCalInterestDateEnd()
	{
		return CalInterestDateEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getCalInterestDateStart()
	{
		return CalInterestDateStart;
	}

	/**
	 * @return
	 */
	public long getClientIDEnd()
	{
		return ClientIDEnd;
	}

	/**
	 * @return
	 */
	public long getClientIDStart()
	{
		return ClientIDStart;
	}

	/**
	 * @return
	 */
	public String getClientNoEnd()
	{
		return ClientNoEnd;
	}

	/**
	 * @return
	 */
	public String getClientNoStart()
	{
		return ClientNoStart;
	}

	/**
	 * @return
	 */
	public String getContractNoEnd()
	{
		return ContractNoEnd;
	}

	/**
	 * @return
	 */
	public String getContractNoStart()
	{
		return ContractNoStart;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public long getDepositTerm()
	{
		return DepositTerm;
	}

	/**
	 * @return
	 */
	public long getDESC()
	{
		return DESC;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDateEnd()
	{
		return ExecuteDateEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDateStart()
	{
		return ExecuteDateStart;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNoEnd()
	{
		return FixedDepositNoEnd;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNoStart()
	{
		return FixedDepositNoStart;
	}

	/**
	 * @return
	 */
	public long getInterestSettlementType()
	{
		return InterestSettlementType;
	}

	/**
	 * @return
	 */
	public long[] getInterestType()
	{
		return InterestType;
	}

	/**
	 * @return
	 */
	public long getClientNature()
	{
		return ClientNature;
	}

	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return ConsignClientID;
	}

	/**
	 * @return
	 */
	public long getLoanTerm()
	{
		return LoanTerm;
	}

	/**
	 * @return
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	/**
	 * @return
	 */
	public long getLoanYear()
	{
		return LoanYear;
	}

	/**
	 * @return
	 */
	public String getNotifyDepositNoEnd()
	{
		return NotifyDepositNoEnd;
	}

	/**
	 * @return
	 */
	public String getNotifyDepositNoStart()
	{
		return NotifyDepositNoStart;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return
	 */
	public long getOrderID()
	{
		return OrderID;
	}

	/**
	 * @return
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * @return
	 */
	public String getPayFormNoEnd()
	{
		return PayFormNoEnd;
	}

	/**
	 * @return
	 */
	public String getPayFormNoStart()
	{
		return PayFormNoStart;
	}

	/**
	 * @param l
	 */
	public void setAccountIDEnd(long l)
	{
		AccountIDEnd = l;
	}

	/**
	 * @param l
	 */
	public void setAccountIDStart(long l)
	{
		AccountIDStart = l;
	}

	/**
	 * @param string
	 */
	public void setAccountNoEnd(String string)
	{
		AccountNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNoStart(String string)
	{
		AccountNoStart = string;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setAutoExecuteDateEnd(Timestamp timestamp)
	{
		AutoExecuteDateEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setAutoExecuteDateStart(Timestamp timestamp)
	{
		AutoExecuteDateStart = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setCalInterestDateEnd(Timestamp timestamp)
	{
		CalInterestDateEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setCalInterestDateStart(Timestamp timestamp)
	{
		CalInterestDateStart = timestamp;
	}

	/**
	 * @param l
	 */
	public void setClientIDEnd(long l)
	{
		ClientIDEnd = l;
	}

	/**
	 * @param l
	 */
	public void setClientIDStart(long l)
	{
		ClientIDStart = l;
	}

	/**
	 * @param string
	 */
	public void setClientNoEnd(String string)
	{
		ClientNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setClientNoStart(String string)
	{
		ClientNoStart = string;
	}

	/**
	 * @param string
	 */
	public void setContractNoEnd(String string)
	{
		ContractNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setContractNoStart(String string)
	{
		ContractNoStart = string;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setDepositTerm(long l)
	{
		DepositTerm = l;
	}

	/**
	 * @param l
	 */
	public void setDESC(long l)
	{
		DESC = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDateEnd(Timestamp timestamp)
	{
		ExecuteDateEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDateStart(Timestamp timestamp)
	{
		ExecuteDateStart = timestamp;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNoEnd(String string)
	{
		FixedDepositNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNoStart(String string)
	{
		FixedDepositNoStart = string;
	}

	/**
	 * @param l
	 */
	public void setInterestSettlementType(long l)
	{
		InterestSettlementType = l;
	}

	/**
	 * @param l
	 */
	public void setInterestType(long[] l)
	{
		InterestType = l;
	}

	/**
	 * @param l
	 */
	public void setClientNature(long l)
	{
		ClientNature = l;
	}

	/**
	 * @param l
	 */
	public void setConsignClientID(long l)
	{
		ConsignClientID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanTerm(long l)
	{
		LoanTerm = l;
	}

	/**
	 * @param l
	 */
	public void setLoanTypeID(long l)
	{
		LoanTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanYear(long l)
	{
		LoanYear = l;
	}

	/**
	 * @param string
	 */
	public void setNotifyDepositNoEnd(String string)
	{
		NotifyDepositNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setNotifyDepositNoStart(String string)
	{
		NotifyDepositNoStart = string;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setOrderID(long l)
	{
		OrderID = l;
	}

	/**
	 * @param l
	 */
	public void setPageCount(long l)
	{
		PageCount = l;
	}

	/**
	 * @param string
	 */
	public void setPayFormNoEnd(String string)
	{
		PayFormNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setPayFormNoStart(String string)
	{
		PayFormNoStart = string;
	}

	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}

	/**
	 * @return Returns the extendAttribute1.
	 */
	public long getExtendAttribute1()
	{
		return ExtendAttribute1;
	}
	/**
	 * @param extendAttribute1 The extendAttribute1 to set.
	 */
	public void setExtendAttribute1(long extendAttribute1)
	{
		ExtendAttribute1 = extendAttribute1;
	}
	/**
	 * @return Returns the extendAttribute2.
	 */
	public long getExtendAttribute2()
	{
		return ExtendAttribute2;
	}
	/**
	 * @param extendAttribute2 The extendAttribute2 to set.
	 */
	public void setExtendAttribute2(long extendAttribute2)
	{
		ExtendAttribute2 = extendAttribute2;
	}
	/**
	 * @return Returns the extendAttribute3.
	 */
	public long getExtendAttribute3()
	{
		return ExtendAttribute3;
	}
	/**
	 * @param extendAttribute3 The extendAttribute3 to set.
	 */
	public void setExtendAttribute3(long extendAttribute3)
	{
		ExtendAttribute3 = extendAttribute3;
	}
	/**
	 * @return Returns the extendAttribute4.
	 */
	public long getExtendAttribute4()
	{
		return ExtendAttribute4;
	}
	/**
	 * @param extendAttribute4 The extendAttribute4 to set.
	 */
	public void setExtendAttribute4(long extendAttribute4)
	{
		ExtendAttribute4 = extendAttribute4;
	}
	
	/**
	 * @return Returns the enterpriseTypeID1.
	 */
	public long getEnterpriseTypeID1() {
		return EnterpriseTypeID1;
	}
	/**
	 * @param enterpriseTypeID1 The enterpriseTypeID1 to set.
	 */
	public void setEnterpriseTypeID1(long enterpriseTypeID1) {
		EnterpriseTypeID1 = enterpriseTypeID1;
	}
	/**
	 * @return Returns the enterpriseTypeID2.
	 */
	public long getEnterpriseTypeID2() {
		return EnterpriseTypeID2;
	}
	/**
	 * @param enterpriseTypeID2 The enterpriseTypeID2 to set.
	 */
	public void setEnterpriseTypeID2(long enterpriseTypeID2) {
		EnterpriseTypeID2 = enterpriseTypeID2;
	}
	/**
	 * @return Returns the enterpriseTypeID3.
	 */
	public long getEnterpriseTypeID3() {
		return EnterpriseTypeID3;
	}
	/**
	 * @param enterpriseTypeID3 The enterpriseTypeID3 to set.
	 */
	public void setEnterpriseTypeID3(long enterpriseTypeID3) {
		EnterpriseTypeID3 = enterpriseTypeID3;
	}
	/**
	 * @return Returns the enterpriseTypeID4.
	 */
	public long getEnterpriseTypeID4() {
		return EnterpriseTypeID4;
	}
	/**
	 * @param enterpriseTypeID4 The enterpriseTypeID4 to set.
	 */
	public void setEnterpriseTypeID4(long enterpriseTypeID4) {
		EnterpriseTypeID4 = enterpriseTypeID4;
	}
	/**
	 * @return Returns the enterpriseTypeID5.
	 */
	public long getEnterpriseTypeID5() {
		return EnterpriseTypeID5;
	}
	/**
	 * @param enterpriseTypeID5 The enterpriseTypeID5 to set.
	 */
	public void setEnterpriseTypeID5(long enterpriseTypeID5) {
		EnterpriseTypeID5 = enterpriseTypeID5;
	}
	/**
	 * @return Returns the enterpriseTypeID6.
	 */
	public long getEnterpriseTypeID6() {
		return EnterpriseTypeID6;
	}
	/**
	 * @param enterpriseTypeID6 The enterpriseTypeID6 to set.
	 */
	public void setEnterpriseTypeID6(long enterpriseTypeID6) {
		EnterpriseTypeID6 = enterpriseTypeID6;
	}

	//add by 2012-05-18 ���ָ���˻����
	public String getAppointAccountNo() {
		return appointAccountNo;
	}

	public void setAppointAccountNo(String appointAccountNo) {
		this.appointAccountNo = appointAccountNo;
	}
	
}
