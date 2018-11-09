/*
 * Created on 2003-10-23
 *
*/
package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;

import com.iss.itreasury.dao.SettlementDAO;


/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class sett_TransCurrentClearInterestDAO extends InterestDAO
{

		
	public sett_TransCurrentClearInterestDAO(Connection transConn){
		super(transConn);
		strTableName = "sett_TransCurrentClearInterest";
	}
	

	
	


}
