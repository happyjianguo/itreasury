package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

public class Sett_TransAccountGuardDAO extends SettlementDAO {

	// 构造函数
	public Sett_TransAccountGuardDAO() {
		super("sett_transaccountguard", false);
		super.setUseMaxID();
	}

	// 计算某时间段内,某账号的支付额之和
	public double sumPayInPeriods(long lId, long lAccountId, Timestamp dtStart, Timestamp dtEnd)
			throws SQLException {

		// 定义变量
		double dbResult = 0.0;

		// 定义数据库查询变量
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		// 开始查询统计
		try {

			// 获取数据库连接
			conn = getConnection();

			StringBuffer strSQLQuery = new StringBuffer();
			// 统计时间段内账户支付金额之和
			strSQLQuery.append("SELECT ");
			strSQLQuery.append("	SUM(AMOUNT) SUM_AMOUNT ");
			strSQLQuery.append("FROM ");
			strSQLQuery.append("	SETT_TRANSACCOUNTGUARD ");
			strSQLQuery.append("WHERE ");
			strSQLQuery.append("	ID != " + lId + " AND ");
			strSQLQuery.append("	PAYACCOUNTID = " + lAccountId + " AND ");
			strSQLQuery.append("	EXECUTEDATE >= TO_DATE('" + dtStart.toString().substring(0, 10)
					+ "','YYYY-MM-DD') AND ");
			strSQLQuery.append("	EXECUTEDATE <= TO_DATE('" + dtEnd.toString().substring(0, 10)
					+ "','YYYY-MM-DD') AND ");
			strSQLQuery.append("	STATUSID = " + Constant.RecordStatus.VALID);

			Log.print("监管账号该周期内交易额之和计算的SQL:" + strSQLQuery.toString());

			// 查询数据库
			ps = conn.prepareStatement(strSQLQuery.toString());
			rs = ps.executeQuery();

			// 提取统计结果
			while (rs.next()) {
				dbResult = rs.getDouble("SUM_AMOUNT");
				break;
			}

		} finally {
			// 清除数据库连接
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		// 返回函数值
		return dbResult;
	}

}
