/*
 * Created on 2004-11-24
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.offbalanceregister.exception;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;

/**
 * Title:        		iTreasury
 * Description:         通过继承的方法保留OffBalanceException的所有功能并且增加在JDK1.4中出现的<P>
 * 						嵌套异常的处理(for project that deployed under version or JDK1.4)<P> 
 *  Copyright           (c) 2004 Company: iSoftStone
 * @author              kewen hu 
 * @version
 *  Date of Creation    2004-11-23
 */
public class OffBalanceRegisterDAOException extends OffBalanceRegisterException {
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public OffBalanceRegisterDAOException(String errorMessage,Throwable cause)  {
		super("Sett_E200",cause);		
		log.error(errorMessage);
	}

	public OffBalanceRegisterDAOException(ITreasuryException cause){
		super("Sett_E200",cause);		
	}
}