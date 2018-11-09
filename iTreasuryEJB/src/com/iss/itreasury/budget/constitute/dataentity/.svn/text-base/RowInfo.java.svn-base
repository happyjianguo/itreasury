/*
 * Created on 2005-8-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.constitute.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Hashtable;

import com.iss.itreasury.budget.util.BUDGETConstant;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RowInfo  implements Serializable{

    /**
	 * ÐÐºÅ
	 */
	private int rowNum=-1;
	private String strOfficeID = "";
	private String strCurrencyID = "";	
	private String clientNo = "";
	private String clientName = "";
	private String systemNo = "";
	private String systemName = "";
	private String periodNo = "";
	private String periodName = "";
	private String strStartDate = "";
	private String strEndDate = "";
	private String strBudgetFlag = "";
	private String itemNo = "";
	private String itemName = "";
	private String strBudgetAmount = "";
	
	private Hashtable errors = new Hashtable(); 

	private long officeID = -1;
	private long currencyID = -1;
	private Timestamp startDate = null;
	private Timestamp endDate = null;
	private double budgetAmount = 0.0;
	private long clientID = -1;
	private long systemID = -1;
	private long periodID = -1;
	private long budgetFlag = -1;
	private long  itemID= -1;
	
	
    /**
     * @return Returns the budgetAmount.
     */
    public double getBudgetAmount() {
        return budgetAmount;
    }
    /**
     * @param budgetAmount The budgetAmount to set.
     */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
    /**
     * @return Returns the budgetFlag.
     */
    public long getBudgetFlag() {
        return budgetFlag;
    }
    /**
     * @param budgetFlag The budgetFlag to set.
     */
    public void setBudgetFlag(String strBudgetFlag) {
        if (strBudgetFlag.equals(BUDGETConstant.BudgetFlag.getCode(BUDGETConstant.BudgetFlag.CONSTITUTE)))
            budgetFlag = BUDGETConstant.BudgetFlag.CONSTITUTE;
		else if (strBudgetFlag.equals(BUDGETConstant.BudgetFlag.getCode(BUDGETConstant.BudgetFlag.ADJUST)))  
		    budgetFlag = BUDGETConstant.BudgetFlag.ADJUST;
		else if (strBudgetFlag.equals(BUDGETConstant.BudgetFlag.getCode(BUDGETConstant.BudgetFlag.TOTAL))) 
		    budgetFlag = BUDGETConstant.BudgetFlag.TOTAL;
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
     * @return Returns the clientNo.
     */
    public String getClientNo() {
        return clientNo;
    }
    /**
     * @param clientNo The clientNo to set.
     */
    public void setClientNo(String clientNo) {
        this.clientNo = clientNo;
    }
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID() {
        return currencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID) {
        this.currencyID = currencyID;
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
    }
    /**
     * @return Returns the errors.
     */
    public Hashtable getErrors() {
        return errors;
    }
    /**
     * @param errors The errors to set.
     */
    public void setErrors(Hashtable errors) {
        this.errors = errors;
    }
    /**
     * @return Returns the itemID.
     */
    public long getItemID() {
        return itemID;
    }
    /**
     * @param itemID The itemID to set.
     */
    public void setItemID(long itemID) {
        this.itemID = itemID;
    }
    /**
     * @return Returns the itemName.
     */
    public String getItemName() {
        return itemName;
    }
    /**
     * @param itemName The itemName to set.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    /**
     * @return Returns the itemNo.
     */
    public String getItemNo() {
        return itemNo;
    }
    /**
     * @param itemNo The itemNo to set.
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID() {
        return officeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID) {
        this.officeID = officeID;
    }
    /**
     * @return Returns the periodID.
     */
    public long getPeriodID() {
        return periodID;
    }
    /**
     * @param periodID The periodID to set.
     */
    public void setPeriodID(long periodID) {
        this.periodID = periodID;
    }
    /**
     * @return Returns the periodName.
     */
    public String getPeriodName() {
        return periodName;
    }
    /**
     * @param periodName The periodName to set.
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }
    /**
     * @return Returns the periodNo.
     */
    public String getPeriodNo() {
        return periodNo;
    }
    /**
     * @param periodNo The periodNo to set.
     */
    public void setPeriodNo(String periodNo) {
        this.periodNo = periodNo;
    }
    /**
     * @return Returns the rowNum.
     */
    public int getRowNum() {
        return rowNum;
    }
    /**
     * @param rowNum The rowNum to set.
     */
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
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
    }
    /**
     * @return Returns the strBudgetAmount.
     */
    public String getStrBudgetAmount() {
        return strBudgetAmount;
    }
    /**
     * @param strBudgetAmount The strBudgetAmount to set.
     */
    public void setStrBudgetAmount(String strBudgetAmount) {
        this.strBudgetAmount = strBudgetAmount;
    }
    /**
     * @return Returns the strBudgetFlag.
     */
    public String getStrBudgetFlag() {
        return strBudgetFlag;
    }
    /**
     * @param strBudgetFlag The strBudgetFlag to set.
     */
    public void setStrBudgetFlag(String strBudgetFlag) {
        this.strBudgetFlag = strBudgetFlag;
    }
    /**
     * @return Returns the strCurrencyID.
     */
    public String getStrCurrencyID() {
        return strCurrencyID;
    }
    /**
     * @param strCurrencyID The strCurrencyID to set.
     */
    public void setStrCurrencyID(String strCurrencyID) {
        this.strCurrencyID = strCurrencyID;
    }
    /**
     * @return Returns the strEndDate.
     */
    public String getStrEndDate() {
        return strEndDate;
    }
    /**
     * @param strEndDate The strEndDate to set.
     */
    public void setStrEndDate(String strEndDate) {
        this.strEndDate = strEndDate;
    }
    /**
     * @return Returns the strOfficeID.
     */
    public String getStrOfficeID() {
        return strOfficeID;
    }
    /**
     * @param strOfficeID The strOfficeID to set.
     */
    public void setStrOfficeID(String strOfficeID) {
        this.strOfficeID = strOfficeID;
    }
    /**
     * @return Returns the strStartDate.
     */
    public String getStrStartDate() {
        return strStartDate;
    }
    /**
     * @param strStartDate The strStartDate to set.
     */
    public void setStrStartDate(String strStartDate) {
        this.strStartDate = strStartDate;
    }
    /**
     * @return Returns the systemID.
     */
    public long getSystemID() {
        return systemID;
    }
    /**
     * @param systemID The systemID to set.
     */
    public void setSystemID(long systemID) {
        this.systemID = systemID;
    }
    /**
     * @return Returns the systemName.
     */
    public String getSystemName() {
        return systemName;
    }
    /**
     * @param systemName The systemName to set.
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    /**
     * @return Returns the systemNo.
     */
    public String getSystemNo() {
        return systemNo;
    }
    /**
     * @param systemNo The systemNo to set.
     */
    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }
    
    public void putError(String param,String msg){
		if(param==null || param.equals("")||msg==null || msg.equals("")){
			
		}else{
			errors.put(param,msg);
		}
	}
}
