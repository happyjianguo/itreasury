/*
 * Created on 2006-8-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obbudget.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBBudgetInfo  extends ITreasuryBaseDataEntity
{
	private long id = -1; //用款id
	private String sname = "";//用款名称
	private Timestamp startdate = null;//用款开始日期
	private Timestamp enddate = null;//用款结束日期
	private long accountID = -1;//用款账户
	private long clientID = -1;//用款客户
	private long inputuser = -1;//录入人
	private long checkuser = -1;//复核人
	private Timestamp inputdate = null;//录入日期
	private Timestamp checkdate = null;//复核日期
	private long status = -1;//状态
	private String note = "";//用款说明
	private String refusereason = "";//拒绝原因
	private long adjunctID = -1;//链接附件ID
	private double amount = 0.00;//用款金额
	private long modifyuser = -1;//修改人
	private Timestamp modifydate = null;//修改日期
	//非数据库字段
	private Timestamp adjustdate = null;//调整用款日期
	private double adjustamount = 0.00;//调整用款金额
	private long budgetadjustid = -1;// 用款调整id
	private long parentBudgetId = -1;	//主记录ID
	private String adjustNote = "";    //用款调整说明
	private InutParameterInfo inutParameterInfo = null;//审批流信息
	private long adjustId = -1;		//调整来源记录
	private long officeId = -1;		//办事处
	private long currencyId = -1;		//币种
	private Timestamp modifyDate = null;	//更新日期，用于自动任务
	
	private long branchId = -1;		//开户行号，用于用款计划执行
	public long getBranchId() {
		return branchId;
	}
	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}
	public long getAdjustId() {
		return adjustId;
	}
	public void setAdjustId(long adjustId) {
		this.adjustId = adjustId;
		putUsedField("adjustId", adjustId);
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);
	}
	public Timestamp getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
		putUsedField("modifyDate", modifyDate);
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);
	}
	public String getAdjustNote() {
		return adjustNote;
	}
	public void setAdjustNote(String adjustNote) {
		this.adjustNote = adjustNote;
		putUsedField("adjustNote", adjustNote);
	}
	public long getParentBudgetId() {
		return parentBudgetId;
	}
	public void setParentBudgetId(long parentBudgetId) {
		this.parentBudgetId = parentBudgetId;
		putUsedField("parentBudgetId", parentBudgetId);
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
		putUsedField("accountID", accountID);
	}
	/**
	 * @return Returns the adjunctID.
	 */
	public long getAdjunctID() {
		return adjunctID;
	}
	/**
	 * @param adjunctID The adjunctID to set.
	 */
	public void setAdjunctID(long adjunctID) {
		this.adjunctID = adjunctID;
		putUsedField("adjunctID", adjunctID);
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
	 * @return Returns the enddate.
	 */
	public Timestamp getEnddate() {
		return enddate;
	}
	/**
	 * @param enddate The enddate to set.
	 */
	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
		putUsedField("enddate", enddate);
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
	 * @return Returns the sname.
	 */
	public String getSname() {
		return sname;
	}
	/**
	 * @param sname The sname to set.
	 */
	public void setSname(String sname) {
		this.sname = sname;
		putUsedField("sname", sname);
	}
	/**
	 * @return Returns the startdate.
	 */
	public Timestamp getStartdate() {
		return startdate;
	}
	/**
	 * @param startdate The startdate to set.
	 */
	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
		putUsedField("startdate", startdate);
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
		//putUsedField("modifydate", modifydate);
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
	
	/**
	 * @return Returns the adjustamount.
	 */
	public double getAdjustamount() {
		return adjustamount;
	}
	/**
	 * @param adjustamount The adjustamount to set.
	 */
	public void setAdjustamount(double adjustamount) {
		this.adjustamount = adjustamount;
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
	}
	
    /**
     * @return Returns the budgetadjustid.
     */
    public long getBudgetadjustid() {
        return budgetadjustid;
    }
    /**
     * @param budgetadjustid The budgetadjustid to set.
     */
    public void setBudgetadjustid(long budgetadjustid) {
        this.budgetadjustid = budgetadjustid;
    }
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
}

 
