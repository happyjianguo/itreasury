package com.iss.itreasury.budget.executecontrol.dataentity;

import com.iss.itreasury.budget.dataentity.BudgetBaseDataEntity;
import java.sql.Timestamp;

public class BudgetItemSumInfo extends BudgetBaseDataEntity {
    private long clientID;
    private long budgetSystemID;
    private long budgetPeriodID;
    private long iTemID;
    private Timestamp inputDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private long statusID;
    private double BudgetAmount=0.0;
    private double ExecuteAmount=0.0;
    private double OriginalAmount = 0.0;
    
    public BudgetItemSumInfo() {
    }


	/**
	 * @return Returns the budgetPeriodID.
	 */
	public long getBudgetPeriodID() {
		return budgetPeriodID;
	}
	/**
	 * @param budgetPeriodID The budgetPeriodID to set.
	 */
	public void setBudgerPeriodID(long budgetPeriodID) {
		this.budgetPeriodID = budgetPeriodID;
		putUsedField("budgetPeriodID", budgetPeriodID);
	}
	/**
	 * @return Returns the budgetSystemID.
	 */
	public long getBudgetSystemID() {
		return budgetSystemID;
	}
	/**
	 * @param budgetSystemID The budgetSystemID to set.
	 */
	public void setBudgetSystemID(long budgetSystemID) {
		this.budgetSystemID = budgetSystemID;
		putUsedField("budgetSystemID", budgetSystemID);
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
	 * @return Returns the endDate.
	 */
	public Timestamp getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
		putUsedField("endDate", endDate);
	}
	/**
	 * @return Returns the iTemID.
	 */
	public long getITemID() {
		return iTemID;
	}
	/**
	 * @param temID The iTemID to set.
	 */
	public void setITemID(long temID) {
		this.iTemID = temID;
		putUsedField("iTemID", iTemID);
	}
	/**
	 * @return Returns the startDate.
	 */
	public Timestamp getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
		putUsedField("startDate", startDate);
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
		putUsedField("statusID", statusID);
	}
	public double getBudgetAmount() {
		return BudgetAmount;
	}


	public void setBudgetAmount(double budgetAmount) {
		this.BudgetAmount = budgetAmount;
		putUsedField("BudgetAmount", BudgetAmount);
	}


	public double getExecuteAmount() {
		return ExecuteAmount;
	}


	public void setExecuteAmount(double executeAmount) {
		this.ExecuteAmount = executeAmount;
		putUsedField("ExecuteAmount", ExecuteAmount);
	}


    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate() {
        return inputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate) {
        this.inputDate = inputDate;
        putUsedField("inputDate", inputDate);
    }
    /**
     * @return Returns the originalAmount.
     */
    public double getOriginalAmount() {
        return OriginalAmount;
    }
    /**
     * @param originalAmount The originalAmount to set.
     */
    public void setOriginalAmount(double originalAmount) {
        OriginalAmount = originalAmount;
        putUsedField("originalAmount", originalAmount);
    }
}
