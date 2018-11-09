package com.iss.itreasury.ebank.obaccountinfo.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.ebank.obdao.OBBaseDao;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * Title:        		iTreasury
 * Description:                   
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author             kewen hu 
 * @version
 * Date of Creation     2004-02-25
 */
public class OBCheckTransDirectionDao extends OBBaseDao
{
	/** Logger */
	private Log4j log = new Log4j(Constant.ModuleType.EBANK, this);
	/** DB连接 */
	private Connection conn = null;

	/**
	 * 构造函数
	 * @param  Connection conn
	 * @return nothing
	 * @exception nothing
	 */
	public OBCheckTransDirectionDao(Connection conn)
	{
		this.conn = conn;
	}

	/**
	 * 构造函数
	 * @param  nothing
	 * @return nothing
	 * @exception nothing
	 */
	public OBCheckTransDirectionDao()
	{
	}

	/**
	 * 判断交易是属于付款业务
	 * @param  Connection conn, String sTransNo, String SerialNo, long
	 * lClientID, long lOfficeID, long lCurrencyID, long lDirection
	 * @return long
	 * @exception throws SQLException
	 */
	private long CheckPayAccountID(Connection conn, String sTransNo, String SerialNo, long lClientID, long lOfficeID, long lCurrencyID, long lDirection) throws SQLException
	{
		long lReturn = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("SELECT nOppAccountID \n");
			bufSQL.append("FROM sett_TransAccountDetail \n");
			bufSQL.append("WHERE sTransNo = '" + sTransNo + "' \n");
			bufSQL.append(" AND nStatusID=" + SETTConstant.TransactionStatus.CHECK);
			bufSQL.append("	AND nOppAccountID IN (SELECT ID \n");
			bufSQL.append("	FROM sett_account \n");
			bufSQL.append("	WHERE nClientID = " + lClientID + ") \n");
			bufSQL.append("	AND nTransDirection = " + lDirection + " \n");
			if (SerialNo != null && SerialNo.trim().length() > 0)
			{
				bufSQL.append("	AND nGroup = " + SerialNo + " \n");
			}
			bufSQL.append("	AND nOfficeID = " + lOfficeID + " \n");
			bufSQL.append("	AND nCurrencyID = " + lCurrencyID + " \n");
			log.print("SQL=" + bufSQL.toString());
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				lReturn = rs.getLong("nOppAccountID");
			}
		}
		catch (SQLException se)
		{
			throw se;
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return lReturn;
	}

	/**
	 * 判断交易是属于收款业务
	 * @param  Connection conn, String sTransNo, String SerialNo, long
	 * lClientID, long lOfficeID, long lCurrencyID, long lDirection
	 * @return long
	 * @exception throws SQLException
	 */
	private long CheckRecieveAccountID(Connection conn, String sTransNo, String SerialNo, long lClientID, long lOfficeID, long lCurrencyID, long lDirection) throws SQLException
	{
		long lReturn = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		try
		{
			bufSQL.append("SELECT nTransAccountID \n");
			bufSQL.append("FROM sett_TransAccountDetail \n");
			bufSQL.append("WHERE sTransNo = '" + sTransNo + "' \n");
			bufSQL.append(" AND nStatusID=" + SETTConstant.TransactionStatus.CHECK);
			bufSQL.append("	AND nTransAccountID IN (SELECT ID \n");
			bufSQL.append("	FROM sett_account \n");
			bufSQL.append("	WHERE nClientID = " + lClientID + ") \n");
			bufSQL.append("	AND nTransDirection = " + lDirection + " \n");
			if (SerialNo != null && SerialNo.trim().length() > 0)
			{
				bufSQL.append("	AND nGroup = " + SerialNo + " \n");
			}
			bufSQL.append("	AND nOfficeID = " + lOfficeID + " \n");
			bufSQL.append("	AND nCurrencyID = " + lCurrencyID + " \n");
			log.print("SQL=" + bufSQL.toString());
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				lReturn = rs.getLong("nTransAccountID");
			}
		}
		catch (SQLException se)
		{
			throw se;
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return lReturn;
	}

	/**
	 * 判断交易是属于收款、付款、收付款业务
	 * @param  long lResultRec, long lResultPay
	 * @return long
	 * @exception nothing
	 */
	private long getCheckAccountID(long lResultRec, long lResultPay)
	{
		long lReturn = 0;
		if (lResultRec != 0 && lResultPay != 0)
		{
			lReturn = OBConstant.CheckSettType.RecAndPayID;
		}
		else if (lResultRec == 0 && lResultPay != 0)
		{
			lReturn = OBConstant.CheckSettType.PayID;
		}
		else if (lResultRec != 0 && lResultPay == 0)
		{
			lReturn = OBConstant.CheckSettType.RecID;
		}
		else
		{
			lReturn = OBConstant.CheckSettType.NoRecAndPayID;
		}
		return lReturn;
	}

	/**
	 * 判断交易是属于收款、付款、收付款业务
	 * @param  String sTransNo, String SerialNo, long lClientID, long lOfficeID,
	 * long lCurrencyID
	 * @return long
	 * @exception Exception
	 */
	public long CheckAccountID(String sTransNo, String SerialNo, long lClientID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lReturn = 0;
		long lReturnRec = 0;
		long lReturnPay = 0;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			lReturnRec =
				this.getCheckAccountID(
					this.CheckRecieveAccountID(conn, sTransNo, SerialNo, lClientID, lOfficeID, lCurrencyID, SETTConstant.DebitOrCredit.CREDIT),
					this.CheckPayAccountID(conn, sTransNo, SerialNo, lClientID, lOfficeID, lCurrencyID, SETTConstant.DebitOrCredit.CREDIT));
			lReturnPay =
				this.getCheckAccountID(
					this.CheckPayAccountID(conn, sTransNo, SerialNo, lClientID, lOfficeID, lCurrencyID, SETTConstant.DebitOrCredit.DEBIT),
					this.CheckRecieveAccountID(conn, sTransNo, SerialNo, lClientID, lOfficeID, lCurrencyID, SETTConstant.DebitOrCredit.DEBIT));
			log.print("=============lReturnRec=" + lReturnRec);
			log.print("=============lReturnPay=" + lReturnPay);
			if (lReturnRec == OBConstant.CheckSettType.RecAndPayID
				|| lReturnPay == OBConstant.CheckSettType.RecAndPayID
				|| (lReturnRec == OBConstant.CheckSettType.RecID && lReturnPay == OBConstant.CheckSettType.PayID)
				|| (lReturnRec == OBConstant.CheckSettType.PayID && lReturnPay == OBConstant.CheckSettType.RecID))
			{
				lReturn = OBConstant.CheckSettType.RecAndPayID;
			}
			else if (lReturnRec == OBConstant.CheckSettType.NoRecAndPayID && lReturnPay == OBConstant.CheckSettType.NoRecAndPayID)
			{
				lReturn = OBConstant.CheckSettType.NoRecAndPayID;
			}
			else if (
				(lReturnRec == OBConstant.CheckSettType.RecID || lReturnRec == OBConstant.CheckSettType.NoRecAndPayID)
					&& (lReturnPay == OBConstant.CheckSettType.RecID || lReturnPay == OBConstant.CheckSettType.NoRecAndPayID))
			{
				lReturn = OBConstant.CheckSettType.RecID;
			}
			else if (
				(lReturnRec == OBConstant.CheckSettType.PayID || lReturnRec == OBConstant.CheckSettType.NoRecAndPayID)
					&& (lReturnPay == OBConstant.CheckSettType.PayID || lReturnPay == OBConstant.CheckSettType.NoRecAndPayID))
			{
				lReturn = OBConstant.CheckSettType.PayID;
			}
			else
			{
				lReturn = OBConstant.CheckSettType.NoRecAndPayID;
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			this.cleanup(conn);
		}
		return lReturn;
	}
	//判断活期账户的收付款方向
	public long getTransDirect(String sTransNo, String SerialNo, long lClientID, long lOfficeID, long lCurrencyID) throws Exception
	{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		long lResult = -1;
		try
		{
			// select 
			m_sbSQL.append(" select * \n");
			m_sbSQL.append(" from ");
			m_sbSQL.append(" ( ");
			m_sbSQL.append(" select \n");
			m_sbSQL.append(" mAmount PayAmount,0 ReceiveAmount \n");
			m_sbSQL.append(" from sett_TransAccountDetail \n");
			m_sbSQL.append(" where sTransNo = '" + sTransNo + "' \n");
			m_sbSQL.append(" and nStatusID=" + SETTConstant.TransactionStatus.CHECK + " \n");
			m_sbSQL.append(" and nTransDirection = 1 \n");
			m_sbSQL.append(" and nOfficeID=" + lOfficeID + " \n");
			m_sbSQL.append(" and nCurrencyID=" + lCurrencyID + "\n");
			m_sbSQL.append(" AND nTransAccountID IN (SELECT ID \n");
			m_sbSQL.append(" FROM sett_account \n");
			m_sbSQL.append(" WHERE nClientID = " + lClientID + ") \n");
			m_sbSQL.append(" union all \n");
			m_sbSQL.append(" select \n");
			m_sbSQL.append(" 0 PayAmount,mAmount ReceiveAmount \n");
			m_sbSQL.append(" from sett_TransAccountDetail \n");
			m_sbSQL.append(" where sTransNo = '" + sTransNo + "' \n");
			m_sbSQL.append(" and nStatusID=" + SETTConstant.TransactionStatus.CHECK);
			m_sbSQL.append(" and nTransDirection = 2 \n");
			m_sbSQL.append(" and nOfficeID=" + lOfficeID + " \n");
			m_sbSQL.append(" and nCurrencyID=" + lCurrencyID + "\n");
			m_sbSQL.append(" AND nTransAccountID IN (SELECT ID \n");
			m_sbSQL.append(" FROM sett_account \n");
			m_sbSQL.append(" WHERE nClientID = " + lClientID + ") \n");
			m_sbSQL.append(" ) \n");

			conn = Database.getConnection();
			log.print("判断收付款方向SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			double dDayPayBalance = 0.0; //每日付款合计
			double dDayReceiveBalance = 0.0; //每日收款合计
			while (rs.next())
			{
				dDayPayBalance = rs.getDouble("PayAmount") + dDayPayBalance;
				dDayReceiveBalance = rs.getDouble("ReceiveAmount") + dDayReceiveBalance;
			}

			if (dDayPayBalance != 0 && dDayReceiveBalance == 0)
			{
				lResult = OBConstant.CheckSettType.PayID;
			}
			else if (dDayPayBalance == 0 && dDayReceiveBalance != 0)
			{
				lResult = OBConstant.CheckSettType.RecID;
			}
			else if (dDayPayBalance != 0 && dDayReceiveBalance != 0)
			{
				lResult = OBConstant.CheckSettType.RecAndPayID;
			}
			else
			{
				lResult = OBConstant.CheckSettType.NoRecAndPayID;
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

		}
		return lResult;
	}
//	判断委托账户的收付款方向
	public long getConsnTransDirect(String sTransNo, String SerialNo, long lClientID, long lOfficeID, long lCurrencyID) throws Exception
	{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		long lResult = -1;
		try
		{
			// select 
			m_sbSQL.append(" select * \n");
			m_sbSQL.append(" from ");
			m_sbSQL.append(" ( ");
			m_sbSQL.append(" select \n");
			m_sbSQL.append(" mAmount PayAmount,0 ReceiveAmount \n");
			m_sbSQL.append(" from sett_TransAccountDetail \n");
			m_sbSQL.append(" where sTransNo = '" + sTransNo + "' \n");
			m_sbSQL.append(" and nStatusID=" + SETTConstant.TransactionStatus.CHECK + " \n");
			m_sbSQL.append(" and nTransDirection = 2 \n");
			m_sbSQL.append(" and nOfficeID=" + lOfficeID + " \n");
			m_sbSQL.append(" and nCurrencyID=" + lCurrencyID + "\n");
			m_sbSQL.append(" AND nTransAccountID IN (SELECT sa.ID \n");
			m_sbSQL.append(" FROM sett_account sa,sett_accountType sat\n");
			m_sbSQL.append(" WHERE sa.nClientID = " + lClientID + " \n");
			m_sbSQL.append(" AND sa.nAccounttypeid = sat.id and sat.nAccountGroupID=" + SETTConstant.AccountGroupType.CONSIGN + ") \n");
			m_sbSQL.append(" union all \n");
			m_sbSQL.append(" select \n");
			m_sbSQL.append(" 0 PayAmount,mAmount ReceiveAmount \n");
			m_sbSQL.append(" from sett_TransAccountDetail \n");
			m_sbSQL.append(" where sTransNo = '" + sTransNo + "' \n");
			m_sbSQL.append(" and nStatusID=" + SETTConstant.TransactionStatus.CHECK);
			m_sbSQL.append(" and nTransDirection = 1 \n");
			m_sbSQL.append(" and nOfficeID=" + lOfficeID + " \n");
			m_sbSQL.append(" and nCurrencyID=" + lCurrencyID + "\n");
			m_sbSQL.append(" AND nTransAccountID IN (SELECT sa.ID \n");
			m_sbSQL.append(" FROM sett_account sa,sett_accountType sat\n");
			m_sbSQL.append(" WHERE sa.nClientID = " + lClientID + " \n");
			m_sbSQL.append(" AND sa.nAccounttypeid = sat.id and sat.nAccountGroupID=" + SETTConstant.AccountGroupType.CONSIGN + ") \n");
			m_sbSQL.append(" ) \n");

			conn = Database.getConnection();
			log.print("判断收付款方向SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			double dDayPayBalance = 0.0; //每日付款合计
			double dDayReceiveBalance = 0.0; //每日收款合计
			while (rs.next())
			{
				dDayPayBalance = rs.getDouble("PayAmount") + dDayPayBalance;
				dDayReceiveBalance = rs.getDouble("ReceiveAmount") + dDayReceiveBalance;
			}

			if (dDayPayBalance != 0 && dDayReceiveBalance == 0)
			{
				lResult = OBConstant.CheckSettType.PayID;
			}
			else if (dDayPayBalance == 0 && dDayReceiveBalance != 0)
			{
				lResult = OBConstant.CheckSettType.RecID;
			}
			else if (dDayPayBalance != 0 && dDayReceiveBalance != 0)
			{
				lResult = OBConstant.CheckSettType.RecAndPayID;
			}
			else
			{
				lResult = OBConstant.CheckSettType.NoRecAndPayID;
			}

			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);

		}
		return lResult;
	}

	//	/**
	//	 * 判断交易是属于收款、付款、收付款业务
	//	 * @param  Connection conn, String sTransNo, String SerialNo, long
	//	 * lClientID, long lOfficeID, long lCurrencyID
	//	 * @return long
	//	 * @exception Exception
	//	 */
	//	public long CheckAccountID(Connection conn, String sTransNo, String SerialNo, long lClientID, long lOfficeID, long lCurrencyID) throws Exception {
	//		long lReturn = 0;
	//		try {
	//			lReturn = this.getCheckAccountID(
	//				this.CheckRecieveAccountID(conn, sTransNo, SerialNo, lClientID, lOfficeID, lCurrencyID, SETTConstant.DebitOrCredit.CREDIT), 
	//				this.CheckPayAccountID(conn, sTransNo, SerialNo, lClientID, lOfficeID, lCurrencyID, SETTConstant.DebitOrCredit.CREDIT));
	//		} catch (Exception e) {
	//			throw e;
	//		} finally {
	//		}
	//		return lReturn;
	//	}
}