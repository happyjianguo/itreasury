/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-19
 */

package com.iss.itreasury.treasuryplan.etl.extract.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ForecastBalanceInfo;
import com.iss.itreasury.treasuryplan.etl.transform.bizlogic.AbstractTransformer;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateItemInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public abstract class AbstractBalanceDAO extends TreasuryPlanDAO
{

	public AbstractBalanceDAO(String strTableName, Connection conn)
	{

		super(strTableName, conn);
		super.setUseMaxID();
	}


	public AbstractBalanceDAO(String strTableName)
	{

		super(strTableName);
		super.setUseMaxID();
	}

	public String	balanceFieldName	= "";


	/**
	 * 根据一条取数逻辑，去Trea_ActualBalance / Trea_ForecastBalance中，计算一条取数逻辑某一天的余额
	 * 
	 * @param officeID
	 * @param currencyID
	 * @param forcastDate
	 * @param templateItemInfo
	 * @return
	 * @throws Exception
	 */
	public double sumBalanceDependOnTemplateItem(long officeID, long currencyID/*
																				 * ,
																				 * Timestamp
																				 * currentDate
																				 */, Timestamp forcastDate, TPTemplateItemInfo templateItemInfo) throws Exception
	{

		double amount = 0.0;
		String strForcastDate = transferTimestampToTo_DateString(forcastDate);
		StringBuffer bufferSQL = new StringBuffer();
		bufferSQL.append("Select sum(" + balanceFieldName + ")as " + balanceFieldName + " from " + strTableName + " where \n ");
		bufferSQL.append(strTableName + ".Officeid= " + officeID + " and " + strTableName + ".Currencyid= " + currencyID + " \n ");
		bufferSQL.append(" and " + strTableName + ".TransactionDate = " + strForcastDate);
		if (templateItemInfo.getModuleID() > 0)
			bufferSQL.append(" and  " + strTableName + ".ModuleID = " + templateItemInfo.getModuleID());
		if (templateItemInfo.getClientCode() != null)
			bufferSQL.append(" and  " + strTableName + ".ClientCode = '" + templateItemInfo.getClientCode() + "'");
		if (templateItemInfo.getAccountCode() != null)
			bufferSQL.append(" and  " + strTableName + ".AccountCode = '" + templateItemInfo.getAccountCode() + "'");
		if (templateItemInfo.getContractCode() != null)
			bufferSQL.append(" and  " + strTableName + ".ContractCode = '" + templateItemInfo.getContractCode() + "'");
		if (templateItemInfo.getPayFormCode() != null)
			bufferSQL.append(" and  " + strTableName + ".PayFormCode = '" + templateItemInfo.getPayFormCode() + "'");
		if (templateItemInfo.getCounterpartName() != null)
			bufferSQL.append(" and  " + strTableName + ".CounterpartName = '" + templateItemInfo.getCounterpartName() + "'");
		if (templateItemInfo.getSecuritiesName() != null)
			bufferSQL.append(" and  " + strTableName + ".SecuritiesName = '" + templateItemInfo.getSecuritiesName() + "'");
		if (templateItemInfo.getAccountTypeId() > 0)
			bufferSQL.append(" and  " + strTableName + ".AccountTypeId = " + templateItemInfo.getAccountTypeId());
		if (templateItemInfo.getGlSubjectCode() != null)
			bufferSQL.append(" and  " + strTableName + ".GlSubjectCode = '" + templateItemInfo.getGlSubjectCode() + "'");

		try {
			System.out.println("-----SQL:" + bufferSQL.toString());
			PreparedStatement localPS = prepareStatement(bufferSQL.toString());
			// localPS.setTimestamp(1, currentDate);
			// localPS.setTimestamp(2, forcastDate);
			// System.out.println("---------currentDate"+currentDate);
			System.out.println("---------forcastDate" + forcastDate);
			ResultSet localRS = executeQuery();
			if (localRS.next()) {
				amount = localRS.getDouble(balanceFieldName);
				System.out.println("---------amount" + amount);
			}
			System.out.println("---------before finalizeDAO");
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		System.out.println("--------after finalizeDAO");

		return amount;
	}


	/**
	 * 根据正在执行的抽取操作的类别获取应该使用的DAO的实例
	 */
	public static AbstractBalanceDAO getBalanceDAOInstance(long extractingTypeID, Connection conn)
	{

		AbstractBalanceDAO balanceDAO = null;
		if (extractingTypeID == AbstractTransformer.Extracting_Type_Actual) {
			balanceDAO = new Trea_ActualBalanceDAO(conn);
		}
		else if (extractingTypeID == AbstractTransformer.Extracting_Type_Forecast) {
			balanceDAO = new Trea_ForecastBalanceDAO(conn);
		}
		else {
		}
		return balanceDAO;
	}


	public void deleteByTransactionDateAndModuleID(Timestamp startDate, Timestamp endDate, long moduleID, long officeID, long currencyID) throws Exception
	{

		try {
			String strStartDate = this.transferTimestampToTo_DateString(startDate);
			String strEndDate = this.transferTimestampToTo_DateString(endDate);
			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append("delete from ");
			bufferSQL.append(strTableName);
			bufferSQL.append(" where TRANSACTIONDATE >= ");
			bufferSQL.append(" to_date('" + DataFormat.formatDate(startDate) + "','yyyy-mm-dd') ");
			bufferSQL.append(" and  TRANSACTIONDATE <= ");
			bufferSQL.append(" to_date('" + DataFormat.formatDate(endDate) + "','yyyy-mm-dd') ");
			bufferSQL.append(" and MODULEID = " + moduleID);
			bufferSQL.append(" and ");
			bufferSQL.append(" officeID = " + officeID);
			bufferSQL.append(" and ");
			bufferSQL.append(" currencyID = " + currencyID);
			this.prepareStatement(bufferSQL.toString());
			this.executeQuery();
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

	}


	public long countLastTransactionDateRecordByModuleID(long moduleID, Timestamp transDate, long officeID, long currencyID) throws Exception
	{

		long num;
		try 
		{
			//startDate = DataFormat.getNextDate(startDate, -1);
			String strTransDate = this.transferTimestampToTo_DateString(transDate);

			StringBuffer bufferSQL = new StringBuffer();
			bufferSQL.append(" select count(*)num From ");
			bufferSQL.append(strTableName);
			bufferSQL.append(" where transactiondate = ");
			bufferSQL.append(strTransDate);
			//bufferSQL.append(" and transactiondate  <= ");
			//bufferSQL.append(strEndDate);
			bufferSQL.append(" and moduleid = ");
			bufferSQL.append(moduleID);
			bufferSQL.append(" and officeID = ");
			bufferSQL.append(officeID);
			bufferSQL.append(" and currencyID = ");
			bufferSQL.append(currencyID);
			prepareStatement(bufferSQL.toString());
			ResultSet localRS = executeQuery();
			num = 0;
			if (localRS.next())
				num = localRS.getLong("num");
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return num;
	}


	/**
	 * 余额预测
	 */
	public Collection caculateForecastBalance(Timestamp transDate,long moduleID, long officeID, long currencyID) throws Exception
	{

		String strTransDate = this.transferTimestampToTo_DateString(transDate);
		//String strEndDate = this.transferTimestampToTo_DateString(endDate);
		ArrayList list = new ArrayList();
		StringBuffer bufferSQL = new StringBuffer();
		bufferSQL.append(" select a.ACCOUNTTYPEID,a.CLIENTCODE,a.ACCOUNTCODE ,a." + balanceFieldName + "+nvl(b.amount,0) " + balanceFieldName);
		bufferSQL.append(" from (select moduleid,OFFICEID,CURRENCYID,ACCOUNTTYPEID,CLIENTCODE,ACCOUNTCODE, sum(" + balanceFieldName + ") " + balanceFieldName);
		bufferSQL.append(" from " + strTableName);
		bufferSQL.append(" 	where transactiondate = " + strTransDate + " -1");
		//bufferSQL.append("  and  transactiondate <= " + strEndDate + " -1");
		bufferSQL.append(" and moduleid = " + moduleID);
		bufferSQL.append(" and OFFICEID = " + officeID);
		bufferSQL.append(" and currencyID = " + currencyID);
		bufferSQL.append(" group by moduleid,OFFICEID,CURRENCYID,ACCOUNTTYPEID,CLIENTCODE,ACCOUNTCODE ) a ,");
		bufferSQL.append(" (select moduleid,OFFICEID,CURRENCYID,ACCOUNTTYPEID,CLIENTCODE,ACCOUNTCODE , ");
		bufferSQL.append(" sum(decode(AMOUNTDIRECTION,1,FORECASTAMOUNT,2,-FORECASTAMOUNT))  amount ");
		bufferSQL.append(" from TREA_FORECASTAMOUNT where transactiondate = ");
		bufferSQL.append(strTransDate);
		//bufferSQL.append(" and  transactiondate <= ");
		//bufferSQL.append(strEndDate);
		bufferSQL.append(" and moduleid = " + moduleID);
		bufferSQL.append(" and OFFICEID = " + officeID);
		bufferSQL.append(" and currencyID = " + currencyID);
		bufferSQL.append(" group by moduleid,OFFICEID,CURRENCYID,ACCOUNTTYPEID,CLIENTCODE,ACCOUNTCODE) b ");
		bufferSQL.append(" where  a.OFFICEID=b.OFFICEID(+) and a.CURRENCYID=b.CURRENCYID(+) and a.moduleid = b.moduleid(+) and a.ACCOUNTTYPEID=b.ACCOUNTTYPEID(+) and a.CLIENTCODE=b.CLIENTCODE(+) and a.ACCOUNTCODE=b.ACCOUNTCODE(+) ");

		try {
			prepareStatement(bufferSQL.toString());
			ResultSet localRS = executeQuery();
			long num = 0;
			while (localRS.next()) {
				ForecastBalanceInfo forecastBalanceInfo = new ForecastBalanceInfo();
				forecastBalanceInfo.setAccountCode(localRS.getString("ACCOUNTCODE"));
				forecastBalanceInfo.setAccountTypeId(localRS.getLong("ACCOUNTTYPEID"));
				forecastBalanceInfo.setClientCode(localRS.getString("CLIENTCODE"));
				forecastBalanceInfo.setCurrencyID(currencyID);
				forecastBalanceInfo.setExecuteDate(Env.getSystemDate());
				forecastBalanceInfo.setForecastBalance(localRS.getDouble(balanceFieldName));
				forecastBalanceInfo.setModuleID(moduleID);
				forecastBalanceInfo.setOfficeID(officeID);
				forecastBalanceInfo.setTransactionDate(transDate);
				list.add(forecastBalanceInfo);
			}
		}
		catch (ITreasuryDAOException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			finalizeDAO();
		}

		return list;
	}
}
