package com.iss.itreasury.bill.mywork.dao;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo;
import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class BillTransDiscountActonDao extends BillMyWorkDao {
	//取消审批业务SQL
	protected void getCancelApprovalSql(BillMyWorkInfo billMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		//审批流表
		m_sbSelect.append(" sappr.*,");
		//交易表
		//m_sbSelect.append(" btran.*");
		m_sbSelect.append(" btran.id applyid,btran.sapplycode lApplyCode,btran.ninorout inOrOut,btran.MLOANAMOUNT transAmount,btran.dtdiscountdate discountDate,btran.nborrowclientid clientID,btran.ndiscounttypeid discountTypeID,btran.nbillstatus billStatus,btran.DTINPUTDATE inputDate,btran.NINPUTUSERID inputUserID");
		//历史表
		//m_sbSelect.append(" ,his.stepname stepName,his.modelname wfDefineName");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SYS_APPROVALRECORD sappr,cra_loanform btran");
		
		m_sbWhere = new StringBuffer();
		//关联
		m_sbWhere.append(" sappr.transid = btran.id");
		//m_sbWhere.append(" and his.entry_id = sappr.approvalentryid");
		//审批记录表信息
		m_sbWhere.append(" and sappr.moduleid = " + billMyWorkInfo.getModuleID());
		m_sbWhere.append(" and sappr.officeid = " + billMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + billMyWorkInfo.getCurrencyID());
		m_sbWhere.append(" and sappr.transtypeid = " + BILLConstant.TraceModule.DRAFT);
		m_sbWhere.append(" and sappr.actionid = " + BILLConstant.DraftOperationType.DiscountDraftPro);
		m_sbWhere.append(" and sappr.lastappuserid = " + billMyWorkInfo.getUserID());
		m_sbWhere.append(" and sappr.statusId  = " + Constant.RecordStatus.VALID);		
		//交易表信息
		m_sbWhere.append(" and btran.nbillstatus = " + BILLConstant.TransctionStatus.APPROVALED);
		//历史交易表
		//m_sbWhere.append(" and his.stepname='审批完成'");
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = billMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = billMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				default:
					m_sbOrderBy.append(" \n order by sappr.approvalentryid desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by sappr.approvalentryid desc" );
		}
		
	}
//	已办业务SQL
	protected void getFinishedWorkSql(BillMyWorkInfo billMyWorkInfo) {
		// TODO Auto-generated method stub
		m_sbSelect = new StringBuffer();
	
		m_sbSelect.append(" his.stepname stepName,his.modelname wfDefineName,his.owner,his.entry_id entryID,his.action_code actionCode,his.step_code stepCode,sappr.*, apply.id applyid,apply.sapplycode lApplyCode,apply.ninorout inOrOut,apply.MLOANAMOUNT transAmount,apply.dtdiscountdate discountDate,apply.nborrowclientid clientID,apply.ndiscounttypeid discountTypeID,apply.nbillstatus billStatus,apply.DTINPUTDATE inputDate,apply.NINPUTUSERID inputUserID \n ");
		// from
		m_sbFrom = new StringBuffer();
		//m_sbFrom.append("  v_os_histroystep his,(select * from cra_loanform cloan where cloan.nbillstatus in( "+ BILLConstant.TransctionStatus.APPROVALED+","+BILLConstant.TransctionStatus.SUBMIT+","+BILLConstant.TransctionStatus.APPROVALING+")) apply,SYS_APPROVALRECORD sappr \n");
		m_sbFrom.append("  v_os_histroystep his,(select cloan.* from cra_loanform cloan where cloan.nstatusid = "+LOANConstant.LoanStatus.CHECK+") apply,SYS_APPROVALRECORD sappr \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = sappr.approvalentryid \n");
		m_sbWhere.append(" and sappr.moduleid = " + billMyWorkInfo.getModuleID() +"\n");
		m_sbWhere.append(" and sappr.currencyid = " + billMyWorkInfo.getCurrencyID()+"\n");
		m_sbWhere.append(" and sappr.officeid = " + billMyWorkInfo.getOfficeID() + " \n");
		m_sbWhere.append(" and sappr.transid = apply.id" + " \n");
		m_sbWhere.append(" and his.owner = '"+billMyWorkInfo.getUserID()+"' \n");
		
		m_sbWhere.append(" and sappr.transtypeid = " + BILLConstant.TraceModule.DRAFT + " \n");
		m_sbWhere.append(" and sappr.actionid = " + BILLConstant.DraftOperationType.DiscountDraftPro+" \n");
		//m_sbWhere.append(" and sappr.approvalentryid in (" + billMyWorkInfo.getApprovalEntryIDs() + ")");


		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by sappr.id desc" );
	}
	//拒绝业务SQL
	protected void getRefuseWorkSql(BillMyWorkInfo billMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		//审批流表
		m_sbSelect.append(" distinct sappr.officeid ,sappr.currencyid ,sappr.moduleid ,sappr.transtypeid ,sappr.actionid ,sappr.transid ,sappr.linkurl ,sappr.statusid ,sappr.nextlevel ,");
		//交易表
		//m_sbSelect.append(" btran.*");
		m_sbSelect.append(" btran.id applyid,btran.sapplycode lApplyCode,btran.ninorout inOrOut,btran.MLOANAMOUNT transAmount,btran.dtdiscountdate discountDate,btran.nborrowclientid clientID,btran.ndiscounttypeid discountTypeID,btran.nbillstatus billStatus,btran.DTINPUTDATE inputDate,btran.NINPUTUSERID inputUserID");
		//历史表
		//m_sbSelect.append(" his.stepname stepName,his.modelname wfDefineName");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" SYS_APPROVALRECORD sappr,cra_loanform btran");
		
		m_sbWhere = new StringBuffer();
		//关联
		m_sbWhere.append(" sappr.transid = btran.id");
		//m_sbWhere.append(" and his.entry_id = sappr.approvalentryid");
		//审批记录表信息
		m_sbWhere.append(" and sappr.moduleid = " + billMyWorkInfo.getModuleID());
		m_sbWhere.append(" and sappr.officeid = " + billMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + billMyWorkInfo.getCurrencyID());
		m_sbWhere.append(" and sappr.transtypeid = " + BILLConstant.TraceModule.DRAFT);
		m_sbWhere.append(" and sappr.actionid = " + BILLConstant.DraftOperationType.DiscountDraftPro);
		//m_sbWhere.append(" and sappr.lastappuserid = " + billMyWorkInfo.getUserID());
		m_sbWhere.append(" and sappr.statusId  = " + Constant.RecordStatus.INVALID);		
		//交易表信息
		m_sbWhere.append(" and btran.nbillstatus = " + BILLConstant.TransctionStatus.SUBMIT);
		//历史交易表
		//m_sbWhere.append(" and his.stepname='审批完成'");
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = billMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = billMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				default:
					m_sbOrderBy.append(" \n order by btran.id desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by btran.id desc" );
		}

		
	}
	//待处理业务SQL
	protected Collection queryCurrentWork(BillMyWorkInfo billMyWorkInfo) throws IException {
		// TODO Auto-generated method stub
		Vector v_Return = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("select sappr.*, apply.id transid,apply.sapplycode,apply.ninorout,apply.MLOANAMOUNT,apply.dtdiscountdate,apply.nborrowclientid,apply.ndiscounttypeid,apply.nbillstatus,apply.DTINPUTDATE,apply.NINPUTUSERID");
		sbSQL.append(" from (select *");
		sbSQL.append(" from cra_loanform cloan" );
		sbSQL.append(" where cloan.nbillstatus = " + BILLConstant.TransctionStatus.APPROVALING);
		sbSQL.append(" ) apply,");		//
		sbSQL.append(" SYS_APPROVALRECORD sappr");			//
		
		sbSQL.append(" where sappr.moduleid = " + billMyWorkInfo.getModuleID());
		sbSQL.append(" and sappr.currencyid = " + billMyWorkInfo.getCurrencyID());
		sbSQL.append(" and sappr.officeid = " + billMyWorkInfo.getOfficeID());
		sbSQL.append(" and sappr.transid = apply.id");
		sbSQL.append(" and sappr.transtypeid = " + BILLConstant.TraceModule.DRAFT);
		sbSQL.append(" and sappr.actionid = " + BILLConstant.DraftOperationType.DiscountDraftPro);
		sbSQL.append(" and sappr.approvalentryid in (" + billMyWorkInfo.getApprovalEntryIDs() + ")");
		
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();

			while (transRS.next()) {
				BillMyWorkInfo workInfo = new BillMyWorkInfo();

				//审批关联信息
				workInfo.setId(transRS.getLong("transid"));
				workInfo.setOfficeID(transRS.getLong("OFFICEID"));
				workInfo.setCurrencyID(transRS.getLong("CURRENCYID"));
				//workInfo.setActionName(transRS.getString("transactionTypeName"));
				workInfo.setApprovalEntryID(transRS.getLong("approvalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));
				workInfo.setUserID(billMyWorkInfo.getUserID());
				workInfo.setStatusID(transRS.getLong("nbillstatus"));
				
				//票据业务信息

				workInfo.setLApplyCode(transRS.getString("sapplycode"));
			       
			    workInfo.setInOrOut(transRS.getLong("ninorout"));
			    workInfo.setTransAmount(transRS.getDouble("MLOANAMOUNT"));
			    workInfo.setDiscountDate(transRS.getTimestamp("dtdiscountdate"));
			    workInfo.setClientID(transRS.getLong("nborrowclientid"));
			    workInfo.setDiscountTypeID(transRS.getLong("ndiscounttypeid"));
			    workInfo.setBillStatus(transRS.getLong("nbillstatus"));
				workInfo.setInputUserID(transRS.getLong("NINPUTUSERID"));
				//workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setInputDate(transRS.getTimestamp("DTINPUTDATE"));

				//对应的审批流引擎的待办信息
				workInfo.setInutWorkInfo((InutApprovalWorkInfo)billMyWorkInfo.getWorkList().get(String.valueOf(workInfo.getApprovalEntryID())));

				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}
	//待提交业务
	protected Collection queryWaitDealWithWork(BillMyWorkInfo illMyWorkInfo) throws IException {
		// TODO Auto-generated method stub
		return null;
	}

}
