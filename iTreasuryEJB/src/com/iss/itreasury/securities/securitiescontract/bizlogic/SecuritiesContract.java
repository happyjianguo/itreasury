package com.iss.itreasury.securities.securitiescontract.bizlogic;

/**
 * Created 2004-3-15 15:01:53
 * Code generated by the Sun ONE Studio EJB Builder
 * @author cpf
 */

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.securitiescontract.dao.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.util.*;
import com.iss.itreasury.system.bizlogic.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.*;

public interface SecuritiesContract extends javax.ejb.EJBObject
{

	/**
	 * ���ҵ�����ͬ��Ϣ
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public SecuritiesContractInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException;

	/**
	 * �����ͬ��Ϣ���������޸ĺ�ͬ
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long save(SecuritiesContractInfo info) throws java.rmi.RemoteException, SecuritiesException;

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