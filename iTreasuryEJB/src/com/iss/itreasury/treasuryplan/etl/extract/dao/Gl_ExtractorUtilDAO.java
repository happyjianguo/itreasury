/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-31
 */
package com.iss.itreasury.treasuryplan.etl.extract.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Gl_ExtractorUtilDAO extends AbstractExtractorUtilDAO{

	/**
	 * @param moduleID
	 * @throws Exception
	 */
	public Gl_ExtractorUtilDAO() throws Exception {
		super(Constant.ModuleType.GENERALLEDGER);
		// TODO Auto-generated constructor stub
	}
	
	public Collection GetGlBalanceInfo(long officeID,long currencyID,Timestamp startDate,Timestamp endDate) throws Exception{
		String strStartDate = this.transferTimestampToTo_DateString(startDate);
        String strEndDate = this.transferTimestampToTo_DateString(endDate);
		String strSQL = "select * from sett_glbalance where nOfficeID = "+officeID+" and nCurrencyID = "+currencyID+" and dtgldate >= to_date('" + DataFormat.formatDate(startDate) +"','yyyy-mm-dd')"+" and dtgldate <= to_date('" + DataFormat.formatDate(endDate) +"','yyyy-mm-dd')";
		//initDAO();
		ArrayList list;
		try {
			PreparedStatement localPS = prepareStatement(strSQL);
			//localPS.setTimestamp(1, transactionDate);
			ResultSet localRS = executeQuery();
			list = new ArrayList();
			int i = 0;
			  while(localRS.next())
			  {
				i++;		  
				GLBalanceInfo tmp = new GLBalanceInfo();
				  tmp.setID(localRS.getLong("ID"));	  
				  tmp.setBalanceDirection(localRS.getLong("NBALANCEDIRECTION"));		  
				  tmp.setCreditAmount(localRS.getDouble("MCREDITAMOUNT"));			  
				  tmp.setCreditBalance(localRS.getDouble("MCREDITBALANCE"));			  
				  tmp.setCreditNumber(localRS.getLong("NCREDITNUMBER"));			  
				  tmp.setCurrencyID(localRS.getLong("NCURRENCYID"));
				  tmp.setDebitAmount(localRS.getDouble("MDEBITAMOUNT"));
				  tmp.setDebitBalance(localRS.getDouble("MDEBITBALANCE"));
				  tmp.setDebitNumber(localRS.getLong("NDEBITNUMBER"));
				  tmp.setGLDate(localRS.getTimestamp("DTGLDATE"));
				  tmp.setGLSubjectCode(localRS.getString("SGLSUBJECTCODE"));
				  tmp.setOfficeID(localRS.getLong("NOFFICEID"));
				  //tmp.setTransDirection(localRS.getLong(""))
				  list.add(tmp);			  
			  }
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return list;
	}
}
