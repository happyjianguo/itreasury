package com.iss.itreasury.loan.mywork.dao;

import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Constant.ApprovalAction;

public class LoanCalWorkDao {

	public String queryLoanTransActionWork(LoanMyWorkInfo info) {

		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.* ,re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId ");

		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sys_approvalrecord re,");
		m_sbFrom.append(" ( (select * from V_LOAN_TRANSACTION ");
		m_sbFrom.append("       where actionid = "
				+ Constant.ApprovalAction.LOAN_CONTRACT + " and (statusid = "
				+ LOANConstant.ContractStatus.CHECK + " or statusid = "
				+ LOANConstant.ContractStatus.NOTACTIVE + " ))");
		m_sbFrom.append("    union ");
		m_sbFrom.append(" 	(select * from V_LOAN_TRANSACTION ");
		m_sbFrom.append(" 		where actionid != "
				+ Constant.ApprovalAction.LOAN_CONTRACT + " and statusid = "
				+ LOANConstant.ContractStatus.CHECK + ")  ");
		m_sbFrom.append("  )  vl ");

		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vl.Id");
		m_sbWhere.append(" and re.officeid = " + info.getOfficeID());
		m_sbWhere.append(" and re.currencyid = " + info.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + info.getModuleID());
		m_sbWhere.append(" and re.actionId = vl.actionId");
		m_sbWhere.append(" and re.transtypeid =  vl.loanSubTypeId");
		m_sbWhere.append(" and re.lastappuserid = " + info.getUserID());
		m_sbWhere.append(" and re.statusid = "
				+ SETTConstant.RecordStatus.VALID);
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String queryLoanContractPlanChangeWork(LoanMyWorkInfo info) {
		// select
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.*, re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId");

		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sys_approvalrecord re,v_loan_contractplanmodify vl");

		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vl.Id");
		m_sbWhere.append(" and re.officeid = " + info.getOfficeID());
		m_sbWhere.append(" and re.currencyid = " + info.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + info.getModuleID());
		m_sbWhere.append(" and re.actionId = " + ApprovalAction.CONTRACT_PLAN);
		m_sbWhere.append(" and re.transtypeid = vl.loanSubTypeId");
		m_sbWhere.append(" and re.lastappuserid = " + info.getUserID());
		m_sbWhere.append(" and re.statusid = "
				+ SETTConstant.RecordStatus.VALID);
		m_sbWhere.append(" and vl.statusid = " + LOANConstant.LoanStatus.CHECK);
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String queryLoanContractStatusChangeWork(LoanMyWorkInfo info) {
		// select
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.*, re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId");

		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append(" sys_approvalrecord re,v_loan_contractstatuschange vl");

		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vl.Id");
		m_sbWhere.append(" and re.officeid = " + info.getOfficeID());
		m_sbWhere.append(" and re.currencyid = " + info.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + info.getModuleID());
		m_sbWhere
				.append(" and re.actionId = " + ApprovalAction.CONTRACT_STATUS);
		m_sbWhere.append(" and re.transtypeid = vl.loanSubTypeId");
		m_sbWhere.append(" and re.lastappuserid = " + info.getUserID());
		m_sbWhere.append(" and re.statusid = "
				+ SETTConstant.RecordStatus.VALID);
		m_sbWhere.append(" and vl.statusid = " + LOANConstant.LoanStatus.CHECK);
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();

	}

	public String queryLoanContractRisklevelChangeWork(LoanMyWorkInfo info) {
		// select
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.*,  vl.oldrisk oldStatus, vl.newrisk changeStatus, re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId");

		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sys_approvalrecord re,v_loan_contractriskchange vl");

		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vl.Id");
		m_sbWhere.append(" and re.officeid = " + info.getOfficeID());
		m_sbWhere.append(" and re.currencyid = " + info.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + info.getModuleID());
		m_sbWhere.append(" and re.actionId = "
				+ ApprovalAction.CONTRACT_RISKLEVEL);
		m_sbWhere.append(" and re.transtypeid = vl.loanSubTypeId");
		m_sbWhere.append(" and re.lastappuserid = " + info.getUserID());
		m_sbWhere.append(" and re.statusid = "
				+ SETTConstant.RecordStatus.VALID);
		m_sbWhere.append(" and vl.statusid = " + LOANConstant.LoanStatus.CHECK);
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String queryLoanContractRateChangeWork(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect;
		StringBuffer m_sbFrom;
		StringBuffer m_sbWhere;
		// select
		if (info.getQSingleOrBatch().equals("batch")) {
			m_sbSelect = new StringBuffer();
			m_sbSelect
					.append(" vl.id batchid,vl.inputuserid,u.sname inputUserName,vl.inputdate,vl.nsubtypeid as loanSubTypeId,l.name as loanSubTypeName,re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId");

			// from
			m_sbFrom = new StringBuffer();
			m_sbFrom
					.append(" sys_approvalrecord re,loan_rateadjust_batch vl,userinfo u,loan_loantypesetting l ");

			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere
					.append(" re.transid = vl.Id and u.id=vl.inputuserid and l.id=vl.nsubtypeid ");
			m_sbWhere.append(" and re.officeid = " + info.getOfficeID());
			m_sbWhere.append(" and re.currencyid = " + info.getCurrencyID());
			m_sbWhere.append(" and re.moduleid = " + info.getModuleID());
			m_sbWhere.append(" and re.actionId = "
					+ ApprovalAction.INTEREST_ADJUST_BATCH);
			m_sbWhere.append(" and re.transtypeid = vl.nSubTypeId");
			m_sbWhere.append(" and re.lastappuserid = " + info.getUserID());
			m_sbWhere.append(" and re.statusid = "
					+ SETTConstant.RecordStatus.VALID);
			m_sbWhere.append(" and vl.status = "
					+ LOANConstant.LoanStatus.CHECK);
		} else {
			// select
			m_sbSelect = new StringBuffer();
			m_sbSelect
					.append(" vl.*, re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId");

			// from
			m_sbFrom = new StringBuffer();
			m_sbFrom
					.append(" sys_approvalrecord re,v_loan_contractrateadjust vl");

			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" re.transid = vl.Id");
			m_sbWhere.append(" and re.officeid = " + info.getOfficeID());
			m_sbWhere.append(" and re.currencyid = " + info.getCurrencyID());
			m_sbWhere.append(" and re.moduleid = " + info.getModuleID());
			m_sbWhere.append(" and re.actionId = "
					+ ApprovalAction.INTEREST_ADJUST);
			m_sbWhere.append(" and re.transtypeid = vl.loanSubTypeId");
			m_sbWhere.append(" and re.lastappuserid = " + info.getUserID());
			m_sbWhere.append(" and re.statusid = "
					+ SETTConstant.RecordStatus.VALID);
			m_sbWhere.append(" and vl.statusid = "
					+ LOANConstant.LoanStatus.CHECK);
		}
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();

	}

	public String queryLoanCredit(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" v_credit.creditId id, v_credit.creditCode code,v_credit.officeId,v_credit.currencyId,v_credit.clientId,v_credit.amountFormId amountFormId, ");
		m_sbSelect
				.append(" v_credit.clientCode,v_credit.clientName borrowclientname,v_credit.creditModel,");
		m_sbSelect
				.append(" v_credit.operationType operationType,v_credit.operationSign operationSign,v_credit.creditAmount amount,v_credit.creditStartDate startdate,v_credit.creditEndDate enddate,");
		m_sbSelect
				.append(" v_credit.creditStatusId,v_credit.inputUserId,v_credit.inputUserName,v_credit.inputDate,");
		m_sbSelect
				.append(" sappr.approvalentryid approvalEntryID,sappr.linkurl linkUrl,sappr.id approvalrecordId");

		StringBuffer m_sbFrom = new StringBuffer();
		// BaseSQL
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr");
		m_sbFrom
				.append(" ,(select t.id from loan_loantypesetting t where t.officeid = "
						+ info.getOfficeID()
						+ " and t.currencyid = "
						+ info.getCurrencyID()
						+ " and t.loantypeid = "
						+ LOANConstant.LoanType.CREDIT
						+ " and t.statusid = "
						+ Constant.RecordStatus.VALID + ") loanTypeSet");

		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.LOAN);
		m_sbWhere.append(" and v_credit.creditStatusId = "
				+ LOANConstant.CreditFlowStatus.CHECK);
		m_sbWhere.append(" and sappr.officeid = " + info.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + info.getCurrencyID());
		m_sbWhere.append(" and sappr.transid = v_credit.creditId");
		// m_sbWhere.append(" and sappr.transtypeid = " +
		// LOANConstant.LoanType.CREDIT);\
		// m_sbWhere.append(" and sappr.transtypeid = (select t.id from
		// loan_loantypesetting t where t.officeid = "+ info.getOfficeID() +"
		// and t.currencyid = "+ info.getCurrencyID() +" and t.loantypeid = "+
		// LOANConstant.LoanType.CREDIT +" and t.statusid = "+
		// Constant.RecordStatus.VALID +")");
		m_sbWhere.append(" and sappr.transtypeid = loanTypeSet.id");
		m_sbWhere.append(" and sappr.actionid = v_credit.operationType");
		m_sbWhere.append(" and sappr.lastappuserid = " + info.getUserID());
		m_sbWhere.append(" and sappr.statusId  = "
				+ Constant.RecordStatus.VALID);
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
		// select
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.*, f.id as contractid,re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId,u.sname as inputusername,c.sname as clientname,f.scontractcode as contractcode,f.nsubtypeid as loanSubTypeId");

		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append(" sys_approvalrecord re,LOAN_LOANAFTERCHECKREPORT vl,userinfo u,client c,loan_contractform f");

		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere
				.append(" re.transid = vl.Id and u.id=vl.inputuserid and c.id = vl.clientid and f.id=vl.loancontractid");
		m_sbWhere.append(" and re.officeid = " + info.getOfficeID());
		m_sbWhere.append(" and re.currencyid = " + info.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + info.getModuleID());
		m_sbWhere.append(" and re.actionId = " + ApprovalAction.DH_1);
		// m_sbWhere.append(" and re.transtypeid = vl.loanSubTypeId");
		m_sbWhere.append(" and re.lastappuserid = " + info.getUserID());
		m_sbWhere.append(" and re.statusid = "
				+ SETTConstant.RecordStatus.VALID);
		m_sbWhere.append(" and vl.status = " + LOANConstant.LoanStatus.CHECK);
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}
	
	public String getCountSql(String sql){
		return " select count(*) count from ("+sql+")";
	}

}
