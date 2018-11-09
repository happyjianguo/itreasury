package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.notice.bizlogic.Notice;
import com.iss.itreasury.securities.notice.bizlogic.NoticeHome;
import com.iss.itreasury.securities.notice.dataentity.*;
import com.iss.itreasury.securities.notice.exception.NoticeException;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.system.approval.dataentity.*;
import java.util.*;

public class NoticeDelegation {
	
	private Notice noticeFacade = null;
	
	public NoticeDelegation() throws RemoteException
	{
		try
		{
			NoticeHome home;
			try {
				home =
					(NoticeHome) EJBHomeFactory.getFactory().lookUpHome(
				NoticeHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			noticeFacade = (Notice) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}

	}


	/**
	 *通知单的保存操作
	*/
	public long save(NoticeInfo info) throws java.rmi.RemoteException,SecuritiesException,IException{
		return noticeFacade.save(info);
	}

	/**
	 * 通知单的审批操作
	 */
	public long doApproval(NoticeInfo nInfo) throws RemoteException,IRollbackException{
		return noticeFacade.doApproval(nInfo);
	}
	
	/**
	 * 通知单的取消审批操作
	 */
	public long cancelApproval(NoticeInfo nInfo)throws RemoteException, IRollbackException{
		return noticeFacade.cancelApproval(nInfo);
	}
	/**
	 *通知单的取消操作
	*/
	public void cancel(long ID) throws java.rmi.RemoteException,SecuritiesException
	{
	    noticeFacade.cancel(ID);
	}

	/**
	 *通知单的删除操作
	*/
	public void delete(long ID) throws java.rmi.RemoteException,SecuritiesException{
		noticeFacade.delete(ID);
	}	
	
	/**
	 * 
	 *通知单的复核操作
	*/
	public void check(ApprovalTracingInfo ATInfo) throws java.rmi.RemoteException,SecuritiesException{
		noticeFacade.check(ATInfo);
	}
	
	/**
	 *通知单的多笔查询操作(和交割单关联)
	*/
	public Collection findByMultiOption(NoticeQueryInfo qInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return noticeFacade.findByMultiOption(qInfo);
	}
	
	/**
	 *通知单的多笔查询操作(和合同关联)
	*/
	public Collection findByMultiOption1(NoticeQueryInfo qInfo) throws java.rmi.RemoteException,SecuritiesException
	{
		return noticeFacade.findByMultiOption1(qInfo);
	}


	/**
	 *通知单的单笔查询操作
	*/
	public NoticeInfo findByID(long lID) throws java.rmi.RemoteException,SecuritiesException
	{
		return noticeFacade.findByID(lID);
	}
	
	/**
	 *委托理财业务通知单新增所选证券信息
	*/
	public long saveNoticeWithSecurities(NoticeWithSecuritiesInfo info) throws java.rmi.RemoteException,SecuritiesException
	{
		return noticeFacade.saveNoticeWithSecurities(info);
	}
	
	/**
	 *委托理财业务通知单删除所选证券信息
	*/
	public void deleteNoticeWithSecurities(long[] lIDList) throws java.rmi.RemoteException,SecuritiesException
	{
		noticeFacade.deleteNoticeWithSecurities(lIDList);
	}
	
	/**
	 *委托理财业务通知单查询所选证券信息
	*/
	public Collection findNoticeWithSecuritiesByNoticeID(long lNoticeID) throws java.rmi.RemoteException,SecuritiesException
	{
		System.out.println("here!");
		return noticeFacade.findNoticeWithSecuritiesByNoticeID(lNoticeID);
	}
	
	/**
	 *债券承销业务通知单查询合同执行情况
	*/
	public Collection findContractWithSecuritiesByContractID(long lContractID) throws java.rmi.RemoteException,SecuritiesException
	{
		System.out.println("contract in!");
		return noticeFacade.findContractWithSecuritiesByContractID(lContractID);
	}
}
