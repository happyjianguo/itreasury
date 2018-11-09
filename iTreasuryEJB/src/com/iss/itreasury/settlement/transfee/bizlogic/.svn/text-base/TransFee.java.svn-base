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
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
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
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long delete(TransFeeInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long check(TransFeeInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long cancelCheck(TransFeeInfo info) throws RemoteException, IRollbackException;

	public Collection findByConditions(TransFeeInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException;

	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransFeeInfo match(TransFeeInfo info)
		throws RemoteException, IRollbackException;

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransFeeInfo
	 * @throws IRollbackException
	 */
	public TransFeeInfo findByID(long lID)
		throws RemoteException, IRollbackException;
}
