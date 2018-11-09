/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.securities.securitiesgeneralledger.exception;

import com.iss.itreasury.securities.exception.SecuritiesException;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DebitAndCreditNotBalanceException extends SecuritiesException {
	public DebitAndCreditNotBalanceException(){
		super("Sec_E140",null);
	}
}
