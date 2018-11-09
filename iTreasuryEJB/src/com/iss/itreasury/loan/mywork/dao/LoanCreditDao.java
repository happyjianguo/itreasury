package com.iss.itreasury.loan.mywork.dao;

import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class LoanCreditDao extends LoanMyWorkDao {

	public String getBaseSQL(){
		StringBuffer baseSQL = new StringBuffer();
		
		baseSQL.append(" (select");
		baseSQL.append(" creditAmountSetup.id creditId,");
		baseSQL.append(" creditAmountSetup.creditCode creditCode,");
		baseSQL.append(" creditAmountSetup.officeid officeId,");
		baseSQL.append(" creditAmountSetup.currencyid currencyId,");
		baseSQL.append(" cc.id clientId,");
		baseSQL.append(" cc.code clientCode,");
		baseSQL.append(" cc.name clientName,");
		//baseSQL.append(" creditAmountSetup.creditlimitid creditLimitID,");
		baseSQL.append(" creditAmountSetup.AMOUNTFORMID amountFormId,");
		
		//baseSQL.append(" creditAmountSetup.creditmodel creditMode,");
		baseSQL.append(" creditAmountSetup.creditModel creditModel,");
		
		//baseSQL.append(" credit.credittypeid creditType,");
		
		//baseSQL.append(" creditAmountSetup.OPERATIONTYPE changeTypeId,");
		baseSQL.append(" creditAmountSetup.OPERATIONTYPE operationType,");
		baseSQL.append(" creditAmountSetup.OPERATIONSIGN operationSign,");
		
		baseSQL.append(" creditAmountSetup.creditAmount creditAmount,");
		baseSQL.append(" creditAmountSetup.startdate creditStartDate,");
		baseSQL.append(" creditAmountSetup.enddate creditEndDate,");
		baseSQL.append(" creditAmountSetup.state creditStatusId,");
		baseSQL.append(" creditAmountSetup.inputuserid inputUserId,");
		baseSQL.append(" userinfo.sname inputUserName,");
		baseSQL.append(" creditAmountSetup.inputdate inputDate");
		baseSQL.append(" from CREDIT_AMOUNTSETUP creditAmountSetup, client_clientinfo cc, userinfo userinfo");
		baseSQL.append(" where creditAmountSetup.clientid = cc.id(+)");
		baseSQL.append(" and creditAmountSetup.inputuserid = userinfo.id(+)) v_credit");
		return baseSQL.toString();
	}
	
	/**
	 * 取消审批
	 */
	protected void getCancelApprovalSql(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" v_credit.creditId id, v_credit.creditCode code,v_credit.officeId,v_credit.currencyId,v_credit.clientId,v_credit.amountFormId amountFormId, ");
		m_sbSelect.append(" v_credit.clientCode,v_credit.clientName borrowclientname,v_credit.creditModel,");
		m_sbSelect.append(" v_credit.operationType operationType,v_credit.operationSign operationSign,v_credit.creditAmount amount,v_credit.creditStartDate startdate,v_credit.creditEndDate enddate,");
		m_sbSelect.append(" v_credit.creditStatusId,v_credit.inputUserId,v_credit.inputUserName,v_credit.inputDate,");
		m_sbSelect.append(" sappr.approvalentryid approvalEntryID,sappr.linkurl linkUrl,sappr.id approvalrecordId");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr");
		m_sbFrom.append(" ,(select t.id from loan_loantypesetting t where t.officeid = "+ loanMyWorkInfo.getOfficeID() +" and t.currencyid = "+ loanMyWorkInfo.getCurrencyID() +" and t.loantypeid = "+ LOANConstant.LoanType.CREDIT +" and t.statusid = "+ Constant.RecordStatus.VALID +") loanTypeSet");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.LOAN);
		m_sbWhere.append(" and v_credit.creditStatusId = " + LOANConstant.CreditFlowStatus.CHECK);
		m_sbWhere.append(" and sappr.officeid = " + loanMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + loanMyWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = v_credit.creditId");
		//m_sbWhere.append(" and sappr.transtypeid = " + LOANConstant.LoanType.CREDIT);\
	//	m_sbWhere.append(" and sappr.transtypeid = (select t.id from loan_loantypesetting t where t.officeid = "+ loanMyWorkInfo.getOfficeID() +" and t.currencyid = "+ loanMyWorkInfo.getCurrencyID() +" and t.loantypeid = "+ LOANConstant.LoanType.CREDIT +" and t.statusid = "+ Constant.RecordStatus.VALID +")");
		m_sbWhere.append(" and sappr.transtypeid = loanTypeSet.id");
		m_sbWhere.append(" and sappr.actionid = v_credit.operationType");
		m_sbWhere.append(" and sappr.lastappuserid = " + loanMyWorkInfo.getUserID());
		m_sbWhere.append(" and sappr.statusId  = " + Constant.RecordStatus.VALID);
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = loanMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by v_credit.creditCode " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by v_credit.clientName " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by v_credit.creditModel " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by v_credit.operationType " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by v_credit.creditAmount " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by v_credit.inputDate " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by v_credit.InputDate desc, sappr.approvalentryid desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by v_credit.InputDate desc, sappr.approvalentryid desc" );
		}	
	}

	/**
	 * 已办业务
	 */
	protected void getFinishedWorkSql(LoanMyWorkInfo loanInutWorkInfo) {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" v_credit.creditId id, v_credit.creditCode code,v_credit.officeId,v_credit.currencyId,v_credit.clientId,v_credit.amountFormId amountFormId,");
		m_sbSelect.append(" v_credit.clientCode,v_credit.clientName borrowclientname,v_credit.creditModel,");
		m_sbSelect.append(" v_credit.operationType operationType,v_credit.operationSign operationSign,v_credit.creditAmount amount,v_credit.creditStartDate startdate,v_credit.creditEndDate enddate,");
		m_sbSelect.append(" v_credit.creditStatusId,v_credit.inputUserId,v_credit.inputUserName,v_credit.inputDate,");
		m_sbSelect.append(" sappr.approvalentryid approvalEntryID, sappr.id approvalrecordId,");
		//m_sbSelect.append("voshi.stepname stepName, voshi.modelname wfDefineName, sappr.linkurl linkUrl, voshi.owner owner,");
		m_sbSelect.append("voshi.stepname stepName, voshi.modelname wfDefineName, sappr.linkurl linkUrl, voshi.caller owner,");
		m_sbSelect.append("voshi.entry_id entryID, voshi.action_code actionCode, voshi.step_code stepCode");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr,");
		m_sbFrom.append("v_os_histroystep voshi,");
		
		m_sbFrom.append(" (select max(voshi.id) maxid ,sappr.approvalentryid from");
		m_sbFrom.append(" (select creditAmountSetup.id  creditId,  creditAmountSetup.OPERATIONTYPE operationType,creditAmountSetup.creditAmount  creditAmount,   creditAmountSetup.inputuserid   inputUserId   ");
		m_sbFrom.append(" from CREDIT_AMOUNTSETUP creditAmountSetup, client_clientinfo  cc, userinfo  userinfo ");
		m_sbFrom.append(" where creditAmountSetup.clientid = cc.id(+)    and creditAmountSetup.inputuserid = userinfo.id(+)) v_credit, SYS_APPROVALRECORD sappr,   v_os_histroystep voshi ");		
		m_sbFrom.append(" where sappr.moduleid = " + Constant.ModuleType.LOAN);	
		m_sbFrom.append(" and sappr.officeid = " + loanInutWorkInfo.getOfficeID());
		m_sbFrom.append(" and sappr.currencyid = " + loanInutWorkInfo.getCurrencyID());	
		m_sbFrom.append(" and sappr.transid = v_credit.creditId");	
		m_sbFrom.append(" and sappr.transtypeid = (select t.id from loan_loantypesetting t where t.officeid = "+ loanInutWorkInfo.getOfficeID() +" and t.currencyid = "+ loanInutWorkInfo.getCurrencyID() +" and t.loantypeid = "+ LOANConstant.LoanType.CREDIT +" and t.statusid = "+ Constant.RecordStatus.VALID +")");
		m_sbFrom.append(" and sappr.actionid = v_credit.operationType");
		m_sbFrom.append(" and voshi.entry_id = sappr.approvalentryid");	
		m_sbFrom.append(" and ((voshi.caller = " + loanInutWorkInfo.getUserID()+") or (v_credit.inputUserId="+loanInutWorkInfo.getUserID()+" and voshi.caller !="+loanInutWorkInfo.getUserID()+") ) group by sappr.approvalentryid ) maxvoshi");
		
		
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.LOAN);
		//m_sbWhere.append(" and v_credit.creditStatusId = " + LOANConstant.CreditFlowStatus.CHECK);	
		m_sbWhere.append(" and sappr.officeid = " + loanInutWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + loanInutWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = v_credit.creditId");
		//m_sbWhere.append(" and sappr.transtypeid = " + LOANConstant.LoanType.CREDIT);
		m_sbWhere.append(" and sappr.transtypeid = (select t.id from loan_loantypesetting t where t.officeid = "+ loanInutWorkInfo.getOfficeID() +" and t.currencyid = "+ loanInutWorkInfo.getCurrencyID() +" and t.loantypeid = "+ LOANConstant.LoanType.CREDIT +" and t.statusid = "+ Constant.RecordStatus.VALID +")");
		m_sbWhere.append(" and sappr.actionid = v_credit.operationType");
		m_sbWhere.append(" and voshi.entry_id = sappr.approvalentryid");
		m_sbWhere.append(" and maxvoshi.maxid=voshi.id ");		
		//m_sbWhere.append(" and voshi.owner = " + loanInutWorkInfo.getUserID());
		m_sbWhere.append(" and ((voshi.caller = " + loanInutWorkInfo.getUserID()+") or (v_credit.inputUserId="+loanInutWorkInfo.getUserID()+" and voshi.caller !="+loanInutWorkInfo.getUserID()+") )");
		
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = loanInutWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = loanInutWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by v_credit.creditCode " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by v_credit.clientName " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by v_credit.creditModel " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by v_credit.operationType " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by v_credit.creditAmount " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by voshi.owner " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by v_credit.inputDate " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by sappr.approvalentryid  desc, v_credit.InputDate desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by  sappr.approvalentryid  desc, v_credit.InputDate  desc" );
		}	
	}

	/**
	 * 拒绝业务
	 */
	protected void getRefuseWorkSql(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct v_credit.creditId id, v_credit.creditCode code,v_credit.officeId,v_credit.currencyId,v_credit.clientId,v_credit.amountFormId amountFormId,");
		m_sbSelect.append(" v_credit.clientCode,v_credit.clientName borrowclientname,v_credit.creditModel,");
		m_sbSelect.append(" v_credit.operationType operationType,v_credit.operationSign operationSign,v_credit.creditAmount amount,v_credit.creditStartDate startdate,v_credit.creditEndDate enddate,");
		m_sbSelect.append(" v_credit.creditStatusId,v_credit.inputUserId,v_credit.inputUserName,v_credit.inputDate,");
		m_sbSelect.append(" sappr.transtypeid transtypeid, sappr.transid transid");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.LOAN);
		m_sbWhere.append(" and v_credit.creditStatusId = " + LOANConstant.CreditFlowStatus.SAVE);
		m_sbWhere.append(" and sappr.officeid = " + loanMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + loanMyWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = v_credit.creditId");
		//m_sbWhere.append(" and sappr.transtypeid = " + LOANConstant.LoanType.CREDIT);
		m_sbWhere.append(" and sappr.transtypeid = (select t.id from loan_loantypesetting t where t.officeid = "+ loanMyWorkInfo.getOfficeID() +" and t.currencyid = "+ loanMyWorkInfo.getCurrencyID() +" and t.loantypeid = "+ LOANConstant.LoanType.CREDIT +" and t.statusid = "+ Constant.RecordStatus.VALID +")");
		m_sbWhere.append(" and v_credit.inputuserid = " + loanMyWorkInfo.getUserID());
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = loanMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by v_credit.creditCode " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by v_credit.clientName " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by v_credit.creditModel " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by v_credit.operationType " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by v_credit.creditAmount " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by v_credit.inputDate " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by v_credit.InputDate desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by v_credit.InputDate desc" );
		}	
	}

	/**
	 * 待审批业务
	 */
	protected Collection queryCurrentWork(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		Vector v_Return = new Vector();
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append("select sappr.*, v_credit.* from");
		//BaseSQL
		m_sbSelect.append(this.getBaseSQL() + ", ");
		m_sbSelect.append("SYS_APPROVALRECORD sappr ");
		m_sbSelect.append("where sappr.moduleid = " + Constant.ModuleType.LOAN);
		m_sbSelect.append(" and v_credit.creditStatusId = " + LOANConstant.CreditFlowStatus.APPROVALING);
		m_sbSelect.append(" and sappr.officeid = " + loanMyWorkInfo.getOfficeID());
		m_sbSelect.append(" and sappr.currencyid = " + loanMyWorkInfo.getCurrencyID());	
		m_sbSelect.append(" and sappr.transid = v_credit.creditId");
		//m_sbSelect.append(" and sappr.transtypeid = " + LOANConstant.LoanType.CREDIT);
		m_sbSelect.append(" and sappr.transtypeid = (select t.id from loan_loantypesetting t where t.officeid = "+ loanMyWorkInfo.getOfficeID() +" and t.currencyid = "+ loanMyWorkInfo.getCurrencyID() +" and t.loantypeid = "+ LOANConstant.LoanType.CREDIT +" and t.statusid = "+ Constant.RecordStatus.VALID +")");
		m_sbSelect.append(" and sappr.actionid = v_credit.operationType");
		m_sbSelect.append(" and sappr.approvalentryid in (" + loanMyWorkInfo.getApprovalEntryIDs() + ")");
		m_sbSelect.append(" order by v_credit.InputDate desc, sappr.approvalentryid desc");
		try {
			initDAO();
			prepareStatement(m_sbSelect.toString());
			executeQuery();

			while (transRS.next()) {
				LoanMyWorkInfo workInfo = new LoanMyWorkInfo();

				//审批关联信息
				workInfo.setId(transRS.getLong("Id"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setActionID(transRS.getLong("actionId"));
				//workInfo.setActionName(transRS.getString("transactionTypeName"));
				workInfo.setApprovalEntryID(transRS.getLong("approvalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));
				//workInfo.setUserID(secMyWorkInfo.getUserID());
				workInfo.setStatusID(transRS.getLong("statusId"));
				
				//贷款授信
				workInfo.setId(transRS.getLong("creditId"));
				workInfo.setAmountFormId(transRS.getLong("amountFormId"));
				workInfo.setCode(transRS.getString("creditCode"));
				workInfo.setCurrencyID(transRS.getLong("currencyId"));
				workInfo.setOfficeID(transRS.getLong("officeId"));
				workInfo.setBorrowClientId(transRS.getLong("clientId"));
				workInfo.setBorrowClientName(transRS.getString("clientName"));
				
				workInfo.setCreditModel(transRS.getLong("creditModel"));
				workInfo.setOperationType(transRS.getLong("operationType"));
				workInfo.setOperationSign(transRS.getLong("operationSign"));
				workInfo.setAmount(transRS.getDouble("creditAmount"));
				
				
				workInfo.setStartDate(transRS.getTimestamp("creditStartDate"));
				workInfo.setEndDate(transRS.getTimestamp("creditEndDate"));
				workInfo.setStatusID(transRS.getLong("creditStatusId"));
				workInfo.setInputUserID(transRS.getLong("inputUserId"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setInputDate(transRS.getTimestamp("inputDate"));

				//对应的审批流引擎的待办信息
				workInfo.setInutWorkInfo((InutApprovalWorkInfo)loanMyWorkInfo.getWorkList().get(String.valueOf(workInfo.getApprovalEntryID())));

				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}

	/**
	 * 待提交业务
	 */
	protected Collection queryWaitDealWithWork(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		Vector v_Return = new Vector();
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append("select v_credit.* from ");
		//BaseSQL
		m_sbSelect.append(this.getBaseSQL());
		m_sbSelect.append(" where v_credit.inputUserId = " + loanMyWorkInfo.getUserID() + " ");
		m_sbSelect.append(" and v_credit.creditStatusId = " + LOANConstant.CreditFlowStatus.SAVE);
		if(loanMyWorkInfo.getCurrencyID()>0)
		{
			m_sbSelect.append(" and v_credit.currencyId = "+loanMyWorkInfo.getCurrencyID());
		}
		if(loanMyWorkInfo.getOfficeID()>0)
		{
			m_sbSelect.append(" and v_credit.officeId = "+loanMyWorkInfo.getOfficeID());
		}
		//m_sbSelect.append(" order by v_credit.inputDate desc, v_credit.creditId desc");
		m_sbSelect.append(" order by v_credit.inputDate desc, v_credit.creditId desc");
		
		try {
			initDAO();
			prepareStatement(m_sbSelect.toString());
			executeQuery();

			while (transRS.next()) {
				LoanMyWorkInfo workInfo = new LoanMyWorkInfo();
				
				//贷款授信
				workInfo.setId(transRS.getLong("creditId"));
				workInfo.setAmountFormId(transRS.getLong("amountFormId"));
				workInfo.setCode(transRS.getString("creditCode"));
				workInfo.setCurrencyID(transRS.getLong("currencyId"));
				workInfo.setOfficeID(transRS.getLong("officeId"));
				workInfo.setBorrowClientId(transRS.getLong("clientId"));
				workInfo.setBorrowClientName(transRS.getString("clientName"));
				
				workInfo.setCreditModel(transRS.getLong("creditModel"));
				workInfo.setOperationType(transRS.getLong("operationType"));
				workInfo.setOperationSign(transRS.getLong("operationSign"));
				workInfo.setAmount(transRS.getDouble("creditAmount"));
				
				
				workInfo.setStartDate(transRS.getTimestamp("creditStartDate"));
				workInfo.setEndDate(transRS.getTimestamp("creditEndDate"));
				workInfo.setStatusID(transRS.getLong("creditStatusId"));
				workInfo.setInputUserID(transRS.getLong("inputUserId"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setInputDate(transRS.getTimestamp("inputDate"));

				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}

}
