/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
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
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Log;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QAccount extends BaseQueryObject
{

	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_ClientCode = 2;
	public final static int OrderBy_ClientName = 3;
	public final static int OrderBy_AccountTypeID = 4;
	public final static int OrderBy_AccountStatusID = 5;
	public final static int OrderBy_OpenDate = 6;
	//
	public StringBuffer m_sbSelect = null;
	public StringBuffer m_sbFrom = null;
	public StringBuffer m_sbWhere = null;
	public StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QAccount()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}
	/**
	 * 
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public double getTodayAccountBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		double dReturn = 0.0;
		//

		try
		{
			con = this.getConnection();
			StringBuffer sb = new StringBuffer();

			sb.append(" select sum(round(nvl(subacct.mbalance,0),2)) balance \n");
			sb.append(" from client, sett_account acct, sett_subaccount subacct \n");
			sb.append(" where client.id=acct.nclientid and acct.id=subacct.naccountid and acct.ncheckstatusid=4 \n");
			if (qawi.getClientID() > 0)
				sb.append(" and client.id=" + qawi.getClientID() + " \n");
			sb.append("       and acct.ncurrencyid=" + qawi.getCurrencyID());
			if (qawi.getAccountTypeSet() != null && qawi.getAccountTypeSet().length() > 0)
				sb.append("       and acct.naccounttypeid in(" + qawi.getAccountTypeSet() + ") \n");
//			add by fxzhang 2012-6-8 start 存款积数查询，增加了查询条件
			//客户编号“从”
			if(qawi.getStartClientCode()!=null && !qawi.getStartClientCode().equals("")){
				sb.append(" and client.scode >= '" +qawi.getStartClientCode() + "' ");
			}
			//客户编号“到”
			if(qawi.getEndClientCode()!=null && !qawi.getEndClientCode().equals("")){
				sb.append(" and client.scode <= '" +qawi.getEndClientCode() + "' ");
				
			}
			//账户号“从”
			if(qawi.getStartAccountNo()!=null && !qawi.getStartAccountNo().equals("")){
				sb.append(" and acct.saccountno >= '" +qawi.getStartAccountNo() + "' ");  
			}
			//账户号“到”
			if(qawi.getEndAccountNo()!=null && !qawi.getEndAccountNo().equals("")){
				sb.append(" and acct.saccountno <= '" +qawi.getEndAccountNo() + "' ");
				
			}
			//指定账户================

			if(qawi.getAppointAccountNo()!=null && !qawi.getAppointAccountNo().equals("")){
				String[] account=qawi.getAppointAccountNo().split(",");
				String inarea="(";
				for(int i=0;i<account.length-1;i++){
						 inarea+=account[i]+",";
				}
				inarea+=account[account.length-1]+")";
				sb.append(" and acct.id in " +inarea);
			}
	
			//add by fxzhang 2012-6-8 end
			
			sb.append(" group by client.id \n");
			//Log.print(sb.toString());
			// select 
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("balance");
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
		return dReturn;
	}
	/**
	 * ncheckstatusid yhlu
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public double getHistoryAccountBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		double dReturn = 0.0;
		//

		try
		{
			con = this.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sum(round(nvl(daily.mbalance,0),2)) balance \n");
			sb.append(" from client, sett_account acct, sett_dailyaccountbalance daily \n");
			sb.append(" where client.id=acct.nclientid and acct.id=daily.naccountid  and acct.ncheckstatusid=4 \n");
			if (qawi.getClientID() > 0)
				sb.append("   and client.id=" + qawi.getClientID() + " \n");
			sb.append("       and acct.ncurrencyid=" + qawi.getCurrencyID());
			if (qawi.getAccountTypeSet() != null && qawi.getAccountTypeSet().length() > 0)
				sb.append("   and acct.naccounttypeid in(" + qawi.getAccountTypeSet() + ") \n");
			//存款明细查询 && 存款积数查询（开始日期=结束日期）
			//存款明细查询，查时间点（原来是用时间来判断是否为存款积数查询，现在传入了一个参数来标识）
//			if((qawi.getStartQueryDate() != null && qawi.getQueryDate() != null && (qawi.getQueryDate().compareTo(qawi.getStartQueryDate()) == 0))
//					|| (qawi.getStartQueryDate() == null && qawi.getQueryDate() != null))
			if(!qawi.isSearchSum())
			{
				sb.append("       and daily.dtDate=to_date('" + DataFormat.getDateString(qawi.getQueryDate()) + "','yyyy-mm-dd')  \n");
			}
			//存款积数查询，查时间段
			else 
			{
				if(qawi.getStartQueryDate() != null)
				{
					sb.append("       and daily.dtDate>=to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd')  \n");
				}
				if(qawi.getEndQueryDate() != null)
				{
					Timestamp queryEndDate = qawi.getEndQueryDate();
					//如果查询结束日期为开机日，则取查询结束日期的前一天。因为如果是关机状态，那开机日是有每日余额记录的。而开机日当天的余额已经加过了。
					if(isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate()))
					{
						queryEndDate = DataFormat.getNextDate(qawi.getEndQueryDate(), -1);
					}

					sb.append("       and daily.dtDate<=to_date('" + DataFormat.getDateString(queryEndDate) + "','yyyy-mm-dd')  \n");
				}
//				add by fxzhang 2012-6-8 start 存款积数查询，增加了查询条件
				//客户编号“从”
				if(qawi.getStartClientCode()!=null && !qawi.getStartClientCode().equals("")){
					sb.append(" and client.scode >= '" +qawi.getStartClientCode() + "' ");
				}
				//客户编号“到”
				if(qawi.getEndClientCode()!=null && !qawi.getEndClientCode().equals("")){
					sb.append(" and client.scode <= '" +qawi.getEndClientCode() + "' ");
					
				}
				//账户号“从”
				if(qawi.getStartAccountNo()!=null && !qawi.getStartAccountNo().equals("")){
					sb.append(" and acct.saccountno >= '" +qawi.getStartAccountNo() + "' ");  
				}
				//账户号“到”
				if(qawi.getEndAccountNo()!=null && !qawi.getEndAccountNo().equals("")){
					sb.append(" and acct.saccountno <= '" +qawi.getEndAccountNo() + "' ");
					
				}
				//指定账户================

				if(qawi.getAppointAccountNo()!=null && !qawi.getAppointAccountNo().equals("")){
					String[] account=qawi.getAppointAccountNo().split(",");
					String inarea="(";
					for(int i=0;i<account.length-1;i++){
							 inarea+=account[i]+",";
					}
					inarea+=account[account.length-1]+")";
					sb.append(" and acct.id in " +inarea);
				}
		
				//add by fxzhang 2012-6-8 end
			}
			sb.append(" group by client.id \n");
			Log.print(sb.toString());
			System.out.println(sb.toString());
			// select 
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("balance");
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
		return dReturn;
	}
	/**
	 * 
	 * @param lClientID
	 * @param QueryDate
	 * @return
	 */
	public double getCurrentBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		//2004-04-13, Forest注释，原因：存款明细中活期存款仅包括01类型
		//qawi.setAccountTypeSet(getCurrentAccountType(qawi.getCurrencyID()));
		double dReturn = 0.0;
		String accountType = "";
		accountType = getCurrentAccountType(qawi.getCurrencyID(),qawi.getOfficeID(),1);
		if(!("").equals(accountType)&&accountType.length()>0)
		{
			qawi.setAccountTypeSet(accountType);	
		}else{
			return dReturn;
		}
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
			dReturn = getTodayAccountBalanceOfClient(qawi);
		else
			dReturn = getHistoryAccountBalanceOfClient(qawi);

		return dReturn;
	}
	
	
	/**
	 * @return
	 */
	public double getFixBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		double dReturn = 0.0;
		String accountType = "";
		accountType = getFixAccountType(qawi.getCurrencyID(),qawi.getOfficeID());
		if(!("").equals(accountType)&&accountType.length()>0)
		{
			qawi.setAccountTypeSet(accountType);
		}else{
			return dReturn;
		}
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
			dReturn = getTodayAccountBalanceOfClient(qawi);
		else
			dReturn = getHistoryAccountBalanceOfClient(qawi);

		return dReturn;
	}

	/**
	 * @return
	 */
	public double getNoticeBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		double dReturn = 0.0;
		String accountType = "";
		accountType = getNotifyAccountType(qawi.getCurrencyID(),qawi.getOfficeID());
		if(!("").equals(accountType)&&accountType.length()>0)
		{
			qawi.setAccountTypeSet(accountType);
		}else{
			return dReturn;
		}
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
			dReturn = getTodayAccountBalanceOfClient(qawi);
		else
			dReturn = getHistoryAccountBalanceOfClient(qawi);

		return dReturn;
	}

	/**
	 * @return
	 */
	public double getPrevDayDepositAccountBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		QueryAccountWhereInfo tmp = new QueryAccountWhereInfo();
		//
		tmp.setOfficeID(qawi.getOfficeID());
		tmp.setCurrencyID(qawi.getCurrencyID());
		tmp.setClientID(qawi.getClientID());
		tmp.setQueryDate(DataFormat.getPreviousDate(qawi.getQueryDate()));
		//jiangqi 2011-01-28  去掉委托存款的所有存款账户类型ID
		//tmp.setAccountTypeSet(getDepositAccountType(qawi.getCurrencyID(),qawi.getOfficeID()));
		tmp.setAccountTypeSet(getDepositAccountTypeWithoutWTDeposit(qawi.getCurrencyID(),qawi.getOfficeID()));
		return getHistoryAccountBalanceOfClient(tmp);
	}

	/**
	 * @return
	 */
	public double getPrevMonthDepositAccountBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		QueryAccountWhereInfo tmp = new QueryAccountWhereInfo();
		//
		tmp.setOfficeID(qawi.getOfficeID());
		tmp.setCurrencyID(qawi.getCurrencyID());
		tmp.setClientID(qawi.getClientID());
		tmp.setQueryDate(DataFormat.getPreviousMonth(qawi.getQueryDate(), 1));
		//jiangqi 2011-01-28  去掉委托存款的所有存款账户类型ID
		//tmp.setAccountTypeSet(getDepositAccountType(qawi.getCurrencyID(),qawi.getOfficeID()));
		tmp.setAccountTypeSet(getDepositAccountTypeWithoutWTDeposit(qawi.getCurrencyID(),qawi.getOfficeID()));

		return getHistoryAccountBalanceOfClient(tmp);
	}

	/**
	 * @return
	 */
	public double getPrevYearDepositAccountBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		QueryAccountWhereInfo tmp = new QueryAccountWhereInfo();
		//
		tmp.setOfficeID(qawi.getOfficeID());
		tmp.setCurrencyID(qawi.getCurrencyID());
		tmp.setClientID(qawi.getClientID());
		tmp.setQueryDate(DataFormat.getPreviousYear(qawi.getQueryDate(), 1));
		//jiangqi 2011-01-28  去掉委托存款的所有存款账户类型ID
		//tmp.setAccountTypeSet(getDepositAccountType(qawi.getCurrencyID(),qawi.getOfficeID()));
		tmp.setAccountTypeSet(getDepositAccountTypeWithoutWTDeposit(qawi.getCurrencyID(),qawi.getOfficeID()));

		return getHistoryAccountBalanceOfClient(tmp);
	}
	
	//2006-8-7 加入账户复核状态判断yhLu
	public void getClientInfo(QueryAccountWhereInfo qawi)
	{
		Log.print("*****	getClientInfo	****");
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" \n * \n");

		m_sbFrom = new StringBuffer();

		m_sbFrom.append(" ( SELECT c.id ClientID, c.scode ClientCode, c.sname ClientName,sum(aa.balance) Balance \n");
		m_sbFrom.append("  FROM  client c, \n");
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
		{
			m_sbFrom.append("   (select a.nclientid , a.nofficeid , a.ncurrencyid , sa.mbalance Balance \n");
			m_sbFrom.append("    from sett_account a, sett_subAccount sa \n");
			m_sbFrom.append("    WHERE  a.id=sa.naccountid and a.naccounttypeid in (" + getDepositAccountType(qawi.getCurrencyID(),qawi.getOfficeID()) + ") \n");
			m_sbFrom.append(" and a.ncheckstatusid=4 \n");
			m_sbFrom.append("   ) aa \n");
		}
		else
		{
			m_sbFrom.append("   (select a.nclientid , a.nofficeid , a.ncurrencyid , da.mbalance Balance \n");
			m_sbFrom.append("    from sett_account a, sett_dailyaccountbalance da \n");
			m_sbFrom.append("    WHERE  a.id=da.naccountid and a.ncheckstatusid=4 and a.naccounttypeid in (" + getDepositAccountType(qawi.getCurrencyID(),qawi.getOfficeID()) + ") \n");
			m_sbFrom.append("           and da.dtdate=to_date('" + DataFormat.getDateString(qawi.getQueryDate()) + "','yyyy-mm-dd') \n");
			m_sbFrom.append("   ) aa \n");
		}
		m_sbFrom.append("  where c.id=aa.nclientid and c.nofficeid=aa.nofficeid and aa.nofficeid=" + qawi.getOfficeID() + " and aa.ncurrencyid=" + qawi.getCurrencyID() + " \n");
		m_sbFrom.append("  group by c.id , c.scode, c.sname \n");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("  having sum(aa.balance)>0 \n");
		m_sbFrom.append(" ) rr \n");

		// where     
		m_sbWhere = new StringBuffer();

		m_sbOrderBy = new StringBuffer();
		if(qawi.getOrderField() == 2)
		{
			m_sbOrderBy.append(" \n order by balance ");
		}
		else
		{
			m_sbOrderBy.append(" \n order by ClientCode ");
		}
		
		if(qawi.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			m_sbOrderBy.append(" desc ");
		}
	}
	
	/*
	 * 2012-06-08  by fxzhang  
	 * 
	 * 存款积数查询，只查询 账户组类型 为 活期（不包括委托存款），定期，和通知的账户
	 * 与存款明细查询getClientInfoOnlyCDN的区别在于，这个方法支持查一个时间段的，并有更多的查询条件
	 * 
	 * 用来算客户存款的合计（主要是合计排序需要根据的字段）
	 * 
	 * */
	public void getClientInfoOnlyCDN1(QueryAccountWhereInfo qawi)
	{
		Log.print("*****	getClientInfo	****");
		boolean isToday = false;
		Timestamp queryEndDate = qawi.getEndQueryDate();
		//如果查询结束日期为开机日，则取查询结束日期的前一天。因为如果是关机状态，那开机日是有每日余额记录的。而开机日当天的余额已经加过了。
		if(isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate()))
		{
			isToday = true;
			queryEndDate = DataFormat.getNextDate(qawi.getEndQueryDate(), -1);
		}
		
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" \n * \n");

		m_sbFrom = new StringBuffer();

		m_sbFrom.append(" ( SELECT c.id ClientID, c.scode ClientCode, c.sname ClientName,sum(aa.balance) Balance  \n");
		m_sbFrom.append("  FROM  client c, \n");
		
		m_sbFrom.append("   (select a.nclientid , a.nofficeid , a.ncurrencyid , da.mbalance Balance,da.naccountid accountid, a.saccountno accountNo \n");
		m_sbFrom.append("    from sett_account a, sett_dailyaccountbalance da \n");
		m_sbFrom.append("    WHERE  a.id=da.naccountid and a.ncheckstatusid=4 and a.naccounttypeid in (" + getDepositAccountTypeWithoutWTDeposit(qawi.getCurrencyID(),qawi.getOfficeID()) + ") \n");
		m_sbFrom.append("           and da.dtdate between  to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd') \n");
		m_sbFrom.append("           and to_date('" + DataFormat.getDateString(queryEndDate) + "','yyyy-mm-dd') \n");


		if (isToday)
		{
			m_sbFrom.append("     -- 当前余额 \n");
			m_sbFrom.append("     union all \n");
			m_sbFrom.append("   select a.nclientid , a.nofficeid , a.ncurrencyid , sa.mbalance Balance ,sa.naccountid accountid, a.saccountno accountNo\n");
			m_sbFrom.append("    from sett_account a, sett_subAccount sa \n");
			m_sbFrom.append("    WHERE  a.id=sa.naccountid and a.naccounttypeid in (" + getDepositAccountTypeWithoutWTDeposit(qawi.getCurrencyID(),qawi.getOfficeID()) + ") \n");
			m_sbFrom.append(" and a.ncheckstatusid=4 \n");
			
		}
		m_sbFrom.append("   ) aa \n");
		m_sbFrom.append("  where c.id=aa.nclientid and aa.nofficeid=" + qawi.getOfficeID() + " and aa.ncurrencyid=" + qawi.getCurrencyID() + " \n");
		
//		add by fxzhang 2012-6-8 start 存款积数查询，增加了查询条件
		//客户编号“从”
		if(qawi.getStartClientCode()!=null && !qawi.getStartClientCode().equals("")){
			m_sbFrom.append(" and c.scode >= '" +qawi.getStartClientCode() + "' ");
		}
		//客户编号“到”
		if(qawi.getEndClientCode()!=null && !qawi.getEndClientCode().equals("")){
			m_sbFrom.append(" and c.scode <= '" +qawi.getEndClientCode() + "' ");
			
		}
		//账户号“从”
		if(qawi.getStartAccountNo()!=null && !qawi.getStartAccountNo().equals("")){
			m_sbFrom.append(" and aa.accountNo >= '" +qawi.getStartAccountNo() + "' ");
		}
		//账户号“到”
		if(qawi.getEndAccountNo()!=null && !qawi.getEndAccountNo().equals("")){
			m_sbFrom.append(" and aa.accountNo <= '" +qawi.getEndAccountNo() + "' ");
			
		}
		//指定账户================

		if(qawi.getAppointAccountNo()!=null && !qawi.getAppointAccountNo().equals("")){
			String[] account=qawi.getAppointAccountNo().split(",");
			String inarea="(";
			for(int i=0;i<account.length-1;i++){
					 inarea+=account[i]+",";
			}
			inarea+=account[account.length-1]+")";
			m_sbFrom.append(" and aa.accountid in " +inarea);
		}
//		add by fxzhang 2012-6-8 end
		
		m_sbFrom.append("  group by c.id , c.scode, c.sname \n");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("  having sum(aa.balance)>0 \n");
		m_sbFrom.append(" ) rr \n");

		// where     
		m_sbWhere = new StringBuffer();

		//=======================
		//日期“从”
		/*
		if(qawi.getStartQueryDate()!=null){
			m_sbWhere.append(qawi.getStartQueryDate());
		}
		//日期“到”
		if(qawi.getEndQueryDate()!=null){
			m_sbWhere.append(qawi.getEndQueryDate());
			
		}
		*/
		// add end
		m_sbOrderBy = new StringBuffer();
		if(qawi.getOrderField() == 2)
		{
			m_sbOrderBy.append(" \n order by balance ");
		}
		else
		{
			m_sbOrderBy.append(" \n order by ClientCode ");
		}
		
		if(qawi.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			m_sbOrderBy.append(" desc ");
		}
	}
	
	/*
	 * 2011-03-02  by jiangqi  
	 * 
	 * 存款明细查询，只查询 账户组类型 为 活期（不包括委托存款），定期，和通知的账户
	 * 
	 * 用来算客户存款的合计（主要是合计排序需要根据的字段）
	 * 
	 * */
	public void getClientInfoOnlyCDN(QueryAccountWhereInfo qawi)
	{
		Log.print("*****	getClientInfo	****");

		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" \n * \n");

		m_sbFrom = new StringBuffer();

		m_sbFrom.append(" ( SELECT c.id ClientID, c.scode ClientCode, c.sname ClientName,sum(aa.balance) Balance  \n");
		m_sbFrom.append("  FROM  client c, \n");
		if (isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getQueryDate()))
		{
			m_sbFrom.append("   (select a.nclientid , a.nofficeid , a.ncurrencyid , sa.mbalance Balance ,sa.naccountid accountid, a.saccountno accountNo\n");
			m_sbFrom.append("    from sett_account a, sett_subAccount sa \n");
			m_sbFrom.append("    WHERE  a.id=sa.naccountid and a.naccounttypeid in (" + getDepositAccountTypeWithoutWTDeposit(qawi.getCurrencyID(),qawi.getOfficeID()) + ") \n");
			m_sbFrom.append(" and a.ncheckstatusid=4 \n");
			m_sbFrom.append("   ) aa \n");
		}
		else
		{
			m_sbFrom.append("   (select a.nclientid , a.nofficeid , a.ncurrencyid , da.mbalance Balance,da.naccountid accountid, a.saccountno accountNo \n");
			m_sbFrom.append("    from sett_account a, sett_dailyaccountbalance da \n");
			m_sbFrom.append("    WHERE  a.id=da.naccountid and a.ncheckstatusid=4 and a.naccounttypeid in (" + getDepositAccountTypeWithoutWTDeposit(qawi.getCurrencyID(),qawi.getOfficeID()) + ") \n");
			m_sbFrom.append("           and da.dtdate=to_date('" + DataFormat.getDateString(qawi.getQueryDate()) + "','yyyy-mm-dd') \n");
			m_sbFrom.append("   ) aa \n");
		}
		m_sbFrom.append("  where c.id=aa.nclientid and aa.nofficeid=" + qawi.getOfficeID() + " and aa.ncurrencyid=" + qawi.getCurrencyID() + " \n");
	
		m_sbFrom.append("  group by c.id , c.scode, c.sname \n");
		if (qawi.getIsFilterNull() > 0)
			m_sbFrom.append("  having sum(aa.balance)>0 \n");
		m_sbFrom.append(" ) rr \n");

		// where     
		m_sbWhere = new StringBuffer();

		//=======================
		//日期“从”
		/*
		if(qawi.getStartQueryDate()!=null){
			m_sbWhere.append(qawi.getStartQueryDate());
		}
		//日期“到”
		if(qawi.getEndQueryDate()!=null){
			m_sbWhere.append(qawi.getEndQueryDate());
			
		}
		*/
		// add end
		m_sbOrderBy = new StringBuffer();
		if(qawi.getOrderField() == 2)
		{
			m_sbOrderBy.append(" \n order by balance ");
		}
		else
		{
			m_sbOrderBy.append(" \n order by ClientCode ");
		}
		
		if(qawi.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
		{
			m_sbOrderBy.append(" desc ");
		}
	}
	
	public void getAccountInfoSQL(QueryAccountWhereInfo qaci)
	{
		m_sbSelect = new StringBuffer();
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
		m_sbFrom = new StringBuffer();

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
		m_sbWhere = new StringBuffer();
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
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qaci.getDesc() == 1 ? " desc " : "";
		switch ((int) qaci.getOrderField())
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
		System.out.println("select " +m_sbSelect.toString() + " from "+ m_sbFrom.toString() + " where "+m_sbWhere.toString() + m_sbOrderBy.toString());
	}
	public void findNegotiateCurrentAccountInfo(QueryAccountWhereInfo qaci)
	{
		m_sbSelect = new StringBuffer();
		// select 
		// modify by leiyang 修改账户信息查询存款合计的查询
		m_sbSelect.append("   \n    distinct acct.nofficeID as OfficeID, acct.nCurrencyID CurrencyID,acct.ID AccountID, \n");
		m_sbSelect.append("        acct.sAccountNo as AccountNo, acct.sName as AccountName, acct.nClientID as ClientID, \n");
		m_sbSelect.append("        Client.SCode as ClientCode,Client.SQUERYPASSWORD as QueryPassWord, Client.sname ClientName,acct.nAccountTypeID as AccountTypeID, \n");
		m_sbSelect.append("        acct.dtOpen as OpenDate,acct.dtFinish  as ClearDate,acct.nstatusid as MainAccountStatusID, acct.sabstract, \n"); 		
		m_sbSelect.append("        b.balance,b.interest,b.availableBalance,b.IsNegotiate,b.limitamount,aa.ac_nInterestRatePlanID interestPlanID,1 isToday \n");
		
       //added by mzh_fu 2008/05/08 解决账户余额查询不统一问题,增加下面7行
		m_sbSelect.append("        ,ss.AC_NFIRSTLIMITTYPEID  firstLimitTypeId,");
		m_sbSelect.append("        ss.AC_MFIRSTLIMITAMOUNT   firstLimitAmount,");
		m_sbSelect.append("        ss.AC_NSECONDLIMITTYPEID  secondLimitTypeId,");
		m_sbSelect.append("        ss.AC_MSECONDLIMITAMOUNT  secondLimitAmount,");
		m_sbSelect.append("        ss.AC_NTHIRDLIMITTYPEID   thirdLimitTypeId,");
		m_sbSelect.append("        ss.AC_MTHIRDLIMITAMOUNT   thirdLimitAmount,");
		m_sbSelect.append("        ss.AC_MCAPITALLIMITAMOUNT capitalLimitAmount");
		
		// from 
		m_sbFrom = new StringBuffer();

		//modified by mzh_fu 2008/05/08 解决账户余额查询不统一问题,增加：sett_subaccount ss,
		m_sbFrom.append("         sett_account acct, client,sett_subaccount ss, (select DISTINCT a.id,b.ac_nInterestRatePlanID from sett_account a,sett_subaccount b where a.ID=b.naccountid(+)) aa, \n");
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
		m_sbWhere = new StringBuffer();
		//
		m_sbWhere.append("        b.nAccountId=aa.id  \n");
		m_sbWhere.append("        and ss.ac_nisnegotiate = 1\n");
		m_sbWhere.append("        and acct.nstatusid in (1,2,3,7,8) \n");
		m_sbWhere.append("        and ss.nstatusid in (1,2,3,4,5,6,7,8) \n");
		//added by mzh_fu 2008/05/08 解决账户余额查询不统一问题,增加下面1行
		m_sbWhere.append("        and ss.naccountid = acct.id  \n");		
		
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
		if (qaci.getAccountStatusID() != 0 && qaci.getAccountStatusID() >0)
			m_sbWhere.append("        and acct.nstatusid = " + qaci.getAccountStatusID() + "\n");
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
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qaci.getDesc() == 1 ? " desc " : "";
		switch ((int) qaci.getOrderField())
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
		System.out.println("select " +m_sbSelect.toString() + " from "+ m_sbFrom.toString() + " where "+m_sbWhere.toString() + m_sbOrderBy.toString());
	}
	public void getHistoryAccountBalance(QueryAccountWhereInfo qaci)
	{
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append("  \n    distinct acct.nofficeID as OfficeID, acct.nCurrencyID CurrencyID,acct.ID AccountID, \n");
		m_sbSelect.append("        acct.sAccountNo as AccountNo, acct.sName as AccountName, acct.nClientID as ClientID, \n");
		m_sbSelect.append("        Client.SCode as ClientCode, Client.sname ClientName,Client.SQUERYPASSWORD as QueryPassWord,acct.nAccountTypeID as AccountTypeID, \n");
		m_sbSelect.append("        acct.dtOpen as OpenDate,acct.dtFinish  as ClearDate,acct.nstatusid as MainAccountStatusID, \n");
		m_sbSelect.append("        b.balance,b.interest,b.balance availableBalance,b.UncheckPaymentAmount,b.InterestPlanID,b.IsNegotiate,b.limitamount,1 isToday \n");
		// from 
		m_sbFrom = new StringBuffer();
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
		m_sbWhere = new StringBuffer();
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
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qaci.getDesc() == 1 ? " desc " : "";
		switch ((int) qaci.getOrderField())
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
		//logger.debug(m_sbSelect.toString() + m_sbFrom.toString() + m_sbWhere.toString());
	}
	public QueryAccountSumInfo queryAccountDepositSum(QueryAccountWhereInfo qaci) throws Exception
	{
		QueryAccountSumInfo sumObj = new QueryAccountSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strDepositWhere = "";
		String strLoanWhere = "";
		String strLastWhere="";
		String sql = "";
		//

		try
		{
			//	modify by zcwang 注释以前的代码，增加客户限制
			/*
			if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
				qaci.setStartClientCode(null);
			if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
				qaci.setEndClientCode(null);
			*/
			getAccountInfoSQL(qaci);
			
			// add by xfma(马现福) 2008-7-14 
			// 修改账户信息查询贷款合计的查询
			m_sbSelect = new StringBuffer();
			m_sbSelect.append("   \n    distinct acct.nofficeID as OfficeID, acct.nCurrencyID CurrencyID,acct.ID AccountID, \n");
			m_sbSelect.append("        acct.sAccountNo as AccountNo, acct.sName as AccountName, acct.nClientID as ClientID, \n");
			m_sbSelect.append("        Client.SCode as ClientCode,Client.SQUERYPASSWORD as QueryPassWord, Client.sname ClientName,acct.nAccountTypeID as AccountTypeID, \n");
			m_sbSelect.append("        acct.dtOpen as OpenDate,acct.dtFinish  as ClearDate,acct.nstatusid as MainAccountStatusID, acct.sabstract, \n"); 		
			m_sbSelect.append("        b.balance,b.interest,b.availableBalance,b.IsNegotiate,b.limitamount,aa.ac_nInterestRatePlanID interestPlanID,1 isToday \n");
			
			// select 
			//strSelect = " select sum(round(b.Balance,2)) balance \n";
			// modify by leiyang 修改账户信息查询存款合计的查询
			strSelect = "select sum(round(Balance,2)) balance from ( select " + m_sbSelect.toString();
			strDepositWhere = "        and acct.nAccounttypeid in(" + getDepositAccountType(qaci.getCurrencyID(),qaci.getOfficeID()) + ") \n";
			//Modify by xfma(马现福) 2008/07/08
			//账户信息查询 存款合计 不统计委托存款账户余额(国电专用)
			strDepositWhere += " and acct.nAccounttypeid != nvl((select id from sett_accounttype where nAccountGroupID = "
				+ SETTConstant.AccountGroupType.CURRENT +" and nstatusId=1 and officeId="
				+ qaci.getOfficeID() +" and currencyId="+ qaci.getCurrencyID() 
				+" and saccounttypecode = '"+Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2")+"' ),-999)) \n";
			
		if(!getLoanAccountType(qaci.getCurrencyID(),qaci.getOfficeID()).equals("") ||getLoanAccountType(qaci.getCurrencyID(),qaci.getOfficeID()).length()>1)
		{
			strLoanWhere = "  and acct.nAccounttypeid in(" + getLoanAccountType(qaci.getCurrencyID(),qaci.getOfficeID()) + ") \n";
		}
			strLastWhere= ")";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strDepositWhere;
			logger.info("存款统计SQL:" + sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 存款合计：活期+定期
				sumObj.setDepositBalanceSum(rs.getDouble("balance"));
			}
			cleanup(rs);
			cleanup(ps);
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString() + strLoanWhere+strLastWhere;
			logger.info("贷款统计SQL:" + sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 贷款合计：信托+委托+贴现
				sumObj.setLoanBalanceSum(rs.getDouble("balance"));
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
	 * 查询-账户信息查询
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAccountInfoForRemind(QueryAccountWhereInfo qaci,String strID) throws Exception
	{
		if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
			qaci.setStartClientCode(null);
		if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
			qaci.setEndClientCode(null);

		getAccountInfoSQL(qaci);
		//m_sbWhere.append(" and acct.id = 2 ");
		m_sbWhere.append(" and acct.id in ("+strID+") ");
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		logger.print("m_sbFrom:"+m_sbFrom.toString());
		logger.print("m_sbSelect:"+m_sbSelect.toString());
		logger.print("m_sbWhere:"+m_sbWhere.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo",
			null);
		logger.print("JAVA:m_sbSelect:"+m_sbSelect.toString()+"<br>");
		logger.print("JAVA:m_sbFrom:"+m_sbFrom.toString()+"<br>");
		logger.print("JAVA:m_sbWhere:"+m_sbWhere.toString()+"<br>");
		logger.print("JAVA:m_sbOrderBy:"+m_sbOrderBy.toString()+"<br>");
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 查询-账户信息查询
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAccountInfo(QueryAccountWhereInfo qaci) throws Exception
	{   
		//modify by zcwang 注释以前的代码，增加客户限制
//		if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
//			qaci.setStartClientCode(null);
//		if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
//			qaci.setEndClientCode(null);
       //
		getAccountInfoSQL(qaci);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	/**
	 * 查询-是协定存款的活期账户信息查询
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader findNegotiateCurrentAccount(QueryAccountWhereInfo qaci) throws Exception
	{   
		//modify by zcwang 注释以前的代码，增加客户限制
//		if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
//			qaci.setStartClientCode(null);
//		if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
//			qaci.setEndClientCode(null);
       //
		//getAccountInfoSQL(qaci);
		findNegotiateCurrentAccountInfo(qaci);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	/**
	 * 查询-账户余额查询
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAccountBalance(QueryAccountWhereInfo qaci) throws Exception
	{   
		if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
			qaci.setStartClientCode(null);
		if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
			qaci.setEndClientCode(null);

		if (isToday(qaci.getOfficeID(), qaci.getCurrencyID(), qaci.getQueryDate()))
			getAccountInfoSQL(qaci);
		else
			getHistoryAccountBalance(qaci);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		logger.info(m_sbFrom.toString());
		logger.info(m_sbSelect.toString());
		logger.info(m_sbWhere.toString());
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	public String queryAccountBalanceStr(QueryAccountWhereInfo qaci) throws Exception{
		if (isToday(qaci.getOfficeID(), qaci.getCurrencyID(), qaci.getQueryDate()))
			getAccountInfoSQL(qaci);
		else
			getHistoryAccountBalance(qaci);
		return "select "+m_sbSelect+" from "+m_sbFrom+" where "+m_sbWhere;
	}
	/**
	 * 查询-存款明细查询
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryDepositDetails(QueryAccountWhereInfo qawi) throws Exception
	{
		if (qawi.getStartAccountNo() != null && qawi.getStartAccountNo().length() > 0)
			qawi.setStartClientCode(null);
		if (qawi.getEndAccountNo() != null && qawi.getEndAccountNo().length() > 0)
			qawi.setEndClientCode(null);
		//2011-03-02  by jiangqi   
		//只查出 改客户 	账户组类型 为 活期（不包括委托存款），定期，和通知的账户	
		//getClientInfo(qawi);
		//modified by fxzhang 2012-6-8 start 将存款明细查询和存款积数查询的sql分开
//		getClientInfoOnlyCDN(qawi);
		//存款积数查询
		if(qawi.isSearchSum())
		{
			getClientInfoOnlyCDN1(qawi);
		}
		//存款明细查询
		else
		{
			getClientInfoOnlyCDN(qawi);
		}
		//modified by fxzhang 2012-6-8 end
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAccountResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;

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

	
	/**
	 * 活期存款积数查询
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public double getCurrentSumBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		//2004-04-13, Forest注释，原因：存款明细中活期存款仅包括01类型
		//qawi.setAccountTypeSet(getCurrentAccountType(qawi.getCurrencyID()));
		double dReturn = 0.0;
		double dTodayReturn = 0.0;
		String accountType = "";
		accountType = getCurrentAccountType(qawi.getCurrencyID(),qawi.getOfficeID(),1);
		if(!("").equals(accountType)&&accountType.length()>0)
		{
			qawi.setAccountTypeSet(accountType);	
		}else{
			return dReturn;
		}
		boolean startDateIsToday = isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getStartQueryDate());
		boolean endDateIsToday = isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate());
//		取得开机日的余额
		if( startDateIsToday || endDateIsToday)
		{
			dTodayReturn = getTodayAccountBalanceOfClient(qawi);
		}
//		取得历史存款积数
		if( !startDateIsToday )
		{
			dReturn = getHistoryAccountBalanceOfClient(qawi);
		}
		dReturn += dTodayReturn;

		return dReturn;
	}
	
	/**
	 * 协定存款积数查询
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public double getNegotiateSumBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		//2004-04-13, Forest注释，原因：存款明细中活期存款仅包括01类型
		//qawi.setAccountTypeSet(getCurrentAccountType(qawi.getCurrencyID()));
		double dReturn = 0.0;
		double dTodayReturn = 0.0;
		String accountType = "";
		accountType = getCurrentAccountType(qawi.getCurrencyID(),qawi.getOfficeID(),1);
		if(!("").equals(accountType)&&accountType.length()>0)
		{
			qawi.setAccountTypeSet(accountType);	
		}else{
			return dReturn;
		}
		boolean startDateIsToday = isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getStartQueryDate());
		boolean endDateIsToday = isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate());
		//取得开机日的余额
		if( startDateIsToday || endDateIsToday)
		{
			dTodayReturn = getTodayNegotiateBalanceOfClient(qawi);
		}
		//取得历史存款积数
		if( !startDateIsToday )
		{
			dReturn = getHistoryNegotiateBalanceOfClient(qawi);
		}
		dReturn += dTodayReturn;


		return dReturn;
	}
	
	/**
	 * 定期存款积数查询
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public double getFixSumBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		double dReturn = 0.0;
		double dTodayReturn = 0.0;
		String accountType = "";
		accountType = getFixAccountType(qawi.getCurrencyID(),qawi.getOfficeID());
		if(!("").equals(accountType)&&accountType.length()>0)
		{
			qawi.setAccountTypeSet(accountType);
		}else{
			return dReturn;
		}
		boolean startDateIsToday = isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getStartQueryDate());
		boolean endDateIsToday = isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate());
//		取得开机日的余额
		if( startDateIsToday || endDateIsToday)
		{
			dTodayReturn = getTodayAccountBalanceOfClient(qawi);
		}
//		取得历史存款积数
		if( !startDateIsToday )
		{
			dReturn = getHistoryAccountBalanceOfClient(qawi);
		}
		dReturn += dTodayReturn;


		return dReturn;
	}

	/**
	 * 通知存款积数查询
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public double getNoticeSumBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		double dReturn = 0.0;
		double dTodayReturn = 0.0;
		String accountType = "";
		accountType = getNotifyAccountType(qawi.getCurrencyID(),qawi.getOfficeID());
		if(!("").equals(accountType)&&accountType.length()>0)
		{
			qawi.setAccountTypeSet(accountType);
		}else{
			return dReturn;
		}
		boolean startDateIsToday = isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getStartQueryDate());
		boolean endDateIsToday = isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate());
//		取得开机日的余额
		if( startDateIsToday || endDateIsToday)
		{
			dTodayReturn = getTodayAccountBalanceOfClient(qawi);
		}
//		取得历史存款积数
		if( !startDateIsToday )
		{
			dReturn = getHistoryAccountBalanceOfClient(qawi);
		}
		dReturn += dTodayReturn;


		return dReturn;
	}
	
	/**
	 * 开机日的协定存款积数查询
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public double getTodayNegotiateBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		double dReturn = 0.0;
		//

		try
		{
			con = this.getConnection();
			StringBuffer sb = new StringBuffer();

			sb.append(" select sum(decode(subacct.AC_mNegotiateUnit,0,0,floor( decode(sign(subacct.mBalance-subacct.AC_mNegotiateAmount-1),-1,0,(subacct.mBalance-subacct.AC_mNegotiateAmount))/subacct.AC_mNegotiateUnit)*subacct.AC_mNegotiateUnit))  balance \n");
			sb.append(" from client, sett_account acct, sett_subaccount subacct \n");
			sb.append(" where client.id=acct.nclientid and acct.id=subacct.naccountid and acct.ncheckstatusid=4  and subacct.nstatusid>0 \n");
			if (qawi.getClientID() > 0)
				sb.append(" and client.id=" + qawi.getClientID() + " \n");
			sb.append("       and acct.ncurrencyid=" + qawi.getCurrencyID());
			if (qawi.getAccountTypeSet() != null && qawi.getAccountTypeSet().length() > 0)
				sb.append("       and acct.naccounttypeid in(" + qawi.getAccountTypeSet() + ") \n");
//			add by fxzhang 2012-6-8 start 存款积数查询，增加了查询条件
			//客户编号“从”
			if(qawi.getStartClientCode()!=null && !qawi.getStartClientCode().equals("")){
				sb.append(" and client.scode >= '" +qawi.getStartClientCode() + "' ");
			}
			//客户编号“到”
			if(qawi.getEndClientCode()!=null && !qawi.getEndClientCode().equals("")){
				sb.append(" and client.scode <= '" +qawi.getEndClientCode() + "' ");
				
			}
			//账户号“从”
			if(qawi.getStartAccountNo()!=null && !qawi.getStartAccountNo().equals("")){
				sb.append(" and acct.saccountno >= '" +qawi.getStartAccountNo() + "' ");  
			}
			//账户号“到”
			if(qawi.getEndAccountNo()!=null && !qawi.getEndAccountNo().equals("")){
				sb.append(" and acct.saccountno <= '" +qawi.getEndAccountNo() + "' ");
				
			}
			//指定账户================

			if(qawi.getAppointAccountNo()!=null && !qawi.getAppointAccountNo().equals("")){
				String[] account=qawi.getAppointAccountNo().split(",");
				String inarea="(";
				for(int i=0;i<account.length-1;i++){
						 inarea+=account[i]+",";
				}
				inarea+=account[account.length-1]+")";
				sb.append(" and acct.id in " +inarea);
			}
	
			//add by fxzhang 2012-6-8 end
			sb.append(" group by client.id \n");
			//Log.print(sb.toString());
			// select 
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("balance");
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
		return dReturn;
	}
	
	/**历史的协定存款积数查询
	 * @param qawi
	 * @return
	 * @throws Exception
	 */
	public double getHistoryNegotiateBalanceOfClient(QueryAccountWhereInfo qawi) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		double dReturn = 0.0;
		//

		try
		{
			con = this.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select sum(decode(subacct.AC_mNegotiateUnit,0,0,floor( decode(sign(daily.mBalance-subacct.AC_mNegotiateAmount-1),-1,0,(daily.mBalance-subacct.AC_mNegotiateAmount))/subacct.AC_mNegotiateUnit)*subacct.AC_mNegotiateUnit))  balance \n");
			sb.append(" from client, sett_account acct, sett_dailyaccountbalance daily, sett_subaccount subacct \n");
			sb.append(" where client.id=acct.nclientid and acct.id=daily.naccountid  and acct.ncheckstatusid=4 \n");
			sb.append(" and subacct.naccountid=acct.id and subacct.nstatusid>0 ");
			if (qawi.getClientID() > 0)
				sb.append("   and client.id=" + qawi.getClientID() + " \n");
			sb.append("       and acct.ncurrencyid=" + qawi.getCurrencyID());
			if (qawi.getAccountTypeSet() != null && qawi.getAccountTypeSet().length() > 0)
				sb.append("   and acct.naccounttypeid in(" + qawi.getAccountTypeSet() + ") \n");
			if(qawi.getStartQueryDate() != null)
			{
				sb.append("       and daily.dtDate>=to_date('" + DataFormat.getDateString(qawi.getStartQueryDate()) + "','yyyy-mm-dd')  \n");
			}
			if(qawi.getEndQueryDate() != null)
			{
				Timestamp queryEndDate = qawi.getEndQueryDate();
				//如果查询结束日期为开机日，则取查询结束日期的前一天。因为如果是关机状态，那开机日是有每日余额记录的。而开机日当天的余额已经加过了。
				if(isToday(qawi.getOfficeID(), qawi.getCurrencyID(), qawi.getEndQueryDate()))
				{
					queryEndDate = DataFormat.getNextDate(qawi.getEndQueryDate(), -1);
				}

				sb.append("       and daily.dtDate<=to_date('" + DataFormat.getDateString(queryEndDate) + "','yyyy-mm-dd')  \n");
			}
//			add by fxzhang 2012-6-8 start 存款积数查询，增加了查询条件
			//客户编号“从”
			if(qawi.getStartClientCode()!=null && !qawi.getStartClientCode().equals("")){
				sb.append(" and client.scode >= '" +qawi.getStartClientCode() + "' ");
			}
			//客户编号“到”
			if(qawi.getEndClientCode()!=null && !qawi.getEndClientCode().equals("")){
				sb.append(" and client.scode <= '" +qawi.getEndClientCode() + "' ");
				
			}
			//账户号“从”
			if(qawi.getStartAccountNo()!=null && !qawi.getStartAccountNo().equals("")){
				sb.append(" and acct.saccountno >= '" +qawi.getStartAccountNo() + "' ");  
			}
			//账户号“到”
			if(qawi.getEndAccountNo()!=null && !qawi.getEndAccountNo().equals("")){
				sb.append(" and acct.saccountno <= '" +qawi.getEndAccountNo() + "' ");
				
			}
			//指定账户================

			if(qawi.getAppointAccountNo()!=null && !qawi.getAppointAccountNo().equals("")){
				String[] account=qawi.getAppointAccountNo().split(",");
				String inarea="(";
				for(int i=0;i<account.length-1;i++){
						 inarea+=account[i]+",";
				}
				inarea+=account[account.length-1]+")";
				sb.append(" and acct.id in " +inarea);
			}
	
			//add by fxzhang 2012-6-8 end
			sb.append(" group by client.id \n");
			Log.print(sb.toString());
			System.out.println(sb.toString());
			// select 
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("balance");
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
		return dReturn;
	}
}