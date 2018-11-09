package com.iss.itreasury.securities.securitiesaccount.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-02
 */


public class SEC_AccountDAO extends SecuritiesDAO {


	private Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
	/**金额类型*/
	public final static int Account_AmountType_InitBalance 	  = 0;
	/**金额类型*/
	public final static int Account_AmountType_InitNetBankroll = 1;
	/**金额类型*/
	public final static int Account_AmountType_Balance         = 2;
	/**金额类型*/
	public final static int Account_AmountType_SuspenseReceive = 3;
	/**金额类型*/
	public final static int Account_AmountType_SuspensePay     = 4;

	

	public SEC_AccountDAO(){
		super("SEC_Account");
	}





	/**
 	* 
	* @param id 资金账户id
	* @param addedAmount 需要增加的金额
	* @return　amountType　金额类型
	* @throws
 	*/
	public void updateAmount(long id, double addedAmount,int amountType) throws ITreasuryDAOException{
		initDAO();
		StringBuffer bufferSQL = new StringBuffer();
		bufferSQL.append("UPDATE "+strTableName + " SET ");
		switch(amountType){
			case Account_AmountType_InitBalance:{
				bufferSQL.append("INITBALANCE = INITBALANCE+? ");
			}
			break;
			case Account_AmountType_InitNetBankroll:{
				bufferSQL.append("INITNETBANKROLL = INITNETBANKROLL+? ");				
			}
			break;
			case Account_AmountType_Balance:{
				bufferSQL.append("BALANCE = BALANCE+? ");				
			}
			break;
			case Account_AmountType_SuspenseReceive:{
				bufferSQL.append("SUSPENSERECEIVE = SUSPENSERECEIVE+? ");				
			}
			break;
			case Account_AmountType_SuspensePay:{
				bufferSQL.append("SUSPENSEPAY = SUSPENSEPAY+? ");				
			}
			break;
		}
		bufferSQL.append("WHERE ID = ?");
		String strSQL = bufferSQL.toString();
		log.debug(strSQL);
		PreparedStatement ps = prepareStatement(strSQL);
		try {
			ps.setDouble(1, addedAmount);
			ps.setLong(2, id);			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("",e);
		}
		executeUpdate();
		
		finalizeDAO();	
	}	
	
	public Collection getAllIDs() throws SecuritiesDAOException{
		try {
			initDAO();
			String strSQL = "select id from "+strTableName+" where statusid > 0";
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();
			ArrayList idList = new ArrayList();
			try {
				while(localRS.next()){
					Long ID = new Long(localRS.getLong("id"));
					idList.add(ID);
				}
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);
			}
			finalizeDAO();
			return idList;
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
	}
}
