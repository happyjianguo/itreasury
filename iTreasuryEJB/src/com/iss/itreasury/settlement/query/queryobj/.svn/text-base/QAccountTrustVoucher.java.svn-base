/*
 * Created on 2004-3-17
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import com.iss.itreasury.settlement.query.paraminfo.QueryAccountTrustVoucherWhereInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QAccountTrustVoucher extends BaseQueryObject{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbGroupBy = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QAccountTrustVoucher()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getAccountTrustVoucherSQL(QueryAccountTrustVoucherWhereInfo queryAccountTrustVoucherWhereInfo)
	{
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" * \n");
		
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sett_AccountTrustVoucher \n");
		
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1=1 and StatusID >0 ");
		if(queryAccountTrustVoucherWhereInfo.getAccountID() >0)
		{
			m_sbWhere.append(" and AccountID ="+queryAccountTrustVoucherWhereInfo.getAccountID());
		}
		if(queryAccountTrustVoucherWhereInfo.getVoucherStart() != null && queryAccountTrustVoucherWhereInfo.getVoucherStart().length() >0
			&& queryAccountTrustVoucherWhereInfo.getVoucherEnd() != null && queryAccountTrustVoucherWhereInfo.getVoucherEnd().length() >0)
		{
			m_sbWhere.append(" and VoucherNo between '"+queryAccountTrustVoucherWhereInfo.getVoucherStart()+"' and '"+queryAccountTrustVoucherWhereInfo.getVoucherEnd()+"'");
		}
		if(queryAccountTrustVoucherWhereInfo.getInputDate() != null)
		{
			m_sbWhere.append(" and InputDate =to_date('"+DataFormat.formatDate(queryAccountTrustVoucherWhereInfo.getInputDate())+"','yyyy-mm-dd')");
		}

		//group by
		m_sbGroupBy = new StringBuffer();
	}

	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAccountTrustVoucher(QueryAccountTrustVoucherWhereInfo queryAccountTrustVoucherWhereInfo) throws Exception
	{

		getAccountTrustVoucherSQL(queryAccountTrustVoucherWhereInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		AppContext appcontext = new AppContext();

		pageLoader.initPageLoader(
			appcontext,
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryAccountTrustVoucherResultInfo",
			null);
		pageLoader.setOrderBy(" order by VoucherNo ");
		pageLoader.setGroupBy(" " + m_sbGroupBy.toString() + " ");
		return pageLoader;
	}

}
