package com.iss.itreasury.loan.mywork.dao;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Constant.ApprovalAction;
public class LoanContractPlanChangeDao extends LoanMyWorkDao {

	///����ҵ��
	protected Collection queryCurrentWork(
			LoanMyWorkInfo loanMyWorkInfo) throws IException {
		Vector v_Return = new Vector();

		StringBuffer sbSQL = new StringBuffer("");
		sbSQL.append("select ar.*,vl.* ");
		sbSQL.append(" from sys_approvalrecord ar,V_LOAN_CONTRACTPLANMODIFY vl");
		sbSQL.append(" where ar.moduleid = " + Constant.ModuleType.LOAN);
		sbSQL.append(" and ar.OfficeID = " + loanMyWorkInfo.getOfficeID());
		sbSQL.append(" and ar.currencyid = " + loanMyWorkInfo.getCurrencyID());	
		sbSQL.append(" and ar.transtypeid = vl.loanSubTypeId ");
		sbSQL.append(" and ar.transId = vl.Id  and ar.actionId = "+ApprovalAction.CONTRACT_PLAN);
		sbSQL.append(" and ar.approvalentryid in (");
		sbSQL.append(loanMyWorkInfo.getApprovalEntryIDs() + ")");
		sbSQL.append(" order by ar.id desc ");
		System.out.println("����ҵ��-��ִͬ�мƻ����SQL��="+sbSQL+"\n");

		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();

			while (transRS.next()) {
				LoanMyWorkInfo workInfo = new LoanMyWorkInfo();

				workInfo.setId(transRS.getLong("Id"));
//				workInfo.setCode(transRS.getString("code"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setContractID(transRS.getLong("contractid"));
				workInfo.setContractCode(transRS.getString("contractcode"));
				// liuguang 2007-11-13 ���Ӻ�ͬ������ʾ
				workInfo.setAmount(transRS.getDouble("amount"));
				// end
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
//				workInfo.setStartDate(transRS.getTimestamp("startdate"));//
//				workInfo.setEndDate(transRS.getTimestamp("enddate"));//
				workInfo.setStatusID(transRS.getLong("statusId"));
				workInfo.setInputUserID(transRS.getLong("inputuserId"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));//
				workInfo.setLoanTypeId(transRS.getLong("loanTypeId"));
				workInfo.setLoanSubTypeId(transRS.getLong("loanSubTypeId"));
				workInfo.setLoanSubTypeName(transRS
						.getString("loanSubTypeName"));
				workInfo.setBorrowClientId(transRS.getLong("borrowclientid"));
				workInfo.setBorrowClientName(transRS
						.getString("borrowClientName"));
				
//				workInfo.setActionID(transRS.getLong("actionId"));
//				workInfo.setActionName(transRS.getString("actionName"));
				
				workInfo.setContractChangeReason(transRS.getString("reason"));///���ԭ��
				workInfo.setContractPlanVersion(transRS.getLong("planversion"));//�ƻ��汾

				workInfo.setUserID(loanMyWorkInfo.getUserID());// �����˵�id
				workInfo.setApprovalEntryID(transRS.getLong("ApprovalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));

				// ��Ӧ������������Ĵ�����Ϣ
				workInfo
						.setInutWorkInfo((InutApprovalWorkInfo) loanMyWorkInfo
								.getWorkList().get(
										String.valueOf(workInfo
												.getApprovalEntryID())));

				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}	
	
	//�Ѱ�ҵ��
	protected void getFinishedWorkSql(LoanMyWorkInfo loanMyWorkInfo) {
		m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.* ,re.approvalentryid approvalEntryID, \n ");
		m_sbSelect
				.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTPLANMODIFY vl \n");
		m_sbFrom.append("  ,(SELECT max(his.id) hisid from v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTPLANMODIFY vl \n");
		m_sbFrom.append("  WHERE his.entry_id = re.approvalentryid  and re.transid = vl.Id   \n");
		m_sbFrom.append("  and re.transtypeid =  vl.loanSubTypeId \n");
		m_sbFrom.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID() + " \n");
		m_sbFrom.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID() + " \n");
		m_sbFrom.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID() + " \n");
		m_sbFrom.append(" and re.actionId = "+ApprovalAction.CONTRACT_PLAN+" \n");
		m_sbFrom.append(" and (vl.inputuserid = '"+loanMyWorkInfo.getUserID()+"' or his.caller='"+loanMyWorkInfo.getUserID()+"') \n");
		m_sbFrom.append("  group by his.entry_id ) maxhis\n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		m_sbWhere.append(" and re.transid = vl.Id \n");
		m_sbWhere.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID() + " \n");
		m_sbWhere.append(" and (vl.inputuserid = '"+loanMyWorkInfo.getUserID()+"' or his.caller='"+loanMyWorkInfo.getUserID()+"') \n");
		m_sbWhere.append(" and re.actionId = "+ApprovalAction.CONTRACT_PLAN+" \n");
		m_sbWhere.append(" and re.transtypeid =  vl.loanSubTypeId ");
		m_sbWhere.append(" and maxhis.hisid = his.id ");
		System.out.println("�Ѱ�ҵ��-��ִͬ�мƻ����sbSQL:\n"
				+m_sbSelect+"\n"+m_sbFrom+"\n"+m_sbWhere+"\n");
		m_sbOrderBy = new StringBuffer();
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = loanMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by vl.loanTypeId " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by vl.loanSubTypeId " + strDesc);
					break;
//				case 3:
//					m_sbOrderBy.append(" \n order by vl.actionId " + strDesc);
//					break;
				case 4:
					m_sbOrderBy.append(" \n order by vl.borrowclientid " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by vl.contractcode " + strDesc);
					break;
//				case 6:
//					m_sbOrderBy.append(" \n order by vl.code " + strDesc);
//					break;
				case 7:
					m_sbOrderBy.append(" \n order by his.owner " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by vl.inputuserId " + strDesc);
					break;
				case 9:
					m_sbOrderBy.append(" \n order by vl.inputdate " + strDesc);
					break;
			}	
		}
	}

	protected void getRefuseWorkSql(LoanMyWorkInfo loanMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct vl.*,re.transtypeid transtypeid,re.transid transid \n ");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,v_loan_contractplanmodify vl \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = vl.id \n");

		m_sbWhere.append(" and  re.officeid="+loanMyWorkInfo.getOfficeID()+" \n");

		m_sbWhere.append(" and re.currencyid="+loanMyWorkInfo.getCurrencyID()+" \n");

		m_sbWhere.append(" and re.moduleid="+loanMyWorkInfo.getModuleID()+" \n");

		m_sbWhere.append("  and re.transtypeid=  vl.loanSubTypeId \n");

		m_sbWhere.append(" and vl.statusid ="+loanMyWorkInfo.getStatusID()+" \n");

		m_sbWhere.append(" and vl.inputuserid = "+loanMyWorkInfo.getUserID()+" \n");
		m_sbOrderBy = new StringBuffer();
		System.out.println("^^^^^^^^^^^"+m_sbSelect);
		System.out.println("^^^^^^^^^^^"+m_sbFrom);
		System.out.println("^^^^^^^^^^^"+m_sbWhere);
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = loanMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by vl.loanTypeId " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by vl.loanSubTypeId " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by vl.actionId " + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by vl.borrowclientid " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by vl.contractcode " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by vl.code " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by vl.inputuserId " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by vl.inputdate " + strDesc);
					break;
			}	
		}
	}

	protected Collection queryWaitDealWithWork(LoanMyWorkInfo loanMyWorkInfo) throws IException {
		Vector v_Return = new Vector();

		StringBuffer sbSQL = new StringBuffer("");
		sbSQL.append("select vl.* ");
		sbSQL.append(" from v_loan_contractplanmodify vl ");
		sbSQL.append(" where vl.officeID = " + loanMyWorkInfo.getOfficeID());
		sbSQL.append(" and vl.currencyid = " + loanMyWorkInfo.getCurrencyID());	
		sbSQL.append(" and vl.inputuserid = " + loanMyWorkInfo.getUserID());
		sbSQL.append(" and vl.statusId = " + LOANConstant.ContractStatus.SAVE);		
		sbSQL.append(" order by vl.INPUTDATE desc ");

		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();

			while (transRS.next()) {
				LoanMyWorkInfo workInfo = new LoanMyWorkInfo();

				workInfo.setId(transRS.getLong("Id"));
				//workInfo.setCode(transRS.getString("code"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setContractID(transRS.getLong("contractid"));
				workInfo.setAmount(transRS.getDouble("amount"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				//workInfo.setStartDate(transRS.getTimestamp("startdate"));
				//workInfo.setEndDate(transRS.getTimestamp("enddate"));
				workInfo.setStatusID(transRS.getLong("statusId"));
				workInfo.setInputUserID(transRS.getLong("inputuserId"));
				workInfo.setContractCode(transRS.getString("contractcode"));
				workInfo.setLoanTypeId(transRS.getLong("loanTypeId"));
				workInfo.setLoanSubTypeId(transRS.getLong("loanSubTypeId"));
				workInfo.setBorrowClientId(transRS.getLong("borrowclientid"));
				workInfo.setLoanSubTypeName(transRS.getString("loanSubTypeName"));
				workInfo.setBorrowClientName(transRS.getString("borrowClientName"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setContractChangeReason(transRS.getString("reason"));
				workInfo.setContractPlanVersion(transRS.getLong("planVersion"));
			v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}
	
	//ȡ������
	protected void getCancelApprovalSql(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vl.*, re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId");
		
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sys_approvalrecord re,v_loan_contractplanmodify vl");
		
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vl.Id");
		m_sbWhere.append(" and re.officeid = " + loanMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and re.currencyid = " + loanMyWorkInfo.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + loanMyWorkInfo.getModuleID());
		m_sbWhere.append(" and re.actionId = " + ApprovalAction.CONTRACT_PLAN);
		m_sbWhere.append(" and re.transtypeid = vl.loanSubTypeId");
		m_sbWhere.append(" and re.lastappuserid = " + loanMyWorkInfo.getUserID());
		m_sbWhere.append(" and re.statusid = " + SETTConstant.RecordStatus.VALID);
		m_sbWhere.append(" and vl.statusid = " + LOANConstant.LoanStatus.CHECK);
		
		// order by
		m_sbOrderBy = new StringBuffer();
		
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		
		long lOrderField = loanMyWorkInfo.getOrderField();
		
		if (lOrderField > 0) {
			switch ((int) lOrderField) {
			case 1:
				m_sbOrderBy.append(" order by vl.loanTypeId " + strDesc);
				break;
			case 2:
				m_sbOrderBy.append(" order by vl.loanSubTypeId " + strDesc);
				break;
			case 3:
				m_sbOrderBy.append(" order by vl.borrowclientid " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" order by vl.contractcode " + strDesc);
				break;
			case 5:
				m_sbOrderBy.append(" order by vl.inputuserId " + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" order by vl.inputdate " + strDesc);
				break;
			default:
				m_sbOrderBy.append(" order by approvalrecordId  desc" );
				break;	
			}
		}else{
			m_sbOrderBy.append(" order by approvalrecordId desc" );
		}
	}

}
