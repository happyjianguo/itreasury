/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-7
 */
package com.iss.itreasury.securities.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;


public class TransactionTypeInfo extends SECBaseDataEntity {
	private long id = -1;               //自增，也就是交易类型编号，查询页面显示时通过补零表现为5位长
	private String name = null;			//交易类型名称
	private long businessTypeID = -1;	//所属业务类型
	//private long isUpdateStock = -1;	//是否修改库存
	private long stockDirection = -1;	//库存收付方向
	//private long isUpdateCapital = -1;	//是否修改资金
	private long capitalDirection = -1; //资金收付方向
	private long isNeedApplyForm = -1;//是否需要业务申请
	private long isNeedNotifyForm = -1;		//是是否需要业务通知单
	private long deliveryOrderAttribute = -1; //交割单性质
	private long priceTarget = -1;			//价格指标	
	private long amountTarget = -1;			//数量指标
	private long dateTarget = -1;           //日期指标
	private long interestRateTarget = -1;   //利率指标
	private long frozenProcess = -1;        //冻结处理方式　　　　
	private long registerProcess = -1;      //登记簿处理方式	
	private long statusID = -1;             //状态
	private long inputUserID = -1;          //录入人
	private Timestamp inputDate = null;		//录入时间
	private long updateUserID = -1;			//修改人
	private Timestamp updateDate = null;    //修改时间
	private long checkUserID = -1;          //复核人
	private Timestamp checkDate = null;     //复核时间
	private long isNeedGLEntry = -1;        //是否需要产生会计分录


	/**
	 * @return Returns the amountTarget.
	 */
	public long getAmountTarget() {
		return amountTarget;
	}
	/**
	 * @param amountTarget The amountTarget to set.
	 */
	public void setAmountTarget(long amountTarget) {
		putUsedField("amountTarget", amountTarget);
		this.amountTarget = amountTarget;
	}
	/**
	 * @return Returns the businessTypeID.
	 */
	public long getBusinessTypeID() {
		return businessTypeID;
	}
	/**
	 * @param businessTypeID The businessTypeID to set.
	 */
	public void setBusinessTypeID(long businessTypeID) {
		putUsedField("businessTypeID", businessTypeID);
		this.businessTypeID = businessTypeID;
	}
	/**
	 * @return Returns the capitalDirection.
	 */
	public long getCapitalDirection() {
		return capitalDirection;
	}
	/**
	 * @param capitalDirection The capitalDirection to set.
	 */
	public void setCapitalDirection(long capitalDirection) {
		putUsedField("capitalDirection", capitalDirection);
		this.capitalDirection = capitalDirection;
	}
	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate() {
		return checkDate;
	}
	/**
	 * @param checkDate The checkDate to set.
	 */
	public void setCheckDate(Timestamp checkDate) {
		putUsedField("checkDate", checkDate);
		this.checkDate = checkDate;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID() {
		return checkUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID) {
		putUsedField("checkUserID", checkUserID);
		this.checkUserID = checkUserID;
	}
	/**
	 * @return Returns the dateTarget.
	 */
	public long getDateTarget() {
		return dateTarget;
	}
	/**
	 * @param dateTarget The dateTarget to set.
	 */
	public void setDateTarget(long dateTarget) {
		putUsedField("dateTarget", dateTarget);
		this.dateTarget = dateTarget;
	}
	/**
	 * @return Returns the deliveryOrderAttribute.
	 */
	public long getDeliveryOrderAttribute() {
		return deliveryOrderAttribute;
	}
	/**
	 * @param deliveryOrderAttribute The deliveryOrderAttribute to set.
	 */
	public void setDeliveryOrderAttribute(long deliveryOrderAttribute) {
		putUsedField("deliveryOrderAttribute", deliveryOrderAttribute);
		this.deliveryOrderAttribute = deliveryOrderAttribute;
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
		putUsedField("id", id);
		this.id = id;
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
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
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
		putUsedField("inputUserID", inputUserID);
		this.inputUserID = inputUserID;
	}
	/**
	 * @return Returns the interestRateTarget.
	 */
	public long getInterestRateTarget() {
		return interestRateTarget;
	}
	/**
	 * @param interestRateTarget The interestRateTarget to set.
	 */
	public void setInterestRateTarget(long interestRateTarget) {
		putUsedField("interestRateTarget", interestRateTarget);
		this.interestRateTarget = interestRateTarget;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		putUsedField("name", name);
		this.name = name;
	}
	/**
	 * @return Returns the priceTarget.
	 */
	public long getPriceTarget() {
		return priceTarget;
	}
	/**
	 * @param priceTarget The priceTarget to set.
	 */
	public void setPriceTarget(long priceTarget) {
		putUsedField("priceTarget", priceTarget);
		this.priceTarget = priceTarget;
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
		putUsedField("statusID", statusID);
		this.statusID = statusID;
	}
	/**
	 * @return Returns the stockDirection.
	 */
	public long getStockDirection() {
		return stockDirection;
	}
	/**
	 * @param stockDirection The stockDirection to set.
	 */
	public void setStockDirection(long stockDirection) {
		putUsedField("stockDirection", stockDirection);
		this.stockDirection = stockDirection;
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
		putUsedField("updateDate", updateDate);
		this.updateDate = updateDate;
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
		putUsedField("updateUserID", updateUserID);
		this.updateUserID = updateUserID;
	}
	/**
	 * @return Returns the frozenProcess.
	 */
	public long getFrozenProcess() {
		return frozenProcess;
	}
	/**
	 * @param frozenProcess The frozenProcess to set.
	 */
	public void setFrozenProcess(long frozenProcess) {
		putUsedField("frozenProcess", frozenProcess);
		this.frozenProcess = frozenProcess;
	}
	/**
	 * @return Returns the registerProcess.
	 */
	public long getRegisterProcess() {
		return registerProcess;
	}
	/**
	 * @param registerProcess The registerProcess to set.
	 */
	public void setRegisterProcess(long registerProcess) {
		putUsedField("registerProcess", registerProcess);
		this.registerProcess = registerProcess;
	}
	/**
	 * @return Returns the isNeedApplyForm.
	 */
	public long getIsNeedApplyForm() {
		return isNeedApplyForm;
	}
	/**
	 * @param isNeedApplyForm The isNeedApplyForm to set.
	 */
	public void setIsNeedApplyForm(long isNeedApplyForm) {
		putUsedField("isNeedApplyForm", isNeedApplyForm);
		this.isNeedApplyForm = isNeedApplyForm;
	}
	/**
	 * @return Returns the isNeedNotifyForm.
	 */
	public long getIsNeedNotifyForm() {
		return isNeedNotifyForm;
	}
	/**
	 * @param isNeedNotifyForm The isNeedNotifyForm to set.
	 */
	public void setIsNeedNotifyForm(long isNeedNotifyForm) {
		putUsedField("isNeedNotifyForm", isNeedNotifyForm);
		this.isNeedNotifyForm = isNeedNotifyForm;
	}
	/**
	 * @return Returns the isNeedGLEntry.
	 */
	public long getIsNeedGLEntry() {
		return isNeedGLEntry;
	}
	/**
	 * @param isNeedGLEntry The isNeedGLEntry to set.
	 */
	public void setIsNeedGLEntry(long isNeedGLEntry) {
		putUsedField("isNeedGLEntry", isNeedGLEntry);
		this.isNeedGLEntry = isNeedGLEntry;
	}
}
