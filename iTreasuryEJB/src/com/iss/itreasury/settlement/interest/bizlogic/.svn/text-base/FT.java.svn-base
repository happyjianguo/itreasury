/*
 * Created on 2003-12-19
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.interest.bizlogic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.iss.itreasury.dao.SettlementDAO;
import java.io.*;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;

/**
 * @author hjliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FT extends SettlementDAO
{

	public static void main(String[] args)
	{

		long lOfficeID = 1;
		long lCurrencyID = 1;
		//Timestamp startDate = DataFormat.getDateTime("2003-12-21");
		Timestamp startDate = DataFormat.getDateTime("2004-6-22");
		
		long lSubAccountID = 0;
		PreparedStatement ps = null;
		ResultSet rset = null;

		try
		{
			Timestamp endDate = getEndDate();
			Connection conn = Database.getConnection();
			InterestFaultTolerance interestFT = new InterestFaultTolerance(conn);
			//select t.nsubaccountid from TMP_CONSIGNLOANINTEREST t where t.sremark2 is null or sloannoteno in ('90037','W2000-09','W99052','W99053','hnl9402');
			//String sqlString = " select t.nsubaccountid from TMP_CONSIGNLOANINTEREST t where t.sremark2 is null ";
			//sqlString += "  or sloannoteno in ('90037','W2000-09','W99052','W99053','hnl9402')";
			//String sqlString = "select t.nsubaccountid from TMP_CONSIGNLOANINTEREST t order by nsubaccountid ";
			//String sqlString = "select ID from sett_subaccount where id in (318,349,355,366,368,424,425,354,389,408,409,372,352) order by id";
			String sqlString = "select ID from sett_subaccount where id in (205,206,207) order by id";
			
			//String sqlString = "select * from sett_SubAccount where Id = ";
			ps = conn.prepareStatement(sqlString);
			//ps.setTimestamp(1, closeDate);
			rset = ps.executeQuery();
			int i = 0;
			while (rset.next())
			{
				i++;
				lSubAccountID = rset.getLong("ID");

				interestFT.ReCalculateInterestPatch(lOfficeID, lCurrencyID, startDate, endDate, lSubAccountID);
				System.out.print("修改完第 " + i + "条子账户[" + lSubAccountID + "] 从" + startDate + "到" + endDate + "的账户余额表");
			}

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Timestamp getEndDate() throws Exception
	{
		Timestamp endDate = null;
		String openStatus ="";
		PreparedStatement ps = null;
		ResultSet rset = null;
		try
		{

			Connection conn = Database.getConnection();
			InterestFaultTolerance interestFT = new InterestFaultTolerance(conn);
			String sqlString = " select * from sett_officeTime where ncurrencyID = 1";
			ps = conn.prepareStatement(sqlString);
			//ps.setTimestamp(1, closeDate);
			rset = ps.executeQuery();

			while (rset.next())
			{
			    openStatus = rset.getString("SSYSTEMSTATUSDESC");
				endDate = rset.getTimestamp("DTOPENDATE");
				if (openStatus != null && !openStatus.equals("Closed"))
				{
					endDate = UtilOperation.getNextNDay(endDate, -1);
				}

			}
			if (rset != null)
			{
				rset.close();
				rset = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(); 
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}

			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
				throw new Exception();

			}

		}
        System.out.println("当前系统开机状态是："+ openStatus + " 所以系统日期是："+ endDate);
		return endDate;
	}
}
