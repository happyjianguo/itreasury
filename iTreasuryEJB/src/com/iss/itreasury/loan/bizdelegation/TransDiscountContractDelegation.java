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

import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountcontract.bizlogic.TransDiscountContract;
import com.iss.itreasury.loan.transdiscountcontract.bizlogic.TransDiscountContractHome;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractInfo;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractQueryInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

public class TransDiscountContractDelegation {
	
	private TransDiscountContract transDiscountContractFacade = null;
	
	public TransDiscountContractDelegation() throws RemoteException{
		try
		{
		    TransDiscountContractHome home;
			try {
				home =
					(TransDiscountContractHome) EJBHomeFactory.getFactory().lookUpHome(
					        TransDiscountContractHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory���Ӵ���",e);
			}
			transDiscountContractFacade = (TransDiscountContract) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException",ce);
		}

	}
	
	/**
	 *��ͬ�ı������
	*/
	public long save(TransDiscountContractInfo info) throws java.rmi.RemoteException, LoanException
    {        
        try 
        {
            return transDiscountContractFacade.save(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }

	/**
	 *��ͬ��ɾ������
	*/
	public void delete(long lID) throws java.rmi.RemoteException, LoanException
	{        
        try 
        {
            transDiscountContractFacade.delete(lID);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }

	/**
	 *��ͬ����˲���
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
	{        
        try 
        {
            transDiscountContractFacade.check(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }

	/**
	 *��ͬ��ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException
	{        
        try 
        {
            transDiscountContractFacade.cancel(lID);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }

	/**
	 *��ͬ�ĵ��ʲ�ѯ����
	*/
	public TransDiscountContractInfo findByID(long lID) throws java.rmi.RemoteException, LoanException
	{        
	    TransDiscountContractInfo info = new TransDiscountContractInfo();
	    try 
        {
            info = transDiscountContractFacade.findByID(lID);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
        return info;
    }

	/**
	 *��ͬ�Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(TransDiscountContractQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{        
	    Collection c = null;
	    try 
        {
            c = transDiscountContractFacade.findByMultiOption(qInfo);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
        return c;
    }

	/**
	 *��ͬ�µ�Ʊ�ݲ�ѯ����
	*/
	public Collection findBillByTransDiscountContractID(long lTransDiscountContractID) throws java.rmi.RemoteException, LoanException
	{        
	    Collection c = null;
	    try 
        {
            c = transDiscountContractFacade.findBillByTransDiscountContractID(lTransDiscountContractID);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
        return c;
    }
    /**
     *��ͬ�µ�Ʊ�ݱ������
     */
    public long saveBill(TransDiscountContractBillInfo info) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            return transDiscountContractFacade.saveBill(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }
    /**
     * ��ͬ���ύ����
     * @param info
     * @throws java.rmi.RemoteException
     * @throws LoanException
     */
    public void submit(TransDiscountContractInfo info) throws java.rmi.RemoteException, LoanException
	{
    	try 
        {
            transDiscountContractFacade.submit(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
	}
	
    /**
	 *���ͺ�ͬ�ĸ��˲���
	*/
	public void cpfCheck(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
	{        
        try 
        {
            transDiscountContractFacade.cpfCheck(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }
	/**
	 *���ͺ�ͬ�Ķ�ʲ�ѯ����
	*/
	public Collection cpfFindByMultiOption(TransDiscountContractQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{        
	    Collection c = null;
	    try 
        {
            c = transDiscountContractFacade.cpfFindByMultiOption(qInfo);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
        return c;
    }
}
