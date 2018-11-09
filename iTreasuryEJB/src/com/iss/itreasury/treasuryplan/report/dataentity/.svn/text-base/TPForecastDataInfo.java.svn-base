/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.report.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;


/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TPForecastDataInfo extends TPDestinationDataInfo
{
	private Timestamp inputTime = null;//被预测的交易数据日期
	private double forecastAmount = 0.0;//预测数
	private double planAmount = 0.0;//计划数	
	private long isReadOnly = -1;   //是否只读现实	

	
	public TPForecastDataInfo(){
	}
	
	public TPForecastDataInfo(TPTemplateInfo templateInfo){
		super(templateInfo);
	}		
	
	/**
	 * @return Returns the forecastAmount.
	 */
	public double getForecastAmount() {
		return (int)forecastAmount;
	}
	/**
	 * @param forecastAmount The forecastAmount to set.
	 */
	public void setForecastAmount(double forecastAmount) {
		putUsedField("forecastAmount", forecastAmount);
		this.forecastAmount = forecastAmount;
	}
	/**
	 * @return Returns the inputTime.
	 */
	public Timestamp getInputTime() {
		return inputTime;
	}
	/**
	 * @param inputTime The inputTime to set.
	 */
	public void setInputTime(Timestamp inputTime) {
		putUsedField("inputTime", inputTime);
		this.inputTime = inputTime;
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
		putUsedField("isReadOnly", isReadOnly);
		this.isReadOnly = isReadOnly;
	}
	/**
	 * @return Returns the planAmount.
	 */
	public double getPlanAmount() {
		return (int)planAmount;
	}
	/**
	 * @param planAmount The planAmount to set.
	 */
	public void setPlanAmount(double planAmount) {
		putUsedField("planAmount", planAmount);
		this.planAmount = planAmount;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.report.dataentity.TPDestinationDataInfo#putDestinationAmount(double)
	 */
	public void putDestinationAmount(double amount) {
		setForecastAmount(amount);
		setPlanAmount(amount);
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.report.dataentity.TPDestinationDataInfo#retrieveAmount()
	 */
	public double retrieveAmount() {
		// TODO Auto-generated method stub
		return forecastAmount;
	}
}	