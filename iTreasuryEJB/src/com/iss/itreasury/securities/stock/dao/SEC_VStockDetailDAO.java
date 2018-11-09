/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-3-31
 */
package com.iss.itreasury.securities.stock.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.deliveryorder.dao.SEC_DeliveryOrderDAO;
import com.iss.itreasury.securities.deliveryorder.dataentity.DeliveryOrderConditionInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.stock.dataentity.DailyStockInfo;
import com.iss.itreasury.securities.stock.dataentity.VStockDetailInfo;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.securities.util.SECConstant;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SEC_VStockDetailDAO extends SecuritiesDAO {
	public SEC_VStockDetailDAO(){
		super("SEC_VStockDetail");
	}
	
	/**
	*
	*/
	public DailyStockInfo sumStockDetails(long clientID,long accountID,long securitiesID,Timestamp deliveryDate, long direction) throws SecuritiesDAOException{
		DailyStockInfo res = new DailyStockInfo(); 
		try {
			initDAO();
			
			String strSQL = "select count(*) as Numbers, sum(Quantity) as Quantities, sum(NetIncome) as NetIcomes, sum(NetPriceAmount) as NetPriceAmounts from "+ strTableName +" where ClientID =" + clientID
							+ " and accountID = " + accountID + " and securitiesID = " +securitiesID + " and direction = " + direction
							+ " and deliveryDate = ?";
							
			PreparedStatement localPS = prepareStatement(strSQL);
			try {
				deliveryDate.setNanos(0);
				localPS.setTimestamp(1, deliveryDate);
	
			ResultSet localRS = executeQuery();
			
			if(localRS.next()){
				if(direction == SECConstant.StockDirection.IN){
					res.setInNumber(localRS.getLong("Numbers"));
					res.setInAmount(localRS.getDouble("NetIcomes"));
					res.setInNetAmount(localRS.getDouble("NetPriceAmounts"));
					res.setInQuantity(localRS.getDouble("Quantities"));
				}else{
					res.setOutNumber(localRS.getLong("Numbers"));
					res.setOutAmount(localRS.getDouble("NetIcomes"));
					res.setOutNetAmount(localRS.getDouble("NetPriceAmounts"));
					res.setOutQuantity(localRS.getDouble("Quantities"));			
				}
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
	
	public Collection getDeliveryOrderInfosByCondition(long clientID,long accountID,long securitiesID,Timestamp deliveryDate) throws SecuritiesDAOException{
	try {	
		initDAO();
		long [] ids;
		String strSQL = "SELECT * FROM "+strTableName+"  where ClientID =" + clientID
							+ " and accountID = " + accountID + " and securitiesID = " +securitiesID + " and deliveryDate = ? order by deliveryOrderID";
			try {
				PreparedStatement localPS = prepareStatement(strSQL);			
				localPS.setTimestamp(1, deliveryDate);
	
				ResultSet localRS = executeQuery();
				ArrayList list = new ArrayList();
			Collection res = getDataEntitiesFromResultSet(VStockDetailInfo.class);		
			finalizeDAO();

			return res;
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);
			}	

		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}		
		
	}
	
	/**
	 * 计算本日出库成本（全价）
	 * 	本日出库成本（净价）
	 * @return double[0] = 本日出库成本（全价）	
	 * @return double[1] = 本日出库成本（净价）
	 * */
	public double[] sumTodayStockCost(long clientID,long accountID,long securitiesID,Timestamp transationDate) throws SecuritiesDAOException{
		double[] res = new double[2]; 
		try {	
			initDAO();
			long [] ids;
			String strSQL = "SELECT sum(UnitCost* Quantity) as OutCost,sum(UnitNetCost * Quantity) as NetOutCost FROM "+strTableName+"  where ClientID =" + clientID
								+ " and accountID = " + accountID + " and securitiesID = " +securitiesID + " and direction = " + SECConstant.StockDirection.OUT
								+ " and deliveryDate = ?";
				try {
					PreparedStatement localPS = prepareStatement(strSQL);			
					localPS.setTimestamp(1, transationDate);
		
					ResultSet localRS = executeQuery();
					ArrayList list = new ArrayList();
					if(localRS.next()){
						res[0] = localRS.getLong("OutCost");
						res[1] = localRS.getLong("NetOutCost");
					}
					finalizeDAO();

				return res;
				} catch (SQLException e1) {
					throw new ITreasuryDAOException("",e1);
				}	

			} catch (ITreasuryDAOException e) {
				throw new SecuritiesDAOException(e);
			}		
					
	}
	
	/**
	 * 本日冻结库存数量
	 * */
	
	public double sumTodayFrozenStock(long clientID,long accountID,long securitiesID,Timestamp transationDate)  throws SecuritiesDAOException{
		double frozenstock = 0.0;
		try {
			initDAO();
			String strSQL = "select sum(decode(t.FrozenProcess,1,+ PledgeSecuritiesQuantity,2, -PledgeSecuritiesQuantity)) as frozenstock from sec_deliveryorder d ,sec_transactiontype t " +
					"where d.statusid>0 and d.transactiontypeid=t.id and ClientID = "+ clientID + " and accountid = "+accountID + " and PledgeSecuritiesID = " + securitiesID
					+ "  and DeliveryDate = ? and ( t.FrozenProcess = " + SECConstant.FrozenProcess.FREEZE +" or t.FrozenProcess = " + SECConstant.FrozenProcess.CANCEL_FREEZE +" )";
			PreparedStatement localPS = prepareStatement(strSQL);
			try {
				System.out.println("-----------查询日期为 "+transationDate);
				transationDate.setNanos(0);
				localPS.setTimestamp(1, transationDate);
			} catch (SQLException e2) {
				throw new ITreasuryDAOException("",e2);
			}
			ResultSet localRS = executeQuery();
			try {
				if(localRS.next()){
					frozenstock = localRS.getLong("frozenstock");						
				}
			} catch (SQLException e1) {
				throw new ITreasuryDAOException("",e1);
			}
			finalizeDAO();			
		} catch (ITreasuryDAOException e) {
			throw new SecuritiesDAOException(e);
		}
		return frozenstock;

	}
}
