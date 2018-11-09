package com.iss.itreasury.ebank.obaccountinfo.dao;

import java.sql.Connection;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.SettlementDAO;

/**
 * Title:        		iTreasury
 * Description:                   
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author             kewen hu 
 * @version
 * Date of Creation     2004-01-12
 */
public abstract class OBConnectionDao extends SettlementDAO {
	/**Used for transaction*/
	private Connection transConn = null;
	/**Use self-maintenanced data pool connection by DAO or use connection inputted from out of DAO*/
	private boolean isUseDataPoolConn = false;
	protected Log4j log = new Log4j(Constant.ModuleType.EBANK, this);

	/**
	 * ¹¹Ôìº¯Êý
	 * @param  Connection
	 * @return nothing
	 * @exception nothing
	 */
	public OBConnectionDao(Connection conn) {
		super(conn);
	}

	/**Override mothod from SettlementDAO for getting connection*/
	protected Connection getConnection() {
		if(isUseDataPoolConn || transConn == null)
			return super.getConnection();
		else
			return transConn;
	}

	protected void setConnection(Connection conn) {
		transConn = conn;
	}
}