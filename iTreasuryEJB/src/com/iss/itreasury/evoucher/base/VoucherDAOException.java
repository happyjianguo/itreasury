/*
 * Title:        		iTreasury
 * Description:         封装所有电子回单柜模块的DAO异常(SQLException)
 * Copyright (c) 2006 	Company: iSoftStone
 * @author             	yanliu 
 * @version
 * Date of Creation    2006-9-18
 */

package com.iss.itreasury.evoucher.base;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;

public class VoucherDAOException extends VoucherException {

	private Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	
	public VoucherDAOException(String errorMessage,Throwable cause)  {
		super("Gen_E009",cause);		
		log.error(errorMessage);
	}	
	
	public VoucherDAOException(ITreasuryException cause){
		super("Gen_E009",cause);		
	}
   
   /**在此可以增加对数据库异常处理的特殊构造函数*/
	

}
