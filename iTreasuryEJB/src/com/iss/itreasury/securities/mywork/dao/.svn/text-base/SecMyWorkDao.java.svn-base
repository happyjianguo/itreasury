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
import java.util.HashMap;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.securities.mywork.dataentity.SecMyWorkInfo;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public abstract class SecMyWorkDao extends ITreasuryDAO {

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

	public Object queryMyWork(SecMyWorkInfo secMyWorkInfo) throws IException {
		try {

			switch ((int) secMyWorkInfo.getQueryPurpose()) {

			case (int) SECConstant.WorkType.WAITDEALWITHWORK:
				return queryWaitDealWithWork(secMyWorkInfo);

			case (int) SECConstant.WorkType.CURRENTWORK:
				return loadCurrentWorkList(secMyWorkInfo);

			case (int) SECConstant.WorkType.HISTORYWORK:
				return loadFinishedWorkList(secMyWorkInfo);

			case (int) SECConstant.WorkType.FINISHEDWORK:
				return loadFinishedWorkList(secMyWorkInfo);

			case (int) SECConstant.WorkType.REFUSEWORK:
				return loadRefuseWorkList(secMyWorkInfo);

			case (int) SECConstant.WorkType.CANCELAPPROVAL:
				return loadCancelApprovalList(secMyWorkInfo);
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
	private PageLoader loadCancelApprovalList(SecMyWorkInfo secMyWorkInfo)throws Exception {
		getCancelApprovalSql(secMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.securities.mywork.dataentity.SecMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询办结业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadFinishedWorkList(SecMyWorkInfo secMyWorkInfo)throws Exception {
		getFinishedWorkSql(secMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.securities.mywork.dataentity.SecMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询拒绝业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadRefuseWorkList(SecMyWorkInfo secMyWorkInfo)throws Exception {
		getRefuseWorkSql(secMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.securities.mywork.dataentity.SecMyWorkInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	private Collection loadCurrentWorkList(SecMyWorkInfo secMyWorkInfo)throws IException {
		try {
			// 构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(secMyWorkInfo.getUserID());
			// 调用待办业务查询接口
			HashMap hm = new HashMap();

			hm = FSWorkflowManager.getCurrentList(pInfo);

			if (hm != null && hm.size() > 0) {
				secMyWorkInfo.setApprovalEntryIDs(mergeString(hm.keySet().toArray()));
				secMyWorkInfo.setWorkList(hm);

				return queryCurrentWork(secMyWorkInfo);
			}

		} catch (Exception e) {
			throw new IException("查询待办任务失败", e);
		}
		return new Vector();
	}

	protected abstract Collection queryWaitDealWithWork(SecMyWorkInfo secMyWorkInfo) throws IException;

	protected abstract Collection queryCurrentWork(SecMyWorkInfo secMyWorkInfo) throws IException;

	protected abstract void getFinishedWorkSql(SecMyWorkInfo secMyWorkInfo) throws IException;

	protected abstract void getRefuseWorkSql(SecMyWorkInfo secMyWorkInfo) throws IException;

	protected abstract void getCancelApprovalSql(SecMyWorkInfo secMyWorkInfo) throws IException;
}
