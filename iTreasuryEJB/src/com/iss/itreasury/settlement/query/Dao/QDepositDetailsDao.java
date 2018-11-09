package com.iss.itreasury.settlement.query.Dao;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * 账户查询数据层
 * 
 * @author xiang
 * 
 */
public class QDepositDetailsDao {

	public String getDepositLoanSQL(QueryAccountWhereInfo info)
			throws Exception {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append(" \n * \n");

		StringBuffer m_sbFrom = new StringBuffer();

		m_sbFrom.append(" ( SELECT c.id ClientID, c.scode ClientCode, c.sname ClientName,sum(aa.balance) Balance  \n");
		m_sbFrom.append("  FROM  client c, \n");
		if (isToday(info.getOfficeID(), info.getCurrencyID(), info.getQueryDate()))
		{
			m_sbFrom.append("   (select a.nclientid , a.nofficeid , a.ncurrencyid , sa.mbalance Balance ,sa.naccountid accountid, a.saccountno accountNo\n");
			m_sbFrom.append("    from sett_account a, sett_subAccount sa \n");
			m_sbFrom.append("    WHERE  a.id=sa.naccountid and a.naccounttypeid in (" + getDepositAccountTypeWithoutWTDeposit(info.getCurrencyID(),info.getOfficeID()) + ") \n");
			m_sbFrom.append(" and a.ncheckstatusid=4 \n");
			m_sbFrom.append("   ) aa \n");
		}
		else
		{
			m_sbFrom.append("   (select a.nclientid , a.nofficeid , a.ncurrencyid , da.mbalance Balance,da.naccountid accountid, a.saccountno accountNo \n");
			m_sbFrom.append("    from sett_account a, sett_dailyaccountbalance da \n");
			m_sbFrom.append("    WHERE  a.id=da.naccountid and a.ncheckstatusid=4 and a.naccounttypeid in (" + getDepositAccountTypeWithoutWTDeposit(info.getCurrencyID(),info.getOfficeID()) + ") \n");
			m_sbFrom.append("           and da.dtdate=to_date('" + DataFormat.getDateString(info.getQueryDate()) + "','yyyy-mm-dd') \n");
			m_sbFrom.append("   ) aa \n");
		}
		m_sbFrom.append("  where c.id=aa.nclientid and aa.nofficeid=" + info.getOfficeID() + " and aa.ncurrencyid=" + info.getCurrencyID() + " \n");
	
		m_sbFrom.append("  group by c.id , c.scode, c.sname \n");
		if (info.getIsFilterNull() > 0)
			m_sbFrom.append("  having sum(aa.balance)>0 \n");
		m_sbFrom.append(" ) rr \n");
		return "SELECT "+ m_sbSelect.toString() +" FROM "+ m_sbFrom.toString();
	}

	public String getAccountBalanceSQL(QueryAccountWhereInfo info ){
		if (info.getStartAccountNo() != null && info.getStartAccountNo().length() > 0)
			info.setStartClientCode(null);
		if (info.getEndAccountNo() != null && info.getEndAccountNo().length() > 0)
			info.setEndClientCode(null);

		if (isToday(info.getOfficeID(), info.getCurrencyID(), info.getQueryDate()))
			return	getAccountInfoSQL(info);
		else
			return getHistoryAccountBalance(info);
	}
	
	private boolean isToday(long lOfficeID, long lCurrencyID, Timestamp tsQueryDate)
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
	
	private String getDepositAccountTypeWithoutWTDeposit(long lCurrencyID,long lOfficeID)
	{
		System.out.println("进入 getDepositAccountTypeWithoutWTDeposit");
		
		String str = "";
		long[] array = SETTConstant.AccountType.getDepositAccountTypeCodeForAudit(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		
		System.out.println("出去 getDepositAccountTypeWithoutWTDeposit:"+str);
		return (str.length()!=0?str.substring(0, str.length() - 1):str);
		
	}
	
	private String getAccountInfoSQL(QueryAccountWhereInfo qaci)
	{
		StringBuffer m_sbSelect = new StringBuffer();
		// select 
		// modify by leiyang 修改账户信息查询存款合计的查询
		m_sbSelect.append("   \n    distinct acct.nofficeID as OfficeID, acct.nCurrencyID CurrencyID,acct.ID AccountID, \n");
		m_sbSelect.append("        acct.sAccountNo as AccountNo, acct.sName as AccountName, acct.nClientID as ClientID, \n");
		m_sbSelect.append("        Client.SCode as ClientCode,Client.SQUERYPASSWORD as QueryPassWord, Client.sname ClientName,acct.nAccountTypeID as AccountTypeID, \n");
		m_sbSelect.append("        acct.dtOpen as OpenDate,acct.dtFinish  as ClearDate,acct.nstatusid as MainAccountStatusID, acct.sabstract, \n"); 		
		m_sbSelect.append("        b.balance,b.interest,b.availableBalance,b.IsNegotiate,b.limitamount,aa.ac_nInterestRatePlanID interestPlanID,1 isToday \n");
		
       //added by mzh_fu 2008/05/08 解决账户余额查询不统一问题,增加下面7行
		m_sbSelect.append("        ,ss.AC_NFIRSTLIMITTYPEID  firstLimitTypeId,");
		//m_sbSelect.append("        ss.AC_MFIRSTLIMITAMOUNT   firstLimitAmount,");
		m_sbSelect.append("        decode(ss.AC_NFIRSTLIMITTYPEID,-1,0.00,ss.AC_MFIRSTLIMITAMOUNT) firstLimitAmount,");
		m_sbSelect.append("        ss.AC_NSECONDLIMITTYPEID  secondLimitTypeId,");
		//m_sbSelect.append("        ss.AC_MSECONDLIMITAMOUNT  secondLimitAmount,");
		m_sbSelect.append("        decode(ss.AC_NSECONDLIMITTYPEID,-1,0.00,ss.AC_MSECONDLIMITAMOUNT) secondLimitAmount,");
		m_sbSelect.append("        ss.AC_NTHIRDLIMITTYPEID   thirdLimitTypeId,");
		//m_sbSelect.append("        ss.AC_MTHIRDLIMITAMOUNT   thirdLimitAmount,");
		m_sbSelect.append("        decode(ss.AC_NTHIRDLIMITTYPEID,-1,0.00,ss.AC_MTHIRDLIMITAMOUNT) thirdLimitAmount,");
		m_sbSelect.append("        ss.AC_MCAPITALLIMITAMOUNT capitalLimitAmount");
		
		// from 
		StringBuffer m_sbFrom = new StringBuffer();

		//modified by mzh_fu 2008/05/08 解决账户余额查询不统一问题,增加：sett_subaccount ss,
		m_sbFrom.append("         sett_account acct, client,sett_subaccount ss, (select DISTINCT a.id,b.ac_nInterestRatePlanID from sett_account a,(select * from sett_subaccount where nstatusid >0) b where a.ID=b.naccountid(+)) aa, \n");
		m_sbFrom.append("              (select distinct a.naccountid,a.balance,a.interest,a.availableBalance,a.limitamount, aa.IsNegotiate \n");
		m_sbFrom.append("               from  (select sum(nvl(ac_nIsNegotiate,0)) IsNegotiate,naccountid from sett_subaccount group by naccountid) aa, \n");
		//修改 by kenny(胡志强)(2007-03-31)处理查询定期、保证金计提利息的问题
		m_sbFrom.append("                    (select acc.id　naccountid, sum(round(nvl(subAcct.mbalance,0),2)) balance,sum(round(nvl(subAcct.ac_mcapitallimitamount,0),2)) limitamount,sum(round(nvl(decode(subAcct.minterest,0,subAcct.af_mpredrawinterest,subAcct.minterest),0),2)+round(nvl(subAcct.ac_mNegotiateInterest,0),2)) Interest, \n");
		
		m_sbFrom.append("                            sum(round(nvl(decode(subAcct.nstatusid,1,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,5,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,4,0,2,0,7,0,8,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,0),0),2)) availableBalance \n");
		m_sbFrom.append("                     from sett_account acc,sett_subaccount subAcct \n");
		m_sbFrom.append("                     where acc.nofficeid=" + qaci.getOfficeID() + " and acc.ncurrencyid=" + qaci.getCurrencyID() + " and acc.id=subAcct.naccountid(+) \n");
		m_sbFrom.append("                    group by acc.id) a \n");
		m_sbFrom.append("               where a.naccountid=aa.naccountid(+) ) b \n");

		// where 
		StringBuffer m_sbWhere = new StringBuffer();
		//
		m_sbWhere.append("        b.nAccountId=aa.id  \n");
		
		//added by mzh_fu 2008/05/08 解决账户余额查询不统一问题,增加下面1行
		m_sbWhere.append("        and ss.naccountid(+) = acct.id  \n");		
		
		m_sbWhere.append("        and acct.nclientid=client.id  \n");
		m_sbWhere.append("        and acct.nofficeid=" + qaci.getOfficeID() + " and acct.nCurrencyID=" + qaci.getCurrencyID() + " \n");
		m_sbWhere.append("        and acct.id=b.naccountid(+) and acct.nCheckStatusID=" + SETTConstant.AccountCheckStatus.CHECK + " \n");
		//m_sbWhere.append("        and acct.id=b.naccountid and acct.nCheckStatusID=" + SETTConstant.AccountCheckStatus.CHECK + " \n");
		if (qaci.getStartClientCode() != null && qaci.getStartClientCode().length() > 0)
			m_sbWhere.append("        and client.scode>='" + qaci.getStartClientCode() + "'");
		if (qaci.getEndClientCode() != null && qaci.getEndClientCode().length() > 0)
			m_sbWhere.append("        and client.scode<='" + qaci.getEndClientCode() + "'");
		if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
			m_sbWhere.append("        and acct.sAccountNo>='" + qaci.getStartAccountNo() + "'");
		if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
			m_sbWhere.append("        and acct.sAccountNo<='" + qaci.getEndAccountNo() + "'");
		if (qaci.getAccountTypeID() > 0)
			m_sbWhere.append("        and acct.naccounttypeid=" + qaci.getAccountTypeID());
		//add by zcwang 2008-2-15
		else if(!"".equals(qaci.getAccountTypeSet()))
		{
			m_sbWhere.append("        and acct.naccounttypeid  IN(" + qaci.getAccountTypeSet()+")");
		}
		//
		if (qaci.getClientManager() > 0)
			m_sbWhere.append("        and client.ncustomermanageruserid=" + qaci.getClientManager());
		if(qaci.getOpenDateFrom()!=null)
		    m_sbWhere.append("		  and nvl(acct.dtOpen,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"+DataFormat.getDateString(qaci.getOpenDateFrom())+"','yyyy-mm-dd')	");
		if(qaci.getOpenDateTo()!=null)
		    m_sbWhere.append("		  and nvl(acct.dtOpen,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"+DataFormat.getDateString(qaci.getOpenDateTo())+"','yyyy-mm-dd')	");
		if(qaci.getFinishDateFrom()!=null)
		{
		    m_sbWhere.append("		  and nvl(acct.dtFinish,to_date('9000-01-01','yyyy-mm-dd')) >= to_date('"+DataFormat.getDateString(qaci.getFinishDateFrom())+"','yyyy-mm-dd')	");
		    m_sbWhere.append("		  and acct.nstatusid="+SETTConstant.AccountStatus.CLOSE+"	");
		}
		if(qaci.getFinishDateTo()!=null)
		{
		    m_sbWhere.append("		  and nvl(acct.dtFinish,to_date('9000-01-01','yyyy-mm-dd')) <= to_date('"+DataFormat.getDateString(qaci.getFinishDateTo())+"','yyyy-mm-dd')	");
		    m_sbWhere.append("		  and acct.nstatusid="+SETTConstant.AccountStatus.CLOSE+"	");
		}
		return " SELECT " + m_sbSelect.toString() + " FROM " +m_sbFrom.toString()  + " WHERE "+m_sbWhere.toString() ;
	}
	
	private String getHistoryAccountBalance(QueryAccountWhereInfo qaci)
	{
		StringBuffer m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append("  \n    distinct acct.nofficeID as OfficeID, acct.nCurrencyID CurrencyID,acct.ID AccountID, \n");
		m_sbSelect.append("        acct.sAccountNo as AccountNo, acct.sName as AccountName, acct.nClientID as ClientID, \n");
		m_sbSelect.append("        Client.SCode as ClientCode, Client.sname ClientName,Client.SQUERYPASSWORD as QueryPassWord,acct.nAccountTypeID as AccountTypeID, \n");
		m_sbSelect.append("        acct.dtOpen as OpenDate,acct.dtFinish  as ClearDate,acct.nstatusid as MainAccountStatusID, \n");
		m_sbSelect.append("        b.balance,b.interest,b.balance availableBalance,b.UncheckPaymentAmount,b.InterestPlanID,b.IsNegotiate,b.limitamount,1 isToday \n");
		// from 
		StringBuffer m_sbFrom = new StringBuffer();
      //modify by  liuchuan 2007-05-17  金额重复
		m_sbFrom.append("         sett_account acct, client, \n");
		m_sbFrom.append("              (select distinct a.naccountid,a.balance,a.interest,a.UncheckPaymentAmount,ac_nInterestRatePlanID InterestPlanID,sett_subaccount.NSTATUSID SubAccountStatus,sett_subaccount.ac_mcapitallimitamount limitamount,ac_nIsNegotiate IsNegotiate \n");
		m_sbFrom.append("               from  sett_subaccount, \n");
		m_sbFrom.append("               (select tt.naccountid,sum(round(nvl(tt.balance,0),2)) balance,sum(round(nvl(decode(tt.Interest,0,tt.af_mpredrawinterest,tt.Interest),0),2)) Interest, 0.0 UncheckPaymentAmount from \n");
     //modify by xwhe 2009-01-08		
		m_sbFrom.append("                    (select distinct daily.nsubaccountid  nsubaccoutnid, daily.naccountid naccountid, daily.mbalance balance,daily.minterest Interest, subAcct.af_mpredrawinterest af_mpredrawinterest \n");		
		m_sbFrom.append("                     from sett_account acct,sett_DailyAccountBalance daily,sett_subaccount subacct \n");
		m_sbFrom.append("                     where acct.nofficeid=" + qaci.getOfficeID() + " and acct.ncurrencyid=" + qaci.getCurrencyID() + " and acct.id=daily.naccountid \n");
		m_sbFrom.append("                       and daily.dtDate=to_date('" + DataFormat.getDateString(qaci.getQueryDate()) + "','yyyy-mm-dd')  \n");
		m_sbFrom.append("                       and acct.id=subacct.naccountid  \n");
		//m_sbFrom.append("                       and subacct.nstatusid(+)="+SETTConstant.SubAccountStatus.NORMAL+" \n");
		m_sbFrom.append("                       and subacct.nstatusid in ("+SETTConstant.SubAccountStatus.NORMAL+","+SETTConstant.SubAccountStatus.FINISH+") \n");
		m_sbFrom.append("                       and daily.nsubaccountid = subacct.id \n");
		m_sbFrom.append("         ) tt  \n");
		m_sbFrom.append("                    group by tt.naccountid) a \n");
		m_sbFrom.append("               where a.naccountid=sett_subaccount.naccountid ) b \n");

		// where 
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append("        acct.nclientid=client.id  \n");
		m_sbWhere.append("        and acct.nofficeid=" + qaci.getOfficeID() + " and acct.nCurrencyID=" + qaci.getCurrencyID() + " \n");
		m_sbWhere.append("        and acct.id=b.naccountid(+) and acct.nCheckStatusID=" + SETTConstant.AccountCheckStatus.CHECK + " \n");

		if (qaci.getStartClientCode() != null && qaci.getStartClientCode().length() > 0)
			m_sbWhere.append("        and client.scode>='" + qaci.getStartClientCode() + "'");
		if (qaci.getEndClientCode() != null && qaci.getEndClientCode().length() > 0)
			m_sbWhere.append("        and client.scode<='" + qaci.getEndClientCode() + "'");
		if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
			m_sbWhere.append("        and acct.sAccountNo>='" + qaci.getStartAccountNo() + "'");
		if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
			m_sbWhere.append("        and acct.sAccountNo<='" + qaci.getEndAccountNo() + "'");
		if (qaci.getAccountTypeID() > 0)
			m_sbWhere.append("        and acct.naccounttypeid=" + qaci.getAccountTypeID());
		
		return " SELECT " +m_sbSelect.toString() + " FROM " + m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}
	
}
