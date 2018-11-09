package com.iss.itreasury.securities.securitiescontractextend.bizlogic;

import javax.ejb.EJBObject;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiescontract.dataentity.ContractBondTypeInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractQueryInfo;
import com.iss.itreasury.securities.securitiescontractextend.dataentity.SecuritiesContractExtendInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;

/**
 *
 * @Name:        SecuritiesContractExtend.java
 * @Description: ί����ƺ�ͬչ�� 
 * @Author:      gqfang 
 * @Create Date: 2005-4-19 
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface SecuritiesContractExtend extends EJBObject
{


	/**
	 * ���ҵ�����ͬ��Ϣ
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public SecuritiesContractExtendInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * �����ͬ��Ϣ���������޸ĺ�ͬ
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long save(SecuritiesContractExtendInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * ȡ����ͬ
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * ���ҷ��������ĺ�ͬ��Ϣ�������޸Ĳ��Һ���˲���
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * ������ͬ
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * �����ͬ��֧����������
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void activateContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException;

	/**
	* ת�ƺ�ͬ������Ȩ�ޣ�֧������ת��
	* @param lID
	* @param lUserID
	* @throws java.rmi.RemoteException
	* @throws SecuritiesException
	*/
	public void transferContractRight(long lID[], long lUserID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * �ֶ�������ͬ��֧����������
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void endContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *��ͬ�µ�ծȯ���ౣ�����
	*/
	public long saveBondType(ContractBondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *��ͬ�µ�ծȯ�����ѯ����
	*/
	public java.util.Collection findBondTypeByContractID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 *��ͬ�µ�ծȯ����ɾ������
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException;

}