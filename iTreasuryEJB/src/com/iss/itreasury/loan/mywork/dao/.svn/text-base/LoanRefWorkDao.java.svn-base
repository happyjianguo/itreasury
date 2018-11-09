package com.iss.itreasury.loan.mywork.dao;

import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Constant.ApprovalAction;

public class LoanRefWorkDao {

	public String queryLoanTransActionWork(LoanMyWorkInfo info) {

		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" distinct vl.*,re.transtypeid transtypeid,re.transid transid \n ");
		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,V_LOAN_TRANSACTION vl \n");
		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = vl.id \n");

		m_sbWhere.append(" and  re.officeid=" + info.getOfficeID() + " \n");

		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID() + " \n");

		m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");

		m_sbWhere.append("  and re.transtypeid=  vl.loanSubTypeId \n");

		// m_sbWhere.append(" and vl.statusid ="+loanMyWorkInfo.getStatusID()+"
		// \n");
		m_sbWhere.append(" and (vl.statusId = " + LOANConstant.LoanStatus.SAVE);
		m_sbWhere.append(" or vl.statusId = " + LOANConstant.LoanStatus.SUBMIT
				+ ")");

		m_sbWhere.append(" and vl.inputuserid = " + info.getUserID() + " \n");
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String queryLoanContractStatusChangeWork(LoanMyWorkInfo info) {

		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" distinct vl.*,re.transtypeid transtypeid,re.transid transid \n");

		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append(" (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,v_loan_contractstatuschange vl \n");

		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vl.id \n");
		m_sbWhere.append(" and re.officeid=" + info.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbWhere.append(" and re.transtypeid=  vl.loanSubTypeId \n");
		m_sbWhere.append(" and (vl.statusId = " + LOANConstant.LoanStatus.SAVE);
		m_sbWhere.append(" or vl.statusId = " + LOANConstant.LoanStatus.SUBMIT
				+ ")");
		m_sbWhere.append(" and vl.inputuserid = " + info.getUserID() + " \n");

		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String queryLoanContractRisklevelChangeWork(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" distinct vl.*, vl.oldrisk oldStatus, vl.newrisk changeStatus, re.transtypeid transtypeid,re.transid transid \n");

		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append(" (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,v_loan_contractriskchange vl \n");

		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vl.id \n");
		m_sbWhere.append(" and re.officeid=" + info.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");
		m_sbWhere.append(" and re.transtypeid=  vl.loanSubTypeId \n");
		m_sbWhere.append(" and (vl.statusId = " + LOANConstant.LoanStatus.SAVE);
		m_sbWhere.append(" or vl.statusId = " + LOANConstant.LoanStatus.SUBMIT
				+ ")");
		m_sbWhere.append(" and vl.inputuserid = " + info.getUserID() + " \n");
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String queryLoanContractPlanChangeWork(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" distinct vl.*,re.transtypeid transtypeid,re.transid transid \n ");
		// from
		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,v_loan_contractplanmodify vl \n");
		// where
		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = vl.id \n");

		m_sbWhere.append(" and  re.officeid=" + info.getOfficeID() + " \n");

		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID() + " \n");

		m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");

		m_sbWhere.append("  and re.transtypeid=  vl.loanSubTypeId \n");

		m_sbWhere.append(" and vl.statusid =" + info.getStatusID() + " \n");

		m_sbWhere.append(" and vl.inputuserid = " + info.getUserID() + " \n");

		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}

	public String queryLoanContractRateChangeWork(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect;
		StringBuffer m_sbFrom;
		StringBuffer m_sbWhere;
		if (info.getQSingleOrBatch().equals("batch")) {
			m_sbSelect = new StringBuffer();
			m_sbSelect
					.append(" distinct re.transtypeid as loanSubTypeId,re.transid transid,vl.id as batchid,vl.inputdate,u.sname as inputusername,l.name as loanSubTypeName \n ");
			// from
			m_sbFrom = new StringBuffer();
			m_sbFrom
					.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,loan_rateadjust_batch vl,userinfo u,loan_loantypesetting l \n");
			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere
					.append("  re.transid = vl.id and l.id=re.transtypeid and u.id=vl.inputuserid \n");

			m_sbWhere.append(" and  re.officeid=" + info.getOfficeID() + " \n");

			m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID()
					+ " \n");

			m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");

			m_sbWhere.append(" and re.transtypeid=  vl.nSubTypeId \n");

			// 以下两行,将拒绝的状态限制去掉.20101130暂时放开条件,关系到是查看拒绝还是被拒绝的问题,注释掉第二行,说明是显示我被拒绝的业务
			m_sbWhere.append(" and re.actionId="
					+ ApprovalAction.INTEREST_ADJUST_BATCH + " and vl.status ="
					+ info.getStatusID() + " \n");

			// m_sbWhere.append(" and
			// re.actionId="+ApprovalAction.INTEREST_ADJUST_BATCH+" \n");

			m_sbWhere.append(" and vl.inputuserid = " + info.getUserID()
					+ " \n");
		} else {
			m_sbSelect = new StringBuffer();
			m_sbSelect
					.append(" distinct re.transtypeid transtypeid,re.transid transid,vl.* \n ");
			// from
			m_sbFrom = new StringBuffer();
			m_sbFrom
					.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,v_loan_contractrateadjust vl \n");
			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append("  re.transid = vl.id \n");

			m_sbWhere.append(" and  re.officeid=" + info.getOfficeID() + " \n");

			m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID()
					+ " \n");

			m_sbWhere.append(" and re.moduleid=" + info.getModuleID() + " \n");

			m_sbWhere.append(" and re.actionid="
					+ ApprovalAction.INTEREST_ADJUST
					+ "  and re.transtypeid=  vl.loanSubTypeId \n");

			m_sbWhere.append(" and vl.statusid =" + info.getStatusID() + " \n");

			m_sbWhere.append(" and vl.inputuserid = " + info.getUserID()
					+ " \n");
		}
		return "SELECT " + m_sbSelect.toString() + " FROM "
				+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();

	}

	public String queryLoanCredit(LoanMyWorkInfo info) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" distinct v_credit.creditId id, v_credit.creditCode code,v_credit.officeId,v_credit.currencyId,v_credit.clientId,v_credit.amountFormId amountFormId,");
		m_sbSelect
				.append(" v_credit.clientCode,v_credit.clientName borrowclientname,v_credit.creditModel,");
		m_sbSelect
				.append(" v_credit.operationType operationType,v_credit.operationSign operationSign,v_credit.creditAmount amount,v_credit.creditStartDate startdate,v_credit.creditEndDate enddate,");
		m_sbSelect
				.append(" v_credit.creditStatusId,v_credit.inputUserId,v_credit.inputUserName,v_credit.inputDate,");
		m_sbSelect
				.append(" sappr.transtypeid transtypeid, sappr.transid transid");

		StringBuffer m_sbFrom = new StringBuffer();
		// BaseSQL
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr");

		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.LOAN);
		m_sbWhere.append(" and v_credit.creditStatusId = "
				+ LOANConstant.CreditFlowStatus.SAVE);
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
		m_sbWhere.append(" and v_credit.inputuserid = " + info.getUserID());
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
		m_sbSelect
				.append(" distinct vl.*,f.scontractcode as contractcode,f.nsubtypeid as loanSubTypeId,f.id as contractid,c.sname as clientname,u.sname as inputusername,re.transtypeid transtypeid,re.transid transid \n");

		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append(" (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,LOAN_LOANAFTERCHECKREPORT vl,client c,userinfo u,loan_contractform f \n");

		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere
				.append(" re.transid = vl.id and c.id=vl.clientid and u.id=vl.inputuserid and f.id=vl.loancontractid \n");
		m_sbWhere.append(" and re.officeid=" + info.getOfficeID()
				+ " \n");
		m_sbWhere.append(" and re.currencyid=" + info.getCurrencyID()
				+ " \n");
		m_sbWhere.append(" and re.moduleid=" + info.getModuleID()
				+ " \n");
		m_sbWhere.append(" and vl.status = "
				+ info.getStatusID());
		m_sbWhere.append(" and re.actionid = " + ApprovalAction.DH_1);
		// m_sbWhere.append(" or vl.status = " +
		// LOANConstant.AfterCreditStatus.SUBMIT +")");
		m_sbWhere.append(" and vl.inputuserid = " + info.getUserID()
				+ " \n");
		return "SELECT " + m_sbSelect.toString() + " FROM "
		+ m_sbFrom.toString() + " WHERE " + m_sbWhere.toString();
	}
	
	public String getCountSql(String sql){
		return "select count(*) count from("+sql+")";
	}

}
