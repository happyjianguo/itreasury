package com.iss.itreasury.settlement.transcurrentdeposit.dataentity;

import java.io.Serializable;
//import java.sql.Timestamp;

import com.iss.itreasury.settlement.account.dataentity.*;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

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
 *This is a assembler class that is used to assemble info classes for passing
 *data between presentation tier and biz logic tier
 */

public class TransCurrentDepositAssembler implements Serializable{
 	private SubAccountCurrentInfo receieveAcountCurrentInfo = null;
	private SubAccountCurrentInfo payAcountCurrentInfo = null;
 	private TransCurrentDepositInfo transCurrentDepositInfo = null;
 	private InutParameterInfo inutParameterInfo = null;
 	
 	public TransCurrentDepositAssembler(TransCurrentDepositInfo transCurrentDepositInfo){
// 		this.receieveAcountCurrentInfo = receieveAcountCurrentInfo;
//		this.payAcountCurrentInfo = payAcountCurrentInfo;
		this.transCurrentDepositInfo = transCurrentDepositInfo;
 	}
 	//@TBD: Add getter and setter functions for every fields in this assembler class
 	

	
	public TransCurrentDepositInfo getSett_TransCurrentDepositInfo(){
		return this.transCurrentDepositInfo;
	}
	

	public SubAccountCurrentInfo getSubAccountCurrentInfo(){
		return payAcountCurrentInfo;
	}
	
	public SubAccountCurrentInfo getReceieveAcountCurrentInfo(){
		
		return receieveAcountCurrentInfo;
	}
	
	public InutParameterInfo getInutParameterInfo()
	{		
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo)
	{		
		this.inutParameterInfo = inutParameterInfo;
	}
}
