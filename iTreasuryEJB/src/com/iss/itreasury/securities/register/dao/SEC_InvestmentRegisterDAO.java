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

import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 *投资登记簿数据访问对象
 * */
public class SEC_InvestmentRegisterDAO extends SecuritiesDAO {
	/**
	 * @param tableName
	 */
	public SEC_InvestmentRegisterDAO() {
		super("SEC_InvestmentRegister");
	}
}
