package com.iss.itreasury.audit.loanaudit.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.audit.loanaudit.dataentity.LoanAuditCondition;
import com.iss.itreasury.audit.loanaudit.dataentity.LoanAuditResultInfo;
import com.iss.itreasury.audit.settlementaudit.dataentity.QueryAuditAccountDetailConditionInfo;
import com.iss.itreasury.audit.loanaudit.dataentity.QueryAuditSubjectDetailConditionInfo;
import com.iss.itreasury.audit.util.AUDITConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class LoanAuditDao extends ITreasuryDAO {
	// SQL语法结构
	private StringBuffer m_sbSelect = null;

	private StringBuffer m_sbFrom = null;

	private StringBuffer m_sbWhere = null;

	private StringBuffer m_sbOrderBy = null;

	private Log4j logger = null;

	public LoanAuditDao() {
		super();
		logger = new Log4j(Constant.ModuleType.AUDIT, this);
	}

	public Collection queryLoanAudit(LoanAuditCondition condition)
			throws IException {
		Collection result = null;
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("select a5.id accountTypeID,a5.saccounttype accountType, \n");
		m_sbSelect.append("a1.amountType, \n");
		m_sbSelect.append("a1.contractID contractID, a1.contractCode contractCode, a1.clientName clientName,a1.accountID accountID,  \n");
//		m_sbSelect.append("nvl(a2.beginBalance,0) beginBalance, \n");
//		m_sbSelect.append("nvl(a4.mb,0) beginBalance, \n");
//		m_sbSelect.append("nvl(a4.mby,0) - nvl(a8.payAmount,0) + nvl(a8.loanAmount,0) beginBalance, \n");
		m_sbSelect.append("case when a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_1
				+" or a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_7+" then \n");
		m_sbSelect.append("nvl(a4.mby,0) + nvl(a8.payAmount,0) - nvl(a8.loanAmount,0) \n");
		m_sbSelect.append("when a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_4
				+" or a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_5+" then \n");
		m_sbSelect.append("nvl(a4.mby,0) - nvl(a8.payAmount,0) + nvl(a8.loanAmount,0) \n"); 
		m_sbSelect.append("else nvl(a4.mby,0) - nvl(a8.payAmount,0) + nvl(a8.loanAmount,0) end beginBalance, \n");
		
		
		
		
//		m_sbSelect.append("case when a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_4
//				+" or a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_5+" then \n");
//		m_sbSelect.append("nvl(a4.mb,0) else nvl(a4.mb,0) end beginBalance, \n");
		
		/**
		 * 由xfma(马现福)解释 2009-1-6
		 * 贷款 本金 13030222 （资产类）期末 = 期初 + 借方发生额 —贷方发生额
         * 贷款 利息收入 60110102022（损益类）期末 = 期初 - 借方发生额 + 贷方发生额
         * 贷款 应计利息 11320303004（资产类）期末 = 期初 + 借方发生额 —贷方发生额
         * 贷款 手续费（损益类）602101 期末 = 期初 - 借方发生额 + 贷方发生额
		 * 贷款 利息税费（负债类）222104 期末 = 期初 - 借方发生额 + 贷方发生额
		 */
		m_sbSelect.append("case when a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_4
				+" or a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_5+" then \n");
//		m_sbSelect.append("nvl(a6.payAmount, 0) else nvl(a3.payAmount, 0) end payAmount, \n");
		m_sbSelect.append("nvl(a3.payAmount, 0) else nvl(a3.payAmount, 0) end payAmount, \n");
		m_sbSelect.append("case when a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_4
				+" or a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_5+" then \n");
//		m_sbSelect.append("nvl(a6.loanAmount, 0) else nvl(a3.loanAmount, 0) end loanAmount, \n");
		m_sbSelect.append("nvl(a3.loanAmount, 0) else nvl(a3.loanAmount, 0) end loanAmount, \n");
		m_sbSelect.append("case when a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_1
				+" or a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_7+" then \n");
//		m_sbSelect.append("nvl(a2.beginBalance, 0) + nvl(a3.payAmount, 0) - nvl(a3.loanAmount, 0) \n");
//		m_sbSelect.append("nvl(a4.mb, 0) + nvl(a3.payAmount, 0) - nvl(a3.loanAmount, 0) \n");
		m_sbSelect.append("nvl(a4.mby,0) + nvl(a8.payAmount,0) - nvl(a8.loanAmount,0) + nvl(a3.payAmount, 0) - nvl(a3.loanAmount, 0) \n");
		m_sbSelect.append("when a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_4
				+" or a1.amountType = "+AUDITConstant.AmountTypeForAudit.AmountType_5+" then \n");
//		m_sbSelect.append("nvl(a2.beginBalance, 0) - nvl(a6.payAmount, 0) + nvl(a6.loanAmount, 0) \n");
//		m_sbSelect.append("nvl(a4.mb, 0) - nvl(a3.payAmount, 0) + nvl(a3.loanAmount, 0) \n");
////		m_sbSelect.append("else nvl(a2.beginBalance, 0) - nvl(a3.payAmount, 0) + nvl(a3.loanAmount, 0) end endBalance, \n");
//		m_sbSelect.append("else nvl(a4.mb, 0) - nvl(a3.payAmount, 0) + nvl(a3.loanAmount, 0) end endBalance, \n");
		
		m_sbSelect.append("nvl(a4.mby,0) - nvl(a8.payAmount,0) + nvl(a8.loanAmount,0) - nvl(a3.payAmount, 0) + nvl(a3.loanAmount, 0) \n"); 
		m_sbSelect.append("else nvl(a4.mby,0) - nvl(a8.payAmount,0) + nvl(a8.loanAmount,0) - nvl(a3.payAmount, 0) + nvl(a3.loanAmount, 0) end endBalance, \n");
		
		m_sbSelect.append("a4.subjectcode, \n");
		m_sbSelect.append("a4.subjectname, \n");
		m_sbSelect.append("nvl(a4.mb,0) subjectbeginBalance, \n");
		m_sbSelect.append("nvl(a4.mc,0) subjectloanBalance,  \n");
		m_sbSelect.append("nvl(a4.md,0) subjectpayBalance, \n");
//		m_sbSelect.append("nvl(a4.mc,0) subjectpayBalance,  \n");
//		m_sbSelect.append("nvl(a4.md,0) subjectloanBalance, \n");
		m_sbSelect.append("nvl(a4.me,0) subjectendBalance, \n");
		
		m_sbSelect.append("nvl(a4.mbd,0) subjectbeginBalanceDebit, \n");
		m_sbSelect.append("nvl(a4.mbc,0) subjectbeginBalanceCredit, \n");
		m_sbSelect.append("nvl(a4.med,0) subjectendBalanceDebit, \n");
		m_sbSelect.append("nvl(a4.mec,0) subjectendBalanceCredit \n");

		m_sbFrom = new StringBuffer();
		m_sbFrom.append("from (select distinct aa.contractID  contractID, \n");
		m_sbFrom.append("bb.scontractcode  contractCode, \n");
		m_sbFrom.append("aa.subjectcode    subjectcode, \n");
		m_sbFrom.append("aa.amounttype     amounttype, \n");
		m_sbFrom.append("cc.id             accountID, \n");
		m_sbFrom.append("cc.naccounttypeid accountTypeID, \n");
		m_sbFrom.append("dd.id             clientID, \n");
		m_sbFrom.append("dd.sName          clientName \n");
		m_sbFrom.append("from (select a.ncontractid  contractID, \n");
		m_sbFrom.append(" a.ssubjectcode subjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_1+" amountType \n");
		m_sbFrom.append("from sett_subaccounttype_loan a \n");
		m_sbFrom.append(" where a.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and a.nofficeid = " + condition.getOfficeID()+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and a.ncurrencyid =  " + condition.getCurrencyID()+ " \n");
		}
		m_sbFrom.append("union all \n");
		m_sbFrom.append("select a.ncontractid      contractID, \n");
		m_sbFrom.append("a.sinterestsubject subjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_6+"  amountType \n");
		m_sbFrom.append("from sett_subaccounttype_loan a \n");
		m_sbFrom.append("where a.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and a.nofficeid = " + condition.getOfficeID()+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and a.ncurrencyid =  " + condition.getCurrencyID()+ " \n");
		}
		m_sbFrom.append("union all \n");
		m_sbFrom.append("select a.ncontractid      contractID, \n");
		m_sbFrom.append("a.Sbookedinterestsubject subjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_7+"  amountType \n");
		m_sbFrom.append("from sett_subaccounttype_loan a \n");
		m_sbFrom.append("where a.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and a.nofficeid = " + condition.getOfficeID()+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and a.ncurrencyid =  " + condition.getCurrencyID()+ " \n");
		}
		m_sbFrom.append("union all \n");
		
		/*
		m_sbFrom.append("select a.ncontractid      contractID, \n");
		m_sbFrom.append("a.scommissionsubject subjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_4+"  amountType \n");
		m_sbFrom.append("from sett_subaccounttype_loan a \n");
		m_sbFrom.append("where a.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and a.nofficeid = " + condition.getOfficeID()+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and a.ncurrencyid =  " + condition.getCurrencyID()+ " \n");
		}
		*/
		
		m_sbFrom.append("select 22 contractID, \n"); 
		m_sbFrom.append("'602101' subjectCode, \n"); 
		m_sbFrom.append("4  amountType \n"); 
		m_sbFrom.append("from dual \n");
		
		m_sbFrom.append("union all \n");
//		m_sbFrom.append("select a.ncontractid      contractID, \n");
//		m_sbFrom.append("a.sinteresttaxsubject subjectCode, \n");
//		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_5+"  amountType \n");
//		m_sbFrom.append("from sett_subaccounttype_loan a \n");
		
		
		
//		m_sbFrom.append("where a.nstatusid > 0 \n");
//		if (condition.getOfficeID() > 0) {
//			m_sbFrom.append(" and a.nofficeid = " + condition.getOfficeID()+ "\n");
//		}
//		if (condition.getCurrencyID() > 0) {
//			m_sbFrom.append(" and a.ncurrencyid =  " + condition.getCurrencyID()+ " \n");
//		}
	
		m_sbFrom.append("select 22 contractID, \n"); 
		m_sbFrom.append("'222104' subjectCode, \n"); 
		m_sbFrom.append("5  amountType \n"); 
		m_sbFrom.append("from dual a \n");
		
		m_sbFrom.append(") aa, \n");
		m_sbFrom.append("loan_contractform bb, \n");
		m_sbFrom.append("loan_payform lp , \n");
		m_sbFrom.append("sett_subaccount sb, \n");
		m_sbFrom.append("sett_account cc, \n");
		m_sbFrom.append("client dd \n");
		m_sbFrom.append("where aa.contractid = bb.id \n");
		m_sbFrom.append("and lp.ncontractid = bb.id \n");
		m_sbFrom.append("and sb.al_nloannoteid = lp.id \n");
		m_sbFrom.append("and sb.naccountid = cc.id \n");
		m_sbFrom.append("and dd.id = cc.nclientid \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and lp.nofficeid = " + condition.getOfficeID()+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and lp.ncurrencyid =  " + condition.getCurrencyID()+ " \n");
		}
		m_sbFrom.append("and bb.nstatusid > 0 \n");
		m_sbFrom.append("and cc.nstatusid > 0 \n");

		if (!"".equals(condition.getAccountTypeSet())) {
			m_sbFrom.append("      and  cc.naccounttypeid  IN("+ condition.getAccountTypeSet() + ") \n");
		} else {
			long[] arrayReturn = SETTConstant.AccountType.getLoanAccountTypeCodeForAudit(condition.getCurrencyID(),condition.getOfficeID());
			String strAccountTypeID = "";
			for (int i = 0; i < arrayReturn.length; i++) {
				strAccountTypeID = strAccountTypeID + arrayReturn[i] + "";
				if (arrayReturn[i] > 0) {
					strAccountTypeID = strAccountTypeID + ",";
				}
			}
			strAccountTypeID = strAccountTypeID.substring(0, strAccountTypeID.length() - 1);
			m_sbFrom.append("        and cc.naccounttypeid  IN("+ strAccountTypeID + ") \n");
		}
		if (condition.getStartClientCode() != null&& condition.getStartClientCode().length() > 0) {
			m_sbFrom.append("        and dd.scode>='"+ condition.getStartClientCode() + "' \n");
		}
		if (condition.getEndClientCode() != null&& condition.getEndClientCode().length() > 0) {
			m_sbFrom.append("        and dd.scode<='"+ condition.getEndClientCode() + "' \n");
		}
		if (condition.getStartContractCode() != null&& condition.getStartContractCode().length() > 0) {
			m_sbFrom.append("        and bb.SCONTRACTCODE>='"+ condition.getStartContractCode() + "' \n");
		}
		if (condition.getEndContractCode() != null&& condition.getEndContractCode().length() > 0) {
			m_sbFrom.append("        and bb.SCONTRACTCODE<='"+ condition.getEndContractCode() + "' \n");
		}
		m_sbFrom.append("order by aa.contractid, aa.amounttype) a1, \n");
		m_sbFrom.append("(select t1.naccountid accountID, \n");
		m_sbFrom.append("b1.ncontractid ncontractID, \n");
		m_sbFrom.append("sum(nvl(t1.minterestbalance, 0)) beginBalance, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_1+" amountType \n");
		m_sbFrom.append("from sett_dailyaccountbalance t1,sett_subaccount a1,loan_payform b1 where t1.nsubaccountid = a1.id and a1.al_nloannoteid = b1.id \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and to_char(t1.dtdate, 'yyyy-mm-dd') = '"+ DataFormat.getPreviousOrNextDate(condition.getSelectDateFrom(), -1) + "' \n");
		}	
		m_sbFrom.append("group by t1.naccountid,b1.ncontractid \n");
		m_sbFrom.append(" union all \n");
		m_sbFrom.append("select t2.naccountid accountID,b2.ncontractid ncontractID, \n");
		m_sbFrom.append("sum(t2.minterest + t2.ac_mnegotiateinterest) beginBalance, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_6+" amountType \n");
		m_sbFrom.append("from sett_dailyaccountbalance t2,sett_subaccount a2,loan_payform b2 where t2.nsubaccountid = a2.id and a2.al_nloannoteid = b2.id \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and to_char(t2.dtdate, 'yyyy-mm-dd') = '"+ DataFormat.getPreviousOrNextDate(condition.getSelectDateFrom(), -1) + "' \n");
		}	
		m_sbFrom.append("group by t2.naccountid,b2.ncontractid \n");
		m_sbFrom.append(" union all \n");
		m_sbFrom.append(" select t3.naccountid accountID,b3.ncontractid ncontractID, \n");
		m_sbFrom.append(" sum(nvl(t3.minterest,0) + nvl(t3.mnegotiateinterest,0)) beginBalance, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_7+ " amountType \n");
		m_sbFrom.append(" from sett_transinterestsettlement t3, sett_subaccount t4,loan_payform b3 \n");
		m_sbFrom.append(" where t3.nsubaccountid = t4.id and t4.al_nloannoteid = b3.id \n");
		m_sbFrom.append(" and t3.nstatusid = "+ SETTConstant.TransactionStatus.CHECK + " \n");
		m_sbFrom.append(" and t4.nstatusid > 0 \n");
		m_sbFrom.append(" and t3.ninteresttype = "+ SETTConstant.InterestFeeType.PREDRAWINTEREST + " \n");
		m_sbFrom.append(" and t4.dtclearinterest <= t3.DTINTERESTSETTLEMENT \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and to_char(t3.DTINTERESTSETTLEMENT, 'yyyy-mm-dd') <='"+ DataFormat.getDateString(condition.getSelectDateFrom()) + "' \n");
		}
		m_sbFrom.append(" group by t3.naccountid,b3.ncontractid) a2, \n");
		
		m_sbFrom.append("  ( \n");
		
		m_sbFrom.append("select subjectCode,sum(nvl(payAmount,0)) payAmount,sum(nvl(loanAmount,0)) loanAmount from ( \n");
				
		m_sbFrom.append("select case \n");
		
//		m_sbFrom.append("  (select case \n");
		m_sbFrom.append(" when b2.transDirection = "+ SETTConstant.DebitOrCredit.DEBIT + " then \n");
		m_sbFrom.append(" b2.subjectCode \n");
		m_sbFrom.append(" else \n");
		m_sbFrom.append(" b3.subjectCode \n");
		m_sbFrom.append(" end subjectCode, \n");
		m_sbFrom.append(" payAmount, \n");
		m_sbFrom.append(" loanAmount \n");
		m_sbFrom.append(" from (select b1.ssubjectcode subjectCode, \n");
		m_sbFrom.append(" b1.ntransdirection transDirection, \n");
		m_sbFrom.append(" sum(b1.mamount) payAmount \n");
		m_sbFrom.append(" from sett_glentry b1 \n");
		m_sbFrom.append(" where b1.nstatusid = "+ SETTConstant.TransactionStatus.CHECK + " \n");
		m_sbFrom.append(" and b1.ntransdirection = "+ SETTConstant.DebitOrCredit.DEBIT + " \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and to_char(b1.dtexecute, 'yyyy-mm-dd') >='"+ DataFormat.getDateString(condition.getSelectDateFrom()) + "' \n");
		}
		if (condition.getSelectDateTo() != null) {
			m_sbFrom.append(" and to_char(b1.dtexecute, 'yyyy-mm-dd') <='"+ DataFormat.getDateString(condition.getSelectDateTo()) + "' \n");
		}
		m_sbFrom.append(" group by b1.ssubjectcode, b1.ntransdirection) b2 \n");
		m_sbFrom.append(" full outer join (select b1.ssubjectcode subjectCode, \n");
		m_sbFrom.append(" b1.ntransdirection transDirection, \n");
		m_sbFrom.append(" sum(b1.mamount) loanAmount \n");
		m_sbFrom.append(" from sett_glentry b1 \n");
		m_sbFrom.append(" where b1.nstatusid = "+ SETTConstant.TransactionStatus.CHECK + " \n");
		m_sbFrom.append(" and b1.ntransdirection = "+ SETTConstant.DebitOrCredit.CREDIT + " \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and to_char(b1.dtexecute, 'yyyy-mm-dd') >='"+ DataFormat.getDateString(condition.getSelectDateFrom()) + "' \n");
		}
		if (condition.getSelectDateTo() != null) {
			m_sbFrom.append(" and to_char(b1.dtexecute, 'yyyy-mm-dd') <='"+ DataFormat.getDateString(condition.getSelectDateTo()) + "' \n");
		}
		m_sbFrom.append("  group by b1.ssubjectcode, b1.ntransdirection) b3 on b2.subjectCode= b3.subjectCode \n");
		
		
		
		
		
		
		


		m_sbFrom
		.append("  union all \n");				
						
				

						m_sbFrom.append("select abc.subjectcode subjectCode, \n");
				m_sbFrom.append("abc.md payAmount,abc.mc loanAmount \n");
				
				m_sbFrom.append("from ( \n");
				m_sbFrom.append("select * from ( \n");
				
				m_sbFrom.append(" select g.ssegmentcode2 subjectcode, \n");
				m_sbFrom.append(" g.ssegmentname2 subjectname,g.nbalancedirection, \n");
				m_sbFrom.append(" nvl(i.beginbalance, 0) + nvl(j.amount, 0) mb, \n");
				m_sbFrom.append(" nvl(h.mc,0) mc, \n");
				m_sbFrom.append(" nvl(h.md,0) md, \n");
				
//				m_sbFrom.append(" nvl(a.beginbalance, 0) + nvl(b.amount, 0) me \n");
				
				
				
				m_sbFrom.append("case g.nbalancedirection \n"); 
				m_sbFrom.append("when 1 then \n"); 
				m_sbFrom.append("nvl(i.beginbalance, 0) + nvl(j.amount, 0) + nvl(h.md,0) - nvl(h.mc,0) \n"); 
				m_sbFrom.append("when 2 then \n"); 
				m_sbFrom.append("nvl(i.beginbalance, 0) + nvl(j.amount, 0) + nvl(h.mc,0) - nvl(h.md,0) \n");  
				m_sbFrom.append("end me \n");
				
				
				
				m_sbFrom
						.append(" from (select nvl(sum(nvl(t.beginbalance, 0)), 0) beginbalance, \n");
				m_sbFrom.append("  t.ccode \n");
				m_sbFrom.append(" from uf_gl_accsum_cny t \n");
				m_sbFrom.append(" where 1=1 \n");
				if (condition.getSelectDateFrom() != null) {
					m_sbFrom.append(" and t.iyear =to_number(to_char(to_date('"
							+ DataFormat.getDateString(condition.getSelectDateFrom())
							+ "', 'yyyy-mm-dd') - 1,'yyyy')) \n");
					m_sbFrom.append(" and t.iperiod = 1 \n");
//					m_sbFrom.append(" and t.iperiod =to_number(to_char(to_date('"
//							+ DataFormat.getDateString(condition.getSelectDateFrom())
//							+ "', 'yyyy-mm-dd') - 1,'mm')) \n");
				}
				m_sbFrom.append(" group by t.ccode) i, \n");
				m_sbFrom.append(" (select nvl(sum(case e.nbalancedirection \n");
				m_sbFrom.append(" when 1 then \n");
				m_sbFrom.append("  nvl(c.md, 0) - nvl(c.mc, 0) \n");
				m_sbFrom.append(" when 2 then \n");
				m_sbFrom.append(" nvl(c.mc, 0) - nvl(c.md, 0) \n");
				m_sbFrom.append(" end), \n");
				m_sbFrom.append(" 0) amount, \n");
				m_sbFrom.append(" e.ssegmentcode2 \n");
				m_sbFrom
						.append(" from uf_gl_accvouch_cny c, sett_glsubjectdefinition e \n");
				m_sbFrom.append(" where c.ccode = e.ssegmentcode2 \n");
				if (condition.getOfficeID() > 0) {
					m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
							+ "\n");
				}
				if (condition.getCurrencyID() > 0) {
					m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
							+ " \n");
				}
				m_sbFrom.append(" and e.nstatus = 1 \n");
//				m_sbFrom.append(" and e.nisleaf = 1 \n");
				m_sbFrom.append(" and e.nisleaf = 1 and c.iperiod != 0 \n");
				
				m_sbFrom.append(" and c.ccode_equal = '4103' \n");
				
				if (condition.getSelectDateFrom() != null) {
					m_sbFrom.append(" and c.iyear =to_number(to_char(to_date('"
							+ DataFormat.getDateString(condition.getSelectDateFrom())
							+ "', 'yyyy-mm-dd') - 1,'yyyy')) \n");
//					m_sbFrom.append(" and c.iperiod =to_number(to_char(to_date('"
//							+ DataFormat.getDateString(condition.getSelectDateFrom())
//							+ "', 'yyyy-mm-dd') - 1,'mm')) \n");
					m_sbFrom.append(" and c.dbill_date <to_date('"
							+ DataFormat.getDateString(condition.getSelectDateFrom())
							+ "', 'yyyy-mm-dd') - 1 + 1 \n");
				}
				m_sbFrom.append(" group by e.ssegmentcode2) j, \n");
				m_sbFrom
						.append(" (select nvl(sum(nvl(t.beginbalance, 0)), 0) beginbalance, \n");
				m_sbFrom.append(" t.ccode \n");
				m_sbFrom.append(" from uf_gl_accsum_cny t \n");
				m_sbFrom.append(" where 1=1 \n");
				if (condition.getSelectDateTo() != null) {
					m_sbFrom.append(" and t.iyear =to_number(to_char(to_date('"
							+ DataFormat.getDateString(condition.getSelectDateTo())
							+ "', 'yyyy-mm-dd') ,'yyyy')) \n");
					m_sbFrom.append(" and t.iperiod = 1 \n");
//					m_sbFrom.append(" and t.iperiod =to_number(to_char(to_date('"
//							+ DataFormat.getDateString(condition.getSelectDateTo())
//							+ "', 'yyyy-mm-dd'),'mm')) \n");
				}
				m_sbFrom.append(" group by t.ccode) a, \n");
				m_sbFrom.append(" (select nvl(sum(case e.nbalancedirection \n");
				m_sbFrom.append(" when 1 then \n");
				m_sbFrom.append(" nvl(c.md, 0) - nvl(c.mc, 0) \n");
				m_sbFrom.append(" when 2 then \n");
				m_sbFrom.append(" nvl(c.mc, 0) - nvl(c.md, 0) \n");
				m_sbFrom.append(" end), \n");
				m_sbFrom.append(" 0) amount, \n");
				m_sbFrom.append(" e.ssegmentcode2 \n");
				m_sbFrom
						.append(" from uf_gl_accvouch_cny c, sett_glsubjectdefinition e \n");
				m_sbFrom.append(" where c.ccode = e.ssegmentcode2 \n");
				if (condition.getOfficeID() > 0) {
					m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
							+ "\n");
				}
				if (condition.getCurrencyID() > 0) {
					m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
							+ " \n");
				}
				m_sbFrom.append(" and e.nstatus = 1 \n");
//				m_sbFrom.append(" and e.nisleaf = 1 \n");
				m_sbFrom.append(" and e.nisleaf = 1 and c.iperiod != 0 \n");
				
				m_sbFrom.append(" and c.ccode_equal = '4103' \n");
				
				if (condition.getSelectDateTo() != null) {
					m_sbFrom.append(" and c.iyear =to_number(to_char(to_date('"
							+ DataFormat.getDateString(condition.getSelectDateTo())
							+ "', 'yyyy-mm-dd') ,'yyyy')) \n");
//					m_sbFrom.append(" and c.iperiod =to_number(to_char(to_date('"
//							+ DataFormat.getDateString(condition.getSelectDateTo())
//							+ "', 'yyyy-mm-dd') ,'mm')) \n");
					m_sbFrom.append(" and c.dbill_date <to_date('"
							+ DataFormat.getDateString(condition.getSelectDateTo())
							+ "', 'yyyy-mm-dd') + 1 \n");
				}
				m_sbFrom.append(" group by e.ssegmentcode2) b, \n");
				m_sbFrom.append(" (select nvl(sum(nvl(c.mc, 0)), 0) mc, \n");
				m_sbFrom.append(" nvl(sum(nvl(c.md, 0)), 0) md, \n");
				m_sbFrom.append(" c.ccode \n");
				m_sbFrom.append(" from uf_gl_accvouch_cny c \n");
//				m_sbFrom.append(" where 1=1  \n");
				m_sbFrom.append(" where c.iperiod !=0  \n");
				
				m_sbFrom.append(" and c.ccode_equal = '4103' \n");
				
				if (condition.getSelectDateFrom() != null) {
					m_sbFrom.append(" and c.dbill_date >= to_date('"
							+ DataFormat.getDateString(condition.getSelectDateFrom())
							+ "', 'yyyy-mm-dd') \n");
				}
				if (condition.getSelectDateTo() != null) {
					m_sbFrom.append(" and c.dbill_date < to_date('"
							+ DataFormat.getDateString(condition.getSelectDateTo())
							+ "', 'yyyy-mm-dd') + 1 \n");
				}
				m_sbFrom.append(" group by c.ccode) h, \n");
				m_sbFrom.append(" (select f.ssegmentname2, f.ssegmentcode2,f.nbalancedirection \n");
				m_sbFrom.append(" from sett_glsubjectdefinition f \n");
				m_sbFrom.append(" where 1=1 \n");
				if (condition.getOfficeID() > 0) {
					m_sbFrom.append(" and f.nofficeid = " + condition.getOfficeID()
							+ "\n");
				}
				if (condition.getCurrencyID() > 0) {
					m_sbFrom.append(" and f.ncurrencyid = " + condition.getCurrencyID()
							+ " \n");
				}
				m_sbFrom.append(" and f.nstatus = 1 \n");
				m_sbFrom.append(" and f.nisleaf = 1) g \n");
				m_sbFrom.append(" where i.ccode(+) = g.ssegmentcode2 \n");
				m_sbFrom.append(" and j.ssegmentcode2(+) = g.ssegmentcode2 \n");
				m_sbFrom.append(" and a.ccode(+) = g.ssegmentcode2 \n");
				m_sbFrom.append(" and b.ssegmentcode2(+) = g.ssegmentcode2 \n");
				m_sbFrom.append(" and h.ccode(+) = g.ssegmentcode2 ) abcd ) abc \n");		
						
						
						
				m_sbFrom.append(") group by subjectCode \n");		
						
						
		
		
		
		

		
		
		
				m_sbFrom.append(") a3,  \n");
		
		
		
		
		
		
		
//		m_sbFrom.append(" (select g.ssegmentcode2 subjectcode, \n");
//		m_sbFrom.append(" g.ssegmentname2 subjectname, \n");
//		m_sbFrom.append(" nvl(i.beginbalance, 0) + nvl(j.amount, 0) mb, \n");
//		m_sbFrom.append(" h.mc, \n");
//		m_sbFrom.append(" h.md, \n");
//		m_sbFrom.append(" nvl(a.beginbalance, 0) + nvl(b.amount, 0) me \n");
//		m_sbFrom.append(" from (select nvl(sum(nvl(t.beginbalance, 0)), 0) beginbalance, \n");
//		m_sbFrom.append("  t.ccode \n");
//		m_sbFrom.append(" from uf_gl_accsum_cny t \n");
//		m_sbFrom.append(" where 1=1 \n");
//		if (condition.getSelectDateFrom() != null) {
//			m_sbFrom.append(" and t.iyear =to_number(to_char(to_date('"+ DataFormat.getDateString(condition.getSelectDateFrom())+ "', 'yyyy-mm-dd') - 1,'yyyy')) \n");
//			m_sbFrom.append(" and t.iperiod = 1 \n");
////			m_sbFrom.append(" and t.iperiod =to_number(to_char(to_date('"+ DataFormat.getDateString(condition.getSelectDateFrom())+ "', 'yyyy-mm-dd') - 1,'mm')) \n");
//		}
//		m_sbFrom.append(" group by t.ccode) i, \n");
//		m_sbFrom.append(" (select nvl(sum(case e.nbalancedirection \n");
//		m_sbFrom.append(" when 1 then \n");
//		m_sbFrom.append("  nvl(c.md, 0) - nvl(c.mc, 0) \n");
//		m_sbFrom.append(" when 2 then \n");
//		m_sbFrom.append(" nvl(c.mc, 0) - nvl(c.md, 0) \n");
//		m_sbFrom.append(" end), \n");
//		m_sbFrom.append(" 0) amount, \n");
//		m_sbFrom.append(" e.ssegmentcode2 \n");
//		m_sbFrom.append(" from uf_gl_accvouch_cny c, sett_glsubjectdefinition e \n");
//		m_sbFrom.append(" where c.ccode = e.ssegmentcode2 \n");
//		if (condition.getOfficeID() > 0) {
//			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()+ "\n");
//		}
//		if (condition.getCurrencyID() > 0) {
//			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()+ " \n");
//		}
//		m_sbFrom.append(" and e.nstatus = 1 \n");
//		m_sbFrom.append(" and e.nisleaf = 1 \n");
//		if (condition.getSelectDateFrom() != null) {
//			m_sbFrom.append(" and c.iyear =to_number(to_char(to_date('"+ DataFormat.getDateString(condition.getSelectDateFrom())+ "', 'yyyy-mm-dd') - 1,'yyyy')) \n");
////			m_sbFrom.append(" and c.iperiod =to_number(to_char(to_date('"+ DataFormat.getDateString(condition.getSelectDateFrom())+ "', 'yyyy-mm-dd') - 1,'mm')) \n");
//			m_sbFrom.append(" and c.dbill_date <to_date('"+ DataFormat.getDateString(condition.getSelectDateFrom())+ "', 'yyyy-mm-dd') - 1 + 1 \n");
//		}
//		m_sbFrom.append(" group by e.ssegmentcode2) j, \n");
//		m_sbFrom.append(" (select nvl(sum(nvl(t.beginbalance, 0)), 0) beginbalance, \n");
//		m_sbFrom.append(" t.ccode \n");
//		m_sbFrom.append(" from uf_gl_accsum_cny t \n");
//		m_sbFrom.append(" where 1=1 \n");
//		if (condition.getSelectDateTo() != null) {
//			m_sbFrom.append(" and t.iyear =to_number(to_char(to_date('"+ DataFormat.getDateString(condition.getSelectDateTo())+ "', 'yyyy-mm-dd') ,'yyyy')) \n");
//			m_sbFrom.append(" and t.iperiod = 1 \n");
////			m_sbFrom.append(" and t.iperiod =to_number(to_char(to_date('"+ DataFormat.getDateString(condition.getSelectDateTo())+ "', 'yyyy-mm-dd'),'mm')) \n");
//		}
//		m_sbFrom.append(" group by t.ccode) a, \n");
//		m_sbFrom.append(" (select nvl(sum(case e.nbalancedirection \n");
//		m_sbFrom.append(" when 1 then \n");
//		m_sbFrom.append(" nvl(c.md, 0) - nvl(c.mc, 0) \n");
//		m_sbFrom.append(" when 2 then \n");
//		m_sbFrom.append(" nvl(c.mc, 0) - nvl(c.md, 0) \n");
//		m_sbFrom.append(" end), \n");
//		m_sbFrom.append(" 0) amount, \n");
//		m_sbFrom.append(" e.ssegmentcode2 \n");
//		m_sbFrom.append(" from uf_gl_accvouch_cny c, sett_glsubjectdefinition e \n");
//		m_sbFrom.append(" where c.ccode = e.ssegmentcode2 \n");
//		if (condition.getOfficeID() > 0) {
//			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()+ "\n");
//		}
//		if (condition.getCurrencyID() > 0) {
//			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()+ " \n");
//		}
//		m_sbFrom.append(" and e.nstatus = 1 \n");
//		m_sbFrom.append(" and e.nisleaf = 1 \n");
//		if (condition.getSelectDateTo() != null) {
//			m_sbFrom.append(" and c.iyear =to_number(to_char(to_date('"+ DataFormat.getDateString(condition.getSelectDateTo())+ "', 'yyyy-mm-dd') ,'yyyy')) \n");
////			m_sbFrom.append(" and c.iperiod =to_number(to_char(to_date('"+ DataFormat.getDateString(condition.getSelectDateTo())+ "', 'yyyy-mm-dd') ,'mm')) \n");
//			m_sbFrom.append(" and c.dbill_date <to_date('"+ DataFormat.getDateString(condition.getSelectDateTo())+ "', 'yyyy-mm-dd') + 1 \n");
//		}
//		m_sbFrom.append(" group by e.ssegmentcode2) b, \n");
//		m_sbFrom.append(" (select nvl(sum(nvl(c.mc, 0)), 0) mc, \n");
//		m_sbFrom.append(" nvl(sum(nvl(c.md, 0)), 0) md, \n");
//		m_sbFrom.append(" c.ccode \n");
//		m_sbFrom.append(" from uf_gl_accvouch_cny c \n");
//		m_sbFrom.append(" where 1=1  \n");
//		if (condition.getSelectDateFrom() != null) {
//			m_sbFrom.append(" and c.dbill_date >= to_date('"+ DataFormat.getDateString(condition.getSelectDateFrom())+ "', 'yyyy-mm-dd') \n");
//		}
//		if (condition.getSelectDateTo() != null) {
//			m_sbFrom.append(" and c.dbill_date < to_date('"+ DataFormat.getDateString(condition.getSelectDateTo())+ "', 'yyyy-mm-dd') + 1 \n");
//		}
//		m_sbFrom.append(" group by c.ccode) h, \n");
//		m_sbFrom.append(" (select f.ssegmentname2, f.ssegmentcode2 \n");
//		m_sbFrom.append(" from sett_glsubjectdefinition f \n");
//		m_sbFrom.append(" where 1=1 \n");
//		if (condition.getOfficeID() > 0) {
//			m_sbFrom.append(" and f.nofficeid = " + condition.getOfficeID()+ "\n");
//		}
//		if (condition.getCurrencyID() > 0) {
//			m_sbFrom.append(" and f.ncurrencyid = " + condition.getCurrencyID()+ " \n");
//		}
//		m_sbFrom.append(" and f.nstatus = 1 \n");
//		m_sbFrom.append(" and f.nisleaf = 1) g \n");
//		m_sbFrom.append(" where i.ccode(+) = g.ssegmentcode2 \n");
//		m_sbFrom.append(" and j.ssegmentcode2(+) = g.ssegmentcode2 \n");
//		m_sbFrom.append(" and a.ccode(+) = g.ssegmentcode2 \n");
//		m_sbFrom.append(" and b.ssegmentcode2(+) = g.ssegmentcode2 \n");
//		m_sbFrom.append(" and h.ccode(+) = g.ssegmentcode2) a4, \n");
		
		
		
		
		
		
		
		m_sbFrom.append("( select abc.subjectcode,abc.subjectname, \n");
		m_sbFrom.append("abc.nbalancedirection, \n");
		m_sbFrom.append("case abc.nbalancedirection when 1 then  \n");
		m_sbFrom.append("case when abc.mb >= 0 then abc.mb else 0 end \n");
		m_sbFrom.append("when 2 then  \n");
		m_sbFrom.append("case when abc.mb < 0 then -abc.mb else 0 end  \n");
		m_sbFrom.append("end mbd, \n");
		m_sbFrom.append("case abc.nbalancedirection when 1 then  \n");
		m_sbFrom.append("case when abc.mb < 0 then -abc.mb else 0 end \n");
		m_sbFrom.append("when 2 then  \n");
		m_sbFrom.append("case when abc.mb >= 0 then abc.mb else 0 end  \n");
		m_sbFrom.append("end mbc, \n");
		m_sbFrom.append("abc.md,abc.mc, \n");
		m_sbFrom.append("case abc.nbalancedirection when 1 then  \n");
		m_sbFrom.append("case when abc.me >= 0 then abc.me else 0 end \n");
		m_sbFrom.append("when 2 then  \n");
		m_sbFrom.append("case when abc.me < 0 then -abc.me else 0 end  \n");
		m_sbFrom.append("end med, \n");
		m_sbFrom.append("case abc.nbalancedirection when 1 then  \n");
		m_sbFrom.append("case when abc.me < 0 then -abc.me else 0 end \n");
		m_sbFrom.append("when 2 then  \n");
		m_sbFrom.append("case when abc.me >= 0 then abc.me else 0 end  \n");
		m_sbFrom.append("end mec, \n");
		m_sbFrom.append("abc.mb, \n");
		m_sbFrom.append("abc.me, \n");
		m_sbFrom.append("abc.mby \n");
		m_sbFrom.append("from ( \n");
		m_sbFrom.append("select * from ( \n");
		
		m_sbFrom.append(" select g.ssegmentcode2 subjectcode, \n");
		m_sbFrom.append(" g.ssegmentname2 subjectname,g.nbalancedirection, \n");
//		m_sbFrom.append(" nvl(i.beginbalance, 0) mby, \n");
		m_sbFrom.append(" nvl(a.beginbalance, 0) + nvl(b.amount, 0) mby, \n");
		m_sbFrom.append(" nvl(i.beginbalance, 0) + nvl(j.amount, 0) mb, \n");
		m_sbFrom.append(" nvl(h.mc,0) mc, \n");
		m_sbFrom.append(" nvl(h.md,0) md, \n");
		
//		m_sbFrom.append(" nvl(a.beginbalance, 0) + nvl(b.amount, 0) me \n");
		
		
		
		m_sbFrom.append("case g.nbalancedirection \n"); 
		m_sbFrom.append("when 1 then \n"); 
		m_sbFrom.append("nvl(i.beginbalance, 0) + nvl(j.amount, 0) + nvl(h.md,0) - nvl(h.mc,0) \n"); 
		m_sbFrom.append("when 2 then \n"); 
		m_sbFrom.append("nvl(i.beginbalance, 0) + nvl(j.amount, 0) + nvl(h.mc,0) - nvl(h.md,0) \n");  
		m_sbFrom.append("end me \n");
		
		
		
		m_sbFrom
				.append(" from (select nvl(sum(nvl(t.beginbalance, 0)), 0) beginbalance, \n");
		m_sbFrom.append("  t.ccode \n");
		m_sbFrom.append(" from uf_gl_accsum_cny t \n");
		m_sbFrom.append(" where 1=1 \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and t.iyear =to_number(to_char(to_date('"
					+ DataFormat.getDateString(condition.getSelectDateFrom())
//					+ "', 'yyyy-mm-dd') - 1,'yyyy')) \n");
			        + "', 'yyyy-mm-dd') ,'yyyy')) \n");
			m_sbFrom.append(" and t.iperiod = 1 \n");
//			m_sbFrom.append(" and t.iperiod =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateFrom())
//					+ "', 'yyyy-mm-dd') - 1,'mm')) \n");
		}
		m_sbFrom.append(" group by t.ccode) i, \n");
		m_sbFrom.append(" (select nvl(sum(case e.nbalancedirection \n");
		m_sbFrom.append(" when 1 then \n");
		m_sbFrom.append("  nvl(c.md, 0) - nvl(c.mc, 0) \n");
		m_sbFrom.append(" when 2 then \n");
		m_sbFrom.append(" nvl(c.mc, 0) - nvl(c.md, 0) \n");
		m_sbFrom.append(" end), \n");
		m_sbFrom.append(" 0) amount, \n");
		m_sbFrom.append(" e.ssegmentcode2 \n");
		m_sbFrom
				.append(" from uf_gl_accvouch_cny c, sett_glsubjectdefinition e \n");
		m_sbFrom.append(" where c.ccode = e.ssegmentcode2 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" and e.nstatus = 1 \n");
//		m_sbFrom.append(" and e.nisleaf = 1 \n");
		m_sbFrom.append(" and e.nisleaf = 1 and c.iperiod != 0 \n");
		
//		m_sbFrom.append(" and c.ccode_equal != '4103' \n");
		
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and c.iyear =to_number(to_char(to_date('"
					+ DataFormat.getDateString(condition.getSelectDateFrom())
//					+ "', 'yyyy-mm-dd') - 1,'yyyy')) \n");
			        + "', 'yyyy-mm-dd') ,'yyyy')) \n");
//			m_sbFrom.append(" and c.iperiod =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateFrom())
//					+ "', 'yyyy-mm-dd') - 1,'mm')) \n");
			m_sbFrom.append(" and c.dbill_date <to_date('"
					+ DataFormat.getDateString(condition.getSelectDateFrom())
					+ "', 'yyyy-mm-dd') - 1 + 1 \n");
		}
		m_sbFrom.append(" group by e.ssegmentcode2) j, \n");
		m_sbFrom
				.append(" (select nvl(sum(nvl(t.beginbalance, 0)), 0) beginbalance, \n");
		m_sbFrom.append(" t.ccode \n");
		m_sbFrom.append(" from uf_gl_accsum_cny t \n");
		m_sbFrom.append(" where 1=1 \n");
		if (condition.getSelectDateTo() != null) {
//			m_sbFrom.append(" and t.iyear =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateTo())
//					+ "', 'yyyy-mm-dd') ,'yyyy')) \n");
			m_sbFrom.append(" and t.iyear = 2008 \n");
			m_sbFrom.append(" and t.iperiod = 1 \n");
//			m_sbFrom.append(" and t.iperiod =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateTo())
//					+ "', 'yyyy-mm-dd'),'mm')) \n");
		}
		m_sbFrom.append(" group by t.ccode) a, \n");
		m_sbFrom.append(" (select nvl(sum(case e.nbalancedirection \n");
		m_sbFrom.append(" when 1 then \n");
		m_sbFrom.append(" nvl(c.md, 0) - nvl(c.mc, 0) \n");
		m_sbFrom.append(" when 2 then \n");
		m_sbFrom.append(" nvl(c.mc, 0) - nvl(c.md, 0) \n");
		m_sbFrom.append(" end), \n");
		m_sbFrom.append(" 0) amount, \n");
		m_sbFrom.append(" e.ssegmentcode2 \n");
		m_sbFrom
				.append(" from uf_gl_accvouch_cny c, sett_glsubjectdefinition e \n");
		m_sbFrom.append(" where c.ccode = e.ssegmentcode2 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" and e.nstatus = 1 \n");
//		m_sbFrom.append(" and e.nisleaf = 1 \n");
		m_sbFrom.append(" and e.nisleaf = 1 and c.iperiod != 0 \n");
		
//		m_sbFrom.append(" and c.ccode_equal != '4103' \n");

		if (condition.getSelectDateTo() != null) {
//			m_sbFrom.append(" and c.iyear =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateTo())
//					+ "', 'yyyy-mm-dd') ,'yyyy')) \n");
////			m_sbFrom.append(" and c.iperiod =to_number(to_char(to_date('"
////					+ DataFormat.getDateString(condition.getSelectDateTo())
////					+ "', 'yyyy-mm-dd') ,'mm')) \n");
//			m_sbFrom.append(" and c.dbill_date <to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateTo())
//					+ "', 'yyyy-mm-dd') + 1 \n");
			m_sbFrom.append(" and c.iyear = 2008 and c.dbill_date <to_date('2008-12-01', 'yyyy-mm-dd') \n");
		}
		m_sbFrom.append(" group by e.ssegmentcode2) b, \n");
		m_sbFrom.append(" (select nvl(sum(nvl(c.mc, 0)), 0) mc, \n");
		m_sbFrom.append(" nvl(sum(nvl(c.md, 0)), 0) md, \n");
		m_sbFrom.append(" c.ccode \n");
		m_sbFrom.append(" from uf_gl_accvouch_cny c \n");
//		m_sbFrom.append(" where 1=1  \n");
		m_sbFrom.append(" where c.iperiod !=0  \n");
		
//		m_sbFrom.append(" and c.ccode_equal != '4103' \n");

		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and c.dbill_date >= to_date('"
					+ DataFormat.getDateString(condition.getSelectDateFrom())
					+ "', 'yyyy-mm-dd') \n");
		}
		if (condition.getSelectDateTo() != null) {
			m_sbFrom.append(" and c.dbill_date < to_date('"
					+ DataFormat.getDateString(condition.getSelectDateTo())
					+ "', 'yyyy-mm-dd') + 1 \n");
		}
		m_sbFrom.append(" group by c.ccode) h, \n");
		m_sbFrom.append(" (select f.ssegmentname2, f.ssegmentcode2,f.nbalancedirection \n");
		m_sbFrom.append(" from sett_glsubjectdefinition f \n");
		m_sbFrom.append(" where 1=1 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and f.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and f.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" and f.nstatus = 1 \n");
		m_sbFrom.append(" and f.nisleaf = 1) g \n");
		m_sbFrom.append(" where i.ccode(+) = g.ssegmentcode2 \n");
		m_sbFrom.append(" and j.ssegmentcode2(+) = g.ssegmentcode2 \n");
		m_sbFrom.append(" and a.ccode(+) = g.ssegmentcode2 \n");
		m_sbFrom.append(" and b.ssegmentcode2(+) = g.ssegmentcode2 \n");
		m_sbFrom.append(" and h.ccode(+) = g.ssegmentcode2 ) abcd ) abc ) a4, \n");
		
		
		
		
		
		
		
		
		m_sbFrom.append(" sett_accounttype a5, \n");//update by xfma 2009-1-6
		
		//add by xfma 2009-1-6 对于手续费和利息税费进行特殊处理（手续费和利息税费只
		//设置了一个科目，没有明细信息）
		m_sbFrom.append(" (select t1.nloancontractid contractID,0 payAmount,sum(t1.mcommission) loanAmount,"
				+AUDITConstant.AmountTypeForAudit.AmountType_4+" amountType \n");
		m_sbFrom.append(" from sett_transrepaymentloan t1  \n");
		m_sbFrom.append(" where t1.MCOMMISSION > 0 \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and t1.dtexecute >= to_date('"
					+ DataFormat.getDateString(condition.getSelectDateFrom())
					+ "', 'yyyy-mm-dd') \n");
		}
		if (condition.getSelectDateTo() != null) {
			m_sbFrom.append(" and t1.dtexecute <= to_date('"
					+ DataFormat.getDateString(condition.getSelectDateTo())
					+ "', 'yyyy-mm-dd') \n");
		}
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and t1.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and t1.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" and t1.nstatusid > 0 \n");
		m_sbFrom.append(" group by t1.nloancontractid \n");
		m_sbFrom.append(" union all \n");
		m_sbFrom.append(" select t2.nloancontractid contractID,0 payAmount,sum(t2.minteresttax) loanAmount,"
				+AUDITConstant.AmountTypeForAudit.AmountType_5+" amountType  \n");
		m_sbFrom.append(" from sett_transrepaymentloan t2  \n");
		m_sbFrom.append(" where t2.minteresttax > 0  \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and t2.dtexecute >= to_date('"
					+ DataFormat.getDateString(condition.getSelectDateFrom())
					+ "', 'yyyy-mm-dd') \n");
		}
		if (condition.getSelectDateTo() != null) {
			m_sbFrom.append(" and t2.dtexecute <= to_date('"
					+ DataFormat.getDateString(condition.getSelectDateTo())
					+ "', 'yyyy-mm-dd') \n");
		}
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and t2.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and t2.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" and t2.nstatusid > 0 \n");
		m_sbFrom.append(" group by t2.nloancontractid) a6, \n");
		
		
		
		m_sbFrom.append("(  \n");
		m_sbFrom.append("select subjectCode,sum(nvl(payAmount,0)) payAmount,sum(nvl(loanAmount,0)) loanAmount from (  \n");
		m_sbFrom.append("select case  \n");
		m_sbFrom.append("when b2.transDirection = 1 then  \n");
		m_sbFrom.append("b2.subjectCode  \n");
		m_sbFrom.append("else  \n");
		m_sbFrom.append("b3.subjectCode  \n");
		m_sbFrom.append("end subjectCode,  \n");
		m_sbFrom.append("payAmount,  \n");
		m_sbFrom.append("loanAmount  \n");
		m_sbFrom.append("from (select b1.ssubjectcode subjectCode,  \n");
		m_sbFrom.append("b1.ntransdirection transDirection,  \n");
		m_sbFrom.append("sum(b1.mamount) payAmount  \n");
		m_sbFrom.append("from sett_glentry b1  \n");
		m_sbFrom.append("where b1.nstatusid = 3  \n");
		m_sbFrom.append("and b1.ntransdirection = 1  \n");
//		m_sbFrom.append("and to_char(b1.dtexecute, 'yyyy-mm-dd') >='"
//							+ DataFormat.getDateString(condition
//									.getSelectDateFrom()).substring(0, 4)+ "-01-01" + "'  \n");
		m_sbFrom.append("and to_char(b1.dtexecute, 'yyyy-mm-dd') >='2008-12-01'  \n");
		m_sbFrom.append("and to_char(b1.dtexecute, 'yyyy-mm-dd') < '"
							+ DataFormat.getDateString(condition
									.getSelectDateFrom()) + "'  \n");
		m_sbFrom.append("group by b1.ssubjectcode, b1.ntransdirection) b2  \n");
		m_sbFrom.append("full outer join (select b1.ssubjectcode subjectCode,  \n");
		m_sbFrom.append("b1.ntransdirection transDirection,  \n");
		m_sbFrom.append("sum(b1.mamount) loanAmount  \n");
		m_sbFrom.append("from sett_glentry b1  \n");
		m_sbFrom.append("where b1.nstatusid = 3  \n");
		m_sbFrom.append("and b1.ntransdirection = 2  \n");
//		m_sbFrom.append("and to_char(b1.dtexecute, 'yyyy-mm-dd') >='"
//							+ DataFormat.getDateString(condition
//									.getSelectDateFrom()).substring(0, 4)+ "-01-01" + "'  \n");
		m_sbFrom.append("and to_char(b1.dtexecute, 'yyyy-mm-dd') >='2008-12-01'  \n");
		m_sbFrom.append("and to_char(b1.dtexecute, 'yyyy-mm-dd') < '"
							+ DataFormat.getDateString(condition
									.getSelectDateFrom()) + "'  \n");
		m_sbFrom.append("group by b1.ssubjectcode, b1.ntransdirection) b3 on b2.subjectCode= b3.subjectCode  \n");
		m_sbFrom.append("union all  \n");
		m_sbFrom.append("select abc.subjectcode subjectCode,  \n");
		m_sbFrom.append("abc.md payAmount,abc.mc loanAmount  \n");
		m_sbFrom.append("from (  \n");
		m_sbFrom.append("select * from (  \n");
		m_sbFrom.append("select g.ssegmentcode2 subjectcode,  \n");
		m_sbFrom.append("g.ssegmentname2 subjectname,g.nbalancedirection,  \n");
		m_sbFrom.append("nvl(h.mc,0) mc,  \n");
		m_sbFrom.append("nvl(h.md,0) md  \n");
		m_sbFrom.append("from \n");
		m_sbFrom.append("(select nvl(sum(nvl(c.mc, 0)), 0) mc,  \n");
		m_sbFrom.append("nvl(sum(nvl(c.md, 0)), 0) md,  \n");
		m_sbFrom.append("c.ccode  \n");
		m_sbFrom.append("from uf_gl_accvouch_cny c  \n");
		m_sbFrom.append("where c.iperiod !=0   \n");
		m_sbFrom.append("and c.ccode_equal = '4103'  \n");
//		m_sbFrom.append("and c.dbill_date >= to_date('"
//							+ DataFormat.getDateString(condition
//									.getSelectDateFrom()).substring(0, 4)+ "-01-01" + "', 'yyyy-mm-dd')  \n");
		m_sbFrom.append("and c.dbill_date >= to_date('2008-12-01', 'yyyy-mm-dd')  \n");
		m_sbFrom.append("and c.dbill_date < to_date('"
							+ DataFormat.getDateString(condition
									.getSelectDateFrom()) + "', 'yyyy-mm-dd')  \n");
		m_sbFrom.append("group by c.ccode) h,  \n");
		m_sbFrom.append("(select f.ssegmentname2, f.ssegmentcode2,f.nbalancedirection  \n");
		m_sbFrom.append("from sett_glsubjectdefinition f  \n");
		m_sbFrom.append("where 1=1  \n");
		m_sbFrom.append("and f.nofficeid = 1 \n");
		m_sbFrom.append("and f.ncurrencyid = 1  \n");
		m_sbFrom.append("and f.nstatus = 1  \n");
		m_sbFrom.append("and f.nisleaf = 1) g  \n");
		m_sbFrom.append("where h.ccode(+) = g.ssegmentcode2 ) abcd ) abc  \n");
		m_sbFrom.append(") group by subjectCode  \n");
		m_sbFrom.append(") a8 \n");
		

		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" where a1.accountID = a2.accountID(+)  \n");
		m_sbWhere.append(" and a1.contractID = a2.ncontractID(+)  \n");
		m_sbWhere.append(" and a1.amountType = a2.amountType(+)  \n");
		m_sbWhere.append(" and a1.subjectCode = a3.subjectCode(+)  \n");
		m_sbWhere.append(" and a1.subjectCode = a8.subjectCode(+)  \n");
		m_sbWhere.append(" and a1.subjectCode = a4.subjectcode(+)  \n");
		m_sbWhere.append(" and a1.accountTypeID = a5.id  \n");
		m_sbWhere.append(" and a1.contractID = a6.contractID(+)  \n");//add by xfma
		m_sbWhere.append(" and a1.amountType = a6.amountType(+)  \n");//add by xfma
		m_sbWhere.append(" and a5.nstatusid>0  \n");
		if (condition.getOfficeID() > 0) {
			m_sbWhere.append(" and a5.officeid = " + condition.getOfficeID()+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbWhere.append(" and a5.currencyid = "+ condition.getCurrencyID() + " \n");
		}
		if (!"".equals(condition.getAmountTypes())) {
			m_sbWhere.append("        and a1.amountType  IN("
					+ condition.getAmountTypes() + ") \n");
		} else {
			long[] arrayAmountTypeReturn = AUDITConstant.AmountTypeForAudit
					.getLoanCode();
			String strAmountTypeID = "";
			for (int i = 0; i < arrayAmountTypeReturn.length; i++) {
				strAmountTypeID = strAmountTypeID + arrayAmountTypeReturn[i]
						+ "";
				if (arrayAmountTypeReturn[i] > 0) {
					strAmountTypeID = strAmountTypeID + ",";
				}
			}
			strAmountTypeID = strAmountTypeID.substring(0, strAmountTypeID
					.length() - 1);
			m_sbWhere.append("       and a1.amountType  IN(" + strAmountTypeID
					+ ") \n");
		}

		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append("   order by a5.saccounttypecode, a1.amountType, a1.contractCode \n");
		result = new ArrayList();
		try {
			/**
			 * 得到当前系统时间
			 */
			Timestamp tsSys = Env.getSystemDate(condition.getOfficeID(),condition.getCurrencyID());
			initDAO();
			prepareStatement(m_sbSelect.toString() + m_sbFrom.toString()+ m_sbWhere.toString() + m_sbOrderBy.toString());
			System.out.println("信贷审计SQL add by xfma(马现福):  \n"+m_sbSelect.toString() + m_sbFrom.toString()+ m_sbWhere.toString() + m_sbOrderBy.toString());
			executeQuery();
			while (transRS.next()) {
				LoanAuditResultInfo resultInfo = new LoanAuditResultInfo();

				resultInfo.setAccountTypeID(transRS.getLong("accountTypeID"));
				resultInfo.setAccountType(transRS.getString("accountType"));
				resultInfo.setAmountTypeID(transRS.getLong("amountType"));
				resultInfo.setContractID(transRS.getLong("contractID"));
				resultInfo.setContractCode(transRS.getString("contractCode"));
				resultInfo.setClientName(transRS.getString("clientName"));
				resultInfo.setAccountID(transRS.getLong("accountID"));
				resultInfo.setPayAmount(transRS.getDouble("payAmount"));
				resultInfo.setLoanAmount(transRS.getDouble("loanAmount"));
				/**
				 * 由xfma(马现福)解释 2009-1-6
		         * 贷款 手续费（损益类）602101 期末 = 期初 - 借方发生额 + 贷方发生额
				 * 贷款 利息税费（负债类）222104 期末 = 期初 - 借方发生额 + 贷方发生额
				 */
//				if(SETTConstant.AccountType.isConsignAccountType(resultInfo.getAccountTypeID()) && resultInfo.getAmountTypeID()==AUDITConstant.AmountTypeForAudit.AmountType_4)
//				{
//					Sett_DailyAccountBalanceDAO dailyDao = new Sett_DailyAccountBalanceDAO(transConn);
//					Collection coll = dailyDao.findAllByAccountIDAndDate(resultInfo.getAccountID(),condition.getOfficeID(),condition.getCurrencyID(),DataFormat.getPreviousDate(condition.getSelectDateFrom()));
//					
//					double sumCommission = 0.0;
//					long subAccountID = -1;
//					if(coll!=null && coll.size()>0)
//					{
//						InterestOperation io = new InterestOperation();
//						for (int i=0;i<coll.size();i++)
//						{
//							DailyAccountBalanceInfo dInfo =(DailyAccountBalanceInfo)coll.toArray()[i];
//							subAccountID= dInfo.getSubAccountID();
//							LoanAccountInterestInfo commission = io.getLoanAccountFee(
//									condition.getOfficeID(),
//									condition.getCurrencyID(),
//									resultInfo.getAccountID(),
//									subAccountID,
//									DataFormat.getPreviousDate(condition.getSelectDateFrom()),
//									tsSys,
//									SETTConstant.InterestFeeType.COMMISION);
//							sumCommission = sumCommission + commission.getInterest();
//						}
//						io.closeConnection();//关闭连接add by xfma 2009-1-4 
//					}
//					resultInfo.setBeginBalance(sumCommission);
//					double endCommBalance = sumCommission-resultInfo.getPayAmount()+resultInfo.getLoanAmount();
//					resultInfo.setEndBalance(endCommBalance);
//				
//				}
//				else if(SETTConstant.AccountType.isConsignAccountType(resultInfo.getAccountTypeID()) && resultInfo.getAmountTypeID()==AUDITConstant.AmountTypeForAudit.AmountType_5)
//				{
//					Sett_DailyAccountBalanceDAO dailyDao = new Sett_DailyAccountBalanceDAO(transConn);
//					Collection coll = dailyDao.findAllByAccountIDAndDate(resultInfo.getAccountID(),condition.getOfficeID(),condition.getCurrencyID(),DataFormat.getPreviousDate(condition.getSelectDateFrom()));
//					
//					double sumInterestTax = 0.0;
//					long subAccountID = -1;
//					if(coll!=null && coll.size()>0)
//					{
//						InterestOperation io = new InterestOperation();
//						for (int i=0;i<coll.size();i++)
//						{
//							DailyAccountBalanceInfo dInfo =(DailyAccountBalanceInfo)coll.toArray()[i];
//							subAccountID= dInfo.getSubAccountID();
//							//利息税费
//							Log.print("----开始计算利息税费----");
//							double dInterestShouldTax = 
//								UtilOperation.Arith.add(
//										dInfo.getMforfeitinterest(),
//									UtilOperation.Arith.add(dInfo.getInterest(),
//											dInfo.getMcompoundinterest()));  //应缴税利息
//							InterestTaxInfo tax = io.getInterestTaxByPlan(
//									resultInfo.getAccountID(),
//									subAccountID,
//									dInterestShouldTax
//									);
//									//	
//							sumInterestTax = sumInterestTax + tax.getInterestTax();
//						}
//						io.closeConnection();//关闭连接add by xfma 2009-1-4 
//					}
//					resultInfo.setBeginBalance(sumInterestTax);
//					double endTaxBalance = sumInterestTax-resultInfo.getPayAmount()+resultInfo.getLoanAmount();
//					resultInfo.setEndBalance(endTaxBalance);
//				}
//				else
				{
					resultInfo.setBeginBalance(transRS.getDouble("beginBalance"));
					resultInfo.setEndBalance(transRS.getDouble("endBalance"));
				}
				resultInfo.setSubjectCode(transRS.getString("subjectCode"));
				resultInfo.setSubjectName(transRS.getString("subjectName"));
				resultInfo.setSubjectBeginBalance(transRS.getDouble("subjectBeginBalance"));
				resultInfo.setSubjectpayAmount(transRS.getDouble("subjectpayBalance"));
				resultInfo.setSubjectloanAmount(transRS.getDouble("subjectloanBalance"));
				resultInfo.setSubjectEndBalance(transRS.getDouble("subjectendBalance"));
				
				resultInfo.setSubjectbeginBalanceDebit(transRS.getDouble("subjectbeginBalanceDebit"));
				resultInfo.setSubjectbeginBalanceCredit(transRS.getDouble("subjectbeginBalanceCredit"));
				resultInfo.setSubjectendBalanceDebit(transRS.getDouble("subjectendBalanceDebit"));
				resultInfo.setSubjectendBalanceCredit(transRS.getDouble("subjectendBalanceCredit"));
				
				result.add(resultInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException("查询结算业务审计出现错误");
		} finally {
			finalizeDAO();
		}
		return result;
	}

	
	/**
	 * 科目明细
	 * @param condition
	 */
	private void getSubjectDetailInfoSQL(QueryAuditSubjectDetailConditionInfo condition) {
		// SELECT
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("t.dbill_date billDate ,to_char(t.ino_id) credenceNo, \n");
		m_sbSelect.append("t.ccode paySubjectCode,nvl(a.ssegmentname2,'')  paySubjectName ,  \n");
		m_sbSelect.append("t.ccode_equal loanSubjectCode,nvl(b.ssegmentname2,'')  loanSubjectName, \n");
		m_sbSelect.append("t.cdigest subjectDigest,t.md paySubjectBalance,t.mc loanSubjectBalance \n");

		m_sbFrom = new StringBuffer();
		m_sbFrom.append("uf_gl_accvouch_cny t, \n");
		m_sbFrom.append("(select f.ssegmentname2,f.ssegmentcode2  \n");
		m_sbFrom.append("from sett_glsubjectdefinition f \n");
		m_sbFrom.append("where f.nofficeid = "+condition.getOfficeID()+" and f.ncurrencyid = "+condition.getCurrencyID()+" and f.nstatus = 1 and f.nisleaf = 1 \n");
		m_sbFrom.append(") a, \n");
		m_sbFrom.append("(select f.ssegmentname2,f.ssegmentcode2  \n");
		m_sbFrom.append("from sett_glsubjectdefinition f \n");
		m_sbFrom.append("where f.nofficeid = "+condition.getOfficeID()+" and f.ncurrencyid = "+condition.getCurrencyID()+" and f.nstatus = 1 and f.nisleaf = 1 \n");
		
		m_sbFrom.append(") b \n");
		
		// WHERE
		m_sbWhere = new StringBuffer();
//		m_sbWhere.append("t.ccode = a.ssegmentcode2(+) and t.ccode_equal = b.ssegmentcode2(+) \n");
		m_sbWhere.append("t.ccode = a.ssegmentcode2(+) and t.ccode_equal = b.ssegmentcode2(+) and t.iperiod !=0 \n");
		m_sbWhere.append("	and t.ccode = '"+condition.getSubjectCode()+"' \n");
		m_sbWhere.append("	and t.dbill_date >= to_date('"+DataFormat.getDateString(condition.getStartDate())+"', 'yyyy-mm-dd')   \n");
		m_sbWhere.append("  and t.dbill_date < to_date('"+DataFormat.getDateString(condition.getEndDate())+"', 'yyyy-mm-dd') + 1 \n");
	}

	/**
	 * 科目明细
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAuditSubjectDetail(QueryAuditSubjectDetailConditionInfo condition) throws Exception {
		// 获取SQL字句
		getSubjectDetailInfoSQL(condition);
		// 获取PageLoader对象
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		// 初始化PageLoader对象、实现查询和分页
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.audit.settlementaudit.dataentity.QueryAuditSubjectDetailInfo",
						null);
		pageLoader.setOrderBy(" order by t.ino_id,t.inid");
		return pageLoader;
	}

}
