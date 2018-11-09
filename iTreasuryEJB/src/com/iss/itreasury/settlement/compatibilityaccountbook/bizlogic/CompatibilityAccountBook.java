/*
 * Created on 2004-8-4
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.compatibilityaccountbook.bizlogic;
import java.rmi.RemoteException;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author gqzhang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface CompatibilityAccountBook extends javax.ejb.EJBObject
{
	/**
	 * Method saveCompatibilityAccountDetails.
	 * �������ҵ���˱�
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveCompatibilityAccountDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method deleteTransCompatibility.
		 * ɾ������ҵ���˱�
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void deleteTransCompatibility(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * Method checkCompatibilityDetails.
	 * ���˼���ҵ��
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * Method cancelCheckCompatibilityDetails.
	 * ȡ�����˼���ҵ��
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method saveAbamentInfoAccountDetails.����ת���������Զ�����ҵ���˱���Ϣ
		 * 
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void saveAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method deleteTransAbament. ɾ��ת���������Զ������˱���Ϣ
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void deleteTransAbament(TransAbatementInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method checkAbamentInfoAccountDetails.����ת���������Զ�����ҵ��
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void checkAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method cancelCheckAbamentInfoAccountDetails.ȡ������ת���������Զ�����ҵ��
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void cancelCheckAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException;
}
