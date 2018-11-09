package com.iss.itreasury.audit.settlementaudit.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.audit.settlementaudit.dataentity.QueryAuditAccountDetailConditionInfo;
import com.iss.itreasury.audit.settlementaudit.dataentity.QueryAuditSubjectDetailConditionInfo;
import com.iss.itreasury.audit.settlementaudit.dataentity.SettlementAuditCondition;
import com.iss.itreasury.audit.settlementaudit.dataentity.SettlementAuditResultInfo;
import com.iss.itreasury.audit.util.AUDITConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountAmountWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class SettlementAuditDao extends ITreasuryDAO {
	// SQL语法结构
	private StringBuffer m_sbSelect = null;

	private StringBuffer m_sbFrom = null;

	private StringBuffer m_sbWhere = null;

	private StringBuffer m_sbOrderBy = null;

	private Log4j logger = null;

	public SettlementAuditDao() {
		super();
		logger = new Log4j(Constant.ModuleType.AUDIT, this);
	}

	public Collection querySettlementAudit(SettlementAuditCondition condition)
			throws IException {
		Collection result = null;
		m_sbSelect = new StringBuffer();
		m_sbSelect
				.append("select a6.id accountTypeID,a6.saccounttype accountType, \n");
		m_sbSelect.append("a1.amountType, \n");
		m_sbSelect
				.append("a5.id accountID , a5.saccountno accountNo, a1.subaccountID subaccountID,a1.depositNo depositNo, \n");
		m_sbSelect.append("a5.sname accountName, \n");
//		m_sbSelect.append("nvl(a2.beginBalance,0) beginBalance, \n");
//		m_sbSelect.append("nvl(a4.mb,0) beginBalance, \n");
		m_sbSelect.append("nvl(a4.mby,0) - nvl(a8.payAmount,0) + nvl(a8.loanAmount,0) beginBalance, \n");
//		m_sbSelect.append("abs(nvl(a4.mb,0)) beginBalanceView, \n");
		m_sbSelect.append("nvl(a3.payAmount,0) payAmount, \n");
		m_sbSelect.append("nvl(a3.loanAmount,0) loanAmount, \n");
//		m_sbSelect
//				.append(" nvl(a2.beginBalance, 0) - nvl(a3.payAmount,0) + nvl(a3.loanAmount,0) endBalance, \n");
//		m_sbSelect
//                .append(" nvl(a4.mb, 0) - nvl(a3.payAmount,0) + nvl(a3.loanAmount,0) endBalance, \n");
		m_sbSelect
        .append(" nvl(a4.mby,0) - nvl(a8.payAmount,0) + nvl(a8.loanAmount,0) - nvl(a3.payAmount,0) + nvl(a3.loanAmount,0) endBalance, \n");
//		m_sbSelect
//		        .append(" abs(nvl(a4.mb, 0) - nvl(a3.payAmount,0) + nvl(a3.loanAmount,0)) endBalanceView, \n");
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
		m_sbFrom.append("from (select b.id accountID, \n");
		m_sbFrom.append("c.id subaccountID, \n");
		m_sbFrom.append("'' depositNo, \n");
		m_sbFrom.append("a.ssubjectcode subjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_1
				+ " amountType \n");
		m_sbFrom.append("from sett_subaccounttype_current a, \n");
		m_sbFrom.append(" sett_account                b, \n");
		m_sbFrom.append(" sett_subaccount             c \n");
		m_sbFrom.append(" where a.naccountid = b.id \n");
		m_sbFrom.append(" and c.naccountid = b.id \n");
		m_sbFrom.append(" and a.nstatusid > 0 \n");
		m_sbFrom.append(" and b.nstatusid > 0 \n");
		m_sbFrom.append(" and c.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and b.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and b.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" union all \n");
		m_sbFrom.append(" select b.id accountID, \n");
		m_sbFrom.append(" c.id subaccountID, \n");
		m_sbFrom.append(" '' depositNo, \n");
		m_sbFrom.append("  a.spayinterestsubject subjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_2
				+ " amountType \n");
		m_sbFrom.append(" from sett_subaccounttype_current a, \n");
		m_sbFrom.append(" sett_account                b, \n");
		m_sbFrom.append(" sett_subaccount             c \n");
		m_sbFrom.append(" where a.naccountid = b.id \n");
		m_sbFrom.append(" and c.naccountid = b.id \n");
		m_sbFrom.append(" and a.nstatusid > 0 \n");
		m_sbFrom.append(" and b.nstatusid > 0 \n");
		m_sbFrom.append(" and c.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and b.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and b.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" union all \n");
		m_sbFrom.append(" select b.id accountID, \n");
		m_sbFrom.append(" c.id subaccountID, \n");
		m_sbFrom.append(" '' depositNo, \n");
		//m_sbFrom.append(" a.snegotiateinterestsubject subjectCode, \n");
		m_sbFrom.append(" a.sbookedinterestsubject subjectCode, \n");
		
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_3
				+ " amountType \n");
		m_sbFrom.append(" from sett_subaccounttype_current a, \n");
		m_sbFrom.append(" sett_account                b, \n");
		m_sbFrom.append(" sett_subaccount             c \n");
		m_sbFrom.append(" where a.naccountid = b.id \n");
		m_sbFrom.append(" and c.naccountid = b.id \n");
		m_sbFrom.append(" and a.nstatusid > 0 \n");
		m_sbFrom.append(" and b.nstatusid > 0 \n");
		m_sbFrom.append(" and c.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and b.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and b.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" union all \n");
		m_sbFrom.append(" select e.id           accountID, \n");
		m_sbFrom.append(" f.id           subaccountID, \n");
		m_sbFrom.append(" d.sdepositno   depositNo, \n");
		m_sbFrom.append(" d.ssubjectcode subjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_1
				+ " amountType \n");
		m_sbFrom.append(" from sett_subaccounttype_fixed d, \n");
		m_sbFrom.append(" sett_account              e, \n");
		m_sbFrom.append(" sett_subaccount           f \n");
//		m_sbFrom.append(" where d.naccountid = e.id \n");
//		m_sbFrom.append(" and f.naccountid = e.id \n");
		m_sbFrom.append(" where f.naccountid = e.id \n");
		
		m_sbFrom.append(" and d.sdepositno = f.af_sdepositno \n");
		m_sbFrom.append(" and d.nstatusid > 0 \n");
		m_sbFrom.append(" and e.nstatusid > 0 \n");
		m_sbFrom.append(" and f.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" union all \n");
		m_sbFrom.append(" select e.id                  accountID, \n");
		m_sbFrom.append(" f.id                  subaccountID, \n");
		m_sbFrom.append(" d.sdepositno          depositNo, \n");
		m_sbFrom.append(" d.spayinterestsubject interestsubjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_2
				+ " amountType \n");
		m_sbFrom.append(" from sett_subaccounttype_fixed d, \n");
		m_sbFrom.append(" sett_account              e, \n");
		m_sbFrom.append(" sett_subaccount           f \n");
//		m_sbFrom.append(" where d.naccountid = e.id \n");
//		m_sbFrom.append(" and f.naccountid = e.id \n");
		m_sbFrom.append(" where f.naccountid = e.id \n");
		
		m_sbFrom.append(" and d.sdepositno = f.af_sdepositno \n");
		m_sbFrom.append(" and d.nstatusid > 0 \n");
		m_sbFrom.append(" and e.nstatusid > 0 \n");
		m_sbFrom.append(" and f.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" union all \n");
		m_sbFrom.append(" select e.id                     accountID, \n");
		m_sbFrom.append(" f.id                     subaccountID, \n");
		m_sbFrom.append(" d.sdepositno             depositNo, \n");
		m_sbFrom.append(" d.sbookedinterestsubject subjectCode, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_3
				+ " amountType \n");
		m_sbFrom.append(" from sett_subaccounttype_fixed d, \n");
		m_sbFrom.append(" sett_account              e, \n");
		m_sbFrom.append(" sett_subaccount           f \n");
//		m_sbFrom.append(" where d.naccountid = e.id \n");
//		m_sbFrom.append(" and f.naccountid = e.id \n");
		m_sbFrom.append(" where f.naccountid = e.id \n");
		
		m_sbFrom.append(" and d.sdepositno = f.af_sdepositno \n");
		m_sbFrom.append(" and d.nstatusid > 0 \n");
		m_sbFrom.append(" and e.nstatusid > 0 \n");
		m_sbFrom.append(" and f.nstatusid > 0 \n");
		if (condition.getOfficeID() > 0) {
			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
					+ " \n");
		}
		m_sbFrom.append(" ) a1, \n");
		m_sbFrom.append(" (select t1.naccountid       accountID, \n");
		m_sbFrom.append(" t1.nsubaccountid    subaccountID, \n");
//		m_sbFrom.append(" t1.minterestbalance beginBalance, \n");
		m_sbFrom.append(" t1.mbalance beginBalance, \n");

		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_1
				+ " amountType \n");
		m_sbFrom.append(" from sett_dailyaccountbalance t1 \n");
		m_sbFrom.append(" where 1=1 \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom.append(" and to_char(t1.dtdate, 'yyyy-mm-dd') = '"
					+ DataFormat.getPreviousOrNextDate(condition
							.getSelectDateFrom(), -1) + "' \n");
		}
		m_sbFrom.append(" union all \n");
		
		
//		m_sbFrom.append(" select t2.naccountid accountID, \n");
//		m_sbFrom.append(" t2.nsubaccountid subaccountID, \n");
//		m_sbFrom
//				.append(" (nvl(t2.minterest,0) + nvl(t2.ac_mnegotiateinterest,0)) beginBalance, \n");
//		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_2
//				+ " amountType \n");
//		m_sbFrom.append(" from sett_dailyaccountbalance t2 \n");
//		m_sbFrom.append(" where 1=1 \n");
//		if (condition.getSelectDateFrom() != null) {
//			m_sbFrom.append(" and to_char(t2.dtdate, 'yyyy-mm-dd') = '"
//					+ DataFormat.getPreviousOrNextDate(condition
//							.getSelectDateFrom(), -1) + "' \n");
//		}
		
		
		m_sbFrom.append(" select t2.naccountid accountID, \n");
		m_sbFrom.append(" t2.nsubaccountid subaccountID, \n");
		m_sbFrom
				.append(" -sum(nvl(t2.minterest,0) + nvl(t2.mnegotiateinterest,0)) beginBalance, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_2
				+ " amountType \n");
		m_sbFrom
				.append(" from sett_transinterestsettlement t2, sett_subaccount t5 \n");
		m_sbFrom.append(" where t2.nsubaccountid = t5.id \n");
		m_sbFrom.append(" and t2.nstatusid = "
				+ SETTConstant.TransactionStatus.CHECK + " \n");
		m_sbFrom.append(" and t5.nstatusid > 0 \n");
		m_sbFrom.append(" and t2.ninteresttype = "
				+ SETTConstant.InterestFeeType.PREDRAWINTEREST + " \n");
//		m_sbFrom
//				.append(" and t5.dtclearinterest <= t2.DTINTERESTSETTLEMENT \n");
		m_sbFrom
		.append(" and to_char(t2.DTINTERESTSETTLEMENT, 'yyyy-mm-dd') >= '2009-01-21' \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom
					.append(" and to_char(t2.DTINTERESTSETTLEMENT, 'yyyy-mm-dd') <='"
							+ DataFormat.getDateString(condition
									.getSelectDateFrom()) + "' \n");
		}
		m_sbFrom.append(" group by t2.naccountid, t2.nsubaccountid \n");

		
		
		m_sbFrom.append(" union all \n");
		m_sbFrom.append(" select t3.naccountid accountID, \n");
		m_sbFrom.append(" t3.nsubaccountid subaccountID, \n");
		m_sbFrom
				.append(" sum(nvl(t3.minterest,0) + nvl(t3.mnegotiateinterest,0)) beginBalance, \n");
		m_sbFrom.append(AUDITConstant.AmountTypeForAudit.AmountType_3
				+ " amountType \n");
		m_sbFrom
				.append(" from sett_transinterestsettlement t3, sett_subaccount t4 \n");
		m_sbFrom.append(" where t3.nsubaccountid = t4.id \n");
		m_sbFrom.append(" and t3.nstatusid = "
				+ SETTConstant.TransactionStatus.CHECK + " \n");
		m_sbFrom.append(" and t4.nstatusid > 0 \n");
		m_sbFrom.append(" and t3.ninteresttype = "
				+ SETTConstant.InterestFeeType.PREDRAWINTEREST + " \n");
//		m_sbFrom
//				.append(" and t4.dtclearinterest <= t3.DTINTERESTSETTLEMENT \n");
		m_sbFrom
		.append(" and to_char(t3.DTINTERESTSETTLEMENT, 'yyyy-mm-dd') >= '2009-01-21' \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom
					.append(" and to_char(t3.DTINTERESTSETTLEMENT, 'yyyy-mm-dd') <='"
							+ DataFormat.getDateString(condition
									.getSelectDateFrom()) + "' \n");
		}
		m_sbFrom.append(" group by t3.naccountid, t3.nsubaccountid) a2, \n");
		m_sbFrom.append("  ( \n");
				
		m_sbFrom.append("select subjectCode,sum(nvl(payAmount,0)) payAmount,sum(nvl(loanAmount,0)) loanAmount from ( \n");
				
		m_sbFrom.append("select case \n");
		m_sbFrom.append(" when b2.transDirection = "
				+ SETTConstant.DebitOrCredit.DEBIT + " then \n");
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
		m_sbFrom.append(" where b1.nstatusid = "
				+ SETTConstant.TransactionStatus.CHECK + " \n");
		m_sbFrom.append(" and b1.ntransdirection = "
				+ SETTConstant.DebitOrCredit.DEBIT + " \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom
					.append(" and to_char(b1.dtexecute, 'yyyy-mm-dd') >='"
							+ DataFormat.getDateString(condition
									.getSelectDateFrom()) + "' \n");
		}
		if (condition.getSelectDateTo() != null) {
			m_sbFrom
					.append(" and to_char(b1.dtexecute, 'yyyy-mm-dd') <='"
							+ DataFormat.getDateString(condition
									.getSelectDateTo()) + "' \n");
		}
		m_sbFrom.append(" group by b1.ssubjectcode, b1.ntransdirection) b2 \n");
		m_sbFrom
				.append(" full outer join (select b1.ssubjectcode subjectCode, \n");
		m_sbFrom.append(" b1.ntransdirection transDirection, \n");
		m_sbFrom.append(" sum(b1.mamount) loanAmount \n");
		m_sbFrom.append(" from sett_glentry b1 \n");
		m_sbFrom.append(" where b1.nstatusid = "
				+ SETTConstant.TransactionStatus.CHECK + " \n");
		m_sbFrom.append(" and b1.ntransdirection = "
				+ SETTConstant.DebitOrCredit.CREDIT + " \n");
		if (condition.getSelectDateFrom() != null) {
			m_sbFrom
					.append(" and to_char(b1.dtexecute, 'yyyy-mm-dd') >='"
							+ DataFormat.getDateString(condition
									.getSelectDateFrom()) + "' \n");
		}
		if (condition.getSelectDateTo() != null) {
			m_sbFrom
					.append(" and to_char(b1.dtexecute, 'yyyy-mm-dd') <='"
							+ DataFormat.getDateString(condition
									.getSelectDateTo()) + "' \n");
		}
		m_sbFrom
				.append("  group by b1.ssubjectcode, b1.ntransdirection) b3 on b2.subjectCode= b3.subjectCode \n");


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
//		m_sbFrom
//				.append(" from (select nvl(sum(nvl(t.beginbalance, 0)), 0) beginbalance, \n");
//		m_sbFrom.append("  t.ccode \n");
//		m_sbFrom.append(" from uf_gl_accsum_cny t \n");
//		m_sbFrom.append(" where 1=1 \n");
//		if (condition.getSelectDateFrom() != null) {
//			m_sbFrom.append(" and t.iyear =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateFrom())
//					+ "', 'yyyy-mm-dd') - 1,'yyyy')) \n");
//			m_sbFrom.append(" and t.iperiod = 1 \n");
////			m_sbFrom.append(" and t.iperiod =to_number(to_char(to_date('"
////					+ DataFormat.getDateString(condition.getSelectDateFrom())
////					+ "', 'yyyy-mm-dd') - 1,'mm')) \n");
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
//		m_sbFrom
//				.append(" from uf_gl_accvouch_cny c, sett_glsubjectdefinition e \n");
//		m_sbFrom.append(" where c.ccode = e.ssegmentcode2 \n");
//		if (condition.getOfficeID() > 0) {
//			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
//					+ "\n");
//		}
//		if (condition.getCurrencyID() > 0) {
//			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
//					+ " \n");
//		}
//		m_sbFrom.append(" and e.nstatus = 1 \n");
//		m_sbFrom.append(" and e.nisleaf = 1 \n");
//		if (condition.getSelectDateFrom() != null) {
//			m_sbFrom.append(" and c.iyear =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateFrom())
//					+ "', 'yyyy-mm-dd') - 1,'yyyy')) \n");
////			m_sbFrom.append(" and c.iperiod =to_number(to_char(to_date('"
////					+ DataFormat.getDateString(condition.getSelectDateFrom())
////					+ "', 'yyyy-mm-dd') - 1,'mm')) \n");
//			m_sbFrom.append(" and c.dbill_date <to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateFrom())
//					+ "', 'yyyy-mm-dd') - 1 + 1 \n");
//		}
//		m_sbFrom.append(" group by e.ssegmentcode2) j, \n");
//		m_sbFrom
//				.append(" (select nvl(sum(nvl(t.beginbalance, 0)), 0) beginbalance, \n");
//		m_sbFrom.append(" t.ccode \n");
//		m_sbFrom.append(" from uf_gl_accsum_cny t \n");
//		m_sbFrom.append(" where 1=1 \n");
//		if (condition.getSelectDateTo() != null) {
//			m_sbFrom.append(" and t.iyear =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateTo())
//					+ "', 'yyyy-mm-dd') ,'yyyy')) \n");
//			m_sbFrom.append(" and t.iperiod = 1 \n");
////			m_sbFrom.append(" and t.iperiod =to_number(to_char(to_date('"
////					+ DataFormat.getDateString(condition.getSelectDateTo())
////					+ "', 'yyyy-mm-dd'),'mm')) \n");
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
//		m_sbFrom
//				.append(" from uf_gl_accvouch_cny c, sett_glsubjectdefinition e \n");
//		m_sbFrom.append(" where c.ccode = e.ssegmentcode2 \n");
//		if (condition.getOfficeID() > 0) {
//			m_sbFrom.append(" and e.nofficeid = " + condition.getOfficeID()
//					+ "\n");
//		}
//		if (condition.getCurrencyID() > 0) {
//			m_sbFrom.append(" and e.ncurrencyid = " + condition.getCurrencyID()
//					+ " \n");
//		}
//		m_sbFrom.append(" and e.nstatus = 1 \n");
//		m_sbFrom.append(" and e.nisleaf = 1 \n");
//		if (condition.getSelectDateTo() != null) {
//			m_sbFrom.append(" and c.iyear =to_number(to_char(to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateTo())
//					+ "', 'yyyy-mm-dd') ,'yyyy')) \n");
////			m_sbFrom.append(" and c.iperiod =to_number(to_char(to_date('"
////					+ DataFormat.getDateString(condition.getSelectDateTo())
////					+ "', 'yyyy-mm-dd') ,'mm')) \n");
//			m_sbFrom.append(" and c.dbill_date <to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateTo())
//					+ "', 'yyyy-mm-dd') + 1 \n");
//		}
//		m_sbFrom.append(" group by e.ssegmentcode2) b, \n");
//		m_sbFrom.append(" (select nvl(sum(nvl(c.mc, 0)), 0) mc, \n");
//		m_sbFrom.append(" nvl(sum(nvl(c.md, 0)), 0) md, \n");
//		m_sbFrom.append(" c.ccode \n");
//		m_sbFrom.append(" from uf_gl_accvouch_cny c \n");
//		m_sbFrom.append(" where 1=1  \n");
//		if (condition.getSelectDateFrom() != null) {
//			m_sbFrom.append(" and c.dbill_date >= to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateFrom())
//					+ "', 'yyyy-mm-dd') \n");
//		}
//		if (condition.getSelectDateTo() != null) {
//			m_sbFrom.append(" and c.dbill_date < to_date('"
//					+ DataFormat.getDateString(condition.getSelectDateTo())
//					+ "', 'yyyy-mm-dd') + 1 \n");
//		}
//		m_sbFrom.append(" group by c.ccode) h, \n");
//		m_sbFrom.append(" (select f.ssegmentname2, f.ssegmentcode2 \n");
//		m_sbFrom.append(" from sett_glsubjectdefinition f \n");
//		m_sbFrom.append(" where 1=1 \n");
//		if (condition.getOfficeID() > 0) {
//			m_sbFrom.append(" and f.nofficeid = " + condition.getOfficeID()
//					+ "\n");
//		}
//		if (condition.getCurrencyID() > 0) {
//			m_sbFrom.append(" and f.ncurrencyid = " + condition.getCurrencyID()
//					+ " \n");
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		m_sbFrom.append(" sett_account a5, \n");
		m_sbFrom.append(" sett_accounttype a6, \n");
		m_sbFrom.append(" client a7, \n");
		
		
		
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
		m_sbWhere.append(" where a1.subaccountID = a2.subaccountID(+)  \n");
		m_sbWhere.append(" and a1.amountType = a2.amountType(+)  \n");
		m_sbWhere.append(" and a1.subjectCode = a3.subjectCode(+)  \n");
		m_sbWhere.append(" and a1.subjectCode = a8.subjectCode(+)  \n");
		m_sbWhere.append(" and a1.subjectCode = a4.subjectcode(+)  \n");
		m_sbWhere.append(" and a1.accountid = a5.id  \n");
		m_sbWhere.append(" and a5.nstatusid > 0  \n");
		m_sbWhere.append(" and a5.naccounttypeid = a6.id  \n");
		m_sbWhere.append(" and a5.NCLIENTID= a7.id  \n");
		if (condition.getOfficeID() > 0) {
			m_sbWhere.append(" and a5.nofficeid = " + condition.getOfficeID()
					+ "\n");
		}
		if (condition.getCurrencyID() > 0) {
			m_sbWhere.append(" and a5.ncurrencyid = "
					+ condition.getCurrencyID() + " \n");
		}
		if (condition.getStartClientCode() != null
				&& condition.getStartClientCode().length() > 0) {
			m_sbWhere.append("        and a7.scode>='"
					+ condition.getStartClientCode() + "' \n");
		}
		if (condition.getEndClientCode() != null
				&& condition.getEndClientCode().length() > 0) {
			m_sbWhere.append("        and a7.scode<='"
					+ condition.getEndClientCode() + "' \n");
		}
		if (condition.getStartAccountNo() != null
				&& condition.getStartAccountNo().length() > 0) {
			m_sbWhere.append("        and a5.sAccountNo>='"
					+ condition.getStartAccountNo() + "' \n");
		}
		if (condition.getEndAccountNo() != null
				&& condition.getEndAccountNo().length() > 0) {
			m_sbWhere.append("        and a5.sAccountNo<='"
					+ condition.getEndAccountNo() + "' \n");
		}
		if (!"".equals(condition.getAccountTypeSet())) {
			m_sbWhere.append("        and a5.naccounttypeid  IN("
					+ condition.getAccountTypeSet() + ") \n");
		} else {
			long[] arrayReturn = SETTConstant.AccountType
					.getDepositAccountTypeCodeForAudit(condition.getCurrencyID(),condition.getOfficeID());
			String strAccountTypeID = "";
			if(arrayReturn==null||arrayReturn.length==0){
				throw new IException("查询结算业务审计出现错误");
			}
			for (int i = 0; i < arrayReturn.length; i++) {
				strAccountTypeID = strAccountTypeID + arrayReturn[i] + "";
				if (arrayReturn[i] > 0) {
					strAccountTypeID = strAccountTypeID + ",";
				}
			}
			strAccountTypeID = strAccountTypeID.substring(0, strAccountTypeID
					.length() - 1);
			m_sbWhere.append("        and a5.naccounttypeid  IN("
					+ strAccountTypeID + ") \n");
		}
		if (!"".equals(condition.getAmountTypes())) {
			m_sbWhere.append("        and a1.amountType  IN("
					+ condition.getAmountTypes() + ") \n");
		} else {
			long[] arrayAmountTypeReturn = AUDITConstant.AmountTypeForAudit
					.getDepositCode();
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
		m_sbOrderBy
				.append("  order by a6.saccounttypecode, a1.amountType, a5.saccountno \n");
		System.out.println("!!!!!!!!!~~~~~~~~~~~"+m_sbSelect.toString() + m_sbFrom.toString()
				+ m_sbWhere.toString() + m_sbOrderBy.toString());
		result = new ArrayList();
		try {
			initDAO();
			prepareStatement(m_sbSelect.toString() + m_sbFrom.toString()
					+ m_sbWhere.toString() + m_sbOrderBy.toString());
			System.out.println("结算审计SQL add by xfma(马现福): \n"+m_sbSelect.toString() 
					+ m_sbFrom.toString()+ m_sbWhere.toString() + m_sbOrderBy.toString());
			executeQuery();
			while (transRS.next()) {
				SettlementAuditResultInfo resultInfo = new SettlementAuditResultInfo();

				resultInfo.setAccountTypeID(transRS.getLong("accountTypeID"));
				resultInfo.setAccountType(transRS.getString("accountType"));
				resultInfo.setAmountTypeID(transRS.getLong("amountType"));
				resultInfo.setAccountID(transRS.getLong("accountID"));
				resultInfo.setAccountNo(transRS.getString("accountNo"));
				resultInfo.setAccountName(transRS.getString("accountName"));
				resultInfo.setSubaccountID(transRS.getLong("subaccountID"));
				resultInfo.setDepositNo(transRS.getString("depositNo") != null ? transRS.getString("depositNo"): "");
				resultInfo.setBeginBalance(transRS.getDouble("beginBalance"));
				resultInfo.setPayAmount(transRS.getDouble("payAmount"));
				resultInfo.setLoanAmount(transRS.getDouble("loanAmount"));
				resultInfo.setEndBalance(transRS.getDouble("endBalance"));
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
	 * 账户金额查询条件的生成
	 * 
	 * @param condition:查询条件
	 * @return nothing
	 */
	private void getAccountDetailInfoSQL(
			QueryAuditAccountDetailConditionInfo condition) {
		// SELECT
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("a.ID ID,NVL(b.ID,-1) trnasID, a.dtExecute executeDate, a.sTransNo transNo, \n");
		m_sbSelect.append("a.nTransactionTypeId transactionTypeId, a.nTransAccountId transAccountId, \n");
		m_sbSelect.append("a.mAmount amount, a.nTransDirection transDirection, a.nOppAccountId oppAccountId, \n");
		m_sbSelect.append("a.nStatusId statusId, a.sAbstract remark, a.nSerialNo serialNo, \n");
		// 增加查询金额类型
		m_sbSelect.append("b.InputUserID, b.CheckUserID, c.nAccountTypeID accountTypeId,a.amounttype amounttype \n");
		//
		// FROM
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("sett_TransAccountDetail a, sett_VTransaction b, sett_account c \n");
		// WHERE
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("1 = 1 \n");
		m_sbWhere.append("	AND a.sTransNo = b.TransNo(+) \n");
		m_sbWhere.append("	AND a.nOfficeID = " + condition.getOfficeID()
				+ " \n");
		m_sbWhere.append("	AND a.nCurrencyID = " + condition.getCurrencyID()
				+ " \n");
		m_sbWhere.append("	And a.nStatusID = "
				+ SETTConstant.TransactionStatus.CHECK + " \n");
		m_sbWhere.append("	AND a.nTransAccountID = " + condition.getAccountID()
				+ " \n");
		m_sbWhere.append("	AND a.nsubaccountid = "
				+ condition.getSubaccountID() + " \n");
		if (condition.getStartDate() != null
				&& condition.getStartDate().toString().trim().length() > 0) {
			m_sbWhere.append("	AND a.dtExecute >= TO_DATE('"
					+ DataFormat.getDateString(condition.getStartDate())
					+ "', 'YYYY-MM-DD') \n");
		}
		if (condition.getEndDate() != null
				&& condition.getEndDate().toString().trim().length() > 0) {
			m_sbWhere.append("	AND a.dtExecute <= TO_DATE('"
					+ DataFormat.getDateString(condition.getEndDate())
					+ "', 'YYYY-MM-DD') \n");
		}
		m_sbWhere.append("	AND a.nTransAccountID = c.ID \n");
	}

	/**
	 * 账户金额动户、账户金额明细查询
	 * 
	 * @param condition
	 * @return nothing
	 * @throws Exception
	 */
	public PageLoader queryAuditAccountDetail(
			QueryAuditAccountDetailConditionInfo condition) throws Exception {
		// 获取SQL字句
		getAccountDetailInfoSQL(condition);
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
						"com.iss.itreasury.audit.settlementaudit.dataentity.QueryAuditAccountDetailInfo",
						null);
		pageLoader.setOrderBy(" ORDER BY a.dtExecute");
		return pageLoader;
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
