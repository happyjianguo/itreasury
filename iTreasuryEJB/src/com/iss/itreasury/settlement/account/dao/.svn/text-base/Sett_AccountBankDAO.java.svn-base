/*
 * Created on 2003-10-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.account.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountBankInfo;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_AccountBankDAO extends SettlementDAO
{
	/**
	 * 
	 */
	public Sett_AccountBankDAO()
	{
		super();
	}
	
	/**
	 * Constructor for Sett_AccountBankDAO.
	 * @param conn
	 */
	public Sett_AccountBankDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "SETT_ACCOUNTBANK";
	}
	
	public static void main(String[] args)
	{
	}
	
	public long add(AccountBankInfo abi) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(
				" insert into SETT_ACCOUNTBANK(NACCOUNTID, NBANKID, SBANKACCOUNTNO) values(?,?,?) ");
			ps = conn.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			ps.setLong(nIndex++, abi.getAccountID());
			ps.setLong(nIndex++, abi.getBankID());
			ps.setString(nIndex++, abi.getBankAccountNo());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	public Collection findByAccountID(long lAccountID) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append("select * from SETT_ACCOUNTBANK where naccountid=" + lAccountID);
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				AccountBankInfo abi = new AccountBankInfo();
				abi.setAccountID(rs.getLong("NACCOUNTID"));
				abi.setBankID(rs.getLong("NBANKID"));
				abi.setBankAccountNo(rs.getString("SBANKACCOUNTNO"));
				v.add(abi);
			}
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}
	
	/**
	 * 根据开户行设置id查询所有的关联信息。
	 * @param id 开户行设置id
	 * @return AccountBankInfo[] 关联信息对象数组，可以为null
	 * @throws Exception
	 */
	public AccountBankInfo[] findByBankId(long id)throws Exception
	{
		ArrayList arr = new ArrayList();
		AccountBankInfo[] resultInfos = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = this.getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append("select * from SETT_ACCOUNTBANK where nbankid=" + id);
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				AccountBankInfo abi = new AccountBankInfo();
				abi.setAccountID(rs.getLong("NACCOUNTID"));
				abi.setBankID(rs.getLong("NBANKID"));
				abi.setBankAccountNo(rs.getString("SBANKACCOUNTNO"));
				arr.add(abi);
			}
			
			if(arr.size() > 0)
			{
				resultInfos = (AccountBankInfo[])arr.toArray(new AccountBankInfo[0]);
			}
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return resultInfos;
	}
	
	public long deleteByAccountID(long lAccountID) throws Exception
	{
		long lReturn;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append("delete from SETT_ACCOUNTBANK where naccountid=" + lAccountID);
			ps = conn.prepareStatement(sbSQL.toString());
			lReturn = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}	
}
