/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-6
 */
package com.iss.itreasury.treasuryplan.etl.extract.dao;

import java.sql.Connection;

import com.iss.itreasury.treasuryplan.util.TPConstant;



/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Trea_ActualAmountDAO extends AbstractAmountDAO {
	public Trea_ActualAmountDAO(Connection conn){
		super("Trea_ActualAmount",conn);
		super.setSelfManagedConn(true);
		super.setUseMaxID();
		amountFieldName = "ActualAmount";
	}
	
	public Trea_ActualAmountDAO() throws Exception{
		super("Trea_ActualAmount");
		this.establishConnectionByModuleID(TPConstant.ModuleType.PLAN);
		super.setSelfManagedConn(true);
		super.setUseMaxID();
		amountFieldName = "ActualAmount";
	}			
	
	
}
