package com.iss.itreasury.creditrating.mywork.dao;

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

import com.iss.itreasury.creditrating.mywork.dataentity.CreRtMyWorkInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.dao.ITreasuryDAO;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public abstract class CreRtMyWorkDao extends ITreasuryDAO {

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

	public Object queryMyWork(CreRtMyWorkInfo creRtMyWorkInfo) throws IException {
		try {

			switch ((int) creRtMyWorkInfo.getQueryPurpose()) {

			case (int) CreRtConstant.WorkType.WAITDEALWITHWORK:
				return queryWaitDealWithWork(creRtMyWorkInfo);

			case (int) CreRtConstant.WorkType.CURRENTWORK:
				return loadCurrentWorkList(creRtMyWorkInfo);

			case (int) CreRtConstant.WorkType.HISTORYWORK:
				return loadFinishedWorkList(creRtMyWorkInfo);

			case (int) CreRtConstant.WorkType.FINISHEDWORK:
				return loadFinishedWorkList(creRtMyWorkInfo);

			case (int) CreRtConstant.WorkType.REFUSEWORK:
				return loadRefuseWorkList(creRtMyWorkInfo);

			case (int) CreRtConstant.WorkType.CANCELAPPROVAL:
				return loadCancelApprovalList(creRtMyWorkInfo);
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
	private PageLoader loadCancelApprovalList(CreRtMyWorkInfo CreRtMyWorkInfo)
			throws Exception {
		getCancelApprovalSql(CreRtMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.creditrating.mywork.dataentity.CreRtMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询办结业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadFinishedWorkList(CreRtMyWorkInfo CreRtMyWorkInfo)
			throws Exception {

		getFinishedWorkSql(CreRtMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.creditrating.mywork.dataentity.CreRtMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询拒绝业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadRefuseWorkList(CreRtMyWorkInfo CreRtMyWorkInfo)
			throws Exception {
		getRefuseWorkSql(CreRtMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.creditrating.mywork.dataentity.CreRtMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	private Collection loadCurrentWorkList(CreRtMyWorkInfo CreRtMyWorkInfo)
			throws IException {
		try {
			// 构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(CreRtMyWorkInfo.getUserID());
			// 调用待办业务查询接口
			HashMap hm = new HashMap();

			hm = FSWorkflowManager.getCurrentList(pInfo);

			if (hm != null && hm.size() > 0) {
				CreRtMyWorkInfo.setApprovalEntryIDs(mergeString(hm.keySet()
						.toArray()));
				CreRtMyWorkInfo.setWorkList(hm);

				return queryCurrentWork(CreRtMyWorkInfo);
			}

		} catch (Exception e) {
			throw new IException("查询待办任务失败", e);
		}
		return new Vector();
	}

	protected abstract Collection queryWaitDealWithWork(
			CreRtMyWorkInfo CreRtMyWorkInfo) throws IException;

	protected abstract Collection queryCurrentWork(CreRtMyWorkInfo creRtMyWorkInfo)
			throws IException;

	protected abstract void getFinishedWorkSql(CreRtMyWorkInfo creRtInutWorkInfo);

	protected abstract void getRefuseWorkSql(CreRtMyWorkInfo creRtMyWorkInfo)
			throws IException;

	protected abstract void getCancelApprovalSql(CreRtMyWorkInfo creRtMyWorkInfo)
			throws IException;

}
