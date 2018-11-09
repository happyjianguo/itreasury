/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryTransAccountDetailWhereInfo extends BaseDataEntity implements Serializable 
{

	/**
	 * 
	 */
	public QueryTransAccountDetailWhereInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
    private long OfficeID = -1;
    private long CurrencyID = -1;
    private String StartClientCode = "";//�ͻ������
    private String EndClientCode = "";//�ͻ���ŵ�
    private String StartAccountNo = "";//�˻�����
    private String EndAccountNo = "";//�˻��ŵ�
    //add by 2012-05-15 ���ָ�����
	private String appointAccountNo = "";
	private Timestamp StartDate = null;//��ʼ����
	private Timestamp EndDate = null;//��������
	private long IsFilterNull = -1;//�˿�(�������ս���)update by xfma 2008-12-2
	private long IsIntrDate = -1;//����Ϣ��ͳ�� add by xfma 2008-12-2
	private String AccountStatusIDs = "";//�˻�״̬����ѡ�� add by xfma 2008-12-2
	//��ϸ��ѯ����ʹ�õ�����
	private String ClientCode = "";//�ͻ���
	private String AccountNo = "";//�˻���
	private long AccountID = -1;//�˻�ID
	private long SubAccountID = -1;//���˻�ID
	private String ContractCode = "";//��ͬ��
	private long ContractID = -1;//��ͬID
	private String LoanNoteNo = "";//�ſ��
	private long LoanNoteID = -1;//�ſ��ID
	private String FixedDepositNo = "";//���ڴ��ݺ�
	private long FixedDepositID = -1;//���ڴ��ݺ�ID
	private String CurrentYear = "";//��ǰ��
	private String CurrentMonth = "";//��ǰ��
	
    private long AccountTypeID = -1;
    private String AccountTypeIDs = "";
    private long Desc = 1;
    private long OrderField = 1;
    
    private String accountnos = "";
    private long unit = 1;
    private double dCanUseBalance = 0.0;

	public double getDCanUseBalance() {
		return dCanUseBalance;
	}

	public void setDCanUseBalance(double canUseBalance) {
		dCanUseBalance = canUseBalance;
	}

	/**
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
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
	public String getClientCode()
	{
		return ClientCode;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return ContractCode;
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
	public long getDesc()
	{
		return Desc;
	}

	/**
	 * @return
	 */
	public String getEndAccountNo()
	{
		return EndAccountNo;
	}

	/**
	 * @return
	 */
	public String getEndClientCode()
	{
		return EndClientCode;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}

	/**
	 * @return
	 */
	public String getFixedDepositNo()
	{
		return FixedDepositNo;
	}

	/**
	 * @return
	 */
	public String getLoanNoteNo()
	{
		return LoanNoteNo;
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
	public long getOrderField()
	{
		return OrderField;
	}

	/**
	 * @return
	 */
	public String getStartAccountNo()
	{
		return StartAccountNo;
	}

	/**
	 * @return
	 */
	public String getStartClientCode()
	{
		return StartClientCode;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		AccountNo = string;
	}

	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setClientCode(String string)
	{
		ClientCode = string;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		ContractCode = string;
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
	public void setDesc(long l)
	{
		Desc = l;
	}

	/**
	 * @param string
	 */
	public void setEndAccountNo(String string)
	{
		EndAccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setEndClientCode(String string)
	{
		EndClientCode = string;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		EndDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setFixedDepositNo(String string)
	{
		FixedDepositNo = string;
	}

	/**
	 * @param string
	 */
	public void setLoanNoteNo(String string)
	{
		LoanNoteNo = string;
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
	public void setOrderField(long l)
	{
		OrderField = l;
	}

	/**
	 * @param string
	 */
	public void setStartAccountNo(String string)
	{
		StartAccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setStartClientCode(String string)
	{
		StartClientCode = string;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		StartDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @return
	 */
	public long getFixedDepositID()
	{
		return FixedDepositID;
	}

	/**
	 * @return
	 */
	public long getLoanNoteID()
	{
		return LoanNoteID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		ContractID = l;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositID(long l)
	{
		FixedDepositID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanNoteID(long l)
	{
		LoanNoteID = l;
	}

	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}

	/**
	 * @param string
	 */
	public void setCurrentMonth(String string)
	{
		CurrentMonth = string;
	}

	/**
	 * @param string
	 */
	public void setCurrentYear(String string)
	{
		CurrentYear = string;
	}

	/**
	 * @return
	 */
	public String getCurrentMonth()
	{
		return CurrentMonth;
	}

	/**
	 * @return
	 */
	public String getCurrentYear()
	{
		return CurrentYear;
	}

	/**
	 * @return Returns the isFilterNull.
	 */
	public long getIsFilterNull() {
		return IsFilterNull;
	}
	/**
	 * @param isFilterNull The isFilterNull to set.
	 */
	public void setIsFilterNull(long isFilterNull) {
		IsFilterNull = isFilterNull;
	}

	public String getAccountStatusIDs() {
		return AccountStatusIDs;
	}

	public void setAccountStatusIDs(String accountStatusIDs) {
		AccountStatusIDs = accountStatusIDs;
	}

	public long getIsIntrDate() {
		return IsIntrDate;
	}

	public void setIsIntrDate(long isIntrDate) {
		IsIntrDate = isIntrDate;
	}

	public String getAccountTypeIDs() {
		return AccountTypeIDs;
	}

	public void setAccountTypeIDs(String accountTypeIDs) {
		AccountTypeIDs = accountTypeIDs;
	}
	
	//add by 2012-05-15 ���ָ�����
	public String getAppointAccountNo() {
		return appointAccountNo;
	}
	public void setAppointAccountNo(String appointAccountNo) {
		this.appointAccountNo = appointAccountNo;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	public String getAccountnos() {
		return accountnos;
	}

	public void setAccountnos(String accountnos) {
		this.accountnos = accountnos;
	}

	public long getUnit() {
		return unit;
	}

	public void setUnit(long unit) {
		this.unit = unit;
	}
	
}
