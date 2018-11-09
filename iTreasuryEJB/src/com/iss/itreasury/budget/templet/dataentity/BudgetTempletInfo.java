/* Generated by Together */

package com.iss.itreasury.budget.templet.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.budget.dataentity.BudgetBaseDataEntity;
public class BudgetTempletInfo extends BudgetBaseDataEntity{
    //private long id = -1;
    private long budgetSystemID = -1;  //预算体系ID
    private String itemNO="";  //项目编号
	private String itemName = "";  //项目名称
	private long itemLevel =-1;    //项目级别
	private long parentItemID = -1;//上级项目ID
	private long isLeaf =-1;       //是否叶子节点
	private long isReadOnly =-1;    //是否只读
	private long budgetType=-1;    //预算类型
	private long controlType=-1;   //控制类型
	private String itemFormula="";//项目公式
	private double suppleScale=0;  //柔性比例
	private long inComeType=-1;//收支类型
	
	private long statusID =-1;   //状态ID
	private long inputUserID =-1; //录入人
	private Timestamp inputDate=null; //录入日期
	private long updateUserID = -1; //修改人
	private Timestamp updateDate=null;  //修改日期
	private Timestamp modifyTime=null;  //修改日期
	private String remark="";


	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
		putUsedField("remark", remark);
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
	 * @return Returns the budgetType.
	 */
	public long getBudgetType() {
		return budgetType;
	}
	/**
	 * @param budgetType The budgetType to set.
	 */
	public void setBudgetType(long budgetType) {
		this.budgetType = budgetType;
		putUsedField("budgetType", budgetType);
	}
	/**
	 * @return Returns the controlType.
	 */
	public long getControlType() {
		return controlType;
	}
	/**
	 * @param controlType The controlType to set.
	 */
	public void setControlType(long controlType) {
		this.controlType = controlType;
		putUsedField("controlType", controlType);
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
	 * @return Returns the isLeaf.
	 */
	public long getIsLeaf() {
		return isLeaf;
	}
	/**
	 * @param isLeaf The isLeaf to set.
	 */
	public void setIsLeaf(long isLeaf) {
		this.isLeaf = isLeaf;
		putUsedField("isLeaf", isLeaf);
	}
	/**
	 * @return Returns the isReadOnly.
	 */
	public long getIsReadOnly() {
		return isReadOnly;
	}
	/**
	 * @param isReadOnly The isReadOnly to set.
	 */
	public void setIsReadOnly(long isReadOnly) {
		this.isReadOnly = isReadOnly;
		putUsedField("isReadOnly", isReadOnly);
	}
	/**
	 * @return Returns the itemFormula.
	 */
	public String getItemFormula() {
		return itemFormula;
	}
	/**
	 * @param itemFormula The itemFormula to set.
	 */
	public void setItemFormula(String itemFormula) {
		this.itemFormula = itemFormula;
		putUsedField("itemFormula", itemFormula);
	}
	/**
	 * @return Returns the itemLevel.
	 */
	public long getItemLevel() {
		return itemLevel;
	}
	/**
	 * @param itemLevel The itemLevel to set.
	 */
	public void setItemLevel(long itemLevel) {
		this.itemLevel = itemLevel;
		putUsedField("itemLevel", itemLevel);
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
		putUsedField("itemName", itemName);
	}
	/**
	 * @return Returns the itemNO.
	 */
	public String getItemNO() {
		return itemNO;
	}
	/**
	 * @param itemNO The itemNO to set.
	 */
	public void setItemNO(String itemNO) {
		this.itemNO = itemNO;
		putUsedField("itemNO", itemNO);
	}
	/**
	 * @return Returns the modifyTime.
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime The modifyTime to set.
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
		putUsedField("modifyTime", modifyTime);
	}
	/**
	 * @return Returns the parentItemID.
	 */
	public long getParentItemID() {
		return parentItemID;
	}
	/**
	 * @param parentItemID The parentItemID to set.
	 */
	public void setParentItemID(long parentItemID) {
		this.parentItemID = parentItemID;
		putUsedField("parentItemID", parentItemID);
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
	public long getInComeType() {
		return inComeType;
	}
	public void setInComeType(long inComeType) {
		this.inComeType = inComeType;
		putUsedField("inComeType", inComeType);
	}
}
