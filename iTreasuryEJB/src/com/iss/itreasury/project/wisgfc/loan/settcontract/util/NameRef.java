package com.iss.itreasury.project.wisgfc.loan.settcontract.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.util.Database;

public class NameRef {
	public static long getLoanSubTypeIdByCode(String code,long officeid,long currenyid){
		long lrtn = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL  = " select id from Loan_LoanTypeSetting a where a.statusid >0 ";
				   strSQL += " and a.code = ? and a.currencyid =? and a.officeid=?";
			ps = con.prepareStatement(strSQL);
			ps.setString(1, code);
			ps.setLong(2, currenyid);
			ps.setLong(3, officeid);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				lrtn = rs.getLong("id");
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
				System.out.println(e.toString());
			}
		}
		return lrtn;
	}
	
	public static long getClientIdByClientCode(String code,long officeid){
		long lrtn = -1;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL  = " select id from client a where a.nStatusID >0 ";
				   strSQL += " and a.sCode = ? and a.nOfficeID =?";
			ps = con.prepareStatement(strSQL);
			ps.setString(1, code);
			ps.setLong(2, officeid);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				lrtn = rs.getLong("id");
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
				System.out.println(e.toString());
			}
		}
		return lrtn;
	}
	public static String getMaxNumForexportTrustCollection(String code){
		String lrtn = "";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try 
		{
			con = Database.getConnection();
			String strSQL  = " select nvl(max(to_number(substr(batchentity,9))),0)+1 id from sett_transtrustcollectiontemp a ";
				   strSQL += " where a.BATCHENTITY like  '"+code+"%'";
			ps = con.prepareStatement(strSQL);
			System.out.print(strSQL);
			rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				lrtn = rs.getString("id");
			}else{
				lrtn="1";
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
				System.out.println(e.toString());
			}
		}
		return lrtn;
	}
}
