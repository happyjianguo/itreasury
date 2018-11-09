package com.iss.itreasury.creditrating.bizdelegation;

import java.rmi.RemoteException;
import java.util.List;

import com.iss.itreasury.creditrating.creditevalution.bizlogic.Crert_CreditRatingRevalued;
import com.iss.itreasury.creditrating.creditevalution.bizlogic.Crert_CreditRatingRevaluedHome;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingDetailInfo;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingRevaluedInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class Crert_CreditRatingRevaluedDelegation {

	
	private Crert_CreditRatingRevalued crert_CreditRatingRevalued = null; 

	public Crert_CreditRatingRevaluedDelegation()throws RemoteException, IRollbackException
	{
		  try{
				
				Crert_CreditRatingRevaluedHome crert_CreditRatingRevaluedHome = (Crert_CreditRatingRevaluedHome)EJBHomeFactory.getFactory().lookUpHome(Crert_CreditRatingRevaluedHome.class);
				
				crert_CreditRatingRevalued = crert_CreditRatingRevaluedHome.create();
				
			}catch(Exception e){
	
				throw new RemoteException(e.getMessage());
			}
		
	}
	
	public long save(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws RemoteException,IRollbackException
	{
		return crert_CreditRatingRevalued.save(crert_CreditRatingRevaluedInfo);
	}
	
	
	public Crert_CreditRatingDetailInfo findByIDCreditRatingDetail(long creditID)throws RemoteException,IRollbackException
	{
		return crert_CreditRatingRevalued.findByIDCreditRatingDetail(creditID);
	}
	
	public Crert_CreditRatingRevaluedInfo findByID(long ID)throws RemoteException,IRollbackException
	{
		return crert_CreditRatingRevalued.findByID(ID);
	}
	
	public long doApproval(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws IRollbackException,RemoteException
	{
		return crert_CreditRatingRevalued.doApproval(crert_CreditRatingRevaluedInfo);
	}
	
	public long cancelApproval(Crert_CreditRatingRevaluedInfo crert_CreditRatingRevaluedInfo)throws IRollbackException,RemoteException
	{
		return crert_CreditRatingRevalued.cancelApproval(crert_CreditRatingRevaluedInfo);
	}
	
	public long delete(long ID)throws IRollbackException,RemoteException
	{
		return crert_CreditRatingRevalued.delete(ID);
	}
	
	 public List getCreditRatingRevaluedList(long creditRatingID)throws IRollbackException,RemoteException
	 {
		 return crert_CreditRatingRevalued.getCreditRatingRevaluedList(creditRatingID);
	 }
	
}
