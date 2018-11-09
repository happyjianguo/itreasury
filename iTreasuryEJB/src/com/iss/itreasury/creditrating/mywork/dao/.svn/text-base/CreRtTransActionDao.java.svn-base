package com.iss.itreasury.creditrating.mywork.dao;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.creditrating.mywork.dataentity.CreRtMyWorkInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class CreRtTransActionDao extends CreRtMyWorkDao {	

	protected Collection queryCurrentWork(
			CreRtMyWorkInfo creRtMyWorkInfo) throws IException {
		Vector v_Return = new Vector();

		StringBuffer sbSQL = new StringBuffer("");
		sbSQL.append("select ar.*,vc.* ");
		sbSQL.append(" from sys_approvalrecord ar,V_CRERT_TRANSACTION vc ");
		sbSQL.append(" where ar.moduleid = " + Constant.ModuleType.CREDITRATING);
		sbSQL.append(" and ar.OfficeID = " + creRtMyWorkInfo.getOfficeID());
		sbSQL.append(" and ar.currencyid = " + creRtMyWorkInfo.getCurrencyID());	
		sbSQL.append(" and ar.transtypeid = vc.APPROVALACTION ");
		sbSQL.append(" and ar.transId = vc.Id ");
		sbSQL.append(" and ar.approvalentryid in (");
		sbSQL.append(creRtMyWorkInfo.getApprovalEntryIDs() + ")");
		sbSQL.append(" order by ar.id desc ");

		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();

			while (transRS.next()) {
				CreRtMyWorkInfo workInfo = new CreRtMyWorkInfo();

				workInfo.setId(transRS.getLong("Id"));
				workInfo.setCode(transRS.getString("code"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setClientID(transRS.getLong("clientId"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				workInfo.setStartDate(transRS.getTimestamp("startdate"));
				workInfo.setEndDate(transRS.getTimestamp("enddate"));
				workInfo.setStatusID(transRS.getLong("statusId"));
				workInfo.setInputUserID(transRS.getLong("inputuserId"));
				workInfo.setActionName(transRS.getString("actionName"));
				workInfo.setRatingtype(transRS.getLong("ratingtype"));
				workInfo.setRatingprojectid(transRS.getLong("ratingprojectid"));
				workInfo.setRatingprojectname(transRS.getString("ratingprojectname"));
				workInfo.setRatingnumeric(transRS.getDouble("ratingnumeric"));
				workInfo.setRatingresult(transRS.getString("ratingresult"));
				workInfo.setApprovalAction(transRS.getLong("ApprovalAction"));
				workInfo.setUserID(creRtMyWorkInfo.getUserID());// 待办人的id
				workInfo.setApprovalEntryID(transRS.getLong("ApprovalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));

				// 对应的审批流引擎的待办信息
				workInfo
						.setInutWorkInfo((InutApprovalWorkInfo) creRtMyWorkInfo
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
	
	protected void getFinishedWorkSql(CreRtMyWorkInfo creRtMyWorkInfo) {
		m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vc.* ,re.approvalentryid approvalEntryID,re.id approvalrecordId, \n ");
		//update by dwj 20110917
		//m_sbSelect
				//.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.owner, \n ");
		//end update by dwj 20110917
		m_sbSelect
			.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.caller owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,V_CRERT_TRANSACTION vc, \n");
		
		//add by dwj 20111108
		m_sbFrom.append(" (select max(his.id) maxid \n");
		m_sbFrom.append(" from \n");
		m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,V_CRERT_TRANSACTION vc \n");
		m_sbFrom.append("  where his.entry_id = re.approvalentryid \n");
		m_sbFrom.append(" and re.transid = vc.Id \n");
		m_sbFrom.append(" and re.officeid=" + creRtMyWorkInfo.getOfficeID() + " \n");
		m_sbFrom.append(" and re.currencyid=" + creRtMyWorkInfo.getCurrencyID() + " \n");
		m_sbFrom.append(" and re.moduleid=" + creRtMyWorkInfo.getModuleID() + " \n");
		m_sbFrom.append(" and (his.caller = '"+creRtMyWorkInfo.getUserID()+"' or vc.inputuserId = '"+creRtMyWorkInfo.getUserID()+"') \n");
		m_sbFrom.append(" and re.transtypeid =  vc.APPROVALACTION group by his.entry_id) maxvoshi ");
		//end add by dwj 20111108
		
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		m_sbWhere.append(" and re.transid = vc.Id \n");
		m_sbWhere.append(" and maxvoshi.maxid = his.id \n");
		m_sbWhere.append(" and re.officeid=" + creRtMyWorkInfo.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + creRtMyWorkInfo.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + creRtMyWorkInfo.getModuleID() + " \n");
		//update by dwj 20110917 已办业务
		//m_sbWhere.append(" and his.owner = '"+creRtMyWorkInfo.getUserID()+"' \n");
		m_sbWhere.append(" and (his.caller = '"+creRtMyWorkInfo.getUserID()+"' or vc.inputuserId = '"+creRtMyWorkInfo.getUserID()+"') \n");
		//end update by dwj 20110917
		m_sbWhere.append(" and re.transtypeid =  vc.APPROVALACTION ");


		m_sbOrderBy = new StringBuffer();
		String strDesc = creRtMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = creRtMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by vc.code " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by vc.APPROVALACTION " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by vc.clientId " + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by vc.RATINGPROJECTNAME " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by vc.STARTDATE " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by vc.ENDDATE " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by vc.RATINGRESULT " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by his.owner " + strDesc);
					break;
				case 9:
					m_sbOrderBy.append(" \n order by vc.inputuserId " + strDesc);
					break;
				case 10:
					m_sbOrderBy.append(" \n order by vc.inputdate " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by approvalrecordId desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by approvalrecordId desc" );
		}
	}

	protected void getRefuseWorkSql(CreRtMyWorkInfo creRtMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct vc.*,re.transtypeid transtypeid,re.transid transid \n ");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,V_CRERT_TRANSACTION vc \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = vc.id \n");

		m_sbWhere.append(" and  re.officeid="+creRtMyWorkInfo.getOfficeID()+" \n");

		m_sbWhere.append(" and re.currencyid="+creRtMyWorkInfo.getCurrencyID()+" \n");

		m_sbWhere.append(" and re.moduleid="+creRtMyWorkInfo.getModuleID()+" \n");

		m_sbWhere.append("  and re.transtypeid=  vc.APPROVALACTION \n");

		m_sbWhere.append(" and vc.statusId = " + LOANConstant.LoanStatus.SAVE);	
		
		m_sbWhere.append(" and vc.inputuserid = "+creRtMyWorkInfo.getUserID()+" \n");
		m_sbOrderBy = new StringBuffer();
		System.out.println("我要的sql"+m_sbSelect);
		System.out.println("我要的sql"+m_sbFrom);
		System.out.println("我要的sql"+m_sbWhere);
		String strDesc = creRtMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = creRtMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
			case 1:
				m_sbOrderBy.append(" \n order by vc.code " + strDesc);
				break;
			case 2:
				m_sbOrderBy.append(" \n order by vc.APPROVALACTION " + strDesc);
				break;
			case 3:
				m_sbOrderBy.append(" \n order by vc.clientId " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" \n order by vc.RATINGPROJECTNAME " + strDesc);
				break;
			case 5:
				m_sbOrderBy.append(" \n order by vc.STARTDATE " + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" \n order by vc.ENDDATE " + strDesc);
				break;
			case 7:
				m_sbOrderBy.append(" \n order by vc.RATINGRESULT " + strDesc);
				break;
			case 9:
				m_sbOrderBy.append(" \n order by vc.inputuserId " + strDesc);
				break;
			case 10:
				m_sbOrderBy.append(" \n order by vc.inputdate " + strDesc);
				break;
			default:
				m_sbOrderBy.append(" \n order by approvalrecordId desc" );
				break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by inputdate desc" );
		}
	}

	protected Collection queryWaitDealWithWork(CreRtMyWorkInfo creRtMyWorkInfo) throws IException {
		Vector v_Return = new Vector();

		StringBuffer sbSQL = new StringBuffer("");
		sbSQL.append("select vc.* ");
		sbSQL.append(" from v_crert_transaction vc ");
		sbSQL.append(" where vc.officeID = " + creRtMyWorkInfo.getOfficeID());
		sbSQL.append(" and vc.currencyid = " + creRtMyWorkInfo.getCurrencyID());	
		sbSQL.append(" and vc.inputuserid = " + creRtMyWorkInfo.getUserID());
		sbSQL.append(" and vc.statusId = " + CreRtConstant.CreRtStatus.SAVE);
		sbSQL.append(" order by vc.INPUTDATE desc ");

		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			while (transRS.next()) {
				CreRtMyWorkInfo workInfo = new CreRtMyWorkInfo();

				workInfo.setId(transRS.getLong("Id"));
				workInfo.setCode(transRS.getString("code"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setClientID(transRS.getLong("clientId"));
				workInfo.setRatingtype(transRS.getLong("ratingtype"));
				workInfo.setRatingprojectid(transRS.getLong("ratingprojectid"));
				workInfo.setRatingprojectname(transRS.getString("ratingprojectname"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				workInfo.setStartDate(transRS.getTimestamp("startdate"));
				workInfo.setEndDate(transRS.getTimestamp("enddate"));
				workInfo.setRatingnumeric(transRS.getDouble("ratingnumeric"));
				workInfo.setRatingresult(transRS.getString("ratingresult"));
				workInfo.setApprovalAction(transRS.getLong("ApprovalAction"));
			    workInfo.setStatusID(transRS.getLong("statusId"));
				workInfo.setInputUserID(transRS.getLong("inputuserId"));
				workInfo.setActionName(transRS.getString("actionName"));

				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}

	//取消审批
	protected void getCancelApprovalSql(CreRtMyWorkInfo creRtMyWorkInfo)
			throws IException {
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vc.* ,re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId ");

		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sys_approvalrecord re,");
		m_sbFrom.append("  v_crert_transaction vc ");
	
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vc.Id");
		m_sbWhere.append(" and re.officeid = " + creRtMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and re.currencyid = " + creRtMyWorkInfo.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + creRtMyWorkInfo.getModuleID());
		m_sbWhere.append(" and re.transtypeid =  vc.APPROVALACTION");
		m_sbWhere.append(" and re.lastappuserid = " + creRtMyWorkInfo.getUserID());
		m_sbWhere.append(" and re.statusid = " + SETTConstant.RecordStatus.VALID);
		m_sbWhere.append(" and vc.statusid = " + CreRtConstant.CreRtStatus.APPROVALED);
		
		// order by
		m_sbOrderBy = new StringBuffer();
		
		String strDesc = creRtMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		
		long lOrderField = creRtMyWorkInfo.getOrderField();
		
		if (lOrderField > 0) {
			switch ((int) lOrderField) {
			case 1:
				m_sbOrderBy.append(" \n order by vc.code " + strDesc);
				break;
			case 2:
				m_sbOrderBy.append(" \n order by vc.APPROVALACTION " + strDesc);
				break;
			case 3:
				m_sbOrderBy.append(" \n order by vc.clientId " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" \n order by vc.RATINGPROJECTNAME " + strDesc);
				break;
			case 5:
				m_sbOrderBy.append(" \n order by vc.STARTDATE " + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" \n order by vc.ENDDATE " + strDesc);
				break;
			case 7:
				m_sbOrderBy.append(" \n order by vc.RATINGRESULT " + strDesc);
				break;
			case 9:
				m_sbOrderBy.append(" \n order by vc.inputuserId " + strDesc);
				break;
			case 10:
				m_sbOrderBy.append(" \n order by vc.inputdate " + strDesc);
				break;
			default:
				m_sbOrderBy.append(" \n order by approvalrecordId desc" );
				break;
			}
		}else{
			m_sbOrderBy.append(" order by approvalrecordId desc" );
		}
	}
}
