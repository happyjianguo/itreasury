/*
 * Created on 2004-11-29
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.recognizancenotice.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.loan.assurechargenotice.dao.AssureChargeNoticeDao;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dao.AssureManagementNoticeDao;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

import com.iss.itreasury.loan.recognizancenotice.dao.*;
import com.iss.itreasury.loan.recognizancenotice.dataentity.*;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RecognizanceNoticeEJB implements SessionBean
{
	/* Methods required by SessionBean Interface. EJB 1.1 section 6.5.1. */
	/**
	 * @see javax.ejb.SessionBean#setContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext context)
	{
		this.context = context;
	}
	private SessionContext context;
	private static Log4j log4j = null;
	/**
	 * No argument constructor required by container.
	 */
	public RecognizanceNoticeEJB()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	/**
	 * Create method specified in EJB 1.1 section 6.10.3
	 */
	public void ejbCreate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate()
	{
	}
	/**
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove()
	{
	}

	/**
	 * 通知单的保存操作
	*/
	public long save(RecognizanceNoticeInfo info) throws java.rmi.RemoteException, LoanException
	{
		long lReturn = -1;
		RecognizanceNoticeDao dao = new RecognizanceNoticeDao();

		try
		{
			/*更新通知单表*/
			if (info.getId() <= 0)
			{
				/*更新通知单表*/
				dao.setUseMaxID();
				lReturn = dao.add(info);
			}
			else if (info.getId() > 0)
			{
				/*更新通知单表*/
				dao.update(info);
				lReturn = info.getId();
				
			}
            InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			
			if(inutParameterInfo != null){
				inutParameterInfo.setTransID(String.valueOf(lReturn));
				inutParameterInfo.setUrl(inutParameterInfo.getUrl()+lReturn);
				inutParameterInfo.setDataEntity(info);
				
				// 提交审批
				FSWorkflowManager.initApproval(inutParameterInfo);
				
			    dao.updateRecognizanceNoticeStatus(lReturn, LOANConstant.RecognizanceNoticeStatus.APPROVALING);
			}
		}
		catch (Exception e)
		{
			//throw new LoanException("Gen_E001", e);
			throw new LoanException(e.getMessage(), e,context);
		}
		return lReturn;
	}

	/**
	 * 通知单的审核操作
	*/
	

	/**
	 * 通知单的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException
	{
		RecognizanceNoticeDao dao = null;
		dao = new RecognizanceNoticeDao();
		RecognizanceNoticeInfo info = new RecognizanceNoticeInfo();

		try
		{			
		    info.setId(lID);
		    info.setStatusID(LOANConstant.RecognizanceNoticeStatus.CANCEL);
		    dao.update(info);
		    dao.doAfterCancel(lID);
		}
		catch (Exception e)
		{
			throw new LoanException(e.getMessage(), e,context);
		}
	}
	
	/**
	 * 通知单的删除操作
	*/
	public void del(long lID) throws java.rmi.RemoteException, LoanException
	{
		RecognizanceNoticeDao dao = null;
		dao = new RecognizanceNoticeDao();
		RecognizanceNoticeInfo info = new RecognizanceNoticeInfo();

		try
		{			
		    info.setId(lID);
		    info.setStatusID(LOANConstant.RecognizanceNoticeStatus.DEL);
		    dao.update(info);
		}
		catch (Exception e)
		{
			throw new LoanException(e.getMessage(), e,context);
		}
	}

	/**
	 * 通知单的单笔查询操作
	*/
	public RecognizanceNoticeInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{
		RecognizanceNoticeInfo returnInfo = new RecognizanceNoticeInfo();
		RecognizanceNoticeDao dao = null;

		dao = new RecognizanceNoticeDao();

		try
		{
		    returnInfo = (RecognizanceNoticeInfo) dao.findByID(lID,returnInfo.getClass());
		}
		catch (Exception e)
		{
			throw new LoanException("Gen_E001", e);
		}

		return returnInfo;
	}

	/**
	 * 通知单的多笔查询操作
	*/
	
	/**
	 * 保证金处理审批通过或者拒绝
	 * @param nInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws IException
	 */
	public long doApprovalRecognizanceNotice(RecognizanceNoticeInfo info) throws RemoteException,
	IRollbackException,IException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		long lResult = -1;
		
		try {
			conn = Database.getConnection();
			
			RecognizanceNoticeDao recognizanceNoticeDao = new RecognizanceNoticeDao();
			RecognizanceNoticeInfo rInfo = new RecognizanceNoticeInfo();
			rInfo = recognizanceNoticeDao.findByID(info.getId());
		
			InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
			InutParameterInfo returnInfo = new InutParameterInfo();
		
			// 将业务记录置入rInfo,转换成标准map传递到审批流引擎
			inutParameterInfo.setDataEntity(rInfo);
		
			// 提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) {
				long info_id=info.getId();
		
				long statusID = -1;
//				if ( info.getRecognizanceAmount() == 0 )
//				{
//					statusID = LOANConstant.RecognizanceNoticeStatus.USED;
//				}
//				else
//				{
					statusID = LOANConstant.RecognizanceNoticeStatus.CHECK;
//				}
				
				lResult = recognizanceNoticeDao.updateRecognizanceNoticeStatus(info_id, statusID);		
				//审批完成后需要做的操作
			}
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
			else if (returnInfo.isRefuse()) {
				lResult = recognizanceNoticeDao.updateRecognizanceNoticeStatus(info.getId(),LOANConstant.RecognizanceNoticeStatus.SUBMIT);
			}
		} catch (Exception e) {
			log4j.error(e.toString());
			throw new IRollbackException(context, e.getMessage(), e);
		}finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				log4j.error(ex.toString());
				throw new IRollbackException(context, ex.getMessage(), ex);
			}
		}
		return lResult;
	}
	
	
	/**
	 * Modify by leiyang date 2007/07/12
	 * 审批流：取消审批方法（担保 . 保后处理）
	 * @param loanInfo
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(RecognizanceNoticeInfo info)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		RecognizanceNoticeDao recognizanceNoticeDao = new RecognizanceNoticeDao();
		try
		{
			//取消审批
			lReturn = recognizanceNoticeDao.updateStatusAndCheckStatus(info.getId(),LOANConstant.RecognizanceNoticeStatus.SUBMIT);
			
			if(lReturn > 0){
				//将审批记录表内的该交易的审批记录状态置为无效
				if(inutParameterInfo.getApprovalEntryID()>0)
				{
					FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
				}				
			}
		}
		catch (Exception e)
		{
			throw new IRollbackException(context, e.getMessage(), e);
		}
		return lReturn;
	}
}
