/*
 * Created on 2004-8-3
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

import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountcredence.bizlogic.TransDiscountCredence;
import com.iss.itreasury.loan.transdiscountcredence.bizlogic.TransDiscountCredenceHome;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.SelectedTransDiscountBillInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo;
import com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceQueryInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

public class TransDiscountCredenceDelegation {
	
	private TransDiscountCredence transDiscountCredenceFacade = null;
	
	public TransDiscountCredenceDelegation() throws RemoteException{
		try
		{
		    TransDiscountCredenceHome home;
			try {
				home =
					(TransDiscountCredenceHome) EJBHomeFactory.getFactory().lookUpHome(
					        TransDiscountCredenceHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			transDiscountCredenceFacade = (TransDiscountCredence) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}

	}
	
	/**
	 *凭证的保存操作
	*/
	public long save(TransDiscountCredenceInfo info) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            return transDiscountCredenceFacade.save(info);            
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
	 *凭证的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            transDiscountCredenceFacade.delete(lID);            
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
	 *凭证的审核操作
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            
			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */
            
//			if(Env.getProjectName().equals(Constant.ProjectName.CNMEF))
//			{
//				transDiscountCredenceFacade.check(info);
//				
//			}
//			else if(Env.getProjectName().equals(Constant.ProjectName.CPF))
//			{
//				transDiscountCredenceFacade.cpfCheck(info);
//			}

			transDiscountCredenceFacade.check(info);
			
			/*  TOCONFIG—END  */	               
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
	 *凭证的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            transDiscountCredenceFacade.cancel(lID);            
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
	 *凭证的单笔查询操作
	*/
	public TransDiscountCredenceInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{
	    TransDiscountCredenceInfo info = new TransDiscountCredenceInfo();
	    try 
        {
            info = transDiscountCredenceFacade.findByID(lID);            
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
	 *凭证的多笔查询操作
	*/
	public Collection findByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{
	    Collection c = null;
		try 
		{
            
			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */
			 
//			if(Env.getProjectName().equals(Constant.ProjectName.CNMEF))
//			{
//				c = transDiscountCredenceFacade.findByMultiOption(qInfo);
//			}
//			else if(Env.getProjectName().equals(Constant.ProjectName.CPF))
//			{
//				c = transDiscountCredenceFacade.cpfFindByMultiOption(qInfo);
//			}
			
			c = transDiscountCredenceFacade.findByMultiOption(qInfo);
			
			/*  TOCONFIG—END  */
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
	
	/**
	 *凭证下的票据查询操作
	*/
	public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID) throws java.rmi.RemoteException, LoanException
	{
	    Collection c = null;
	    try 
        {
            c = transDiscountCredenceFacade.findBillByTransDiscountCredenceID(lTransDiscountCredenceID);            
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

	/**
	 *票据查询操作
	*/
	public Collection findTransDiscountCredenceBill(long lContractID, long lCredenceID, long lBillSourceTypeID) throws java.rmi.RemoteException, LoanException
	{
	    Collection c = null;
	    try 
        {
            c = transDiscountCredenceFacade.findTransDiscountCredenceBill(lContractID, lCredenceID, lBillSourceTypeID);            
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

	/**
	 *票据选择操作
	*/
	public void saveTransDiscountCredenceBill(long lTransDiscountCredenceID,long[] lIDList) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            transDiscountCredenceFacade.saveTransDiscountCredenceBill(lTransDiscountCredenceID,lIDList);
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
	 * 选定的票据信息，操作Loan_DiscountContractBill表
	 * @param SelectedTransDiscountBillInfo 选定的票据信息
	 * @return SelectedTransDiscountBillInfo
	 */
	public SelectedTransDiscountBillInfo findBillInterestByBillID(SelectedTransDiscountBillInfo info) throws java.rmi.RemoteException, LoanException
	{
	    SelectedTransDiscountBillInfo newinfo = new SelectedTransDiscountBillInfo();
	    try 
        {
	        newinfo = transDiscountCredenceFacade.findBillInterestByBillID(info);            
        }
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw new LoanDAOException(e);
        }
        return newinfo;
	}
	
}
