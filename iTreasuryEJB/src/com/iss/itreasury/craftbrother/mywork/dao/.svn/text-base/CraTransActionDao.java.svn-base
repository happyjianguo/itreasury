package com.iss.itreasury.craftbrother.mywork.dao;

import java.util.Collection;
import java.util.Vector;

import  com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInfo;
import com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInterface;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.craftbrother.util.*;
import com.iss.itreasury.util.IException;

public class CraTransActionDao extends CraMyWorkDao {	

	protected Collection queryCurrentWork(CraMyWorkInterface craMyWorkInfo) throws IException {
		Vector v_Return = new Vector();

		StringBuffer sbSQL = new StringBuffer("");
		sbSQL.append("select ar.*,vl.* ");
		sbSQL.append(" from sys_approvalrecord ar,v_cra_transaction vl ");
		sbSQL.append(" where ar.moduleid = " + Constant.ModuleType.CRAFTBROTHER);
		sbSQL.append(" and ar.OfficeID = " + craMyWorkInfo.getOfficeID());
		sbSQL.append(" and ar.currencyid = " + craMyWorkInfo.getCurrencyID());	
		//sbSQL.append(" and ar.transtypeid = "+CRAconstant.SameBusinessAttribute.DISCOUNT);
		sbSQL.append(" and vl.statusid in  ("+LOANConstant.LoanStatus.APPROVALING+","+SECConstant.NoticeFormStatus.APPROVALING+","+SECConstant.ApplyFormStatus.APPROVALING+","+SECConstant.ContractStatus.APPROVALING+") ");
		sbSQL.append(" and ar.transId = vl.Id  ");
		sbSQL.append(" and ar.transtypeid = vl.loanTypeId  ");
		sbSQL.append(" and ar.statusid = " + Constant.RecordStatus.VALID);
		sbSQL.append(" and ar.approvalentryid in (");
		sbSQL.append(craMyWorkInfo.getApprovalEntryIDs() + ")");
		sbSQL.append(" order by ar.id desc ");
		System.out.println("我的SQL是："+sbSQL.toString());


		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
            System.out.println("我的SQL是："+sbSQL.toString());
			while (transRS.next()) {
				CraMyWorkInfo workInfo = new CraMyWorkInfo();

				workInfo.setId(transRS.getLong("ID"));
				workInfo.setCode(transRS.getString("CODE"));
				workInfo.setCurrencyID(transRS.getLong("CURRENCYID"));
				workInfo.setOfficeID(transRS.getLong("OFFICEID"));
			    workInfo.setContractID(transRS.getLong("contractid"));
				workInfo.setContractCode(transRS.getString("contractcode"));
				workInfo.setAmount(transRS.getDouble("Amount"));
				workInfo.setInputDate(transRS.getTimestamp("INPUTDATE"));
				workInfo.setStartDate(transRS.getTimestamp("STARTDATE"));
				workInfo.setEndDate(transRS.getTimestamp("ENDDATE"));
				workInfo.setStatusID(transRS.getLong("STATUSID"));
				workInfo.setInputUserID(transRS.getLong("INPUTUSERID"));
			    workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setLoanTypeId(transRS.getLong("LOANTYPEID"));
				workInfo.setBorrowClientId(transRS.getLong("borrowclientid"));
				workInfo.setBorrowClientName(transRS.getString("borrowClientName"));
				workInfo.setActionID(transRS.getLong("ACTIONID"));
				workInfo.setActionName(transRS.getString("actionName"));
				workInfo.setUserID(craMyWorkInfo.getUserID());// 待办人的id
				workInfo.setApprovalEntryID(transRS.getLong("ApprovalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));

				// 对应的审批流引擎的待办信息
				workInfo.setInutWorkInfo((InutApprovalWorkInfo)craMyWorkInfo.getWorkList().get(String.valueOf(workInfo.getApprovalEntryID())));

				v_Return.add(workInfo);
			}
		} catch (Exception e) {
			throw new IException("Gen_E001", e);
		} finally {
			finalizeDAO();
		}
		return v_Return;
	}	
	
	protected void getFinishedWorkSql(CraMyWorkInterface craMyWorkInfo) {
		m_sbSelect = new StringBuffer();
		m_sbSelect
				.append(" vl.*,re.approvalentryid approvalEntryID,re.id approvalrecordId, \n ");
		m_sbSelect
				.append(" his.stepname stepName,his.modelname wfDefineName,re.linkurl linkUrl,his.owner, \n ");
		m_sbSelect
				.append(" his.entry_id entryID,his.action_code actionCode,his.step_code stepCode \n ");
		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  v_os_histroystep his,sys_approvalrecord re,v_cra_transaction vl \n");
		m_sbFrom.append("  ,(SELECT max(his.id) hisid from v_os_histroystep his,sys_approvalrecord re,v_cra_transaction vl \n");
		m_sbFrom.append("  WHERE his.entry_id = re.approvalentryid  and re.actionId = vl.actionId and re.transId = vl.Id and re.transtypeid =  vl.loanTypeId   \n");
		m_sbFrom.append("  and his.entry_id = re.approvalentryid \n");
		m_sbFrom.append(" and re.officeid=" + craMyWorkInfo.getOfficeID() + " \n");
		m_sbFrom.append(" and re.currencyid=" + craMyWorkInfo.getCurrencyID() + " \n");
		m_sbFrom.append(" and re.moduleid=" + craMyWorkInfo.getModuleID() + " \n");
		m_sbFrom.append("  and (vl.inputuserid = '"+craMyWorkInfo.getUserID()+"' or his.caller='"+craMyWorkInfo.getUserID()+"') \n");
		m_sbFrom.append("  group by his.entry_id ) maxhis\n");
	
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  his.entry_id = re.approvalentryid \n");
		//m_sbWhere.append(" and re.transid = 6 \n");
		m_sbWhere.append(" and re.officeid=" + craMyWorkInfo.getOfficeID() + " \n");
		m_sbWhere.append(" and re.currencyid=" + craMyWorkInfo.getCurrencyID() + " \n");
		m_sbWhere.append(" and re.moduleid=" + craMyWorkInfo.getModuleID() + " \n");
		
		//m_sbWhere.append(" and his.caller = '"+craMyWorkInfo.getUserID()+"' \n");
		m_sbWhere.append(" and re.actionId = vl.actionId \n");
		m_sbWhere.append("  and (vl.inputuserid = '"+craMyWorkInfo.getUserID()+"' or his.caller='"+craMyWorkInfo.getUserID()+"') \n");
		//m_sbWhere.append(" and vl.statusid =  "+LOANConstant.LoanStatus.CHECK);
		m_sbWhere.append(" and re.transId = vl.Id " ); 
		m_sbWhere.append(" and re.transtypeid =  vl.loanTypeId ");
		m_sbWhere.append(" and maxhis.hisid = his.id ");
		System.out.println("我的SQL是："+"select "+m_sbSelect+" from "+m_sbFrom+" where "+m_sbWhere);
		m_sbOrderBy = new StringBuffer();
		String strDesc = craMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = craMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by vl.loanTypeId " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by vl.actionId " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by vl.borrowclientid " + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by vl.contractcode " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by vl.code " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by his.owner " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by vl.inputuserId " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by vl.inputdate " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by approvalrecordId desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by approvalrecordId desc" );
		}
		
	}

	protected void getRefuseWorkSql(CraMyWorkInterface craMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct vl.*,re.transtypeid transtypeid,re.transid transid \n ");
		//		 from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("  (select distinct t.officeid,t.currencyid,t.moduleid,t.transtypeid,t.actionid,t.transid from sys_approvalrecord t) re,v_cra_transaction vl \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append("  re.transid = vl.id \n");

		m_sbWhere.append(" and  re.officeid="+craMyWorkInfo.getOfficeID()+" \n");

		m_sbWhere.append(" and re.currencyid="+craMyWorkInfo.getCurrencyID()+" \n");

		m_sbWhere.append(" and re.moduleid="+craMyWorkInfo.getModuleID()+" \n");

		m_sbWhere.append("  and re.transtypeid=  vl.loanTypeId \n");

		m_sbWhere.append(" and vl.statusid ="+craMyWorkInfo.getStatusID()+" \n");

		m_sbWhere.append(" and vl.inputuserid = "+craMyWorkInfo.getUserID()+" \n");
		m_sbOrderBy = new StringBuffer();
		System.out.println("我要的sql"+m_sbSelect);
		System.out.println("我要的sql"+m_sbFrom);
		System.out.println("我要的sql"+m_sbWhere);
		String strDesc = craMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = craMyWorkInfo.getOrderField();
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
				default:
					//m_sbOrderBy.append(" \n order by approvalrecordId  desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by inputdate desc" );
		}
	}

	protected Collection queryWaitDealWithWork(CraMyWorkInterface craMyWorkInfo) throws IException {
		Vector v_Return = new Vector();

		StringBuffer sbSQL = new StringBuffer("");
		sbSQL.append("select vl.* ");
		sbSQL.append(" from v_cra_transaction vl ");
		sbSQL.append(" where vl.officeID = " + craMyWorkInfo.getOfficeID());
		sbSQL.append(" and vl.currencyid = " + craMyWorkInfo.getCurrencyID());	
		sbSQL.append(" and vl.inputuserid = " + craMyWorkInfo.getUserID());
		sbSQL.append(" and vl.statusId = " + LOANConstant.LoanStatus.SAVE);		
		sbSQL.append(" order by vl.INPUTDATE desc");
		System.out.println("我要的SQL是:"+sbSQL);

		try {
			initDAO();
			prepareStatement(sbSQL.toString());
			executeQuery();
			int flag=0;
			while (transRS.next()) {
				CraMyWorkInfo workInfo = new CraMyWorkInfo();

				workInfo.setId(transRS.getLong("Id"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setContractID(transRS.getLong("contractid"));
          //	处理资金回收合同编号问题 modified by xwhe on 07-10-8
				if(("".equals(transRS.getString("code"))||transRS.getString("code")==null)&&transRS.getLong("loanTypeId")==3502 ){
					flag++;
					//System.out.println("---------------------------------------"+Integer.parseInt(flag));
					workInfo.setCode(getNumber(flag));
				}else{
					workInfo.setCode(transRS.getString("code"));
				}
				workInfo.setContractCode(transRS.getString("contractcode"));
				workInfo.setAmount(transRS.getDouble("amount"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				workInfo.setStartDate(transRS.getTimestamp("startdate"));
				workInfo.setEndDate(transRS.getTimestamp("enddate"));
				workInfo.setStatusID(transRS.getLong("statusId"));
				workInfo.setInputUserID(transRS.getLong("inputuserId"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));
				workInfo.setLoanTypeId(transRS.getLong("loanTypeId"));
				//workInfo.setLoanSubTypeId(transRS.getLong("loanSubTypeId"));
				//workInfo.setLoanSubTypeName(transRS.getString("loanSubTypeName"));
				workInfo.setBorrowClientId(transRS.getLong("borrowclientid"));
				workInfo.setBorrowClientName(transRS.getString("borrowClientName"));
				workInfo.setActionID(transRS.getLong("actionId"));
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
	protected void getCancelApprovalSql(CraMyWorkInterface craMyWorkInfo)
			throws IException {
		// select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" vl.* ,re.approvalentryid approvalEntryID,re.linkurl linkUrl,re.id approvalrecordId ");

		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sys_approvalrecord re,v_cra_transaction vl");
		//m_sbFrom.append(" ( (select * from v_cra_transaction ");
		//m_sbFrom.append("       where actionid = " + Constant.ApprovalAction.LOAN_CONTRACT + " and (statusid = " + LOANConstant.ContractStatus.CHECK +" or statusid = " + LOANConstant.ContractStatus.NOTACTIVE+ " ))");
		//m_sbFrom.append("    union " );
		//m_sbFrom.append(" 	(select * from v_cra_transaction ");
		//m_sbFrom.append(" 		where actionid != "+Constant.ApprovalAction.LOAN_CONTRACT+" and statusid = "+LOANConstant.ContractStatus.CHECK +")  ");
		//m_sbFrom.append("  )  vl ");
	
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" re.transid = vl.Id");
		m_sbWhere.append(" and re.officeid = " + craMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and re.currencyid = " + craMyWorkInfo.getCurrencyID());
		m_sbWhere.append(" and re.moduleid = " + craMyWorkInfo.getModuleID());
		m_sbWhere.append(" and re.actionId = vl.actionId");
	//	m_sbWhere.append(" and re.transtypeid =vl.loanTypeId");
		m_sbWhere.append(" and re.lastappuserid = " + craMyWorkInfo.getUserID());
		m_sbWhere.append(" and re.statusid = " + SETTConstant.RecordStatus.VALID);
		//modify by xwhe 暂时加上一个其他的状态,为转贴现凭证服务
		m_sbWhere.append(" and vl.statusid in( " + LOANConstant.LoanStatus.CHECK+","+ LOANConstant.LoanStatus.OTHER+","+SECConstant.NoticeFormStatus.CHECKED+","+SECConstant.ApplyFormStatus.CHECKED+","+SECConstant.ContractStatus.CHECK+")");
		
		// order by
		m_sbOrderBy = new StringBuffer();
		
		String strDesc = craMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		
		long lOrderField = craMyWorkInfo.getOrderField();
		System.out.println("我的SQL"+"select"+m_sbSelect+"from"+m_sbFrom+"where"+m_sbWhere);
		if (lOrderField > 0) {
			switch ((int) lOrderField) {
			case 1:
				m_sbOrderBy.append(" order by vl.loanTypeId " + strDesc);
				break;			
			case 2:
				m_sbOrderBy.append(" order by vl.actionId " + strDesc);
				break;
			case 3:
				m_sbOrderBy.append(" order by vl.borrowclientid " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" order by vl.contractcode " + strDesc);
				break;
			case 5:
				m_sbOrderBy.append(" order by vl.code " + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" order by vl.inputuserId " + strDesc);
				break;
			case 7:
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
	//解决展期申请，业务编号生成方法
	/**
	 * 此方法有待改进，如果大于999，则不知道怎样编号好
	 */
	public String getNumber(int id){
		String number="";
		number=Integer.toString(id);
		int len=number.length();
		switch(len){
		case 1:
			return ("00"+number);
		case 2:
			return ("0"+number);
		}
		return number;
	}

}
