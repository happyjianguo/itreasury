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
	 * 保存兼容业务账薄
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveCompatibilityAccountDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method deleteTransCompatibility.
		 * 删除兼容业务账薄
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void deleteTransCompatibility(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * Method checkCompatibilityDetails.
	 * 复核兼容业务
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException;
	/**
	 * Method cancelCheckCompatibilityDetails.
	 * 取消复核兼容业务
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method saveAbamentInfoAccountDetails.保存转贴现卖出自动冲销业务账薄信息
		 * 
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void saveAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method deleteTransAbament. 删除转贴现卖出自动冲销账薄信息
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void deleteTransAbament(TransAbatementInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method checkAbamentInfoAccountDetails.复核转贴现卖出自动冲销业务
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void checkAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException;
	/**
		 * Method cancelCheckAbamentInfoAccountDetails.取消复核转贴现卖出自动冲销业务
		 * @param transInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
	public void cancelCheckAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException;
}
