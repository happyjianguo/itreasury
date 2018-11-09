/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-17
 */
package com.iss.itreasury.budget.exception;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BudgetInvalidRecordException extends com.iss.itreasury.budget.exception.BudgetException {
	//该笔[?]不存在或已删除
	public BudgetInvalidRecordException(String recordName){
	super("Sec_E150",recordName,null);
	}
}
