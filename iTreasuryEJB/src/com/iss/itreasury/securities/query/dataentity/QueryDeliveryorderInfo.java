/*
 * Created on 2004-4-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author chluo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryDeliveryorderInfo extends SECBaseDataEntity	implements Serializable 
{
    private long   deliveryId    = -1;          //交割单ID
	private String deliveryCode="";             //1
	private String   applyFormCode="";          //2
	private String clientName="";               //3
	private String stockHolderAccountName = ""; //股东帐户名称
	private long    businessTypeId = -1;
	private String businessTypeName="";         //4
	private String transactionTypeName="";      //5
	private Timestamp transactionDate=null;     //6交易时间
	private String counterpartName="";          //7交易对手(国机)
	private String accountID="";                //8资金帐号
	private double netIncome=0.0;               //9
	private long statusID=-1;                   //10
	private long userId=-1;                     //11
	private long isViolation = -1;              //是否违规
	private long ownerTypeId           = -1;    //类别选择
	private long businessAttribute=-1; 
	
	private long counterPartId = -1;//交易对手－－华能
	private long bankOfDepositId = -1;//开户营业部－－华能

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	

	/**
	 * @return Returns the businessTypeName.
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}
	/**
	 * @param businessTypeName The businessTypeName to set.
	 */
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
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
	 * @return Returns the deliveryCode.
	 */
	public String getDeliveryCode() {
		return deliveryCode;
	}
	/**
	 * @param deliveryCode The deliveryCode to set.
	 */
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}
	
	/**
	 * @return Returns the netIncome.
	 */
	public double getNetIncome() {
		return netIncome;
	}
	/**
	 * @param netIncome The netIncome to set.
	 */
	public void setNetIncome(double netIncome) {
		this.netIncome = netIncome;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
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
	 * @return Returns the inputUserName.
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param inputUserName The inputUserName to set.
	 */
	public void setUserId(long inputUserID) {
		this.userId = inputUserID;
	}
	/**
	 * @return Returns the businessAttribute.
	 */
	public long getBusinessAttribute() {
		return businessAttribute;
	}
	/**
	 * @param businessAttribute The businessAttribute to set.
	 */
	public void setBusinessAttribute(long businessAttribute) {
		this.businessAttribute = businessAttribute;
	}
	/**
	 * @return Returns the accountID.
	 */
	public String getAccountID() {
		return accountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	/**
	 * @return Returns the deliveryId.
	 */
	public long getDeliveryId() {
		return deliveryId;
	}

	/**
	 * @param deliveryId The deliveryId to set.
	 */
	public void setDeliveryId(long deliveryId) {
		this.deliveryId = deliveryId;
	}

	/**
	 * @return Returns the businessTypeId.
	 */
	public long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @param businessTypeId The businessTypeId to set.
	 */
	public void setBusinessTypeId(long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	/**
	 * @return
	 */
	public String getApplyFormCode() {
		return applyFormCode;
	}

	/**
	 * @param string
	 */
	public void setApplyFormCode(String string) {
		applyFormCode = string;
	}

	/**
	 * @return
	 */
	public String getStockHolderAccountName() {
		return stockHolderAccountName;
	}

	/**
	 * @param string
	 */
	public void setStockHolderAccountName(String string) {
		stockHolderAccountName = string;
	}

	/**
	 * @return
	 */
	public long getIsViolation() {
		return isViolation;
	}

	/**
	 * @param l
	 */
	public void setIsViolation(long l) {
		isViolation = l;
	}

	/**
	 * @return
	 */
	public long getOwnerTypeId()
	{
		return ownerTypeId;
	}

	/**
	 * @param l
	 */
	public void setOwnerTypeId(long l)
	{
		ownerTypeId = l;
	}

	/**
	 * @return
	 */
	public long getBankOfDepositId()
	{
		return bankOfDepositId;
	}

	/**
	 * @return
	 */
	public long getCounterPartId()
	{
		return counterPartId;
	}

	/**
	 * @param l
	 */
	public void setBankOfDepositId(long l)
	{
		bankOfDepositId = l;
	}

	/**
	 * @param l
	 */
	public void setCounterPartId(long l)
	{
		counterPartId = l;
	}

}
