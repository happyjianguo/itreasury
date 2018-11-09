package com.iss.itreasury.settlement.reportlossorfreeze.bizlogic;

import java.rmi.RemoteException;

import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeInfo;
import com.iss.itreasury.util.IRollbackException;

public interface ReportLossOrFreeze extends javax.ejb.EJBObject {
	public long lossSaveAndCheck(ReportLossOrFreezeInfo info)
			throws RemoteException, IRollbackException;

	public long lossDeleteAndCancelCheck(ReportLossOrFreezeInfo rInfo)
			throws RemoteException, IRollbackException;
	public void doApproval(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException;
	public void cancelApproval(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException;
	public void initApproval(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException;
	public long delete(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException;
	public long save(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException;
	public long check(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException;
	public long cancelCheck(ReportLossOrFreezeInfo info) throws RemoteException, IRollbackException;
}
