/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transferloancontract.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transferloancontract.dataentity.NoticeAndAgentDetailConditionInfo;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.util.IRollbackException;


/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransferLoanContractDeposit extends javax.ejb.EJBObject
{

	public long preSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException;


	public long tempSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException;



	/**
	 * Method delete.
	 * @param info
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long delete(TransferLoanContractInfo info) throws RemoteException, IRollbackException;


	/**
	 * Method delete.
	 * @param info
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long transferCheck(boolean checkOrCancel,TransferLoanContractInfo info) throws RemoteException, IRollbackException;

	/**
	 * Method delete.
	 * @param info
	 * @return long 收款复核的记录ID
	 * @throws RemoteException
	 */
	public long repaytransferCheck(boolean checkOrCancel,TransferLoanContractInfo info) throws RemoteException, IRollbackException;

	/**
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param TransferLoanContractInfo info
	* @return TransferLoanContractInfo
	*/
	public TransferLoanContractInfo transferMatch(TransferLoanContractInfo info)
		throws RemoteException, IRollbackException;

	public Collection findByConditions(TransferLoanContractInfo info)
	    throws RemoteException, IRollbackException;
	public TransferLoanContractInfo findByID(TransferLoanContractInfo info)
        throws RemoteException, IRollbackException;
	
	/**
	 * 查询代收收款通知单和收款业务明细组装数据
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findNoticeAndAgentDetial(NoticeAndAgentDetailConditionInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * 查询代收收款通知单和收款业务明细组装数据(为错误时返回原修改后的数据)
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findNoticeAndAgentDetialForFalse(NoticeAndAgentDetailConditionInfo info,Collection coll) throws RemoteException, IRollbackException;
	
	/**
	 * 代收成员单位保存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long preClientSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException;


	/**
	 * 代收成员单位暂存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long tempClientSave(TransferLoanContractInfo info) throws RemoteException, IRollbackException;


	/**
	 * 代收成员单位删除
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long clientDelete(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
	public long transferClientCheck(boolean checkOrCancel,TransferLoanContractInfo info) throws RemoteException, IRollbackException;

	/**
	 * 查询非代收收款通知单业务明细组装数据
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findTransferloandetailByTransferId(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
	/**
	 * 非代收交易对手
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long preSaveNoProxy(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	/**
	 * 非代收交易对手 删除
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long deleteNoProxy(TransferLoanContractInfo info) throws RemoteException, IRollbackException;

	/**
	 * 通过交易号查询
	 *作者：赵敏
	 *时间：2009-8-27上午10:35:56
	 *@param info
	 *@return
	 *@throws RemoteException
	 *@throws IRollbackException
	 *TransferLoanContractInfo
	 */
	public TransferLoanContractInfo findInfoByTransNo(TransferLoanContractInfo info) throws RemoteException, IRollbackException;
	
}
