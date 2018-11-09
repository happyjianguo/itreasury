/*
 * Created on 2007-1-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.craftbrother.query.dao;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.craftbrother.query.dataentity.QueryFundLendingRequisitionInfo;
import com.iss.itreasury.craftbrother.query.dataentity.QueryPerformInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.securitiescontract.dataentity.AssetDealRequisitionInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractQueryInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryContractDao extends SecuritiesDAO
{
	private StringBuffer m_sbSelect = null;
	private StringBuffer m_sbFrom = null;
	private StringBuffer m_sbWhere = null;
	private StringBuffer m_sbOrderBy = null;
	
	public QueryContractDao()
	{
		super("");
	}
	
	public PageLoader querycontract(SecuritiesContractQueryInfo info) throws Exception
	{
		getContractSQL(info);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	
	
	//资产转让通知单查询
	public PageLoader queryRequisition_HL(AssetDealRequisitionInfo info) throws Exception
	{
		getRequisitionSQL_HL(info);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.securitiescontract.dataentity.AssetDealRequisitionInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	//资金拆借通知单查询
	
	public PageLoader queryRequisition_FL(QueryFundLendingRequisitionInfo info) throws Exception
	{
		getRequisitionSQL_FL(info);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.craftbrother.query.dataentity.QueryFundLendingRequisitionInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	
	public PageLoader querycontract_HL(SecuritiesContractQueryInfo info) throws Exception
	{
		getContractSQL_HL(info);
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	public double queryContractSumAmount(SecuritiesContractQueryInfo info) throws Exception
	{
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//
		try
		{	initDAO();
			getContractSQL_HL(info);
			// select 
			strSelect = " select sum(round(amount,2)) amountSum \n";
			
			sql = strSelect + " from " + m_sbFrom.toString() + "\n where " + m_sbWhere.toString();
			prepareStatement(sql);
			executeQuery();
			if (transRS.next())
			{
				dReturn = transRS.getDouble("amountSum");
			}
			
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("合同金额统计异常",e);
		}finally{
			finalizeDAO();
		}
		System.out.println("1312342424");
		return dReturn;
	}
	
	//资金拆借SQL语句
	public void getRequisitionSQL_FL(QueryFundLendingRequisitionInfo info){
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("a.CODE fundLendingCode, a.statusid ,b.clientid secclientid, b.pledgesecuritiesamount, a.transactiontypeid secTransactionTypeId,b.code deliveryOrderCode, a.inputdate,a.inputuserid,b.id deliveryOrderId,a.id requisitionid ");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SEC_NoticeForm a, SEC_DeliveryOrder b ");
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("a.deliveryorderid = b.id ");
		if(info.getSecTransactionTypeId()>0){
			m_sbWhere.append("and b.transactiontypeid = '"+info.getSecTransactionTypeId()+"' ");
		}
		if(info.getSecClientId()>0){
			m_sbWhere.append("and b.clientid = "+info.getSecClientId()+" ");
		}
		if(info.getSecCounterpartId()>0){
			m_sbWhere.append("and b.counterpartId = "+info.getSecCounterpartId()+" ");
		}
		if(info.getSecPledgeSecuritiesAmountFrom()>0){
			m_sbWhere.append("and  b.pledgesecuritiesamount >="+info.getSecPledgeSecuritiesAmountFrom()+" ");
		}
		if(info.getSecPledgeSecuritiesAmountTo()>0){
			m_sbWhere.append("and  b.pledgesecuritiesamount <= "+info.getSecPledgeSecuritiesAmountTo()+" ");
		}
		if(info.getTransactionDateFrom()!=null){
			m_sbWhere.append("and to_char(a.inputdate,'yyyy-mm-dd') >= '"+DataFormat.formatDate(info.getTransactionDateFrom())+"' ");
		}
		if(info.getTransactionDateTo()!=null){
			m_sbWhere.append("and to_char(a.inputdate,'yyyy-mm-dd') <= '"+DataFormat.formatDate(info.getTransactionDateTo())+"' ");
		}
		if(info.getStatusId()>=0){
			m_sbWhere.append("and a.statusId = "+info.getStatusId()+" ");
		}
		if(info.getDeliveryOrderCode()!=null && info.getDeliveryOrderCode().length()>0){
			m_sbWhere.append("and b.code = '"+info.getDeliveryOrderCode()+"' ");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by b.code ");

		System.out.println("SQL===="+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"");
	}
	
	public void getContractSQL(SecuritiesContractQueryInfo info)
	{
		// select 
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" * ");
		
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( ");
		
		m_sbFrom.append(" select id,code,amount,rate,inputdate,transactiontypeid,statusid,inputuserid from sec_applycontract ");
		m_sbFrom.append(" where ");
		m_sbFrom.append(" transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+" ) ");
		m_sbFrom.append(" and officeid = "+info.getOfficeId()+" and currencyid = "+info.getCurrencyId()+" ");
		if ( info.getStartAmount() > 0 )
		{
			m_sbFrom.append(" and amount >= "+info.getStartAmount()+" ");
		}
		if ( info.getEndAmount() > 0 )
		{
			m_sbFrom.append(" and amount <= "+info.getEndAmount()+" ");
		}
		if ( info.getStartInputDate() != null )
		{
			m_sbFrom.append(" and to_char( inputdate,'yyyy-mm-dd' ) >= '"+ DataFormat.formatDate( info.getStartInputDate() )+"' ");
		}
		if ( info.getEndInputDate() != null )
		{
			m_sbFrom.append(" and to_char( inputdate,'yyyy-mm-dd' ) <= '"+ DataFormat.formatDate( info.getEndInputDate() )+"' ");
		}
		if ( info.getStatusId() > 0 )
		{
			m_sbFrom.append(" and statusid = "+info.getStatusId()+" ");
		}
		else{
			m_sbFrom.append(" and statusid <> "+SECConstant.ContractStatus.CANCEL+" ");//查询全部时过滤已取消状态的合同
		}
		if ( info.getTransactionTypeId() > 0 )
		{
			m_sbFrom.append(" and transactiontypeid = "+info.getTransactionTypeId()+" ");
		}
		//合同编号
		if ( info.getContractCodeFrom() != null && info.getContractCodeFrom().length() > 0 )
		{
			m_sbFrom.append(" and code >= '"+info.getContractCodeFrom()+"' ");
		}
		if ( info.getContractCodeTo() != null && info.getContractCodeTo().length() > 0 )
		{
			m_sbFrom.append(" and code <= '"+info.getContractCodeTo()+"' ");
		}
		//合同管理人
		if ( info.getInputUserId() > 0 )
		{
			m_sbFrom.append(" and inputuserid = "+info.getInputUserId()+" ");
		}
		//added by qhzhou 2008-09-22 交易对手
		if(info.getCounterpartId() > 0){
			m_sbFrom.append(" and CounterpartID = " +  info.getCounterpartId());
		}
		m_sbFrom.append(" union ");
		
		m_sbFrom.append(" select id,SCONTRACTCODE,MLOANAMOUNT,MDISCOUNTRATE,dtinputdate,ntypeid,nstatusid,ninputuserid from LOAN_CONTRACTFORM ");
		m_sbFrom.append(" where ");
		m_sbFrom.append(" ntypeid = "+LOANConstant.LoanType.ZTX+" ");
		m_sbFrom.append(" and nofficeid = "+info.getOfficeId()+" and ncurrencyid = "+info.getCurrencyId()+" ");
		if ( info.getStartAmount() > 0 )
		{
			m_sbFrom.append(" and MLOANAMOUNT >= "+info.getStartAmount()+" ");
		}
		if ( info.getEndAmount() > 0 )
		{
			m_sbFrom.append(" and MLOANAMOUNT <= "+info.getEndAmount()+" ");
		}
		if ( info.getStartInputDate() != null )
		{
			m_sbFrom.append(" and to_char( dtinputdate,'yyyy-mm-dd' ) >= '"+ DataFormat.formatDate( info.getStartInputDate() ) +"' ");
		}
		if ( info.getEndInputDate() != null )
		{
			m_sbFrom.append(" and to_char( dtinputdate,'yyyy-mm-dd' ) <= '"+ DataFormat.formatDate( info.getEndInputDate() ) +"' ");
		}
		if ( info.getStatusId() > 0 )
		{
			m_sbFrom.append(" and nstatusid = "+info.getStatusId()+" ");
		}
		if ( info.getTransactionTypeId() > 0 )
		{
			m_sbFrom.append(" and ntypeid = "+info.getTransactionTypeId()+" ");
		}
		//合同编号
		if ( info.getContractCodeFrom() != null && info.getContractCodeFrom().length() > 0 )
		{
			m_sbFrom.append(" and SCONTRACTCODE >= '"+info.getContractCodeFrom()+"' ");
		}
		if ( info.getContractCodeTo() != null && info.getContractCodeTo().length() > 0 )
		{
			m_sbFrom.append(" and SCONTRACTCODE <= '"+info.getContractCodeTo()+"' ");
		}
		//合同管理人
		if ( info.getInputUserId() > 0 )
		{
			m_sbFrom.append(" and ninputuserid = "+info.getInputUserId()+" ");
		}
		//added by qhzhou 2008-09-22 交易对手
		if(info.getCounterpartId() > 0){
			m_sbFrom.append(" and nborrowclientid = " +  info.getCounterpartId());
		}
		m_sbFrom.append(" ) ");
		
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1=1 ");
		
		// order 
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by id ");

		System.out.println("SQL===="+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"");
	}
	
	//资产转让SQL语句
	public void getRequisitionSQL_HL(AssetDealRequisitionInfo info){
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("a.CODE formCode, b.CODE,a.TRANSACTIONTYPEID,b.amount,a.statusid,a.inputdate, a.inputuserid,b.id applyId,a.id requisitionId,a.noticeAmount ");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("Sec_Noticeform a, SEC_APPLYCONTRACT b");
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("b.id = a.contractid ");
		if(info.getContractIDFrom()!=null && info.getContractIDFrom().length()>0){
			m_sbWhere.append("and b.code >= '"+info.getContractIDFrom()+"' ");
		}
		if(info.getContractIDTo()!=null && info.getContractIDTo().length()>0){
			m_sbWhere.append("and b.code <= '"+info.getContractIDTo()+"' ");
		}
		if(info.getTypeId()>0){
			m_sbWhere.append("and b.transactionTypeId = "+info.getTypeId()+" ");
		}
		if(info.getTransactionTypeId()>0){
			m_sbWhere.append("and a.transactionTypeId = "+info.getTransactionTypeId()+" ");
		}
		if(info.getCounterpartId()>0){
			m_sbWhere.append("and b.counterpartId = "+info.getCounterpartId()+" ");
		}
		if(info.getStartAmount()>0){
			m_sbWhere.append("and b.amount >="+info.getStartAmount()+" ");
		}
		if(info.getEndAmount()>0){
			m_sbWhere.append("and b.amount <= "+info.getEndAmount()+" ");
		}
		if(info.getStartReceiveDate()!=null){
			m_sbWhere.append("and to_char(a.inputdate,'yyyy-mm-dd') >= '"+DataFormat.formatDate(info.getStartReceiveDate())+"' ");
		}
		if(info.getEndReceiveDate()!=null){
			m_sbWhere.append("and to_char(a.inputdate,'yyyy-mm-dd') <= '"+DataFormat.formatDate(info.getEndReceiveDate())+"' ");
		}
		if(info.getStatusId()!=-1){
			m_sbWhere.append("and a.statusId = "+info.getStatusId()+" ");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by a.inputdate ");

		System.out.println("SQL===="+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"");
	}
	public void getContractSQL_HL(SecuritiesContractQueryInfo info)
	{
		// select 
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" * ");
		
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" ( ");
		
		m_sbFrom.append(" select id,code,amount,incomerate,inputdate,transactiontypeid,statusid,inputuserid from sec_applycontract ");
		m_sbFrom.append(" where ");
		m_sbFrom.append(" transactiontypeid in ( "+SECConstant.BusinessType.CAPITAL_REPURCHASE.REPURCHASE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.AVERAGE_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.BREAK_NOTIFY+", "+SECConstant.BusinessType.CAPITAL_REPURCHASE.INVEST_BREAK+" ) ");
		m_sbFrom.append(" and officeid = "+info.getOfficeId()+" and currencyid = "+info.getCurrencyId()+" ");
		if ( info.getStartAmount() > 0 )
		{
			m_sbFrom.append(" and amount >= "+info.getStartAmount()+" ");
		}
		if ( info.getEndAmount() > 0 )
		{
			m_sbFrom.append(" and amount <= "+info.getEndAmount()+" ");
		}
		if ( info.getStartInputDate() != null )
		{
			m_sbFrom.append(" and to_char( inputdate,'yyyy-mm-dd' ) >= '"+ DataFormat.formatDate( info.getStartInputDate() )+"' ");
		}
		if ( info.getEndInputDate() != null )
		{
			m_sbFrom.append(" and to_char( inputdate,'yyyy-mm-dd' ) <= '"+ DataFormat.formatDate( info.getEndInputDate() )+"' ");
		}
		if ( info.getStatusId() > 0 )
		{
			m_sbFrom.append(" and statusid = "+info.getStatusId()+" ");
		}
		else{
			m_sbFrom.append(" and statusid <> "+SECConstant.ContractStatus.CANCEL+" ");//查询全部时过滤已取消状态的合同
		}
		if ( info.getTransactionTypeId() > 0 )
		{
			m_sbFrom.append(" and transactiontypeid = "+info.getTransactionTypeId()+" ");
		}
		//合同编号
		if ( info.getContractCodeFrom() != null && info.getContractCodeFrom().length() > 0 )
		{
			m_sbFrom.append(" and code >= '"+info.getContractCodeFrom()+"' ");
		}
		if ( info.getContractCodeTo() != null && info.getContractCodeTo().length() > 0 )
		{
			m_sbFrom.append(" and code <= '"+info.getContractCodeTo()+"' ");
		}
		//合同管理人
		if ( info.getInputUserId() > 0 )
		{
			m_sbFrom.append(" and inputuserid = "+info.getInputUserId()+" ");
		}
		//added by qhzhou 2008-09-22 交易对手
		if(info.getCounterpartId() > 0){
			m_sbFrom.append(" and CounterpartID = " +  info.getCounterpartId());
		}
		m_sbFrom.append(" and transactiontypeid != "+LOANConstant.LoanType.ZTX+" ");
		
		m_sbFrom.append(" ) ");
		
		// where 
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1=1 ");
		
		// order 
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by id ");

		System.out.println("SQL===="+m_sbSelect.toString()+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"");
	}
	
	public Collection queryContractPerform(long lID, long lParam, long lDesc) throws Exception
	{
		Vector v = new Vector();
		String strSQL = "";

		try
		{
			Timestamp tsToday = Env.getSystemDate();
			initDAO();
			strSQL =
				" select dtexecute, ntransactiontypeid, nsubtransactiontypeid, mamount"
					+ " from sett_transcraftbrother "
					+ " where nStatusID >= "+ SETTConstant.TransactionStatus.CHECK
					+ " and nnoticeid in (select ID from Sec_Noticeform where contractID="
					+ lID
					+ "and statusid ="
					+ SECConstant.NoticeFormStatus.USED
					+ ")";
					

			String strDesc = (lDesc == 1) ? " desc " : "";

			switch ((int) lParam)
			{
				case 1 : //金额
					strSQL += " \n order by mAmount" + strDesc;
					break;
				case 2 : //执行时间
					strSQL += " \n order by dtExecute" + strDesc;
					break;
				case 3 : //通知单类型
					strSQL += " \n order by ntransactiontypeid" + strDesc;
					break;
			}
			log.print(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			while (transRS.next())
			{
				QueryPerformInfo info = new QueryPerformInfo();
				info.setPerformAmount(transRS.getDouble("mAmount"));
				info.setPerformDate(transRS.getTimestamp("dtExecute"));
				info.setTransactionTypeId(transRS.getLong("ntransactiontypeid"));
				info.setSubTransactionTypeId(transRS.getLong("nsubtransactiontypeid"));
				v.add(info);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			finalizeDAO();
		}
		return v;
	}
	
	/**转贴现凭证查询
	 * added by lijunli 2010/09/3
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findTransDiscountCredence(long lContractId) throws Exception{
		Vector v = new Vector();
		ResultSet localRS = null;
		TransDiscountCredenceInfo  tdcInfo = null;
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select d.id id,d.scode code,c.scontractcode   contractcode, d.sdraftcode draftcode, d.sapplyclient applyclient, d.mamount amount, ");
			
			
			buffer.append("d.nstatusid  statusid, c.dtdiscountdate discountdate, c.mexamineamount examineamount, c.mdiscountrate  discountrate, ");
			buffer.append("c.ninorout   inorout,  c.ndiscounttypeid discounttypeid, f.NACCEPTPOTYPEID   drafttypeid  ");
			buffer.append("from loan_contractform c,loan_discountcredence d  ");
			buffer.append(" ,(select distinct  e.transdiscountcredenceid , f.NACCEPTPOTYPEID  from rtransdiscountcredencebill e,loan_discountcontractbill f where   e.discountcontractbillid=f.id and f.nstatusid>0) f ");
			buffer.append("where c.id = d.ncontractid and d.id=f.transdiscountcredenceid ");
			buffer.append(" and c.id = " + lContractId);
		    buffer.append(" order by d.scode");
			String strSQL = buffer.toString();
			System.out.println("转贴现凭证查询SQL:"+strSQL);
			log.debug(strSQL);
			prepareStatement(strSQL.toString());
			localRS = executeQuery();
	
			while (localRS.next())
			{
			    tdcInfo = new TransDiscountCredenceInfo();
				//billInfo = new TransDiscountContractBillInfo(); new tran
				tdcInfo.setId(localRS.getInt("id"));
				tdcInfo.setContractCode(localRS.getString("contractcode"));
				tdcInfo.setDraftCode(localRS.getString("draftcode"));
				tdcInfo.setApplyClient(localRS.getString("applyclient"));
				tdcInfo.setAmount(localRS.getDouble("amount"));
				tdcInfo.setStatusID(localRS.getLong("statusid"));
				tdcInfo.setDiscountDate(localRS.getTimestamp("discountdate"));
				
				tdcInfo.setCheckAmount(localRS.getDouble("examineamount"));
				tdcInfo.setInOrOut(localRS.getLong("inorout"));
				tdcInfo.setDiscountTypeID(localRS.getLong("discounttypeid"));
				tdcInfo.setDraftTypeID(localRS.getLong("drafttypeid"));
				tdcInfo.setDiscountRate(localRS.getDouble("discountrate"));
				tdcInfo.setCode(localRS.getString("code"));
				//System.out.println("----------dfhdf----------:"+localRS.getLong("discountrate"));
				v.add(tdcInfo);
			 }
			
			
			localRS.close();
			localRS = null;
	
			finalizeDAO();
	    }
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询失败", e);
		}
		finally
	    {
	        try
	        {
	            finalizeDAO();
	        } catch (ITreasuryDAOException e1)
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	    }
	
		return (v.size() > 0 ? v : null);
	}	
	
}
