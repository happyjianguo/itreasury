/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import com.iss.itreasury.settlement.interest.dao.Sett_LoanNoticeDAO;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryLoanNoticeInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author xrli
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QLoanNoticeBook extends BaseQueryObject
{

	public final static int OrderBy_FormYear = 1;	
	public final static int OrderBy_FormNo = 2;	
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QLoanNoticeBook()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getLoanNoticeBookSQL(QueryLoanNoticeInfo qInfo)
	{
		m_sbSelect = new StringBuffer();
		// select		
		m_sbSelect.append("loanNotice.sContractCode as ContractNo, \n");
		m_sbSelect.append("loanNotice.sPayFormCode PayFormNo, \n");
		m_sbSelect.append("loanNotice.sFormYear as FormYear, \n");
		m_sbSelect.append("loanNotice.id as ID, \n");
		m_sbSelect.append("loanNotice.sFormCode as FormNo, \n");			
		m_sbSelect.append("loanNotice.nFormNo as FormNum,  \n");
		
		m_sbSelect.append("loanNotice.nAccountID as AccountID, \n");		
		m_sbSelect.append("loanNotice.nSubAccountID as SubAccountID, \n");		
		m_sbSelect.append("loanNotice.sAssureCodeCode as AssureContractNo, \n");		
		m_sbSelect.append("loanNotice.sBorrowClientName as BorrowClientName, \n");		
		m_sbSelect.append("loanNotice.sAssureClientName as AssureClientName, \n");
		m_sbSelect.append("loanNotice.nFornType as FormTypeID, \n");
		
		m_sbSelect.append("loanNotice.dtExecute as ExecuteDate, \n");			
		m_sbSelect.append("loanNotice.dtInterest as ClearInterestDate,  \n");
		m_sbSelect.append("loanNotice.mOpenAmount as LoanAmount, \n");
		m_sbSelect.append("loanNotice.dtStart as LoanStartDate, \n");
		m_sbSelect.append("loanNotice.dtEnd as LoanEndDate, \n");			
		m_sbSelect.append("loanNotice.nIntervalNum as LoanTerm,  \n");
		m_sbSelect.append("loanNotice.mContractInterestRate as OriginalContractRate, \n");
		m_sbSelect.append("loanNotice.mBalance as LoanBalance, \n");
		m_sbSelect.append("loanNotice.mExecuteInterestRate as ExecuteRate, \n");			
		m_sbSelect.append("loanNotice.mNewExecuteInterestRate as NewExecuteRate,  \n");
			
		m_sbSelect.append("loanNotice.dtInterestRateValid as ExecuteRateValidDate, \n");
		m_sbSelect.append("loanNotice.mInterest as Interest, \n");
		m_sbSelect.append("loanNotice.mSuretyFee as SuretyFee, \n");			
		m_sbSelect.append("loanNotice.mCompoundInterest as CompoundInterest,  \n");
		m_sbSelect.append("loanNotice.mCommissionRate as CommissionRate, \n");
		m_sbSelect.append("loanNotice.mCommission as Commission, \n");
		m_sbSelect.append("loanNotice.mOverCommissionRate as OverDueInterestRate, \n");			
		m_sbSelect.append("loanNotice.mOverDueInterest as OverDueInterest,  \n");
		m_sbSelect.append("loanNotice.mAllInterest as AllInterest, \n");
		m_sbSelect.append("loanNotice.mTotal as TotalInterest, \n");
		m_sbSelect.append("loanNotice.nStatusID as StatusID, \n");	
		m_sbSelect.append("loanNotice.dtInterestStart as InterestStartDate, \n");				
		m_sbSelect.append("contractform.ID as ContractID,  \n");
		m_sbSelect.append(" payform.mAmount AS PayFormAmount, \n");
		m_sbSelect.append("payform.ID as LoanNoteID  \n");
		
			
		// from 
		m_sbFrom = new StringBuffer();
		//m_sbFrom.append("sett_account acct, sett_subAccount subAcct, sett_TransAccountDetail acctDetail, client \n");
		//m_sbFrom.append("sett_LoanNotice loanNotice , client,sett_account acct\n");
		m_sbFrom.append(" sett_LoanNotice loanNotice,sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform,client \n");
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" loanNotice.nSubAccountID=subaccount.id  \n");
		m_sbWhere.append(" and subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID \n");		
		
		
		//m_sbWhere = new StringBuffer();		
		//m_sbWhere.append("loanNotice.nAccountID=acct.id and acct.nclientid=client.id  \n");
		m_sbWhere.append("and loanNotice.nofficeid="+ qInfo.getOfficeID() + " \n");
		m_sbWhere.append("and loanNotice.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		m_sbWhere.append("and loanNotice.nStatusID <> 0 \n");
		if (qInfo.getNoticeTypeID() >0)
				m_sbWhere.append("and loanNotice.nFornType=" + qInfo.getNoticeTypeID() + "");
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append("and client.scode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append("and client.scode<='" + qInfo.getClientNoTo() + "'");
		
		if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
			m_sbWhere.append("and account.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
			m_sbWhere.append("and account.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
				
		if (qInfo.getContractNoFrom() != null && qInfo.getContractNoFrom().length() > 0)
			m_sbWhere.append("and loanNotice.sContractCode>='" + qInfo.getContractNoFrom() + "'");
		if (qInfo.getContractNoTo() != null && qInfo.getContractNoTo().length() > 0)
			m_sbWhere.append("and loanNotice.sContractCode<='" + qInfo.getContractNoTo() + "'");
		if (qInfo.getPayFormNoFrom() != null && qInfo.getPayFormNoFrom().length() > 0)
			m_sbWhere.append("and loanNotice.sPayFormCode>='" + qInfo.getPayFormNoFrom() + "'");
		if (qInfo.getPayFormNoTo() != null && qInfo.getPayFormNoTo().length() > 0)
			m_sbWhere.append("and loanNotice.sPayFormCode<='" + qInfo.getPayFormNoTo() + "'");
			
		if (qInfo.getFormYearFrom() != null && qInfo.getFormYearFrom().length() > 0)
			m_sbWhere.append("and loanNotice.sFormYear>='" + qInfo.getFormYearFrom() + "'");
		if (qInfo.getFormYearTo() != null && qInfo.getFormYearTo().length() > 0)
			m_sbWhere.append("and loanNotice.sFormYear<='" + qInfo.getFormYearTo() + "'");
						
		if (qInfo.getNoticeNoFrom() != null && qInfo.getNoticeNoFrom().length() > 0)
		{			
			if (qInfo.getNoticeNoTo() != null && qInfo.getNoticeNoTo().length() > 0)
			{
				if (qInfo.getFormYearFrom() != null && qInfo.getFormYearFrom().length() > 0)
				{
					m_sbWhere.append("and ((loanNotice.sFormYear='" + qInfo.getFormYearFrom() + "'");
					m_sbWhere.append("and loanNotice.sFormCode>='" + qInfo.getNoticeNoFrom() + "')");				
					m_sbWhere.append("or (loanNotice.sFormYear='" + qInfo.getFormYearTo() + "'");
					m_sbWhere.append("and loanNotice.sFormCode<='" + qInfo.getNoticeNoTo() + "'))");
				}
				else
				{
					m_sbWhere.append("and loanNotice.sFormCode>='" + qInfo.getNoticeNoFrom() + "'");				
				}
			}
			else
			{									
				m_sbWhere.append("and loanNotice.sFormCode>='" + qInfo.getNoticeNoFrom() + "'");				
			}				
		}			
		if (qInfo.getNoticeNoTo() != null && qInfo.getNoticeNoTo().length() > 0)
		{
			if (qInfo.getNoticeNoFrom() != null && qInfo.getNoticeNoFrom().length() > 0)
			{
				if (qInfo.getFormYearTo() != null && qInfo.getFormYearTo().length() > 0)
				{
					m_sbWhere.append("and ((loanNotice.sFormYear='" + qInfo.getFormYearFrom() + "'");
					m_sbWhere.append("and loanNotice.sFormCode>='" + qInfo.getNoticeNoFrom() + "')");				
					m_sbWhere.append("or (loanNotice.sFormYear='" + qInfo.getFormYearTo() + "'");
					m_sbWhere.append("and loanNotice.sFormCode<='" + qInfo.getNoticeNoTo() + "'))");
				}
				else
				{
					m_sbWhere.append("and loanNotice.sFormCode<='" + qInfo.getNoticeNoTo() + "'");				
				}
			}
			else
			{
									
				m_sbWhere.append("and loanNotice.sFormCode<='" + qInfo.getNoticeNoTo() + "'");
				
			}			
		}				
		if (qInfo.getFormNumFrom() >0)
			m_sbWhere.append("and loanNotice.nFormNo>=" + qInfo.getFormNumFrom() + "");			
		if (qInfo.getFormNumTo() >0)
				m_sbWhere.append("and loanNotice.nFormNo<=" + qInfo.getFormNumTo() + "");
		
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_FormYear :
				m_sbOrderBy.append(" \n order by loanNotice.sFormYear" + strDesc);
				break;			
			case OrderBy_FormNo :
				m_sbOrderBy.append(" \n order by loanNotice.sFormCode" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by loanNotice.sFormYear,loanNotice.sFormCode");
				break;					
		}
		logger.debug(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
	}
	/*
	 * É¾³ýÍ¨ÖªÊé
	 */
	public void delete(LoanNoticeInfo info) throws  Exception
	{
		Sett_LoanNoticeDAO dao = new Sett_LoanNoticeDAO();
		long lFlag =dao.updateStatus(null,info.getID(),0);		
	}
	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryLoanNoticeInfo(QueryLoanNoticeInfo qInfo) throws Exception
	{

		getLoanNoticeBookSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

}