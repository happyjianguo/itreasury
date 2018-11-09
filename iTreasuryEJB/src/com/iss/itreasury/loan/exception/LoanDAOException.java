package com.iss.itreasury.loan.exception;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;


/**
 * Title:        		iTreasury
 * Description:         封装所有证券模块的DAO异常(SQLException)
 *  Copyright (c) 2003 Company: iSoftStone
 * @author             ninh 
 * @version
 *  Date of Creation    2004-4-21
 */
public class LoanDAOException extends LoanException {

	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);
	
	public LoanDAOException(String errorMessage,Throwable cause)  
    {
		super("Loan_E100",cause);		
		log.error(errorMessage);
	}	
	
	public LoanDAOException(ITreasuryException cause)
    {
		super("Loan_E100",cause);		
	}
   
   /**在此可以增加对数据库异常处理的特殊构造函数*/
	

}
