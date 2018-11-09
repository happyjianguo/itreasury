package com.iss.itreasury.settlement.reportlossorfreeze.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.reportlossorfreeze.dao.Sett_ReportLossOrFreezeDAO;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class ReportLossOrFreezeEJB implements SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;

	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException,
			java.rmi.RemoteException {
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException {
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException {
	}

	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
			throws java.rmi.RemoteException {
		mySessionCtx = ctx;
	}

	private void finalizeConnection(Connection transConn)
			throws ITreasuryDAOException {
		try {
			if (transConn != null) {
				transConn.close();
				transConn = null;
			}
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库关闭异常发生", e);
		}
	}

	public long lossSaveAndCheck(ReportLossOrFreezeInfo info)
			throws RemoteException, IRollbackException {
		long lReturn = -1;
		Connection transConn = null;
		try {
			transConn = Database.getConnection();

			ReportLossOrFreezeInfo upInfo = new ReportLossOrFreezeInfo();
			ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();

			lReturn = reportLossOrFreezeBean.save(info, transConn);
			upInfo = reportLossOrFreezeBean.findByID(lReturn, transConn);

			upInfo.setCheckUserId(info.getInputUserId());
			upInfo.setCheckDate(DataFormat.getDateTime(Env.getSystemDateString(
					info.getOfficeId(), info.getCurrencyId())));

			lReturn = reportLossOrFreezeBean.check(upInfo, transConn);

		} catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e
					.getMessage());
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e
						.getMessage());
			}
		}
		return lReturn;
	}

	public long lossDeleteAndCancelCheck(ReportLossOrFreezeInfo rInfo)
			throws RemoteException, IRollbackException {
		long lReturn = -1;
		Connection transConn = null;
		try {
			transConn = Database.getConnection();

			ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();
			ReportLossOrFreezeInfo rInfo2 = new ReportLossOrFreezeInfo();
			ReportLossOrFreezeInfo cInfo = new ReportLossOrFreezeInfo();

			Timestamp tsModifyDate = rInfo.getModifyDate();
			long lId = rInfo.getId();
            System.out.println("------------lid------------------"+lId);
			rInfo2 = reportLossOrFreezeBean.findByID(lId,transConn);
			rInfo2.setModifyDate(tsModifyDate);

			lReturn = reportLossOrFreezeBean.cancelCheck(rInfo2, transConn);

			cInfo = reportLossOrFreezeBean.findByID(lId,transConn);
			lReturn = reportLossOrFreezeBean.delete(cInfo, transConn);

		} catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e
					.getMessage());
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e
						.getMessage());
			}
		}

		return lReturn;
	}

	

	
	/**
	 * 审批方法
	 * added by zcwang end
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void doApproval(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException
	{
		Connection transConn=null;
		ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();
		try
		{	
			transConn = Database.getConnection();
			reportLossOrFreezeBean.doApproval(info, transConn);
		}
		catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e.getMessage());
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e.getMessage());
			}
		}
	}
	
	/**
	 * 取消审批方法
	 * added by zcwang end
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelApproval(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException
	{
		Connection transConn=null;
		ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();
		try
		{	
			transConn = Database.getConnection();
			reportLossOrFreezeBean.cancelApproval(info, transConn);
		}
		catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e.getMessage());
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e.getMessage());
			}
		}
	}
	
	/**
	 * 提交审批方法
	 * added by zcwang end
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void initApproval(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException
	{
		Connection transConn=null;
		ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();
		try
		{	
			transConn = Database.getConnection();
			reportLossOrFreezeBean.initApproval(info, transConn);
		}
		catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e.getMessage());
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e.getMessage());
			}
		}
	}
	
	/**
	 * 保存方法
	 * added by zcwang end
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException
	{
		Connection transConn=null;
		long lReturn = -1;
		ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();
		try
		{	
			transConn = Database.getConnection();
			lReturn = reportLossOrFreezeBean.save(info, transConn);
		}
		catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e.getMessage());
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e.getMessage());
			}
		}
		return lReturn;
	}
	
	/**
	 * 复核方法
	 * added by zcwang end
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long  check(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException
	{
		Connection transConn=null;
		long lReturn = -1;
		ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();
		try
		{	
			transConn = Database.getConnection();
			lReturn = reportLossOrFreezeBean.check(info, transConn);
		}
		catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e.getMessage());
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e.getMessage());
			}
		}
		return lReturn;
	}
	
	/**
	 * 取消复核方法
	 * added by zcwang end
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelCheck(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException
	{
		Connection transConn=null;
		long lReturn = -1;
		ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();
		try
		{	
			transConn = Database.getConnection();
			lReturn = reportLossOrFreezeBean.cancelCheck(info, transConn);
		}
		catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e.getMessage(),e);
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e.getMessage(),e);
			}
		}
		return lReturn;
	}
	
	/**
	 * 删除方法
	 * added by zcwang end
	 * @param info
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long  delete(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException
	{
		Connection transConn=null;
		long lReturn = -1;
		ReportLossOrFreezeBean reportLossOrFreezeBean = new ReportLossOrFreezeBean();
		try
		{	
			transConn = Database.getConnection();
			lReturn = reportLossOrFreezeBean.delete(info, transConn);
		}
		catch (Exception e) {
			throw new IRollbackException(this.getSessionContext(), e.getMessage());
		} finally {
			try {
				finalizeConnection(transConn);
			} catch (ITreasuryDAOException e) {
				throw new IRollbackException(this.getSessionContext(), e.getMessage());
			}
		}
		return lReturn;
	}
}
