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

import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
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
public class QTransactionForSubAccount extends BaseQueryObject
{

	//public final static int OrderBy_AccountNo = 1;

	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QTransactionForSubAccount()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}
	/**
	 * 产生查询SQL
	 * @param info
	 */
	public void getTransactionSQL(QueryTransactionConditionInfo info)
	{
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		m_sbSelect.append(" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		m_sbSelect.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		m_sbSelect.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		m_sbSelect.append(" LoanFormID,DepositNo,NewDepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		m_sbSelect.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName \n");

		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_VTRANSACTION \n");
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" ((TransactionTypeID not in (11,23) and StatusID=3) ");//多笔贷款收回/一付多收例外
		m_sbWhere.append(" or (TransactionTypeID in (11,23) and StatusID=7)) ");
		
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
			
		/*
		if (info.getStatusID() > 0)
			m_sbWhere.append(" and StatusID=" + info.getStatusID() + "");
		*/
		if (info.getContractID() > 0)
			m_sbWhere.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and LoanFormID=" + info.getPayFormID() + "");

		if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0) &&
		(info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0) &&
		(info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0) &&
		(info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0))
		{
			m_sbWhere.append(" and ((PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			m_sbWhere.append(" and PayAccountNo<='" + info.getPayAccountNoEnd() + "')");
			m_sbWhere.append(" or (ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			m_sbWhere.append(" and ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'))");
		}
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			m_sbWhere.append(" and (DepositNo='" + info.getDepositNo() + "' or NewDepositNo='" + info.getDepositNo() + "')");

		if (info.getExecuteStart() != null )
			m_sbWhere.append(" and Execute<=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd'");
		if (info.getExecuteEnd() != null )
			m_sbWhere.append(" and Execute>=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd'");

		
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";
		
		switch ((int) info.getOrderID())
		{
			default :
				m_sbOrderBy.append(" order by TransNo" + strDesc+" \n");
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
	public PageLoader queryTransactionInfo(QueryTransactionConditionInfo info) throws Exception
	{

		getTransactionSQL(info);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), m_sbFrom.toString(), m_sbSelect.toString(), m_sbWhere.toString(), (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo", null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

}