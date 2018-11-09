package com.iss.itreasury.craftbrother.mywork.dao;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInterface;
import com.iss.itreasury.craftbrother.mywork.dataentity.CreditSettingMyWorkInfo;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class CreditSettingMyWorkDao extends CraMyWorkDao {
	
	//Base SQL
	public String getBaseSQL(){
		StringBuffer baseSQL = new StringBuffer();
		//财务公司对同行授信
		baseSQL.append(" (select");
		baseSQL.append(" cracr.id csID,"); //授信记录ID
		baseSQL.append(" cracr.creditclientid creditClientID,"); //授信方ID
		baseSQL.append(" clien.scode creditClientCode,"); //授信方客户编号
		baseSQL.append(" clien.sname creditClientName,"); //授信方客户名称
		baseSQL.append(" cracr.creditedclientid creditedClientID,"); //被授信方ID
		baseSQL.append(" secco.code creditedClientCode,"); //被授信方客户编号
		baseSQL.append(" secco.name creditedClientName,"); //被授信方客户名称
		baseSQL.append(" cracr.creditamount amount,"); //授信额度
		baseSQL.append(" cracr.transactiontype transactionType,"); //交易类型
		baseSQL.append(" cracr.creditdirection creditDirection,"); //授信方向
		baseSQL.append(" cracr.startdate startDate,"); //授信开始时间
		baseSQL.append(" cracr.enddate endDate,"); //授信结束时间
		baseSQL.append(" cracr.enddate-cracr.startdate term,"); //授信期限
		baseSQL.append(" cracr.remark remark,"); //备注
		baseSQL.append(" cracr.inputuserid inputUserID,"); //录入人ID
		baseSQL.append(" useri.sname inputUser,"); //录入人
		baseSQL.append(" cracr.inputdate inputDate,"); //录入时间
		baseSQL.append(" cracr.statusid statusID"); //授信记录状态
		baseSQL.append(" from");
		baseSQL.append(" CRA_CREDITLIMIT cracr,");
		baseSQL.append(" CLIENT clien,");
		baseSQL.append(" SEC_COUNTERPART secco,");
		baseSQL.append(" USERINFO useri");
		baseSQL.append(" where");
		baseSQL.append(" cracr.creditdirection = 1");
		baseSQL.append(" and cracr.creditclientid = clien.id");
		baseSQL.append(" and cracr.creditedclientid = secco.id");
		baseSQL.append(" and cracr.inputuserid = useri.id");
		baseSQL.append(" union");
		//同行对财务公司及成员单位授信
		baseSQL.append(" select");
		baseSQL.append(" cracr.id csID,"); //授信记录ID
		baseSQL.append(" cracr.creditedclientid creditedClientID,"); //授信方ID
		baseSQL.append(" secco.code creditedClientCode,"); //授信方客户编号
		baseSQL.append(" secco.name creditedClientName,"); //授信方客户名称
		baseSQL.append(" cracr.creditclientid creditClientID,"); //被授信方ID
		baseSQL.append(" clien.scode creditClientCode,"); //被授信方客户编号
		baseSQL.append(" clien.sname creditClientName,"); //被授信方客户名称
		baseSQL.append(" cracr.creditamount amount,"); //授信额度
		baseSQL.append(" cracr.transactiontype transactionType,"); //交易类型
		baseSQL.append(" cracr.creditdirection creditDirection,"); //授信方向
		baseSQL.append(" cracr.startdate startDate,"); //授信开始时间
		baseSQL.append(" cracr.enddate endDate,"); //授信结束时间
		baseSQL.append(" cracr.enddate-cracr.startdate term,"); //授信期限
		baseSQL.append(" cracr.remark remark,"); //备注
		baseSQL.append(" cracr.inputuserid inputUserID,"); //录入人ID
		baseSQL.append(" useri.sname inputUser,"); //录入人
		baseSQL.append(" cracr.inputdate inputDate,"); //录入时间
		baseSQL.append(" cracr.statusid statusID"); //授信记录状态
		baseSQL.append(" from");
		baseSQL.append(" CRA_CREDITLIMIT cracr,");
		baseSQL.append(" CLIENT clien,");
		baseSQL.append(" SEC_COUNTERPART secco,");
		baseSQL.append(" USERINFO useri");
		baseSQL.append(" where");
		baseSQL.append(" cracr.creditdirection = 2");
		baseSQL.append(" and cracr.creditclientid = secco.id");
		baseSQL.append(" and cracr.creditedclientid = clien.id");
		baseSQL.append(" and cracr.inputuserid = useri.id) csett");
		return baseSQL.toString();
	}

	/**
	 * 取消审批
	 */
	protected void getCancelApprovalSql(CraMyWorkInterface craMyWorkInfo)
			throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" csett.*, sappr.approvalentryid approvalEntryID,sappr.linkurl linkUrl,sappr.id approvalrecordId");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.CRAFTBROTHER);
		m_sbWhere.append(" and sappr.officeid = " + craMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + craMyWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = csett.csID");
		m_sbWhere.append(" and csett.statusID = " + CRAconstant.TransactionStatus.APPROVALED);
		m_sbWhere.append(" and sappr.transtypeid = " + CRAconstant.SameBusinessAttribute.OTHER);
		m_sbWhere.append(" and sappr.actionid = csett.transactionType");
		m_sbWhere.append(" and sappr.lastappuserid = " + craMyWorkInfo.getUserID());
		m_sbWhere.append(" and sappr.statusId  = " + Constant.RecordStatus.VALID);
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = craMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = craMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by csett.csID " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by csett.transactionType " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by csett.creditedClientName " + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by csett.creditClientName " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by csett.startDate " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by csett.endDate " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by csett.term " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by csett.inputDate " + strDesc);
					break;
				case 9:
					m_sbOrderBy.append(" \n order by csett.amount " + strDesc);
					break;
				case 10:
					m_sbOrderBy.append(" \n order by csett.inputUser " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by csett.InputDate desc, sappr.approvalentryid desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by csett.InputDate desc, sappr.approvalentryid desc" );
		}	

	}

	/**
	 * 已办业务
	 */
	protected void getFinishedWorkSql(CraMyWorkInterface craMyWorkInfo) {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" csett.*, sappr.approvalentryid approvalEntryID, sappr.id approvalrecordId,");
		m_sbSelect.append("voshi.stepname stepName, voshi.modelname wfDefineName, sappr.linkurl linkUrl, voshi.owner owner,");
		m_sbSelect.append("voshi.entry_id entryID, voshi.action_code actionCode, voshi.step_code stepCode");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr,");
		m_sbFrom.append("v_os_histroystep voshi");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.CRAFTBROTHER);
		m_sbWhere.append(" and sappr.officeid = " + craMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + craMyWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = csett.csID");
		m_sbWhere.append(" and sappr.transtypeid = " + CRAconstant.SameBusinessAttribute.OTHER);
		m_sbWhere.append(" and sappr.actionid = csett.transactionType");
		m_sbWhere.append(" and voshi.entry_id = sappr.approvalentryid");
		m_sbWhere.append(" and voshi.owner = " + craMyWorkInfo.getUserID());
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = craMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = craMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by csett.csID " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by csett.transactionType " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by csett.creditedClientName " + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by csett.creditClientName " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by csett.startDate " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by csett.endDate " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by csett.term " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by csett.inputDate " + strDesc);
					break;
				case 9:
					m_sbOrderBy.append(" \n order by csett.amount " + strDesc);
					break;
				case 10:
					m_sbOrderBy.append(" \n order by csett.inputUser " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by csett.InputDate desc, sappr.approvalentryid desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by csett.InputDate desc, sappr.approvalentryid desc" );
		}	
	}

	/**
	 * 拒绝业务
	 */
	protected void getRefuseWorkSql(CraMyWorkInterface craMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct csett.*, sappr.transtypeid transtypeid, sappr.transid transid");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL() + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.CRAFTBROTHER);
		m_sbWhere.append(" and sappr.officeid = " + craMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + craMyWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = csett.csID");
		m_sbWhere.append(" and csett.statusID = " + CRAconstant.TransactionStatus.SAVE);
		m_sbWhere.append(" and sappr.transtypeid = " + CRAconstant.SameBusinessAttribute.OTHER);
		m_sbWhere.append(" and csett.inputuserid = " + craMyWorkInfo.getUserID());
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = craMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = craMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by csett.csID " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by csett.transactionType " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by csett.creditedClientName " + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by csett.creditClientName " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by csett.startDate " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by csett.endDate " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by csett.term " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by csett.inputDate " + strDesc);
					break;
				case 9:
					m_sbOrderBy.append(" \n order by csett.amount " + strDesc);
					break;
				case 10:
					m_sbOrderBy.append(" \n order by csett.inputUser " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by csett.InputDate desc, csett.csID desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by csett.InputDate desc, csett.csID desc" );
		}	

	}
	
	/**
	 * 待审批业务
	 */
	protected Collection queryCurrentWork(CraMyWorkInterface craMyWorkInfo) throws IException {
		Vector v_Return = new Vector();
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append("select sappr.*, csett.* from");
		//BaseSQL
		m_sbSelect.append(this.getBaseSQL() + ", ");
		m_sbSelect.append("SYS_APPROVALRECORD sappr ");
		m_sbSelect.append("where sappr.moduleid = " + Constant.ModuleType.CRAFTBROTHER);
		m_sbSelect.append(" and sappr.officeid = " + craMyWorkInfo.getOfficeID());
		m_sbSelect.append(" and sappr.currencyid = " + craMyWorkInfo.getCurrencyID());	
		m_sbSelect.append(" and sappr.transid = csett.csID");
		m_sbSelect.append(" and csett.statusID = " + CRAconstant.TransactionStatus.APPROVALING);
		m_sbSelect.append(" and sappr.transtypeid = " + CRAconstant.SameBusinessAttribute.OTHER);
		m_sbSelect.append(" and sappr.actionid = csett.transactionType");
		m_sbSelect.append(" and sappr.approvalentryid in (" + craMyWorkInfo.getApprovalEntryIDs() + ")");
		m_sbSelect.append(" order by csett.InputDate desc, sappr.approvalentryid desc");
		try {
			initDAO();
			prepareStatement(m_sbSelect.toString());
			executeQuery();

			while (transRS.next()) {
				CreditSettingMyWorkInfo workInfo = new CreditSettingMyWorkInfo();

				//审批关联信息
				workInfo.setId(transRS.getLong("Id"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setActionID(transRS.getLong("actionId"));
				workInfo.setActionName(CRAconstant.TransactionType.getName(transRS.getLong("transactionType")));
				workInfo.setApprovalEntryID(transRS.getLong("approvalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));
				workInfo.setUserID(craMyWorkInfo.getUserID());
				workInfo.setStatusID(transRS.getLong("statusId"));
				
				//授信信息
			    workInfo.setCsID(transRS.getLong("csID"));
			    workInfo.setCreditClientID(transRS.getLong("creditClientID"));
			    workInfo.setCreditClientCode(transRS.getString("creditClientCode"));
			    workInfo.setCreditClientName(transRS.getString("creditClientName"));
			    workInfo.setCreditedClientID(transRS.getLong("creditedClientID"));
			    workInfo.setCreditedClientCode(transRS.getString("creditedClientCode"));
			    workInfo.setCreditedClientName(transRS.getString("creditedClientName"));
			    workInfo.setAmount(transRS.getDouble("amount"));
			    workInfo.setTransactionType(transRS.getLong("transactionType"));
			    workInfo.setCreditDirection(transRS.getLong("creditDirection"));
			    workInfo.setStartDate(transRS.getTimestamp("startDate"));
			    workInfo.setEndDate(transRS.getTimestamp("endDate"));
			    workInfo.setTerm(transRS.getLong("term"));
			    workInfo.setRemark(transRS.getString("remark"));
			    workInfo.setInputUserID(transRS.getLong("inputUserID"));
			    workInfo.setInputUser(transRS.getString("inputUser"));
			    workInfo.setInputDate(transRS.getTimestamp("inputDate"));

				//对应的审批流引擎的待办信息
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

	/**
	 * 待提交业务
	 */
	protected Collection queryWaitDealWithWork(CraMyWorkInterface craMyWorkInfo) throws IException {
		Vector v_Return = new Vector();
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append("select csett.* from");
		//BaseSQL
		m_sbSelect.append(this.getBaseSQL());
		m_sbSelect.append(" where csett.InputUserId = " + craMyWorkInfo.getUserID());
		m_sbSelect.append(" and csett.statusID = " + CRAconstant.TransactionStatus.SAVE);
		m_sbSelect.append(" order by csett.InputDate desc, csett.csID desc");
		
		try {
			initDAO();
			prepareStatement(m_sbSelect.toString());
			executeQuery();

			while (transRS.next()) {
				CreditSettingMyWorkInfo workInfo = new CreditSettingMyWorkInfo();
				
				//授信信息
			    workInfo.setCsID(transRS.getLong("csID"));
			    workInfo.setCreditClientID(transRS.getLong("creditClientID"));
			    workInfo.setCreditClientCode(transRS.getString("creditClientCode"));
			    workInfo.setCreditClientName(transRS.getString("creditClientName"));
			    workInfo.setCreditedClientID(transRS.getLong("creditedClientID"));
			    workInfo.setCreditedClientCode(transRS.getString("creditedClientCode"));
			    workInfo.setCreditedClientName(transRS.getString("creditedClientName"));
			    workInfo.setAmount(transRS.getDouble("amount"));
			    workInfo.setTransactionType(transRS.getLong("transactionType"));
			    workInfo.setCreditDirection(transRS.getLong("creditDirection"));
			    workInfo.setStartDate(transRS.getTimestamp("startDate"));
			    workInfo.setEndDate(transRS.getTimestamp("endDate"));
			    workInfo.setTerm(transRS.getLong("term"));
			    workInfo.setRemark(transRS.getString("remark"));
			    workInfo.setInputUserID(transRS.getLong("inputUserID"));
			    workInfo.setInputUser(transRS.getString("inputUser"));
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
