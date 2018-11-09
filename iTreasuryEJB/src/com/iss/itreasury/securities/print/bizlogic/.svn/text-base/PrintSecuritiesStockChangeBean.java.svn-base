/*
 * Created on 2004-6-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.bizlogic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesStockChangeResultInfo;
import com.iss.itreasury.securities.print.dataentity.PrintSecuritiesStockChangeWhereInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
/**
 * @author ruixie
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSecuritiesStockChangeBean extends ITreasuryDAO {
	protected Log4j log = new Log4j(Constant.ModuleType.SECURITIES, this);
	/*
	 * 取得某个帐户下、某个证券 期初或者期末的库存余额
	 */
	public double getStockBalance(long lAccountID, long lSecuritiesID,
			Timestamp tsDate) {
		double dReturn = 0.0;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		String strSQL = "";
		strSQL = "select Quantity from SEC_DailyStock where securitiesID = "
				+ lSecuritiesID + " and AccountID = " + lAccountID
				+ " and StockDate = to_date('yyyy-mm-dd','"
				+ DataFormat.formatDate(tsDate) + "')";
		log.info("SQL======" + strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			this.initDAO();
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			if (localRS.next()) {
				dReturn = localRS.getDouble("Quantity");
			}
			if (localRS != null) {
				localRS.close();
				localRS = null;
			}
			if (localPS != null) {
				localPS.close();
				localPS = null;
			}
			this.finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dReturn;
	}
	/*
	 * 取得某个帐户下、某个证券的累计发生额 返回：1、实际增加 2、实际减少
	 */
	public double[] getTotalStockBalance(long lAccountID, long lSecuritiesID,
			Timestamp tsDateStart, Timestamp tsDateEnd) {
		double dReturn[] = new double[2];
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		String strSQL = "";
		strSQL = "select sum(InNumber) InNumber,sum(OutNumber) OutNumber from SEC_DailyStock where securitiesID = "
				+ lSecuritiesID
				+ " and accountId = "
				+ lAccountID
				+ " and StockDate between to_date('yyyy-mm-dd','"
				+ DataFormat.formatDate(tsDateStart)
				+ "') and to_date('yyyy-mm-dd','"
				+ DataFormat.formatDate(tsDateEnd) + "')";
		log.info("SQL======" + strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			this.initDAO();
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			if (localRS.next()) {
				dReturn[0] = localRS.getDouble("InNumber");
				dReturn[1] = localRS.getDouble("OutNumber");
			}
			if (localRS != null) {
				localRS.close();
				localRS = null;
			}
			if (localPS != null) {
				localPS.close();
				localPS = null;
			}
			this.finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dReturn;
	}
	/*
	 * 取得某个帐户下、某个证券的实际成本
	 */
	public double getFactCost(long lAccountID, long lSecuritiesID,
			Timestamp tsDate) {
		double dReturn = 0.0;
		PreparedStatement localPS = null;
		ResultSet localRS = null;
		String strSQL = "";
		strSQL = "select cost from SEC_DailyStock where securitiesID = "
				+ lSecuritiesID + " and accountId = " + lAccountID
				+ " and StockDate = to_date('yyyy-mm-dd','"
				+ DataFormat.formatDate(tsDate) + "')";
		log.info("SQL======" + strSQL);
		try {//内部维护RS、PS、CONN，否则将会产生冲突
			this.initDAO();
			localPS = transConn.prepareStatement(strSQL);
			localRS = localPS.executeQuery();
			if (localRS.next()) {
				dReturn = localRS.getDouble("cost");
			}
			if (localRS != null) {
				localRS.close();
				localRS = null;
			}
			if (localPS != null) {
				localPS.close();
				localPS = null;
			}
			this.finalizeDAO();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return dReturn;
	}
	/*
	 * 证券库存变化表－股票
//	 */
//	public Collection querySecurities(PrintSecuritiesStockChangeWhereInfo psscwi)
//			throws ITreasuryDAOException {
//		ArrayList list = new ArrayList();
//		PreparedStatement localPS = null;
//		ResultSet localRS = null;
////		this.initDAO();
////		String strSQL = "";
////		strSQL = " select Sec_Account.Id AccountID,Sec_Account.Stockholderaccountid1 StockHolderAccountID,Sec_Account.AccountCode,Sec_securities.Id securitiesId \n" 
////			+= " from Sec_securities,Sec_securitiesStock,Sec_Account \n" 
////			+= " where Sec_securities.ID = Sec_securitiesStock.securitiesID and Sec_securitiesStock.AccountID = Sec_Account.ID \n" 
////			+= " and sec_securities.TypeID = 1 \n" 
////			+= " order by AccountID \n";
////		log.info("SQL======" + strSQL);
//		try {//内部维护RS、PS、CONN，否则将会产生冲突
//			localPS = transConn.prepareStatement(strSQL);
//			localRS = localPS.executeQuery();
//			while (localRS.next()) {
//				PrintSecuritiesStockChangeResultInfo info = new PrintSecuritiesStockChangeResultInfo();
//				
//				list.add(info);
//			}
//			if (localRS != null) {
//				localRS.close();
//				localRS = null;
//			}
//			if (localPS != null) {
//				localPS.close();
//				localPS = null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		}
//		this.finalizeDAO();
//		return list;
//	}
}
