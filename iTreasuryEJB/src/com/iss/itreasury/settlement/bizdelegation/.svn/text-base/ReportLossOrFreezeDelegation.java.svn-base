/*
 * Created on 2004-11-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;

import com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.ReportLossOrFreeze;
import com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.ReportLossOrFreezeHome;
import com.iss.itreasury.settlement.reportlossorfreeze.dataentity.ReportLossOrFreezeInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author yychen
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ReportLossOrFreezeDelegation {
	private ReportLossOrFreeze reportLossOrFreeze = null;

	public ReportLossOrFreezeDelegation() throws RemoteException {
		try {
			ReportLossOrFreezeHome home = (ReportLossOrFreezeHome) EJBHomeFactory
					.getFactory().lookUpHome(ReportLossOrFreezeHome.class);
			reportLossOrFreeze = home.create();
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}

	public long lossSaveAndCheck(ReportLossOrFreezeInfo info)
			throws RemoteException, IRollbackException, IException {
		return reportLossOrFreeze.lossSaveAndCheck(info);
	}

	public long lossDeleteAndCancelCheck(ReportLossOrFreezeInfo rInfo)
			throws RemoteException, IRollbackException, IException {
		return reportLossOrFreeze.lossDeleteAndCancelCheck(rInfo);
	}
	
	public void doApproval(ReportLossOrFreezeInfo rInfo)
	throws RemoteException, IRollbackException, IException {
		 reportLossOrFreeze.doApproval(rInfo);
		}
	public void cancelApproval(ReportLossOrFreezeInfo rInfo)
	throws RemoteException, IRollbackException, IException {
		 reportLossOrFreeze.cancelApproval(rInfo);
		}
	public void initApproval(ReportLossOrFreezeInfo rInfo)
	throws RemoteException, IRollbackException, IException {
		 reportLossOrFreeze.initApproval(rInfo);
		}
	public long delete(ReportLossOrFreezeInfo rInfo)
	throws RemoteException, IRollbackException, IException {
		return  reportLossOrFreeze.delete(rInfo);
		}
	public long save(ReportLossOrFreezeInfo rInfo)
	throws RemoteException, IRollbackException, IException {
		return  reportLossOrFreeze.save(rInfo);
		}
	public long check(ReportLossOrFreezeInfo rInfo)
	throws RemoteException, IRollbackException, IException {
		 return reportLossOrFreeze.check(rInfo);
		}
	public long cancelCheck(ReportLossOrFreezeInfo rInfo)
	throws RemoteException, IRollbackException, IException {
		return reportLossOrFreeze.cancelCheck(rInfo);
		}
}
