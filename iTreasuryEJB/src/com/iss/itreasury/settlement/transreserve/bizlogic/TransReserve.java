package com.iss.itreasury.settlement.transreserve.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transreserve.dataentity.TransReserveInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.IRollbackException;

public interface TransReserve  extends javax.ejb.EJBObject {
	

	public long tempSave(TransReserveInfo info) throws RemoteException, IRollbackException;
	
	public Collection findByConditions(TransReserveInfo info, int orderByType, boolean isDesc) throws RemoteException, IRollbackException;

	/**
	 * Method ׼�������ձ��淽��.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long saveReserveUpreceive(TransReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException;

	/**
	 * Method ׼�������ձ��淽��.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long saveReserveDownReturn(TransReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long ɾ���ļ�¼ID
	 * @throws RemoteException
	 */
	public long deleteReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long ɾ���ļ�¼ID
	 * @throws RemoteException
	 */
	public long deleteReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long ���˵ļ�¼ID
	 * @throws RemoteException
	 */
	public long checkReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long ���˵ļ�¼ID
	 * @throws RemoteException
	 */
	public long checkReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException;
/**
	 * Method delete.
	 * @param info
	 * @return long ȡ�����˵ļ�¼ID
	 * @throws RemoteException
	 */
	public long cancelCheckReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long ȡ�����˵ļ�¼ID
	 * @throws RemoteException
	 */
	public long cancelCheckReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException;


	/**
	* ����˵�������ݲ�ѯ����ƥ��
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransReserveInfo matchReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException;
	/**
	* ����˵�������ݲ�ѯ����ƥ��
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransReserveInfo matchReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: ���� 1: ί��ƾ֤������--��ʾ���� 2: Ʊ�ݲ����� 3: �ظ����׼�¼ 4: �˻�͸֧
	 * @throws RemoteException
	 */
	public long preSave(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * ����˵���������˻�ID���õ��˻���Ϣ
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IRollbackException
	 */
	public TransReserveInfo findByID(long transID) throws RemoteException, IRollbackException;

}
