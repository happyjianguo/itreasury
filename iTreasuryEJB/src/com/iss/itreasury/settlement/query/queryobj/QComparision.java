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
import java.util.Vector;

import com.iss.itreasury.settlement.query.paraminfo.QComparisionConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QCapitalReceiveResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QComparisionResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QComparision extends BaseQueryObject
{

	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_ClientCode = 2;
	public final static int OrderBy_AccountName = 3;
	public final static int OrderBy_CurrencyID = 4;
	public final static int OrderBy_BankType = 5;
	public final static int OrderBy_BankAccountNo = 6;
	public final static int OrderBy_BankName = 7;
	//
	public StringBuffer m_sbSelect = null;
	public StringBuffer m_sbFrom = null;
	public StringBuffer m_sbWhere = null;
	public StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QComparision()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}
	
	public void getCompareAccountInfoSQL(QComparisionConditionInfo qaci)
	{
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append("  \n  acct.nCurrencyID as CurrencyID, \n");
		m_sbSelect.append("        acct.sAccountNo as AccountNo, acct.sName as AccountName, \n");
		m_sbSelect.append("        client.SCode as ClientCode, \n");		
		m_sbSelect.append("      c.s_name as BankType, \n");
		m_sbSelect.append("        b.s_accountno as BankAccountNo,b.s_accountname as BankName \n");
		// from 
		m_sbFrom = new StringBuffer();
 
		m_sbFrom.append("      sett_account acct, client client,bs_bankaccountinfo b,bs_banksetting c \n");
	
		// where 
		m_sbWhere = new StringBuffer();
		//
		m_sbWhere.append("      acct.id = b.n_subjectid and acct.nclientid = client.id(+) and c.n_id(+) = b.n_bankid and b.n_accountstatus = 1 and b.n_isCheck= 1 and b.n_rdstatus= 1  \n");
		if (qaci.getOfficeID() != -1)
			m_sbWhere.append("        and client.nOfficeID=" + qaci.getOfficeID());
		if (qaci.getStartClientCode() != null && qaci.getStartClientCode().length() > 0)
			m_sbWhere.append("        and client.scode>='" + qaci.getStartClientCode() + "'");
		if (qaci.getEndClientCode() != null && qaci.getEndClientCode().length() > 0)
			m_sbWhere.append("        and client.scode<='" + qaci.getEndClientCode() + "'");
		if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
			m_sbWhere.append("        and acct.sAccountNo>='" + qaci.getStartAccountNo() + "'");
		if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
			m_sbWhere.append("        and acct.sAccountNo<='" + qaci.getEndAccountNo() + "'");
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
			case OrderBy_AccountName :
				m_sbOrderBy.append(" \n order by AccountName" + strDesc);
				break;
			case OrderBy_CurrencyID :
				m_sbOrderBy.append(" \n order by CurrencyID" + strDesc);
				break;
			case OrderBy_BankType :
				m_sbOrderBy.append(" \n order by BankType" + strDesc);
				break;
			case OrderBy_BankAccountNo :
				m_sbOrderBy.append(" \n order by BankAccountNo" + strDesc);
				break;
			case OrderBy_BankName :
				m_sbOrderBy.append(" \n order by BankName" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by AccountNo" + strDesc);
				break;
		}
		//logger.debug("select " +m_sbSelect.toString() + " from "+ m_sbFrom.toString() + " where "+m_sbWhere.toString() + m_sbOrderBy.toString());
	}
	/**
	 * 查询-账户对照信息查询
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader CompareAccountInfo(QComparisionConditionInfo qaci) throws Exception
	{
		if (qaci.getStartAccountNo() != null && qaci.getStartAccountNo().length() > 0)
			qaci.setStartClientCode(null);
		if (qaci.getEndAccountNo() != null && qaci.getEndAccountNo().length() > 0)
			qaci.setEndClientCode(null);

		getCompareAccountInfoSQL(qaci);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QComparisionResultInfo",
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
}