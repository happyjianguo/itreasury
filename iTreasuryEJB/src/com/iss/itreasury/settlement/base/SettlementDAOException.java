package com.iss.itreasury.settlement.base;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;


/**
 * Title:        		iTreasury
 * Description:         ��װ����֤ȯģ���DAO�쳣(SQLException)
 *  Copyright (c) 2003 Company: iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-27
 */
public class SettlementDAOException extends SettlementException {

	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	
	public SettlementDAOException(String errorMessage,Throwable cause)  {
		super("Sec_E100",cause);		
		log.error(errorMessage);
	}	
	
	public SettlementDAOException(ITreasuryException cause){
		super("Sec_E100",cause);		
	}
   
   /**�ڴ˿������Ӷ����ݿ��쳣��������⹹�캯��*/
	

}
