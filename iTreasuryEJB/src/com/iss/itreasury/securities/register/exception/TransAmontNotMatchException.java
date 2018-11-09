/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-4
 */
package com.iss.itreasury.securities.register.exception;

import com.iss.itreasury.securities.exception.SecuritiesException;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TransAmontNotMatchException extends SecuritiesException {
	public TransAmontNotMatchException(double returnAmount,double transAmount){
		//Sec_E121=返款金额[?]与拆借金额[?]不符
		super("Sec_E121",(new Double(returnAmount)).toString(),(new Double(transAmount)).toString(),null);
	}
}
