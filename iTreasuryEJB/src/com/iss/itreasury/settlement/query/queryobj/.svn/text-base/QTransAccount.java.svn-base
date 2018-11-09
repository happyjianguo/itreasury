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

import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QTransAccount extends BaseQueryObject
{

	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_ClientCode = 2;
	public final static int OrderBy_ClientName = 3;
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbGroupBy = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QTransAccount()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getAccountInfoSQL(QueryTransAccountDetailWhereInfo qtai)
	{
		//add by zcwang 2008-04-01  判断是否查询当天的账户信息
		boolean isSelectToday = false;
		if(qtai !=null)
		{
			Timestamp tempToday = Env.getSystemDate(qtai.getOfficeID(),qtai.getCurrencyID());
			if( qtai.getEndDate().compareTo(tempToday)==0)
			{
				isSelectToday = true;
			}
		}
		//
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" sett_Account.ID AccountID,sett_Account.sAccountNo AccountNo,Client.sCode ClientCode,Client.sName ClientName, \n");
		if(!isSelectToday)
		{
			m_sbSelect.append(" sum(sett_SubAccount.mBalance) CurrentBalance,nvl(sum(sett_DailyAccountBalance.mBalance),0.00) HistoryBalance \n");
		}
		else
		{
			m_sbSelect.append(" sum(sett_SubAccount.mBalance) CurrentBalance,sum(sett_SubAccount.mBalance) HistoryBalance \n");
		}
		// from 
		m_sbFrom = new StringBuffer();
		if(!isSelectToday)
		{
			m_sbFrom.append(" sett_Account ,sett_SubAccount,Client,sett_DailyAccountBalance \n");
		}
		else
		{
			m_sbFrom.append(" sett_Account ,sett_SubAccount,Client \n");
		}
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sett_SubAccount.nAccountID = sett_Account.ID and sett_Account.nClientID = Client.ID \n");
		if(!isSelectToday)
		{
			m_sbWhere.append(" and sett_SubAccount.ID = sett_DailyAccountBalance.nSubAccountID(+) \n");
		}
		m_sbWhere.append(" and sett_Account.nOfficeID = "+qtai.getOfficeID()+" and sett_Account.nCurrencyID = "+qtai.getCurrencyID()+" \n");
		//m_sbWhere.append(" and sett_DailyAccountBalance.nAccountID(+) = sett_Account.ID \n");//子账户中已经存在外连接
		m_sbWhere.append(" and sett_Account.nCheckStatusID="+SETTConstant.AccountCheckStatus.CHECK+" \n");
		if(!isSelectToday)
		{
			//m_sbWhere.append(" and sett_DailyAccountBalance.dtDate between to_date('"+DataFormat.formatDate(qtai.getStartDate())+"','yyyy-mm-dd') and to_date('"+DataFormat.formatDate(qtai.getEndDate())+"','yyyy-mm-dd') \n");
			m_sbWhere.append(" and sett_DailyAccountBalance.dtDate(+) = to_date('"+DataFormat.formatDate(qtai.getEndDate())+"','yyyy-mm-dd') \n");
		}
		if (qtai.getStartClientCode() != null && qtai.getStartClientCode().length() > 0)
			m_sbWhere.append(" and client.scode>='" + qtai.getStartClientCode() + "'");
		if (qtai.getEndClientCode() != null && qtai.getEndClientCode().length() > 0)
			m_sbWhere.append(" and client.scode<='" + qtai.getEndClientCode() + "'");
		if (qtai.getStartAccountNo() != null && qtai.getStartAccountNo().length() > 0)
			m_sbWhere.append(" and sett_Account.sAccountNo>='" + qtai.getStartAccountNo() + "'");
		if (qtai.getEndAccountNo() != null && qtai.getEndAccountNo().length() > 0)
			m_sbWhere.append(" and sett_Account.sAccountNo<='" + qtai.getEndAccountNo() + "'");
		//add by 2012-05-15 添加指定编号
		if(qtai.getAppointAccountNo() != null && qtai.getAppointAccountNo().length() > 0){
			m_sbWhere.append(" and sett_Account.sAccountNo in ('"+qtai.getAppointAccountNo()+"')");
		}
		//add by xfma 2008-12-2 账户状态
		if (!qtai.getAccountStatusIDs().equals("") && qtai.getAccountStatusIDs() != null )
		{
			m_sbWhere.append(" and sett_Account.Nstatusid in ("+qtai.getAccountStatusIDs()+")");
		}		
		 		
		if(qtai.getIsFilterNull() == 1)
		{
			//m_sbWhere.append(" and sett_Account.nStatusID <> " + SETTConstant.AccountStatus.CLOSE);
			//滤空表示在sett_transaccountdetail内是否有对应当前帐户的记录
			m_sbWhere.append(" and sett_Account.ID in (select distinct ntransaccountid from sett_transaccountdetail where nstatusID="+SETTConstant.TransactionStatus.CHECK);
			//add by xfma 2008-12-2 按起息日统计
			if(qtai.getIsIntrDate() == 1)
			{
				if(qtai.getStartDate()!=null){
					m_sbWhere.append(" and dtintereststart>=to_date('"+DataFormat.formatDate(qtai.getStartDate())+"','yyyy-mm-dd')");
				}
				if(qtai.getStartDate()!=null){
					m_sbWhere.append(" and dtintereststart<=to_date('"+DataFormat.formatDate(qtai.getEndDate())+"','yyyy-mm-dd')");
				}
			}
			else
			{
				if(qtai.getStartDate()!=null){
					m_sbWhere.append(" and DTEXECUTE>=to_date('"+DataFormat.formatDate(qtai.getStartDate())+"','yyyy-mm-dd')");
				}
				if(qtai.getStartDate()!=null){
					m_sbWhere.append(" and DTEXECUTE<=to_date('"+DataFormat.formatDate(qtai.getEndDate())+"','yyyy-mm-dd')");
				}
			}
			m_sbWhere.append(")");
		}
		
		
		m_sbWhere.append(" \n group by sett_Account.ID,sett_Account.sAccountNo,client.sCode,client.sName \n");
		//order by
		m_sbOrderBy = new StringBuffer();
		String strDesc = qtai.getDesc() == 1 ? " asc " : " desc ";
		switch ((int) qtai.getOrderField())
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
		}
		//logger.debug(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
	}

	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAccount(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{

		getAccountInfoSQL(qtai);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		AppContext appcontext = new AppContext();

		pageLoader.initPageLoader(
			appcontext,
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	public void getOrganizationAccountInfoSQL(QueryTransAccountDetailWhereInfo qtai)
	{
		//add by zcwang 2008-04-01  判断是否查询当天的账户信息
		boolean isSelectToday = false;
		if(qtai !=null)
		{
			Timestamp tempToday = Env.getSystemDate(qtai.getOfficeID(),qtai.getCurrencyID());
			if( qtai.getEndDate().compareTo(tempToday)==0)
			{
				isSelectToday = true;
			}
		}
		//
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" sett_Account.ID AccountID,sett_Account.sAccountNo AccountNo,Client.sCode ClientCode,Client.sName ClientName,  sett_accounttype.saccounttype  AccountType ,\n");
		if(!isSelectToday)
		{
			m_sbSelect.append(" sum(sett_SubAccount.mBalance) CurrentBalance,nvl(sum(sett_DailyAccountBalance.mBalance),0.00) HistoryBalance \n");
		}
		else
		{
			m_sbSelect.append(" sum(sett_SubAccount.mBalance) CurrentBalance,sum(sett_SubAccount.mBalance) HistoryBalance \n");
		}
		// from 
		m_sbFrom = new StringBuffer();
		if(!isSelectToday)
		{
			m_sbFrom.append(" sett_Account ,sett_SubAccount,Client,sett_DailyAccountBalance,sett_accounttype \n");
		}
		else
		{
			m_sbFrom.append(" sett_Account ,sett_SubAccount,Client,sett_accounttype \n");
		}
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sett_SubAccount.nAccountID = sett_Account.ID and sett_Account.nClientID = Client.ID   and  sett_account.naccounttypeid=sett_accounttype.id(+) \n");
		if(!isSelectToday)
		{
			m_sbWhere.append(" and sett_SubAccount.ID = sett_DailyAccountBalance.nSubAccountID(+) \n");
		}
		//m_sbWhere.append(" and sett_Account.nOfficeID = "+qtai.getOfficeID()+" and sett_Account.nCurrencyID = "+qtai.getCurrencyID()+" \n");	
		m_sbWhere.append(" and Client.Isinstitutionalclient= "+qtai.getOfficeID()+" and sett_Account.nCurrencyID = "+qtai.getCurrencyID()+" \n");		
		m_sbWhere.append(" and sett_Account.nCheckStatusID="+SETTConstant.AccountCheckStatus.CHECK+" \n");
		if(!isSelectToday)
		{
			m_sbWhere.append(" and sett_DailyAccountBalance.dtDate(+) = to_date('"+DataFormat.formatDate(qtai.getEndDate())+"','yyyy-mm-dd') \n");
		}		
		if (qtai.getStartAccountNo() != null && qtai.getStartAccountNo().length() > 0)
			m_sbWhere.append(" and sett_Account.sAccountNo>='" + qtai.getStartAccountNo() + "'");
		if (qtai.getEndAccountNo() != null && qtai.getEndAccountNo().length() > 0)
			m_sbWhere.append(" and sett_Account.sAccountNo<='" + qtai.getEndAccountNo() + "'");		
		if (!qtai.getAccountTypeIDs().equals("") && qtai.getAccountTypeIDs() != null )
		{
			m_sbWhere.append(" and sett_Account.naccounttypeid in ("+qtai.getAccountTypeIDs()+")");
		}			
		m_sbWhere.append(" \n group by sett_Account.ID,sett_Account.sAccountNo,client.sCode,client.sName,sett_accounttype.saccounttype \n");
		//order by
		m_sbOrderBy = new StringBuffer();
		String strDesc = qtai.getDesc() == 1 ? " asc " : " desc ";
		switch ((int) qtai.getOrderField())
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
		}
		//logger.debug(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
	}

	public PageLoader queryOrganizationAccount(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{

		getOrganizationAccountInfoSQL(qtai);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		AppContext appcontext = new AppContext();

		pageLoader.initPageLoader(
			appcontext,
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo",
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
	 * @param buffer
	 */
	public void setOrderBy(StringBuffer buffer)
	{
		m_sbOrderBy = buffer;
	}

}