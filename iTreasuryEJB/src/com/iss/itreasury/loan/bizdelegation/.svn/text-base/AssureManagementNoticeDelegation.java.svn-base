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

package com.iss.itreasury.loan.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.loan.assuremanagementnotice.bizlogic.AssureManagementNotice;
import com.iss.itreasury.loan.assuremanagementnotice.bizlogic.AssureManagementNoticeHome;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementQueryInfo;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

public class AssureManagementNoticeDelegation {
	
	private AssureManagementNotice assureManagementNoticeFacade = null;
	
	public AssureManagementNoticeDelegation() throws RemoteException{
		try
		{
		    AssureManagementNoticeHome home;
			try {
				home =
					(AssureManagementNoticeHome) EJBHomeFactory.getFactory().lookUpHome(
					        AssureManagementNoticeHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			assureManagementNoticeFacade = (AssureManagementNotice) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}

	}
	
	/**
	 *通知单的保存操作
	*/
	public long save(AssureManagementNoticeInfo info) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            return assureManagementNoticeFacade.save(info);            
        }
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw new LoanDAOException(e);
        }
	}	

	/**
	 *通知单的审核操作
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
	{ 
	    try 
        {
			assureManagementNoticeFacade.check(info);
        }
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw new LoanDAOException(e);
        }
	}

	/**
	 *通知单的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            assureManagementNoticeFacade.cancel(lID);            
        }
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw new LoanDAOException(e);
        }
	}

	/**
	 *通知单的单笔查询操作
	*/
	public AssureManagementNoticeInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{
	    AssureManagementNoticeInfo info = new AssureManagementNoticeInfo();
	    try 
        {
            info = assureManagementNoticeFacade.findByID(lID);            
        }
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw new LoanDAOException(e);
        }
        return info;
	}
	
	/**
	 * 通知单的多笔查询操作
	*/
	public Collection findByMultiOption(AssureManagementQueryInfo qInfo
	       )
	throws java.rmi.RemoteException, LoanException
	{
	    Collection c = null;
	    try 
        {
            c = assureManagementNoticeFacade.findByMultiOption(qInfo);            
        }
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw new LoanDAOException(e);
        }
        return c;
	}	
}
