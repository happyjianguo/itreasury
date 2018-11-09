/* Generated by Together */

package com.iss.itreasury.budget.setting.dataentity;
import com.iss.itreasury.budget.dataentity.BudgetBaseDataEntity;
import java.sql.Timestamp;
public class BudgetSystemInfo extends BudgetBaseDataEntity {

    //private long id =-1;
	private long  currencyID =-1;                  //币种
    private long officeID =-1;                     //办事处
   private	long inputUserID = -1;	//	录入人
	private	Timestamp inputDate	= null;//	录入时间
	private	long updateUserID = -1;//	修改人
	private	Timestamp updateDate = null;//	修改时间
    private String budgetSystemNo ="";         //预算体系编号
    private String budgetSystemName = "";      //预算体系名称
    private double warnScale = 0.0;              //预警比例
	private double suppleScale = 0.0;           //柔性比例比例
    private long statusID =-1;                  //状态id 
	/**
	 * @return Returns the budgetSystemName.
	 */
	public String getBudgetSystemName() {
		return budgetSystemName;
	}
	/**
	 * @param budgetSystemName The budgetSystemName to set.
	 */
	public void setBudgetSystemName(String budgetSystemName) {
		this.budgetSystemName = budgetSystemName;
		putUsedField("budgetSystemName", budgetSystemName);
	}
	/**
	 * @return Returns the budgetSystemNo.
	 */
	public String getBudgetSystemNo() {
		return budgetSystemNo;
	}
	/**
	 * @param budgetSystemNo The budgetSystemNo to set.
	 */
	public void setBudgetSystemNo(String budgetSystemNo) {
		this.budgetSystemNo = budgetSystemNo;
		putUsedField("budgetSystemNo", budgetSystemNo);
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
		putUsedField("currencyID", currencyID);
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
 * @return Returns the inputUserID.
 */
public long getInputUserID() {
	return inputUserID;
}
/**
 * @param inputUserID The inputUserID to set.
 */
public void setInputUserID(long inputUserID) {
	this.inputUserID = inputUserID;
	putUsedField("inputUserID", inputUserID);
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
		putUsedField("officeID", officeID);
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
	/**
	 * @return Returns the suppleScale.
	 */
	public double getSuppleScale() {
		return suppleScale;
	}
	/**
	 * @param suppleScale The suppleScale to set.
	 */
	public void setSuppleScale(double suppleScale) {
		this.suppleScale = suppleScale;
		putUsedField("suppleScale", suppleScale);
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
		putUsedField("updateDate", updateDate);
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserID() {
		return updateUserID;
	}
	/**
	 * @param updateUserID The updateUserID to set.
	 */
	public void setUpdateUserID(long updateUserID) {
		this.updateUserID = updateUserID;
		putUsedField("updateUserID", updateUserID);
	}
	/**
	 * @return Returns the warnScale.
	 */
	public double getWarnScale() {
		return warnScale;
	}
	/**
	 * @param warnScale The warnScale to set.
	 */
	public void setWarnScale(double warnScale) {
		this.warnScale = warnScale;
		putUsedField("warnScale", warnScale);
	}
}
