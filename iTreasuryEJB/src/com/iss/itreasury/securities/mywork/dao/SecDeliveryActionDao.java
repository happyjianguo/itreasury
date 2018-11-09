package com.iss.itreasury.securities.mywork.dao;

/*
 * Created on 2007-09-11
 *
 * Title:				iTreasury
 * @author             	杨垒
 * Company:             iSoftStone
 * @version				iTreasury4.0新增
 * Description:         产品化4.0在证券新增审批流,,该功能实现查找我的任务
 */

import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.securities.mywork.dataentity.SecMyWorkInfo;
import com.iss.itreasury.system.approval.dataentity.InutApprovalWorkInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class SecDeliveryActionDao extends SecMyWorkDao {

	protected void getCancelApprovalSql(SecMyWorkInfo secMyWorkInfo)
			throws IException {
		// TODO Auto-generated method stub

	}

	protected void getFinishedWorkSql(SecMyWorkInfo secMyWorkInfo) {
		// TODO Auto-generated method stub

	}

	protected void getRefuseWorkSql(SecMyWorkInfo secMyWorkInfo)
			throws IException {
		// TODO Auto-generated method stub

	}

	protected Collection queryCurrentWork(SecMyWorkInfo secMyWorkInfo)throws IException {
		Vector v_Return = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		
		sbSQL.append("select sappr.*,");
		sbSQL.append(" sdeli.id SecId,");
		sbSQL.append(" sdeli.code SecCode,");
		sbSQL.append(" sclie.name ClientName,");
		sbSQL.append(" sstoc.name StockHolderAccountName,");
		sbSQL.append(" sbusi.id BusinessTypeId,");
		sbSQL.append(" sbusi.name BusinessTypeName,");
		sbSQL.append(" stran.id TransactionTypeId,");
		sbSQL.append(" stran.name TransactionTypeName,");
		sbSQL.append(" sdeli.inputdate InputDate,");
		sbSQL.append(" scoun.id BankOfDepositId,");	
		sbSQL.append(" scoun.name BankOfDepositName,");
		sbSQL.append(" sacco.accountcode AccountCode,");
		sbSQL.append(" sdeli.inputuserid InputUserId,");
		sbSQL.append(" useri.sname InputUserName");
		sbSQL.append(" ");
		sbSQL.append("from SEC_DELIVERYORDER sdeli,");
		sbSQL.append(" SEC_APPLYFORM sappl,");
		sbSQL.append(" SEC_CLIENT sclie,");
		sbSQL.append(" SEC_BUSINESSTYPE sbusi,");
		sbSQL.append(" SEC_BUSINESSATTRIBUTE sbusn,");
		sbSQL.append(" SEC_TRANSACTIONTYPE stran,");
		sbSQL.append(" SEC_ACCOUNT sacco,");
		sbSQL.append(" SEC_SECURITIES ssecu,");
		sbSQL.append(" SEC_STOCKHOLDERACCOUNT sstoc,");
		sbSQL.append(" USERINFO useri,");
		sbSQL.append(" (select * from SEC_COUNTERPART where ISBANK is null or ISBANK = -1) scoun,");	
		sbSQL.append(" SYS_APPROVALRECORD sappr");
		sbSQL.append(" ");
		sbSQL.append("where sdeli.APPLYFORMID = sappl.ID(+)");
		sbSQL.append(" and sdeli.ClientID = sclie.id(+)");
		sbSQL.append(" and sdeli.TransactionTypeID = stran.id(+)");
		sbSQL.append(" and sdeli.CounterpartID = scoun.id(+)");
		sbSQL.append(" and sdeli.accountID = sacco.id(+)");
		sbSQL.append(" and sdeli.SECURITIESID = ssecu.ID(+)");
		
		sbSQL.append(" and sdeli.ISRELATEDBYNOTICEFORM <> 1");
		
		sbSQL.append(" and sacco.STOCKHOLDERACCOUNTID1 = sstoc.ID(+)");
		sbSQL.append(" and sbusn.id = sbusi.BUSINESSATTRIBUTEID");
		sbSQL.append(" and sappl.inputuserid = useri.id");
		
		sbSQL.append(" and sappr.moduleid = " + Constant.ModuleType.SECURITIES);
		//sbSQL.append(" and sappr.officeid = " + secMyWorkInfo.getOfficeID());
		//sbSQL.append(" and sappr.currencyid = " + secMyWorkInfo.getCurrencyID());	
		sbSQL.append(" and sappr.transid = sdeli.id");
		sbSQL.append(" and sappr.transtypeid = sbusi.id");
		sbSQL.append(" and sappr.actionid = stran.id");
		sbSQL.append(" and sappr.approvalentryid in (" + secMyWorkInfo.getApprovalEntryIDs() + ")");
		sbSQL.append(" order by sappr.id desc");
		
		try {
			initDAO();
			prepareStatement(sbSQL.toString());
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

	protected Collection queryWaitDealWithWork(SecMyWorkInfo secMyWorkInfo)
			throws IException {
		// TODO Auto-generated method stub
		return null;
	}

}
