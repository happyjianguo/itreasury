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
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Trea_ForecastAmountDAO extends AbstractAmountDAO {
	public Trea_ForecastAmountDAO(Connection conn) {
		super("Trea_ForecastAmount", conn);
		this.setSelfManagedConn(true);
		super.setUseMaxID();
		amountFieldName = "ForecastAmount";
	}

	public Trea_ForecastAmountDAO() throws Exception {
		super("Trea_ForecastAmount");
		this.establishConnectionByModuleID(TPConstant.ModuleType.PLAN);
		this.setSelfManagedConn(true);
		super.setUseMaxID();
		amountFieldName = "ForecastAmount";
	}

	public void delAllCurrent() throws Exception {
		initDAO();
		String strSQL = "DELETE Trea_ForecastAmount WHERE accounttypeid=1 ";
		prepareStatement(strSQL);
		executeUpdate();
		finalizeDAO();
	}

}