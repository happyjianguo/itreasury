/*
 * Created on 2003-10-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.clientmanage.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.clientmanage.dataentity.ClientSealPicInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_ClientSealPicDAO extends SettlementDAO
{
	/**
	 * 
	 */
	Log4j logger = null; // log4j 用于打印日志。
	public Sett_ClientSealPicDAO()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(SETTConstant.ModuleType.SETTLEMENT, this);
	}
	private long getNextID() throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lNextID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select nvl( max( id ) , 0 ) + 1 as maxid ");
			sbSQL.append(" from SETT_ClientSealPic ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lNextID = rs.getLong("maxid");
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
		return lNextID;
	}
	/**
	 * @param cspi
	 * @param rs
	 */
	private void getInfoFromResultSet(ClientSealPicInfo cspi, ResultSet rs) throws Exception
	{
		int i = 1;
		try
		{
			// ID, NCLIENTID, SFULLNAME, SPICNAME, DTSTART,NMODIFYUSERID, NSTATUSID
			cspi.setSealPicID(rs.getLong("ID"));
			cspi.setClientID(rs.getLong("NCLIENTID"));
			cspi.setPath(rs.getString("SPath"));
			cspi.setPicName(rs.getString("SPICNAME"));
			cspi.setStartDate(rs.getTimestamp("DTSTART"));
			cspi.setModifyUserID(rs.getLong("NMODIFYUSERID"));
			cspi.setStatusID(rs.getLong("NSTATUSID"));
		}
		catch (Exception se)
		{
			throw se;
		}
	}
	private void setPrepareStatementByInfo(ClientSealPicInfo cspi, PreparedStatement ps) throws Exception
	{
		int i = 1;
		try
		{
			// ID, NCLIENTID, SFULLNAME, SPICNAME, DTSTART,NMODIFYUSERID, NSTATUSID
			ps.setLong(i++, cspi.getSealPicID());
			ps.setLong(i++, cspi.getClientID());
			ps.setString(i++, cspi.getPath());
			ps.setString(i++, cspi.getSealPicID()+cspi.getPicName());
			ps.setTimestamp(i++, cspi.getStartDate());
			ps.setLong(i++, cspi.getModifyUserID());
			ps.setLong(i++, cspi.getStatusID());
		}
		catch (Exception se)
		{
			throw se;
		}
	}
	public static void main(String[] args)
	{
	}
	public long add(ClientSealPicInfo cspi) throws Exception
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
			sbSQL.append(" insert into SETT_ClientSealPic(ID, NCLIENTID, SPath, SPICNAME, DTSTART,NMODIFYUSERID, NSTATUSID) \n");
			sbSQL.append(" values( ?,?,?,?,?,?,?) \n");
			ps = conn.prepareStatement(sbSQL.toString());
			System.out.println("sql is :" + sbSQL.toString());
			//get the maximum id
			lReturn = getNextID();
			cspi.setSealPicID(lReturn);
			//set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(cspi, ps);
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
	public ClientSealPicInfo findByID(long lID) throws Exception
	{
		ClientSealPicInfo cspi = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from SETT_CLIENTSEALPIC ");
			sbSQL.append(" where ID = ? and nStatusID = " + SETTConstant.RecordStatus.VALID);
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				cspi = new ClientSealPicInfo();
				getInfoFromResultSet(cspi, rs);
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
		return cspi != null ? cspi : null;
	}
	public Collection findByClientID(long lClientID) throws Exception
	{
		ClientSealPicInfo cspi = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		Vector v = new Vector();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from SETT_CLIENTSEALPIC ");
			sbSQL.append(" where NCLIENTID=? and nStatusID = " + SETTConstant.RecordStatus.VALID);
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				cspi = new ClientSealPicInfo();
				getInfoFromResultSet(cspi, rs);
				v.add(cspi);
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
		return v.size() > 0 ? v : null;
	}
}
