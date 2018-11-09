/*
 * Created on 2006-9-8
 */
package com.iss.itreasury.treasuryplan.reportquery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.query.paraminfo.QCounterTransInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.DailyGatherInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAverageAccountResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

public class ReportQueryDAO {

	// 查所有办事处
	public Vector queryOfficeList() throws Exception {
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = " select id from office where nstatusid = 1 order by id asc";
			conn = Database.getConnection();
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while (rs.next()) {
				long officeID = rs.getLong(1);
				v.add(new Long(officeID));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstat != null)
					pstat.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
		return v;
	}

	// 根据办事处ID号查询名称
	public String getOfficeNameByID(long lOfficeID) throws Exception {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		String sql = null;
		String strName = null;
		try {
			sql = " select sname from office where id = " + lOfficeID
					+ " and nstatusid = 1";
			conn = Database.getConnection();
			Log.print(sql);
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while (rs.next()) {
				strName = rs.getString(1);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstat != null)
					pstat.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
		return strName;
	}

	// 根据某一交易类型查询所有办事处的交易金额和交易笔数
	public Collection queryCounterTransByTransType(QCounterTransInfo qInfo)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		Vector v = new Vector();
		try {
			sbSQL.append(" select b.id OfficeID, ");
			sbSQL.append(" nvl(SumPay, 0) SumPay, nvl(SumReceive, 0) SumReceive, nvl(nCount, 0) nCount ");
			sbSQL.append(" from ( ");
			sbSQL.append("   select TransactionTypeID, OfficeID, ");
			sbSQL.append("   sum(PayAmount) SumPay, sum(ReceiveAmount) SumReceive, count(TransNo) nCount ");
			sbSQL.append("   from Sett_VTransaction where CurrencyID = ? ");
			sbSQL.append("   and StatusID in (?, ?, ?, ?, ?) ");
			sbSQL.append("   and TransactionTypeID = ? ");
			sbSQL.append("   and Execute between ? and ? ");
			sbSQL.append("   group by TransactionTypeID, OfficeID ");
			sbSQL.append(" ) a, Office b ");
			sbSQL.append(" where a.OfficeID(+) = b.id ");
			sbSQL.append(" and b.nstatusid = ? ");
			sbSQL.append(" order by b.id asc ");

			conn = Database.getConnection();
			ps = conn.prepareStatement(sbSQL.toString());
			int index = 1;
			// 借用原有的实体类封装的查询条件
			ps.setLong(index++, qInfo.getCurrencyID());
			ps.setLong(index++, SETTConstant.TransactionStatus.CHECK);
			ps.setLong(index++, SETTConstant.TransactionStatus.NOTSIGN);
			ps.setLong(index++, SETTConstant.TransactionStatus.SIGN);
			ps.setLong(index++, SETTConstant.TransactionStatus.CONFIRM);
			ps.setLong(index++, SETTConstant.TransactionStatus.CIRCLE);
			ps.setLong(index++, qInfo.getAccountType()); // 对应交易类型TransactionTypeID
			ps.setTimestamp(index++, qInfo.getStartDate());
			ps.setTimestamp(index++, qInfo.getEndDate());
			ps.setLong(index++, Constant.RecordStatus.VALID);

			rs = ps.executeQuery();
			while (rs.next()) {
				DailyGatherInfo info = new DailyGatherInfo();
				int rsIndex = 1;
				info.setID(rs.getLong(rsIndex++)); // 办事处编号
				info.setPutAmount(rs.getDouble(rsIndex++)); // 付款
				info.setGetAmount(rs.getDouble(rsIndex++)); // 收款
				info.setNumber(rs.getLong(rsIndex++)); // 笔数
				v.add(info);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
		return v.size() > 0 ? v : null;
	}

	// 根据账户组查询时间段内平均余额
	public Collection queryAverageAccountBalance(QueryAccountWhereInfo qInfo) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		Vector v = new Vector();
		try {
			// 取出查询条件
			long lCurrencyID = qInfo.getCurrencyID(); // 币种
			long lAccountGroupID = qInfo.getAccountID(); // 账户组类型
			Timestamp tsStartDate = qInfo.getStartQueryDate(); // 开始时间
			Timestamp tsEndDate = qInfo.getEndQueryDate(); // 结束时间
			long lIntervalDays = DataFormat.getIntervalDays(tsStartDate, tsEndDate); // 间隔天数
			
			// 分两部分查询，历史数据从sett_dailyaccountbalance表中查询，当前的业务数据从sett_subAccount表中取
			// sett_dailyaccountbalance表中的数据可以直接取，sett_subAccount表里的数据需要进行计算才能得到协定余额
			// 协定存款是活期业务的一种，已包含在账户余额中，需要根据协定金额和单位金额来计算
			// 要求将协定和活期的余额分开来计算，所以活期余额=账户余额-协定余额
			// 平均余额 = 余额总和/间隔天数 
			sbSQL.append(" select t2.id OfficeID, round(nvl(t1.balance, 0)/? , 2) Balance, round(nvl(t1.negotiatebalance, 0)/? , 2) NegotiateBalance ");
			sbSQL.append(" from ( ");
			sbSQL.append("   select nofficeid, sum(Balance) - sum(NegotiateBalance) Balance, sum(NegotiateBalance) NegotiateBalance ");
			sbSQL.append("   from ( ");
			sbSQL.append("     select a.nofficeid, round(nvl(da.mBalance, 0), 2) Balance, round(nvl(da.AC_mNegotiateBalance, 0), 2) NegotiateBalance ");
			sbSQL.append("     from sett_account a, sett_accounttype b, sett_subAccount sa, sett_dailyaccountbalance da ");
			sbSQL.append("     where b.naccountgroupid = ? and b.nstatusId=1 and b.officeId="+qInfo.getOfficeID()+" and b.currencyId="+qInfo.getCurrencyID()+" and a.ncurrencyid = ? and da.dtDate between ? and ? ");
			sbSQL.append("     and b.id = a.naccounttypeid and a.id = da.naccountid and da.nSubAccountID = sa.id ");
			sbSQL.append("     union all ");
			sbSQL.append("     select a.nofficeid, round(nvl(sa.mbalance, 0), 2) Balance, ");
			sbSQL.append("     round(decode(sign(nvl(sa.mBalance, 0) - nvl(sa.AC_mNegotiateAmount, 0)), -1, 0, decode(sa.ac_nIsNegotiate, 1, floor((nvl(sa.mBalance, 0) - nvl(sa.AC_mNegotiateAmount, 0)) / sa.ac_mNegotiateUnit) * sa.ac_mNegotiateUnit, 0)), 2) NegotiateBalance ");
			sbSQL.append("     from sett_account a, sett_accounttype b, sett_subaccount sa ");
			sbSQL.append("     where b.naccountgroupid = ? and b.nstatusId=1 and b.officeId="+qInfo.getOfficeID()+" and b.currencyId="+qInfo.getCurrencyID()+" and a.ncurrencyid = ? and b.id = a.naccounttypeid and a.id = sa.naccountid ");
			sbSQL.append("   ) ");
			sbSQL.append("   group by nofficeid ");
			sbSQL.append(" ) t1, office t2 ");
			sbSQL.append(" where t1.nofficeid(+) = t2.id ");
			sbSQL.append(" order by t2.id ");			
			
			conn = Database.getConnection();
			ps = conn.prepareStatement(sbSQL.toString());
			int index = 1;
			ps.setLong(index++, lIntervalDays);
			ps.setLong(index++, lIntervalDays);
			ps.setLong(index++, lAccountGroupID);
			ps.setLong(index++, lCurrencyID);
			ps.setTimestamp(index++, tsStartDate);
			ps.setTimestamp(index++, tsEndDate);
			ps.setLong(index++, lAccountGroupID);
			ps.setLong(index++, lCurrencyID);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				QueryAverageAccountResultInfo info = new QueryAverageAccountResultInfo();
				int rsIndex = 1;
				info.setAccountID(rs.getLong(rsIndex++)); // 办事处编号
				info.setBalance(rs.getDouble(rsIndex++)); // 平均余额（已除去协定余额）
				info.setNegotiateBalance(rs.getDouble(rsIndex++)); // 协定平均余额
				v.add(info);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				throw e;
			}
		}
		return v.size() > 0 ? v : null;
	}
}