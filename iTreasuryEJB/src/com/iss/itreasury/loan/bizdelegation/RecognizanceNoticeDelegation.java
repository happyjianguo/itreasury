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

import javax.ejb.CreateException;

import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.recognizancenotice.bizlogic.RecognizanceNotice;
import com.iss.itreasury.loan.recognizancenotice.bizlogic.RecognizanceNoticeHome;
import com.iss.itreasury.loan.recognizancenotice.dataentity.RecognizanceNoticeInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

public class RecognizanceNoticeDelegation {
	
	private RecognizanceNotice recognizanceNoticeNoticeFacade = null;
	
	public RecognizanceNoticeDelegation() throws RemoteException{
		try
		{
			RecognizanceNoticeHome home;
			try {
				home =
					(RecognizanceNoticeHome) EJBHomeFactory.getFactory().lookUpHome(
							RecognizanceNoticeHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory���Ӵ���",e);
			}
			recognizanceNoticeNoticeFacade = (RecognizanceNotice) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException",ce);
		}

	}
	
	/**
	 *֪ͨ���ı������
	*/
	public long save(RecognizanceNoticeInfo info) throws java.rmi.RemoteException, LoanException,IException
	{
	    try 
        {
            return recognizanceNoticeNoticeFacade.save(info);            
        }
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
			throw new IException(e.getMessage());
        }
	}	

	/**
	 *֪ͨ������˲���
	*/
	
	/**
	 *֪ͨ����ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
	    	recognizanceNoticeNoticeFacade.cancel(lID);            
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
	 *֪ͨ����ɾ������
	*/
	public void del(long lID) throws java.rmi.RemoteException, LoanException
	{
	    try 
        {
	    	recognizanceNoticeNoticeFacade.del(lID);            
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
	 *֪ͨ���ĵ��ʲ�ѯ����
	*/
	
	
	/**
	 * ֪ͨ���Ķ�ʲ�ѯ����
	*/
	
}
