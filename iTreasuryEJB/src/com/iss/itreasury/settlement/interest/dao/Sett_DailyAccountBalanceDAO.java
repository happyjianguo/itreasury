package com.iss.itreasury.settlement.interest.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.print.PrintVoucher;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.DataFormat;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-11-1
 */
public class Sett_DailyAccountBalanceDAO extends InterestDAO
{
	public Sett_DailyAccountBalanceDAO(Connection transConn)
	{
		super(transConn);
		super.strTableName = "Sett_DailyAccountBalance";
	}
	/**
	 * 
	 * @param conn
	 * @param subAccountID
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public DailyAccountBalanceInfo findAllBySubAccountIDAndDate(long subAccountID, long lOfficeID, long lCurrencyID, Timestamp date) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DailyAccountBalanceInfo res = null;
		StringBuffer bufferSQL = new StringBuffer();
		try
		{
			conn = getConnection();
			//select * from sett_DailyAccountBalance daily ,sett_account account
			//where
			//daily.naccountid= account.id and account.nofficeID=1 and account.ncurrencyID=2;
			bufferSQL.append(" select * from sett_DailyAccountBalance daily ,sett_account account ");
			bufferSQL.append(" WHERE  \n");
			bufferSQL.append(" daily.naccountid= account.id  \n");
			bufferSQL.append(" and account.nofficeID= " + lOfficeID + " \n");
			bufferSQL.append(" and account.ncurrencyID=" + lCurrencyID + "  \n");
			bufferSQL.append(" and DTDATE =  to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')   \n");
			bufferSQL.append(" and NSUBACCOUNTID= " + subAccountID);
			log.info(bufferSQL.toString());
			ps = conn.prepareStatement(bufferSQL.toString());
			//ps.setTimestamp(1, date);
			rs = ps.executeQuery();
			if (rs.next())
			{
				res = new DailyAccountBalanceInfo();
				res = getInfoFromRS(rs);
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
		return res;
	}
	/**
	 * @param accountID
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public Collection findAllByAccountIDAndDate(long accountID, long lOfficeID, long lCurrencyID, Timestamp date) throws SQLException
	{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DailyAccountBalanceInfo res = null;
		Collection coll = new ArrayList();
		StringBuffer bufferSQL = new StringBuffer();
		try
		{
			conn = getConnection();
			bufferSQL.append(" select * from sett_DailyAccountBalance daily ,sett_account account ");
			bufferSQL.append(" WHERE  \n");
			bufferSQL.append(" daily.naccountid= account.id  \n");
			bufferSQL.append(" and account.nofficeID= " + lOfficeID + " \n");
			bufferSQL.append(" and account.ncurrencyID=" + lCurrencyID + "  \n");
			bufferSQL.append(" and DTDATE =  to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')   \n");
			bufferSQL.append(" and NACCOUNTID= " + accountID);
			log.info(bufferSQL.toString());
			ps = conn.prepareStatement(bufferSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				res = new DailyAccountBalanceInfo();
				res = getInfoFromRS(rs);
				coll.add(res);
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
		return coll;
	}
	public double sumInterestBalanceBySubAccountIDAndDate(long subAccountID, Timestamp sDate, Timestamp eDate) throws SQLException
	{
		return sumBlanceBySubAccountIDAndDate(BALANCETYPE_NORMAL, subAccountID, sDate, eDate);
	}
	public double sumAccountBalanceBySubAccountIDAndDate(long subAccountID, Timestamp sDate, Timestamp eDate) throws SQLException
	{
		return sumBlanceBySubAccountIDAndDate(BALANCETYPE_ACCOUNT, subAccountID, sDate, eDate);
	}	
	public double sumNegotiateBalanceBySubAccountIDAndDate(long subAccountID, Timestamp sDate, Timestamp eDate) throws SQLException
	{
		return sumBlanceBySubAccountIDAndDate(BALANCETYPE_NEGOTIATION, subAccountID, sDate, eDate);
	}
	public double sumArrearageInterestBySubAccountIDAndDate(long subAccountID, Timestamp sDate, Timestamp eDate) throws SQLException
	{
		return sumBlanceBySubAccountIDAndDate(BALANCETYPE_ARREARAGEINTEREST, subAccountID, sDate, eDate);
	}

	public double sumOverdueAmountBySubAccountIDAndDate(long subAccountID, Timestamp sDate, Timestamp eDate) throws SQLException
	{
		return sumBlanceBySubAccountIDAndDate(BALANCETYPE_OVERDUEAMOUNT, subAccountID, sDate, eDate);
	}
	private final static int BALANCETYPE_NORMAL = 1; //正常余额
	private final static int BALANCETYPE_NEGOTIATION = 2; //协定余额
	private final static int BALANCETYPE_ARREARAGEINTEREST = 3; //累计欠息
	private final static int BALANCETYPE_OVERDUEAMOUNT = 4; //累计欠息
	private final static int BALANCETYPE_ACCOUNT = 5; //账户余额

	private double sumBlanceBySubAccountIDAndDate(int blanceType, long subAccountID, Timestamp sDate, Timestamp eDate) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double sumOfInterestBlance = 0.0;
		StringBuffer bufferSQL = new StringBuffer();
		try
		{
			conn = getConnection();
			String balanceFieldName = "";
			
			if (blanceType == BALANCETYPE_NORMAL)
			{
				balanceFieldName = "mInterestBalance";
			}
			else if (blanceType == BALANCETYPE_NEGOTIATION)
			{
				balanceFieldName = "AC_mNegotiateBalance";
			}
			else if (blanceType == BALANCETYPE_ARREARAGEINTEREST)
			{
				balanceFieldName = "AL_MARREARAGEINTEREST * MINTERESTRATE / 36000.0";
			}
			else if (blanceType == BALANCETYPE_OVERDUEAMOUNT)
			{
				balanceFieldName = "AL_MOVERDUEAMOUNT";
			}
			else if (blanceType == BALANCETYPE_ACCOUNT)
			{
				balanceFieldName = "mInterestBalance";	
			}
			
			bufferSQL.append(" SELECT SUM( \n ");
			bufferSQL.append(balanceFieldName);
			bufferSQL.append(" ) FROM   \n");
			bufferSQL.append(strTableName);
			bufferSQL.append(" WHERE NSUBACCOUNTID = " + subAccountID);
			//起息日
			bufferSQL.append(" AND (DTDATE >= to_date('" + DataFormat.getDateString(sDate) + "','yyyy-mm-dd') \n"); //modify liuhuijun 2003-11-20,//后 修改 2003-12-07
			//止息日
			bufferSQL.append(" AND DTDATE <= to_date('" + DataFormat.getDateString(eDate) + "','yyyy-mm-dd') ) \n"); //modify liuhuijun 2003-11-20,//后 修改 2003-12-07
			
			if (blanceType == BALANCETYPE_ARREARAGEINTEREST)
			{
				log.debug("返回复利已计积数SQLSTRING : " + bufferSQL.toString());
			}
			
			ps = conn.prepareStatement(bufferSQL.toString());
			//ps.setTimestamp(1, sDate);
			//ps.setTimestamp(2, eDate);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumOfInterestBlance = rs.getDouble(1);
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
		return sumOfInterestBlance;
	}
	
	public DailyAccountBalanceInfo getInfoFromRS(ResultSet rs) throws SQLException
	{
		DailyAccountBalanceInfo resInfo = new DailyAccountBalanceInfo();
		//赋值 2003-11-08 hjLiu
		//System.out.println("lhj debug info =====nACCOUNTID===" + rs.getLong("nACCOUNTID"));
		resInfo.setAccountID(rs.getLong("nACCOUNTID"));
		//System.out.println("lhj debug info =====nSUBACCOUNTID===" + rs.getLong("nSUBACCOUNTID")); //主账户ID
		resInfo.setSubAccountID(rs.getLong("nSUBACCOUNTID"));
		//System.out.println("lhj debug info =====dtDATE===" + rs.getTimestamp("dtDATE")); //子账户ID
		resInfo.setDate(rs.getTimestamp("dtDATE"));
		//System.out.println("lhj debug info ====nACCOUNTSTATUSID====" + rs.getLong("nACCOUNTSTATUSID")); //日期
		resInfo.setAccountStatusID(rs.getLong("nACCOUNTSTATUSID"));
		//System.out.println("lhj debug info =====mBALANCE===" + rs.getDouble("mBALANCE")); //主账户状态
		resInfo.setBalance(rs.getDouble("mBALANCE"));
		//System.out.println("lhj debug info =====mINTERESTBALANCE===" + rs.getDouble("mINTERESTBALANCE")); //账户余额
		resInfo.setInterestBalance(rs.getDouble("mINTERESTBALANCE"));
		//System.out.println("lhj debug info =====mINTERESTRATE===" + rs.getDouble("mINTERESTRATE")); //计息余额/计息正常余额
		resInfo.setInterestRate(rs.getDouble("mINTERESTRATE"));
		//System.out.println("lhj debug info =====mDAILYINTEREST===" + rs.getDouble("mDAILYINTEREST")); //执行利率/正常利率
		resInfo.setDailyInterest(rs.getDouble("mDAILYINTEREST"));
		//System.out.println("lhj debug info =====mINTEREST===" + rs.getDouble("mINTEREST")); //本日利率/本日正常利率
		resInfo.setInterest(rs.getDouble("mINTEREST"));
		//System.out.println("lhj debug info =====AC_mNEGOTIATEBALANCE===" + rs.getDouble("AC_mNEGOTIATEBALANCE")); //累计利息/累计正常利息
		resInfo.setAc_mNegotiateBalance(rs.getDouble("AC_mNEGOTIATEBALANCE"));
		//System.out.println("lhj debug info =====AC_mNEGOTIATERATE===" + rs.getDouble("AC_mNEGOTIATERATE")); //计息协定余额，活期专用
		resInfo.setAc_mNegotiateRate(rs.getDouble("AC_mNEGOTIATERATE"));
		//System.out.println("lhj debug info ======AC_mDAILYNEGOTIATEINTEREST==" + rs.getDouble("AC_mDAILYNEGOTIATEINTEREST")); //协定利率，活期专用
		resInfo.setAc_mDailyNegotiateInterest(rs.getDouble("AC_mDAILYNEGOTIATEINTEREST"));
		//System.out.println("lhj debug info ==AC_mNEGOTIATEINTEREST=====" + rs.getDouble("AC_mNEGOTIATEINTEREST")); //本日协定利息，活期专用
		resInfo.setAc_mNegotiateInterest(rs.getDouble("AC_mNEGOTIATEINTEREST"));
		System.out.println("lhj debug info ====AL_mARREARAGEINTEREST====" + rs.getDouble("AL_mARREARAGEINTEREST")); //累计欠息余额，贷款专用
		resInfo.setAl_mArrearageInterest(rs.getDouble("AL_mARREARAGEINTEREST")); //累计欠息余额，贷款专用        
		System.out.println("lhj debug info ====AL_MOVERDUEAMOUNT====" + rs.getDouble("AL_MOVERDUEAMOUNT")); //逾期金额，贷款专用
		resInfo.setAl_mOverDueAmount(rs.getDouble("AL_MOVERDUEAMOUNT")); //逾期金额，贷款专用
		
		resInfo.setMforfeitdailyinterset(rs.getDouble("mforfeitdailyinterset"));	//每日罚息
		resInfo.setMforfeitinterest(rs.getDouble("mforfeitinterest"));				//罚息
		resInfo.setMcompounddailyinterset(rs.getDouble("mcompounddailyinterset"));	//每日复利
		resInfo.setMcompoundinterest(rs.getDouble("mcompoundinterest"));			//复利
		
		return resInfo;
	}
	/**
	 * 为账户余额日结表增加一条记录
	 * @author hjLiu   2003-11-08
	 *  2003-11-10 写，但没有经过测试
	 * @param dailyAccountBalanceInfo
	 * @return 记录增加后的主账户ID
	 */
	public long add(DailyAccountBalanceInfo dailyAccountBalanceInfo) throws Exception
	{
		//System.out.println("调用账户余额日结表的增加add()方法!!!!!!");
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer bufferSQL = null;
		try
		{
			conn = getConnection();
			//establish the insert sql string
			bufferSQL = new StringBuffer();
			//构造SQL语句
			bufferSQL.append(" INSERT INTO Sett_DailyAccountBalance \n");
			bufferSQL.append(" ( nACCOUNTID,nSUBACCOUNTID,dtDATE \n");
			bufferSQL.append(",nACCOUNTSTATUSID,mBALANCE,mINTERESTBALANCE \n");
			bufferSQL.append(",mINTERESTRATE,mDAILYINTEREST,mINTEREST \n");
			bufferSQL.append(",AC_mNEGOTIATEBALANCE,AC_mNEGOTIATERATE \n");
			bufferSQL.append(",AC_mDAILYNEGOTIATEINTEREST,AC_mNEGOTIATEINTEREST \n");
			bufferSQL.append(",AL_mARREARAGEINTEREST,AL_MOVERDUEAMOUNT,mFreezeAmount,nSubAccountStatusID,mforfeitdailyinterset,mforfeitinterest,mcompounddailyinterset,mcompoundinterest, MForfeitInterestRate) values ( \n");
			bufferSQL.append(" ?,?,to_date('" + DataFormat.getDateString(dailyAccountBalanceInfo.getDate()) + "','yyyy-mm-dd'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			//将SQL语句写如Log文件
			//log.info(bufferSQL.toString());
			log.info("****************************************");
			log.info("nACCOUNTID="+dailyAccountBalanceInfo.getAccountID()+",getSubAccountID="+dailyAccountBalanceInfo.getSubAccountID()+",SubAccountStatusid"+dailyAccountBalanceInfo.getSubAccountStatusid());
			ps = conn.prepareStatement(bufferSQL.toString());
			int nIndex = 1;
			ps.setLong(nIndex++, dailyAccountBalanceInfo.getAccountID());
			ps.setLong(nIndex++, dailyAccountBalanceInfo.getSubAccountID());
			//ps.setTimestamp(nIndex++, dailyAccountBalanceInfo.getDate());
			ps.setLong(nIndex++, dailyAccountBalanceInfo.getAccountStatusID());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getBalance());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getInterestBalance());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getInterestRate());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getDailyInterest());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getInterest());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getAc_mNegotiateBalance());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getAc_mNegotiateRate());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getAc_mDailyNegotiateInterest());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getAc_mNegotiateInterest());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getAl_mArrearageInterest());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getAl_mOverDueAmount());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getFreezeAmount());
			ps.setLong(nIndex++, dailyAccountBalanceInfo.getSubAccountStatusid());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getMforfeitdailyinterset());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getMforfeitinterest());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getMcompounddailyinterset());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getMcompoundinterest());
			ps.setDouble(nIndex++, dailyAccountBalanceInfo.getMForfeitInterestRate());
			//执行
			ps.executeUpdate();
			//log.info("add sett_dailyAccountBalance successfully. AccountID is " + lReturn);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqle)
		{
			//System.out.println("增加账户余额日结表记录出错！");
			sqle.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 更新账户余额日结表的一条记录
	 * @author hjLiu   2003-11-10
	 * @param dailyAccountBalanceInfo
	 * @return 记录更新后的主账户ID
	 */
	public long update(DailyAccountBalanceInfo dailyAccountBalanceInfo) throws Exception
	{
		//System.out.println("调用账户余额日结表的修改update()方法!!!!!!");
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer bufferSQL = null;
		try
		{
			conn = getConnection();
			//establish the insert sql string
			bufferSQL = new StringBuffer();
			long AccountID = dailyAccountBalanceInfo.getAccountID();
			long SubAccountID = dailyAccountBalanceInfo.getSubAccountID();
			//构造SQL语句
			bufferSQL.append(" UPDATE SETT_DAILYACCOUNTBALANCE \n");
			// bufferSQL.append(" SET dtDATE = ?            \n");
			bufferSQL.append("  set nACCOUNTSTATUSID = " + dailyAccountBalanceInfo.getAccountStatusID() + " \n");
			bufferSQL.append("     ,mBALANCE = " + dailyAccountBalanceInfo.getBalance() + " \n");
			bufferSQL.append("     ,mINTERESTBALANCE = " + dailyAccountBalanceInfo.getInterestBalance() + " \n");
			bufferSQL.append("     ,mINTERESTRATE = " + dailyAccountBalanceInfo.getInterestRate() + "    \n");
			bufferSQL.append("     ,mDAILYINTEREST = " + dailyAccountBalanceInfo.getDailyInterest() + "   \n");
			bufferSQL.append("     ,mINTEREST = " + dailyAccountBalanceInfo.getInterest() + "        \n");
			bufferSQL.append("     ,AC_mNEGOTIATEBALANCE = " + dailyAccountBalanceInfo.getAc_mNegotiateBalance() + "       \n");
			bufferSQL.append("     ,AC_mNEGOTIATERATE = " + dailyAccountBalanceInfo.getAc_mNegotiateRate() + "          \n");
			bufferSQL.append("     ,AC_mDAILYNEGOTIATEINTEREST = " + dailyAccountBalanceInfo.getAc_mDailyNegotiateInterest() + " \n");
			bufferSQL.append("     ,AC_mNEGOTIATEINTEREST = " + dailyAccountBalanceInfo.getAc_mNegotiateInterest() + "      \n");
			bufferSQL.append("     ,AL_mARREARAGEINTEREST = " + dailyAccountBalanceInfo.getAl_mArrearageInterest() + "      \n");
			bufferSQL.append("     ,AL_MOVERDUEAMOUNT = " + dailyAccountBalanceInfo.getAl_mOverDueAmount() + "      \n");
			
			bufferSQL.append("     ,mforfeitdailyinterset = " 	+ dailyAccountBalanceInfo.getMforfeitdailyinterset() + " \n");
			bufferSQL.append("     ,mforfeitinterest = " 		+ dailyAccountBalanceInfo.getMforfeitinterest() + "      \n");
			bufferSQL.append("     ,mcompounddailyinterset = " 	+ dailyAccountBalanceInfo.getMcompounddailyinterset() + "      \n");
			bufferSQL.append("     ,mcompoundinterest = " 		+ dailyAccountBalanceInfo.getMcompoundinterest() + "      \n");
			bufferSQL.append("     ,MForfeitInterestRate = " 		+ dailyAccountBalanceInfo.getMForfeitInterestRate() + "      \n");
			
			bufferSQL.append(" where nACCOUNTID = " + AccountID + "\n");
			bufferSQL.append(" and  nSUBACCOUNTID =   " + SubAccountID + " \n");
			bufferSQL.append(" and dtDATE = to_date('" + DataFormat.getDateString(dailyAccountBalanceInfo.getDate()) + "','yyyy-mm-dd')            \n");
			//将SQL语句写如Log文件
			//log.info(bufferSQL.toString());
			ps = conn.prepareStatement(bufferSQL.toString());
			//执行
			long lTmp = ps.executeUpdate();
			lReturn = AccountID;
			//log.info("update sett_dailyAccountBalance successfully. AccountID is " + lReturn);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 查找账户余额日结表SETT_DAILYACCOUNTBALANCE中日期是关机日期的所有记录。
	 * @param connection 调用此方法的用户传入的Connection 不可为空！
	 * @param officeID  //办事处编号 
	 * @param currencyID//主账户中的币种
	 * @param closeDate //关机日期
	 * @return Collection 账户余额日结表对象集合
	 * @throws Exception
	 */
	public Collection findByDate(long officeID, long currencyID, Timestamp closeDate) throws Exception
	{
		Vector dailyAccountBalanceInfoVector = new Vector();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
		bufferSQL = new StringBuffer();
		bufferSQL.append(" select distinct daily.* from \n");
		bufferSQL.append(" SETT_DAILYACCOUNTBALANCE daily, \n");
		bufferSQL.append(" SETT_ACCOUNT account, \n");
		bufferSQL.append(" SETT_SUBACCOUNT subAccount \n");
		bufferSQL.append(" where daily.nACCOUNTID=account.ID \n");
		bufferSQL.append(" and daily.nsubACCOUNTID = subaccount.ID \n");
		bufferSQL.append(" and account.ID = subAccount.nAccountID \n");
		bufferSQL.append(" and subAccount.NISINTEREST = 1 \n");
		bufferSQL.append(" and  account.NOFFICEID = " + officeID + " \n");
		bufferSQL.append(" and account.NCURRENCYID =  " + currencyID + "\n");
		bufferSQL.append(" and dtDATE = to_date('" + DataFormat.getDateString(closeDate) + "','yyyy-mm-dd')");
		String sqlString = bufferSQL.toString();
		//log.print(" the method of findByDate in the sett_DailyAccountBalanceDAO,the sqlString is : " + sqlString);
		//
		try
		{
			conn = getConnection();
			ps = conn.prepareStatement(sqlString);
			//ps.setTimestamp(1, closeDate);
			rset = ps.executeQuery();
			while (rset.next())
			{
				//log.print(" **************888888888888888888********************8 ");
				//新建一个DailyAccountBalanceInfo对象
				dailyAccountBalanceInfo = new DailyAccountBalanceInfo();
				//结果集中的相应字段值赋值到DailyAccountBalanceInfo对象对应的属性
				dailyAccountBalanceInfo.setAccountID(rset.getLong("NACCOUNTID"));
				dailyAccountBalanceInfo.setSubAccountID(rset.getLong("NSUBACCOUNTID"));
				dailyAccountBalanceInfo.setDate(rset.getTimestamp("DTDATE"));
				dailyAccountBalanceInfo.setAccountStatusID(rset.getLong("NACCOUNTSTATUSID"));
				dailyAccountBalanceInfo.setBalance(rset.getDouble("MBALANCE"));
				dailyAccountBalanceInfo.setInterestBalance(rset.getDouble("MINTERESTBALANCE"));
				dailyAccountBalanceInfo.setInterestRate(rset.getDouble("MINTERESTRATE"));
				dailyAccountBalanceInfo.setDailyInterest(rset.getDouble("MDAILYINTEREST"));
				dailyAccountBalanceInfo.setInterest(rset.getDouble("MINTEREST"));
				dailyAccountBalanceInfo.setAc_mNegotiateBalance(rset.getDouble("AC_MNEGOTIATEBALANCE"));
				dailyAccountBalanceInfo.setAc_mNegotiateRate(rset.getDouble("AC_MNEGOTIATERATE"));
				dailyAccountBalanceInfo.setAc_mDailyNegotiateInterest(rset.getDouble("AC_MDAILYNEGOTIATEINTEREST"));
				dailyAccountBalanceInfo.setAc_mNegotiateInterest(rset.getDouble("AC_MNEGOTIATEINTEREST"));
				dailyAccountBalanceInfo.setAl_mArrearageInterest(rset.getDouble("AL_MARREARAGEINTEREST"));
				dailyAccountBalanceInfo.setAl_mOverDueAmount(rset.getDouble("AL_MOVERDUEAMOUNT"));
				dailyAccountBalanceInfo.setMcompoundinterest(rset.getDouble("mcompoundinterest"));
				dailyAccountBalanceInfo.setMforfeitinterest(rset.getDouble("mforfeitinterest"));
				//将DailyAccountBalanceInfo加入到Vector之中
				dailyAccountBalanceInfoVector.add(dailyAccountBalanceInfo);
			}
			cleanup(rset);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(rset);
			cleanup(ps);
			cleanup(conn);
		}
		return dailyAccountBalanceInfoVector;
	}

	/**
		 * 更新账户余额日结表的逾期欠息
		 * @author hjLiu   2004-01-12
		 * @param  Al_overDueAmount
		 * @param  subAccountID
		 * @param  fineDate
		 * @return lRetur －1：不成功，1:成功
		 * 
		 */
	public long updateOverDueArrearageAmount(long subAccountID, double AL_OverDueAmount,double LoanInterest, Timestamp fineDate) throws Exception
	{
		log.info("调用账户余额表中的更新逾期欠息方法......");
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer bufferSQL = null;
		try
		{
			conn = getConnection();
			//establish the insert sql string
			bufferSQL = new StringBuffer();
			//构造SQL语句
			bufferSQL.append(" UPDATE SETT_DAILYACCOUNTBALANCE \n");
			bufferSQL.append(" SET AL_MOVERDUEAMOUNT = " + AL_OverDueAmount + ", \n");
			bufferSQL.append(" MINTERESTBALANCE = 0 , MDAILYINTEREST = 0,AL_MARREARAGEINTEREST = 0"+ ", \n");
			bufferSQL.append(" MINTEREST = " + LoanInterest +" \n");
			bufferSQL.append(" where nSUBACCOUNTID =   " + subAccountID + " \n");
			bufferSQL.append(" and dtDATE >= to_date('" + DataFormat.getDateString(fineDate) + "','yyyy-mm-dd')   ");
			//将SQL语句写如Log文件
			log.info(bufferSQL.toString());
			ps = conn.prepareStatement(bufferSQL.toString());
			//执行
			long lTmp = ps.executeUpdate();
			log.info("逾期欠息修改成功 " + lTmp + " 条！");
			lReturn = 1;
			;
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new Exception();
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 查找活期结息分段计息
	 * @param conn
	 * @param subAccountID
	 * @param date
	 * @author xwhe   2008-08-10
	 * @throws SQLException
	 */
	public Collection selectSettlementInterest (long subAccountID, long accountID ,long lOfficeID, long lCurrencyID, Timestamp date) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DailyAccountBalanceInfo res = null;
		StringBuffer bufferSQL = new StringBuffer();
		Vector v = new Vector();
		try
		{
			conn = getConnection();
		/*
			bufferSQL.append(" select min(d.dtdate) startDate,max(d.dtdate) endDate,max(d.dtdate) - min(d.dtdate) days, sum(d.interest) interest,d.interestRate from sett_DailyAccountBalance daily ,sett_account account ");
			bufferSQL.append(" WHERE  \n");
			bufferSQL.append(" daily.naccountid= account.id  \n");
			bufferSQL.append(" and account.nofficeID= " + lOfficeID + " \n");
			bufferSQL.append(" and account.ncurrencyID=" + lCurrencyID + "  \n");
			bufferSQL.append(" and daily.dtdate >= (select max(t.dtdate) from sett_dailyaccountbalance t where t.nsubaccountid = " + subAccountID+"\n");
		    bufferSQL.append(" and t.mdailyinterest = t.minterest  and t.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd'))");
			bufferSQL.append(" and NSUBACCOUNTID = " + subAccountID);
			bufferSQL.append(" and daily.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')");
			bufferSQL.append(" order by daily.dtdate ");
			*/				
			bufferSQL.append(" select min(d.dtdate) startDate,max(d.dtdate) endDate, ");
			bufferSQL.append(" max(d.dtdate)-min(d.dtdate) days, ");
			bufferSQL.append(" sum(d.interest) interest ");
			bufferSQL.append(" ,d.interestRate  \n");
			bufferSQL.append(" from (select d.dtdate, ");		
			bufferSQL.append(" d.minterestrate interestRate,d.mdailyinterest interest ");
			bufferSQL.append(" from sett_dailyaccountbalance d \n");
			bufferSQL.append(" where d.naccountid = " + accountID);
			bufferSQL.append(" and d.nsubaccountid = " + subAccountID);
			bufferSQL.append(" and d.dtdate>= (select max(t.dtdate) from sett_dailyaccountbalance t where t.nsubaccountid = " + subAccountID+"\n");
		    bufferSQL.append(" and t.mdailyinterest = t.minterest  and t.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd'))");
			bufferSQL.append(" and d.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')");
			bufferSQL.append(" order by d.dtdate) d  \n");
			bufferSQL.append(" group by d.interestRate having round(sum(d.interest),6) >= 0.000001 order by startDate \n");			
			ps = conn.prepareStatement(bufferSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				res = new DailyAccountBalanceInfo();
				res = getInterestInfoFromRS(rs);
				v.add(res);
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
		return v;
	}
	/**
	 * 查找活期协定分段计息
	 * @param conn
	 * @param subAccountID
	 * @param date
	 * @author xwhe   2008-08-20
	 * @throws SQLException
	 */
	public Collection selectSettNegotiateInterest (long subAccountID, long accountID ,long lOfficeID, long lCurrencyID, Timestamp date) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DailyAccountBalanceInfo res = null;
		StringBuffer bufferSQL = new StringBuffer();
		Vector v = new Vector();
		try
		{
			conn = getConnection();				
			bufferSQL.append(" select min(d.dtdate) startDate,max(d.dtdate) endDate,sum(d.ac_mnegotiatebalance) ac_mnegotiatebalance,");
			bufferSQL.append(" max(d.dtdate)-min(d.dtdate) days, ");
			bufferSQL.append(" sum(d.interest) interest ");
			bufferSQL.append(" ,d.interestRate  \n");
			bufferSQL.append(" from (select d.dtdate, ");		
			bufferSQL.append(" d.ac_mnegotiaterate interestRate,d.ac_mdailynegotiateinterest interest,d.ac_mnegotiatebalance ac_mnegotiatebalance ");
			bufferSQL.append(" from sett_dailyaccountbalance d \n");
			bufferSQL.append(" where d.naccountid = " + accountID);
			bufferSQL.append(" and d.nsubaccountid = " + subAccountID);
			bufferSQL.append(" and d.dtdate>= (select max(t.dtdate) from sett_dailyaccountbalance t where t.nsubaccountid = " + subAccountID+"\n");
		    bufferSQL.append(" and t.ac_mdailynegotiateinterest = t.ac_mnegotiateinterest  and t.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd'))");
			bufferSQL.append(" and d.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')");
			bufferSQL.append(" order by d.dtdate) d  \n");
			bufferSQL.append(" group by d.interestRate  order by startDate \n");			
			ps = conn.prepareStatement(bufferSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				res = new DailyAccountBalanceInfo();
				res = getInterestInfoFromRS3(rs);
				v.add(res);
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
		return v;
	}
	
	//取活期余额的积数
	public double currentAmassBySubAccountIDAndDate( long subAccountID, Timestamp sDate, Timestamp eDate) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double sumOfInterestBlance = 0.0;
		StringBuffer bufferSQL = new StringBuffer();
		try
		{
			conn = getConnection();	
			bufferSQL.append(" SELECT SUM( minterestbalance \n ");
			bufferSQL.append(" ) FROM   \n");
			bufferSQL.append(" sett_dailyaccountbalance " );
			bufferSQL.append(" WHERE nsubaccountid = " + subAccountID);
			//起息日
			bufferSQL.append(" AND (DTDATE >= to_date('" + DataFormat.getDateString(sDate) + "','yyyy-mm-dd') \n"); 
			//止息日
			bufferSQL.append(" AND DTDATE <= to_date('" + DataFormat.getDateString(eDate) + "','yyyy-mm-dd') ) \n"); 
			ps = conn.prepareStatement(bufferSQL.toString());
		
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumOfInterestBlance = rs.getDouble(1);
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
		return sumOfInterestBlance;
	}
//	取协定的积数
	public double negoAmassBySubAccountIDAndDate( long subAccountID, Timestamp sDate, Timestamp eDate) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double sumOfInterestBlance = 0.0;
		StringBuffer bufferSQL = new StringBuffer();
		try
		{
			conn = getConnection();	
			bufferSQL.append(" SELECT SUM( AC_MNEGOTIATEBALANCE \n ");
			bufferSQL.append(" ) FROM   \n");
			bufferSQL.append(" sett_dailyaccountbalance " );
			bufferSQL.append(" WHERE nsubaccountid = " + subAccountID);
			//起息日
			bufferSQL.append(" AND (DTDATE >= to_date('" + DataFormat.getDateString(sDate) + "','yyyy-mm-dd') \n"); 
			//止息日
			bufferSQL.append(" AND DTDATE <= to_date('" + DataFormat.getDateString(eDate) + "','yyyy-mm-dd') ) \n"); 
			ps = conn.prepareStatement(bufferSQL.toString());
		
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumOfInterestBlance = rs.getDouble(1);
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
		return sumOfInterestBlance;
	}
	
	/**
	 * 查找活期结息分段计息
	 * @param conn
	 * @param subAccountID
	 * @param date
	 * @author xwhe   2008-08-10
	 * @throws Exception 
	 */
	public Collection selectSettlementInterest (long transActionTypeID, long subAccountID, long accountID ,long lOfficeID, long lCurrencyID, Timestamp date) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		DailyAccountBalanceInfo res = null;
		StringBuffer bufferSQL = new StringBuffer();
		StringBuffer bufferLoanSQL = new StringBuffer();
		StringBuffer bufferInterestSQL = new StringBuffer();
		StringBuffer bufferCurrenssSQL = new StringBuffer();
		Vector v = new Vector();
		try
		{
			conn = getConnection();
			
			if(transActionTypeID == SETTConstant.TransactionType.INTERESTFEEPAYMENT)
			{
				double balanceAmount = 0.0;
				
				System.out.println("111111111111111111111=="+date);
				
				bufferLoanSQL.append(" select st.mamount from sett_transrepaymentloan st ");
				bufferLoanSQL.append(" where st.nsubaccountid = "+ subAccountID +" and st.dtintereststart <= to_date('" + DataFormat.getDateString(date) + "', 'yyyy-mm-dd') ");
				bufferLoanSQL.append(" and st.nstatusid = 3 ");
				bufferLoanSQL.append(" and st.ntransactiontypeid != "+ SETTConstant.TransactionType.INTERESTFEEPAYMENT +" ");
				bufferLoanSQL.append(" order by st.dtintereststart desc ");
				ps2 = conn.prepareStatement(bufferLoanSQL.toString());
				rs2 = ps2.executeQuery();
				if (rs2.next())
				{
					//贷款收回金额
					balanceAmount = rs2.getDouble("mamount");
				}
				
				bufferSQL.append(" select min(d.dtdate) startDate,max(d.dtdate) endDate,sum(d.minterestbalance) minterestbalance,sum(d.ac_mnegotiatebalance) ac_mnegotiatebalance, ");
				bufferSQL.append(" max(d.dtdate)-min(d.dtdate) days, ");
				bufferSQL.append(" sum(d.interest) interest, ");
				bufferSQL.append(" d.interestRate  \n");
				bufferSQL.append(" from (select d.dtdate, ");		
				bufferSQL.append(" d.minterestrate interestRate,d.mdailyinterest interest ,d.minterestbalance minterestbalance,d.ac_mnegotiatebalance ac_mnegotiatebalance");
				bufferSQL.append(" from sett_dailyaccountbalance d \n");
				bufferSQL.append(" where d.naccountid = " + accountID);
				bufferSQL.append(" and d.nsubaccountid = " + subAccountID);
				bufferSQL.append(" and d.dtdate>= (select max(t.dtdate) from sett_dailyaccountbalance t where t.nsubaccountid = " + subAccountID+"\n");
			    bufferSQL.append(" and t.mdailyinterest = t.minterest  and t.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd'))");
				bufferSQL.append(" and d.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')");
				bufferSQL.append(" order by d.dtdate) d  \n");
				bufferSQL.append(" group by d.interestRate having round(sum(d.interest),6) >= 0.000001 order by startDate \n");			
				ps = conn.prepareStatement(bufferSQL.toString());
				rs = ps.executeQuery();
				while (rs.next())
				{
					double sumBalanceAmount = 0.0;
					double sumInterest = 0.0;
					long sumDate = -1;
					res = new DailyAccountBalanceInfo();
					res = getInterestInfoFromRS2(rs);
					
					sumDate = PrintVoucher.getIntervalDays(res.getStartDate(), DataFormat.getNextDate( res.getDate(), 1), 1);
					if(balanceAmount != 0.0){
						sumBalanceAmount = UtilOperation.Arith.mul(balanceAmount, sumDate);  //积数
					}else{
						sumBalanceAmount = res.getMCurrensInterestbalance();
					}
					
					sumInterest = 
						UtilOperation.Arith.div(
							UtilOperation.Arith.mul(sumBalanceAmount, res.getInterestRate() )
							, 36000);  //通过积数计算的利息
					
					res.setMCurrensInterestbalance(sumBalanceAmount);
					res.setInterest(sumInterest);
					
					v.add(res);
				}
			}
            //取出每个分段日期，判断该账户是否有在贷款收回sett_transrepaymentloan交易类型不是45的交易，若有则取sett_dailyaccountbalance 里面enddate 的minterestbalance*天数
			//没有的话保持现状
			else
			{
				bufferSQL.append(" select min(d.dtdate) startDate,max(d.dtdate) endDate,sum(d.minterestbalance) minterestbalance,sum(d.ac_mnegotiatebalance) ac_mnegotiatebalance, ");
				bufferSQL.append(" max(d.dtdate)-min(d.dtdate) days, ");
				bufferSQL.append(" sum(d.interest) interest, ");
				bufferSQL.append(" d.interestRate  \n");
				bufferSQL.append(" from (select d.dtdate, ");		
				bufferSQL.append(" d.minterestrate interestRate,d.mdailyinterest interest ,d.minterestbalance minterestbalance,d.ac_mnegotiatebalance ac_mnegotiatebalance");
				bufferSQL.append(" from sett_dailyaccountbalance d \n");
				bufferSQL.append(" where d.naccountid = " + accountID);
				bufferSQL.append(" and d.nsubaccountid = " + subAccountID);
				bufferSQL.append(" and d.dtdate>= (select max(t.dtdate) from sett_dailyaccountbalance t where t.nsubaccountid = " + subAccountID+"\n");
			    bufferSQL.append(" and t.mdailyinterest = t.minterest  and t.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd'))");
				bufferSQL.append(" and d.dtdate < to_date('" + DataFormat.getDateString(date) + "','yyyy-mm-dd')");
				bufferSQL.append(" order by d.dtdate) d  \n");
				bufferSQL.append(" group by d.interestRate having round(sum(d.interest),6) >= 0.000001 order by startDate \n");			
				ps = conn.prepareStatement(bufferSQL.toString());
				rs = ps.executeQuery();
				while (rs.next())
				{
					bufferInterestSQL = new StringBuffer();
					res = new DailyAccountBalanceInfo();
					res = getInterestInfoFromRS2(rs);
					
					long lRecordCount = -1;
					bufferInterestSQL.append(" select count(*) from sett_transrepaymentloan st ");
					bufferInterestSQL.append(" where st.nsubaccountid = "+ subAccountID +" and st.dtintereststart <= to_date('" + DataFormat.getDateString(res.getDate()) + "', 'yyyy-mm-dd') ");
					bufferInterestSQL.append(" and st.dtintereststart >= to_date('" + DataFormat.getDateString(res.getStartDate()) + "', 'yyyy-mm-dd') ");
					bufferInterestSQL.append(" and st.nstatusid = 3 ");
					bufferInterestSQL.append(" and st.ntransactiontypeid != "+ SETTConstant.TransactionType.INTERESTFEEPAYMENT +" ");
					bufferInterestSQL.append(" order by st.dtintereststart desc ");
					ps3 = conn.prepareStatement(bufferInterestSQL.toString());
					rs3 = ps3.executeQuery();
					if (rs3 != null&& rs3.next())
	                {
	                lRecordCount = rs3.getLong(1);
	                }
					if(lRecordCount>0)
					{
					bufferCurrenssSQL = new StringBuffer();	
					bufferCurrenssSQL.append(" select k.minterestbalance amount from sett_dailyaccountbalance k ");
					bufferCurrenssSQL.append(" where k.dtdate = to_date('" + DataFormat.getDateString(res.getDate()) + "','yyyy-mm-dd')");
					bufferCurrenssSQL.append(" and k.nsubaccountid = " + subAccountID);
					bufferCurrenssSQL.append(" and k.naccountid = " + accountID);
					bufferCurrenssSQL.append(" order by k.dtdate \n");
					ps4 = conn.prepareStatement(bufferCurrenssSQL.toString());
					rs4 = ps4.executeQuery();
					if (rs4 != null && rs4.next())
	                {
					double minterestbalance = rs4.getDouble("amount");
					long days =PrintVoucher.getIntervalDays(res.getStartDate(), DataFormat.getNextDate( res.getDate(), 1), 1);				
					double CurrentAmassAmount = UtilOperation.Arith.mul(minterestbalance, days);  //积数
					
					double CurrentInterest = 
						UtilOperation.Arith.div(
							UtilOperation.Arith.mul(CurrentAmassAmount, res.getInterestRate() )
							, 36000);  //通过积数计算的利息
					res.setMCurrensInterestbalance(CurrentAmassAmount);
					res.setInterest(CurrentInterest);
	                }
					}
					v.add(res);
				}
			}
			
			
			cleanup(rs4);
			cleanup(ps4);
			cleanup(rs3);
			cleanup(ps3);
			cleanup(rs);
			cleanup(ps);
			cleanup(rs2);
			cleanup(ps2);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs4);
			cleanup(ps4);
			cleanup(rs3);
			cleanup(ps3);
			cleanup(rs);
			cleanup(ps);
			cleanup(rs2);
			cleanup(ps2);
			cleanup(conn);
		}
		
		return v;
	}
	
	public DailyAccountBalanceInfo getInterestInfoFromRS(ResultSet rs) throws SQLException
	{
		DailyAccountBalanceInfo resInfo = new DailyAccountBalanceInfo();
		
		resInfo.setStartDate(rs.getTimestamp("startDate"));
		resInfo.setDate(rs.getTimestamp("endDate"));
		resInfo.setInterestRate(rs.getDouble("interestRate"));
		resInfo.setInterest(rs.getDouble("interest"));
		
		return resInfo;
	}
	
	public DailyAccountBalanceInfo getInterestInfoFromRS2(ResultSet rs) throws SQLException
	{
		DailyAccountBalanceInfo resInfo = new DailyAccountBalanceInfo();
		
		resInfo.setStartDate(rs.getTimestamp("startDate"));
		resInfo.setDate(rs.getTimestamp("endDate"));
		resInfo.setInterestRate(rs.getDouble("interestRate"));
		resInfo.setInterest(rs.getDouble("interest"));
		resInfo.setMAc_mnegoInterestbalance(rs.getDouble("ac_mnegotiatebalance"));
		resInfo.setMCurrensInterestbalance(rs.getDouble("minterestbalance"));
		
		return resInfo;
	}
	public DailyAccountBalanceInfo getInterestInfoFromRS3(ResultSet rs) throws SQLException
	{
		DailyAccountBalanceInfo resInfo = new DailyAccountBalanceInfo();
		
		resInfo.setStartDate(rs.getTimestamp("startDate"));
		resInfo.setDate(rs.getTimestamp("endDate"));
		resInfo.setInterestRate(rs.getDouble("interestRate"));
		resInfo.setInterest(rs.getDouble("interest"));
		resInfo.setMAc_mnegoInterestbalance(rs.getDouble("ac_mnegotiatebalance"));
		
		return resInfo;
	}
	
	/**
	 * 查找贷款分段计息
	 * @param conn
	 * @param subAccountID
	 * @param date
	 * @author leiyang3  2011.01.17
	 * @throws SQLException
	 */
	public Collection selectLoanInterest (long subAccountID, long accountID ,long lOfficeID, long lCurrencyID, Timestamp date) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DailyAccountBalanceInfo resInfo = null;
		StringBuffer bufferSQL = new StringBuffer();
		Vector v = new Vector();
		
		try
		{
			conn = getConnection();				
			bufferSQL.append(" select d.minterestrate interestRate, min(d.dtdate) startDate, max(d.dtdate) endDate,  \n");
			bufferSQL.append(" count(d.minterestrate) days, max(d.minterest) interest \n");
			bufferSQL.append(" from sett_dailyaccountbalance d \n");
			bufferSQL.append(" where d.naccountid = " + accountID);
			bufferSQL.append(" and d.nsubaccountid = " + subAccountID);		
			bufferSQL.append(" and d.dtdate < to_date('" + DataFormat.getDateString(date) + "', 'yyyy-mm-dd') \n");
			bufferSQL.append(" and d.mdailyinterest > 0.0 \n");
			bufferSQL.append(" group by d.minterestrate \n");	
			bufferSQL.append(" order by startDate ");	
			ps = conn.prepareStatement(bufferSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				resInfo = new DailyAccountBalanceInfo();
				
				resInfo.setStartDate(rs.getTimestamp("startDate"));
				resInfo.setDate(rs.getTimestamp("endDate"));
				resInfo.setInterestRate(rs.getDouble("interestRate"));
				resInfo.setInterest(rs.getDouble("interest"));
				
				v.add(resInfo);
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
		return v;
	}
	
	/**
	 * 查找贷款复利分段计息
	 * @param conn
	 * @param subAccountID
	 * @param date
	 * @author leiyang3  2011.01.17
	 * @throws SQLException
	 */
	public Collection selectLoanCompoundInterest (long subAccountID, long accountID ,long lOfficeID, long lCurrencyID, Timestamp date) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DailyAccountBalanceInfo resInfo = null;
		StringBuffer bufferSQL = new StringBuffer();
		Vector v = new Vector();
		
		try
		{
			conn = getConnection();		
			bufferSQL.append(" select * from ( ");
			bufferSQL.append(" select d.minterestrate compoundInterestRate, min(d.dtdate) startDate, max(d.dtdate) endDate,  \n");
			bufferSQL.append(" count(d.mforfeitinterestrate) days, max(d.MCOMPOUNDINTEREST) compoundInterest \n");
			bufferSQL.append(" from sett_dailyaccountbalance d \n");
			bufferSQL.append(" where d.naccountid = " + accountID);
			bufferSQL.append(" and d.nsubaccountid = " + subAccountID);		
			bufferSQL.append(" and d.dtdate < to_date('" + DataFormat.getDateString(date) + "', 'yyyy-mm-dd') \n");
			bufferSQL.append(" and d.mdailyinterest > 0.0 \n");
			bufferSQL.append(" and d.mcompounddailyinterset > 0.0 \n");
			bufferSQL.append(" group by d.minterestrate \n");
			bufferSQL.append(" order by startDate ) ");
			
			bufferSQL.append(" union ");		
			
			bufferSQL.append(" select * from ( ");
			bufferSQL.append(" select d.mforfeitinterestrate compoundInterestRate, min(d.dtdate) startDate, max(d.dtdate) endDate,  \n");
			bufferSQL.append(" count(d.mforfeitinterestrate) days, max(d.MCOMPOUNDINTEREST) compoundInterest  \n");
			bufferSQL.append(" from sett_dailyaccountbalance d  \n");
			bufferSQL.append(" where d.naccountid = " + accountID);
			bufferSQL.append(" and d.nsubaccountid = " + subAccountID);		
			bufferSQL.append(" and d.dtdate < to_date('" + DataFormat.getDateString(date) + "', 'yyyy-mm-dd')  \n");
			bufferSQL.append(" and d.mdailyinterest = 0.0  \n");
			bufferSQL.append(" and d.mcompounddailyinterset > 0.0  \n");
			bufferSQL.append(" group by d.mforfeitinterestrate  \n");
			bufferSQL.append(" order by startDate ) ");
			
			ps = conn.prepareStatement(bufferSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				resInfo = new DailyAccountBalanceInfo();
				
				resInfo.setStartDate(rs.getTimestamp("startDate"));
				resInfo.setDate(rs.getTimestamp("endDate"));
				resInfo.setInterestRate(rs.getDouble("compoundInterestRate"));
				resInfo.setMcompoundinterest(rs.getDouble("compoundInterest"));
				
				v.add(resInfo);
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
		return v;
	}
	
	/**
	 * 查找贷款罚息分段计息
	 * @param conn
	 * @param subAccountID
	 * @param date
	 * @author leiyang3  2011.01.17
	 * @throws SQLException
	 */
	public Collection selectLoanOverDueInterest (long subAccountID, long accountID ,long lOfficeID, long lCurrencyID, Timestamp date) throws SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DailyAccountBalanceInfo resInfo = null;
		StringBuffer bufferSQL = new StringBuffer();
		Vector v = new Vector();
		
		try
		{
			conn = getConnection();				
			bufferSQL.append(" select d.mforfeitinterestrate forfeitIntersetRate, min(d.dtdate) startDate, max(d.dtdate) endDate,  \n");
			bufferSQL.append(" count(d.mforfeitinterestrate) days, max(d.MFORFEITINTEREST) forfeitInterset \n");
			bufferSQL.append(" from sett_dailyaccountbalance d \n");
			bufferSQL.append(" where d.naccountid = " + accountID);
			bufferSQL.append(" and d.nsubaccountid = " + subAccountID);		
			bufferSQL.append(" and d.dtdate < to_date('" + DataFormat.getDateString(date) + "', 'yyyy-mm-dd') \n");
			bufferSQL.append(" and d.mdailyinterest = 0.0 \n");
			bufferSQL.append(" group by d.mforfeitinterestrate \n");		
			bufferSQL.append(" order by startDate ");
			ps = conn.prepareStatement(bufferSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				resInfo = new DailyAccountBalanceInfo();
				
				resInfo.setStartDate(rs.getTimestamp("startDate"));
				resInfo.setDate(rs.getTimestamp("endDate"));
				resInfo.setMForfeitInterestRate(rs.getDouble("forfeitIntersetRate"));
				resInfo.setMforfeitinterest(rs.getDouble("forfeitInterset"));
				
				v.add(resInfo);
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
		return v;
	}
	
}