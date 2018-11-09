/*
 * Created on 2004-7-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.ImportBankAccountHisDataSettingInfo;

/**
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_ImportBankAccountHisDataSettingDAO extends SettlementDAO
{
	/**
	 * Constructor for Sett_ImportBankAccountCurDataSettingDAO.
	 */
	public Sett_ImportBankAccountHisDataSettingDAO()
	{
		super();
		this.strTableName = "Sett_ImpBankAcctHisDataSetting";
	}

	/**
	 * Constructor for Sett_ImportBankAccountCurDataSettingDAO.
	 * 
	 * @param conn
	 */
	public Sett_ImportBankAccountHisDataSettingDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "Sett_ImpBankAcctHisDataSetting";
	}

	public ImportBankAccountHisDataSettingInfo[] findByCondition(
			ImportBankAccountHisDataSettingInfo condition) throws Exception
	{
		ArrayList al = new ArrayList(8);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from ");
			sbSQL.append(this.strTableName);
			sbSQL.append(" where 1 = 1 ");

			if (condition.getId() > 0)
			{
				sbSQL.append(" and ID = " + condition.getId());
			}
			if (condition.getBankType() > 0)
			{
				sbSQL.append(" and NBANKTYPE = " + condition.getBankType());
			}
			if (condition.getTaskStatus() > 0)
			{
				sbSQL.append(" and NTASKSTATUS = " + condition.getTaskStatus());
			}

			if (condition.getStatus() > 0)
			{
				sbSQL.append(" and NSTATUS = " + condition.getStatus());
			}

			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				ImportBankAccountHisDataSettingInfo info = new ImportBankAccountHisDataSettingInfo();
				getInfoFromResultSet(info, rs);
				al.add(info);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		ImportBankAccountHisDataSettingInfo[] result = null;

		if (al.size() > 0)
		{
			result = new ImportBankAccountHisDataSettingInfo[0];

			result = (ImportBankAccountHisDataSettingInfo[]) al.toArray(result);
		}

		return result;
	}

	/**
	 * Method update.
	 * 
	 * @param info
	 */
	public void update(ImportBankAccountHisDataSettingInfo info)
			throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("update ");
		buffer.append(this.strTableName);
		buffer.append(" set \n");

		buffer.append("NBANKTYPE = ?, \n");
		buffer.append("DTEXETIME = ?, \n");
		buffer.append("NTASKSTATUS = ?, \n");
		buffer.append("NSTATUS = ?, \n");
		buffer.append("DTMODIFY = ? \n");		

		buffer.append("where ID=?\n");

		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			pstmt.setLong(nIndex++, info.getBankType());
			pstmt.setTimestamp(nIndex++, info.getExecuteTime());
			pstmt.setLong(nIndex++, info.getTaskStatus());
			pstmt.setLong(nIndex++, info.getStatus());
			pstmt.setTimestamp(nIndex++, info.getModifyTime());
			

			pstmt.setLong(nIndex++, info.getId());

			pstmt.execute();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
	}

	private void getInfoFromResultSet(ImportBankAccountHisDataSettingInfo info,
			ResultSet rs) throws Exception
	{
		try
		{
			info.setId(rs.getLong("ID")); // ID
			info.setBankType(rs.getLong("NBANKTYPE")); // 
			info.setExecuteTime(rs.getTimestamp("DTEXETIME")); //
			info.setModifyTime(rs.getTimestamp("DTMODIFY")); // 
			info.setStatus(rs.getLong("NSTATUS"));
			info.setTaskStatus(rs.getLong("NTASKSTATUS")); //
			

		}
		catch (Exception se)
		{
			throw se;
		}
	}
}
