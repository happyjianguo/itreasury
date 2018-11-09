/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-10
 */
package com.iss.itreasury.securities.securitiesgeneralledger.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.util.Constant;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_GLEntryDAO extends SecuritiesDAO {
	public SEC_GLEntryDAO(){
		super("SEC_GLEntry");
	}
	
	public SEC_GLEntryDAO(Connection conn){
		super("SEC_GLEntry",conn);
	}	
	
	public Timestamp getLastPostGLVoucherDate()throws SecuritiesDAOException{
		Timestamp date = null;
		try {
			initDAO();
			String strSQL = "select min(executedate) from sec_glentry where statusid = "+SECConstant.TransactionStatus.CHECK
			+" and poststatusid != "+Constant.YesOrNo.YES;
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			try {
				if(localRS.next())
					date = localRS.getTimestamp(1);
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);	
			}
			finalizeDAO();
		}			
		catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} 
		return date;
	}	
}
