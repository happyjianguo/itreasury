/*
 * Created on 2003-12-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Constant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QAverageAccountBalance extends BaseQueryObject
{
	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_ClientCode = 2;
	public final static int OrderBy_ClientName = 3;
	public final static int OrderBy_AccountTypeID = 4;
	public final static int OrderBy_AccountStatusID = 5;
	public final static int OrderBy_OpenDate = 6;
	//    
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	boolean isToday = false;
	/**
	 * 
	 */
	public QAverageAccountBalance()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}

	public int queryIntervalDays(Timestamp tsStartDate, Timestamp tsEndDate) throws Exception
	{
		int nIntervalDays = 1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select (to_date('" + DataFormat.getDateString(tsEndDate) + "','yyyy-mm-dd')-to_date('" + DataFormat.getDateString(tsStartDate) + "','yyyy-mm-dd')+1) IntervalDays from dual";
			logger.info(sql);
			con = this.getConnection();
			ps = con.prepareCall(sql);
			rs = ps.executeQuery();
			if (rs.next())
				nIntervalDays = rs.getInt("IntervalDays");
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return nIntervalDays;
	}
	public void getAverageAccountBalanceBySubAccountID(QueryAccountWhereInfo qawi)
	{

		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" * \n");
		//
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( \n");
		m_sbFrom.append("     select  AccountID,AccountNo,AccountName,SubAccountID,FormNo,sum(Balance) Balance,sum(NegotiateBalance) NegotiateBalance," + qawi.getIntervalDays() + " IntervalDays \n");
		m_sbFrom.append("     from \n");
		m_sbFrom.append("     ( \n");
		m_sbFrom.append("         select a.id AccountID,a.saccountno AccountNo, a.sname AccountName,sa.id SubAccountID, sa.AF_sDepositNo||vc.contractcode||'::'||vc.loanPayCode FormNo, \n");
		m_sbFrom.append("                c.nClienttypeID1,c.nClienttypeID2, c.nClienttypeID3, c.nClienttypeID4, c.nClienttypeID5, c.nClienttypeID6, \n");
		m_sbFrom.append("                 a.nAccountTypeid,sa.ac_nIsNegotiate IsNegotiate, \n");	
		m_sbFrom.append("                round(nvl(decode(sa.nIsInterest,1,da.mInterestBalance,da.mBalance),0),2) Balance, \n");
		m_sbFrom.append("                round(nvl(da.AC_mNegotiateBalance,0),2) NegotiateBalance \n");
		m_sbFrom.append("         from client c,sett_account a, sett_subAccount sa, sett_vcontractinfo vc,sett_dailyaccountbalance da \n");
		m_sbFrom.append("         where a.nofficeid=" + qawi.getOfficeID() + " and a.ncurrencyid=" + qawi.getCurrencyID() + " and a.id=da.naccountid and c.id=a.nclientid and da.nSubAccountID=sa.ID \n");
		m_sbFrom.append("               and sa.al_nLoanNoteID=vc.LoanPayID(+) \n");
		m_sbFrom.append("               and da.dtDate between to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') ");
		m_sbFrom.append(" and to_date('" + DataFormat.getDateString(qawi.getEndQueryDate()) + "','yyyy-mm-dd') \n");
		if (isToday == true)
		{
			m_sbFrom.append("         union all \n");
			m_sbFrom.append("         select a.id AccountID,a.saccountno AccountNo, a.sname AccountName,sa.id SubAccountID,sa.AF_sDepositNo||vc.contractcode||'::'||vc.loanPayCode FormNo, \n");
			m_sbFrom.append("                c.nClienttypeID1,c.nClienttypeID2, c.nClienttypeID3, c.nClienttypeID4, c.nClienttypeID5, c.nClienttypeID6, \n");
			m_sbFrom.append("                 a.nAccountTypeid,sa.ac_nIsNegotiate IsNegotiate, \n");
			m_sbFrom.append("                round(nvl(sa.mbalance,0))-decode(sign(nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0)),-1,0,decode(sa.ac_nIsNegotiate,1,floor((nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0))/sa.ac_mNegotiateUnit)*sa.ac_mNegotiateUnit,0)),2) Balance, \n");
			m_sbFrom.append("                decode(sign(nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0)),-1,0, \n");
			m_sbFrom.append("                   decode(sa.ac_nIsNegotiate,1,floor((nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0))/sa.ac_mNegotiateUnit)*sa.ac_mNegotiateUnit,0)) NegotiateBalance \n");
			m_sbFrom.append("         from client c,sett_account a, sett_vcontractinfo vc,sett_subaccount sa \n");
			m_sbFrom.append("         where a.nofficeid=" + qawi.getOfficeID() + " and a.ncurrencyid=" + qawi.getCurrencyID() + " and a.id=sa.naccountid   and c.id=a.nclientid \n");
			m_sbFrom.append("               and sa.al_nLoanNoteID=vc.LoanPayID(+) \n");
		}
		m_sbFrom.append("     ) \n");
		m_sbFrom.append("     where 1=1 \n");
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			m_sbFrom.append(" and AccountNo>='" + qawi.getStartAccountNo() + "' ");
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			m_sbFrom.append(" and AccountNo<='" + qawi.getEndAccountNo() + "' ");
		if (qawi.getAccountTypeID() > 0)
			m_sbFrom.append(" and nAccountTypeid=" + qawi.getAccountTypeID());
		if (qawi.getEnterpriseTypeID1() > 0)
			m_sbFrom.append("    and   nClienttypeID1 = " + qawi.getEnterpriseTypeID1());
		if (qawi.getEnterpriseTypeID2() > 0)
			m_sbFrom.append("    and   nClienttypeID2 = " + qawi.getEnterpriseTypeID2());
		if (qawi.getEnterpriseTypeID3() > 0)
			m_sbFrom.append("    and   nClienttypeID3 = " + qawi.getEnterpriseTypeID3());
		if (qawi.getEnterpriseTypeID4() > 0)
			m_sbFrom.append("    and   nClienttypeID4 = " + qawi.getEnterpriseTypeID4());
		if (qawi.getEnterpriseTypeID5() > 0)
			m_sbFrom.append("    and   nClienttypeID5 = " + qawi.getEnterpriseTypeID5());
		if (qawi.getEnterpriseTypeID6() > 0)
			m_sbFrom.append("    and   nClienttypeID6 = " + qawi.getEnterpriseTypeID6());
		if (qawi.getIsNegotiate() > 0)
			m_sbFrom.append(" and IsNegotiate=1");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append(" and Balance>0");
		m_sbFrom.append("     group by AccountID,AccountNo,AccountName,SubAccountID,FormNo \n");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("     having sum(Balance)>0 ");
		m_sbFrom.append(" ) rs \n");
		//
		m_sbWhere = new StringBuffer();
		//
		//sbOrderBy = new StringBuffer();       
		String strDesc = qawi.getDesc() == 1 ? " desc " : "";
		switch ((int) qawi.getOrderField())
		{
			case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n order by AccountNo" + strDesc);
				break;
			case OrderBy_ClientCode :
				m_sbOrderBy.append(" \n order by ClientCode" + strDesc);
				break;
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n order by ClientName" + strDesc);
				break;
			case OrderBy_AccountTypeID :
				m_sbOrderBy.append(" \n order by AccountTypeID" + strDesc);
				break;
			case OrderBy_AccountStatusID :
				m_sbOrderBy.append(" \n order by MainAccountStatusID" + strDesc);
				break;
			case OrderBy_OpenDate :
				m_sbOrderBy.append(" \n order by OpenDate" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by AccountNo" + strDesc);
				break;
		}
	}

	public void getAverageAccountBalanceByAccountID(QueryAccountWhereInfo qawi)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" * \n");
		//
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( \n");
//		m_sbFrom.append("     select AccountID,AccountNo,AccountName,ClientCode,nAccountTypeid,sum(Balance) Balance,sum(decode(NegotiateUnit,0,0,floor( decode(sign(Balance-NegotiateAmount-1),-1,0,(Balance-NegotiateAmount))/NegotiateUnit)*NegotiateUnit)  )  NegotiateBalance, \n");
		m_sbFrom.append("     select AccountID,AccountNo,AccountName,ClientCode,nAccountTypeid,sum(Balance)+sum(NegotiateBalance) Balance,sum(NegotiateBalance)  NegotiateBalance, \n");
		//按查询区间和开户日期综合查询天数
		if(Config.getBoolean(ConfigConstant.AVERAGE_DATE_TYPE,true)){
		m_sbFrom.append("    (select (select case when seacct.dtfinish <= to_date('" + DataFormat.getDateString(isToday?DataFormat.getNextDate(qawi.getEndQueryDate()):qawi.getEndQueryDate()) + "', 'yyyy-mm-dd') then seacct.dtfinish else  to_date('" + DataFormat.getDateString(isToday?DataFormat.getNextDate(qawi.getEndQueryDate()):qawi.getEndQueryDate()) + " ', 'yyyy-mm-dd') end endDate from sett_account seacct where seacct.id = AccountID) - (select case when seacct.dtopen >= to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "', 'yyyy-mm-dd') then seacct.dtopen  else to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "', 'yyyy-mm-dd') end startDate from sett_account seacct where seacct.id = AccountID) + 1 from dual) IntervalDays \n");
		}else {//按查询区间查询天数
		m_sbFrom.append(" " + qawi.getIntervalDays() + " IntervalDays \n");	
		}
		//
		m_sbFrom.append("     from \n");
		m_sbFrom.append("     ( \n");
		m_sbFrom.append("         select a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
		m_sbFrom.append("                c.nClienttypeID1,c.nClienttypeID2, c.nClienttypeID3, c.nClienttypeID4, c.nClienttypeID5, c.nClienttypeID6,c.nCustomerManagerUserId, \n");
		m_sbFrom.append("                 a.nAccountTypeid,sa.ac_nIsNegotiate IsNegotiate,c.SCODE ClientCode, \n");
		m_sbFrom.append("                round(nvl(da.minterestbalance,0),2) Balance, \n");
//		m_sbFrom.append("                round(nvl(da.mbalance,0),2) Balance, \n");
		m_sbFrom.append("                round(nvl(da.AC_mNegotiateBalance,0),2) NegotiateBalance \n");
		m_sbFrom.append("                ,round(nvl(sa.AC_mNegotiateAmount,0),2) NegotiateAmount,round(nvl(sa.AC_mNegotiateUnit,0),2) NegotiateUnit \n");
		m_sbFrom.append("         from client c,sett_account a, sett_subAccount sa,sett_dailyaccountbalance da \n");
		m_sbFrom.append("         where a.nofficeid=" + qawi.getOfficeID() + " and a.ncurrencyid=" + qawi.getCurrencyID() + " and c.id=a.nclientid and a.id=da.naccountid \n");
		m_sbFrom.append("               and da.nSubAccountID=sa.ID  and a.naccounttypeid not in (select ty.id from sett_accounttype ty where ty.naccountgroupid=" + SETTConstant.AccountGroupType.DISCOUNT + ") \n");
		m_sbFrom.append("               and da.dtDate between to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd')");
		m_sbFrom.append(" and to_date('" + DataFormat.getDateString(qawi.getEndQueryDate()) + "','yyyy-mm-dd') \n");
		//查询有效的账户信息
		if(Config.getBoolean(ConfigConstant.AVERAGE_DATE_TYPE,true)){
		m_sbFrom.append(" and (a.dtfinish>to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') or a.dtfinish is null ) \n");
		}
		//jiangqi 2011-02-25 
		//BUG #3938::贴现账户平均余额分析数据不正确
		//贴现贷款账户，显示查询取值的是每日余额表sett_dailyaccountbalance的mbalance字段(原来不区分时去的minterestbalance字段)
		m_sbFrom.append("  union  all  \n");
		m_sbFrom.append("         select a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
		m_sbFrom.append("                c.nClienttypeID1,c.nClienttypeID2, c.nClienttypeID3, c.nClienttypeID4, c.nClienttypeID5, c.nClienttypeID6,c.nCustomerManagerUserId, \n");
		m_sbFrom.append("                 a.nAccountTypeid,sa.ac_nIsNegotiate IsNegotiate,c.SCODE ClientCode, \n");
		m_sbFrom.append("                round(nvl(da.mbalance,0),2) Balance, \n");
//		m_sbFrom.append("                round(nvl(da.mbalance,0),2) Balance, \n");
		m_sbFrom.append("                round(nvl(da.AC_mNegotiateBalance,0),2) NegotiateBalance \n");
		m_sbFrom.append("                ,round(nvl(sa.AC_mNegotiateAmount,0),2) NegotiateAmount,round(nvl(sa.AC_mNegotiateUnit,0),2) NegotiateUnit \n");
		m_sbFrom.append("         from client c,sett_account a, sett_subAccount sa,sett_dailyaccountbalance da \n");
		m_sbFrom.append("         where a.nofficeid=" + qawi.getOfficeID() + " and a.ncurrencyid=" + qawi.getCurrencyID() + " and c.id=a.nclientid and a.id=da.naccountid \n");
		m_sbFrom.append("               and da.nSubAccountID=sa.ID  and a.naccounttypeid in (select ty.id from sett_accounttype ty where ty.naccountgroupid=" + SETTConstant.AccountGroupType.DISCOUNT + ") \n");
		m_sbFrom.append("               and da.dtDate between to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd')");
		m_sbFrom.append(" and to_date('" + DataFormat.getDateString(qawi.getEndQueryDate()) + "','yyyy-mm-dd') \n");
		//查询有效的账户信息
		if(Config.getBoolean(ConfigConstant.AVERAGE_DATE_TYPE,true)){
		m_sbFrom.append(" and (a.dtfinish>to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') or a.dtfinish is null ) \n");
		}

		if (isToday == true)
		{
			m_sbFrom.append("         union all \n");
			m_sbFrom.append("         select a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
			m_sbFrom.append("                c.nClienttypeID1,c.nClienttypeID2, c.nClienttypeID3, c.nClienttypeID4, c.nClienttypeID5, c.nClienttypeID6,c.nCustomerManagerUserId, \n");
			m_sbFrom.append("                a.nAccountTypeid,sa.ac_nIsNegotiate IsNegotiate,c.SCODE ClientCode, \n");
			m_sbFrom.append(" round((nvl(sa.mbalance,0) -round(decode(sign(nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0)),-1,0,  \n");                      
			m_sbFrom.append(" decode(sa.ac_nIsNegotiate,1,floor((nvl(sa.mBalance,0)\n");
			m_sbFrom.append(" -nvl(sa.AC_mNegotiateAmount,0))/sa.ac_mNegotiateUnit)*sa.ac_mNegotiateUnit,0)),2)),2) Balance,\n");
			m_sbFrom.append("                round(decode(sign(nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0)),-1,0, \n");
			m_sbFrom.append("                       decode(sa.ac_nIsNegotiate,1,floor((nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0))/sa.ac_mNegotiateUnit)*sa.ac_mNegotiateUnit,0)),2) NegotiateBalance \n");
			m_sbFrom.append("                ,round(nvl(sa.AC_mNegotiateAmount,0),2) NegotiateAmount,round(nvl(sa.AC_mNegotiateUnit,0),2) NegotiateUnit \n");
			m_sbFrom.append("         from   client c,sett_account a, sett_subaccount sa \n");
			m_sbFrom.append("         where  a.nofficeid=" + qawi.getOfficeID() + " and a.ncurrencyid=" + qawi.getCurrencyID() + " and c.id=a.nclientid and a.id=sa.naccountid \n");
			//查询有效的账户信息
			if(Config.getBoolean(ConfigConstant.AVERAGE_DATE_TYPE,true)){
			m_sbFrom.append(" and (a.dtfinish>=to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') or a.dtfinish is null )\n");
			}
		}
		m_sbFrom.append("     ) \n");
		m_sbFrom.append("     where 1=1 \n");
		System.out.println("zhaokehuid----::"+qawi.getClientManager());
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			m_sbFrom.append(" and AccountNo>='" + qawi.getStartAccountNo() + "' ");
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			m_sbFrom.append(" and AccountNo<='" + qawi.getEndAccountNo() + "' ");
		if (qawi.getStartClientCode() != null && qawi.getStartClientCode().length() > 0)
			m_sbFrom.append(" and ClientCode>='" + qawi.getStartClientCode() + "' ");
		if (qawi.getEndClientCode() != null && qawi.getEndClientCode().length() > 0)
			m_sbFrom.append(" and ClientCode<='" + qawi.getEndClientCode() + "' ");
		if (qawi.getClientManager() > 0)
			m_sbFrom.append(" and nCustomerManagerUserId=" + qawi.getClientManager());
		//if (qawi.getAccountTypeID() > 0)
		if(qawi.getAccountTypeIDArray()!= null && qawi.getAccountTypeIDArray().length>0)
			{
				String[] strID = qawi.getAccountTypeIDArray();
				StringBuffer sbId = new StringBuffer();
				for(int m = 0;m<strID.length;m++)
				{
					sbId.append(strID[m]).append(",");
				}
				m_sbFrom.append(" and nAccountTypeid in ( " + sbId.substring(0, sbId.length()-1) +" ) ");
			}
		if (qawi.getEnterpriseTypeID1() > 0)
			m_sbFrom.append("    and   nClienttypeID1 = " + qawi.getEnterpriseTypeID1());
		if (qawi.getEnterpriseTypeID2() > 0)
			m_sbFrom.append("    and   nClienttypeID2 = " + qawi.getEnterpriseTypeID2());
		if (qawi.getEnterpriseTypeID3() > 0)
			m_sbFrom.append("    and   nClienttypeID3 = " + qawi.getEnterpriseTypeID3());
		if (qawi.getEnterpriseTypeID4() > 0)
			m_sbFrom.append("    and   nClienttypeID4 = " + qawi.getEnterpriseTypeID4());
		if (qawi.getEnterpriseTypeID5() > 0)
			m_sbFrom.append("    and   nClienttypeID5 = " + qawi.getEnterpriseTypeID5());
		if (qawi.getEnterpriseTypeID6() > 0)
			m_sbFrom.append("    and   nClienttypeID6 = " + qawi.getEnterpriseTypeID6());
		if (qawi.getIsNegotiate() > 0)
			m_sbFrom.append(" and IsNegotiate=1");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append(" and Balance>0");
		m_sbFrom.append("     group by AccountID,AccountNo,ClientCode,AccountName,nAccountTypeid \n");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("     having sum(Balance)>0 ");
		m_sbFrom.append(" ) rs      \n");

		//
		m_sbWhere = new StringBuffer();
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qawi.getDesc() == 1 ? " desc " : "";
		switch ((int) qawi.getOrderField())
		{
			case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n order by AccountNo" + strDesc);
				break;
			case OrderBy_ClientCode :
				m_sbOrderBy.append(" \n order by ClientCode" + strDesc);
				break;
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n order by AccountName" + strDesc);
				break;
			case OrderBy_AccountTypeID :
				m_sbOrderBy.append(" \n order by AccountTypeID" + strDesc);
				break;
			case OrderBy_AccountStatusID :
				m_sbOrderBy.append(" \n order by MainAccountStatusID" + strDesc);
				break;
			case OrderBy_OpenDate :
				m_sbOrderBy.append(" \n order by OpenDate" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by AccountNo" + strDesc);
				break;
		}
	}
	/**
	 * 查询-平均余额分析::按账户汇总余额
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAverageAccountBalanceByAccountID(QueryAccountWhereInfo qawi) throws Exception
	{

		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			qawi.setStartClientCode(null);
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			qawi.setEndClientCode(null);
		QueryAccountWhereInfo qc = new QueryAccountWhereInfo();
		// 判断查询的结束日期是否是今天
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate()))
		{
			isToday = true;
			qc.copy(qawi);
			qc.setEndQueryDate(DataFormat.getPreviousDate(qawi.getEndQueryDate()));
		}
		else
		{
			qc.copy(qawi);
			isToday = false;
		}
		qc.setIntervalDays(queryIntervalDays(qawi.getStartQueryDate(), qawi.getEndQueryDate()));
		getAverageAccountBalanceByAccountID(qc);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAverageAccountResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;

	}
	/**
	 * 查询-平均余额分析::按活期、定期、通知汇总余额
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public void getAverageAccountBalanceByAccountType(QueryAccountWhereInfo qawi)
	{
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" * \n");
		//
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( \n");
		m_sbFrom.append(
			" select ClientCode, ClientName, round(Sum(CurrentBalance)/"
				+ qawi.getIntervalDays()
				+ ",2) CurrentBalance,round(Sum(CurrentInterestBalance)/"
				+ qawi.getIntervalDays()
				+ ",2) CurrentInterestBalance,round(Sum(CurrentNegotiateBalance)/"
				+ qawi.getIntervalDays()
				+ ",2) CurrentNegotiateBalance,round(Sum(FixBalance)/"
				+ qawi.getIntervalDays()
				+ ",2) FixBalance,round(Sum(NoticeBalance)/"
				+ qawi.getIntervalDays()
				+ ",2) NoticeBalance \n");
		m_sbFrom.append(" from ( \n");
		m_sbFrom.append("     -- 活期、定期、通知 \n");
		m_sbFrom.append("     select ClientCode, ClientName, CurrentBalance,CurrentInterestBalance,CurrentNegotiateBalance,FixBalance,NoticeBalance, \n");
		m_sbFrom.append("     nClienttypeID1, nClienttypeID2, nClienttypeID3, nClienttypeID4, nClienttypeID5, nClienttypeID6 \n");
		m_sbFrom.append("     from ( \n");
		m_sbFrom.append("     -- 历史余额 \n");
		m_sbFrom.append("     select c.scode ClientCode, c.sname ClientName,a.nofficeid OfficeID,a.nCurrencyID CurrencyID, a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
		m_sbFrom.append("                c.nClienttypeID1 nClienttypeID1,c.nClienttypeID2 nClienttypeID2, c.nClienttypeID3 nClienttypeID3, c.nClienttypeID4 nClienttypeID4, c.nClienttypeID5 nClienttypeID5, c.nClienttypeID6 nClienttypeID6, \n");
		m_sbFrom.append("            round(nvl(da.mBalance,0),2) CurrentBalance,round(nvl(da.mInterestBalance,0),2) CurrentInterestBalance,decode(sa.AC_mNegotiateUnit,0,0,floor( decode(sign(da.mBalance-sa.AC_mNegotiateAmount-1),-1,0,(da.mBalance-sa.AC_mNegotiateAmount))/sa.AC_mNegotiateUnit)*sa.AC_mNegotiateUnit)   CurrentNegotiateBalance,0.00 FixBalance,0.00 NoticeBalance \n");
		m_sbFrom.append("     from client c,sett_account a, sett_dailyAccountBalance da,sett_subaccount sa \n");
		m_sbFrom.append("     where c.id=a.nclientid and a.id=da.naccountid and a.id=sa.naccountid \n");
		//Forest注释，2004-04-05，因为活期存款只包括活期存款账户（01类型的）
		//m_sbFrom.append("           and a.naccounttypeid in(" + getCurrentAccountType(qawi.getCurrencyID()) + ") \n");
		//m_sbFrom.append("           and a.naccounttypeid in(select id from sett_accountType where nAccountGroupID="+ SETTConstant.AccountGroupType.CURRENT +") \n");
		m_sbFrom.append("           and a.naccounttypeid in(select id from sett_accounttype where NACCOUNTGROUPID in ("+ SETTConstant.AccountGroupType.CURRENT +") and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+") \n");		
		//Modify by xfma(马现福) 2008/07/08
		//账户信息查询 存款合计 不统计委托存款账户余额(国电专用)
		m_sbFrom.append("           and a.nAccounttypeid !=nvl((select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT +" and nstatusId=1 and officeId="+ qawi.getOfficeID() +" and currencyId="+ qawi.getCurrencyID() +" and saccounttypecode = '"+Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+"' ),-999) \n");
		m_sbFrom.append("           and da.dtDate between to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') and to_date('" + DataFormat.getDateString(qawi.getEndQueryDate()) + "','yyyy-mm-dd') \n");
		m_sbFrom.append("     union all \n");
		m_sbFrom.append("     select c.scode ClientCode, c.sname ClientName,a.nofficeid OfficeID,a.nCurrencyID CurrencyID,  a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
		m_sbFrom.append("                c.nClienttypeID1 nClienttypeID1,c.nClienttypeID2 nClienttypeID2, c.nClienttypeID3 nClienttypeID3, c.nClienttypeID4 nClienttypeID4, c.nClienttypeID5 nClienttypeID5, c.nClienttypeID6 nClienttypeID6, \n");		
		m_sbFrom.append("            0.00 CurrentBalance,0.00 CurrentInterestBalance,0.00 CurrentNegotiateBalance,round(nvl(da.mBalance,0),2) FixBalance,0.00 NoticeBalance \n");
		m_sbFrom.append("     from client c,sett_account a, sett_dailyAccountBalance da \n");
		m_sbFrom.append("     where c.id=a.nclientid and a.id=da.naccountid \n");
		//m_sbFrom.append("           and a.naccounttypeid in(2,14) \n");
		m_sbFrom.append("           and a.naccounttypeid in(select id from sett_accounttype where NACCOUNTGROUPID in ("+ SETTConstant.AccountGroupType.FIXED +") and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+") \n");
		m_sbFrom.append("           and da.dtDate between to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') and to_date('" + DataFormat.getDateString(qawi.getEndQueryDate()) + "','yyyy-mm-dd') \n");
		m_sbFrom.append("     union all \n");
		m_sbFrom.append("     select c.scode ClientCode, c.sname ClientName,a.nofficeid OfficeID,a.nCurrencyID CurrencyID,  a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
		m_sbFrom.append("                c.nClienttypeID1 nClienttypeID1,c.nClienttypeID2 nClienttypeID2, c.nClienttypeID3 nClienttypeID3, c.nClienttypeID4 nClienttypeID4, c.nClienttypeID5 nClienttypeID5, c.nClienttypeID6 nClienttypeID6, \n");
		m_sbFrom.append("            0.00 CurrentBalance,0.00 CurrentInterestBalance,0.00 CurrentNegotiateBalance,0.00 FixBalance,round(nvl(da.mBalance,0),2) NoticeBalance \n");
		m_sbFrom.append("     from client c,sett_account a, sett_dailyAccountBalance da \n");
		m_sbFrom.append("     where c.id=a.nclientid and a.id=da.naccountid \n");
		//m_sbFrom.append("           and a.naccounttypeid in(3,15) \n");
		m_sbFrom.append("           and a.naccounttypeid in(select id from sett_accounttype where NACCOUNTGROUPID in ("+ SETTConstant.AccountGroupType.NOTIFY +") and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+") \n");
		m_sbFrom.append("           and da.dtDate between to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') and to_date('" + DataFormat.getDateString(qawi.getEndQueryDate()) + "','yyyy-mm-dd') \n");
		if (isToday == true)
		{
			m_sbFrom.append("     -- 当前余额 \n");
			m_sbFrom.append("     union all \n");
			m_sbFrom.append("     select c.scode ClientCode, c.sname ClientName, a.nofficeid OfficeID,a.nCurrencyID CurrencyID, a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
			m_sbFrom.append("                c.nClienttypeID1 nClienttypeID1,c.nClienttypeID2 nClienttypeID2, c.nClienttypeID3 nClienttypeID3, c.nClienttypeID4 nClienttypeID4, c.nClienttypeID5 nClienttypeID5, c.nClienttypeID6 nClienttypeID6, \n");
			m_sbFrom.append("            round(nvl(sa.mBalance,0),2) CurrentBalance,round(nvl(sa.mbalance,0),2)-round(decode(sign(nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0)),-1,0,decode(sa.ac_nIsNegotiate,1,floor((nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0))/decode(sa.ac_mNegotiateUnit,0,1,sa.ac_mNegotiateUnit))*sa.ac_mNegotiateUnit,0)),2) CurrentInterestBalance,round(decode(sign(nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0)),-1,0,decode(sa.ac_nIsNegotiate,1,floor((nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0))/decode(sa.ac_mNegotiateUnit,0,1,sa.ac_mNegotiateUnit))*sa.ac_mNegotiateUnit,0)),2) CurrentNegotiateBalance,0.00 FixBalance,0.00 NoticeBalance \n");
			m_sbFrom.append("     from client c,sett_account a, sett_SubAccount sa \n");
			m_sbFrom.append("     where c.id=a.nclientid  and a.id=sa.naccountid \n");
			//Forest注释，2004-04-05，因为活期存款只包括活期存款账户（01类型的）
			//m_sbFrom.append("            and a.naccounttypeid in(" + getCurrentAccountType(qawi.getCurrencyID()) + ") \n");
			m_sbFrom.append("           and a.naccounttypeid in(select id from sett_accounttype where NACCOUNTGROUPID in ("+ SETTConstant.AccountGroupType.CURRENT +") and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+") \n");
			//Modify by xfma(马现福) 2008/07/08
			//账户信息查询 存款合计 不统计委托存款账户余额(国电专用)
			m_sbFrom.append("           and a.nAccounttypeid !=nvl((select id from sett_accounttype where nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT +" and nstatusId=1 and officeId="+ qawi.getOfficeID() +" and currencyId="+ qawi.getCurrencyID() +" and saccounttypecode = '"+Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+"' ),-999) \n");
			m_sbFrom.append("     union all \n");
			m_sbFrom.append("     select c.scode ClientCode, c.sname ClientName, a.nofficeid OfficeID,a.nCurrencyID CurrencyID, a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
			m_sbFrom.append("                c.nClienttypeID1 nClienttypeID1,c.nClienttypeID2 nClienttypeID2, c.nClienttypeID3 nClienttypeID3, c.nClienttypeID4 nClienttypeID4, c.nClienttypeID5 nClienttypeID5, c.nClienttypeID6 nClienttypeID6, \n");
			m_sbFrom.append("            0.00 CurrentBalance,0.00 CurrentInterestBalance,0.00 CurrentNegotiateBalance,round(nvl(sa.mBalance,0),2) FixBalance,0.00 NoticeBalance \n");
			m_sbFrom.append("     from client c,sett_account a, sett_SubAccount sa \n");
			m_sbFrom.append("     where c.id=a.nclientid and a.id=sa.naccountid \n");
			//m_sbFrom.append("           and a.naccounttypeid in(2,14) \n");
			m_sbFrom.append("           and a.naccounttypeid in(select id from sett_accounttype where NACCOUNTGROUPID in ("+ SETTConstant.AccountGroupType.FIXED +") and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+") \n");
			m_sbFrom.append("     union all \n");
			m_sbFrom.append("     select c.scode ClientCode, c.sname ClientName,a.nofficeid OfficeID,a.nCurrencyID CurrencyID,  a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
			m_sbFrom.append("                c.nClienttypeID1 nClienttypeID1,c.nClienttypeID2 nClienttypeID2, c.nClienttypeID3 nClienttypeID3, c.nClienttypeID4 nClienttypeID4, c.nClienttypeID5 nClienttypeID5, c.nClienttypeID6 nClienttypeID6, \n");
			m_sbFrom.append("            0.00 CurrentBalance,0.00 CurrentInterestBalance,0.00 CurrentNegotiateBalance,0.00 FixBalance,round(nvl(sa.mBalance,0),2) NoticeBalance \n");
			m_sbFrom.append("     from client c,sett_account a, sett_SubAccount sa \n");
			m_sbFrom.append("     where c.id=a.nclientid  and a.id=sa.naccountid \n");
			//m_sbFrom.append("           and a.naccounttypeid in(3,15) \n");
			m_sbFrom.append("           and a.naccounttypeid in(select id from sett_accounttype where NACCOUNTGROUPID in ("+ SETTConstant.AccountGroupType.NOTIFY +") and nstatusId=1 and officeId="+qawi.getOfficeID()+" and currencyId="+qawi.getCurrencyID()+") \n");
		}
		m_sbFrom.append("     ) \n");
		m_sbFrom.append("     where OfficeID=" + qawi.getOfficeID() + " and CurrencyID=" + qawi.getCurrencyID() + " \n");
		if (qawi.getEnterpriseTypeID1() > 0)
			m_sbFrom.append("    and   nClienttypeID1 = " + qawi.getEnterpriseTypeID1());
		if (qawi.getEnterpriseTypeID2() > 0)
			m_sbFrom.append("    and   nClienttypeID2 = " + qawi.getEnterpriseTypeID2());
		if (qawi.getEnterpriseTypeID3() > 0)
			m_sbFrom.append("    and   nClienttypeID3 = " + qawi.getEnterpriseTypeID3());
		if (qawi.getEnterpriseTypeID4() > 0)
			m_sbFrom.append("    and   nClienttypeID4 = " + qawi.getEnterpriseTypeID4());
		if (qawi.getEnterpriseTypeID5() > 0)
			m_sbFrom.append("    and   nClienttypeID5 = " + qawi.getEnterpriseTypeID5());
		if (qawi.getEnterpriseTypeID6() > 0)
			m_sbFrom.append("    and   nClienttypeID6 = " + qawi.getEnterpriseTypeID6());
		
		//add by fxzhang 2012-6-8 start 存款积数查询，增加了查询条件
		//客户编号“从”
		if(qawi.getStartClientCode()!=null && !qawi.getStartClientCode().equals("")){
			m_sbFrom.append(" and ClientCode >= '" +qawi.getStartClientCode() + "' ");
		}
		//客户编号“到”
		if(qawi.getEndClientCode()!=null && !qawi.getEndClientCode().equals("")){
			m_sbFrom.append(" and ClientCode <= '" +qawi.getEndClientCode() + "' ");
			
		}
		//账户号“从”
		if(qawi.getStartAccountNo()!=null && !qawi.getStartAccountNo().equals("")){
			m_sbFrom.append(" and AccountNo >= '" +qawi.getStartAccountNo() + "' ");
		}
		//账户号“到”
		if(qawi.getEndAccountNo()!=null && !qawi.getEndAccountNo().equals("")){
			m_sbFrom.append(" and AccountNo <= '" +qawi.getEndAccountNo() + "' ");
			
		}
		//指定账户================

		if(qawi.getAppointAccountNo()!=null && !qawi.getAppointAccountNo().equals("")){
			String[] account=qawi.getAppointAccountNo().split(",");
			String inarea="(";
			for(int i=0;i<account.length-1;i++){
					 inarea+=account[i]+",";
			}
			inarea+=account[account.length-1]+")";
			m_sbFrom.append(" and AccountID in " +inarea);
		}
		
		//add by fxzhang 2012-6-8 end
		m_sbFrom.append(" ) \n");
		m_sbFrom.append(" group by ClientCode, ClientName \n");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("     having Sum(CurrentBalance)+Sum(FixBalance)+Sum(NoticeBalance)>0 ");
		m_sbFrom.append(" ) order by ClientCode \n");

		//
		m_sbWhere = new StringBuffer();
		//
		m_sbOrderBy = new StringBuffer();
	}
	/**
	 * 查询-平均余额分析::按账户汇总余额
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAverageAccountBalanceBySubAccountID(QueryAccountWhereInfo qawi) throws Exception
	{

		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			qawi.setStartClientCode(null);
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			qawi.setEndClientCode(null);
		QueryAccountWhereInfo qc = new QueryAccountWhereInfo();
		// 判断查询的结束日期是否是今天
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate()))
		{
			isToday = true;
			qc.copy(qawi);
			qc.setEndQueryDate(DataFormat.getPreviousDate(qawi.getEndQueryDate()));
		}
		else
		{
			qc.copy(qawi);
			isToday = false;
		}
		qc.setIntervalDays(queryIntervalDays(qawi.getStartQueryDate(), qawi.getEndQueryDate()));
		getAverageAccountBalanceBySubAccountID(qc);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAverageAccountResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;

	}
	public QueryAccountSumInfo queryAverageAccountBalance(QueryAccountWhereInfo qawi) throws Exception
	{

		QueryAccountSumInfo sumObj = new QueryAccountSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strDepositWhere = "";
		String strLoanWhere = "";
		String sql = "";
		//

		try
		{
			if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
				qawi.setStartClientCode(null);
			if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
				qawi.setEndClientCode(null);
			QueryAccountWhereInfo qc = new QueryAccountWhereInfo();
			// 判断查询的结束日期是否是今天
			if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate()))
			{
				isToday = true;
				qc.copy(qawi);
				qc.setEndQueryDate(DataFormat.getPreviousDate(qawi.getEndQueryDate()));
			}
			else
			{
				qc.copy(qawi);
				isToday = false;
			}
			// 天数
			qc.setIntervalDays(queryIntervalDays(qawi.getStartQueryDate(), qawi.getEndQueryDate()));
			getAverageAccountBalanceByAccountID(qc);
			// select 
			//strSelect = " select sum(Balance) BalanceSum, sum(NegotiateBalance) NegotiateBalanceSum \n";
			strSelect = " select sum(round(Balance/IntervalDays,2)) BalanceSum, sum(round(NegotiateBalance/IntervalDays,2)) NegotiateBalanceSum \n";
			sql = strSelect + " from " + m_sbFrom.toString();
			
			//Modify by leiyang 2008/05/01
			//平均余额分析 存款合计 不统计委托存款账户余额(国电专用)
			/*sql += " where rs.nAccountTypeID != (select id from sett_accounttype where nAccountGroupID = "
				+ SETTConstant.AccountGroupType.CURRENT +" and nstatusId=1 and officeId="+ qawi.getOfficeID() 
				+" and currencyId="+ qawi.getCurrencyID() 
				+" and saccounttypecode = "+Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+")";*/
						
			
			con = this.getConnection();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 存款合计：活期+定期
				sumObj.setBalanceSum(rs.getDouble("BalanceSum"));
				sumObj.setNegotiateBalanceSum((rs.getDouble("NegotiateBalanceSum")));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumObj;

	}
	/**
	 * 查询-平均余额分析::按活期、定期、通知汇总余额
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAverageAccountBalanceByAccountType(QueryAccountWhereInfo qawi) throws Exception
	{

		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			qawi.setStartClientCode(null);
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			qawi.setEndClientCode(null);
		QueryAccountWhereInfo qc = new QueryAccountWhereInfo();
		// 判断查询的结束日期是否是今天
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate()))
		{
			isToday = true;
			qc.copy(qawi);
			qc.setEndQueryDate(DataFormat.getPreviousDate(qawi.getEndQueryDate()));
		}
		else
		{
			qc.copy(qawi);
			isToday = false;
		}
		qc.setIntervalDays(queryIntervalDays(qawi.getStartQueryDate(), qawi.getEndQueryDate()));
		getAverageAccountBalanceByAccountType(qc);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAverageAccountResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	public QueryAccountSumInfo queryAverageAccountBalanceSumByAccountID(QueryAccountWhereInfo qawi) throws Exception
	{
		QueryAccountSumInfo sumObj = new QueryAccountSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strDepositWhere = "";
		String strLoanWhere = "";
		String sql = "";
		//

		try
		{
			if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
				qawi.setStartClientCode(null);
			if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
				qawi.setEndClientCode(null);
			QueryAccountWhereInfo qc = new QueryAccountWhereInfo();
			// 判断查询的结束日期是否是今天
			Log.print("EndQueryDate : " + qawi.getEndQueryDate());
			if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate()))
			{
				isToday = true;
				qc.copy(qawi);
				qc.setEndQueryDate(DataFormat.getPreviousDate(qawi.getEndQueryDate()));
			}
			else
			{
				qc.copy(qawi);
				isToday = false;
			}
			// modifed by fxzhang 2012-6-8 start
//			qc.setIntervalDays(queryIntervalDays(qawi.getStartQueryDate(), qawi.getEndQueryDate()));
			//如果是存款积数查询，查的是余额之和，而不是平均余额，所以不需要除以天数
			if(qawi.isSearchSum())
			{
				qc.setIntervalDays(1);
				qc.setStartClientCode(qawi.getStartClientCode());
				qc.setEndClientCode(qawi.getEndClientCode());
				qc.setStartAccountNo(qawi.getStartAccountNo());
				qc.setEndAccountNo(qawi.getEndAccountNo());
				qc.setAppointAccountNo(qawi.getAppointAccountNo());	
			}
			else
			{
				qc.setIntervalDays(queryIntervalDays(qawi.getStartQueryDate(), qawi.getEndQueryDate()));
			}
			// modified by fxzhang 2012-6-8 end
			Log.print("EndQueryDate : " + qc.getEndQueryDate());
			getAverageAccountBalanceByAccountType(qc);
			m_sbSelect.setLength(0);
			//加上协定存款
//			m_sbSelect.append(" SELECT  sum(CurrentBalance) CurrentBalanceSum,sum(FixBalance) FixBalanceSum,sum(NoticeBalance) NoticeBalanceSum \n");
			m_sbSelect.append(" SELECT  sum(CurrentBalance) CurrentBalanceSum,sum(CurrentNegotiateBalance) CurrentNegotiateBalanceSum,sum(FixBalance) FixBalanceSum,sum(NoticeBalance) NoticeBalanceSum \n");

			con = this.getConnection();
			sql = m_sbSelect.toString() + " from " + m_sbFrom.toString();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumObj.setCurrentBalanceSum(rs.getDouble("CurrentBalanceSum"));  //包含活期和协定
				sumObj.setNegotiateBalanceSum(rs.getDouble("CurrentNegotiateBalanceSum"));  //协定
				sumObj.setFixBalanceSum(rs.getDouble("FixBalanceSum"));
				sumObj.setNoticeBalanceSum(rs.getDouble("NoticeBalanceSum"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumObj;

	}
	/**
	 * @return
	 */
	public StringBuffer getOrderBy()
	{
		return m_sbOrderBy;
	}

	/**
	 * @param orderBy
	 */
	public void setOrderBy(StringBuffer orderBy)
	{
		m_sbOrderBy = orderBy;
	}
	public void setOrderBy(QueryAccountWhereInfo qawi) {
        // create orderby
        m_sbOrderBy = new StringBuffer();
        String strDesc = qawi.getDesc() == 1 ? " desc " : "";
        switch ((int) qawi.getOrderField()) {
        	case OrderBy_AccountNo :
				m_sbOrderBy.append(" \n order by AccountNo" + strDesc);
				break;
			case OrderBy_ClientCode :
				m_sbOrderBy.append(" \n order by ClientCode" + strDesc);
				break;
			case OrderBy_ClientName :
				m_sbOrderBy.append(" \n order by AccountName" + strDesc);
				break;
			case OrderBy_AccountTypeID :
				m_sbOrderBy.append(" \n order by AccountTypeID" + strDesc);
				break;
			case OrderBy_AccountStatusID :
				m_sbOrderBy.append(" \n order by MainAccountStatusID" + strDesc);
				break;
			case OrderBy_OpenDate :
				m_sbOrderBy.append(" \n order by OpenDate" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by AccountNo" + strDesc);
				break;
        }
    }
}
