/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.project.wisgfc.settlement.postsupervise.analysis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.iss.itreasury.settlement.integration.client.info.SettResultInfo;
import com.iss.itreasury.settlement.integration.client.info.SettTransInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.resultinfo.QUpGatherAccountDetailInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo;
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
public class QTransaction extends BaseQueryObject
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
	public QTransaction()
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
		m_sbSelect.append(
			" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		m_sbSelect.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		m_sbSelect.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		m_sbSelect.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		m_sbSelect.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName \n");
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_VTRANSACTION \n");
		// where 
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" StatusID>0 ");
		
		if (info.getQueryType() == 200)		
		{
			//当页面上点选了 查询已删除定期开立、通知开立交易 按钮功能时走此分支
			m_sbWhere.append(" StatusID =0 ");
			m_sbWhere.append(
					" and TransactionTypeID in ("
						+ SETTConstant.TransactionType.OPENFIXEDDEPOSIT				//定期开立
						+ ","
						+ SETTConstant.TransactionType.FIXEDCONTINUETRANSFER		//定期转存
						+ ","
						+ SETTConstant.TransactionType.OPENNOTIFYACCOUNT			//通知存款开立
						+ ")");
			System.out.println("执行的是定期，通知开立交易的记录!");
		}
		else
		{
			m_sbWhere.append(" StatusID>0 ");
		}
		
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
		//选择了账户交易类型后，交易类型便无效
		log.info("getTransactionSQL++++++++++++"+info.getAccountTransTypeID());
		if (info.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) info.getAccountTransTypeID())
			{
				//活期存款：可以查询出所有使用活期存款账户的交易
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
							+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					m_sbWhere.append(
						" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//定期存款：可以查询出和定期存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//通知存款：可以查询出和通知存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//信托贷款：可以查询出和信托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//委托贷款：可以查询出和委托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//贴现：可以查询出和贴现业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					// 短期贷款：可以查询出和短期贷款业务相关的交易。
					//循环贷款：可以查询出和循环贷款业务相关的交易。
					//委托业务：可以查询出委托业务菜单下所有的交易。
					//对外付款：可以查询银行付款、支票付款、现金付款、票汇付款业务。
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					m_sbWhere.append(
						" and TransactionTypeID in ("
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ ")");
					break;
			}
		}
		else
		{
			if (info.getTransactionTypeIDs().length() > 0) //add by rxie for query TransSpecialOperation subTrans
				m_sbWhere.append(" and ( TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
		}
		if (info.getBankID() > 0)
			m_sbWhere.append(" and BankID=" + info.getBankID() + "");
		if (info.getInputuserID() > 0)
			m_sbWhere.append(" and InputuserID=" + info.getInputuserID() + "");
		if (info.getCheckuserID() > 0)
			m_sbWhere.append(" and CheckuserID=" + info.getCheckuserID() + "");
		if (info.getStatusID() > 0)
			m_sbWhere.append(" and StatusID=" + info.getStatusID() + "");
		
		if (info.getPayClientIDStart() > 0 && (info.getPayClientNoStart() == null || info.getPayClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayclientID>=" + info.getPayClientIDStart() + "");
		if (info.getPayClientIDEnd() > 0 && (info.getPayClientNoEnd() == null || info.getPayClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayclientID<=" + info.getPayClientIDEnd() + "");
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID<=" + info.getPayAccountIDEnd() + "");
		if (info.getReceiveClientIDStart() > 0 && (info.getReceiveClientNoStart() == null || info.getReceiveClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveClientID>=" + info.getReceiveClientIDStart() + "");
		if (info.getReceiveClientIDEnd() > 0 && (info.getReceiveClientNoEnd() == null || info.getReceiveClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveClientID<=" + info.getReceiveClientIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");

		if (info.getContractID() > 0)
			m_sbWhere.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and LoanFormID=" + info.getPayFormID() + "");
		if (info.getPayAmountStart() > 0.0)
			m_sbWhere.append(" and PayAmount>=" + info.getPayAmountStart() + "");
		if (info.getPayAmountEnd() > 0.0)
			m_sbWhere.append(" and PayAmount<=" + info.getPayAmountEnd() + "");
		if (info.getReceiveAmountStart() > 0.0)
			m_sbWhere.append(" and ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (info.getReceiveAmountEnd() > 0.0)
			m_sbWhere.append(" and ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getQueryType() == 100)
		{
			//从账户金额查询进入，逻辑是，收付账户是“或”的关系
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				m_sbWhere.append(" and (PayAccountNo='" + info.getPayAccountNoStart() + "' or ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (info.getPayClientNoStart() != null && info.getPayClientNoStart().trim().length() > 0
					&& (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and PayClientCode>='" + info.getPayClientNoStart() + "'");
			if (info.getPayClientNoEnd() != null && info.getPayClientNoEnd().trim().length() > 0
					&& (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and PayClientCode<='" + info.getPayClientNoEnd() + "'");
			if (info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			if (info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and PayAccountNo<='" + info.getPayAccountNoEnd() + "'");
			if (info.getReceiveClientNoStart() != null && info.getReceiveClientNoStart().trim().length() > 0
					&& (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and ReceiveClientCode>='" + info.getReceiveClientNoStart() + "'");
			if (info.getReceiveClientNoEnd() != null && info.getReceiveClientNoEnd().trim().length() > 0
					&& (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and ReceiveClientCode<='" + info.getReceiveClientNoEnd() + "'");
			if (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			if (info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'");
		}
		if (info.getTransNoStart() != null && info.getTransNoStart().trim().length() > 0)
			m_sbWhere.append(" and TransNo>='" + info.getTransNoStart() + "'");
		if (info.getTransNoEnd() != null && info.getTransNoEnd().trim().length() > 0)
			m_sbWhere.append(" and TransNo<='" + info.getTransNoEnd() + "'");
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			m_sbWhere.append(" and DepositNo='" + info.getDepositNo() + "'");
		if (info.getInterestStartStart() != null)
			m_sbWhere.append(" and InterestStart>=to_date('" + DataFormat.getDateString(info.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (info.getInterestStartEnd() != null)
			m_sbWhere.append(" and InterestStart<=to_date('" + DataFormat.getDateString(info.getInterestStartEnd()) + "','yyyy-mm-dd')");
		if (info.getExecuteStart() != null)
			m_sbWhere.append(" and Execute>=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteEnd() != null)
			m_sbWhere.append(" and Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";
		switch ((int) info.getOrderID())
		{
			default :
				m_sbOrderBy.append(" order by TransNo" + strDesc + " \n");
				break;
		}
		log.print("$$$$$$$$$$QTransaction$$$$$$$$$ select"+m_sbSelect.toString()
				+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
	/**
	 * 产生查询SQL ,不包括焕发证书
	 * @param info
	 */
	public void getTransactionSQLForQuery(QueryTransactionConditionInfo info)
	{
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" sv.ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		m_sbSelect.append(
			" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		m_sbSelect.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		m_sbSelect.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		m_sbSelect.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		m_sbSelect.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName,sa.NSTATUSID \n");
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_VTRANSACTION sv left join sett_analysis sa on  sv.id = sa.ntransid \n");
		// where 
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" StatusID>0 ");
		
		if (info.getQueryType() == 200)		
		{
			//当页面上点选了 查询已删除定期开立、通知开立交易 按钮功能时走此分支
			m_sbWhere.append(" StatusID =0 and (sa.ntransid is null or sa.nstatusid=0) ");
			m_sbWhere.append(
					" and TransactionTypeID in ("
						+ SETTConstant.TransactionType.OPENFIXEDDEPOSIT				//定期开立
						+ ","
						+ SETTConstant.TransactionType.FIXEDCONTINUETRANSFER		//定期转存
						+ ","
						+ SETTConstant.TransactionType.OPENNOTIFYACCOUNT			//通知存款开立
						+ ")");
			System.out.println("执行的是定期，通知开立交易的记录!");
		}
		else
		{
			m_sbWhere.append(" StatusID in (3,7) and (sa.ntransid is null or sa.nstatusid=0) ");
		}
		if(info.getNSTATUSID()>0){
			m_sbWhere.append(" and sa.NSTATUSID=" + info.getNSTATUSID() + "");
			
		}
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
		//选择了账户交易类型后，交易类型便无效
		log.info("getTransactionSQL++++++++++++"+info.getAccountTransTypeID());
		if (info.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) info.getAccountTransTypeID())
			{
				//活期存款：可以查询出所有使用活期存款账户的交易
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
							+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					m_sbWhere.append(
						" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//定期存款：可以查询出和定期存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//通知存款：可以查询出和通知存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//信托贷款：可以查询出和信托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//委托贷款：可以查询出和委托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//贴现：可以查询出和贴现业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					// 短期贷款：可以查询出和短期贷款业务相关的交易。
					//循环贷款：可以查询出和循环贷款业务相关的交易。
					//委托业务：可以查询出委托业务菜单下所有的交易。
					//对外付款：可以查询银行付款、支票付款、现金付款、票汇付款业务。
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					m_sbWhere.append(
						" and TransactionTypeID in ("
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ ")");
					break;
			}
		}
		else
		{
			m_sbWhere.append(" and TransactionTypeID<>" + SETTConstant.TransactionType.CHANGECERTIFICATE + "");
			if (info.getTransactionTypeIDs().length() > 0) //add by rxie for query TransSpecialOperation subTrans
				m_sbWhere.append(" and ( TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
		}
		if (info.getBankID() > 0)
			m_sbWhere.append(" and BankID=" + info.getBankID() + "");
		if (info.getInputuserID() != -1)//modify by xwhe 2009-03-30 机制ID 为-100
			m_sbWhere.append(" and InputuserID=" + info.getInputuserID() + "");
		if (info.getCheckuserID() != -1)//modify by xwhe 2009-03-30 机核ID 为-100
			m_sbWhere.append(" and CheckuserID=" + info.getCheckuserID() + "");
		if (info.getStatusID() > 0)
			m_sbWhere.append(" and StatusID=" + info.getStatusID() + "");
		
		if (info.getPayClientIDStart() > 0 && (info.getPayClientNoStart() == null || info.getPayClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayclientID>=" + info.getPayClientIDStart() + "");
		if (info.getPayClientIDEnd() > 0 && (info.getPayClientNoEnd() == null || info.getPayClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayclientID<=" + info.getPayClientIDEnd() + "");
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID<=" + info.getPayAccountIDEnd() + "");
		if (info.getReceiveClientIDStart() > 0 && (info.getReceiveClientNoStart() == null || info.getReceiveClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveClientID>=" + info.getReceiveClientIDStart() + "");
		if (info.getReceiveClientIDEnd() > 0 && (info.getReceiveClientNoEnd() == null || info.getReceiveClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveClientID<=" + info.getReceiveClientIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");

		if (info.getContractID() > 0)
			m_sbWhere.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and LoanFormID=" + info.getPayFormID() + "");
		if (info.getPayAmountStart() > 0.0)
			m_sbWhere.append(" and PayAmount>=" + info.getPayAmountStart() + "");
		if (info.getPayAmountEnd() > 0.0)
			m_sbWhere.append(" and PayAmount<=" + info.getPayAmountEnd() + "");
		if (info.getReceiveAmountStart() > 0.0)
			m_sbWhere.append(" and ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (info.getReceiveAmountEnd() > 0.0)
			m_sbWhere.append(" and ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getQueryType() == 100)
		{
			//从账户金额查询进入，逻辑是，收付账户是“或”的关系
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				m_sbWhere.append(" and (PayAccountNo='" + info.getPayAccountNoStart() + "' or ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (info.getPayClientNoStart() != null && info.getPayClientNoStart().trim().length() > 0
					&& (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and PayClientCode>='" + info.getPayClientNoStart() + "'");
			if (info.getPayClientNoEnd() != null && info.getPayClientNoEnd().trim().length() > 0
					&& (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and PayClientCode<='" + info.getPayClientNoEnd() + "'");
			if (info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			if (info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and PayAccountNo<='" + info.getPayAccountNoEnd() + "'");
			if (info.getReceiveClientNoStart() != null && info.getReceiveClientNoStart().trim().length() > 0
					&& (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and ReceiveClientCode>='" + info.getReceiveClientNoStart() + "'");
			if (info.getReceiveClientNoEnd() != null && info.getReceiveClientNoEnd().trim().length() > 0
					&& (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and ReceiveClientCode<='" + info.getReceiveClientNoEnd() + "'");
			if (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			if (info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'");
		}
		if (info.getTransNoStart() != null && info.getTransNoStart().trim().length() > 0)
			m_sbWhere.append(" and TransNo>='" + info.getTransNoStart() + "'");
		if (info.getTransNoEnd() != null && info.getTransNoEnd().trim().length() > 0)
			m_sbWhere.append(" and TransNo<='" + info.getTransNoEnd() + "'");
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			m_sbWhere.append(" and DepositNo='" + info.getDepositNo() + "'");
		if (info.getInterestStartStart() != null)
			m_sbWhere.append(" and InterestStart>=to_date('" + DataFormat.getDateString(info.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (info.getInterestStartEnd() != null)
			m_sbWhere.append(" and InterestStart<=to_date('" + DataFormat.getDateString(info.getInterestStartEnd()) + "','yyyy-mm-dd')");
		if (info.getExecuteStart() != null)
			m_sbWhere.append(" and Execute>=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteEnd() != null)
			m_sbWhere.append(" and Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		//		add by jzw 2010-08-24 增加对定期提前部分支取新开立存单的过滤
		int index=info.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || info.getTransactionTypeIDs()==""){
		
			m_sbWhere.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";
		switch ((int) info.getOrderID())
		{
			default :
				m_sbOrderBy.append(" order by TransNo" + strDesc + " \n");
				break;
		}
		log.print("$$$$$$$$$$QTransaction$$$$$$$$$ select"+m_sbSelect.toString()
				+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
	
	/**
	 * 产生查询SQL ,做过差错分析
	 * @param info
	 */
	public void getTransactionSQLForQueryAnalysis(QueryTransactionConditionInfo info)
	{
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" sv.ID,SerialNo,OfficeID,CurrencyID,TransNo, \n");
		m_sbSelect.append(
			" TransactionTypeID,InterestStart,Execute,StatusID,InputuserID, \n");
		m_sbSelect.append(" CheckuserID,Abstract,PayclientID,PayaccountID,PayAmount, \n");
		m_sbSelect.append(" ReceiveAmount,ReceiveClientID,ReceiveAccountID,BankID,ContractID, \n");
		m_sbSelect.append(" LoanFormID,DepositNo,PayAccountNo,PayAccountName,ReceiveAccountNo, \n");
		m_sbSelect.append(" ReceiveAccountName,PayClientCode,PayClientName,ReceiveClientCode,ReceiveClientName,sa.NSTATUSID \n");
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_VTRANSACTION sv left join sett_analysis sa on  sv.id = sa.ntransid \n");
		// where 
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" StatusID>0 ");
		
		if (info.getQueryType() == 200)		
		{
			//当页面上点选了 查询已删除定期开立、通知开立交易 按钮功能时走此分支
			m_sbWhere.append(" StatusID =0 and sa.ntransid is not null and sa.nstatusid<>0 ");
			m_sbWhere.append(
					" and TransactionTypeID in ("
						+ SETTConstant.TransactionType.OPENFIXEDDEPOSIT				//定期开立
						+ ","
						+ SETTConstant.TransactionType.FIXEDCONTINUETRANSFER		//定期转存
						+ ","
						+ SETTConstant.TransactionType.OPENNOTIFYACCOUNT			//通知存款开立
						+ ")");
			System.out.println("执行的是定期，通知开立交易的记录!");
		}
		else
		{
			m_sbWhere.append(" StatusID in (3,7) and sa.ntransid is not null and sa.nstatusid<>0 ");
		}
		if(info.getNSTATUSID()>0){
			m_sbWhere.append(" and sa.NSTATUSID=" + info.getNSTATUSID() + "");
			
		}
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and CurrencyID=" + info.getCurrencyID() + "");
		//选择了账户交易类型后，交易类型便无效
		log.info("getTransactionSQL++++++++++++"+info.getAccountTransTypeID());
		if (info.getAccountTransTypeID() > 0)
		{
		    String sqlTmp = "";
			switch ((int) info.getAccountTransTypeID())
			{
				//活期存款：可以查询出所有使用活期存款账户的交易
				case (int) SETTConstant.AccountTransactionType.CURRENT_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
							+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CURRENT;
					m_sbWhere.append(
						" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//定期存款：可以查询出和定期存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.FIXED_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.FIXED;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//通知存款：可以查询出和通知存款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.NOTIFY_DEPOSIT :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.NOTIFY;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//信托贷款：可以查询出和信托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.TRUST_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.TRUST;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//委托贷款：可以查询出和委托贷款业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.CONSIGN_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.CONSIGN;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					//贴现：可以查询出和贴现业务相关的交易。
				case (int) SETTConstant.AccountTransactionType.DISCOUNT_LOAN :
				    sqlTmp = "select a.ID from sett_Account a,sett_accountType b where a.nAccountTypeID=b.id"
						+ " and b.nAccountGroupID = "+ SETTConstant.AccountGroupType.DISCOUNT;
				    m_sbWhere.append(
							" and (PayAccountID in (" + sqlTmp + ") or ReceiveAccountID in ("+ sqlTmp +"))");
					break;
					// 短期贷款：可以查询出和短期贷款业务相关的交易。
					//循环贷款：可以查询出和循环贷款业务相关的交易。
					//委托业务：可以查询出委托业务菜单下所有的交易。
					//对外付款：可以查询银行付款、支票付款、现金付款、票汇付款业务。
				case (int) SETTConstant.AccountTransactionType.OUT_PAYMENT :
					m_sbWhere.append(
						" and TransactionTypeID in ("
							+ SETTConstant.TransactionType.BANKPAY
							+ ","
							+ SETTConstant.TransactionType.DRAFTPAY
							+ ","
							+ SETTConstant.TransactionType.CASHPAY
							+ ","
							+ SETTConstant.TransactionType.CHECKPAY
							+ ")");
					break;
			}
		}
		else
		{
			m_sbWhere.append(" and TransactionTypeID<>" + SETTConstant.TransactionType.CHANGECERTIFICATE + "");
			if (info.getTransactionTypeIDs().length() > 0) //add by rxie for query TransSpecialOperation subTrans
				m_sbWhere.append(" and ( TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
		}
		if (info.getBankID() > 0)
			m_sbWhere.append(" and BankID=" + info.getBankID() + "");
		if (info.getInputuserID() != -1)//modify by xwhe 2009-03-30 机制ID 为-100
			m_sbWhere.append(" and InputuserID=" + info.getInputuserID() + "");
		if (info.getCheckuserID() != -1)//modify by xwhe 2009-03-30 机核ID 为-100
			m_sbWhere.append(" and CheckuserID=" + info.getCheckuserID() + "");
		if (info.getStatusID() > 0)
			m_sbWhere.append(" and StatusID=" + info.getStatusID() + "");
		
		if (info.getPayClientIDStart() > 0 && (info.getPayClientNoStart() == null || info.getPayClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayclientID>=" + info.getPayClientIDStart() + "");
		if (info.getPayClientIDEnd() > 0 && (info.getPayClientNoEnd() == null || info.getPayClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayclientID<=" + info.getPayClientIDEnd() + "");
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and PayaccountID<=" + info.getPayAccountIDEnd() + "");
		if (info.getReceiveClientIDStart() > 0 && (info.getReceiveClientNoStart() == null || info.getReceiveClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveClientID>=" + info.getReceiveClientIDStart() + "");
		if (info.getReceiveClientIDEnd() > 0 && (info.getReceiveClientNoEnd() == null || info.getReceiveClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveClientID<=" + info.getReceiveClientIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");

		if (info.getContractID() > 0)
			m_sbWhere.append(" and ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and LoanFormID=" + info.getPayFormID() + "");
		if (info.getPayAmountStart() > 0.0)
			m_sbWhere.append(" and PayAmount>=" + info.getPayAmountStart() + "");
		if (info.getPayAmountEnd() > 0.0)
			m_sbWhere.append(" and PayAmount<=" + info.getPayAmountEnd() + "");
		if (info.getReceiveAmountStart() > 0.0)
			m_sbWhere.append(" and ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (info.getReceiveAmountEnd() > 0.0)
			m_sbWhere.append(" and ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getQueryType() == 100)
		{
			//从账户金额查询进入，逻辑是，收付账户是“或”的关系
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				m_sbWhere.append(" and (PayAccountNo='" + info.getPayAccountNoStart() + "' or ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (info.getPayClientNoStart() != null && info.getPayClientNoStart().trim().length() > 0
					&& (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and PayClientCode>='" + info.getPayClientNoStart() + "'");
			if (info.getPayClientNoEnd() != null && info.getPayClientNoEnd().trim().length() > 0
					&& (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and PayClientCode<='" + info.getPayClientNoEnd() + "'");
			if (info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			if (info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and PayAccountNo<='" + info.getPayAccountNoEnd() + "'");
			if (info.getReceiveClientNoStart() != null && info.getReceiveClientNoStart().trim().length() > 0
					&& (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and ReceiveClientCode>='" + info.getReceiveClientNoStart() + "'");
			if (info.getReceiveClientNoEnd() != null && info.getReceiveClientNoEnd().trim().length() > 0
					&& (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and ReceiveClientCode<='" + info.getReceiveClientNoEnd() + "'");
			if (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			if (info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'");
		}
		if (info.getTransNoStart() != null && info.getTransNoStart().trim().length() > 0)
			m_sbWhere.append(" and TransNo>='" + info.getTransNoStart() + "'");
		if (info.getTransNoEnd() != null && info.getTransNoEnd().trim().length() > 0)
			m_sbWhere.append(" and TransNo<='" + info.getTransNoEnd() + "'");
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			m_sbWhere.append(" and DepositNo='" + info.getDepositNo() + "'");
		if (info.getInterestStartStart() != null)
			m_sbWhere.append(" and InterestStart>=to_date('" + DataFormat.getDateString(info.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (info.getInterestStartEnd() != null)
			m_sbWhere.append(" and InterestStart<=to_date('" + DataFormat.getDateString(info.getInterestStartEnd()) + "','yyyy-mm-dd')");
		if (info.getExecuteStart() != null)
			m_sbWhere.append(" and Execute>=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteEnd() != null)
			m_sbWhere.append(" and Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		//		add by jzw 2010-08-24 增加对定期提前部分支取新开立存单的过滤
		int index=info.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || info.getTransactionTypeIDs()==""){
		
			m_sbWhere.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";
		switch ((int) info.getOrderID())
		{
			default :
				m_sbOrderBy.append(" order by TransNo" + strDesc + " \n");
				break;
		}
		log.print("$$$$$$$$$$QTransaction$$$$$$$$$ select"+m_sbSelect.toString()
				+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
	
	

	/**
	 * 打印资金上收明细时用。
	 * 获取上收单位的信息。
	 * @param info
	 * @return Collection
	 * @throws Exception
	 * @author xgzhang
	 * @create 2005-09-13
	 */
	public Collection getReceiveAccount(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strWhere = "";
		String sql = "";
		ArrayList result = new ArrayList();
		try
		{
			getTransactionSQL(info);
			// select 
			strSelect = " SELECT ReceiveClientId,ReceiveClientName,ReceiveAccountId,ReceiveAccountNo,sum(round(ReceiveAmount,2)) ReceiveAmountSum \n";
			strWhere = "and receiveaccountid != -1 group by ReceiveClientId, receiveaccountid,  ReceiveAccountNo ,ReceiveClientName";
			sql = strSelect + " from " 
			+ m_sbFrom.toString() 
			+ " where " + m_sbWhere.toString()
			+ strWhere;
			logger.info(sql);
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				QUpGatherAccountDetailInfo quad = new QUpGatherAccountDetailInfo();
				quad.setClientID(rs.getLong("ReceiveClientId"));
				quad.setClientName(rs.getString("ReceiveClientName"));
				quad.setAccountID(rs.getLong("ReceiveAccountId"));
				quad.setAccountNo(rs.getString("ReceiveAccountNo"));
				quad.setAccountAmountSum(rs.getDouble("ReceiveAmountSum"));
				result.add(quad);
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
		return result;
	}
	/**
	 * 打印资金上收明细时用。
	 * 获取上收单位的信息。
	 * @param info
	 * @return Collection
	 * @throws Exception
	 * @author xgzhang
	 * @create 2005-09-13
	 */
	public Collection getPayAccount(QueryTransactionConditionInfo info,long lreceiveAccountID) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strWhere = "";
		String strGroupby = "";
		String sql = "";
		ArrayList result = new ArrayList();
		try
		{
			getTransactionSQL(info);
			// select 
			strSelect = " SELECT PayClientID,PayClientName, PayAccountID, PayAccountNo ,sum(round(PayAmount,2)) PayAmountSum \n";
			strWhere = " and Payaccountid != -1 and  transno in (SELECT transno from SETT_VTRANSACTION where receiveaccountid = "+
			lreceiveAccountID+")";
			strGroupby = " group by  PayAccountNo ,PayClientName , PayClientID  ,PayAccountID";
			sql = strSelect + " from " 
			+ m_sbFrom.toString() 
			+ " where " + m_sbWhere.toString()
			+ strWhere
			+ strGroupby;
			logger.info(sql);
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				QUpGatherAccountDetailInfo quad = new QUpGatherAccountDetailInfo();
				quad.setClientID(rs.getLong("PayClientID"));
				quad.setClientName(rs.getString("PayClientName"));
				quad.setAccountID(rs.getLong("PayAccountID"));
				quad.setAccountNo(rs.getString("PayAccountNo"));
				quad.setAccountAmountSum(rs.getDouble("PayAmountSum"));
				result.add(quad);
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
		return result;
	}
	/**
	 * 获得付方金额合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getPayAmountSum(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//
		try
		{
			getTransactionSQL(info);
			// select 
			strSelect = " select sum(round(PayAmount,2)) PayAmountSum \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("PayAmountSum");
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
		return dReturn;
	}
	
	/**
	 * 获得付方金额合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getPayAmountSumForQuery(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//
		try
		{
			getTransactionSQLForQuery(info);
			// select 
			strSelect = " select sum(round(PayAmount,2)) PayAmountSum \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();

			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("PayAmountSum");
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
		return dReturn;
	}
	
	/**
	 * 获得付方金额合计
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getPayAmountSumForQueryAnalysis(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//
		try
		{
			getTransactionSQLForQueryAnalysis(info);
			// select 
			strSelect = " select sum(round(PayAmount,2)) PayAmountSum \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();

			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("PayAmountSum");
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
		return dReturn;
	}
	/**
	 * 获得收方金额合计,不包括焕发证书
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getReceiveAmountSum(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//
		try
		{
			getTransactionSQL(info);
			// select 
			strSelect = " select sum(round(ReceiveAmount,2)) ReceiveAmountSum \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("ReceiveAmountSum");
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
		return dReturn;
	}
	
	/**
	 * 获得收方金额合计,不包括焕发证书
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getReceiveAmountSumForQuery(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//
		try
		{
			getTransactionSQLForQuery(info);
			// select 
			strSelect = " select sum(round(ReceiveAmount,2)) ReceiveAmountSum \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("ReceiveAmountSum");
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
		return dReturn;
	}
	
	/**
	 * 获得收方金额合计,不包括焕发证书
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public double getReceiveAmountSumForQueryAnalysis(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		double dReturn = 0.0; //
		//
		try
		{
			getTransactionSQLForQueryAnalysis(info);
			// select 
			strSelect = " select sum(round(ReceiveAmount,2)) ReceiveAmountSum \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				dReturn = rs.getDouble("ReceiveAmountSum");
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
		return dReturn;
	}
	
	/**
	 * 获得总记录数
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getCount(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		long lReturn = -1; //
		//
		try
		{
			getTransactionSQL(info);
			// select 
			strSelect = " select count(*) count \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			System.out.println("jzw test-----"+sql.toString());
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong("count");
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
		return lReturn;
	}
	/**
	 * 获得总记录数
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getCountForQuery(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		long lReturn = -1; //
		//
		try
		{
			getTransactionSQLForQuery(info);
			// select 
			strSelect = " select count(*) count \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong("count");
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
		return lReturn;
	}
	
	/**
	 * 获得总记录数
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long getCountForQueryAnalysis(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String sql = "";
		long lReturn = -1; //
		//
		try
		{
			getTransactionSQLForQueryAnalysis(info);
			// select 
			strSelect = " select count(*) count \n";
			con = this.getConnection();
			sql = strSelect + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			
			logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn = rs.getLong("count");
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
		return lReturn;
	}
	
	/**
	 * 
	 * @param info
	 * @param strTransNo
	 * @return
	 * @throws Exception
	 */
	public PageLoader getTransactionByTransNo(QueryTransactionConditionInfo info,String strTransNo) throws Exception
	{
		getTransactionSQL(info);
		if(strTransNo != null && !"".equals(strTransNo))
			m_sbWhere.append(" and TransNo = " + strTransNo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo",
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
	public PageLoader queryTransactionInfo(QueryTransactionConditionInfo info) throws Exception
	{
		getTransactionSQL(info);
		//add by zyyao 2007-6-7 增加摘要作为查询条件
		if(info.getAbstract() != null && !"".equals(info.getAbstract().trim()))
		{
			m_sbWhere.append(" and abstract like '%" + info.getAbstract().trim()+"%'");
		}
		//
		
		//		add by qhzhou 2007-07-25 增加对定期提前部分支取新开立存单的过滤
		int index=info.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || info.getTransactionTypeIDs()==""){
		
			m_sbWhere.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	/**
	 * 查询交易不包括焕发证书
	 * @param info
	 * @return PageLoader
	 * @throws Exception
	 */
	public PageLoader queryTransactionInfoForQuery(QueryTransactionConditionInfo info) throws Exception
	{
		getTransactionSQLForQuery(info);
		//add by zyyao 2007-6-7 增加摘要作为查询条件
		if(info.getAbstract() != null && !"".equals(info.getAbstract().trim()))
		{
			m_sbWhere.append(" and abstract like '%" + info.getAbstract().trim()+"%'");
		}
		if(info.getSigner()>0)
		{
			m_sbWhere.append(" and nvl(PayclientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
			m_sbWhere.append(" and nvl(ReceiveClientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
		}
		//
		
		//		add by qhzhou 2007-07-25 增加对定期提前部分支取新开立存单的过滤
		int index=info.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || info.getTransactionTypeIDs()==""){
		
			m_sbWhere.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	
	/**
	 * 查询做过差错分析的交易不包括焕发证书
	 * @param info
	 * @return PageLoader
	 * @throws Exception
	 */
	public PageLoader queryTransactionInfoAnalysis(QueryTransactionConditionInfo info) throws Exception
	{
		getTransactionSQLForQueryAnalysis(info);
		// 增加摘要作为查询条件
		if(info.getAbstract() != null && !"".equals(info.getAbstract().trim()))
		{
			m_sbWhere.append(" and abstract like '%" + info.getAbstract().trim()+"%'");
		}
		if(info.getSigner()>0)
		{
			m_sbWhere.append(" and nvl(PayclientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
			m_sbWhere.append(" and nvl(ReceiveClientID,0) not in (select nclientid from sett_signature where NISSIGNATURE=1 ) ");
		}
		//
		
		//		 增加对定期提前部分支取新开立存单的过滤
		int index=info.getTransactionTypeIDs().indexOf(""+SETTConstant.TransactionType.OPENFIXEDDEPOSIT);
		if(index >= 0 || info.getTransactionTypeIDs()==""){
		
			m_sbWhere.append(" and StatusID <> "+SETTConstant.TransactionStatus.WAITAPPROVAL);
		
		}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	
	/**
	 * 查询可以合并的交易 add by zwxiao 2008-03-24
	 * @param info
	 * @return PageLoader
	 * @throws Exception
	 */
	public Collection queryTransactionInfoByMerged(QueryTransactionConditionInfo info) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		ArrayList result = new ArrayList();
		try
		{
			getTransactionSQLByMerged(info);
			sql = " select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				QueryTransactionInfo queryTransactionInfo = new QueryTransactionInfo();
				queryTransactionInfo.setID(rs.getLong("ID"));
				queryTransactionInfo.setSerialNo(rs.getLong("SerialNo"));
				queryTransactionInfo.setOfficeID(rs.getLong("OfficeID"));
				queryTransactionInfo.setCurrencyID(rs.getLong("CurrencyID"));
				queryTransactionInfo.setTransNo(rs.getString("TransNo"));
				queryTransactionInfo.setTransactionTypeID(rs.getLong("TransactionTypeID"));
				queryTransactionInfo.setInterestStart(rs.getTimestamp("InterestStart"));
				queryTransactionInfo.setExecute(rs.getTimestamp("Execute"));
				queryTransactionInfo.setStatusID(rs.getLong("StatusID"));
				queryTransactionInfo.setInputUserID(rs.getLong("InputuserID"));
				queryTransactionInfo.setCheckUserID(rs.getLong("CheckuserID"));
				queryTransactionInfo.setAbstract(rs.getString("Abstract"));
				queryTransactionInfo.setPayClientID(rs.getLong("PayclientID"));
				queryTransactionInfo.setPayAccountID(rs.getLong("PayaccountID"));
				queryTransactionInfo.setPayAmount(rs.getDouble("PayAmount"));
				queryTransactionInfo.setReceiveAmount(rs.getDouble("ReceiveAmount"));
				queryTransactionInfo.setReceiveClientID(rs.getLong("ReceiveClientID"));
				queryTransactionInfo.setReceiveAccountID(rs.getLong("ReceiveAccountID"));
				queryTransactionInfo.setBankID(rs.getLong("BankID"));
				queryTransactionInfo.setContractID(rs.getLong("ContractID"));
				queryTransactionInfo.setLoanFormID(rs.getLong("LoanFormID"));
				queryTransactionInfo.setDepositNo(rs.getString("DepositNo"));
				queryTransactionInfo.setPayAccountNo(rs.getString("PayAccountNo"));
				queryTransactionInfo.setPayAccountName(rs.getString("PayAccountName"));
				queryTransactionInfo.setReceiveAccountNo(rs.getString("ReceiveAccountNo"));
				queryTransactionInfo.setReceiveAccountName(rs.getString("ReceiveAccountName"));
				queryTransactionInfo.setPayClientCode(rs.getString("PayClientCode"));
				queryTransactionInfo.setPayClientName(rs.getString("PayClientName"));
				queryTransactionInfo.setReceiveClientCode(rs.getString("ReceiveClientCode"));
				queryTransactionInfo.setReceiveClientName(rs.getString("ReceiveClientName"));
				result.add(queryTransactionInfo);
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
		return result;
	}
	/**
	 *查询可以合并的交易,不包括焕发证书
	 * @param info
	 */
	public void getTransactionSQLByMerged(QueryTransactionConditionInfo info)
	{
		m_sbSelect = new StringBuffer();
		// select 
		m_sbSelect.append(" a.ID ID,a.SerialNo SerialNo,a.OfficeID OfficeID,a.CurrencyID CurrencyID,a.TransNo TransNo, \n");
		m_sbSelect.append(
			" a.TransactionTypeID TransactionTypeID,a.InterestStart InterestStart,a.Execute Execute,a.StatusID StatusID,a.InputuserID InputuserID, \n");
		m_sbSelect.append(" a.CheckuserID CheckuserID,a.Abstract Abstract,a.PayclientID PayclientID,a.PayaccountID PayaccountID,a.PayAmount PayAmount, \n");
		m_sbSelect.append(" a.ReceiveAmount ReceiveAmount,a.ReceiveClientID ReceiveClientID,a.ReceiveAccountID ReceiveAccountID,a.BankID BankID,a.ContractID ContractID, \n");
		m_sbSelect.append(" a.LoanFormID LoanFormID,a.DepositNo DepositNo,a.PayAccountNo PayAccountNo,a.PayAccountName PayAccountName,a.ReceiveAccountNo ReceiveAccountNo, \n");
		m_sbSelect.append(" a.ReceiveAccountName ReceiveAccountName,a.PayClientCode PayClientCode,a.PayClientName PayClientName,a.ReceiveClientCode ReceiveClientCode,a.ReceiveClientName ReceiveClientName \n");
		// from 
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SETT_VTRANSACTION a,(select distinct STRANSNO,NSTATUSID from SETT_GLENTRY) b\n");
		// where 
		m_sbWhere = new StringBuffer();
		//m_sbWhere.append(" StatusID>0 ");

		m_sbWhere.append(" a.TRANSNO = b.STRANSNO ");
		m_sbWhere.append(" and b.NSTATUSID = "+SETTConstant.EntryStatus.CHECKED);
		m_sbWhere.append(" and a.StatusID>0 ");
		
		if (info.getOfficeID() > 0)
			m_sbWhere.append(" and a.OfficeID=" + info.getOfficeID() + "");
		if (info.getCurrencyID() > 0)
			m_sbWhere.append(" and a.CurrencyID=" + info.getCurrencyID() + "");
		if (info.getTransactionTypeIDs().length() > 0) //add by rxie for query TransSpecialOperation subTrans
		m_sbWhere.append(" and ( a.TransactionTypeID in (" + info.getTransactionTypeIDs() + ") or a.OperationTypeID in (" + info.getTransactionTypeIDs() + ") )");
		if (info.getBankID() > 0)
			m_sbWhere.append(" and a.BankID=" + info.getBankID() + "");
		if (info.getStatusID() > 0)
			m_sbWhere.append(" and a.StatusID=" + info.getStatusID() + "");
		
		if (info.getPayClientIDStart() > 0 && (info.getPayClientNoStart() == null || info.getPayClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and a.PayclientID>=" + info.getPayClientIDStart() + "");
		if (info.getPayClientIDEnd() > 0 && (info.getPayClientNoEnd() == null || info.getPayClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and a.PayclientID<=" + info.getPayClientIDEnd() + "");
		if (info.getPayAccountIDStart() > 0 && (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and a.PayaccountID>=" + info.getPayAccountIDStart() + "");
		if (info.getPayAccountIDEnd() > 0 && (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and a.PayaccountID<=" + info.getPayAccountIDEnd() + "");
		if (info.getReceiveClientIDStart() > 0 && (info.getReceiveClientNoStart() == null || info.getReceiveClientNoStart().trim().length()<=0))
			m_sbWhere.append(" and a.ReceiveClientID>=" + info.getReceiveClientIDStart() + "");
		if (info.getReceiveClientIDEnd() > 0 && (info.getReceiveClientNoEnd() == null || info.getReceiveClientNoEnd().trim().length()<=0))
			m_sbWhere.append(" and a.ReceiveClientID<=" + info.getReceiveClientIDEnd() + "");
		if (info.getReceiveAccountIDStart() > 0 && (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
			m_sbWhere.append(" and a.ReceiveAccountID>=" + info.getReceiveAccountIDStart() + "");
		if (info.getReceiveAccountIDEnd() > 0 && (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
			m_sbWhere.append(" and a.ReceiveAccountID<=" + info.getReceiveAccountIDEnd() + "");

		if (info.getContractID() > 0)
			m_sbWhere.append(" and a.ContractID=" + info.getContractID() + "");
		if (info.getPayFormID() > 0)
			m_sbWhere.append(" and a.LoanFormID=" + info.getPayFormID() + "");
		if (info.getPayAmountStart() > 0.0)
			m_sbWhere.append(" and a.PayAmount>=" + info.getPayAmountStart() + "");
		if (info.getPayAmountEnd() > 0.0)
			m_sbWhere.append(" and a.PayAmount<=" + info.getPayAmountEnd() + "");
		if (info.getReceiveAmountStart() > 0.0)
			m_sbWhere.append(" and a.ReceiveAmount>=" + info.getReceiveAmountStart() + "");
		if (info.getReceiveAmountEnd() > 0.0)
			m_sbWhere.append(" and a.ReceiveAmount<=" + info.getReceiveAmountEnd() + "");
		if (info.getQueryType() == 100)
		{
			//从账户金额查询进入，逻辑是，收付账户是“或”的关系
			if ((info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				&& (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0))
			{
				m_sbWhere.append(" and (a.PayAccountNo='" + info.getPayAccountNoStart() + "' or a.ReceiveAccountNo='" + info.getReceiveAccountNoStart() + "')");
			}
		}
		else
		{
			if (info.getPayClientNoStart() != null && info.getPayClientNoStart().trim().length() > 0
					&& (info.getPayAccountNoStart() == null || info.getPayAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and a.PayClientCode>='" + info.getPayClientNoStart() + "'");
			if (info.getPayClientNoEnd() != null && info.getPayClientNoEnd().trim().length() > 0
					&& (info.getPayAccountNoEnd() == null || info.getPayAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and a.PayClientCode<='" + info.getPayClientNoEnd() + "'");
			if (info.getPayAccountNoStart() != null && info.getPayAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and a.PayAccountNo>='" + info.getPayAccountNoStart() + "'");
			if (info.getPayAccountNoEnd() != null && info.getPayAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and a.PayAccountNo<='" + info.getPayAccountNoEnd() + "'");
			if (info.getReceiveClientNoStart() != null && info.getReceiveClientNoStart().trim().length() > 0
					&& (info.getReceiveAccountNoStart() == null || info.getReceiveAccountNoStart().trim().length()<=0))
				m_sbWhere.append(" and a.ReceiveClientCode>='" + info.getReceiveClientNoStart() + "'");
			if (info.getReceiveClientNoEnd() != null && info.getReceiveClientNoEnd().trim().length() > 0
					&& (info.getReceiveAccountNoEnd() == null || info.getReceiveAccountNoEnd().trim().length()<=0))
				m_sbWhere.append(" and a.ReceiveClientCode<='" + info.getReceiveClientNoEnd() + "'");
			if (info.getReceiveAccountNoStart() != null && info.getReceiveAccountNoStart().trim().length() > 0)
				m_sbWhere.append(" and a.ReceiveAccountNo>='" + info.getReceiveAccountNoStart() + "'");
			if (info.getReceiveAccountNoEnd() != null && info.getReceiveAccountNoEnd().trim().length() > 0)
				m_sbWhere.append(" and a.ReceiveAccountNo<='" + info.getReceiveAccountNoEnd() + "'");
		}
		if (info.getTransNoStart() != null && info.getTransNoStart().trim().length() > 0)
			m_sbWhere.append(" and a.TransNo>='" + info.getTransNoStart() + "'");
		if (info.getTransNoEnd() != null && info.getTransNoEnd().trim().length() > 0)
			m_sbWhere.append(" and a.TransNo<='" + info.getTransNoEnd() + "'");
		if (info.getDepositNo() != null && info.getDepositNo().trim().length() > 0)
			m_sbWhere.append(" and a.DepositNo='" + info.getDepositNo() + "'");
		if (info.getInterestStartStart() != null)
			m_sbWhere.append(" and a.InterestStart>=to_date('" + DataFormat.getDateString(info.getInterestStartStart()) + "','yyyy-mm-dd')");
		if (info.getInterestStartEnd() != null)
			m_sbWhere.append(" and a.InterestStart<=to_date('" + DataFormat.getDateString(info.getInterestStartEnd()) + "','yyyy-mm-dd')");
		if (info.getExecuteStart() != null)
			m_sbWhere.append(" and a.Execute>=to_date('" + DataFormat.getDateString(info.getExecuteStart()) + "','yyyy-mm-dd')");
		if (info.getExecuteEnd() != null)
			m_sbWhere.append(" and a.Execute<=to_date('" + DataFormat.getDateString(info.getExecuteEnd()) + "','yyyy-mm-dd')");
		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = info.getDESC() == 1 ? " desc " : "";
		switch ((int) info.getOrderID())
		{
			default :
				m_sbOrderBy.append(" order by  TransNo" + strDesc + " \n");
				break;
		}
		log.print("$$$$$$$$$$QTransaction$$$$$$$$$ select"+m_sbSelect.toString()
				+" from "+m_sbFrom.toString()+" where "+m_sbWhere.toString() +"$$$$");
	}
	/**
	 * 查询结算交易状态信息，结算rmi接口使用 add by minzhao 2009-10-14
	 * @param info
	 * @return SettResultInfo
	 * @throws Exception
	 */
	public SettResultInfo getTransactionInfo(SettTransInfo info) throws Exception
	{
		SettResultInfo settResultInfo = new SettResultInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{
			m_sbSelect = new StringBuffer();
			// select 
			m_sbSelect.append(" ID,OfficeID,CurrencyID,TransNo,StatusID \n");
			// from 
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" SETT_VTRANSACTION \n");
			// where 
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" TransNo='"+info.getTransNo()+ "' \n");
			if(info.getOfficeID() > 0){
				m_sbWhere.append(" and OfficeID=" + info.getOfficeID() + " \n");
			}
			//m_sbWhere.append(" and PayAmount=" + info.getAmount() + " \n"); //金额暂时不作为匹配条件
			m_sbWhere.append(" and (StatusID=" + SETTConstant.TransactionStatus.CHECK + " or " + "StatusID=" +SETTConstant.TransactionStatus.APPROVALED + ") \n");
			
			sql = " select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			logger.info(sql);
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				settResultInfo.setTransNo(rs.getString("TransNo"));
				settResultInfo.setStatus(Constant.TRUE);
				settResultInfo.setMessage("找到对应结算交易[交易号："+info.getTransNo()+"，办事处："+(info.getOfficeID()>0?String.valueOf(info.getOfficeID()):"")+"，状态:"+SETTConstant.TransactionStatus.getName(rs.getLong("StatusID"))+"]");
			}else{
				settResultInfo.setTransNo("");
				settResultInfo.setStatus(Constant.FALSE);
				settResultInfo.setMessage("找不到对应[交易号："+info.getTransNo()+"，办事处："+(info.getOfficeID()>0?String.valueOf(info.getOfficeID()):"")+"]的结算交易");
			}
			 
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			settResultInfo.setTransNo("");
			settResultInfo.setStatus(Constant.FALSE);
			settResultInfo.setMessage("查询对应[交易号："+info.getTransNo()+"，办事处："+(info.getOfficeID()>0?String.valueOf(info.getOfficeID()):"")+"]的结算交易，出现数据库异常");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return settResultInfo;
	}
	
	/**
	 * 获得差错状态信息。
	 * @param info
	 * @return Collection
	 * @throws Exception
	 * @author xgzhang
	 * @create 2005-09-13
	 */
	public List getAnalysisList() throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		List list = new ArrayList();
		try
		{
			
			// select 
			sql="select PROPVALUE,PROPNAME from SYS_ENUMERATE where FUNCTION = 'Analysis' order by PROPINDEX";
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Map map =new HashMap();
				map.put("PROPVALUE", rs.getString("PROPVALUE"));
				map.put("PROPNAME", rs.getString("PROPNAME"));
				list.add(map);
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
		return list;
	}
	
	/**
	 * 获得已做过差错分析交易数据
	 * @param info
	 * @return Collection
	 * @throws Exception
	 * @author xgzhang
	 * @create 2005-09-13
	 */
	public List getAnalysisMap(String lId) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		List list = new ArrayList();
		try
		{
			 
			sql="select NSTATUSID,REMARK from sett_analysis where NTRANSID ="+lId+ " and NSTATUSID <> 0";
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Map map =new HashMap();
				map.put("NSTATUSID", rs.getString("NSTATUSID"));
				map.put("REMARK", rs.getString("REMARK"));
				list.add(map);
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
		return list;
	}
	
	
	/**
	 * 差错分析新增
	 * @param String lId,String strTransNos,String analysis,String remark
	 * @return void
	 * @throws Exception
	 * @author 
	 * @create 
	 */
	public long addAnalysis(String lId,String strTransNos,String analysis,String remark ) throws Exception
	{
		long result = -1;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "";
		try
		{

			sql="insert into sett_analysis (ID,STRANSNO,NTRANSID,NSTATUSID,REMARK) values (SEQ_SETT_ANALYSIS.NEXTVAL,'"+strTransNos+"',"+lId+","+analysis+",'"+remark+"')";
			con = this.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			result= ps.executeUpdate();
			con.commit();
		}
		catch (Exception exp)
		{
			con.rollback();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}
	
	/**
	 * 差错分析修改
	 * @param String lId,String strTransNos,String analysis,String remark
	 * @return void
	 * @throws Exception
	 * @author 
	 * @create 
	 */
	public long updateAnalysis(String lId,String strTransNos,String analysis,String remark ) throws Exception
	{
		long result = -1;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "";
		
		try
		{
			sql="update sett_analysis set NSTATUSID = "+analysis+",REMARK ='"+remark+"' where NTRANSID ="+lId;
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			result= ps.executeUpdate();
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}
	
	/**
	 * 获得差错状态信息。
	 * @param String lId
	 * @return List
	 * @throws Exception
	 * @author ld
	 * @create 2010-11-04
	 */
	public List getAnalysisInfo(String lId) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		List list = new ArrayList();
		try
		{
			sql="select ID from sett_analysis where ntransid= "+lId;
			con = this.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Map map =new HashMap();
				map.put("ID", rs.getString("ID"));
				list.add(map);
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
		return list;
	}
	
	
}