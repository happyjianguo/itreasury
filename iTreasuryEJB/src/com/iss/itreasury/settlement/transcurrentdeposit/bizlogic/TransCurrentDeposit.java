/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcurrentdeposit.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransCurrentDeposit extends javax.ejb.EJBObject
{
	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 */
	public long preSave(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;

	public long preSave(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException;
	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long tempSave(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;

	public long tempSave(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long save(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;

	public long save(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException;
	
	public long saveDebitInterestInfo(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;
	public void saveDebitInterest(Vector currentVec) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long delete(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;

	public long delete(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long check(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;

	public long check(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException;
	/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long cancelCheck(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;

	public long cancelCheck(TransOnePayMultiReceiveInfo info) throws RemoteException, IRollbackException;

	public Collection findByConditions(TransCurrentDepositAssembler info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException;

	public Collection findByConditions(TransOnePayMultiReceiveInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException;
	
	/**
	 * 供一付多收勾账查询使用
	 * @param info
	 * @param orderByType
	 * @param isDesc
	 * @return Collection
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public Collection findByConditionsForSquareUp(TransOnePayMultiReceiveInfo info, int orderByType, boolean isDesc)
			throws RemoteException, IRollbackException;
	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransCurrentDepositInfo match(long transactionType, TransCurrentDepositInfo info)
		throws RemoteException, IRollbackException;

	public TransOnePayMultiReceiveInfo match(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException;

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IRollbackException
	 */
	public TransCurrentDepositInfo findBySett_TransCurrentDepositID(long transCurrentDepositID)
		throws RemoteException, IRollbackException;

	public TransOnePayMultiReceiveInfo findBySett_TransOnePayMultiReceiveID(long transCurrentDepositID)
		throws RemoteException, IRollbackException;

	public boolean squareUp(TransOnePayMultiReceiveInfo[] infos) throws RemoteException, IRollbackException;

	public boolean cancelSquareUp(TransOnePayMultiReceiveInfo[] infos) throws RemoteException, IRollbackException;
	
	/**
	 * 自动保存和复核
	 * */
	public TransCurrentDepositAssembler saveAndCheckAutomatically(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;

	/**
	 * 自动保存和复核(网银)
	 * */
	public TransCurrentDepositAssembler saveAndCheckAutomaticallyforEbank(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;
	
	
	/**
	 * 自动保存和复核
	 * 是 saveAndCheckAutomatically 的改进，CheckUserID 就是 InputUserID
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransCurrentDepositAssembler saveAndCheckNew(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;
	
	/**
	 *取消复核与删除交易
	 * */	
	public void cancelSaveAndCheckAutomatically(TransCurrentDepositAssembler info) throws RemoteException, IRollbackException;
	/**
	 * 从网银过来的自动保存复核
	 */
	public String saveAndCheckAutomatically(FinanceInfo info,long userID) throws RemoteException, IRollbackException;	
	
	/**
	 * 提交审批方法
	 */
	public long initApproval(TransCurrentDepositAssembler info)throws RemoteException, IRollbackException;
	
	/**
	 * 审批方法
	 */
	public long doApproval(TransCurrentDepositAssembler info)throws RemoteException, IRollbackException;
	
	/**
	 * 取消审批方法
	 */
	public long cancelApproval(TransCurrentDepositAssembler info)throws RemoteException, IRollbackException;

	public long saveAndCheck(
			TransOnePayMultiReceiveInfo[] transOnePayMultiReceiveInfos,long batchEntity)throws RemoteException, IRollbackException;

}
