/**
 * 
 */
package com.iss.itreasury.credit.exp;

import com.iss.itreasury.util.IException;

/**
 * @author xintan
 * 
 * 贷款授信校验时用的异常，柔性控制
 *
 */
public class CreditFlexException extends IException {
	
	public CreditFlexException(String strMessage){
		super(strMessage);
	}
}
