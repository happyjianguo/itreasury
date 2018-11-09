package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.AccountRelationsSettingInfo;
import com.iss.itreasury.settlement.util.NameRef;

public class Sett_AccountRelationsSettingDAO extends SettlementDAO {

	/**
	 * 
	 */
	public Sett_AccountRelationsSettingDAO()
	{
		super();
	}
	
	/**
	 * Constructor for Sett_AccountBankDAO.
	 * @param conn
	 */
	public Sett_AccountRelationsSettingDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "Sett_AccountRelationsSetting";
	}

	public long add(AccountRelationsSettingInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
//			get the connection from Database
			conn =  this.getConnection();
			lReturn = this.getMaxID();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(
				" insert into Sett_AccountRelationsSetting(ID,ACCOUNTID, ACCOUNTNO, ORIGINALACCOUNTNO) values(?,?,?,?) ");
			ps = conn.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			ps.setLong(nIndex++, lReturn);
			ps.setLong(nIndex++, info.getAccountID());
			ps.setString(nIndex++, info.getAccountNo());
			ps.setString(nIndex++, info.getOriginalAccountNo());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}

	public long update(AccountRelationsSettingInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
//			get the connection from Database
			conn = this.getConnection();
			lReturn = this.getMaxID();
			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update  Sett_AccountRelationsSetting set ACCOUNTID=?,ACCOUNTNO=?,ORIGINALACCOUNTNO=? ");
			sbSQL.append(" where id =? ");
			
			ps = conn.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			ps.setLong(nIndex++, info.getAccountID());
			ps.setString(nIndex++, info.getAccountNo());
			ps.setString(nIndex++, info.getOriginalAccountNo());
			ps.setLong(nIndex++, info.getId());
			lReturn = ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}	
	
	public long delete(long lID) throws Exception
	{
		long lReturn;
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
			sbSQL.append("delete from Sett_AccountRelationsSetting where ID=" + lID);
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

	public Collection findByCondition( AccountRelationsSettingInfo info) throws Exception
	{
		Vector vReturn = new Vector();
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
			sbSQL.append("select seta.ID,seta.ACCOUNTID,seta.ACCOUNTNO,seta.ORIGINALACCOUNTNO from Sett_AccountRelationsSetting seta , sett_account a where 1=1 AND seta.ACCOUNTID=a.id" );
			if(info.getAccountID()>0)
			{
				sbSQL.append(" and seta.ACCOUNTID = " + info.getAccountID());
			}
			if(info.getOriginalAccountNo()!=null && info.getOriginalAccountNo().length()>0)
			{
				sbSQL.append(" and seta.ORIGINALACCOUNTNO = '" + info.getOriginalAccountNo() + "'");
			}
			if(info.getOfficeID()>0)
			{
				sbSQL.append(" and a.NOFFICEID = " + info.getOfficeID());
			}
			if(info.getCurrencyID()>0)
			{
				sbSQL.append(" and a.NCURRENCYID = " + info.getCurrencyID());
			}
			if(info.getId()>0)//用于修改检验
			{
				sbSQL.append(" and seta.id != " + info.getId());
			}
			//System.out.print("------------sql = "+sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{				
				AccountRelationsSettingInfo tempInfo = new AccountRelationsSettingInfo();
				tempInfo.setId(rs.getLong("ID"));
				tempInfo.setAccountID(rs.getLong("ACCOUNTID"));
				tempInfo.setAccountNo(rs.getString("ACCOUNTNO"));
				tempInfo.setOriginalAccountNo(rs.getString("ORIGINALACCOUNTNO"));
				vReturn.add(tempInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
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
		return vReturn;
	}	

	public AccountRelationsSettingInfo findByID( long lID) throws Exception
	{
		AccountRelationsSettingInfo returnInfo = new AccountRelationsSettingInfo();
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
			sbSQL.append("select * from Sett_AccountRelationsSetting where 1=1 " );
			sbSQL.append(" and ID = " + lID);
			
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{								
				returnInfo.setId(rs.getLong("ID"));
				returnInfo.setAccountID(rs.getLong("ACCOUNTID"));
				returnInfo.setAccountNo(rs.getString("ACCOUNTNO"));
				returnInfo.setOriginalAccountNo(rs.getString("ORIGINALACCOUNTNO"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
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
		return returnInfo;
	}	
	
	public AccountRelationsSettingInfo findByAccountID( long lAccountID) throws Exception
	{
		AccountRelationsSettingInfo returnInfo = new AccountRelationsSettingInfo();
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
			sbSQL.append("select * from Sett_AccountRelationsSetting where 1=1 " );
			sbSQL.append(" and ACCOUNTID = " + lAccountID);
			
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{								
				returnInfo.setId(rs.getLong("ID"));
				returnInfo.setAccountID(rs.getLong("ACCOUNTID"));
				returnInfo.setAccountNo(rs.getString("ACCOUNTNO"));
				returnInfo.setOriginalAccountNo(rs.getString("ORIGINALACCOUNTNO"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
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
		return returnInfo;
	}	
	
	/** get the current maximum id of table Sett_AccountRelationsSetting
	 * @return the current maximum id of table Sett_AccountRelationsSetting
	 * @exception
	 */
	private long getMaxID() throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lMaxID = -1;
		try
		{
			//get the connection from Database
			conn = this.getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select nvl( max( id ) , 0 ) + 1 as maxno ");
			sbSQL.append(" from Sett_AccountRelationsSetting ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong("maxno");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
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
		return lMaxID;
	}	

	/** get the display accountno for print voucher
	 * @return the display accountno
	 * @exception
	 */
	public String getDisplayAcctNoByAcctID( long lAccountID) throws Exception
	{		
		String strAccountNo = "";
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
			sbSQL.append("select * from Sett_AccountRelationsSetting where 1=1 " );
			sbSQL.append(" and ACCOUNTID = " + lAccountID);
			
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{								
				strAccountNo = rs.getString("ORIGINALACCOUNTNO");
			}
			else
			{
				strAccountNo = NameRef.getAccountNoByID(lAccountID);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
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
		return strAccountNo;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
	}

}
