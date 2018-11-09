package com.iss.itreasury.creditrating.becominginvalid.bizlogic;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.iss.itreasury.creditrating.becominginvalid.dataentity.Crert_CreditRatingCancelInfo;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingDetailInfo;
import com.iss.itreasury.util.IRollbackException;

public interface Crert_CreditRatingCancel extends EJBObject {

	public Crert_CreditRatingDetailInfo getCrert_CreditRatingDetailInfo(long ID)throws IRollbackException,RemoteException;
    
	public long save(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws IRollbackException,RemoteException;
	
	public Crert_CreditRatingCancelInfo getCreditRatingCancelByCondition(long ID)throws IRollbackException,RemoteException;
	
	public long doApproval(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws IRollbackException,RemoteException;
	
	public long cancelApproval(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws IRollbackException,RemoteException;

	public long updateStatus(long lStatus,long ID)throws IRollbackException,RemoteException;
}
