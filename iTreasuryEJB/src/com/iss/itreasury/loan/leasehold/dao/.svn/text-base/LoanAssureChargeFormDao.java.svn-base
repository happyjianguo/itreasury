package com.iss.itreasury.loan.leasehold.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.*;
import java.text.DateFormat;


import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.loan.util.LOANConstant.AssureChargeNoticeStatus;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant.ApprovalAction;
import com.iss.itreasury.util.Constant.ApprovalDecision;
import com.iss.itreasury.util.Constant.ModuleType;
import com.iss.itreasury.util.Constant.PageControl;
import com.iss.itreasury.util.Constant.RecordStatus;
import com.iss.itreasury.util.Env;

/**
 * 
 * <p>
 * Title: 融资租赁收款通知单
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
public class LoanAssureChargeFormDao extends LoanDAO {

	protected Log4j log = new Log4j(ModuleType.LOAN, this);

	public LoanAssureChargeFormDao() {
		super("LOAN_ASSURECHARGEFORM");
	}

	/**
	 * 融资租赁收款通知单的保存操作
	 * 
	 * @param paynoticeinfo
	 *            LeaseholdPayNoticeInfo
	 * @return long
	 * @throws LoanException
	 */
	public long savePayNotice(LeaseholdPayNoticeInfo paynoticeinfo) throws LoanException 
	{
		long returnRes = -1l;
		double unPayAmount = 0.0;
		
		try 
		{
			//Add Boxu 208年3月12日 并发处理
			if(paynoticeinfo.getContractId() <= 0)
            {
            	throw new IException("合同ID为空"); 
            }
			unPayAmount = this.findUnPayAmountByID(paynoticeinfo.getContractId(),paynoticeinfo.getId());
			//if(paynoticeinfo.getRecognizanceAmount() > unPayAmount)
            //{
        	//    throw new IException("本次发放金额大于合同未录入收款通知单金额 " + DataFormat.formatDisabledAmount(unPayAmount)); 
            //}
			
			if (paynoticeinfo == null) 
			{
				return returnRes;
			} 
			else if (paynoticeinfo.getId() <= 0) 
			{
				/** 更新通知单表 */
				//setUseMaxID();
				
				super.strSequence="Seq_Loan_PayFrm_DiscountCred";
				returnRes = add(paynoticeinfo);
				
			} 
			else if (paynoticeinfo.getId() > 0) 
			{
				/** 更新通知单表 */
				update(paynoticeinfo);
				returnRes = paynoticeinfo.getId();
			}
			
			InutParameterInfo inutParameterInfo = paynoticeinfo.getInutParameterInfo();
			
			if(inutParameterInfo != null){
				inutParameterInfo.setTransID(String.valueOf(returnRes));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl()+returnRes);
				inutParameterInfo.setDataEntity(paynoticeinfo);
				
				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
			    updateLeaseholdNoticeStatus(returnRes, LOANConstant.LoanPayNoticeStatus.APPROVALING);
			}
			
		} catch (Exception e) {
			log.error(" 融资租赁收款通知单的保存操作失败原因：\r\n" + e);
			throw new LoanException("Gen_E001", e);
		}
		return returnRes;
	}

	/**
	 * @author xiong fei 
	 * @date 2010-07-15
	 * @function 融资租赁判断是否能提前还款
	 * @param contractId,officeID,currencyID
	 * @return long 
	 * @throws LoanException
	 * 
	 * @modify by yunchang
	 * @date 2010-11-04 11点29分
	 * @modify 到期日如果跟开机日在同一天。则可以单笔还款还可以进行批量还款
	 */
	public long isAheadRepay(long contractId,long officeID,long currencyID) throws ITreasuryDAOException 
	{
		long returnRes = -1;
		StringBuffer SQL1 = new StringBuffer();
		String SQL2 = "";
		String SQL3 = "";
		String SQL4 = "";
		ResultSet rs = null;
		Map map = new HashMap();//存放从最新计划明细里得到的日期和期数
		int issueD = 0;  //获取之前还款的最大的期数
		int issue = -1 ;//获取还款单表里面的该合同的最大期数
		String planDate = "";//之前还款最大期数的下一期的日期
		String sysDate = Env.getSystemDateString(officeID,currencyID);//获取系统时间
		DateFormat df = DateFormat.getDateInstance();
		try 
		{
			if(contractId <= 0)
			{
				throw new IException("合同ID为空"); 
			}
			SQL1.append(" select dtplandate, ");
			SQL1.append(" rank() over(partition by ncontractplanid order by dtplandate) as issue ");
			SQL1.append(" from loan_loancontractplandetail ");
			SQL1.append(" where ncontractplanid = ( " );
			SQL1.append(" select max(id) as maxplanid ");
			SQL1.append(" from  loan_loancontractplan ");
			SQL1.append(" where  nstatusid = 1 and nisused = 2 and ncontractid = " + contractId );
			SQL1.append(" ) ");
				
			SQL2 = "select max(issue) as issue from sett_transreturnfinance where ncontractid = '"+contractId+"' and nstatusid = 3";
				
			SQL3 = "select max(issue) as issue  from loan_leaseholdrepayform where contractid = "+contractId+"and nstatusid <>5 ";
				
			SQL4 = "select count(*) from loan_rateadjustpaycondition a where a.ncontractid = "+contractId+" and a.nstatusid not in (0,3)";
				
			initDAO();
			prepareStatement(SQL1.toString());
			log.debug(SQL1.toString());
			/**
			 * 得到最计划新明细里的计划日期和期数
			 */
			rs = executeQuery();
			while(rs.next())
			{
				map.put(String.valueOf(rs.getInt("issue")),String.valueOf(rs.getDate("DTPLANDATE")));
			}
			prepareStatement(SQL2);
			log.debug(SQL2);
			rs = executeQuery();
			while(rs.next())
			{
				issueD = rs.getInt("issue");//得到之前还款期数的最大值
			}
			prepareStatement(SQL3);
			log.debug(SQL3);
			rs = executeQuery();
			while(rs.next())
			{
				issue = rs.getInt("issue");//取出还款单里该合同下的最大期数
			}
			prepareStatement(SQL4);
			log.debug(SQL4);
			rs = executeQuery();
			rs.next();
			if(rs.getInt(1) != 0)
			{//如果该合同有正在进行的利率调整
				returnRes = -3;
			}
			else
			{
				planDate = (String)map.get(String.valueOf(issueD+1));//取得之前还款最大期数的下一期的计划还款日期来与当期系统日期比较
				if((issueD+1) != issue)
				{
					//如果还款单表里最大期数不等于本期应还期数
					if(planDate!=null&&!planDate.equals(""))
					{
						//如果该合同下一期还款日期在当前日期之后
						//modify by yunchang
						//date 2010-11-04 11:43
						//function 判断合同下一期还款日期是否在当前日期之后，由于sysdate是当前时间 plandate是0点，所以必须将SysDate放在前面
						if((df.parse(planDate)).after(df.parse(sysDate))||df.parse(planDate).equals(df.parse(sysDate)))
						{
							//如果该合同下一期还款日期在当前日期之后							
							returnRes = issueD;//将已还期数返回
						}
						else
						{
							returnRes = -1;
						}
					}
				}
				else
				{
					returnRes = -2;
				}
			}
		} 
		catch (Exception e) 
		{
			log.error(" 融资租赁提前还款判断日期失败原因：\r\n" + e);
			throw new ITreasuryDAOException("Gen_E001", e);
		}
		finally 
		{
			if(rs!=null)
			{
				try 
				{
					rs.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			finalizeDAO();
		}
		return returnRes;
	}
	
	/**
	 * 融资租赁收款通知单的取消操作
	 * 
	 * @param paynoticeid
	 *            long
	 * @throws LoanException
	 */
	public void cancelPayNotice(long paynoticeid) throws LoanException {
		LeaseholdPayNoticeInfo paynoticeinfo;
		try {
			paynoticeinfo = new LeaseholdPayNoticeInfo();
			paynoticeinfo.setId(paynoticeid);
			/**
			 * <p>
			 * 由于融资租赁收款通知单的状态与担保收费通知单状态所有就用 担保收费通知单状态
			 * </p>
			 */
			paynoticeinfo.setStatusId(AssureChargeNoticeStatus.CANCEL);
			update(paynoticeinfo);
			doAfterCancel(paynoticeid);
		} catch (Exception e) {
			log.error(" 融资租赁收款通知单的取消操作失败原因：\r\n" + e);
			throw new LoanException("Gen_E001", e);
		}
	}

	/**
	 * 融资租赁收款通知单的审核操作
	 * 
	 * @param info
	 *            ApprovalTracingInfo
	 * @throws LoanDAOException
	 */
	public void checkPayNotice(ApprovalTracingInfo info)
			throws LoanDAOException {
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
		long lActionID = LOANConstant.ApprovalAction.LEASEHOLDPAY_NOTICE;
		info.setActionID(lActionID);
		String sContractID = LOANNameRef.getNameByID("contractid",
				"LOAN_ASSURECHARGEFORM", "id", String.valueOf(info
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
				log.error("获得ApprovalID失败原因是:\r\n" + e1);
				throw new LoanDAOException(" 获得ApprovalID失败 ", e1);
			}
			// 处理审批意见
			switch ((int) info.getCheckActionID()) {
			case (int) LOANConstant.Actions.REJECT: {
				// 拒绝
				// 审批意见状态
				lStatusID = RecordStatus.VALID;
				// 审批操作类型
				lResultID = ApprovalDecision.REFUSE;
				break;
			}
			case (int) LOANConstant.Actions.CHECK: {
				// 审批
				lStatusID = RecordStatus.VALID;
				lResultID = ApprovalDecision.PASS;
				break;
			}
			case (int) LOANConstant.Actions.CHECKOVER: {
				// 审批&&最后
				lStatusID = RecordStatus.VALID;
				lResultID = ApprovalDecision.FINISH;
				// 审批完成后需要做的操作
				break;
			}
			case (int) LOANConstant.Actions.RETURN: {
				// 修改
				lStatusID = RecordStatus.VALID;
				lResultID = ApprovalDecision.RETURN;
				break;
			}
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
					log.error(" 审核融资租赁收款通知单时出错 :\r\n" + e);
					throw new LoanDAOException(" 审核融资租赁收款通知单时出错 ", e);
				}
			}
		}
	}

	/**
	 * 融资租赁收款通知单的单笔查询操作
	 * 
	 * @param paynoticeid
	 *            long
	 * @return LeaseholdPayNoticeInfo
	 * @throws LoanException
	 */
	public LeaseholdPayNoticeInfo findPayNoticeByID(long paynoticeid)
			throws LoanException {
		LeaseholdPayNoticeInfo paynoticeinfo = new LeaseholdPayNoticeInfo();
		try {
			paynoticeinfo = (LeaseholdPayNoticeInfo) findByID(paynoticeid,
					paynoticeinfo.getClass());
		} catch (Exception e) {
			log.error(" 融资租赁收款通知单的单笔查询操作失败原因：\r\n" + e);
			throw new LoanException("Gen_E001", e);
		}
		return paynoticeinfo;
	}

	/**
	 * 融资租赁收款通知单的多笔查询操作
	 * 
	 * @param paynoticeinfo
	 *            LeaseholdPayNoticeInfo
	 * @return Collection
	 * @throws LoanException
	 */
	public Collection findPayNoticeByMultiOption(
			LeaseholdPayNoticeInfo paynoticeinfo) throws LoanException {
		Collection c = null;
		long lApprovalID = -1l;
		String strUser = " ( 0 ) ";
		// 定义相应操作常量
		// 模块类型
		long lModuleID = ModuleType.LOAN;
		// 业务类型
		long lLoanTypeID = LOANConstant.LoanType.RZZL;
		// 操作类型
		long lActionID = ApprovalAction.LEASEHOLDPAY_NOTICE;

		LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
		ApprovalDelegation appBiz = new ApprovalDelegation();

		// 得到审批流ID
		try {
			lApprovalID = appBiz.getApprovalID(lModuleID, lLoanTypeID,
					lActionID, paynoticeinfo.getOfficeId(), paynoticeinfo
							.getCurrencyId());
		} catch (Exception e1) {
			log.error("getApprovalID fail");
			e1.printStackTrace();
		}

		// 查询可以做该审核的实际用户（真实或传给的虚拟的人！）
		try {
			long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
					paynoticeinfo.getOfficeId(), paynoticeinfo.getCurrencyId(),
					new long[] { lLoanTypeID });
			if (a_SubLoanType != null && a_SubLoanType.length > 0) {
				strUser = "( ";
				for (int i = 0; i < a_SubLoanType.length; i++) {
					strUser = strUser
							+ " (a.NEXTCHECKUSERID in "
							+ appBiz.findTheVeryUser(lModuleID,
									a_SubLoanType[i], lActionID, paynoticeinfo
											.getOfficeId(), paynoticeinfo
											.getCurrencyId(), paynoticeinfo
											.getUserID())
							+ " and b.NSUBTYPEID = " + a_SubLoanType[i] + ")";
					if (i < a_SubLoanType.length - 1)
						strUser += " or ";
					else
						strUser += " ) ";
				}
			}else{
				return null;
			}
			paynoticeinfo.setStrUser(strUser);
		} catch (Exception e2) {
			log.error("获得真正来审批这个记录的人失败原因：\r\n" + e2);
			throw new LoanException("Gen_E001", e2);
		}

		try {
			c = findByMultiObj(paynoticeinfo);
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

	protected Collection findByMultiObj(LeaseholdPayNoticeInfo paynoticeinfo)
			throws LoanDAOException {
		String strSQL = "";
		String strSelect = "";

		Vector v = new Vector();
		String contractCodeStart = paynoticeinfo.getContractCodeStart(); // 融资租赁合同编号
		String contractCodeEnd = paynoticeinfo.getContractCodeEnd(); // 融资租赁合同编号
		long clientIDStart = paynoticeinfo.getClientIDStart(); // 承租单位ID
		long clientIDEnd = paynoticeinfo.getClientIDEnd(); // 承租单位ID
		Timestamp startDate = paynoticeinfo.getQueryStartDate(); // 租赁日期
		Timestamp endDate = paynoticeinfo.getQueryEndDate(); // 租赁日期
		long statusID = paynoticeinfo.getStatusId(); // 状态
		long currencyID = paynoticeinfo.getCurrencyId(); // 币种
		long officeID = paynoticeinfo.getOfficeId(); // 办事处

		long userID = paynoticeinfo.getUserID();
		String strUser = paynoticeinfo.getStrUser();
		long queryPurpose = paynoticeinfo.getQueryPurpose();

		long pageLineCount = paynoticeinfo.getPageLineCount();
		long pageNo = paynoticeinfo.getPageNo();
		long desc = paynoticeinfo.getDesc();
		String orderParamString = paynoticeinfo.getOrderParamString();
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
				strSQL = " from LOAN_ASSURECHARGEFORM a,Loan_ContractForm b,Client c "
						+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) "
						+ " and a.StatusID >= "
						+ LOANConstant.LoanPayNoticeStatus.SUBMIT
						+ " and a.StatusID <= "
						+ LOANConstant.LoanPayNoticeStatus.CHECK
						+ " and a.InputUserID = " + userID;
				if (statusID == LOANConstant.LoanPayNoticeStatus.SUBMIT) {
					strSQL += " and a.nextCheckLevel = 1 ";
					strSQL += " and a.StatusID = "
							+ LOANConstant.LoanPayNoticeStatus.SUBMIT;
				} else {
					strSQL += " and a.StatusID = "
							+ LOANConstant.LoanPayNoticeStatus.CHECK;
				}
			} else if (queryPurpose == 2l) { // for examine
				strSelect = " select count(*) ";
				strSQL = " from LOAN_ASSURECHARGEFORM a,Loan_ContractForm b,Client c "
						+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) "
						+ " and " + strUser;
				if (statusID == LOANConstant.LoanPayNoticeStatus.SUBMIT) {
					strSQL += " and a.StatusID = "
							+ LOANConstant.LoanPayNoticeStatus.SUBMIT;
				} else {
					strSQL += " and a.StatusID = "
							+ LOANConstant.LoanPayNoticeStatus.SUBMIT;
				}
			}
			// ////////////////////查询条件////////////////////////////
			if (clientIDStart > 0) {
				strSQL += " and b.nBorrowClientID >= " + clientIDStart;
			}
			if (clientIDEnd > 0) {
				strSQL += " and b.nBorrowClientID <= " + clientIDEnd;
			}

			if (officeID > 0) {
				strSQL += " and a.OfficeID = " + officeID;
			}
			if (currencyID > 0) {
				strSQL += " and a.currencyID = " + currencyID;
			}
			if (startDate != null) {
				strSQL += " and to_char(b.dtStartDate,'yyyy-mm-dd') >= '"
						+ DataFormat.getDateString(startDate) + "'";
			}
			if (endDate != null) {
				strSQL += " and to_char(b.dtEndDate,'yyyy-mm-dd') <= '"
						+ DataFormat.getDateString(endDate) + "'";
			}
			if (contractCodeStart != null && contractCodeStart.length() > 0) {
				strSQL += " and b.sContractCode >= '" + contractCodeStart + "'";
			}
			if (contractCodeEnd != null && contractCodeEnd.length() > 0) {
				strSQL += " and b.sContractCode <= '" + contractCodeEnd + "'";
			}
			/* modify by zcwang 2007-3-27 审核查找时 只需要下一个审核人就可以，
			 * 而需要录入人会造成审核人和下一级审核人是从同一个变量里得到的，无法查到数据。
			if (userID > 0) {
				strSQL += " and a.INPUTUSERID = " + userID ;
			}
			*/
			// //////////////////////////排序处理///////////////////////
			int nIndex = 0;
			nIndex = orderParamString.indexOf(".");
			if (nIndex > 0) {
				if (orderParamString.substring(0, nIndex).toLowerCase().equals(
						"loan_assurechargeform")) {
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
			//计算总页数
			if (recordCount > pageLineCount) {
				pageCount = recordCount / pageLineCount;
				if ((recordCount % pageLineCount) != 0) {
					pageCount++;
				}
			} else if (recordCount > 0 &&
					   recordCount <= pageLineCount) {
				pageCount = 1;
			} else {
				pageCount = 0;
			}
			// ////////////////返回需求的结果集////////////////////////////////////
			rowNumStart = (pageNo - 1) * pageLineCount + 1;
			rowNumEnd = rowNumStart + pageLineCount - 1;
			strSelect = " select a.*,b.sContractCode,b.dtStartDate ,b.dtEndDate,c.sName,c.id ClientID ";
			strSQL = " select * from ( select aa.*,rownum r from ( "
					+ strSelect + strSQL;
			strSQL += " ) aa ) where r between " + rowNumStart + " and "
					+ rowNumEnd;
			try {
				System.out.println("=====================strSQL1" + strSQL);
				prepareStatement(strSQL);
				ResultSet rs1 = executeQuery();
				while (rs1 != null && rs1.next()) {
					LeaseholdPayNoticeInfo assureChargeNoticeInfo = new LeaseholdPayNoticeInfo();
					assureChargeNoticeInfo.setId(rs1.getLong("ID")); // id
					assureChargeNoticeInfo.setContractId(rs1
							.getLong("CONTRACTID")); // CONTRACTID
					assureChargeNoticeInfo.setOfficeId(rs1.getLong("OfficeID")); // 办事处
					assureChargeNoticeInfo.setCurrencyId(rs1
							.getLong("CurrencyID")); // 币种
					assureChargeNoticeInfo.setCode(rs1.getString("Code")); // 申请书编号
					assureChargeNoticeInfo.setClientId(rs1.getLong("ClientID")); //
					assureChargeNoticeInfo.setAssureAmount(rs1
							.getDouble("AssureAmount")); // 承保金额
					assureChargeNoticeInfo.setRecognizanceAccountId(rs1
							.getLong("RecognizanceAccountID")); // 保证金账户
					assureChargeNoticeInfo.setRecognizanceAmount(rs1
							.getDouble("RecognizanceAmount")); //
					assureChargeNoticeInfo.setAssureChargeAccountId(rs1
							.getLong("AssureChargeAccountID")); // 担保手续费账户
					assureChargeNoticeInfo.setAssureChargeAmount(rs1
							.getDouble("AssureChargeAmount")); //
					assureChargeNoticeInfo.setStartDate(rs1
							.getTimestamp("dtStartDate")); // 租赁开始日期
					assureChargeNoticeInfo.setEndDate(rs1
							.getTimestamp("dtEndDate")); // 租赁结束日期
					assureChargeNoticeInfo.setNextCheckUserId(rs1
							.getLong("NextCheckUserID")); // 下一级审核人
					assureChargeNoticeInfo.setNextCheckLevel(rs1
							.getLong("NextCheckLevel")); // 下一级审核级别
					assureChargeNoticeInfo.setInputUserId(rs1
							.getLong("InputUserID")); // 录入人
					assureChargeNoticeInfo.setInputDate(rs1
							.getTimestamp("InputDate")); // 录入时间
					assureChargeNoticeInfo.setStatusId(rs1.getLong("StatusID")); // 状态
					assureChargeNoticeInfo.setIsLowLevel(rs1
							.getLong("IsLowLevel")); // 是否最低审核级别
					// 表中没有的字段
					assureChargeNoticeInfo
							.setClientName(rs1.getString("sName")); //
					assureChargeNoticeInfo.setContractCode(rs1
							.getString("sContractCode")); //
					assureChargeNoticeInfo.setRecordCount(recordCount); // 记录数
					assureChargeNoticeInfo.setPageCount(pageCount); // 页数
					// 新加的字段2006－3－20
					assureChargeNoticeInfo.setIsInteRest(rs1
							.getLong("IsinteRest"));
					assureChargeNoticeInfo.setRate(rs1.getDouble("Rate"));
					assureChargeNoticeInfo.setRecrecognizanceAccountId(rs1
							.getLong("RecrecognizanceAccountid"));
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

		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		long lApprovalID = ATInfo.getApprovalID();
		LeaseholdPayNoticeInfo aInfo = new LeaseholdPayNoticeInfo();
		LeaseholdPayNoticeInfo tempInfo = new LeaseholdPayNoticeInfo();
		try {
			tempInfo = (LeaseholdPayNoticeInfo) findByID(lApprovalContentID,
					tempInfo.getClass());
		} catch (ITreasuryDAOException e) {
			throw new LoanDAOException(e);
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) { // 拒绝
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(LOANConstant.AssureChargeNoticeStatus.REFUSE);
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) { // 审批
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserId(lNextUserID);
			ApprovalDelegation appbiz = new ApprovalDelegation();
			try {
				aInfo.setNextCheckLevel(appbiz.findApprovalUserLevel(
						lApprovalID, lNextUserID));
			} catch (Exception e) {
				throw new LoanDAOException("设置下一个审核级别时出错：", e);
			}
			try {
				update(aInfo);
			} catch (ITreasuryDAOException e) {
				throw new LoanDAOException(" 审批更新出错 ", e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER) { // 审批&&最后
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusId(LOANConstant.AssureChargeNoticeStatus.CHECK);
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
			aInfo.setStatusId(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
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
	
	public static void main(String[] args)
	{
		LoanAssureChargeFormDao dao = new LoanAssureChargeFormDao();

		try
		{
			LeaseholdPayNoticeInfo info = new LeaseholdPayNoticeInfo();
			info.setQueryPurpose(1);
			info.setInputUserId(1);
			info.setOfficeId(1);
			info.setCurrencyId(1);
			//info.setPageCount(1);
			info.setPageNo(1);
			info.setPageLineCount(10);
			
			//Collection c = dao.findByMultiOption(info);
			Collection c = dao.findByMultiObj(info);
			
			System.out.println("ok = "+ c.size());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//added by qhzhou 2007.07.06
	public long updateLeaseholdNoticeStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update LOAN_ASSURECHARGEFORM  set STATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan updateAssureManagementNoticeStatus error : "
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
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lId
	 * @param statusId
	 * @return
	 * @throws IException
	 */
	public long updateNoticeStatusAndCheckStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update LOAN_ASSURECHARGEFORM  set STATUSID = ? where ID = ? and STATUSID = ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, LOANConstant.LoanPayNoticeStatus.CHECK);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;
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
		return lResult;
	}
	
	//added by qhzhou 2007.07.09
	public long updateLeaseholdRepayNoticeStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_leaseholdrepayform  set NSTATUSID = ? where ID = ? ";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan updateLeaseholdRepayNoticeStatus error : "
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
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param lId
	 * @param statusId
	 * @return
	 * @throws IException
	 */
	public long updateRepayNoticeStatusAndCheckStatus(long lId,long statusId)throws IException {
		PreparedStatement ps = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL = " update loan_leaseholdrepayform  set NSTATUSID = ? where ID = ? and NSTATUSID = ?";

			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, lId);
			ps.setLong(3, LOANConstant.LoanPayNoticeStatus.CHECK);

			lResult = ps.executeUpdate();

			ps.close();
			ps = null;
			conn.close();
			conn = null;
			
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
		return lResult;
	}
	
	/**
	 * Boxu Add 2008年3月12日 未收款金额(合同未做收款通知单金额)
	 * @param  statusId
	 * @return double
	 * @throws IException
	 */
	public double findUnPayAmountByID(long lContractID,long payID)throws LoanException 
	{
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ContractInfo contractInfo = new ContractInfo();
		double unPayAmount = 0.0;
		double payAmount = 0.0;
		double mAmount = 0.0;
		double mCharge = 0.0;
 
		try 
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			StringBuffer sbSQL2 = new StringBuffer();
			StringBuffer sbSQL3 = new StringBuffer();
			
			if (lContractID > 0) 
			{
				sbSQL.append(" select sum(la.recognizanceamount) payAmount ");
				sbSQL.append(" from LOAN_ASSURECHARGEFORM la ");
				sbSQL.append(" where la.contractid = ? and la.statusid != ? and la.statusid != ? and la.statusid != 0 ");
				if(payID>0)
				{
					sbSQL.append(" and  la.ID<>? ");
				}
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lContractID);
				ps.setLong(2, LOANConstant.AssureChargeNoticeStatus.CANCEL);
				ps.setLong(3, LOANConstant.AssureChargeNoticeStatus.REFUSE);
				if(payID>0)
				{
					ps.setLong(4, payID);
				}
				rs = ps.executeQuery();

				if (rs.next()) 
				{
					payAmount = rs.getDouble("payAmount");
				}
				
				ps.close();
				ps = null;
				rs.close();
				rs = null;
				
				sbSQL2.append(" select mamount ");
				sbSQL2.append(" from loan_loancontractassure ll ");
				sbSQL2.append(" where ll.ncontractid = ? and ll.nstatusid != ? and ll.nstatusid != ? and ll.nstatusid != 0 ");
				
				ps = con.prepareStatement(sbSQL2.toString());
				ps.setLong(1, lContractID);
				ps.setLong(2, LOANConstant.AssureChargeNoticeStatus.CANCEL);
				ps.setLong(3, LOANConstant.AssureChargeNoticeStatus.REFUSE);
				rs = ps.executeQuery();

				if (rs.next()) 
				{
					mAmount = rs.getDouble("mamount");
				}
				
				ps.close();
				ps = null;
				rs.close();
				rs = null;
				
				//add by yunchang
				//2010-07-06 11:20
				sbSQL3.append(" select sum(mbailamount) mbailamount from sett_transreturnfinance aa where aa.ncontractid = ? and aa.nstatusid = 3 ");
				
				ps = con.prepareStatement(sbSQL3.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					mCharge = rs.getDouble("mbailamount");
				}
				
				ps.close();
				ps = null;
				rs.close();
				rs = null;
				
				//合同保证金金额减去已生成收款通知单金额
				unPayAmount = UtilOperation.Arith.round(UtilOperation.Arith.sub(mAmount, payAmount), 2);
				unPayAmount = UtilOperation.Arith.round(UtilOperation.Arith.add(unPayAmount, mCharge), 2);
			}
		} 
		catch (Exception e) 
		{
			throw new LoanException("Gen_E001", e);
		} 
		finally 
		{
			try 
			{
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
			} 
			catch (Exception e) 
			{
				throw new LoanException("Gen_E001", e);
			}
		}
		
		return unPayAmount;
	}
	
	/**
	 * @author yunchang
	 * @date 2010-05-25 17:28
	 * @function 查找出记录担保收费通知单信息(LOAN_ASSURECHARGEFORM)中相同合同号的收取租金账户,为多笔收款通知单做校验,相同合同的收取租金账户、收保证金账户必须相同
	 * @param LeaseholdPayNoticeInfo
	 * @throws LoanException
	 * @throws LeaseholdPayNoticeInfo
	 */
	public LeaseholdPayNoticeInfo getCollectionRentAccountID(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws LoanException,LoanDAOException
	{
		Log.print("=========================================================================================================");
		Log.print("进入方法：LoanAssureChargeFormDao.getCollectionRentAccountID(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)");
		Log.print("=========================================================================================================");
		LeaseholdPayNoticeInfo leaseholdPayNoticeInfoTemp = new LeaseholdPayNoticeInfo();
		StringBuffer strSQL = new StringBuffer();
		try
		{
			initDAO();
			strSQL.append(" select a.collectionrentaccountid as collectionrentaccountid,a.recrecognizanceaccountid as recrecognizanceaccountid from loan_assurechargeform a where 1=1 ");
			strSQL.append(" and a.statusid > 0 ");
			strSQL.append(" and a.statusid <> "+ LOANConstant.AssureChargeNoticeStatus.CANCEL);
			if(leaseholdPayNoticeInfo.getId() > 0 )
			{
				strSQL.append(" and a.id <> " + leaseholdPayNoticeInfo.getId());
			}
			if(leaseholdPayNoticeInfo.getOfficeId() > 0)
			{
				strSQL.append(" and a.officeid = " + leaseholdPayNoticeInfo.getOfficeId() );
			}
			if(leaseholdPayNoticeInfo.getInputUserId() > 0)
			{
				strSQL.append(" and a.inputuserid = " + leaseholdPayNoticeInfo.getInputUserId() );
			}
			if(leaseholdPayNoticeInfo.getContractId() > 0 )
			{
				strSQL.append(" and a.contractid = " + leaseholdPayNoticeInfo.getContractId() );
			}
			try
			{
				prepareStatement(strSQL.toString());
				ResultSet resultSet = executeQuery();
				while(resultSet.next())
				{
					leaseholdPayNoticeInfoTemp.setCollectionRentAccountID(resultSet.getLong("collectionrentaccountid"));
					leaseholdPayNoticeInfoTemp.setRecrecognizanceAccountId(resultSet.getLong("recrecognizanceaccountid"));
				}
			}
			catch(SQLException e)
			{
				throw new LoanDAOException("新增收款通知单（查询收取租金账号）产生错误",e);
			}
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		catch(Exception e)
		{
			throw new LoanDAOException("新增收款通知单（查询收取租金账号）产生错误", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e1)
			{
				throw new LoanDAOException(e1);
			}
		}
		return leaseholdPayNoticeInfoTemp;
	}

	/**
	 * @author yunchang
	 * @date 2010-06-01 19:24
	 * @function 查找出融资租赁新增(LOAN_LOANFORM)中的新增手续费,为多笔收款通知单做校验,多比手续费总和不能超过新增手续费
	 * @param LeaseholdPayNoticeInfo
	 * @throws LoanException
	 * @throws LeaseholdPayNoticeInfo
	 */
	public LeaseholdPayNoticeInfo getMchargeAmount(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)throws LoanException,LoanDAOException
	{
		Log.print("=========================================================================================================");
		Log.print("进入方法：LoanAssureChargeFormDao.getMchargeAmount(LeaseholdPayNoticeInfo leaseholdPayNoticeInfo)");
		Log.print("=========================================================================================================");
		LeaseholdPayNoticeInfo leaseholdPayNoticeInfoTemp = new LeaseholdPayNoticeInfo();
		StringBuffer strSQL = new StringBuffer();
		try
		{
			initDAO();
			strSQL.append(" select (a.mchargeamount-b.assurechargeamount) as mchargeamount from loan_loanform a, ");
			strSQL.append(" (select nvl(sum(c.assurechargeamount), to_number(0.00)) as assurechargeamount, d.id as contractid,d.nloanid as nloanid ");
			strSQL.append(" from (select * from loan_assurechargeform c where c.statusid>0 and c.statusid<>"+LOANConstant.AssureChargeNoticeStatus.CANCEL+") c, loan_contractform d where c.contractid(+) = d.id group by d.id,d.nloanid) b ");
			strSQL.append(" where a.id = b.nloanid ");
			strSQL.append(" and a.nstatusid > 0 ");
			if(leaseholdPayNoticeInfo.getOfficeId() > 0)
			{
				strSQL.append(" and a.nofficeid = " + leaseholdPayNoticeInfo.getOfficeId() );
			}
			if(leaseholdPayNoticeInfo.getInputUserId() > 0)
			{
				strSQL.append(" and a.ninputuserid = " + leaseholdPayNoticeInfo.getInputUserId() );
			}
			if(leaseholdPayNoticeInfo.getContractId() > 0 )
			{
				strSQL.append(" and b.contractid = " + leaseholdPayNoticeInfo.getContractId() );
			}
			try
			{
				prepareStatement(strSQL.toString());
				ResultSet resultSet = executeQuery();
				while(resultSet.next())
				{
					leaseholdPayNoticeInfoTemp.setAssureChargeAmount(resultSet.getDouble("mchargeamount"));
				}
			}
			catch(SQLException e)
			{
				throw new LoanDAOException("新增收款通知单（查询新增手续费金额）产生错误",e);
			}
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		catch(Exception e)
		{
			throw new LoanDAOException("新增收款通知单（查询新增手续费金额）产生错误", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e1)
			{
				throw new LoanDAOException(e1);
			}
		}
		return leaseholdPayNoticeInfoTemp;
	}	
	
	/**
	 * @author yunchang
	 * @date 2010-06-28 17:37
	 * @function 收款通知单保证金余额
	 * @param 合同ID
	 * @throws LoanException
	 * @throws LoanDAOException
	 */
	public double getSurplusRecognizanceAmount(long constractID)throws LoanException,LoanDAOException
	{
		Log.print("=========================================================================================================");
		Log.print("进入方法：LoanAssureChargeFormDao.getSurplusRecognizanceAmount(double constractID)");
		Log.print("=========================================================================================================");
		double tempSurplusRecognizanceAmount = 0d;
		StringBuffer strSQL = new StringBuffer();
		try
		{
			initDAO();
			strSQL.append(" select sum(mbalance) surplus from sett_subaccount where AL_NLOANNOTEID in ");
			strSQL.append(" (select b.id from loan_contractform a, loan_assurechargeform b where a.id = b.contractid ");
			if( constractID >0 )
			{
				strSQL.append(" and a.id = " + constractID);
			}
			strSQL.append(" )and nstatusid = 1 ");
			try
			{
				prepareStatement(strSQL.toString());
				ResultSet resultSet = executeQuery();
				while(resultSet.next())
				{
					tempSurplusRecognizanceAmount = resultSet.getDouble("surplus");
				}
			}
			catch(SQLException e)
			{
				throw new LoanDAOException("保证金余额计算错误",e);
			}
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		catch(Exception e)
		{
			throw new LoanDAOException("保证金余额计算错误", e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(ITreasuryDAOException e1)
			{
				throw new LoanDAOException(e1);
			}
		}		
		return tempSurplusRecognizanceAmount;
	}
}
