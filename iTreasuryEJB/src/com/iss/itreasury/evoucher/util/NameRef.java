/*
 * Created on 2003-9-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.evoucher.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.evoucher.setting.dataentity.PrintBillrelationInfo;
import com.iss.itreasury.util.*;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class NameRef {
	private static HashMap hmClientName = new HashMap();

	private static HashMap hmClientCode = new HashMap();

	private static HashMap hmUserName = new HashMap();

	//
	private static HashMap hmAccountNo = new HashMap();

	private static HashMap hmAccountName = new HashMap();

	private static HashMap hmClientIDByAccountID = new HashMap();

	private static HashMap hmClientCodeByAccountID = new HashMap();

	private static HashMap hmClientNameByAccountID = new HashMap();

	private static HashMap hmBankNoByID = new HashMap();

	private static HashMap hmBankNameByID = new HashMap();

	// private static HashMap hmBankAccountCodeByID = new HashMap();

	// private static HashMap hmExtAccountNoByID = new HashMap();
	// private static HashMap hmExtClientNameByID = new HashMap();
	// private static HashMap hmExtClientProvinceByID = new HashMap();
	// private static HashMap hmExtClientCityByID = new HashMap();
	// private static HashMap hmExtClientBankNameByID = new HashMap();

	private static HashMap hmCashFlowNameByID = new HashMap();

	private static HashMap hmEnterpriceName = new HashMap();

	
	/**
	 * 
	 * @param lAccountID
	 * @return
	 * @
	 */
	public static long getSetBoolean(PrintBillrelationInfo info)
	{
		long strReturn = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL = "select id from print_billrelation where 1=1 " 
							+ " and nofficeid = " + info.getNofficeid()
							+ " and ncurrency = " + info.getNcurrency()
							+ " and ntransactiontypeid = " + info.getNtransactiontypeid()
							+ " and ndeptid = " + info.getNdeptid()
							+ " and stempname = '" + info.getStempName() + "'";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				strReturn = 1;
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		return strReturn;
	}
	
	
	/**
	 * 
	 * @param lAccountID
	 * @return
	 * @
	 */
	public static long getSetBooleanmany(PrintBillrelationInfo info)
	{
		long strReturn = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL = "select id from print_manybillrelation where 1=1 " 
							+ " and nofficeid = " + info.getNofficeid()
							+ " and ncurrency = " + info.getNcurrency()
							+ " and ntransactiontypeid = " + info.getNtransactiontypeid()
							+ " and ndeptid = " + info.getNdeptid()
							+ " and stempname = '" + info.getStempName() + "'";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				strReturn = 1;
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		return strReturn;
	}
	
	

	/**
	 * 
	 * @param lAccountID
	 * @return
	 * @
	 */
	public static String getBillTypeNameByInfo(PrintBillrelationInfo info)
	{
		String strReturn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL = "select sbilltypeName from print_billrelation where 1=1 " 
							+ " and nofficeid = " + info.getNofficeid()
							+ " and ncurrency = " + info.getNcurrency()
							+ " and ntransactiontypeid = " + info.getNtransactiontypeid()
							+ " and ndeptid = " + info.getNdeptid()
							+ " and stempname = '" + info.getStempName() + "'";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				strReturn = rs.getString("sbilltypeName");
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		return strReturn;
	}

	/**
	 * 
	 * @param lAccountID
	 * @return
	 * @
	 */
	public static long getMaxPrintByInfo(PrintBillrelationInfo info)
	{
		long strReturn = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL = "select nmaxprint from print_billrelation where 1=1 " 
							+ " and nofficeid = " + info.getNofficeid()
							+ " and ncurrency = " + info.getNcurrency()
							+ " and ntransactiontypeid = " + info.getNtransactiontypeid()
							+ " and ndeptid = " + info.getNdeptid()
							+ " and stempname = '" + info.getStempName() + "'";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				strReturn = rs.getLong("nmaxprint");
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		return strReturn;
	}
	
	/**
	 * @param info 
	 * @return infoRet include new nmaxprint,sbilltypeName
	 * 
	 */
	public static PrintBillrelationInfo getPartByInfo(PrintBillrelationInfo info)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		PrintBillrelationInfo infoRet = new PrintBillrelationInfo();
		infoRet.setStempName(info.getStempName());
		try 
		{
			con = Database.getConnection();
			String strSQL = "select nmaxprint,sbilltypeName,nissignature from print_billrelation where 1=1 " 
							+ " and nofficeid = " + info.getNofficeid()
							+ " and ncurrency = " + info.getNcurrency()
							+ " and ntransactiontypeid = " + info.getNtransactiontypeid()
							+ " and ndeptid = " + info.getNdeptid()
							+ " and stempname = '" + info.getStempName() + "'";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				infoRet.setNmaxprint(rs.getLong("nmaxprint"));
				infoRet.setSbilltypeName(rs.getString("sbilltypeName"));
				infoRet.setNissignature(rs.getLong("nissignature"));
			}
			else
			{
				infoRet.setNmaxprint(-1);
				infoRet.setSbilltypeName("");
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				;
			}
		}
		
		return infoRet;
	}
	
	// 后台主测试函数
	public static void main(String[] args) {
		System.out.println("=========================开始==============!");
		//System.out.println(" 结果为:" + NameRef.getBankFactAccountNO(179));
		//System.out.println(" 结果为:" + NameRef.getOpenBankName(179));
		System.out.println("=========================结束==============!");

		// System.out.println(NameRef.getExtAccountNoByID(1));
		// System.out.println(NameRef.getAccountNoByID(1));
		// System.out.println(NameRef.getCashFlowNameByID(1));
		// System.out.println(NameRef.getAccountTypeByID(484));
	}
	
}
