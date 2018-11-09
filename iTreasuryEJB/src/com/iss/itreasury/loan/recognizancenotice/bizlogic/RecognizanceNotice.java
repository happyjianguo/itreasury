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
package com.iss.itreasury.loan.recognizancenotice.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo;
import com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementQueryInfo;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.recognizancenotice.dataentity.RecognizanceNoticeInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author quanshao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface RecognizanceNotice extends javax.ejb.EJBObject
{
	/**
	 * ֪ͨ���ı������
	*/
	public long save(RecognizanceNoticeInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 * ֪ͨ������˲���
	*/

	/**
	 * ֪ͨ����ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;
	/**
	 * ֪ͨ����ɾ������
	*/
	public void del(long lID) throws java.rmi.RemoteException, LoanException;
	/**
	 * ֪ͨ���ĵ��ʲ�ѯ����
	*/
	 public RecognizanceNoticeInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;
	/**
	 * ֪ͨ���Ķ�ʲ�ѯ����
	*/
	/**
	 * ֪ͨ������������
	*/
	 public long doApprovalRecognizanceNotice(RecognizanceNoticeInfo info) throws RemoteException,
		IRollbackException,IException;
	 
	 public long cancelApproval(RecognizanceNoticeInfo info)throws RemoteException, IRollbackException;
}