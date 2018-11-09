/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-4
 */
package com.iss.itreasury.securities.register.dao;

import com.iss.itreasury.securities.setting.dataentity.TransactionTypeInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.util.SECConstant;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RegisterDAOFactory {
	public RegisterDAOFactory(){
	}
	
	public static SecuritiesDAO getRegisterDAO(TransactionTypeInfo typeInfo){
		long register = SECConstant.RegisterProcess.getBelongRegister(typeInfo.getRegisterProcess());
		if(register == SECConstant.RegisterProcess.REPURCHASE_REGISTER){
			return new SEC_RepurchaseRegisterDAO();
		}else if(register == SECConstant.RegisterProcess.PURCHASE_REGISTER){
			return new SEC_PurchaseRegisterDAO();					
		}else if(register == SECConstant.RegisterProcess.LONGTERMINVESTMENT_REGISTER){
			return new SEC_InvestmentRegisterDAO();
		}
		return null;
	} 
}
