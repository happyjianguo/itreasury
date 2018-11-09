package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-10-31
 */
public class InterestRate implements Serializable {
	private double rate = 0.0;
	private long type = -1;
	
	
	public InterestRate(double rate, long type){
		this.rate = rate;
		this.type = type;
	}
	
	/**
	 * Returns the rate.
	 * @return double
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * Returns the type.
	 * @return long
	 */
	public long getType() {
		return type;
	}

	/**
	 * Sets the rate.
	 * @param rate The rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(long type) {
		this.type = type;
	}

}
