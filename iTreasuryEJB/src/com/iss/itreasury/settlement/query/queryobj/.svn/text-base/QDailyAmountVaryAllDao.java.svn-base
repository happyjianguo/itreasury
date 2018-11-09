/*
 * Created on 2004-03-04
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.ArrayList;
import java.sql.SQLException;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.query.resultinfo.QueryInnerAccountInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryDailyCapitalWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QCurrencyDepositInfo;

/**
 * @author kewen hu 2004-03-04
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QDailyAmountVaryAllDao extends BaseQueryObject {
	// SQL语法结构
	private StringBuffer m_sbSelect = null;
	private StringBuffer m_sbFrom = null;
	private StringBuffer m_sbWhere = null;
	// DB连接
	private Connection conn = null;

	/**
	 * 构造函数
	 * @param nothing
	 * @return nothing
	 * @see java.lang.Object#Object()
	 * @exception nothing
	 */
	public QDailyAmountVaryAllDao() {
		super();
	}

	/**
	 * 构造函数
	 * @param nothing
	 * @return nothing
	 * @see java.lang.Object#Object()
	 * @exception nothing
	 */
	public QDailyAmountVaryAllDao(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 将金额为负的值-Value转化为(Value),为正的值Value转化为Value
	 *  -123456.00-->(123,456.00),123456.00-->123,456.00
	 * @param nothing
	 * @return long[]
	 */
	public static String amountTrim(double dAmount) {
		return amountTrim(dAmount, 3);
	}

	/** 存款每日变动情况表 */
	/**
	 *  取得时间的SQL语句
	 * @param  QueryDailyCapitalWhereInfo qdcwi, int nType
	 * @return StringBuffer
	 * @exception nothing
	 */
	private StringBuffer getStringSqlOfDate(QueryDailyCapitalWhereInfo qdcwi, int nType) {
		StringBuffer bufSQL = new StringBuffer();
		switch (nType) {
		case (int)1:
			if (qdcwi.getQueryDate() != null) {
				bufSQL.append("		AND dtExecute = TO_DATE('"+
					DataFormat.getDateString(qdcwi.getQueryDate())+"','YYYY/MM/DD HH24:MI:SS') \n");
			}
			break;
		case (int)2:
			if (qdcwi.getQueryDate() != null) {
				String sFirstDay = DataFormat.getDateString(qdcwi.getQueryDate());
				sFirstDay = sFirstDay.substring(0, 8) + "01";
				String sLastDay = DataFormat.getDateString(DataFormat.getPreviousDate(qdcwi.getQueryDate()));
				bufSQL.append("		AND dtExecute BETWEEN TO_DATE('"+sFirstDay+"','YYYY/MM/DD HH24:MI:SS') \n");
				bufSQL.append("		AND TO_DATE('"+sLastDay+"','YYYY/MM/DD HH24:MI:SS') \n");
			}
			break;
		}
		return bufSQL;
	}

	/**
	 * 取余额部分SQL
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return StringBuffer
	 * @exception nothing
	 */
	private StringBuffer getBalancePartSql(QueryDailyCapitalWhereInfo qdcwi) {
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("(SELECT bb.ID nClientID,NVL(SUM(aa.mBalance),0.0) mBalance \n");
		bufSQL.append("FROM \n");
		bufSQL.append("	(SELECT b.nClientID,b.ID,NVL(SUM(a.mBalance),0.0) mBalance \n");
		bufSQL.append("	FROM (SELECT * FROM \n");
		bufSQL.append("		sett_DailyAccountBalance \n");
		if (qdcwi.getQueryDate() != null) {
			bufSQL.append("	WHERE dtDate = TO_DATE('"+DataFormat.getLastDateString(
			DataFormat.getPreviousMonth(qdcwi.getQueryDate(), (int)1))+"','YYYY/MM/DD HH24:MI:SS')) a, \n");
		}
		bufSQL.append("	(SELECT nClientID,ID FROM sett_Account \n");
		bufSQL.append("		WHERE nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("		AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("		AND nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("		AND nAccountTypeID IN (select id from sett_accountType where nAccountGroupID in("+SETTConstant.AccountGroupType.CURRENT+","+
			SETTConstant.AccountGroupType.FIXED+","+SETTConstant.AccountGroupType.NOTIFY+","+SETTConstant.AccountGroupType.OTHERDEPOSIT+"))) b \n");
		bufSQL.append("	WHERE a.nAccountID(+) = b.ID \n");
		bufSQL.append("	GROUP BY b.nClientID,b.ID) aa, \n");
		bufSQL.append("	(SELECT ID \n");
		bufSQL.append("	FROM Client \n");
		bufSQL.append("	WHERE ID != 0 \n");
		bufSQL.append("		AND nOfficeID = "+qdcwi.getOfficeID()+") bb \n");
		bufSQL.append("WHERE aa.nClientID(+) = bb.ID \n");
		bufSQL.append("GROUP BY bb.ID) dddd \n");

		return bufSQL;
	}

	/**
	 * 取细小部分FROM的SQL
	 * @param  QueryDailyCapitalWhereInfo qdcwi, int nType, long lDirecID
	 * @return nothing
	 * @exception nothing
	 */
	private StringBuffer getLitPartSqlOfFrom(QueryDailyCapitalWhereInfo qdcwi, int nType, long lDirecID) {
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("	(SELECT a.nClientID,b.nTransAccountID,b.mAmount mAmount \n");
		bufSQL.append("	FROM \n");
		bufSQL.append("		sett_Account a, \n");
		bufSQL.append("		(SELECT nTransAccountID,nTransDirection,SUM(mAmount) mAmount \n");
		bufSQL.append("		FROM sett_TransAccountDetail \n");
		bufSQL.append("		WHERE nStatusID = "+
			SETTConstant.TransactionStatus.CHECK+" \n");
		bufSQL.append(this.getStringSqlOfDate(qdcwi, nType));
		bufSQL.append("			AND nTransDirection = "+lDirecID+" \n");
		bufSQL.append("		GROUP BY nTransAccountID,nTransDirection) b, \n");
		bufSQL.append("		Client c \n");
		bufSQL.append("	WHERE a.ID = b.nTransAccountID \n");
		bufSQL.append("		AND a.nClientID = c.ID \n");
		bufSQL.append("		AND a.nAccountTypeID IN (select id from sett_accountType where nAccountGroupID in("+SETTConstant.AccountGroupType.CURRENT+","+
			SETTConstant.AccountGroupType.FIXED+","+SETTConstant.AccountGroupType.NOTIFY+","+SETTConstant.AccountGroupType.OTHERDEPOSIT+")) \n");
		bufSQL.append("		AND a.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("		AND a.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("		AND a.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+") "+
			(lDirecID==SETTConstant.DebitOrCredit.CREDIT?"aa,":"bb")+" \n");

		return bufSQL;
	}
	/**
	 * 取部分FROM的SQL
	 * @param  QueryDailyCapitalWhereInfo qdcwi, int nType
	 * @return StringBuffer
	 * @exception nothing
	 */
	private StringBuffer getPartSqlOfFrom(QueryDailyCapitalWhereInfo qdcwi, int nType) {
		StringBuffer bufSQL = new StringBuffer();
		StringBuffer bufTmp = new StringBuffer();
		bufSQL.append(" (SELECT aaa.nClientID,SUM(aaa.mAmountIn) "+
			(nType==1?"mDayAmountIn":"mMonthAmountIn")+", \n");
		bufSQL.append("SUM(aaa.mAmountOut) "+
			(nType==1?"mDayAmountOut":"mMonthAmountOut")+" FROM \n");
		bufSQL.append("	(SELECT aa.nClientID,aa.mAmount mAmountIn,bb.mAmount mAmountOut \n");
		bufTmp.append("FROM \n");
		bufTmp.append(this.getLitPartSqlOfFrom(qdcwi, nType, SETTConstant.DebitOrCredit.CREDIT));
		bufTmp.append(this.getLitPartSqlOfFrom(qdcwi, nType, SETTConstant.DebitOrCredit.DEBIT));
		bufSQL.append(bufTmp);
		bufSQL.append("	WHERE aa.nTransAccountID = bb.nTransAccountID(+) \n");
		bufSQL.append("	UNION \n");
		bufSQL.append("	SELECT bb.nClientID,aa.mAmount mAmountIn,bb.mAmount mAmountOut \n");
		bufSQL.append(bufTmp);
		bufSQL.append("	WHERE aa.nTransAccountID(+) = bb.nTransAccountID) aaa \n");
		bufSQL.append("GROUP BY aaa.nClientID) "+(nType==1?"aaaa,":"bbbb,")+" \n");

		return bufSQL;
	}

	/**
	 * 取内部客户数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return nothing
	 * @exception nothing
	 */
	private void getStringSqlForInner(QueryDailyCapitalWhereInfo qdcwi) {
		m_sbSelect = new StringBuffer();
		m_sbFrom = new StringBuffer();
		m_sbWhere = new StringBuffer();
		// SELECT
		m_sbSelect.append(" cccc.id,cccc.sName DepositCorp, \n");
		m_sbSelect.append("ROUND(NVL(aaaa.mDayAmountIn,0.0)/10000,0) DailyHandIn, \n");
		m_sbSelect.append("ROUND(NVL(aaaa.mDayAmountOut,0.0)/10000,0) DailyCost, \n");
		m_sbSelect.append("ROUND(NVL(bbbb.mMonthAmountIn,0.0)/10000,0) MonthHandIn, \n");
		m_sbSelect.append("ROUND(NVL(bbbb.mMonthAmountOut,0.0)/10000,0) MonthCost, \n");
		m_sbSelect.append("ROUND((NVL(aaaa.mDayAmountIn,0.0)+NVL(bbbb.mMonthAmountIn,0.0))/10000,0) MonthSumHandIn, \n");
		m_sbSelect.append("ROUND((NVL(aaaa.mDayAmountOut,0.0)+NVL(bbbb.mMonthAmountOut,0.0))/10000,0) MonthSumCost, \n");
		m_sbSelect.append("ROUND(((NVL(aaaa.mDayAmountIn,0.0)+NVL(bbbb.mMonthAmountIn,0.0))- \n");
		m_sbSelect.append("(NVL(aaaa.mDayAmountOut,0.0)+NVL(bbbb.mMonthAmountOut,0.0)))/10000,0) NowDeposit, \n");
		m_sbSelect.append("ROUND(NVL(dddd.mBalance,0.0)/10000,0) PreMonthBalance, \n");
		m_sbSelect.append("ROUND(((NVL(aaaa.mDayAmountIn,0.0)+NVL(bbbb.mMonthAmountIn,0.0))- \n");
		m_sbSelect.append("(NVL(aaaa.mDayAmountOut,0.0)+NVL(bbbb.mMonthAmountOut,0.0))+ \n");
		m_sbSelect.append("(NVL(dddd.mBalance,0.0)))/10000,0) NowDayBalance \n");
		// FROM
		m_sbFrom.append(this.getPartSqlOfFrom(qdcwi, (int)1));
		m_sbFrom.append(this.getPartSqlOfFrom(qdcwi, (int)2));
		m_sbFrom.append("Client cccc, \n");
		m_sbFrom.append(this.getBalancePartSql(qdcwi));
		// WHERE
		m_sbWhere.append(" dddd.nClientID = bbbb.nClientID(+) \n");
		m_sbWhere.append("	AND dddd.nClientID = aaaa.nClientID(+) \n");
		m_sbWhere.append("	AND dddd.nClientID = cccc.ID \n");
	}

	/**
	 * 取内部客户数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception throws SQLException
	 */
	public Collection getInnerInfoFromDB(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		this.getStringSqlForInner(qdcwi);
		String strSQL = "SELECT"+m_sbSelect+"FROM"+m_sbFrom+"WHERE"+m_sbWhere;
		log.info("SQL="+strSQL);

		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();

			long lIndex = 0;
			while (rs.next()) {
				QueryInnerAccountInfo info = new QueryInnerAccountInfo();
				// 序号
				info.setNo(++lIndex);
				// 存款单位
				info.setDepositCorp(rs.getString("DepositCorp"));
				// 本月累计-存入
				info.setMonthHandIn(rs.getDouble("MonthHandIn"));
				// 本月累计-支取
				info.setMonthCost(rs.getDouble("MonthCost"));
				// 当日变动-存入
				info.setDailyHandIn(rs.getDouble("DailyHandIn"));
				// 当日变动-支取
				info.setDailyCost(rs.getDouble("DailyCost"));
				// 本月合计-存入
				info.setMonthSumHandIn(rs.getDouble("MonthSumHandIn"));
				// 本月合计-支取
				info.setMonthSumCost(rs.getDouble("MonthSumCost"));
				// 当前存款
				info.setNowDeposit(rs.getDouble("NowDeposit"));
				// 上月余额
				info.setPreMonthBalance(rs.getDouble("PreMonthBalance"));
				// 当日余额
				info.setNowDayBalance(rs.getDouble("NowDayBalance"));

				list.add(info);
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return list.size() > 0?list:null;
	}

	/** 人民币存款周报 */
	/**
	 * 取得上周账户余额
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lClientID
	 * @return double
	 * @exception SQLException
	 */
	public double getPreBalanceByClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lClientID) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT b.nClientID,ROUND(SUM(NVL(a.mBalance,0.0))/10000,0) mBalance \n");
		bufSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
		bufSQL.append("WHERE a.nAccountID = b.ID \n");
		bufSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("	AND b.nClientID = "+lClientID+" \n");
		bufSQL.append("	AND b.nAccountTypeID IN ("+
			DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		bufSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(
			DataFormat.getPreviousDate(qdcwi.getDateFrom()))+
			"','YYYY/MM/DD HH24:MI:SS') \n");
		bufSQL.append("GROUP BY b.nClientID \n");
		log.print("SQL="+bufSQL.toString());
		try {
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return mBalance;
	}

	/**
	 * 取得上周账户余额
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID
	 * @return double
	 * @exception SQLException
	 */
	public double getPreBalanceByClientID(QueryDailyCapitalWhereInfo qdcwi, long lClientID) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT b.nClientID,ROUND(SUM(NVL(a.mBalance,0.0))/10000,0) mBalance \n");
		bufSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
		bufSQL.append("WHERE a.nAccountID = b.ID \n");
		bufSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("	AND b.nClientID = "+lClientID+" \n");
		bufSQL.append("	AND b.nAccountTypeID IN ("+
			DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		bufSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(
			DataFormat.getPreviousDate(qdcwi.getDateFrom()))+
			"','YYYY/MM/DD HH24:MI:SS') \n");
		bufSQL.append("GROUP BY b.nClientID \n");
		log.print("SQL="+bufSQL.toString());
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return mBalance;
	}

	/**
	 * 取得上周账户余额
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn, long
	 * lClientID, long lAccountTypeID
	 * @return double
	 * @exception SQLException
	 */
	public double getNowBalanceByClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lClientID, long lAccountTypeID) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT b.nClientID,ROUND(SUM(NVL(a.mBalance,0.0))/10000,0) mBalance \n");
		bufSQL.append("FROM sett_DailyAccountBalance a,sett_Account b,sett_accountType c \n");
		bufSQL.append("WHERE a.nAccountID = b.ID \n");
		bufSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("	AND b.nClientID = "+lClientID+" \n");
		bufSQL.append("	AND b.nAccountTypeID = c.id \n");
		if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
		        ||SETTConstant.AccountType.isOtherDepositAccountType(lAccountTypeID))
		{
			bufSQL.append("	AND b.nAccountTypeID IN (select id from sett_accountType where nAccountGroupID=" + SETTConstant.AccountGroupType.CURRENT + ") \n");
		}
		else if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
		{
			bufSQL.append("	AND b.nAccountTypeID IN (select id from sett_accountType where nAccountGroupID=" + SETTConstant.AccountGroupType.FIXED + ") \n");
		}
		else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
		{
			bufSQL.append("	AND b.nAccountTypeID IN (select id from sett_accountType where nAccountGroupID=" + SETTConstant.AccountGroupType.NOTIFY + ") \n");
		}
		else if (lAccountTypeID == -1)
		{
			bufSQL.append("	AND b.nAccountTypeID IN ("+
					DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		}
		bufSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(
			DataFormat.getPreviousDate(qdcwi.getDateTo()))+
			"','YYYY/MM/DD HH24:MI:SS') \n");
		bufSQL.append("GROUP BY b.nClientID \n");
		log.print("SQL="+bufSQL.toString());
		try {
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return mBalance;
	}

	/**
	 * 取得上周账户余额
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID, long
	 * lAccountTypeID
	 * @return double
	 * @exception SQLException
	 */
	public double getNowBalanceByClientID(QueryDailyCapitalWhereInfo qdcwi, long lClientID, long lAccountTypeID) throws SQLException {
		double mBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT b.nClientID,ROUND(SUM(NVL(a.mBalance,0.0))/10000,0) mBalance \n");
		bufSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
		bufSQL.append("WHERE a.nAccountID = b.ID \n");
		bufSQL.append("	AND b.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("	AND b.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("	AND b.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("	AND b.nClientID = "+lClientID+" \n");
		if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
		        ||SETTConstant.AccountType.isOtherDepositAccountType(lAccountTypeID))
		{
			bufSQL.append("	AND b.nAccountTypeID IN (select id from sett_accountType where nAccountGroupID=" + SETTConstant.AccountGroupType.CURRENT + ") \n");
		}
		else if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
		{
			bufSQL.append("	AND b.nAccountTypeID IN (select id from sett_accountType where nAccountGroupID=" + SETTConstant.AccountGroupType.FIXED + ") \n");
		}
		else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
		{
			bufSQL.append("	AND b.nAccountTypeID IN (select id from sett_accountType where nAccountGroupID=" + SETTConstant.AccountGroupType.NOTIFY + ") \n");
		}
		else if (lAccountTypeID == -1)
		{
			bufSQL.append("	AND b.nAccountTypeID IN ("+
					DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		}
		bufSQL.append("	AND a.dtDate = TO_DATE('"+DataFormat.getDateString(
			DataFormat.getPreviousDate(qdcwi.getDateTo()))+
			"','YYYY/MM/DD HH24:MI:SS') \n");
		bufSQL.append("GROUP BY b.nClientID \n");
		log.print("SQL="+bufSQL.toString());
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return mBalance;
	}

	/**
	 * 取得本周账户交易发生额
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lClientID
	 * @return double
	 * @exception SQLException
	 */
	public double getAmountByClientID(QueryDailyCapitalWhereInfo qdcwi, Connection conn, long lClientID, long lDirection) throws SQLException {
		double mAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT a.nClientID,ROUND(SUM(NVL(b.mAmount,0.0))/10000,0) mAmount \n");
		bufSQL.append("FROM \n");
		bufSQL.append("	sett_Account a, \n");
		bufSQL.append("	(SELECT nTransAccountID,nTransDirection,SUM(NVL(mAmount,0.0)) mAmount \n");
		bufSQL.append("	FROM sett_TransAccountDetail \n");
		bufSQL.append("	WHERE nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		bufSQL.append("		AND nTransDirection = "+SETTConstant.DebitOrCredit.CREDIT+" \n");
		bufSQL.append("	GROUP BY nTransAccountID,nTransDirection) b \n");
		bufSQL.append("WHERE a.ID = b.nTransAccountID \n");
		bufSQL.append("	AND a.nClientID = "+lClientID+" \n");
		bufSQL.append("	AND a.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		bufSQL.append("	AND a.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("	AND a.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("	AND a.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("GROUP BY a.nClientID \n");
		log.print("SQL="+bufSQL.toString());
		try {
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return mAmount;
	}

	/**
	 * 取得本周账户交易发生额
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lClientID
	 * @return double
	 * @exception SQLException
	 */
	public double getAmountByClientID(QueryDailyCapitalWhereInfo qdcwi, long lClientID, long lDirection) throws SQLException {
		double mAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT a.nClientID,ROUND(SUM(NVL(b.mAmount,0.0))/10000,0) mAmount \n");
		bufSQL.append("FROM \n");
		bufSQL.append("	sett_Account a, \n");
		bufSQL.append("	(SELECT nTransAccountID,nTransDirection,SUM(NVL(mAmount,0.0)) mAmount \n");
		bufSQL.append("	FROM sett_TransAccountDetail \n");
		bufSQL.append("	WHERE nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
		bufSQL.append("		AND nTransDirection = "+SETTConstant.DebitOrCredit.CREDIT+" \n");
		bufSQL.append("	GROUP BY nTransAccountID,nTransDirection) b \n");
		bufSQL.append("WHERE a.ID = b.nTransAccountID \n");
		bufSQL.append("	AND a.nClientID = "+lClientID+" \n");
		bufSQL.append("	AND a.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
		bufSQL.append("	AND a.nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("	AND a.nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("	AND a.nCheckStatusID = "+SETTConstant.AccountCheckStatus.CHECK+" \n");
		bufSQL.append("GROUP BY a.nClientID \n");
		log.print("SQL="+bufSQL.toString());
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(bufSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return mAmount;
	}

	/**
	 * 取公司合计SQL
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lAccountTypeID, 
	 * @param  long lDirectionID
	 * @return Collection
	 * @exception throws SQLException
	 */
	private StringBuffer getChildAllAmountSql(
		QueryDailyCapitalWhereInfo qdcwi,
		long lDirectionID,
		long lAccountTypeID) {
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT ROUND(SUM(NVL(mAmount,0.0))/10000,0) mAmount \n");
		bufSQL.append("FROM sett_GlEntry \n");
		if (lAccountTypeID == SETTConstant.AccountGroupType.CURRENT) {
			bufSQL.append("WHERE SUBSTR(sSubjectCode,0,4) = '2012' \n");
		} else if (lAccountTypeID == SETTConstant.AccountGroupType.FIXED) {
			bufSQL.append("WHERE SUBSTR(sSubjectCode,0,7) = '2012001' \n");
		} else if (lAccountTypeID == SETTConstant.AccountGroupType.NOTIFY) {
			bufSQL.append("WHERE SUBSTR(sSubjectCode,0,7) = '2013002' \n");
		}
		bufSQL.append("	AND nTransDirection = "+lDirectionID+" \n");
		bufSQL.append("	AND nStatusID = 3 \n");
		bufSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("	AND nCurrencyID = "+qdcwi.getCurrencyID()+" \n");
		bufSQL.append("	AND dtExecute BETWEEN TO_DATE('"+
			DataFormat.getDateString(qdcwi.getDateFrom())+
			"','YYYY/MM/DD HH24:MI:SS') \n");
		bufSQL.append("	AND TO_DATE('"+
			DataFormat.getDateString(qdcwi.getDateTo())+
			"','YYYY/MM/DD HH24:MI:SS') \n");
		return bufSQL;
	}
	/**
	 * 取子公司SQL-合计
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lAccountTypeID, 
	 * @param  long lDirectionID
	 * @return Collection
	 * @exception throws SQLException
	 */
	private StringBuffer getChildAllBalanceSql(
		QueryDailyCapitalWhereInfo qdcwi, 
		long lAccountTypeID) {
		StringBuffer bufSQL = new StringBuffer();
		bufSQL.append("SELECT ROUND(SUM(DECODE(nBalanceDirection, \n");
		bufSQL.append("	"+SETTConstant.DebitOrCredit.CREDIT+",-(NVL(mDebitBalance,0.0)+NVL(mCreditBalance,0.0))/10000, \n");
		bufSQL.append("	"+SETTConstant.DebitOrCredit.DEBIT+",(NVL(mDebitBalance,0.0)+NVL(mCreditBalance,0.0))/10000)),0) mBalance \n");
		bufSQL.append("FROM  sett_GlBalance \n");
		if (lAccountTypeID == 0) {
			bufSQL.append("WHERE (SUBSTR(sGlSubjectCode,0,4) = '2012' \n");
			bufSQL.append("	OR SUBSTR(sGlSubjectCode,0,6) = '2013001' \n");
			bufSQL.append("	OR SUBSTR(sGlSubjectCode,0,6) = '2013002') \n");
		} else if (lAccountTypeID == 1) {
			bufSQL.append("WHERE SUBSTR(sGlSubjectCode,0,4) = '2012' \n");
		} else if (lAccountTypeID == 2) {
			bufSQL.append("WHERE SUBSTR(sGlSubjectCode,0,6) = '2013001' \n");
		} else if (lAccountTypeID == 3) {
			bufSQL.append("WHERE SUBSTR(sGlSubjectCode,0,6) = '2013002' \n");
		}
		bufSQL.append("	AND nOfficeID = "+qdcwi.getOfficeID()+" \n");
		bufSQL.append("	AND nCurrencyID ="+qdcwi.getCurrencyID()+" \n");
		if (lAccountTypeID == 0) {
			bufSQL.append("	AND dtGlDate = TO_DATE('"+DataFormat.getDateString(
				DataFormat.getPreviousDate(qdcwi.getDateFrom()))+
				"','YYYY/MM/DD HH24:MI:SS') \n");
		} else {
			bufSQL.append("	AND dtGlDate = TO_DATE('"+DataFormat.getDateString(
				DataFormat.getPreviousDate(qdcwi.getDateFrom()))+
				"','YYYY/MM/DD HH24:MI:SS') \n");
		}
		return bufSQL;
	}
	/**
	 * 取子公司数据-金额
	 * @param  QueryDailyCapitalWhereInfo qdcwi, long lAccountTypeID, 
	 * @param  long lDirectionID
	 * @return double
	 * @exception throws SQLException
	 */
	private double getChildAllAmount(
		QueryDailyCapitalWhereInfo qdcwi,
		Connection conn,
		long lDirectionID,
		long lAccountGroupID) 
		throws SQLException {
		double dAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer strSQL = this.getChildAllAmountSql(qdcwi, lDirectionID,lAccountGroupID);
			log.print("SQL="+strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
	
			while (rs.next()) {
				dAmount = rs.getDouble("mAmount");
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return dAmount;
	}
	/**
	 * 取子公司数据-余额
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn,
	 * @param  long lAccountTypeID,long lDirectionID
	 * @return double
	 * @exception throws SQLException
	 */
	private double getChildAllBalance(
		QueryDailyCapitalWhereInfo qdcwi,
		Connection conn, 
		long lAccountTypeID) 
		throws SQLException {
		double dBalance = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer strSQL = this.getChildAllBalanceSql(qdcwi, lAccountTypeID);
			log.print("SQL="+strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				dBalance = rs.getDouble("mBalance");
			}
		} catch (SQLException se) {
			throw se;
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return dBalance;
	}
	/**
	 * 取子公司数据-合计
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return QCurrencyDepositInfo
	 * @exception nothing
	 */
	public Collection getChildAllInfo(QueryDailyCapitalWhereInfo qdcwi) throws SQLException {
		ArrayList list = new ArrayList();
		Connection conn = null;
		QCurrencyDepositInfo info = new QCurrencyDepositInfo();
		try {
			conn = this.getConnection();
			double currentAmountCredit = this.getChildAllAmount(qdcwi, conn, 
			SETTConstant.DebitOrCredit.CREDIT,SETTConstant.AccountGroupType.CURRENT);
			double currentAmountDebit = this.getChildAllAmount(qdcwi, conn, 
			SETTConstant.DebitOrCredit.DEBIT,SETTConstant.AccountGroupType.CURRENT);
			double fixedAmountCredit = this.getChildAllAmount(qdcwi, conn, 
			SETTConstant.DebitOrCredit.CREDIT,SETTConstant.AccountGroupType.FIXED);
			double fixedAmountDebit = this.getChildAllAmount(qdcwi, conn, 
			SETTConstant.DebitOrCredit.DEBIT,SETTConstant.AccountGroupType.FIXED);
			double notifyAmountCredit = this.getChildAllAmount(qdcwi, conn, 
			SETTConstant.DebitOrCredit.CREDIT,SETTConstant.AccountGroupType.NOTIFY);
			double notifyAmountDebit = this.getChildAllAmount(qdcwi, conn, 
			SETTConstant.DebitOrCredit.DEBIT,SETTConstant.AccountGroupType.NOTIFY);
			double sumAmountCredit = currentAmountCredit+fixedAmountCredit+notifyAmountCredit;
			double sumAmountDebit = currentAmountDebit+fixedAmountDebit+notifyAmountDebit;
			// 存款单位
			info.setDepositCorp("合计");
			// 上周存款余额
			info.setPreWeeklyBalance(this.getChildAllBalance(qdcwi, conn, (long)0));
			// 本周-存入
			info.setWeeklyHandIn(sumAmountCredit);
			// 本周-支取
			info.setWeeklyCost(sumAmountDebit);
			// 本周存款余额-活期存款
			double CurrencyBalance = this.getChildAllBalance(qdcwi, conn, (long)1)+
				currentAmountCredit-currentAmountDebit;
			info.setCurrencyBalance(CurrencyBalance);
			// 本周存款余额-定期存款
			double FixedBalance = this.getChildAllBalance(qdcwi, conn, (long)2)+
				fixedAmountCredit-fixedAmountDebit;
			info.setFixedBalance(FixedBalance);
			// 本周存款余额-通知存款
			double NotifyBalance = this.getChildAllBalance(qdcwi, conn, (long)3)+
				notifyAmountCredit-notifyAmountDebit;
			info.setNotifyBalance(NotifyBalance);
			// 合计
			info.setSumBalance(CurrencyBalance+FixedBalance+NotifyBalance);
			list.add(info);
		} catch (SQLException se) {
			throw se;
		}
		return list.size() > 0?list:null;
	}

	/**
	 * 取内部数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception throws Exception
	 */
	public Collection getInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws Exception {
		
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp queryDate = qdcwi.getQueryDate();
		long lIndex = 0;
		try {
			StringBuffer strSQL = new StringBuffer();
			if (qdcwi.getIsCheckedType() == 3) {
				strSQL.append("SELECT b.ID,b.sName,b.nSettClientTypeID nClientTypeID,a.sName sClientName \n");
				strSQL.append("FROM \n");
				strSQL.append("	sett_ClientType a, \n");
			} else if (qdcwi.getIsCheckedType() == 6) {
				strSQL.append("SELECT b.ID,b.sName,b.nLoanClientTypeID nClientTypeID,a.sName sClientName \n");
				strSQL.append("FROM \n");
				strSQL.append("	loan_ClientType a, \n");
			}
			strSQL.append("	(SELECT DISTINCT b.ID,b.sName,b.nSettClientTypeID,b.nLoanClientTypeID \n");
			strSQL.append("	FROM sett_Account a,Client b \n");
			strSQL.append("	WHERE a.nClientID(+) = b.ID \n");
			//strSQL.append("		AND a.nAccountTypeID IN ("+
			//	DataFormat.getStringWithTagByLongArray(
			//	SETTConstant.AccountType.getAllCode(qdcwi.getCurrencyID()))+") \n");
			if (qdcwi.getParentCorpID1() != -1) {
				strSQL.append("		AND b.nParentCorpID1 = "+qdcwi.getParentCorpID1()+" \n");
			}
			if (qdcwi.getParentCorpID2() != -1) {
				strSQL.append("		AND b.nParentCorpID2 = "+qdcwi.getParentCorpID2()+" \n");
			}
			if (qdcwi.getIsCheckedType() == 3) {
				if (qdcwi.getSettClientTypeID() != -1) {
					strSQL.append("		AND b.nSettClientTypeID = "+qdcwi.getSettClientTypeID()+" \n");
				}
				strSQL.append("	) b \n");
				strSQL.append("WHERE a.ID(+) = b.nSettClientTypeID \n");
				strSQL.append("ORDER BY b.nSettClientTypeID,b.ID \n");
			} else if (qdcwi.getIsCheckedType() == 6) {
				if (qdcwi.getLoanClientTypeID() != -1) {
					strSQL.append("		AND b.nLoanClientTypeID = "+qdcwi.getLoanClientTypeID()+" \n");
				}
				strSQL.append("	) b \n");
				strSQL.append("WHERE a.ID(+) = b.nLoanClientTypeID \n");
				strSQL.append("ORDER BY b.nLoanClientTypeID,b.ID \n");
			}
			log.print("SQL=" + strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				QCurrencyDepositInfo info = new QCurrencyDepositInfo();
				//单位名称
				long lClientID = rs.getLong("ID");
				// 序号
				info.setNo(++lIndex);
				// 存款单位
				info.setDepositCorp(rs.getString("sName"));
				// 上周存款余额
				double PreWeeklyBalance = this.getPreBalanceByClientID(qdcwi, conn, lClientID);
				info.setPreWeeklyBalance(PreWeeklyBalance);
				// 本周-存入
				double WeeklyHandIn = this.getAmountByClientID(qdcwi, conn, lClientID, SETTConstant.DebitOrCredit.CREDIT);
				info.setWeeklyHandIn(WeeklyHandIn);
				// 本周-支取
				double WeeklyCost = this.getAmountByClientID(qdcwi, conn, lClientID, SETTConstant.DebitOrCredit.DEBIT);
				info.setWeeklyCost(WeeklyCost);
				// 本周存款余额-活期存款
				double CurrencyBalance = this.getNowBalanceByClientID(qdcwi, conn, lClientID, SETTConstant.AccountGroupType.CURRENT);
				info.setCurrencyBalance(CurrencyBalance);
				// 本周存款余额-定期存款
				double FixedBalance = this.getNowBalanceByClientID(qdcwi, conn, lClientID, SETTConstant.AccountGroupType.FIXED);
				info.setFixedBalance(FixedBalance);
				// 本周存款余额-通知存款
				double NotifyBalance = this.getNowBalanceByClientID(qdcwi, conn, lClientID, SETTConstant.AccountGroupType.NOTIFY);
				info.setNotifyBalance(NotifyBalance);
				// 合计
				info.setSumBalance(CurrencyBalance+FixedBalance+NotifyBalance);
				// 客户分类ID
				info.setClientTypeID(rs.getLong("nClientTypeID"));
				// 客户分类名称
				info.setClientTypeName(rs.getString("sClientName"));

				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();throw new Exception("取内部客户信息出错" + e.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
		}
		return list.size() > 0?list:null;
		
	}

	/**
	 * 取内部数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi, Connection conn
	 * @return Collection
	 * @exception throws Exception
	 */
	public Collection getInsideInfo(QueryDailyCapitalWhereInfo qdcwi, Connection conn) throws Exception {
		return this.getInfo(qdcwi, conn);
	}

	/**
	 * 取内部数据
	 * @param  QueryDailyCapitalWhereInfo qdcwi
	 * @return Collection
	 * @exception throws Exception
	 */
	public Collection getInsideInfo(QueryDailyCapitalWhereInfo qdcwi) throws Exception {
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = this.getConnection();
			list = (ArrayList)this.getInfo(qdcwi, conn);
		} catch (Exception e) {
			throw e;
		} finally {
			this.cleanup(conn);
		}
		return list.size() > 0?list:null;
	}
}