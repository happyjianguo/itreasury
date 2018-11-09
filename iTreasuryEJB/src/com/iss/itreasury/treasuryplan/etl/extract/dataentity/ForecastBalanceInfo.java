/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-6
 */
package com.iss.itreasury.treasuryplan.etl.extract.dataentity;


/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ForecastBalanceInfo extends AbstractBalanceInfo {
	private double forecastBalance = 0.0;   //‘§≤‚”‡∂Ó
	

	/**
	 * @return Returns the forecastBalance.
	 */
	public double getForecastBalance() {
		return forecastBalance;
	}
	/**
	 * @param forecastBalance The forecastBalance to set.
	 */
	public void setForecastBalance(double forecastBalance) {
		putUsedField("forecastBalance", forecastBalance);
		this.forecastBalance = forecastBalance;
	}
}
