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

import com.iss.itreasury.loan.assurechargenotice.bizlogic.AssureChargeNotice;
import com.iss.itreasury.loan.assurechargenotice.bizlogic.AssureChargeNoticeHome;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeQueryInfo;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

public class AssureChargeNoticeDelegation {
	
	private AssureChargeNotice assureChargeNoticeFacade = null;
	
	public AssureChargeNoticeDelegation() throws RemoteException{
		try
		{
		    AssureChargeNoticeHome home;
			try {
				home =
					(AssureChargeNoticeHome) EJBHomeFactory.getFactory().lookUpHome(
					        AssureChargeNoticeHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			assureChargeNoticeFacade = (AssureChargeNotice) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}

	}
	
	/**
	 *通知单的保存操作
	*/
	public long save(AssureChargeNoticeInfo info) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            return assureChargeNoticeFacade.save(info);            
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
			assureChargeNoticeFacade.check(info);
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
            assureChargeNoticeFacade.cancel(lID);            
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
	public AssureChargeNoticeInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{
	    AssureChargeNoticeInfo info = new AssureChargeNoticeInfo();
	    try 
        {
            info = assureChargeNoticeFacade.findByID(lID);            
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
	public Collection findByMultiOption(AssureChargeQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{
	    Collection c = null;
	    try 
        {
            c = assureChargeNoticeFacade.findByMultiOption(qInfo);            
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
