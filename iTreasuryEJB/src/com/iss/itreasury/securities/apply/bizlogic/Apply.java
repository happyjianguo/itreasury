package com.iss.itreasury.securities.apply.bizlogic;

import java.rmi.RemoteException;
import java.util.*;
import java.sql.Timestamp;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.system.approval.dataentity.*;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Apply extends javax.ejb.EJBObject
{
	/**
	 *������ı������
	*/
	public long save(ApplyInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *�������ɾ������
	*/
	public void delete(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *���������˲���
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *�������ȡ������
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *������ĵ��ʲ�ѯ����
	*/
	public ApplyInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *������Ķ�ʲ�ѯ����
	*/
	public Collection findByMultiOption(ApplyQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *�������µ�Ͷ�����䱣�����
	*/
	public long saveBidRange(BidRangeInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *�������µ�Ͷ�������ѯ����
	*/
	public Collection findBidRangeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *�������µ�Ͷ������ɾ������
	*/
	public void deleteBidRange(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException;
	/**
	 *���������������
	*/
	public long doApproval(ApplyInfo info, ApprovalTracingInfo approvalInfo) throws java.rmi.RemoteException, SecuritiesException;
	/**
	 *�������ȡ����������
	*/
	public long cancelApproval(ApplyInfo info, ApprovalTracingInfo approvalInfo) throws java.rmi.RemoteException, SecuritiesException;
	
	/**
	 *�������µ�ծȯ���ౣ�����
	*/
	public long saveBondType(BondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *�������µ�ծȯ�����ѯ����
	*/
	public Collection findBondTypeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *�������µ�ծȯ����ɾ������
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException;

	/**
		* ת�������������Ȩ�ޣ�֧������ת��
		* @param lID
		* @param lUserID
		* @throws java.rmi.RemoteException
		* @throws SecuritiesException
		*/
	public void transferApplyRight(long lID[], long lUserID) throws java.rmi.RemoteException, SecuritiesException;

}
