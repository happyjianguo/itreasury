/*
 * Created on 2005-6-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QBudgetVersionResultInfo implements Serializable{

    private long planID = -1;				//预算ID
    private long clientID = -1;				//clientID
    private String versionNo = "";		//预算版本号
    private Timestamp startDate = null;	//预算区间开始日期
    private Timestamp endDate = null;	//预算区间结束日期
   
    private String budgetPeriodName = "";	//预算周期名称
    private Timestamp planDate = null;	//编制日期
    private long inputUserID = -1;		//录入人
    private long StatusID = -1;			//状态
    private long budgetSystemID = -1;  //预算体系ID 
    private String budgetSystemName = "";  //预算体系名称
    private long budgetPeriodID= -1;     //预算周期ID
	
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	public Timestamp getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Timestamp planDate) {
		this.planDate = planDate;
	}
	public long getPlanID() {
		return planID;
	}
	public void setPlanID(long planID) {
		this.planID = planID;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public long getStatusID() {
		return StatusID;
	}
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getBudgetPeriodName() {
		return budgetPeriodName;
	}
	public void setBudgetPeriodName(String budgetPeriodName) {
		this.budgetPeriodName = budgetPeriodName;
	}
	public long getBudgetSystemID() {
		return budgetSystemID;
	}
	public void setBudgetSystemID(long budgetSystemID) {
		this.budgetSystemID = budgetSystemID;
	}
	public String getBudgetSystemName() {
		return budgetSystemName;
	}
	
	public void setBudgetSystemName(String budgetSystemName) {
		this.budgetSystemName = budgetSystemName;
	}
	public long getBudgetPeriodID() {
		return budgetPeriodID;
	}
	public void setBudgetPeriodID(long budgetPeriodID) {
		this.budgetPeriodID = budgetPeriodID;
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
}
