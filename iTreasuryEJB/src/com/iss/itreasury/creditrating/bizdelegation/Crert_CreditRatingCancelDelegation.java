package com.iss.itreasury.creditrating.bizdelegation;

import java.rmi.RemoteException;

import com.iss.itreasury.creditrating.becominginvalid.bizlogic.Crert_CreditRatingCancel;
import com.iss.itreasury.creditrating.becominginvalid.bizlogic.Crert_CreditRatingCancelHome;
import com.iss.itreasury.creditrating.becominginvalid.dataentity.Crert_CreditRatingCancelInfo;
import com.iss.itreasury.creditrating.creditevalution.dataentity.Crert_CreditRatingDetailInfo;
import com.iss.itreasury.creditrating.util.CreRtConstant;
import com.iss.itreasury.util.EJBHomeFactory;

import com.iss.itreasury.util.IRollbackException;

public class Crert_CreditRatingCancelDelegation {
	
	private Crert_CreditRatingCancel crert_CreditRatingCancel = null; 
    
	public Crert_CreditRatingCancelDelegation()throws RemoteException, IRollbackException
	{
       try{
			
    	   Crert_CreditRatingCancelHome crert_CreditRatingCancelHome = (Crert_CreditRatingCancelHome)EJBHomeFactory.getFactory().lookUpHome(Crert_CreditRatingCancelHome.class);
    	   
    	   crert_CreditRatingCancel     =  crert_CreditRatingCancelHome.create();
			
		}catch(Exception e){

			throw new RemoteException(e.getMessage());
		}
	}
	
	public Crert_CreditRatingDetailInfo getCrert_CreditRatingDetailInfo(long ID)throws RemoteException,IRollbackException
	{
		return crert_CreditRatingCancel.getCrert_CreditRatingDetailInfo(ID);
	}
	
	public long save(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws RemoteException,IRollbackException
	{
		return crert_CreditRatingCancel.save(crert_CreditRatingCancelInfo);
	}
	
	public Crert_CreditRatingCancelInfo getCreditRatingCancelByCondition(long ID)throws IRollbackException,RemoteException
	{
		return crert_CreditRatingCancel.getCreditRatingCancelByCondition(ID);
	}
	
	public long doApproval(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws IRollbackException,RemoteException
	{
		return crert_CreditRatingCancel.doApproval(crert_CreditRatingCancelInfo);
	}
	
	public long cancelApproval(Crert_CreditRatingCancelInfo crert_CreditRatingCancelInfo)throws IRollbackException,RemoteException
	{
		return crert_CreditRatingCancel.cancelApproval(crert_CreditRatingCancelInfo);
	}
	
	public long delete(long ID)throws IRollbackException,RemoteException
	{
		return crert_CreditRatingCancel.updateStatus(CreRtConstant.CreRtStatus.DELETE,ID);
	}
}
