/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code 
Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dao.Sett_LoanNoticeDAO;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.FixedDepositAccountPayableInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
/**
 * @author xrli
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - 
Code Generation - Code
 * and Comments
 */
public class QLoanNotice extends BaseQueryObject
{

	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	int iii = 1;
	/**
	 *  
	 */
	public QLoanNotice()
	{
		super();		
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}
	
	/**
	 * 查询贷款通知书
	 * @param qInfo
	 */
	public void getLoanNoticeSQL(InterestEstimateQueryInfo qInfo)
	{
		m_sbSelect = new StringBuffer();
		
		
		m_sbSelect.append(" distinct account.ID AS accountID, \n");  
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");                      
		m_sbSelect.append(" account.nOfficeID AS OfficeID, \n");
		m_sbSelect.append(" account.nCurrencyID AS CurrencyID, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect.append(" subaccount.dtclearinterest as ClearInterestDate, \n");
		m_sbSelect.append(" subaccount.ID AS subAccountID, \n");
		m_sbSelect.append(" subaccount.AL_NLOANNOTEID AS LoanNoteID, \n");		
		m_sbSelect.append(" contractform.ID AS ContractID, \n");
		m_sbSelect.append(" contractform.sContractCode AS contractNo, \n");
		m_sbSelect.append(" contractform.nBorrowClientID AS BorrowClientID, \n");
		m_sbSelect.append(" contractform.mLoanAmount AS LoanAmount, \n");
		m_sbSelect.append(" contractform.nTypeID AS LoanTypeID,\n"); //贷款类型
		//m_sbSelect.append(" contractform.dtStartDate AS LoanStartDate, \n");
		//m_sbSelect.append(" contractform.dtEndDate AS LoanEndDate, \n");		
		m_sbSelect.append(" payform.mAmount AS PayFormAmount, \n");
		m_sbSelect.append(" payform.dtStart AS LoanStartDate, \n");
		m_sbSelect.append(" payform.dtEnd AS LoanEndDate, \n");
		m_sbSelect.append(" contractform.nInterValNum as LoanTerm, \n");				
		m_sbSelect.append(" payform.sCode AS payFormNo \n");								
		// from 
		m_sbFrom = new StringBuffer();		
		m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client,client consigner \n");
		// where 
		m_sbWhere = new StringBuffer();
		
		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+) \n");
		if(qInfo.getIsSelectConsigner()>0)
		{
			
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" contractform.nConsignClientID = consigner.ID \n");		
						
		}			
		
		m_sbWhere.append(" AND account.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" and subaccount.dtClearInterest is not null \n");
		if(qInfo.getOfficeID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if(qInfo.getCurrencyID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}	
		if(qInfo.getIsSelectClientNo()>0)
		{		
			if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append(" and client.scode>='" + qInfo.getClientNoFrom() + "'");
			if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append(" and client.scode<='" + qInfo.getClientNoTo() + "'");
		}	
		if(qInfo.getIsSelectClearInterestDate()>0)
		{
			if(qInfo.getClearInterestDate() != null)
			{                                    //结息日				
				m_sbWhere.append(" and subaccount.dtClearInterest<= to_date('"+DataFormat.formatDate(qInfo.getClearInterestDate())+"','yyyy-mm-dd')");
			}	    	
		}
		if(qInfo.getIsSelectSelfLoanSort()>0)
		{
			if(qInfo.getSelfLoanSort() >0)
			{				
				//m_sbWhere.append(" and loanform.nTypeID<=" + qInfo.getSelfLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='" + qInfo.getSelfLoanSort() + "' \n");
			}
			if(qInfo.getSelfLoanSort()==0)
			{
				m_sbWhere.append("and contractform.nTypeID in (1,2,5,6,7,8) ");
			}
		}
		if(qInfo.getIsSelectSelfLoanTerm()>0)
		{
			if(qInfo.getSelfLoanTermFrom() >0)
			{				
				m_sbWhere.append(" and contractform.nIntervalNum >=" + qInfo.getSelfLoanTermFrom() + "");
			}
			if(qInfo.getSelfLoanTermTo() >0)
			{				
				m_sbWhere.append(" and contractform.nIntervalNum <=" + qInfo.getSelfLoanTermTo() + "");				
			}
			// add by kevin(刘连凯)2011-08-01 当选择的自营贷款期限时，合同类型应默认是自营
			m_sbWhere.append("and contractform.ntypeid ="+LOANConstant.LoanType.ZY+" \n"); 
		}
		if(qInfo.getIsSelectConsignLoanSort()>0)
		{
			if(qInfo.getConsignLoanSort() >0)
			{				
				//m_sbWhere.append(" and loanform.nTypeID<=" + qInfo.getConsignLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='" + qInfo.getConsignLoanSort() + "' \n");
			}
			if(qInfo.getConsignLoanSort()==0)
			{
				m_sbWhere.append("and contractform.nTypeID in (3,4) ");
			}
		}
		if(qInfo.getIsSelectConsigner()>0)
		{
			if (qInfo.getConsignerFrom() != null && qInfo.getConsignerFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and consigner.scode>='" + qInfo.getConsignerFrom() + "'");				
			}
			if (qInfo.getConsignerTo() != null && qInfo.getConsignerTo().length() > 0)
			{                                    
				m_sbWhere.append(" and consigner.scode<='" + qInfo.getConsignerTo() + "'");				
			}						
		}
		if(qInfo.getIsSelectContractNo()>0)
		{
			if (qInfo.getContractNoFrom() != null && qInfo.getContractNoFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and contractform.sContractCode>='" + qInfo.getContractNoFrom() + "'");				
			}
			if (qInfo.getContractNoTo() != null && qInfo.getContractNoTo().length() > 0)
			{                                    
				m_sbWhere.append(" and contractform.sContractCode<='" + qInfo.getContractNoTo() + "'");				
			}			
		}
		if(qInfo.getIsSelectPayFormNo()>0)
		{
			if (qInfo.getPayFormNoFrom() != null && qInfo.getPayFormNoFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and payform.sCode>='" + qInfo.getPayFormNoFrom() + "'");				
			}
			if (qInfo.getPayFormNoTo() != null && qInfo.getPayFormNoTo().length() > 0)
			{                                    
				m_sbWhere.append(" and payform.sCode<='" + qInfo.getPayFormNoTo() + "'");				
			}			
		}
		m_sbOrderBy = new StringBuffer();
		String strDesc = " desc ";
				
		log.debug("select " + m_sbSelect.toString()+"from "+m_sbFrom.toString()+"where "+m_sbWhere.toString());
	}
	/**
	 * 查询客户应收贷款利息通知书
	 * @param qInfo
	 */
	public void getLoanNoticeYsClientSQL(InterestEstimateQueryInfo qInfo,Vector resultVec)
	{
		m_sbSelect = new StringBuffer();
		
		
		m_sbSelect.append(" distinct account.ID AS accountID, \n");  
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");                      
		m_sbSelect.append(" account.nOfficeID AS OfficeID, \n");
		m_sbSelect.append(" account.nCurrencyID AS CurrencyID, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect.append(" subaccount.dtclearinterest as ClearInterestDate, \n");
		m_sbSelect.append(" subaccount.ID AS subAccountID, \n");
		m_sbSelect.append(" subaccount.AL_NLOANNOTEID AS LoanNoteID, \n");		
		m_sbSelect.append(" contractform.ID AS ContractID, \n");
		m_sbSelect.append(" contractform.sContractCode AS contractNo, \n");
		m_sbSelect.append(" contractform.nBorrowClientID AS BorrowClientID, \n");
		m_sbSelect.append(" contractform.mLoanAmount AS LoanAmount, \n");
		m_sbSelect.append(" contractform.nTypeID AS LoanTypeID,\n"); //贷款类型
		//m_sbSelect.append(" contractform.dtStartDate AS LoanStartDate, \n");
		//m_sbSelect.append(" contractform.dtEndDate AS LoanEndDate, \n");
		m_sbSelect.append(" payform.dtStart AS LoanStartDate, \n");
		m_sbSelect.append(" payform.dtEnd AS LoanEndDate, \n");
		m_sbSelect.append(" payform.mAmount AS PayFormAmount, \n");
		m_sbSelect.append(" contractform.nInterValNum as LoanTerm, \n");		
		m_sbSelect.append(" payform.sCode AS payFormNo \n");										
		// from 
		m_sbFrom = new StringBuffer();		
		//m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client \n");
		m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client,client consigner \n");
		// where 
		m_sbWhere = new StringBuffer();
		
		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if(qInfo.getIsSelectConsigner()>0)
		{			
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" contractform.nConsignClientID = consigner.ID \n");		
						
		}
		m_sbWhere.append(" AND account.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		if(qInfo.getOfficeID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if(qInfo.getCurrencyID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append(" and client.scode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append(" and client.scode<='" + qInfo.getClientNoTo() + "'");
		
		if(qInfo.getClearInterestDate() != null)
		{                                    //结息日				
			m_sbWhere.append(" and subaccount.dtClearInterest<= to_date('"+DataFormat.formatDate(qInfo.getClearInterestDate())+"','yyyy-mm-dd')");
		}
		if(qInfo.getIsSelectSelfLoanSort()>0)
		{
			if(qInfo.getSelfLoanSort() >0)
			{				
				//m_sbWhere.append(" and loanform.nTypeID<=" + qInfo.getSelfLoanSort() + "");
				//m_sbWhere.append(" and contractform.nTypeID='" + qInfo.getSelfLoanSort() + "' \n");
				//modify by xwhe 查询的条件应该取合同的贷款子类型，不应该取贷款类型
				m_sbWhere.append(" and contractform.nsubtypeid='" + qInfo.getSelfLoanSort() + "' \n");
			}
			if(qInfo.getSelfLoanSort()==0)
			{
				m_sbWhere.append("and contractform.nTypeID in (1,2,5,6,7,8) ");
			}
		}
		if(qInfo.getIsSelectSelfLoanTerm()>0)
		{
			if(qInfo.getSelfLoanTermFrom() >0)
			{				
				m_sbWhere.append(" and contractform.nIntervalNum >=" + qInfo.getSelfLoanTermFrom() + "");
			}
			if(qInfo.getSelfLoanTermTo() >0)
			{				
				m_sbWhere.append(" and contractform.nIntervalNum <=" + qInfo.getSelfLoanTermTo() + "");				
			}
		}
		if(qInfo.getIsSelectConsignLoanSort()>0)
		{
			if(qInfo.getConsignLoanSort() >0)
			{				
				//m_sbWhere.append(" and loanform.nTypeID<=" + qInfo.getConsignLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='" + qInfo.getConsignLoanSort() + "' \n");				
			}
			if(qInfo.getConsignLoanSort()==0)
			{
				m_sbWhere.append("and contractform.nTypeID in (3,4) ");
			}
		}
		if(qInfo.getIsSelectConsigner()>0)
		{
			if (qInfo.getConsignerFrom() != null && qInfo.getConsignerFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and consigner.scode>='" + qInfo.getConsignerFrom() + "'");				
			}
			if (qInfo.getConsignerTo() != null && qInfo.getConsignerTo().length() > 0)
			{                                    
				m_sbWhere.append(" and consigner.scode<='" + qInfo.getConsignerTo() + "'");				
			}						
		}
		if(qInfo.getIsSelectContractNo()>0)
		{
			if (qInfo.getContractNoFrom() != null && qInfo.getContractNoFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and contractform.sContractCode>='" + qInfo.getContractNoFrom() + "'");				
			}
			if (qInfo.getContractNoTo() != null && qInfo.getContractNoTo().length() > 0)
			{                                    
				m_sbWhere.append(" and contractform.sContractCode<='" + qInfo.getContractNoTo() + "'");				
			}			
		}
		if(qInfo.getIsSelectPayFormNo()>0)
		{
			if (qInfo.getPayFormNoFrom() != null && qInfo.getPayFormNoFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and payform.sCode>='" + qInfo.getPayFormNoFrom() + "'");				
			}
			if (qInfo.getPayFormNoTo() != null && qInfo.getPayFormNoTo().length() > 0)
			{                                    
				m_sbWhere.append(" and payform.sCode<='" + qInfo.getPayFormNoTo() + "'");				
			}			
		}		
		if(resultVec!=null && resultVec.size()!=0)
		{
			for(int i=0;i<resultVec.size();i++)
			{
				InterestEstimateQueryResultInfo resultInfo = (InterestEstimateQueryResultInfo)resultVec.elementAt(i);
				if(resultVec.size()>1)
				{
					if(i==0)
					{				
						m_sbWhere.append(" and (client.id = " + resultInfo.getClientID() + "  \n");
					}
					else if(i==resultVec.size()-1)
					{
						m_sbWhere.append(" or client.id = " + resultInfo.getClientID() + ")  \n");
					}
					else
					{
						m_sbWhere.append(" or client.id = " + resultInfo.getClientID() + "  \n");
					}
				}
				else
				{
					m_sbWhere.append(" and client.id = " + resultInfo.getClientID() + "  \n");
				}
								
			}
		}		
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by contractNo,payFormNo");
		String strDesc = " desc ";
				
		log.debug("select " + m_sbSelect.toString()+"from "+m_sbFrom.toString()+"where "+m_sbWhere.toString());
	}
	/**
	 * 查询客户应收贷款利息通知书
	 * @param qInfo
	 */
	public void getLoanNoticeYsAccountSQL(InterestEstimateQueryInfo qInfo,Vector resultVec)
	{
		m_sbSelect = new StringBuffer();
		
		
		m_sbSelect.append(" distinct account.ID AS accountID, \n");  
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");                      
		m_sbSelect.append(" account.nOfficeID AS OfficeID, \n");
		m_sbSelect.append(" account.nCurrencyID AS CurrencyID, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect.append(" subaccount.dtclearinterest as ClearInterestDate, \n");
		m_sbSelect.append(" subaccount.ID AS subAccountID, \n");
		m_sbSelect.append(" subaccount.AL_NLOANNOTEID AS LoanNoteID, \n");		
		m_sbSelect.append(" contractform.ID AS ContractID, \n");
		m_sbSelect.append(" contractform.sContractCode AS contractNo, \n");
		m_sbSelect.append(" contractform.nBorrowClientID AS BorrowClientID, \n");
		m_sbSelect.append(" contractform.mLoanAmount AS LoanAmount, \n");
		m_sbSelect.append(" contractform.nTypeID AS LoanTypeID,\n"); //贷款类型
		//m_sbSelect.append(" contractform.dtStartDate AS LoanStartDate, \n");
		//m_sbSelect.append(" contractform.dtEndDate AS LoanEndDate, \n");
		m_sbSelect.append(" payform.dtStart AS LoanStartDate, \n");
		m_sbSelect.append(" payform.dtEnd AS LoanEndDate, \n");
		m_sbSelect.append(" payform.mAmount AS PayFormAmount, \n");
		m_sbSelect.append(" contractform.nInterValNum as LoanTerm, \n");		
		m_sbSelect.append(" payform.sCode AS payFormNo \n");											
		// from 
		m_sbFrom = new StringBuffer();		
		//m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client \n");
		m_sbFrom.append(" sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client,client consigner \n");
		// where 
		m_sbWhere = new StringBuffer();
		
		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if(qInfo.getIsSelectConsigner()>0)
		{
			
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" contractform.nConsignClientID = consigner.ID \n");		
						
		}
		m_sbWhere.append(" AND account.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = " + SETTConstant.AccountStatus.NORMAL);
		if(qInfo.getOfficeID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if(qInfo.getCurrencyID()>0)
		{
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append(" and client.scode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append(" and client.scode<='" + qInfo.getClientNoTo() + "'");
		
		if(qInfo.getClearInterestDate() != null)
		{                                    //结息日				
			m_sbWhere.append(" and subaccount.dtClearInterest<= to_date('"+DataFormat.formatDate(qInfo.getClearInterestDate())+"','yyyy-mm-dd')");
		}
		if(qInfo.getIsSelectSelfLoanSort()>0)
		{
			if(qInfo.getSelfLoanSort() >0)
			{				
				//m_sbWhere.append(" and loanform.nTypeID<=" + qInfo.getSelfLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='" + qInfo.getSelfLoanSort() + "' \n");
			}
			if(qInfo.getSelfLoanSort()==0)
			{
				m_sbWhere.append("and contractform.nTypeID in (1,2,5,6,7,8) ");
			}
		}
		if(qInfo.getIsSelectSelfLoanTerm()>0)
		{
			if(qInfo.getSelfLoanTermFrom() >0)
			{				
				m_sbWhere.append(" and contractform.nIntervalNum >=" + qInfo.getSelfLoanTermFrom() + "");
			}
			if(qInfo.getSelfLoanTermTo() >0)
			{				
				m_sbWhere.append(" and contractform.nIntervalNum <=" + qInfo.getSelfLoanTermTo() + "");				
			}
		}
		if(qInfo.getIsSelectConsignLoanSort()>0)
		{
			if(qInfo.getConsignLoanSort() >0)
			{				
				//m_sbWhere.append(" and loanform.nTypeID<=" + qInfo.getConsignLoanSort() + "");
				m_sbWhere.append(" and contractform.nTypeID='" + qInfo.getConsignLoanSort() + "' \n");
			}
			if(qInfo.getConsignLoanSort()==0)
			{
				m_sbWhere.append("and contractform.nTypeID in (3,4) ");
			}
		}
		if(qInfo.getIsSelectConsigner()>0)
		{
			if (qInfo.getConsignerFrom() != null && qInfo.getConsignerFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and consigner.scode>='" + qInfo.getConsignerFrom() + "'");				
			}
			if (qInfo.getConsignerTo() != null && qInfo.getConsignerTo().length() > 0)
			{                                    
				m_sbWhere.append(" and consigner.scode<='" + qInfo.getConsignerTo() + "'");				
			}						
		}
		if(qInfo.getIsSelectContractNo()>0)
		{
			if (qInfo.getContractNoFrom() != null && qInfo.getContractNoFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and contractform.sContractCode>='" + qInfo.getContractNoFrom() + "'");				
			}
			if (qInfo.getContractNoTo() != null && qInfo.getContractNoTo().length() > 0)
			{                                    
				m_sbWhere.append(" and contractform.sContractCode<='" + qInfo.getContractNoTo() + "'");				
			}			
		}
		if(qInfo.getIsSelectPayFormNo()>0)
		{
			if (qInfo.getPayFormNoFrom() != null && qInfo.getPayFormNoFrom().length() > 0)
			{                                    
				m_sbWhere.append(" and payform.sCode>='" + qInfo.getPayFormNoFrom() + "'");				
			}
			if (qInfo.getPayFormNoTo() != null && qInfo.getPayFormNoTo().length() > 0)
			{                                    
				m_sbWhere.append(" and payform.sCode<='" + qInfo.getPayFormNoTo() + "'");				
			}			
		}
		if(resultVec!=null && resultVec.size()!=0)
		{
			for(int i=0;i<resultVec.size();i++)
			{
				InterestEstimateQueryResultInfo resultInfo = (InterestEstimateQueryResultInfo)resultVec.elementAt(i);
				if(resultVec.size()>1)
				{					
					if(i==0)
					{				
						m_sbWhere.append(" and (account.id = " + resultInfo.getAccountID() + "  \n");
					}
					else if(i==resultVec.size()-1)
					{
						m_sbWhere.append(" or account.id = " + resultInfo.getAccountID() + ")  \n");
					}
					else
					{
						m_sbWhere.append(" or account.id = " + resultInfo.getAccountID() + "  \n");
					}
				}
				else
				{
					m_sbWhere.append(" and account.id = " + resultInfo.getAccountID() + "  \n");
				}				
			}
		}
				
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by contractNo,payFormNo");
		String strDesc = " desc ";
				
		log.debug("select " + m_sbSelect.toString()+"from "+m_sbFrom.toString()+"where "+m_sbWhere.toString());
	}
	/**
	 * @return Vector
	 * @param resultVec 查出的结果集	
	 * @throws Exception
	 */
//	根据查询信息返回利息信息
	  public Vector queryNoticeInterest(Connection con,Vector resultVec,InterestEstimateQueryInfo queryInfo) throws Exception
	  {
		  Vector rtnVec = new Vector();
		  Connection conInternal = null;
		  if (con == null)
		  {
			  try
			  {
				  conInternal = getConnection();
				  conInternal.setAutoCommit(false);
			  }
			  catch (Exception e)
			  {
				  e.printStackTrace();
				  e.printStackTrace();throw new IException("无法取得数据库连接");
			  }
		  }
		  else
		  {
	
			  conInternal = con;
			  conInternal.setAutoCommit(false);
		  }		  
			
		 try
			{
				InterestOperation io = new InterestOperation(conInternal);
				InterestBatch ib = new InterestBatch(conInternal);	
				sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);				
				Sett_LoanNoticeDAO loanNoticeDao= new Sett_LoanNoticeDAO();
				//通知书编号
				String strNo = "";
				if(queryInfo.getNoticetype()==4)
				{
					strNo = loanNoticeDao.getMaxFormNo(conInternal,String.valueOf(queryInfo.getClearInterestDate().getYear()+1900),SETTConstant.LoanNoticeType.LoanInterestNotice);
				}
				else
				{
					strNo = loanNoticeDao.getMaxFormNo(conInternal,String.valueOf(queryInfo.getClearInterestDate().getYear()+1900),queryInfo.getNoticetype());	
				}
				 long firstContractID = -1;
				 long  lFirstNo =Integer.valueOf(strNo).intValue();
				 for(int i=0;i<resultVec.size();i++)
				  {
				  		LoanNoticeInfo info = new LoanNoticeInfo();
						info = (LoanNoticeInfo)resultVec.elementAt(i);
						if(i==0)
						{
							firstContractID = info.getContractID();							
						}
						log.info("----------得到利息信息开始-------------");						
						//到期贷款通知书
						if(queryInfo.getNoticetype()==SETTConstant.LoanNoticeType.LoanMatureNotice)
						{							
							queryInfo.setClearInterestDate(info.getLoanEndDate());
							strNo = loanNoticeDao.getMaxFormNo(conInternal,String.valueOf(queryInfo.getClearInterestDate().getYear()+1900),queryInfo.getNoticetype());
						}					
						info = getInterestInfo(queryInfo,info,io,ib,conInternal,transDetailDAO);
						
						if(info.getLoanTypeID()==LOANConstant.LoanType.YT)
						{
							BankLoanQuery bankLoanQuery =new BankLoanQuery();
							//承贷比例
							log.info("--------银团贷款操作——将利息数据按承贷比例转换");
							double dRate = 0.0;
							try {				
								dRate=bankLoanQuery.findRateByFormID(info.getLoanNoteID());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}					
							
							if(dRate>0)
							{								
								info.setInterest(info.getInterest()/dRate*100);
							}	
						}
						log.info("----------得到利息信息结束-------------");
						log.info("-----------得到贷款合同利率余额的信息开始------");
						ContractAmountInfo contractAmountInfo = new ContractAmountInfo();
						contractAmountInfo =getLateAmount(conInternal,info.getContractID());
						
						RateInfo rateInfo = new RateInfo();
						rateInfo = getLatelyRate(conInternal,-1,info.getContractID(),queryInfo.getClearInterestDate());
						//合同余额
						info.setLoanBalance(contractAmountInfo.getBalanceAmount());
						//原合同利率
						info.setOriginalContractRate(rateInfo.getRate());
						//调整后合同利率
						info.setNewExecuteRate(rateInfo.getLateRate());
						//执行利率
						info.setExecuteRate(rateInfo.getRate());
						//调整后执行利率
						info.setNewExecuteRate(rateInfo.getLateRate());						
						//调整日期
						info.setExecuteRateValidDate(rateInfo.getAdjustDate());
						
						//担保客户另外实现
						//担保合同号暂放
						//通知书年度							
						info.setFormYear(String.valueOf(queryInfo.getClearInterestDate().getYear()+1900));						
						
						//通知书类型
						info.setFormTypeID(queryInfo.getNoticetype());
						info.setClearInterestDate(queryInfo.getClearInterestDate());
						//通知书编号
						//到期贷款通知书
						if(queryInfo.getNoticetype()==SETTConstant.LoanNoticeType.LoanMatureNotice)
						{
							int k= Integer.valueOf(strNo).intValue();
							String strReturn = DataFormat.formatInt(k, 4);
							info.setFormNo(strReturn);
						}
						else if(queryInfo.getNoticetype()==SETTConstant.LoanNoticeType.LoanDunNotice)
						{					
							int k= Integer.valueOf(strNo).intValue()+i;
							String strReturn = DataFormat.formatInt(k, 4);
							info.setFormNo(strReturn);
						}
						else
						{
							//应付利息通知书															
							if(firstContractID!=info.getContractID())
							{
								String strReturn = DataFormat.formatInt(lFirstNo+1, 4);
								info.setFormNo(strReturn);	
								firstContractID=info.getContractID();
								lFirstNo = lFirstNo+1;
							}
							else
							{
								String strReturn = DataFormat.formatInt(lFirstNo, 4);
								info.setFormNo(strReturn);	
							}
													
						}
						if(queryInfo.getNoticetype()==SETTConstant.LoanNoticeType.LoanDunNotice)
						{						
							//催收次数
							info.setFormNum(loanNoticeDao.getMaxFormNum(conInternal,info.getPayFormNo()));
						}
						log.info("-----------得到贷款合同利率的信息结束------");
						rtnVec.addElement(info);
					    log.info("\n\n\n~~~~~~~~~~in queryNoticeInterest~~~~~~~\n\n\n");
						
				  }	
			}
			catch (Exception e)
			{				
				e.printStackTrace();
				throw e;				
			}
			finally
			{
		
				try
				{					
					if (con == null)
					{
						conInternal.close();			
					}
				}
				catch (Exception e)
				{
					logger.error(e.toString());
					e.printStackTrace();throw new IException("Gen_E001");
				}
			}
		  return rtnVec;		
	  }	
	/**
	 * 算出一条记录的利息
	 * @param queryInfo
	 * @param con
	 * @param rs
	 * @param io
	 * @param ib
	 * @param transDetailDAO
	 * @return
	 * @throws SQLException
	 * @throws IException
	 * @throws Exception
	 */	
	private LoanNoticeInfo getInterestInfo(
		InterestEstimateQueryInfo queryInfo,
		LoanNoticeInfo resultInfo,		
		InterestOperation io,
		InterestBatch ib,
		Connection con,
		sett_TransAccountDetailDAO transDetailDAO)
		throws SQLException, IException, Exception
	{
		
		/*
		 * 如果不过滤，并且“放款通知单”的结束日期存在（贷款），并且“放款通知单”的结束日期在选择的匡算日期之前 将匡算日改为结束日。
		 * 
		 */
		if (queryInfo.getDoFilterDate() != 1
				&& // 是否过滤 1 过滤 -1 不过滤
				resultInfo.getLoanEndDate() != null
				&& resultInfo.getLoanEndDate().before(
						queryInfo.getClearInterestDate())) {
			resultInfo.setClearInterestDate(resultInfo.getLoanEndDate());

		} else {
			resultInfo.setClearInterestDate(queryInfo.getClearInterestDate());
		}		
		
		
		if(SETTConstant.AccountType.isFixAccountType(resultInfo.getAccountTypeID())
		        ||SETTConstant.AccountType.isNotifyAccountType(resultInfo.getAccountTypeID()))
		{
			log.info("lhj debug info ===进入定期算息=======");
			FixedDepositAccountPayableInterestInfo fixedInterestInfo =
				new FixedDepositAccountPayableInterestInfo();
			if (queryInfo.getIsSelectInterest()>0)
			{
				log.info("--------------开始定期算息------------");
				fixedInterestInfo =
					io.getFixedDepositAccountPayableInterest(
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						resultInfo.getClearInterestDate());
				log.info("--------------结束定期算息------------");
				
				resultInfo.setInterest(fixedInterestInfo.getInterest());											
			}
			log.info("lhj debug info ===结束定期算息=======");
		}				
		else if(SETTConstant.AccountType.isCurrentAccountType(resultInfo.getAccountTypeID())
		        ||SETTConstant.AccountType.isOtherDepositAccountType(resultInfo.getAccountTypeID()))
		{
			log.info("lhj debug info ===进入活期算息=======");
			CurrentDepositAccountInterestInfo currInterestInfo =
				new CurrentDepositAccountInterestInfo();
		
			Collection coll = null;
			log.info("-------------判断是否需要单户倒填---------");
			coll =
				transDetailDAO.findByIsBackward(					
					resultInfo.getAccountID(),
					resultInfo.getSubAccountID(),
					queryInfo.getCurrencyID(),
					queryInfo.getOfficeID(),
					queryInfo.getClearInterestDate());
		
			Iterator itResult = null;
			if (coll != null && coll.size() > 0)
			{
				itResult = coll.iterator();
				if (itResult != null && itResult.hasNext())
				{
					while (itResult.hasNext())
					{
						TransAccountDetailInfo detailInfo =
							(TransAccountDetailInfo) itResult.next();
						
						log.info("-------------开始单户算息倒填---------");
						long flag =
								ib.accountInterestCalculationBackward(
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								detailInfo.getDtInterestStart(),
								detailInfo.getAmount(),
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								Env.getSystemDate(con,
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID()),SETTConstant.BooleanValue.ISFALSE);
						if (flag < 0)
						{
							throw new IException("单户倒填失败");
						}
						log.info("-------------单户倒填结束---------");
					}
				}
			}
			log.info("-------------判断是否需要单户倒填结束---------");
			log.info("-------------算息开始---------");
			currInterestInfo =
				io.getCurrentDepositAccountInterest(
					queryInfo.getOfficeID(),
					queryInfo.getCurrencyID(),
					resultInfo.getSubAccountID(),
					queryInfo.getClearInterestDate(),
					Env.getSystemDate(queryInfo.getOfficeID(), queryInfo.getCurrencyID()));			
						
			resultInfo.setInterest(currInterestInfo.getNormalInterest());									
			
			//结息日					
			log.info("-------------算息结束---------");
			
			log.info("lhj debug info ===结束活期算息=======");
		}
		else if (SETTConstant.AccountType.isLoanAccountType(resultInfo.getAccountTypeID()))//贷款
		{
			log.info("lhj debug info ===进入贷款算息=======");
			LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
		
			if (queryInfo.getIsSelectInterest()>0)
			{
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll =
					transDetailDAO.findByIsBackward(						
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(),
						queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0)
				{
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext())
					{
						while (itResult.hasNext())
						{
							TransAccountDetailInfo detailInfo =
								(TransAccountDetailInfo) itResult.next();
							log.info("-------------开始单户算息倒填---------");
							long flag =
								ib.accountInterestCalculationBackward(
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								detailInfo.getDtInterestStart(),
								detailInfo.getAmount(),
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								Env.getSystemDate(con,
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID()),SETTConstant.BooleanValue.ISFALSE);
							if (flag < 0)
							{
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				log.info("-------------判断是否需要单户倒填结束---------");
		
				//利息
				log.info("-------------算息开始---------");
				log.info("-----打印---结息日期（查询条件）-----"+queryInfo.getClearInterestDate()+"--------"+(iii)+"--------");
				log.info("-----打印---算息日期（放款通知单结束日期）-----"+resultInfo.getClearInterestDate()+"--------"+(iii)+"--------");
				loanInterestInfo =
					io.GetLoanAccountInterest(
						queryInfo.getOfficeID(),
						queryInfo.getCurrencyID(),
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						resultInfo.getClearInterestDate(),
						Env.getSystemDate(
							queryInfo.getOfficeID(),
							queryInfo.getCurrencyID()));
				log.info("-------------算息结束---------");
				
				//利息
				resultInfo.setInterest(loanInterestInfo.getInterest());
				log.info("-----计算第"+(iii)+"次利息--------"+loanInterestInfo.getInterest()+"----------------");
				iii++;
				//起息日
				resultInfo.setInterestStartDate(loanInterestInfo.getSDate());					
								
			}
			if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
			{
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll =
					transDetailDAO.findByIsBackward(											
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(),
						queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0)
				{
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext())
					{
						while (itResult.hasNext())
						{
							TransAccountDetailInfo detailInfo =
								(TransAccountDetailInfo) itResult.next();
							log.info("-------------开始单户倒填---------");
							long flag =
								ib.accountInterestSettlelmentBackward(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									detailInfo.getDtInterestStart(),
									detailInfo.getAmount(),
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID(),
									Env.getSystemDate(
										queryInfo.getOfficeID(),
										queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
							if (flag < 0)
							{
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				loanInterestInfo =
					io.getLoanAccountFee(
						queryInfo.getOfficeID(),
						queryInfo.getCurrencyID(),
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getClearInterestDate(),
						Env.getSystemDate(
							queryInfo.getOfficeID(),
							queryInfo.getCurrencyID()),
						SETTConstant.InterestFeeType.COMMISION);
				//手续费						
				resultInfo.setCommission(loanInterestInfo.getInterest());
				resultInfo.setCommissionRate(loanInterestInfo.getRate());	
				//起息日
				resultInfo.setCommissionStartDate(loanInterestInfo.getSDate());	
								
			}
			else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
			{
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll =
					transDetailDAO.findByIsBackward(						
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(),
						queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0)
				{
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext())
					{
						while (itResult.hasNext())
						{
							TransAccountDetailInfo detailInfo =
								(TransAccountDetailInfo) itResult.next();
							log.info("-------------开始单户算息倒填---------");
							long flag =
								ib.accountInterestCalculationBackward(
								resultInfo.getAccountID(),
								resultInfo.getSubAccountID(),
								detailInfo.getDtInterestStart(),
								detailInfo.getAmount(),
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(),
								Env.getSystemDate(con,
								queryInfo.getOfficeID(),
								queryInfo.getCurrencyID()),SETTConstant.BooleanValue.ISFALSE);
							if (flag < 0)
							{
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				loanInterestInfo =
					io.getLoanAccountFee(
						queryInfo.getOfficeID(),
						queryInfo.getCurrencyID(),
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getClearInterestDate(),
						Env.getSystemDate(
							queryInfo.getOfficeID(),
							queryInfo.getCurrencyID()),
						SETTConstant.InterestFeeType.ASSURE);
				
				//担保费
				resultInfo.setSuretyFee(loanInterestInfo.getInterest());
				//起息日
				resultInfo.setSuretyFeeStartDate(loanInterestInfo.getSDate());	
			}
			if (queryInfo.getIsSelectCompoundInterest()>0)
			{
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll =
					transDetailDAO.findByIsBackward(						
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(),
						queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0)
				{
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext())
					{
						while (itResult.hasNext())
						{
							TransAccountDetailInfo detailInfo =
								(TransAccountDetailInfo) itResult.next();
							log.info("-------------开始单户倒填---------");
							long flag =
								ib.accountInterestSettlelmentBackward(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									detailInfo.getDtInterestStart(),
									detailInfo.getAmount(),
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID(),
									Env.getSystemDate(
										queryInfo.getOfficeID(),
										queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
							if (flag < 0)
							{
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				//复利
				loanInterestInfo =
					io.getLoanAccountFee(
						queryInfo.getOfficeID(),
						queryInfo.getCurrencyID(),
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getClearInterestDate(),
						Env.getSystemDate(
							queryInfo.getOfficeID(),
							queryInfo.getCurrencyID()),
						SETTConstant.InterestFeeType.COMPOUNDINTEREST);
				
				//复利
				resultInfo.setCompoundInterest(loanInterestInfo.getInterest());				
				//起息日
				resultInfo.setCompoundInterestStartDate(loanInterestInfo.getSDate());
			}
			if (queryInfo.getIsSelectForfeitInterest()>0)
			{
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll =
					transDetailDAO.findByIsBackward(						
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(),
						queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0)
				{
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext())
					{
						while (itResult.hasNext())
						{
							TransAccountDetailInfo detailInfo =
								(TransAccountDetailInfo) itResult.next();
							log.info("-------------开始单户倒填---------");
							long flag =
								ib.accountInterestSettlelmentBackward(
									resultInfo.getAccountID(),
									resultInfo.getSubAccountID(),
									detailInfo.getDtInterestStart(),
									detailInfo.getAmount(),
									queryInfo.getOfficeID(),
									queryInfo.getCurrencyID(),
									Env.getSystemDate(
										queryInfo.getOfficeID(),
										queryInfo.getCurrencyID()),SETTConstant.InterestFeeType.INTEREST);
							if (flag < 0)
							{
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				//罚息
				loanInterestInfo =
					io.getLoanAccountFee(
						queryInfo.getOfficeID(),
						queryInfo.getCurrencyID(),
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(),
						queryInfo.getClearInterestDate(),
						Env.getSystemDate(
							queryInfo.getOfficeID(),
							queryInfo.getCurrencyID()),
						SETTConstant.InterestFeeType.FORFEITINTEREST);
				
				//逾期罚息
				resultInfo.setOverDueInterest(loanInterestInfo.getInterest());						
				resultInfo.setOverDueInterestRate(loanInterestInfo.getRate());
				//起息日
				resultInfo.setOverDueInterestStartDate(loanInterestInfo.getSDate());				
			}
			log.info("lhj debug info ===结束贷款算息=======");
		}
		return resultInfo;
	}	
	/**
		* 得到合同当前金额
		* Create Date: 2003-10-15
		* @param lContractID 合同ID
		* @return ContractAmountInfo 
		* @exception Exception
		*/
	public ContractAmountInfo getLateAmount(Connection con, long lContractID) throws Exception
	{
		ContractAmountInfo info = new ContractAmountInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conInternal = null;
		try
		{
			if (con == null)
			{
				conInternal = this.getConnection();
			}
			else
			{
				conInternal = con;
			}
			StringBuffer sbSQL = new StringBuffer();
			if (lContractID > 0)
			{
				sbSQL.append(" SELECT SUM(a.mOpenAmount) OpenAmount,SUM(a.mBalance) Balance");
				sbSQL.append(" FROM sett_subAccount a,loan_payform b");
				sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
				sbSQL.append(" AND a.nStatusID =" + SETTConstant.SubAccountStatus.NORMAL);
				sbSQL.append(" AND b.nContractID = ? ");
				logger.info(sbSQL.toString());
				ps = conInternal.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					info.setBalanceAmount(rs.getDouble("Balance")); //合同余额
					info.setOpenAmount(rs.getDouble("OpenAmount")); //合同已发放金额
					info.setRepayAmount(rs.getDouble("OpenAmount") - rs.getDouble("Balance")); //合同已还金额
				}
				cleanup(rs);
				cleanup(ps);
				
				sbSQL.setLength(0);
				sbSQL.append(" SELECT c.mExamineAmount,c.nTypeID");
				sbSQL.append(" FROM loan_contractform c");
				sbSQL.append(" WHERE c.id = ? ");
				logger.info(sbSQL.toString());
				ps = conInternal.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				double dTmp = 0;
				if (rs.next())
				{
					if (rs.getLong("nTypeID") == LOANConstant.LoanType.ZGXE)
					{
						dTmp = rs.getDouble("mExamineAmount") - info.getBalanceAmount();
					}
					else
					{
						dTmp = rs.getDouble("mExamineAmount") - info.getOpenAmount();
					}
					info.setUnPayAmount(dTmp); //未发放金额
				}
			}
		}
		catch (Exception e)
		{
			logger.error(e.toString());
			e.printStackTrace();throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con == null)
				{
					conInternal.close();
				}
			}
			catch (Exception e)
			{
				logger.error(e.toString());
				e.printStackTrace();throw new IException("Gen_E001");
			}
		}
		return info;
	}
	
	/**
	* 得到执行利率，参数lLoanID和lContractID必传入一个，不传入的话请设置为-1。
	* Create Date: 2003-10-15
	* @param lLoanID 贷款ID
	* @param lContractID 合同ID
	* @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
	* @return double 执行利率
	* @exception Exception
	*/
	public RateInfo getLatelyRate(Connection con, long lLoanID, long lContractID, Timestamp tsDate) throws Exception
	{
		log.print("------in getLateLyRate方法------");
		RateInfo info = new RateInfo();
		Connection conn = con;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = new StringBuffer();
		boolean bIsHaveLate = false;
		long lInterestTypeID = -1;
		long lLoanTypeID = -1;
		String strRate = "";

		try
		{
			if (conn == null)
			{
				conn = this.getConnection();
			}
			
			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(conn);
			}

			//取得利率类型
			if (lContractID > 0)
			{
				log.print("------lContractID=" + lContractID);
			    sbSQL.setLength(0);
				sbSQL.append(" SELECT b.nTypeID,b.nInterestTypeID,b.nLiborRateID,nvl(b.mInterestRate,0) mInterestRate,b.nBankInterestID, ");
				sbSQL.append(" nvl(b.mAdjustRate,0) mAdjustRate,nvl(b.mStaidAdjustRate,0) mStaidAdjustRate,nvl(c.mRate,0) mRate,d.LiborName,d.IntervalNum ");
				sbSQL.append(" FROM loan_contractForm b,loan_interestRate c,loan_liborInterestRate d ");
				sbSQL.append(" WHERE 1 = 1 ");
				sbSQL.append(" AND nvl(b.nBankInterestID,-1) = c.ID(+) ");
				sbSQL.append(" AND nvl(b.nLiborRateID,-1) = d.ID(+) ");
				sbSQL.append(" AND b.ID = ? ");
				//log4j.info(sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					//利率类型
					lInterestTypeID = rs.getLong("nInterestTypeID");
					//贷款类型
					lLoanTypeID = rs.getLong("nTypeID");
					log.print("------贷款类型为-----------"+lLoanTypeID);
					//未调整的基准利率
					if (lLoanTypeID == LOANConstant.LoanType.WT)
					{
						info.setBasicRate(rs.getDouble("mInterestRate"));
					}
					else
					{
						info.setBasicRate(rs.getDouble("mRate"));
					}
					//未调整的利率ID
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					//未调整的利率
					System.out.println("基准利率:"+info.getBasicRate());
					info.setRate(info.getBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
					info.setAdjustRate(rs.getDouble("mAdjustRate"));
					info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					//Libor利率ID
					info.setLiborRateID(rs.getLong("nLiborRateID"));
					//Libor利率名称
					info.setLiborName(rs.getString("LiborName"));
					//Libor利率期限
					info.setLiborIntervalNum(rs.getLong("IntervalNum"));
					
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			else if (lLoanID > 0)
			{
			    sbSQL.setLength(0);				
			    sbSQL.append(" SELECT b.nInterestTypeID, b.nTypeID,b.mInterestRate,b.nBankInterestID,b.mAdjustRate,b.mStaidAdjustRate,b.nLiborRateID,c.mRate,d.LiborName,d.IntervalNum ");
				sbSQL.append(" FROM loan_loanForm b,loan_interestRate c,loan_liborInterestRate d ");
				sbSQL.append(" WHERE 1 = 1 ");
				sbSQL.append(" AND nvl(b.nBankInterestID,-1) = c.ID(+) ");
				sbSQL.append(" AND nvl(b.nLiborRateID,-1) = d.ID(+) ");
				sbSQL.append(" AND b.ID = ? ");
				//log4j.info(sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					//利率类型
					lInterestTypeID = rs.getLong("nInterestTypeID");
					//贷款类型
					lLoanTypeID = rs.getLong("nTypeID");
					//未调整的基准利率	
					if (lLoanTypeID == LOANConstant.LoanType.WT)
					{
						info.setBasicRate(rs.getDouble("mInterestRate"));
					}
					else
					{
						info.setBasicRate(rs.getDouble("mRate"));
					}
					//未调整的利率ID
					info.setBankInterestID(rs.getLong("nBankInterestID"));
					//未调整的利率
					info.setRate(info.getBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
					info.setAdjustRate(rs.getDouble("mAdjustRate"));
					info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
					//Libor利率ID
					info.setLiborRateID(rs.getLong("nLiborRateID"));
					//Libor利率名称
					info.setLiborName(rs.getString("LiborName"));
					//Libor利率期限
					info.setLiborIntervalNum(rs.getLong("IntervalNum"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			else
			{
			    return null;
			}
			
			if (lInterestTypeID == LOANConstant.InterestRateType.BANK)
			{
				log.print("-------银行利率------");
				//通过合同ID取利率
				if (lContractID > 0)
				{
				    sbSQL.setLength(0);
				    sbSQL.append(" SELECT a.dtStartDate,b.mRate, b.ID,a.mAdjustRate,a.mStaidAdjustRate ");
					sbSQL.append(" FROM loan_rateAdjustContractDetail a,loan_interestRate b ");
					sbSQL.append(" WHERE 1 = 1 ");
					sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd') ");
					sbSQL.append(" AND a.nBankInterestID = b.id(+) ");
					sbSQL.append(" AND a.nContractID = ? ");
					sbSQL.append(" ORDER BY a.dtStartDate DESC ");
					//log4j.info(sbSQL.toString());
					ps = conn.prepareStatement(sbSQL.toString());
					ps.setTimestamp(1, tsDate);
					ps.setLong(2, lContractID);
					rs = ps.executeQuery();
	
					if (rs.next())
					{
						info.setLateBasicRate(rs.getDouble("mRate")); //调整后的基准利率
						//======ninh 2004-06-22 需求变更 增加固定浮动利率===执行利率算法改变===//
						info.setLateRate(info.getLateBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
						info.setLateAdjustRate(rs.getDouble("mAdjustRate"));
						//调整后的利率
						info.setLateStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
						//调整后的基准利率ID
						info.setLateBankInterestID(rs.getLong("ID"));
						//调整生效时间
						info.setAdjustDate(rs.getTimestamp("dtStartDate"));
						bIsHaveLate = true;
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				}
				//如果利率未调整过，取未调整的原始利率值
				if (!bIsHaveLate)
				{
					info.setLateBankInterestID(info.getBankInterestID());
					info.setLateBasicRate(info.getBasicRate());
					info.setLateAdjustRate(info.getAdjustRate());
					info.setLateStaidAdjustRate(info.getStaidAdjustRate());
					info.setLateRate(info.getRate());
					info.setLateRateString(info.getFormatRate());
				}
			}
			else if (lInterestTypeID == LOANConstant.InterestRateType.LIBOR)
			{
				log.print("-----Libor利率-----");
			    strRate = info.getLiborName();
			    if (info.getAdjustRate() < 0)
			    {
			        strRate = strRate + " - " + DataFormat.formatRate(java.lang.Math.abs(info.getAdjustRate()));
			    }
			    else if (info.getAdjustRate() > 0)
			    {
			        strRate = strRate + " + " + DataFormat.formatRate(java.lang.Math.abs(info.getAdjustRate()));
			    }
			    if (info.getStaidAdjustRate() < 0)
			    {
			        strRate = strRate + " - " + DataFormat.formatRate(java.lang.Math.abs(info.getStaidAdjustRate()));
			    }
			    else if (info.getStaidAdjustRate() > 0)
			    {
			        strRate = strRate + " + " + DataFormat.formatRate(java.lang.Math.abs(info.getStaidAdjustRate()));
			    }
			    info.setLateRateString(strRate);
			    
			    info.setAdjustRate(info.getAdjustRate());
			    info.setStaidAdjustRate(info.getStaidAdjustRate());
			}
			//针对委托贷款
			else
			{
			    info.setLateBankInterestID(info.getBankInterestID());
				info.setLateBasicRate(info.getBasicRate());
				info.setLateAdjustRate(info.getAdjustRate());
				info.setLateStaidAdjustRate(info.getStaidAdjustRate());
				info.setLateRate(info.getRate());
				info.setLateRateString(info.getFormatRate());    
			}
			log.print("基准利率:"+info.getBasicRate());
			log.print("浮动利率:"+info.getAdjustRate());
			log.print("固定浮动利率:" + info.getStaidAdjustRate());;
			log.print("合同利率:"+info.getRate());
			log.print("调整后利率:"+info.getRate());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null && con == null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				e.printStackTrace();throw new IException("Gen_E001");
			}
		}
		log.print("------out getLateLyRate方法------");
		return info;
	}		
	/**
	 * 贷款逾期
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryLoanDunNotice(InterestEstimateQueryInfo qInfo) throws Exception
	{

		getLoanNoticeSQL(qInfo);
		
		m_sbWhere.append(" and payform.dtEnd < to_date('" + DataFormat.formatDate(qInfo.getClearInterestDate()) + "','yyyy-mm-dd') \n");
		
		//
		PageLoader pageLoader = (PageLoader) 
		com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

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
	/**
		 * 贷款到期
		 * @param qaci
		 * @return
		 * @throws Exception
		 */
		public PageLoader queryLoanMatureNotice(InterestEstimateQueryInfo qInfo) throws Exception
		{

			getLoanNoticeSQL(qInfo);
			//m_sbWhere.append(" and payform.dtEnd <= to_date('" + DataFormat.formatDate(UtilOperation.getNextNDay(Env.getSystemDate(qInfo.getOfficeID(),qInfo.getCurrencyID()),10)) + "','yyyy-mm-dd') \n");
			//
			PageLoader pageLoader = (PageLoader) 
			com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

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
	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryLoanNoticeClientYs(InterestEstimateQueryInfo qInfo,Vector resultVec) throws Exception
	{

		getLoanNoticeYsClientSQL(qInfo,resultVec);
		//
		PageLoader pageLoader = (PageLoader) 
		com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

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
	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryLoanNoticeAccountYs(
		InterestEstimateQueryInfo qInfo,
		Vector resultVec)
		throws Exception
	{
		getLoanNoticeYsAccountSQL(qInfo, resultVec);
		//
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject(
				"com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.interest.dataentity.LoanNoticeInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	/**
	 * Method queryLoanInterestNotice.
	 * @param qInfo
	 * @return Vector
	 * @throws Exception
	 * added by gqzhang
	 * 用于批量打印
	 */
	public Vector queryLoanInterestNotice(InterestEstimateQueryInfo qInfo, Vector resultVec) throws Exception
	{
		Vector rtnVec = new Vector();
		Vector rsVec = new Vector();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			if (qInfo.getNoticetype() == SETTConstant.LoanNoticeType.LoanInterestNotice) //应付利息通知单
			{
				getLoanNoticeYsClientSQL(qInfo, resultVec);
			}
			else
			{
				getLoanNoticeSQL(qInfo);
			}
			//逾期
			if (qInfo.getNoticetype() == SETTConstant.LoanNoticeType.LoanDunNotice)
			{
				m_sbWhere.append(" and payform.dtEnd < to_date('" + DataFormat.formatDate(qInfo.getClearInterestDate()) + "','yyyy-mm-dd') \n");
			}
			sql = "select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			System.out.println("~~~~~~~~~~~~~~~~~sql语句~~~~~~~~~~~~~~~~~~~~`");
			System.out.println(sql);
			System.out.println("~~~~~~~~~~~~~~~~~sql语句~~~~~~~~~~~~~~~~~~~~`");
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("~~~~~~~~~~~~~~~~~`before rs.next()~~~~~~~~~~~~~~~~~`泰山");
			while (rs.next())
			{
				System.out.println("\n\n\n~~~~~~~~~~~~~~~ in rs.next()\n\n\n");
				LoanNoticeInfo loanNoticeInfo = new LoanNoticeInfo();
				loanNoticeInfo.setAccountID(rs.getLong("accountID"));
				loanNoticeInfo.setAccountNo(rs.getString("accountno"));
				loanNoticeInfo.setOfficeID(rs.getLong("OfficeID"));
				loanNoticeInfo.setCurrencyID(rs.getLong("CurrencyID"));
				loanNoticeInfo.setAccountTypeID(rs.getLong("accountTypeID"));
				loanNoticeInfo.setClearInterestDate(rs.getTimestamp("ClearInterestDate"));
				loanNoticeInfo.setSubAccountID(rs.getLong("subAccountID"));
				loanNoticeInfo.setLoanNoteID(rs.getLong("LoanNoteID"));
				loanNoticeInfo.setContractID(rs.getLong("ContractID"));
				loanNoticeInfo.setContractNo(rs.getString("contractNo"));
				loanNoticeInfo.setBorrowClientID(rs.getLong("BorrowClientID"));
				loanNoticeInfo.setLoanAmount(rs.getDouble("LoanAmount"));
				loanNoticeInfo.setLoanStartDate(rs.getTimestamp("LoanStartDate"));
				loanNoticeInfo.setLoanEndDate(rs.getTimestamp("LoanEndDate"));
				loanNoticeInfo.setLoanTerm(rs.getLong("LoanTerm"));
				loanNoticeInfo.setPayFormNo(rs.getString("payFormNo"));
				rsVec.add(loanNoticeInfo);
			}
			System.out.println("\n\n\n~~~~~~~~~~~~~~~~~`after rs.next()~~~~~~~~~~~~~~~~~`\n\n\n");
			System.out.println("\n\n\n~~~~~~~~~~~~~~~~~~~~`Vector大小：" + rsVec.size() + "\n\n\n");
			if (rsVec.size() > 0)
			{
				System.out.println("\n\n\n~~~~~~~~~~~~~~~before queryNoticeInterest\n\n\n");
				rtnVec = queryNoticeInterest(con, rsVec, qInfo);
				System.out.println("\n\n\n~~~~~~~~~~~~~~~after queryNoticeInterest\n\n\n");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return rtnVec;
	}		
	public static void main(String[] args) throws Exception
	{
		
		QLoanNotice q = new QLoanNotice();
		System.out.println(q.getLatelyRate(null,-1,47,null).getRate());
	}
	
}