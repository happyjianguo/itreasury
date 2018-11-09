/**
 * Creat by kevin(刘连凯)
 * 2011-07-13
 * 内部拆借业务
 */
package com.iss.itreasury.settlement.transinternallend.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transinternallend.dataentity.QueryInternalLendConditionInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.util.IRollbackException;

public interface TransInternalLend extends javax.ejb.EJBObject{
	/**
	 * 内部拆借业务-暂存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transTempSave(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借业务-保存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transSave(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 业务处理/业务复核链接查找
	 * @param queryInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection transFindByStatus(QueryInternalLendConditionInfo queryInfo) throws RemoteException, IRollbackException;
	/**
	 * 通过id查询详细信息
	 * @param id
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public TransInternalLendInfo FindTransInternalLendDetailByID(long id) throws RemoteException, IRollbackException;
	/**
	 * 删除操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transDelete(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 *  内部拆借-匹配操作
	 * @param conditonInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo match(TransInternalLendInfo conditonInfo) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借-复核操作
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借-取消复核
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelCheck(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回-暂存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transRepaymentTempSave(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回-保存
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transRepaymentSave(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回业务处理/业务复核链接查找
	 * @param queryInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection transRepaymentFindByStatus(QueryInternalLendConditionInfo queryInfo) throws RemoteException, IRollbackException;
	/**
	 * 通过id查询内部拆借收回详细信息
	 * @param id
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public TransInternalLendInfo FindTransInternalLendRepaymentDetailByID(long id) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回-删除
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public long transRepaymentDelete(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回-匹配
	 * @param conditonInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo repaymentMatch(TransInternalLendInfo conditonInfo) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回-复核
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCheck(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	/**
	 * 内部拆借收回-取消复核
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCancelCheck(TransInternalLendInfo info) throws RemoteException, IRollbackException;
	
	

}
