package com.iss.itreasury.craftbrother.mywork.dao;

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
import com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInterface;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public abstract class CraMyWorkDao extends ITreasuryDAO {

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

	public Object queryMyWork(CraMyWorkInterface craMyWorkInfo) throws IException {
		try {

			switch ((int) craMyWorkInfo.getQueryPurpose()) {

			case (int) CRAconstant.WorkType.WAITDEALWITHWORK:
				return queryWaitDealWithWork(craMyWorkInfo);

			case (int) CRAconstant.WorkType.CURRENTWORK:
				return loadCurrentWorkList(craMyWorkInfo);

			case (int) CRAconstant.WorkType.HISTORYWORK:
				// hm = FSWorkflowManager.getHistoryList(pInfo);
				return loadFinishedWorkList(craMyWorkInfo);

			case (int) CRAconstant.WorkType.FINISHEDWORK:
				return loadFinishedWorkList(craMyWorkInfo);

			case (int) CRAconstant.WorkType.REFUSEWORK:
				return loadRefuseWorkList(craMyWorkInfo);

			case (int) CRAconstant.WorkType.CANCELAPPROVAL:
				return loadCancelApprovalList(craMyWorkInfo);
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
	private PageLoader loadCancelApprovalList(CraMyWorkInterface craMyWorkInfo)
			throws Exception {
		getCancelApprovalSql(craMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		if(craMyWorkInfo.getCraMyWorkInterfaceType() == 1){
			pageLoader.initPageLoader(
							new AppContext(),
							m_sbFrom.toString(),
							m_sbSelect.toString(),
							m_sbWhere.toString(),
							(int) Constant.PageControl.CODE_PAGELINECOUNT,
							"com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInfo",
							null);
		}
		else{
			pageLoader.initPageLoader(
					new AppContext(),
					m_sbFrom.toString(),
					m_sbSelect.toString(),
					m_sbWhere.toString(),
					(int) Constant.PageControl.CODE_PAGELINECOUNT,
					"com.iss.itreasury.craftbrother.mywork.dataentity.CreditSettingMyWorkInfo",
					null);
		}
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询办结业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadFinishedWorkList(CraMyWorkInterface craMyWorkInfo)
			throws Exception {

		getFinishedWorkSql(craMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		if(craMyWorkInfo.getCraMyWorkInterfaceType() == 1){
			pageLoader.initPageLoader(
							new AppContext(),
							m_sbFrom.toString(),
							m_sbSelect.toString(),
							m_sbWhere.toString(),
							(int) Constant.PageControl.CODE_PAGELINECOUNT,
							"com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInfo",
							null);
		}
		else{
			pageLoader.initPageLoader(
					new AppContext(),
					m_sbFrom.toString(),
					m_sbSelect.toString(),
					m_sbWhere.toString(),
					(int) Constant.PageControl.CODE_PAGELINECOUNT,
					"com.iss.itreasury.craftbrother.mywork.dataentity.CreditSettingMyWorkInfo",
					null);
		}
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询拒绝业务
	 * 
	 * @throws Exception
	 */
	private PageLoader loadRefuseWorkList(CraMyWorkInterface craMyWorkInfo)
			throws Exception {
		getRefuseWorkSql(craMyWorkInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");
		if(craMyWorkInfo.getCraMyWorkInterfaceType() == 1){
			pageLoader.initPageLoader(
							new AppContext(),
							m_sbFrom.toString(),
							m_sbSelect.toString(),
							m_sbWhere.toString(),
							(int) Constant.PageControl.CODE_PAGELINECOUNT,
							"com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInfo",
							null);
		}
		else{
			pageLoader.initPageLoader(
					new AppContext(),
					m_sbFrom.toString(),
					m_sbSelect.toString(),
					m_sbWhere.toString(),
					(int) Constant.PageControl.CODE_PAGELINECOUNT,
					"com.iss.itreasury.craftbrother.mywork.dataentity.CreditSettingMyWorkInfo",
					null);
		}
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	private Collection loadCurrentWorkList(CraMyWorkInterface craMyWorkInfo)
			throws IException {
		try {
			// 构造查询类
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setUserID(craMyWorkInfo.getUserID());
			// 调用待办业务查询接口
			HashMap hm = new HashMap();

			hm = FSWorkflowManager.getCurrentList(pInfo);

			if (hm != null && hm.size() > 0) {
				craMyWorkInfo.setApprovalEntryIDs(mergeString(hm.keySet().toArray()));
				craMyWorkInfo.setWorkList(hm);

				return queryCurrentWork(craMyWorkInfo);
			}

		} catch (Exception e) {
			throw new IException("查询待办任务失败", e);
		}
		return new Vector();
	}

	protected abstract Collection queryWaitDealWithWork(CraMyWorkInterface craMyWorkInfo) 
	        throws IException;

	protected abstract Collection queryCurrentWork(CraMyWorkInterface craMyWorkInfo)
			throws IException;

	protected abstract void getFinishedWorkSql(CraMyWorkInterface craMyWorkInfo);

	protected abstract void getRefuseWorkSql(CraMyWorkInterface craMyWorkInfo)
			throws IException;

	protected abstract void getCancelApprovalSql(CraMyWorkInterface craMyWorkInfo)
			throws IException;

}
