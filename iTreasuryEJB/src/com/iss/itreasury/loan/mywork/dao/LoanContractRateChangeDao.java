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

public class LoanContractRateChangeDao extends LoanMyWorkDao {	

	protected Collection queryCurrentWork(
			LoanMyWorkInfo loanMyWorkInfo) throws IException {
		Vector v_Return = new Vector();
		String flag = loanMyWorkInfo.getQSingleOrBatch();
		StringBuffer sbSQL = new StringBuffer("");
//		sbSQL.append("select ar.*,vl.* ");
//		sbSQL.append(" from sys_approvalrecord ar,V_LOAN_CONTRACTRATEADJUST vl");
//		sbSQL.append(" where ar.moduleid = " + Constant.ModuleType.LOAN);
//		sbSQL.append(" and ar.OfficeID = " + loanMyWorkInfo.getOfficeID());
//		sbSQL.append(" and ar.currencyid = " + loanMyWorkInfo.getCurrencyID());	
//		sbSQL.append(" and ar.transtypeid = vl.loanSubTypeId ");
//		sbSQL.append(" and ar.transId = vl.Id  and ar.actionId = "+ApprovalAction.INTEREST_ADJUST);
//		sbSQL.append(" and ar.approvalentryid in (");
//		sbSQL.append(loanMyWorkInfo.getApprovalEntryIDs() + ")");
//		sbSQL.append(" order by ar.id desc ");
		if(flag.equals("batch")){
		loanMyWorkInfo.getLoanSubTypeId();
		sbSQL.append("select ar.*,bl.*,u.sname ");
		sbSQL.append(" from sys_approvalrecord ar,loan_rateadjust_batch bl,userinfo u");
		sbSQL.append(" where ar.moduleid = " + Constant.ModuleType.LOAN);
		sbSQL.append(" and ar.OfficeID = " + loanMyWorkInfo.getOfficeID());
		sbSQL.append(" and ar.currencyid = " + loanMyWorkInfo.getCurrencyID());	
		sbSQL.append(" and ar.transtypeid = bl.nsubtypeid and bl.inputuserid=u.id ");
		sbSQL.append(" and ar.transId = bl.Id and ar.actionId = "+ApprovalAction.INTEREST_ADJUST_BATCH);
		sbSQL.append(" and ar.approvalentryid in (");
		sbSQL.append(loanMyWorkInfo.getApprovalEntryIDs() + ")");
		sbSQL.append(" order by ar.id desc ");
		}else{
			sbSQL.append("select ar.*,vl.* ");
			sbSQL.append(" from sys_approvalrecord ar,V_LOAN_CONTRACTRATEADJUST vl");
			sbSQL.append(" where ar.moduleid = " + Constant.ModuleType.LOAN);
			sbSQL.append(" and ar.OfficeID = " + loanMyWorkInfo.getOfficeID());
			sbSQL.append(" and ar.currencyid = " + loanMyWorkInfo.getCurrencyID());	
			sbSQL.append(" and ar.transtypeid = vl.loanSubTypeId ");
			sbSQL.append(" and ar.transId = vl.Id  and ar.actionId = "+ApprovalAction.INTEREST_ADJUST);
			sbSQL.append(" and ar.approvalentryid in (");
			sbSQL.append(loanMyWorkInfo.getApprovalEntryIDs() + ")");
			sbSQL.append(" order by ar.id desc ");
		}
		System.out.println("待办业务-合同执行利率变更===="+sbSQL);
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			if(flag.equals("batch")){
				while (transRS.next()) {
					LoanMyWorkInfo workInfo = new LoanMyWorkInfo();

					//workInfo.setId(transRS.getLong("Id"));
					workInfo.setCurrencyID(transRS.getLong("currencyid"));
					workInfo.setOfficeID(transRS.getLong("officeid"));
					workInfo.setContractID(transRS.getLong("transid"));//实际所存为批次ID,待改造
					//workInfo.setContractCode(transRS.getString("contractcode"));
					//workInfo.setAmount(transRS.getDouble("amount"));
					workInfo.setInputDate(transRS.getTimestamp("inputdate"));
					//workInfo.setStartDate(transRS.getTimestamp("startdate"));
					//workInfo.setEndDate(transRS.getTimestamp("enddate"));
					workInfo.setStatusID(transRS.getLong("status"));
					workInfo.setInputUserID(transRS.getLong("inputuserid"));
					workInfo.setInputUserName(transRS.getString("sname"));
					//workInfo.setLoanTypeId(transRS.getLong("loanTypeId"));
					//workInfo.setLoanSubTypeId(transRS.getLong("loanSubTypeId"));
					//workInfo.setLoanSubTypeName(transRS.getString("loanSubTypeName"));
					//workInfo.setBorrowClientId(transRS.getLong("borrowclientid"));
					//workInfo.setBorrowClientName(transRS.getString("borrowClientName"));
					workInfo.setActionID(transRS.getLong("actionId"));
					workInfo.setBatchid(transRS.getLong("batchno"));
					//workInfo.setActionName(transRS.getString("actionName"));
					////新增
					//workInfo.setReason(transRS.getString("reason"));
					//workInfo.setDtValiDate(transRS.getTimestamp("dtvalidate"));
					//workInfo.setRate(transRS.getDouble("rate"));
					////////
					workInfo.setUserID(loanMyWorkInfo.getUserID());// 待办人的id
					workInfo.setApprovalEntryID(transRS.getLong("ApprovalEntryID"));
					workInfo.setLinkUrl(transRS.getString("LinkUrl"));

					// 对应的审批流引擎的待办信息
					workInfo
							.setInutWorkInfo((InutApprovalWorkInfo) loanMyWorkInfo
									.getWorkList().get(
											String.valueOf(workInfo
													.getApprovalEntryID())));

					v_Return.add(workInfo);
				}
			}else{
				while (transRS.next()) {
					LoanMyWorkInfo workInfo = new LoanMyWorkInfo();

					workInfo.setId(transRS.getLong("Id"));
					workInfo.setCurrencyID(transRS.getLong("currencyid"));
					workInfo.setOfficeID(transRS.getLong("officeid"));
					workInfo.setContractID(transRS.getLong("contractid"));
					workInfo.setContractCode(transRS.getString("contractcode"));
					//workInfo.setAmount(transRS.getDouble("amount"));
					workInfo.setInputDate(transRS.getTimestamp("inputdate"));
					//workInfo.setStartDate(transRS.getTimestamp("startdate"));
					//workInfo.setEndDate(transRS.getTimestamp("enddate"));
					workInfo.setStatusID(transRS.getLong("statusId"));
					workInfo.setInputUserID(transRS.getLong("inputuserId"));
					workInfo.setInputUserName(transRS.getString("inputUserName"));
					workInfo.setLoanTypeId(transRS.getLong("loanTypeId"));
					workInfo.setLoanSubTypeId(transRS.getLong("loanSubTypeId"));
					workInfo.setLoanSubTypeName(transRS
							.getString("loanSubTypeName"));
					workInfo.setBorrowClientId(transRS.getLong("borrowclientid"));
					workInfo.setBorrowClientName(transRS
							.getString("borrowClientName"));
//					workInfo.setActionID(transRS.getLong("actionId"));
//					workInfo.setActionName(transRS.getString("actionName"));
					////新增
					workInfo.setReason(transRS.getString("reason"));
					workInfo.setDtValiDate(transRS.getTimestamp("dtvalidate"));
					workInfo.setRate(transRS.getDouble("rate"));
					////////
					workInfo.setUserID(loanMyWorkInfo.getUserID());// 待办人的id
					workInfo.setApprovalEntryID(transRS.getLong("ApprovalEntryID"));
					workInfo.setLinkUrl(transRS.getString("LinkUrl"));

					// 对应的审批流引擎的待办信息
					workInfo
							.setInutWorkInfo((InutApprovalWorkInfo) loanMyWorkInfo
									.getWorkList().get(
											String.valueOf(workInfo
													.getApprovalEntryID())));

					v_Return.add(workInfo);
				}
			}
			
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}	
	
	protected void getFinishedWorkSql(LoanMyWorkInfo loanMyWorkInfo) {
		if(loanMyWorkInfo.getQSingleOrBatch().equals("batch"))
		{
			m_sbSelect = new StringBuffer();
			m_sbSelect
					.append(" vl.id as batchid ,vl.inputdate,re.approvalentryid approvalEntryID,u.sname as inputusername, \n ");
			m_sbSelect
					//.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.owner, \n ");
			.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.CALLER owner, \n ");
			m_sbSelect
					.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
			// from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,loan_rateadjust_batch vl,userinfo u \n");
			m_sbFrom.append(" ,( \n");
			m_sbFrom.append("select max(his.id) maxid, re.approvalentryid \n");
			m_sbFrom.append(" from \n");
			m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,loan_rateadjust_batch vl,userinfo u \n");
			m_sbFrom.append(" where \n");
			m_sbFrom.append("  his.entry_id = re.approvalentryid \n");
			m_sbFrom.append(" and re.transid = vl.Id \n");
			m_sbFrom.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID() + " \n");
			m_sbFrom.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID() + " \n");
			m_sbFrom.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID() + " \n");
			//m_sbFrom.append(" and his.owner = '"+loanMyWorkInfo.getUserID()+"' \n");
			m_sbFrom.append(" and re.actionId = "+ApprovalAction.INTEREST_ADJUST_BATCH+" \n");
			m_sbFrom.append(" and re.transtypeid =  vl.NSUBTYPEID ");
			m_sbFrom.append(" and vl.INPUTUSERID = u.id ");
			m_sbFrom.append(" and (vl.INPUTUSERID = " + loanMyWorkInfo.getUserID()+" or his.caller = " + loanMyWorkInfo.getUserID() + ") \n");
			m_sbFrom.append(" group by re.approvalentryid ) maxvoshi \n");
		
			
			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
			m_sbWhere.append(" and re.transid = vl.Id \n");
			m_sbWhere.append(" and maxvoshi.maxid = his.id \n");
			m_sbWhere.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID() + " \n");
			m_sbWhere.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID() + " \n");
			m_sbWhere.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID() + " \n");
			m_sbWhere.append(" and re.actionId = "+ApprovalAction.INTEREST_ADJUST_BATCH+" \n");
			m_sbWhere.append(" and re.transtypeid =  vl.NSUBTYPEID ");
			m_sbWhere.append(" and vl.INPUTUSERID = u.id ");
			m_sbWhere.append(" and (vl.INPUTUSERID = " + loanMyWorkInfo.getUserID()+" or his.caller = " + loanMyWorkInfo.getUserID() + ") \n");
		}
		else
		{
			m_sbSelect = new StringBuffer();
			m_sbSelect
					.append(" vl.* ,re.approvalentryid approvalEntryID, \n ");
			m_sbSelect
					//.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.owner, \n ");
			.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
			m_sbSelect
					.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
			// from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTRATEADJUST vl \n");
			m_sbFrom.append(" ,(\n");
			m_sbFrom.append(" select max(his.id) maxid, re.approvalentryid \n");
			m_sbFrom.append(" from \n");
			m_sbFrom.append(" v_os_histroystep his,sys_approvalrecord re,V_LOAN_CONTRACTRATEADJUST vl \n");
			m_sbFrom.append(" where \n");
			m_sbFrom.append("  his.entry_id = re.approvalentryid \n");
			m_sbFrom.append(" and re.transid = vl.Id \n");
			m_sbFrom.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID() + " \n");
			m_sbFrom.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID() + " \n");
			m_sbFrom.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID() + " \n");
			//m_sbFrom.append(" and his.owner = '"+loanMyWorkInfo.getUserID()+"' \n");
			m_sbFrom.append(" and (vl.INPUTUSERID = " + loanMyWorkInfo.getUserID()+" or his.caller = " + loanMyWorkInfo.getUserID() + ") \n");
			m_sbFrom.append(" and re.actionId = "+ApprovalAction.INTEREST_ADJUST+" \n");
			m_sbFrom.append(" and re.transtypeid =  vl.loanSubTypeId ");
			m_sbFrom.append(" group by re.approvalentryid ");
			m_sbFrom.append(" ) maxvoshi \n");
			m_sbWhere = new StringBuffer();
			m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
			m_sbWhere.append(" and re.transid = vl.Id \n");
			m_sbWhere.append(" and maxvoshi.maxid = his.id \n");
			m_sbWhere.append(" and re.officeid=" + loanMyWorkInfo.getOfficeID() + " \n");
			m_sbWhere.append(" and re.currencyid=" + loanMyWorkInfo.getCurrencyID() + " \n");
			m_sbWhere.append(" and re.moduleid=" + loanMyWorkInfo.getModuleID() + " \n");
			m_sbWhere.append(" and (vl.INPUTUSERID = " + loanMyWorkInfo.getUserID()+" or his.caller = " + loanMyWorkInfo.getUserID() + ") \n");
			m_sbWhere.append(" and re.actionId = "+ApprovalAction.INTEREST_ADJUST+" \n");
			m_sbWhere.append(" and re.transtypeid =  vl.loanSubTypeId ");
		}

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
						m_sbOrderBy.append(" \n order by his.owner " + strDesc);
						break;
					case 8:
						m_sbOrderBy.append(" \n order by vl.inputuserId " + strDesc);
						break;
					case 9:
						m_sbOrderBy.append(" \n order by vl.inputdate " + strDesc);
						break;
					case 10:
						m_sbOrderBy.append(" \n order by vl.dtvalidate " + strDesc);
						break;
					default: //add by dwj 20110711
						m_sbOrderBy.append(" \n order by re.approvalentryid desc" );
						break;
				}	
		}
		else //add by dwj 20110711
		{
			m_sbOrderBy.append(" \n order by re.approvalentryid desc" );
		} //end add by dwj 20110711
	}

	protected void getRefuseWorkSql(LoanMyWorkInfo loanMyWorkInfo) throws IException {
		if(loanMyWorkInfo.getQSingleOrBatch().equals("batch")){
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" distinct re.transtypeid as loanSubTypeId,re.transid transid,vl.id as batchid,vl.inputdate,u.sname as inputusername,l.name as loanSubTypeName \n ");
			//		 from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,loan_rateadjust_batch vl,userinfo u,loan_loantypesetting l \n");
			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append("  re.transid = vl.id and l.id=re.transtypeid and u.id=vl.inputuserid \n");

			m_sbWhere.append(" and  re.officeid="+loanMyWorkInfo.getOfficeID()+" \n");

			m_sbWhere.append(" and re.currencyid="+loanMyWorkInfo.getCurrencyID()+" \n");

			m_sbWhere.append(" and re.moduleid="+loanMyWorkInfo.getModuleID()+" \n");

			m_sbWhere.append(" and re.transtypeid=  vl.nSubTypeId \n");

			//以下两行,将拒绝的状态限制去掉.20101130暂时放开条件,关系到是查看拒绝还是被拒绝的问题,注释掉第二行,说明是显示我被拒绝的业务
			m_sbWhere.append(" and re.actionId="+ApprovalAction.INTEREST_ADJUST_BATCH+" and vl.status ="+loanMyWorkInfo.getStatusID()+" \n");
			
			//m_sbWhere.append(" and re.actionId="+ApprovalAction.INTEREST_ADJUST_BATCH+" \n");
			
			m_sbWhere.append(" and vl.inputuserid = "+loanMyWorkInfo.getUserID()+" \n");
		}else{
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" distinct re.transtypeid transtypeid,re.transid transid,vl.* \n ");
			//		 from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,v_loan_contractrateadjust vl \n");
			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append("  re.transid = vl.id \n");

			m_sbWhere.append(" and  re.officeid="+loanMyWorkInfo.getOfficeID()+" \n");

			m_sbWhere.append(" and re.currencyid="+loanMyWorkInfo.getCurrencyID()+" \n");

			m_sbWhere.append(" and re.moduleid="+loanMyWorkInfo.getModuleID()+" \n");

			m_sbWhere.append(" and re.actionid="+ApprovalAction.INTEREST_ADJUST+"  and re.transtypeid=  vl.loanSubTypeId \n");

			m_sbWhere.append(" and vl.statusid ="+loanMyWorkInfo.getStatusID()+" \n");

			m_sbWhere.append(" and vl.inputuserid = "+loanMyWorkInfo.getUserID()+" \n");
		}
		
		 
	
		
		System.out.println("****"+m_sbSelect+m_sbFrom+m_sbWhere);
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		
		long lOrderField = loanMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by bl.nsubtypeid " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by bl.id " + strDesc);
					break;
	         
				case 3:
					m_sbOrderBy.append(" \n order by bl.inputuserid " + strDesc);
					break;	
				case 4:
					m_sbOrderBy.append(" \n order by bl.inputdate " + strDesc);
					break;
			}	
		}
	}

	protected Collection queryWaitDealWithWork(LoanMyWorkInfo loanMyWorkInfo) throws IException {
		Vector v_Return = new Vector();
		StringBuffer sbSQL = new StringBuffer("");
		if(loanMyWorkInfo.getQSingleOrBatch().equals("batch"))
		{
			/*
			sbSQL.append("select bl.*,u.sname ");
			sbSQL.append(" from v_loan_contractrateadjust vl ");
			sbSQL.append(" where vl.officeID = " + loanMyWorkInfo.getOfficeID());
			sbSQL.append(" and vl.currencyid = " + loanMyWorkInfo.getCurrencyID());	
			sbSQL.append(" and vl.inputuserid = " + loanMyWorkInfo.getUserID());
			sbSQL.append(" and vl.statusId = " + LOANConstant.InterestRateSettingStatus.SUBMIT);		
			sbSQL.append(" order by vl.INPUTDATE desc ");
			*/
			sbSQL.append("select bl.*, u.sname, ll.name subTypeName,ll.id as subTypeID ");
			sbSQL.append(" from loan_rateadjust_batch bl, userinfo u, loan_loantypesetting ll ");
			sbSQL.append(" where bl.inputuserid = u.id ");
			sbSQL.append(" and bl.nsubtypeid = ll.id ");
			
			sbSQL.append(" and bl.OFFICEID = " + loanMyWorkInfo.getOfficeID());
			sbSQL.append(" and bl.CURRENCYID = " + loanMyWorkInfo.getCurrencyID());	
			sbSQL.append(" and bl.INPUTUSERID = " + loanMyWorkInfo.getUserID());
			sbSQL.append(" and bl.STATUS = " + LOANConstant.InterestRateSettingStatus.SUBMIT);
			System.out.println("the sql is "+sbSQL.toString());
		}
		else
		{
			sbSQL.append("select vl.* ");
			sbSQL.append(" from v_loan_contractrateadjust vl ");
			sbSQL.append(" where vl.officeID = " + loanMyWorkInfo.getOfficeID());
			sbSQL.append(" and vl.currencyid = " + loanMyWorkInfo.getCurrencyID());	
			sbSQL.append(" and vl.inputuserid = " + loanMyWorkInfo.getUserID());
			sbSQL.append(" and vl.statusId = " + LOANConstant.InterestRateSettingStatus.SUBMIT);		
			sbSQL.append(" and vl.batchid is null order by vl.INPUTDATE desc ");
			System.out.println("the sql is "+sbSQL.toString());
		}
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			
			if(loanMyWorkInfo.getQSingleOrBatch().equals("batch"))
			{
				while (transRS.next()) {
				LoanMyWorkInfo workInfo = new LoanMyWorkInfo();
				workInfo.setLoanSubTypeName(transRS.getString("subTypeName"));
				workInfo.setBatchid(transRS.getLong("id"));
				workInfo.setInputUserName(transRS.getString("sname"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				workInfo.setLoanSubTypeId(transRS.getLong("subTypeID"));
				
				
				/*
				workInfo.setId(transRS.getLong("Id"));
			//	workInfo.setCode(transRS.getString("code"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setContractID(transRS.getLong("contractid"));
			//	workInfo.setContractCode(transRS.getString("contractcode"));
			//	workInfo.setAmount(transRS.getDouble("amount"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
			//	workInfo.setStartDate(transRS.getTimestamp("startdate"));
				workInfo.setDtValiDate(transRS.getTimestamp("dtvalidate"));
				workInfo.setStatusID(transRS.getLong("statusId"));
				workInfo.setInputUserID(transRS.getLong("inputuserId"));
				workInfo.setContractCode(transRS.getString("contractcode"));
				workInfo.setLoanTypeId(transRS.getLong("loanTypeId"));
				workInfo.setLoanSubTypeId(transRS.getLong("loanSubTypeId"));
				workInfo.setBorrowClientId(transRS.getLong("borrowclientid"));
				workInfo.setLoanSubTypeName(transRS.getString("loanSubTypeName"));
				workInfo.setBorrowClientName(transRS.getString("borrowClientName"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setReason(transRS.getString("reason"));
				workInfo.setRate(transRS.getDouble("rate"));	
			//	workInfo.setActionID(transRS.getLong("actionId"));
			//	workInfo.setActionName(transRS.getString("actionName"));
				*/
				v_Return.add(workInfo);
				}
			}
			else
			{
				while (transRS.next()) {
					LoanMyWorkInfo workInfo = new LoanMyWorkInfo();
					workInfo.setId(transRS.getLong("Id"));
				//	workInfo.setCode(transRS.getString("code"));
					workInfo.setCurrencyID(transRS.getLong("currencyid"));
					workInfo.setOfficeID(transRS.getLong("officeid"));
					workInfo.setContractID(transRS.getLong("contractid"));
				//	workInfo.setContractCode(transRS.getString("contractcode"));
				//	workInfo.setAmount(transRS.getDouble("amount"));
					workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				//	workInfo.setStartDate(transRS.getTimestamp("startdate"));
					workInfo.setDtValiDate(transRS.getTimestamp("dtvalidate"));
					workInfo.setStatusID(transRS.getLong("statusId"));
					workInfo.setInputUserID(transRS.getLong("inputuserId"));
					workInfo.setContractCode(transRS.getString("contractcode"));
					workInfo.setLoanTypeId(transRS.getLong("loanTypeId"));
					workInfo.setLoanSubTypeId(transRS.getLong("loanSubTypeId"));
					workInfo.setBorrowClientId(transRS.getLong("borrowclientid"));
					workInfo.setLoanSubTypeName(transRS.getString("loanSubTypeName"));
					workInfo.setBorrowClientName(transRS.getString("borrowClientName"));
					workInfo.setInputUserName(transRS.getString("inputUserName"));
					workInfo.setReason(transRS.getString("reason"));
					workInfo.setRate(transRS.getDouble("rate"));	
				//	workInfo.setActionID(transRS.getLong("actionId"));
				//	workInfo.setActionName(transRS.getString("actionName"));
					v_Return.add(workInfo);
				}
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}
	
	
	protected void getCancelApprovalSql(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		// select
		if(loanMyWorkInfo.getQSingleOrBatch().equals("batch")){
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" vl.id batchid,vl.inputuserid,u.sname inputUserName,vl.inputdate,vl.nsubtypeid as loanSubTypeId,l.name as loanSubTypeName,re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId");
			
			// from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" sys_approvalrecord re,loan_rateadjust_batch vl,userinfo u,loan_loantypesetting l ");
			
			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" re.transid = vl.Id and u.id=vl.inputuserid and l.id=vl.nsubtypeid ");
			m_sbWhere.append(" and re.officeid = " + loanMyWorkInfo.getOfficeID());
			m_sbWhere.append(" and re.currencyid = " + loanMyWorkInfo.getCurrencyID());
			m_sbWhere.append(" and re.moduleid = " + loanMyWorkInfo.getModuleID());
			m_sbWhere.append(" and re.actionId = " + ApprovalAction.INTEREST_ADJUST_BATCH);
			m_sbWhere.append(" and re.transtypeid = vl.nSubTypeId");
			m_sbWhere.append(" and re.lastappuserid = " + loanMyWorkInfo.getUserID());
			m_sbWhere.append(" and re.statusid = " + SETTConstant.RecordStatus.VALID);
			m_sbWhere.append(" and vl.status = " + LOANConstant.LoanStatus.CHECK);
		}else{
			// select
			m_sbSelect = new StringBuffer();
			m_sbSelect.append(" vl.*, re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId");
			
			// from
			m_sbFrom = new StringBuffer();
			m_sbFrom.append(" sys_approvalrecord re,v_loan_contractrateadjust vl");
			
			// where
			m_sbWhere = new StringBuffer();
			m_sbWhere.append(" re.transid = vl.Id");
			m_sbWhere.append(" and re.officeid = " + loanMyWorkInfo.getOfficeID());
			m_sbWhere.append(" and re.currencyid = " + loanMyWorkInfo.getCurrencyID());
			m_sbWhere.append(" and re.moduleid = " + loanMyWorkInfo.getModuleID());
			m_sbWhere.append(" and re.actionId = " + ApprovalAction.INTEREST_ADJUST);
			m_sbWhere.append(" and re.transtypeid = vl.loanSubTypeId");
			m_sbWhere.append(" and re.lastappuserid = " + loanMyWorkInfo.getUserID());
			m_sbWhere.append(" and re.statusid = " + SETTConstant.RecordStatus.VALID);
			m_sbWhere.append(" and vl.statusid = " + LOANConstant.LoanStatus.CHECK);
		}
		
		
		//order by
		m_sbOrderBy = new StringBuffer();
		
		String strDesc = loanMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		
		long lOrderField = loanMyWorkInfo.getOrderField();
		
		if (lOrderField > 0) {
			switch ((int) lOrderField) {
			case 1:
				m_sbOrderBy.append(" order by vl.nSubTypeId " + strDesc);
				break;
			case 2:
				m_sbOrderBy.append(" order by vl.id " + strDesc);
				break;
			case 3:
				m_sbOrderBy.append(" order by vl.inputuserid " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" order by vl.inputdate " + strDesc);
				break;
			}
		}else{
			m_sbOrderBy.append(" order by vl.id desc" );
		}

}

}