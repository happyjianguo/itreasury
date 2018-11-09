package com.iss.itreasury.securities.mywork.dao;

/*
 * Created on 2007-09-05
 *
 * Title:				iTreasury
 * @author             	杨垒、何小文
 * Company:             iSoftStone
 * @version				iTreasury4.0新增
 * Description:         产品化4.0在证券新增审批流,,该功能实现查找我的任务
 */

import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.securities.mywork.dataentity.SecMyWorkInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class SecApplyActionDao extends SecMyWorkDao {
	
	public String getBaseSQL(long statuId){
		StringBuffer baseSQL = new StringBuffer();
		
		baseSQL.append(" (select");
		baseSQL.append(" sappl.id SecId,"); //申请书ID  
		baseSQL.append(" sappl.code SecCode,"); //申请书编号
		baseSQL.append(" sclie.name ClientName,"); //业务单位名称
		baseSQL.append(" sstoc.name StockHolderAccountName,"); //股东帐户名称
		baseSQL.append(" sbusi.id BusinessTypeId,"); //业务类型ID
		baseSQL.append(" sbusi.name BusinessTypeName,"); //业务类型名称
		baseSQL.append(" stran.id TransactionTypeId,"); //交易类型ID
		baseSQL.append(" stran.name TransactionTypeName,"); //交易类型名称
		baseSQL.append(" sappl.amount Amount,"); //金额
		baseSQL.append(" sappl.inputdate InputDate,"); //申请录入日期
		baseSQL.append(" scoun.id BankOfDepositId,"); //开户营业部ID
		baseSQL.append(" scoun.name BankOfDepositName,"); //开户营业部名称
		baseSQL.append(" sacco.accountcode AccountCode,"); //资金帐号
		baseSQL.append(" sappl.inputuserid InputUserId,"); //录入人ID
		baseSQL.append(" useri.sname InputUserName"); //录入人名称
		baseSQL.append(" from");
		baseSQL.append(" SEC_APPLYFORM sappl,"); //申请单存入数据的表
		baseSQL.append(" SEC_CLIENT sclie,"); //业务单位表
		baseSQL.append(" SEC_BUSINESSTYPE sbusi,"); //业务类型
		baseSQL.append(" SEC_TRANSACTIONTYPE stran,"); //交易类型表
		baseSQL.append(" SEC_ACCOUNT sacco,"); //证券帐户表
		baseSQL.append(" SEC_SECURITIES ssecu,"); //证券产品表
		baseSQL.append(" SEC_STOCKHOLDERACCOUNT sstoc,"); //股东
		baseSQL.append(" USERINFO useri,"); //用户信息表
		baseSQL.append(" (select * from SEC_COUNTERPART where ISBANK is null or ISBANK = -1) scoun"); //营业部
		baseSQL.append(" where sappl.ClientID = sclie.id(+)");
		baseSQL.append(" and sappl.TransactionTypeID = stran.id(+)");
		baseSQL.append(" and sappl.CounterpartID = scoun.id(+)");
		baseSQL.append(" and sappl.ACCOUNTID = sacco.ID(+)");
		baseSQL.append(" and sappl.SECURITIESID = ssecu.ID(+)");
		baseSQL.append(" and sacco.STOCKHOLDERACCOUNTID1 = sstoc.ID(+)");
		baseSQL.append(" and stran.businesstypeid = sbusi.id");
		baseSQL.append(" and sappl.inputuserid = useri.id");
		if(statuId == -100){
			baseSQL.append(" and sbusi.statusid <> " + Constant.RecordStatus.INVALID + ") apply ");
		}
		else{
			baseSQL.append(" and sbusi.statusid <> " + Constant.RecordStatus.INVALID);
			baseSQL.append(" and sappl.statusid = " + statuId + ") apply ");
		}
		
		return baseSQL.toString();
	}

	/**
	 * 待审批业务
	 */
	protected Collection queryCurrentWork(SecMyWorkInfo secMyWorkInfo) throws IException {
		Vector v_Return = new Vector();
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append("select sappr.*, apply.* from");
		//BaseSQL
		m_sbSelect.append(this.getBaseSQL(SECConstant.ApplyFormStatus.APPROVALING) + ", ");
		m_sbSelect.append("SYS_APPROVALRECORD sappr ");
		m_sbSelect.append("where sappr.moduleid = " + Constant.ModuleType.SECURITIES);
		m_sbSelect.append(" and sappr.officeid = " + secMyWorkInfo.getOfficeID());
		m_sbSelect.append(" and sappr.currencyid = " + secMyWorkInfo.getCurrencyID());	
		m_sbSelect.append(" and sappr.transid = apply.SecId");
		m_sbSelect.append(" and sappr.transtypeid = apply.TransactionTypeId");
		m_sbSelect.append(" and sappr.actionid = " + SECConstant.AttachParentType.APPLY);
		m_sbSelect.append(" and sappr.approvalentryid in (" + secMyWorkInfo.getApprovalEntryIDs() + ")");
		m_sbSelect.append(" order by apply.InputDate desc, sappr.approvalentryid desc");
		try {
			initDAO();
			prepareStatement(m_sbSelect.toString());
			executeQuery();

			while (transRS.next()) {
				SecMyWorkInfo workInfo = new SecMyWorkInfo();

				//审批关联信息
				workInfo.setId(transRS.getLong("Id"));
				workInfo.setOfficeID(transRS.getLong("officeid"));
				workInfo.setCurrencyID(transRS.getLong("currencyid"));
				workInfo.setActionID(transRS.getLong("actionId"));
				workInfo.setActionName(transRS.getString("transactionTypeName"));
				workInfo.setApprovalEntryID(transRS.getLong("approvalEntryID"));
				workInfo.setLinkUrl(transRS.getString("LinkUrl"));
				workInfo.setUserID(secMyWorkInfo.getUserID());
				workInfo.setStatusID(transRS.getLong("statusId"));
				
				//证券业务信息
				workInfo.setSecId(transRS.getLong("secId"));
				workInfo.setSecCode(transRS.getString("secCode"));
				workInfo.setClientName(transRS.getString("clientName"));
				workInfo.setStockHolderAccountName(transRS.getString("stockHolderAccountName"));
				workInfo.setBusinessTypeId(transRS.getLong("businessTypeId"));
				workInfo.setBusinessTypeName(transRS.getString("businessTypeName"));
				workInfo.setTransactionTypeId(transRS.getLong("transactionTypeId"));
				workInfo.setTransactionTypeName(transRS.getString("transactionTypeName"));
				workInfo.setAmount(transRS.getDouble("Amount"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				workInfo.setBankOfDepositId(transRS.getLong("bankOfDepositId"));
				workInfo.setBankOfDepositName(transRS.getString("bankOfDepositName"));
				workInfo.setAccountCode(transRS.getString("accountCode"));
				workInfo.setInputUserID(transRS.getLong("inputUserID"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));

				//对应的审批流引擎的待办信息
				workInfo.setInutWorkInfo((InutApprovalWorkInfo)secMyWorkInfo.getWorkList().get(String.valueOf(workInfo.getApprovalEntryID())));

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
	 * 取消审批
	 */
	protected void getCancelApprovalSql(SecMyWorkInfo secMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" apply.*, sappr.approvalentryid approvalEntryID,sappr.linkurl linkUrl,sappr.id approvalrecordId");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL(SECConstant.ApplyFormStatus.CHECKED) + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.SECURITIES);
		m_sbWhere.append(" and sappr.officeid = " + secMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + secMyWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = apply.SecId");
		m_sbWhere.append(" and sappr.transtypeid = apply.TransactionTypeId");
		m_sbWhere.append(" and sappr.actionid = " + SECConstant.AttachParentType.APPLY);
		m_sbWhere.append(" and sappr.lastappuserid = " + secMyWorkInfo.getUserID());
		m_sbWhere.append(" and sappr.statusId  = " + Constant.RecordStatus.VALID);
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = secMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = secMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by apply.SecCode " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by apply.ClientName " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by apply.StockHolderAccountName " + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by apply.BusinessTypeName " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by apply.TransactionTypeName " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by apply.Amount " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by apply.InputDate " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by apply.BankOfDepositName " + strDesc);
					break;
				case 9:
					m_sbOrderBy.append(" \n order by apply.AccountCode " + strDesc);
					break;
				case 10:
					m_sbOrderBy.append(" \n order by apply.InputUserName " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by apply.InputDate desc, sappr.approvalentryid desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by apply.InputDate desc, sappr.approvalentryid desc" );
		}	
	}

	/**
	 * 已办业务
	 */
	protected void getFinishedWorkSql(SecMyWorkInfo secMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" apply.*, sappr.approvalentryid approvalEntryID, sappr.id approvalrecordId,");
		m_sbSelect.append("voshi.stepname stepName, voshi.modelname wfDefineName, sappr.linkurl linkUrl, voshi.owner owner,");
		m_sbSelect.append("voshi.entry_id entryID, voshi.action_code actionCode, voshi.step_code stepCode");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL(-100) + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr,");
		m_sbFrom.append("v_os_histroystep voshi");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.SECURITIES);
		m_sbWhere.append(" and sappr.officeid = " + secMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + secMyWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = apply.SecId");
		m_sbWhere.append(" and sappr.transtypeid = apply.TransactionTypeId");
		m_sbWhere.append(" and sappr.actionid = " + SECConstant.AttachParentType.APPLY);
		m_sbWhere.append(" and voshi.entry_id = sappr.approvalentryid");
		m_sbWhere.append(" and voshi.owner = " + secMyWorkInfo.getUserID());
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = secMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = secMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
				case 1:
					m_sbOrderBy.append(" \n order by apply.SecCode " + strDesc);
					break;
				case 2:
					m_sbOrderBy.append(" \n order by apply.ClientName " + strDesc);
					break;
				case 3:
					m_sbOrderBy.append(" \n order by apply.StockHolderAccountName " + strDesc);
					break;
				case 4:
					m_sbOrderBy.append(" \n order by apply.BusinessTypeName " + strDesc);
					break;
				case 5:
					m_sbOrderBy.append(" \n order by apply.TransactionTypeName " + strDesc);
					break;
				case 6:
					m_sbOrderBy.append(" \n order by apply.Amount " + strDesc);
					break;
				case 7:
					m_sbOrderBy.append(" \n order by apply.InputDate " + strDesc);
					break;
				case 8:
					m_sbOrderBy.append(" \n order by apply.BankOfDepositName " + strDesc);
					break;
				case 9:
					m_sbOrderBy.append(" \n order by apply.AccountCode " + strDesc);
					break;
				case 10:
					m_sbOrderBy.append(" \n order by apply.InputUserName " + strDesc);
					break;
				default:
					m_sbOrderBy.append(" \n order by apply.InputDate desc, sappr.approvalentryid desc" );
					break;
			}	
		}else{
			m_sbOrderBy.append(" \n order by apply.InputDate desc, sappr.approvalentryid desc" );
		}
	}

	/**
	 * 拒绝业务
	 */
	protected void getRefuseWorkSql(SecMyWorkInfo secMyWorkInfo) throws IException {
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" distinct apply.*, sappr.transtypeid transtypeid, sappr.transid transid");
		
		m_sbFrom = new StringBuffer();
		//BaseSQL
		m_sbFrom.append(this.getBaseSQL(SECConstant.ApplyFormStatus.SUBMITED) + ", ");
		m_sbFrom.append("SYS_APPROVALRECORD sappr");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" sappr.moduleid = " + Constant.ModuleType.SECURITIES);
		m_sbWhere.append(" and sappr.officeid = " + secMyWorkInfo.getOfficeID());
		m_sbWhere.append(" and sappr.currencyid = " + secMyWorkInfo.getCurrencyID());	
		m_sbWhere.append(" and sappr.transid = apply.SecId");
		m_sbWhere.append(" and sappr.transtypeid = apply.TransactionTypeId");
		m_sbWhere.append(" and apply.inputuserid = " + secMyWorkInfo.getUserID());
		
		m_sbOrderBy = new StringBuffer();
		String strDesc = secMyWorkInfo.getDesc() == 2 ? " desc " : " asc";
		long lOrderField = secMyWorkInfo.getOrderField();
		if (lOrderField > 0) {
			switch ((int) lOrderField)
			{
			case 1:
				m_sbOrderBy.append(" \n order by apply.SecCode " + strDesc);
				break;
			case 2:
				m_sbOrderBy.append(" \n order by apply.ClientName " + strDesc);
				break;
			case 3:
				m_sbOrderBy.append(" \n order by apply.StockHolderAccountName " + strDesc);
				break;
			case 4:
				m_sbOrderBy.append(" \n order by apply.BusinessTypeName " + strDesc);
				break;
			case 5:
				m_sbOrderBy.append(" \n order by apply.TransactionTypeName " + strDesc);
				break;
			case 6:
				m_sbOrderBy.append(" \n order by apply.Amount " + strDesc);
				break;
			case 7:
				m_sbOrderBy.append(" \n order by apply.InputDate " + strDesc);
				break;
			case 8:
				m_sbOrderBy.append(" \n order by apply.BankOfDepositName " + strDesc);
				break;
			case 9:
				m_sbOrderBy.append(" \n order by apply.AccountCode " + strDesc);
				break;
			case 10:
				m_sbOrderBy.append(" \n order by apply.InputUserName " + strDesc);
				break;
			default:
				m_sbOrderBy.append(" \n order by apply.InputDate desc, apply.SecId desc" );
				break;
			}
		}else{
			m_sbOrderBy.append(" \n order by apply.InputDate desc, apply.SecId desc" );
		}
	}

	/**
	 * 待提交业务
	 */
	protected Collection queryWaitDealWithWork(SecMyWorkInfo secMyWorkInfo) throws IException {
		Vector v_Return = new Vector();
		m_sbSelect = new StringBuffer();
		
		m_sbSelect.append("select apply.* from");
		//BaseSQL
		m_sbSelect.append(this.getBaseSQL(SECConstant.ApplyFormStatus.SUBMITED));
		m_sbSelect.append("where apply.InputUserId = " + secMyWorkInfo.getUserID() + " ");
		m_sbSelect.append(" order by apply.InputDate desc, apply.SecId desc");
		
		try {
			initDAO();
			prepareStatement(m_sbSelect.toString());
			executeQuery();

			while (transRS.next()) {
				SecMyWorkInfo workInfo = new SecMyWorkInfo();
				
				//证券业务信息
				workInfo.setSecId(transRS.getLong("secId"));
				workInfo.setSecCode(transRS.getString("secCode"));
				workInfo.setClientName(transRS.getString("clientName"));
				workInfo.setStockHolderAccountName(transRS.getString("stockHolderAccountName"));
				workInfo.setBusinessTypeId(transRS.getLong("businessTypeId"));
				workInfo.setBusinessTypeName(transRS.getString("businessTypeName"));
				workInfo.setTransactionTypeId(transRS.getLong("transactionTypeId"));
				workInfo.setTransactionTypeName(transRS.getString("transactionTypeName"));
				workInfo.setAmount(transRS.getDouble("Amount"));
				workInfo.setInputDate(transRS.getTimestamp("inputdate"));
				workInfo.setBankOfDepositId(transRS.getLong("bankOfDepositId"));
				workInfo.setBankOfDepositName(transRS.getString("bankOfDepositName"));
				workInfo.setAccountCode(transRS.getString("accountCode"));
				workInfo.setInputUserID(transRS.getLong("inputUserID"));
				workInfo.setInputUserName(transRS.getString("inputUserName"));

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