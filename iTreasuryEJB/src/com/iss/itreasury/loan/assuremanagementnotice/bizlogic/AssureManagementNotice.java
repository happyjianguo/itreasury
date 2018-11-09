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
package com.iss.itreasury.loan.assuremanagementnotice.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementQueryInfo;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface AssureManagementNotice extends javax.ejb.EJBObject
{
	/**
	 * ֪ͨ���ı������
	*/
	public long save(AssureManagementNoticeInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 * ֪ͨ������˲���
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 * ֪ͨ����ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;
	
	/**
	 * ֪ͨ���ĵ��ʲ�ѯ����
	*/
	public AssureManagementNoticeInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;
	/**
	 * ֪ͨ���Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(AssureManagementQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;
	/**
	 * ֪ͨ������������
	*/
	public long doApprovalAssureManagementNotice(AssureManagementNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException;
	
	/**
	 * ��֤����֪ͨ������������
	*/
	public long doApprovalAssureManagementNotice4Decognizance(AssureManagementNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException;
	
	public long cancelApproval(AssureManagementNoticeInfo nInfo)throws RemoteException, IRollbackException;
}