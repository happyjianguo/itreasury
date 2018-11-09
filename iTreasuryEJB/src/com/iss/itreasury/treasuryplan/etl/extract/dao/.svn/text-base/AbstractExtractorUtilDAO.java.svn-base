/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-12
 */

package com.iss.itreasury.treasuryplan.etl.extract.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett.OfficeTimeInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class AbstractExtractorUtilDAO extends TreasuryPlanDAO
{

	protected Log4j	log	= new Log4j(Constant.ModuleType.PLAN, this);


	public AbstractExtractorUtilDAO(long moduleID) throws Exception
	{

		super.establishConnectionByModuleID(moduleID);
		setSelfManagedConn(true);
	}


	/**
	 * 
	 */
	public Collection findAllOfficeTime() throws Exception
	{

		Collection c;
		try
		{
			String strSQL = "select * from sett_officetime where nstatusid = 1 and ncurrencyid=1";

			prepareStatement(strSQL);
			executeQuery();
			c = getDataEntitiesFromResultSet(OfficeTimeInfo.class);
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return c;

	}
	public Collection findAllOfficeTimeByID(long currencyid,long officeid) throws Exception
	{

		Collection c;
		try
		{
			String strSQL = "select * from sett_officetime where nstatusid = 1";
			strSQL=strSQL+" and nofficeid="+officeid;
			strSQL=strSQL+" and ncurrencyid="+currencyid;
			prepareStatement(strSQL);
			executeQuery();
			c = getDataEntitiesFromResultSet(OfficeTimeInfo.class);
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return c;

	}


	public Timestamp getOfficeOpenDate(long officeID, long currencyId) throws Exception
	{

		String strSQL = "select dtopendate from sett_officetime where nofficeid = " + officeID + " and ncurrencyid = " + currencyId;
		Timestamp openDate = null;
		try 
		{
			prepareStatement(strSQL);
			ResultSet localRS = executeQuery();

			if (localRS.next())
			{
				openDate = localRS.getTimestamp("dtopendate");
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return openDate;

	}


	public void closeModuleConn() throws Exception
	{

		if (transConn != null)
			transConn.close();
	}

	protected static ArrayList	settlemtInterestDates	= new ArrayList();


	/**
	 * 判断是否是结息日
	 */
	public boolean isSettlemtInterestDate(Timestamp date) throws Exception
	{

		if (settlemtInterestDates.size() != 0) 
		{
			return settlemtInterestDates.contains(date);
		}

		boolean res;
		try 
		{
			String strSQL = " select DtCOMPOUNDINTEREST from SETT_COMPOUNDINTERESTSETTING  where nStatusId = " + Constant.RecordStatus.VALID;
			res = false;
			prepareStatement(strSQL);
			ResultSet localRs = executeQuery();
			while (localRs != null && localRs.next()) 
			{
				Timestamp settlemtDate = localRs.getTimestamp("DtCOMPOUNDINTEREST");
				settlemtInterestDates.add(settlemtDate);
				if (settlemtDate.equals(date)) 
				{
					res = true;
					break;
				}
			}
		}
		catch (ITreasuryDAOException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally 
		{
			finalizeDAO();
		}

		return res;
	}

}
