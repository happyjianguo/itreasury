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
	 * Method 准备金上收保存方法.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long saveReserveUpreceive(TransReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException;

	/**
	 * Method 准备金上收保存方法.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long saveReserveDownReturn(TransReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long deleteReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long deleteReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long checkReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long checkReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException;
/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long cancelCheckReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long cancelCheckReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException;


	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransReserveInfo matchReserveUpreceive(TransReserveInfo info) throws RemoteException, IRollbackException;
	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransReserveInfo matchReserveDownReturn(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 */
	public long preSave(TransReserveInfo info) throws RemoteException, IRollbackException;

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IRollbackException
	 */
	public TransReserveInfo findByID(long transID) throws RemoteException, IRollbackException;

}
