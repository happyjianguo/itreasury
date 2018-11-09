/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-8
 */
package com.iss.itreasury.securities.stock.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.stock.dataentity.SecuritiesStockInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;


public class SEC_SecuritiesStockDAO extends SecuritiesDAO {
	public SEC_SecuritiesStockDAO(){
		super("SEC_SecuritiesStock");
	}
	
	public Collection findAll() throws SecuritiesDAOException{
		Collection res = null;
		try {
			initDAO();
			
			String strSQL = "select * from "+ strTableName;
			prepareStatement(strSQL);
			executeQuery();
			res = getDataEntitiesFromResultSet(SecuritiesStockInfo.class);
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return res;
	}
	
	
	/**
	 * 根据证券ID或者证券类别ID获取库存余额
	 * */
	public double getAmountOfSecuritiesStock(long securitiesID, long securitiesTypeID) throws SecuritiesDAOException{
		double amount = 0.0;
		try {		
			String strSQL = "select sum(Cost) from " +strTableName+ " where SecuritiesID "; 
			initDAO();
			if(securitiesID > 0){
				strSQL += " = "+ securitiesID; 
			}else{
				strSQL += " in (select id from SEC_Securities where TypeID= "+ securitiesTypeID + ")"; 
			}
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
