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

import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.query.paraminfo.QuerySecuritiesNoticeInfo;
import com.iss.itreasury.settlement.query.resultinfo.QuerySecuritiesSumInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author xrli
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QSecuritiesNotice extends BaseQueryObject
{

	public final static int OrderBy_Code = 1;
	
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QSecuritiesNotice()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getSecuritiesNoticeSQL(QuerySecuritiesNoticeInfo qInfo)
	{
		m_sbSelect = new StringBuffer();
		// select		
		
		
		m_sbSelect.append("Trans.ID as ID, \n");
		m_sbSelect.append("Notice.id as SECURITIESNOTICEID, \n");					
		m_sbSelect.append("busitype.Name SecTransTypeDesc, \n");	
		m_sbSelect.append("busitype.BUSINESSATTRIBUTEID as PayOrReceiveType, \n");
		m_sbSelect.append("Notice.CompanyBankID as BankID, \n");		
		m_sbSelect.append("cb.BankName counterpartBankName,    \n");
		m_sbSelect.append("cb.BankAccountName counterpartAccountName,    \n");		
		m_sbSelect.append("d.Amount as Amount,  \n");
		m_sbSelect.append("Trans.NTRANSACTIONTYPEID as TransactionTypeID,  \n");
		m_sbSelect.append("Notice.ExecuteDate as TransDate, \n");
		m_sbSelect.append("Notice.StatusID as STATUSID, \n");
		m_sbSelect.append("Trans.sTransNo as TransNo \n");				
					
		// from 
		m_sbFrom = new StringBuffer();		
		m_sbFrom.append(" SETT_TRANSSECURITIES Trans,sec_NoticeForm Notice, sec_DeliverYorder d,  sett_Branch b,  SEC_TransactionType transtype,  SEC_BusinessType busitype,  SEC_CounterpartBankAccount cb   \n");
		// where 
		m_sbWhere = new StringBuffer();
		
		m_sbWhere.append("Notice.id =Trans.NSECURITIESNOTICEID(+) \n");
		m_sbWhere.append("and Notice.DELIVERYORDERID=d.id  \n");
		m_sbWhere.append("and Notice.CompanyBankID=b.ID(+) \n");
		m_sbWhere.append("and Notice.TransactionTypeid=transtype.ID(+) \n");
		m_sbWhere.append("and transtype.BusinessTypeID=busitype.ID(+) \n");
		m_sbWhere.append("and Notice.CounterpartBankID=cb.ID(+) \n");
		m_sbWhere.append("and d.officeid="+ qInfo.getOfficeID() + " \n");
		m_sbWhere.append("and d.CurrencyID=" + qInfo.getCurrencyID()+ " \n");
		m_sbWhere.append("and Notice.StatusID <> 0 \n");
		m_sbWhere.append("and Notice.StatusID in ( " + SECConstant.NoticeFormStatus.CHECKED  + "," + SECConstant.NoticeFormStatus.USED + "," + SECConstant.NoticeFormStatus.COMPLETED + "," + SECConstant.NoticeFormStatus.POSTED +" ) \n");
		
		if (qInfo.getSecuritiesNoticeNoFrom() != null && qInfo.getSecuritiesNoticeNoFrom().length() > 0)
			m_sbWhere.append("and Notice.code>='" + qInfo.getSecuritiesNoticeNoFrom() + "'");
		if (qInfo.getSecuritiesNoticeNoto() != null && qInfo.getSecuritiesNoticeNoto().length() > 0)
			m_sbWhere.append("and Notice.code<='" + qInfo.getSecuritiesNoticeNoto() + "'");
		if (qInfo.getPayOrReceiveType() == SETTConstant.ReceiveOrPay.PAY)
		{
			m_sbWhere.append("and transtype.CAPITALDIRECTION in ( " + SECConstant.Direction.FINANCE_PAY  + "," + SECConstant.Direction.RECEIVE_AND_FINANCE_PAY +" ) \n");
		}
		else if(qInfo.getPayOrReceiveType() == SETTConstant.ReceiveOrPay.RECEIVE)
		{
			m_sbWhere.append("and transtype.CAPITALDIRECTION in ( " + SECConstant.Direction.FINANCE_RECEIVE  + "," + SECConstant.Direction.PAY_AND_FINANCE_RECEIVE +" ) \n");
		}
			
		if (qInfo.getStatusID() >0)
			m_sbWhere.append("and Notice.StatusID=" + qInfo.getStatusID() + "");	
		if (qInfo.getBankID() >0)
				m_sbWhere.append("and Notice.CompanyBankID=" + qInfo.getBankID() + "");
		if (qInfo.getDateFrom() != null)
				m_sbWhere.append("and Notice.ExecuteDate>=to_date('"+DataFormat.formatDate(qInfo.getDateFrom())+"','yyyy-mm-dd') ");
		if (qInfo.getDateTo() != null)
				m_sbWhere.append("and Notice.ExecuteDate<=to_date('"+DataFormat.formatDate(qInfo.getDateTo())+"','yyyy-mm-dd') ");
		if (qInfo.getAmountFrom() >0)
				m_sbWhere.append("and d.Amount>=" + qInfo.getAmountFrom() + "");
		if (qInfo.getAmountTo() >0)
				m_sbWhere.append("and d.Amount<=" + qInfo.getAmountTo() + "");								
				
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_Code :
				m_sbOrderBy.append(" \n order by Notice.code" + strDesc);
				break;			
								
		}
		logger.debug(m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
	}
	/*
	 * 得到金额合计
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	public QuerySecuritiesSumInfo querySecuritiesSum(QuerySecuritiesNoticeInfo qInfo) throws Exception
	{
	   QuerySecuritiesSumInfo sumObj = new QuerySecuritiesSumInfo();
	   Connection con = null;
	   ResultSet rs = null;
	   PreparedStatement ps = null;
	   String strSelect = "";
	   String strDepositWhere = "";
	   String strLoanWhere = "";
	   String sql = "";
	   //

	   try
	   {
		getSecuritiesNoticeSQL(qInfo);
		   // select 
		   strSelect = " select sum(round(d.Amount,2)) Amount \n";			

		   con = this.getConnection();	
			m_sbWhere.append("and transtype.CAPITALDIRECTION in ( " + SECConstant.Direction.FINANCE_PAY  + "," + SECConstant.Direction.RECEIVE_AND_FINANCE_PAY +" ) \n");		
			
		   sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
		   logger.info(sql);
		   ps = con.prepareStatement(sql);
		   rs = ps.executeQuery();
		   if (rs.next())
		   {
			   // 金额合计
			   sumObj.setPayAmountSum(rs.getDouble("Amount"));			   
		   }
		   cleanup(rs);
		   cleanup(ps);	
				
			getSecuritiesNoticeSQL(qInfo);			
			m_sbWhere.append("and transtype.CAPITALDIRECTION in ( " + SECConstant.Direction.FINANCE_RECEIVE  + "," + SECConstant.Direction.PAY_AND_FINANCE_RECEIVE +" ) \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				sumObj.setReceiveAmountSum(rs.getDouble("Amount"));			   
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
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader querySecuritiesNoticeInfo(QuerySecuritiesNoticeInfo qInfo) throws Exception
	{

		getSecuritiesNoticeSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QuerySecuritiesNoticeResultInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	

}