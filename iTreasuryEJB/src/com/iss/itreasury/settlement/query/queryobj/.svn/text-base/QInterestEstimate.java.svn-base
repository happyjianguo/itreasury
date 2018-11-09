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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.FixedDepositAccountPayableInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestAccountIDInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author xrli
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QInterestEstimate extends BaseQueryObject {

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
	public QInterestEstimate() {
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	}

	// 匡算（资金限制）
	public void getInterestEstimateInfoAccountSQL(
			InterestEstimateQueryInfo qInfo) {
		m_sbSelect = new StringBuffer();

		m_sbSelect.append(" distinct account.ID AS accountID, \n");
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect.append(" client.sname ClientName, \n");
		m_sbSelect.append(" client.scode ClientNo, \n");
		m_sbSelect.append(" client.ID ClientID \n");
		// from
		m_sbFrom = new StringBuffer();
		// m_sbFrom.append(" sett_Account account,SETT_ACCOUNTTYPE accountType,
		// sett_SubAccount subaccount, client,loan_PayForm payform,
		// loan_ContractForm contractform, loan_LoanForm loanform \n");
		m_sbFrom
				.append(" sett_Account account,SETT_ACCOUNTTYPE accountType, sett_SubAccount subaccount, client,loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client consigner,loan_loantypesetting subtype \n");

		// where
		m_sbWhere = new StringBuffer();

		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nAccountTypeID = accountType.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID and subtype.id=contractform.nsubtypeid \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if (qInfo.getIsSelectConsigner() > 0) {

			m_sbWhere.append(" AND ");
			m_sbWhere
					.append(" contractform.nConsignClientID = consigner.ID \n");

		}
		m_sbWhere.append(" AND account.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		// 不等于活期
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.CURRENT + " \n");

		// 不等于定期，疑问
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.FIXED + " \n");
		// 不是通知
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.NOTIFY + " \n");
		// 不是贴现
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.DISCOUNT + " \n");
		if (qInfo.getOfficeID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}
		if (qInfo.getIsSelectClientNo() > 0) {
			if (qInfo.getClientNoFrom() != null
					&& qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append("and client.scode>='"
						+ qInfo.getClientNoFrom() + "'");
			if (qInfo.getClientNoTo() != null
					&& qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append("and client.scode<='" + qInfo.getClientNoTo()
						+ "'");
		}
		if (qInfo.getIsSelectClearInterestDate() > 0) {
			if (qInfo.getClearInterestDate() != null) { // 结息日
				m_sbWhere.append("and subaccount.dtClearInterest<= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterestDate())
						+ "','yyyy-mm-dd')");
			}
		}

		if (qInfo.getIsSelectSelfLoanSort() > 0
				&& qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0 && qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid in ( "
						+ qInfo.getSelfLoanSort() + ", "
						+ qInfo.getConsignLoanSort() + " ) ");
			} else if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid = "
						+ qInfo.getSelfLoanSort() + "");
			} else if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid = "
						+ qInfo.getConsignLoanSort() + "");
			} else {
				m_sbWhere.append(" and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ", "
						+ LOANConstant.LoanType.WT + ") ");
			}
		}
		// 是否选择自营贷款种类
		else if (qInfo.getIsSelectSelfLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getSelfLoanSort() + "");
			}
			if (qInfo.getSelfLoanSort() == 0 || qInfo.getSelfLoanSort() == -1) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ") ");
			}
		}
		// 是否选择委托贷款种类
		else if (qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getConsignLoanSort() + "");
			}

			if (qInfo.getConsignLoanSort() == 0
					|| qInfo.getConsignLoanSort() == -1) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.WT + ") ");
			}
		}

		if (qInfo.getIsSelectSelfLoanTerm() > 0) {
			if (qInfo.getSelfLoanTermFrom() > 0) {
				m_sbWhere.append("and contractform.nIntervalNum >="
						+ qInfo.getSelfLoanTermFrom() + "");
			}
			if (qInfo.getSelfLoanTermTo() > 0) {
				m_sbWhere.append("and contractform.nIntervalNum <="
						+ qInfo.getSelfLoanTermTo() + "");
			}
			//add by kevin(刘连凯)2011-08-01 当选择的自营贷款期限时，合同类型应默认是自营
			m_sbWhere.append("and contractform.ntypeid ="+LOANConstant.LoanType.ZY+" \n"); 
		}
		if (qInfo.getIsSelectConsigner() > 0) {
			if (qInfo.getConsignerFrom() != null
					&& qInfo.getConsignerFrom().length() > 0) {
				m_sbWhere.append("and consigner.scode>='"
						+ qInfo.getConsignerFrom() + "'");
			}
			if (qInfo.getConsignerTo() != null
					&& qInfo.getConsignerTo().length() > 0) {
				m_sbWhere.append("and consigner.scode<='"
						+ qInfo.getConsignerTo() + "'");
			}
		}
		if (qInfo.getIsSelectContractNo() > 0) {
			if (qInfo.getContractNoFrom() != null
					&& qInfo.getContractNoFrom().length() > 0) {
				m_sbWhere.append("and contractform.sContractCode>='"
						+ qInfo.getContractNoFrom() + "'");
			}
			if (qInfo.getContractNoTo() != null
					&& qInfo.getContractNoTo().length() > 0) {
				m_sbWhere.append("and contractform.sContractCode<='"
						+ qInfo.getContractNoTo() + "'");
			}
		}
		if (qInfo.getIsSelectPayFormNo() > 0) {
			if (qInfo.getPayFormNoFrom() != null
					&& qInfo.getPayFormNoFrom().length() > 0) {
				m_sbWhere.append("and payform.sCode>='"
						+ qInfo.getPayFormNoFrom() + "'");
			}
			if (qInfo.getPayFormNoTo() != null
					&& qInfo.getPayFormNoTo().length() > 0) {
				m_sbWhere.append("and payform.sCode<='"
						+ qInfo.getPayFormNoTo() + "'");
			}
		}
		if (qInfo.getDoFilterDate() == 1) { // 是否过滤 1 过滤 -1 不过滤
			m_sbWhere.append(" and payform.DTEND >= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterestDate())
					+ "','yyyy-mm-dd') \n");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" \n order by accountNo \n");
		String strDesc = " desc ";

		log.debug("select " + m_sbSelect.toString() + "from "
				+ m_sbFrom.toString() + "where " + m_sbWhere.toString());
		System.out.println("select " + m_sbSelect.toString() + "from "
				+ m_sbFrom.toString() + "where " + m_sbWhere.toString());
	}

	/**
	 * 查询应付利率SQL
	 * 
	 * @param qInfo
	 */
	public void getInterest4LoanSQL(InterestEstimateQueryInfo qInfo) {
		m_sbSelect = new StringBuffer();

		m_sbSelect.append(" distinct subaccount.id  as subaccountID, account.ID AS accountID," +
		"subaccount.AL_NPAYINTERESTACCOUNTID as npayInterestAccountID," +
		"payInterestAccount.saccountno npayInterestAccountNO , \n");
		m_sbSelect.append(" account.sAccountNo AS accountNo, \n");
		m_sbSelect.append(" account.nAccountTypeID AS accountTypeID, \n");
		m_sbSelect.append(" client.sname ClientName, \n");
		m_sbSelect.append(" client.scode ClientNo, \n");
		m_sbSelect.append(" contractform.nTypeID AS LoanTypeID, \n");
		m_sbSelect.append(" payform.ID AS PayFormID,  \n");
		m_sbSelect.append(" payform.SCODE AS PayFormNo,  \n");
		m_sbSelect.append(" contractform.nsubtypeid AS nsubTypeID,  \n");
		m_sbSelect.append(" subtype.name AS nsubTypeName,  \n");
		m_sbSelect.append(" contractform.scontractcode AS ContractNo, \n");
		m_sbSelect.append(" payform.DTEND DtEnd , \n");
		// m_sbSelect.append(" payform.SCODE AS PayFormNo, \n"); //放款通知单SCODE
		// m_sbSelect.append(" contractform.nsubtypeid AS nsubTypeID, \n");
		// //贷款子类型
		// m_sbSelect.append(" contractform.scontractcode AS ContractNo, \n");
		// //贷款合同号
		m_sbSelect.append(" client.ID ClientID \n");
		// from
		m_sbFrom = new StringBuffer();
		// m_sbFrom.append(" sett_Account account,SETT_ACCOUNTTYPE accountType,
		// sett_SubAccount subaccount, client,loan_PayForm payform,
		// loan_ContractForm contractform, loan_LoanForm loanform \n");
		m_sbFrom
				.append(" sett_Account payInterestAccount, sett_Account account,SETT_ACCOUNTTYPE accountType, sett_SubAccount subaccount, client,loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client consigner,loan_loantypesetting subtype \n");

		// where
		m_sbWhere = new StringBuffer();

		m_sbWhere.append(" subaccount.nAccountID = account.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payInterestAccount.id = subaccount.AL_NPAYINTERESTACCOUNTID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nClientID = client.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" account.nAccountTypeID = accountType.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subaccount.AL_nLoanNoteID = payform.ID \n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" payform.nContractID = contractform.ID and subtype.id=contractform.nsubtypeid\n");
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" contractform.nLoanID = loanform.ID(+)  \n");
		if (qInfo.getIsSelectConsigner() > 0) {

			m_sbWhere.append(" AND ");
			m_sbWhere
					.append(" contractform.nConsignClientID = consigner.ID \n");

		}
		m_sbWhere.append(" AND account.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		// //不等于活期
		// m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
		// + SETTConstant.AccountGroupType.CURRENT + " \n");
		//
		// //不等于定期，疑问
		// m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
		// + SETTConstant.AccountGroupType.FIXED + " \n");
		// //不是通知
		// m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
		// + SETTConstant.AccountGroupType.NOTIFY + " \n");
		// //不是贴现
		// m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
		// + SETTConstant.AccountGroupType.DISCOUNT + " \n");
		if (qInfo.getOfficeID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}
		
			if (qInfo.getClientNoFrom() != null
					&& qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append("and client.scode>='"
						+ qInfo.getClientNoFrom() + "'");
			if (qInfo.getClientNoTo() != null
					&& qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append("and client.scode<='" + qInfo.getClientNoTo()
						+ "'");
	
		if (qInfo.getIsSelectClearInterestDate() > 0) {
			if (qInfo.getClearInterestDate() != null) { // 结息日
				m_sbWhere.append("and subaccount.dtClearInterest<= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterestDate())
						+ "','yyyy-mm-dd')");
			}
		}
		if(qInfo.getIsSelectCircleLoan()>0){//循环贷款标志
			
				m_sbWhere.append("and contractform.NISCIRCLE= 1 ");
				
		}
		
			if (!qInfo.getSubTypeIds().equals("")&&!qInfo.getSubTypeIds().equals("-1") ) {
				m_sbWhere.append(" and contractform.nsubtypeid in ( "
						+ qInfo.getSubTypeIds() + " ) ");
			}
			 else {
				m_sbWhere.append(" and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ", "
						+ LOANConstant.LoanType.WT + ") ");
			}
		
		

		
	
			if (qInfo.getContractNoFrom() != null
					&& qInfo.getContractNoFrom().length() > 0) {
				m_sbWhere.append("and contractform.sContractCode>='"
						+ qInfo.getContractNoFrom() + "'");
			}
			if (qInfo.getContractNoTo() != null
					&& qInfo.getContractNoTo().length() > 0) {
				m_sbWhere.append("and contractform.sContractCode<='"
						+ qInfo.getContractNoTo() + "'");
			}
		
		
			if (qInfo.getPayFormNoFrom() != null
					&& qInfo.getPayFormNoFrom().length() > 0) {
				m_sbWhere.append("and payform.sCode>='"
						+ qInfo.getPayFormNoFrom() + "'");
			}
			if (qInfo.getPayFormNoTo() != null
					&& qInfo.getPayFormNoTo().length() > 0) {
				m_sbWhere.append("and payform.sCode<='"
						+ qInfo.getPayFormNoTo() + "'");
			}
		
		if (qInfo.getDoFilterDate() == 1) { // 是否过滤 1 过滤 -1 不过滤
			m_sbWhere.append(" and payform.DTEND >= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterestDate())
					+ "','yyyy-mm-dd') \n");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" \n order by   client.scode , subaccount.AL_NPAYINTERESTACCOUNTID \n");
		String strDesc = " asc ";

		log.debug("select " + m_sbSelect.toString() + "from "
				+ m_sbFrom.toString() + "where " + m_sbWhere.toString());
		System.out.println("select " + m_sbSelect.toString() + "from "
				+ m_sbFrom.toString() + "where " + m_sbWhere.toString());
	}

	// 匡算
	public void getInterestEstimateInfoClientSQL(InterestEstimateQueryInfo qInfo) {
		m_sbSelect = new StringBuffer();

		m_sbSelect.append(" distinct client.sname ClientName, \n");
		m_sbSelect.append(" client.scode ClientNo, \n");

		// Boxu Add 只有选择委托方才需要
		if (qInfo.getIsSelectConsigner() > 0) {
			// Added by ylguo at 2009-02-09,用来解决委托方名称显示错误的问题
			m_sbSelect
					.append(" consigner.id ConsignClientID, --增加了委托方的客户id \n");
			m_sbSelect
					.append(" consigner.scode ConsignClientCode, --增加了委托方的客户编号 \n");
			m_sbSelect
					.append(" consigner.sName ConsignClientName, --增加了委托方客户名称 \n");
			// 添加完毕 ylguo
		}

		m_sbSelect.append(" client.ID ClientID \n");

		// from
		m_sbFrom = new StringBuffer();
		// m_sbFrom.append(" sett_Account account, SETT_ACCOUNTTYPE
		// accountType,sett_SubAccount subaccount, client,loan_PayForm payform,
		// loan_ContractForm contractform, loan_LoanForm loanform \n");
		m_sbFrom
				.append(" sett_Account account,SETT_ACCOUNTTYPE accountType, sett_SubAccount subaccount, client,loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client consigner \n");
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
		if (qInfo.getIsSelectConsigner() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere
					.append(" contractform.nConsignClientID = consigner.ID \n");

		}
		m_sbWhere.append(" AND account.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		// 不等于活期
		m_sbWhere.append(" and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.CURRENT + " \n");

		// 不等于定期，疑问
		m_sbWhere.append(" and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.FIXED + " \n");
		// 不是通知
		m_sbWhere.append(" and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.NOTIFY + " \n");
		// 不是贴现
		m_sbWhere.append(" and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.DISCOUNT + " \n");
		if (qInfo.getOfficeID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}
		if (qInfo.getIsSelectClientNo() > 0) {
			if (qInfo.getClientNoFrom() != null
					&& qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append(" and client.scode>='"
						+ qInfo.getClientNoFrom() + "' \n");
			if (qInfo.getClientNoTo() != null
					&& qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append(" and client.scode<='" + qInfo.getClientNoTo()
						+ "' \n");
		}
		if (qInfo.getIsSelectClearInterestDate() > 0) {
			if (qInfo.getClearInterestDate() != null) { // 结息日
				m_sbWhere.append(" and subaccount.dtClearInterest<= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterestDate())
						+ "','yyyy-mm-dd') \n");
			}
		}

		if (qInfo.getIsSelectSelfLoanSort() > 0
				&& qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0 && qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid in ( "
						+ qInfo.getSelfLoanSort() + ", "
						+ qInfo.getConsignLoanSort() + " ) ");
			} else if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid = "
						+ qInfo.getSelfLoanSort() + "");
			} else if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append(" and contractform.nsubtypeid = "
						+ qInfo.getConsignLoanSort() + "");
			} else {
				m_sbWhere.append(" and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ", "
						+ LOANConstant.LoanType.WT + ") ");
			}
		}
		// 是否选择自营贷款种类
		else if (qInfo.getIsSelectSelfLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getSelfLoanSort() + "");
			}
			if (qInfo.getSelfLoanSort() == 0 || qInfo.getSelfLoanSort() == -1) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.ZY + ", "
						+ LOANConstant.LoanType.ZGXE + ") ");
			}
		}
		// 是否选择委托贷款种类
		else if (qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getConsignLoanSort() + "");
			}

			if (qInfo.getConsignLoanSort() == 0
					|| qInfo.getConsignLoanSort() == -1) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.WT + ") ");
			}
		}

		if (qInfo.getIsSelectSelfLoanTerm() > 0) {
			if (qInfo.getSelfLoanTermFrom() > 0) {
				m_sbWhere.append(" and contractform.nIntervalNum >='"
						+ qInfo.getSelfLoanTermFrom() + "' \n");
			}
			if (qInfo.getSelfLoanTermTo() > 0) {
				m_sbWhere.append(" and contractform.nIntervalNum <='"
						+ qInfo.getSelfLoanTermTo() + "' \n");
			}
			// add by kevin(刘连凯)2011-08-01 当选择的自营贷款期限时，合同类型应默认是自营
			m_sbWhere.append("and contractform.ntypeid ="+LOANConstant.LoanType.ZY+" \n"); 
		}
		if (qInfo.getIsSelectConsigner() > 0) {
			if (qInfo.getConsignerFrom() != null
					&& qInfo.getConsignerFrom().length() > 0) {
				m_sbWhere.append(" and consigner.scode>='"
						+ qInfo.getConsignerFrom() + "' \n");
			}
			if (qInfo.getConsignerTo() != null
					&& qInfo.getConsignerTo().length() > 0) {
				m_sbWhere.append(" and consigner.scode<='"
						+ qInfo.getConsignerTo() + "' \n");
			}
		}
		if (qInfo.getIsSelectContractNo() > 0) {
			if (qInfo.getContractNoFrom() != null
					&& qInfo.getContractNoFrom().length() > 0) {
				m_sbWhere.append(" and contractform.sContractCode>='"
						+ qInfo.getContractNoFrom() + "' \n");
			}
			if (qInfo.getContractNoTo() != null
					&& qInfo.getContractNoTo().length() > 0) {
				m_sbWhere.append(" and contractform.sContractCode<='"
						+ qInfo.getContractNoTo() + "' \n");
			}
		}
		if (qInfo.getIsSelectPayFormNo() > 0) {
			if (qInfo.getPayFormNoFrom() != null
					&& qInfo.getPayFormNoFrom().length() > 0) {
				m_sbWhere.append(" and payform.sCode>='"
						+ qInfo.getPayFormNoFrom() + "' \n");
			}
			if (qInfo.getPayFormNoTo() != null
					&& qInfo.getPayFormNoTo().length() > 0) {
				m_sbWhere.append(" and payform.sCode<='"
						+ qInfo.getPayFormNoTo() + "' \n");
			}
		}
		if (qInfo.getDoFilterDate() == 1) { // 是否过滤 1 过滤 -1 不过滤
			m_sbWhere.append(" and payform.DTEND >= to_date('"
					+ DataFormat.formatDate(qInfo.getClearInterestDate())
					+ "','yyyy-mm-dd') \n");
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" \n order by ClientNo \n");
		String strDesc = " desc ";
		log.debug("select " + m_sbSelect.toString() + "from "
				+ m_sbFrom.toString() + "where " + m_sbWhere.toString());
	}

	// 根据账户号返回各种合计利息信息
	public InterestEstimateQueryResultInfo queryDetailForAccount(
			InterestEstimateQueryInfo queryInfo, long lAccountID)
			throws Exception {
		InterestEstimateQueryResultInfo rtnInfo = new InterestEstimateQueryResultInfo();
		Vector rtnVec = new Vector();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer m_sbSelect1 = null;
		String strAccountWhere = "";

		String sql = "";

		try {
			m_sbSelect1 = new StringBuffer();
			getInterestEstimateInfoAccountSQL(queryInfo);
			// select  getInterest4LoanSQL  getInterestEstimateInfoAccountSQL

			m_sbSelect1.append(" distinct account.ID AS accountID, \n");
			m_sbSelect1.append(" account.sAccountNo AS accountNo, \n");
			m_sbSelect1.append(" account.nAccountTypeID AS accountTypeID, \n");
			m_sbSelect1.append(" subaccount.ID AS subAccountID, \n");
			m_sbSelect1.append(" contractform.nTypeID AS LoanTypeID,\n"); // 贷款类型
			m_sbSelect1.append(" payform.ID AS PayFormID, \n"); // 放款通知单ID
			m_sbSelect1.append(" payform.SCODE AS PayFormNo, \n"); // 放款通知单SCODE
			m_sbSelect1.append(" contractform.nsubtypeid AS nsubTypeID, \n"); // 贷款子类型 
			m_sbSelect1.append(" subtype.name AS nsubTypeName, \n"); // 贷款子类型名称
			m_sbSelect1.append(" contractform.scontractcode AS ContractNo, \n"); // 贷款合同号
			m_sbSelect1.append(" client.sname ClientName, \n");
			m_sbSelect1.append(" client.scode ClientNo, \n");
			m_sbSelect1.append(" client.ID ClientID, \n");
			m_sbSelect1.append(" payform.DTEND DtEnd, \n");
			m_sbSelect1.append(" subaccount.AL_NPAYINTERESTACCOUNTID as npayInterestAccountID, \n");
			m_sbSelect1.append(" payInterestAccount.saccountno npayInterestAccountNO \n");
			

			strAccountWhere = "  and payInterestAccount.id = subaccount.AL_NPAYINTERESTACCOUNTID      and account.ID =" + lAccountID + " \n";
			con = this.getConnection();
			InterestOperation io = new InterestOperation(con);
			InterestBatch ib = new InterestBatch(con);
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(
					con);

			sql = "select " + m_sbSelect1.toString() + " from  sett_Account payInterestAccount, "
					+ m_sbFrom.toString() + " where " + m_sbWhere.toString()
					+ strAccountWhere;
			// logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				long nInterestType = -1;
				if (queryInfo.getIsSelectInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.INTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);

				}
				if (queryInfo.getIsSelectCompoundInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.COMPOUNDINTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getIsSelectForfeitInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.FORFEITINTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE) {
					nInterestType = SETTConstant.InterestFeeType.ASSURE;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION) {
					nInterestType = SETTConstant.InterestFeeType.COMMISION;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);

				}
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return rtnInfo;
	}

	
	// 根据账户号返回各种贷款合计利息信息
	public InterestEstimateQueryResultInfo queryDetailForLoan(
			InterestEstimateQueryInfo queryInfo, long subAccountID)
			throws Exception {
		
		InterestEstimateQueryResultInfo rtnInfo =  new InterestEstimateQueryResultInfo();;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		double sumInterest=0;
		try {
			getInterest4LoanSQL(queryInfo);
			//m_sbWhere.append( "  and subaccount.id =" + subAccountID + " \n");
			  m_sbWhere.append( " and subaccount.AL_NPAYINTERESTACCOUNTID = \n");
			  m_sbWhere.append( "     (select u.AL_NPAYINTERESTACCOUNTID    \n");
			  m_sbWhere.append( "        from sett_subaccount u   \n");
			  m_sbWhere.append( "       where u.id = "+subAccountID+")     \n");
			con = this.getConnection();
			InterestOperation io = new InterestOperation(con);
			InterestBatch ib = new InterestBatch(con);
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(con);

			sql = "select " + m_sbSelect.toString() + " from " + m_sbFrom.toString() + " where " + m_sbWhere.toString();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				if(rs.getLong("subaccountID")==subAccountID)
				{
					long nInterestType = -1;
					if (queryInfo.getIsSelectInterest() > 0) {
						nInterestType = SETTConstant.InterestFeeType.INTEREST;
						InterestEstimateQueryResultInfo resultInfo = null;
						resultInfo = getDetailInterestInfo(queryInfo,
								nInterestType, con, rs, io, ib, transDetailDAO);
						// 设置活期余额
						setCurrentBanlance(con, ib, nInterestType, resultInfo);
						setRtnInfo(rtnInfo, resultInfo);
						if(resultInfo.getLoanTypeID()==LOANConstant.LoanType.WT)
						{
							sumInterest+=resultInfo.getConsignLoanInterest();
						}
						else
						{
							sumInterest+=resultInfo.getSelfLoanInterest();
						}
					}
					if (queryInfo.getIsSelectCompoundInterest() > 0) {
						nInterestType = SETTConstant.InterestFeeType.COMPOUNDINTEREST;
						InterestEstimateQueryResultInfo resultInfo = null;
						resultInfo = getDetailInterestInfo(queryInfo,
								nInterestType, con, rs, io, ib, transDetailDAO);
						// 设置活期余额
						setCurrentBanlance(con, ib, nInterestType, resultInfo);
						setRtnInfo(rtnInfo, resultInfo);
					}
					if (queryInfo.getIsSelectForfeitInterest() > 0) {
						nInterestType = SETTConstant.InterestFeeType.FORFEITINTEREST;
						InterestEstimateQueryResultInfo resultInfo = null;
						resultInfo = getDetailInterestInfo(queryInfo,
								nInterestType, con, rs, io, ib, transDetailDAO);
						// 设置活期余额
						setCurrentBanlance(con, ib, nInterestType, resultInfo);
						setRtnInfo(rtnInfo, resultInfo);
					}
					if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE) {
						nInterestType = SETTConstant.InterestFeeType.ASSURE;
						InterestEstimateQueryResultInfo resultInfo = null;
						resultInfo = getDetailInterestInfo(queryInfo,
								nInterestType, con, rs, io, ib, transDetailDAO);
						// 设置活期余额
						setCurrentBanlance(con, ib, nInterestType, resultInfo);
						setRtnInfo(rtnInfo, resultInfo);
					}
					if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION) {
						nInterestType = SETTConstant.InterestFeeType.COMMISION;
						InterestEstimateQueryResultInfo resultInfo = null;
						resultInfo = getDetailInterestInfo(queryInfo,
								nInterestType, con, rs, io, ib, transDetailDAO);
						// 设置活期余额
						setCurrentBanlance(con, ib, nInterestType, resultInfo);
						setRtnInfo(rtnInfo, resultInfo);
						
					}
				}
				else
				{
					long nInterestType = -1;
					if (queryInfo.getIsSelectInterest() > 0) {
						nInterestType = SETTConstant.InterestFeeType.INTEREST;
						InterestEstimateQueryResultInfo resultInfo = null;
						resultInfo = getDetailInterestInfo(queryInfo,
								nInterestType, con, rs, io, ib, transDetailDAO);
						if(resultInfo.getLoanTypeID()==LOANConstant.LoanType.WT)
						{
							sumInterest+=resultInfo.getConsignLoanInterest();
						}
						else
						{
							sumInterest+=resultInfo.getSelfLoanInterest();
						}
					}
				}
			}
			rtnInfo.setTotalInterest(sumInterest);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return rtnInfo;
	}
	
	
	
	
	
	
	
	/**
	 * 得到合计值
	 * 
	 * @param rtnInfo
	 * @param resultInfo
	 */
	public InterestEstimateQueryResultInfo queryInterestSum(
			InterestEstimateQueryInfo queryInfo) throws Exception {
		InterestEstimateQueryResultInfo rtnInfo = new InterestEstimateQueryResultInfo();
		Vector rtnVec = new Vector();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer m_sbSelect1 = null;
		String strAccountWhere = "";

		String sql = "";
		//

		try {
			m_sbSelect1 = new StringBuffer();
			getInterestEstimateInfoAccountSQL(queryInfo);
			// select

			m_sbSelect1.append(" distinct account.ID AS accountID, \n");
			m_sbSelect1.append(" account.sAccountNo AS accountNo, \n");
			m_sbSelect1.append(" account.nAccountTypeID AS accountTypeID, \n");
			m_sbSelect1.append(" subaccount.ID AS subAccountID, \n");
			m_sbSelect1.append(" contractform.nTypeID AS LoanTypeID,\n"); // 贷款类型
			m_sbSelect1.append(" payform.ID AS PayFormID, \n"); // 放款通知单ID
			m_sbSelect1.append(" payform.SCODE AS PayFormNo, \n"); // 放款通知单SCODE
			m_sbSelect1.append(" contractform.nsubtypeid AS nsubTypeID, \n"); // 贷款子类型
			m_sbSelect1.append(" subtype.name AS nsubTypeName, \n"); // 贷款子类型名称
			m_sbSelect1.append(" contractform.scontractcode AS ContractNo, \n"); // 贷款合同号
			m_sbSelect1.append(" client.sname ClientName, \n");
			m_sbSelect1.append(" client.scode ClientNo, \n");
			m_sbSelect1.append(" client.ID ClientID, \n");
			m_sbSelect1.append(" payform.DTEND DtEnd, \n"); // 如果是贷款的话，有放款通知单的结束日期
			m_sbSelect1.append(" subaccount.AL_NPAYINTERESTACCOUNTID as npayInterestAccountID, \n");
			m_sbSelect1.append(" payInterestAccount.saccountno npayInterestAccountNO \n");

			con = this.getConnection();
			InterestOperation io = new InterestOperation(con);
			InterestBatch ib = new InterestBatch(con);
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(
					con);

			sql = "select " + m_sbSelect1.toString() + " from sett_Account payInterestAccount, \n"
					+ m_sbFrom.toString() 
					+ " where payInterestAccount.id = subaccount.AL_NPAYINTERESTACCOUNTID and \n" + m_sbWhere.toString()
					+ strAccountWhere;
			System.out.print(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				long nInterestType = -1;
				if (queryInfo.getIsSelectInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.INTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					setRtnInfo(rtnInfo, resultInfo);

				}
				if (queryInfo.getIsSelectCompoundInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.COMPOUNDINTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getIsSelectForfeitInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.FORFEITINTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE) {
					nInterestType = SETTConstant.InterestFeeType.ASSURE;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION) {
					nInterestType = SETTConstant.InterestFeeType.COMMISION;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					setRtnInfo(rtnInfo, resultInfo);
				}
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		UtilOperation.dataentityToString(rtnInfo);
		return rtnInfo;
	}

	private void setRtnInfo(InterestEstimateQueryResultInfo rtnInfo,
			InterestEstimateQueryResultInfo resultInfo) {
		if (resultInfo != null) {
			rtnInfo.setAccountID(resultInfo.getAccountID());
			rtnInfo.setAccountNo(resultInfo.getAccountNo());
			rtnInfo.setSubAccountID(resultInfo.getSubAccountID());
			rtnInfo.setLoanTypeID(resultInfo.getLoanTypeID());
			rtnInfo.setClientID(resultInfo.getClientID());
			rtnInfo.setClientName(resultInfo.getClientName());
			rtnInfo.setClientNo(resultInfo.getClientNo());
			rtnInfo.setPayFormID(resultInfo.getPayFormID());
			rtnInfo.setPayFormNo(resultInfo.getPayFormNo());
			rtnInfo.setContractNo(resultInfo.getContractNo());
			rtnInfo.setNsubTypeID(resultInfo.getNsubTypeID());
			rtnInfo.setNsubTypeName(resultInfo.getNsubTypeName());
			log.info("**********账户ID Vector1= "
					+ rtnInfo.getPayInterestAccountID());
			log.info("**********账户ID Vector2= "
					+ resultInfo.getPayInterestAccountID());

			// 暂时不显示活期余额
			/*
			 * //活期余额 if(rtnInfo.getPayInterestAccountID()==null) {
			 * if(resultInfo.getPayInterestAccountID()!=null) {
			 * rtnInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
			 * rtnInfo.setBalance(resultInfo.getBalance()); } } else {
			 * log.info("**********账户ID Vector1 size=
			 * "+rtnInfo.getPayInterestAccountID().size());
			 * if(resultInfo.getPayInterestAccountID()!=null) {
			 * if(!rtnInfo.getPayInterestAccountID().contains(resultInfo.getPayInterestAccountID().elementAt(0))) {
			 * log.info("**********账户ID Vector2 size=
			 * "+resultInfo.getPayInterestAccountID().size()); Vector payAcc =
			 * null; payAcc = rtnInfo.getPayInterestAccountID();
			 * payAcc.addElement(resultInfo.getPayInterestAccountID().elementAt(0));
			 * rtnInfo.setPayInterestAccountID(payAcc);
			 * rtnInfo.setBalance(rtnInfo.getBalance()+resultInfo.getBalance()); } } }
			 */
			rtnInfo.setInterest(UtilOperation.Arith.round(resultInfo
					.getInterest(), 2)
					+ UtilOperation.Arith.round(rtnInfo.getInterest(), 2));
			/*
			 * if(resultInfo.getLoanTypeID()==LOANConstant.LoanType.YTDQ
			 * ||resultInfo.getLoanTypeID()==LOANConstant.LoanType.YTZCQ) {
			 * BankLoanQuery bankLoanQuery =new BankLoanQuery(); //承贷比例
			 * log.info("--------银团贷款操作——将利息数据按承贷比例转换"); double dRate = 0.0; try {
			 * dRate=bankLoanQuery.findRateByFormID(resultInfo.getPayFormID()); }
			 * catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 * 
			 * if(dRate>0) {
			 * resultInfo.setSelfLoanInterest(resultInfo.getSelfLoanInterest()/dRate*100);
			 * //rtnInfo.setAmount(rtnInfo.getAmount()/dRate*100);
			 * //rtnInfo.setBalance(rtnInfo.getBalance()/dRate*100); } }
			 */
			rtnInfo.setSelfLoanInterest(UtilOperation.Arith.round(resultInfo
					.getSelfLoanInterest(), 2)
					+ UtilOperation.Arith.round(rtnInfo.getSelfLoanInterest(),
							2));
			rtnInfo.setCompoundInterest(UtilOperation.Arith.round(resultInfo
					.getCompoundInterest(), 2)
					+ UtilOperation.Arith.round(rtnInfo.getCompoundInterest(),
							2));
			rtnInfo.setConsignLoanInterest(UtilOperation.Arith.round(resultInfo
					.getConsignLoanInterest(), 2)
					+ UtilOperation.Arith.round(rtnInfo
							.getConsignLoanInterest(), 2));
			rtnInfo.setForfeitInterest(UtilOperation.Arith.round(resultInfo
					.getForfeitInterest(), 2)
					+ UtilOperation.Arith
							.round(rtnInfo.getForfeitInterest(), 2));
			rtnInfo.setTotalInterest(UtilOperation.Arith.round(rtnInfo
					.getTotalInterest(), 2)
					+ UtilOperation.Arith.round(resultInfo
							.getSelfLoanInterest(), 2)
					+ UtilOperation.Arith.round(resultInfo
							.getConsignLoanInterest(), 2)
					+ UtilOperation.Arith.round(resultInfo
							.getCompoundInterest(), 2)
					+ UtilOperation.Arith.round(
							resultInfo.getForfeitInterest(), 2));
			rtnInfo.setCommission(UtilOperation.Arith.round(resultInfo
					.getCommission(), 2)
					+ UtilOperation.Arith.round(rtnInfo.getCommission(), 2));
			rtnInfo.setAssuranceCharge(UtilOperation.Arith.round(resultInfo
					.getAssuranceCharge(), 2)
					+ UtilOperation.Arith
							.round(rtnInfo.getAssuranceCharge(), 2));
			rtnInfo.setClearInterestDate(resultInfo.getClearInterestDate());
			rtnInfo.setTotal(UtilOperation.Arith.round(rtnInfo
					.getTotalInterest(), 2)
					+ UtilOperation.Arith
							.round(rtnInfo.getAssuranceCharge(), 2)
					+ UtilOperation.Arith.round(rtnInfo.getCommission(), 2));

			log.info("***************getTotal**" + rtnInfo.getTotal());
			log.info("***************getBalance**" + rtnInfo.getBalance());
			log.info("****************getBackInterest**"
					+ rtnInfo.getBackInterest());
			if (rtnInfo.getTotal() - rtnInfo.getBalance() > 0) {
				// rtnInfo.setBackInterest(rtnInfo.getBackInterest()+rtnInfo.getTotal()-resultInfo.getBalance());
				rtnInfo.setBackInterest(rtnInfo.getTotal()
						- rtnInfo.getBalance());
			} else {
				rtnInfo.setBackInterest(0);
			}
			rtnInfo.setNpayInterestAccountNO(resultInfo.getNpayInterestAccountNO());
			rtnInfo.setNpayInterestAccountID(resultInfo.getNpayInterestAccountID());
			rtnInfo.setNpayInterestAccountBalance(resultInfo.getNpayInterestAccountBalance());
		}
	}

	/**
	 * 匡算结息表
	 * 
	 * @param InterestEstimateQueryInfo
	 * @param loanType
	 *            0 自营 1 委贷
	 * @return
	 * @throws Exception
	 */
	public Vector getInterestEstimate(Connection con,
			InterestEstimateQueryInfo qInfo, long lLoanType) throws Exception {
		Vector rtnVec = new Vector();
		Connection conInternal = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (con == null) {
			conInternal = getConnection();
		} else {
			conInternal = con;
		}
		m_sbSelect = new StringBuffer();

		m_sbSelect.append(" distinct client.sname ClientName, \n");
		m_sbSelect.append(" client.scode ClientNo, \n");
		m_sbSelect.append(" client.ID ClientID, \n");
		m_sbSelect.append(" client.sAccount as LoanAccountNo, \n"); // 贷款账户号
		m_sbSelect.append(" account.ID AS AccountID, \n");
		m_sbSelect.append(" account.sAccountNo AS AccountNo,  \n"); // 账户号
		m_sbSelect.append(" account.nAccountTypeID AS AccountTypeID, \n"); // 账户类型
		m_sbSelect.append(" subaccount.ID AS SubAccountID, \n"); // 子账户 ID
		m_sbSelect.append(" subaccount.AF_sDepositNo AS FixedDepositNo, \n"); // 定期单据号
		m_sbSelect.append(" subaccount.mBalance AS Balance, \n"); // 余额
		m_sbSelect.append(" contractform.sContractCode AS ContractNo, \n"); // 合同号
		m_sbSelect.append(" contractform.NINTERVALNUM AS ContractTerm,\n "); // 合同期限
		m_sbSelect.append(" contractform.dtStartDate AS StartDate, \n");
		m_sbSelect.append(" contractform.dtEndDate AS EndDate, \n");

		// Boxu Add 只有选择委托方才需要
		if (qInfo.getIsSelectConsigner() > 0) {
			// Added by ylguo(郭英亮)添加了委托方信息的3个字段
			m_sbSelect.append(" consigner.id ConsignClientID, \n");
			m_sbSelect.append(" consigner.scode ConsignClientCode, \n");
			m_sbSelect.append(" consigner.sName ConsignClientName, \n");
			// 委托方信息添加结束
		}

		// m_sbSelect.append(" payform.dtStart AS StartDate, \n");
		// m_sbSelect.append(" payform.dtEnd AS EndDate, \n");
		m_sbSelect.append(" contractform.nTypeID AS LoanTypeID,\n"); // 贷款类型
		m_sbSelect.append(" payform.ID AS PayFormID, \n"); // 放款通知单ID
		m_sbSelect.append(" subaccount.mOpenAmount AS LoanAmount, \n"); // 贷款本金
		m_sbSelect.append(" payform.sCode AS PayFormNo , \n"); // 放款通知单号
		m_sbSelect.append(" payform.DTEND AS DtEnd , \n"); // 如果是贷款的话，有放款通知单的结束日期
		m_sbSelect.append(" contractform.nsubtypeid AS nsubTypeID, \n"); // 贷款子类型
		m_sbSelect.append(" subtype.name AS nsubTypeName, \n"); // 贷款子类型名称
		m_sbSelect.append(" subaccount.AL_NPAYINTERESTACCOUNTID AS npayInterestAccountID, \n");
		m_sbSelect.append(" account.saccountno AS npayInterestAccountNO \n");
		// from
		m_sbFrom = new StringBuffer();
		// m_sbFrom.append(" sett_Account account, SETT_ACCOUNTTYPE
		// accountType,sett_SubAccount subaccount, client,loan_PayForm payform,
		// loan_ContractForm contractform, loan_LoanForm loanform \n");
		m_sbFrom
				.append(" sett_Account account,SETT_ACCOUNTTYPE accountType, sett_SubAccount subaccount, client,loan_PayForm payform, loan_ContractForm contractform, loan_LoanForm loanform,client consigner,loan_loantypesetting subtype \n");
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
		m_sbWhere.append(" AND ");
		m_sbWhere.append(" subtype.id = contractform.nsubtypeid  \n");
		if (qInfo.getIsSelectConsigner() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere
					.append(" contractform.nConsignClientID = consigner.ID \n");
		}
		m_sbWhere.append(" AND account.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" AND subaccount.NSTATUSID = "
				+ SETTConstant.AccountStatus.NORMAL);
		// 不等于活期
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.CURRENT + " \n");
		// 不是通知
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.NOTIFY + " \n");
		// 不是贴现
		m_sbWhere.append("and accountType.NACCOUNTGROUPID!= "
				+ SETTConstant.AccountGroupType.DISCOUNT + " \n");
		if (qInfo.getOfficeID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nOfficeID = " + qInfo.getOfficeID());
		}
		if (qInfo.getCurrencyID() > 0) {
			m_sbWhere.append(" AND ");
			m_sbWhere.append(" account.nCurrencyID = " + qInfo.getCurrencyID());
		}
		if (qInfo.getIsSelectClientNo() > 0) {
			if (qInfo.getClientNoFrom() != null
					&& qInfo.getClientNoFrom().length() > 0)
				m_sbWhere.append("and client.scode>='"
						+ qInfo.getClientNoFrom() + "' \n");
			if (qInfo.getClientNoTo() != null
					&& qInfo.getClientNoTo().length() > 0)
				m_sbWhere.append("and client.scode<='" + qInfo.getClientNoTo()
						+ "' \n");
		}
		if (qInfo.getIsSelectClearInterestDate() > 0) {
			if (qInfo.getClearInterestDate() != null) { // 结息日
				m_sbWhere.append("and subaccount.dtClearInterest<= to_date('"
						+ DataFormat.formatDate(qInfo.getClearInterestDate())
						+ "','yyyy-mm-dd') \n");
			}
		}
		if (qInfo.getIsSelectSelfLoanSort() > 0) {
			if (qInfo.getSelfLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid="
						+ qInfo.getSelfLoanSort() + "");
			}
			if (qInfo.getSelfLoanSort() == 0) {
				m_sbWhere.append("and contractform.nTypeID in ( "
						+ LOANConstant.LoanType.TX + ","
						+ LOANConstant.LoanType.YT + ","
						+ LOANConstant.LoanType.ZGXE + ","
						+ LOANConstant.LoanType.ZY + ") ");
			}
		}
		if (lLoanType == 0) {
			m_sbWhere.append("and contractform.nTypeID in ( "
					+ LOANConstant.LoanType.TX + "," + LOANConstant.LoanType.YT
					+ "," + LOANConstant.LoanType.ZGXE + ","
					+ LOANConstant.LoanType.ZY + ") ");
		} else {
			m_sbWhere.append("and contractform.nTypeID in ("
					+ LOANConstant.LoanType.WT + ") ");
		}
		if (qInfo.getIsSelectSelfLoanTerm() > 0) {
			if (qInfo.getSelfLoanTermFrom() > 0) {
				m_sbWhere.append("and contractform.nIntervalNum >='"
						+ qInfo.getSelfLoanTermFrom() + "' \n");
			}
			if (qInfo.getSelfLoanTermTo() > 0) {
				m_sbWhere.append("and contractform.nIntervalNum <='"
						+ qInfo.getSelfLoanTermTo() + "' \n");
			}
		}
		if (qInfo.getIsSelectConsignLoanSort() > 0) {
			if (qInfo.getConsignLoanSort() > 0) {
				m_sbWhere.append("and contractform.nsubtypeid='"
						+ qInfo.getConsignLoanSort() + "' \n");
			}
			if (qInfo.getConsignLoanSort() == 0) {
				m_sbWhere.append("and contractform.nTypeID in ("
						+ LOANConstant.LoanType.WT + ") ");
			}
		}
		if (qInfo.getIsSelectConsigner() > 0) {
			if (qInfo.getConsignerFrom() != null
					&& qInfo.getConsignerFrom().length() > 0) {
				m_sbWhere.append("and consigner.scode>='"
						+ qInfo.getConsignerFrom() + "' \n");
			}
			if (qInfo.getConsignerTo() != null
					&& qInfo.getConsignerTo().length() > 0) {
				m_sbWhere.append("and consigner.scode<='"
						+ qInfo.getConsignerTo() + "' \n");
			}
		}
		if (qInfo.getIsSelectContractNo() > 0) {
			if (qInfo.getContractNoFrom() != null
					&& qInfo.getContractNoFrom().length() > 0) {
				m_sbWhere.append("and contractform.sContractCode>='"
						+ qInfo.getContractNoFrom() + "' \n");
			}
			if (qInfo.getContractNoTo() != null
					&& qInfo.getContractNoTo().length() > 0) {
				m_sbWhere.append("and contractform.sContractCode<='"
						+ qInfo.getContractNoTo() + "' \n");
			}
		}
		if (qInfo.getIsSelectPayFormNo() > 0) {
			if (qInfo.getPayFormNoFrom() != null
					&& qInfo.getPayFormNoFrom().length() > 0) {
				m_sbWhere.append("and payform.sCode>='"
						+ qInfo.getPayFormNoFrom() + "' \n");
			}
			if (qInfo.getPayFormNoTo() != null
					&& qInfo.getPayFormNoTo().length() > 0) {
				m_sbWhere.append("and payform.sCode<='"
						+ qInfo.getPayFormNoTo() + "' \n");
			}
		}
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy
				.append(" \n order by account.sAccountNo,ContractNo,PayFormNo \n");
		// String strDesc = " desc ";
		String strSql = "";
		strSql = "select " + m_sbSelect.toString() + " from "
				+ m_sbFrom.toString() + " where " + m_sbWhere.toString()
				+ m_sbOrderBy.toString();
		System.out.print(strSql);
		log.debug("select " + m_sbSelect.toString() + " from "
				+ m_sbFrom.toString() + " where " + m_sbWhere.toString()
				+ m_sbOrderBy.toString());

		try {
			ps = conInternal.prepareStatement(strSql);
			// 执行查询
			rs = ps.executeQuery();

			InterestOperation io = new InterestOperation(conInternal);
			InterestBatch ib = new InterestBatch(conInternal);
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(
					conInternal);
			while (rs.next()) {
				InterestEstimateQueryResultInfo infoRtn = null;
				InterestEstimateQueryResultInfo infoResult = null;
				infoRtn = this.fetchEstimateDataFromRS(rs);

				// Boxu Add 只有选择委托方才需要
				if (qInfo.getIsSelectConsigner() > 0) {
					// Added by ylguo(郭英亮)将委托方名称和编号添加进来
					infoRtn.setConsignClientID(rs.getLong("ConsignClientID"));
					infoRtn.setConsignClientCode(rs
							.getString("ConsignClientCode"));
					infoRtn.setConsignClientName(rs
							.getString("ConsignClientName"));
				}

				infoResult = getDetailInterestInfo(qInfo,
						SETTConstant.InterestFeeType.ASSURE, conInternal, rs,
						io, ib, transDetailDAO);
				log.info("-------设置活期余额----------");
				setCurrentBanlance(con, ib,
						SETTConstant.InterestFeeType.ASSURE, infoResult);
				setRtnInfo(infoRtn, infoResult);
				infoResult = getDetailInterestInfo(qInfo,
						SETTConstant.InterestFeeType.COMMISION, conInternal,
						rs, io, ib, transDetailDAO);
				log.info("-------设置活期余额----------");
				setCurrentBanlance(con, ib,
						SETTConstant.InterestFeeType.COMMISION, infoResult);
				setRtnInfo(infoRtn, infoResult);
				if (qInfo.getIsSelectForfeitInterest() > 0) {
					infoResult = getDetailInterestInfo(qInfo,
							SETTConstant.InterestFeeType.FORFEITINTEREST,
							conInternal, rs, io, ib, transDetailDAO);
					log.info("-------设置活期余额----------");
					setCurrentBanlance(con, ib,
							SETTConstant.InterestFeeType.FORFEITINTEREST,
							infoResult);
					setRtnInfo(infoRtn, infoResult);
				}
				if (qInfo.getIsSelectCompoundInterest() > 0) {
					infoResult = getDetailInterestInfo(qInfo,
							SETTConstant.InterestFeeType.COMPOUNDINTEREST,
							conInternal, rs, io, ib, transDetailDAO);
					log.info("-------设置活期余额----------");
					setCurrentBanlance(con, ib,
							SETTConstant.InterestFeeType.COMPOUNDINTEREST,
							infoResult);
					setRtnInfo(infoRtn, infoResult);
				}
				if (qInfo.getIsSelectInterest() > 0) {
					infoResult = getDetailInterestInfo(qInfo,
							SETTConstant.InterestFeeType.INTEREST, conInternal,
							rs, io, ib, transDetailDAO);
					log.info("-------设置活期余额----------");
					setCurrentBanlance(con, ib,
							SETTConstant.InterestFeeType.INTEREST, infoResult);
					setRtnInfo(infoRtn, infoResult);
				}

				rtnVec.addElement(infoRtn);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询失败！");
		} finally { // 释放资源
			cleanup(rs);
			cleanup(ps);
			if (con == null) {
				cleanup(conInternal);
			}
		}

		return rtnVec;
	}

	/**
	 * 
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	private InterestEstimateQueryResultInfo fetchEstimateDataFromRS(ResultSet rs)
			throws Exception {
		// int i = 0; //lhj debug data
		InterestEstimateQueryResultInfo info = new InterestEstimateQueryResultInfo();
		info.setAccountNo(rs.getString("AccountNo"));
		info.setAccountTypeID(rs.getLong("AccountTypeID"));
		info.setAccountID(rs.getLong("AccountID"));
		info.setSubAccountID(rs.getLong("SubAccountID"));
		info.setFixedDepositNo(rs.getString("FixedDepositNo"));
		info.setContractNo(rs.getString("ContractNo"));
		info.setPayFormNo(rs.getString("PayFormNo"));
		info.setLoanAccountNo(rs.getString("LoanAccountNo"));
		info.setContractTerm(rs.getLong("ContractTerm"));
		info.setLoanAmount(rs.getDouble("LoanAmount"));
		info.setStartDate(rs.getTimestamp("StartDate"));
		info.setEndDate(rs.getTimestamp("EndDate"));
		info.setBalance(rs.getDouble("Balance"));
		info.setPayFormID(rs.getLong("PayFormID"));
		info.setLoanTypeID(rs.getLong("LoanTypeID"));

		return info;

	}

	// 算出一条记录的利息
	private InterestEstimateQueryResultInfo getDetailInterestInfo(
			InterestEstimateQueryInfo queryInfo, long nInterestTypeID,
			Connection con, ResultSet rs, InterestOperation io,
			InterestBatch ib, sett_TransAccountDetailDAO transDetailDAO)
			throws SQLException, IException, Exception {
		Connection conInternal = null;
		if (con == null) {
			conInternal = this.getConnection();
		} else {
			conInternal = con;
		}
		InterestEstimateQueryResultInfo resultInfo = new InterestEstimateQueryResultInfo();
		resultInfo.setAccountNo(rs.getString("accountNo"));
		resultInfo.setAccountTypeID(rs.getLong("accountTypeID"));
		resultInfo.setAccountID(rs.getLong("accountID"));
		resultInfo.setSubAccountID(rs.getLong("subAccountID"));
		resultInfo.setLoanTypeID(rs.getLong("LoanTypeID"));
		resultInfo.setPayFormID(rs.getLong("PayFormID"));
		resultInfo.setClientID(rs.getLong("ClientID"));
		resultInfo.setClientName(rs.getString("ClientName"));
		resultInfo.setClientNo(rs.getString("ClientNo"));
		resultInfo.setContractNo(rs.getString("ContractNo"));
		resultInfo.setPayFormNo(rs.getString("PayFormNo"));
		resultInfo.setNsubTypeID(rs.getLong("nsubTypeID"));
		resultInfo.setNsubTypeName(rs.getString("nsubTypeName"));
		resultInfo.setNpayInterestAccountNO(rs.getString("npayInterestAccountNO"));
		resultInfo.setNpayInterestAccountID(rs.getLong("npayInterestAccountID"));
		/*
		 * 如果不过滤，并且“放款通知单”的结束日期存在（贷款），并且“放款通知单”的结束日期在选择的匡算日期之前 将匡算日改为结束日。
		 * 
		 */
		if (queryInfo.getDoFilterDate() != 1
				&& // 是否过滤 1 过滤 -1 不过滤
				rs.getTimestamp("DtEnd") != null
				&& rs.getTimestamp("DtEnd").before(
						queryInfo.getClearInterestDate())) {
			resultInfo.setClearInterestDate(rs.getTimestamp("DtEnd"));

		} else {
			resultInfo.setClearInterestDate(queryInfo.getClearInterestDate());
		}

		if (SETTConstant.AccountType.isFixAccountType(resultInfo
				.getAccountTypeID())
				|| SETTConstant.AccountType.isNotifyAccountType(resultInfo
						.getAccountTypeID())) {
			log.info("lhj debug info ===进入定期算息=======");
			FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
			if (nInterestTypeID == SETTConstant.InterestFeeType.INTEREST) {
				log.info("--------------开始定期算息------------");
				fixedInterestInfo = io.getFixedDepositAccountPayableInterest(
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(), resultInfo
								.getClearInterestDate());
				log.info("--------------结束定期算息------------");

				resultInfo.setInterest(fixedInterestInfo.getInterest());
				resultInfo.setInterestBalance(fixedInterestInfo.getBalance());
				Sett_SubAccountDAO subAccDao = new Sett_SubAccountDAO(con);
				double banlance = subAccDao.findByID(
						resultInfo.getSubAccountID()).getSubAccountFixedInfo()
						.getBalance();
				resultInfo.setBalance(banlance);
			}
			log.info("lhj debug info ===结束定期算息=======");
		} else if (SETTConstant.AccountType.isCurrentAccountType(resultInfo
				.getAccountTypeID())
				|| SETTConstant.AccountType.isNotifyAccountType(resultInfo
						.getAccountTypeID())) {
			log.info("lhj debug info ===进入活期算息=======");
			CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();

			Collection coll = null;
			log.info("-------------判断是否需要单户倒填---------");
			coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(),
					resultInfo.getSubAccountID(), queryInfo.getCurrencyID(),
					queryInfo.getOfficeID(), queryInfo.getClearInterestDate());

			Iterator itResult = null;
			if (coll != null && coll.size() > 0) {
				itResult = coll.iterator();
				if (itResult != null && itResult.hasNext()) {
					while (itResult.hasNext()) {
						TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult
								.next();
						log.info("-------------开始单户倒填---------");
						long flag = ib.accountInterestSettlelmentBackward(
								resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo
										.getDtInterestStart(), detailInfo
										.getAmount(), queryInfo.getOfficeID(),
								queryInfo.getCurrencyID(), Env.getSystemDate(
										conInternal, queryInfo.getOfficeID(),
										queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.INTEREST);
						if (flag < 0) {
							throw new IException("单户倒填失败");
						}
						log.info("-------------单户倒填结束---------");
					}
				}
			}
			log.info("-------------判断是否需要单户倒填结束---------");
			log.info("-------------算息开始---------");
			currInterestInfo = io.getCurrentDepositAccountInterest(queryInfo
					.getOfficeID(), queryInfo.getCurrencyID(), resultInfo
					.getSubAccountID(), queryInfo.getClearInterestDate(), Env
					.getSystemDate(conInternal, queryInfo.getOfficeID(),
							queryInfo.getCurrencyID()));

			resultInfo.setInterestBalance(currInterestInfo.getNormalBalance());
			// 当日余额
			Sett_SubAccountDAO subAccDao = new Sett_SubAccountDAO(con);
			double banlance = subAccDao.findByID(resultInfo.getSubAccountID())
					.getSubAccountCurrenctInfo().getBalance();
			resultInfo.setBalance(banlance);
			resultInfo.setInterest(currInterestInfo.getNormalInterest());

			// 结息日
			log.info("-------------算息结束---------");

			log.info("lhj debug info ===结束活期算息=======");
		} else if (SETTConstant.AccountType.isLoanAccountType(resultInfo
				.getAccountTypeID()))// 贷款
		{
			log.info("lhj debug info ===进入贷款算息=======");
			LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();

			if (nInterestTypeID == SETTConstant.InterestFeeType.INTEREST) {
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll = transDetailDAO.findByIsBackward(resultInfo
						.getAccountID(), resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(), queryInfo.getOfficeID(),
						resultInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0) {
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext()) {
						while (itResult.hasNext()) {
							TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult
									.next();
							log.info("-------------开始单户倒填---------");
							long flag = ib.accountInterestSettlelmentBackward(
									resultInfo.getAccountID(), resultInfo
											.getSubAccountID(), detailInfo
											.getDtInterestStart(), detailInfo
											.getAmount(), queryInfo
											.getOfficeID(), queryInfo
											.getCurrencyID(), Env
											.getSystemDate(conInternal,
													queryInfo.getOfficeID(),
													queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.INTEREST);
							if (flag < 0) {
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				log.info("-------------判断是否需要单户倒填结束---------");

				// 利息
				log.info("-------------算息开始---------");
				log.info("-----匡算--------"+resultInfo.getClearInterestDate()+"--------"+(iii)+"--------");
				loanInterestInfo = io.GetLoanAccountInterest(queryInfo
						.getOfficeID(), queryInfo.getCurrencyID(), resultInfo
						.getAccountID(), resultInfo.getSubAccountID(),
						resultInfo.getClearInterestDate(), Env.getSystemDate(
								conInternal, queryInfo.getOfficeID(), queryInfo
										.getCurrencyID()));
				log.info("-------------算息结束---------");

				// 利息
				resultInfo.setInterest(loanInterestInfo.getInterest());
				log.info("-----匡算第"+(iii)+"次利息--------"+loanInterestInfo.getInterest()+"----------------");
				iii++;
				/*
				 * if(resultInfo.getLoanTypeID()==LOANConstant.LoanType.YTDQ ||
				 * resultInfo.getLoanTypeID()==LOANConstant.LoanType.YTZCQ) {
				 * BankLoanQuery bankLoanQuery =new BankLoanQuery(); //承贷比例
				 * log.info("--------银团贷款操作——将利息数据按承贷比例转换"); double dRate = 0.0;
				 * try {
				 * dRate=bankLoanQuery.findRateByFormID(resultInfo.getPayFormID()); }
				 * catch (Exception e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 * 
				 * if(dRate>0) {
				 * resultInfo.setInterest(resultInfo.getInterest()/dRate*100); } }
				 */
				// 活期余额
				resultInfo.setInterestBalance(loanInterestInfo.getBalance());
				// 当日余额
				Sett_SubAccountDAO subAccDao = new Sett_SubAccountDAO(con);
				double banlance = subAccDao.findByID(
						resultInfo.getSubAccountID()).getSubAccountLoanInfo()
						.getBalance();
				resultInfo.setBalance(banlance);
				// 委托贷款账户
				if (SETTConstant.AccountType.isConsignAccountType(resultInfo
						.getAccountTypeID())) {
					// 委托贷款利息
					resultInfo.setConsignLoanInterest(loanInterestInfo
							.getInterest());
					// 委托贷款账户

					InterestTaxInfo taxInfo = new InterestTaxInfo();

					/**
					 * taxInfo = io.getInterestTax(resultInfo.getAccountID(),
					 * resultInfo.getSubAccountID(), resultInfo .getInterest());
					 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					 * resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
					 */

					// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
					taxInfo = io.getInterestTaxByPlan(
							resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo
									.getInterest());
					resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
					resultInfo.setInterestTaxRatePlan(taxInfo
							.getInterestTaxPlanId());

				} else {
					// 自营贷款利息
					resultInfo.setSelfLoanInterest(loanInterestInfo
							.getInterest());
				}
				// 委托贷款账户
				if (SETTConstant.AccountType.isConsignAccountType(resultInfo
						.getAccountTypeID())) {
					InterestTaxInfo taxInfo = new InterestTaxInfo();

					/**
					 * taxInfo = io.getInterestTax(resultInfo.getAccountID(),
					 * resultInfo.getSubAccountID(), resultInfo .getInterest());
					 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					 * resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
					 */

					// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
					taxInfo = io.getInterestTaxByPlan(
							resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo
									.getInterest());
					resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
					resultInfo.setInterestTaxRatePlan(taxInfo
							.getInterestTaxPlanId());
				}

			}
			if (nInterestTypeID == SETTConstant.InterestFeeType.COMMISION) {
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll = transDetailDAO.findByIsBackward(resultInfo
						.getAccountID(), resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(), queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0) {
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext()) {
						while (itResult.hasNext()) {
							TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult
									.next();
							log.info("-------------开始单户倒填---------");
							long flag = ib.accountInterestSettlelmentBackward(
									resultInfo.getAccountID(), resultInfo
											.getSubAccountID(), detailInfo
											.getDtInterestStart(), detailInfo
											.getAmount(), queryInfo
											.getOfficeID(), queryInfo
											.getCurrencyID(), Env
											.getSystemDate(conInternal,
													queryInfo.getOfficeID(),
													queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.INTEREST);
							if (flag < 0) {
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				log.info("-------------判断是否需要单户倒填结束---------");
				log.info("-------------算手续费开始---------");
				loanInterestInfo = io.getLoanAccountFee(
						queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(), queryInfo
								.getClearInterestDate(), Env.getSystemDate(
								conInternal, queryInfo.getOfficeID(), queryInfo
										.getCurrencyID()),
						SETTConstant.InterestFeeType.COMMISION);
				log.info("-------------算手续费结束---------");
				resultInfo.setCommission(loanInterestInfo.getInterest());
				// 银团没有手续费
				if (resultInfo.getLoanTypeID() == LOANConstant.LoanType.YT) {
					resultInfo.setCommission(0);
				}
				// 活期余额
				resultInfo.setInterestBalance(loanInterestInfo.getBalance());
				// 当日余额
				Sett_SubAccountDAO subAccDao = new Sett_SubAccountDAO(con);
				double banlance = subAccDao.findByID(
						resultInfo.getSubAccountID()).getSubAccountLoanInfo()
						.getBalance();
				resultInfo.setBalance(banlance);
			} else if (nInterestTypeID == SETTConstant.InterestFeeType.ASSURE) {
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll = transDetailDAO.findByIsBackward(resultInfo
						.getAccountID(), resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(), queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0) {
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext()) {
						while (itResult.hasNext()) {
							TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult
									.next();
							log.info("-------------开始单户倒填---------");
							long flag = ib.accountInterestSettlelmentBackward(
									resultInfo.getAccountID(), resultInfo
											.getSubAccountID(), detailInfo
											.getDtInterestStart(), detailInfo
											.getAmount(), queryInfo
											.getOfficeID(), queryInfo
											.getCurrencyID(), Env
											.getSystemDate(conInternal,
													queryInfo.getOfficeID(),
													queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.INTEREST);
							if (flag < 0) {
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				log.info("-------------判断是否需要单户倒填结束---------");
				log.info("-------------算担保费开始---------");
				loanInterestInfo = io.getLoanAccountFee(
						queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
						resultInfo.getAccountID(),
						resultInfo.getSubAccountID(), queryInfo
								.getClearInterestDate(), Env.getSystemDate(
								conInternal, queryInfo.getOfficeID(), queryInfo
										.getCurrencyID()),
						SETTConstant.InterestFeeType.ASSURE);
				log.info("-------------算担保费结束---------");
				// 担保费
				resultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
				// 活期余额
				resultInfo.setInterestBalance(loanInterestInfo.getBalance());
				// 当日余额
				Sett_SubAccountDAO subAccDao = new Sett_SubAccountDAO(con);
				double banlance = subAccDao.findByID(
						resultInfo.getSubAccountID()).getSubAccountLoanInfo()
						.getBalance();
				resultInfo.setBalance(banlance);
				// 利息税费
				InterestTaxInfo taxInfo = new InterestTaxInfo();

				/**
				 * taxInfo = io.getInterestTax(resultInfo.getAccountID(),
				 * resultInfo.getSubAccountID(), resultInfo
				 * .getAssuranceCharge());
				 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
				 */

				// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
				taxInfo = io.getInterestTaxByPlan(resultInfo.getAccountID(),
						resultInfo.getSubAccountID(), resultInfo.getInterest());
				resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
				resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
				resultInfo.setInterestTaxRatePlan(taxInfo
						.getInterestTaxPlanId());

			}

			if (nInterestTypeID == SETTConstant.InterestFeeType.COMPOUNDINTEREST) {
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll = transDetailDAO.findByIsBackward(resultInfo
						.getAccountID(), resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(), queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());

				Iterator itResult = null;
				if (coll != null && coll.size() > 0) {
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext()) {
						while (itResult.hasNext()) {
							TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult
									.next();
							log.info("-------------开始单户倒填---------");
							long flag = ib.accountInterestSettlelmentBackward(
									resultInfo.getAccountID(), resultInfo
											.getSubAccountID(), detailInfo
											.getDtInterestStart(), detailInfo
											.getAmount(), queryInfo
											.getOfficeID(), queryInfo
											.getCurrencyID(), Env
											.getSystemDate(conInternal,
													queryInfo.getOfficeID(),
													queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.INTEREST);
							if (flag < 0) {
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				log.info("-------------判断是否需要单户倒填结束---------");

				log.info("-------------算复利开始---------");
				// 复利
				loanInterestInfo = io.getLoanGuoDianAccountFee(queryInfo
						.getOfficeID(), queryInfo.getCurrencyID(), resultInfo
						.getAccountID(), resultInfo.getSubAccountID(),
						queryInfo.getClearInterestDate(), Env.getSystemDate(
								conInternal, queryInfo.getOfficeID(), queryInfo
										.getCurrencyID()),
						SETTConstant.InterestFeeType.COMPOUNDINTEREST);
				log.info("-------------算复利结束---------");

				// 复利
				resultInfo.setCompoundInterest(loanInterestInfo.getInterest());
				// 活期余额
				resultInfo.setInterestBalance(loanInterestInfo.getBalance());
				// 当日余额
				Sett_SubAccountDAO subAccDao = new Sett_SubAccountDAO(con);
				double banlance = subAccDao.findByID(
						resultInfo.getSubAccountID()).getSubAccountLoanInfo()
						.getBalance();
				resultInfo.setBalance(banlance);
				// 委托贷款
				if (SETTConstant.AccountType.isConsignAccountType(resultInfo
						.getAccountTypeID())) {
					// 利息税费
					InterestTaxInfo taxInfo = new InterestTaxInfo();

					/**
					 * taxInfo = io.getInterestTax(resultInfo.getAccountID(),
					 * resultInfo.getSubAccountID(), resultInfo
					 * .getCompoundInterest());
					 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					 * resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
					 */

					// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
					taxInfo = io.getInterestTaxByPlan(
							resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo
									.getInterest());
					resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
					resultInfo.setInterestTaxRatePlan(taxInfo
							.getInterestTaxPlanId());
				}
			}

			if (nInterestTypeID == SETTConstant.InterestFeeType.FORFEITINTEREST) {
				Collection coll = null;
				log.info("-------------判断是否需要单户倒填---------");
				coll = transDetailDAO.findByIsBackward(resultInfo
						.getAccountID(), resultInfo.getSubAccountID(),
						queryInfo.getCurrencyID(), queryInfo.getOfficeID(),
						queryInfo.getClearInterestDate());
				Iterator itResult = null;
				if (coll != null && coll.size() > 0) {
					itResult = coll.iterator();
					if (itResult != null && itResult.hasNext()) {
						while (itResult.hasNext()) {
							TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult
									.next();
							log.info("-------------开始单户倒填---------");
							long flag = ib.accountInterestSettlelmentBackward(
									resultInfo.getAccountID(), resultInfo
											.getSubAccountID(), detailInfo
											.getDtInterestStart(), detailInfo
											.getAmount(), queryInfo
											.getOfficeID(), queryInfo
											.getCurrencyID(), Env
											.getSystemDate(conInternal,
													queryInfo.getOfficeID(),
													queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.INTEREST);
							if (flag < 0) {
								throw new IException("单户倒填失败");
							}
							log.info("-------------单户倒填结束---------");
						}
					}
				}
				log.info("-------------判断是否需要单户倒填结束---------");

				log.info("-------------算罚息开始---------");
				// 罚息
				loanInterestInfo = io.getLoanGuoDianAccountFee(queryInfo
						.getOfficeID(), queryInfo.getCurrencyID(), resultInfo
						.getAccountID(), resultInfo.getSubAccountID(),
						queryInfo.getClearInterestDate(), Env.getSystemDate(
								conInternal, queryInfo.getOfficeID(), queryInfo
										.getCurrencyID()),
						SETTConstant.InterestFeeType.FORFEITINTEREST);
				log.info("-------------算罚息结束---------");

				// 逾期罚息
				resultInfo.setForfeitInterest(loanInterestInfo.getInterest());
				// 活期余额
				resultInfo.setInterestBalance(loanInterestInfo.getBalance());
				// 当日余额
				Sett_SubAccountDAO subAccDao = new Sett_SubAccountDAO(con);
				double banlance = subAccDao.findByID(
						resultInfo.getSubAccountID()).getSubAccountLoanInfo()
						.getBalance();
				resultInfo.setBalance(banlance);
				// 利息税费
				// 委托贷款
				if (SETTConstant.AccountType.isConsignAccountType(resultInfo
						.getAccountTypeID())) {
					// 利息税费
					InterestTaxInfo taxInfo = new InterestTaxInfo();

					/**
					 * taxInfo = io.getInterestTax(resultInfo.getAccountID(),
					 * resultInfo.getSubAccountID(), resultInfo
					 * .getCompoundInterest());
					 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					 * resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
					 */

					// 求取利息税费的方式发生变更，改成从利息税费率计划中获取
					taxInfo = io.getInterestTaxByPlan(
							resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo
									.getInterest());
					resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
					resultInfo.setInterestTaxRatePlan(taxInfo
							.getInterestTaxPlanId());
				}
			}
			/*
			 * } else { if (nInterestTypeID ==
			 * SETTConstant.InterestFeeType.COMPOUNDINTEREST) { Collection coll =
			 * null; log.info("-------------判断是否需要单户倒填---------"); coll =
			 * transDetailDAO.findByIsBackward(resultInfo .getAccountID(),
			 * resultInfo.getSubAccountID(), queryInfo.getCurrencyID(),
			 * queryInfo.getOfficeID(), queryInfo.getClearInterestDate());
			 * Iterator itResult = null; if (coll != null && coll.size() > 0) {
			 * itResult = coll.iterator(); if (itResult != null &&
			 * itResult.hasNext()) { while (itResult.hasNext()) {
			 * TransAccountDetailInfo detailInfo = (TransAccountDetailInfo)
			 * itResult .next(); log.info("-------------开始单户倒填---------"); long
			 * flag = ib.accountInterestSettlelmentBackward(
			 * resultInfo.getAccountID(), resultInfo .getSubAccountID(),
			 * detailInfo .getDtInterestStart(), detailInfo .getAmount(),
			 * queryInfo .getOfficeID(), queryInfo .getCurrencyID(), Env
			 * .getSystemDate(conInternal, queryInfo.getOfficeID(),
			 * queryInfo.getCurrencyID())); if (flag < 0) { throw new
			 * IException("单户倒填失败"); } log.info("-------------单户倒填结束---------"); } } }
			 * log.info("-------------判断是否需要单户倒填结束---------");
			 * log.info("-------------算复利开始---------"); //复利 loanInterestInfo =
			 * io.getLoanAccountFee( queryInfo.getOfficeID(),
			 * queryInfo.getCurrencyID(), resultInfo.getAccountID(),
			 * resultInfo.getSubAccountID(), queryInfo .getClearInterestDate(),
			 * Env.getSystemDate( conInternal, queryInfo.getOfficeID(),
			 * queryInfo .getCurrencyID()),
			 * SETTConstant.InterestFeeType.COMPOUNDINTEREST);
			 * log.info("-------------算复利结束---------"); //复利
			 * resultInfo.setCompoundInterest(loanInterestInfo.getInterest());
			 * //活期余额
			 * resultInfo.setInterestBalance(loanInterestInfo.getBalance());
			 * //当日余额 Sett_SubAccountDAO subAccDao = new
			 * Sett_SubAccountDAO(con); double banlance = subAccDao.findByID(
			 * resultInfo.getSubAccountID()).getSubAccountLoanInfo()
			 * .getBalance(); resultInfo.setBalance(banlance); //委托贷款 if
			 * (SETTConstant.AccountType.isConsignAccountType(resultInfo.getAccountTypeID())) {
			 * //利息税费 InterestTaxInfo taxInfo = new InterestTaxInfo();
			 * 
			 *//**
				 * taxInfo = io.getInterestTax(resultInfo.getAccountID(),
				 * resultInfo.getSubAccountID(), resultInfo
				 * .getCompoundInterest());
				 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
				 * resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
				 */
			/*
			 * 
			 * //求取利息税费的方式发生变更，改成从利息税费率计划中获取 taxInfo = io.getInterestTaxByPlan(
			 * resultInfo.getAccountID(), resultInfo.getSubAccountID(),
			 * resultInfo.getInterest());
			 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
			 * resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
			 * resultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId()); } }
			 * 
			 * if (nInterestTypeID ==
			 * SETTConstant.InterestFeeType.FORFEITINTEREST) { Collection coll =
			 * null; log.info("-------------判断是否需要单户倒填---------"); coll =
			 * transDetailDAO.findByIsBackward(resultInfo .getAccountID(),
			 * resultInfo.getSubAccountID(), queryInfo.getCurrencyID(),
			 * queryInfo.getOfficeID(), queryInfo.getClearInterestDate());
			 * Iterator itResult = null; if (coll != null && coll.size() > 0) {
			 * itResult = coll.iterator(); if (itResult != null &&
			 * itResult.hasNext()) { while (itResult.hasNext()) {
			 * TransAccountDetailInfo detailInfo = (TransAccountDetailInfo)
			 * itResult .next(); log.info("-------------开始单户倒填---------"); long
			 * flag = ib.accountInterestSettlelmentBackward(
			 * resultInfo.getAccountID(), resultInfo .getSubAccountID(),
			 * detailInfo .getDtInterestStart(), detailInfo .getAmount(),
			 * queryInfo .getOfficeID(), queryInfo .getCurrencyID(), Env
			 * .getSystemDate(conInternal, queryInfo.getOfficeID(),
			 * queryInfo.getCurrencyID())); if (flag < 0) { throw new
			 * IException("单户倒填失败"); } log.info("-------------单户倒填结束---------"); } } }
			 * log.info("-------------判断是否需要单户倒填结束---------");
			 * log.info("-------------算罚息开始---------"); //罚息 loanInterestInfo =
			 * io.getLoanAccountFee( queryInfo.getOfficeID(),
			 * queryInfo.getCurrencyID(), resultInfo.getAccountID(),
			 * resultInfo.getSubAccountID(), queryInfo .getClearInterestDate(),
			 * Env.getSystemDate( conInternal, queryInfo.getOfficeID(),
			 * queryInfo .getCurrencyID()),
			 * SETTConstant.InterestFeeType.FORFEITINTEREST);
			 * log.info("-------------算罚息结束---------"); //逾期罚息
			 * resultInfo.setForfeitInterest(loanInterestInfo.getInterest());
			 * //活期余额
			 * resultInfo.setInterestBalance(loanInterestInfo.getBalance());
			 * //当日余额 Sett_SubAccountDAO subAccDao = new
			 * Sett_SubAccountDAO(con); double banlance = subAccDao.findByID(
			 * resultInfo.getSubAccountID()).getSubAccountLoanInfo()
			 * .getBalance(); resultInfo.setBalance(banlance); //利息税费 //委托贷款 if
			 * (SETTConstant.AccountType.isConsignAccountType(resultInfo.getAccountTypeID())) {
			 * //利息税费 InterestTaxInfo taxInfo = new InterestTaxInfo();
			 * 
			 *//**
				 * taxInfo = io.getInterestTax(resultInfo.getAccountID(),
				 * resultInfo.getSubAccountID(), resultInfo
				 * .getCompoundInterest());
				 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
				 * resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
				 */
			/*
			 * 
			 * //求取利息税费的方式发生变更，改成从利息税费率计划中获取 taxInfo = io.getInterestTaxByPlan(
			 * resultInfo.getAccountID(), resultInfo.getSubAccountID(),
			 * resultInfo.getInterest());
			 * resultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
			 * resultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
			 * resultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId()); } } }
			 */

			log.info("lhj debug info ===结束贷款算息=======");
		}

		return resultInfo;
	}

	// 根据客户ID返回各种合计利息信息
	public InterestEstimateQueryResultInfo queryDetailForClient(
			InterestEstimateQueryInfo queryInfo, long lClientID)
			throws Exception {
		InterestEstimateQueryResultInfo rtnInfo = new InterestEstimateQueryResultInfo();
		Vector rtnVec = new Vector();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer m_sbSelect1 = null;
		String strClientWhere = "";

		String sql = ""; //

		try {
			getInterestEstimateInfoClientSQL(queryInfo);
			m_sbSelect1 = new StringBuffer();
			// select

			m_sbSelect1.append(" distinct account.ID AS accountID, \n");
			m_sbSelect1.append(" account.sAccountNo AS accountNo, \n");
			m_sbSelect1.append(" account.nAccountTypeID AS accountTypeID, \n");
			m_sbSelect1.append(" subaccount.ID AS subAccountID, \n");
			m_sbSelect1.append(" contractform.nTypeID AS LoanTypeID,\n"); // 贷款类型
			m_sbSelect1.append(" payform.ID AS PayFormID, \n"); // 放款通知单ID
			m_sbSelect1.append(" client.sname ClientName, \n");
			m_sbSelect1.append(" client.scode ClientNo, \n");
		 	m_sbSelect1.append(" contractform.scontractcode ContractNo, \n");
		 	m_sbSelect1.append(" payform.scode PayFormNo, \n");
			m_sbSelect1.append(" contractform.nsubtypeid nsubTypeID, \n");
			m_sbSelect1.append(" '' nsubTypeName, \n");

			// Boxu Add 只有选择委托方才需要
			if (queryInfo.getIsSelectConsigner() > 0) {
				// Added by ylguo 添加了委托方名称和编号,ID的字段
				m_sbSelect1.append(" consigner.id ConsignClientID, \n");
				m_sbSelect1.append(" consigner.scode ConsignClientCode, \n");
				m_sbSelect1.append(" consigner.sName ConsignClientName, \n");
			}

			m_sbSelect1.append(" client.ID ClientID ,\n");
			m_sbSelect1.append(" payform.DTEND DtEnd, \n"); // 如果是贷款的话，有放款通知单的结束日期
			m_sbSelect1.append(" subaccount.AL_NPAYINTERESTACCOUNTID as npayInterestAccountID, \n");
			m_sbSelect1.append(" payInterestAccount.saccountno npayInterestAccountNO \n");

			strClientWhere = "  and payInterestAccount.id = subaccount.AL_NPAYINTERESTACCOUNTID   and client.ID =" + lClientID + " \n";
			con = this.getConnection();
			sql = "select " + m_sbSelect1.toString() + " from sett_Account payInterestAccount, "
					+ m_sbFrom.toString() + " where " + m_sbWhere.toString()
					+ strClientWhere;
			log.info(sql);
			InterestOperation io = new InterestOperation(con);
			InterestBatch ib = new InterestBatch(con);
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(
					con);
			// logger.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			log.debug(UtilOperation.dataentityToString(queryInfo));
			while (rs.next()) {
				long nInterestType = -1;
				if (queryInfo.getIsSelectInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.INTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getIsSelectCompoundInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.COMPOUNDINTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getIsSelectForfeitInterest() > 0) {
					nInterestType = SETTConstant.InterestFeeType.FORFEITINTEREST;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE) {
					nInterestType = SETTConstant.InterestFeeType.ASSURE;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);
				}
				if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION) {
					nInterestType = SETTConstant.InterestFeeType.COMMISION;
					InterestEstimateQueryResultInfo resultInfo = null;
					resultInfo = getDetailInterestInfo(queryInfo,
							nInterestType, con, rs, io, ib, transDetailDAO);
					// 设置活期余额
					setCurrentBanlance(con, ib, nInterestType, resultInfo);
					setRtnInfo(rtnInfo, resultInfo);
				}

			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return rtnInfo;
	}

	private void setCurrentBanlance(Connection con, InterestBatch ib,
			long nInterestType, InterestEstimateQueryResultInfo resultInfo)
			throws IException, SQLException {
		if (resultInfo != null) {
			try
			{
				log.info("-------------得到付息账户号开始得到活期余额---------");
				double banlance = 0.0;
				AccountOperation au = new AccountOperation(con);
				banlance = au.findAvailableBalance(resultInfo.getNpayInterestAccountID(), 1, 1);
				resultInfo.setNpayInterestAccountBalance(banlance);
				log.info("-------------得到付息账户号结束得到活期余额---------");
			}
			catch(Exception e)
			{	
				throw new IException(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryInterestEstimateAccountInfo(
			InterestEstimateQueryInfo qInfo) throws Exception {

		getInterestEstimateInfoAccountSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询贷款利率，对应 应付利息查询 功能
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryInterest4Loan(InterestEstimateQueryInfo qInfo)
			throws Exception {

		getInterest4LoanSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo",
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
	public PageLoader queryInterestEstimateClientInfo(
			InterestEstimateQueryInfo qInfo) throws Exception {

		getInterestEstimateInfoClientSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.settlement.interest.dataentity.InterestEstimateQueryResultInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * added by mzh_fu 2008/03/22
	 * 
	 * @param currentDay
	 * @param n
	 * @return
	 */
	public Timestamp getNextNDay(Timestamp currentDay, int n) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDay);
		calendar.add(Calendar.DATE, n);
		java.util.Date resDate = calendar.getTime();
		return new Timestamp(resDate.getTime());
	}

	/**
	 * added by xwhe 2008/06/20
	 * 
	 * @param clientID
	 * @param
	 * @return
	 */
	public Collection findCurrentBalanceByClientID(long clientID,
			long nOfficeID, long nCurrencyID) throws Exception {
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try {
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select m.saccountno,  (nvl(p.mbalance, 0)-nvl(p.muncheckpaymentamount, 0))as mbalance,c.id  ");
			sbSQL
					.append(" from sett_account m, sett_accounttype n, sett_subaccount p, client c ");
			sbSQL.append(" where ");
			sbSQL
					.append(" m.id = p.naccountid  and m.naccounttypeid = n.id and m.nclientid = c.id ");
			sbSQL
					.append(" and n.saccounttypecode!= "
							+ Config
									.getProperty(
											ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,
											"2"));// 国电需求
			if (nOfficeID > 0) {
				sbSQL.append(" and m.nofficeid =" + nOfficeID);
			}
			if (nCurrencyID > 0) {
				sbSQL.append(" and m.ncurrencyid =" + nCurrencyID);
			}
			sbSQL.append(" and m.NSTATUSID = "
					+ SETTConstant.AccountStatus.NORMAL);
			sbSQL.append(" and p.NSTATUSID = "
					+ SETTConstant.AccountStatus.NORMAL);
			sbSQL.append(" and n.NACCOUNTGROUPID = "
					+ SETTConstant.AccountGroupType.CURRENT + " \n");
			sbSQL.append(" and m.nclientid = " + clientID);
			sbSQL.append(" order by saccountno ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				InterestEstimateQueryResultInfo resultInfo = new InterestEstimateQueryResultInfo();
				resultInfo.setAccountNo(rs.getString("saccountno"));
				resultInfo.setBalance(rs.getDouble("mbalance"));
				v.add(resultInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}

	/**
	 * 得到付息账户的信息，主要是付息账号和付息账户余额
	 * 
	 * @param npayInterestAccountID
	 * @return
	 * @throws Exception 
	 */
	public InterestEstimateQueryResultInfo getPayInterestAccountInfo(long npayInterestAccountID) throws Exception {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sbSQL = null;
		InterestEstimateQueryResultInfo resultInfo = new InterestEstimateQueryResultInfo();
		try {
			conn = getConnection();
			sbSQL = "select a.saccountno ,b.balance from sett_account a,(select naccountid,sum(MBALANCE) as balance from sett_subaccount where naccountid="
					+ npayInterestAccountID
					+ " group by naccountid) b where a.id=b.naccountid and b.naccountid="
					+ npayInterestAccountID;
			ps = conn.prepareStatement(sbSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				
				resultInfo.setNpayInterestAccountNO(rs.getString("saccountno"));
				resultInfo.setNpayInterestAccountBalance(rs.getDouble("balance"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return resultInfo;
	}
	
	/**
	 * 查找结息打印信息
	 */
	public ArrayList queryAllTypeInterestInfo(InterestQueryInfo qInfo) throws Exception
	{
		ArrayList list = new ArrayList();
		InterestSettmentInfo interestSettmentInfo = null;
		StringBuffer sql = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try
		{
			conn = getConnection();
			sql.append(" SELECT * FROM sett_queryInterest ");
			sql.append(" where 1=1 ");
			if(qInfo.getOfficeID()>0)
			{
				sql.append(" AND OfficeID ="+qInfo.getOfficeID());
			}
			if(qInfo.getCurrencyID()>0)
			{
				sql.append(" AND CurrencyID ="+qInfo.getCurrencyID());
			}
			sql.append(" and StatusID <>  " + SETTConstant.TransactionStatus.DELETED +  " ");
			if(qInfo.getClearInterest()!=null)
			{
				sql.append(" AND InterestSettlement= to_date('"+DataFormat.formatDate(qInfo.getClearInterest())+"','yyyy-mm-dd')");
			}
			if(qInfo.getUserID()>0)
			{
				sql.append(" AND( ");		
				sql.append(" InputUserID= " + qInfo.getUserID() + "");
				sql.append(" or InterestFeeSettingDetailID != -1 ");
				sql.append(" ) ");				
			}
			sql.append(" order by TransNo ");
			log.info("sql="+sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				interestSettmentInfo = new InterestSettmentInfo();
				interestSettmentInfo.setAccountNo(rs.getString("AccountNo"));
				interestSettmentInfo.setContractNo(rs.getString("contractNo"));
				interestSettmentInfo.setPayFormNo(rs.getString("payFormNo"));
				interestSettmentInfo.setInterestStartDate(rs.getTimestamp("InterestStartDate"));
				interestSettmentInfo.setInterestEndDate(rs.getTimestamp("InterestEndDate"));
				interestSettmentInfo.setInterestDays(rs.getLong("InterestDays"));
				interestSettmentInfo.setBaseBalance(rs.getDouble("BaseBalance"));
				interestSettmentInfo.setRate(rs.getDouble("Rate"));
				interestSettmentInfo.setInterestType(rs.getLong("InterestType"));
				interestSettmentInfo.setInterest(rs.getDouble("Interest"));
				interestSettmentInfo.setInterestTax(rs.getDouble("InterestTax"));
				interestSettmentInfo.setInputUserID(rs.getLong("InputUserID"));
				interestSettmentInfo.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				interestSettmentInfo.setTransNo(rs.getString("TransNo"));
				list.add(interestSettmentInfo);
				
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return list.size()>0?list:null;
		
	}

}