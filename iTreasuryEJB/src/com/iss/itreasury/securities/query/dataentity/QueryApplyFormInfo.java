/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
 
import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author 王怡
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryApplyFormInfo extends SECBaseDataEntity	implements Serializable {
    //业务申请书ID
	private long NoticeId              = -1;
	//业务通知单编号
	private String noticeCode          = "";
	//对应交割单编号
	private String deliveryOrderCode   = "";
	//业务单位名称
	private String clientName          = "";
	private String stockHolderAccountName = ""; //股东帐户名称
	//业务类型
	private String businessTypeName    = "";
	//业务类型
	private long businessTypeId        = -1;
	//交易类型
	private String transactionTypeName = "";
	//交易类型Id
	private long transactionTypeID     = -1;
	//业务通知单录入日期
	private Timestamp noticeInputDate  = null;
	
	//申请成交开始日
	private Timestamp transactionStartDate  = null;
	//申请成交结束日
	private Timestamp transactionEndDate  = null;
	
	
	//通知单成交日期
	private Timestamp transactionDate  = null;
	//交易对手名称 (三个之一：开户营业部，基金管理公司，交易对手)--给国机的结果
	private String counterPartName     = "";
	//交易对手－－给华能的结果
	private long counterPartId = -1;
	//开户营业部－－给华能的结果
	private long bankOfDepositId = -1;
	//资金账户
	private String accountcode         = "";
	//证券名称
	private String securitiesName      = "";
	//实际收付
	private double netIncome           = 0.0;
	//业务通知单状态
	private long noticeStatusId        = -1;
	
	//审核人，作为参数查出审核状态
	private long nextCheckUserId	   = -1;
	
	//录入人
	private long userId                = -1;
	//用款金额
	private double pledgeSecuritiesAmount = 0.0;
	//锁定期
	private Timestamp maturityDate     = null;
	//类别选择
	private long ownerTypeId           = -1;  
	//业务性质ID 
	//           businessAttributeId＝1时候交易对手是交易对手
    //           businessAttributeId＝2时候交易对手是开户营业部
	//           businessAttributeId＝3时候交易对手是基金管理公司
	private long  businessAttributeId  = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub

	}

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
	 * @return Returns the counterPartName.
	 */
	public String getCounterPartName() {
		return counterPartName;
	}

	/**
	 * @param counterPartName The counterPartName to set.
	 */
	public void setCounterPartName(String counterPartName) {
		this.counterPartName = counterPartName;
	}

	/**
	 * @return Returns the deliveryOrderCode.
	 */
	public String getDeliveryOrderCode() {
		return deliveryOrderCode;
	}

	/**
	 * @param deliveryOrderCode The deliveryOrderCode to set.
	 */
	public void setDeliveryOrderCode(String deliveryOrderCode) {
		this.deliveryOrderCode = deliveryOrderCode;
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
	 * @return Returns the noticeCode.
	 */
	public String getNoticeCode() {
		return noticeCode;
	}

	/**
	 * @param noticeCode The noticeCode to set.
	 */
	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}

	/**
	 * @return Returns the noticeInputDate.
	 */
	public Timestamp getNoticeInputDate() {
		return noticeInputDate;
	}

	/**
	 * @param noticeInputDate The noticeInputDate to set.
	 */
	public void setNoticeInputDate(Timestamp noticeInputDate) {
		this.noticeInputDate = noticeInputDate;
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
	 * @return Returns the securitiesName.
	 */
	public String getSecuritiesName() {
		return securitiesName;
	}

	/**
	 * @param securitiesName The securitiesName to set.
	 */
	public void setSecuritiesName(String securitiesName) {
		this.securitiesName = securitiesName;
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
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return transactionTypeID;
	}

	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		this.transactionTypeID = transactionTypeID;
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
	 * @return Returns the businessAttributeID.
	 */
	public long getBusinessAttributeId() {
		return businessAttributeId;
	}

	/**
	 * @param businessAttributeID The businessAttributeID to set.
	 */
	public void setBusinessAttributeId(long businessAttributeId) {
		this.businessAttributeId = businessAttributeId;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionEndDate() {
		return transactionEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionStartDate() {
		return transactionStartDate;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionEndDate(Timestamp timestamp) {
		transactionEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionStartDate(Timestamp timestamp) {
		transactionStartDate = timestamp;
	}

	/**
	 * @return Returns the noticeId.
	 */
	public long getNoticeId() {
		return NoticeId;
	}

	/**
	 * @param noticeId The noticeId to set.
	 */
	public void setNoticeId(long noticeId) {
		NoticeId = noticeId;
	}

	/**
	 * @return
	 */
	public long getNextCheckUserId() {
		return nextCheckUserId;
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserId(long l) {
		nextCheckUserId = l;
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
	public long getUserId() {
		return userId;
	}

	/**
	 * @param l
	 */
	public void setUserId(long l) {
		userId = l;
	}

	/**
	 * @return
	 */
	public Timestamp getMaturityDate()
	{
		return maturityDate;
	}

	/**
	 * @return
	 */
	public double getPledgeSecuritiesAmount()
	{
		return pledgeSecuritiesAmount;
	}

	/**
	 * @param timestamp
	 */
	public void setMaturityDate(Timestamp timestamp)
	{
		maturityDate = timestamp;
	}

	/**
	 * @param d
	 */
	public void setPledgeSecuritiesAmount(double d)
	{
		pledgeSecuritiesAmount = d;
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
	public long getBankOfDepositId() {
		return bankOfDepositId;
	}

	/**
	 * @return
	 */
	public long getCounterPartId() {
		return counterPartId;
	}

	/**
	 * @param l
	 */
	public void setBankOfDepositId(long l) {
		bankOfDepositId = l;
	}

	/**
	 * @param l
	 */
	public void setCounterPartId(long l) {
		counterPartId = l;
	}

}
