package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.SettlementDAO;

/**
 * Title:        		iTreasury
 * Description:                   
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-10-25
 */
public abstract class InterestDAO extends SettlementDAO {
	
	/**Used for transaction*/
	private Connection transConn = null;
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	/**Use self-maintenanced data pool connection by DAO or use connection inputted from out of DAO*/
	private boolean isUseDataPoolConn = false;	
	
	public InterestDAO(Connection transConn){
		super(transConn);
		
	}
	
	/**Override mothod from SettlementDAO for getting connection*/
	protected Connection getConnection()
	{
		if(isUseDataPoolConn || transConn == null)
			return super.getConnection();
		else
			return transConn;
	} 

}
