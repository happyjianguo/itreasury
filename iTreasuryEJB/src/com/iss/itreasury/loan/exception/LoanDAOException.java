package com.iss.itreasury.loan.exception;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;


/**
 * Title:        		iTreasury
 * Description:         ��װ����֤ȯģ���DAO�쳣(SQLException)
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
   
   /**�ڴ˿������Ӷ����ݿ��쳣��������⹹�캯��*/
	

}
