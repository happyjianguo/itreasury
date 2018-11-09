package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.transcommission.bizlogic.TransCommission;
import com.iss.itreasury.settlement.transcommission.bizlogic.TransCommissionBean;
import com.iss.itreasury.settlement.transcommission.bizlogic.TransCommissionHome;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionConditionInfo;
import com.iss.itreasury.settlement.transcommission.dataentity.TransCommissionResultInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;

public class TransCommissionDelegation
{
    private TransCommission commissionFacade =null;
    
    TransCommissionBean transCommissionBean = new TransCommissionBean();
    
    /**
     * Constructor for TransCommissionDelegation.
     */
    public TransCommissionDelegation() throws RemoteException, IRollbackException
    {
        try
        {

            TransCommissionHome home =  (TransCommissionHome) EJBHomeFactory.getFactory().lookUpHome(TransCommissionHome.class);
            
            commissionFacade = (TransCommission) home.create();
            
        }
        catch (RemoteException e)
        {
            throw e;
        }
        catch (IException e)
        {
            e.printStackTrace();
            e.printStackTrace();throw new RemoteException();
        }
        catch (CreateException e)
        {
            e.printStackTrace();
            e.printStackTrace();throw new RemoteException();
        }
    }
    
    /**
     *@description:
     *PageLoader
     *@param conditionInfo
     *@return
     *@throws Exception
     */
    public PageLoader findPrepareByCondition(TransCommissionConditionInfo conditionInfo)  throws Exception
    {
        System.out.println("==============In CommissionDelegation.findPrepareByCondition==============");
        return transCommissionBean.findPrepareByCondition(conditionInfo);
    }
    
    /**
     *@description:
     *PageLoader
     *@param conditionInfo
     *@return
     *@throws Exception
     */
    public PageLoader findDoneByCondition(TransCommissionConditionInfo conditionInfo)  throws Exception
    {
        System.out.println("==============In CommissionDelegation.findDoneByCondition==============");
        return transCommissionBean.findDoneByCondition(conditionInfo);
    }
    
    /**
     *@description:
     *TransCommissionResultInfo
     *@param transNo
     *@return
     *@throws Exception
     */
    public TransCommissionResultInfo findByTransNo(String transNo) throws Exception
    {
        System.out.println("==============In CommissionDelegation.transNo==============");
        return transCommissionBean.findByTransNo(transNo);
    }
    public List findWithTransNo(String transNo) throws Exception{
    	return transCommissionBean.findWithTransNo(transNo);
    }
    
    /**
     *@description:
     *long
     *@param info
     *@return
     *@throws RemoteException
     *@throws IRollbackException
     */
    public long commissionSettlement( Collection coll ) throws RemoteException,IRollbackException
    {
        System.out.println("==============In CommissionDelegation.commissionSettlement==============");
        return commissionFacade.commissionSettlement(coll);
    }
}