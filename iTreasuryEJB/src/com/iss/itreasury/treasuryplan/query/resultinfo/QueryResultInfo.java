/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-20
 */
package com.iss.itreasury.treasuryplan.query.resultinfo;

import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TPDestinationDataInfo;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryResultInfo extends TPDestinationDataInfo {
	private double actualAmount = 0.0;    //实际数
	private double forecastAmount = 0.0;  //预测数
	private double differenceAmount = 0.0; //差值
	private double planAmount = 0.0;  //计划数
	
	
	public QueryResultInfo(){
	}
	
	public QueryResultInfo(TPTemplateInfo templateInfo){
		super(templateInfo);
	}			
	
	/**
	 * @return Returns the actualAmount.
	 */
	public double getActualAmount() {
		return (long)actualAmount;
	}
	/**
	 * @param actualAmount The actualAmount to set.
	 */
	public void setActualAmount(double actualAmount) {
		//putUsedField("actualAmount", actualAmount);
		this.actualAmount = actualAmount;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.report.dataentity.TPDestinationDataInfo#putDestinationAmount(double)
	 */
	public void putDestinationAmount(double amount) {
		setActualAmount(amount);
	}
	/**
	 * @return Returns the defenrenceAmount.
	 */
	public double getDifferenceAmount() {
		return (long)differenceAmount;
	}
	/**
	 * @param defenrenceAmount The defenrenceAmount to set.
	 */
	public void setDifferenceAmount(double defenrenceAmount) {
		this.differenceAmount = defenrenceAmount;
	}
	/**
	 * @return Returns the forcastAmount.
	 */
	public double getForecastAmount() {
		return (long)forecastAmount;
	}
	/**
	 * @param forcastAmount The forcastAmount to set.
	 */
	public void setForecastAmount(double forcastAmount) {
		this.forecastAmount = forcastAmount;
	}
	/**
	 * @return Returns the planAmount.
	 */
	public double getPlanAmount() {
		return (long)planAmount;
	}
	/**
	 * @param planAmount The planAmount to set.
	 */
	public void setPlanAmount(double planAmount) {
		this.planAmount = planAmount;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.report.dataentity.TPDestinationDataInfo#retrieveAmount()
	 */
	public double retrieveAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
