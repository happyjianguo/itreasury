/*
 * Created on 2003-10-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.interest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.*;
//import com.iss.itreasury.settlement.interest.dataentity.InterestRateInfo;
//import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class sett_InterestRateDAO extends InterestDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public sett_InterestRateDAO(Connection transConn){
		super(transConn);
		super.strTableName = "sett_InterestRate";
	}
	/**
	 * select rate whose effective day is less than input validate, id is equal input rateID and effective is lastest
	 * modify 2003-11-14 	
	 * @param rateID
	 * @param validDate
	 * @return
	 * @throws SQLException
	 */
	public float getRateByValidDateAndRateID(long rateID, Timestamp validDate) throws SQLException{
		float rate = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;				
		
		StringBuffer bufferSQL = new StringBuffer();
		
		conn = getConnection();
		
		bufferSQL.append("SELECT InterestRate.frate FROM  \n");
		bufferSQL.append("  (SELECT MAX(dtEffective) latestdtEffective FROM Sett_InterestRate \n");
		bufferSQL.append("  WHERE id = " + rateID + " AND dtEffective <= ? ) a, \n");		
		bufferSQL.append("  Sett_InterestRate InterestRate \n");
		bufferSQL.append("  WHERE InterestRate.dtEffective = a.latestdtEffective  \n");
		bufferSQL.append("  and InterestRate.id = " + rateID);
		
		log.info(bufferSQL.toString());
		ps = conn.prepareStatement(bufferSQL.toString());
		ps.setTimestamp(1,validDate);
		rs = ps.executeQuery(); 
		
		if(rs.next()){
			rate = rs.getFloat("frate");			
		}
	    cleanup(rs);
	    cleanup(ps);
	    cleanup(conn);
	    
		return rate;
				
	}
}
