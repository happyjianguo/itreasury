/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transgeneralledger.bizlogic;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransGeneralLedger extends javax.ejb.EJBObject
{
	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 */
	public long preSave(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long tempSave(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long save(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long delete(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long check(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long cancelCheck(TransGeneralLedgerInfo info) throws RemoteException, IRollbackException;

	public Collection findByConditions(TransGeneralLedgerInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException;

	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransGeneralLedgerInfo match(TransGeneralLedgerInfo info)
		throws RemoteException, IRollbackException;

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransGeneralLedgerInfo
	 * @throws IRollbackException
	 */
	public TransGeneralLedgerInfo findByID(long lID)
		throws RemoteException, IRollbackException;
	
	/**
	 * 方法说明：总账审批
	 * @param TransGeneralLedgerInfo
	 * @return Sett_TransGeneralLedgerInfo
	 * @throws IRollbackException,RemoteException
	 */
	public long doApproval(TransGeneralLedgerInfo info)throws RemoteException, IRollbackException;
	/**
	 * 方法说明：取消审批方法
	 * @param TransGeneralLedgerInfo
	 * @return Sett_TransGeneralLedgerInfo
	 * @throws IRollbackException,RemoteException
	 */
	public long cancelApproval(TransGeneralLedgerInfo info)throws RemoteException, IRollbackException;
}
