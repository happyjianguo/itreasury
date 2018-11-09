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
				throw new RemoteException("EJBHomeFactory���Ӵ���",e);
			}
			transDiscountCredenceFacade = (TransDiscountCredence) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException",ce);
		}

	}
	
	/**
	 *ƾ֤�ı������
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
	 *ƾ֤��ɾ������
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
	 *ƾ֤����˲���
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
            
			/*  TOCONFIG��TODELETE  */
			/*
			 * ��Ʒ������������Ŀ 
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
			
			/*  TOCONFIG��END  */	               
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
	 *ƾ֤��ȡ������
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
	 *ƾ֤�ĵ��ʲ�ѯ����
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
	 *ƾ֤�Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(TransDiscountCredenceQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
	{
	    Collection c = null;
		try 
		{
            
			/*  TOCONFIG��TODELETE  */
			/*
			 * ��Ʒ������������Ŀ 
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
			
			/*  TOCONFIG��END  */
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
	 *ƾ֤�µ�Ʊ�ݲ�ѯ����
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
	 *Ʊ�ݲ�ѯ����
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
	 *Ʊ��ѡ�����
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
	 * ѡ����Ʊ����Ϣ������Loan_DiscountContractBill��
	 * @param SelectedTransDiscountBillInfo ѡ����Ʊ����Ϣ
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
