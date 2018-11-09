/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-31
 */
package com.iss.itreasury.securities.securitiesaccount.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.DuplicatePrimaryKeyException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.securitiesaccount.dataentity.DailyAccountInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_DailyAccountDAO extends SecuritiesDAO {
	public SEC_DailyAccountDAO(){
		super("SEC_DailyAccount");
	}
	
	public DailyAccountInfo findByAccountIDAndDate(long accountID, Timestamp date) throws SecuritiesDAOException{
		DailyAccountInfo info = new DailyAccountInfo();
		info.setAccountID(accountID);
		info.setAccountDate(date);
		ArrayList list;
		try {
			list = (ArrayList) findByCondition(info);
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		if(list.size() > 1){
			throw new DuplicatePrimaryKeyException(strTableName);
		}
		if(list.size() == 0){
			return null;
		}
		return (DailyAccountInfo) list.get(0);
		
	} 
	
	public Timestamp findLastEndProcessTime(long id) throws SecuritiesDAOException{
		Timestamp res = null;
		try {
			initDAO();
			String strSQL = "select max(accountdate) as lastendprocessdate from Sec_DailyAccount where accountid = "+id;
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
		DailyAccountInfo dailyAccountInfo = (DailyAccountInfo) dataEntity;
		initDAO();
		String strSQL = "update "+strTableName+" set balance = "+dailyAccountInfo.getBalance()+", payNumber = "+dailyAccountInfo.getPayNumber()
		+", payAmount = "+dailyAccountInfo.getPayAmount()+", receiveNumber = "+dailyAccountInfo.getReceiveNumber()+", receiveAmount = "+dailyAccountInfo.getReceiveAmount()
		+ " where accountDate = ? and accountID = "+ dailyAccountInfo.getAccountID();
		PreparedStatement localPS = prepareStatement(strSQL);
		try {
			localPS.setTimestamp(1, dailyAccountInfo.getAccountDate());
		} catch (SQLException e) {
			throw new ITreasuryDAOException("",e);
		}
		executeUpdate();
		finalizeDAO();
	}
}
