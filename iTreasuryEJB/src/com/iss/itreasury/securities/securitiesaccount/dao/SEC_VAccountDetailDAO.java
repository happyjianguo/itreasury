/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-4-2
 */
package com.iss.itreasury.securities.securitiesaccount.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.securitiesaccount.dataentity.DailyAccountInfo;
import com.iss.itreasury.securities.securitiesaccount.dataentity.VAccountDetailInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_VAccountDetailDAO extends SecuritiesDAO {
	public SEC_VAccountDetailDAO(){
		super("SEC_VAccountDetail");
	}
	
	public double[] sumAccountDetails(long accountID,Timestamp date, long direction) throws SecuritiesDAOException{
		double[] res = new double[2]; 
		try {
			initDAO();
			
			String strSQL = "select count(*) as DONumber, sum(NetIncome) as NetIcomes from "+ strTableName +" where accountID = " + accountID + 
			" and direction = " + direction + " and DeliveryDate = ?";
							
			PreparedStatement localPS = prepareStatement(strSQL);
			try {
				localPS.setTimestamp(1, date);
	
			ResultSet localRS = executeQuery();
			
			if(localRS.next()){
				res[0] = (double)localRS.getLong("DONumber");
				res[1] = localRS.getDouble("NetIcomes");
			}
			//res.localRS.getLong("Num");				
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);
			}		
			finalizeDAO();			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		

		return res;			
	}
}
