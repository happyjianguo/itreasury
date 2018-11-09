package com.iss.itreasury.bill.mywork.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

import com.iss.itreasury.bill.mywork.dataentity.*;
import com.iss.itreasury.bill.util.BILLConstant;

public abstract class BillMyWorkDao  extends ITreasuryDAO {

	protected StringBuffer m_sbSelect = null;

	protected StringBuffer m_sbFrom = null;

	protected StringBuffer m_sbWhere = null;

	protected StringBuffer m_sbOrderBy = null;

	private String mergeString(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i].toString()).append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	public Object queryMyWork(BillMyWorkInfo billMyWorkInfo) throws IException {
		try {

			switch ((int) billMyWorkInfo.getQueryPurpose()) {

			case (int) BILLConstant.WorkType.WAITDEALWITHWORK:
				return queryWaitDealWithWork(billMyWorkInfo);

			case (int) BILLConstant.WorkType.CURRENTWORK:
				return loadCurrentWorkList(billMyWorkInfo);

			case (int) BILLConstant.WorkType.HISTORYWORK:
				return loadFinishedWorkList(billMyWorkInfo);

			case (int) BILLConstant.WorkType.FINISHEDWORK:
				return loadFinishedWorkList(billMyWorkInfo);

			case (int) BILLConstant.WorkType.REFUSEWORK:
				return loadRefuseWorkList(billMyWorkInfo);

			case (int) BILLConstant.WorkType.CANCELAPPROVAL:
				return loadCancelApprovalList(billMyWorkInfo);
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
	private PageLoader loadCancelApprovalList(BillMyWorkInfo billMyWorkInfo)throws Exception {
		getCancelApprovalSql(billMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询办结业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadFinishedWorkList(BillMyWorkInfo billMyWorkInfo)throws Exception {
		getFinishedWorkSql(billMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询拒绝业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadRefuseWorkList(BillMyWorkInfo billMyWorkInfo)throws Exception {
		getRefuseWorkSql(billMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.bill.mywork.dataentity.BillMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	private Collection loadCurrentWorkList(BillMyWorkInfo billMyWorkInfo)throws IException {
		try {
			// 构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(billMyWorkInfo.getUserID());
			pInfo.setModuleID(billMyWorkInfo.getModuleID());
			pInfo.setOfficeID(billMyWorkInfo.getOfficeID());
			pInfo.setCurrencyID(billMyWorkInfo.getCurrencyID());
			// 调用待办业务查询接口
			HashMap hm = new HashMap();

			hm = FSWorkflowManager.getCurrentList(pInfo);

			if (hm != null && hm.size() > 0) {
				billMyWorkInfo.setApprovalEntryIDs(mergeString(hm.keySet().toArray()));
				billMyWorkInfo.setWorkList(hm);

				return queryCurrentWork(billMyWorkInfo);
			}

		} catch (Exception e) {
			throw new IException("查询待办任务失败", e);
		}
		return new Vector();
	}

	protected abstract Collection queryWaitDealWithWork(BillMyWorkInfo illMyWorkInfo) throws IException;

	protected abstract Collection queryCurrentWork(BillMyWorkInfo billMyWorkInfo)throws IException;
	
	protected abstract void getFinishedWorkSql(BillMyWorkInfo billMyWorkInfo);

	protected abstract void getRefuseWorkSql(BillMyWorkInfo billMyWorkInfo)throws IException;

	protected abstract void getCancelApprovalSql(BillMyWorkInfo billMyWorkInfo)throws IException;
}