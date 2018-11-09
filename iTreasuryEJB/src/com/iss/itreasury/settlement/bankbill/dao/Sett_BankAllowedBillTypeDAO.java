package com.iss.itreasury.settlement.bankbill.dao;

import java.sql.*;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankbill.dataentity.*;
import com.iss.itreasury.util.Database;

/**
 * Sett_BillTypeDAO.java
 * Ʊ�����͵����ݷ��ʲ�
 * @author  Ryan
 * @version 1.0
*/

public class Sett_BankAllowedBillTypeDAO extends SettlementDAO
{
	/**����Ʊ�ݷ��ŵ����У������У���
	 * @param lBankID ����ID
	 * @param lBankBillTypeID Ʊ������ID
	 * @return void
	 * @exception
	 */
	public void addGrantBillBank(long lBankID,long lBankBillTypeID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		BankBillTypeInfo objBankBillTypeInfo = null;

		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" insert into sett_BankAllowedBillType(nBankID,nBillTypeID) ");
			sbSQL.append(" values(?,?) ");
			
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBankID);
			ps.setLong(2, lBankBillTypeID);
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
	}
	
	/**ɾ��Ʊ�ݷ��ŵ����У������У���
	 * @param lBankID ����ID
	 * @param lBankBillTypeID Ʊ������ID������
	 * @return void
	 * @exception
	 */
	public void dropGrantBillBank(long lBankID,long lBankBillTypeID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		BankBillTypeInfo objBankBillTypeInfo = null;

		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" delete from sett_BankAllowedBillType ");
			sbSQL.append(" where nBankID=? and nBillTypeID=? ");
			
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBankID);
			ps.setLong(2, lBankBillTypeID);
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
	}
	
	/**�ж������Ƿ��Ƿ�Ʊ���еķ�����
	 * @param lBankID ����ID
	 * @return void
	 * @exception
	 */
	public boolean isGrantBillBank(long lBankID,long lBankBillTypeID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		BankBillTypeInfo objBankBillTypeInfo = null;
		boolean blnRtn = false;
		
		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select count(*) from sett_BankAllowedBillType ");
			sbSQL.append(" where nBankID=?");
			if (lBankBillTypeID > 0)
			{
				sbSQL.append(" and nBillTypeID=?");
			}
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBankID);
			if (lBankBillTypeID > 0)
			{
				ps.setLong(2, lBankBillTypeID);
			}
			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				if (rs.getLong(1) > 0)
				{
					blnRtn = true;
				}
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
		return blnRtn;
	}
}