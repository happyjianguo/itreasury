/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-8
 */
package com.iss.itreasury.securities.stock.exception;

import com.iss.itreasury.securities.exception.SecuritiesException;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OutOfStockException extends SecuritiesException {
	public OutOfStockException(String accountNo, String clientNo,String secCode){
		super("Sec_E111",null);
		String[] strs = {accountNo,clientNo,secCode};
		super.setMessageArgs(strs);
	}
}
