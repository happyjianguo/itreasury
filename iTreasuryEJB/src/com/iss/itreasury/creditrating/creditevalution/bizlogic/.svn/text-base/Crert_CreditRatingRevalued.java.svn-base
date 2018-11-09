package com.iss.itreasury.creditrating.creditevalution.bizlogic;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingDetailInfo;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingRevaluedInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public interface Crert_CreditRatingRevalued extends EJBObject {

	public long save(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws IRollbackException,RemoteException;
	
	public Crert_CreditRatingDetailInfo findByIDCreditRatingDetail(long creditID)throws IRollbackException,RemoteException;
	
	public Crert_CreditRatingRevaluedInfo findByID(long ID)throws IRollbackException,RemoteException;
	
	public long doApproval(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws IRollbackException,RemoteException;
	
	public long cancelApproval(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws IRollbackException,RemoteException;
	
	public long delete(long ID)throws IRollbackException,RemoteException;
	
	 public List getCreditRatingRevaluedList(long creditRatingID)throws IRollbackException,RemoteException;
	
	
}
