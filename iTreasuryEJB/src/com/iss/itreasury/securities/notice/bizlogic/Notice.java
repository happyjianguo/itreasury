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
	 *֪ͨ���ı������
	*/
	public long save(NoticeInfo info) throws java.rmi.RemoteException,SecuritiesException,IException;
	
	/**
	 * ֪ͨ������������
	 */
	public long doApproval(NoticeInfo nInfo) throws RemoteException,IRollbackException;
	
	/**
	 * ֪ͨ����ȡ����������
	 */
	public long cancelApproval(NoticeInfo nInfo)throws RemoteException, IRollbackException;
	
	/**
	 *֪ͨ����ɾ������
	*/
	public void delete(long ID) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *֪ͨ������˲���
	*/
	public void check(ApprovalTracingInfo aInfo) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *֪ͨ����ȡ������
	*/
	public void cancel(long ID) throws java.rmi.RemoteException,SecuritiesException;

	/**
	 *֪ͨ���ĵ��ʲ�ѯ����
	*/
	public NoticeInfo findByID(long ID) throws java.rmi.RemoteException,SecuritiesException;

	/**
	 *֪ͨ���Ķ�ʲ�ѯ����(�ͽ������)
	*/
	public Collection findByMultiOption(NoticeQueryInfo info) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *֪ͨ���Ķ�ʲ�ѯ����(�ͺ�ͬ����)
	*/
	public Collection findByMultiOption1(NoticeQueryInfo info) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *ί�����ҵ��֪ͨ��������ѡ֤ȯ��Ϣ
	*/
	public long saveNoticeWithSecurities(NoticeWithSecuritiesInfo info) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *ί�����ҵ��֪ͨ��ɾ����ѡ֤ȯ��Ϣ
	*/
	public void deleteNoticeWithSecurities(long[] lIDList) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *ί�����ҵ��֪ͨ����ѯ��ѡ֤ȯ��Ϣ
	*/
	public Collection findNoticeWithSecuritiesByNoticeID(long lNoticeID) throws java.rmi.RemoteException,SecuritiesException;
	
	/**
	 *ծȯ����ҵ��֪ͨ����ѯ��ִͬ�����
	*/
	public Collection findContractWithSecuritiesByContractID(long lContractID) throws java.rmi.RemoteException,SecuritiesException;
}
