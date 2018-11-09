/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-5-26
 */
package com.iss.itreasury.securities.deliveryorderservice.dao;

import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesTypeCreditLineDAO extends SecuritiesDAO {
	public SecuritiesTypeCreditLineDAO(){
		super("SEC_SecuritiesTypeCreditLine");
	}
	
	
	static public void main(String[] args){
		SecuritiesTypeCreditLineDAO dao = new SecuritiesTypeCreditLineDAO();
		double cl=-1.0;
		try {
			cl = dao.getCreditLine(1);
		} catch (SecuritiesDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("----------"+cl);
	}
	
	public double getCreditLine(/*long securitiesID, */long securitiesTypeID) throws SecuritiesDAOException{
		double amount = 0.0;
		try {		
			String strSQL = "select CreditLine from " +strTableName+ " where statusid = 3 and SecuritiesTypeID = "+  securitiesTypeID; 
			initDAO();

 

			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			try {
				if(localRS.next())
					amount = localRS.getDouble(1);
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);	
			}
			finalizeDAO();
		} catch (ITreasuryDAOException e) {			
			throw new SecuritiesDAOException(e);
		}	
		return amount;
		
		
	}
	
	}