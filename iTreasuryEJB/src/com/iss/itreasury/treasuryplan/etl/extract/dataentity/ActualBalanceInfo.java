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
public class ActualBalanceInfo extends AbstractBalanceInfo {
	private double actualBalance = 0.0;              // µº ”‡∂Ó
	/**
	 * @return Returns the actualBalance.
	 */
	public double getActualBalance() {
		return actualBalance;
	}
	/**
	 * @param actualBalance The actualBalance to set.
	 */
	public void setActualBalance(double actualBalance) {
		putUsedField("actualBalance", actualBalance);
		this.actualBalance = actualBalance;
	}
}
