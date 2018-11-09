package com.iss.itreasury.settlement.transspecial.dataentity;

import java.io.Serializable;
//import java.sql.Timestamp;

import com.iss.itreasury.settlement.account.dataentity.*;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-9-8
 */

/**
 * This is a assembler class that is used to assemble info classes for passing
 * data between presentation tier and biz logic tier
 */

public class TransSpecialOperationAssembler implements Serializable {
	private SubAccountCurrentInfo receieveAcountCurrentInfo = null;

	private SubAccountCurrentInfo payAcountCurrentInfo = null;

	private TransSpecialOperationInfo transSpecialOperationInfo = null;

	public TransSpecialOperationAssembler(TransSpecialOperationInfo transSpecialOperationInfo) {
		// this.receieveAcountCurrentInfo = receieveAcountCurrentInfo;
		// this.payAcountCurrentInfo = payAcountCurrentInfo;
		this.transSpecialOperationInfo = transSpecialOperationInfo;
	}

	// @TBD: Add getter and setter functions for every fields in this assembler
	// class

	public TransSpecialOperationInfo getSett_TransSpecialOperationInfo() {
		return this.transSpecialOperationInfo;
	}

	public SubAccountCurrentInfo getSubAccountCurrentInfo() {
		return payAcountCurrentInfo;
	}

	public SubAccountCurrentInfo getReceieveAcountCurrentInfo() {

		return receieveAcountCurrentInfo;
	}
}
