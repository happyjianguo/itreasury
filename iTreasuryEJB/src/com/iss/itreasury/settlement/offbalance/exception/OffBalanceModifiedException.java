/*
 * Created on 2004-11-24
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.offbalance.exception;

import com.iss.itreasury.settlement.offbalance.exception.OffBalanceException;

/**
 * Title:        		iTreasury
 * Description:         通过继承的方法保留OffBalanceModifiedException的所有功能并且增加在JDK1.4中出现的<P>
 * 						嵌套异常的处理(for project that deployed under version or JDK1.4)<P> 
 *  Copyright           (c) 2004 Company: iSoftStone
 * @author              kewen hu 
 * @version
 *  Date of Creation    2004-11-23
 */
public class OffBalanceModifiedException extends OffBalanceException {
	public OffBalanceModifiedException(String offBalanceReason){
		super("Sett_E201",offBalanceReason,null);
	}
}