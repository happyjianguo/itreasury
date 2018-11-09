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
import com.iss.itreasury.securities.register.bizlogic.RegisterOperation;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;

/**
 * ֤ȯ�깺�Ǽǲ����ݷ��ʶ���
 */
public class SEC_PurchaseRegisterDAO extends SecuritiesDAO {
	/**
	 * @param tableName
	 */
	public SEC_PurchaseRegisterDAO() {
		super("SEC_PurchaseRegister");
	}
	

	
	/**
	 * ���ݽ������ͻ�ȡ������������δ��ǩ,δ����/������깺���ID
	 * */
	public Collection getDeliverOrderIDsForConfirm(long transactionTypeID) throws SecuritiesDAOException{
		ArrayList idList = null;
		try {
			initDAO();
			
			String sql = "SELECT APPLYDELIVERYORDERID FROM "+strTableName
			+ " WHERE APPLYDELIVERYORDERID > 0 AND NVL(CONFIRMDELIVERYORDERID,0) <= 0 AND NVL(DRAWBACKDELIVERYORDERID,0) <=0 AND TRANSACTIONTYPEID = "+transactionTypeID
			+ " AND STATUSID = " + SECConstant.BusinessAttributeStatus.SAVED;
			
			prepareStatement(sql);
			ResultSet rs = executeQuery();

			idList = new ArrayList();
			int i = 0;
			while(rs.next()){
				long doID = rs.getLong("APPLYDELIVERYORDERID");
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
	 * ���ݽ������ͻ�ȡ������ǩ����δ�������ǩ���ID��δ��ǩ��δ����Ľ��ID
	 * */
	public Collection getDeliverOrderIDsForDrawBack(long transactionTypeID,long repayOrSupply) throws SecuritiesDAOException{
		ArrayList idList = null;
		try {
			initDAO();
			
			String sql = "SELECT CONFIRMDELIVERYORDERID AS DOID FROM "+strTableName
			+ " WHERE CONFIRMDELIVERYORDERID > 0 AND NVL(DRAWBACKDELIVERYORDERID,0) <=0 AND TRANSACTIONTYPEID = "+transactionTypeID
			+ " AND STATUSID = " + SECConstant.BusinessAttributeStatus.SAVED;
			
			if(repayOrSupply == SECConstant.BusinessType.TRANS_GROUP_REPAY){
				sql += " AND CONFIRMAMOUNT > 0.0 AND APPLYAMOUNT > CONFIRMAMOUNT";
			}else if(repayOrSupply == SECConstant.BusinessType.TRANSTYPE_GROUP_SUPPLY){
				sql += " AND CONFIRMAMOUNT > 0.0 AND APPLYAMOUNT < CONFIRMAMOUNT";				
			}			
			
			
			sql +=  " UNION "+
			"SELECT APPLYDELIVERYORDERID AS DOID FROM "+strTableName
			+ " WHERE APPLYDELIVERYORDERID > 0 AND NVL(CONFIRMDELIVERYORDERID,0) <= 0 AND NVL(DRAWBACKDELIVERYORDERID,0) <=0 AND TRANSACTIONTYPEID = "+transactionTypeID
			+ " AND STATUSID = " + SECConstant.BusinessAttributeStatus.SAVED;
			
			prepareStatement(sql);
			ResultSet rs = executeQuery();

			idList = new ArrayList();
			int i = 0;
			while(rs.next()){
				long doID = rs.getLong("DOID");
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
	


}
