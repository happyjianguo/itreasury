/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfee.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransFee extends javax.ejb.EJBObject
{
	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: ���� 1: ί��ƾ֤������--��ʾ���� 2: Ʊ�ݲ����� 3: �ظ����׼�¼ 4: �˻�͸֧
	 * @throws RemoteException
	 */
	public long preSave(TransFeeInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long tempSave(TransFeeInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long save(TransFeeInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long ɾ���ļ�¼ID
	 * @throws RemoteException
	 */
	public long delete(TransFeeInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long ���˵ļ�¼ID
	 * @throws RemoteException
	 */
	public long check(TransFeeInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long ȡ�����˵ļ�¼ID
	 * @throws RemoteException
	 */
	public long cancelCheck(TransFeeInfo info) throws RemoteException, IRollbackException;

	public Collection findByConditions(TransFeeInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException;

	/**
	* ����˵�������ݲ�ѯ����ƥ��
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransFeeInfo match(TransFeeInfo info)
		throws RemoteException, IRollbackException;

	/**
	 * ����˵���������˻�ID���õ��˻���Ϣ
	 * @param transCurrentDepositID
	 * @return Sett_TransFeeInfo
	 * @throws IRollbackException
	 */
	public TransFeeInfo findByID(long lID)
		throws RemoteException, IRollbackException;
}
