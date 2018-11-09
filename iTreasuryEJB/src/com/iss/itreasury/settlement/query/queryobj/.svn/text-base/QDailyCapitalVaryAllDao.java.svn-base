/*
 * Created on 2003-12-10
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.ArrayList;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.query.resultinfo.QueryDailyCapitalInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryDailyCapitalWhereInfo;

/**
 * @author kewen hu 2003-12-15
 * To change the template for this generated type
 * comment go to Window&gt; Preferences&gt;Java&gt;Code Generation&gt;Code and
 * Comments
 */
public class QDailyCapitalVaryAllDao extends BaseQueryObject {
	private Connection conn = null;

	/**
	 * 构造函数
	 * @param nothing
	 * @return nothing
	 * @see java.lang.Object#Object()
	 * @exception nothing
	 */
	public QDailyCapitalVaryAllDao() {
		super();
	}

	/**
	 * 构造函数
	 * @param nothing
	 * @return nothing
	 * @see java.lang.Object#Object()
	 * @exception nothing
	 */
	public QDailyCapitalVaryAllDao(Connection conn) {
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
		return amountTrim(dAmount, 0);
	}

	/*****取内部客户数据******/
	/**
	 * 取当日上交
	 * @param  Timestamp date, long lClientID
	 * @return double
	 * @exception nothing
	 */	
	private double seekDailyHandIn(Timestamp date, long lClientID)
		throws Exception {
		double dAmount = 0.0;
		try {
			long nDirection = SETTConstant.DebitOrCredit.CREDIT;
			dAmount = this.queryInsideDailyAmount(nDirection, lClientID, date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("取当日上交数据出错:" + e.getMessage());
		}
		return dAmount;
	}

	/**
	 * 取当日支款
	 * @param  Timestamp date, long lClientID
	 * @return double
	 * @exception throws Exception
	 */
	private double seekDailyCost(Timestamp date, long lClientID) throws Exception {
		double dAmount = 0.0;
		try {
			long nDirection = SETTConstant.DebitOrCredit.DEBIT;
			dAmount = this.queryInsideDailyAmount(nDirection, lClientID, date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("取当日支款数据出错:" + e.getMessage());
		}
		return dAmount;
	}

	/**
	 * 取本月上交
	 * @param  Timestamp date, long lClientID
	 * @return double
	 * @exception throws Exception
	 */
	private double seekMonthHandIn(Timestamp date, long lClientID)
		throws Exception {
		double dAmount = 0.0;
		try {
			long nDirection = SETTConstant.DebitOrCredit.CREDIT;
			dAmount = this.queryInsideMonthAmount(nDirection, lClientID, date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("取本月上交数据出错:" + e.getMessage());
		}
		return dAmount;
	}

	/**
	 * 取本月支款
	 * @param  Timestamp date, long lClientID
	 * @return double
	 * @exception throws Exception
	 */
	private double seekMonthCost(Timestamp date, long lClientID) throws Exception {
		double dAmount = 0.0;
		try {
			long nDirection = SETTConstant.DebitOrCredit.DEBIT;
			dAmount = this.queryInsideMonthAmount(nDirection, lClientID, date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("取本月支款数据出错:" + e.getMessage());
		}
		return dAmount;
	}

	/**
	 * 取内部数据
	 * @param  Timestamp date
	 * @return Collection
	 * @exception throws Exception
	 */
	public Collection getInsideInfo(QueryDailyCapitalWhereInfo qdcwi) throws Exception {
		Connection conn = null;
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lIndex = 0;
		try {
			conn = this.getConnection();
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
				QueryDailyCapitalInfo info = new QueryDailyCapitalInfo();
				//单位名称
				long lClientID = rs.getLong("ID");
				Timestamp queryDate = qdcwi.getQueryDate();
				info.setID(++lIndex);
				info.setName(rs.getString("sName"));
				info.setClientTypeID(rs.getLong("nClientTypeID"));
				info.setClientTypeName(rs.getString("sClientName"));
				//当日变动-上交 1
				double dmDailyHandIn = this.seekDailyHandIn(queryDate, lClientID);
				info.setDailyHandIn(dmDailyHandIn);
				//当日变动-支款 2
				double dmDailyCost = this.seekDailyCost(queryDate, lClientID);
				info.setDailyCost(dmDailyCost);
				//本月累计-上交 3
				double dmMonthHandIn = this.seekMonthHandIn(queryDate, lClientID);
				info.setMonthHandIn(dmMonthHandIn);
				//本月累计-支款 4
				double dmMonthCost = this.seekMonthCost(queryDate, lClientID);
				info.setMonthCost(dmMonthCost);
				//本月预算-上交 5
				double dmMonthBudgetHandIn = 0.0;
				info.setMonthBudgetHandIn(dmMonthBudgetHandIn);
				//本月预算-支款 6
				double dmMonthBudgetCost = 0.0;
				info.setMonthBudgetCost(dmMonthBudgetCost);
				//与预算差额-上交 7
				double dmMarginHandIn = dmMonthHandIn - dmMonthBudgetHandIn;
				info.setMarginHandIn(dmMarginHandIn);
				//与预算差额-支款 8
				double dmMarginCost = dmMonthCost - dmMonthBudgetCost;
				info.setMarginCost(dmMarginCost);
				//月初余额 9
				double dmMonthBalance =
					this.seekBalanceOfBeginOfMonth(queryDate, lClientID);
				info.setMonthBalance(dmMonthBalance);
				//今日余额 10=9+3-4
				double dmTodayBalance =
					dmMonthBalance + dmMonthHandIn - dmMonthCost;
				info.setTodayBalance(dmTodayBalance);
				//备注
				info.setAbstract("");				

				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("取内部客户信息出错" + e.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return list.size() > 0?list:null;
	}

	/**
	 * 查询内部客户当日数据
	 * @param  long nDirection, long lClientID, Timestamp date
	 * @return double
	 * @exception throws Exception
	 */
	private double queryInsideDailyAmount(
		long nDirection,
		long lClientID,
		Timestamp date)
		throws Exception {
		double mDailyAmount = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("SELECT NVL(SUM(a.mAmount),0.0) mAmount \n");
			strSQL.append("FROM sett_TransAccountDetail a,sett_Account b \n");
			strSQL.append("WHERE a.nTransAccountID = b.ID \n");
			strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
			strSQL.append("	AND b.nClientID = "+lClientID+" \n");
			if (date != null) {
				strSQL.append("	AND a.dtExecute = TO_DATE('"+
					DataFormat.getDateString(date)+"','YYYY/MM/DD HH24:MI:SS') \n");
			}
			strSQL.append("	and a.nTransDirection = "+nDirection+" \n");
			log.print("SQL=" + strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mDailyAmount = rs.getDouble("mAmount");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询内部客户日数据出错:" + e.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return mDailyAmount;
	}

	/**
	 * 查询内部客户本月累计数据
	 * @param  long nDirection, long lClientID, Timestamp date
	 * @return double
	 * @exception throws Exception
	 */
	private double queryInsideMonthAmount(
		long nDirection,
		long lClientID,
		Timestamp date)
		throws Exception {
		double mSumAmount = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//取得本月的最初一天和最后一天 
			conn = this.getConnection();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("SELECT NVL(SUM(a.mAmount),0.0) mAmount \n");
			strSQL.append("FROM sett_TransAccountDetail a,sett_Account b \n");
			strSQL.append("WHERE a.nTransAccountID = b.ID \n");
			strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
			strSQL.append("	AND b.nClientID = "+lClientID+" \n");
			if (date != null) {
				String sFirstDay = DataFormat.getDateString(date);
				sFirstDay = sFirstDay.substring(0, sFirstDay.length()-2)+"01";
				String sLastDay = DataFormat.getDateString(date);
				strSQL.append("	AND a.dtExecute >= TO_DATE('"+
					sFirstDay+"','YYYY/MM/DD HH24:MI:SS') \n");
				strSQL.append("	AND a.dtExecute <= TO_DATE('"+
				sLastDay+"','YYYY/MM/DD HH24:MI:SS') \n");
			}
			strSQL.append("	and a.nTransDirection = "+nDirection+" \n");
			log.print("SQL=" + strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mSumAmount = rs.getDouble("mAmount");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询内部客户月数据出错:" + e.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return mSumAmount;
	}

	/**
	 * 查询月初余额
	 * @param  Timestamp date, long lClientID
	 * @return double
	 * @exception throws Exception
	 */
	private double seekBalanceOfBeginOfMonth(
		Timestamp date,
		long lClientID)
		throws Exception {
		double mBalance = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("SELECT NVL(SUM(a.mBalance),0.0) mBalance \n");
			strSQL.append("FROM sett_DailyAccountBalance a,sett_Account b \n");
			strSQL.append("WHERE a.nAccountID = b.ID \n");
			strSQL.append("	AND b.nAccountTypeID IN ("+DataFormat.getStringWithTagByLongArray(this.getAccountTypeCode())+") \n");
			strSQL.append("	AND b.nClientID = "+lClientID+" \n");
			if (date != null) {
				strSQL.append("	AND a.dtDate = TO_DATE('"+
				DataFormat.getLastDateString(DataFormat.getPreviousMonth(date, 1))+"','YYYY/MM/DD HH24:MI:SS') \n");
			}
			log.print("SQL=" + strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				mBalance = rs.getDouble("mBalance");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询月初余额出错:" + e.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		return mBalance;
	}
}