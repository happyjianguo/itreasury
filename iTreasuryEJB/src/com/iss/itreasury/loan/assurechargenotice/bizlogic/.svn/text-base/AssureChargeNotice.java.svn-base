/*
 * Created on 2004-11-29
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.assurechargenotice.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureAdjustInfo;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeQueryInfo;
import com.iss.itreasury.loan.base.*;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface AssureChargeNotice extends javax.ejb.EJBObject
{
	/**
	 * 通知单的保存操作
	*/
	public long save(AssureChargeNoticeInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 * 通知单的审核操作
	*/
	public void check(ApprovalTracingInfo info ) throws java.rmi.RemoteException, LoanException;

	/**
	 * 通知单的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, LoanException;
	
	/**
	 * 通知单的单笔查询操作
	*/
	public AssureChargeNoticeInfo findByID(long lID) throws java.rmi.RemoteException, LoanException;
	
	/**
	 * 通知单的多笔查询操作
	*/
	public Collection findByMultiOption(AssureChargeQueryInfo qInfo) throws java.rmi.RemoteException, LoanException;
	
	/**
	 * 新增保证金调整通知单
	*/
	public long saveAdjustAssure(AssureAdjustInfo info) throws java.rmi.RemoteException, LoanException;
	
	/**
	 * 审核保证金调整通知单
	*/
	public long checkAdjustAssure(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException;

	/**
	 * 取消保证金调整通知单
	*/
	public long cancleAdjustAssure(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 * 查找单条保证金调整通知单
	*/
	public AssureAdjustInfo findAdjustByID(long lID) throws java.rmi.RemoteException, LoanException;

	/**
	 * 查询保证金调整通知单
	*/
	public Collection findAdjustByMultiOption(AssureAdjustInfo info) throws java.rmi.RemoteException, LoanException;
	
	/**
	 * 担保通知单审批
	*/
	public long doApprovalNotice(AssureChargeNoticeInfo nInfo) throws RemoteException,
	IRollbackException,IException;
	

	public long cancelApproval(AssureChargeNoticeInfo nInfo)throws RemoteException, IRollbackException;
	
	//查询未收款金额(合同未做收款通知单金额)
	public double findUnPayAmountByID(long lContractID,long payID) throws java.rmi.RemoteException, LoanException;
}