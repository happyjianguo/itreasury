/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

package com.iss.itreasury.settlement.transmargindeposit.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginOpenInfo;
import com.iss.itreasury.settlement.transmargindeposit.dataentity.TransMarginWithdrawInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author gqfang
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransMarginDeposit extends javax.ejb.EJBObject
{

	/** 保证金存款 - 开立 start* */
	// 开立交易的保存
	public long openSave(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// 开立交易的暂存
	public long openTempSave(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// 开立交易的匹配
	public Collection openMatch(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// 开立交易的链接查找
	public Collection openFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;


	// 开立交易的根据ID查找
	public TransMarginOpenInfo openFindByID(long lID) throws IRollbackException, RemoteException;


	// 开立交易的根据交易号查找
	public TransMarginOpenInfo openFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;


	// 开立交易的复核
	public long openCheck(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// 开立交易的取消复核
	public long openCancelCheck(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	// 开立交易的删除
	public long openDelete(TransMarginOpenInfo info) throws IRollbackException, RemoteException;


	/** 保证金存款 - 开立 end* */

	
	
	
	/** 保证金存款 - 支取 start* */
	// 支取交易的保存
	public long drawSave(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// 支取交易的暂存
	public long drawTempSave(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// 支取交易的匹配
	public Collection drawMatch(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// 支取交易的链接查找
	public Collection drawFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException, RemoteException;


	// 支取交易的根据交易号查找
	public TransMarginWithdrawInfo drawFindByTransNo(String strTransNo) throws IRollbackException, RemoteException;


	// 支取交易的继续
	public TransMarginWithdrawInfo drawNext(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// 支取交易的根据ID查找
	public TransMarginWithdrawInfo drawFindByID(long lID) throws IRollbackException, RemoteException;


	// 支取交易的复核
	public long drawCheck(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// 支取交易的取消复核
	public long drawCancelCheck(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;


	// 支取交易的删除
	public long drawDelete(TransMarginWithdrawInfo info) throws IRollbackException, RemoteException;
	/** 保证金存款 - 支取 end* */
    
	
	
    //	审批方法（开立）/
	public long doApproval(TransMarginOpenInfo info)throws RemoteException, IRollbackException;
    //	审批方法（开立）/
	public long cancelApproval(TransMarginOpenInfo info)throws RemoteException, IRollbackException;
	public long cancelApproval(TransMarginWithdrawInfo info) throws RemoteException, IRollbackException;
	
	
	//审批方法（支取）/
	public long doApproval(TransMarginWithdrawInfo info)throws RemoteException, IRollbackException;
}
