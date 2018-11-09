package com.iss.itreasury.securities.deliveryorder.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.net.ano.SupervisorService;

import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-1
 */
public class Sec_RemarkDAO extends SecuritiesDAO {


	/**
	 * @see com.iss.itreasury.securities.util.SecuritiesDAO#getNextID()
	 */
	
	public Sec_RemarkDAO(){
		super("Sec_Remark");
	}
	
	public long getNextID()  throws SecuritiesDAOException{
		return 10;
	}
	

}
