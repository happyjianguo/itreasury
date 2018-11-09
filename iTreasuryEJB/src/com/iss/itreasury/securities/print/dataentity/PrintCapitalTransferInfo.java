/*
 * Created on 2004-4-26
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author chluo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintCapitalTransferInfo extends SECBaseDataEntity	implements Serializable 
{

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) 
	{
		// TODO Auto-generated method stub
		
	}
//	ҵ��֪ͨ�����
	private String noticeformCode          = "";
//	�ɽ�����
	private Timestamp transactionDate  = null;
//	¼����ID
	private long inputuserID            = -1;
//	ҵ��֪ͨ��״̬
	private long noticeStatusId        = -1;
//	��������
	private String transactionTypeName = "";
//  ��������ID
	private long transactiontypeID = -1 ;
//	ҵ��λ����
	private String clientName          = "";
//  ҵ��λID
	private long clientID = -1 ;
//	�ʽ��˻���
	private String accountcode         = "";
//  �ʽ��ʺ�ID
	private long accountID = -1;
//	�������
	private double  amount           = 0.0;
//  ��˾������ID
	private long companyBankID      =-1;
//  ��˾����������
	private String companyBankName      ="";
//  ��˾�����ʺ�
	private long companyAccountID   =-1;
//  ��˾�ʻ�����
	private String companyAccountName = "";
//  ��Ȩ��˾������ID
	private long counterpartBankID = -1;
//  ֤ȯ��˾������	
	private String counterpartBankName      ="";
//	֤ȯ��˾�����ʺ�
	private long counterpartAccountID = -1;
//	֤ȯ��˾�����ʺ�����
	private String counterpartAccountName = "";
//  ����Ӫҵ������
	private String counterpartName = "";
//  ����Ӫҵ��ID
	private long counterpartID = -1;
//  ֤ȯId
    private long securitiesId = -1;
//  ��ע
    private String remark = "";
	
	
/**
 * @return Returns the accountcode.
 */
public String getAccountcode() {
	return accountcode;
}
/**
 * @param accountcode The accountcode to set.
 */
public void setAccountcode(String accountcode) {
	this.accountcode = accountcode;
}
/**
 * @return Returns the accountID.
 */
public long getAccountID() {
	return accountID;
}
/**
 * @param accountID The accountID to set.
 */
public void setAccountID(long accountID) {
	this.accountID = accountID;
}
/**
 * @return Returns the amount.
 */
public double getAmount() {
	return amount;
}
/**
 * @param amount The amount to set.
 */
public void setAmount(double amount) {
	this.amount = amount;
}
/**
 * @return Returns the clientName.
 */
public String getClientName() {
	return clientName;
}
/**
 * @param clientName The clientName to set.
 */
public void setClientName(String clientName) {
	this.clientName = clientName;
}
/**
 * @return Returns the companyAccountID.
 */
public long getCompanyAccountID() {
	return companyAccountID;
}
/**
 * @param companyAccountID The companyAccountID to set.
 */
public void setCompanyAccountID(long companyAccountID) {
	this.companyAccountID = companyAccountID;
}
/**
 * @return Returns the companyAccountName.
 */
public String getCompanyAccountName() {
	return companyAccountName;
}
/**
 * @param companyAccountName The companyAccountName to set.
 */
public void setCompanyAccountName(String companyAccountName) {
	this.companyAccountName = companyAccountName;
}
/**
 * @return Returns the companyBankID.
 */
public long getCompanyBankID() {
	return companyBankID;
}
/**
 * @param companyBankID The companyBankID to set.
 */
public void setCompanyBankID(long companyBankID) {
	this.companyBankID = companyBankID;
}
/**
 * @return Returns the companyBankName.
 */
public String getCompanyBankName() {
	return companyBankName;
}
/**
 * @param companyBankName The companyBankName to set.
 */
public void setCompanyBankName(String companyBankName) {
	this.companyBankName = companyBankName;
}
/**
 * @return Returns the counterpartAccountID.
 */
public long getCounterpartAccountID() {
	return counterpartAccountID;
}
/**
 * @param counterpartAccountID The counterpartAccountID to set.
 */
public void setCounterpartAccountID(long counterpartAccountID) {
	this.counterpartAccountID = counterpartAccountID;
}
/**
 * @return Returns the counterpartAccountName.
 */
public String getCounterpartAccountName() {
	return counterpartAccountName;
}
/**
 * @param counterpartAccountName The counterpartAccountName to set.
 */
public void setCounterpartAccountName(String counterpartAccountName) {
	this.counterpartAccountName = counterpartAccountName;
}
/**
 * @return Returns the counterpartBankID.
 */
public long getCounterpartBankID() {
	return counterpartBankID;
}
/**
 * @param counterpartBankID The counterpartBankID to set.
 */
public void setCounterpartBankID(long counterpartBankID) {
	this.counterpartBankID = counterpartBankID;
}
/**
 * @return Returns the counterpartBankName.
 */
public String getCounterpartBankName() {
	return counterpartBankName;
}
/**
 * @param counterpartBankName The counterpartBankName to set.
 */
public void setCounterpartBankName(String counterpartBankName) {
	this.counterpartBankName = counterpartBankName;
}
/**
 * @return Returns the counterpartID.
 */
public long getCounterpartID() {
	return counterpartID;
}
/**
 * @param counterpartID The counterpartID to set.
 */
public void setCounterpartID(long counterpartID) {
	this.counterpartID = counterpartID;
}
/**
 * @return Returns the counterpartName.
 */
public String getCounterpartName() {
	return counterpartName;
}
/**
 * @param counterpartName The counterpartName to set.
 */
public void setCounterpartName(String counterpartName) {
	this.counterpartName = counterpartName;
}
/**
 * @return Returns the inputuserID.
 */
public long getInputuserID() {
	return inputuserID;
}
/**
 * @param inputuserID The inputuserID to set.
 */
public void setInputuserID(long inputuserID) {
	this.inputuserID = inputuserID;
}

/**
 * @return Returns the noticeStatusId.
 */
public long getNoticeStatusId() {
	return noticeStatusId;
}
/**
 * @param noticeStatusId The noticeStatusId to set.
 */
public void setNoticeStatusId(long noticeStatusId) {
	this.noticeStatusId = noticeStatusId;
}
/**
 * @return Returns the transactionDate.
 */
public Timestamp getTransactionDate() {
	return transactionDate;
}
/**
 * @param transactionDate The transactionDate to set.
 */
public void setTransactionDate(Timestamp transactionDate) {
	this.transactionDate = transactionDate;
}
/**
 * @return Returns the transactionTypeName.
 */
public String getTransactionTypeName() {
	return transactionTypeName;
}
/**
 * @param transactionTypeName The transactionTypeName to set.
 */
public void setTransactionTypeName(String transactionTypeName) {
	this.transactionTypeName = transactionTypeName;
}
/**
 * @return Returns the clientID.
 */
public long getClientID() {
	return clientID;
}
/**
 * @param clientID The clientID to set.
 */
public void setClientID(long clientID) {
	this.clientID = clientID;
}
/**
 * @return Returns the transactiontypeID.
 */
public long getTransactiontypeID() {
	return transactiontypeID;
}
/**
 * @param transactiontypeID The transactiontypeID to set.
 */
public void setTransactiontypeID(long transactiontypeID) {
	this.transactiontypeID = transactiontypeID;
}
/**
 * @return Returns the noticeformCode.
 */
public String getNoticeformCode() {
	return noticeformCode;
}
/**
 * @param noticeformCode The noticeformCode to set.
 */
public void setNoticeformCode(String noticeformCode) {
	this.noticeformCode = noticeformCode;
}
/**
 * @return
 */
public long getSecuritiesId()
{
	return securitiesId;
}

/**
 * @param l
 */
public void setSecuritiesId(long l)
{
	securitiesId = l;
}

/**
 * @return
 */
public String getRemark()
{
	return remark;
}

/**
 * @param string
 */
public void setRemark(String string)
{
	remark = string;
}

}
