package com.iss.itreasury.budget.exception;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.securities.exception.SecuritiesException;


/**
 * Title:        		iTreasury
 * Description:         封装所有证券模块的DAO异常(SQLException)
 *  Copyright (c) 2003 Company: iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-27
 */
public class BudgetDAOException extends com.iss.itreasury.budget.exception.BudgetException {

	private Log4j log = new Log4j(Constant.ModuleType.BUDGET, this);
	
	public BudgetDAOException(String errorMessage,Throwable cause)  {
		super("Sec_E100",cause);		
		log.error(errorMessage);
	}	
	
	public BudgetDAOException(ITreasuryException cause){
		super("Sec_E100",cause);		
	}
   
   /**在此可以增加对数据库异常处理的特殊构造函数*/
	

}
