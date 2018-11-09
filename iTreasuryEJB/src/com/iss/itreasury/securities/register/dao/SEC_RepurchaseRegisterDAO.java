/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-4
 */
package com.iss.itreasury.securities.register.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.util.SECConstant;

/**
 * 拆借/回购登记簿数据访问对象
 * */
public class SEC_RepurchaseRegisterDAO extends SecuritiesDAO {
	/**
	 * @param tableName
	 */
	public SEC_RepurchaseRegisterDAO() {
		super("SEC_RepurchaseRegister");
	}
	
	public Collection getAllNotRepayDeliverOrderID(long transactionTypeID) throws SecuritiesDAOException{
		ArrayList idList = null;
		try {
			initDAO();
			
			String sql = "SELECT FIRSTDELIVERYORDERID FROM "+strTableName
			+ " WHERE BALANCE > 0.0 AND TRANSACTIONTYPEID = "+transactionTypeID
			+ " AND STATUSID = " + SECConstant.BusinessAttributeStatus.SAVED;
			System.out.println("sql="+sql);
			prepareStatement(sql);
			ResultSet rs = executeQuery();

			idList = new ArrayList();
			int i = 0;
			while(rs.next()){
				long doID = rs.getLong("FIRSTDELIVERYORDERID");
				idList.add(new Long(doID));
				i++;
			}

			
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("Sec_100",e);
		}
		return idList;
	}
	
	/**
	 * 判断一个交割单是否已经反款
	 * @param deliveryOrderId
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public long isDeliveryOrderRepaid(long deliveryOrderId)throws SecuritiesDAOException{
		long isRepaid = SECConstant.FALSE;
		
		try{
			initDAO();
			
			String strSql = "select id from sec_repurchaseregister \n" +
					"where FIRSTDELIVERYORDERID = "+deliveryOrderId + " \n" +
					"and LASTDELIVERYORDERID is not null";
			
			prepareStatement(strSql);
			ResultSet rs = executeQuery();
			
			if (rs.next()){
				isRepaid = SECConstant.TRUE;
			}
			
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		} catch (SQLException e) {
			throw new SecuritiesDAOException("获得交割单是否反款信息出错",e);
		}
		
		return isRepaid;
	}
}
