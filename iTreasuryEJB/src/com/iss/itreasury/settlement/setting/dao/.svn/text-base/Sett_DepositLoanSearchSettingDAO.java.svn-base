/*
 * Created on 2003-12-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import javax.transaction.SystemException;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.DepositLoanSearchSettingInfo;
import com.iss.itreasury.util.Log;
/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Sett_DepositLoanSearchSettingDAO extends SettlementDAO
{
	public Sett_DepositLoanSearchSettingDAO()
	{
		super("sett_DEPOSITLOANSEARCHSETTING");
		setUseMaxID();
	}
	//查询记录
	public Collection query(long lCurrencyID,long lOfficeID) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = this.getConnection();

			//先判断是否重复
			//modify by 何小文 2007-03-27
			String strSQL = " select * from sett_DEPOSITLOANSEARCHSETTING where  nCurrencyID=" + lCurrencyID +" and nOfficeID=" + lOfficeID + " order by ID";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				
				DepositLoanSearchSettingInfo dlsi = new DepositLoanSearchSettingInfo();
				dlsi.setID(rs.getLong("ID"));
				dlsi.setCurrencyID(rs.getLong("nCurrencyID"));
				dlsi.setName(rs.getString("sName"));
				dlsi.setAccountTypeID(rs.getString("sAccountTypeID"));
				
				v.add(dlsi);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return v;
	}
	public String queryName(String strAccountTypeID) throws Exception
	{
		String strReturn = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = this.getConnection();
			
			String strSQL = " select sAccountType from sett_AccountType where ID in ("+strAccountTypeID+") order by ID";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				strReturn = strReturn + rs.getString("sAccountType") + ",";
			}
			Log.print("strAccountTypeName="+strReturn);
			if(strReturn != null && strReturn.length() > 0)
			{
				strReturn = strReturn.substring(0,strReturn.length()-1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return strReturn;
	}
	public DepositLoanSearchSettingInfo findByID(long lID) throws Exception
	{
		DepositLoanSearchSettingInfo returnInfo = new DepositLoanSearchSettingInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = this.getConnection();

			String strSQL = " select * from sett_DEPOSITLOANSEARCHSETTING where ID=" + lID;
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				returnInfo.setID(rs.getLong("ID"));
				returnInfo.setCurrencyID(rs.getLong("nCurrencyID"));
				returnInfo.setName(rs.getString("sName"));
				returnInfo.setAccountTypeID(rs.getString("sAccountTypeID"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			String strAccountTypeID = "";
			String strAccountTypeName = "";
			if(returnInfo.getAccountTypeID()!=null&&!returnInfo.getAccountTypeID().equals(""))
			{
				strSQL = " select ID,sAccountType from sett_AccountType where ID in ("+returnInfo.getAccountTypeID()+")";
				
				Log.print(strSQL);
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				while (rs.next())
				{
					strAccountTypeID = strAccountTypeID + rs.getLong("ID") + ",";
					strAccountTypeName = strAccountTypeName + rs.getString("sAccountType") + ",";
				}
				Log.print("strAccountTypeID="+strAccountTypeID);
				Log.print("strAccountTypeName="+strAccountTypeName);
				if(strAccountTypeID != null && strAccountTypeID.length() > 0)
				{
					strAccountTypeID = strAccountTypeID.substring(0,strAccountTypeID.length()-1);
				}
				if(strAccountTypeName != null && strAccountTypeName.length() > 0)
				{
					strAccountTypeName = strAccountTypeName.substring(0,strAccountTypeName.length()-1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			returnInfo.setAccountTypeID(strAccountTypeID);
			returnInfo.setAccountTypeName(strAccountTypeName);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new SystemException(e.getMessage());
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return returnInfo;
	}
	public long update(DepositLoanSearchSettingInfo dlsi) throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = this.getConnection();

			//先判断是否重复
			String strSQL = " update sett_DEPOSITLOANSEARCHSETTING set SACCOUNTTYPEID='"+dlsi.getAccountTypeID()+"' where ID = "+dlsi.getID();
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = dlsi.getID();
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}

		return lResult;
	}
}
