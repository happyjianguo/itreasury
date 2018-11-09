package com.iss.itreasury.craftbrother.credit.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
public class CreditSettingInfo implements java.io.Serializable {
	//对应cra_creditlimit表
	private long ID=-1;//PK
	private long creditClientID=-1;//授信方ID
	private String creditClientCode ="";//授信方客户编号
	private String creditClientName ="";//授信方客户名称
	private long creditedClientID=-1;//被授信方ID
	private String creditedClientCode ="";//被授信方客户编号
	private String creditedClientName ="";//被授信方客户名称
	private double amount=0.00;//授信额度
	private long transactionType=-1;//交易类型
	private long creditDirection =-1;//授信方向1-财务公司对同行授信 2-同行对财务公司及成员单位授信
	private long statusID =-1;//状态
	private Timestamp startDate=null;//授信开始时间
	private Timestamp endDate=null;//授信结束时间
	private long term = -1;//授信期限
	private String remark="";//备注
	private long inputUserID=-1;//录入人
	private Timestamp inputDate=null;//录入时间
    private long currencyID = -1;//币种
    private long officeID = -1;//办事处
    private long attachId = -1;//链接附件ID
    private InutParameterInfo inutParameterInfo;
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getCreditClientID() {
		return creditClientID;
	}
	public void setCreditClientID(long creditClientID) {
		this.creditClientID = creditClientID;
	}
	public long getCreditDirection() {
		return creditDirection;
	}
	public void setCreditDirection(long creditDirection) {
		this.creditDirection = creditDirection;
	}
	public long getCreditedClientID() {
		return creditedClientID;
	}
	public void setCreditedClientID(long creditedClientID) {
		this.creditedClientID = creditedClientID;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(long transactionType) {
		this.transactionType = transactionType;
	}
	public String getCreditClientCode() {
		return creditClientCode;
	}
	public void setCreditClientCode(String creditClientCode) {
		this.creditClientCode = creditClientCode;
	}
	public String getCreditClientName() {
		return creditClientName;
	}
	public void setCreditClientName(String creditClientName) {
		this.creditClientName = creditClientName;
	}
	public String getCreditedClientCode() {
		return creditedClientCode;
	}
	public void setCreditedClientCode(String creditedClientCode) {
		this.creditedClientCode = creditedClientCode;
	}
	public String getCreditedClientName() {
		return creditedClientName;
	}
	public void setCreditedClientName(String creditedClientName) {
		this.creditedClientName = creditedClientName;
	}
	public long getTerm() {
		return term;
	}
	public void setTerm(long term) {
		this.term = term;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public long getAttachId() {
		return attachId;
	}
	public void setAttachId(long attachId) {
		this.attachId = attachId;
	}

}
