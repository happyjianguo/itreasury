package com.iss.itreasury.loan.leasehold.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo;
import com.iss.itreasury.loan.loanapply.dataentity.LoanPlanDetailInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANConstant.AssureChargeNoticeStatus;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Constant.ApprovalAction;
import com.iss.itreasury.util.Constant.ApprovalDecision;
import com.iss.itreasury.util.Constant.ModuleType;
import com.iss.itreasury.util.Constant.PageControl;
import com.iss.itreasury.util.Constant.RecordStatus;
import java.sql.Timestamp;
import com.iss.itreasury.util.DataFormat;

/**
 * 
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: 信贷管理－融资租赁
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: iSoftStone
 * </p>
 * 
 * @author liuxz
 * @version 1.0
 */
public class LoanLeaseholdRepayFormDao extends LoanDAO {
	public LoanLeaseholdRepayFormDao() {
		super("LOAN_LEASEHOLDREPAYFORM");
	}

	/**
	 * 融资租赁还款通知单的保存操作
	 * 
	 * @param repaynoticeInfo
	 *            LeaseholdRepayNoticeInfo
	 * @return long
	 * @throws RemoteException
	 * @throws LoanException
	 */
	public long saveRepayNotice(LeaseholdRepayNoticeInfo repaynoticeInfo)
			throws RemoteException, LoanException {
		long returnRes = -1l;
		try {
			if (repaynoticeInfo == null) {
				return returnRes;
			} else if (repaynoticeInfo.getId() <= 0) {
				/** 更新通知单表 */
				setUseMaxID();
				returnRes = add(repaynoticeInfo);
			} else if (repaynoticeInfo.getId() > 0) {
				/** 更新通知单表 */
				update(repaynoticeInfo);
				returnRes = repaynoticeInfo.getId();
			}

			InutParameterInfo inutParameterInfo = repaynoticeInfo
					.getInutParameterInfo();

			if (inutParameterInfo != null) {
				inutParameterInfo.setTransID(String.valueOf(returnRes));
				inutParameterInfo
						.setUrl(inutParameterInfo.getUrl() + returnRes);
				inutParameterInfo.setDataEntity(repaynoticeInfo);

				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);

				updateLeaseholdNoticeStatus(returnRes,
						LOANConstant.AssureChargeNoticeStatus.APPROVALING);
			}
		} catch (Exception e) {
			log.error(" 融资租赁还款通知单的保存操作失败原因：\r\n" + e);
			throw new LoanException("Gen_E001", e);
		}
		return returnRes;
	}

	/**
	 * 融资租赁还款通知单的取消操作
	 * 
	 * @param repaynoticeid
	 *            long
	 * @throws RemoteException
	 * @throws LoanException
	 */
	public void cancelRepayNotice(long repaynoticeid) throws RemoteException,
			LoanException {
		LeaseholdRepayNoticeInfo repaynoticeinfo;
		try {
			repaynoticeinfo = new LeaseholdRepayNoticeInfo();
			repaynoticeinfo.setId(repaynoticeid);
			repaynoticeinfo.setNStatusId(AssureChargeNoticeStatus.CANCEL);
			update(repaynoticeinfo);
			doAfterCancel(repaynoticeid);
		} catch (Exception e) {
			log.error(" 融资租赁还款通知单的取消操作失败原因：\r\n" + e);
			throw new LoanException("Gen_E001", e);
		}
	}

	/**
	 * 融资租赁还款通知单的审核操作
	 * 
	 * @param info
	 *            ApprovalTracingInfo
	 * @throws RemoteException
	 * @throws LoanDAOException
	 */
	public void checkRepayNotice(ApprovalTracingInfo info)
			throws RemoteException, LoanDAOException {
		long lCount = 0;
		long lStatusID = -1;
		long lResultID = -1;
		long lApprovalID = -1;
		long[] lApprovalContentIDList;
		// 定义相应操作常量
		// 模块类型
		long lModuleID = LOANConstant.ModuleType.LOAN;
		info.setModuleID(lModuleID);
		// 操作类型
		long lActionID = LOANConstant.ApprovalAction.LEASEHOLDREPAY_NOTICE;
		info.setActionID(lActionID);

		String sContractID = LOANNameRef.getNameByID("contractid",
				"LOAN_LEASEHOLDREPAYFORM", "id", String.valueOf(info
						.getApprovalContentIDList()[0]), null);
		long conID = -1;
		if (sContractID != null && sContractID.length() > 0) {
			conID = Long.valueOf(sContractID).longValue();
		}
		long lLoanTypeID = -1;
		String sSubType = LOANNameRef.getSubTypeByContractID(conID);
		if (sSubType != null && sSubType.length() > 0) {
			lLoanTypeID = Long.valueOf(sSubType).longValue();
		}
		info.setLoanTypeID(lLoanTypeID);
		ApprovalDelegation appbiz = new ApprovalDelegation();
		lApprovalContentIDList = info.getApprovalContentIDList();
		if (lApprovalContentIDList.length > 0) {
			try {
				// 获得ApprovalID
				lApprovalID = appbiz.getApprovalID(lModuleID, lLoanTypeID,
						lActionID, info.getOfficeID(), info.getCurrencyID());
				info.setApprovalID(lApprovalID);
			} catch (Exception e1) {
				log.error("getApprovalID fail");
				e1.printStackTrace();
			}
			// 处理审批意见
			if (info.getCheckActionID() == LOANConstant.Actions.REJECT) { // 拒绝
				// 审批意见状态
				lStatusID = RecordStatus.VALID;
				// 审批操作类型
				lResultID = ApprovalDecision.REFUSE;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECK) { // 审批
				lStatusID = RecordStatus.VALID;
				lResultID = ApprovalDecision.PASS;
			}
			if (info.getCheckActionID() == LOANConstant.Actions.CHECKOVER) { // 审批&&最后
				lStatusID = RecordStatus.VALID;
				lResultID = ApprovalDecision.FINISH;
				// 审批完成后需要做的操作
			}
			if (info.getCheckActionID() == LOANConstant.Actions.RETURN) { // 修改
				lStatusID = RecordStatus.VALID;
				lResultID = ApprovalDecision.RETURN;
			}
			info.setApprovalID(lApprovalID);
			info.setResultID(lResultID);
			info.setStatusID(lStatusID);
			lCount = lApprovalContentIDList.length;
			for (int i = 0; i < lCount; i++) {
				if (lApprovalContentIDList[i] > 0) {
					info.setApprovalContentID(lApprovalContentIDList[i]);
				} else {
					break;
				}
				// 审核通知单
				check(info);
				try {
					appbiz.saveApprovalTracing(info);
				} catch (Exception e) {
					log.error("saveApprovalTracing fail");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 融资租赁还款通知单的单笔查询操作
	 * 
	 * @param repaynoticeid
	 *            long
	 * @return LeaseholdRepayNoticeInfo
	 * @throws RemoteException
	 * @throws LoanException
	 */
	public LeaseholdRepayNoticeInfo findRepayNoticeByID(long repaynoticeid)
			throws RemoteException, LoanException {
		LeaseholdRepayNoticeInfo repaynoticeinfo = new LeaseholdRepayNoticeInfo();
		try {
			repaynoticeinfo = (LeaseholdRepayNoticeInfo) findByID(
					repaynoticeid, repaynoticeinfo.getClass());
			// 还款通知单对应的收款通知单id,结算处理时使用
			initDAO();
			String strSQL = "";
			strSQL = " select ID from LOAN_ASSURECHARGEFORM "
					+ " where CONTRACTID=" + repaynoticeinfo.getContractId()
					+ " and STATUSID = "
					+ LOANConstant.LoanPayNoticeStatus.USED
					+ " and RECOGNIZANCEAMOUNT > 0 ";
			prepareStatement(strSQL);
			executeQuery();
			if (transRS != null && transRS.next()) {
				repaynoticeinfo.setAssureChargeFormID(transRS.getLong(1));
			}
		} catch (Exception e) {
			log.error(" 融资租赁收款通知单的单笔查询操作失败原因：\r\n" + e);
			throw new LoanException("Gen_E001", e);
		} finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				throw new LoanDAOException(e1);
			}
		}
		return repaynoticeinfo;
	}

	/**
	 * 融资租赁还款通知单的多笔查询操作
	 * 
	 * @param repaynoticeInfo
	 *            LeaseholdRepayNoticeInfo
	 * @return Collection
	 * @throws RemoteException
	 * @throws LoanException
	 */
	public Collection findRepayNoticeByMultiOption(
			LeaseholdRepayNoticeInfo repaynoticeInfo) throws RemoteException,
			LoanException {
		Collection c = null;
		long lApprovalID = -1l;
		String strUser = "";
		// 定义相应操作常量
		// 模块类型
		long lModuleID = ModuleType.LOAN;
		// 业务类型
		long lLoanTypeID = LOANConstant.LoanType.RZZL;
		// 操作类型
		long lActionID = ApprovalAction.LEASEHOLDREPAY_NOTICE;

		LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
		ApprovalDelegation appBiz = new ApprovalDelegation();

		// 获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		try {
			long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
					repaynoticeInfo.getOfficeId(), repaynoticeInfo
							.getCurrencyId(), new long[] { lLoanTypeID });
			if (a_SubLoanType != null && a_SubLoanType.length > 0) {
				strUser = "( ";
				for (int i = 0; i < a_SubLoanType.length; i++) {
					strUser = strUser
							+ " (a.NEXTCHECKUSERID in "
							+ appBiz.findTheVeryUser(lModuleID,
									a_SubLoanType[i], lActionID,
									repaynoticeInfo.getOfficeId(),
									repaynoticeInfo.getCurrencyId(),
									repaynoticeInfo.getUserID())
							+ " and b.NSUBTYPEID = " + a_SubLoanType[i] + ")";
					if (i < a_SubLoanType.length - 1)
						strUser += " or ";
					else
						strUser += " ) ";
				}
			} else {
				return null;
			}
			repaynoticeInfo.setStrUser(strUser);
		} catch (Exception e2) {
			log.error("获得真正来审批这个记录的人失败原因：\r\n" + e2);
			e2.printStackTrace();
		}
		try {
			c = findByMultiObj(repaynoticeInfo);
		} catch (Exception e) {
			throw new LoanException("Gen_E001", e);
		}
		return c;
	}

	/**
	 * 判断在LOAN_ASSUREADJUSTDETAIL表中是否有生效日期大于等于本次审核记录的生效日的日期 如果有，则将原有记录置为无效
	 * 
	 * @param lApplyID
	 *            long
	 * @throws LoanException
	 * @throws LoanDAOException
	 */
	protected void doAfterCancel(long lApplyID) throws LoanException,
			LoanDAOException {
		String strSQL = "";
		long lStatusID = -1;
		long lContractID = -1;
		try {
			initDAO();
			strSQL = " select b.nstatusid contractStatusID,b.id contractID from LOAN_ASSURECHARGEFORM a,loan_contractform b "
					+ " where a.contractid = b.id and a.id = " + lApplyID;
			try {
				prepareStatement(strSQL);
				ResultSet rs = executeQuery();
				if (rs != null && rs.next()) {
					lStatusID = rs.getLong(1);
					lContractID = rs.getLong(2);
				}
				if (lStatusID == LOANConstant.ContractStatus.FINISH
						&& lContractID > 0) {
					strSQL = "UPDATE loan_contractform SET nStatusID = "
							+ LOANConstant.ContractStatus.ACTIVE
							+ " where id = " + lContractID;
					log.debug(strSQL);
					prepareStatement(strSQL);
					executeUpdate();
				}
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			} catch (SQLException e) {
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			}
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
		} catch (Exception e) {
			throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
		} finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				throw new LoanDAOException(e1);
			}
		}
	}

	protected Collection findByMultiObj(LeaseholdRepayNoticeInfo repaynoticeinfo)
			throws LoanDAOException {
		String strSQL = "";
		String strSelect = "";

		Vector v = new Vector();

		String contractCodeStart = repaynoticeinfo.getContractCodeStart(); // 融资租赁合同编号
		String contractCodeEnd = repaynoticeinfo.getContractCodeEnd(); // 融资租赁合同编号
		long clientIDStart = repaynoticeinfo.getClientIDStart(); // 承租单位ID
		long clientIDEnd = repaynoticeinfo.getClientIDEnd(); // 承租单位ID
		Timestamp startDate = repaynoticeinfo.getQueryStartDate(); // 租赁日期
		Timestamp endDate = repaynoticeinfo.getQueryEndDate(); // 租赁日期
		long statusID = repaynoticeinfo.getNStatusId(); // 状态
		long currencyID = repaynoticeinfo.getCurrencyId(); // 币种
		long officeID = repaynoticeinfo.getOfficeId(); // 办事处

		long clientID = repaynoticeinfo.getClientId();
		long userID = repaynoticeinfo.getInputUserId();
		String strUser = repaynoticeinfo.getStrUser();
		long queryPurpose = repaynoticeinfo.getQueryPurpose();
		long pageLineCount = repaynoticeinfo.getPageLineCount();
		long pageNo = repaynoticeinfo.getPageNo();
		long desc = repaynoticeinfo.getDesc();
		String orderParamString = repaynoticeinfo.getOrderParamString();
		long recordCount = -1l;
		long pageCount = -1l;
		long rowNumStart = -1l;
		long rowNumEnd = -1l;

		try {
			initDAO();
			// 计算记录总数
			if (queryPurpose == 1l) { // for modify
				strSQL = "";
				strSelect = " select count(*) ";
				strSQL = " from LOAN_LEASEHOLDREPAYFORM a,Loan_ContractForm b,Client c "
						+ " where a.CONTRACTID = b.ID and b.nBorrowClientID = c.ID(+) "
						+ " and a.NSTATUSID >= "
						+ LOANConstant.LoanPayNoticeStatus.SUBMIT
						+ " and a.NSTATUSID <= "
						+ LOANConstant.LoanPayNoticeStatus.CHECK
						+ " and a.INPUTUSERID = " + userID;
				if (statusID == LOANConstant.LoanPayNoticeStatus.SUBMIT) {
					strSQL += " and a.nextCheckLevel = 1 ";
					strSQL += " and a.NSTATUSID = "
							+ LOANConstant.LoanPayNoticeStatus.SUBMIT;
				} else {
					strSQL += " and a.NSTATUSID = "
							+ LOANConstant.LoanPayNoticeStatus.CHECK;
				}
			} else if (queryPurpose == 2) { // for examine
				strSelect = " select count(*) ";
				strSQL = " from LOAN_LEASEHOLDREPAYFORM a,Loan_ContractForm b,Client c "
						+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) "
						+ " and " + strUser;
				if (statusID == LOANConstant.LoanPayNoticeStatus.SUBMIT) {
					strSQL += " and a.NSTATUSID = "
							+ LOANConstant.LoanPayNoticeStatus.SUBMIT;
				} else {
					strSQL += " and a.NSTATUSID = "
							+ LOANConstant.LoanPayNoticeStatus.SUBMIT;
				}
			}
			// ////////////////////查询条件///////////////////////////////////////
			if (clientID != -1) {
				strSQL += " and b.nBorrowClientID = " + clientID;
			}
			if (officeID > 0) {
				strSQL += " and a.OFFICEID = " + officeID;
			}
			if (currencyID > 0) {
				strSQL += " and a.CURRENCYID = " + currencyID;
			}
			if (contractCodeStart != null && contractCodeStart.length() > 0) {
				strSQL += " and b.sContractCode >= '" + contractCodeStart + "'";
			}
			if (contractCodeEnd != null && contractCodeEnd.length() > 0) {
				strSQL += " and b.sContractCode <= '" + contractCodeEnd + "'";
			}
			if (clientIDStart > 0) {
				strSQL += " and b.nBorrowClientID >= " + clientIDStart;
			}
			if (clientIDEnd > 0) {
				strSQL += " and b.nBorrowClientID <= " + clientIDEnd;
			}
			if (startDate != null) {
				strSQL += " and to_char(b.dtStartDate,'yyyy-mm-dd') >= '"
						+ DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null) {
				strSQL += " and to_char(b.dtEndDate,'yyyy-mm-dd') <= '"
						+ DataFormat.getDateString(endDate) + "'";
			}
			if (userID > 0) {
				strSQL += " and a.INPUTUSERID = " + userID;
			}
			// //////////////////////////排序处理/////////////////////////////////
			int nIndex = 0;
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0) {
				if (orderParamString.substring(0, nIndex).toLowerCase().equals(
						"loan_leaseholdrepayform")) {
					strSQL += " order by a."
							+ orderParamString.substring(nIndex + 1);
				} else if (orderParamString.substring(0, nIndex).toLowerCase()
						.equals("loan_contractform")) {
					strSQL += " order by b."
							+ orderParamString.substring(nIndex + 1);
				} else if (orderParamString.substring(0, nIndex).toLowerCase()
						.equals("client")) {
					strSQL += " order by c."
							+ orderParamString.substring(nIndex + 1);
				}
			} else {
				strSQL += " order by a.ID";
			}
			if (desc == PageControl.CODE_ASCORDESC_DESC) {
				strSQL += " desc";
			}
			log.debug(strSelect + strSQL);
			try {
				prepareStatement(strSelect + strSQL);
				ResultSet rs = executeQuery();
				if (rs != null && rs.next()) {
					recordCount = rs.getLong(1);
				}
			} catch (ITreasuryDAOException e) {
				log.error("批量查询通知单笔数产生错误 : " + e.getMessage());
				throw new LoanDAOException("批量查询通知单笔数产生错误", e);
			} catch (SQLException e) {
				log.error("批量查询通知单笔数产生错误 : " + e.getMessage());
				throw new LoanDAOException("批量查询通知单笔数产生错误", e);
			}

			// 计算总页数
			if (recordCount > pageLineCount) {
				pageCount = recordCount / pageLineCount;
				if ((recordCount % pageLineCount) != 0) {
					pageCount++;
				}
			} else if (recordCount > 0 && recordCount <= pageLineCount) {
				pageCount = 1;
			} else {
				pageCount = 0;
			}

			// ////////////////返回需求的结果集////////////////////////////////////
			rowNumStart = (pageNo - 1) * pageLineCount + 1;
			rowNumEnd = rowNumStart + pageLineCount - 1;
			strSelect = " select a.*,b.sContractCode,c.sName,c.id ClientID ";
			strSQL = " select * from ( select aa.*,rownum r from ( "
					+ strSelect + strSQL;
			strSQL += " ) aa ) where r between " + rowNumStart + " and "
					+ rowNumEnd;
			log.debug(strSQL);
			try {
				prepareStatement(strSQL);
				ResultSet rs1 = executeQuery();
				while (rs1 != null && rs1.next()) {
					LeaseholdRepayNoticeInfo assureChargeNoticeInfo = new LeaseholdRepayNoticeInfo();
					assureChargeNoticeInfo.setId(rs1.getLong("ID")); // id
					assureChargeNoticeInfo.setContractId(rs1
							.getLong("CONTRACTID")); // CONTRACTID
					assureChargeNoticeInfo.setOfficeId(rs1.getLong("OfficeID")); // 办事处
					assureChargeNoticeInfo.setCurrencyId(rs1
							.getLong("CurrencyID")); // 币种
					assureChargeNoticeInfo.setCode(rs1.getString("Code")); // 申请书编号
					assureChargeNoticeInfo.setClientId(rs1.getLong("ClientID")); //
					assureChargeNoticeInfo.setAmount(rs1.getDouble("AMOUNT")); // 承保金额
					assureChargeNoticeInfo.setInterestAmount(rs1
							.getDouble("INTERESTAMOUNT"));// 利息
					assureChargeNoticeInfo.setRecognizanceAccountId(rs1
							.getLong("RECOGNIZANCEACCOUNTID")); // 保证金账户
					assureChargeNoticeInfo.setRecognizanceAmount(rs1
							.getDouble("RECOGNIZANCEAMOUNT")); //
					assureChargeNoticeInfo.setNextCheckUserId(rs1
							.getLong("NextCheckUserID")); // 下一级审核人
					assureChargeNoticeInfo.setNextCheckLevel(rs1
							.getLong("NextCheckLevel")); // 下一级审核级别
					assureChargeNoticeInfo.setInputUserId(rs1
							.getLong("InputUserID")); // 录入人
					assureChargeNoticeInfo.setInputDate(rs1
							.getTimestamp("InputDate")); // 录入时间
					assureChargeNoticeInfo.setNStatusId(rs1
							.getLong("nStatusID")); // 状态
					assureChargeNoticeInfo.setIsLowLevel(rs1
							.getLong("IsLowLevel")); // 是否最低审核级别
					// 表中没有的字段
					assureChargeNoticeInfo
							.setClientName(rs1.getString("sName")); //
					assureChargeNoticeInfo.setContractCode(rs1
							.getString("sContractCode")); //
					assureChargeNoticeInfo.setRecordCount(recordCount); // 记录数
					assureChargeNoticeInfo.setPageCount(pageCount); // 页数
					v.add(assureChargeNoticeInfo);
				}
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException("批量查询申请书产生错误", e);
			} catch (SQLException e) {
				throw new LoanDAOException("批量查询申请书产生错误", e);
			}
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				log.error("批量查询通知单笔数产生错误 : " + e.getMessage());
				throw new LoanDAOException(e);
			}
		} catch (Exception e) {
			throw new LoanDAOException("批量查询通知单笔数产生错误", e);
		} finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				throw new LoanDAOException(e1);
			}
		}
		return (v.size() > 0 ? v : null);
	}

	protected long check(ApprovalTracingInfo ATInfo) throws LoanDAOException {
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";
		// 定义相应操作常量
		// 模块类型
		long lModuleID = ATInfo.getModuleID();
		// 业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		// 操作类型
		long lActionID = ATInfo.getActionID();
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		// long lNextLevel = ATInfo.getNextLevel();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		LeaseholdRepayNoticeInfo aInfo = new LeaseholdRepayNoticeInfo();
		LeaseholdRepayNoticeInfo tempInfo = new LeaseholdRepayNoticeInfo();
		try {
			tempInfo = (LeaseholdRepayNoticeInfo) findByID(lApprovalContentID,
					tempInfo.getClass());
		} catch (ITreasuryDAOException e) {
			throw new LoanDAOException(e);
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) { // 拒绝
			aInfo.setId(lApprovalContentID);
			aInfo.setNStatusId(LOANConstant.AssureChargeNoticeStatus.REFUSE);
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) { // 审批
			aInfo.setId(lApprovalContentID);
			aInfo.setNStatusId(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserId(lNextUserID);
			ApprovalDelegation appbiz = new ApprovalDelegation();
			try {
				aInfo.setNextCheckLevel(appbiz.findApprovalUserLevel(
						lApprovalID, lNextUserID));
			} catch (Exception e) {
				throw new LoanDAOException("审批", e);
			}
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER) { // 审批&&最后
			aInfo.setId(lApprovalContentID);
			aInfo.setNStatusId(LOANConstant.AssureChargeNoticeStatus.CHECK);
			aInfo.setNextCheckUserId(lNextUserID);
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
			// 审批完成后需要做的操作
			doAfterCheckOver(lApprovalContentID);
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) { // 修改
			aInfo.setId(lApprovalContentID);
			aInfo.setNStatusId(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserId(ATInfo.getInputUserID());
			// 置下一级审核为1
			aInfo.setNextCheckLevel(1);
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
		}
		log.debug("check end");
		return lApprovalContentID;
	}

	private void doAfterCheckOver(long lApplyID) throws LoanDAOException {
	}

	public String getApplyCode(long lContractID) throws LoanDAOException {
		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2, 4);
		try {
			try {
				initDAO();
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
			strSQL = " select max(nvl(Code,0)) Code from " + strTableName
					+ " where ContractID = " + lContractID;
			// log4j.debug(strSQL);
			try {
				prepareStatement(strSQL);
				ResultSet rs = executeQuery();
				if (rs != null && rs.next()) {
					strCode = rs.getString(1);
					// log4j.debug(strCode);
					if (strCode != null && strCode.length() > 0) {
						lCode = Long.parseLong(strCode) + 1;
					} else {
						lCode = 1;
					}
					strCode = DataFormat.formatInt(lCode, 3, true);
				}
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException("生成通知单编号产生错误", e);
			} catch (SQLException e) {
				throw new LoanDAOException("生成通知单编号产生错误", e);
			}
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// log4j.debug(":::::::::: ::::strcode::::::" + strCode);
		return strCode;
	}

	/**
	 * 根据日期获得融资租赁该期的租金偿付情况
	 * 
	 * @param strDate
	 *            String
	 * @return LoanPlanDetailInfo
	 * @throws RemoteException
	 */
	public LoanPlanDetailInfo getPlanDetailInfo(String strDate, long lLoanID)
			throws IException {
		LoanPlanDetailInfo returnInfo = new LoanPlanDetailInfo();
		String strSQL = "";
		try {
			try {
				initDAO();
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
			strSQL = "select * from loan_loanformplandetail lpd, loan_loanformplan lp "
					+ " where lpd.NPLANID = lp.id and lpd.dtplandate >= to_date('"
					+ strDate
					+ "','yyyy-mm-dd')"
					+ " and lp.NLOANID= "
					+ lLoanID + " order by lpd.dtplandate asc ";
			try {
				prepareStatement(strSQL);
				ResultSet rs = executeQuery();
				if (rs != null && rs.next()) {
					returnInfo.setRecognizanceAmount(rs
							.getDouble("MRECOGNIZANCEAMOUNT"));
					returnInfo.setInterestAmount(rs
							.getDouble("MINTERESTAMOUNT"));
					returnInfo.setAmount(rs.getDouble("MAMOUNT"));
				}
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			} catch (SQLException e) {
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			}
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return returnInfo;
	}

	/**
	 * added by xiong fei 2010-07-27 根据合同ID来获取该期计划信息
	 * 
	 * @param strDate
	 *            String
	 * @return LoanPlanDetailInfo
	 * @throws RemoteException
	 */
	public LoanPlanDetailInfo getRZZLPlanDetailInfo(long contractID)
			throws IException {
		LoanPlanDetailInfo returnInfo = new LoanPlanDetailInfo();
		StringBuffer strSQL = new StringBuffer();
		try {
			try {
				initDAO();
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
			strSQL.append("select MAMOUNT,MINTERESTAMOUNT,mrecognizanceamount from( ");
			strSQL.append(" SELECT id,NCONTRACTPLANID,DTPLANDATE,round(MAMOUNT,2) as MAMOUNT, round(MINTERESTAMOUNT,2) as MINTERESTAMOUNT,"); 
			strSQL.append(" round(mrecognizanceamount ,2) as mrecognizanceamount ,");
			strSQL.append(" RANK() OVER(PARTITION BY NCONTRACTPLANID ORDER BY DTPLANDATE) as issue ");
			strSQL.append(" FROM loan_loancontractplandetail where ncontractplanid = (");
			strSQL.append(" select max(id) from  loan_loancontractplan ");
			strSQL.append(" where  NSTATUSID = 1 and NISUSED = 2 and NCONTRACTID = "+contractID+")");
			strSQL.append(" )where issue = (");
			strSQL.append(" nvl((select max(issue)+1 from sett_transreturnfinance where ncontractid='"+contractID+"' and nstatusid=3),1))");

			try {
				prepareStatement(strSQL.toString());
				ResultSet rs = executeQuery();
				if (rs != null && rs.next()) {
					returnInfo.setRecognizanceAmount(rs
							.getDouble("MRECOGNIZANCEAMOUNT"));
					returnInfo.setInterestAmount(rs
							.getDouble("MINTERESTAMOUNT"));
					returnInfo.setAmount(rs.getDouble("MAMOUNT"));
				}
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			} catch (SQLException e) {
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			}
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				finalizeDAO();
			} catch (ITreasuryDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return returnInfo;
	}

	public static void main(String[] args) {
		LoanLeaseholdRepayFormDao dao = new LoanLeaseholdRepayFormDao();

		try {
			LeaseholdRepayNoticeInfo info = new LeaseholdRepayNoticeInfo();
			info.setQueryPurpose(1);
			info.setInputUserId(1);
			info.setOfficeId(1);
			info.setCurrencyId(1);
			// info.setPageCount(1);
			info.setPageNo(1);
			info.setPageLineCount(10);

			// Collection c = dao.findByMultiOption(info);
			Collection c = dao.findByMultiObj(info);

			System.out.println("ok = " + c.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// added by qhzhou 2007.07.06
	public long updateLeaseholdNoticeStatus(long lId, long statusId)
			throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update LOAN_LEASEHOLDREPAYFORM  set NSTATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log
						.print(" update loan updateAssureManagementNoticeStatus error : "
								+ lResult);
				return -1;
			} else {
				return lId;
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
	}

	/**
	 * @param lContractID
	 *            合同ID
	 * @param rePayID
	 *            还款通知单ID
	 * @return 查询未还款金额
	 * @throws RemoteException
	 * @throws IException
	 */
	public double findUnRePayAmountByID(long lContractID, long rePayID)
			throws Exception {
		double unRePayAmount = 0.0;
		double rePayAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lContractID > 0) {
				// modified by xiongfei 2010-07-26 还款总额从结算表里选
				sbSQL.append(" select sum(b.mcorpusamount) RepayAmount ");
				sbSQL.append(" from sett_transreturnfinance b ");
				sbSQL.append(" where b.ncontractid = ? and b.nstatusid = 3 ");
				// if(rePayID>0)
				// {
				// sbSQL.append(" and lep.ID<>? ");
				// }
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				// ps.setLong(2, LOANConstant.AheadRepayStatus.REFUSE);
				// ps.setLong(3, LOANConstant.AheadRepayStatus.CANCEL);
				// ps.setLong(4, LOANConstant.AheadRepayStatus.USED);
				// if(rePayID>0)
				// {
				// ps.setLong(5, rePayID);
				// }
				rs = ps.executeQuery();

				if (rs.next()) {
					rePayAmount = rs.getDouble("RepayAmount");
				}

				ps.close();
				ps = null;
				ContractDao cDao = new ContractDao();
				ContractInfo cInfo = cDao.findByID(lContractID);

				// 合同金额减去已生成还款单金额
				unRePayAmount = UtilOperation.Arith.round(UtilOperation.Arith
						.sub(cInfo.getExamineAmount(), rePayAmount), 2);
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new IException("Gen_E001");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (Exception e) {
				log.error(e.toString());
				throw new IException("Gen_E001");
			}
		}

		return unRePayAmount;
	}

	/**
	 * @author yunchang
	 * @date 2010-09-08 17:31
	 * @param contractID 合同ID
	 * @param sysDate 系统开机日
	 * @return String
	 * @throws RemoteException
	 * @function 提前还款：离开机日最近的一期是否还过，如果为""则表示并没有还过，如果不为空，则表示归还过。
	 */
	public String getLastIssue(long contractID,String sysDate) throws IException 
	{
		/*
		 * 定义临时期数
		 */
		String issueTemp = "";
		/*
		 * 设置查询SQLBuffer
		 */
		StringBuffer stringBufferSQL = new StringBuffer();
		try 
		{
			/*
			 * 初始化查询DAO
			 */
			try 
			{
				initDAO();
			} 
			catch (ITreasuryDAOException e) 
			{
				throw new LoanDAOException(e);
			}
			/*
			 * 拼写查询SQL
			 */
			stringBufferSQL.append(" select a.issue as issue from ( select id, ncontractplanid, dtplandate, round( mamount, 2 ) as mamount, round( minterestamount , 2 ) as minterestamount, ");
			stringBufferSQL.append(" round( mrecognizanceamount, 2 ) as mrecognizanceamount, rank() over( partition by ncontractplanid order by dtplandate ) as issue ");
			stringBufferSQL.append(" from loan_loancontractplandetail where ncontractplanid = ( select max(id)  from loan_loancontractplan where nstatusid = 1 ");
			stringBufferSQL.append(" and nisused = 2 and ncontractid = " + contractID + ")) a, (select issue from sett_transreturnfinance where ncontractid =  " + contractID );
			stringBufferSQL.append(" and nstatusid = 3 ) b where a.issue(+) = b.issue and a.dtplandate >= to_date('" + sysDate + "', 'yyyy-mm-dd') and rownum = 1 ");
			/*
			 * 打印SQL
			 */
			System.out.println("============================================================");
			System.out.println("提前还款：离开机日最近的一期是否还过，如果为\"\"则表示并没有还过，如果不为空，则表示归还过:");
			System.out.println(stringBufferSQL.toString()); 
			System.out.println("============================================================");
			/*
			 * 
			 */
			try 
			{
				/*
				 * 执行操作
				 */
				prepareStatement(stringBufferSQL.toString());
				/*
				 * 结果集
				 */
				ResultSet resultSet = executeQuery();
				/*
				 * 判断
				 */
				if (resultSet != null && resultSet.next()) 
				{
					issueTemp = resultSet.getString("issue");
				}	
			} 
			catch (ITreasuryDAOException e) 
			{
				throw new LoanDAOException("获取合同还款期数产生错误", e);
			} 
			catch (SQLException e) 
			{
				throw new LoanDAOException("获取合同还款期数产生错误", e);
			}
			try 
			{
				/*
				 * 关闭DAO连接
				 */
				finalizeDAO();
			} 
			catch (ITreasuryDAOException e) 
			{
				/*
				 * 捕获DAO异常
				 */
				throw new LoanDAOException(e);
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				finalizeDAO();
			} 
			catch (ITreasuryDAOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return issueTemp;
	}	
}
