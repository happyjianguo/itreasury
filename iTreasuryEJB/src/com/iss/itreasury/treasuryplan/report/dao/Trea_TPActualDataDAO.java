/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-20
 */
package com.iss.itreasury.treasuryplan.report.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.report.dataentity.TPActualDataInfo;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Trea_TPActualDataDAO extends AbstractDestinationDataDAO {
	
	public Trea_TPActualDataDAO(Connection conn){
		super("Trea_TPActualData",conn);
		super.setUseMaxID();
		amountFieldName = "ACTUALAMOUNT";
	}
	
	
	/**
	 * 从ResultSet中获取查询结果　
	 * @param className 需要查找的数据库表对应的Data Entity的类名
	 * @param 　
	 * @return
	 * @throws ITreasuryDAOException
	 */	
	public Collection getDataEntitiesFromResultSet(Class dataEntityClass) throws ITreasuryDAOException{

		
		ArrayList resList = new ArrayList();
		try {
			while(transRS.next()){
				TPActualDataInfo info= new TPActualDataInfo();				
				setRStoBaseObject(transRS,info);
//				info.setPlanAmount(transRS.getDouble("PlanAmount"));
				info.setTransactionDate(transRS.getTimestamp("TransactionDate"));
				info.setActualAmount(transRS.getDouble("actualAmount"));
				//info.setDifferenceAmount(transRS.getDouble("differenceAmount"));
			
				resList.add(info);
			}
			
		
		}catch(SQLException e){
			throw new ITreasuryDAOException("",e);
		}
		return resList;
	}
	

	public double updateAmountByLineIDAndTransactionDate(long officeID,long currencyID,double amount,long lineID,Timestamp transDate) throws Exception{
		String strDate = this.transferTimestampToTo_DateString(transDate);
		String strSQL = " update  "+strTableName+"  set actualamount = "+amount+" where transactiondate = "+strDate+" and lineid =" + lineID
		+" and OFFICEID = "+ officeID + " and CURRENCYID = "+currencyID;;
		try {
			this.prepareStatement(strSQL);
			this.executeUpdate();
		} catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			finalizeDAO();
		}

		return amount;
	}


	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.report.dao.AbstractDestinationDataDAO#resetAmountFieldName()
	 */
	public void resetAmountFieldName() {
		amountFieldName = "ACTUALAMOUNT";		
	}	 	
	
}

