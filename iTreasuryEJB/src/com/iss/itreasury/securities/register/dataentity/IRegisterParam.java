/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-4
 */
package com.iss.itreasury.securities.register.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface IRegisterParam {
	

	
	public long getTransactionTypeID(); 
	/**获取对应的登记薄信息*/
	public SECBaseDataEntity gainRegisterInfo(int operationType);

}
