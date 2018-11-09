package com.iss.itreasury.settlement.generalledger.dao;

import com.iss.itreasury.dao.SettlementDAO;
import java.sql.*;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-11-17
 */
public class Sett_GeneralLedgerDAO extends SettlementDAO {
	
	public Sett_GeneralLedgerDAO(){
		super.strTableName = "Sett_GeneralLedger";
	}
	
	public String findSubjectCodeByID(long id) throws SQLException{
		String subjectCode = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT SSUBJECTCODE FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" WHERE ID = " + id);

			String strSQL = strSQLBuffer.toString();
			log.debug(strSQL);				
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery(); 
			if(rs.next()){
				subjectCode = rs.getString(1);
			}
		}

		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}		
		return subjectCode;
	}
	public String findGLSubjectCode(long id) throws SQLException{
		String subjectCode = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT SSUBJECTCODE FROM \n");
			strSQLBuffer.append( "sett_vglsubjectdefinition \n");
			strSQLBuffer.append(" WHERE ID = " + id);

			String strSQL = strSQLBuffer.toString();
			log.debug(strSQL);				
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery(); 
			if(rs.next()){
				subjectCode = rs.getString(1);
			}
		}

		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}		
		return subjectCode;
	}
}
