/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-9
 */
package com.iss.itreasury.dao;

import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ITreasuryDAOException extends ITreasuryException {
	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);	
	public ITreasuryDAOException(String errorMessage,Throwable cause)  {
		//modified by mzh_fu 2007/06/22s
		//super("Sec_E100",cause);	
		super(errorMessage,cause);
		log.error(errorMessage);		
	}		
}
