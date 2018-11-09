/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-30
 */
package com.iss.itreasury.securities.stock.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.stock.dataentity.DailyStockInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_DailyStockDAO extends SecuritiesDAO {
	public SEC_DailyStockDAO(){
		super("SEC_DailyStock");
	}
	
	/**
	 * 获取上次库存日结日期
	 * */
	
	public Timestamp findLastEndProcessTime(long accountID,long clientID,long securitiesID) throws SecuritiesDAOException{
		Timestamp res = null;
		try {
			initDAO();
			String strSQL = "select max(stockDate) as lastendprocessdate from "+strTableName+" where accountid = "+accountID
			+ " and clientid = "+clientID
			+ " and securitiesID = "+securitiesID;
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			try {
				if(localRS.next()){
					res = localRS.getTimestamp("lastendprocessdate");
				}
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);
			}
			finalizeDAO();
			return res;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
	
	
	/*
	 * 重载数据库更新操作　
	 * @param dataEntity 需要被更新的数据库表对应的Data Entity的实例
	 * @param 　
	 * @return
	 * @throws ITreasuryDAOException
	 */			
	public void update(ITreasuryBaseDataEntity dataEntity) throws ITreasuryDAOException{
		DailyStockInfo info = (DailyStockInfo) dataEntity;
		initDAO();
		String strSQL = "update "+strTableName+" set quantity = "+info.getQuantity()+", frozenQuantity = "+info.getFrozenQuantity()
		+", cost = "+info.getCost()+", netCost = "+info.getNetCost()+", profitLoss = "+info.getProfitLoss() + ", netProfitLoss = "+info.getNetProfitLoss()
		+", inNumber = "+ info.getInNumber() + ", inQuantity = "+ info.getInQuantity() + ", inAmount = "+ info.getInAmount() + ", inNetAmount = "+ info.getInNetAmount()
		+ ", outNumber = "+info.getOutNumber()+ ", outQuantity = "+ info.getOutQuantity() + ", outCost = "+ info.getOutCost() + ", outNetCost = "+ info.getOutNetCost()
		+ ", outAmount = "+info.getOutAmount() + ", outNetAmount = "+ info.getOutNetAmount() + " where stockDate = ?" + " and securitiesID = "+ info.getSecuritiesID() + " and accountID = "+info.getAccountID()
		+ " and clientID = "+ info.getClientID();
		PreparedStatement localPS = prepareStatement(strSQL);
		try {
			localPS.setTimestamp(1, info.getStockDate());
		} catch (SQLException e) {
			throw new ITreasuryDAOException("",e);
		}
		executeUpdate();
		finalizeDAO();
	}	
}
