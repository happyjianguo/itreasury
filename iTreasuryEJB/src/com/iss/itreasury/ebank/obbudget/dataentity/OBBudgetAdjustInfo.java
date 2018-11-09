/*
 * Created on 2006-8-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obbudget.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetAdjustInfo extends ITreasuryBaseDataEntity
{

	private long id = -1; //id
	private long budgetID = -1;//预算id
	private Timestamp adjustdate = null;//调整预算日期
	private long accountID = -1;//调整预算账户
	private long clientID = -1;//调整预算客户
	private long inputuser = -1;//录入人
	private long checkuser = -1;//复核人
	private Timestamp inputdate = null;//录入日期
	private Timestamp checkdate = null;//复核日期
	private long status = -1;//状态
	private String note = "";//调整预算说明
	private String refusereason = "";//拒绝原因
	private double amount = 0.00;//调整预算金额
	private long modifyuser = -1;//修改人
	private Timestamp modifydate = null;//修改日期
	
	

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
		putUsedField("accountID", accountID);
	}
	/**
	 * @return Returns the adjustdate.
	 */
	public Timestamp getAdjustdate() {
		return adjustdate;
	}
	/**
	 * @param adjustdate The adjustdate to set.
	 */
	public void setAdjustdate(Timestamp adjustdate) {
		this.adjustdate = adjustdate;
		putUsedField("adjustdate", adjustdate);
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
		putUsedField("amount", amount);
	}
	/**
	 * @return Returns the budgetID.
	 */
	public long getBudgetID() {
		return budgetID;
	}
	/**
	 * @param budgetID The budgetID to set.
	 */
	public void setBudgetID(long budgetID) {
		this.budgetID = budgetID;
		putUsedField("budgetID", budgetID);
	}
	/**
	 * @return Returns the checkdate.
	 */
	public Timestamp getCheckdate() {
		return checkdate;
	}
	/**
	 * @param checkdate The checkdate to set.
	 */
	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
		putUsedField("checkdate", checkdate);
	}
	/**
	 * @return Returns the checkuser.
	 */
	public long getCheckuser() {
		return checkuser;
	}
	/**
	 * @param checkuser The checkuser to set.
	 */
	public void setCheckuser(long checkuser) {
		this.checkuser = checkuser;
		putUsedField("checkuser", checkuser);
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
		putUsedField("clientID", clientID);
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	/**
	 * @return Returns the inputdate.
	 */
	public Timestamp getInputdate() {
		return inputdate;
	}
	/**
	 * @param inputdate The inputdate to set.
	 */
	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
		putUsedField("inputdate", inputdate);
	}
	/**
	 * @return Returns the inputuser.
	 */
	public long getInputuser() {
		return inputuser;
	}
	/**
	 * @param inputuser The inputuser to set.
	 */
	public void setInputuser(long inputuser) {
		this.inputuser = inputuser;
		putUsedField("inputuser", inputuser);
	}
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		this.note = note;
		putUsedField("note", note);
	}
	/**
	 * @return Returns the refusereason.
	 */
	public String getRefusereason() {
		return refusereason;
	}
	/**
	 * @param refusereason The refusereason to set.
	 */
	public void setRefusereason(String refusereason) {
		this.refusereason = refusereason;
		putUsedField("refusereason", refusereason);
	}
	/**
	 * @return Returns the status.
	 */
	public long getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(long status) {
		this.status = status;
		putUsedField("status", status);
	}
	
	/**
	 * @return Returns the modifydate.
	 */
	public Timestamp getModifydate() {
		return modifydate;
	}
	/**
	 * @param modifydate The modifydate to set.
	 */
	public void setModifydate(Timestamp modifydate) {
		this.modifydate = modifydate;
		putUsedField("modifydate", modifydate);
	}
	/**
	 * @return Returns the modifyuser.
	 */
	public long getModifyuser() {
		return modifyuser;
	}
	/**
	 * @param modifyuser The modifyuser to set.
	 */
	public void setModifyuser(long modifyuser) {
		this.modifyuser = modifyuser;
		putUsedField("modifyuser", modifyuser);
	}
}
