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

import com.iss.itreasury.settlement.query.paraminfo.QuerySecuritiesInfo;
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
public class QSecurities extends BaseQueryObject
{

	public final static int OrderBy_Date = 1;	
	public final static int OrderBy_sTransNo = 2;	
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QSecurities()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getSecuritiesSQL(QuerySecuritiesInfo qInfo)
	{
		m_sbSelect = new StringBuffer();
		// select		
		m_sbSelect.append("Trans.dtExecute as ExecuteDate, \n");
		m_sbSelect.append("Trans.sTransNo as TransNo, \n");
		m_sbSelect.append("Trans.ID as ID, \n");
		m_sbSelect.append("Trans.NSECURITIESNOTICEID as SECURITIESNOTICEID, \n");
		m_sbSelect.append("Notice.Code as SECURITIESNOTICENO, \n");
		m_sbSelect.append("Trans.SSECURITIESTRANSACTION as SECURITIESTRANSACTION, \n");
		m_sbSelect.append("Trans.nBankID as BankID, \n");			
		m_sbSelect.append("Trans.mAmount as Amount,  \n");
		
		m_sbSelect.append("Trans.NTRANSACTIONTYPEID as TRANSACTIONTYPEID, \n");		
		m_sbSelect.append("Trans.NINPUTUSERID as INPUTUSERID, \n");		
		m_sbSelect.append("Trans.NCHECKUSERID as CHECKUSERID, \n");		
		m_sbSelect.append("Trans.NSTATUSID as STATUSID, \n");		
		m_sbSelect.append("Trans.SABSTRACT as ABSTRACT \n");			
		// from 
		m_sbFrom = new StringBuffer();		
		m_sbFrom.append(" SETT_TRANSSECURITIES Trans,SEC_NOTICEFORM Notice   \n");
		// where 
		m_sbWhere = new StringBuffer();
		
		m_sbWhere.append("Trans.NSECURITIESNOTICEID = Notice.id \n");
		m_sbWhere.append("and Trans.nofficeid="+ qInfo.getOfficeID() + " \n");
		m_sbWhere.append("and Trans.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		m_sbWhere.append("and Trans.nStatusID <> 0 \n");
		
		if (qInfo.getTransactionTypeID() >0)
				m_sbWhere.append("and Trans.NTRANSACTIONTYPEID=" + qInfo.getTransactionTypeID() + "");
		if (qInfo.getSecuritiesNoticeNoFrom() != null && qInfo.getSecuritiesNoticeNoFrom().length() > 0)
			m_sbWhere.append("and Notice.code>='" + qInfo.getSecuritiesNoticeNoFrom() + "'");
		if (qInfo.getSecuritiesNoticeNoto() != null && qInfo.getSecuritiesNoticeNoto().length() > 0)
			m_sbWhere.append("and Notice.code<='" + qInfo.getSecuritiesNoticeNoto() + "'");
		if (qInfo.getBankID() >0)
				m_sbWhere.append("and Trans.nBankID=" + qInfo.getBankID() + "");
		if (qInfo.getDateFrom() != null)
				m_sbWhere.append("and Trans.dtDate>=to_date('"+DataFormat.formatDate(qInfo.getDateFrom())+"','yyyy-mm-dd') ");
		if (qInfo.getDateTo() != null)
				m_sbWhere.append("and Trans.dtDate<=to_date('"+DataFormat.formatDate(qInfo.getDateTo())+"','yyyy-mm-dd') ");
		if (qInfo.getAmountFrom() >0)
				m_sbWhere.append("and Trans.mAmount>=" + qInfo.getAmountFrom() + "");
		if (qInfo.getAmountTo() >0)
				m_sbWhere.append("and Trans.mAmount<=" + qInfo.getAmountTo() + "");	
		if (qInfo.getTransNoFrom() != null && qInfo.getTransNoFrom().length() > 0)
			m_sbWhere.append("and Trans.sTransNo>='" + qInfo.getTransNoFrom() + "'");
		if (qInfo.getTransNoto() != null && qInfo.getTransNoto().length() > 0)
			m_sbWhere.append("and Trans.sTransNo<='" + qInfo.getTransNoto() + "'");			
		if (qInfo.getExecuteDateFrom() != null)
				m_sbWhere.append("and Trans.dtExeCute>=to_date('"+DataFormat.formatDate(qInfo.getExecuteDateFrom())+"','yyyy-mm-dd') ");
		if (qInfo.getExecuteDateTo() != null)
				m_sbWhere.append("and Trans.dtExeCute<=to_date('"+DataFormat.formatDate(qInfo.getExecuteDateTo())+"','yyyy-mm-dd') ");
		if (qInfo.getStatusID() >0)
				m_sbWhere.append("and Trans.nStatusID=" + qInfo.getStatusID() + "");
		if (qInfo.getCheckUserID() >0)
				m_sbWhere.append("and Trans.nCheckUserID=" + qInfo.getCheckUserID() + "");
		if (qInfo.getInputUserID() >0)
				m_sbWhere.append("and Trans.nInputUserID=" + qInfo.getInputUserID() + "");						
				
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_Date :
				m_sbOrderBy.append(" \n order by Trans.dtExecute" + strDesc);
				break;			
			case OrderBy_sTransNo :
				m_sbOrderBy.append(" \n order by Trans.sTransNo" + strDesc);
				break;
			default :
				m_sbOrderBy.append(" \n order by Trans.sTransNo");
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
	public QuerySecuritiesSumInfo querySecuritiesSum(QuerySecuritiesInfo qInfo) throws Exception
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
		getSecuritiesSQL(qInfo);
		   // select 
		   strSelect = " select sum(round(Trans.mAmount,2)) Amount \n";			

		   con = this.getConnection();
			m_sbWhere.append("and Trans.NTRANSACTIONTYPEID=" + SETTConstant.TransactionType.SECURITIESPAY + "");
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
				
			getSecuritiesSQL(qInfo);							   
			m_sbWhere.append("and Trans.NTRANSACTIONTYPEID=" + SETTConstant.TransactionType.SECURITIESRECEIVE + "");
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
	public PageLoader querySecuritiesInfo(QuerySecuritiesInfo qInfo) throws Exception
	{

		getSecuritiesSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.transsecurities.dataentity.TransSecuritiesInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	

}