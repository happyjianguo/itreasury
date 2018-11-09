package com.iss.itreasury.settlement.query.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

public class QAverageAccountBalanceDao extends BaseQueryObject{
	
	StringBuffer strSQL = null;
	boolean isToday = false;

	public String queryAccountSQL(QueryAccountWhereInfo qawi) throws Exception {
		// TODO Auto-generated method stub
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
		qc.set_AccountTypeIDArray(qawi.get_AccountTypeIDArray());
		qc.setIntervalDays(queryIntervalDays(qawi.getStartQueryDate(), qawi.getEndQueryDate()));
		getAverageAccountBalanceByAccountID(qc);
		return strSQL.toString();
	}
	
	public void getAverageAccountBalanceByAccountID(QueryAccountWhereInfo qawi)
	{
		strSQL = new StringBuffer();
		//
		strSQL = new StringBuffer();
		strSQL.append(" ( \n");
		strSQL.append("     select AccountID,AccountNo,AccountName,ClientCode,nAccountTypeid,sum(Balance)+sum(NegotiateBalance) Balance,sum(NegotiateBalance)  NegotiateBalance, \n");
		//按查询区间和开户日期综合查询天数
		if(Config.getBoolean(ConfigConstant.AVERAGE_DATE_TYPE,true)){
		strSQL.append("    (select (select case when seacct.dtfinish <= to_date('" + DataFormat.getDateString(isToday?DataFormat.getNextDate(qawi.getEndQueryDate()):qawi.getEndQueryDate()) + "', 'yyyy-mm-dd') then seacct.dtfinish else  to_date('" + DataFormat.getDateString(isToday?DataFormat.getNextDate(qawi.getEndQueryDate()):qawi.getEndQueryDate()) + " ', 'yyyy-mm-dd') end endDate from sett_account seacct where seacct.id = AccountID) - (select case when seacct.dtopen >= to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "', 'yyyy-mm-dd') then seacct.dtopen  else to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "', 'yyyy-mm-dd') end startDate from sett_account seacct where seacct.id = AccountID) + 1 from dual) IntervalDays \n");
		}else {//按查询区间查询天数
		strSQL.append(" " + qawi.getIntervalDays() + " IntervalDays \n");	
		}
		//
		strSQL.append("     from \n");
		strSQL.append("     ( \n");
		strSQL.append("         select a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
		strSQL.append("                c.nClienttypeID1,c.nClienttypeID2, c.nClienttypeID3, c.nClienttypeID4, c.nClienttypeID5, c.nClienttypeID6,c.nCustomerManagerUserId, \n");
		strSQL.append("                 a.nAccountTypeid,sa.ac_nIsNegotiate IsNegotiate,c.SCODE ClientCode, \n");
		strSQL.append("                round(nvl(da.minterestbalance,0),2) Balance, \n");
//		strSQL.append("                round(nvl(da.mbalance,0),2) Balance, \n");
		strSQL.append("                round(nvl(da.AC_mNegotiateBalance,0),2) NegotiateBalance \n");
		strSQL.append("                ,round(nvl(sa.AC_mNegotiateAmount,0),2) NegotiateAmount,round(nvl(sa.AC_mNegotiateUnit,0),2) NegotiateUnit \n");
		strSQL.append("         from client c,sett_account a, sett_subAccount sa,sett_dailyaccountbalance da \n");
		strSQL.append("         where a.nofficeid=" + qawi.getOfficeID() + " and a.ncurrencyid=" + qawi.getCurrencyID() + " and c.id=a.nclientid and a.id=da.naccountid \n");
		strSQL.append("               and da.nSubAccountID=sa.ID  and a.naccounttypeid not in (select ty.id from sett_accounttype ty where ty.naccountgroupid=" + SETTConstant.AccountGroupType.DISCOUNT + ") \n");
		strSQL.append("               and da.dtDate between to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd')");
		strSQL.append(" and to_date('" + DataFormat.getDateString(qawi.getEndQueryDate()) + "','yyyy-mm-dd') \n");
		//查询有效的账户信息
		if(Config.getBoolean(ConfigConstant.AVERAGE_DATE_TYPE,true)){
		strSQL.append(" and (a.dtfinish>to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') or a.dtfinish is null ) \n");
		}
		//jiangqi 2011-02-25 
		//BUG #3938::贴现账户平均余额分析数据不正确
		//贴现贷款账户，显示查询取值的是每日余额表sett_dailyaccountbalance的mbalance字段(原来不区分时去的minterestbalance字段)
		strSQL.append("  union  all  \n");
		strSQL.append("         select a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
		strSQL.append("                c.nClienttypeID1,c.nClienttypeID2, c.nClienttypeID3, c.nClienttypeID4, c.nClienttypeID5, c.nClienttypeID6,c.nCustomerManagerUserId, \n");
		strSQL.append("                 a.nAccountTypeid,sa.ac_nIsNegotiate IsNegotiate,c.SCODE ClientCode, \n");
		strSQL.append("                round(nvl(da.mbalance,0),2) Balance, \n");
//		strSQL.append("                round(nvl(da.mbalance,0),2) Balance, \n");
		strSQL.append("                round(nvl(da.AC_mNegotiateBalance,0),2) NegotiateBalance \n");
		strSQL.append("                ,round(nvl(sa.AC_mNegotiateAmount,0),2) NegotiateAmount,round(nvl(sa.AC_mNegotiateUnit,0),2) NegotiateUnit \n");
		strSQL.append("         from client c,sett_account a, sett_subAccount sa,sett_dailyaccountbalance da \n");
		strSQL.append("         where a.nofficeid=" + qawi.getOfficeID() + " and a.ncurrencyid=" + qawi.getCurrencyID() + " and c.id=a.nclientid and a.id=da.naccountid \n");
		strSQL.append("               and da.nSubAccountID=sa.ID  and a.naccounttypeid in (select ty.id from sett_accounttype ty where ty.naccountgroupid=" + SETTConstant.AccountGroupType.DISCOUNT + ") \n");
		strSQL.append("               and da.dtDate between to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd')");
		strSQL.append(" and to_date('" + DataFormat.getDateString(qawi.getEndQueryDate()) + "','yyyy-mm-dd') \n");
		//查询有效的账户信息
		if(Config.getBoolean(ConfigConstant.AVERAGE_DATE_TYPE,true)){
		strSQL.append(" and (a.dtfinish>to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') or a.dtfinish is null ) \n");
		}

		if (isToday == true)
		{
			strSQL.append("         union all \n");
			strSQL.append("         select a.id AccountID,a.saccountno AccountNo, a.sname AccountName, \n");
			strSQL.append("                c.nClienttypeID1,c.nClienttypeID2, c.nClienttypeID3, c.nClienttypeID4, c.nClienttypeID5, c.nClienttypeID6,c.nCustomerManagerUserId, \n");
			strSQL.append("                a.nAccountTypeid,sa.ac_nIsNegotiate IsNegotiate,c.SCODE ClientCode, \n");
			strSQL.append(" round((nvl(sa.mbalance,0) -round(decode(sign(nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0)),-1,0,  \n");                      
			strSQL.append(" decode(sa.ac_nIsNegotiate,1,floor((nvl(sa.mBalance,0)\n");
			strSQL.append(" -nvl(sa.AC_mNegotiateAmount,0))/sa.ac_mNegotiateUnit)*sa.ac_mNegotiateUnit,0)),2)),2) Balance,\n");
			strSQL.append("                round(decode(sign(nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0)),-1,0, \n");
			strSQL.append("                       decode(sa.ac_nIsNegotiate,1,floor((nvl(sa.mBalance,0)-nvl(sa.AC_mNegotiateAmount,0))/sa.ac_mNegotiateUnit)*sa.ac_mNegotiateUnit,0)),2) NegotiateBalance \n");
			strSQL.append("                ,round(nvl(sa.AC_mNegotiateAmount,0),2) NegotiateAmount,round(nvl(sa.AC_mNegotiateUnit,0),2) NegotiateUnit \n");
			strSQL.append("         from   client c,sett_account a, sett_subaccount sa \n");
			strSQL.append("         where  a.nofficeid=" + qawi.getOfficeID() + " and a.ncurrencyid=" + qawi.getCurrencyID() + " and c.id=a.nclientid and a.id=sa.naccountid \n");
			//查询有效的账户信息
			if(Config.getBoolean(ConfigConstant.AVERAGE_DATE_TYPE,true)){
			strSQL.append(" and (a.dtfinish>=to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') or a.dtfinish is null )\n");
			}
		}
		strSQL.append("     ) \n");
		strSQL.append("     where 1=1 \n");
		System.out.println("zhaokehuid----::"+qawi.getClientManager());
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			strSQL.append(" and AccountNo>='" + qawi.getStartAccountNo() + "' ");
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			strSQL.append(" and AccountNo<='" + qawi.getEndAccountNo() + "' ");
		if (qawi.getStartClientCode() != null && qawi.getStartClientCode().length() > 0)
			strSQL.append(" and ClientCode>='" + qawi.getStartClientCode() + "' ");
		if (qawi.getEndClientCode() != null && qawi.getEndClientCode().length() > 0)
			strSQL.append(" and ClientCode<='" + qawi.getEndClientCode() + "' ");
		if (qawi.getClientManager() > 0)
			strSQL.append(" and nCustomerManagerUserId=" + qawi.getClientManager());
		//if (qawi.getAccountTypeID() > 0)
//		if(qawi.getAccountTypeIDArray()!= null && qawi.getAccountTypeIDArray().length>0)
//			{
//				String[] strID = qawi.getAccountTypeIDArray();
//				StringBuffer sbId = new StringBuffer();
//				for(int m = 0;m<strID.length;m++)
//				{
//					sbId.append(strID[m]).append(",");
//				}
//				strSQL.append(" and nAccountTypeid in ( " + sbId.substring(0, sbId.length()-1) +" ) ");
//			}
		if(qawi.get_AccountTypeIDArray()!= null && qawi.get_AccountTypeIDArray().length()>0){
			strSQL.append(" and nAccountTypeid in ( " + qawi.get_AccountTypeIDArray() +" ) ");
		}
		if (qawi.getEnterpriseTypeID1() > 0)
			strSQL.append("    and   nClienttypeID1 = " + qawi.getEnterpriseTypeID1());
		if (qawi.getEnterpriseTypeID2() > 0)
			strSQL.append("    and   nClienttypeID2 = " + qawi.getEnterpriseTypeID2());
		if (qawi.getEnterpriseTypeID3() > 0)
			strSQL.append("    and   nClienttypeID3 = " + qawi.getEnterpriseTypeID3());
		if (qawi.getEnterpriseTypeID4() > 0)
			strSQL.append("    and   nClienttypeID4 = " + qawi.getEnterpriseTypeID4());
		if (qawi.getEnterpriseTypeID5() > 0)
			strSQL.append("    and   nClienttypeID5 = " + qawi.getEnterpriseTypeID5());
		if (qawi.getEnterpriseTypeID6() > 0)
			strSQL.append("    and   nClienttypeID6 = " + qawi.getEnterpriseTypeID6());
		if (qawi.getIsNegotiate() > 0)
			strSQL.append(" and IsNegotiate=1");
		if (qawi.getIsFilterNull() > 0)
			strSQL.append(" and Balance>0");
		strSQL.append("     group by AccountID,AccountNo,ClientCode,AccountName,nAccountTypeid \n");
		if (qawi.getIsFilterNull() > 0)
			strSQL.append("     having sum(Balance)>0 ");
		strSQL.append(" )       \n");

	}

	
	public boolean isToday(long lOfficeID, long lCurrencyID, Timestamp tsQueryDate)
	{
		boolean b = true;
		Timestamp tsOpenDate = Env.getSystemDate(lOfficeID, lCurrencyID);
		if (tsOpenDate != null && tsQueryDate != null)
		{
			if (tsOpenDate.toString().substring(0, 10).equals(tsQueryDate.toString().substring(0, 10)))
				b = true;
			else
				b = false;
		}
		return b;
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
}
