/*
 * Created on 2006-4-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transfinance.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfinance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceNewInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author feiye
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransFinance extends javax.ejb.EJBObject
{

	/** 融资租赁存款 - 收款 start* */
	// 收款交易的保存
	public long receiveSave(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException;

	// 收款交易的暂存
	public long receiveTempSave(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException;

	// 收款交易的匹配
	public Collection receiveMatch(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException;

	// 收款交易的链接查找
	public Collection receiveFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;

	// 收款交易的根据ID查找
	public TransReceiveFinanceInfo receiveFindByID(long lID) throws IRollbackException, RemoteException;

	// 收款交易的根据交易号查找
	public TransReceiveFinanceInfo receiveFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;
	/**
	 * 审批流：Added by zwsun, 2007-6-20
	 * 方法说明：审批方法
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransReturnFinanceInfo info)
		throws RemoteException, IRollbackException;
	/**
	 * 审批流：Added by zwsun, 2007-6-20
	 * 方法说明：审批拒绝
	 *  Method  doApproval. 
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransReturnFinanceInfo info)
		throws RemoteException, IRollbackException;
	
	// 收款交易的复核
	public long receiveCheck(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException;

	// 收款交易的取消复核
	public long receiveCancelCheck(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException;

	// 收款交易的删除
	public long receiveDelete(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException;

	//收款交易的继续
	public TransReceiveFinanceInfo receiveNext(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException;

	/** 融资租赁存款 - 收款 end* */
	
	
	/** 融资租赁存款 - 付款 start* */
	// 付款交易的保存
	public long returnSave(TransReturnFinanceInfo info) throws IRollbackException, RemoteException;

	// 付款交易的暂存
	public long returnTempSave(TransReturnFinanceInfo info) throws IRollbackException, RemoteException;

	// 付款交易的匹配
	public Collection returnMatch(TransReturnFinanceInfo info) throws IRollbackException, RemoteException;

	// 付款交易的链接查找
	public Collection returnFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;

	// 付款交易的根据交易号查找
	public TransReturnFinanceInfo returnFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;

	// 付款交易的继续
	public TransReturnFinanceInfo returnNext(TransReturnFinanceInfo info) throws IRollbackException, RemoteException;

	// 付款交易的根据ID查找
	public TransReturnFinanceInfo returnFindByID(long lID) throws IRollbackException, RemoteException;

	// 付款交易的复核
	public long returnCheck(TransReturnFinanceInfo info) throws IRollbackException, RemoteException;

	// 付款交易的取消复核
	public long returnCancelCheck(TransReturnFinanceInfo info) throws IRollbackException, RemoteException;

	// 付款交易的删除
	public long returnDelete(TransReturnFinanceInfo info) throws IRollbackException, RemoteException;
	/** 融资租赁存款 - 付款 end* */
	
	//批量还款
	public int quantityRepaymentBalance_createRecord(TransReturnFinanceNewInfo trfi,String balanceType) throws IRollbackException, RemoteException;
	//批量还款--链接查找删除
	public void hrefFindDelete(String deleteParam) throws IRollbackException, RemoteException;

	/**
	 * @author yunchang
	 * @date 2010-07-02
	 * @function 融资租赁-收款-已收保证金金额
	 * @param long 
	 * @return double
	 * @throws IRollbackException RemoteException
	 */
	public double getMbailamount(long constractID) throws IRollbackException, RemoteException;
}
