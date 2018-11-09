/*
 * Created on 2003-12-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.settlement.query.paraminfo.QCounterTransInfo;
import com.iss.itreasury.settlement.query.resultinfo.DailyGatherInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QCountertTransDao extends BaseQueryObject
{
	Log4j logger = null;
	/**
	 *  
	 */
	public QCountertTransDao()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	public Collection queryCounterTransByTransType(QCounterTransInfo paramInfo)
	{
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			//得到账户类型，账户编号
			conn = Database.getConnection();
			//added by jiangwei
			sbSQL.setLength(0);
			/*TOCONFIG-TODELETE*/
			/*
			if(Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append(" select TransactionTypeID,sum(SumPay) SumPay,sum(SumReceive) SumReceive,sum(nCount) nCount from \n");
				sbSQL.append(" ( \n");
			}*/
			sbSQL.append("    select TransactionTypeID,sum(PayAmount) SumPay,sum(ReceiveAmount) SumReceive,count(TransNo) nCount \n ");
			sbSQL.append("    from Sett_VTransaction \n ");
			sbSQL.append("    where OfficeID=" + paramInfo.getOfficeID() + " and CurrencyID=" + paramInfo.getCurrencyID() + " \n ");
			sbSQL.append("          and StatusID  in (3,4,5,6,7) and Execute between ? and ?  \n ");
			if (paramInfo.getUserID() > 0)
			{
				sbSQL.append("         and InputUserID=" + paramInfo.getUserID() + " \n ");
			}
			sbSQL.append("    group by TransactionTypeID  \n ");
			/*
			if(Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append("  union\n");
				sbSQL.append("    select nTransactionTypeID TransactionTypeID,sum(mAmount) SumPay,sum(mAmount) SumReceive,count(sTransNo) nCount \n");
				sbSQL.append("    from Sett_TransSecurities \n");
				sbSQL.append("    where nOfficeID=" + paramInfo.getOfficeID() + " and nCurrencyID=" + paramInfo.getCurrencyID() + " \n ");
				sbSQL.append("          and nStatusID  in (3,4,5,6,7) and dtExecute between ? and ?  \n ");
				if (paramInfo.getUserID() > 0)
				{
					sbSQL.append("         and nInputUserID=" + paramInfo.getUserID() + " \n ");
				}
				sbSQL.append("    group by nTransactionTypeID \n");
				sbSQL.append(" ) group by TransactionTypeID\n");
			}
			*/
			//查询记录
			String strSQLRecord = "select * from   ( " + sbSQL.toString();
			strSQLRecord = strSQLRecord + " ) ";
			switch ((int) paramInfo.getOrderParam())
			{
				case 1 :
				default :
					strSQLRecord = strSQLRecord + " order by TransactionTypeID ";
					break;
				case 2 :
					strSQLRecord = strSQLRecord + " order by SumPay ";
					break;
				case 3 :
					strSQLRecord = strSQLRecord + " order by SumReceive ";
					break;
				case 4 :
					strSQLRecord = strSQLRecord + " order by nCount ";
					break;
			}
			if (paramInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQLRecord += " desc";
			}
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(strSQLRecord);
			ps.setTimestamp(1, paramInfo.getStartDate());
			ps.setTimestamp(2, paramInfo.getEndDate());
			/*
			if(Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				ps.setTimestamp(3, paramInfo.getStartDate());
				ps.setTimestamp(4, paramInfo.getEndDate());
			}*/
			rs = ps.executeQuery();
			while (rs.next())
			{
				DailyGatherInfo info = new DailyGatherInfo();
				info.setID(rs.getLong("TransactionTypeID"));
				info.setGetAmount(rs.getDouble("SumReceive"));
				info.setPutAmount(rs.getDouble("SumPay"));
				info.setName(SETTConstant.TransactionType.getName(info.getID()));
				info.setNumber(rs.getLong("nCount"));
				v.addElement(info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			/*TOCONFIG-END*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
			}
		}
		return (v.size() > 0 ? v : null);
	}
	public Collection queryCounterTransByTransTypeDate(QCounterTransInfo paramInfo)
	{
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer sbSQL = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		boolean bHaveData = false;
		try
		{
			Timestamp tsExecute = null;
			double dDayTotalPayAmount = 0;
			double dDayTotalReceiveAmount = 0;
			long lDayTotalNumber = 0;
			long lWeek = -1;
			double dWeekTotalPayAmount = 0;
			double dWeekTotalReceiveAmount = 0;
			long lWeekTotalNumber = 0;
			long lMonth = -1;
			double dMonthTotalPayAmount = 0;
			double dMonthTotalReceiveAmount = 0;
			long lMonthTotalNumber = 0;
			long lYear = -1;
			double dYearTotalPayAmount = 0;
			double dYearTotalReceiveAmount = 0;
			long lYearTotalNumber = 0;
			//得到账户类型，账户编号
			conn = Database.getConnection();
			//added by jiangwei
			sbSQL.setLength(0);
			/*TOCONFIG-TODELETE*/
			/*
			if(Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append(" select TransactionTypeID,sum(SumPay) SumPay,sum(SumReceive) SumReceive,sum(nCount) nCount,Execute,lWeek from \n");
				sbSQL.append(" ( \n");
			}
			*/
			sbSQL.append("     select TransactionTypeID,sum(PayAmount) SumPay,sum(ReceiveAmount) SumReceive,count(TransNo) nCount,Execute,to_char(Execute,'IW') lWeek  \n ");
			sbSQL.append("     from Sett_VTransaction \n ");
			sbSQL.append("     where OfficeID=" + paramInfo.getOfficeID() + " and CurrencyID=" + paramInfo.getCurrencyID() + " \n ");
			sbSQL.append("           and StatusID in (3,4,5,6,7)  and Execute between ? and ?  \n ");
			if (paramInfo.getUserID() > 0)
			{
				sbSQL.append("        and InputUserID=" + paramInfo.getUserID() + " \n ");
			}
			sbSQL.append("     group by TransactionTypeID,Execute,to_char(Execute,'IW')   \n ");
			/*
			if(Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				sbSQL.append("   union \n");
				sbSQL.append("     select nTransactionTypeID,sum(mAmount) SumPay,sum(mAmount) SumReceive,count(sTransNo) nCount,dtExecute,to_char(dtExecute,'IW') lWeek  \n ");
				sbSQL.append("     from Sett_TransSecurities \n ");
				sbSQL.append("     where nOfficeID=" + paramInfo.getOfficeID() + " and nCurrencyID=" + paramInfo.getCurrencyID() + " \n ");
				sbSQL.append("           and nStatusID in (3,4,5,6,7)  and dtExecute between ? and ?  \n ");
				if (paramInfo.getUserID() > 0)
				{
					sbSQL.append("        and nInputUserID=" + paramInfo.getUserID() + " \n ");
				}
				sbSQL.append("     group by nTransactionTypeID,dtExecute,to_char(dtExecute,'IW')   \n ");
				sbSQL.append(" ) group by TransactionTypeID,Execute,lWeek \n");
			}
			*/
			sbSQL.append(" order by  Execute asc ,TransactionTypeID asc \n ");
			//查询记录
			String strSQLRecord = "select * from   ( " + sbSQL.toString();
			strSQLRecord = strSQLRecord + " ) ";
			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(strSQLRecord);
			ps.setTimestamp(1, paramInfo.getStartDate());
			ps.setTimestamp(2, paramInfo.getEndDate());
			/*
			if(Env.getProjectName().equals(Constant.ProjectName.HN))
			{
				ps.setTimestamp(3, paramInfo.getStartDate());
				ps.setTimestamp(4, paramInfo.getEndDate());
			}
			*/
			/*TOCONFIG-END*/
			rs = ps.executeQuery();
			while (rs.next())
			{
				bHaveData = true;
				if (tsExecute != null && !DataFormat.getDateString(tsExecute).equals(DataFormat.getDateString(rs.getTimestamp("Execute"))))
				{
					DailyGatherInfo dayinfo = new DailyGatherInfo();
					v.add(dayinfo);
					dayinfo.setPutAmount(dDayTotalPayAmount);
					dayinfo.setGetAmount(dDayTotalReceiveAmount);
					dayinfo.setName(DataFormat.getDateString(tsExecute) + "小计");
					dayinfo.setNumber(lDayTotalNumber);
					dDayTotalPayAmount = 0;
					dDayTotalReceiveAmount = 0;
					lDayTotalNumber = 0;
				}
				if (lWeek > 0 && lWeek != rs.getLong("lWeek"))
				{
					DailyGatherInfo weekinfo = new DailyGatherInfo();
					v.add(weekinfo);
					weekinfo.setPutAmount(dWeekTotalPayAmount);
					weekinfo.setGetAmount(dWeekTotalReceiveAmount);
					weekinfo.setName("周合计");
					weekinfo.setNumber(lWeekTotalNumber);
					dWeekTotalPayAmount = 0;
					dWeekTotalReceiveAmount = 0;
					lWeekTotalNumber = 0;
				}
				if (tsExecute != null && tsExecute.getMonth() != rs.getTimestamp("Execute").getMonth())
				{
					DailyGatherInfo monthinfo = new DailyGatherInfo();
					v.add(monthinfo);
					monthinfo.setPutAmount(dMonthTotalPayAmount);
					monthinfo.setGetAmount(dMonthTotalReceiveAmount);
					monthinfo.setName("月合计");
					monthinfo.setNumber(lMonthTotalNumber);
					dMonthTotalPayAmount = 0;
					dMonthTotalReceiveAmount = 0;
					lMonthTotalNumber = 0;
				}
				if (tsExecute != null && tsExecute.getYear() != rs.getTimestamp("Execute").getYear())
				{
					DailyGatherInfo yearinfo = new DailyGatherInfo();
					v.add(yearinfo);
					yearinfo.setPutAmount(dYearTotalPayAmount);
					yearinfo.setGetAmount(dYearTotalReceiveAmount);
					yearinfo.setName("年合计");
					yearinfo.setNumber(lYearTotalNumber);
					dYearTotalPayAmount = 0;
					dYearTotalReceiveAmount = 0;
					lYearTotalNumber = 0;
				}
				DailyGatherInfo info = new DailyGatherInfo();
				v.addElement(info);
				info.setID(rs.getLong("TransactionTypeID"));
				info.setGetAmount(rs.getDouble("SumReceive"));
				info.setPutAmount(rs.getDouble("SumPay"));
				info.setName(SETTConstant.TransactionType.getName(info.getID()));
				info.setNumber(rs.getLong("nCount"));
				tsExecute = rs.getTimestamp("Execute");
				dDayTotalPayAmount += info.getPutAmount();
				dDayTotalReceiveAmount += info.getGetAmount();
				lDayTotalNumber += info.getNumber();
				lWeek = rs.getLong("lWeek");
				dWeekTotalPayAmount += info.getPutAmount();
				dWeekTotalReceiveAmount += info.getGetAmount();
				lWeekTotalNumber += info.getNumber();
				lMonth = tsExecute.getMonth();
				dMonthTotalPayAmount += info.getPutAmount();
				dMonthTotalReceiveAmount += info.getGetAmount();
				lMonthTotalNumber += info.getNumber();
				lYear = tsExecute.getYear();
				dYearTotalPayAmount += info.getPutAmount();
				dYearTotalReceiveAmount += info.getGetAmount();
				lYearTotalNumber += info.getNumber();
			}
			if (bHaveData)
			{
				DailyGatherInfo dayinfo = new DailyGatherInfo();
				v.add(dayinfo);
				dayinfo.setPutAmount(dDayTotalPayAmount);
				dayinfo.setGetAmount(dDayTotalReceiveAmount);
				dayinfo.setName(DataFormat.getDateString(tsExecute) + "小计");
				dayinfo.setNumber(lDayTotalNumber);
				dDayTotalPayAmount = 0;
				dDayTotalReceiveAmount = 0;
				lDayTotalNumber = 0;
			}
			if (bHaveData)
			{
				DailyGatherInfo weekinfo = new DailyGatherInfo();
				v.add(weekinfo);
				weekinfo.setPutAmount(dWeekTotalPayAmount);
				weekinfo.setGetAmount(dWeekTotalReceiveAmount);
				weekinfo.setName("周合计");
				weekinfo.setNumber(lWeekTotalNumber);
				dWeekTotalPayAmount = 0;
				dWeekTotalReceiveAmount = 0;
				lWeekTotalNumber = 0;
			}
			if (bHaveData)
			{
				DailyGatherInfo monthinfo = new DailyGatherInfo();
				v.add(monthinfo);
				monthinfo.setPutAmount(dMonthTotalPayAmount);
				monthinfo.setGetAmount(dMonthTotalReceiveAmount);
				monthinfo.setName("月合计");
				monthinfo.setNumber(lMonthTotalNumber);
				dMonthTotalPayAmount = 0;
				dMonthTotalReceiveAmount = 0;
				lMonthTotalNumber = 0;
			}
			if (bHaveData)
			{
				DailyGatherInfo yearinfo = new DailyGatherInfo();
				v.add(yearinfo);
				yearinfo.setPutAmount(dYearTotalPayAmount);
				yearinfo.setGetAmount(dYearTotalReceiveAmount);
				yearinfo.setName("年合计");
				yearinfo.setNumber(lYearTotalNumber);
				dYearTotalPayAmount = 0;
				dYearTotalReceiveAmount = 0;
				lYearTotalNumber = 0;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
			}
		}
		return (v.size() > 0 ? v : null);
	}
}
