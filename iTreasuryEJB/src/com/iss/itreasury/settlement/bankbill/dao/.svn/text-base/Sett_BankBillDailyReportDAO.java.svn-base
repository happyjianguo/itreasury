/*
 * Created on 2003-12-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bankbill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankbill.dataentity.BankBillDailyReportInfo;
import com.iss.itreasury.settlement.bankbill.dataentity.Sett_DailyReportInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_BankBillDailyReportDAO extends SettlementDAO
{
	public Sett_BankBillDailyReportDAO(Connection conn)
	{
		super(conn);
	}
	/**
	* 保存每日票据报表
	* @param tsDate 系统开机日
	*/
	public boolean saveBankBillDailyReportInfo(Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		boolean bRtn = true;
		try
		{
			conn = getConnection();

			sbSQL = new StringBuffer();
			sbSQL.append(" select distinct ID from sett_BankBillType ");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			//
			while (rs.next())
			{
				Sett_DailyReportInfo info = new Sett_DailyReportInfo();
				long lBillTypeID = rs.getLong("ID");

				info.setID(this.getNextID());
				info.setBilliTypeID(lBillTypeID);
				info.setDate(tsDate);
				info.setOriginalCount(this.getOriginalBalance(lBillTypeID, tsDate));
				info.setIncomeCount(this.getIncomeCount(lBillTypeID, tsDate));
				info.setOutCount(this.getOutCount(lBillTypeID, tsDate));
				info.setBalance(info.getOriginalCount() + info.getIncomeCount() - info.getOutCount());

				//保存信息
				this.add(info);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return bRtn;
	}
	/**
	* 获得起初余额
	* @param lBillTypeID 票据类型
	* @param tsDate 系统开机日
	*/
	private long getOriginalBalance(long lBillTypeID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		long lOriginalBalance = 0;
		try
		{
			conn = getConnection();

			sbSQL = new StringBuffer();
			sbSQL.append(" select nvl(nBalance,0) OriginalBalance from Sett_BankBillDailyReport where nBilliTypeID=? order by dtDate desc ");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBillTypeID);
			rs = ps.executeQuery();
			//
			if (rs != null && rs.next())
			{
				lOriginalBalance = rs.getLong("OriginalBalance");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return lOriginalBalance;
	}
	/**
	* 新增记录
	*/
	private void add(Sett_DailyReportInfo info) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		try
		{
			conn = getConnection();

			sbSQL = new StringBuffer();
			sbSQL.append(" insert into Sett_BankBillDailyReport(ID,dtDate,nBilliTypeID,nOriginalCount,nIncomeCount,nOutCount,nBalance) ");
			sbSQL.append(" values(?,?,?,?,?,?,?) ");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getID());
			ps.setTimestamp(2, info.getDate());
			ps.setLong(3, info.getBilliTypeID());
			ps.setLong(4, info.getOriginalCount());
			ps.setLong(5, info.getIncomeCount());
			ps.setLong(6, info.getOutCount());
			ps.setLong(7, info.getBalance());

			ps.executeUpdate();
			//
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
	}

	/**
	* 得到下一条纪录标识
	*/
	private long getNextID() throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		long lNextID = -1;
		try
		{
			conn = getConnection();

			sbSQL = new StringBuffer();
			//
			sbSQL.append(" select nvl(max(ID+1),1) NextID from Sett_BankBillDailyReport ");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			//
			if (rs != null && rs.next())
			{
				lNextID = rs.getLong("NextID");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return lNextID;
	}
	/**
	* 得到当日入库数量
	*/
	private long getIncomeCount(long lBillTypeID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		long lIncomeCount = -1;
		try
		{
			conn = getConnection();

			sbSQL = new StringBuffer();
			//
			sbSQL.append(" select count(*) IncomeCount from sett_BankBill where nTypeID=? and nStatusID>=? and dtRegister=? ");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBillTypeID);
			ps.setLong(2, SETTConstant.BankBillStatus.REGISTER);
			ps.setTimestamp(3, tsDate);
			rs = ps.executeQuery();
			//
			if (rs != null && rs.next())
			{
				lIncomeCount = rs.getLong("IncomeCount");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return lIncomeCount;
	}
	/**
	* 得到当日出库数量
	*/
	private long getOutCount(long lBillTypeID, Timestamp tsDate) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		long lOutCount = -1;
		try
		{
			conn = getConnection();

			sbSQL = new StringBuffer();
			//
			sbSQL.append(" select count(*) OutCount from sett_BankBill where nTypeID=? and nStatusID>=? and dtRequire=? ");
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBillTypeID);
			ps.setLong(2, SETTConstant.BankBillStatus.REQUIRE);
			ps.setTimestamp(3, tsDate);
			rs = ps.executeQuery();
			//
			if (rs != null && rs.next())
			{
				lOutCount = rs.getLong("OutCount");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return lOutCount;
	}
}
