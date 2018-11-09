/*
 * Created on 2004-02-02
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.obinstruction.bizlogic;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.obinstruction.dataentity.QueryOBFinanceInstrInfo;
import com.iss.itreasury.settlement.obinstruction.dataentity.QueryOBFinanceSumInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
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
public class QOBFinanceInstr extends BaseQueryObject
{
	public final static int OrderBy_IntructionNo = 1;	
	public final static int OrderBy_IntructionType = 2;
	public final static int OrderBy_RemitType = 3;
	public final static int OrderBy_Amount = 4;
	public final static int OrderBy_ExecuteDate = 5;
	public final static int OrderBy_TranaNo = 6;
	public final static int OrderBy_PayClientName = 7;
	
	//
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	Log4j logger = null;
	/**
	 *  
	 */
	public QOBFinanceInstr()
	{
		super();
		// TODO Auto-generated constructor stub
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	public void getOBFinanceInstrSQL(QueryOBFinanceInstrInfo qInfo)
	{
		boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false);  //是否为航天科工项目
		m_sbSelect = new StringBuffer();
						
		//select		
		m_sbSelect.append(" fin.id as ID,  \n");			
		m_sbSelect.append(" fin.nTransType as TransType,  \n");
		m_sbSelect.append(" client.sName as PayClientName,  \n");
		m_sbSelect.append(" fin.nPayerAcctID as PayerAcctID,  \n");
		m_sbSelect.append(" fin.nPayeeAcctID as PayeeAcctID,  \n");
		m_sbSelect.append(" fin.nRemitType as RemitType,  \n");
		m_sbSelect.append(" fin.mAmount as Amount,  \n");
		m_sbSelect.append(" fin.CPF_NDEALUSERID as DealUserID,  \n");		
		m_sbSelect.append(" fin.mRealInterest as Interest,  \n");
		m_sbSelect.append(" fin.dtExecute as ExecuteDate,  \n");
		m_sbSelect.append(" fin.sNote as Note,  \n");
		m_sbSelect.append(" fin.nStatus as Status,  \n");
		m_sbSelect.append(" fin.nDepositBillStatusID as DepositBillStatusID,  \n");	//得到换开定期存单的ID
		m_sbSelect.append(" fin.cpf_nDefaultTransType as DefaultTransType,  \n");
		m_sbSelect.append(" fin.cpf_sTransNo as TransNo, \n");
		m_sbSelect.append(" fin.dtcheck as CheckDate, \n");
		m_sbSelect.append(" fin.dtsign as signSignDate, \n");
		m_sbSelect.append(" fin.dtmodify as dtModify, \n");
		m_sbSelect.append(" fin.isautocontinue as isautocontinue, \n");
		m_sbSelect.append(" fin.autocontinuetype as autocontinuetype, \n");
		m_sbSelect.append(" fin.autocontinueaccountid as autocontinueaccountid, \n");
		m_sbSelect.append(" fin.lsource as Source \n");
		// from 
		m_sbFrom = new StringBuffer();		
		m_sbFrom.append(" OB_FinanceInstr fin,sett_account acc,client,client c \n");
		// where 
		m_sbWhere = new StringBuffer();		
			
		m_sbWhere.append(" fin.NPAYERACCTID=acc.id \n");
		m_sbWhere.append(" and acc.nclientid=client.id \n");	
		m_sbWhere.append(" and fin.nclientid=c.id \n");
		m_sbWhere.append(" and fin.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		m_sbWhere.append(" and acc.nofficeid=" + qInfo.getOfficeID()+ " \n");
		if(qInfo.getTransType()>0)
		{
			if(qInfo.getTransType()==SETTConstant.SettInstrType.CHANGEFIXDEPOSIT){
				//System.out.println("查询了换开定期存单的数据!");
				qInfo.setTransType(SETTConstant.SettInstrType.OPENFIXDEPOSIT);	//定期开立
				//m_sbWhere.append(" and ( fin.SDEPOSITBILLNO is not null ) ");	//换开定期存单不为空或者不是不存在
				m_sbWhere.append(" and ( fin.NDEPOSITBILLINPUTUSERID is not null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}
			else if(qInfo.getTransType()==SETTConstant.SettInstrType.OPENFIXDEPOSIT){
				//System.out.println("查询了定期存单的数据!");
				//m_sbWhere.append(" and ( fin.SDEPOSITBILLNO is null ) ");	//定期存单为空(定期开立)
				m_sbWhere.append(" and ( fin.NDEPOSITBILLINPUTUSERID is null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}
			if(isHTKG)
			{
				if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY)
				{
					m_sbWhere.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL+","+SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL+") \n");
				}
				else if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
				{
					m_sbWhere.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT+") \n");
				}
				else
				{
					m_sbWhere.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
				}
			}
			else
			{
				m_sbWhere.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
			}
		}
		if(qInfo.getStatus()==0)   //全部
		{
			m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
		}
		else if(qInfo.getStatus()==SETTConstant.SettInstrStatus.SIGN)   //已提交
		{
			m_sbWhere.append("and (fin.nStatus =" + OBConstant.SettInstrStatus.CHECK + " or fin.nStatus =" + OBConstant.SettInstrStatus.SIGN + ") \n");
			m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
		}	
		else
		{
			m_sbWhere.append("and fin.nStatus =" + qInfo.getStatus()+ " \n");
			m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
		}
//		if(qInfo.getConfirmDateFrom()!=null)
//		{
//			m_sbWhere.append("and to_char(fin.dtConfirm,'yyyy-mm-dd')>= '"+DataFormat.formatDate(qInfo.getConfirmDateFrom())+"'");
//		}
//		if(qInfo.getConfirmDateFrom()!=null)
//		{
//			m_sbWhere.append("and to_char(fin.dtConfirm,'yyyy-mm-dd')<= '"+DataFormat.formatDate(qInfo.getConfirmDateFrom())+"'");
//		}
		if(qInfo.getAmountFrom()>0)
		{
			m_sbWhere.append("and fin.mAmount>=" + qInfo.getAmountFrom()+ " \n");
		}
		if(qInfo.getAmountTo()>0)
		{
			m_sbWhere.append("and fin.mAmount<=" + qInfo.getAmountTo()+ " \n");
		}
		// 判断选择的查询日期
		if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("execute")){
			// 执行日期
			if(qInfo.getExecuteDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtExecute,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getExecuteDateFrom())+"'");
			}
			if(qInfo.getExecuteDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtExecute,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getExecuteDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("check")){
			// 复核日期
			if(qInfo.getCheckDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtCheck,'yyyy-mm-dd') >= '"+DataFormat.formatDate(qInfo.getCheckDateFrom())+"'");
			}
			if(qInfo.getCheckDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtCheck,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getCheckDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("sign")){
			// 签认日期
			if(qInfo.getSignDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtSign,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getSignDateFrom())+"'");
			}
			if(qInfo.getSignDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtSign,'yyyy-mm-dd') <='"+DataFormat.formatDate(qInfo.getSignDateTo())+"'");
			}
		}
		if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
			m_sbWhere.append("and acc.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
			m_sbWhere.append("and acc.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append("and c.sCode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append("and c.sCode<='" + qInfo.getClientNoTo() + "'");
		
		if (qInfo.getTransNo() != null && qInfo.getTransNo().length() > 0)
		{
			m_sbWhere.append("and fin.cpf_sTransNo='" + qInfo.getTransNo() + "'");
		}
		if(qInfo.getInstructionNo()>0)
		{
			m_sbWhere.append("and fin.id=" + qInfo.getInstructionNo()+ " \n");
		}
		if(qInfo.getSource()>0)
		{
			m_sbWhere.append("and fin.lsource=" + qInfo.getSource()+ " \n");
		}
		if (qInfo.getApplyCode() != null && qInfo.getApplyCode().length() > 0)
		{
			m_sbWhere.append("and fin.sApplyCode like '%" + qInfo.getApplyCode() + "%'");
		}
		
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = "";		
		if(qInfo.getDesc()==1)
		{
			strDesc = " desc ";
		}
		else
		{
			strDesc = " asc ";
		}		
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_IntructionNo :
				m_sbOrderBy.append(" \n order by ID" + strDesc);
				break;			
			case OrderBy_IntructionType :
				m_sbOrderBy.append(" \n order by TransType" + strDesc);
				break;
			case OrderBy_RemitType :
				m_sbOrderBy.append(" \n order by RemitType" + strDesc);
				break;
			case OrderBy_Amount :
				m_sbOrderBy.append(" \n order by Amount" + strDesc);
				break;
			case OrderBy_ExecuteDate :
				m_sbOrderBy.append(" \n order by ExecuteDate" + strDesc);
				break;
			case OrderBy_TranaNo :
				m_sbOrderBy.append(" \n order by TransNo" + strDesc);
				break;
			case OrderBy_PayClientName :
				m_sbOrderBy.append(" \n order by PayClientName" + strDesc);
				break;		
					
		}				
		//logger.debug("SQL="+m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
	}	
	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryOBFinanceInstrInfo(QueryOBFinanceInstrInfo qInfo) throws Exception
	{

		getOBFinanceInstrSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		log.print("Java Select" + m_sbSelect);
		log.print("Java From" + m_sbFrom);
		log.print("Java where" + m_sbWhere);
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo",
			null);						
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		log.print("Java " + m_sbOrderBy);
		return pageLoader;
	}
	
	
	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	// 
	public PageLoader queryOBFinanceInstrInfohl(QueryOBFinanceInstrInfo qInfo) throws Exception
	{

		getOBFinanceInstrSQLhl(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		log.print("Java Select" + m_sbSelect);
		log.print("Java From" + m_sbFrom);
		log.print("Java where" + m_sbWhere);
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
	  		"com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo",
			null);						
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		//log.print("Java " + m_sbOrderBy);
		return pageLoader;
	}
	
	public void getOBFinanceInstrSQLhl(QueryOBFinanceInstrInfo qInfo)
	{
		boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false);  //是否为航天科工项目
		m_sbSelect = new StringBuffer();
						
		//select		
		m_sbSelect.append(" fin.id as ID,  \n");			
		m_sbSelect.append(" fin.nTransType as TransType,  \n");
		m_sbSelect.append(" client.sName as PayClientName,  \n");
		m_sbSelect.append(" fin.nPayerAcctID as PayerAcctID,  \n");
		m_sbSelect.append(" fin.nPayeeAcctID as PayeeAcctID,  \n");
		m_sbSelect.append(" fin.nRemitType as RemitType,  \n");
		m_sbSelect.append(" fin.mAmount as Amount,  \n");
		m_sbSelect.append(" fin.CPF_NDEALUSERID as DealUserID,  \n");		
		m_sbSelect.append(" fin.mRealInterest as Interest,  \n");
		m_sbSelect.append(" fin.dtExecute as ExecuteDate,  \n");
		m_sbSelect.append(" fin.sNote as Note,  \n");
		m_sbSelect.append(" fin.nStatus as Status,  \n");
		m_sbSelect.append(" fin.nDepositBillStatusID as DepositBillStatusID,  \n");	//得到换开定期存单的ID
		m_sbSelect.append(" fin.cpf_nDefaultTransType as DefaultTransType,  \n");
		m_sbSelect.append(" fin.cpf_sTransNo as TransNo, \n");
		m_sbSelect.append(" fin.dtcheck as CheckDate, \n");
		m_sbSelect.append(" fin.dtmodify as dtModify, \n");
		m_sbSelect.append(" fin.remitArea as remitArea, \n");
		m_sbSelect.append(" fin.remitSpeed as remitSpeed, \n");
		m_sbSelect.append(" fin.lsource as Source, \n");
		m_sbSelect.append(" p.bankname as bankname, \n");
		m_sbSelect.append(" p.spayeebankname as PayeeBankName \n");
		// from 
		m_sbFrom = new StringBuffer();		
		m_sbFrom.append(" OB_FinanceInstr fin left join ob_payeeinfo p on fin.npayeeacctid = p.id, \n");
		m_sbFrom.append(" sett_account acc,client,client c \n ");
		// where 
		m_sbWhere = new StringBuffer();		
		m_sbWhere.append("fin.cpf_nofficeid="+ qInfo.getOfficeID() + " \n");	
		m_sbWhere.append(" and fin.NPAYERACCTID=acc.id \n");
		m_sbWhere.append(" and acc.nclientid=client.id \n");	
		m_sbWhere.append(" and fin.nclientid=c.id \n");
		m_sbWhere.append(" and fin.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		
		/*if(qInfo.getTransType()>0)
		{
			if(qInfo.getTransType()==SETTConstant.SettInstrType.CHANGEFIXDEPOSIT){
				System.out.println("查询了换开定期存单的数据!");
				qInfo.setTransType(SETTConstant.SettInstrType.OPENFIXDEPOSIT);	//定期开立
				//m_sbWhere.append(" and ( fin.SDEPOSITBILLNO is not null ) ");	//换开定期存单不为空或者不是不存在
				m_sbWhere.append(" and ( fin.NDEPOSITBILLINPUTUSERID is not null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}
			else if(qInfo.getTransType()==SETTConstant.SettInstrType.OPENFIXDEPOSIT){
				System.out.println("查询了定期存单的数据!");
				//m_sbWhere.append(" and ( fin.SDEPOSITBILLNO is null ) ");	//定期存单为空(定期开立)
				m_sbWhere.append(" and ( fin.NDEPOSITBILLINPUTUSERID is null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}else{}
			m_sbWhere.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
		}*/
		
		//Boxu Update 2009年02月19日 批量接收只接收"资金划拨-银行付款"和"资金划拨-内部转账"
		if(isHTKG)
		{
			if(qInfo.getTransType()>0)
			{
				if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY)
				{
					m_sbWhere.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL+","+SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL+") \n");
				}
				else if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
				{
					m_sbWhere.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT+") \n");
				}
				else
				{
					m_sbWhere.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
				}
					
			}
			else
			{
				m_sbWhere.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL+","+SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL+") \n");
			}
		}
		else 
		{
			if(qInfo.getTransType()>0)
			{
				m_sbWhere.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
			}
			else
			{
				m_sbWhere.append(" and fin.nTransType in (" + SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY + ", " + SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT + ") \n");
			}
		}
		
		if(qInfo.getStatus()==0)   //全部
		{
			m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
		}
		else if(qInfo.getStatus()==SETTConstant.SettInstrStatus.SIGN)   //已提交
		{
			m_sbWhere.append("and (fin.nStatus =" + OBConstant.SettInstrStatus.CHECK + " or fin.nStatus =" + OBConstant.SettInstrStatus.SIGN + ") \n");
			m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
		}
		else if(qInfo.getStatus()==SETTConstant.SettInstrStatus.DEAL)   //处理中
		{
			m_sbWhere.append("and fin.nStatus =" + OBConstant.SettInstrStatus.DEAL +  " \n");				
		}
		else
		{
			m_sbWhere.append("and fin.nStatus =" + qInfo.getStatus()+ " \n");
			m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
		}
//		if(qInfo.getConfirmDateFrom()!=null)
//		{
//			m_sbWhere.append("and to_char(fin.dtConfirm,'yyyy-mm-dd')>= '"+DataFormat.formatDate(qInfo.getConfirmDateFrom())+"'");
//		}
//		if(qInfo.getConfirmDateFrom()!=null)
//		{
//			m_sbWhere.append("and to_char(fin.dtConfirm,'yyyy-mm-dd')<= '"+DataFormat.formatDate(qInfo.getConfirmDateFrom())+"'");
//		}
		if(qInfo.getAmountFrom()>0)
		{
			m_sbWhere.append("and fin.mAmount>=" + qInfo.getAmountFrom()+ " \n");
		}
		if(qInfo.getAmountTo()>0)
		{
			m_sbWhere.append("and fin.mAmount<=" + qInfo.getAmountTo()+ " \n");
		}
		// 判断选择的查询日期
		if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("execute")){
			// 执行日期
			if(qInfo.getExecuteDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtExecute,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getExecuteDateFrom())+"'");
			}
			if(qInfo.getExecuteDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtExecute,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getExecuteDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("check")){
			// 复核日期
			if(qInfo.getCheckDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtCheck,'yyyy-mm-dd') >= '"+DataFormat.formatDate(qInfo.getCheckDateFrom())+"'");
			}
			if(qInfo.getCheckDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtCheck,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getCheckDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("sign")){
			// 签认日期
			if(qInfo.getSignDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtSign,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getSignDateFrom())+"'");
			}
			if(qInfo.getSignDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtSign,'yyyy-mm-dd') <='"+DataFormat.formatDate(qInfo.getSignDateTo())+"'");
			}
		}
		if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
			m_sbWhere.append("and acc.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
			m_sbWhere.append("and acc.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append("and c.sCode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append("and c.sCode<='" + qInfo.getClientNoTo() + "'");
		
		if (qInfo.getTransNo() != null && qInfo.getTransNo().length() > 0)
		{
			m_sbWhere.append("and fin.cpf_sTransNo='" + qInfo.getTransNo() + "'");
		}
		if(qInfo.getInstructionNo()>0)
		{
			m_sbWhere.append("and fin.id=" + qInfo.getInstructionNo()+ " \n");
		}
		if(qInfo.getSource()>0)
		{
			m_sbWhere.append("and fin.lsource=" + qInfo.getSource()+ " \n");
		}
		if (qInfo.getApplyCode() != null && qInfo.getApplyCode().length() > 0)
		{
			m_sbWhere.append("and fin.sApplyCode like '%" + qInfo.getApplyCode() + "%'");
		}
		if(qInfo.getBankName() !=null && qInfo.getBankName().trim().length()>0)
		{
			m_sbWhere.append(" and p.bankname ='"+qInfo.getBankName()+"'");
		}
		
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = "";		
		if(qInfo.getDesc()==1)
		{
			strDesc = " desc ";
		}
		else
		{
			strDesc = " asc ";
		}		
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_IntructionNo :
				m_sbOrderBy.append(" \n order by ID" + strDesc);
				break;			
			case OrderBy_IntructionType :
				m_sbOrderBy.append(" \n order by TransType" + strDesc);
				break;
			case OrderBy_RemitType :
				m_sbOrderBy.append(" \n order by RemitType" + strDesc);
				break;
			case OrderBy_Amount :
				m_sbOrderBy.append(" \n order by Amount" + strDesc);
				break;
			case OrderBy_ExecuteDate :
				m_sbOrderBy.append(" \n order by ExecuteDate" + strDesc);
				break;
			case OrderBy_TranaNo :
				m_sbOrderBy.append(" \n order by TransNo" + strDesc);
				break;
			case OrderBy_PayClientName :
				m_sbOrderBy.append(" \n order by PayClientName" + strDesc);
				break;		
					
		}				
		//logger.debug("SQL="+m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
	}	
	
	
	
	
	
	public PageLoader queryOBFinanceInstrInfo11(QueryOBFinanceInstrInfo qInfo) throws Exception
	{
             
		getOBFinanceInstrSQL11(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		log.print("Java Select" + m_sbSelect);
		log.print("Java From" + m_sbFrom);
		log.print("Java where" + m_sbWhere);
		System.out.println("*****************************Select"+m_sbSelect+"From"+m_sbFrom+"where"+m_sbWhere);
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int)Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.obinstruction.dataentity.OBFinanceInfo",
			null);						
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		log.print("Java " + m_sbOrderBy);
		return pageLoader;
	}
	
	public void getOBFinanceInstrSQL11(QueryOBFinanceInstrInfo qInfo)
	{
		boolean isHTKG = Config.getBoolean(ConfigConstant.SETTLEMENT_ISHTKG, false);  //是否为航天科工项目
		m_sbSelect = new StringBuffer();
						
		//select		
		m_sbSelect.append(" distinct fin.id as ID,  \n");			
		m_sbSelect.append(" fin.nTransType as TransType,  \n");
		m_sbSelect.append(" client.sName as PayClientName,  \n");
		m_sbSelect.append(" fin.nPayerAcctID as PayerAcctID,  \n");
		m_sbSelect.append(" fin.nPayeeAcctID as PayeeAcctID,  \n");
		m_sbSelect.append(" fin.nRemitType as RemitType,  \n");
		m_sbSelect.append(" fin.mAmount as Amount,  \n");
		m_sbSelect.append(" fin.CPF_NDEALUSERID as DealUserID,  \n");		
		m_sbSelect.append(" fin.mRealInterest as Interest,  \n");
		m_sbSelect.append(" fin.dtExecute as ExecuteDate,  \n");
		m_sbSelect.append(" fin.sNote as Note,  \n");
		m_sbSelect.append(" fin.nStatus as Status,  \n");
		m_sbSelect.append(" fin.nDepositBillStatusID as DepositBillStatusID,  \n");	//得到换开定期存单的ID
		m_sbSelect.append(" fin.cpf_nDefaultTransType as DefaultTransType,  \n");
		m_sbSelect.append(" fin.cpf_sTransNo as TransNo, \n");
		m_sbSelect.append(" fin.dtcheck as CheckDate, \n");
		m_sbSelect.append(" fin.lsource as Source \n");
		// from 
		m_sbFrom = new StringBuffer();		
		m_sbFrom.append(" OB_FinanceInstr fin,sett_account acc,client,client c,sett_vtransaction s  \n");
		// where 
		m_sbWhere = new StringBuffer();		
		m_sbWhere.append("fin.cpf_nofficeid="+ qInfo.getOfficeID() + " \n");	
		m_sbWhere.append(" and fin.NPAYERACCTID=acc.id \n");
		m_sbWhere.append(" and acc.nclientid=client.id \n");	
		m_sbWhere.append(" and fin.nclientid=c.id \n");
		m_sbWhere.append(" and to_char(fin.id)=s.applycode(+) \n");
		m_sbWhere.append(" and fin.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
		if(qInfo.getTransType()>0)
		{
			if(qInfo.getTransType()==SETTConstant.SettInstrType.CHANGEFIXDEPOSIT){
				System.out.println("查询了换开定期存单的数据!");
				qInfo.setTransType(SETTConstant.SettInstrType.OPENFIXDEPOSIT);	//定期开立
				//m_sbWhere.append(" and ( fin.SDEPOSITBILLNO is not null ) ");	//换开定期存单不为空或者不是不存在
				m_sbWhere.append(" and ( fin.NDEPOSITBILLINPUTUSERID is not null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}
			else if(qInfo.getTransType()==SETTConstant.SettInstrType.OPENFIXDEPOSIT){
				System.out.println("查询了定期存单的数据!");
				//m_sbWhere.append(" and ( fin.SDEPOSITBILLNO is null ) ");	//定期存单为空(定期开立)
				m_sbWhere.append(" and ( fin.NDEPOSITBILLINPUTUSERID is null ) ");	//换开定期存单输入人ID不为空或者不是不存在
			}else{}
			if(isHTKG)
			{
				if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY)
				{
					m_sbWhere.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_BANKEXTERNAL+","+SETTConstant.SettInstrType.BOOKING_BANKEXTERNAL+") \n");
				}
				else if(qInfo.getTransType()==SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT)
				{
					m_sbWhere.append(" and fin.nTransType in ("+SETTConstant.SettInstrType.TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_TRANSFER_INTERNALVIREMENT+","+SETTConstant.SettInstrType.BOOKING_INTERNALVIREMENT+","+SETTConstant.SettInstrType.SCM_BOOKING_INTERNALVIREMENT+") \n");
				}
				else
				{
					m_sbWhere.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
				}
			}
			else
			{
				m_sbWhere.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
			}		

		}
		if(qInfo.getStatus()==4)   //全部
		{
			m_sbWhere.append("and fin.nStatus =" + OBConstant.SettInstrStatus.DEAL +  " \n");
			m_sbWhere.append("and  fin.cpf_ndealuserid ! =" + qInfo.getUserID() +  " \n");
			
		}
		else if(qInfo.getStatus()==5)  
		{
			m_sbWhere.append("and fin.nStatus =" + OBConstant.SettInstrStatus.FINISH +  " \n");
			m_sbWhere.append("and s.statusid =" + SETTConstant.TransactionStatus.CHECK +  " \n");
			m_sbWhere.append("and to_char(s.execute,'yyyy-mm-dd') ='"+DataFormat.formatDate(qInfo.getOpenDate())+"' \n");
			if ( !Config.getBoolean(ConfigConstant.SETT_OBINSTRUCTION_AUTOCHECK,false) )
			{
				m_sbWhere.append("and s.checkuserid =" + qInfo.getUserID() + " \n");
			}
			//m_sbWhere.append("and fin.cpf_ndealuserid  =" + qInfo.getUserID() +  " \n");
		}
//		if(qInfo.getConfirmDateFrom()!=null)
//		{
//			m_sbWhere.append("and to_char(fin.dtConfirm,'yyyy-mm-dd')>= '"+DataFormat.formatDate(qInfo.getConfirmDateFrom())+"'");
//		}
//		if(qInfo.getConfirmDateFrom()!=null)
//		{
//			m_sbWhere.append("and to_char(fin.dtConfirm,'yyyy-mm-dd')<= '"+DataFormat.formatDate(qInfo.getConfirmDateFrom())+"'");
//		}
		if(qInfo.getAmountFrom()>0)
		{
			m_sbWhere.append("and fin.mAmount>=" + qInfo.getAmountFrom()+ " \n");
		}
		if(qInfo.getAmountTo()>0)
		{
			m_sbWhere.append("and fin.mAmount<=" + qInfo.getAmountTo()+ " \n");
		}
		// 判断选择的查询日期
		if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("execute")){
			// 执行日期
			if(qInfo.getExecuteDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtExecute,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getExecuteDateFrom())+"'");
			}
			if(qInfo.getExecuteDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtExecute,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getExecuteDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("check")){
			// 复核日期
			if(qInfo.getCheckDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtCheck,'yyyy-mm-dd') >= '"+DataFormat.formatDate(qInfo.getCheckDateFrom())+"'");
			}
			if(qInfo.getCheckDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtCheck,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getCheckDateTo())+"'");
			}
		}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("sign")){
			// 签认日期
			if(qInfo.getSignDateFrom()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtSign,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getSignDateFrom())+"'");
			}
			if(qInfo.getSignDateTo()!=null)
			{
				m_sbWhere.append("and to_char(fin.dtSign,'yyyy-mm-dd') <='"+DataFormat.formatDate(qInfo.getSignDateTo())+"'");
			}
		}
		if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
			m_sbWhere.append("and acc.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
			m_sbWhere.append("and acc.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
		
		if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append("and c.sCode>='" + qInfo.getClientNoFrom() + "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append("and c.sCode<='" + qInfo.getClientNoTo() + "'");
		
		if (qInfo.getTransNo() != null && qInfo.getTransNo().length() > 0)
		{
			m_sbWhere.append("and fin.cpf_sTransNo='" + qInfo.getTransNo() + "'");
		}
		if(qInfo.getInstructionNo()>0)
		{
			m_sbWhere.append("and fin.id=" + qInfo.getInstructionNo()+ " \n");
		}
		if(qInfo.getSource()>0)
		{
			m_sbWhere.append("and fin.lsource=" + qInfo.getSource()+ " \n");
		}
		if (qInfo.getApplyCode() != null && qInfo.getApplyCode().length() > 0)
		{
			m_sbWhere.append("and fin.sApplyCode like '%" + qInfo.getApplyCode() + "%'");
		}
		
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = "";		
		if(qInfo.getDesc()==1)
		{
			strDesc = " desc ";
		}
		else
		{
			strDesc = " asc ";
		}		
		switch ((int) qInfo.getOrderField())
		{
			case OrderBy_IntructionNo :
				m_sbOrderBy.append(" \n order by ID" + strDesc);
				break;			
			case OrderBy_IntructionType :
				m_sbOrderBy.append(" \n order by TransType" + strDesc);
				break;
			case OrderBy_RemitType :
				m_sbOrderBy.append(" \n order by RemitType" + strDesc);
				break;
			case OrderBy_Amount :
				m_sbOrderBy.append(" \n order by Amount" + strDesc);
				break;
			case OrderBy_ExecuteDate :
				m_sbOrderBy.append(" \n order by ExecuteDate" + strDesc);
				break;
			case OrderBy_TranaNo :
				m_sbOrderBy.append(" \n order by TransNo" + strDesc);
				break;
			case OrderBy_PayClientName :
				m_sbOrderBy.append(" \n order by PayClientName" + strDesc);
				break;		
					
		}				
		logger.debug("SQL="+m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString());
	}	
	
	public QueryOBFinanceSumInfo querySumOBFinanceInstrInfo(QueryOBFinanceInstrInfo qInfo) throws Exception
	{
		QueryOBFinanceSumInfo sumObj = new QueryOBFinanceSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		//String strSelect = "";
		//String strDepositWhere = "";
		//String strLoanWhere = "";
		String sql = "";
		//

		try
		{
			
			m_sbSelect = new StringBuffer();
			
			//select		
			m_sbSelect.append(" select sum(Amount) allFinSum from (  \n");			
			m_sbSelect.append(" select fin.mAmount as Amount \n");
			// from 
			m_sbFrom = new StringBuffer();		
			m_sbFrom.append("  from OB_FinanceInstr fin,sett_account acc,client,client c \n");
			// where 
			m_sbWhere = new StringBuffer();		
			m_sbWhere.append(" where fin.cpf_nofficeid="+ qInfo.getOfficeID() + " \n");	
			m_sbWhere.append(" and fin.NPAYERACCTID=acc.id \n");
			m_sbWhere.append(" and acc.nclientid=client.id \n");	
			m_sbWhere.append(" and fin.nclientid=c.id \n");
			m_sbWhere.append(" and fin.nCurrencyID=" + qInfo.getCurrencyID()+ " \n");
			
			if(qInfo.getTransType()>0)
			{
				m_sbWhere.append(" and fin.nTransType=" + qInfo.getTransType()+ " \n");
			}
			else
			{
				m_sbWhere.append(" and fin.nTransType in (" + SETTConstant.SettInstrType.CAPTRANSFER_BANKPAY + ", " + SETTConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT + ") \n");
			}
			
			if(qInfo.getStatus()==0)   //全部
			{
				m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
			}
			else if(qInfo.getStatus()==SETTConstant.SettInstrStatus.SIGN)   //已提交
			{
				m_sbWhere.append("and (fin.nStatus =" + OBConstant.SettInstrStatus.CHECK + " or fin.nStatus =" + OBConstant.SettInstrStatus.SIGN + ") \n");
				m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
			}
			else if(qInfo.getStatus()==SETTConstant.SettInstrStatus.DEAL)   //处理中
			{
				m_sbWhere.append("and fin.nStatus =" + OBConstant.SettInstrStatus.DEAL +  " \n");				
			}
			else
			{
				m_sbWhere.append("and fin.nStatus =" + qInfo.getStatus()+ " \n");
				m_sbWhere.append("and fin.nIsCanAccept = 1  \n");
			}
			if(qInfo.getAmountFrom()>0)
			{
				m_sbWhere.append("and fin.mAmount>=" + qInfo.getAmountFrom()+ " \n");
			}
			if(qInfo.getAmountTo()>0)
			{
				m_sbWhere.append("and fin.mAmount<=" + qInfo.getAmountTo()+ " \n");
			}
			// 判断选择的查询日期
			if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("execute")){
				// 执行日期
				if(qInfo.getExecuteDateFrom()!=null)
				{
					m_sbWhere.append("and to_char(fin.dtExecute,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getExecuteDateFrom())+"'");
				}
				if(qInfo.getExecuteDateTo()!=null)
				{
					m_sbWhere.append("and to_char(fin.dtExecute,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getExecuteDateTo())+"'");
				}
			}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("check")){
				// 复核日期
				if(qInfo.getCheckDateFrom()!=null)
				{
					m_sbWhere.append("and to_char(fin.dtCheck,'yyyy-mm-dd') >= '"+DataFormat.formatDate(qInfo.getCheckDateFrom())+"'");
				}
				if(qInfo.getCheckDateTo()!=null)
				{
					m_sbWhere.append("and to_char(fin.dtCheck,'yyyy-mm-dd') <= '"+DataFormat.formatDate(qInfo.getCheckDateTo())+"'");
				}
			}else if(qInfo.getDateRadio() != null && qInfo.getDateRadio().equals("sign")){
				// 签认日期
				if(qInfo.getSignDateFrom()!=null)
				{
					m_sbWhere.append("and to_char(fin.dtSign,'yyyy-mm-dd') >='"+DataFormat.formatDate(qInfo.getSignDateFrom())+"'");
				}
				if(qInfo.getSignDateTo()!=null)
				{
					m_sbWhere.append("and to_char(fin.dtSign,'yyyy-mm-dd') <='"+DataFormat.formatDate(qInfo.getSignDateTo())+"'");
				}
			}
			if (qInfo.getAccountNoFrom() != null && qInfo.getAccountNoFrom().length() > 0)
				m_sbWhere.append("and acc.sAccountNo>='" + qInfo.getAccountNoFrom() + "'");
			if (qInfo.getAccountNoTo() != null && qInfo.getAccountNoTo().length() > 0)
				m_sbWhere.append("and acc.sAccountNo<='" + qInfo.getAccountNoTo() + "'");
			
			if (qInfo.getClientNoFrom() != null && qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append("and c.sCode>='" + qInfo.getClientNoFrom() + "'");
			if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append("and c.sCode<='" + qInfo.getClientNoTo() + "'");
			
			if (qInfo.getTransNo() != null && qInfo.getTransNo().length() > 0)
			{
				m_sbWhere.append("and fin.cpf_sTransNo='" + qInfo.getTransNo() + "'");
			}
			if(qInfo.getInstructionNo()>0)
			{
				m_sbWhere.append("and fin.id=" + qInfo.getInstructionNo()+ " \n");
			}
			if(qInfo.getSource()>0)
			{
				m_sbWhere.append("and fin.lsource=" + qInfo.getSource()+ " \n");
			}
			if (qInfo.getApplyCode() != null && qInfo.getApplyCode().length() > 0)
			{
				m_sbWhere.append("and fin.sApplyCode like '%" + qInfo.getApplyCode() + "%'");
			}
			
			m_sbWhere.append(")\n");					
			con = this.getConnection();
			sql = m_sbSelect.toString() + m_sbFrom.toString()+ m_sbWhere.toString();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumObj.setAllFinanceSumInfo(rs.getDouble("allFinSum"));

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
	 * 增加查询金额总和
	 * @author weizhang
	 * @param qinfo
	 * @return
	 * @throws Exception
	 */
	public double queryAmountCountInfo(QueryOBFinanceInstrInfo qInfo) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double amount = 0.00;
		//

		try
		{
			getOBFinanceInstrSQL(qInfo);
			// select 
			strSelect = " select sum(round(MAMOUNT,2)) MAMOUNT \n";			

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				amount = rs.getDouble("MAMOUNT");
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
		return amount;
	}
	
	/**
	 * 获得总记录数
	 * @author weizhang
	 * @param qinfo
	 * @return
	 * @throws Exception
	 */
	public long queryCount(QueryOBFinanceInstrInfo qInfo) throws Exception
	{

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		long count =0;
		//

		try
		{
			getOBFinanceInstrSQL(qInfo);
			// select 
			strSelect = " select count(*) count \n";			

			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				count =rs.getLong("count");;
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
		return count;
	}
	
}