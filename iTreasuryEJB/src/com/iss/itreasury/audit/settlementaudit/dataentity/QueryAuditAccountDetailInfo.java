
package com.iss.itreasury.audit.settlementaudit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class QueryAuditAccountDetailInfo implements Serializable {
	//交易日
	private Timestamp executeDate = null;
	//交易号
	private String transNo = "";
	//业务类型
	private long transactionTypeId = -1;
	//账户号
	private long transAccountId = -1;
	//金额
	private double amount = 0.0;
	//	金额类型
	private long amountType = -1;
	//收付方向
	private long transDirection = -1;
	//对方账户号
	private long oppAccountId = -1;
	//交易状态
	private long statusId = -1;
	//摘要
	private String remark = "";
	//录入人
	private long inputUserId = -1;
	//复核人
	private long checkUserId = -1;
	private long accountTypeId = -1;
	private long serialNo = -1;
	private long Id = -1;
	private long trnasID = -1;
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
	 * @return Returns the checkUserId.
	 */
	public long getCheckUserId() {
		return checkUserId;
	}
	/**
	 * @param checkUserId The checkUserId to set.
	 */
	public void setCheckUserId(long checkUserId) {
		this.checkUserId = checkUserId;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		this.executeDate = executeDate;
	}
	/**
	 * @return Returns the inputUserId.
	 */
	public long getInputUserId() {
		return inputUserId;
	}
	/**
	 * @param inputUserId The inputUserId to set.
	 */
	public void setInputUserId(long inputUserId) {
		this.inputUserId = inputUserId;
	}
	/**
	 * @return Returns the oppAccountId.
	 */
	public long getOppAccountId() {
		return oppAccountId;
	}
	/**
	 * @param oppAccountId The oppAccountId to set.
	 */
	public void setOppAccountId(long oppAccountId) {
		this.oppAccountId = oppAccountId;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the statusId.
	 */
	public long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId The statusId to set.
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return Returns the transAccountId.
	 */
	public long getTransAccountId() {
		return transAccountId;
	}
	/**
	 * @param transAccountId The transAccountId to set.
	 */
	public void setTransAccountId(long transAccountId) {
		this.transAccountId = transAccountId;
	}
	/**
	 * @return Returns the transactionTypeId.
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}
	/**
	 * @param transactionTypeId The transactionTypeId to set.
	 */
	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	/**
	 * @return Returns the transDirection.
	 */
	public long getTransDirection() {
		return transDirection;
	}
	/**
	 * @param transDirection The transDirection to set.
	 */
	public void setTransDirection(long transDirection) {
		this.transDirection = transDirection;
	}
	/**
	 * @return Returns the transNo.
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * @param transNo The transNo to set.
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		Id = id;
	}
	/**
	 * @return Returns the serialNo.
	 */
	public long getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo The serialNo to set.
	 */
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return Returns the accountTypeId.
	 */
	public long getAccountTypeId() {
		return accountTypeId;
	}
	/**
	 * @param accountTypeId The accountTypeId to set.
	 */
	public void setAccountTypeId(long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
    /**
     * @return Returns the trnasID.
     */
    public long getTrnasID()
    {
        return trnasID;
    }
    /**
     * @param trnasID The trnasID to set.
     */
    public void setTrnasID(long trnasID)
    {
        this.trnasID = trnasID;
    }
	public long getAmountType() {
		return amountType;
	}
	public void setAmountType(long amountType) {
		this.amountType = amountType;
	}
}