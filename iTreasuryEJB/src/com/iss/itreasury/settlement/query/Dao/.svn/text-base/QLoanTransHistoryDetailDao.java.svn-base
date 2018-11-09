package com.iss.itreasury.settlement.query.Dao;

import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;


/**
 * 贷款交易历史明细查询
 * @author songwenxiao
 *
 */
public class QLoanTransHistoryDetailDao {
	
	public final static int OrderBy_FormYear = 1;	
	public final static int OrderBy_FormNo = 2;	
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	
	public QLoanTransHistoryDetailDao()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	
	public String getLoanTransHistoryDetailSQL(QueryTransactionConditionInfo info)
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

		logger.debug(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
		String sql="select "+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString();
		return sql;
	}

}
