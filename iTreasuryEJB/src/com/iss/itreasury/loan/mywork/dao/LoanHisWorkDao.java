package com.iss.itreasury.loan.mywork.dao;

import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Constant.ApprovalAction;

/*
 * Created on 2007-06-21
 *
 * Title:				iTreasury
 * @author             	付明正 
 * Company:             iSoftStone
 * @version				iTreasury4.0新增
 * Description:         产品化4.0在信贷新增审批流,,该功能实现查找我的任务
 */

public class LoanHisWorkDao {

	public String getQueryHisWork(LoanMyWorkInfo loanMyWorkInfo) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.* ,re.approvalentryid approvalEntryID,re.id approvalrecordId, \n ");
		m_sbSelect
				.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.CALLER owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");

		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("  v_os_histroystep his,sys_approvalrecord re,V_LOAN_TRANSACTION vl \n");
		m_sbFrom
				.append("  ,(SELECT max(his.id) hisid  FROM v_os_histroystep his, sys_approvalrecord re,V_LOAN_TRANSACTION vl \n");
		m_sbFrom
				.append("  WHERE his.entry_id = re.approvalentryid and re.transid = vl.Id  and re.actionId = vl.actionId and re.transtypeid = vl.loanSubTypeId \n");
		m_sbFrom.append("  and his.entry_id = re.approvalentryid \n");
		m_sbFrom.append("  and re.transid = vl.Id \n");
		m_sbFrom.append("  and re.officeid=" + loanMyWorkInfo.getOfficeID()
				+ " \n");
		m_sbFrom.append("  and re.currencyid=" + loanMyWorkInfo.getCurrencyID()
				+ " \n");
		m_sbFrom.append("  and re.moduleid=" + loanMyWorkInfo.getModuleID()
				+ " \n");
		m_sbFrom.append("  and (vl.inputuserid = '"
				+ loanMyWorkInfo.getUserID() + "' or his.caller='"
				+ loanMyWorkInfo.getUserID() + "') \n");
		m_sbFrom.append("  group by his.entry_id ) maxhis\n");

		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		m_sbWhere.append(" and re.transid = vl.Id \n");
		m_sbWhere.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID()
				+ " \n");
		m_sbWhere.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID()
				+ " \n");
		m_sbWhere.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID()
				+ " \n");
		// 2010-07-21 孙景 修改： 登录人为录入人 或 登录人为审批人
		m_sbWhere.append(" and (vl.inputuserid = '"
				+ loanMyWorkInfo.getUserID() + "' or his.caller='"
				+ loanMyWorkInfo.getUserID() + "') \n");
		m_sbWhere.append(" and re.actionId = vl.actionId \n");
		m_sbWhere.append(" and re.transtypeid =  vl.loanSubTypeId ");
		m_sbWhere.append(" and maxhis.hisid = his.id "); 

		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String getQueryLoanContractStatusChangeWorkSql(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vl.* ,re.approvalentryid approvalEntryID, \n ");
		m_sbSelect
				.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("  v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTSTATUSCHANGE vl \n");
		m_sbFrom
				.append("  ,(SELECT max(his.id) hisid  FROM  v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTSTATUSCHANGE vl \n");
		m_sbFrom
				.append("  WHERE his.entry_id = re.approvalentryid and re.transid = vl.Id and re.transtypeid =  vl.loanSubTypeId \n");
		m_sbFrom.append("  and re.officeid=" + info.getOfficeID() + " \n");
		m_sbFrom.append("  and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbFrom.append("  and re.moduleid=" + info.getModuleID() + " \n");
		m_sbFrom.append("  and (vl.inputuserid = '" + info.getUserID()
				+ "' or his.caller='" + info.getUserID() + "') \n");
		m_sbFrom.append("  group by his.entry_id ) maxhis\n");
		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		m_sbWhere.append(" and re.transid = vl.Id \n");
		m_sbWhere.append(" and re.officeid=" + info.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbWhere.append(" and (vl.inputuserid = '" + info.getUserID()
				+ "' or his.caller='" + info.getUserID() + "') \n");
		m_sbWhere.append(" and re.actionId = " + ApprovalAction.CONTRACT_STATUS
				+ " \n");
		m_sbWhere.append(" and re.transtypeid =  vl.loanSubTypeId ");
		m_sbWhere.append(" and maxhis.hisid = his.id ");

		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String getQueryLoanContractRisklevelChangeWorkSetHandleSql(
			LoanMyWorkInfo info) {

		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.* , vl.oldrisk oldStatus, vl.newrisk changeStatus,re.approvalentryid approvalEntryID, \n ");
		m_sbSelect
				.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("  v_os_histroystep his,sys_approvalrecord re,v_loan_contractriskchange vl \n");
		m_sbFrom
				.append(" ,(SELECT max(his.id) hisid  from v_os_histroystep his,sys_approvalrecord re,v_loan_contractriskchange vl\n");
		m_sbFrom.append("  WHERE his.entry_id = re.approvalentryid \n");
		m_sbFrom.append(" and re.transid = vl.Id \n");
		m_sbFrom.append(" and re.officeid=" + info.getOfficeID() + " \n");
		m_sbFrom.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbFrom.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbFrom.append("  and (vl.inputuserid = '" + info.getUserID()
				+ "' or his.caller='" + info.getUserID() + "') \n");
		m_sbFrom.append(" and re.actionId = "
				+ ApprovalAction.CONTRACT_RISKLEVEL + " \n");
		m_sbFrom.append(" and re.transtypeid =  vl.loanSubTypeId ");
		m_sbFrom.append("  group by his.entry_id ) maxhis\n");
		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		m_sbWhere.append(" and re.transid = vl.Id \n");
		m_sbWhere.append(" and re.officeid=" + info.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbWhere.append(" and (vl.inputuserid = '" + info.getUserID()
				+ "' or his.caller='" + info.getUserID() + "') \n");
		m_sbWhere.append(" and re.actionId = "
				+ ApprovalAction.CONTRACT_RISKLEVEL + " \n");
		m_sbWhere.append(" and re.transtypeid =  vl.loanSubTypeId ");
		m_sbWhere.append(" and maxhis.hisid = his.id ");
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String getQueryLoanContractPlanChangeWorkSql(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vl.* ,re.approvalentryid approvalEntryID, \n ");
		m_sbSelect
				.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("  v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTPLANMODIFY vl \n");
		m_sbFrom
				.append("  ,(SELECT max(his.id) hisid from v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTPLANMODIFY vl \n");
		m_sbFrom
				.append("  WHERE his.entry_id = re.approvalentryid  and re.transid = vl.Id   \n");
		m_sbFrom.append("  and re.transtypeid =  vl.loanSubTypeId \n");
		m_sbFrom.append(" and re.statusid = 1 \n");
		m_sbFrom.append(" and re.officeid=" + info.getOfficeID() + " \n");
		m_sbFrom.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbFrom.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbFrom.append(" and re.actionId = " + ApprovalAction.CONTRACT_PLAN
				+ " \n");
		m_sbFrom.append(" and (vl.inputuserid = '" + info.getUserID()
				+ "' or his.caller='" + info.getUserID() + "') \n");
		m_sbFrom.append("  group by his.entry_id ) maxhis\n");
		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		m_sbWhere.append(" and re.transid = vl.Id \n");
		m_sbWhere.append(" and re.officeid=" + info.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbWhere.append(" and (vl.inputuserid = '" + info.getUserID()
				+ "' or his.caller='" + info.getUserID() + "') \n");
		m_sbWhere.append(" and re.actionId = " + ApprovalAction.CONTRACT_PLAN
				+ " \n");
		m_sbWhere.append(" and re.transtypeid =  vl.loanSubTypeId ");
		m_sbWhere.append(" and maxhis.hisid = his.id ");
		String aaa  = "SELECT " + m_sbSelect.toString() + " FROM "
		+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
		return aaa;
	}

	public String getQueryLoanContractRateChangeWorkSql(LoanMyWorkInfo info) {

		StringBuffer m_sbWhere;
		StringBuffer m_sbFrom;
		StringBuffer m_sbSelect;
		if (info.getQSingleOrBatch().equals("batch")) {
			m_sbSelect = new StringBuffer();
			m_sbSelect
					.append(" vl.id as batchid ,vl.inputdate,re.approvalentryid approvalEntryID,u.sname as inputusername, \n ");
			m_sbSelect
					.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.CALLER owner, \n ");
			m_sbSelect
					.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");

			m_sbFrom = new StringBuffer();
			m_sbFrom
					.append("  v_os_histroystep his,sys_approvalrecord re,loan_rateadjust_batch vl,userinfo u \n");
			m_sbFrom.append(" ,( \n");
			m_sbFrom.append("select max(his.id) maxid, re.approvalentryid \n");
			m_sbFrom.append(" from \n");
			m_sbFrom
					.append("  v_os_histroystep his,sys_approvalrecord re,loan_rateadjust_batch vl,userinfo u \n");
			m_sbFrom.append(" where \n");
			m_sbFrom.append("  his.entry_id = re.approvalentryid \n");
			m_sbFrom.append(" and re.transid = vl.Id \n");
			m_sbFrom.append(" and re.officeid=" + info.getOfficeID() + " \n");
			m_sbFrom.append(" and re.currencyid=" + info.getCurrencyID()
					+ " \n");
			m_sbFrom.append(" and re.moduleid=" + info.getModuleID() + " \n");
			m_sbFrom.append(" and re.actionId = "
					+ ApprovalAction.INTEREST_ADJUST_BATCH + " \n");
			m_sbFrom.append(" and re.transtypeid =  vl.NSUBTYPEID ");
			m_sbFrom.append(" and vl.INPUTUSERID = u.id ");
			m_sbFrom.append(" and (vl.INPUTUSERID = " + info.getUserID()
					+ " or his.caller = " + info.getUserID() + ") \n");
			m_sbFrom.append(" group by re.approvalentryid ) maxvoshi \n");

			m_sbWhere = new StringBuffer();
			m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
			m_sbWhere.append(" and re.transid = vl.Id \n");
			m_sbWhere.append(" and maxvoshi.maxid = his.id \n");
			m_sbWhere.append(" and re.officeid=" + info.getOfficeID() + " \n");
			m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID()
					+ " \n");
			m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");
			m_sbWhere.append(" and re.actionId = "
					+ ApprovalAction.INTEREST_ADJUST_BATCH + " \n");
			m_sbWhere.append(" and re.transtypeid =  vl.NSUBTYPEID ");
			m_sbWhere.append(" and vl.INPUTUSERID = u.id ");
			m_sbWhere.append(" and (vl.INPUTUSERID = " + info.getUserID()
					+ " or his.caller = " + info.getUserID() + ") \n");
		} else {
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" vl.* ,re.approvalentryid approvalEntryID, \n ");
			m_sbSelect
					.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
			m_sbSelect
					.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");

			m_sbFrom = new StringBuffer();
			m_sbFrom
					.append("  v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTRATEADJUST vl \n");
			m_sbFrom.append(" ,(\n");
			m_sbFrom.append(" select max(his.id) maxid, re.approvalentryid \n");
			m_sbFrom.append(" from \n");
			m_sbFrom
					.append(" v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTRATEADJUST vl \n");
			m_sbFrom.append(" where \n");
			m_sbFrom.append("  his.entry_id = re.approvalentryid \n");
			m_sbFrom.append(" and re.transid = vl.Id \n");
			m_sbFrom.append(" and re.officeid=" + info.getOfficeID() + " \n");
			m_sbFrom.append(" and re.currencyid=" + info.getCurrencyID()
					+ " \n");
			m_sbFrom.append(" and re.moduleid=" + info.getModuleID() + " \n");
			m_sbFrom.append(" and (vl.INPUTUSERID = " + info.getUserID()
					+ " or his.caller = " + info.getUserID() + ") \n");
			m_sbFrom.append(" and re.actionId = "
					+ ApprovalAction.INTEREST_ADJUST + " \n");
			m_sbFrom.append(" and re.transtypeid =  vl.loanSubTypeId ");
			m_sbFrom.append(" group by re.approvalentryid ");
			m_sbFrom.append(" ) maxvoshi \n");

			m_sbWhere = new StringBuffer();
			m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
			m_sbWhere.append(" and re.transid = vl.Id \n");
			m_sbWhere.append(" and maxvoshi.maxid = his.id \n");
			m_sbWhere.append(" and re.officeid=" + info.getOfficeID() + " \n");
			m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID()
					+ " \n");
			m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");
			m_sbWhere.append(" and (vl.INPUTUSERID = " + info.getUserID()
					+ " or his.caller = " + info.getUserID() + ") \n");
			m_sbWhere.append(" and re.actionId = "
					+ ApprovalAction.INTEREST_ADJUST + " \n");
			m_sbWhere.append(" and re.transtypeid =  vl.loanSubTypeId ");
		}
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();

	}

	public String queryLoanCredit(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" v_credit.creditId id, v_credit.creditCode code,v_credit.officeId,v_credit.currencyId,v_credit.clientId,v_credit.amountFormId amountFormId,");
		m_sbSelect
				.append(" v_credit.clientCode,v_credit.clientName borrowclientname,v_credit.creditModel,");
		m_sbSelect
				.append(" v_credit.operationType operationType,v_credit.operationSign operationSign,v_credit.creditAmount amount,v_credit.creditStartDate startdate,v_credit.creditEndDate enddate,");
		m_sbSelect
				.append(" v_credit.creditStatusId,v_credit.inputUserId,v_credit.inputUserName,v_credit.inputDate,");
		m_sbSelect
				.append(" sappr.approvalentryid approvalEntryID, sappr.id approvalrecordId,");
		m_sbSelect
				.append("voshi.stepname stepName, voshi.modelname wfDefineName, sappr.linkurl linkUrl, voshi.caller owner,");
		m_sbSelect
				.append("voshi.entry_id entryID, voshi.action_code actionCode, voshi.step_code stepCode");

		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr,");
		m_sbFrom.append("v_os_histroystep voshi,");

		m_sbFrom
				.append(" (select max(voshi.id) maxid ,sappr.approvalentryid from");
		m_sbFrom
				.append(" (select creditAmountSetup.id  creditId,  creditAmountSetup.OPERATIONTYPE operationType,creditAmountSetup.creditAmount  creditAmount,   creditAmountSetup.inputuserid   inputUserId   ");
		m_sbFrom
				.append(" from CREDIT_AMOUNTSETUP creditAmountSetup, client_clientinfo  cc, userinfo  userinfo ");
		m_sbFrom
				.append(" where creditAmountSetup.clientid = cc.id(+)    and creditAmountSetup.inputuserid = userinfo.id(+)) v_credit, SYS_APPROVALRECORD sappr,   v_os_histroystep voshi ");
		m_sbFrom.append(" where sappr.moduleid = " + Constant.ModuleType.LOAN);
		m_sbFrom.append(" and sappr.officeid = " + info.getOfficeID());
		m_sbFrom.append(" and sappr.currencyid = " + info.getCurrencyID());
		m_sbFrom.append(" and sappr.transid = v_credit.creditId");
		m_sbFrom
				.append(" and sappr.transtypeid = (select t.id from loan_loantypesetting t where t.officeid = "
						+ info.getOfficeID()
						+ " and t.currencyid = "
						+ info.getCurrencyID()
						+ " and t.loantypeid = "
						+ LOANConstant.LoanType.CREDIT
						+ " and t.statusid = "
						+ Constant.RecordStatus.VALID + ")");
		m_sbFrom.append(" and sappr.actionid = v_credit.operationType");
		m_sbFrom.append(" and voshi.entry_id = sappr.approvalentryid");
		m_sbFrom.append(" and ((voshi.caller = " + info.getUserID()
				+ ") or (v_credit.inputUserId=" + info.getUserID()
				+ " and voshi.caller !=" + info.getUserID()
				+ ") ) group by sappr.approvalentryid ) maxvoshi");

		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.LOAN);
		m_sbWhere.append(" and sappr.officeid = " + info.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + info.getCurrencyID());
		m_sbWhere.append(" and sappr.transid = v_credit.creditId");
		m_sbWhere
				.append(" and sappr.transtypeid = (select t.id from loan_loantypesetting t where t.officeid = "
						+ info.getOfficeID()
						+ " and t.currencyid = "
						+ info.getCurrencyID()
						+ " and t.loantypeid = "
						+ LOANConstant.LoanType.CREDIT
						+ " and t.statusid = "
						+ Constant.RecordStatus.VALID + ")");
		m_sbWhere.append(" and sappr.actionid = v_credit.operationType");
		m_sbWhere.append(" and voshi.entry_id = sappr.approvalentryid");
		m_sbWhere.append(" and maxvoshi.maxid=voshi.id ");
		m_sbWhere.append(" and ((voshi.caller = " + info.getUserID()
				+ ") or (v_credit.inputUserId=" + info.getUserID()
				+ " and voshi.caller !=" + info.getUserID() + ") )");
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String getBaseSQL() {
		StringBuffer baseSQL = new StringBuffer();

		baseSQL.append(" (select");
		baseSQL.append(" creditAmountSetup.id creditId,");
		baseSQL.append(" creditAmountSetup.creditCode creditCode,");
		baseSQL.append(" creditAmountSetup.officeid officeId,");
		baseSQL.append(" creditAmountSetup.currencyid currencyId,");
		baseSQL.append(" cc.id clientId,");
		baseSQL.append(" cc.code clientCode,");
		baseSQL.append(" cc.name clientName,");
		// baseSQL.append(" creditAmountSetup.creditlimitid creditLimitID,");
		baseSQL.append(" creditAmountSetup.AMOUNTFORMID amountFormId,");

		// baseSQL.append(" creditAmountSetup.creditmodel creditMode,");
		baseSQL.append(" creditAmountSetup.creditModel creditModel,");

		// baseSQL.append(" credit.credittypeid creditType,");

		// baseSQL.append(" creditAmountSetup.OPERATIONTYPE changeTypeId,");
		baseSQL.append(" creditAmountSetup.OPERATIONTYPE operationType,");
		baseSQL.append(" creditAmountSetup.OPERATIONSIGN operationSign,");

		baseSQL.append(" creditAmountSetup.creditAmount creditAmount,");
		baseSQL.append(" creditAmountSetup.startdate creditStartDate,");
		baseSQL.append(" creditAmountSetup.enddate creditEndDate,");
		baseSQL.append(" creditAmountSetup.state creditStatusId,");
		baseSQL.append(" creditAmountSetup.inputuserid inputUserId,");
		baseSQL.append(" userinfo.sname inputUserName,");
		baseSQL.append(" creditAmountSetup.inputdate inputDate");
		baseSQL
				.append(" from CREDIT_AMOUNTSETUP creditAmountSetup, client_clientinfo cc, userinfo userinfo");
		baseSQL.append(" where creditAmountSetup.clientid = cc.id(+)");
		baseSQL
				.append(" and creditAmountSetup.inputuserid = userinfo.id(+)) v_credit");
		return baseSQL.toString();
	}

	public String queryAfterCreditWork(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vl.* ,re.approvalentryid approvalEntryID, \n ");
		m_sbSelect
				.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode,u.sname as inputusername,c.sname as clientname,f.scontractcode as contractcode \n ");
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("  v_os_histroystep his,sys_approvalrecord re,LOAN_LOANAFTERCHECKREPORT vl,userinfo u,client c,loan_contractform f \n");
		m_sbFrom
				.append(" ,(SELECT max(his.id) hisid  from v_os_histroystep his,sys_approvalrecord re,LOAN_LOANAFTERCHECKREPORT vl,userinfo u,client c,loan_contractform f \n");
		m_sbFrom
				.append(" WHERE his.entry_id = re.approvalentryid and f.id=vl.loancontractid and c.id=vl.clientid and u.id=vl.inputuserid \n");
		m_sbFrom.append(" and re.transid = vl.Id \n");
		m_sbFrom.append("  and re.officeid=" + info.getOfficeID() + " \n");
		m_sbFrom.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbFrom.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbFrom.append(" and (vl.inputuserid = '" + info.getUserID()
				+ "' or his.caller='" + info.getUserID() + "') \n");
		m_sbFrom.append(" and re.actionId = " + ApprovalAction.DH_1 + " \n");
		m_sbFrom.append("  group by his.entry_id ) maxhis\n");
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere
				.append("  his.entry_id = re.approvalentryid and f.id=vl.loancontractid and c.id=vl.clientid and u.id=vl.inputuserid \n");
		m_sbWhere.append(" and re.transid = vl.Id \n");
		m_sbWhere.append(" and re.officeid=" + info.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbWhere.append(" and (vl.inputuserid = '" + info.getUserID()
				+ "' or his.caller='" + info.getUserID() + "') \n");
		m_sbWhere.append(" and re.actionId = " + ApprovalAction.DH_1 + " \n");
		m_sbWhere.append(" and maxhis.hisid = his.id ");
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}
	
	public String getCountSql(String sql){
		return "select count(*) count from ("+sql+")";
	}

}
