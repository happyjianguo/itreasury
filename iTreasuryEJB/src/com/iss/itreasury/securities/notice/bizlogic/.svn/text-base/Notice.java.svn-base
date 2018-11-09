/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.notice.bizlogic;
import java.rmi.RemoteException;
import java.util.*;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.notice.dataentity.*;
import com.iss.itreasury.securities.notice.exception.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Notice extends javax.ejb.EJBObject
{
	/**
	 *通知单的保存操作
	*/
	public long save(NoticeInfo info) throws java.rmi.RemoteException,SecuritiesException,IException;
	
	/**
	 * 通知单的审批操作
	 */
	public long doApproval(NoticeInfo nInfo) throws RemoteException,IRollbackException;
	
	/**
	 * 通知单的取消审批操作
	 */
	public long cancelApproval(NoticeInfo nInfo)throws RemoteException, IRollbackException;
	
	/**
	 *通知单的删除操作
	*/
	public void delete(long ID) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *通知单的审核操作
	*/
	public void check(ApprovalTracingInfo aInfo) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *通知单的取消操作
	*/
	public void cancel(long ID) throws java.rmi.RemoteException,SecuritiesException;

	/**
	 *通知单的单笔查询操作
	*/
	public NoticeInfo findByID(long ID) throws java.rmi.RemoteException,SecuritiesException;

	/**
	 *通知单的多笔查询操作(和交割单关联)
	*/
	public Collection findByMultiOption(NoticeQueryInfo info) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *通知单的多笔查询操作(和合同关联)
	*/
	public Collection findByMultiOption1(NoticeQueryInfo info) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *委托理财业务通知单新增所选证券信息
	*/
	public long saveNoticeWithSecurities(NoticeWithSecuritiesInfo info) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *委托理财业务通知单删除所选证券信息
	*/
	public void deleteNoticeWithSecurities(long[] lIDList) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *委托理财业务通知单查询所选证券信息
	*/
	public Collection findNoticeWithSecuritiesByNoticeID(long lNoticeID) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *债券承销业务通知单查询合同执行情况
	*/
	public Collection findContractWithSecuritiesByContractID(long lContractID) throws java.rmi.RemoteException,SecuritiesException;
}
