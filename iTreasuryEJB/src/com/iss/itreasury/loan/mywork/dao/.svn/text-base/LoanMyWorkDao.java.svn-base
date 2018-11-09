package com.iss.itreasury.loan.mywork.dao;

/*
 * Created on 2007-06-21
 *
 * Title:				iTreasury
 * @author             	付明正 
 * Company:             iSoftStone
 * @version				iTreasury4.0新增
 * Description:         产品化4.0在信贷新增审批流,,该功能实现查找我的任务
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public abstract class LoanMyWorkDao extends ITreasuryDAO {

	protected StringBuffer m_sbSelect = null;

	protected StringBuffer m_sbFrom = null;

	protected StringBuffer m_sbWhere = null;

	protected StringBuffer m_sbOrderBy = null;

	public String mergeString(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i].toString()).append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	public Object queryMyWork(LoanMyWorkInfo loanMyWorkInfo) throws IException {
		try {

			switch ((int) loanMyWorkInfo.getQueryPurpose()) {

			case (int) LOANConstant.WorkType.WAITDEALWITHWORK:
				return queryWaitDealWithWork(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.CURRENTWORK:
				return loadCurrentWorkList(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.HISTORYWORK:
				// hm = FSWorkflowManager.getHistoryList(pInfo);
				return loadFinishedWorkList(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.FINISHEDWORK:
				return loadFinishedWorkList(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.REFUSEWORK:
				return loadRefuseWorkList(loanMyWorkInfo);

			case (int) LOANConstant.WorkType.CANCELAPPROVAL:
				return loadCancelApprovalList(loanMyWorkInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("Gen_E001", e);
		} finally {
			this.finalizeDAO();
		}
		return new Vector();
	}

	/**
	 * 查询取消审批业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadCancelApprovalList(LoanMyWorkInfo loanMyWorkInfo)
			throws Exception {
		getCancelApprovalSql(loanMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询办结业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadFinishedWorkList(LoanMyWorkInfo loanMyWorkInfo)
			throws Exception {

		getFinishedWorkSql(loanMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询拒绝业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadRefuseWorkList(LoanMyWorkInfo loanMyWorkInfo)
			throws Exception {
		getRefuseWorkSql(loanMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	public Collection loadCurrentWorkList(LoanMyWorkInfo loanMyWorkInfo)
			throws IException {
		try {
			// 构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(loanMyWorkInfo.getUserID());
			// 调用待办业务查询接口
			HashMap hm = new HashMap();

			hm = FSWorkflowManager.getCurrentList(pInfo);

			if (hm != null && hm.size() > 0) {
				loanMyWorkInfo.setApprovalEntryIDs(mergeString(hm.keySet()
						.toArray()));
				loanMyWorkInfo.setWorkList(hm);

				return queryCurrentWork(loanMyWorkInfo);
			}

		} catch (Exception e) {
			throw new IException("查询待办任务失败", e);
		}
		return new Vector();
	}

	protected abstract Collection queryWaitDealWithWork(
			LoanMyWorkInfo loanMyWorkInfo) throws IException;

	protected abstract Collection queryCurrentWork(LoanMyWorkInfo loanMyWorkInfo)
			throws IException;

	protected abstract void getFinishedWorkSql(LoanMyWorkInfo loanInutWorkInfo);

	protected abstract void getRefuseWorkSql(LoanMyWorkInfo loanMyWorkInfo)
			throws IException;

	protected abstract void getCancelApprovalSql(LoanMyWorkInfo loanMyWorkInfo)
			throws IException;

}
