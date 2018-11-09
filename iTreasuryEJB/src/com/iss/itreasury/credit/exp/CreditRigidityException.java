/**
 * 
 */
package com.iss.itreasury.credit.exp;

import com.iss.itreasury.util.IException;

/**
 * @author xintan
 *
 *  授信异常，刚性控制
 */
public class CreditRigidityException extends IException {

	public CreditRigidityException(String strMessage){
		super(strMessage);
	}
}
