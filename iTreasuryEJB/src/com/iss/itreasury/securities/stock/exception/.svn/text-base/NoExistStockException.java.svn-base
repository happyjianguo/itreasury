/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-15
 */
package com.iss.itreasury.securities.stock.exception;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.NameRef;;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NoExistStockException extends SecuritiesException {
	//Sec_E112=资金帐户[?]业务单位[?]证券[?]库存记录不存在
	public NoExistStockException(long accountID,long clientID,long securitiesID){
		super("Sec_E112",null);		
		String accountCode = NameRef.getAccountNobyAccountID(accountID);
		String client = NameRef.getClientNameByID(clientID);
		String securitiesCode = NameRef.getSecuritiesCodeByID(securitiesID);
		String[] strs = {accountCode,client,securitiesCode};
		super.setMessageArgs(strs);
	}
}
