package com.iss.itreasury.securities.securitiesaccount.exception;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.SECConstant;

import com.iss.itreasury.securities.util.SECConstant;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-02
 */

public class AccounStatusException extends SecuritiesException {

	/**根据账户的错误状态产生不同的异常提示*/
	public AccounStatusException(String accountNo,long accountStatusID)  {
		super();
		String[] strs = {accountNo};
		setMessageArgs(strs);
		switch((int)accountStatusID){
			case (int)SECConstant.AccountStatusID.CLOSE:{
				setErrorCode("Sec_E104");
				}
			case (int)SECConstant.AccountStatusID.FREEZE:{
				setErrorCode("Sec_E106");
				} 
			break;
			case (int)SECConstant.AccountStatusID.SEALUP:{
				setErrorCode("Sec_E105");
				} 
			break;
			case (int)SECConstant.AccountStatus.DELETED:{
				setErrorCode("Sec_E102");
				} 			
			break;		
			default:{
				setErrorCode("Sec_E102");
			}
			break;						
		}

	}
	
	public AccounStatusException(String accountNo)  {
			super("Sec_E102",accountNo,null);
	}	

}
