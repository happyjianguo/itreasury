/*
 * Created on 2004-2-27
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.print.templateinfo.SubsectionDateInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubsectionInterest extends SettlementDAO
{
	Log4j logger = null;
	public static void main(java.lang.String[] args) throws Exception
	{
		//在此处插入用来启动应用程序的代码。
		try
		{
			SubsectionDateInfo info = new SubsectionDateInfo();
			Vector v = new Vector();
			SubsectionInterest dao = new SubsectionInterest();
			v = dao.getSubsectionDate(254, DataFormat.getDateTime("2003-09-21"), DataFormat.getDateTime("2003-12-20"));
			info = dao.addSubsectionInterest(v);
			System.out.println(info.getPrintStartDate());
			System.out.println(info.getPrintEndDate());
			System.out.println(info.getPrintDays());
			System.out.println(info.getPrintBalance());
			System.out.println(info.getPrintInterestRate());
			System.out.println(info.getPrintInterest());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public SubsectionInterest()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	/**
	 * 取得分段利息的方法
	 * @param lSubAccountID
	 * @param tsInterestStartDate
	 * @param tsInterestEndDate
	 * @return 分段后的利息值，组合后的String类型
	 * @throws Exception
	 */
	public SubsectionDateInfo getSubsectionInterest(long lAccountID, long lSubAccountID, long lLoanNoteID, Timestamp tsInterestStartDate, Timestamp tsInterestEndDate)
		throws Exception
	{
		Vector v = new Vector();
		SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
		try
		{
			if (lSubAccountID <= 0) //没有传入 SubAccountID
			{
				lSubAccountID = getSubAccountID(lAccountID, lLoanNoteID);
			}
			v = getSubsectionDate(lSubAccountID, tsInterestStartDate, tsInterestEndDate); //取得分段
			PrintSubsectionInfo = addSubsectionInterest(v); //填入利息
		}
		catch (Exception exp)
		{
			throw exp;
		}
		return PrintSubsectionInfo;
	}
	public long getSubAccountID(long lAccountID, long lLoanNoteID) throws Exception
	{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lID = -1;
		String strSQL = null;
		try
		{
			strSQL = "select ID from sett_SubAccount where nAccountID=" + lAccountID + " and AL_nLoanNoteID=" + lLoanNoteID + " and nStatusID > 0 ";
			conn = getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lID = rs.getLong("ID");
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
		return lID;
	}
	/**
	 * 取得分段的日期 余额 利率
	 * @param lSubAccountID
	 * @param tsInterestStartDate 利息的开始日期
	 * @param tsInterestEndDate 利息的结束日期
	 * @return 
	 * @throws Exception
	 */
	public Vector getSubsectionDate(long lSubAccountID, Timestamp tsInterestStartDate, Timestamp tsInterestEndDate) throws Exception
	{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Vector v = new Vector();
		String strSQL = null;
		try
		{
			strSQL =
				" select min(dtDate) dtDate,mInterestBalance,mInterestRate from sett_DailyAccountBalance"
					+ " where nSubAccountID = "
					+ lSubAccountID
					+ " and dtDate between to_date('"
					+ DataFormat.getDateString(tsInterestStartDate)
					+ "','yyyy-mm-dd') and to_date('"
					+ DataFormat.getDateString(tsInterestEndDate)
					+ "','yyyy-mm-dd') "
					+ " group by mInterestBalance,mInterestRate "
					+ " order by dtDate ";
			conn = getConnection();
			log.print("取得余额和利率变化的SQL=" + strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			//取得所有分段的日期 的 临界点
			while (rs.next())
			{
				SubsectionDateInfo sdi = new SubsectionDateInfo();
				sdi.setDate(rs.getTimestamp("dtDate"));
				sdi.setBalance(rs.getDouble("mInterestBalance"));
				sdi.setInterestRate(rs.getDouble("mInterestRate"));
				v.add(sdi);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			//将分段的日期拆分成 开始日期和结束日期
			int ii = 0;
			boolean bIsFirst = true;
			Timestamp tsTempDate = null;
			if (v != null)
			{
				log.print("v 大小:" + v.size());
				if (v.size() == 1) //如果只有一条记录那么 就不是分段利息
				{
					log.print("不是分段利息");
					SubsectionDateInfo sdi = new SubsectionDateInfo();
					sdi = (SubsectionDateInfo) v.elementAt(0);
					sdi.setStartDate(tsInterestStartDate);
					sdi.setEndDate(tsInterestEndDate);
					InterestCalculation calInterest = new InterestCalculation();
					//sdi.setInterest(calInterest.calculateLoanAccountInterest(-1,sdi.getInterestRate(),1,sdi.getBalance(),sdi.getStartDate(),sdi.getEndDate()));
					sdi.setInterest(
						calInterest.calculateLoanAccountInterest(-1, sdi.getInterestRate(), 1, sdi.getBalance(), sdi.getStartDate(), DataFormat.getNextDate(sdi.getEndDate())));
					sdi.setDays(PrintVoucher.getIntervalDays(sdi.getStartDate(), DataFormat.getNextDate(sdi.getEndDate()), 1));
				}
				else
					if (v.size() > 1)
					{
						log.print("是分段利息");
						for (ii = 0; ii < v.size(); ii++)
						{
							SubsectionDateInfo sdi = new SubsectionDateInfo();
							sdi = (SubsectionDateInfo) v.elementAt(ii);
							tsTempDate = sdi.getDate();
							if (bIsFirst)
							{
								bIsFirst = false;
							}
							else
							{
								if ((ii - 1) >= 0)
								{
									SubsectionDateInfo sdi2 = new SubsectionDateInfo();
									sdi2 = (SubsectionDateInfo) v.elementAt(ii - 1);
									sdi2.setStartDate(sdi2.getDate());
									sdi2.setEndDate(DataFormat.getPreviousDate(sdi.getDate()));
								}
							}
						}
						//补充最后一条记录 的开始结束日期
						SubsectionDateInfo sdi3 = new SubsectionDateInfo();
						sdi3 = (SubsectionDateInfo) v.elementAt(ii - 1);
						sdi3.setStartDate(sdi3.getDate());
						sdi3.setEndDate(tsInterestEndDate);
					}
			}
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
		return v;
	}
	/**
	 * 传入已经分段的日期 余额 利率信息
	 * @param v
	 * @return 算出每个分段的利息 算出每段的天数
	 * @throws Exception
	 */
	public SubsectionDateInfo addSubsectionInterest(Vector v) throws Exception
	{
		double interestSum = 0.0;
		SubsectionDateInfo PrintSubsectionInfo = new SubsectionDateInfo();
		try
		{
			InterestCalculation calInterest = new InterestCalculation();
			if (v != null)
			{
				for (int i = 0; i < v.size(); i++)
				{
					SubsectionDateInfo sdi = new SubsectionDateInfo();
					sdi = (SubsectionDateInfo) v.elementAt(i);
					//取得利息 天数
					if (sdi.getInterest() == 0) //如果有数值则说明是不分段的利息 已经计算过了
					{
						double interest = calInterest.calculateLoanAccountInterest(-1, sdi.getInterestRate(), 1, sdi.getBalance(), sdi.getStartDate(), DataFormat.getNextDate(sdi.getEndDate()));
						sdi.setInterest(interest);
					}
					if (sdi.getDays() < 0)
					{
						sdi.setDays(PrintVoucher.getIntervalDays(sdi.getStartDate(), DataFormat.getNextDate(sdi.getEndDate()), 1));
					}
					//将需要打印的信息组合起来
					if (i != (v.size() - 1))
					{
						PrintSubsectionInfo.setPrintStartDate(PrintSubsectionInfo.getPrintStartDate() + DataFormat.formatDate(sdi.getStartDate()) + "<br>&nbsp;");
						PrintSubsectionInfo.setPrintEndDate(PrintSubsectionInfo.getPrintEndDate() + DataFormat.formatDate(sdi.getEndDate()) + "<br>&nbsp;");
						PrintSubsectionInfo.setPrintDays(PrintSubsectionInfo.getPrintDays() + sdi.getDays() + "<br>&nbsp;");
						PrintSubsectionInfo.setPrintBalance(PrintSubsectionInfo.getPrintBalance() + DataFormat.formatDisabledAmount(sdi.getBalance()) + "<br>&nbsp;");
						PrintSubsectionInfo.setPrintInterestRate(PrintSubsectionInfo.getPrintInterestRate() + String.valueOf(sdi.getInterestRate()) + "<br>&nbsp;");
						PrintSubsectionInfo.setPrintInterest(PrintSubsectionInfo.getPrintInterest() + DataFormat.formatDisabledAmount(sdi.getInterest()) + "<br>&nbsp;");
						interestSum = DataFormat.formatDouble(interestSum)+DataFormat.formatDouble(sdi.getInterest());
					}
					else
					{
						PrintSubsectionInfo.setPrintStartDate(PrintSubsectionInfo.getPrintStartDate() + DataFormat.formatDate(sdi.getStartDate()));
						PrintSubsectionInfo.setPrintEndDate(PrintSubsectionInfo.getPrintEndDate() + DataFormat.formatDate(sdi.getEndDate()));
						PrintSubsectionInfo.setPrintDays(PrintSubsectionInfo.getPrintDays() + sdi.getDays());
						PrintSubsectionInfo.setPrintBalance(PrintSubsectionInfo.getPrintBalance() + DataFormat.formatDisabledAmount(sdi.getBalance()));
						PrintSubsectionInfo.setPrintInterestRate(PrintSubsectionInfo.getPrintInterestRate() + String.valueOf(sdi.getInterestRate()));
						PrintSubsectionInfo.setPrintInterest(PrintSubsectionInfo.getPrintInterest() + DataFormat.formatDisabledAmount(sdi.getInterest()));
						interestSum = DataFormat.formatDouble(interestSum)+DataFormat.formatDouble(sdi.getInterest());
					}
				}
			}
			PrintSubsectionInfo.setNormalInterestSum(interestSum);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		return PrintSubsectionInfo;
	}
}
